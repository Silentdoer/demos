package me.silentdoer.springbootcustomvalidator.valid;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Slf4j
public class MyValidatorInteger implements ConstraintValidator<MyRange, Integer> {
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
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.info("是由MyValidatorInteger处理的");
        if(value >= this.min && value <= this.max) {
            return true;
        }
        return false;
    }
}
