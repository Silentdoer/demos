import com.google.common.base.Stopwatch;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 3:51 PM
 */
public class CompareTwoImage {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        // set imaget path
        String image1 = "E:\\Desktop\\Images\\mao.jpg";
        String image2 = "E:\\Desktop\\Images\\mao_test5.jpg";

        int ret = compareHistogram(image1, image2);
        if (ret > 0) {
            System.out.println("two image are same");
        } else {
            System.out.println("two image are different");
        }
    }

    static int compareHistogram(String image1, String image2) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int retVal = 0;
        Mat img1 = Imgcodecs.imread(image1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat img2 = Imgcodecs.imread(image2, Imgcodecs.CV_LOAD_IMAGE_COLOR);

        Mat hsvImg1 = new Mat();
        Mat hsvImg2 = new Mat();

        // Convert to HSV
        Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

        // set configuration for calchist()
        List<Mat> listImg1 = new ArrayList<>(8);
        List<Mat> listImg2 = new ArrayList<>(8);

        listImg1.add(hsvImg1);
        listImg2.add(hsvImg2);

        MatOfFloat ranges = new MatOfFloat(0, 255);
        MatOfInt histSize = new MatOfInt(50);
        MatOfInt channels = new MatOfInt(0);

        // histograms
        Mat histImg1 = new Mat();
        Mat histImg2 = new Mat();

        Imgproc.calcHist(listImg1, channels, new Mat(), histImg1, histSize, ranges);
        Imgproc.calcHist(listImg2, channels, new Mat(), histImg2, histSize, ranges);

        Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        double result0, result1, result2, result3;
        result0 = Imgproc.compareHist(histImg1, histImg2, 0);
        result1 = Imgproc.compareHist(histImg1, histImg2, 1);
        result2 = Imgproc.compareHist(histImg1, histImg2, 2);
        result3 = Imgproc.compareHist(histImg1, histImg2, 3);

        // 1.0（1.0似乎是完全一样的意思，非负数越接近1.0越是匹配）
        System.out.println("Method [0] " + result0);
        // 0.0（这个值非负数越小越匹配）
        System.out.println("Method [1] " + result1);
        // 2.5621874514245064（小于2.56非负数越接近2.56越是匹配）
        System.out.println("Method [2] " + result2);
        // 0.0（非负数越小越好）
        System.out.println("Method [3] " + result3);

        int count = 0;
        if (result0 > 0.9) {
            count++;
        }
        if (result1 < 0.1) {
            count++;
        }
        if (result2 > 1.5) {
            count++;
        }
        if (result3 < 0.3) {
            count++;
        }
        if (count >= 3) {
            retVal = 1;
        }
        stopwatch.stop();

        System.out.println("elapsed time : " + stopwatch.elapsed());
        return retVal;
    }
}
