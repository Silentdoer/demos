package test;

import sun.reflect.Reflection;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public void print() {
        try {
            //System.out.println(String.format("调用此方法的类：%s", Reflection.getCallerClass().toString()));
            System.out.println(this.getClass() + "输出了根本的");
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }
}
