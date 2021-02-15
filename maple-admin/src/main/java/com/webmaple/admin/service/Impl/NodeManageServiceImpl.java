package com.webmaple.admin.service.Impl;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.admin.BeanConfig;
import com.webmaple.admin.container.WorkerContainer;
import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.admin.service.NodeManageService;
import com.webmaple.common.model.Result;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.util.CommonUtil;
import com.webmaple.common.util.RequestUtil;
import com.webmaple.common.util.SSHUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/7
 */
@Service
public class NodeManageServiceImpl implements NodeManageService {
    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private RequestSender requestSender;

    @Autowired
    private BeanConfig beanConfig;

    @Override
    public List<NodeDTO> queryNodeList() {
        return workerContainer.getWorkerList();
    }

    @Override
    public Result<Void> addWorker(String ip, String user, String password, Integer port, String fileName) {
        Result<Void> result = new Result<>();

        String jarPath = beanConfig.getJarPath();
        String filePath = CommonUtil.getFullFilePath(jarPath, fileName);
        File targetFile = new File(filePath);
        if (!targetFile.exists() || !targetFile.isFile() || !fileName.endsWith(".jar")) {
            return result.fail("jar包上传出现错误");
        }

        NodeDTO worker = new NodeDTO();
        worker.setType(NodeType.WORKER.getType());
        worker.setIp(ip);
        worker.setPort(port);
        String workerName = workerContainer.addWorker(worker);
        if (StringUtils.isBlank(workerName)) {
            return result.fail("节点类型错误或创建节点个数已达到最大上限");
        }

        Connection conn = SSHUtil.getConnection(ip, user, password);
        SSHUtil.uploadFile(conn, filePath);
        SSHUtil.execCommand(conn, "nohup java -jar " + fileName + " > temp.log 2>&1 &");
        SSHUtil.close(conn);

        HashMap<String, String > param = new HashMap<>();
        param.put("workerName", workerName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "setWorkerName", param);

        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            JSONObject responseResult = RequestUtil.getResponseObject(response);
            if (responseResult.getBoolean("success")) {
                return result.success();

            } else {
                shutdownWorkerServer(ip, user, password);
                workerContainer.remove(workerName);
                return result.fail("创建节点失败");
            }

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    // TODO: 删除服务器上已经在运行的节点
    private void shutdownWorkerServer(String ip, String user, String password) {
    }


    @Override
    public void removeWorker(String workerName) {
        workerContainer.remove(workerName);
    }

    List<NodeDTO> mockNodeList() {
        List<NodeDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            NodeDTO nodeDTO = new NodeDTO();
            if (i == 2) {
                nodeDTO.setType(NodeType.ADMIN.getType());
                nodeDTO.setIp("144.34.188.164");
                nodeDTO.setName("myAdmin");
            } else {
                nodeDTO.setType(NodeType.WORKER.getType());
                nodeDTO.setIp("101.89.18.39");
                nodeDTO.setName("myWorker");
            }
            list.add(nodeDTO);
        }
        return list;
    }
}
