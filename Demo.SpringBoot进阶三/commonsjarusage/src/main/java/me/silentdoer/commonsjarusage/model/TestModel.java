package me.silentdoer.commonsjarusage.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * TODO 重要：如果BaseModel里也用了@Data，那么setterBaseModel的字段时用的是BaseModel上@Data生成的，即子类TestModel的@Data不会覆盖BaseModel对其字段的setter方法
 * @author liqi.wang
 * @version 1.0.0
 */
@Data
@Accessors(chain = true)
public class TestModel extends BaseModel{
    private String fGender;
    private String fMemo;

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
