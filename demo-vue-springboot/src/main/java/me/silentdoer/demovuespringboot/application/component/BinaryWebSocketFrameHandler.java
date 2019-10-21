package me.silentdoer.demovuespringboot.application.component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-10-15 上午8:59
 */
@Slf4j
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
		log.info("服务器接收到二进制消息:");
		ByteBuf content = msg.content();
		byte[] bytes = new byte[content.capacity()];
		content.readBytes(bytes);
		System.out.println(bytes[0]);
		System.out.println(bytes[1]);
		System.out.println(bytes[2]);
		//System.out.println(new String(bytes, StandardCharsets.UTF_8));

		ByteBuf resp = Unpooled.buffer(256);
		resp.writeBytes("回复你了".getBytes(StandardCharsets.UTF_8));

		// 必须是发送WebSocketFrame的对象，否则客户端收不到
		ctx.writeAndFlush(new BinaryWebSocketFrame(resp));
	}
}
