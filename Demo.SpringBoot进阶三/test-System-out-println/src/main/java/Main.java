import java.io.PrintStream;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        // TODO 这里将System.out::println 换成 PrintStream::println会报错，而System.out确实是PrintStream的对象
        PrintlnDelegate println = System.out::println;
        println.apply("MMMMM");

        // TODO println由于是实例方法，因此它本质上有两个参数，第一个参数是隐藏的PrintStream对象，第二个则是msg
        // TODO 重要，lambda表达式的参数值由 调用 这个lambda表达式的方法内部提供，看test方法即可知；
        // 对于这里就是test内部会提供两个参数，即 TODO lambda表达式 是消费者，
       test(PrintStream::println);

       // TODO 好吧，上面的总结不准确，lambda表达式确实是消费者，但是lambda里可以带有一个对象参数，如System.out::println
        // TODO  lambda表达式 可以不带 this对象，也可以带this对象（但是原理是什么呢，不带的好理解，带this对象的有点迷）
        // TODO 带this对象的lambda表达式应该理解为 委托 对象， 然后委托对象 就 类似Object，它可以承载 任意对象，故delegate在这里就代表System.out对象
       test2(System.out::println);

       // TODO 总结： lambda表达式生成的是一个委托对象（其实就是一个接口对象），然后这个委托对象很特殊，它类似Object可以充当任意对象
        // 或说lambda表达式有能力将实例方法转换成“静态”方法
    }

    public static void test(TestDelegate delegate) {
        delegate.apply(System.out, "uuuu");
    }

    public static void test2(PrintlnDelegate delegate) {
        delegate.apply("UUUUUUUUUUU");
    }

    @FunctionalInterface
    public interface PrintlnDelegate {
        void apply(Object msg);
    }

    @FunctionalInterface
    public interface TestDelegate {
        void apply(PrintStream out, Object msg);
    }
}
