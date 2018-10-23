package me.silentdoer.ideaplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/15/2018 11:33 AM
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    private ToolWindow toolWindow;

    private JPanel mainPanel;

    private JLabel usernameLbl;

    private JLabel passwordLbl;

    private JTextField txtUsername;

    private JTextField txtPassword;

    private JTextArea txtContent;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;

        // TODO 如果ToolWindow只有一个标签页，那么将第二个参数设置为空字符串即可
        Content content = ContentFactory.SERVICE.getInstance().createContent(mainPanel, "TagTitle", false);
        toolWindow.getContentManager().addContent(content);

        txtContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                txtContent.setCursor(new Cursor(Cursor.TEXT_CURSOR));   //鼠标进入Text区后变为文本输入指针
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                txtContent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));   //鼠标离开Text区后恢复默认形态
            }
        });
    }
}
