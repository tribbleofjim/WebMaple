package com.webmaple.worker;

import com.webmaple.worker.annotation.MapleProcessor;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Set;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Component
public class ComponentRegister {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentRegister.class);

    @Autowired
    private ReflectionsConfig reflectionsConfig;

    public ComponentRegister() {
        register();
    }

    public void register() {
        Reflections reflections = reflectionsConfig.getReflections();

        Set<Class<?>> processors = reflections.getTypesAnnotatedWith(MapleProcessor.class);
        LOGGER.info("start processor register...");
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
        LOGGER.info("end processor register.");

        Set<Class<? extends Downloader>> downloaders = reflections.getSubTypesOf(Downloader.class);
        LOGGER.info("start downloader register...");
        for (Class<?> clazz : downloaders) {
            // do register
            System.out.println("downloader class : " + clazz.getName());
        }
        LOGGER.info("end downloader register.");
    }
}
