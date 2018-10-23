import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 3:02 PM
 */
public class SummarizingIntEntrance {

    public static void main(String[] args) {

        // TODO 获得映射后的int元素的 摘要分析（将user转换为score）
        IntSummaryStatistics statistics = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .collect(Collectors.summarizingInt(u -> u.score));

        // 这个摘要分析里包括 平均值、元素个数、元素最大/最小值、元素值总和
        System.out.printf("%s,%s,%s,%s,%s", statistics.getAverage(), statistics.getCount(), statistics.getMax(), statistics.getMin(), statistics.getSum()).println();

        // 上面的代码可以直接用mapToInt(..)代替，不过下面的只能一次性获得一种结果，而上面的是一次性将多个结果缓存
        OptionalDouble average = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                .mapToInt(u -> u.score).average();
        average.ifPresent(System.out::println);
    }
}
