package com.webmaple.admin.service;

import com.webmaple.admin.model.NodeDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/7
 */
public interface NodeManageService {
    List<NodeDTO> queryNodeList();
}
