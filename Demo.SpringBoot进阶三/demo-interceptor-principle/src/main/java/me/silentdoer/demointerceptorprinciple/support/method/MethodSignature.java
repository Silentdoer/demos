package me.silentdoer.demointerceptorprinciple.support.method;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/12/2018 10:16 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodSignature {

    private Class<?> targetType;

    private String methodName;

    private List<Class<?>> methodArgTypes;
}
