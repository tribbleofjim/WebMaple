package com.webmaple.worker.controller;

import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderDTO;
import com.webmaple.worker.SpiderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Result createSpider(@RequestParam SpiderDTO spiderDTO) {
        spiderProcess.createSpider(spiderDTO);
        return Result.success();
    }

    @RequestMapping("/startSpider")
    @ResponseBody
    public Result startSpider(@RequestParam SpiderDTO spiderDTO) {
        spiderProcess.startSpider(spiderDTO);
        return Result.success();
    }

    @RequestMapping("/removeSpider")
    @ResponseBody
    public Result removeSpider(@RequestParam String uuid) {
        spiderProcess.removeSpider(uuid);
        return Result.success();
    }

    @RequestMapping("/removeSpider")
    @ResponseBody
    public Result stopSpider(@RequestParam String uuid) {
        spiderProcess.stopSpider(uuid);
        return Result.success();
    }
}
