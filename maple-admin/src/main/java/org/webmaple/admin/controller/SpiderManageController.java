package org.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;
import org.webmaple.common.model.DataTableDTO;
import org.webmaple.common.model.Result;
import org.webmaple.admin.service.SpiderManageService;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.common.util.ModelConverter;
import org.webmaple.common.view.SpiderAdvanceView;
import org.webmaple.common.view.SpiderView;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/5
 */
@Controller
public class SpiderManageController {
    @Resource
    private SpiderManageService spiderManageService;

    @PostMapping("/createSpider")
    @ResponseBody
    public Result<Void> createSpider(SpiderView spiderView) {
        return spiderManageService.createSpider(ModelConverter.getSpiderDTOFromView(spiderView));
    }

    @GetMapping("/startSpider")
    @ResponseBody
    public Result<Void> startSpider(@RequestParam String uuid, @RequestParam String worker) {
        return spiderManageService.startSpider(uuid, worker);
    }

    @PostMapping("/modifySpider")
    @ResponseBody
    public Result<Void> modifySpider(@RequestParam Integer threadNum, @RequestParam String worker) {
        return spiderManageService.modifySpider(threadNum, worker);
    }

    @RequestMapping("/deleteSpider")
    @ResponseBody
    public Result<Void> deleteSpider(@RequestParam String uuid, @RequestParam String worker) {
        return spiderManageService.removeSpider(uuid, worker);
    }

    @RequestMapping("/stopSpider")
    @ResponseBody
    public Result<Void> stopSpider(@RequestParam String uuid, @RequestParam String worker) {
        return spiderManageService.stopSpider(uuid, worker);
    }

    @RequestMapping("/spiderList")
    @ResponseBody
    public DataTableDTO querySpiderList(@RequestParam int page, @RequestParam int limit) {
        Result<List<SpiderDTO>> result = spiderManageService.querySpiderList();
        List<SpiderDTO> spiderList = result.getModel();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(spiderList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(spiderList.size());
        }
        dataTableDTO.setData(JSON.toJSON(spiderList));
        return dataTableDTO;
    }

    @RequestMapping("/spiderAdvance")
    @ResponseBody
    public DataTableDTO querySpiderAdvance(@RequestParam int page, @RequestParam int limit) {
        List<SpiderAdvanceView> advanceViewList = spiderManageService.querySpiderAdvance().getModel();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(advanceViewList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(advanceViewList.size());
        }
        dataTableDTO.setData(JSON.toJSON(advanceViewList));
        return dataTableDTO;
    }
}
