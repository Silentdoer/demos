import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 10:39 AM
 */
public class GroupingBy_ReducingEntrance {

    public static void main(String[] args) {

        // 用reducing统计同一个名字的用户的分数值
        Map<String, Optional<User>> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.groupingBy(u -> u.name, Collectors.reducing((a, b) -> {
                    a.score += b.score;
                    return a;
                })));
        System.out.println(collect);

        Map<String, User> collect1 = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // 这两种情况下的reducing返回的类型都是T类型而不能顺便进行一个转换
                .collect(Collectors.groupingBy(u -> u.name, Collectors.reducing(new User(3, "mmm"), (a, b) -> {
                    a.score += b.score;
                    return a;
                })));
        System.out.println(collect1);

        Map<String, Integer> collect2 = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // 注意，这个三个参数的reducing和三个参数的reduce是不一样的，这里第二个是将T先转换为U，第三个参数才是accumulator，而reduce的第三个参数combiner，它是在第二个参数里同时完成map和累加
                // 个人感觉reducing更好理解一点，但是实用还是reduce要好，比如我的identity是StringBuilder，reducing则必须先转换为StringBuilder，而reduce可以用StringBuilder来append
                .collect(Collectors.groupingBy(u -> u.name, Collectors.reducing(0, a -> a.score, (a, b) -> a + b)));
        System.out.println(collect2);

        StringBuilder collect3 = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO 如果是reduce，第二个参数可以改写为 (sb, e) -> sb.append(e.name)，这样就可以不用将e.name先转换为StringBuilder了
                .collect(Collectors.reducing(new StringBuilder(1024), e -> new StringBuilder(e.name), (a, b) -> a.append(b)));
        System.out.println(collect3);
    }
}
