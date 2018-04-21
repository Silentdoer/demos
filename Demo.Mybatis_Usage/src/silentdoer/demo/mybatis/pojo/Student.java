package silentdoer.demo.mybatis.pojo;

import java.io.Serializable;

/**
 *
 * 和数据库的student表对应，将该表记录映射成Student对象，或将Student对象映射成
 * 表student的一条记录
 *
 * @author Silentdoer
 * @since 2018-1-8
 * @version 1.0
 */
public class Student implements Serializable {
    private Long uid = -1L;
    private String no;
    private String name;
    private String gender;

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
