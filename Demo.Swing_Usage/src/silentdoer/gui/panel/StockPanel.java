package silentdoer.gui.panel;

import silentdoer.gui.utils.ImageMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// 可以设置Layout
public class StockPanel extends JComponent {
    private Image backgroundImage;
    private Image ddjImg;
    private Point lastPressed;
    private boolean ddjIsSelected;

    public void setBackgroundImage(Image backgroundImage) {
        Image old = this.backgroundImage;
        this.backgroundImage = backgroundImage;
        if(this.backgroundImage != old) {
            this.setPreferredSize(new Dimension(this.backgroundImage.getWidth(this), this.backgroundImage.getHeight(this)));
        }

        ddjImg = ImageMaker.getCachedImage(System.getProperty("user.dir").concat("/images/DDJ.gif"));

        ddjRange = new Rectangle(30, 30, ddjImg.getWidth(this), ddjImg.getHeight(this));  //

        this.addMouseMotionListener(new MouseMotionListenerImpl());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPressed = e.getPoint();
                if(ddjRange.contains(lastPressed)){
                    ddjIsSelected = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                ddjIsSelected = false;
            }
        });
        this.setToolTipText("注册一下");
    }

    /*@Override
    public void paint(Graphics g) {
        System.out.println("DDDDDDDDDDDD");
        super.paint(g);
        if(this.getWidth() == 0 || this.getHeight() == 0 || !this.isShowing())
            return;
        if(this.isOpaque())
        {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(getForeground());
        this.paintComponent(g);
        this.paintBorder(g);
        this.paintChildren(g);
    }*/

    // 网上说覆盖paintComponent即可，不要覆盖paint方法
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.backgroundImage != null && g.hitClip(0, 0, getWidth(), getHeight()))
        {
            Rectangle clip = g.getClipBounds();
            clip = clip.intersection(new Rectangle(0, 0, this.backgroundImage.getWidth(this), this.backgroundImage.getHeight(this)));
            g.drawImage(this.backgroundImage, clip.x, clip.y, clip.x + clip.width, clip.y + clip.height, clip.x, clip.y, clip.x + clip.width, clip.y + clip.height, this);
        }
        // 创建一支和g一模一样功能的新画笔画堆垛机
        paintDDJ((Graphics2D)g.create());
        // super的后执行应该是super画的会在StockPanel上面（故推测设备是在ShapePane里画的，而托盘图标又是在设备之后画的）
    }

    private Rectangle ddjRange = new Rectangle();

    // 每个设备都是一个ImageMaker的话则这个方法可以放在ImageMaker里
    private void paintDDJ(Graphics2D g2){
        /*Rectangle shape = new Rectangle();
        shape.x = 150;
        shape.y = 150;
        shape.height = 30;
        shape.width = 30;
        g2.draw(shape);*/

        g2.drawImage(ddjImg, ddjRange.x, ddjRange.y, ddjRange.width, ddjRange.height, this);

        Rectangle shape = new Rectangle();
        shape.x = ddjRange.x;
        shape.y = ddjRange.y;
        shape.height = ddjRange.height;
        shape.width = ddjRange.width;
        g2.draw(shape);

        g2.translate(ddjRange.getX(), ddjRange.getY());
        Color oldColor = g2.getColor();
        g2.setColor(Color.green);
        g2.fillRect(0, 0, 10, 10);
        g2.setColor(oldColor);

        g2.drawImage(ImageMaker.getCachedImage(System.getProperty("user.dir").concat("/images/TP.gif")), 15, 3, this);

        g2.translate(-ddjRange.getX(), -ddjRange.getY());
    }

    private static int s = 0;
    @Override  // 注册之后在控件上一移动就会触发事件使得ToolTipManager调用getToolTipText，如果返回的值一样则显示位置不变
    public String getToolTipText(MouseEvent e) {
            int curDevice;
            if(ddjRange.getBounds().contains(e.getPoint())){  // 鼠标处于堆垛机上
                curDevice = 1;
            }else{
                curDevice = -1;
            }
            if(curDevice != -1){
                return "<html>" + "通过curDevice获取当前设备的名字" + "</html>";
            }else{
                return String.format("<html><body><b>%s</b>, <font color=\"red\">%s</font></body><html>", e.getX(), e.getY());
            }


//        String text = (String)super.getClientProperty(TOOL_TIP_TEXT_KEY);
//
//        boolean debug = true;
//        if(text == null){
//            if(debug){
//                text = String.format("<html><body><b>%s</b>, <h1>%s</h1></body><html>", e.getX(), e.getY());
//            }
//        }
//        String lll = "##" + s;
//        s++;
//        text += lll;
//        System.out.println(text);
//        return text;
    }

    // 对于ToolTipText而言最重要的就是getToolTipText方法和为控件注册ToolTip（每当鼠标在该控件上移动时都会触发ToolTipManager的方法，然后调用getToolTipText）

    @Override
    public void setToolTipText(String text) {
        // 取出内存区A里的key是TOOL_TIP_TEXT_KEY的值
        String oldText = (String)super.getClientProperty(TOOL_TIP_TEXT_KEY);
        // 这里设置要显示的文字（存储在内存区A里）
        super.putClientProperty(TOOL_TIP_TEXT_KEY, text == null ? null : ("<html>".concat(text).concat("</html>")));
        // 获取共享示例（比如XX作用域下的单例）
        ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
        if (text != null) {
            if (oldText == null) {  // 说明第一次移动到this控件上
                // 主要看registerComponent，至于clientProperty只是获取要显示值的一种手段，这个值完全可以是自己自定义的而不是非要从clientProperty里取（但是是通过getToolTipText取）
                // setToolTipText方法其实可以不要，这里主要的代码只是为this注册ToolTip而已，在程序任意地方都可以注册。

                // 错误的原理应该是这个manager利用观察者模式/监听器模式注册了一个观察者到JComponent监听clientProperties的值的变化
                // ，一旦执行了super.putClientProperty(TOOL_TIP_TEXT_KEY, text)然后manager就能马上知道（即putClientProperty有触发方法，里会调用manager注册上去的回调方法）
                // ，但是manager知道的值是JComponent通过getToolTipText获取给manager的，JComponent的getToolTipText是getClientProperty获得
                // ，然后manager为this清除老的Tip显示然后显示新的并重开Tip显示时间的监听器（如果回调方法获取的值是老值则只是重置显示时钟而不会刷新Tip框）。如果某个显示超过Delay时间后则也会被manager清除。
                toolTipManager.registerComponent(this);  // 比如在HashMap里添加一条记录key是this，而value可以是默认值（初始值，如null、0、-1之类的，有点像AGV任务的未下发状态）
                toolTipManager.setDismissDelay(3000);
                toolTipManager.setInitialDelay(100);
                //System.out.println("FJKLJFLDJ");  // 不断调用此方法这里也只执行了一次
            }
        } else {  // text为null则取消注册此控件的ToolTip
            //System.out.println("取消");
            //toolTipManager.unregisterComponent(this);  // 貌似没啥用，不过一般也不需要用到
        }
    }

    // 自己写的事件监听器最好继承EventListener
    private class MouseMotionListenerImpl implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e){
            //System.out.println("MouseDragged");
            if(!ddjIsSelected)
                return;
            // 先不判断是否拖出边界
            Rectangle old = ddjRange;
            Point cur = e.getPoint();
            ddjRange = new Rectangle(old.x + (cur.x - lastPressed.x), old.y + (cur.y - lastPressed.y), old.width, old.height);
            lastPressed = cur;
            Rectangle refreshRange = new Rectangle(old.x - 2, old.y -2, old.width + 4, old.height + 4).union(new Rectangle(ddjRange.x - 2, ddjRange.y - 2, ddjRange.width + 4, ddjRange.height + 4));
            StockPanel.this.repaint(refreshRange);  // 只刷新部分区域
        }

        @Override
        public void mouseMoved(MouseEvent e){
//            //System.out.println("MouseMoved");
//            //StockPanel.this.putClientProperty("ToolTipMsg", ((Object) ("<html>".concat(String.format("%s, %s", e.getX(), e.getY())).concat("</html>"))));
//            int curDevice;
//            if(ddjRange.getBounds().contains(e.getPoint())){  // 鼠标处于堆垛机上
//                curDevice = 1;
//            }else{
//                curDevice = -1;
//            }
//            if(curDevice != -1){
//                StockPanel.this.setToolTipText("通过curDevice获取当前设备的名字");
//            }else{
//                // ToolTip的概念可以简单理解为每设置一次就显示一段时间（同样值只更新显示时间，不同值还更新显示位置）
//                // ，而显示的内容却是通过getToolTipText获得，setToolTipText方法手动调用，而getToolTipText方法“自动”调用。
//                StockPanel.this.setToolTipText(null);
//            }
        }
    }
}
