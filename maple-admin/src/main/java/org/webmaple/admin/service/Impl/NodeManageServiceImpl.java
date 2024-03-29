package org.webmaple.admin.service.Impl;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson.JSONObject;
import org.webmaple.admin.BeanConfig;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.common.enums.NodeType;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.admin.service.NodeManageService;
import org.webmaple.common.model.Result;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.CommonUtil;
import org.webmaple.common.util.RequestUtil;
import org.webmaple.common.util.SSHUtil;
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

        Connection conn = SSHUtil.getConnection(ip, user, password);
        SSHUtil.uploadFile(conn, filePath);
        SSHUtil.execCommand(conn, "nohup java -jar " + fileName + " > temp.log 2>&1 &");
        SSHUtil.close(conn);

        return result.success("创建节点成功！");
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
