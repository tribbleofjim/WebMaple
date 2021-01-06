package com.webmaple.admin.controller.Impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Controller
public class CommonController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
