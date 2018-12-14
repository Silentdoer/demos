package me.silentdoer.demointerceptorprinciple.support.util;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

/**
 * Class 的Helper类，静态类
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/11/2018 6:29 PM
 */
public final class ClassUtils {

    private ClassUtils() {
    }

    public static boolean equals(@Nonnull Class<?> a, @Nonnull Class<?> b) {
        return a.equals(b);
    }

    /**
     * 注意a和b内部的每个元素都不应该是null
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(@Nonnull Class<?>[] a, @Nonnull Class<?>[] b) {
        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            // 只要存在Class对象是null，那么就认为是false
            if (a[i] == null || b[i] == null) {
                return false;
            }

            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }
}
