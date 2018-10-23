import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 2:38 PM
 */
public class CollectingAndThenEntrance {

    public static void main(String[] args) {
        Collection<User> collect = Stream.of(new User(6, "aaa"), new User(5, "bbb"), new User(13, "aaa"))
                // TODO collectingAndThen和flatMap很像，就可以理解为它是collect的升级版，即，第一个参数是默认的collect操作的参数【和flatMap的Map操作的参数一样】，第二个参数
                // TODO 则是对第一个参数产生的集合进一步Map操作，这里是对collect的结果集的map操作而非其中某个元素
                // 所以其实很纳闷为什么它不直接放在Stream里而是放在Collectors里？
                .collect(Collectors.collectingAndThen(Collectors.toList(), a -> {a.add(null); return a;}));
    }
}
