package com.webmaple.worker;

import com.webmaple.common.model.SpiderDTO;
import com.webmaple.common.util.ModelUtil;
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
    protected CountableThreadPool threadPool;

    protected int threadNum = 0;

    private static final int DEFAULT_THREAD_NUM = 5;

    public SpiderProcess() {
        init();
    }

    public void init() {
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

    public void startSpider(SpiderDTO spiderDTO) {
        Spider spider = ModelUtil.getSpiderFromDTO(spiderDTO);
        if (spider == null) {
            return;
        }
        SpiderContainer.put(spider.getUUID(), spiderDTO);
        run(spider);
    }

    private void run(Spider spider) {
        threadPool.execute(spider);
    }

    public List<SpiderDTO> getSpiders() {
        return null;
    }

    public void modifySpider(SpiderDTO spiderDTO) {

    }
}
