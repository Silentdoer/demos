import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 3:46 PM
 */
public class PartitioningByEntrance {

    public static void main(String[] args) {

        // TODO 按照某个要求来对集合进行分割为两部分（其实就类似groupingBy）【这里partitioningBy后如果没有第二个参数则将会是List<User>而非Set<String>
        Map<Boolean, Set<String>> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.partitioningBy(e -> e.score > 6, Collectors.mapping(e -> e.name, Collectors.toSet())));

        System.out.println(collect);
    }
}
