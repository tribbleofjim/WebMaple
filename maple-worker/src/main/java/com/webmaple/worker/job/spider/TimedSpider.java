package com.webmaple.worker.job.spider;

import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 定时spider
 * 提供可定时停止spider的接口
 * 停止规则有两种：
 * 1、启动后经过一定时间后停止爬取（maintainTime, 单位为minutes）
 * 2、启动后爬取一定数目URL后停止爬取（urlNum）
 * 如果二者都设置，默认选取urlNum
 *
 * @author lyifee
 * on 2021/1/12
 */
public class TimedSpider {
    private final Spider spider;

    private int maintainTime = -1;

    private int maintainUrlNum = -1;

    public TimedSpider(Spider spider) {
        this.spider = spider;
    }

    public TimedSpider maintainTime(int maintainTime) {
        this.maintainTime = maintainTime;
        return this;
    }

    public TimedSpider maintainUrls(int maintainUrlNum) {
        this.maintainUrlNum = maintainUrlNum;
        return this;
    }

    public int getMaintainTime() {
        return maintainTime;
    }

    public int getMaintainUrlNum() {
        return maintainUrlNum;
    }

    public void executeSpider() {
        if (spider == null) {
            return;
        }

        // spider already executed and stopped
        if (Spider.Status.Stopped.equals(spider.getStatus())) {
            spider.start();
            return;
        }

        // the first time spider executed
        if (maintainUrlNum > 0) {
            UrlNumListener urlNumListener = new UrlNumListener(spider, maintainUrlNum);
            spider.setSpiderListeners(new ArrayList<>(Collections.singletonList(urlNumListener)));

        } else if (maintainTime > 0) {
            TimeListener timeListener = new TimeListener(spider, maintainTime);
            spider.setSpiderListeners(new ArrayList<>(Collections.singletonList(timeListener)));

        } else {
            UrlNumListener urlNumListener = new UrlNumListener(spider);
            spider.setSpiderListeners(new ArrayList<>(Collections.singletonList(urlNumListener)));
        }
        spider.run();
    }
}
