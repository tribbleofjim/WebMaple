package org.webmaple.admin.service;

import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;

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
    Result<Void> createSpider(SpiderDTO spiderDTO);

    /**
     * start a spider
     * @param uuid uuid
     */
    Result<Void> startSpider(String uuid);

    /**
     * remove a spider
     * @param uuid uuid of spider
     */
    Result<Void> removeSpider(String uuid);

    /**
     * stop a spider
     * @param uuid uuid of spider
     */
    Result<Void> stopSpider(String uuid);

    /**
     * query all spiders
     * @return spider list
     */
    Result<List<SpiderDTO>> querySpiderList();
}
