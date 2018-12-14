package me.silentdoer.springbootcontrolleradvice.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author liqi.wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {
    private int code;
    private String msg;
    private T data;
}
