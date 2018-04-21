package silentdoer.demo.spring.pojo;

import java.io.Serializable;

public class Student implements Serializable {
    private long uid;
    private String name;
    private String gender;

    public Student(){
        this.name = "silentdoer";
    }

    public Student(long uid){
        this.uid = uid;
    }

    public Student(long uid, String gender){
        this.uid = uid;
        this.gender = gender;
    }

    /*public Student(String gender, long uid){
        this.name = gender;
        this.uid = uid;
    }*/

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
