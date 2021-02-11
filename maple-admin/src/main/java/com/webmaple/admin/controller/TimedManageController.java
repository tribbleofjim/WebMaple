package com.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import com.webmaple.admin.container.WorkerContainer;
import com.webmaple.admin.service.TimedJobService;
import com.webmaple.common.model.DataTableDTO;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderJobDTO;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.network.RequestUtil;
import com.webmaple.common.view.TimedSpiderView;
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

    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private RequestSender requestSender;

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

    private Result<Void> createTimedSpiderFromWorker(TimedSpiderView timedSpiderView) {
        Result<Void> result = new Result<>();
        if (timedSpiderView == null) {
            return result.fail("定时任务不能为空！");
        }
        String workerName = timedSpiderView.getWorker();
        if (StringUtils.isBlank(workerName)) {
            return result.fail("节点名称不能为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("spiderDTO", timedSpiderView.getUuid());
        Request request = RequestUtil.postRequest(worker.getIp(), worker.getPort(), "createTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            String text = RequestUtil.getResponseText(response);
            return result.success(text);

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
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
