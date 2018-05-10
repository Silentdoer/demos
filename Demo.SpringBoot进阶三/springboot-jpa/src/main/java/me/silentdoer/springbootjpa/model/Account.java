package me.silentdoer.springbootjpa.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * `@Entity`是javax.persistence包里的
 * `@Entity`的DO似乎必须有且只能有一个主键（意思是不能为联合主键）
 *
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "t_account")  // TODO，如果只是要account那么这个注解可以不要
public class Account {
    /**
     * `@Id`声明主键，@GenerationValue定义这个值的生成策略
     *
     * TODO 经过测试确实生成了account表，且fId在表里是主键也确实是f_id
     * TODO 但是除了主键其他的都是可空的，可以查一下怎么通过主键定义不可空和默认值；
     *
     * `@GeneratedValue(strategy = GenerationType.AUTO)`会导致生成hibernate_sequence表（这个似乎只适用于oracle，因为oracle没有自增长）
     * 但是可以用strategy = GenerationType.IDENTITY，这个不会导致生成辅助表，而且会令主键自增长；
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fId;  // TODO 默认对应Mysql的bigint(20)
    @Column(unique = true, nullable = false, columnDefinition = "varchar(20)")  // TODO 唯一键也是可以有null值的，所以要主动指定不为null
    private String name;
    @Column(nullable =false, columnDefinition = "char(1) default 'f'")
    private Character gender;
    /**
     * TODO 注意columnDefinition里除了字段名可以忽略，其他的要么都写要么都不写（即必须有类型定义而不能只有个not null）
     */
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp")
    private Timestamp createTime;
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp on update current_timestamp")
    private Timestamp modifyTime;
}
