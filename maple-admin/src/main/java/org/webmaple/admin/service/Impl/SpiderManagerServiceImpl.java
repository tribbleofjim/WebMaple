package org.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.webmaple.admin.container.BasicDataContainer;
import org.webmaple.admin.container.WorkerContainer;
//import org.webmaple.admin.mapper.SpiderMapper;
import org.webmaple.admin.model.MapleSpider;
import org.webmaple.common.enums.SpiderState;
import org.webmaple.common.model.*;
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
import org.webmaple.common.view.SpiderAdvanceView;
import us.codecraft.webmagic.Request;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Service
public class SpiderManagerServiceImpl implements SpiderManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderManageService.class);

    @Autowired
    private WorkerContainer workerContainer;

//    @Autowired
//    private SpiderMapper spiderMapper;

    @Autowired
    private RequestSender requestSender;

    @Override
    public Result<Void> createSpider(SpiderDTO spiderDTO) {
        return createSpiderFromWorker(spiderDTO);
//        Result<Void> result = new Result<>();
//        return result.success("创建成功！");
    }

    @Override
    public Result<Void> startSpider(String uuid, String worker) {
        return startSpiderFromWorker(uuid, worker);
//        Result<Void> result = new Result<>();
//        return result.success("启动成功！");
    }

    @Override
    public Result<Void> removeSpider(String uuid, String worker) {
        return removeSpiderFromWorker(uuid, worker);
//        Result<Void> result = new Result<>();
//        return result.success("删除成功！");
    }

    @Override
    public Result<Void> stopSpider(String uuid, String worker) {
        return stopSpiderFromWorker(uuid, worker);
//        Result<Void> result = new Result<>();
//        return result.success("暂停成功！");
    }

    @Override
    public Result<Void> modifySpider(Integer threadNum, String worker) {
        Result<Void> result = new Result<>();
        return result.success("修改成功！");
    }

    @Override
    public Result<List<SpiderDTO>> querySpiderList() {
        Result<List<SpiderDTO>> result = new Result<>();
        return result.success(querySpiderListFromWorkers());
//        Result<List<SpiderDTO>> result = new Result<>();
//        return result.success(mockSpiders());
    }

    @Override
    public Result<List<SpiderAdvanceView>> querySpiderAdvance() {
        Result<List<SpiderAdvanceView>> result = new Result<>();
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<SpiderAdvanceView> advanceList = new ArrayList<>();
        for (NodeDTO worker : workers) {
            Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "advance", null);
            HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
            try {
                CloseableHttpResponse response = requestSender.request(httpUriRequest);
                String text = RequestUtil.getResponseText(response);
                JSONObject spiderObject = JSON.parseObject(text);
                if (spiderObject == null || spiderObject.getJSONArray("model") == null) {
                    return result.success(new ArrayList<>());
                }
                List<SpiderAdvance> advances = spiderObject.getJSONArray("model").toJavaList(SpiderAdvance.class);
                List<SpiderAdvanceView> advanceViews = advances.stream().map(spiderAdvance -> {
                    SpiderAdvanceView spiderAdvanceView = new SpiderAdvanceView();
                    spiderAdvanceView.setWorker(worker.getName());
                    spiderAdvanceView.setUuid(spiderAdvance.getUuid());
                    spiderAdvanceView.setTarget(spiderAdvance.getPageNum());
                    spiderAdvanceView.setTemp(spiderAdvance.getTemp());
                    spiderAdvanceView.setPercent(((double)spiderAdvance.getTemp() / spiderAdvance.getPageNum()) * 100 + "%");
                    return spiderAdvanceView;
                }).collect(Collectors.toList());
                advanceList.addAll(advanceViews);

            } catch (Exception e) {
                LOGGER.error("query_spider_advance_exception:", e);
            }
        }
        return result.success(advanceList);
    }

    private Result<Void> createSpiderFromWorker(SpiderDTO spiderDTO) {
        Result<Void> result = new Result<>();
        if (spiderDTO == null) {
            LOGGER.error("null_spiderDTO_create_spider");
            return result.fail("爬虫实体不能为空");
        }

        NodeDTO worker = workerContainer.getWorker(spiderDTO.getWorker());
        String uuid = spiderDTO.getUuid();
        spiderDTO.setUuid(uuid + "_" + worker.getIdx());

        HashMap<String, Object> params = new HashMap<>();
        params.put("worker", spiderDTO.getWorker());
        params.put("urls", spiderDTO.getUrls());
        params.put("uuid", spiderDTO.getUuid());
        params.put("state", spiderDTO.getState());
        params.put("threadNum", spiderDTO.getThreadNum());
        params.put("processor", spiderDTO.getProcessor());
        params.put("pipeline", spiderDTO.getPipeline());
        params.put("downloader", spiderDTO.getDownloader());
        // params.put("spiderDTO", spiderDTO);
        Request request = RequestUtil.postRequest(worker.getIp(), worker.getPort(), "createSpider", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        try {
            CloseableHttpResponse response = requestSender.request(httpUriRequest);
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                MapleSpider mapleSpider = spiderDTO2MapleSpider(spiderDTO);
                // spiderMapper.createSpider(mapleSpider);
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }
        } catch (Exception e) {
            LOGGER.error("create_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> startSpiderFromWorker(String uuid, String workerName) {
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
                // spiderMapper.startSpider(uuid, workerName);
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            LOGGER.error("start_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> removeSpiderFromWorker(String uuid, String workerName) {
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
                // spiderMapper.removeSpider(uuid, workerName);
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            LOGGER.error("remove_spider_exception:", e);
            return result.fail("admin端错误");
        }
    }

    private Result<Void> stopSpiderFromWorker(String uuid, String workerName) {
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
//        List<MapleSpider> mapleSpiders = spiderMapper.spiderList();
//        List<SpiderDTO> spiderList = mapleSpiders.stream().map(spider -> {
//            SpiderDTO spiderDTO = new SpiderDTO();
//            spiderDTO.setWorker(spider.getWorker());
//            spiderDTO.setUuid(spider.getUuid());
//            spiderDTO.setUrls(JSONObject.parseArray(spider.getUrls()).toJavaList(String.class));
//            spiderDTO.setThreadNum(spider.getThreadNum());
//            spiderDTO.setDownloader(spider.getDownloader());
//            spiderDTO.setProcessor(spider.getProcessor());
//            spiderDTO.setPipeline(spider.getPipeline());
//            spiderDTO.setState(spider.getState());
//            return spiderDTO;
//        }).collect(Collectors.toList());
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
                LOGGER.error("query_spider_job_list_exception:", e);
            }
        }

        // spider num
        BasicDataContainer.setSpiderNum(spiderList.size());

        return spiderList;
    }

    private MapleSpider spiderDTO2MapleSpider(SpiderDTO spiderDTO) {
        if (spiderDTO == null) {
            return null;
        }
        MapleSpider mapleSpider = new MapleSpider();
        mapleSpider.setWorker(spiderDTO.getWorker());
        mapleSpider.setUrls(JSON.toJSONString(spiderDTO.getUrls()));
        mapleSpider.setUuid(spiderDTO.getUuid());
        mapleSpider.setState(spiderDTO.getState());
        mapleSpider.setThreadNum(spiderDTO.getThreadNum());
        mapleSpider.setDownloader(spiderDTO.getDownloader());
        mapleSpider.setProcessor(spiderDTO.getProcessor());
        mapleSpider.setPipeline(spiderDTO.getPipeline());
        return mapleSpider;
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
