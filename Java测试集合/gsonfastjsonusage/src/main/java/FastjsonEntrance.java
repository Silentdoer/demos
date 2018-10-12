import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/18/2018 3:55 PM
 */
public class FastjsonEntrance {

    public static void main(String[] args) {
        Student stud = new Student();
        stud.setUid("111111");
        stud.setCurrentTime(new Date());
        stud.setName("Silent");
        String result = JSON.toJSONString(stud, new MyValueFilter());
        System.out.println(result);
    }

    public static class MyValueFilter implements ValueFilter {

        /**
         * @param obj 待序列化的对象，即上面的stud
         * @param key 待序列化的对象的某个字段，这里包括uid、name（即序列化一个对象时这个方法是可能调用多次的）
         * @param value 待序列化对象的某个字段的序列化后的值，
         * @return 字段序列化后的值
         */
        @Override
        public Object process(Object obj, String key, Object value) {

            System.err.println(obj.getClass().getName());
            // TODO 重要，value不是经过转换后的值，而就是原值，所以这里返回出去的值还是会被Fastjson再次 处理的，如果返回的是字符串
            // 那么fastjson发现是字符串则可以不再继续处理，但是如果有ValueFilter则还是会被拦截一次的所以需要自己判断如果value是字符串则直接return而不要再JSON.toJSONString(str)；
            System.err.println(key + "##" + value + "##" + value.getClass());

            List<Field> collect = FieldUtils.getAllFieldsList(obj.getClass()).stream().filter(e -> key.equals(e.getName())).collect(Collectors.toList());
            System.err.println(collect.size());

            // 是可能大于1的，即java里允许基类和子类都有某个字段的定义，但是序列化时却只会序列化子类的
            if (!collect.isEmpty() && !Objects.isNull(collect.get(0).getAnnotation(Test.class))) {
                return "啦啦啦";
            } else {
                return value;
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Test {
        String value() default "";
    }

    public static class Student extends Base{

private Date currentTime;

        private String name;

@Test
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(Date currentTime) {
            this.currentTime = currentTime;
        }
    }

    public static class Base {
        @Test
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
