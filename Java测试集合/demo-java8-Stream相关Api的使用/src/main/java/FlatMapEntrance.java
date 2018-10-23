import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/18/2018 11:07 AM
 */
public class FlatMapEntrance {

    public static void main(String[] args) {
        // "abc".split("")将会生成String["a","b","c"]
        String[] arr = new String[] {"abc", "def", "opk"};
        // TODO 这里的需求是将上面的合并为 abcdefopk的List，即元素由String变为Character
        // 可以用flatMap，先映射为List<Character>【java里char无法直接转换为Character所以用String代替】，然后扁平化为一个【如果不用flatMap则可以先map然后reduce】
        // map+reduce版本
        List<String> afterFlattening = Arrays.stream(arr).map(e -> new LinkedList(Arrays.asList(e.split("")))).reduce((a, b) -> {
            a.addAll(b);
            return a;
        }).get();
        System.out.println(afterFlattening);
        // flatMap版本，注意flatMap确实是先map，然后flattening是自动执行的；但是要求map部分返回的是stream而非List或数组
        List<String> collect = Arrays.stream(arr).flatMap(a -> Arrays.stream(a.split(""))).collect(Collectors.toList());
        System.out.println(collect);
    }
}
