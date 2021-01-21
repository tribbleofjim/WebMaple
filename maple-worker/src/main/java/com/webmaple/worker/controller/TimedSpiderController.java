package com.webmaple.worker.controller;

import com.webmaple.common.enums.MaintainType;
import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.job.JobService;
import com.webmaple.worker.job.spider.TimedSpider;
import com.webmaple.worker.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/1/21
 */
@Controller
public class TimedSpiderController {
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
}
