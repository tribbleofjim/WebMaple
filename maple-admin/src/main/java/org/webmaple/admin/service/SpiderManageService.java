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
    Result<Void> startSpider(String uuid, String worker);

    /**
     * remove a spider
     * @param uuid uuid of spider
     */
    Result<Void> removeSpider(String uuid, String worker);

    /**
     * stop a spider
     * @param uuid uuid of spider
     */
    Result<Void> stopSpider(String uuid, String worker);

    /**
     * modify a spider
     * @param threadNum thread num
     */
    Result<Void> modifySpider(Integer threadNum, String worker);

    /**
     * query all spiders
     * @return spider list
     */
    Result<List<SpiderDTO>> querySpiderList();
}
