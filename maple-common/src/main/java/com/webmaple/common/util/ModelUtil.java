package com.webmaple.common.util;

import com.webmaple.common.model.SpiderDTO;
import us.codecraft.webmagic.Spider;

import java.util.Date;

/**
 * @author lyifee
 * on 2021/1/8
 */
public class ModelUtil {
    public static SpiderDTO getSpiderDO(Spider spider) {
        SpiderDTO spiderDTO = new SpiderDTO();
        spiderDTO.setUuid(spider.getUUID());
        spiderDTO.setIp("11.11.11.11");
        spiderDTO.setThreadNum(spider.getThreadAlive());
        spiderDTO.setState("running");
        return spiderDTO;
    }
}
