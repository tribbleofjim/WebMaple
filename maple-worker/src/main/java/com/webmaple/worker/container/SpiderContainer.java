package com.webmaple.worker.container;

import com.webmaple.common.enums.SpiderState;
import com.webmaple.common.model.SpiderDTO;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Spider;

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

    private static final ConcurrentHashMap<String, Spider> EXECUTING_SPIDER_MAP = new ConcurrentHashMap<>();

    public static List<SpiderDTO> getSpiderList() {
        List<SpiderDTO> spiderDTOS = new ArrayList<>();
        for (Map.Entry<String, SpiderDTO> entry : SPIDER_MAP.entrySet()) {
            SpiderDTO spiderDTO = entry.getValue();
            if (EXECUTING_SPIDER_MAP.containsKey(entry.getKey())) {
                spiderDTO.setState(SpiderState.RUNNING.getState());
            } else {
                spiderDTO.setState(SpiderState.STOP.getState());
            }
            spiderDTOS.add(entry.getValue());
        }
        return spiderDTOS;
    }

    public static void createSpider(String uuid, SpiderDTO spider) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        SPIDER_MAP.put(uuid, spider);
    }

    public static SpiderDTO getSpiderDTO(String uuid) {
        return SPIDER_MAP.get(uuid);
    }

    public static void removeSpiderDTO(String uuid) {
        SPIDER_MAP.remove(uuid);
    }

    public static void runSpider(Spider spider) {
        EXECUTING_SPIDER_MAP.put(spider.getUUID(), spider);
    }

    public static Spider getSpider(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return null;
        }
        return EXECUTING_SPIDER_MAP.get(uuid);
    }

    public static void stopSpider(String uuid) {
        Spider spider = EXECUTING_SPIDER_MAP.get(uuid);
        if (spider == null) {
            return;
        }
        spider.stop();
    }
}
