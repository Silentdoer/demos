package me.silentdoer.updater;

import com.github.odiszapc.nginxparser.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application3AlterNgxConfig {

    private static final String CONFIG_PATH = "C:\\Apps\\nginx-1.17.2\\conf\\nginx.conf";

    public static void main(String[] args) throws IOException {
        NgxConfig config = NgxConfig.read(CONFIG_PATH);
        // config.findAll(NgxConfig.BLOCK, "http", "server");
        List<NgxBlock> blocks = config.findAll(NgxConfig.BLOCK, "http", "upstream")
                .stream().map(e -> ((NgxBlock) e)).collect(Collectors.toList());

        System.out.println(blocks.size());
        // 找出需要的那个upstream block
        Optional<NgxBlock> specialBlockOptional = blocks.stream().filter(block -> {
            System.out.println(block.getValue());
            return StringUtils.equals(block.getValue(), "clusterservers");
        }).findFirst();

        NgxBlock clusterServers;
        if (specialBlockOptional.isPresent()) {
            System.out.println("找到了对应的upstream");
            clusterServers = specialBlockOptional.get();
        } else {
            throw new RuntimeException("没有找到相关的upstream block");
        }

        // 找出失效的upstream内部的server
        Optional<NgxEntry> failServerOptional = clusterServers.getEntries().stream().filter(entry -> {
            if (NgxEntryType.fromClass(entry.getClass()) == NgxEntryType.PARAM) {
                NgxParam param = (NgxParam) entry;
                // 失效的server
                List<String> values = param.getValues();
                System.out.println(values);
                return values.contains("127.0.0.1:8891");
            }
            return false;
        }).findFirst();

        NgxEntry failEntry;
        if (failServerOptional.isPresent()) {
            failEntry = failServerOptional.get();
            System.out.println("找到了upstream里失效的server配置");
        } else {
            throw new RuntimeException("失效的server不存在");
        }
        // 移除失效的server
        clusterServers.remove(failEntry);
        // config.remove(failEntry);  // 这个也可以

        // 更新nginx配置文件，config里已经有文件路径了
        NgxDumper dumper = new NgxDumper(config);
        // 注意，这里的dump并不会直接去覆盖config的文件（防止因为文件占用什么的导致异常），而是会返回更新后的配置
        // 由程序员自己去覆盖
        String newConfig = dumper.dump();

        // 覆盖原配置文件
        FileUtils.writeStringToFile(Paths.get(CONFIG_PATH).toFile(), newConfig, StandardCharsets.UTF_8);

        System.out.println("更新配置成功，记得reload");
    }
}
