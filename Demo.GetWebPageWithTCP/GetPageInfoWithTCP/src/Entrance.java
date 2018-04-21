import java.io.*;
import java.net.*;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

public class Entrance {

    // http://localhost:8080/JustStruts2OK/judge.action?uname=silent
    // http://localhost:8080/JustStruts2OK/
    public static void main(String[] args)throws Exception{
        Socket client = new Socket();
        //client.bind(endpoint);
        URL url = new URL("http://localhost:8080");
        String host = url.getHost();  // localhost
        // url.getDefaultPort()是80
        int port = url.getPort();  // 8080
        //System.out.println(String.format("%s:%s", host, port));
        InetSocketAddress remoteEndpoint = new InetSocketAddress(host, port);
        client.connect(remoteEndpoint);
        OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        String path = "/JustStruts2OK/";  // 注意大小写不能写错
        // bw会等到flush或close或缓冲区满了才一次写入，而OutputStreamWriter则每次write都会flush？？
        // 以下所有的换行也可以写成 \r\n
        bw.write("GET " + path + " HTTP/1.1\n");
        // 这里可以不带端口，因为Socket连接时已经设定好了端口，不过不是80的最好都带上
        bw.write("Host: " + "localhost:8080" + "\n");
        // 接受返回的数据类型，这里只需要有text/html即可
        bw.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n");
        bw.write("Accept-Language: zh-CN,zh;q=0.8\n");
        // 这里所有的数据都是可以逐步flush()的，但是要在1分钟内，因为默认情况下服务端一分钟内没有继续收到某客户端的数据会断开与该客户端的连接(TCP连接）
        // 即这里可以先bw.flush();再继续bw.write("\n");
        bw.write("\n");  // 最后要有个换行（CRLF是回车换行的意思），否则无法获取数据（服务端会等待客户的[此程序]发送最后的换行，如果长时间没有此换行发送过去会忽略此次请求，故这里将没有数据返回）
        bw.flush();  // 不能close，close相当于关闭了Socket，故用flush，否则bw不一定会写入
        // 读取返回数据
        BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
        String line = null;
        //client.setSoTimeout(2000);
        // 注意，这里会读取到null一般情况下都是断开了TCP连接（长时间在Stream里读取不到数据而导致超时的话是抛异常），这里是服务端因为此程序1分钟内没有继续向服务端发送数据主动断开了连接
        // 如果超时时间是无穷大，连接也一直好的，那么这里会一直读取数据
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        // 此时其实连接已经由服务端断开，通过继续给服务端发数据测试，（不能通过isConnected()和isClosed()来测试，这两个貌似只有这里抛了异常或主动关闭才会得到正确值）
        //bw.write("sssd");bw.flush();  // 抛出异常，提示Socket closed
        // 在最后要关闭socket前再来关闭read和write，且只需要关闭最外层的即可Reader或Writer，内部会逐级关闭Stream
        br.close();
        bw.close();
        client.close();
    }
}
