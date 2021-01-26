package com.webmaple.admin.service;

import com.webmaple.common.model.SpiderDTO;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
public interface SpiderManageService {
    /**
     * create a spider
     * @param spiderDTO spider
     */
    void createSpider(SpiderDTO spiderDTO);

    /**
     * start a spider
     * @param spiderDTO spider
     */
    void startSpider(SpiderDTO spiderDTO);

    /**
     * remove a spider
     * @param uuid uuid of spider
     */
    void removeSpider(String uuid);

    /**
     * stop a spider
     * @param uuid uuid of spider
     */
    void stopSpider(String uuid);

    /**
     * query all spiders
     * @return spider list
     */
    List<SpiderDTO> querySpiderList();
}
