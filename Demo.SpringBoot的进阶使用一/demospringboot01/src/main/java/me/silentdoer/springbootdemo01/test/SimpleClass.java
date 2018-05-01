package me.silentdoer.springbootdemo01.test;

import org.springframework.stereotype.Component;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 5/1/18 11:12 AM
 */
//@Component
public class SimpleClass {
    // 普通类，没有加@Component之类的注解那么是不会被默认的ScanComponent给添加到IOC容器的，但是加了@Component之类的注解后就会了（前提是在根包路径下）
}
