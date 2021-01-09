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
    public ReflectionsRegister reflectionsRegister() {
        return new ReflectionsRegister("com.webmaple.worker");
    }
}
