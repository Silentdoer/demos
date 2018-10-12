/*
package com.manaowan.api.common.util;

import java.util.function.Supplier;
import org.apache.log4j.Logger;
import com.jrzmq.core.utils.ThreadUtil;
import com.jrzmq.core.web.converter.Result;
import com.manaowan.api.common.redis.RedisUtil;

*/
/**
 * 分布式锁简单工具
 *
 * @author hgl
 * @version create：2016年10月24日 下午5:32:08
 *//*

public class DistributedLockUtil {

    private final static Logger LOGGER = Logger.getLogger(DistributedLockUtil.class);

    */
/**
     * 带分布式锁的操作-String，串行执行，每个线程最长等待时间10秒
     *
     * @param userId
     * @param cacheKey
     * @param supplier
     * @return
     *//*

    public static <T> Result<T> syncLock(String cacheKey, Supplier<Result<T>> supplier) {
        Result<T> result = new Result<T>(null);
        Long flagVal = -1l;
        try {
            flagVal = RedisUtil.decr(cacheKey);
            // 设置失效时间
            RedisUtil.expire(cacheKey, 120);
            int tryTime = 20 + (int) (Math.abs(flagVal) - 1) * 20;
            while (flagVal < -1) {
                RedisUtil.incr(cacheKey);
                ThreadUtil.sleep(2000);
                flagVal = RedisUtil.decr(cacheKey);
                if (--tryTime <= 0) {
                    break;
                }
            }
        } finally {
        }
        try {
            result = supplier.get();
            RedisUtil.incr(cacheKey);
        } catch (Exception e) {
            LOGGER.error("syncLock ex " + e.toString(), e);
            RedisUtil.incr(cacheKey);
            return result.commonFail("处理异常，请稍后再试");
        }
        return result;
    }

    */
/**
     * 带分布式锁的操作-hash
     *
     * @param userId
     * @param cacheKey
     * @param supplier
     * @return
     *//*

    public static <T> Result<T> tryLock(String cacheKey, String field, Supplier<Result<T>> supplier) {
        Result<T> result = new Result<T>(null);

        Long flagVal = -1l;
        try {
            flagVal = RedisUtil.hincrby(cacheKey, field, -1);
        } finally {
            if (flagVal.intValue() < -1) {
                RedisUtil.hincrby(cacheKey, field, 1);
                return result.commonFail("您的请求过于频繁，请稍后再试");
            }
        }
        try {
            result = supplier.get();
            RedisUtil.hincrby(cacheKey, field, 1);
        } catch (Exception e) {
            LOGGER.error("tryLock ex " + e.toString(), e);
            RedisUtil.hincrby(cacheKey, field, 1);
            return result.commonFail("处理异常，请稍后再试");
        }
        return result;
    }

    */
/**
     * 带分布式锁的操作-String，默认失效时间 30 秒
     * @param cacheKey
     * @param supplier
     * @return
     *//*

    public static <T> Result<T> tryLock(String cacheKey, Supplier<Result<T>> supplier) {
        return tryLock(cacheKey, supplier, 30, "您的请求过于频繁，请稍后再试");
    }

    */
/**
     * 带分布式锁的操作-String，指定失效时间
     * @param userId
     * @param cacheKey
     * @param supplier
     * @param seconds 缓存失效时间（秒）
     * @return
     *//*

    public static <T> Result<T> tryLock(String cacheKey, Supplier<Result<T>> supplier, int seconds, String denyMsg) {
        Result<T> result = new Result<T>(null);

        Long flagVal = -1l;
        try {
            flagVal = RedisUtil.decr(cacheKey);
        } finally {
            if (flagVal.intValue() < -1) {
                RedisUtil.incr(cacheKey);
                return result.commonFail(denyMsg);
            } else {
                // 设置失效时间
                RedisUtil.expire(cacheKey, seconds);
            }
        }
        try {
            result = supplier.get();
        } catch (Exception e) {
            LOGGER.error("tryLock ex " + e.toString(), e);
            result.commonFail("处理异常，请稍后再试");
        } finally {
            RedisUtil.del(cacheKey);
        }
        return result;
    }

    */
/**
     * 指定时间内，保证一个cacheKey只被执行一次
     * @param cacheKey
     * @param supplier
     * @param times
     * @return
     *//*

    public static <T> Result<T> uniqueLock(String cacheKey, Supplier<Result<T>> supplier, int times) {
        Result<T> result = new Result<T>(null);

        Long flagVal = -1l;
        try {
            flagVal = RedisUtil.decr(cacheKey);
            // 设置失效时间
            RedisUtil.expire(cacheKey, times);
        } finally {
            if (flagVal.intValue() < -1) {
                return result.commonFail("唯一操作已经执行过，不允许重复执行");
            }
        }
        try {
            result = supplier.get();
        } catch (Exception e) {
            LOGGER.error("uniqueLock处理异常，异常信息：" + e.getMessage(), e);
            return result.commonFail("唯一操作处理异常");
        }
        return result;
    }

}
*/
