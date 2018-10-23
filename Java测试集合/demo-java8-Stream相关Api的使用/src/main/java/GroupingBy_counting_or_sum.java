import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 2:57 PM
 */
public class GroupingBy_counting_or_sum {

    public static void main(String[] args) {
        // TODO groupingBy后再对分组进行counting和sum的聚合操作
        // 统计同一个名字的用户的个数，key是name，value是该name的记录的个数
        Map<String, Long> groupCounting = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.groupingBy(u -> u.name, Collectors.counting()/*counting是groupingBy的参数而非collect的*/));
        // 输出{aaa=2, bbb=1}
        System.out.println(groupCounting);

        // 统计同一个名字的用户的分数值
        Map<String, Integer> groupSum = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO 这里与summingInt对应的还有summingDouble和summingLong（注意左边的变量类型也要改）
                .collect(Collectors.groupingBy(u -> u.name, Collectors.summingInt(u -> u.score)));
        System.out.println(groupSum);
        // 是HashMap
        System.out.println(groupSum.getClass());

        Map<String, Integer> groupSum2 = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO 第二个参数是Supplier，即最终结果容器的提供者
                .collect(Collectors.groupingBy(u -> u.name, TreeMap::new, Collectors.summingInt(u -> u.score)));
        System.out.println(groupSum2);
        System.out.println(groupSum2.getClass());
    }
}
