package me.silentdoer.springbootdemo01.pojo;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 11:48 AM
 */
public class Student {
    private Long uid;
    private String name;
    private String gender;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
