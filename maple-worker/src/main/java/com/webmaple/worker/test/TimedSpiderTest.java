package com.webmaple.worker.test;

import com.webmaple.common.enums.MaintainType;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.job.JobService;
import com.webmaple.worker.util.ModelUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/30
 */
@Component
public class TimedSpiderTest implements InitializingBean {
    @Autowired
    private JobService jobService;

    @Autowired
    private ModelUtil modelUtil;

    @Override
    public void afterPropertiesSet() throws Exception {
        SpiderDTO spiderDTO = new SpiderDTO();
        spiderDTO.setDownloader("us.codecraft.webmagic.downloader.HttpClientDownloader");
        spiderDTO.setProcessor("us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor");
        spiderDTO.setPipeline("com.webmaple.worker.test.TestPipeline");
        spiderDTO.setThreadNum(1);
        List<String> urls = new ArrayList<>();
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
        urls.add(String.format(urlTemplate,"风力发电"));
        urls.add(String.format(urlTemplate,"太阳能"));
        urls.add(String.format(urlTemplate,"地热发电"));
        urls.add(String.format(urlTemplate,"水力发电"));
        spiderDTO.setUrls(urls);
        spiderDTO.setUuid("baidu_test");

        String maintainType = MaintainType.URL_NUM.getType();
        int maintain = 2;
        String cron = "1m";
        jobService.addJob(modelUtil.getQuartzJobForSpider(spiderDTO, maintainType, maintain, cron));
    }
}
