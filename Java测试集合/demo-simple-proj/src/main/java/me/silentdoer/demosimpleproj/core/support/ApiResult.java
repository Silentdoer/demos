package me.silentdoer.demosimpleproj.core.support;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/29/2018 6:49 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {

    /**
     * 后台的code
     */
    private Integer code;

    /**
     * 后台的msg
     */
    private String msg;

    /**
     * 其他平台的code
     */
    private Integer thirdCode;

    /**
     * 其他平台的msg
     */
    private String thirdMsg;

    /**
     * 单次请求的唯一ID，一般用于生产后的统计，调试阶段的调用是一一匹配的，但是生产后某个接口是会被大量调用，因此某些接口可能
     * 需要这个字段来实现区分统计功能，可以用UUID实现
     */
    private String requestId;

    /**
     * 具体的数据
     */
    private T data;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}