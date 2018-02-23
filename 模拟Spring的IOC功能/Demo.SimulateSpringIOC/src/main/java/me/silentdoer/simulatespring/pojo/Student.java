package me.silentdoer.simulatespring.pojo;

import java.io.Serializable;

public class Student implements Serializable {
    private Long uid;
    private String name;
    private String gender;

    public Student(){
        this.name = "silentdoer";
    }

    public Student(Long uid){
        this.uid = uid;
    }

    public Student(Long uid, String gender){
        this.uid = uid;
        this.gender = gender;
    }

    @Override
    public String toString(){
        return String.format("Student-[uid=%s, name=%s, gender=%s]", this.uid, this.name, this.gender);
    }

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
