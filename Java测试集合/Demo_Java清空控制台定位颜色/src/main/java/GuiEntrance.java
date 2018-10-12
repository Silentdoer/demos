import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/30/2018 3:20 PM
 */
public class GuiEntrance {

    public static void main(String[] args) {
        JFrame window = new JFrame("窗口标题");
        Container rootPane = window.getContentPane();

        JPanel showPane = new JPanel();
        showPane.setSize(300, 400);
        showPane.setPreferredSize(showPane.getSize());
        showPane.setLayout(new FlowLayout());
        showPane.setVisible(true);

        rootPane.add(showPane, BorderLayout.NORTH);

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
        });

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 显示后不可再调整window大小
        window.setResizable(false);
        window.setVisible(true);
        window.pack();
    }

    public static class MyPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawString("□", 5, 5);
        }
    }
}
