import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通过Stream统计文件中26个英文字母【忽略大小写】每个字母的出现的个数
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 4:56 PM
 */
public class CountFileWordEntrance {

    public static void main(String[] args) throws IOException {
        String file = "E:\\Documents\\IDEAProjs\\Java测试集合\\demo-java8-Stream相关Api的使用\\src\\main\\java\\CountFileWordEntrance.java";
        Map<String, Long> count = count(Paths.get(file));
        System.out.println(count);
    }

    public static Map<String, Long> count(Path file) throws IOException {
        // Files.lines(file)是将一个文件以行为标准读取为List<String>，不过这里是用Stream所以可以延迟读取
        Map<String, Long> collect = Files.lines(file).flatMap(line -> Arrays.stream(line.replaceAll("[^a-zA-Z]", "").toLowerCase().split("")))
                .filter(line -> !line.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return collect;
    }
}
