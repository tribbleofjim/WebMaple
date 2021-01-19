package com.webmaple.worker;

import com.webmaple.common.model.SpiderDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lyifee
 * on 2021/1/14
 */
public class SpiderContainer {
    private static final ConcurrentHashMap<String, SpiderDTO> SPIDER_MAP = new ConcurrentHashMap<>();

    public static List<SpiderDTO> getSpiderList() {
        List<SpiderDTO> spiderDTOS = new ArrayList<>();
        for (Map.Entry<String, SpiderDTO> entry : SPIDER_MAP.entrySet()) {
            spiderDTOS.add(entry.getValue());
        }
        return spiderDTOS;
    }

    public static void put(String uuid, SpiderDTO spider) {
        SPIDER_MAP.put(uuid, spider);
    }

    public static SpiderDTO get(String uuid) {
        return SPIDER_MAP.get(uuid);
    }

    public static void remove(String uuid) {
        SPIDER_MAP.remove(uuid);
    }
}
