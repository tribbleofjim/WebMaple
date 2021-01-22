package com.webmaple.worker.controller;

import com.webmaple.common.enums.MaintainType;
import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.job.JobService;
import com.webmaple.worker.job.model.QuartzJob;
import com.webmaple.worker.util.ModelUtil;
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
    public Result createTimedSpider(@RequestParam SpiderDTO spiderDTO,
                                    @RequestParam String maintainType,
                                    @RequestParam int maintain,
                                    @RequestParam String cron) {
        if (spiderDTO == null) {
            return Result.fail("spiderDTO_cannot_be_null");
        }
        if (MaintainType.getType(maintainType) == null) {
            return Result.fail("invalid_maintain_type", maintainType);
        }

        try {
            jobService.addJob(modelUtil.getQuartzJobForSpider(spiderDTO, maintainType, maintain, cron));
            return Result.success();

        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @RequestMapping("/timedSpiderList")
    @ResponseBody
    public Result timedSpiderList() {
        List<QuartzJob> quartzJobs = jobService.getAllJobs();
        return Result.success(quartzJobs);
    }

    @RequestMapping("/pauseSpider")
    @ResponseBody
    public Result pauseSpider(@RequestParam String jobName) {
        try {
            jobService.pauseJob(jobName, "com.webmaple.worker.job.spider.SpiderJob");
            return Result.success();

        } catch (Exception e) {
            LOGGER.error("pause_spider_exception:", e);
            return Result.fail(e.getMessage());
        }
    }

    @RequestMapping("/resumeSpider")
    @ResponseBody
    public Result resumeSpider(@RequestParam String jobName) {
        try {
            jobService.resumeJob(jobName, "com.webmaple.worker.job.spider.SpiderJob");
            return Result.success();

        } catch (Exception e) {
            LOGGER.error("resume_spider_exception:", e);
            return Result.fail(e.getMessage());
        }
    }
}
