package com.webmaple.worker.network;

import com.webmaple.common.network.RequestSender;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;

/**
 * 定时向admin节点发送心跳
 *
 * @author lyifee
 * on 2021/1/14
 */
@Component
public class HeartBeatSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatSender.class);

    @Autowired
    private RequestSender requestSender;

    public void sendHeartBeat() {
        Request request = new Request("http://101.37.89.200:8080/heartbeat");
        HttpUriRequest httpUriRequest = requestSender.getHttpUriRequest(request);
        try {
            requestSender.request(httpUriRequest);
        } catch (Exception e) {
            LOGGER.error("send_heartbeat_exception:", e);
        }
    }
}
