package me.silentdoer.ideaplugin.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import me.silentdoer.ideaplugin.persistent.PersistentState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TODO 注意这里的getChildren(..)是实时执行的，即每次需要显示group的时候都会执行一次
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/16/2018 7:24 PM
 */
public class MyDynamicActionGroup extends ActionGroup {

    PersistentState persistentState = PersistentState.getInstance();

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        // 这两个Action可以设置为Group的实例内部类，假设这些Action只属于这个group
        MyAction1 first = new MyAction1("first");
        boolean enableFirst = persistentState.isEnableFirst();
        if (enableFirst) {
            return new AnAction[]{first, new MyAction2("second")};
        } else {
            return new AnAction[]{new MyAction2("second")};
        }
    }
}
