package org.webmaple.worker.config;

import org.webmaple.common.network.RequestSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/1/29
 */
@Configuration
@Component
public class NetworkConfig {
    @Bean
    public RequestSender requestSender() {
        return new RequestSender();
    }
}
