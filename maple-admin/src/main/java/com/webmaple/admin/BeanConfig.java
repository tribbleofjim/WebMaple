package com.webmaple.admin;

import com.webmaple.common.network.RequestSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/1/25
 */
@Configuration
@Component
public class BeanConfig {
    @Bean
    public RequestSender requestSender(){
        return new RequestSender();
    }
}
