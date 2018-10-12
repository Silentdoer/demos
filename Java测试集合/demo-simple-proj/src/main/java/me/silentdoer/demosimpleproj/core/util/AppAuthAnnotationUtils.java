package me.silentdoer.demosimpleproj.core.util;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.demosimpleproj.core.support.RequiresLogin;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 3:14 PM
 */
public class AppAuthAnnotationUtils {

    private static List<Class<?>> authAnnotations = new ArrayList<>(5);

    static {
        authAnnotations.add(RequiresLogin.class);
        // 后续加上RequiresRoles，RequiresPermissions，RequiresBackstage，RequiresGuest
    }

    public static Set<Class<?>> getAuthAnnotations(HandlerMethod method) {
        Set<Class<?>> result = new HashSet<>(5);
        for (Class annotation : authAnnotations) {
            // 注意，只要methodAnnotation不为null，则说明method上面有annotation的注解，但是注意methodAnnotation是annotation
            // 注解类型的一个实例（通过动态代理生成的接口的子类对象）
            Annotation methodAnnotation = method.getMethodAnnotation(annotation);
            if (!Objects.isNull(methodAnnotation)) {
                result.add(annotation);
            }
        }
        return result;
    }
}
