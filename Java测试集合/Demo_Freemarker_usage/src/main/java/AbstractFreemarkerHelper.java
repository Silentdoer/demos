import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author liqi.wang
 * @since 2019-08-04
 */
public abstract class AbstractFreemarkerHelper {

	protected volatile boolean initialized = false;

	protected String templateLocation;

	protected Configuration configuration;

	public AbstractFreemarkerHelper() {
	}

	public synchronized void init() throws IOException {

		if (this.initialized) {
			throw new RuntimeException("Freemarker 不能重复初始化");
		}

		init0();

		this.initialized = true;
	}

	public void reset() {
		this.initialized = false;
	}

	protected abstract void init0() throws IOException;

	protected abstract void init0(String subFolderPath) throws IOException;

	protected void checkInit() {
		if (!this.initialized) {
			throw new RuntimeException("Freemarker 尚未初始化");
		}
	}

	public String viewRender(String templateContent, Map<String, Object> model) {
		checkInit();
		Template temp = null;
		StringWriter sw = null;
		try {
			temp = new Template(null, templateContent, this.configuration);
			sw = new StringWriter();
			temp.process(model, sw);
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
		return sw.toString();
	}

	public String viewRender(Template template, Map<String, Object> model) {
		checkInit();
		StringWriter sw = new StringWriter();
		try {
			template.process(model, sw);
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
		return sw.toString();
	}
}
