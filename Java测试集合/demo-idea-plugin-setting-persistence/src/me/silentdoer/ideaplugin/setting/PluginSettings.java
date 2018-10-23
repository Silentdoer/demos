package me.silentdoer.ideaplugin.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import me.silentdoer.ideaplugin.persistent.PersistentState;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

/**
 * TODO 更改配置后 有些非实时读取的配置是需要重启生效的
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 11:56 AM
 */
public class PluginSettings implements SearchableConfigurable {

    private JPanel mainPanel;

    private JPanel availPlugin;

    private JPanel pluginSetting;

    private JLabel lblCodeCleaner;

    private JTextField txtPattern;

    private JLabel lblShowFirst;

    private JCheckBox chkBoxEnableFirst;

    /**
     * 插件配置的持久化
     */
    private PersistentState persistentState = PersistentState.getInstance();

    /**
     * @return 在Setting中显示的配置栏的名称
     */
    @Nls
    @Override
    public String getDisplayName() {
        return "Silentdoer Settings";
    }

    @NotNull
    @Override
    public String getId() {
        return "Silentdoer Settings";
    }

    /**
     * @return 插件配置界面的绘制，负责用户输入信息的接受。
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        //this.mainPanel.repaint();
        return this.mainPanel;
    }

    /**
     * 当用户修改配置参数后，在点击“OK”“Apply”按钮前，框架会自动调用该方法，判断是否有修改，进而控制按钮“OK”“Apply”的是否可用。
     */
    @Override
    public boolean isModified() {
        // TODO
        return !Objects.equals(persistentState.getCodeCleanerPattern(), txtPattern.getText())
                || persistentState.isEnableFirst() != chkBoxEnableFirst.isSelected();
    }

    /**
     * 用户点击“OK”或“Apply”按钮后会调用该方法，通常用于完成配置信息持久化。【同时会调用getState()来持久化】
     */
    @Override
    public void apply() throws ConfigurationException {
        persistentState.setCodeCleanerPattern(txtPattern.getText());
        persistentState.setEnableFirst(chkBoxEnableFirst.isSelected());
    }

    /**
     * 启动后会立刻调用的api，从而自动从persistentState里取出文件里的值
     */
    @Override
    public void reset() {
        // TODO 随着配置的增多这里需要重置的配置是要增加的）
        txtPattern.setText(persistentState.getCodeCleanerPattern());
        boolean enableFirst = persistentState.isEnableFirst();
        chkBoxEnableFirst.setSelected(enableFirst);
    }
}
