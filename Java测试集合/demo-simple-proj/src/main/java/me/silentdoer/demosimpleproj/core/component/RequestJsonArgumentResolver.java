// package me.silentdoer.demosimpleproj.core.component;
//
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import lombok.var;
// import lombok.val;
// import lombok.extern.slf4j.Slf4j;
// import me.silentdoer.demosimpleproj.core.support.RequestJson;
// import org.apache.commons.codec.binary.Base64;
// import org.springframework.core.Conventions;
// import org.springframework.core.MethodParameter;
// import org.springframework.core.annotation.AnnotationUtils;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.Errors;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.WebDataBinder;
// import org.springframework.web.bind.support.WebDataBinderFactory;
// import org.springframework.web.context.request.NativeWebRequest;
// import org.springframework.web.method.support.HandlerMethodArgumentResolver;
// import org.springframework.web.method.support.ModelAndViewContainer;
//
// import javax.servlet.http.HttpServletRequest;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.lang.annotation.Annotation;
// import java.lang.reflect.ParameterizedType;
// import java.lang.reflect.Type;
// import java.util.Collection;
// import java.util.Map;
//
// /**
//  * @author liqi.wang
//  * @version 1.0.0
//  * @date 9/12/2018 4:37 PM
//  */
// public class RequestJsonArgumentResolver implements HandlerMethodArgumentResolver {
//
//     private Gson gson;
//
//     public RequestJsonArgumentResolver() {
//         super();
//         this.gson = new GsonBuilder().disableHtmlEscaping().create();
//     }
//
//     /**
//      * 仅支持注解为RequestJson的参数.
//      */
//     @Override
//     public boolean supportsParameter(MethodParameter parameter) {
//         return parameter.hasParameterAnnotation(RequestJson.class);
//     }
//
//     @Override
//     public Object resolveArgument(MethodParameter parameter,
//                                   ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
//                                   WebDataBinderFactory binderFactory) throws Exception {
//         Object argument = null;
//
//         RequestJson jsonAnn = parameter.getParameterAnnotation(RequestJson.class);
//         String encoder = jsonAnn.encoder();
//
//         String allParam = getRequestParam(webRequest, encoder);
//         if (null == allParam || "".equals(allParam)) {
//             return null;
//         }
//
//         JsonNode node = this.objectMapper.readTree(allParam);
//
//         String path = parameter.getParameterName();
//         if (node.has(path)) {
//             argument = this.objectMapper.readValue(node.path(path), getReferenceType(parameter));
//         } else {
//             argument = this.objectMapper.readValue(allParam, getReferenceType(parameter));
//         }
//
//         String name = Conventions.getVariableNameForParameter(parameter);
//         WebDataBinder binder = binderFactory.createBinder(webRequest, argument, name);
//
//         if (argument != null) {
//             validate(binder, parameter);
//         }
//
//         mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
//
//         return argument;
//
//     }
//
//     /**
//      * 得到参数的类型.
//      * @param parameter
//      * @return
//      */
//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     private JavaType getReferenceType(MethodParameter parameter) {
//         Type type = parameter.getGenericParameterType();
//         if ((type instanceof ParameterizedType)) {
//             Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
//             Class<?> parameterType = parameter.getParameterType();
//             if (Collection.class.isAssignableFrom(parameterType)) {
//                 if ((genericTypes.length >= 1) && ((genericTypes[0] instanceof Class))) {
//                     return this.objectMapper.getTypeFactory().constructCollectionType(
//                             (Class<? extends Collection>) parameter.getParameterType(), (Class) genericTypes[0]);
//                 }
//             } else if (Map.class.isAssignableFrom(parameter.getParameterType())) {
//                 if ((genericTypes.length >= 2) && ((genericTypes[0] instanceof Class)) &&
//                         ((genericTypes[1] instanceof Class))) {
//                     return this.objectMapper.getTypeFactory().constructMapType(
//                             (Class<? extends Map>) parameter.getParameterType(),
//                             (Class) genericTypes[0], (Class) genericTypes[1]);
//                 }
//                 if ((genericTypes.length == 1) && ((genericTypes[0] instanceof Class))) {
//                     return this.objectMapper.getTypeFactory().constructMapType(
//                             (Class<? extends Map>) parameter.getParameterType(),
//                             (Class) genericTypes[0], Object.class);
//                 }
//                 return this.objectMapper.getTypeFactory().constructMapType(
//                         (Class<? extends Map>) parameter.getParameterType(), Object.class, Object.class);
//             }
//             throw new UnsupportedOperationException("Unsuppored Reference " + parameter.getParameterName()
//                                                             + " To JavaType " + parameter.getParameterType().getName());
//         }
//         return this.objectMapper.getTypeFactory().constructType(parameter.getParameterType());
//     }
//
//
//     /**
//      * 根据request，得到请求文本.
//      * @param webRequest
//      * @param encoder 编码方式
//      * @return
//      */
//     private String getRequestParam(NativeWebRequest webRequest, String encoder) throws IOException {
//         HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest(
//                 HttpServletRequest.class);
//         String method = httpServletRequest.getMethod();
//         StringBuffer requestBody = new StringBuffer();
//         if ("GET".equals(method) || "DELETE".equals(method)) {
//             requestBody.append(httpServletRequest.getQueryString());
//         } else {
//             StringBuilder buffer = new StringBuilder();
//             BufferedReader reader = httpServletRequest.getReader();
//             String line;
//             while ((line = reader.readLine()) != null) {
//                 buffer.append(line);
//             }
//             requestBody.append(buffer);
//         }
//
//         if("base64".equals(encoder)) {
//             byte[] originBytes = requestBody.toString().getBytes("UTF-8");
//             if (Base64.isBase64(originBytes)) {
//                 return new String(Base64.decodeBase64(originBytes), "UTF-8");
//             } else {
//                 return null;
//             }
//         } else {
//             return requestBody.toString();
//         }
//     }
//
//     private void validate(WebDataBinder binder, MethodParameter parameter) throws Exception, MethodArgumentNotValidException {
//
//         Annotation[] annotations = parameter.getParameterAnnotations();
//         for (Annotation annot : annotations) {
//             if (annot.annotationType().getSimpleName().startsWith("Valid")) {
//                 Object hints = AnnotationUtils.getValue(annot);
//                 binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
//                 BindingResult bindingResult = binder.getBindingResult();
//                 if (bindingResult.hasErrors()) {
//                     if (isBindExceptionRequired(binder, parameter)) {
//                         throw new MethodArgumentNotValidException(parameter, bindingResult);
//                     }
//                 }
//                 break;
//             }
//         }
//     }
//
//     /**
//      * Whether to raise a {@link MethodArgumentNotValidException} on validation errors.
//      * @param binder the data binder used to perform data binding
//      * @param parameter the method argument
//      * @return {@code true} if the next method argument is not of type {@link Errors}.
//      */
//     private boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
//         int i = parameter.getParameterIndex();
//         Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
//         boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
//
//         return !hasBindingResult;
//     }
// }