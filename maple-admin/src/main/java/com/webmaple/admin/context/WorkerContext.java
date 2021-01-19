package com.webmaple.admin.context;

import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyifee
 * on 2021/1/18
 */
@Component
public class WorkerContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerContext.class);

    private static final HashMap<String, NodeDTO> WORKERS_MAP = new HashMap<>();

    private static int MAX_VALUE = 10;

    public WorkerContext() {
        try {
            MAX_VALUE = Integer.parseInt(ConfigUtil.getValueToString("application.yml", "props.workers.max-value"));
        } catch (Exception e) {
            LOGGER.error("worker_context_init_error:{}", e.getMessage());
        }
    }

    public static List<NodeDTO> getWorkerList() {
        List<NodeDTO> list = new ArrayList<>();
        for (Map.Entry<String, NodeDTO> entry : WORKERS_MAP.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static void addWorker(NodeDTO worker) {
        if (WORKERS_MAP.size() >= MAX_VALUE || !worker.getType().equals(NodeType.WORKER.getType())) {
            return;
        }
        WORKERS_MAP.put(worker.getName(), worker);
    }

    public static NodeDTO getWorker(String name) {
        return WORKERS_MAP.get(name);
    }

    public static void remove(String workerName) {
        WORKERS_MAP.remove(workerName);
    }

    public static int size() {
        return WORKERS_MAP.size();
    }
}
