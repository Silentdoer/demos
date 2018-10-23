package hi.chyl.json;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.netbeans.swing.tabcontrol.DefaultTabDataModel;
import org.netbeans.swing.tabcontrol.TabData;
import org.netbeans.swing.tabcontrol.TabDataModel;
import org.netbeans.swing.tabcontrol.TabbedContainer;
import org.netbeans.swing.tabcontrol.event.ComplexListDataEvent;
import org.netbeans.swing.tabcontrol.event.ComplexListDataListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.Segment;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/22/2018 10:45 AM
 */
public class MyFrame extends JFrame {

    private JDialog aboutBox;
    private TabDataModel tabDataModel;
    private TabbedContainer tabbedContainer;
    private Map jsonEleTreeMap = new HashMap();
    private boolean isTxtFindDlgOpen = false;
    private boolean isTreeFinDlgdOpen = false;
    private List<TreePath> treePathLst = new ArrayList();
    private char dot = 30;
    private int curPos = 0;


    private void initUI() {
        Image ico = (new ImageIcon(MyFrame.this.getClass().getResource("resources/json.png"))).getImage();
        MyFrame.this.setIconImage(ico);
        //MyFrame.this.setToolBar(MyFrame.this.createToolBar());
        MyFrame.this.getRootPane().getContentPane().add(MyFrame.this.createToolBar(), "North");
        MyFrame.this.getRootPane().setJMenuBar(MyFrame.this.createMenuBar());
        MyFrame.this.initTabbedContainer();
        MyFrame.this.getRootPane().getContentPane().add(MyFrame.this.tabbedContainer, "Center");
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        final JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(180, 25));
        JButton btnAppTitle = new JButton("标题修改");
        JButton btnFormat = new JButton("格式化JSON字符串(F)");
        JButton btnClean = new JButton("清空(D)");
        JButton btnParse = new JButton("粘帖(V)");
        JButton btnNewLine = new JButton("清除(\\n)");
        JButton btnXG = new JButton("清除(\\)");
        JButton btnTxtFind = new JButton("文本查找");
        JButton btnNodeFind = new JButton("节点查找");
        JButton btnNewTab = new JButton("新标签(N)");
        JButton btnSelTabName = new JButton("标签名修改");
        JButton btnCloseTab = new JButton("关闭标签");
        btnAppTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(textField.getText());
                MyFrame.this.setTitle(textField.getText());
            }
        });
        btnFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.formatJson();
            }
        });
        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = MyFrame.this.getTextArea();
                if (ta != null) {
                    ta.setText("");
                }

            }
        });
        btnParse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = MyFrame.this.getTextArea();
                if (ta != null) {
                    ta.paste();
                    MyFrame.this.formatJson();
                }

            }
        });
        btnNewLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = MyFrame.this.getTextArea();
                if (ta != null) {
                    ta.setText(ta.getText().replaceAll("\n", ""));
                }

            }
        });
        btnXG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = MyFrame.this.getTextArea();
                if (ta != null) {
                    ta.setText(ta.getText().replaceAll("\\\\", ""));
                }

            }
        });
        btnTxtFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MyFrame.this.isTxtFindDlgOpen) {
                    MyFrame.this.showFindDialog(1, "文本查找对话框");
                }
            }
        });
        btnNodeFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MyFrame.this.isTreeFinDlgdOpen) {
                    MyFrame.this.showFindDialog(2, "树节点查找对话框");
                }
            }
        });
        btnSelTabName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selIndex = MyFrame.this.getTabIndex();
                if (selIndex >= 0) {
                    MyFrame.this.tabDataModel.setText(selIndex, textField.getText());
                    System.out.println("Modify HashCode : " + MyFrame.this.getTree(selIndex).hashCode() + " . TabTitle : " + textField.getText() + " !");
                }

            }
        });
        btnNewTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.addTab("NewTab", true);
            }
        });
        btnCloseTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selIndex = MyFrame.this.getTabIndex();
                if (selIndex >= 0) {
                    MyFrame.this.tabDataModel.removeTab(selIndex);
                }

            }
        });
        toolbar.add(btnNewTab);
        toolbar.add(btnCloseTab);
        toolbar.add(btnFormat);
        toolbar.add(btnClean);
        toolbar.add(btnParse);
        toolbar.add(btnNewLine);
        toolbar.add(btnXG);
        toolbar.add(btnNodeFind);
        toolbar.add(btnTxtFind);
        toolbar.addSeparator(new Dimension(30, 20));
        toolbar.add(textField);
        toolbar.add(btnAppTitle);
        toolbar.add(btnSelTabName);
        return toolbar;
    }

    private int getTabIndex() {
        return MyFrame.this.tabbedContainer.getSelectionModel().getSelectedIndex();
    }

    private JMenuItem createMenuItem(String name, int keyCode) {
        JMenuItem menuItem = new JMenuItem();
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyCode, 2));
        menuItem.setText("MenuItem-" + name);
        return menuItem;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        JMenu editMenu = new JMenu();
        JMenu toolMenu = new JMenu();
        JMenu helpMenu = new JMenu();
        menuBar.setName("menuBar");
        fileMenu.setText("文件");
        fileMenu.setName("fileMenu");
        JMenuItem menuItemOpenFile = MyFrame.this.createMenuItem("menuItemOpenFile", 79);
        menuItemOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.openFileAction(MyFrame.this.getTextArea());
            }
        });
        fileMenu.add(menuItemOpenFile);
        JMenuItem menuItemSaveFile = MyFrame.this.createMenuItem("menuItemSaveFile", 83);
        menuItemSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.saveFileAction(MyFrame.this.getTextArea());
            }
        });
        fileMenu.add(menuItemSaveFile);
        JMenuItem exitMenuItem = new JMenuItem();
        //exitMenuItem.setAction(actionMap.get("quit"));
        exitMenuItem.setName("exitMenuItem");
        exitMenuItem.setText("退出");
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        editMenu.setText("编辑");
        JMenuItem menuItemClean = MyFrame.this.createMenuItem("menuItemClean", 68);
        menuItemClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.getTextArea().setText("");
            }
        });
        editMenu.add(menuItemClean);
        JMenuItem menuItemFormat = MyFrame.this.createMenuItem("menuItemFormat", 70);
        menuItemFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.formatJson();
            }
        });
        editMenu.add(menuItemFormat);
        menuBar.add(editMenu);
        toolMenu.setText("工具栏");
        JMenuItem menuItemLayout = MyFrame.this.createMenuItem("menuItemLayout", 76);
        menuItemLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.changeLayout();
            }
        });
        toolMenu.add(menuItemLayout);
        JMenuItem menuItemNew = MyFrame.this.createMenuItem("menuItemNew", 78);
        menuItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.addTab("NewTab", true);
            }
        });
        toolMenu.add(menuItemNew);
        JMenuItem menuItemCode = MyFrame.this.createMenuItem("menuItemCode", 84);
        menuItemCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFrame.this.codeChangeAction();
            }
        });
        toolMenu.add(menuItemCode);
        menuBar.add(toolMenu);
        helpMenu.setText("帮助");
        helpMenu.setName("helpMenu");
        menuBar.add(helpMenu);
        return menuBar;
    }

    private void initTabbedContainer() {
        TabData tabData = MyFrame.this.newTabData("Welcome!", "This is a Tab!", null);
        MyFrame.this.tabDataModel = new DefaultTabDataModel(new TabData[]{tabData});
        MyFrame.this.tabbedContainer = new TabbedContainer(MyFrame.this.tabDataModel, 1);
        MyFrame.this.tabbedContainer.getSelectionModel().setSelectedIndex(0);
        MyFrame.this.tabbedContainer.setShowCloseButton(true);
        MyFrame.this.tabDataModel.addComplexListDataListener(new ComplexListDataListener() {
            @Override
            public void indicesAdded(ComplexListDataEvent clde) {
            }

            @Override
            public void indicesRemoved(ComplexListDataEvent clde) {
            }

            @Override
            public void indicesChanged(ComplexListDataEvent clde) {
            }

            @Override
            public void intervalAdded(ListDataEvent e) {
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                ComplexListDataEvent ce = (ComplexListDataEvent)e;
                TabData[] tbArr = ce.getAffectedItems();
                if (tbArr != null && tbArr.length > 0) {
                    tbArr[0].getText();
                    JTree tree = MyFrame.this.getTree(tbArr[0]);
                    if (tree != null) {
                        MyFrame.this.jsonEleTreeMap.remove(tree.hashCode());
                        System.out.println("Remove HashCode: " + tree.hashCode() + ". Close Tab: " + tbArr[0].getText() + " !");
                    }
                }

            }

            @Override
            public void contentsChanged(ListDataEvent e) {
            }
        });
        MyFrame.this.tabbedContainer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("select".equalsIgnoreCase(e.getActionCommand())) {
                    MyFrame.this.treePathLst.clear();
                }

            }
        });
    }

    private TabData newTabData(String tabName, String tabTip, Icon icon) {
        final JSplitPane splitPane = new JSplitPane();
        splitPane.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(0.45D);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        RSyntaxTextArea textArea = MyFrame.this.newTextArea();
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);
        splitPane.setLeftComponent(sp);
        final JSplitPane rightSplitPane = new JSplitPane();
        rightSplitPane.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = rightSplitPane.getWidth();
                if (w > 500) {
                    rightSplitPane.setDividerLocation((double)((float)(w - 220) / ((float)w * 1.0F)));
                } else {
                    rightSplitPane.setDividerLocation(0.8D);
                }

            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        JTree tree = MyFrame.this.newTree();
        rightSplitPane.setLeftComponent(new JScrollPane(tree));
        JTable table = MyFrame.this.newTable();
        rightSplitPane.setRightComponent(new JScrollPane(table));
        splitPane.setRightComponent(rightSplitPane);
        TabData tabData = new TabData(splitPane, icon, tabName, tabTip);
        return tabData;
    }

    private RSyntaxTextArea newTextArea() {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle("text/javascript");
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setAutoscrolls(true);
        SyntaxScheme scheme = textArea.getSyntaxScheme();
        scheme.getStyle(13).foreground = Color.BLUE;
        scheme.getStyle(10).foreground = new Color(164, 0, 0);
        scheme.getStyle(11).foreground = new Color(164, 0, 0);
        scheme.getStyle(9).foreground = Color.RED;
        scheme.getStyle(23).foreground = Color.BLACK;
        textArea.revalidate();
        textArea.addMouseListener(new MyFrame.TextAreaMouseListener());
        return textArea;
    }

    private JTree newTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("o-JSON");
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree tree = new JTree(model);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                MyFrame.this.treeSelection(MyFrame.this.getTree(), MyFrame.this.getTable());
            }
        });
        MyFrame.this.setNodeIcon(tree);
        tree.addMouseListener(new MyFrame.TreeMouseListener(tree));
        System.out.println("New HashCode : " + tree.hashCode());
        return tree;
    }

    private JTable newTable() {
        String[] col = new String[]{"key", "value"};
        DefaultTableModel tm = new DefaultTableModel();
        tm.setColumnCount(2);
        tm.setColumnIdentifiers(col);
        JTable table = new JTable(tm);
        table.setAutoResizeMode(0);
        table.setAutoscrolls(true);
        table.setMinimumSize(new Dimension(160, 100));
        return table;
    }

    private void treeSelection(JTree tree, JTable table) {
        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        if (selNode != null) {
            String[] col = new String[]{"key", "value"};
            DefaultTableModel tm = (DefaultTableModel)table.getModel();
            tm.setColumnCount(2);
            tm.setColumnIdentifiers(col);
            if (selNode.isLeaf()) {
                tm.setRowCount(1);
                String[] arr = Kit.pstr(selNode.toString());
                tm.setValueAt(arr[1], 0, 0);
                tm.setValueAt(arr[2], 0, 1);
            } else {
                int childCount = selNode.getChildCount();
                tm.setRowCount(childCount);

                for(int i = 0; i < childCount; ++i) {
                    String[] arr = Kit.pstr(selNode.getChildAt(i).toString());
                    tm.setValueAt(arr[1], i, 0);
                    tm.setValueAt(arr[2], i, 1);
                }
            }

            table.setModel(tm);
            TableColumn column0 = table.getColumnModel().getColumn(0);
            column0.setPreferredWidth(MyFrame.this.getPreferredWidthForColumn(table, column0));
            TableColumn column1 = table.getColumnModel().getColumn(1);
            column1.setPreferredWidth(MyFrame.this.getPreferredWidthForColumn(table, column1));
            table.updateUI();
        }
    }

    private int addTab(String tabName, boolean isSel) {
        TabData tabData = MyFrame.this.newTabData(tabName, tabName, null);
        int newIndex = MyFrame.this.tabbedContainer.getTabCount();
        MyFrame.this.tabDataModel.addTab(newIndex, tabData);
        if (isSel) {
            MyFrame.this.tabbedContainer.getSelectionModel().setSelectedIndex(newIndex);
        }

        return newIndex;
    }

    private JTextArea getTextArea() {
        int selIndex = MyFrame.this.getTabIndex();
        if (selIndex >= 0) {
            TabData selTabData = MyFrame.this.tabDataModel.getTab(selIndex);
            JSplitPane selSplitPane = (JSplitPane)selTabData.getComponent();
            JScrollPane sp = (JScrollPane)selSplitPane.getLeftComponent();
            JViewport vp = (JViewport)sp.getComponent(0);
            JTextArea ta = (JTextArea)vp.getComponent(0);
            return ta;
        } else {
            return null;
        }
    }

    private JTree getTree(TabData tabData) {
        if (tabData == null) {
            return null;
        } else {
            JSplitPane selSplitPane = (JSplitPane)tabData.getComponent();
            JSplitPane rightSplitPane = (JSplitPane)selSplitPane.getRightComponent();
            JScrollPane sp = (JScrollPane)rightSplitPane.getLeftComponent();
            JViewport vp = (JViewport)sp.getComponent(0);
            JTree t = (JTree)vp.getComponent(0);
            return t;
        }
    }

    private JTree getTree(int tabIndex) {
        if (tabIndex >= 0) {
            TabData selTabData = MyFrame.this.tabDataModel.getTab(tabIndex);
            return MyFrame.this.getTree(selTabData);
        } else {
            return null;
        }
    }

    private JTree getTree() {
        return MyFrame.this.getTree(MyFrame.this.getTabIndex());
    }

    private JTable getTable(int tabIndex) {
        if (tabIndex >= 0) {
            TabData selTabData = MyFrame.this.tabDataModel.getTab(tabIndex);
            JSplitPane selSplitPane = (JSplitPane)selTabData.getComponent();
            JSplitPane rightSplitPane = (JSplitPane)selSplitPane.getRightComponent();
            JScrollPane sp = (JScrollPane)rightSplitPane.getRightComponent();
            JViewport vp = (JViewport)sp.getComponent(0);
            JTable t = (JTable)vp.getComponent(0);
            return t;
        } else {
            return null;
        }
    }

    private JTable getTable() {
        return MyFrame.this.getTable(MyFrame.this.getTabIndex());
    }

    // TODO 重要方法
    private void formatJson() {
        JsonElement jsonEle = null;
        JTextArea ta = MyFrame.this.getTextArea();
        String text = ta.getText();

        try {
            JsonParser parser = new JsonParser();
            jsonEle = parser.parse(text);
            if (jsonEle != null && !jsonEle.isJsonNull()) {
                GsonBuilder gb = new GsonBuilder();
                gb.setPrettyPrinting();
                gb.serializeNulls();
                Gson gson = gb.create();
                String jsonStr = gson.toJson(jsonEle);
                if (jsonStr != null) {
                    jsonStr = StringEscapeUtils.unescapeJava(jsonStr);
                    ta.setText(jsonStr);
                }
            } else {
                MyFrame.this.showMessageDialog("非法JSON字符串！", "是否缺少开始“{”或结束“}”？");
            }
        } catch (Exception var10) {
            MyFrame.this.showMessageDialog("非法JSON字符串！", var10.getMessage());
            return;
        }

        System.gc();
        JTree tree = MyFrame.this.getTree();
        System.out.println("Put HashCode : " + tree.hashCode() + " . TabTitle : " + MyFrame.this.getTabTitle() + " !");
        MyFrame.this.jsonEleTreeMap.put(tree.hashCode(), jsonEle);
        DefaultMutableTreeNode root = Kit.objNode("JSON");
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();

        try {
            MyFrame.this.createJsonTree(jsonEle, root);
            model.setRoot(root);
            MyFrame.this.setNodeIcon(tree);
        } catch (Exception var9) {
            root.removeAllChildren();
            model.setRoot(root);
            MyFrame.this.showMessageDialog("创建json树失败！", var9.getMessage());
        }

        System.gc();
    }

    private String getTabTitle() {
        return MyFrame.this.tabDataModel.getTab(MyFrame.this.getTabIndex()).getText();
    }

    private void createJsonTree(JsonElement obj, DefaultMutableTreeNode pNode) {
        if (obj.isJsonNull()) {
            pNode.add(Kit.nullNode("NULL"));
        } else if (obj.isJsonArray()) {
            MyFrame.this.createJsonArray(obj.getAsJsonArray(), pNode, "[0]");
        } else if (obj.isJsonObject()) {
            JsonObject child = obj.getAsJsonObject();
            MyFrame.this.createJsonObject(child, pNode);
        } else if (obj.isJsonPrimitive()) {
            JsonPrimitive pri = obj.getAsJsonPrimitive();
            MyFrame.this.formatJsonPrimitive("PRI", pri, pNode);
        }

    }

    private void createJsonArray(JsonArray arr, DefaultMutableTreeNode pNode, String key) {
        int index = 0;
        DefaultMutableTreeNode child = Kit.arrNode(key);

        for(Iterator it = arr.iterator(); it.hasNext(); ++index) {
            JsonElement el = (JsonElement)it.next();
            if (el.isJsonObject()) {
                JsonObject obj = el.getAsJsonObject();
                DefaultMutableTreeNode node = Kit.objNode(index);
                MyFrame.this.createJsonObject(obj, node);
                child.add(node);
            } else if (el.isJsonArray()) {
                JsonArray lst = el.getAsJsonArray();
                MyFrame.this.createJsonArray(lst, child, Kit.fkey(index));
            } else if (el.isJsonNull()) {
                child.add(Kit.nullNode(index));
            } else if (el.isJsonPrimitive()) {
                MyFrame.this.formatJsonPrimitive(Kit.fkey(index), el.getAsJsonPrimitive(), child);
            }
        }

        pNode.add(child);
    }

    private void createJsonObject(JsonObject obj, DefaultMutableTreeNode pNode) {
        Iterator i$ = obj.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<String, JsonElement> el = (Map.Entry)i$.next();
            String key = el.getKey();
            JsonElement val = el.getValue();
            if (val.isJsonNull()) {
                pNode.add(Kit.nullNode(key));
            } else if (val.isJsonArray()) {
                MyFrame.this.createJsonArray(val.getAsJsonArray(), pNode, key);
            } else if (val.isJsonObject()) {
                JsonObject child = val.getAsJsonObject();
                DefaultMutableTreeNode node = Kit.objNode(key);
                MyFrame.this.createJsonObject(child, node);
                pNode.add(node);
            } else if (val.isJsonPrimitive()) {
                JsonPrimitive pri = val.getAsJsonPrimitive();
                MyFrame.this.formatJsonPrimitive(key, pri, pNode);
            }
        }

    }

    private void formatJsonPrimitive(String key, JsonPrimitive pri, DefaultMutableTreeNode pNode) {
        if (pri.isJsonNull()) {
            pNode.add(Kit.nullNode(key));
        } else if (pri.isNumber()) {
            pNode.add(Kit.numNode(key, pri.getAsString()));
        } else if (pri.isBoolean()) {
            pNode.add(Kit.boolNode(key, pri.getAsBoolean()));
        } else if (pri.isString()) {
            pNode.add(Kit.strNode(key, pri.getAsString()));
        } else if (pri.isJsonArray()) {
            MyFrame.this.createJsonArray(pri.getAsJsonArray(), pNode, key);
        } else if (pri.isJsonObject()) {
            JsonObject child = pri.getAsJsonObject();
            DefaultMutableTreeNode node = Kit.objNode(key);
            MyFrame.this.createJsonObject(child, node);
            pNode.add(node);
        } else if (pri.isJsonPrimitive()) {
            MyFrame.this.formatJsonPrimitive(key, pri.getAsJsonPrimitive(), pNode);
        }

    }

    private void setNodeIcon(JTree tree) {
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                String tmp = node.toString();
                if (tmp.startsWith("a-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/a.gif")));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith("v-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/v.gif")));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith("o-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/o.gif")));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith("n-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/n.gif")));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith("k-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/k.gif")));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith("b-")) {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/v.gif")));
                    this.setText(tmp.substring(2));
                } else {
                    this.setIcon(new ImageIcon(MyFrame.this.getClass().getResource("resources/v.gif")));
                    this.setText(tmp.substring(2));
                }

                return this;
            }
        });
    }

    private void showMessageDialog(String title, String msg) {
        if (msg == null) {
            msg = "";
        }

        String ex = "com.google.gson.stream.MalformedJsonException:";
        int index = msg.indexOf(ex);
        if (index >= 0) {
            msg = msg.substring(index + ex.length());
        }

        JDialog dlg = new JDialog(this);
        dlg.setTitle(title);
        dlg.setMinimumSize(new Dimension(350, 160));
        BorderLayout layout = new BorderLayout();
        dlg.getContentPane().setLayout(layout);
        dlg.getContentPane().add(new JLabel("异常信息："), "North");
        JTextArea ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setText(msg);
        ta.setWrapStyleWord(true);
        dlg.getContentPane().add(new JScrollPane(ta), "Center");
        dlg.setVisible(true);
    }

    private int getPreferredWidthForColumn(JTable table, TableColumn col) {
        int hw = MyFrame.this.columnHeaderWidth(table, col);
        int cw = MyFrame.this.widestCellInColumn(table, col);
        return hw > cw ? hw : cw;
    }

    private int columnHeaderWidth(JTable table, TableColumn col) {
        TableCellRenderer renderer = table.getTableHeader().getDefaultRenderer();
        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
        return comp.getPreferredSize().width;
    }

    private int widestCellInColumn(JTable table, TableColumn col) {
        int c = col.getModelIndex();
        int width = 0;
        int maxw = 0;

        for(int r = 0; r < table.getRowCount(); ++r) {
            TableCellRenderer renderer = table.getCellRenderer(r, c);
            Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, c), false, false, r, c);
            width = comp.getPreferredSize().width;
            maxw = width > maxw ? width : maxw;
        }

        if (maxw < 90) {
            maxw = 90;
        }

        return maxw + 10;
    }

    private void modifyDialgTitle(JDialog dlg, boolean flag, int n) {
        String[] tmp = dlg.getTitle().split("-");
        if (n == -1) {
            dlg.setTitle(tmp[0] + "-" + "  ==");
        } else {
            if (flag) {
                dlg.setTitle(tmp[0] + "-" + "  找到了^_^");
            } else {
                dlg.setTitle(tmp[0] + "-" + "  没找到╮(╯_╰)╭");
            }

        }
    }

    private TreePath expandTreeNode(JTree tree, TreeNode[] arr, Boolean expand) {
        TreePath[] tp = new TreePath[arr.length];
        tp[0] = new TreePath(arr[0]);
        int pos = 0;

        int i;
        for(i = 1; i < arr.length; ++i) {
            tp[i] = tp[i - 1].pathByAddingChild(arr[i]);
        }

        for(i = 0; i < arr.length; pos = i++) {
            if (expand) {
                tree.expandPath(tp[i]);
            } else {
                tree.collapsePath(tp[i]);
            }
        }

        return tp[pos];
    }

    private void findTreeChildValue(String findText, List<TreePath> treePathLst) {
        JTree tree = MyFrame.this.getTree();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
        Enumeration e = root.depthFirstEnumeration();
        treePathLst.clear();
        MyFrame.this.curPos = 0;

        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
            if (node.isLeaf()) {
                String str = node.toString();
                if (str.substring(2).indexOf(findText) >= 0) {
                    tree.expandPath(new TreePath(node.getPath()));
                    TreePath tp = MyFrame.this.expandTreeNode(tree, node.getPath(), true);
                    treePathLst.add(tp);
                }
            }
        }

        if (!treePathLst.isEmpty()) {
            tree.setSelectionPath(treePathLst.get(0));
            tree.scrollPathToVisible(treePathLst.get(0));
        }

    }

    private void showFindDialog(final int type, String title) {
        final JDialog openDlg = new JDialog(this);
        openDlg.setTitle(title);
        openDlg.setModal(false);
        openDlg.setSize(500, 70);
        openDlg.setResizable(false);
        Container pane = openDlg.getContentPane();
        FlowLayout layout = new FlowLayout(0);
        pane.setLayout(layout);
        JButton btnFind = new JButton("查找");
        JButton btnNext = new JButton("下一个");
        JButton btnPrev = new JButton("上一个");
        final JTextField textFieldFind = new JTextField(50);
        pane.add(textFieldFind);
        pane.add(btnFind);
        pane.add(btnPrev);
        pane.add(btnNext);
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                MyFrame.this.modifyDialgTitle(openDlg, flag, -1);
                if (type == 1) {
                    flag = MyFrame.this.startSegmentFindOrReplaceOperation(MyFrame.this.getTextArea(), textFieldFind.getText(), true, true, true);
                } else {
                    MyFrame.this.findTreeChildValue(textFieldFind.getText(), MyFrame.this.treePathLst);
                    if (!MyFrame.this.treePathLst.isEmpty()) {
                        flag = true;
                    }
                }

                MyFrame.this.modifyDialgTitle(openDlg, flag, 1);
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                MyFrame.this.modifyDialgTitle(openDlg, flag, -1);
                JTree tree = MyFrame.this.getTree();
                if (type == 1) {
                    flag = MyFrame.this.startSegmentFindOrReplaceOperation(MyFrame.this.getTextArea(), textFieldFind.getText(), true, true, false);
                } else {
                    MyFrame.this.curPos++;
                    if (MyFrame.this.curPos < MyFrame.this.treePathLst.size()) {
                        tree.setSelectionPath(MyFrame.this.treePathLst.get(MyFrame.this.curPos));
                        tree.scrollPathToVisible(MyFrame.this.treePathLst.get(MyFrame.this.curPos));
                        flag = true;
                    } else {
                        MyFrame.this.curPos = MyFrame.this.treePathLst.size() - 1;
                    }
                }

                MyFrame.this.modifyDialgTitle(openDlg, flag, 1);
            }
        });
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                JTree tree = MyFrame.this.getTree();
                MyFrame.this.modifyDialgTitle(openDlg, flag, -1);
                if (type == 1) {
                    flag = MyFrame.this.startSegmentFindOrReplaceOperation(MyFrame.this.getTextArea(), textFieldFind.getText(), true, false, false);
                } else {
                    MyFrame.this.curPos--;
                    if (MyFrame.this.curPos >= 0) {
                        tree.setSelectionPath(MyFrame.this.treePathLst.get(MyFrame.this.curPos));
                        tree.scrollPathToVisible(MyFrame.this.treePathLst.get(MyFrame.this.curPos));
                        flag = true;
                    } else {
                        MyFrame.this.curPos = 0;
                    }
                }

                MyFrame.this.modifyDialgTitle(openDlg, flag, 1);
            }
        });
        openDlg.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                MyFrame.this.treePathLst.clear();
                if (type == 1) {
                    MyFrame.this.isTxtFindDlgOpen = false;
                } else {
                    MyFrame.this.isTreeFinDlgdOpen = false;
                }

                System.gc();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        openDlg.setVisible(true);
        if (type == 1) {
            MyFrame.this.isTxtFindDlgOpen = true;
        } else {
            MyFrame.this.isTreeFinDlgdOpen = true;
        }

    }

    public boolean startSegmentFindOrReplaceOperation(JTextArea textArea, String key, boolean ignoreCase, boolean down, boolean isFirst) {
        int length = key.length();
        Document doc = textArea.getDocument();
        int offset = textArea.getCaretPosition();
        int charsLeft = doc.getLength() - offset;
        if (charsLeft <= 0) {
            offset = 0;
            charsLeft = doc.getLength() - offset;
        }

        if (!down) {
            offset -= length;
            --offset;
            charsLeft = offset;
        }

        if (isFirst) {
            offset = 0;
            charsLeft = doc.getLength() - offset;
        }

        Segment text = new Segment();
        text.setPartialReturn(true);

        while(true) {
            try {
                if (charsLeft > 0) {
                    doc.getText(offset, length, text);
                    if (ignoreCase && text.toString().equalsIgnoreCase(key) || !ignoreCase && text.toString().equals(key)) {
                        textArea.requestFocus();
                        textArea.setSelectionStart(offset);
                        textArea.setSelectionEnd(offset + length);
                        return true;
                    }

                    --charsLeft;
                    if (down) {
                        ++offset;
                    } else {
                        --offset;
                    }
                    continue;
                }
            } catch (Exception var12) {
            }

            return false;
        }
    }

    private void changeLayout() {
        int selIndex = MyFrame.this.getTabIndex();
        if (selIndex >= 0) {
            TabData selTabData = MyFrame.this.tabDataModel.getTab(selIndex);
            JSplitPane splitPane = (JSplitPane)selTabData.getComponent();
            if (splitPane.getOrientation() == 0) {
                splitPane.setOrientation(1);
                splitPane.setDividerLocation(0.45D);
            } else {
                splitPane.setOrientation(0);
                splitPane.setDividerLocation(0.45D);
            }

        }
    }

    private void openFileAction(JTextArea textArea) {
        String title = "打开";
        FileDialog openDlg = new FileDialog(this, title, 0);
        openDlg.setVisible(true);
        File file = new File(openDlg.getDirectory(), openDlg.getFile());
        if (file != null && file.getPath().length() != 0) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();

            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
                reader = new BufferedReader(isr);
                String temp = null;

                while((temp = reader.readLine()) != null) {
                    sb.append(temp);
                }

                reader.close();
            } catch (IOException var17) {
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException var16) {
                    }
                }

            }

            textArea.setText(sb.toString());
            MyFrame.this.formatJson();
        }
    }

    private void codeChangeAction() {
        JDialog dlg = new JDialog(this);
        dlg.setTitle("子菜单");
        dlg.setSize(500, 350);
        dlg.setMinimumSize(new Dimension(500, 350));
        JSplitPane spiltPane2 = new JSplitPane();
        spiltPane2.setDividerLocation(150);
        spiltPane2.setOrientation(0);
        final JTextArea textAreaSrc = new JTextArea();
        final JTextArea textAreaDest = new JTextArea();
        textAreaSrc.setLineWrap(true);
        textAreaDest.setLineWrap(true);
        spiltPane2.setTopComponent(new JScrollPane(textAreaSrc));
        spiltPane2.setBottomComponent(new JScrollPane(textAreaDest));
        JButton btnOK = new JButton("转换");
        btnOK.setSize(50, 25);
        Container pane = dlg.getContentPane();
        BorderLayout layout = new BorderLayout();
        pane.setLayout(layout);
        pane.add(spiltPane2, "Center");
        pane.add(btnOK, "South");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = textAreaSrc.getText();
                str = StringEscapeUtils.unescapeJava(str);
                textAreaDest.setText(str);
            }
        });
        dlg.setVisible(true);
    }

    private void saveFileAction(JTextArea textArea) {
        String title = "关闭";
        FileDialog closeDlg = new FileDialog(this, title, 1);
        closeDlg.setVisible(true);
        File file = new File(closeDlg.getDirectory(), closeDlg.getFile());
        if (file != null && file.getPath().length() != 0) {
            BufferedWriter write = null;
            new StringBuilder();

            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "GBK");
                write = new BufferedWriter(osw);
                String text = StringUtils.replace(textArea.getText(), "\n", "\r\n");
                write.write(text, 0, text.length());
                write.close();
            } catch (IOException var17) {
            } finally {
                if (write != null) {
                    try {
                        write.close();
                    } catch (IOException var16) {
                    }
                }

            }

        }
    }

    private class TextAreaMenuItemActionListener implements ActionListener {
        private int optType;
        private String str;

        public TextAreaMenuItemActionListener(int optType) {
            this.optType = optType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.optType == 1) {
                MyFrame.this.getTextArea().copy();
            } else if (this.optType == 2) {
                MyFrame.this.getTextArea().paste();
                MyFrame.this.formatJson();
            } else if (this.optType == 3) {
                MyFrame.this.getTextArea().selectAll();
            } else if (this.optType == 4) {
                MyFrame.this.getTextArea().setText("");
            }

        }
    }

    private class TextAreaMouseListener implements MouseListener {
        private TextAreaMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JPopupMenu popMenu = new JPopupMenu();
                JMenuItem mtCopy = new JMenuItem("复制");
                JMenuItem mtPaste = new JMenuItem("粘贴");
                JMenuItem mtSelAll = new JMenuItem("全选");
                JMenuItem mtClean = new JMenuItem("清空");
                popMenu.add(mtCopy);
                popMenu.add(mtPaste);
                popMenu.add(mtSelAll);
                popMenu.add(mtClean);
                JTextArea ta = MyFrame.this.getTextArea();
                if (ta.getSelectedText() == null || ta.getSelectedText().length() == 0) {
                    mtCopy.setEnabled(false);
                }

                mtCopy.addActionListener(MyFrame.this.new TextAreaMenuItemActionListener(1));
                mtPaste.addActionListener(MyFrame.this.new TextAreaMenuItemActionListener(2));
                mtSelAll.addActionListener(MyFrame.this.new TextAreaMenuItemActionListener(3));
                mtClean.addActionListener(MyFrame.this.new TextAreaMenuItemActionListener(4));
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private class TreeNodeMenuItemActionListener implements ActionListener {
        private int optType;
        private Object obj;
        private JTree tree;

        public TreeNodeMenuItemActionListener(JTree tree, int optType, Object obj) {
            this.optType = optType;
            this.obj = obj;
            this.tree = tree;
        }

        public String copyTreeNodePath(TreePath treePath) {
            String str = "";
            String s = "";
            int len = treePath.getPathCount() - 1;

            for(int i = 0; i <= len; ++i) {
                s = treePath.getPathComponent(i).toString();
                if (i > 0) {
                    str = str + String.valueOf(MyFrame.this.dot);
                }

                if (i == len) {
                    str = str + Kit.pstr(s)[1];
                } else {
                    str = str + s.substring(2);
                }
            }

            str = StringUtils.replace(str, MyFrame.this.dot + "[", "[");
            str = StringUtils.substring(str, 5);
            return str;
        }

        public String copySimilarPathKeyValue(TreeNode treeNode) {
            String str = "";
            String key = Kit.pstr(treeNode.toString())[1];
            TreeNode node = treeNode.getParent();
            if (node == null) {
                return "";
            } else {
                node = node.getParent();
                if (node == null) {
                    return "";
                } else {
                    int count = node.getChildCount();


                    for(int i = 0; i < count; ++i) {
                        TreeNode child = node.getChildAt(i);
                        if (child != null) {
                            int size = child.getChildCount();

                            for(int i2 = 0; i2 < size; ++i2) {
                                TreeNode tmp = child.getChildAt(i2);
                                if (tmp != null) {
                                    String[] arr = Kit.pstr(tmp.toString());
                                    if (key != null && key.equals(arr[1])) {
                                        str = str + arr[2] + "\n";
                                    }
                                }
                            }
                        }
                    }

                    return str;
                }
            }
        }

        private String copyNodeContent(String path, boolean isFormat) {
            String str = "";
            String[] arr = StringUtils.split(path, String.valueOf(MyFrame.this.dot));
            System.out.println("Get HashCode : " + this.tree.hashCode() + " . TabTitle : " + MyFrame.this.getTabTitle());
            JsonElement obj = (JsonElement)MyFrame.this.jsonEleTreeMap.get(this.tree.hashCode());
            if (arr.length > 1) {
                for(int i = 1; i < arr.length; ++i) {
                    int index = Kit.getIndex(arr[i]);
                    String key = Kit.getKey(arr[i]);
                    if (obj.isJsonPrimitive()) {
                        break;
                    }

                    if (index == -1) {
                        obj = obj.getAsJsonObject().get(key);
                    } else {
                        obj = obj.getAsJsonObject().getAsJsonArray(key).get(index);
                    }
                }
            }

            if (obj != null && !obj.isJsonNull()) {
                GsonBuilder gb = new GsonBuilder();
                if (isFormat) {
                    gb.setPrettyPrinting();
                }

                gb.serializeNulls();
                Gson gson = gb.create();
                str = gson.toJson(obj);
            }

            return str;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.obj != null) {
                StringSelection stringSelection = null;
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String str;
                if (this.optType == 4) {
                    str = this.copyTreeNodePath((TreePath)this.obj);
                    str = StringUtils.replace(str, String.valueOf(MyFrame.this.dot), ".");
                    stringSelection = new StringSelection(str);
                    clipboard.setContents(stringSelection, null);
                } else if (this.optType == 5) {
                    stringSelection = new StringSelection(this.copySimilarPathKeyValue((TreeNode)this.obj));
                    clipboard.setContents(stringSelection, null);
                } else {
                    String temp;
                    if (this.optType != 6 && this.optType != 7) {
                        str = this.obj.toString();
                        String[] arr = Kit.pstr(str);
                        if ("<null>".equals(arr[2])) {
                            arr[2] = "null";
                        }

                        if (this.optType != 1 && this.optType != 2) {
                            if (this.optType == 3) {
                                stringSelection = new StringSelection(str.substring(2));
                            } else if (this.optType == 8) {
                                temp = "\"" + arr[1] + "\",\"" + arr[2] + "\"";
                                stringSelection = new StringSelection(temp);
                            }
                        } else {
                            stringSelection = new StringSelection(arr[this.optType]);
                        }

                        clipboard.setContents(stringSelection, null);
                    } else {
                        str = this.copyTreeNodePath((TreePath)this.obj);
                        boolean isForamt = false;
                        if (this.optType == 7) {
                            isForamt = true;
                        }

                        temp = this.copyNodeContent(str, isForamt);
                        stringSelection = new StringSelection(temp);
                        clipboard.setContents(stringSelection, null);
                    }
                }
            }
        }
    }

    private class TreeMouseListener implements MouseListener {
        private JTree tree;

        public TreeMouseListener(JTree tree) {
            this.tree = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            TreePath path = this.tree.getPathForLocation(e.getX(), e.getY());
            if (path != null) {
                this.tree.setSelectionPath(path);
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode)this.tree.getLastSelectedPathComponent();
                if (e.isPopupTrigger()) {
                    JPopupMenu popMenu = new JPopupMenu();
                    JMenuItem copyValue = new JMenuItem("复制 键值");
                    JMenuItem copyKey = new JMenuItem("复制 键名");
                    JMenuItem copyPath = new JMenuItem("复制 路径");
                    JMenuItem copyKeyValue = new JMenuItem("复制 键名键值");
                    JMenuItem copyNode = new JMenuItem("复制 节点内容");
                    JMenuItem copyPathAllVal = new JMenuItem("复制 同路径键值");
                    JMenuItem copySingleNodeString = new JMenuItem("复制 MAP式内容");
                    JMenuItem copyNodeFormat = new JMenuItem("复制 节点内容带格式");
                    popMenu.add(copyKey);
                    popMenu.add(copyValue);
                    popMenu.add(copyPath);
                    popMenu.add(copyNode);
                    popMenu.add(copyKeyValue);
                    popMenu.add(copySingleNodeString);
                    popMenu.add(copyPathAllVal);
                    popMenu.add(copyNodeFormat);
                    copyKey.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 1, selNode));
                    copyValue.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 2, selNode));
                    copyKeyValue.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 3, selNode));
                    copyPath.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 4, path));
                    copyPathAllVal.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 5, selNode));
                    copyNode.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 6, path));
                    copyNodeFormat.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 7, path));
                    copySingleNodeString.addActionListener(MyFrame.this.new TreeNodeMenuItemActionListener(this.tree, 8, selNode));
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        myFrame.initUI();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
}
