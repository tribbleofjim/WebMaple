package com.webmaple.worker;

import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.util.ModelUtil;
import com.webmaple.worker.container.SpiderContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.thread.CountableThreadPool;
import java.util.List;

/**
 * run a spider
 * and manage information of all spiders
 * basically one spring boot project
 * a worker can have more than one spider process
 *
 * @author lyifee
 * on 2021/1/5
 */
@Component
public class SpiderProcess {
    @Autowired
    private ModelUtil modelUtil;

    protected CountableThreadPool threadPool;

    protected int threadNum = 0;

    private static final int DEFAULT_THREAD_NUM = 5;

    public SpiderProcess() {
        init();
    }

    private void init() {
        if (threadNum <= 0) {
            threadPool = new CountableThreadPool(DEFAULT_THREAD_NUM);
        } else {
            threadPool = new CountableThreadPool(threadNum);
        }
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public void createSpider(SpiderDTO spiderDTO) {
        if (spiderDTO == null || StringUtils.isBlank(spiderDTO.getUuid())) {
            return;
        }
        SpiderContainer.createSpider(spiderDTO.getUuid(), spiderDTO);
    }

    public void startSpider(SpiderDTO spiderDTO) {
        Spider spider = modelUtil.getSpiderFromDTO(spiderDTO);
        if (spider == null) {
            return;
        }
        if (SpiderContainer.getSpiderDTO(spiderDTO.getUuid()) == null) {
            createSpider(spiderDTO);
        }
        run(spider);
    }

    private void run(Spider spider) {
        threadPool.execute(spider);
    }

    public List<SpiderDTO> getSpiders() {
        return SpiderContainer.getSpiderList();
    }

    public void modifySpider(SpiderDTO spiderDTO) {

    }

    public void removeSpider(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        SpiderContainer.removeSpiderDTO(uuid);
    }

    public void stopSpider(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        SpiderContainer.stopSpider(uuid);
    }
}
