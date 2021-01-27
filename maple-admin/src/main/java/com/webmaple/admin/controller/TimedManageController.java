package com.webmaple.admin.controller;

import com.webmaple.common.model.Result;
import com.webmaple.common.view.TimedSpiderView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/1/27
 */
@Controller
public class TimedManageController {
    @PostMapping("/createTimedSpider")
    @ResponseBody
    public Result<Void> createTimedSpider(TimedSpiderView timedSpiderView) {
        System.out.println(timedSpiderView);
        Result<Void> result = new Result<>();
        return result.success("添加成功！");
    }
}
