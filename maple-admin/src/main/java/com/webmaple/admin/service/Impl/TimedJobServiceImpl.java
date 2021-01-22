package com.webmaple.admin.service.Impl;

import com.webmaple.common.enums.MaintainType;
import com.webmaple.common.model.SpiderJobDTO;
import com.webmaple.admin.service.TimedJobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/12
 */
@Service
public class TimedJobServiceImpl implements TimedJobService {
    @Override
    public List<SpiderJobDTO> queryTimedJobList() {
        return mock();
    }

    private List<SpiderJobDTO> mock() {
        List<SpiderJobDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SpiderJobDTO spiderJobDTO = new SpiderJobDTO();
            if (i % 2 == 0) {
                spiderJobDTO.setIp("144.34.188.64");
                spiderJobDTO.setWorker("myworker1");
                spiderJobDTO.setSpiderUUID("jd.com");
                spiderJobDTO.setSpiderSite("jd.com");
                spiderJobDTO.setType(MaintainType.URL_NUM.getType());
                spiderJobDTO.setMaintain(1000);

            } else {
                spiderJobDTO.setIp("101.37.89.200");
                spiderJobDTO.setWorker("myworker2");
                spiderJobDTO.setSpiderUUID("jd.com");
                spiderJobDTO.setSpiderSite("jd.com");
                spiderJobDTO.setType(MaintainType.TIME.getType());
                spiderJobDTO.setMaintain(180);

            }
            list.add(spiderJobDTO);
        }
        return list;
    }
}
