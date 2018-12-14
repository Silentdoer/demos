package me.silentdoer.springbootsessionidgeneratestrategy.config;

import lombok.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootsessionidgeneratestrategy.support.MyHttpSessionIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/19/2018 7:11 PM
 */
@Configuration
public class StrategyConfig {

    /**
     * 配置了这个以后将不会再返回有关SESSION的Cookie给客户端，但是response的header里有Api-Sign: xxxx
     */
    @Bean
    public HttpSessionIdResolver httpSessionStrategy() {
        // 表示SESSION的值通过header里的Api-Sign来生成（映射，但是会加入随机数，比如最后四位是随机数，那么先对客户端的Api-Sign的值
        // 进行解码，然后移除最后四位，然后剩下的就是客户端真正的数据）；不过这个策略其实和Header是很像的，仍然要求客户端要保存response
        // 里的Api-Sign的值，然后每次传都要传这个值，只不过是放在了header里（其实可以认为跟存储SESSION在Cookie是一模一样的，只是位置不一样了而已）
        //return new HeaderHttpSessionIdResolver("Api-Sign");
        return new MyHttpSessionIdResolver();
    }
}
