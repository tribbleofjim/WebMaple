package com.webmaple.common.util;

import com.webmaple.common.model.SpiderDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/8
 */
@Component
public class ModelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtil.class);

    @Autowired
    private JedisPool jedisPool;

    public Spider getSpiderFromDTO(SpiderDTO spiderDTO) {
        try {
            String processorClass = spiderDTO.getProcessor();
            String pipelineClass = spiderDTO.getPipeline();
            String downloaderClass = spiderDTO.getDownloader();
            String uuid = spiderDTO.getUuid();
            String urls;
            List<String> urlList = spiderDTO.getUrls();
            StringBuilder builder = new StringBuilder();
            for (String url : urlList) {
                builder.append(url).append(",");
            }
            urls = builder.toString();
            urls = urls.substring(0, urls.length() - 1);

            int threadNum = spiderDTO.getThreadNum();

            PageProcessor processor = (PageProcessor) Class.forName(processorClass).newInstance();
            Pipeline pipeline = (Pipeline) Class.forName(pipelineClass).newInstance();
            Downloader downloader = null;
            if (StringUtils.isNotBlank(downloaderClass)) {
                downloader = (Downloader) Class.forName(downloaderClass).newInstance();
            }

            Spider spider = Spider.create(processor)
                    .setUUID(uuid)
                    .addUrl(urls)
                    .addPipeline(pipeline)
                    .addPipeline(new ConsolePipeline())
                    .setScheduler(new RedisScheduler(getJedisPool()))
                    .thread(threadNum);

            if (downloader != null) {
                spider.setDownloader(downloader);
            }
            return spider;

        } catch (Exception e) {
            LOGGER.error("build_spider_exception:", e);
        }
        return null;
    }

    private JedisPool getJedisPool() {
        // TODO:这里可以做成一个接口供调用
        return jedisPool;
    }

    public SpiderDTO getSpiderDTO(Spider spider) {
        SpiderDTO spiderDTO = new SpiderDTO();
        spiderDTO.setUuid(spider.getUUID());
        spiderDTO.setThreadNum(spider.getThreadAlive());
        spiderDTO.setState("running");
        return spiderDTO;
    }
}
