import silentdoer.gui.panel.StockPanel;
import silentdoer.gui.utils.ImageMaker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuKeyEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class Entrance {
    public static void main(String[] args) throws Throwable{
        // 显示监控界面背景图
        StockPanel stockPanel = new StockPanel();  // 用JComponent也行，JPanel直接继承自JComponent
        stockPanel.setBackground(Color.decode("#D4D0C7"));  // 设置背景颜色
        stockPanel.setForeground(Color.decode("#000000"));
        ImageMaker.setMediaTracker(new MediaTracker(stockPanel));
        Image image = ImageMaker.getCachedImage(System.getProperty("user.dir").concat("/images/background.png"));
        stockPanel.setBackgroundImage(image);
        stockPanel.setSize(stockPanel.getPreferredSize());

        // 包含stockPanel和StackersPanel及InstViewer
        JLayeredPane jkPlatform = new JLayeredPane();
        jkPlatform.setBorder(new TitledBorder("边框标题"));
        jkPlatform.setOpaque(true);  // 设置为不透明的
        jkPlatform.setBackground(Color.decode("#D4D0C7"));
        jkPlatform.add(stockPanel, JLayeredPane.FRAME_CONTENT_LAYER);

        stockPanel.setLocation(0, 200);  // 相对于它的Owner而言的
        // 设置此句后当鼠标在stockPanel上停留一段时间后会出现ToolTip框，只显示一次（过一段时间如果没有再次set则此框消失），如果要一直显示则要在事件监听器处理方法里一直调用此方法（此事件要一直发生）。
        //stockPanel.setToolTipText("HelloMMM");

        JPanel stockersPane = new JPanel();
        stockersPane.setSize(300, 100);
        stockersPane.setLocation(20, 20);
        stockersPane.setBackground(Color.red);
        stockersPane.setBorder(new TitledBorder("堆垛机操作界面"));

        jkPlatform.add(stockersPane, JLayeredPane.DEFAULT_LAYER);

        Rectangle jkPlatformBounds = stockPanel.getBounds().union(stockersPane.getBounds());
        // 给Rectangle增加一个点（一个矩形外面有一个点，则扩充矩形使得矩形正好包括该点）；而添加新矩形就跟union类似只不过一个是返回新值一个是更新属性值
        jkPlatformBounds.add(0, 0);
        jkPlatform.setPreferredSize(jkPlatformBounds.getSize());

        JScrollPane medium = new JScrollPane(jkPlatform);
        medium.setBorder(null);
        medium.setOpaque(false);  // 设置为透明（这样就不会挡住什么，因为这里JScrollPane只是充当容器而不显示什么它本身的东西，如背景图片）

        JFrame rootFrame = new JFrame("这是一个标题");
        rootFrame.setIconImage(ImageMaker.getCachedImage(System.getProperty("user.dir").concat("/images/TP.gif")));
        rootFrame.setContentPane(medium);
        //rootFrame.setTitle("标题");
        rootFrame.setLocation(100, 100);
        //rootFrame.setSize(400, 300);
        rootFrame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();  // 上面的横栏
        JMenu menu = new JMenu("Menu");  // 横栏中的一列
        JMenuItem item = new JMenuItem("Item");
        //item.addActionListener(XX);
        JMenuItem item2 = new JMenuItem("Item2");
        // 菜单的选项
        menu.add(item);
        menu.addSeparator();  // 将两个Item分隔
        menu.add(item2);
        menu.setMnemonic(MenuKeyEvent.VK_F);  // Alt + F快捷键
        // 设置F1为item2的快捷键（生效的前提是menu点开了）
        item2.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_F1, 0));
        // 菜单栏的第二个菜单
        JMenu menu2 = new JMenu("Menu2");
        menu2.add(new JMenuItem("hello"));
        menu2.add(new ActionItemImpl());  // 本质上也会创建JMenuItem
        menuBar.add(menu);
        menuBar.add(menu2);

        // 控件如果要在JLayeredPane里显示则该控件要setSize
        //menuBar.setSize(menuBar.getPreferredSize());
        //jkPlatform.add(menuBar, JLayeredPane.POPUP_LAYER);

        rootFrame.setJMenuBar(menuBar);  // 给Frame设置菜单栏
        // 此窗体关闭时进程也关闭
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //rootFrame.setIconImage();
        rootFrame.pack();  // 将窗体置位合适大小（即rootFrame.setSize(..)可以不设置）
        //rootFrame.addWindowListener();
    }

    private static class ActionItemImpl extends AbstractAction{
        public ActionItemImpl() {
            super();
            super.putValue("Name", "ActionItem");  // Name的N要大写
        }

        @Override
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, System.getProperty("user.dir"), "消息", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
