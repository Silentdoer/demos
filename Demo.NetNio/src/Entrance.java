import silentdoer.web.server.NioServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Entrance {
    public static void main(String[] args) throws Throwable {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e.toString());
            }
        });
        //List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        //CopyOnWriteArrayList
        //FutureTask
        NioServer server = NioServer.createServer();
        server.init(new InetSocketAddress("127.0.0.1", 56368), Charset.forName("UTF-8"));
        try {
            server.start();  // 不断的在轮询多种操作（Accept、Read等）
        }catch (Throwable th){
            th.printStackTrace();
            server.stop();
        }
    }
}
