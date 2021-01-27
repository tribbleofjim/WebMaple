package com.webmaple.admin.controller;

import com.webmaple.admin.service.TimedJobService;
import com.webmaple.common.model.Result;
import com.webmaple.common.view.TimedSpiderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/1/27
 */
@Controller
public class TimedManageController {
    @Autowired
    private TimedJobService timedJobService;

    @PostMapping("/createTimedSpider")
    @ResponseBody
    public Result<Void> createTimedSpider(TimedSpiderView timedSpiderView) {
        System.out.println(timedSpiderView);
        Result<Void> result = new Result<>();
        return result.success("添加成功！");
    }

    @GetMapping("/resumeTimedSpider")
    @ResponseBody
    public Result<Void> resumeTimedSpider(String jobName) {
        System.out.println(jobName);
        Result<Void> result = new Result<>();
        return result.success("重新启动成功！");
    }

    @GetMapping("/pauseTimedSpider")
    @ResponseBody
    public Result<Void> pauseTimedSpider(String jobName) {
        System.out.println(jobName);
        Result<Void> result = new Result<>();
        return result.success("暂停成功！");
    }

    @GetMapping("/deleteTimedSpider")
    @ResponseBody
    public Result<Void> deleteTimedSpider(String jobName) {
        System.out.println(jobName);
        Result<Void> result = new Result<>();
        return result.success("删除成功！");
    }

}
