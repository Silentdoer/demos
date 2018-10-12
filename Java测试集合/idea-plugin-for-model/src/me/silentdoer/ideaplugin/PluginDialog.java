package me.silentdoer.ideaplugin;

import javax.swing.*;
import java.awt.event.*;

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
