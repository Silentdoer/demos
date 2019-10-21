package me.silentdoer.demovuespringboot.application.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @author wlq
 * @version 1.0.0
 * @since 19-9-26 下午2:58
 */
@Data
public class IndexRecord {

	private String id = UUID.randomUUID().toString();

	private String name;

	private Date date;

	private Content content;

	@Data
	public static class Content {

		private Boolean done;

		private String todo;

		private String problem;
	}
}
