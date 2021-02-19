package org.webmaple.admin;

import org.webmaple.common.network.RequestSender;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${webmaple.jar}")
    private String jarPath;

    public String getJarPath() {
        return jarPath;
    }
}
