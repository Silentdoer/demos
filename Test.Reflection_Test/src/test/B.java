package test;

import util.StackTraceHelper;

public class B {
    public void bb(){
        System.out.println(StackTraceHelper.getCurLineInfo());
        A a = new A();
        a.aa();
    }
}
