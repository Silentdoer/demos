package me.silentdoer.demosimpleproj.core.util;

import com.alibaba.fastjson.JSON;
import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 2:44 PM
 */
public final class IOUtils {

    public static <T> void write(T obj, OutputStream os) {
        if (Objects.isNull(os)) {
            return;
        }
        String data = JSON.toJSONString(obj);
        if (StringUtils.isBlank(data)) {
            return;
        }
        try {
            org.apache.commons.io.IOUtils.write(data, os, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
