import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 5:19 PM
 */
public class GroupingByConcurrentEntrance {

    public static void main(String[] args) {
        // List<User>变成了Set<User>
        Map<String, Set<User>> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.groupingByConcurrent(u -> u.name, Collectors.toSet()));
        System.out.println(collect);
        // java.util.concurrent.ConcurrentHashMap
        System.out.println(collect.getClass());

        // TODO Collectors.xxx返回的都是Collector接口的实例
        final Pattern PATTERN = Pattern.compile("\\s");
        System.out.println(PATTERN.splitAsStream("sfd sfe ttt").flatMap(str -> Arrays.stream(str.split("")))
                                   .collect(Collectors.joining(",", "{", "}")));
    }
}
