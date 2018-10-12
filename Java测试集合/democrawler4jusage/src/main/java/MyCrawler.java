import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Map;
import java.util.Set;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/26/2018 5:15 PM
 */
public class MyCrawler extends WebCrawler {

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return super.shouldVisit(referringPage, url);
    }

    @Override
    public void visit(Page page) {
        // 获取url
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        // 判断是否是html数据
        if (page.getParseData() instanceof HtmlParseData) {
            // 强制类型转换，获取html数据对象
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            // 获取页面纯文本（无html标签）
            String text = htmlParseData.getText();
            // 获取页面Html
            String html = htmlParseData.getHtml();
            // 获取页面输出链接
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            // TODO crawler4j其实也是获取网页的内容，然后将它转换为多个有层级的element，通过分析这些element来实现爬虫
            Map<String, String> metaTags = htmlParseData.getMetaTags();
            htmlParseData.getMetaTagValue("tagName");
            System.out.println("纯文本长度: " + text.length());
            System.out.println("html长度: " + html.length());
            System.out.println("链接个数 " + links.size());
        }
    }
}
