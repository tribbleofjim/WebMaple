package com.webmaple.admin.service.Impl;

import com.webmaple.admin.model.SpiderDTO;
import com.webmaple.admin.service.SpiderManageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Service
public class SpiderManagerServiceImpl implements SpiderManageService {
    @Override
    public void addSpider(List<String> urls, int threadNum) {

    }

    @Override
    public void deleteSpider(String uuid) {

    }

    @Override
    public List<SpiderDTO> querySpiderList() {
        return null;
    }

    @Override
    public SpiderDTO getSpiderByUUID(String uuid) {
        return null;
    }

    @Override
    public SpiderDTO getSpiderBySite(String site) {
        return null;
    }

    @Override
    public void modifyThreadNum(int threadNum) {

    }

    @Override
    public void addUrls(List<String> urls, String uuid) {

    }

    @Override
    public void delUrls(List<String> urls, String uuid) {

    }
}
