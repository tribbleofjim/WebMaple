package org.webmaple.worker.util;

import com.alibaba.fastjson.JSON;
import org.webmaple.common.enums.MaintainType;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.config.JedisSPI;
import org.webmaple.worker.job.model.QuartzJob;
import org.webmaple.worker.job.model.SpiderInfo;
import org.apache.commons.collections.CollectionUtils;
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

import java.util.*;

/**
 * @author lyifee
 * on 2021/1/8
 */
@Component
public class ModelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtil.class);

    @Autowired
    private JedisSPI jedisSPI;

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
                    .setScheduler(new RedisScheduler(jedisSPI.getJedisPool()))
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
    
    public QuartzJob getQuartzJobForSpider(SpiderDTO spiderDTO, String maintainType, int maintain, String rawCron) {
        QuartzJob quartzJob = new QuartzJob();

        String uuid = spiderDTO.getUuid();
        quartzJob.setExecuting(true);
        quartzJob.setJobName(getJobWithPlus(getUuidPlus(uuid)));
        quartzJob.setJobClazz("org.webmaple.worker.job.spider.SpiderJob");
        String cron = getCron(rawCron);
        if (StringUtils.isBlank(cron)) {
            LOGGER.error("invalid_cron");
            return null;
        }
        System.out.println(cron);
        quartzJob.setCronExpression(cron);
        quartzJob.setStartTime(new Date());

        SpiderInfo spiderInfo = new SpiderInfo();
        spiderInfo.setProcessor(spiderDTO.getProcessor());
        spiderInfo.setPipeline(spiderDTO.getPipeline());
        spiderInfo.setUrls(listToString(spiderDTO.getUrls()));
        spiderInfo.setUuid(uuid);
        spiderInfo.setDownloader(spiderDTO.getDownloader());
        spiderInfo.setThreadNum(spiderDTO.getThreadNum());
        spiderInfo.setMaintainUrlNum(maintain);

        quartzJob.setExtraInfo(JSON.toJSONString(spiderInfo));
        return quartzJob;
    }

    public String getUuidPlus(String uuid) {
        String[] strs;
        if (StringUtils.isBlank(uuid) || (strs = uuid.split("_")).length < 2) {
            return null;
        }
        return strs[1];
    }

    public String getJobWithPlus(String plus) {
        return "job_" + plus;
    }

    public String listToString(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(",").append(str);
        }
        return builder.toString().substring(1);
    }

    public String getCron(String rawCron) {
        // 5m / 4h
        if (StringUtils.isBlank(rawCron)) {
            LOGGER.error("invalid_rawCron : {}", rawCron);
            return null;
        }
        String baseCron = "0 * * * * ?";
        String num = "0/" + rawCron.substring(0, rawCron.length() - 1);
        if (rawCron.endsWith("m")) {
            return baseCron.replaceFirst("\\*", num);

        } else if (rawCron.endsWith("h")){
            String cron = baseCron.replaceFirst("\\*", "0");
            cron = cron.replaceFirst("\\*", num);
            return cron;

        } else {
            throw new RuntimeException("invalid_rawCron : " + rawCron);
        }
    }

    public String fromCron(String cron) {
        if (StringUtils.isBlank(cron)) {
            LOGGER.error("invalid_cron:{}", cron);
            return null;
        }
        String[] strs = cron.split(" ");
        String easyCron;
        if (!strs[1].equals("0")) {
            easyCron = strs[1].split("/")[1] + "m";
        } else {
            easyCron = strs[2].split("/")[1] + "h";
        }
        return easyCron;
    }
}
