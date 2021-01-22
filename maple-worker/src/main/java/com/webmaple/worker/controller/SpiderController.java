package com.webmaple.worker.controller;

import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.SpiderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/19
 */
@Controller
public class SpiderController {
    @Autowired
    private SpiderProcess spiderProcess;

    @RequestMapping("/createSpider")
    @ResponseBody
    public Result<Void> createSpider(@RequestParam SpiderDTO spiderDTO) {
        spiderProcess.createSpider(spiderDTO);
        Result<Void> result = new Result<>();
        return result.success();
    }

    @RequestMapping("/startSpider")
    @ResponseBody
    public Result<Void> startSpider(@RequestParam SpiderDTO spiderDTO) {
        spiderProcess.startSpider(spiderDTO);
        Result<Void> result = new Result<>();
        return result.success();
    }

    @RequestMapping("/removeSpider")
    @ResponseBody
    public Result<Void> removeSpider(@RequestParam String uuid) {
        spiderProcess.removeSpider(uuid);
        Result<Void> result = new Result<>();
        return result.success();
    }

    @RequestMapping("/stopSpider")
    @ResponseBody
    public Result<Void> stopSpider(@RequestParam String uuid) {
        spiderProcess.stopSpider(uuid);
        Result<Void> result = new Result<>();
        return result.success();
    }

    @RequestMapping("/spiderList")
    @ResponseBody
    public Result<List<SpiderDTO>> spiderList() {
        List<SpiderDTO> spiders = spiderProcess.getSpiders();
        Result<List<SpiderDTO>> result = new Result<>();
        return result.success(spiders);
    }
}