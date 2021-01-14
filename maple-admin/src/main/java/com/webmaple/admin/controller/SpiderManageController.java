package com.webmaple.admin.controller;

import com.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
public interface SpiderManageController {
    /**
     * add a spider
     * @param urls urls to start
     * @param threadNum thread num
     * @return is success
     */
    Result addSpider(List<String> urls, int threadNum);

    /**
     * delete a spider
     * @param uuid uuid of spider
     * @return is success
     */
    Result deleteSpider(String uuid);

    /**
     * query all spiders
     * @return spider list
     */
    Result querySpiderList();

    /**
     * get a spider by uuid
     * @param uuid uuid
     * @return spider
     */
    Result getSpiderByUUID(String uuid);

    /**
     * get a spider by site root
     * @param site site root , such as http://jd.search.com
     * @return spider
     */
    Result getSpiderBySite(String site);

    /**
     * modify thread num of a spider
     * @param threadNum thread num you want to set
     * @return is success
     */
    Result modifyThreadNum(int threadNum);

    /**
     * add urls to a spider
     * @param urls urls you want to set
     * @param uuid uuid of spider
     * @return is success
     */
    Result addUrls(List<String> urls, String uuid);

    /**
     * delete urls from a spider
     * @param urls urls you want to remove
     * @param uuid uuid of spider
     * @return is success
     */
    Result delUrls(List<String> urls, String uuid);
}
