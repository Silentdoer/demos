package me.silentdoer.mybatispluginqaq.action;

import com.intellij.database.psi.DbTable;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import me.silentdoer.mybatispluginqaq.model.TableInfo;

import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/30/2018 5:15 PM
 */
public class MainAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        PsiElement[] psiElements = anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            Messages.showMessageDialog("请选择一张表", "提示", Messages.getInformationIcon());
            return;
        }
        for (PsiElement psiElement : psiElements) {
            if (!(psiElement instanceof DbTable)) {
                Messages.showMessageDialog("请选择一张表", "提示", Messages.getInformationIcon());
                return;
            }
        }
        DbTable table = (DbTable) anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY)[0];
        TableInfo tableInfo = new TableInfo(table);
        String value = Messages.showInputDialog("1显示表名，2显示列信息", "输入框", AllIcons.Actions.Find);
        if ("1".equals(value)) {
            Messages.showInputDialog(tableInfo.getTableName().concat(",").concat(String.valueOf(table.getComment())), "Title", Messages.getInformationIcon());
        } else {
            Messages.showInputDialog(tableInfo.getColumns().stream().map(e -> {
                return String.join(",", e.getName(), e.getDataType().toString(), e.getComment());
            }).collect(Collectors.joining("#", "[", "]")), "Title", AllIcons.Actions.Checked);
        }
    }
}
