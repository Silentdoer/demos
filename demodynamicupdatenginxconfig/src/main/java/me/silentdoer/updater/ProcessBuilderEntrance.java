package me.silentdoer.updater;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 2019/8/6 14:09
 */
public class ProcessBuilderEntrance {

	private static final String NGINX_EXEC_PATH = "C:/Apps/nginx-1.17.2/nginx";

	private static final String NGINX_CONFIG_PATH = "C:/Apps/nginx-1.17.2/conf/nginx.conf";

	private static final String TERMINAL_WORKDIR = "C:\\Apps\\nginx-1.17.2";

	public static void main(String[] args) throws IOException {

		System.out.println(FilenameUtils.separatorsToUnix(FilenameUtils.getFullPathNoEndSeparator(NGINX_EXEC_PATH)));
		System.out.println(FilenameUtils.getName(NGINX_EXEC_PATH));

		// TODO 这个不是用来验证nginx是否启动的，而是验证nginx.conf文件是否正确的；
		// TODO 验证nginx是否启动是用ps -ef|grep nginx来实现，如果consoleOutput不是空白符则说明有nginx实例在
		//  ./nginx -t -c "C:\Apps\nginx-1.17.2\conf\nginx.conf"
		List<String> cmdList = new LinkedList<>();
		cmdList.add("cmd");
		cmdList.add("/c");
		cmdList.add(NGINX_EXEC_PATH);
		cmdList.add("-t");
		cmdList.add("-c");
		cmdList.add(NGINX_CONFIG_PATH);
		//cmdList = Arrays.asList("cmd", "/c", "tasklist", "|", "findstr", "nginx");
		//cmdList = Arrays.asList("cmd", "/c", NGINX_EXEC_PATH, "-s", "reload");
		cmdList = Arrays.asList("cmd", "/C", NGINX_EXEC_PATH, "-c", NGINX_CONFIG_PATH);
		ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
		// 设置终端启动时的工作目录
		processBuilder.directory(new File(TERMINAL_WORKDIR));
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();

		//region
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//endregion
		System.out.println(process.exitValue());

		InputStream consoleOutput = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(consoleOutput));
		String tmp;
		StringBuilder stringBuilder = new StringBuilder();
		while ((tmp = reader.readLine()) != null) {
			stringBuilder.append(tmp).append(System.lineSeparator());
		}
		String cmdExecMsg = stringBuilder.toString();

		System.out.println(cmdExecMsg);
	}
}
