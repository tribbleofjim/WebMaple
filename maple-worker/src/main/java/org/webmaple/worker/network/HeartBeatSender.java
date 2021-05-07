package org.webmaple.worker.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;

import javax.annotation.PostConstruct;
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

    @Value("${webmaple.admin.host}")
    private String adminHost;

    @Value("${webmaple.admin.port}")
    private Integer adminPort;

    @Value("${webmaple.admin.heartbeat}")
    private String heartbeat;

    @Value("${webmaple.worker.port}")
    private Integer workerPort;

    @Scheduled(cron = "0 0/20 * * * ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    @PostConstruct // 在项目初始加载时即执行
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
            LOGGER.info("sending_heartbeat_request:{}", httpUriRequest.getURI());
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            String text = RequestUtil.getResponseText(response);
            JSONObject jsonObject = JSON.parseObject(text);

            String returnedWorkerName;
            if ((returnedWorkerName = jsonObject.getString("message")) != null) {
                setWorkerName(returnedWorkerName);
            }
        } catch (Exception e) {
            LOGGER.error("send_heartbeat_exception:", e);
        }
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
