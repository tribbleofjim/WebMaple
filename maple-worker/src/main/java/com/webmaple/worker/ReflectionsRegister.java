package com.webmaple.worker;

import org.reflections.Reflections;

/**
 * @author lyifee
 * on 2021/1/9
 */
public class ReflectionsRegister {
    private final Reflections reflections;

    private final String packagePath;

    public ReflectionsRegister(String packagePath) {
        this.packagePath = packagePath;
        reflections = new Reflections(packagePath);
    }

    public Reflections getReflections() {
        return reflections;
    }

    public String getPackagePath() {
        return packagePath;
    }
}
