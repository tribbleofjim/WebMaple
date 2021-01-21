package com.webmaple.worker;

import com.webmaple.worker.annotation.MapleProcessor;
import com.webmaple.worker.container.ComponentContainer;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ComponentContainer container;

    public ComponentRegister() {
        register();
    }

    public void register() {
        Reflections reflections = reflectionsConfig.getReflections();

        // processor register
        // format : com.ecspider.common.processor.JDProcessor#http://jd.Search.com
        Set<Class<?>> processors = reflections.getTypesAnnotatedWith(MapleProcessor.class);
        List<String> processorList = new ArrayList<>();
        LOGGER.info("start processor register...");
        for (Class<?> clazz : processors) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inteface : interfaces) {
                if (inteface == PageProcessor.class) {
                    try {
                        MapleProcessor mapleProcessor = clazz.getAnnotation(MapleProcessor.class);
                        String site = mapleProcessor.site();
                        processorList.add(clazz.getName() + "#" + site);

                    } catch (Exception e) {
                        LOGGER.error("exception_register_processor: {}, {}", clazz.getName(), e.getMessage());
                    }
                }
            }
        }
        container.addProcessors(processorList);
        LOGGER.info("end processor register.");

        // downloader register
        Set<Class<? extends Downloader>> downloaders = reflections.getSubTypesOf(Downloader.class);
        List<String> downloaderList = new ArrayList<>();
        LOGGER.info("start downloader register...");
        for (Class<?> clazz : downloaders) {
            downloaderList.add(clazz.getName());
        }
        container.addDownloaders(downloaderList);
        LOGGER.info("end downloader register.");

        // pipeline register
        Set<Class<? extends Pipeline>> pipelines = reflections.getSubTypesOf(Pipeline.class);
        List<String> pipelineList = new ArrayList<>();
        LOGGER.info("start pipeline register...");
        for (Class<?> clazz : pipelines) {
            pipelineList.add(clazz.getName());
        }
        container.addPipelines(pipelineList);
        LOGGER.info("end pipeline register.");
    }
}
