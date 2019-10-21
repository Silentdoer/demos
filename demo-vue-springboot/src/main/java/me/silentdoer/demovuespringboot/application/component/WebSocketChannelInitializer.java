package me.silentdoer.demovuespringboot.application.component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-10-14 下午3:18
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		// 注意，下面的是有添加顺序的貌似
		//pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
		// HttpRequestDecoder和HttpResponseEncoder的一个组合，针对http协议进行编解码
		pipeline.addLast(new HttpServerCodec());
		// 分块向客户端写数据，防止发送大文件时导致内存溢出，channel.write(new ChunkedFile(new File("bigFile.mkv")))
		pipeline.addLast(new ChunkedWriteHandler());
		// 将HttpMessage和HttpContents聚合到一个完成的FullHttpRequest或FullHttpResponse中
		// ，具体是FullHttpRequest对象还是FullHttpResponse对象取决于是请求还是响应
		// 这里只处理一次http请求，所以这个length这么大就够了
		pipeline.addLast(new HttpObjectAggregator(8192));
		// webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler的第三个参数需要设置成true
		pipeline.addLast(new WebSocketServerCompressionHandler());
		// 服务器端向外暴露的 web socket 端点，当客户端传递比较大的对象时，maxFrameSize参数的值需要调大
		pipeline.addLast(new WebSocketServerProtocolHandler("/websocket", null, true, 10240));

		// 自定义Text数据处理
		pipeline.addLast(new TextWebSocketFrameHandler());
		// 自定义处理器 - 处理 web socket 二进制消息
		pipeline.addLast(new BinaryWebSocketFrameHandler());
	}
}