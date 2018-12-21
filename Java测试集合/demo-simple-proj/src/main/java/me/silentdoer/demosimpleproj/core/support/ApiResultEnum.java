package me.silentdoer.demosimpleproj.core.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationFilterChain;

/**
 * ApiResult的状态的枚举，并提供常用的状态ApiResult的生产方法
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 10:39 AM
 */
@Getter
@AllArgsConstructor
public enum ApiResultEnum {

    NORMAL(10000, "正常"),

    PUSH_TASK(11000, "提交任务成功"),

    FAILURE(30000, "通用失败"),

    FAILURE_TIP(31000, "通用失败需要弹框");

    private int code;

    private String msg;

    public <T> ApiResult<T> wrap(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(this.code);
        result.setMsg(this.msg);
        result.setData(data);
        return result;
    }

    public <T> ApiResult<T> wrap(String msg, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(this.code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public <T> ApiResult<T> translate() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(this.code);
        result.setMsg(this.msg);
        return result;
    }

    public <T> ApiResult<T> translate(String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(this.code);
        result.setMsg(msg);
        return result;
    }

    public <T> boolean correspond(ApiResult<T> result) {
        return this.code == result.getCode();
    }

    public static <T> ApiResult<T> ok(T data) {
        return NORMAL.wrap(data);
    }

    public static <T> ApiResult<T> ok() {
        return NORMAL.wrap(null);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return FAILURE.translate(msg);
    }

    public static <T> ApiResult<T> failTip(String msg) {
        return FAILURE_TIP.translate(msg);
    }
}
