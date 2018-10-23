import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/17/2018 3:58 PM
 */
public class SearchJarSettingComponent implements SearchableConfigurable {

    public String path;

    Project project;
    AnActionEvent action;
    // 可用GUI Form的mainPanel代替，即SearchJarSettingComponent本身就可以是一个Gui Form的绑定类
    JPanel settingJPanel;
    private JLabel pathLabel;
    private JButton browseBtn;
    private JTextField pathField;
    private PersistentState persistentState = PersistentState.getInstance();

    @NotNull
    @Override
    public String getId() {
        return "Search Jar";
    }

    // 在设置栏的标题
    @Nls
    @Override
    public String getDisplayName() {
        return "Search Jar";
    }

    // 配置界面
    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingJPanel != null) {
            settingJPanel.repaint();
            return settingJPanel;
        }
        pathField = new JTextField(50);
        pathLabel = new JLabel("Search Path");
        settingJPanel = new JPanel();
        browseBtn = new JButton();
        browseBtn.setIcon(AllIcons.Actions.Find);
        settingJPanel.add(pathLabel);
        settingJPanel.add(pathField);
        settingJPanel.add(browseBtn);
        pathLabel.setLabelFor(pathField);
        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseDir(settingJPanel, pathField);
            }
        });
        return settingJPanel;
    }

    private void browseDir(JPanel jPanel, JTextField pathField) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        if (chooser.showOpenDialog(jPanel) == 0) {
            pathField.setText(chooser.getSelectedFile().getPath());
        }
    }

    @Override
    public boolean isModified() {
        return !pathField.getText().equals(persistentState.getSearchPath());
    }

    // 点击ok或apply
    @Override
    public void apply() throws ConfigurationException {
        persistentState.setPath(pathField.getText());
    }
    // 打开界面立刻执行的方法
    @Override
    public void reset() {
        pathField.setText(persistentState.getSearchPath());
    }
}
