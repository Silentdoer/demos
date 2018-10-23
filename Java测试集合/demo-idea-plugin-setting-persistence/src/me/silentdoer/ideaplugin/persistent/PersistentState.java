package me.silentdoer.ideaplugin.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.ui.Messages;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * name：持久化文件组件名称可随意指定，通常用插件名称即可。
 *
 * storages ：定义配置参数的持久化位置。其中$APP_CONFIG$变量为Idea安装后默认的用户路径，
 * 例如：C:\Users\liqi.wang\.IntelliJIdea2018.1\config\options\silentdoerSettings.xml
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 10:53 AM
 */
@State(name = "Silentdoer Plugin", storages = {@Storage("$APP_CONFIG$/silentdoerSettings.xml")})
public class PersistentState implements PersistentStateComponent<Element> {

    private String codeCleanerPattern;

    private boolean enableFirst;

    public static PersistentState getInstance() {
        return ServiceManager.getService(PersistentState.class);
    }

    /**
     * 当保存配置后会自动修改或生成（当文件不存在）如下配置文件（第一个元素默认就是component，但子元素则是按name来命名）
     * <application>
     *   <component name="Silentdoer Plugin" version="1.0.0">
     *     <plugin name="CodeCleaner" pattern="ewr" />
     *   </component>
     * </application>
     */
    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("Silentdoer-Settings");
        element.setAttribute("version", "1.0.0");
        Element codeCleaner = new Element("plugin");
        codeCleaner.setAttribute("name", "CodeCleaner");
        codeCleaner.setAttribute("pattern", this.codeCleanerPattern);
        element.addContent(codeCleaner);

        Element config = new Element("config");
        config.setAttribute("name", "ActionEnableConfig");
        config.setAttribute("first", String.valueOf(this.enableFirst));
        element.addContent(config);
        return element;
    }

    /**
     * 由于设置了这个类是applicationService，所以IDEA启动后就会先自动调用loadState来加载数据
     * @param element 注意这个element是component元素
     */
    @Override
    public void loadState(@NotNull Element element) {
        Element codeCleaner = element.getChildren("plugin").stream()
                .filter(e -> Objects.equals(e.getAttributeValue("name"), "CodeCleaner"))
                .findFirst().orElse(null);

        Element actionEnableConfig = element.getChildren("config").stream()
                .filter(e -> Objects.equals(e.getAttributeValue("name"), "ActionEnableConfig"))
                .findFirst().orElse(null);
        if (Objects.isNull(codeCleaner)) {
            this.codeCleanerPattern = "";
        } else {
            this.codeCleanerPattern = codeCleaner.getAttributeValue("pattern");
        }

        if (Objects.isNull(actionEnableConfig)) {
            this.enableFirst = false;
        } else {
            this.enableFirst = Boolean.valueOf(actionEnableConfig.getAttributeValue("first"));
        }
    }

    public String getCodeCleanerPattern() {
        return Objects.isNull(codeCleanerPattern) ? "" : codeCleanerPattern;
    }

    public void setCodeCleanerPattern(String codeCleanerPattern) {
        this.codeCleanerPattern = codeCleanerPattern;
    }

    public boolean isEnableFirst() {
        return enableFirst;
    }

    public void setEnableFirst(boolean enableFirst) {
        this.enableFirst = enableFirst;
    }
}
