package me.silentdoer.springbootvalidannotation.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO 经过测试，@Data的对象不能被Jackson由JSON字符串反序列化，但是手动写getter和setter就可以，问下
 * // TODO 经过测试，序列化测试是可以的，即返回MockModel能够被自动序列化成JSON字符串；但是有问题，序列化的字符串属性全变成了小写如fuid/fname而非fUid
 * @author liqi.wang
 * @version 1.0
 */
// TODO 经过测试@Data并不会附加有@AllArgsConstructor注解，只有默认构造方法，但是添加了@AllArgsConstructor默认构造方法会消失，因此又需要再添加@NoArgsConstructor
    // TODO Fastjson支持下面的所有注解
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MockModel {
    @JSONField(name="f_uid")
    private String fUid;
    private String fName;
    private String fGender;

    /*@Override
    public String toString(){
        return String.format("[uid=%s,name=%s,gender=%s]", this.fUid, this.fName, this.fGender);
    }

    public String getfUid() {
        return fUid;
    }

    public void setfUid(String fUid) {
        this.fUid = fUid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfGender() {
        return fGender;
    }

    public void setfGender(String fGender) {
        this.fGender = fGender;
    }*/
}
