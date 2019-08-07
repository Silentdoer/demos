import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 11:46 AM
 */
public final class MavenFreemarkerHelper extends AbstractFreemarkerHelper {

	private ClassLoader classLoader;

	public MavenFreemarkerHelper(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public MavenFreemarkerHelper() {
		this(MavenFreemarkerHelper.class.getClassLoader());
	}

	public MavenFreemarkerHelper(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	protected void init0(String subFolderPath) throws IOException {
		this.configuration = new Configuration(Configuration.VERSION_2_3_28);
		this.configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
		this.configuration.setEncoding(Locale.CHINESE, StandardCharsets.UTF_8.name());

		if (StringUtils.isNotBlank(this.templateLocation)) {
			File templatesDir = new File(this.templateLocation);
			this.configuration.setDirectoryForTemplateLoading(templatesDir);
		} else {
			String templatePath = Objects.requireNonNull(this.classLoader.getResource(subFolderPath)
					, String.format("请确保模板文件放在resources/%s目录下", subFolderPath)).getPath();
			templatePath = URLDecoder.decode(templatePath, StandardCharsets.UTF_8.name());
			File templatesDir = new File(templatePath);
			this.configuration.setDirectoryForTemplateLoading(templatesDir);
		}
	}

	@Override
	protected void init0() throws IOException {
		this.init0("templates");
	}

	public Template getTemplateFromStore(String name) throws IOException {
		checkInit();
		return this.configuration.getTemplate(name);
	}
}
