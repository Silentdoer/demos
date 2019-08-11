package me.silentdoer.updater;

import com.github.odiszapc.nginxparser.*;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws IOException {
        NgxConfig config = NgxConfig.read("C:\\Apps\\nginx-1.17.2\\conf\\nginx.conf");
        // 获得第一个符合条件的block（这里之所以是... params是为了能查找内部的NgxBlock，如("http", "server")
        // 这个是只取第一个，但是对于http内部的server的Block是可以有多个的，因此可以用config.findAll(NgxConfig.BLOCK, "http", "server");
        NgxBlock httpBlock = config.findBlock("http");

        // 这里获得的NgxEntry的实例的类型有NgxParam对于JSON里的普通属性（字符串，数值之类的）
        // NgxComment对应的是#开头的一行注释
        // NgxBlock对应的是类似JSON对象里内部的另一个JSON对象，它们都实现了NgxEntry接口
        // 它的实现方式应该也是首先以行作为分隔，其次看这一行左侧是否有key，没有说明是作为上一行的value部分；
        //System.out.println(httpBlock.getEntries().stream().map(Object::toString).filter(e -> !e.startsWith("#")).collect(Collectors.joining("\n")));
        //System.out.println(httpBlock.getEntries().stream().map(en -> String.join("<***>", en.getClass().toString(), en.toString())).collect(Collectors.joining("\n")));
        httpBlock.getEntries().forEach(entry -> {
            switch (NgxEntryType.fromClass(entry.getClass())) {
                case PARAM:
                    NgxParam param = (NgxParam) entry;
                    //Collection<NgxToken> tokens = param.getTokens();
                    // NgxToken是用于描述一个NgxEntry的所有组件，以空白符作为分割，比如log_format有5个token，而default_type则只有两个
                    // getValues一个key可以有多个value，比如log_format就是多个value，它和token概念类似
                    // 但是如果是getValue则自动会将values组装为一个String输出，而不是取values的第一个
                    System.out.println(param.getName());
                    System.out.println(param.getValue());
                    break;

                case BLOCK:
                    NgxBlock block = (NgxBlock) entry;
                    // location的value是匹配pattern比如/，而对于server由于它直接是{..}所以value是空字符串；
                    //System.out.println(block.toString());
                    System.out.println(block.getName());
                    System.out.println(block.getValue());
                    break;
                default:
                    break;
            }
        });

    }
}
