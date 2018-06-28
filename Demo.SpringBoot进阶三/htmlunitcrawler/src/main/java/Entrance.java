import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.concurrent.TimeUnit;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 6/28/2018 2:37 PM
 */
public class Entrance {

    public static void main(String[] args) throws Exception {
        String webUrl = "file:///E:/Documents/NodeJSProjs/element-starter/dist/index.html";
        HtmlPage page = getHtmlPage(webUrl);
        System.out.println(page.asXml());
//        final HtmlTable div = (HtmlTable) page.getElementById("mytable");
        DomNodeList<DomElement> nodes = page.getElementsByTagName("button");
        // TODO 輸出了Start，說明確實可以獲得是通過JS渲染出來的Dom元素
        // TODO 如果某個元素如<div>...</div>只是普通的文本（即沒有作為Dom），那麼page.getElementsByTagName("div")是不包括它的
        // TODO 接上，但是如果它不是Dom元素那麼其實獲取不到也沒有關係因為它不會作為網頁佈局的一部份出現在頁面上而只是普通的文本而已；
        // TODO 接上，如果也要爬出来，那么可以将page整个转换为字符串再来爬出，即page.asXml()来完成；
        System.out.println(nodes.get(0).getTextContent());
//        HtmlTableBody tbody = (HtmlTableBody) div.getBodies().get(0);
//        printTable(tbody);
        System.out.println(nodes.size());
        System.err.println("查询数据成功");
    }

    private static HtmlPage getHtmlPage(String url) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setMaxInMemory(1024 * 64);
        final HtmlPage page = webClient.getPage(url);
        System.err.println("查询中，请稍候..");

        // web请求数据需要时间，必须让主线程休眠片刻
        TimeUnit.SECONDS.sleep(3);
        webClient.close();
        return page;
    }

    private static void printTable(HtmlTableBody tbody) throws Exception {
        DomNodeList<HtmlElement> trs = tbody.getElementsByTagName("tr");
        for (int i = 0; i < trs.size(); i++) {
            HtmlElement node = trs.get(i);
            DomNodeList<HtmlElement> tds = node.getElementsByTagName("td");
            for (int j = 0; j < tds.size(); j++) {
                HtmlElement td = tds.get(j);
                System.err.print(td.asText() + "\t");
            }
            System.err.println();
        }
    }
}