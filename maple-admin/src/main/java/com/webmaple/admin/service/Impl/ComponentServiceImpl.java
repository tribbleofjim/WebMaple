package com.webmaple.admin.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webmaple.admin.container.WorkerContainer;
import com.webmaple.admin.service.ComponentService;
import com.webmaple.common.enums.ComponentType;
import com.webmaple.common.model.ComponentDTO;
import com.webmaple.common.model.NodeDTO;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.network.RequestUtil;
import com.webmaple.common.util.CommonUtil;
import com.webmaple.common.util.SerializeUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;

import java.util.ArrayList;
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
        return mockComponentList();
    }

    private List<ComponentDTO> componentList() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<ComponentDTO> res = new ArrayList<>();
        for (NodeDTO worker : workers) {
            List<ComponentDTO> list = queryComponentsFromWorker(worker);
            res.addAll(list);
        }
        return res;
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
            JSONArray jsonArray = JSON.parseArray(text);

            for (Object o : jsonArray) {
                try {
                    String componentStr = (String) o;
                    ComponentDTO component = (ComponentDTO) SerializeUtil.deserialize(componentStr);
                    components.add(component);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
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
            } else if (i == 1) {
                componentDTO.setType(ComponentType.PROCESSOR.getType());
                componentDTO.setName("com.ecspider.processor.JDProcessor");
                componentDTO.setSite("jd.com");
            } else {
                componentDTO.setType(ComponentType.PIPELINE.getType());
                componentDTO.setName("com.ecspider.pipeline.JDPipeline");
                componentDTO.setSite("jd.com");
            }
            componentList.add(componentDTO);
        }
        return componentList;
    }
}
