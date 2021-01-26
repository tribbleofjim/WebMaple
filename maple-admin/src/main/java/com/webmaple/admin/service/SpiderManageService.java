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
     * @param startUrls urls to start
     * @param threadNum thread num
     */
    void createSpider(String uuid, String processor, String downloader, String pipeline, List<String> startUrls, int threadNum);

    /**
     * delete a spider
     * @param uuid uuid of spider
     */
    void deleteSpider(String uuid);

    /**
     * query all spiders
     * @return spider list
     */
    List<SpiderDTO> querySpiderList();

    /**
     * get a spider by uuid
     * @param uuid uuid
     * @return spider
     */
    SpiderDTO getSpiderByUUID(String uuid);

    /**
     * get a spider by site root
     * @param site site root , such as http://jd.search.com
     * @return spider
     */
    SpiderDTO getSpiderBySite(String site);

    /**
     * modify thread num of a spider
     * @param threadNum thread num you want to set
     */
    void modifyThreadNum(int threadNum);

    /**
     * add urls to a spider
     * @param urls urls you want to set
     * @param uuid uuid of spider
     */
    void addUrls(List<String> urls, String uuid);

    /**
     * delete urls from a spider
     * @param urls urls you want to remove
     * @param uuid uuid of spider
     */
    void delUrls(List<String> urls, String uuid);
}
