package org.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.webmaple.admin.container.BasicDataContainer;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.common.enums.JobState;
import org.webmaple.common.enums.MaintainType;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderJobDTO;
import org.webmaple.admin.service.TimedJobService;
import org.springframework.stereotype.Service;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.webmaple.common.view.TimedSpiderView;
import us.codecraft.webmagic.Request;

import java.util.*;

/**
 * @author lyifee
 * on 2021/1/12
 */
@Service
public class TimedJobServiceImpl implements TimedJobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedJobServiceImpl.class);

    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private RequestSender requestSender;

    @Override
    public Result<Void> createTimedSpider(TimedSpiderView timedSpiderView) {
        return createTimedSpiderFromWorker(timedSpiderView);
//        Result<Void> result = new Result<>();
//        return result.success("创建成功！");
    }

    @Override
    public Result<Void> pauseSpider(String jobName, String worker) {
        return pauseTimedSpiderFromWorker(jobName, worker);
//        Result<Void> result = new Result<>();
//        return result.success("暂停成功！");
    }

    @Override
    public Result<Void> resumeSpider(String jobName, String worker) {
        return resumeTimedSpiderFromWorker(jobName, worker);
//        Result<Void> result = new Result<>();
//        return result.success("重启成功！");
    }

    @Override
    public Result<Void> deleteSpider(String jobName, String worker) {
        return deleteTimedSpiderFromWorker(jobName, worker);
//        Result<Void> result = new Result<>();
//        return result.success("删除成功！");
    }

    @Override
    public Result<List<SpiderJobDTO>> queryTimedJobList() {
        return queryTimedJobListFromWorkers();
//        Result<List<SpiderJobDTO>> result = new Result<>();
//        return result.success(mock());
    }

    private Result<Void> createTimedSpiderFromWorker(TimedSpiderView timedSpiderView) {
        Result<Void> result = new Result<>();
        if (timedSpiderView == null) {
            return result.fail("定时任务不能为空！");
        }
        String workerName = timedSpiderView.getWorker();
        if (StringUtils.isBlank(workerName)) {
            return result.fail("节点名称不能为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("uuid", timedSpiderView.getUuid());
        param.put("maintainType", timedSpiderView.getType());
        param.put("maintain", timedSpiderView.getMaintain());
        String rawCron = timedSpiderView.getCronNum() + timedSpiderView.getCronUnit();
        param.put("cron", rawCron);
        Request request = RequestUtil.postRequest(worker.getIp(), worker.getPort(), "createTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> resumeTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "resumeTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> pauseTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "pauseTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }
        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<Void> deleteTimedSpiderFromWorker(String jobName, String workerName) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(jobName) || StringUtils.isBlank(workerName)) {
            return result.fail("定时任务名或节点名为空！");
        }
        NodeDTO worker = workerContainer.getWorker(workerName);
        if (worker == null) {
            return result.fail("无效的节点名称，请更换一个节点");
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("jobName", jobName);
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "deleteTimedSpider", param);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            JSONObject responseObject = RequestUtil.getResponseObject(response);
            if (responseObject.getBoolean("success")) {
                return result.success(responseObject.getString("message"));
            } else {
                return result.fail(responseObject.getString("message"));
            }

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }

    private Result<List<SpiderJobDTO>> queryTimedJobListFromWorkers() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<SpiderJobDTO> spiderJobList = new ArrayList<>();
        for (NodeDTO worker : workers) {
            Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "timedSpiderList", null);
            HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
            try {
                CloseableHttpResponse response = requestSender.request(httpUriRequest);
                String text = RequestUtil.getResponseText(response);
                JSONObject spiderObject = JSON.parseObject(text);
                List<SpiderJobDTO> spiders = toSpiderJobList(spiderObject.getJSONArray("model"), worker.getName());
                spiderJobList.addAll(spiders);

            } catch (Exception e) {
                LOGGER.error("query_spider_job_list_exception:", e);
            }
        }

        // timed spider num
        BasicDataContainer.setTimedJobNum(spiderJobList.size());

        Result<List<SpiderJobDTO>> result = new Result<>();
        return result.success(spiderJobList);
    }

    private List<SpiderJobDTO> toSpiderJobList(JSONArray quartzJobs, String workerName) {
        List<SpiderJobDTO> list = new ArrayList<>();
        for (Object quartzJob : quartzJobs) {
            try {
                JSONObject job = JSON.parseObject(JSONObject.toJSONString(quartzJob));
                SpiderJobDTO spiderJobDTO = new SpiderJobDTO();
                spiderJobDTO.setJobName(job.getString("jobName"));
                spiderJobDTO.setCronExpression(job.getString("cronExpression"));
                spiderJobDTO.setJobClazz(job.getString("jobClazz"));
                spiderJobDTO.setStartTime(job.getString("startTime"));
                spiderJobDTO.setState(job.getBoolean("executing") ? JobState.RUNNING.getState() : JobState.STOP.getState());
                spiderJobDTO.setWorker(workerName);
                spiderJobDTO.setIp(workerContainer.getWorker(workerName).getIp());

                JSONObject extraInfo = job.getJSONObject("extraInfo");
                spiderJobDTO.setSpiderUUID(extraInfo.getString("uuid"));
                spiderJobDTO.setType((extraInfo.containsKey("maintainUrlNum")) ? MaintainType.URL_NUM.getType() : MaintainType.TIME.getType());
                spiderJobDTO.setMaintain((extraInfo.containsKey("maintainUrlNum")) ? extraInfo.getInteger("maintainUrlNum") : extraInfo.getInteger("maintainTime"));

                list.add(spiderJobDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private List<SpiderJobDTO> mock() {
        List<SpiderJobDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SpiderJobDTO spiderJobDTO = new SpiderJobDTO();
            if (i % 2 == 0) {
                spiderJobDTO.setJobName("job_24");
                spiderJobDTO.setWorker("worker2");
                spiderJobDTO.setSpiderUUID("jd.com");
                spiderJobDTO.setType(MaintainType.URL_NUM.getType());
                spiderJobDTO.setState(JobState.RUNNING.getState());
                spiderJobDTO.setMaintain(1000);
                spiderJobDTO.setCronExpression("0 0 0/2 * * ?");

            } else {
                spiderJobDTO.setJobName("job_12");
                spiderJobDTO.setWorker("worker1");
                spiderJobDTO.setSpiderUUID("jd.com");
                spiderJobDTO.setType(MaintainType.TIME.getType());
                spiderJobDTO.setState(JobState.STOP.getState());
                spiderJobDTO.setMaintain(180);
                spiderJobDTO.setCronExpression("0 0 0/5 * * ?");

            }
            list.add(spiderJobDTO);
        }
        return list;
    }
}
