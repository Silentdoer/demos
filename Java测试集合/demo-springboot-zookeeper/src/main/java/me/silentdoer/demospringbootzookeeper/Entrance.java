package me.silentdoer.demospringbootzookeeper;

import org.springframework.core.Ordered;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/17/2018 4:42 PM
 */
public class Entrance {

    public static void main(String[] args) {

        String foo = "{a},{b},{c,e}";
        System.out.println(foo.replace("},{", "} {"));
    }
}
