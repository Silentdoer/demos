import java.util.ArrayList;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-8 10:20
 */
public class ArrayListEntranceTest {
    public static void main(String[] args){
        ArrayList<Long> list = new ArrayList<>(16);
        list.add(8L);
        ArrayList<String> list2 = new ArrayList<>(16);
        list2.add("BB");
        System.out.println(list.getClass());
        System.out.println(list2.getClass());  // 都是ArrayList类，没有泛型（但是C#里会是ArrayList<Long>和ArrayList<String>这两个 类）
        System.out.println(list.getClass() == list2.getClass());  // true
        System.out.println(list.getClass().equals(list2.getClass()));  // true
    }
}
