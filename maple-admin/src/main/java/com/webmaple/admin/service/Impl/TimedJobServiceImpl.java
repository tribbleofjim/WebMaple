package com.webmaple.admin.service.Impl;

import com.webmaple.admin.enums.MaintainType;
import com.webmaple.admin.model.JobDTO;
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
    public List<JobDTO> queryTimedJobList() {
        return mock();
    }

    private List<JobDTO> mock() {
        List<JobDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            JobDTO jobDTO = new JobDTO();
            if (i % 2 == 0) {
                jobDTO.setIp("144.34.188.64");
                jobDTO.setWorker("myworker1");
                jobDTO.setSpiderUUID("jd.com");
                jobDTO.setSpiderSite("jd.com");
                jobDTO.setType(MaintainType.URL_NUM.getType());
                jobDTO.setMaintain(1000);

            } else {
                jobDTO.setIp("101.37.89.200");
                jobDTO.setWorker("myworker2");
                jobDTO.setSpiderUUID("jd.com");
                jobDTO.setSpiderSite("jd.com");
                jobDTO.setType(MaintainType.TIME.getType());
                jobDTO.setMaintain(180);

            }
            list.add(jobDTO);
        }
        return list;
    }
}
