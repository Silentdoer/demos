package me.silentdoer.demovuespringboot.application.config;

import io.netty.channel.ChannelHandlerContext;
import me.silentdoer.demovuespringboot.application.component.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-10-14 上午11:16
 */
@Configuration
public class SystemConfig {

	/**
	 * ChannelHandlerContext类似Socket里的某个和客户端交互的socket对象
	 */
	public static Map<String, ChannelHandlerContext> channelHandlers = new HashMap<>();

	@Bean
	public WebSocketServer webSocketServer() {
		WebSocketServer webSocketServer = new WebSocketServer(9933);
		Thread thd = new Thread(webSocketServer);
		thd.setDaemon(true);
		thd.start();
		return webSocketServer;
	}
}
