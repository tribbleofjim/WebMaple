package com.webmaple.worker.util;

import com.webmaple.worker.dao.model.SpiderDO;
import us.codecraft.webmagic.Spider;

import java.util.Date;

/**
 * @author lyifee
 * on 2021/1/8
 */
public class ModelUtil {
    public static SpiderDO getSpiderDO(Spider spider) {
        SpiderDO spiderDO = new SpiderDO();
        spiderDO.setUuid(spider.getUUID());
        spiderDO.setIp("11.11.11.11");
        spiderDO.setThreadNum(spider.getThreadAlive());
        spiderDO.setState("running");
        spiderDO.setCreateDate(new Date());
        spiderDO.setUpdateDate(new Date());
        return spiderDO;
    }
}
