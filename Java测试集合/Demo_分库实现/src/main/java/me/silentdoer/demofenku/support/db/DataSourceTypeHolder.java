package me.silentdoer.demofenku.support.db;

import lombok.extern.slf4j.Slf4j;

/**
 * 本地线程，数据源上下文
 */
@Slf4j
public class DataSourceTypeHolder {

    /**
     * 当前线程的DataSource类型
     */
    private static final ThreadLocal<String> CURRENT_TYPE = new ThreadLocal<String>();

    /**
     * 读库
     */
    public static void setRead() {
        CURRENT_TYPE.set(DataSourceTypeEnum.READ.getType());
        log.info("数据库切换到读库...");
    }

    /**
     * 写库
     */
    public static void setWrite() {
        CURRENT_TYPE.set(DataSourceTypeEnum.WRITE.getType());
        log.info("数据库切换到写库...");
    }

    /**
     * 获取当前线程的DataSource类型
     */
    public static String getCurrentType() {
        return CURRENT_TYPE.get();
    }

    /**
     * 清除掉为当前线程设置的DataSource类型
     */
    public static void clearTypeSetting() {
        CURRENT_TYPE.remove();
        log.info("清除当前线程DataSource的设置...");
    }
}