package me.silentdoer.springboottest.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author silentdoer
 */
@Data
@Slf4j
public class Student implements Serializable {
    private Long uid;
    private String name;
    private Character gender;

    @Override
    public String toString(){
        // TODO 可以直接用log变量，这是lombok插件提供的
        //log.debug("UUUUUUUUUU");
        return String.format("[uid=%s, name=%s, gender=%s]", this.uid, this.name, this.gender);
    }
}
