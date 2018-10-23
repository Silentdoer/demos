package me.silentdoer.ideaplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;

public class PluginDialog extends JDialog {

    private JPanel contentPane;

    private JButton buttonOK;

    private JButton buttonCancel;

    private JTextField usernameTxt;

    private JTextField passwordTxt;

    private JLabel usernameLbl;

    private JLabel passwordLbl;

    public PluginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // TODO add your code here
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        // 由于是测试，所以只有最后一个idea有这个插件
        Project project = openProjects[openProjects.length - 1];
        if (Objects.nonNull(project)) {
            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Silentdoer Output");
            if (Objects.nonNull(toolWindow)) {

                // 只是用于自动打开下方的ToolWindow
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                JTextArea textArea = (JTextArea) Arrays.stream(toolWindow.getContentManager().getContent(0)
                                                                       .getComponent()
                                                                       .getComponents())
                        .filter(c -> c instanceof JTextArea).findFirst().get();

                String name = toolWindow.getContentManager().getContent(0).getComponent().getClass().getSimpleName();
                this.passwordTxt.setText(name);
                textArea.append("房间死哦房间");
            }
        }
        // 销毁Dialog
        dispose();
    }

    private void onCancel() {
        // TODO add your code here if necessary

        // 销毁对话框
        dispose();
    }

    public static void main(String[] args) {
        PluginDialog dialog = new PluginDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
