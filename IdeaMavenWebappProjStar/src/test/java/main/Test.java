package main;

public class Test {

    public static void main(String[] args){
        //assert(args == null); 尽量不要用assert关键字
        System.out.println("有些测试用JUnit4无法测试，比如Aop[在Eclipse里个人测试不了]，这时候就需要在main方法里测试了");
    }
}
