package com.webmaple.admin.controller;

import com.webmaple.common.model.Result;
import com.webmaple.common.view.SpiderView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/2/9
 */
@Controller
public class TestController {
    @GetMapping("/testGet")
    @ResponseBody
    public Result<Void> testGet(@RequestParam String testStr) {
        Result<Void> result = new Result<>();
        return result.success("success! " + testStr);
    }

    @PostMapping("/testPost")
    @ResponseBody
    public Result<Void> testPost(SpiderView spiderView) {
        Result<Void> result = new Result<>();
        System.out.println(spiderView);
        return result.success("success! ");
    }
}
