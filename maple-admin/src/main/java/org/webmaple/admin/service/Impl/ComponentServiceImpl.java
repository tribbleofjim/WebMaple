package org.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.admin.service.ComponentService;
import org.webmaple.common.enums.ComponentType;
import org.webmaple.common.model.ComponentDTO;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.network.RequestSender;
import org.webmaple.common.util.RequestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/24
 */
@Service
public class ComponentServiceImpl implements ComponentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentService.class);

    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private RequestSender requestSender;

    @Override
    public List<ComponentDTO> queryComponentList() {
        // return mockComponentList();
        return componentList();
    }

    private List<ComponentDTO> componentList() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        HashMap<String, ComponentDTO> componentMap = new HashMap<>();
        for (NodeDTO worker : workers) {
            List<ComponentDTO> list = queryComponentsFromWorker(worker);
            for (ComponentDTO component : list) {
                ComponentDTO tempComp;
                if (componentMap.containsKey(component.getName())) {
                    tempComp = componentMap.get(component.getName());
                    tempComp.setWorker(tempComp.getWorker() + "," + component.getWorker());
                } else {
                    componentMap.put(component.getName(), component);
                }
            }
        }
        return new ArrayList<>(componentMap.values());
    }

    private List<ComponentDTO> queryComponentsFromWorker(NodeDTO worker) {
        if (worker == null) {
            return null;
        }
        List<ComponentDTO> components = new ArrayList<>();
        Request request = RequestUtil.getRequest(worker.getIp(), worker.getPort(), "getComponents", null);
        try {
            CloseableHttpResponse response = requestSender.request(RequestUtil.getHttpUriRequest(request));
            String text = RequestUtil.getResponseText(response);
            JSONObject result = JSON.parseObject(text);
            JSONArray jsonArray = result.getJSONArray("model");
            for (Object o : jsonArray) {
                String componentStr = (String) o;
                if (StringUtils.isNotBlank(componentStr)) {
                    ComponentDTO component = JSON.parseObject(componentStr, ComponentDTO.class);
                    component.setWorker(worker.getName());
                    components.add(component);
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return components;
    }

    private List<ComponentDTO> mockComponentList() {
        List<ComponentDTO> componentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ComponentDTO componentDTO = new ComponentDTO();
            if (i == 0) {
                componentDTO.setType(ComponentType.DOWNLOADER.getType());
                componentDTO.setName("com.ecspider.downloader.SeleniumDownloader");
                componentDTO.setWorker("worker0,worker1");
            } else if (i == 1) {
                componentDTO.setType(ComponentType.PROCESSOR.getType());
                componentDTO.setName("com.ecspider.processor.JDProcessor");
                componentDTO.setSite("jd.com");
                componentDTO.setWorker("worker0,worker1");
            } else {
                componentDTO.setType(ComponentType.PIPELINE.getType());
                componentDTO.setName("com.ecspider.pipeline.JDPipeline");
                componentDTO.setSite("jd.com");
                componentDTO.setWorker("worker0,worker1");
            }
            componentList.add(componentDTO);
        }
        return componentList;
    }
}
