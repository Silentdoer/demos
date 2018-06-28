import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 6/28/2018 3:38 PM
 */
public class EntranceJsoup {
    public static void main(String[] args) throws IOException {
        // Jsoup处理功能比htmlunit强大但是对单页应用似乎支持不太好；
        Document document = Jsoup.connect("http://localhost:8010/index").get();
        System.out.println(document.toString());
    }
}
