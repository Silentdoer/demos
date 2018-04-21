package me.silentdoer.simulatemybatis.pojo;

/**
 * @Author Silentdoer
 * @Since 1.0
 * @Version 1.0
 * @Date 2018-2-19 19:16
 */
public class Student {
    private Long uid;
    private String no;
    private String name;
    private String gender;

    public Student(){
        this.uid = -1L;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    @Override
    public String toString(){
        return String.format("Student [uid=%s, no=%s, name=%s, gender=%s]", this.uid, this.no, this.name, this.gender);
    }
}
