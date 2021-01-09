package com.webmaple.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Configuration
public class ReflectionsConfigure {
    @Bean
    public ReflectionsConfig reflectionsRegister() {
        return new ReflectionsConfig("com.webmaple.worker");
    }
}
