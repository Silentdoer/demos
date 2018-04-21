import test.B;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Entrance {
    // BootstrapClassLoader,SystemClassLoader,ExtClassLoader,AppClassLoader,CustomClassLoader
    public static void main(String[] args){
        B b = new B();
        b.bb();
        try {
            try (InputStream is = new ByteArrayInputStream("我好".getBytes("UTF-8"))) {
                System.out.println("is 会自动close");
            }

        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        //System.out.println(StackTraceHelper.getCurLineInfo());
//        for(Iterator ite = System.getProperties().entrySet().iterator(); ite.hasNext();){
//            System.out.println(ite.next());
//        }
    }
}
