import silentdoer.web.client.NioClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-1-29 14:21
 */
public class EntranceClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e.toString());
            }
        });

        NioClient client = NioClient.createClient(Charset.forName("UTF-8"), new InetSocketAddress("127.0.0.1", 56368));
        client.connect();
        String line;
        Scanner scanner = new Scanner(System.in);
        while(!"".equals(line = scanner.nextLine())) {
            client.write(ByteBuffer.wrap(line.getBytes("UTF-8")));
        }
        client.stop();
        //client.client.shutdownOutput();
        System.exit(-1);
    }
}
