package com.webmaple.admin.service;

import com.webmaple.common.model.SpiderJobDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/12
 */
public interface TimedJobService {
    List<SpiderJobDTO> queryTimedJobList();
}
