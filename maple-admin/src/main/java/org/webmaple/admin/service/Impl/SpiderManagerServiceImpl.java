package org.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.common.enums.SpiderState;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.admin.service.SpiderManageService;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;

import java.util.*;

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
    public Result<Void> createSpider(SpiderDTO spiderDTO) {
        Result<Void> result = new Result<>();
        return result.success("创建成功！");
    }

    @Override
    public Result<Void> startSpider(String uuid) {
        Result<Void> result = new Result<>();
        return result.success("启动成功！");
    }

    @Override
    public Result<Void> removeSpider(String uuid) {
        Result<Void> result = new Result<>();
        return result.success("删除成功！");
    }

    @Override
    public Result<Void> stopSpider(String uuid) {
        Result<Void> result = new Result<>();
        return result.success("暂停成功！");
    }

    @Override
    public Result<Void> modifySpider(Integer threadNum) {
        Result<Void> result = new Result<>();
        return result.success("修改成功！");
    }

    @Override
    public Result<List<SpiderDTO>> querySpiderList() {
        Result<List<SpiderDTO>> result = new Result<>();
        return result.success(mockSpiders());
    }

    private Result<Void> createSpiderFromWorker(SpiderDTO spiderDTO) {
        Result<Void> result = new Result<>();

        if (spiderDTO == null) {
            LOGGER.error("null_spiderDTO_create_spider");
            return result.fail("爬虫实体不能为空");
        }
        NodeDTO worker = workerContainer.getWorker(spiderDTO.getWorker());
        HashMap<String, Object> params = new HashMap<>();
        params.put("spiderDTO", spiderDTO);
        Request request = RequestUtil.postRequest(worker.getIp(), worker.getPort(), "createSpider", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }
        } catch (Exception e) {
            LOGGER.error("create_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> startSpiderFromWorker(String workerName, String uuid) {
        Result<Void> result = new Result<>();

        if (StringUtils.isBlank(workerName) || StringUtils.isBlank(uuid)) {
            LOGGER.error("null_param_start_spider");
            return result.fail("参数不能为空");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", uuid);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "startSpider", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            LOGGER.error("start_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> removeSpiderFromWorker(String workerName, String uuid) {
        Result<Void> result = new Result<>();

        if (StringUtils.isBlank(workerName) || StringUtils.isBlank(uuid)) {
            LOGGER.error("null_param_start_spider");
            return result.fail("参数不能为空");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", uuid);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "removeSpider", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            LOGGER.error("remove_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> stopSpiderFromWorker(String workerName, String uuid) {
        Result<Void> result = new Result<>();

        if (StringUtils.isBlank(workerName) || StringUtils.isBlank(uuid)) {
            LOGGER.error("null_param_start_spider");
            return result.fail("参数不能为空");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", uuid);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "stopSpider", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            LOGGER.error("stop_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private List<SpiderDTO> querySpiderListFromWorkers() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<SpiderDTO> spiderList = new ArrayList<>();
        for (NodeDTO worker : workers) {
            Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "spiderList", null);
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
        for (int i = 0; i < 8; i++) {
            SpiderDTO spiderDTO = new SpiderDTO();
            if (i % 3 == 0) {
                spiderDTO.setWorker("worker0");
                spiderDTO.setUuid("jd.com" + random.nextInt(20));
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=手机&suggest=1.def.0.base&wq=手机&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(1);
            } else if (i % 3 == 1) {
                spiderDTO.setWorker("worker2");
                spiderDTO.setUuid("jd.com" + random.nextInt(20));
                spiderDTO.setUrls(Arrays.asList("https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=5&s=116&click=0",
                        "https://search.jd.com/Search?keyword=家电&suggest=1.def.0.base&wq=家电&page=7&s=176&click=0"));
                spiderDTO.setState(SpiderState.RUNNING.getState());
                spiderDTO.setThreadNum(2);
            } else {
                spiderDTO.setWorker("worker1");
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
