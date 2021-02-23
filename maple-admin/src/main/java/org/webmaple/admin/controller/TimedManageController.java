package org.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.admin.service.TimedJobService;
import org.webmaple.common.model.DataTableDTO;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderJobDTO;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.webmaple.common.util.CommonUtil;
import org.webmaple.common.view.TimedSpiderView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Request;

import java.util.HashMap;
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
        return timedJobService.createTimedSpider(timedSpiderView);
    }

    @GetMapping("/resumeTimedSpider")
    @ResponseBody
    public Result<Void> resumeTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
        return timedJobService.resumeSpider(jobName, worker);
    }

    @GetMapping("/pauseTimedSpider")
    @ResponseBody
    public Result<Void> pauseTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
        return timedJobService.pauseSpider(jobName, worker);
    }

    @GetMapping("/deleteTimedSpider")
    @ResponseBody
    public Result<Void> deleteTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
        return timedJobService.deleteSpider(jobName, worker);
    }

    @RequestMapping("/timedJobs")
    @ResponseBody
    public DataTableDTO queryTimedJobsList(@RequestParam int page, @RequestParam int limit) {
        Result<List<SpiderJobDTO>> result = timedJobService.queryTimedJobList();
        List<SpiderJobDTO> jobList = result.getModel();
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
