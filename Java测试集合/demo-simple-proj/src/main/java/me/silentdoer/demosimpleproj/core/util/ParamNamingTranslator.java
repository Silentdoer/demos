package me.silentdoer.demosimpleproj.core.util;

import java.util.Arrays;

/**
 * 用于转换参数的命名方式，如req_name -> reqName等
 *
 * @author liqi.wang
 * @version 1.0.0
 */
public final class ParamNamingTranslator {
    /**
     * @param lowerCamelParam reqName
     * @return req_name
     */
    public static String lowerCamel2Underline(String lowerCamelParam) {
        return lowerCamelParam.trim().replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * @param underlineParam req_name
     * @return reqName
     */
    public static String underline2LowerCamel(String underlineParam) {
        return Arrays.stream(underlineParam.trim().split("_")).reduce((a, b) -> a.concat(firstChar2UpperCase(b))).orElse(null);
    }

    /**
     * 将字符串的第一个字符转换为小写
     */
    private static String firstChar2LowerCase(String str) {
        return str.substring(0, 1).toLowerCase().concat(str.substring(1));
    }

    /**
     * 将字符串的第一个字符转换为大写
     */
    private static String firstChar2UpperCase(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }
}
