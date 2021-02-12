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
import com.webmaple.common.util.CommonUtil;
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
    public Result<Void> resumeTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
        System.out.println(jobName);
        Result<Void> result = new Result<>();
        return result.success("重新启动成功！");
    }

    @GetMapping("/pauseTimedSpider")
    @ResponseBody
    public Result<Void> pauseTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
        System.out.println(jobName);
        Result<Void> result = new Result<>();
        return result.success("暂停成功！");
    }

    @GetMapping("/deleteTimedSpider")
    @ResponseBody
    public Result<Void> deleteTimedSpider(@RequestParam String jobName, @RequestParam String worker) {
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
        param.put("uuid", timedSpiderView.getUuid());
        param.put("maintainType", timedSpiderView.getType());
        param.put("maintain", timedSpiderView.getMaintain());
        String rawCron = timedSpiderView.getCronNum() + timedSpiderView.getCronUnit();
        param.put("cron", CommonUtil.getCron(rawCron));
        Request request = RequestUtil.postRequest(worker.getIp(), worker.getPort(), "createTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            String text = RequestUtil.getResponseText(response);
            return result.success(text);

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> resumeTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "resumeTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            String text = RequestUtil.getResponseText(response);
            return result.success(text);

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> pauseTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "pauseTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            String text = RequestUtil.getResponseText(response);
            return result.success(text);

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> deleteTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "deleteTimedSpider", param);
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
