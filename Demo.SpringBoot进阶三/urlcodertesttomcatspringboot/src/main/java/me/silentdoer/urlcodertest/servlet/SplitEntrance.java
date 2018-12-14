package me.silentdoer.urlcodertest.servlet;

import java.util.Arrays;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class SplitEntrance {
    public static void main(String[] args){
        String str = "str";
        System.out.println(str.split("=").length);
        List<Long> list = Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        list.stream().parallel().forEach(e -> {
            System.out.println(e);
        });
    }
}
