//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hi.chyl.json;

import java.awt.Frame;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainAboutBox extends JDialog {
    private JButton closeButton;

    public MainAboutBox(Frame parent) {
        super(parent);
        this.initComponents();
        this.getRootPane().setDefaultButton(this.closeButton);
    }

    @Action
    public void closeAboutBox() {
        this.dispose();
    }

    private void initComponents() {
        this.closeButton = new JButton();
        JLabel appTitleLabel = new JLabel();
        JLabel versionLabel = new JLabel();
        JLabel appVersionLabel = new JLabel();
        JLabel vendorLabel = new JLabel();
        JLabel appVendorLabel = new JLabel();
        JLabel homepageLabel = new JLabel();
        JLabel appHomepageLabel = new JLabel();
        JLabel appDescLabel = new JLabel();
        JLabel imageLabel = new JLabel();
        JLabel homepageLabel1 = new JLabel();
        JLabel appHomepageLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        ResourceMap resourceMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getResourceMap(MainAboutBox.class);
        this.setTitle(resourceMap.getString("title", new Object[0]));
        this.setModal(true);
        this.setName("aboutBox");
        this.setResizable(false);
        ActionMap actionMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getActionMap(MainAboutBox.class, this);
        this.closeButton.setAction(actionMap.get("closeAboutBox"));
        this.closeButton.setName("closeButton");
        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | 1, (float)(appTitleLabel.getFont().getSize() + 4)));
        appTitleLabel.setText(resourceMap.getString("Application.title", new Object[0]));
        appTitleLabel.setName("appTitleLabel");
        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | 1));
        versionLabel.setText(resourceMap.getString("versionLabel.text", new Object[0]));
        versionLabel.setName("versionLabel");
        appVersionLabel.setText(resourceMap.getString("Application.version", new Object[0]));
        appVersionLabel.setName("appVersionLabel");
        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | 1));
        vendorLabel.setText(resourceMap.getString("vendorLabel.text", new Object[0]));
        vendorLabel.setName("vendorLabel");
        appVendorLabel.setText(resourceMap.getString("Application.vendor", new Object[0]));
        appVendorLabel.setName("appVendorLabel");
        homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | 1));
        homepageLabel.setText(resourceMap.getString("homepageLabel.text", new Object[0]));
        homepageLabel.setName("homepageLabel");
        appHomepageLabel.setText(resourceMap.getString("Application.homepage", new Object[0]));
        appHomepageLabel.setName("appHomepageLabel");
        appDescLabel.setText(resourceMap.getString("appDescLabel.text", new Object[0]));
        appDescLabel.setName("appDescLabel");
        imageLabel.setIcon(resourceMap.getIcon("imageLabel.icon"));
        imageLabel.setName("imageLabel");
        homepageLabel1.setFont(homepageLabel1.getFont().deriveFont(homepageLabel1.getFont().getStyle() | 1));
        homepageLabel1.setText(resourceMap.getString("homepageLabel1.text", new Object[0]));
        homepageLabel1.setName("homepageLabel1");
        appHomepageLabel1.setText(resourceMap.getString("appHomepageLabel1.text", new Object[0]));
        appHomepageLabel1.setName("appHomepageLabel1");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(imageLabel).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(appDescLabel, -1, 266, 32767).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(versionLabel).addComponent(vendorLabel).addComponent(homepageLabel)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(appVersionLabel).addComponent(appVendorLabel).addComponent(appHomepageLabel, -2, 102, -2))).addComponent(appTitleLabel).addGroup(layout.createSequentialGroup().addComponent(homepageLabel1).addPreferredGap(ComponentPlacement.RELATED).addComponent(appHomepageLabel1, -1, 220, 32767)))).addGroup(layout.createSequentialGroup().addGap(106, 106, 106).addComponent(this.closeButton))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(imageLabel, -2, -1, 32767).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(appTitleLabel).addPreferredGap(ComponentPlacement.RELATED).addComponent(appDescLabel, -2, 24, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(versionLabel).addComponent(appVersionLabel)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(vendorLabel).addComponent(appVendorLabel)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(homepageLabel).addComponent(appHomepageLabel, -2, 15, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(homepageLabel1).addComponent(appHomepageLabel1, -2, 15, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.closeButton).addGap(1, 1, 1)));
        this.pack();
    }
}
