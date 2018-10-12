package me.silentdoer.sourceanalysis.core;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 后续可以用接口抽象化，然后生成JavaSourceHelper、KotlinSourceHelper等等，不过目前只需java即可；
 * <p>
 * 目前只对Pojo类进行操作（可以有简单的继承关系和实现关系）
 * <p>
 * 需要做的工作有：获取当前类的包路径；获取当前类的类名；获取当前类的父类名；获取当前类的所有私有的字段；
 * 获取父类的包路径；
 * <p>
 * 由于是简单的pojo，所以这里规定类不能有内部类，所有的引用都放到最上面，规定文件都是UTF-8格式，且规定类是public的
 *
 * 由于java规定只能单继承，所以简化了判断，规定实现的父类文件必须是在项目里而非在jar包里（毕竟如果还要扫描jar包花费的时间太大）
 * 且要求父类不能是JDK自带的类，如Object，Long之类的因为没有意义；
 *
 * 规定，如果存在父类名是A，而又需要用到另一个类但名字也是A，那么父类的A import，另一个A类用绝对包路径来引用；
 * 如果不按规定继承了在jar包里的父类，那么只能说后果自负（不是啥严重后果但是肯定结果不完整）
 *
 * 规定成员的类型都是JDK自带的常见类型（它们是能和数据库类型对应的），如基础类型加上 Date、BigDecimal等类型
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 4:37 PM
 */
public class JavaSourceHelper {

    /**
     * 各类Pattern，注意，使用java正则表达式必须设置 TODO MultiLine，否则^是匹配整个字符串的第一个位置，因此会匹配失败
     */
    private final Pattern PACKAGE_PATTERN = Pattern.compile("^\\s*package\\s*(\\S*)(?=;)", Pattern.MULTILINE);

    private final Pattern CLASS_NAME_PATTERN = Pattern.compile("^\\s*public\\s*class\\s*(\\w*)(?=(\\s*\\{)|(\\s*extends)|(\\s*implements))", Pattern.MULTILINE);

    private final Pattern SUPER_CLASS_PATTERN = Pattern.compile("^\\s*public\\s*class.*extends\\s*(\\w*)\\b", Pattern.MULTILINE);

    private final Pattern SUPER_CLASS_PACKAGE_PATTERN = Pattern.compile("^\\s*import\\s*(\\S*)\\.(\\w*);", Pattern.MULTILINE);

    private final Pattern AVAIL_FIELD_PATTERN = Pattern.compile("^\\s*private\\s(\\w*)\\s(\\w*);", Pattern.MULTILINE);

    private String sourceContent;

    /**
     * 类的绝对路径名
     */
    private String absClsName;

    /**
     * 当前类名
     */
    private String className;

    /**
     * 父类的绝对类名
     */
    private String superAbsClsName;

    /**
     * 父类名
     */
    private String superClassName;

    /**
     * 所有的实例private字段，有如下规定：就是private TypeName fieldName，这里需要存储字段名和其对于类型;
     */
    private Map<String, String> fields;

    public JavaSourceHelper(String sourcePath) {
        try {
            sourceContent = FileUtils.readFileToString(new File(sourcePath), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fields = new LinkedHashMap<>();
    }

    /**
     * 这里我有点理解Spring的PropertyEditor为什么是线程不安全的了，它也是类似这里有个absClassName，然后如果每次
     * 都这样获取一次显然是浪费时间的，而且其他结果是依赖这个结果的，所以就进行了缓存，但是缓存的情况如果并发执行就会导致数据的脏读
     */
    public synchronized String getAbsClassName() {
        if (Objects.isNull(this.absClsName)) {
            Matcher matcher = PACKAGE_PATTERN.matcher(this.sourceContent);
            if (!matcher.find()) {
                return this.getClassName();
            } else {
                String absClsName = String.format("%s.%s", matcher.group(1), this.getClassName());
                this.absClsName = absClsName;
                return absClsName;
            }
        } else {
            return this.absClsName;
        }
    }

    public synchronized String getClassName() {
        if (Objects.isNull(this.className)) {
            // 这一步相当于把Pattern填写到搜索栏，但是还没有开始搜索，因此需要find()
            Matcher matcher = CLASS_NAME_PATTERN.matcher(this.sourceContent);
            if (!matcher.find()) {
                throw new IllegalStateException("源码不合要求或解析工具出错");
            }
            this.className = matcher.group(1);
            return this.className;
        } else {
            return this.className;
        }
    }

    public synchronized String getSuperClsName() {
        if (Objects.isNull(this.superClassName)) {
            Matcher matcher = SUPER_CLASS_PATTERN.matcher(this.sourceContent);
            if (!matcher.find()) {
                return null;
            }
            this.superClassName = matcher.group(1);
            return this.superClassName;
        } else {
            return this.superClassName;
        }
    }

    public synchronized String getAbsSuperClsName() {
        if (Objects.isNull(this.superAbsClsName)) {
            String superClsName = this.getSuperClsName();
            // 如果连父类都没有，自然直接返回null即可，否则查找此父类的包名
            if (Objects.isNull(superClsName)) {
                return null;
            } else {
                Matcher matcher = SUPER_CLASS_PACKAGE_PATTERN.matcher(this.sourceContent);
                // 匹配import中的类符合找到的父类名
                String matchesPackage = null;
                while (matcher.find()) {
                    String tmp = matcher.group(2);
                    if (StringUtils.equals(superClsName, tmp)) {
                        matchesPackage = matcher.group(1);
                    }
                }
                if (Objects.isNull(matchesPackage)) {
                    throw new IllegalStateException("不允许继承Long、Object等无意义的类型");
                }
                this.superAbsClsName = String.format("%s.%s", matchesPackage, superClsName);
                return this.superAbsClsName;
            }
        } else {
            return this.superAbsClsName;
        }
    }

    public synchronized Map<String, String> getAvailFields() {
        if (MapUtils.isEmpty(this.fields)) {
            Map<String, String> result = new LinkedHashMap<>();
            Matcher matcher = AVAIL_FIELD_PATTERN.matcher(this.sourceContent);
            while (matcher.find()) {
                result.put(matcher.group(2), matcher.group(1));
            }
            this.fields.putAll(result);
            return result;
        } else {
            return this.fields;
        }
    }

    public boolean correspondClass(String absClsName) {
        String tmp = this.getAbsClassName();
        if (StringUtils.equals(absClsName, tmp)) {
            return true;
        }
        return false;
    }
}
