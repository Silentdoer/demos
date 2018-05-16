package me.silentdoer.springbootsession;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args){
        // TODO 如果不强制转换，那么返回的类型就是String，那么t.show(..)的返回类型就是String，从而往上递推出test的T是String类型
        // TODO 因此其实也是可以推算出泛型链上所有的泛型类型的，但是java不允许从底层往高层递推，只能从调用出一级一级往下推算泛型是什么
        // TODO 而不能从调用处 a 推算出 被调用出 b 的下一级 c 的泛型，只能先推算 b 然后推算 c；
        System.out.println(test(str -> "mmmmmm"));
    }

    private static String test(ITest<String> t){
        System.out.println("SSSSSSSSS");
        return t.show("UUUUUUUUU");
    }
}
