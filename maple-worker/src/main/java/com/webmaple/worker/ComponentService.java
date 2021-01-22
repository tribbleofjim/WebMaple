package com.webmaple.worker;

import com.webmaple.worker.util.ModelUtil;
import com.webmaple.worker.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lyifee
 * on 2021/1/20
 */
@Service
public class ComponentService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ModelUtil modelUtil;

    public void addProcessors(List<String> processorList) {
        redisUtil.sadd("processors", modelUtil.listToString(processorList));
    }

    public void addDownloaders(List<String> downloaderList) {
        redisUtil.sadd("downloaders", modelUtil.listToString(downloaderList));
    }

    public void addPipelines(List<String> pipelineList) {
        redisUtil.sadd("pipelines", modelUtil.listToString(pipelineList));
    }

    public List<String> getComponents() {
        List<String> componentsList = new ArrayList<>();
        componentsList.addAll(getProcessors());
        componentsList.addAll(getDownloaders());
        componentsList.addAll(getPipelines());
        return componentsList;
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
        removeProcessor(modelUtil.listToString((processorList)));

        List<String> downloaderList = getDownloaders();
        removeDownloader(modelUtil.listToString(downloaderList));

        List<String> pipelineList = getPipelines();
        removePipeline(modelUtil.listToString(pipelineList));
    }
}
