package com.webmaple.worker.network;

import com.webmaple.common.model.Result;
import com.webmaple.common.model.SpiderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Controller
public class MethodController {
    @RequestMapping
    @ResponseBody
    public Result createSpider(@RequestParam SpiderDTO spiderDTO) {

        return Result.success();
    }
}
