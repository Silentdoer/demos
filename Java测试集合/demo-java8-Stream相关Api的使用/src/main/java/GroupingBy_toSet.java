import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 3:40 PM
 */
public class GroupingBy_toSet {

    public static void main(String[] args) {
        // List<User>变成了Set<User>
        Map<String, Set<User>> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.groupingBy(u -> u.name, Collectors.toSet()));
        System.out.println(collect);
        // 是HashMap
        System.out.println(collect.getClass());

        TreeMap<String, Long> collect1 = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO 这里与summingInt对应的还有summingDouble和summingLong（注意左边的变量类型也要改）
                .collect(Collectors.groupingBy(u -> u.name, TreeMap::new, Collectors.counting()));
    }
}
