package com.webmaple.worker.container;

import com.webmaple.worker.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lyifee
 * on 2021/1/20
 */
@Component
public class ComponentContainer {

    @Autowired
    private RedisUtil redisUtil;

    public void addProcessors(List<String> processorList) {
        redisUtil.sadd("processors", listToString(processorList));
    }

    public void addDownloaders(List<String> downloaderList) {
        redisUtil.sadd("downloaders", listToString(downloaderList));
    }

    public void addPipelines(List<String> pipelineList) {
        redisUtil.sadd("pipelines", listToString(pipelineList));
    }

    public List<String> getProcessors() {
        Set<String> processors = redisUtil.smembers("processors");
        return new ArrayList<>(processors);
    }

    public List<String> getDownloaders() {
        Set<String> processors = redisUtil.smembers("downloaders");
        return new ArrayList<>(processors);
    }

    public List<String> getPipelines() {
        Set<String> processors = redisUtil.smembers("pipelines");
        return new ArrayList<>(processors);
    }

    public void removeProcessor(String... remProcessors) {
        redisUtil.srem("processors", remProcessors);
    }

    public void removeDownloader(String... remDownloaders) {
        redisUtil.srem("downloaders", remDownloaders);
    }

    public void removePipeline(String... remPipelines) {
        redisUtil.srem("pipelines", remPipelines);
    }

    public void destroy() {
        List<String> processorList = getProcessors();
        removeProcessor(listToString((processorList)));

        List<String> downloaderList = getDownloaders();
        removeDownloader(listToString(downloaderList));

        List<String> pipelineList = getPipelines();
        removePipeline(listToString(pipelineList));
    }

    private String listToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(",").append(str);
        }
        return builder.toString().substring(1);
    }
}
