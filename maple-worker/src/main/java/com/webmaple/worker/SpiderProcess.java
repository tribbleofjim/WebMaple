package com.webmaple.worker;

import com.webmaple.worker.dao.SpiderDAO;
import com.webmaple.worker.dao.model.SpiderDO;
import com.webmaple.worker.util.ModelUtil;
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
    private SpiderDAO spiderDAO;

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

    public void run(Spider spider) {
        SpiderDO spiderDO = ModelUtil.getSpiderDO(spider);
        spiderDAO.insertSpider(spiderDO);
        threadPool.execute(spider);
    }

    public List<SpiderDO> getSpiders() {
        return spiderDAO.querySpiderList();
    }

    public void modifySpider(SpiderDO spiderDO) {

    }
}
