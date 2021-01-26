package com.webmaple.admin.controller.Impl;

import com.webmaple.admin.controller.SpiderManageController;
import com.webmaple.common.enums.CommonErrorCode;
import com.webmaple.common.model.Result;
import com.webmaple.admin.service.SpiderManageService;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.common.util.ModelConverter;
import com.webmaple.common.view.SpiderView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/createSpider")
    @ResponseBody
    public Result<Void> createSpider(SpiderView spiderView) {
        Result<Void> result = new Result<>();

        if (spiderView == null || CollectionUtils.isEmpty(spiderView.getStartUrls())) {
            return result.fail(CommonErrorCode.NULL_PARAM);
        }
        spiderManageService.createSpider(ModelConverter.getSpiderDTOFromView(spiderView));
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
