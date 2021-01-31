package com.webmaple.admin.container;

import com.webmaple.common.enums.NodeType;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lyifee
 * on 2021/1/18
 */
@Component
public class WorkerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerContainer.class);

    private static final HashMap<String, NodeDTO> WORKERS_MAP = new HashMap<>();

    private static boolean[] IDX_SET;

    private static int MAX_VALUE = 10;

    public WorkerContainer() {
        // try to init max_value
        try {
            MAX_VALUE = Integer.parseInt(ConfigUtil.getValueToString("application.yml", "props.workers.max-value"));
        } catch (Exception e) {
            LOGGER.error("worker_context_init_error:{}", e.getMessage());
        }
        IDX_SET = new boolean[MAX_VALUE];
        Arrays.fill(IDX_SET, false);

        // mock
        NodeDTO worker1 = new NodeDTO();
        worker1.setName("worker1");
        worker1.setIp("144.34.188.164");
        worker1.setType(NodeType.WORKER.getType());
        NodeDTO worker2 = new NodeDTO();
        worker2.setName("worker2");
        worker2.setIp("101.37.89.200");
        worker2.setType(NodeType.WORKER.getType());
        NodeDTO worker3 = new NodeDTO();
        worker3.setName("worker3");
        worker3.setIp("101.33.188.66");
        worker3.setType(NodeType.WORKER.getType());
        addWorker(worker1);
        addWorker(worker2);
        addWorker(worker3);
    }

    private static void setWorkerIdxAndName(NodeDTO worker) {
        for (int i = 0; i < MAX_VALUE; i++) {
            if (!IDX_SET[i]) {
                worker.setIdx(i);
                worker.setName("worker" + i);
            }
        }
        throw new RuntimeException("worker_num_out_of_max_value");
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
        setWorkerIdxAndName(worker);
        WORKERS_MAP.put(worker.getName(), worker);
    }

    public static NodeDTO getWorker(String name) {
        return WORKERS_MAP.get(name);
    }

    public static void remove(String workerName) {
        NodeDTO worker = WORKERS_MAP.get(workerName);
        if (worker == null) {
            return;
        }
        WORKERS_MAP.remove(workerName);
        IDX_SET[worker.getIdx()] = false;
    }

    public static int size() {
        return WORKERS_MAP.size();
    }
}
