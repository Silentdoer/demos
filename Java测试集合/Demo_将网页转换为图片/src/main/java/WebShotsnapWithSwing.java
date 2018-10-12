import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 5:26 PM
 */
public class WebShotsnapWithSwing {

    /**
     * 经过测试不好用，貌似只能用前端的库来实现了？？
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     * @throws AWTException
     */
    public static void main(String[] args) throws IOException, URISyntaxException, AWTException {
        // 此方法仅适用于JdK1.6及以上版本
        Desktop.getDesktop().browse(
                new URL("http://www.baidu.com").toURI());
        Robot robot = new Robot();
        robot.delay(10000);
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();
        // 最大化浏览器
        robot.keyRelease(KeyEvent.VK_F11);
        robot.delay(2000);
        Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
                                                              height));
        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        // 保存图片
        ImageIO.write(bi, "jpg", new File("E:\\Desktop\\Images\\testDDD" + System.currentTimeMillis() + ".jpg"));
    }
}