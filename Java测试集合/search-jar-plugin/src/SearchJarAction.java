import com.intellij.compiler.impl.generic.GenericCompilerCache;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.PersistentStateComponentWithModificationTracker;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 9:40 PM
 */
public class SearchJarAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        showClassDlg();
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(AllIcons.Actions.Find);
    }

    public void showClassDlg() {
        String className = getSearchClass();
        if ((className == null) || (!className.matches("([a-zA-Z1-9]+\\.)*[a-zA-Z1-9]+"))) {
            return;
        }

        if ((PersistentState.getInstance().getSearchPath() == null) || (PersistentState.getInstance().getSearchPath().trim().isEmpty())) {
            Messages.showInfoMessage("Please set the search directory first.\n[File]->[Settings]-[Search Jar]", "Search Jar Path");
            return;
        }
        searchJar(className);
    }

    private String getSearchClass() {
        String initValue = getClassName();
        return Messages.showInputDialog("Class Name:", "Search Jar", AllIcons.Actions.Find, initValue, null);
    }

    private String getClassName() {
        String copyValue = getCopyValue();
        if (copyValue.trim().startsWith("import ") || copyValue.trim().endsWith(";")) {
            return copyValue.replace("import ", "").replace(";", "").trim();
        }
        return copyValue.trim();
    }

    private void searchJar(String className) {
        ProgressManager.getInstance().run(new SearchBackgroundableTask(ProjectManager.getInstance().getOpenProjects()[0], "Searching jar...", className)); // 启动搜索背景任务
    }
        private String getCopyValue() {
            String initValue = "";
            // TODO 获得当前粘贴缓存里的数据
            CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();
            Transferable contents = copyPasteManager.getContents();
            if (Objects.isNull(contents)) {
                return initValue;
            }
            DataFlavor[] flavors = contents.getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                if (!copyPasteManager.getContents().isDataFlavorSupported(flavor)) {
                    continue;
                }
                Object obj = null;
                try {
                    obj = copyPasteManager.getContents().getTransferData(flavor);
                } catch (Exception e) {
                    // ignore
                }
                if ((obj instanceof String)) {
                    initValue = obj.toString();
                }
            }
            return initValue;
        }
}
