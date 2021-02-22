package org.webmaple.worker.controller;

import org.springframework.web.bind.annotation.*;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.SpiderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/19
 */
@Controller
public class SpiderController {
    @Autowired
    private SpiderProcess spiderProcess;

    @PostMapping("/createSpider")
    @ResponseBody
    public Result<Void> createSpider(@RequestBody SpiderDTO spiderDTO) {
        return spiderProcess.createSpider(spiderDTO);
    }

    @RequestMapping("/startSpider")
    @ResponseBody
    public Result<Void> startSpider(@RequestParam String uuid) {
        return spiderProcess.startSpider(uuid);
    }

    @RequestMapping("/removeSpider")
    @ResponseBody
    public Result<Void> removeSpider(@RequestParam String uuid) {
        return spiderProcess.removeSpider(uuid);
    }

    @RequestMapping("/stopSpider")
    @ResponseBody
    public Result<Void> stopSpider(@RequestParam String uuid) {
        return spiderProcess.stopSpider(uuid);
    }

    @RequestMapping("/modifySpider")
    @ResponseBody
    public Result<Void> modifySpider(@RequestBody SpiderDTO spiderDTO) {
        return spiderProcess.modifySpider(spiderDTO);
    }

    @RequestMapping("/spiderList")
    @ResponseBody
    public Result<List<SpiderDTO>> spiderList() {
        return spiderProcess.getSpiders();
    }
}
