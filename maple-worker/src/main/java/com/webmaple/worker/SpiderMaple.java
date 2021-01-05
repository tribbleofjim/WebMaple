package com.webmaple.worker;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.thread.CountableThreadPool;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * @author lyifee
 * on 2021/1/5
 */
public class SpiderMaple {
    protected CopyOnWriteArrayList<Spider> spiderList = new CopyOnWriteArrayList<>();

    protected CountableThreadPool threadPool;

    public SpiderMaple(int threadNum) {
        threadPool = new CountableThreadPool(threadNum);
    }

    public SpiderMaple(int threadNum, ExecutorService executorService) {
        threadPool = new CountableThreadPool(threadNum, executorService);
    }

    public void run(Spider spider) {
        spiderList.add(spider);
        threadPool.execute(spider);
    }

    public List<Spider> getSpiders() {
        return Arrays.asList(spiderList.toArray(new Spider[0]));
    }
}
