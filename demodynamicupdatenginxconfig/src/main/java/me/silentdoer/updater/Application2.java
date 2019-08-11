package me.silentdoer.updater;

import com.github.odiszapc.nginxparser.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Application2 {

    public static void main(String[] args) throws IOException {
        NgxConfig config = NgxConfig.read("C:\\Apps\\nginx-1.17.2\\conf\\nginx.conf");
        // config.findAll(NgxConfig.BLOCK, "http", "server");
        List<?> serversBlock = config.findAll(NgxConfig.BLOCK, "http", "server");
        List<NgxBlock> blocks = (List<NgxBlock>)serversBlock;
        System.out.println(blocks.size());
        // 找出需要的那个server block
        Optional<NgxBlock> specialServerBlock = blocks.stream().filter(block -> {
            return block.getEntries().stream().anyMatch(en -> {
                if (NgxEntryType.PARAM == NgxEntryType.fromClass(en.getClass())) {
                    NgxParam param = (NgxParam) en;
                    System.out.println(param.toString());
                    if (StringUtils.equals(param.getName(), "server_name")) {
                        System.out.println(param.getValue());
                        return StringUtils.equals(param.getValue(), "clusterservers");
                    }
                }
                return false;
            });
        }).findFirst();

        NgxBlock clusterServers;
        if (specialServerBlock.isPresent()) {
            System.out.println("找到了");
            clusterServers = specialServerBlock.get();
        } else {
            throw new RuntimeException("没有找到相关的server block");
        }

    }
}
