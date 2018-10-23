import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 3:53 PM
 */
public class MinByEntrance {

    public static void main(String[] args) {

        // MinBy其实就可以用Stream的min来代替即可
        Optional<User> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.minBy(Comparator.comparingInt(a -> a.score)));

        // Optional<Test>也可以通过map方法将Test转换为其它的类型然后返回的还是Optional类型
        System.out.println(collect.map(user -> user.name));
    }
}
