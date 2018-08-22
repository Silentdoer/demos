import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 11:46 AM
 */
public final class FreemarkerUtils {

    private static Configuration config;

    static {
        FreemarkerUtils.config = new Configuration(Configuration.VERSION_2_3_23);
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        config.setEncoding(Locale.CHINESE, StandardCharsets.UTF_8.name());
        try {
            config.setSharedVaribles(new HashMap<String, Object>() {
                {
                    this.put("author", "silentdoer");
                }
            });

            File dir = new File(System.getProperty("user.dir"));
            config.setDirectoryForTemplateLoading(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // config.setClassLoaderForTemplateLoading(FreemarkerUtils.class.getClassLoader(), "/ftl");
        // config.setClassForTemplateLoading();
    }

    private FreemarkerUtils() {
    }

    public static void setConfiguration(Configuration config) {
        FreemarkerUtils.config = config;
    }

    public static Template getTemplate(String templateStr) throws IOException {
        return new Template(null, templateStr, null);
    }

    public static Template getTemplate(String templateStr, Configuration config) throws IOException {
        return new Template(null, templateStr, config);
    }

    @SneakyThrows
    public static Template getTemplateFromStore(String name) {
        // 这个是文件的全名（但不是绝对路径名），包括后缀
        return FreemarkerUtils.config.getTemplate(name);
    }

    @SneakyThrows
    public static String viewRender(String templateStr, Map<String, Object> model) {
        Template temp = new Template(null, templateStr, FreemarkerUtils.config);
        StringWriter sw = new StringWriter();
        temp.process(model, sw);
        return sw.toString();
    }
}
