import lombok.Data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 用于测试子类对象怎么获取父类的泛型的具体类型
 *
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/2/2018 2:56 PM
 */
public class GenericTypeTestEntrance {
    public static void main(String[] args) throws NoSuchFieldException {
        Class<?> clazz = new ParentClass<Long>() {}.getClass();
        Type ge = clazz.getGenericSuperclass();
        if (ge instanceof Class<?>) {
            throw new IllegalStateException("据说这种情况是错误的");
        }
        System.out.println(ge.getClass());

        System.out.println(ge.toString());

        // TODO 下面这两步很重要，很多框架都是通过这两步来获取泛型的具体类型的
        ParameterizedType type = (ParameterizedType) ge;
        // TODO 经过测试，这种方式似乎只能获取到父类里是 SuperClass<T, M...>这种格式的泛型的具体类型
        // TODO ，但是对于其内部的如List<Integer> list;的list的元素的类型似乎不行，必须放到类上面声明；
        System.out.println(Arrays.asList(type.getActualTypeArguments()));

        // output:class java.lang.Class
        System.out.println(type.getActualTypeArguments()[0].getClass());

        // TODO 这个不能获取到泛型的具体类型Class对象，它取出的只是一个字符串而已；
        System.out.println(ge.getTypeName());

        System.out.println("<!--------------------------->");
        Class<?> clazz2 = ChildClass.class;
        Type genericSuperclass = clazz2.getGenericSuperclass();
        ParameterizedType genericSuperclass1 = (ParameterizedType) genericSuperclass;
        System.out.println(Arrays.asList(genericSuperclass1.getActualTypeArguments()));

        Type list = ChildClass.class.getSuperclass().getDeclaredField("list").getGenericType();
        System.out.println(list);
        System.out.println(Arrays.asList(((ParameterizedType) list).getActualTypeArguments()));

        System.out.println("<------------------------>");
        System.out.println(Arrays.asList(((ParameterizedType) new SuperClass<Long, String, String>() {
        }.getClass().getGenericSuperclass()).getActualTypeArguments()));
    }

    @Data
    public static class SuperClass<T1, T2, T3> {}

    @Data
    public static class ParentClass<T> {
        T pro;
        // TODO 这一块是无法反射到的，因为它在字节码里就是List<T>（T是Object的子类），但是Gson似乎能获取到，why？
        List<Integer> list;
    }

    @Data
    public static class ChildClass<String> extends ParentClass<String> {

    }
}
