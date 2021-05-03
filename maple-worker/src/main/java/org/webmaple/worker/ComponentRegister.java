package org.webmaple.worker;

import com.alibaba.fastjson.JSON;
import org.webmaple.common.enums.ComponentType;
import org.webmaple.common.model.ComponentDTO;
import org.webmaple.worker.annotation.MaplePipeline;
import org.webmaple.worker.annotation.MapleProcessor;
import org.webmaple.worker.config.ReflectionsConfig;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 注册项目中的Processor、Downloader和Pipeline
 * Processor通过注解注册，其他两个类直接扫描注册
 *
 * @author lyifee
 * on 2021/1/9
 */
@Component
public class ComponentRegister implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentRegister.class);

    @Autowired
    private ReflectionsConfig reflectionsConfig;

    @Autowired
    private ComponentService componentService;

    public void register() {
        Reflections reflections = reflectionsConfig.getReflections();

        // processor register
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
                        ComponentDTO component = new ComponentDTO();
                        component.setName(clazz.getName());
                        component.setSite(site);
                        component.setType(ComponentType.PROCESSOR.getType());
                        processorList.add(JSON.toJSONString(component));

                    } catch (Exception e) {
                        LOGGER.error("exception_register_processor: {}, {}", clazz.getName(), e.getMessage());
                    }
                }
            }
        }
        componentService.addProcessors(processorList);
        LOGGER.info("end processor register.");

        // downloader register
        Set<Class<? extends Downloader>> downloaders = reflections.getSubTypesOf(Downloader.class);
        List<String> downloaderList = new ArrayList<>();
        LOGGER.info("start downloader register...");
        for (Class<?> clazz : downloaders) {
            ComponentDTO component = new ComponentDTO();
            component.setName(clazz.getName());
            component.setType(ComponentType.DOWNLOADER.getType());
            try {
                downloaderList.add(JSON.toJSONString(component));

            } catch (Exception e) {
                LOGGER.warn("serialize_component_exception:{}", component);
            }
        }
        componentService.addDownloaders(downloaderList);
        LOGGER.info("end downloader register.");

        // pipeline register
        Set<Class<?>> pipelines = reflections.getTypesAnnotatedWith(MaplePipeline.class);
        List<String> pipelineList = new ArrayList<>();
        LOGGER.info("start processor register...");
        for (Class<?> clazz : pipelines) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inteface : interfaces) {
                if (inteface == Pipeline.class) {
                    try {
                        MaplePipeline maplePipeline = clazz.getAnnotation(MaplePipeline.class);
                        String site = maplePipeline.site();
                        ComponentDTO component = new ComponentDTO();
                        component.setName(clazz.getName());
                        component.setType(ComponentType.PIPELINE.getType());
                        component.setSite(site);
                        pipelineList.add(JSON.toJSONString(component));

                    } catch (Exception e) {
                        LOGGER.error("exception_register_pipeline: {}, {}", clazz.getName(), e.getMessage());
                    }
                }
            }
        }
        componentService.addPipelines(pipelineList);
        LOGGER.info("end pipeline register.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register();
    }
}
