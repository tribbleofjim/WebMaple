package com.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.admin.container.SpiderProcessContainer;
import com.webmaple.common.enums.SpiderState;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.admin.service.SpiderManageService;
import com.webmaple.common.model.SpiderProcessDTO;
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

/**
 * @author lyifee
 * on 2021/1/6
 */
@Service
public class SpiderManagerServiceImpl implements SpiderManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderManageService.class);

    @Autowired
    private RequestSender requestSender;

    @Override
    public void addSpider(List<String> urls, int threadNum) {

    }

    @Override
    public void deleteSpider(String uuid) {

    }

    @Override
    public List<SpiderDTO> querySpiderList() {
        // return querySpiderListFromWorkers();
        return mockSpiders();
    }

    @Override
    public SpiderDTO getSpiderByUUID(String uuid) {
        return null;
    }

    @Override
    public SpiderDTO getSpiderBySite(String site) {
        return null;
    }

    @Override
    public void modifyThreadNum(int threadNum) {

    }

    @Override
    public void addUrls(List<String> urls, String uuid) {

    }

    @Override
    public void delUrls(List<String> urls, String uuid) {

    }

    private List<SpiderDTO> querySpiderListFromWorkers() {
        List<SpiderProcessDTO> spiderProcesses = SpiderProcessContainer.getSpiderProcesses();
        List<SpiderDTO> spiderList = new ArrayList<>();
        for (SpiderProcessDTO spiderProcess : spiderProcesses) {
            Request request = new Request(RequestUtil.getRequest(spiderProcess.getIp(), spiderProcess.getPort(), "spiderList", null));
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
        for (int i = 0; i < 20; i++) {
            SpiderDTO spiderDTO = new SpiderDTO();
            if (i < 8) {
                spiderDTO.setIp("101.87.19.29");
                spiderDTO.setUuid("https://jd.search.com");
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(1);
            } else if (i < 16) {
                spiderDTO.setIp("144.34.288.164");
                spiderDTO.setUuid("https://jd.search.com");
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(4);
            } else {
                spiderDTO.setIp("101.87.19.29");
                spiderDTO.setUuid("https://jd.search.com");
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
