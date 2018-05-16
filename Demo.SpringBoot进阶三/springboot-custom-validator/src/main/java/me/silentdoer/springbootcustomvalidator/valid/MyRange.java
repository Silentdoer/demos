package me.silentdoer.springbootcustomvalidator.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 实现自己的验证注解，类似@Email，@Min，@NotBlank之类的
 * TODO 第三个注解@Constraint表示自定义的验证器由谁来验证，这个验证器必须实现
 *
 * TODO 经过测试，MyContraint对应处理的Validator要在同一个包里否则报错
 * @author liqi.wang
 * @version 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MyValidatorString.class, MyValidatorInteger.class})
public @interface MyRange {
    /**
     * TODO 查了一下网上的，这三个是必备的，那就不管他用就是
     * @return
     */
    String message() default "字符串不能被转换为数值";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}
