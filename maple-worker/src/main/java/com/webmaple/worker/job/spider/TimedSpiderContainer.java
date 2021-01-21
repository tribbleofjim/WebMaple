package com.webmaple.worker.job.spider;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TimedSpider的容器
 * 由于Job是无状态的，需要将爬虫的状态保存在容器中
 * 这里采用直接保存TimedSpider的方式
 *
 * @author lyifee
 * on 2021/1/14
 */
public class TimedSpiderContainer {
    private static final ConcurrentHashMap<String, TimedSpider> timedSpiderMap = new ConcurrentHashMap<>();

    public static void put(String uuid, TimedSpider timedSpider) {
        timedSpiderMap.put(uuid, timedSpider);
    }

    public static TimedSpider get(String uuid) {
        return timedSpiderMap.getOrDefault(uuid, null);
    }

    public static void remove(String uuid) {
        timedSpiderMap.remove(uuid);
    }
}
