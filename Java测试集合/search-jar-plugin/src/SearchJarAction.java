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

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 9:40 PM
 */
public class SearchJarAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        LangDataKeys.MODULE.getData(e).
        showClassDlg();
    }

    public void showClassDlg() {
        String className = getSearchClass();
        if ((className == null) || (!className.matches("([a-zA-Z1-9]+\\.)*[a-zA-Z1-9]+"))) {
            return;
        }

        if ((PersistentState.getInstance().getSearchPath() == null) || (PersistentState.getInstance().getSearchPath().trim().isEmpty())) {
            Messages.showInfoMessage("Please set the search directory first.\n[File]->[Settings]-[Search Jar]", "Search jar path");
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
            CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();
            DataFlavor[] flavors = copyPasteManager.getContents().getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                if (!copyPasteManager.getContents().isDataFlavorSupported(flavor)) {
                    continue;
                }
                Object obj = null;
                try {
                    obj = copyPasteManager.getContents().getTransferData(flavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ((obj != null) && (obj instanceof String)) {
                    initValue = obj.toString();
                }
            }
            return initValue;
        }
}
