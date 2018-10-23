package me.silentdoer.ideaplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.openapi.wm.WindowManager;
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
        PluginDialog dialog = new PluginDialog();
        dialog.setTitle("啦啦啦");
        dialog.pack();
        dialog.setLocationRelativeTo(WindowManager.getInstance().getFrame(anActionEvent.getProject()));
        dialog.setVisible(true);
    }
}
