package me.silentdoer.ssmdemo.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 2:11 PM
 */
public class Student implements Serializable {
    private long uid;
    private String name;
    private int gender;
    private Timestamp createTime;  // Timestamp is implements java.util.Date
    private Timestamp updateTime;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString(){
        return String.format("[uid=%s, name=%s, gender=%s, createTime=%s, updateTime=%s]", this.uid, this.name, this.gender, this.createTime, this.updateTime);
    }
}
