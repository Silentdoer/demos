import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * TODO 总结，java没法弄控制台，不过可以用GUI程序作为控制台程序，然后通过一个MultiLine作为控制台应用程序的输出
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/30/2018 11:34 AM
 */
public class Entrance {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("jskoldfjkl");
        System.out.println("fsdiofjdijo");
        System.err.println("uuuuuuuuuuuuuuu8888:");
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("fusiofji");

        /*JFrame frame = new JFrame("Crystal");
        Container container = frame.getContentPane();
        JTextArea area = new JTextArea(10, 20);
        for (int i = 0; i < 5; i++) {
            area.append("□ □ □ □ □\n");
        }
        JLabel label = new JLabel("构造文本域:");
        label.setBounds(10, 10, 120, 20);
        area.setBounds(130, 10, 150, 100);
        frame.setLayout(null);
        container.add(area);
        container.add(label);
        frame.setSize(300, 150);
        frame.setLocation(300, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);*/

        Test test = new Test();
        test.gui();
    }

    private static void clearConsole() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception exception) {
            //  Handle exception.
        }
    }
}
