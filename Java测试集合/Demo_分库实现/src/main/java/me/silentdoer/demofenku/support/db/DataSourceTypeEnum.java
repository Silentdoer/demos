package me.silentdoer.demofenku.support.db;

public enum DataSourceTypeEnum {

    WRITE("WRITE", "Master主库"),

    READ("READ", "Slave从库"),

    NULL_DATA_SOURCE("NULL_DATA_SOURCE", "不可访问库，类似null的概念");

    private String type;

    private String description;

    DataSourceTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}