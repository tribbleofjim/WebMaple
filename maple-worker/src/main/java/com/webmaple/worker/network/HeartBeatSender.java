package com.webmaple.worker.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.network.RequestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;

import java.util.HashMap;

/**
 * 定时向admin节点发送心跳
 *
 * @author lyifee
 * on 2021/1/14
 */
@Configuration
@Component
public class HeartBeatSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatSender.class);

    private String workerName = null;

    @Autowired
    private RequestSender requestSender;

    @Value("${props.admin.host}")
    private String adminHost;

    @Value("${props.admin.port}")
    private Integer adminPort;

    @Value("${props.admin.heartbeat}")
    private String heartbeat;

    @Value("${props.worker.port}")
    private Integer workerPort;

    // @Scheduled(cron = "0 0/20 * * * ?")
    public void sendHeartBeat() {
        HashMap<String, String> params = new HashMap<>();
        if (workerName == null) {
            params.put("port", String.valueOf(workerPort));

        } else {
            params.put("workerName", workerName);
        }
        Request request = RequestUtil.getRequest(adminHost, adminPort, heartbeat, params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            String text = RequestUtil.getResponseText(response);
            JSONObject jsonObject = JSON.parseObject(text);

            String returnedWorkerName;
            if ((returnedWorkerName = jsonObject.getString("workerName")) != null) {
                setWorkerName(returnedWorkerName);
            }
        } catch (Exception e) {
            LOGGER.error("send_heartbeat_exception:", e);
        }
    }

    public String getWorkerName() {
        return workerName;
    }

    private void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
