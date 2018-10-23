import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 2:36 PM
 */
public class Collectors_averagingDouble {

    public static void main(String[] args) {
        // 获取用户的score的平均值
        Double average = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO 这里与还有averagingInt、averagingLong
                .collect(Collectors.averagingDouble(u -> u.score));
        System.out.println(average);
    }
}
