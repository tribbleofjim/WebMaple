package com.webmaple.worker;

import com.webmaple.common.model.SpiderDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/29
 */
@SpringBootTest(classes = WorkerApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpiderTest {
    @Autowired
    private SpiderProcess spiderProcess;

    private final SpiderDTO spiderDTO;

    public SpiderTest () {
        spiderDTO = new SpiderDTO();
        spiderDTO.setDownloader("us.codecraft.webmagic.downloader.HttpClientDownloader");
        spiderDTO.setProcessor("us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor");
        spiderDTO.setPipeline("com.webmaple.worker.test.TestPipeline");
        spiderDTO.setThreadNum(1);
        List<String> urls = new ArrayList<>();
        urls.add("http://baike.baidu.com/search/word?word=%太阳能&pic=1&sug=1&enc=utf8");
        spiderDTO.setUrls(urls);
        spiderDTO.setUuid("baidu_test");
    }

    @Order(1)
    @Test
    public void createSpiderTest() {
        spiderProcess.createSpider(spiderDTO);
    }

    @Order(2)
    @Test
    public void startSpiderTest() {
        spiderProcess.startSpider("baidu_test");
    }

    @Order(3)
    @Test
    public void spiderListTest() {
        List<SpiderDTO> spiders = spiderProcess.getSpiders();
        for (SpiderDTO spiderDTO : spiders) {
            System.out.println(spiderDTO);
        }
    }

    @Order(4)
    @Test
    public void stopSpider() {
        spiderProcess.stopSpider(spiderDTO.getUuid());
    }

    @Order(5)
    @Test
    public void removeSpider() {
        spiderProcess.removeSpider(spiderDTO.getUuid());
    }
}
