package test;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class A {
    public void aa(){
//        int a = 0;
//        a = 3/a;
        //new Exception("");
        try {
            System.out.println(Class.forName(new Throwable().getStackTrace()[1].getClassName()).toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(Reflection.getCallerClass(1));
        //System.out.println(Reflection.get);
    }
}
