package me.silentdoer.springbootcontrolleradvice.api.enumerate;

import lombok.Getter;
import me.silentdoer.springbootcontrolleradvice.api.AppResponse;

import java.util.Collections;
import java.util.Map;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Getter
public enum AppResponseEnum {
    /**
     * 1000段为成功，每个段的0位都保留，如1000保留从1001开始
     */
    SUCCESS(1001, "成功返回"),
    SUCCESS_HUB(1002, "成功返回并hub弹窗"),
    /**
     * 2000段为非业务错误
     */
    SYSTEM_ERROR(2001, "系统错误"),
    /**
     * 4000段为服务错误
     */
    SERVICE_ERROR_ALERT(4001, "服务错误并alert");

    private int code;
    private String msg;
    AppResponseEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据data获取具体的response对象
     * @param data
     * @param <T>
     * @return
     */
    public <T> AppResponse<T> getResult(T data){
        return new AppResponse<>(this.code, this.msg, data);
    }

    /**
     * 按照总监的写的，暂时不知道干什么用
     * @return
     */
    public AppResponse<Map> getResult(){
        return getResult(Collections.EMPTY_MAP);
    }
}
