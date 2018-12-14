package me.silentdoer.springbootcache.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 注意：Fastjson对pojo进行序列化或反序列化时，pojo类是不需要实现Serializable接口的，但是Jackson需要；
 * 但是Jackson对pojo类序列化和反序列化是不需要serialVersionUID的，那个是ObjectOutputStream这种方式会用到；
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class Student implements Serializable {
    private Long uid;
    private String name;
    private Character gender;
}
