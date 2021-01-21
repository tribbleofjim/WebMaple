package com.webmaple.worker.job.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.common.util.ConfigUtil;
import com.webmaple.worker.job.JobMapDataKey;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 执行定时spider的任务
 * jobDataMap中必须提供的参数例子：
 * {
 *     "extraInfo": {
 *          "processor" : "com.ecspider.common.processor.JDProcessor", // 必填
 *          "pipeline" : "com.ecspider.common.pipeline.JDPipeline", // 必填
 *          "urls" : "https://www.sojson.com/editor.html, https://blog.csdn.net/qq_123456/article/details/654321", // 必填
 *          "uuid" : "jd.com", // 必填
 *          "downloader" : "com.ecspider.common.downloader.SeleniumDownloader", // 可选
 *          "threadNum" : 1, // 必填
 *          "maintainType" : 1000 // 必填
 *     }
 * }
 *
 * 或者
 * {
 *     "extraInfo": {
 *          "processor" : "com.ecspider.common.processor.JDProcessor", // 必填
 *          "pipeline" : "com.ecspider.common.pipeline.JDPipeline", // 必填
 *          "urls" : "https://www.sojson.com/editor.html, https://blog.csdn.net/qq_123456/article/details/654321", // 必填
 *          "uuid" : "jd.com", // 必填
 *          "downloader" : "com.ecspider.common.downloader.SeleniumDownloader", // 可选
 *          "threadNum" : 1, // 必填
 *          "maintainType" : 60 // 必填
 *     }
 * }
 *
 *
 * @author lyifee
 * on 2021/1/11
 */
public class SpiderJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderJob.class);

    private String extraInfo;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JSONObject spiderJson = JSON.parseObject(extraInfo);
        String spiderId = spiderJson.getString(JobMapDataKey.UUID.getKey());
        TimedSpider timedSpider;
        if ((timedSpider = TimedSpiderContainer.get(spiderId)) != null) {
            timedSpider.executeSpider();
            return;
        }

        timedSpider = buildTimedSpiderFromJson(spiderJson);
        if (timedSpider == null) {
            LOGGER.error("cannot_build_timed_spider_from_spiderJson:{}", spiderJson);
            return;
        }
        TimedSpiderContainer.put(spiderId, timedSpider);
        timedSpider.executeSpider();
    }

    private TimedSpider buildTimedSpiderFromJson(JSONObject spiderJson) {
        String processorClass = spiderJson.getString(JobMapDataKey.PROCESSOR.getKey());
        String pipelineClass = spiderJson.getString(JobMapDataKey.PIPELINE.getKey());
        String urlString = spiderJson.getString(JobMapDataKey.URLS.getKey());
        String uuid = spiderJson.getString(JobMapDataKey.UUID.getKey());
        String downloaderClass = spiderJson.getString(JobMapDataKey.DOWNLOADER.getKey());
        int threadNum = spiderJson.getInteger(JobMapDataKey.THREAD_NUM.getKey());

        int maintain;
        String maintainType = null;
        if (spiderJson.containsKey(JobMapDataKey.MAINTAIN_URL_NUM.getKey())) {
            maintainType = JobMapDataKey.MAINTAIN_URL_NUM.getKey();
            maintain = spiderJson.getInteger(maintainType);

        } else if (spiderJson.containsKey(JobMapDataKey.MAINTAIN_TIME.getKey())){
            maintainType = JobMapDataKey.MAINTAIN_TIME.getKey();
            maintain = spiderJson.getInteger(maintainType);

        } else {
            maintain = -1;
        }

        try {
            PageProcessor processor = (PageProcessor) Class.forName(processorClass).newInstance();
            Pipeline pipeline = (Pipeline) Class.forName(pipelineClass).newInstance();
            Downloader downloader = null;
            if (StringUtils.isNotBlank(downloaderClass)) {
                downloader = (Downloader) Class.forName(downloaderClass).newInstance();
            }

            Spider spider = Spider.create(processor)
                    .setUUID(uuid)
                    .addUrl(String.valueOf(urlString))
                    .addPipeline(pipeline)
                    .addPipeline(new ConsolePipeline())
                    .setScheduler(new RedisScheduler(getJedisPool()))
                    .thread(threadNum);

            if (downloader != null) {
                spider.setDownloader(downloader);
            }

            TimedSpider timedSpider;
            if (maintainType == null) {
                timedSpider = new TimedSpider(spider);
            } else {
                if (maintainType.equals(JobMapDataKey.MAINTAIN_URL_NUM.getKey())) {
                    timedSpider = new TimedSpider(spider).maintainUrls(maintain);
                } else {
                    timedSpider = new TimedSpider(spider).maintainTime(maintain);
                }
            }
            return timedSpider;

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            LOGGER.error("exception_when_building_spider:", e);
        }
        return null;
    }

    private JedisPool getJedisPool() {
        String host = ConfigUtil.getValueToString("application.yml", "spring.redis.host");
        int port = 6379;
        String password = ConfigUtil.getValueToString("application.yml", "spring.redis.password");
        int timeout = Integer.parseInt(ConfigUtil.getValueToString("application.yml", "spring.redis.timeout"));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
