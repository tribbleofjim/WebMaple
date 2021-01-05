package com.webmaple.admin.controller.Impl;

import com.webmaple.admin.controller.SpiderManageController;
import com.webmaple.admin.enums.CommonErrorCode;
import com.webmaple.admin.model.Result;
import com.webmaple.admin.service.SpiderManageService;
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
    public Result addSpider(@RequestParam List<String> urls,
                            @RequestParam int threadNum) {
        if (CollectionUtils.isEmpty(urls)) {
            return Result.fail(CommonErrorCode.NULL_PARAM);
        }
        spiderManageService.addSpider(urls, threadNum);
        return Result.success();
    }

    @Override
    @RequestMapping("/deleteSpider")
    @ResponseBody
    public Result deleteSpider(@RequestParam String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return Result.fail(CommonErrorCode.NULL_PARAM);
        }
        spiderManageService.deleteSpider(uuid);
        return Result.success();
    }

    @Override
    @RequestMapping("/querySpiderList")
    @ResponseBody
    public Result querySpiderList() {
        return Result.success(spiderManageService.querySpiderList());
    }

    @Override
    @RequestMapping("/getSpiderByUUID")
    public Result getSpiderByUUID(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return Result.fail(CommonErrorCode.NULL_PARAM);
        }
        return Result.success(spiderManageService.getSpiderByUUID(uuid));
    }

    @Override
    public Result getSpiderBySite(String site) {
        return null;
    }

    @Override
    public Result modifyThreadNum(int threadNum) {
        return null;
    }

    @Override
    public Result addUrls(List<String> urls, String uuid) {
        return null;
    }

    @Override
    public Result delUrls(List<String> urls, String uuid) {
        return null;
    }
}
