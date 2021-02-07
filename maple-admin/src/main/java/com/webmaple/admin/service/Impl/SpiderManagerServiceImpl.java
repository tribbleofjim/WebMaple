package com.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.admin.container.WorkerContainer;
import com.webmaple.common.enums.SpiderState;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.admin.service.SpiderManageService;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.network.RequestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Service
public class SpiderManagerServiceImpl implements SpiderManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderManageService.class);

    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private RequestSender requestSender;

    @Override
    public void createSpider(SpiderDTO spiderDTO) {
        System.out.println("spiderDTO:" + spiderDTO);
    }

    @Override
    public void startSpider(SpiderDTO spiderDTO) {

    }

    @Override
    public void removeSpider(String uuid) {

    }

    @Override
    public void stopSpider(String uuid) {

    }

    @Override
    public List<SpiderDTO> querySpiderList() {
        // return querySpiderListFromWorkers();
        return mockSpiders();
    }

    private void startSpiderFromWorker(SpiderDTO spiderDTO) {

    }

    private List<SpiderDTO> querySpiderListFromWorkers() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<SpiderDTO> spiderList = new ArrayList<>();
        for (NodeDTO worker : workers) {
            Request request = new Request(RequestUtil.getRequest(worker.getIp(), worker.getPort(), "spiderList", null));
            HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
            try {
                CloseableHttpResponse response = requestSender.request(httpUriRequest);
                String text = RequestUtil.getResponseText(response);
                JSONObject spiderObject = JSON.parseObject(text);
                List<SpiderDTO> spiders = spiderObject.getJSONArray("model").toJavaList(SpiderDTO.class);
                spiderList.addAll(spiders);

            } catch (Exception e) {
                LOGGER.error("query_spider_list_exception:", e);
            }
        }
        return spiderList;
    }

    private List<SpiderDTO> mockSpiders() {
        List<SpiderDTO> spiderDTOS = new ArrayList<>();
        Random random = new Random(20);
        for (int i = 0; i < 20; i++) {
            SpiderDTO spiderDTO = new SpiderDTO();
            if (i < 8) {
                spiderDTO.setWorker("101.87.19.29");
                spiderDTO.setUuid("jd.com" + random.nextInt(20));
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(1);
            } else if (i < 16) {
                spiderDTO.setWorker("144.34.288.164");
                spiderDTO.setUuid("jd.com" + random.nextInt(20));
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(4);
            } else {
                spiderDTO.setWorker("101.87.19.29");
                spiderDTO.setUuid("jd.com" + random.nextInt(20));
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.STOP.getState());
                spiderDTO.setThreadNum(3);
            }
            spiderDTOS.add(spiderDTO);
        }
        return spiderDTOS;
    }
}
