package com.webmaple.admin.service;

import com.webmaple.common.model.SpiderDTO;
import com.webmaple.common.model.SpiderJobDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/12
 */
public interface TimedJobService {
    void createTimedSpider(SpiderDTO spiderDTO, String maintainType, int maintain, String cron);

    void pauseSpider(String jobName);

    void resumeSpider(String jobName);

    void deleteSpider(String jobName);

    List<SpiderJobDTO> queryTimedJobList();
}
