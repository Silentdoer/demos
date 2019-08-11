package me.silentdoer.updater;

import java.io.File;
import java.io.IOException;

public class Application4ReloadNgx {

    public static void main(String[] args) throws IOException {
        /*
        Linux里是：
        Process child = Runtime.getRuntime()
        .exec(new String[] {"/bin/sh","-c","nginx -s reload" }, null, new File("/usr/local/nginx"));
        child.waitFor();
         */
        // 后面的File是指定执行这个命令的工作目录
        Process exec = Runtime.getRuntime().exec(new String[] {"cmd", "/c", "nginx -s reload" }, null, new File("c:/Apps/nginx-1.17.2"));
        try {
            exec.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(exec.exitValue());
        System.out.println("reload success");
    }
}
