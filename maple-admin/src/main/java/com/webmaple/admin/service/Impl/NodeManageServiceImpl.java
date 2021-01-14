package com.webmaple.admin.service.Impl;

import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.admin.service.NodeManageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/7
 */
@Service
public class NodeManageServiceImpl implements NodeManageService {
    @Override
    public List<NodeDTO> queryNodeList() {
        return mockNodeList();
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
