import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/20/2018 3:34 PM
 */
public class Entrance {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读取图片
        Mat src = Imgcodecs.imread("E:\\Desktop\\Images\\mao.jpg");
        Mat dest = gradient(src);
        Imgcodecs.imwrite("E:\\Desktop\\Images\\mao_tidu.jpg", dest);
    }

    static Mat blur(Mat src) {
        // 高斯模糊
        Mat dst = new Mat();
        Imgproc.GaussianBlur(src, dst, new Size(15, 15), 0);
        return dst;
    }

    /**
     * 梯度
     * @param image
     * @return
     */
    static public Mat gradient(Mat image) {
        // 梯度
        Mat grad_x = new Mat();
        Mat grad_y = new Mat();
        Mat abs_grad_x = new Mat();
        Mat abs_grad_y = new Mat();

        Imgproc.Sobel(image, grad_x, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(image, grad_y, CvType.CV_32F, 0, 1);
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);
        grad_x.release();
        grad_y.release();
        Mat gradxy = new Mat();
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 10, gradxy);
        return gradxy;
    }
}
