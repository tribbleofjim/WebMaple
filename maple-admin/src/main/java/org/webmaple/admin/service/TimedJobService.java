package org.webmaple.admin.service;

import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.common.model.SpiderJobDTO;
import org.webmaple.common.view.TimedSpiderView;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/12
 */
public interface TimedJobService {
    Result<Void> createTimedSpider(TimedSpiderView timedSpiderView);

    Result<Void> pauseSpider(String jobName, String worker);

    Result<Void> resumeSpider(String jobName, String worker);

    Result<Void> deleteSpider(String jobName, String worker);

    Result<List<SpiderJobDTO>> queryTimedJobList();
}
