package com.webmaple.worker;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.thread.CountableThreadPool;
import java.util.List;

/**
 * run a spider
 * and store information of all spiders
 * basically one spring boot project
 * a worker can have more than one spider process
 *
 * @author lyifee
 * on 2021/1/5
 */
public class SpiderProcess {
    protected CountableThreadPool threadPool;

    protected int threadNum = 0;

    private static final int DEFAULT_THREAD_NUM = 5;

    public SpiderProcess(int threadNum) {
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            threadPool = new CountableThreadPool(DEFAULT_THREAD_NUM);
        } else {
            threadPool = new CountableThreadPool(threadNum);
        }
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void run(Spider spider) {
        threadPool.execute(spider);
    }

    public List<Spider> getSpiders() {
        return null;
    }
}
