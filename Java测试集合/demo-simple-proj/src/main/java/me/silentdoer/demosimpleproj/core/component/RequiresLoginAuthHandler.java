package me.silentdoer.demosimpleproj.core.component;

import me.silentdoer.demosimpleproj.api.user.model.User;
import me.silentdoer.demosimpleproj.api.user.service.IUserService;
import me.silentdoer.demosimpleproj.core.support.*;
import me.silentdoer.demosimpleproj.core.util.AppAuthAnnotationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/30/2018 5:48 PM
 */
@Component
public class RequiresLoginAuthHandler extends AbstractAppAuthHandler {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supports(HandlerMethod method) {

        boolean result = false;
        Set<Class<?>> authAnnotations = AppAuthAnnotationUtils.getAuthAnnotations(method);
        for (Class<?> annotation : authAnnotations) {
            // TODO 需要进一步了解findAnnotation(..)的作用，似乎和自己想的不一样
            RequiresLogin canFind = AnnotationUtils.findAnnotation(annotation, RequiresLogin.class);
            if (!Objects.isNull(canFind) || annotation.equals(RequiresLogin.class)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public ApiResult<Object> auth(HttpServletRequest request) {
        MediaType contentType = MediaType.valueOf(request.getContentType());
        String appKey = request.getHeader(Api.APP_KEY);
        // 登录态要求必须传APP_KEY的请求头，且非multipart/data的情况下还需要用token对queryString和requestBody进行验签
        if (StringUtils.isBlank(appKey)) {
            return ApiResultEnum.fail("缺失必要参数".concat(Api.APP_KEY));
        }
        String fIdStr = JwtUtils.getTokenKey(appKey);
        if (Objects.isNull(fIdStr) || !NumberUtils.isCreatable(fIdStr)) {
            return ApiResultEnum.fail(Api.APP_KEY.concat("系统参数验证失败"));
        }
        Long fdId = Long.valueOf(fIdStr);
        User user = this.userService.getUserById(fdId);
        if (StringUtils.isBlank(user.getFdToken())) {
            return ApiResultEnum.fail("登录态已失效，请重新登录");
        }
        // multipart/data，此种情况下不验签
        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
            return ApiResultEnum.ok();
        } else {
            String authorization = request.getHeader(Api.APP_AUTHORIZATION);
            if (StringUtils.isBlank(authorization)) {
                return ApiResultEnum.fail("验签失败，未提供签名");
            }
            String queryString = request.getQueryString();
            String body;
            try {
                body = new String(IOUtils.toByteArray(request.getInputStream()), StandardCharsets.ISO_8859_1);
            } catch (IOException e) {
                return ApiResultEnum.fail("验签失败，请求体数据异常");
            }
            String tmp = user.getFdToken() + queryString + body + user.getFdToken();
            String signature = DigestUtils.md5DigestAsHex(tmp.getBytes(StandardCharsets.UTF_8));
            if (Objects.equals(authorization, signature)) {
                return ApiResultEnum.ok();
            } else {
                return ApiResultEnum.fail("验签失败");
            }
        }
    }
}
