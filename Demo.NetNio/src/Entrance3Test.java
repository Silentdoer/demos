/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-1-31 10:34
 */
public class Entrance3Test {
    public static void main(String[] args){
        // 当左侧是?时右侧的<..>不能省略
        TestA<? extends Object> m = new TestA<Integer>();
        m.setPro(null);  // null可以代表任意类型故set null是可以的，但是set其它对象则会报错
        System.out.println(m.getPro().getClass());
    }

    private static class TestA<T>{
        private T pro;

        public T getPro() {
            return pro;
        }

        public void setPro(T pro) {
            this.pro = pro;
        }
    }
}
