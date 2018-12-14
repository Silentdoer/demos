package me.silentdoer.springbootvalidannotation.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class TestModel {
    /**
     * 经过测试确实可以使用hibernate的validator约束
     */
    @Range(min = 1, max = 3, message = "超出范围")
    private Long id;
}
