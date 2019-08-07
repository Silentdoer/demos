package me.silentdoer.logbackusage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liqi.wang
 * @since 2019/8/5 8:35
 */
public class SomeUtil {

	private static final Logger LOG = LoggerFactory.getLogger(SomeUtil.class);

	public static void test() {
		LOG.info("helels是: {}", "季度付款");
		System.out.println("wink");
	}
}
