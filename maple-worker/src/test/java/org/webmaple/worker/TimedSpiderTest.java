package org.webmaple.worker;

import org.webmaple.common.enums.MaintainType;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.job.JobService;
import org.webmaple.worker.job.QuartzJobContainer;
import org.webmaple.worker.job.model.QuartzJob;
import org.webmaple.worker.util.ModelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/30
 */
@SpringBootTest(classes = WorkerApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimedSpiderTest {
    @Autowired
    private JobService jobService;

    @Autowired
    private ModelUtil modelUtil;

    private final SpiderDTO spiderDTO;

    public TimedSpiderTest () {
        spiderDTO = new SpiderDTO();
        spiderDTO.setDownloader("us.codecraft.webmagic.downloader.HttpClientDownloader");
        spiderDTO.setProcessor("us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor");
        spiderDTO.setPipeline("com.webmaple.worker.test.TestPipeline");
        spiderDTO.setThreadNum(1);
        List<String> urls = new ArrayList<>();
        urls.add("http://baike.baidu.com/search/word?word=%太阳能&pic=1&sug=1&enc=utf8");
        urls.add("http://baike.baidu.com/search/word?word=%地热能&pic=1&sug=1&enc=utf8");
        urls.add("http://baike.baidu.com/search/word?word=%风力发电&pic=1&sug=1&enc=utf8");
        urls.add("http://baike.baidu.com/search/word?word=%水力发电&pic=1&sug=1&enc=utf8");
        spiderDTO.setUrls(urls);
        spiderDTO.setUuid("baidu_test");
    }

    @Test
    public void createTimedTest() throws Exception {
        String maintainType = MaintainType.URL_NUM.getType();
        int maintain = 2;
        String cron = "1m";
        jobService.addJob(modelUtil.getQuartzJobForSpider(spiderDTO, maintainType, maintain, cron));
    }

    @Test
    public void pauseJobTest() throws SchedulerException {
        List<QuartzJob> jobList = QuartzJobContainer.getJobList();
        if (CollectionUtils.isNotEmpty(jobList)) {
            QuartzJob job = jobList.get(0);
            jobService.pauseJob(job.getJobName(), job.getJobClazz());
        }
    }
}
