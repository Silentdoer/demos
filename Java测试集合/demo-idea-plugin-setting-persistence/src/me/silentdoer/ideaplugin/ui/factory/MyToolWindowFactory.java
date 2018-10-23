package me.silentdoer.ideaplugin.ui.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/15/2018 10:52 AM
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private ToolWindow myToolWindow;

    private JPanel mainPanel;

    private JTextArea textArea;

    private JScrollPane scrollPane;

    private JTextArea txtContent;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        this.myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);

        txtContent.setEditable(true);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        txtContent.setOpaque(false);

        txtContent.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                txtContent.setCursor(new Cursor(Cursor.HAND_CURSOR));   //鼠标进入Text区后切换指针
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                txtContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));   //鼠标离开Text区后恢复默认形态
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                // TODO 设置在ToolWindow内部的Content右键时弹出Popup窗口
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // 添加右键菜单的内容 2017/3/18 21:12
                    JBList<String> list = new JBList<>();
                    list.setFocusable(false);
                    String[] title = new String[2];
                    title[0] = "Select All";
                    title[1] = "Clear All";
                    list.setListData(title); // 设置数据 2017/3/18 21:13

                    // 创建菜单 2017/3/18 21:13
                    JBPopup popup = new PopupChooserBuilder(list)
                            .setItemChoosenCallback(new Runnable() { // 添加点击项的监听事件 2017/3/18 21:13
                                @Override
                                public void run() {
                                    String value = list.getSelectedValue();
                                    if ("Clear All".equals(value)) {
                                        txtContent.setText("");
                                    } else if ("Select All".equals(value)) {
                                        txtContent.selectAll();
                                    }
                                }
                            }).createPopup();

                    // 设置大小 2017/3/18 21:13
                    Dimension dimension = popup.getContent().getPreferredSize();
                    popup.setSize(new Dimension(150, dimension.height));

                    // 显示 2017/3/18 21:25
                    popup.show(new RelativePoint(e)); // 传入e，获取位置进行显示 2017/3/19 09:48
                }
            }
        });

        txtContent.getCaret().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                txtContent.getCaret().setVisible(true);   //使Text区的文本光标显示
            }
        });

        // 数据初始化
        txtContent.append("上解放路口地方街道上看见网io分解微分");
        mainPanel.setFocusable(true);
        mainPanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // TODO 强行获得焦点
                txtContent.requestFocus();
            }
        });
    }
}
