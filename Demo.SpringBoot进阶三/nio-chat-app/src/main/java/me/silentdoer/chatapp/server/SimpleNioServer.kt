package me.silentdoer.chatapp.server

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.HashMap
import kotlin.concurrent.thread

/**
 * 默认构造函数一般可以直接写在类名后面，其他的构造方法可以用constructor方法来定义（但是类名后面的也可以是有参数的）
 * ，class默认是private 的？（是不是private不知道，但是是final class）
 *
 * 次构造函数不能有声明 val 或 var
 *
 * @author liqi.wang
 * @version 1.0.0
 */
class SimpleNioServer(name: String, ip: String, port: Int) : Runnable {
    constructor(name: String) : this(name, "127.0.0.1", 9999)
    constructor(name: String, ip: String) : this(name, ip, 9999)
    constructor(name: String, port: Int) : this(name, "127.0.0.1", port)

    /**
     * 和java不一样的地方是kotlin不存在默认域初始化，如果要默认初始化为null需要自己主动指定
     */
    private var address: InetSocketAddress = InetSocketAddress(ip, port)

    /**
     * 这个name其实是来自主构造方法，注意这个name不能是val，哪怕没有setter，这是为何？？（貌似就是这样，java的final字段也是不允许有getter和setter的）
     * 注意：getter和setter都是紧接在field后面，因此直接用field代替this.name
     *
     * kotlin里，字段默认就是public的，（kotlin也有public关键字）
     */
    private lateinit var server: ServerSocketChannel
    /**
     * 必须加上lateinit，否则提示Property must be initialized or be abstract
     *
     * lateinit只能用于var（且不能是Int这样的基础类型及不能用于可空类型）
     *
     * by lazy{}只能用于val，如：val name: String by lazy { "sherlbon" }
     */
    private val selector: Selector by lazy { Selector.open() }

    private val clientsCount: AtomicInteger = AtomicInteger(0)

    private val writeMsgQueue: LinkedBlockingQueue<String> by lazy {
        LinkedBlockingQueue<String>()
    }

    /**
     * 將數據寫給客戶端的線程，這裡直接廣播給所有的客戶端
     */
    private val writeMsgThd: Thread =
            thread(start = true, isDaemon = true) {
                while (true) {
                    val readLine = readLine()
                    // 這步可以註釋掉
                    //this@SimpleNioServer.writeMsgQueue.offer(readLine)
                    // TODO 這裡其實需要進行線程同步
                    for ((k, v) in this@SimpleNioServer.clients) {
                        System.err.println("已经向客户端${k}写上面输入的数据；")
                        v.write(Charsets.UTF_8.encode(readLine))
                        //v.register(this@SimpleNioServer.selector, SelectionKey.OP_WRITE)
                    }
                }
            }

    /**
     * TODO 在kotlin里Map和MutableMap是不同的概念，有点像CheckedException和UncheckedException
     * TODO 同样的还有List和MutableList，用的时候要分清楚是kotlin的还是java的，统一用kotlin的，kotlin没有再用java的
     */
    private val clients: MutableMap<String, SocketChannel> by lazy {
        HashMap<String, SocketChannel>(16)
    }

    /**
     * 这个跟java很不一样，java里只要是类就能承载null值，而对于kotlin所有的类型都必须后面加?才能承载，且kotlin实际上没有基础类型
     * Unit类似void，可以省略，就像类名后面的constructor一样，这里init虽然是关键字，但是仍然可以用作方法名，就像sql里可以将关键字作为字段名，而且支持``
     */
    fun `init`() {
        this@SimpleNioServer.server = ServerSocketChannel.open()
        // 如果server是null会抛空指针异常
        this@SimpleNioServer.server.bind(this.address)
        // 懒得写!!了，用?代替，注意它们作用是不一样的
        this@SimpleNioServer.server.configureBlocking(false)

        // TODO xx.register可以理解为xx.addListener，下面的代码就可以理解为给server的OP_ACCEPT事件添加了一个selector的观察者
        // TODO 然后server里产生了相关的事件后就会通知给selector，这里的通知其实就是产生一个SelectionKey对象给selector
        // TODO 然后可以用selector.select()等方法就可以获取到对应的数据；
        this.server.register(this.selector, SelectionKey.OP_ACCEPT)
        /*this.writeMsgThd.isDaemon = true
        this.writeMsgThd.start()*/
    }

    /**
     * 这个跟C#一样，
     */
    override fun run() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 这里的loopWhile@就类似java的label，到时候break时可以break@loopWhile
        while1@ while (true) {
            val availableCount = this.selector.selectNow()
            if (availableCount <= 0) {
                continue@while1
            }
            //break@loopWhile
            val readySelectionKeys = this.selector.selectedKeys()
            // : SelectionKey可以省略，这种情况就类似C#的for (var key in keys)
            // kotlin怎么在迭代过程中移除元素，要留意一下
            val iterator = readySelectionKeys.iterator()
            while2@ while (iterator.hasNext()) {
                val next = iterator.next()
                // 立刻remove掉
                iterator.remove()
                if (next.isValid.not()) {
                    continue@while2
                }
                when {
                    next.isAcceptable -> {
                        val channel = next.channel() as ServerSocketChannel
                        val clientChannel = channel.accept()
                        this.processAcceptable(clientChannel)
                    }
                    next.isReadable -> {
                        val channel = next.channel() as SocketChannel
                        this.processReadable(channel)
                    }
                    else -> {
                        System.err.println("暫不支持其他")
                    }
                }

            }
        }
    }

    private fun processAcceptable(client: SocketChannel) {
        client.configureBlocking(false)
        // 对于List是只能get或[..]来读取而不能修改、新增、删除
        this@SimpleNioServer.clients[client.remoteAddress.toString()] = client
        System.err.println("接受客户端：${client.remoteAddress}的连接请求，当前连接的客户端数量为：${this.clientsCount.addAndGet(1)}")
        client.register(this.selector, SelectionKey.OP_READ)

    }

    private fun processReadable(client: SocketChannel) {
        val buffer = ByteBuffer.allocate(2048)
        val readCount: Int
        try {
            readCount = client.read(buffer)
        } catch (ex: Exception) {
            System.err.println(ex.localizedMessage)
            this.clients.remove(client.remoteAddress.toString())
            this.clientsCount.decrementAndGet()
            client.shutdownOutput()
            client.close()
            return
        }
        if (readCount > 0) {
            buffer.flip()
            val bytes = buffer.array()
            val msg = String(bytes, Charsets.UTF_8)
            System.err.println("收到来自客户端${client.remoteAddress}的消息：$msg")

        } else {
            System.err.println("客户端${client.remoteAddress}主動断开连接，这里也会产生一个isReadable，然后读取的count为（一般是-1）：$readCount")
            this.clients.remove(client.remoteAddress.toString())
            this.clientsCount.decrementAndGet()
            client.shutdownOutput()
            client.close()
        }
    }
}

fun main(args: Array<String>) {
    val server = SimpleNioServer("测试", "127.0.0.1", 9999)
    server.init()
    System.err.println("服务已启动；")
    server.run()
}