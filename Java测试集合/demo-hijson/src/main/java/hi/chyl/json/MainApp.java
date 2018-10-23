//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hi.chyl.json;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.netbeans.swing.tabcontrol.TabData;

import javax.swing.*;

public class MainApp extends SingleFrameApplication {
    public MainApp() {
    }

    @Override
    protected void startup() {
        //this.show(new MainView(this));
    }

    @Override
    protected void configureWindow(Window root) {
    }

    public static MainApp getApplication() {
        return null;
    }

    public static void main(String[] args) {
        //launch(MainApp.class, args);
        JFrame frame = new MainView(new MainApp()).getFrame();
        // 窗口居中
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        // frame.pack();
        frame.setVisible(true);
    }

    public static JFrame getJFrame() {
        Image ico = (new ImageIcon(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("resources/json.png")))).getImage();
        JFrame frame = new JFrame("MyJsonViewer");
        frame.setIconImage(ico);


        return frame;
    }
}
