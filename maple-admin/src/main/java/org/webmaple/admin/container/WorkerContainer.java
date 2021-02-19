package org.webmaple.admin.container;

import org.webmaple.admin.util.RedisUtil;
import org.webmaple.common.enums.NodeType;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lyifee
 * on 2021/1/18
 */
@Component
public class WorkerContainer implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerContainer.class);

    @Autowired
    private RedisUtil redisUtil;

    private static boolean[] IDX_SET;

    private static ReentrantLock IDX_LOCK = new ReentrantLock();

    private static int MAX_VALUE = 10;

    private static long EXPIRE_TIME = 1800;

    private static int INDEX_DB = 0;

    private static String WORKER_PREFIX = "worker";

    public WorkerContainer() {
        // try to init max_value
        try {
            MAX_VALUE = Integer.parseInt(ConfigUtil.getValueToString("application.yml", "props.workers.max-value"));
        } catch (Exception e) {
            LOGGER.error("worker_context_init_error:{}", e.getMessage());
        }
        IDX_SET = new boolean[MAX_VALUE];
        Arrays.fill(IDX_SET, false);
    }

    public List<NodeDTO> getWorkerList() {
        List<NodeDTO> list = new ArrayList<>();
        for (int i = 0; i < MAX_VALUE; i++) {
            if (IDX_SET[i]) {
                String worker;
                if ((worker = redisUtil.get(WORKER_PREFIX + i, INDEX_DB)) != null) {
                    list.add(NodeDTO.fromString(worker));
                }
            }
        }
        return list;
    }

    public String addWorker(NodeDTO worker) {
        if (tempSize() >= MAX_VALUE || !worker.getType().equals(NodeType.WORKER.getType())) {
            return null;
        }
        setWorkerIdxAndName(worker);
        redisUtil.set(worker.getName(), worker.toString(), EXPIRE_TIME, INDEX_DB);
        IDX_SET[worker.getIdx()] = true;
        return worker.getName();
    }

    public NodeDTO getWorker(String name) {
        String worker = redisUtil.get(name, INDEX_DB);
        if (worker == null) {
            return null;
        }
        return NodeDTO.fromString(worker);
    }

    public void aliveWorker(String workerName) {
        String workerStr = redisUtil.get(workerName, INDEX_DB);
        if (workerStr != null) {
            NodeDTO worker = NodeDTO.fromString(workerStr);
            redisUtil.set(worker.getName(), worker.toString(), EXPIRE_TIME, INDEX_DB);
        }
    }

    public void remove(String workerName) {
        NodeDTO worker = getWorker(workerName);
        if (worker == null) {
            return;
        }
        redisUtil.del(workerName, INDEX_DB);
        IDX_SET[worker.getIdx()] = false;
    }

    public int size() {
        return tempSize();
    }

    private void setWorkerIdxAndName(NodeDTO worker) {
        IDX_LOCK.lock();
        try {
            for (int i = 0; i < MAX_VALUE; i++) {
                if (!IDX_SET[i]) {
                    worker.setIdx(i);
                    worker.setName(WORKER_PREFIX + i);
                    return;
                }
            }
            throw new RuntimeException("worker_num_out_of_max_value");

        } finally {
            IDX_LOCK.unlock();
        }
    }

    private int tempSize() {
        int res = 0;
        for (int i = 0; i < MAX_VALUE; i++) {
            if (IDX_SET[i]) {
                res++;
            }
        }
        return res;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
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
}
