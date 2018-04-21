import java.lang.reflect.Array;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-8 10:33
 */
public class JudgeIsArrayEntrance {
    public static void main(String[] args){
        Object arr1 = Array.newInstance(int.class, 3);
        System.out.println(arr1.getClass().isArray());
        int[] arr2 = new int[4];
        Object arr2T = arr2;
        System.out.println("##" + (arr1 == arr2T));  // false
        System.out.println(arr1.getClass().equals(arr2.getClass()));  // true
        Class cls = arr2.getClass();
        System.out.println(cls.isArray());
        System.out.println(cls.getModifiers());  // 1041
        System.out.println(cls.isPrimitive());
        cls.isAnnotation();
        //cls.isAssignableFrom(..);
        cls.isInterface();
        cls.isEnum();
        cls.isSynthetic();  // 是否是综合的/人造的
    }
}
