package com.webmaple.worker.container;

import com.webmaple.worker.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/20
 */
@Component
public class ComponentContainer {

    @Autowired
    private RedisUtil redisUtil;

    public void addProcessors(List<String> processorList) {

    }

    public void addDownloaders(List<String> downloaderList) {

    }

    public void addPipelines(List<String> pipelineList) {

    }

    public List<String> getProcessors() {
        return null;
    }

    public List<String> getDownloaders() {
        return null;
    }

    public List<String> getPipelines() {
        return null;
    }

    public void removeProcessor(String processor) {

    }

    public void removeDownloader(String downloader) {

    }

    public void removePipeline(String pipeline) {

    }

    public void destroy() {

    }
}
