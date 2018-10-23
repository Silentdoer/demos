package me.silentdoer.ideaplugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/16/2018 7:02 PM
 */
public class MyAction1 extends AnAction {

    public MyAction1(String text) {
        super(text);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Messages.showMessageDialog("绿色空间放到", "Sjkfl", Messages.getInformationIcon());
    }
}
