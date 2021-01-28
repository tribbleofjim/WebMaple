package com.webmaple.admin.controller;

import com.webmaple.common.model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/1/28
 */
@Controller
public class TemplateManageController {
    @PostMapping("/submitTemplate")
    @ResponseBody
    public Result<Void> submitTemplate(@RequestParam String html, @RequestParam String formUrl) {
        // 这里的html值应该存储到mysql中
        System.out.println(formUrl);
        Result<Void> result = new Result<>();
        return result.success("提交成功！");
    }
}
