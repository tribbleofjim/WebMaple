package com.webmaple.admin.service;

import com.webmaple.common.model.JobDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/12
 */
public interface TimedJobService {
    List<JobDTO> queryTimedJobList();
}
