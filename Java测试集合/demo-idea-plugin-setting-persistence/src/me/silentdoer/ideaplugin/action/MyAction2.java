package me.silentdoer.ideaplugin.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/16/2018 7:03 PM
 */
public class MyAction2 extends AnAction {

    public MyAction2(String text) {
        super(text);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Messages.showInfoMessage("jlksfjl", "Titlesee");
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(AllIcons.General.Balloon);
    }
}
