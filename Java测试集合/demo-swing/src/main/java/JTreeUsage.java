import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Hashtable;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/29/2018 7:36 PM
 */
public class JTreeUsage {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(500, 550);
        // Container是不需要设置大小的，它就是window的Content部分【即border、标题栏这些除去的那部分】
        Container cp = window.getContentPane();
        // 表示不用布局管理器，那么cp内所有的组件都通过setBounds(..)来设置绝对位置和大小【类似C#里的托控件的感觉】
        cp.setLayout(null);

        /*// 对JTree进行初始化
        JTree jTree = new JTree(new Hashtable<String, String>(4) {
            {
                put("aa", "啦啦啦");
                put("bb", "语句是");
                put("cc", "卡洛斯放假啦");
                put("aaa", "我方是");
            }
        });
        // 是否显示一个根节点【可以理解为最外围的JSON对象】
        jTree.setRootVisible(true);*/

        // 实现了TreeNode接口，作为JTree中的某个节点，TODO 注意这个也可以是复杂变量，那么显示的节点名则是obj.toString()
        // TODO 如果要为节点设置自定义的图标，需要实现自己的Renderer
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("根元素名");
        JTree jTree = new JTree(root);
        root.add(new DefaultMutableTreeNode("一级子节点"));
        DefaultMutableTreeNode 一级子节点2 = new DefaultMutableTreeNode("一级子节点2");
        root.add(一级子节点2);
        一级子节点2.add(new DefaultMutableTreeNode("二级子节点"));

        // 这种写法可以理解为JTree就是JScrollPane的Container【除去拖动栏这些】
        JScrollPane jsp = new JScrollPane(jTree);
        jsp.setBounds(100,20,300,500);
        cp.add(jsp);

        // 添加选择事件
        jTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // 这里涉及到闭包的概念，你可以理解为编译器会为这个匿名类自动添加了一个JTree成员，然后将外部的jTree set，
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree
                        .getLastSelectedPathComponent();

                if (node == null) {
                    return;
                }

                Object object = node.getUserObject();
                // 只对叶子节点生效
                if (node.isLeaf()) {
                    System.out.println("你选择了：" + object);
                }
            }
        });

        window.setVisible(true);
    }
}
