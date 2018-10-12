package me.silentdoer.myplugin;

import com.intellij.icons.AllIcons;
import com.intellij.lang.jvm.JvmModifier;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/10/2018 3:16 PM
 */
public class FirstCustomAction extends AnAction {

    public FirstCustomAction() {
        super("Dynamically added action.");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        VirtualFile[] allFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        String activeFiles = Arrays.stream(allFiles).map(VirtualFile::getName).collect(Collectors.joining(",", "[", "]"));
        // Messages.showMessageDialog(String.format("文件：%s,class:%s", activeFiles, e.getDataContext().getClass()), "提示", Messages.getInformationIcon());

        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (Objects.isNull(project)) {
            Messages.showMessageDialog("当前未打开项目", "错误", Messages.getErrorIcon());
            return;
        }
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (Objects.isNull(file)) {
            Messages.showMessageDialog("当前未打开活动文件", "错误", Messages.getErrorIcon());
            return;
        }
        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (Objects.isNull(fileEditor)) {
            Messages.showMessageDialog("当前未打开活动文件", "错误", Messages.getErrorIcon());
            return;
        }
        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();
        Module module = modules[0];
        // module的绝对路径，如果获取module的名字用module.getName()即可
        String moduleFilePath = module.getModuleFilePath();
        /*if (module.getModuleContentScope().contains(file)) {
            Messages.showMessageDialog(String.format("文件：%s属于%s模块", file.getName(), module.getName()), "提示", Messages.getInformationIcon());
            return;
        }*/

        // 项目名，即获取调用此插件的当前项目的名字，输出为demo-test-plugin-dev
        String projectName = project.getName();
        // C:/Users/liqi.wang/IdeaProjects/demo-test-plugin-dev
        String projectBasePath = project.getBasePath();
        // java
        String fileTypeName = file.getExtension();
        // Entrance.java
        String fileName = file.getName();
        // C:/Users/liqi.wang/IdeaProjects/demo-test-plugin-dev/src/Entrance.java
        String fileCanonicalPath = file.getCanonicalPath();
        String moduleName = module.getName();
        // Messages.showMessageDialog(String.format("%s#%s#%s#%s#%s#%s#%s", moduleName, moduleFilePath, projectName, projectBasePath, fileTypeName, fileName, fileCanonicalPath), "TestTile", Messages.getWarningIcon());

        Module curModule = LangDataKeys.MODULE.getData(e.getDataContext());
        Objects.requireNonNull(curModule, "由于我的group-id是EditorPopupMenu所以肯定不为null");
        // 获得当前文件所在的Module的名字；
        String curModuleName = curModule.getName();
        // PsiFile很特别，可以从它上获得文件的语言（java、kotlin、xml）
        PsiFile[] files = FilenameIndex.getFilesByName(project, fileName, curModule.getModuleContentScope());
        // 重要，居然还能对源文件作为一个类来操作，获得如父类、子类等信息
        // TODO 这个真的叼爆了，不但可以获取.java文件的成员，而且还能获取它父类的成员（protected），这样自己写的工具其实派不上用场了
        PsiClass aClass = JavaPsiFacade.getInstance(project).findClass("me.silentdoer.tc.Foo", GlobalSearchScope.projectScope(project));

        //Messages.showMessageDialog(String.format("%s", Arrays.stream(aClass.getAllFields()).map(r -> r.getName()).collect(Collectors.joining(","))), "提示", AllIcons.Actions.Find);

        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(e.getDataContext());
        Objects.requireNonNull(psiFile, "不可能是null");
        // 这里就是当前文件的PsiPackage，PsiClass等等数据，注意，因为他们都继承自接口PsiElement；
        if (!psiFile.getLanguage().getDisplayName().toLowerCase().equals("java")) {
            Messages.showMessageDialog("当前活动文件不是java文件", "提示", Messages.getInformationIcon());
        }


        PsiClass curFilePsiClass = (PsiClass) Arrays.stream(psiFile.getChildren()).filter(r -> r instanceof PsiClass).findFirst().orElse(null);
        Objects.requireNonNull(curFilePsiClass, "不可能是null");
        Messages.showMessageDialog(String.format("%s, %s", curFilePsiClass.getQualifiedName(), Arrays.stream(curFilePsiClass.getAllFields()).
                filter(r -> !r.hasModifier(JvmModifier.FINAL) && !r.hasModifier(JvmModifier.PUBLIC) && !r.hasModifier(JvmModifier.STATIC) && !r.hasModifier(JvmModifier.PACKAGE_LOCAL))
                .map(r -> new HashMap<String, String>(1) {
                    {
                        put(r.getName(), r.getType().getPresentableText());
                    }
                }).reduce((a, b) -> {
                    a.putAll(b);
                    return a;
                }).orElseGet(() -> null)), "提示", AllIcons.Icon);

    }
}