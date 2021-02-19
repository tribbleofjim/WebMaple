package org.webmaple.admin.service;

import org.webmaple.common.model.ComponentDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/24
 */
public interface ComponentService {
    List<ComponentDTO> queryComponentList();
}
