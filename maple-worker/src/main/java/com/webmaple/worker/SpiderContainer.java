package com.webmaple.worker;

import us.codecraft.webmagic.Spider;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lyifee
 * on 2021/1/14
 */
public class SpiderContainer {
    private static final ConcurrentHashMap<String, Spider> spiderMap = new ConcurrentHashMap<>();

    public static void put(String uuid, Spider spider) {
        spiderMap.put(uuid, spider);
    }

    public static Spider get(String uuid) {
        return spiderMap.get(uuid);
    }

    public static void remove(String uuid) {
        spiderMap.remove(uuid);
    }
}
