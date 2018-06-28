import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.*;
import com.google.common.io.Files;
import org.joda.time.DateTime;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class GuavaEntrance {
    public static void main(String[] args) throws InterruptedException, IOException {
        PushbackInputStream inputStream = new PushbackInputStream(new ByteArrayInputStream(new byte[256]));

        // TODO Multimap其实是key可以重复，而Multiset是基于Multimap，因此也是可重复
        com.google.common.collect.Multimap<String, String> map = ArrayListMultimap.create();
        map.put("aa", "mmm");
        map.put("aa", "uuu");
        map.put("bb", "ttt");
        System.out.println(map.toString());
        Map<String, Collection<String>> stringCollectionMap = map.asMap();
        Collection<String> aa = stringCollectionMap.get("aa");
        System.out.println(aa.size());

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        TimeUnit.SECONDS.sleep(2);
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        ImmutableBiMap<String, String> immutableBiMap = ImmutableBiMap.of("sss", "mmm");
        System.out.println(immutableBiMap.get("sss"));
        // 通过value找key
        System.out.println(immutableBiMap.inverse().get("mmm"));

        // TODO 创建不可变的List，所谓不可变就是说所有内部值的初始化都是在构建的时候执行，之后这个List将不可再set、add、remove（set即update），只能查询（get）
        // JDK里的Collections.unmodify...的也是，但是这个效率不高；
        ImmutableList<Long> immutableList = ImmutableList.of(3L, 4L, 8L, 2L);
        System.out.println(immutableList.reverse());

        // TODO 可重复的set，通过count来检测当前的Set有多少个参数元素
        Multiset<Long> multiset = HashMultiset.create();
        multiset.add(3L);
        multiset.add(8L);
        multiset.add(3L);
        System.out.println(multiset.count(3L));
        System.out.println(Joiner.on("##").join(multiset));

        Table table = HashBasedTable.create();
        Table.Cell<String, String, Integer> score = Tables.immutableCell("silentdoer", "C++", 90);

        HashMap<String, String> map2 = Maps.newHashMap();
        map2.put("aa", "uuu");
        map2.put("bb", "sss");
        map2.put("cc", "ttt");
        String join = Joiner.on("-").withKeyValueSeparator(",").join(map2);
        System.out.println(join);

        String strTT = "22,bb,cc,dd";
        // 还能将String转换为Map
        List<String> strings = Splitter.on(",").splitToList(strTT);
        System.out.println(strings);

        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or(Predicates.equalTo("Guava"), Predicates.equalTo("Java")));
        System.out.println(fitered);

        // TODO 可以用来代替Spring的 Assert
        Preconditions.checkNotNull(names, "names不能为null");
        // IllegalArgumentException
        //Preconditions.checkArgument(names);
        // IndexOutOfBoundsException
        //Preconditions.checkElementIndex()
        // IndexOutOfBoundsException
        //Preconditions.checkPositionIndex()
        // IllegalStateException
        //Preconditions.checkState();

        // 实现了Comparator接口，可以不用自己写很多的Comparator的逻辑了
        Ordering<Comparable> natural = Ordering.natural();

        // 这个是guava里的，JDK里也有这个类，TODO 用JDK的，看了下JDK的就已经很足够了
        //Files.copy(new File(""), new File(""));

        // 类似Map，但是可以设置最大值和过期时间之类的，还有回调函数；
        CacheBuilder<Object, Object> objectObjectCacheBuilder = CacheBuilder.newBuilder().maximumSize(10L).expireAfterAccess(Duration.ofMinutes(1));

        // joda-time的Date类，可以转换为JDK的Date对象
        DateTime dt = new DateTime();
        Date d = dt.toDate();
        Calendar calendar = dt.toCalendar(Locale.CHINA);


    }
}
