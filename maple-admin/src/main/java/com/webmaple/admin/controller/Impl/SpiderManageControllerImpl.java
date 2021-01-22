package com.webmaple.admin.controller.Impl;

import com.webmaple.admin.controller.SpiderManageController;
import com.webmaple.common.enums.CommonErrorCode;
import com.webmaple.common.model.Result;
import com.webmaple.admin.service.SpiderManageService;
import com.webmaple.common.model.SpiderDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
@Controller
public class SpiderManageControllerImpl implements SpiderManageController {
    @Resource
    private SpiderManageService spiderManageService;

    @Override
    @RequestMapping("/addSpider")
    @ResponseBody
    public Result<Void> addSpider(@RequestParam List<String> urls,
                            @RequestParam int threadNum) {
        Result<Void> result = new Result<>();

        if (CollectionUtils.isEmpty(urls)) {
            return result.fail(CommonErrorCode.NULL_PARAM);
        }
        spiderManageService.addSpider(urls, threadNum);
        return result.success();
    }

    @Override
    @RequestMapping("/deleteSpider")
    @ResponseBody
    public Result<Void> deleteSpider(@RequestParam String uuid) {
        Result<Void> result = new Result<>();

        if (StringUtils.isBlank(uuid)) {
            return result.fail(CommonErrorCode.NULL_PARAM);
        }
        spiderManageService.deleteSpider(uuid);
        return result.success();
    }

    @Override
    @RequestMapping("/querySpiderList")
    @ResponseBody
    public Result<List<SpiderDTO>> querySpiderList() {
        Result<List<SpiderDTO>> result = new Result<>();
        return result.success(spiderManageService.querySpiderList());
    }

    @Override
    @RequestMapping("/getSpiderByUUID")
    @ResponseBody
    public Result<SpiderDTO> getSpiderByUUID(String uuid) {
        Result<SpiderDTO> result = new Result<>();
        if (StringUtils.isBlank(uuid)) {
            return result.fail(CommonErrorCode.NULL_PARAM);
        }
        return result.success(spiderManageService.getSpiderByUUID(uuid));
    }

    @Override
    public Result<SpiderDTO> getSpiderBySite(String site) {
        return null;
    }

    @Override
    public Result<Void> modifyThreadNum(int threadNum) {
        return null;
    }

    @Override
    public Result<Void> addUrls(List<String> urls, String uuid) {
        return null;
    }

    @Override
    public Result<Void> delUrls(List<String> urls, String uuid) {
        return null;
    }
}
