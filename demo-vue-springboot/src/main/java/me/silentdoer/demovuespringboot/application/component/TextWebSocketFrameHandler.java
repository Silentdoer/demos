package me.silentdoer.demovuespringboot.application.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static me.silentdoer.demovuespringboot.application.config.SystemConfig.channelHandlers;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-10-14 下午3:20
 * <p>
 * 还有一个BinaryWebSocketFrame
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		log.info("{}, {}", ctx.channel().remoteAddress().toString()
				, ctx.channel().localAddress().toString());
		System.out.println("收到消息： " + msg.text());

		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间： " + LocalDateTime.now()));
	}

	/**
	 * new WebSocket后会产生这个方法调用
	 * handlerAdded: b46d83fffeeecfd7-000016d7-00000001-fb8b92bf2ddc7ffb-27e44da6
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		if (!channelHandlers.containsValue(ctx)) {
			// TODO handlerRemoved(..)记得将这个ctx移除掉
			channelHandlers.put("WebSocket-" + ctx.name(), ctx);
		}

		// ctx和channel应该是一一对应的关系
		System.out.println(channelHandlers.size() + " handlerAdded: " + ctx.channel().id().asLongText());
	}

	/**
	 * 长时间和客户端没有数据往来会执行这个方法（服务端断开该连接）
	 * 客户端手动断开连接也会执行这个
	 *
	 * @param ctx
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		System.out.println("handlerRemoved: " + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("WebSocket服务器发生异常");
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	/**
	 * 还有个Inactive，应该是通道长时间没有数据交互则置为不活跃调用该方法
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		// 不是一直执行，只是刚连接时会调用这个方法。。，完全可以在handlerAdded里做。。
		// ctx.name()是TextWebSocketFrameHandler#0
		System.out.println("是一直执行这个方法吗？" + ctx.name());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//log.info("通道不活跃，将自动关闭");
		//ctx.close();
		super.channelInactive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			log.info("web socket 握手成功。");
			WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
			String requestUri = handshakeComplete.requestUri();
			log.info("requestUri:[{}]", requestUri);
			String subProtocol = handshakeComplete.selectedSubprotocol();
			log.info("subProtocol:[{}]", subProtocol);
			handshakeComplete.requestHeaders().forEach(entry -> log.info("header key:[{}] value:[{}]", entry.getKey(), entry.getValue()));
		}
		super.userEventTriggered(ctx, evt);
	}
}