package me.silentdoer.mybatispluginqaq.model;

import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasObject;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.util.containers.JBIterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/30/2018 5:23 PM
 */
public class TableInfo {

    private final DbTable tableMetaInfo;

    private final String tableName;

    private List<DasColumn> columns;

    private List<String> primaryKeysName = new LinkedList<>();

    public TableInfo(DbTable table) {
        this.tableMetaInfo = table;
        this.tableName = table.getName();

        this.columns = new ArrayList<>(DasUtil.getColumns(table).toList());
        for (DasColumn dasColumn : this.columns) {
            if (DasUtil.isPrimary(dasColumn)) {
                primaryKeysName.add(dasColumn.getName());
            }
        }
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<String> getPrimaryKeysName() {
        // 防止修改元数据
        return Collections.unmodifiableList(this.primaryKeysName);
    }

    public List<String> getColumnsName() {
        return Collections.unmodifiableList(this.columns.stream().map(DasObject::getName).collect(Collectors.toList()));
    }

    public List<DasColumn> getColumns() {
        return Collections.unmodifiableList(this.columns);
    }
}
