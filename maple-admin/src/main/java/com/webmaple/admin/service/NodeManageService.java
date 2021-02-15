package com.webmaple.admin.service;

import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/7
 */
public interface NodeManageService {
    List<NodeDTO> queryNodeList();

    Result<Void> addWorker(String ip, String user, String password, String filePath, String fileName);

    void removeWorker(String workerName);

}
