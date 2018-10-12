import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/30/2018 2:53 PM
 */
public class Test {

    JFrame frame = null;

    JTextPane textPane = null;

    File file = null;

    Icon image = null;

    public Test() {
        frame = new JFrame("JTextPane");
        textPane = new JTextPane();
        //file = new File("./classes/test/icon.gif");
        //image = new ImageIcon(file.getAbsoluteFile().toString());
    }

    public void insert(String str, AttributeSet attrSet) {
        Document doc = textPane.getDocument();
        str = "\n" + str;
        try {
            doc.insertString(doc.getLength(), str, attrSet);
        } catch (BadLocationException e) {
            System.out.println("BadLocationException: " + e);
        }
    }

    public void remove(int line, int columnCharIdx) {
        Document doc = textPane.getDocument();
    }

    public void setDocs(String str, Color col, boolean bold, int fontSize) {
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attrSet, col);
        // 颜色
        if (bold == true) {
            StyleConstants.setBold(attrSet, true);
        }// 字体类型
        StyleConstants.setFontSize(attrSet, fontSize);
        // 字体大小
        // StyleConstants.setFontFamily(attrSet, "黑体");
        // 设置字体
        insert(str, attrSet);
    }

    public void gui() {
        //textPane.insertIcon(image);
        setDocs("□ □ □ □ □", Color.RED, false, 16);
        setDocs("□ □ □ □ □", Color.BLACK, false, 16);
        setDocs("□ □ □ □ □", Color.BLUE, false, 16);
        Container container = frame.getContentPane();
        container.add(textPane, BorderLayout.CENTER);
        JLabel input = new JLabel();
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                /*
                向左箭头 --> 37
向上箭头 --> 38
向右箭头 --> 39
向下箭头 --> 40
                 */
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setDocs("□ □ □ □ □", Color.BLACK, false, 16);
                }
            }
        });
        container.add(input);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 300);
        frame.setVisible(true);
    }
}