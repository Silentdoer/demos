import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/18/2018 12:01 PM
 */
public class StreamOfEntrance {

    public static void main(String[] args) {
        List<Integer> collect = Stream.of(1, 2, 5, 3).sorted().collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(Stream.of(1, 2, 5, 3).sorted((a, b) -> b - a).collect(Collectors.toList()));
    }
}
