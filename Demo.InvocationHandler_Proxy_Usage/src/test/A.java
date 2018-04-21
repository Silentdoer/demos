package test;


public class A {
    private  int pro;
    @Deprecated
    public A(int pro, long pro2){
        this.pro = pro;
    }

    public int getPro(){
        return this.pro;
    }

    public void setPro(int pro){
        this.pro = pro;
    }
}
