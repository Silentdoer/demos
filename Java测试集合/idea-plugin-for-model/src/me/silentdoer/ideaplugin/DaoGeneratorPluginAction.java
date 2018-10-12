package me.silentdoer.ideaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.ui.popup.PopupComponent;

import javax.swing.*;
import java.awt.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 7:59 PM
 */
public class DaoGeneratorPluginAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        DialogBuilder builder = new DialogBuilder();
        builder.setTitle("世界和平");
        JPanel centerPanel = new JPanel();
        centerPanel.setSize(400, 300);
        centerPanel.setPreferredSize(new Dimension(400, 300));
        centerPanel.setVisible(true);
        builder.setCenterPanel(centerPanel);
        Window window = builder.getWindow();
        window.setName("啦啦啦");
        window.pack();
        window.setVisible(true);
    }
}
