package me.silentdoer.demovuespringboot.application.component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-10-14 上午11:21
 */
@Slf4j
public class WebSocketServer implements Runnable {

	private final int port;

	public WebSocketServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					// 2小时无数据激活心跳机制
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new WebSocketChannelInitializer());

			ChannelFuture sync = bootstrap.bind(this.port).sync();
			System.out.println("Web socket server started at port " + this.port + '.');

			sync.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new WebSocketServer(9933).run();
	}
}
