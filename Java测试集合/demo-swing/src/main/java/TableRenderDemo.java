/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * TableRenderDemo.java requires no other files.
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * TableRenderDemo is just like TableDemo, except that it
 * explicitly initializes column sizes and it uses a combo box
 * as an editor for the Sport column.
 */
public class TableRenderDemo {

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // GridLayout(rows, cols)如果是0表示可以为任意行/列（不限制）
        JPanel newContentPane = new JPanel(new GridLayout(1, 1));
        // MyTableModel的构造函数没有什么特别的，主要是它实现的那几个接口api
        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        TableRenderDemo.initColumnSizes(table);

        //Fiddle with the Sport column's cell editors/renderers.
        TableRenderDemo.setUpSportColumn(table, table.getColumnModel().getColumn(2));

        //Add the scroll pane to this panel.
        newContentPane.add(scrollPane);
        // 设置为不透明的
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(TableRenderDemo::createAndShowGUI);
    }

    /**
     * 设置列宽度
     */
    public static void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel) table.getModel();
        TableColumn column;

        for (int i = 0; i < model.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);

            /*comp = table.getDefaultRenderer(model.getColumnClass(i)).
                    getTableCellRendererComponent(
                            table, longValues[i],
                            false, false, 0, i);
            // 获得最佳的宽度
            cellWidth = comp.getPreferredSize().width;*/

            // 设置列宽度
            column.setPreferredWidth(10);
        }
    }

    /**
     * 设置那些有下拉框的列，这里只设置第三列修改时可以下拉
     */
    public static void setUpSportColumn(JTable table, TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem("Snowboarding");
        comboBox.addItem("Rowing");
        comboBox.addItem("Knitting");
        comboBox.addItem("Speed reading");
        comboBox.addItem("Pool");
        comboBox.addItem("None of the above");
        // 重要步骤
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public static class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

        private Object[][] data = {
                {"Kathy", "Smith", "Snowboarding", 5, false},

                {"John", "Doe", "Rowing", 3, true},

                {"Sue", "Black", "Knitting", 2, false},

                {"Jane", "White", "Speed reading", 20, true},

                {"Joe", "Brown", "Pool", 10, false}
        };

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        /**
         * 这里要求某列的每一行的数据类型都是一样的，因此这里取了第一行的该列的数据来获得其Class对象
         */
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /**
         * 设定单元格是否可以修改
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            if (col < this.getColumnCount() / 2) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /**
         * 设置单元的值并更新视图
         */
        @Override
        public void setValueAt(Object value, int row, int col) {

            data[row][col] = value;
            // 通知更新视图，原理大概是视图对象实现了相关的监听器接口，然后通知函数里会更新视图，所以这里通知监听器时会自动更新视图
            // 而普通的写法则是，这里调用视图对象的更新方法
            super.fireTableCellUpdated(row, col);
        }
    }
}
