package com.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import com.webmaple.admin.service.TimedJobService;
import com.webmaple.common.model.DataTableDTO;
import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderJobDTO;
import com.webmaple.common.view.TimedSpiderView;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/timedJobs")
    @ResponseBody
    public DataTableDTO queryTimedJobsList(@RequestParam int page, @RequestParam int limit) {
        List<SpiderJobDTO> jobList = timedJobService.queryTimedJobList();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(jobList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(jobList.size());
        }
        dataTableDTO.setData(JSON.toJSON(jobList));
        return dataTableDTO;
    }

}
