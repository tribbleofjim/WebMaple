package com.webmaple.admin.container;

import com.webmaple.common.model.SpiderProcessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/25
 */
public class SpiderProcessContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderProcessContainer.class);

    private static final HashSet<SpiderProcessDTO> spiderProcesses = new HashSet<>();

    public static void addSpiderProcess(SpiderProcessDTO spiderProcessDTO) {
        spiderProcesses.add(spiderProcessDTO);
    }

    public static void removeSpiderProcess(SpiderProcessDTO spiderProcessDTO) {
        spiderProcesses.remove(spiderProcessDTO);
    }

    public static List<SpiderProcessDTO> getSpiderProcesses() {
        return new ArrayList<>(spiderProcesses);
    }
}
