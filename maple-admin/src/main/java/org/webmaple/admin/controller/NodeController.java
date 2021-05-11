package org.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.webmaple.admin.BeanConfig;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.admin.service.NodeManageService;
import org.webmaple.common.model.DataTableDTO;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;
import org.webmaple.common.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/2/13
 */
@Controller
public class NodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeController.class);

    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private NodeManageService nodeManageService;

    @Autowired
    private BeanConfig beanConfig;

    @RequestMapping("/workers")
    @ResponseBody
    public List<String> workers() {
        List<String> list = new ArrayList<>();
        List<NodeDTO> workers = workerContainer.getWorkerList();
        for (NodeDTO worker : workers) {
            list.add(worker.getName());
        }
        return list;
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

    @PostMapping("/addWorker")
    @ResponseBody
    public Result<Void> addWorker(@RequestParam String ip, @RequestParam String user,
                                  @RequestParam String password, @RequestParam Integer port, @RequestParam String fileName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(ip) || StringUtils.isBlank(user) || StringUtils.isBlank(password)
                || StringUtils.isBlank(fileName) || port == null) {
            return result.fail("参数为空");
        }
        return nodeManageService.addWorker(ip, user, password, port, fileName);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public DataTableDTO upload(@RequestParam MultipartFile file) {
        String targetFilePath = beanConfig.getJarPath();
        String fileName = file.getOriginalFilename();
        File targetFile = new File(CommonUtil.getFullFilePath(targetFilePath, fileName));

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(file.getInputStream(), fileOutputStream);
            LOGGER.info("------>>>>>>uploaded a file successfully!<<<<<<------");

        } catch (Exception e) {
            LOGGER.error(e.getMessage());

        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.close();
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(200);
        dataTableDTO.setMsg("success");
        dataTableDTO.setData("upload file");
        return dataTableDTO;
    }

    @RequestMapping("/removeWorker")
    @ResponseBody
    public Result<Void> removeWorker(@RequestParam String name) {
        assert StringUtils.isNotBlank(name);
        nodeManageService.removeWorker(name);
        Result<Void> result = new Result<>();
        return result.success();
    }
}
