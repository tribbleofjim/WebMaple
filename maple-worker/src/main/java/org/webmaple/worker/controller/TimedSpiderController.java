package org.webmaple.worker.controller;

import org.webmaple.common.enums.MaintainType;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.container.SpiderContainer;
import org.webmaple.worker.job.JobService;
import org.webmaple.worker.job.model.QuartzJob;
import org.webmaple.worker.util.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/21
 */
@Controller
public class TimedSpiderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedSpiderController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private ModelUtil modelUtil;

    @RequestMapping("/createTimedSpider")
    @ResponseBody
    public Result<Void> createTimedSpider(@RequestParam String uuid,
                                    @RequestParam String maintainType,
                                    @RequestParam int maintain,
                                    @RequestParam String cron) {
        Result<Void> result = new Result<>();

        if (StringUtils.isBlank(uuid)) {
            return result.fail("spider_uuid_cannot_be_null");

        }
        if (MaintainType.getType(maintainType) == null) {
            return result.fail("invalid_maintain_type");
        }
        SpiderDTO spiderDTO = SpiderContainer.getSpiderDTO(uuid);
        if (spiderDTO == null) {
            return result.fail("在该节点上找不到对应的爬虫");
        }

        try {
            jobService.addJob(modelUtil.getQuartzJobForSpider(spiderDTO, maintainType, maintain, cron));
            return result.success();

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    @RequestMapping("/timedSpiderList")
    @ResponseBody
    public Result<List<QuartzJob>> timedSpiderList() {
        Result<List<QuartzJob>> result = new Result<>();
        List<QuartzJob> quartzJobs = jobService.getAllJobs();
        return result.success(quartzJobs);
    }

    @RequestMapping("/pauseSpider")
    @ResponseBody
    public Result<Void> pauseSpider(@RequestParam String jobName) {
        Result<Void> result = new Result<>();
        try {
            jobService.pauseJob(jobName, "org.webmaple.worker.job.spider.SpiderJob");
            return result.success();

        } catch (Exception e) {
            LOGGER.error("pause_spider_exception:", e);
            return result.fail(e.getMessage());
        }
    }

    @RequestMapping("/resumeSpider")
    @ResponseBody
    public Result<Void> resumeSpider(@RequestParam String jobName) {
        Result<Void> result = new Result<>();
        try {
            jobService.resumeJob(jobName, "org.webmaple.worker.job.spider.SpiderJob");
            return result.success();

        } catch (Exception e) {
            LOGGER.error("resume_spider_exception:", e);
            return result.fail(e.getMessage());
        }
    }

    @RequestMapping("/deleteSpider")
    @ResponseBody
    public Result<Void> deleteSpider(@RequestParam String jobName) {
        Result<Void> result = new Result<>();
        try {
            jobService.deleteJob(jobName, "org.webmaple.worker.job.spider.SpiderJob");
            return result.success();

        } catch (Exception e) {
            LOGGER.error("delete_spider_exception:", e);
            return result.fail(e.getMessage());
        }
    }
}
