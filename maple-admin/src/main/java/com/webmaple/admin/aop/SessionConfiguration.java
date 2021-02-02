package com.webmaple.admin.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lyifee
 * on 2021/2/2
 */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginAuthInterceptor())
                .addPathPatterns("/index", "/spider", "/node", "/timed", "/user", "/auth", "/template", "/upload",
                        "/add*", "/remove*", "/create*", "/delete*", "/resume*", "/pause*");
    }
}
