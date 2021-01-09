package com.webmaple.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Configuration
public class ComponentRegisterConfigure {
    @Bean
    public ComponentRegister componentRegister() {
        return new ComponentRegister("com.webmaple.worker");
    }
}
