import sun.reflect.Reflection;
import test.A;
import test.TestInterface;
import test.TestInterface2;
import test.TestInterfaceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Entrance {
    public static void main(String[] args) throws Exception{
//        int[] eles = new int[]{1,26,88,3,90,30,2,67,34,21};
//        int index = Arrays.binarySearch(eles, 90);
//        System.out.println(index);
        System.out.println(A.class.getAnnotations().length);
        Constructor<A> cons = A.class.getConstructor(int.class, long.class);  // 默认构造函数也能用这种方式创建对象
        System.out.println(cons.getAnnotations().length);
        A a = (A)cons.newInstance(new Object[]{3, 8});
        //A a = (A)cons.newInstance(3, 8);  // 这个是Java的语法糖，如果一个都不写实际上内部会有个0长度的数组
        System.out.println(a.getPro());

        System.out.println(TestInterfaceImpl.class.getInterfaces().length);
        TestInterface ins = new TestInterfaceImpl();
        // 这里的ClassLoader是后面接口的类加载器，可以写成TestInterfaceImpl.class.getClassLoader()
        Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), TestInterfaceImpl.class.getInterfaces(), new InvocationHandlerImpl(ins));
        ((TestInterface)proxy).print();
        System.out.println("$$$:" + proxy.getClass());
        Method method = proxy.getClass().getMethod("print", new Class[0]);
        method.invoke(proxy, new Object[]{});
        print(3);
    }

    // 这应该说是IDE的语法糖而不是Java语法糖
    private static void print(int a, Object ...args){
        System.out.println(a + "#####" + args.length);
    }

    // 和上面重复
//    private static void print(int a, Object[] args){
//        System.out.println(a + "#####" + args.length);
//    }

    private static class InvocationHandlerImpl implements InvocationHandler{
        private Object target;

        public InvocationHandlerImpl(Object target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            System.out.println("proxyClass: " + proxy.getClass());
            System.out.println("methodName: " + method.getName());
            Method method2 = TestInterface.class.getMethod("print");
            System.out.println(method2.equals(method));
            Method method3 = TestInterface2.class.getMethod("print");
            System.out.println("###" + method.equals(method3));
            System.out.println(method.getDeclaringClass());
            System.out.println(method3.getDeclaringClass());
            System.out.println(method.getClass());
            System.out.println("args is null: " + (args == null));
            long startTime = System.nanoTime();
            Object result = method.invoke(this.target, args);
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println(String.format("经过的时间: %s 纳秒", elapsedTime));
            return result;
        }
    }
}
