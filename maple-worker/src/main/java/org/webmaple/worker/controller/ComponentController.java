package org.webmaple.worker.controller;

import org.webmaple.common.model.Result;
import org.webmaple.worker.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lyifee
 * on 2021/1/22
 */
@Controller
public class ComponentController {
    @Autowired
    private ComponentService componentService;

    @RequestMapping("/getComponents")
    @ResponseBody
    public Result<List<String>> getComponents() {
        List<String> componentList = componentService.getComponents();
        Result<List<String>> result = new Result<>();
        return result.success(componentList);
    }
}
