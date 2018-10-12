import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 注意这个jar包要用google的0.9版本的（2.0.1版本的有问题）
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 4:59 PM
 */
public class Entrance {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        /*HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadUrl(new URL("http://www.renren.com/"));
        generator.getBufferedImage();
        generator.saveAsImage("E:\\Desktop\\Images\\lalala.jpg");
        generator.saveAsHtmlWithMap("lalala.html", "lalala.jpg");*/

        /*BufferedImage bufferedImage = Html2Image.fromURI(new URI("http://www.baidu.com/")).getImageRenderer().getBufferedImage();
        javax.imageio.ImageIO.write(bufferedImage, gui.ava.html.renderer.FormatNameUtil.getDefaultFormat(), new File("E:/Desktop/Images/lalalas.jpg"));*/

        // 可以是可以，但是会有颜色问题
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>" + " <html xmlns='http://www.w3.org/1999/xhtml'>" + "<style>.p1 {font-size:36px;font-weight:200;color:#0000FF;}</style>" + "<style>.p2 { font-size:24px;font-weight:300;color: #FF0000;}</style>" + "<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title>CSS外联样式表</title></head>" + "<body><p class='p1'>CSS外联样式表<p class='p2'>CSS外联样式表<p class='p3'>CSS外联样式表<p class='p4'>CSS外联样式表 </body></html>";
        //imageGenerator.loadHtml(html);
        imageGenerator.loadUrl("http://zhihu.com/");
        // 等待加载（比如图片之类的）
        Thread.sleep(3000L);
        //imageGenerator.loadUrl("http://www.icbc.com.cn/ICBC/%E7%BD%91%E4%B8%8A%E5%9F%BA%E9%87%91/%E5%9F%BA%E9%87%91%E5%B9%B3%E5%8F%B0/%E5%9F%BA%E9%87%91%E4%BA%A7%E5%93%81%E5%88%97%E8%A1%A8.htm");
        imageGenerator.getBufferedImage();
        imageGenerator.saveAsImage("E:/Desktop/Images/lalala.png");
        //imageGenerator.saveAsImage("d:/test/hello-world2.jpg");
        // imageGenerator.saveAsHtml("d:/test/hello.html", html);

        System.out.println("finished.");
    }
}
