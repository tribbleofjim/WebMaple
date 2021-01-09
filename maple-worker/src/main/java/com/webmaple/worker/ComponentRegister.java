package com.webmaple.worker;

import com.webmaple.worker.annotation.MapleProcessor;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Set;

/**
 * @author lyifee
 * on 2021/1/9
 */
public class ComponentRegister {
    private final String packagePath;

    private final Reflections reflections;

    public ComponentRegister(String packagePath) {
        this.packagePath = packagePath;
        reflections = new Reflections(packagePath);
    }

    public void register() {
        Set<Class<?>> processors = reflections.getTypesAnnotatedWith(MapleProcessor.class);
        System.out.println("start processor register...");
        for (Class<?> clazz : processors) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inteface : interfaces) {
                if (inteface == PageProcessor.class) {
                    MapleProcessor mapleProcessor = clazz.getAnnotation(MapleProcessor.class);
                    String site = mapleProcessor.site();
                    // do register
                    System.out.println("processor class : " + clazz.getName() + ", site : " + site);
                }
            }
        }
        System.out.println("end processor register.");

        Set<Class<? extends Downloader>> downloaders = reflections.getSubTypesOf(Downloader.class);
        System.out.println("start downloader register...");
        for (Class<?> clazz : downloaders) {
            // do register
            System.out.println("downloader class : " + clazz.getName());
        }
        System.out.println("end downloader register.");
    }

    public String getPackagePath() {
        return packagePath;
    }

}
