import com.google.common.base.Stopwatch;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 2:29 PM
 */
public class Entrance {

    /**
     * 获取图片的文字（比如可以用于获取验证码的值）
     * @param args
     */
    public static void main(String[] args) {
        String folder = "E:\\Desktop\\Images";
        File image = new File(folder, "timg.jpg");
        String tessdata = "C:\\Users\\liqi.wang\\.m2\\repository\\net\\sourceforge\\tess4j\\tess4j\\4.0.2\\tessdata";
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessdata);
        // eng是英语，chi_sim是简体中文（需要单独下载到tessdata目录如chi_sim.traineddata）
        tesseract.setLanguage("chi_sim");
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            String result = tesseract.doOCR(image);
            stopwatch.stop();
            System.out.println(stopwatch.elapsed().toMillis());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
