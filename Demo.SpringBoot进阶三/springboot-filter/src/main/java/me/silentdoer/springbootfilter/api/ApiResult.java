package me.silentdoer.springbootfilter.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Null;

/*
@Accessors(prefix = {"first"}, fluent = true, chain = false) private String firstName; 与生存的get和set方法有关
，prefix与定义属性前缀相同时且接下来的字符大写才生效，可以看源码注释或自行尝试；
fluent是决定生成的get/set方法要不要set/get前缀，chain决定set方法是void类型还是返回this
 */
/**
 * `@Accessor(chain = true)`表示set时返回类型不为void而是this
 * @author silentdoer
 * @version 1.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
/**
 * TODO 注意，@Data是@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集
 * 但是如果要手动重写toString或equals/hashCode方法可以主动给出那么lombok就不会自动生成了；
 * */
@Data
public class ApiResult<T> {
    public int code;
    public String msg;
    //@Null
    public T data;
    //@AssertTrue
    //@AssertFalse
    //public boolean valid;

    /**
     * 有效，优先使用自定义的
     * @return
     */
    /*@Override
    public String toString(){
        return "5555555555";
    }*/
}
