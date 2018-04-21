package silentdoer.web.server;

import org.jetbrains.annotations.NotNull;
import silentdoer.web.model.NioMessage;
import silentdoer.web.util.Bootable;
import silentdoer.web.util.TCPBootable;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个NIO Demo的服务端实现，也可以实现Runnable接口，然后run()方法就相当于start
 * @Author Silentdoer
 * @Version 1.0
 * @Date 2018.01.28
 */
public class NioServer implements TCPBootable {
    /**
     * 用来记录Server的状态
     */
    private volatile NioServerStateEnum state;
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private SocketAddress bindingAddr;
    /**
     * 将字符串转换为字节数组或将字节数组转换为字符串时用到的编码
     */
    private Charset charset;
    private Map<String, SocketChannel> cachedClient;
    private ExecutorService threadPool;

    private NioServer(){
        serverChannel = null;
        selector = null;
        bindingAddr = InetSocketAddress.createUnresolved("localhost", 56568);
        charset = Charset.forName("UTF-8");
        //cachedClient = Collections.synchronizedList(new LinkedList<>());
        cachedClient = new ConcurrentHashMap<>(3);

        class DaemonThreadFactory implements ThreadFactory{
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName(r.toString());
                return t;
            }
        }
        //threadPool = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        threadPool = Executors.newCachedThreadPool(new DaemonThreadFactory());
        state = NioServerStateEnum.GENERATED;
    }

    public static NioServer createServer(){
        return new NioServer();
    }

    @Override
    public void init(SocketAddress address, Charset charset) {
        this.bindingAddr = address;
        this.charset = charset;
        this.state = NioServerStateEnum.INITED;
    }

    @Override
    public void start() throws Throwable {
        System.out.println("NioServer#start()");
        synchronized (state){
            if(!state.equals(NioServerStateEnum.INITED)){
                throw new IllegalStateException("NioServer还未初始化");
            }
        }
        //selector = SelectorProvider.provider().openSelector();
        selector = Selector.open();  // 内部执行的就是上面的代码
        serverChannel = ServerSocketChannel.open();
        //serverChannel.bind("", 3);
        serverChannel.configureBlocking(false);  // 设置为非阻塞模式
        WeakReference<ServerSocket> server = new WeakReference<>(serverChannel.socket());
        server.get().setReuseAddress(true);  // 端口复用
        final SocketAddress socketAddress = this.bindingAddr;
        server.get().bind(socketAddress);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);  // 还有个Attachment
        // 注意这里只有一个ServerSocketChannel可能不太好看出来Selector的作用，如果这里再加一个ServerSocketChannel也注册到该selector上
        state = NioServerStateEnum.RUNNING;
        handleServer();
    }

    protected void handleServer() throws Exception {
        System.out.println("NioServer#handleServer()");
        final NioServerStateEnum state = this.state;
        synchronized (state){
            if(!state.equals(NioServerStateEnum.RUNNING)){
                throw new IllegalStateException("NioServer不是运行状态");
            }
        }
        while(state.equals(NioServerStateEnum.RUNNING)){
            checkOnce();
        }
    }

    // 要和C#的BeginConnect、BeginAccept、BeginSend、BeginReceive连接起来。
    // Send有些特别，它不需要应用层不断的去轮询，只需要下发了数据TCP会自动将数据分批发给客户端，然后客户端的应答消息TCP也能直接处理
    //，但是Receive不一样它获取到消息是需要应用层处理的（Connect、Accept同理）。
    private void checkOnce() throws Exception {
        System.out.println("NioServer#checkOnce()");
        final Selector selector = this.selector;
        // 注册在selector上的Channel有“反应”，消费这些“反应”
        int count = selector.select(0L);  // 一直等待（有点像accept会一直阻塞，只不过select是对所有Channel和所有类型进行检查）
        if(count == 0){
            throw new Exception("检查下是不是没有移除已经处理的SelectionKey");
        }
        // 获取那些准备好了的SelectionKey
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        for(Iterator<SelectionKey> keyIterator = readyKeys.iterator();keyIterator.hasNext();){
            SelectionKey key = keyIterator.next();
            if(key.isAcceptable()){
                // 注意这里是由Channel来accept而非Socket
                SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
                cacheClient(client);
            }else if(key.isValid() && key.isReadable()){
                // channel里可以有多个SelectionKey，但是channel和单个Selector只产生一个key？（channel可以对应多个selector故可能多个key）
                // 这里所有的channel的所有类型都是注册在同一个selector上故每个channel最多只有一个key
                // 这里可以这样设计，多个selector，一个selector专门负责OP_ACCEPT，一个专门负责OP_READ等等，
                // 多个channel的接受都注册到处理接受selector、多个channel的读都注册到处理读selector；每个selector都开启单个线程去轮询
                // attachment是基于SelectionKey的，由于这里channel所有的网络操作都注册在同一个selector上，故attachment是channel共享的
                String clientAddr = (String)key.attachment();
                //key.interestOps(3)
                //key.cancel();
                // 由于是intern()，故clientAddr和cachedClient里的key是完全相等的。
                System.out.println("是否包括此client：" + this.cachedClient.containsKey(clientAddr));
                SocketChannel client = (SocketChannel) key.channel();
                SocketChannel client2 = this.cachedClient.get(clientAddr);
                System.out.println("是否完全相等：" + Objects.equals(client, client2));
                processRead(client);
            }else if(key.isValid() && key.isWritable()){
                // TODO 何时会用到？？
            }else{
                //AtomicBoolean
                //AtomicInteger a = new AtomicInteger(3);
                //int am = a.addAndGet(4);
                System.err.println("isConnectable()???");  // 纯服务端不会有此项
            }
            // 移除的是最后一次返回的元素，即上面的key；这里必须移除否则后面会出现重复消费的问题
            keyIterator.remove();
        }
    }

    private void cacheClient(final SocketChannel client) throws Exception{
        System.out.println("NioServer#cacheClient()");
        System.out.println(String.format("服务端接受来自:%s的请求", client.getRemoteAddress().toString()));
        final Map<String, SocketChannel> clients = this.cachedClient;
        client.configureBlocking(false);  // 也配置为非阻塞
        String clientAddr = client.getRemoteAddress().toString().intern();
        //SelectionKey key =
        //client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, clientAddr);  // 附件也可以放在构造方法里
        // 似乎一个Channel只会产生一个SelectionKey，只不过它的Type是根据比特位是否为1来判断具有哪些type的（如有新的type则对应比特位变为1）
        client.register(this.selector, SelectionKey.OP_READ, clientAddr);  // 这个附件实际上是存在了此次register产生的SelectionKey里
        //NioMessage msg;
        //key.attach(msg = new NioMessage());
        //msg.setFrom(client.getLocalAddress().toString());
        //msg.setTo(client.getRemoteAddress().toString());
        clients.put(clientAddr, client);
        // Logback输出XXX接受来自XX的请求
    }

    // TODO 或者Channel或Socket内部有两个connected，一个是己方，一个是对方的？？

    // 经过测试SelectionKey里面至少有Channel、SelectionKey.[Type]和Selector这三个属性
    // 这个方法其实就该放入线程池里执行
    // TODO 对方正常close后会最后进入这里一次，之后便没有消息了（因为自己close了），但是不会引起报错（shutdownOutput也是一个效果）。
    // 而如果对方强制关闭则不做其他操作的情况下这里会一直进入且一直报错？？？实现机制是什么？？（心跳？？）（据说是有错误的pending）（用拔网线试试也是一样吗？如果不是一样则说明强制关闭可能会给对方发一些数据）。
    private void processRead(final SocketChannel client){  // 换成key，因为出错后还应该再调用key.cancel()
        System.out.println("NioServer#processRead()");
        System.out.println(client.isConnected() + "是否还在连接");
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int len;
        try{
            len = client.read(buffer);
            // 读取到了EOF，
            if(len < 0){  // 比如对方关闭了输出流
                System.out.println("对方正常close()或shutdownOutput？？？");
                client.shutdownOutput();
                client.close();
            }
            buffer.flip();  // 使读取的数据“生效”
            // TODO 这里应该再判断一下数据的有效性，防止读取了EOF还继续处理。
        }catch (Exception ex){
            // 对方强制关闭，如果检测到后自己不关闭则会一直进入这里（因为selector发现channel还是处于“连接、可读”状态故会循环去检查是否有数据。
            ex.printStackTrace();
            try {
                //stop();
                client.close();
                return;
            }catch (Throwable e){
                e.printStackTrace();
                System.exit(-1);
            }
        }
        threadPool.execute(EchoMsgCmd.createCmd(buffer, this.charset));
    }

    // 接口换成Servable、NetServable
    @Override
    public void stop() throws Throwable {
        this.serverChannel.close();
        this.selector.close();
        threadPool.shutdown();
        this.state = NioServerStateEnum.STOP;
    }

    private static class EchoMsgCmd implements Runnable{
        private ByteBuffer msg;
        private Charset charset;

        private EchoMsgCmd(ByteBuffer msg, Charset charset){
            this.msg = msg;
            this.charset = charset;
        }

        public static EchoMsgCmd createCmd(final ByteBuffer msg, final Charset charset){
            return new EchoMsgCmd(msg, charset);
        }

        @Override
        public void run(){
            System.out.println("EchoMsgCmd#run()");
            String msg = this.charset.decode(this.msg).toString();
            System.out.println(msg);
        }
    }

    private enum NioServerStateEnum{
        GENERATED,
        INITED,
        RUNNING,
        STOP
    }
}
