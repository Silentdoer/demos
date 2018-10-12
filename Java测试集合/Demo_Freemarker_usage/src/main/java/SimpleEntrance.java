import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 10:26 AM
 */
public class SimpleEntrance {

    public static void main(String[] args) throws IOException, TemplateException {
        final String templateStr = "html文本其实也是普通文本？${result}";
        Map<String, Object> model = new HashMap<>(1);
        model.put("result", "ok搜索");
        /*
        config是可以配置输出目录、输入目录、编码等内容，
        然后用config.getTemplate("templateName");就可以自动从输入目录导入template
         如果不需要额外配置configuration可以为null;
          */
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        // 设置生成text的编码
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 如果Template是手动new的，那么name可直接省略；，name是全名
        Template template = new Template(null, templateStr, config);

        StringWriter sw = new StringWriter();
        template.process(model, sw);
        System.out.println(sw.toString());
    }
}
