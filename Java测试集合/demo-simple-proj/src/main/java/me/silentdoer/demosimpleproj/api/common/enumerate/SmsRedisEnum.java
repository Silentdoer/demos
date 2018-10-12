package me.silentdoer.demosimpleproj.api.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 6:53 PM
 */
@Getter
@AllArgsConstructor
public enum SmsRedisEnum {

    REGISTER(-1, "GENERAL_REGISTER_SMS_CODE_{}", "普通注册");

    /**
     * 注册场景，从1开始，-1表示不需要此字段
     */
    private int scene;

    /**
     * redisKey字符串模板
     */
    private String redisKeyTemplate;

    /**
     * 备注
     */
    private String memo;

    public String getRedisKey(String key) {
        String result = this.redisKeyTemplate;
        if (this.scene > 0) {
            result = result.replaceFirst("\\{}", String.valueOf(this.scene));
        }
        result = result.replace("{}", key);
        return result;
    }

    public static SmsRedisEnum getCorrespondSceneEnum(int scene) {
        for (SmsRedisEnum anEnum : SmsRedisEnum.values()) {
            if (anEnum.scene == scene) {
                return anEnum;
            }
        }
        return null;
    }
}
