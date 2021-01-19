package com.webmaple.admin.controller.Impl;

import com.alibaba.fastjson.JSON;
import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.*;
import com.webmaple.admin.service.NodeManageService;
import com.webmaple.admin.service.SpiderManageService;
import com.webmaple.admin.service.TimedJobService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Controller
public class CommonController {
    @Resource
    private SpiderManageService spiderManageService;

    @Resource
    private NodeManageService nodeManageService;

    @Resource
    private TimedJobService timedJobService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/spider")
    public String spider() {
        return "spider";
    }

    @RequestMapping("/node")
    public String node() {
        return "node";
    }

    @RequestMapping("/timed")
    public String timed() {
        return "timed";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/spiderList")
    @ResponseBody
    public DataTableDTO querySpiderList(@RequestParam int page, @RequestParam int limit) {
        List<SpiderDTO> spiderList = spiderManageService.querySpiderList();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(spiderList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(spiderList.size());
        }
        dataTableDTO.setData(JSON.toJSON(spiderList));
        return dataTableDTO;
    }

    @RequestMapping("/nodeList")
    @ResponseBody
    public DataTableDTO queryNodeList(@RequestParam int page, @RequestParam int limit) {
        List<NodeDTO> nodeList = nodeManageService.queryNodeList();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(nodeList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(nodeList.size());
        }
        dataTableDTO.setData(JSON.toJSON(nodeList));
        return dataTableDTO;
    }

    @RequestMapping("/addWorker")
    @ResponseBody
    public Result addWorker(@RequestParam String ip, @RequestParam String name) {
        assert StringUtils.isNotBlank(ip);
        assert StringUtils.isNotBlank(name);

        NodeDTO worker = new NodeDTO();
        worker.setType(NodeType.WORKER.getType());
        worker.setIp(ip);
        worker.setName(name);
        nodeManageService.addWorker(worker);
        return Result.success();
    }

    @RequestMapping("removeWorker")
    @ResponseBody
    public Result removeWorker(@RequestParam String name) {
        assert StringUtils.isNotBlank(name);

        nodeManageService.removeWorker(name);
        return Result.success();
    }

    @RequestMapping("/timedJobs")
    @ResponseBody
    public DataTableDTO queryTimedJobsList(@RequestParam int page, @RequestParam int limit) {
        List<JobDTO> jobList = timedJobService.queryTimedJobList();
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
