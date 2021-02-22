package org.webmaple.worker.config;

import org.reflections.Reflections;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Configuration
@Component
public class ReflectionsConfig implements InitializingBean {
    private Reflections reflections;

    @Value("${webmaple.worker.package-path}")
    private String packagePath;

    public Reflections getReflections() {
        return reflections;
    }

    public String getPackagePath() {
        return packagePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reflections = new Reflections(packagePath);
    }
}
