package com.webmaple.worker.config;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Component
public class ReflectionsConfig {
    private final Reflections reflections;

    private final String packagePath;

    public ReflectionsConfig() {
        this.packagePath = "com.webmaple.worker";
        reflections = new Reflections(packagePath);
    }

    public Reflections getReflections() {
        return reflections;
    }

    public String getPackagePath() {
        return packagePath;
    }
}
