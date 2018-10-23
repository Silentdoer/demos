import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 3:23 PM
 */
public class ReduceEntrance {

    public static void main(String[] args) {
        ArrayList<Integer> reduce = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .reduce(new ArrayList<Integer>(), (r, e) -> {
                    r.add(e.score);
                    return r;
                }, (a, b) -> a);
    }
}
