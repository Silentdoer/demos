package me.silentdoer.springbootbeannamegeneratorstrategy.support.generator;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/6/2018 3:39 PM
 */
public class MyAnnotationBeanNameGenerator implements BeanNameGenerator {
    private static final String COMPONENT_ANNOTATION_CLASSNAME = "org.springframework.stereotype.Component";

    private final List<String> suffixList = new ArrayList<String>(4) {
        {
            add("Impl");
        }
    };

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        // Fallback: generate a unique default bean name.
        return buildDefaultBeanName(definition, registry);
    }

    @Nullable
    private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {

        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(amd.getAnnotationAttributes(type, false));
            if (attributes != null && isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        if (beanName != null && !strVal.equals(beanName)) {
                            throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
                                                                    "component names: '" + beanName + "' versus '" + strVal + "'");
                        }
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    private boolean isStereotypeWithNameValue(String annotationType,
                                              Set<String> metaAnnotationTypes, @Nullable Map<String, Object> attributes) {

        boolean isStereotype = annotationType.equals(COMPONENT_ANNOTATION_CLASSNAME) ||
                metaAnnotationTypes.contains(COMPONENT_ANNOTATION_CLASSNAME) ||
                annotationType.equals("javax.annotation.ManagedBean") ||
                annotationType.equals("javax.inject.Named");

        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }

    private String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }

    /**
     * TODO 生成默认BeanName的方法
     * @param definition
     * @return
     */
    private String buildDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        String shortClassName = ClassUtils.getShortName(beanClassName);
        // XXX like UserServiceImpl
        for (String next : this.suffixList) {
            if (shortClassName.endsWith(next)) {
                shortClassName = shortClassName.substring(0, shortClassName.length() - next.length());
            }
        }
        // 就是将第一个字母小写
        return Introspector.decapitalize(shortClassName);
    }
}