package me.silentdoer.demovuespringboot.application.controller;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import me.silentdoer.demovuespringboot.application.config.SystemConfig;
import me.silentdoer.demovuespringboot.application.model.IndexRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-9-26 上午9:26
 */
@RestController
public class IndexController {

	@GetMapping("/index")
	public ModelAndView index() {
		System.out.println("TTTTTTTTTTTTTT");
		ModelAndView result = new ModelAndView();
		result.setViewName("index.html");
		return result;
	}

	@GetMapping("/sendWebSocketMsg")
	public String sendMsg() {
		System.out.println("减肥开始了疯狂了");
		ChannelHandlerContext context = (ChannelHandlerContext) SystemConfig.channelHandlers.values().toArray()[0];
		String msg = "实际付款了";
		context.channel().writeAndFlush(new TextWebSocketFrame(msg));
		return msg;
	}

	// void 改成 ApiResult
	@PostMapping("/api/record")
	public void test(@RequestBody IndexRecord payload) {
		System.out.println("HHHHHHHHHHHHHHH" + payload.getName());
		if (!payload.getContent().getDone()) {
			dbRecords.remove(0);
		} else {
			IndexRecord record1 = new IndexRecord();
			record1.setName(payload.getName());
			record1.setDate(new Date());
			IndexRecord.Content content = new IndexRecord.Content();
			content.setDone(payload.getContent().getDone());
			content.setTodo(payload.getContent().getTodo());
			content.setProblem(payload.getContent().getProblem());
			record1.setContent(content);
			dbRecords.add(record1);
		}
	}

	private static List<IndexRecord> dbRecords;

	static {
		dbRecords = new LinkedList<>();
		long tmp = System.currentTimeMillis();
		IndexRecord record1 = new IndexRecord();
		record1.setName("aaa");
		record1.setDate(new Date(tmp));
		IndexRecord.Content content = new IndexRecord.Content();
		content.setDone(true);
		content.setTodo("AAA");
		content.setProblem("aaa is AAA");
		record1.setContent(content);

		IndexRecord record2 = new IndexRecord();
		record2.setName("bbb");
		record2.setDate(new Date(tmp + 3000));
		IndexRecord.Content content2 = new IndexRecord.Content();
		content2.setDone(false);
		content2.setTodo("BBB");
		content2.setProblem("bbb is BBB");
		record2.setContent(content2);
		dbRecords.addAll(Arrays.asList(record1, record2));
	}

	@GetMapping("/api/dates")
	public List<IndexRecord> dates() {
		System.out.println("EEEEEEEEEEEEE");

		return dbRecords;
	}
}
