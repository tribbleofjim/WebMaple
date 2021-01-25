package com.webmaple.admin.service.Impl;

import com.webmaple.admin.service.ComponentService;
import com.webmaple.common.enums.ComponentType;
import com.webmaple.common.model.ComponentDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/24
 */
@Service
public class ComponentServiceImpl implements ComponentService {

    @Override
    public List<ComponentDTO> queryComponentList() {
        return mockComponentList();
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
