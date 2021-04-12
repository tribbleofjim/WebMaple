package org.webmaple.worker;

import org.springframework.stereotype.Component;
import org.webmaple.common.model.SpiderAdvance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lyifee
 * on 2020/12/27
 */
public class SpiderAdvanceCache {
    private static final ConcurrentHashMap<String, SpiderAdvance> ADVANCE_CACHE = new ConcurrentHashMap<>();

    public static void put(String keyword, SpiderAdvance spiderAdvance) {
        ADVANCE_CACHE.put(keyword, spiderAdvance);
    }

    public static SpiderAdvance get(String keyword) {
        return ADVANCE_CACHE.getOrDefault(keyword, null);
    }

    public static void remove(String keyword) {
        ADVANCE_CACHE.remove(keyword);
    }

    public static List<SpiderAdvance> getAdvanceList() {
        List<SpiderAdvance> list = new ArrayList<>();
        for (Map.Entry<String, SpiderAdvance> entry : ADVANCE_CACHE.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

}
