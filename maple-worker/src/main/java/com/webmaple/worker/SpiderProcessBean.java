package com.webmaple.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyifee
 * on 2021/1/8
 */
@Configuration
public class SpiderProcessBean {
    @Bean
    public SpiderProcess spiderProcess() {
        return new SpiderProcess(7);
    }
}
