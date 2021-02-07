package com.webmaple.common.util;

import com.webmaple.common.model.SpiderDTO;
import com.webmaple.common.view.SpiderView;

/**
 * @author lyifee
 * on 2021/1/26
 */
public class ModelConverter {
    public static SpiderDTO getSpiderDTOFromView(SpiderView spiderView) {
        SpiderDTO spiderDTO = new SpiderDTO();
        spiderDTO.setUuid(spiderView.getUuid());
        spiderDTO.setWorker(spiderView.getWorker());
        spiderDTO.setProcessor(spiderView.getProcessor());
        spiderDTO.setDownloader(spiderView.getDownloader());
        spiderDTO.setPipeline(spiderView.getPipeline());
        spiderDTO.setUrls(spiderView.getStartUrls());
        spiderDTO.setThreadNum(spiderView.getThreadNum());
        return spiderDTO;
    }
}
