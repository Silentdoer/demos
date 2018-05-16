package me.silentdoer.springbootcustomvalidator.valid;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 注意String是@MyRange要注解的成员的类型，这个不能写错，否则报找不到对应的Validator
 * TODO 一个Constraint可以有多个Validator对应，比如当一个Constraint可以注解在不同类型的时候
 * 但要配置validatedBy = {MyValidatorString.class, MyValidatorInteger.class}
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
public class MyValidatorString implements ConstraintValidator<MyRange, String> {
    private int min;
    private int max;

    /**
     * MyRange 是会被@MyConstraint注解的成员由此validator来验证
     * @param constraintAnnotation
     */
    @Override
    public void initialize(MyRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    /**
     *
     * @param value 被@MyConstraint注解的成员的值，一般都是基础类型
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("是由MyValidatorString处理的");
        if(!NumberUtils.isCreatable(value)){
            return false;
        }
        int iVal = Integer.parseInt(value);
        if(iVal >= this.min && iVal <= this.max) {
            return true;
        }
        return false;
    }
}
