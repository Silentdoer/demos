package silentdoer.web.client;

import silentdoer.web.server.NioServer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-1-29 12:05
 */
public class NioClient {
    private Charset charset;
    private Selector selector;
    public SocketChannel client;  // 方便测试
    private SocketAddress remoteAddr;
    private List<FutureTask<Boolean>> tasks;  // 先不用，待研究
    private WriterDaemonThread writeThread;

    private NioClient(){
        charset = Charset.forName("UTF-8");
    }

    public static NioClient createClient(Charset charset, SocketAddress remote){
        NioClient client = new NioClient();
        client.charset = charset;
        client.remoteAddr = remote;
        client.tasks = new LinkedList<>();
        // 线程池的原理有点像将多个Runnable对象存入thread的队列，然后此thread不断的从里面获取Runnable对象执行
        return client;
    }

    // Runnable是一个傻瓜Cmd，它不需要知道owner的状态，如果需要知道owner的状态则应该继承Thread覆盖run()


    // TODO key.interestOps(int)是重新赋值（重新设置感兴趣的操作集）而非添加新的感兴趣的操作
    public void connect() throws IOException {
        System.out.println("NioClient#connect()");
        if(!checkState()){
            throw new IllegalStateException("NioClient未处于就绪状态");
        }
        final Selector selector = this.selector = Selector.open();
        // TODO 这里其实就应该开启一个线程来轮询了，而不是在这个方法最后才轮询（服务端不一样它不需要主动调用send这样的方法，一切都是被动接收的）
        Thread thread = new Thread(SelectorHandler.createHandler(this.charset, selector));
        thread.setDaemon(false);
        thread.start();
        //final SocketChannel client = this.client = SocketChannel.open(this.remoteAddr);
        final SocketChannel client = this.client = SocketChannel.open();
        // 下面两句话要在所有网络操作之前执行，比如connect、accept等
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_CONNECT);
        // 非阻塞连接，即调用此方法立刻返回，但是可能还没有连接成功，连接成功后会被Selector捕获到（已经注册到了Selector上）
        client.connect(this.remoteAddr);
        this.writeThread = new WriterDaemonThread(this.toString(), client);
        this.writeThread.start();
    }

    public void write(ByteBuffer buffer){
        System.out.println("NioClient#write()");
        this.writeThread.pushMsg(buffer);
    }

    public void stop() throws IOException, InterruptedException {
        this.client.close();
        this.selector.close();  // 报错？？？
        this.writeThread.join(1000);
    }

    private static class WriterDaemonThread extends Thread{
        private final LinkedList<ByteBuffer> writeMsgQueue = new LinkedList<>();
        private volatile boolean valid;
        private SocketChannel client;

        public WriterDaemonThread(String owner, SocketChannel client){
            super(owner);
            this.client = client;
            setDaemon(true);
            valid = true;
        }

        public void pushMsg(ByteBuffer buffer){
            System.out.println("WriterDaemonThread#pushMsg()");
            synchronized (this.writeMsgQueue) {
                this.writeMsgQueue.addLast(buffer);
                this.writeMsgQueue.notifyAll();
            }
        }

        @Override
        public void run(){
            System.out.println("WriterDaemonThread#run()");
            final LinkedList<ByteBuffer> queue = this.writeMsgQueue;
            final SocketChannel client = this.client;
            while(valid){
                try {
                    if (queue.isEmpty()) {
                        synchronized (queue) {
                            try {
                                queue.wait(60000L);
                            }catch (InterruptedException ex){}
                        }
                    }else{
                        if(client.isConnected()) {
                            // 可以一次取出全部一次性写
                            ByteBuffer msg = queue.removeFirst();
                            client.write(msg);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 这里其实应该继承Thread，不然不好判断线程是否关闭
     */
    private static class SelectorHandler implements Runnable{
        final private Selector selector;
        //final private Thread owner;
        final private Charset charset;
        private ExecutorService threadPool;

        private SelectorHandler(Charset charset, Selector selector){
            System.out.println("SelectorHandler#SelectorHandler()");
            this.selector = selector;
            this.charset = charset;
            //this.owner = owner;
            ThreadFactory factory = Executors.defaultThreadFactory();
            this.threadPool = Executors.newCachedThreadPool(factory);
        }

        public static SelectorHandler createHandler(Charset charset, Selector selector){
            SelectorHandler handler = new SelectorHandler(charset, selector);
            return handler;
        }

        @Override
        public void run() {
            System.out.println("SelectorHandler#run()");
            // 这里还是要用owner的形式，判断线程是否interrupted，否则这个地方还在运行但是selector已经关闭
            while(null != this){
                checkOnce();
            }
        }

        protected void checkOnce(){
            System.out.println("SelectorHandler#checkOnce()");
            try {
                int count = this.selector.select(0L);
                if(count == 0){
                    throw new Exception("Selector#select()返回0");
                }
                Set<SelectionKey> readyKeys = this.selector.selectedKeys();
                for(Iterator<SelectionKey> iterator = readyKeys.iterator();iterator.hasNext();){
                    SelectionKey key = iterator.next();
                    if(key.isConnectable()){
                        System.out.println("SelectorHandler#key.isConnectable()");
                        SocketChannel client = ((SocketChannel) key.channel());
                        if(client.isConnectionPending()){
                            client.finishConnect();
                        }
                        client.configureBlocking(false);  // 之前已经执行过了，不过根据OOP设计原则，A不应该知道B做了什么故再做一次
                        client.register(this.selector, SelectionKey.OP_READ);
                    }else if(key.isValid() && key.isReadable()){
                        processRead(((SocketChannel) key.channel()));
                    }else if(key.isValid() && key.isWritable()){
                        // TODO哪怕没写数据它也会一直进来，故isWritable()是判断此key是否可写数据的意思？而不是调用了channel.write后会进入这里？？
                        System.err.println("Writable会进来吗？？");
                    }else{
                        System.err.println("还未实现");
                    }
                    iterator.remove();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        private void processRead(final SocketChannel client){
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            int len;
            try{
                len = client.read(buffer);
                if(len < 0){  // 比如对方关闭了输出流
                    client.shutdownOutput();
                    client.close();
                }
                buffer.flip();  // 使读取的数据“生效”
            }catch (Exception ex){
                ex.printStackTrace();
            }
            threadPool.execute(EchoMsgCmd.createCmd(buffer, this.charset));
        }
    }

    private boolean checkState(){
        return this.charset != null && this.remoteAddr != null;
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
            String msg = this.charset.decode(this.msg).toString();
            System.out.println(msg);
        }
    }
}
