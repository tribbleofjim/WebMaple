package org.webmaple.admin.service;

import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/7
 */
public interface NodeManageService {
    List<NodeDTO> queryNodeList();

    Result<Void> addWorker(String ip, String user, String password, Integer port, String fileName);

    void removeWorker(String workerName);

}
