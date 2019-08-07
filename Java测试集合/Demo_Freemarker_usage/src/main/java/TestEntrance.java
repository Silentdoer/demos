import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/14/2018 12:03 PM
 */
public class TestEntrance {

	/**
	 * TODO 重要，用Freemarker的时候只需要把它看成是字符串模板渲染器，而不要狭隘的理解为是Html的，所以对于<#list>不会生成<ul>很正常</ul></#list>
	 *
	 * @param args
	 */
	public static void main(String[] args) throws IOException, TemplateException {

		MavenFreemarkerHelper freemarkerHelper = new MavenFreemarkerHelper();
		freemarkerHelper.init();
		final String temp = "思考疯了${foo}";
		Map<String, Object> model = new HashMap<>(1);
		model.put("foo", "sjkl换号");
		System.out.println(freemarkerHelper.viewRender(temp, model));
		System.out.println();

		model.clear();
		final String temp2 = "<ul>\n<#list foos as foo><li>${foo}#</li>\n</#list></ul>\n${author}\n${uu!\"默认值\"}";
		model.put("foos", Arrays.asList("后", "shi", "外网"));
		model.put("author", "刷卡积分了");
		System.out.println(freemarkerHelper.viewRender(temp2, model));
		System.out.println();

		System.out.println(StringUtils.join("<", StringUtils.repeat("-", 10), ">"));
		Template template = freemarkerHelper.getTemplateFromStore("test.ftl");
		template.process(model, new OutputStreamWriter(System.out));

		model.clear();
		model.put("studs", Arrays.asList(new Student("AA", 11.0), new Student("BB", 18.0), new Student("CC", 14.5)));
		// TODO Freemarker里不支持 > <=之类的符号，但是支持 ==（主要是因为>和<符号是特殊符号而==没有涉及这些符号）
		final String temp3 = "<#list studs as stud>" +
				"<#if stud.age lt 12>" +
				"${stud.name}是小学生" +
				"<#elseif stud.age gte 18>" +
				"${stud.name}是成人" +
				"<#break>" +  // 注意这里加了break所以没有输出其他类别
				"<#else>" +
				"${stud.name}是其他类别" +
				"</#if>\n" +
				"</#list>";
		System.out.println();
		System.out.println(freemarkerHelper.viewRender(temp3, model));

		final String temp4 = "<#list studs as stud>" +
				"<#switch stud.name>" +
				"<#case \"AA\">" +
				"AA的年龄是${stud.age}" +
				"<#break>" +
				"<#case \"BB\">" +
				"BB的年龄是${stud.age}" +
				"<#default>" +
				"其他人的年龄是${stud.age}" +
				"</#switch>\n" +
				"</#list>";
		System.out.println();
		System.out.println(freemarkerHelper.viewRender(temp4, model));

		// 原来还可以这么写
		final String temp5 = "<#list 1..10 as t>" +
				"循环的第${t}次\n" +
				"</#list>";
		System.out.println();
		System.out.println(freemarkerHelper.viewRender(temp5, null));
	}

	public static class Student {

		private String name;

		private Double age;

		public Student(String name, Double age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getAge() {
			return age;
		}

		public void setAge(Double age) {
			this.age = age;
		}
	}
}
