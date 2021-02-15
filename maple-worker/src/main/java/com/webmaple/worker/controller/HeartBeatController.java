package com.webmaple.worker.controller;

import com.webmaple.common.model.Result;
import com.webmaple.worker.network.HeartBeatSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyifee
 * on 2021/2/15
 */
@Controller
public class HeartBeatController {
    @Autowired
    private HeartBeatSender heartBeatSender;

    @GetMapping("/setWorkerName")
    @ResponseBody
    public Result<Void> setWorkerName(String workerName) {
        Result<Void> result = new Result<>();
        try {
            heartBeatSender.setWorkerName(workerName);
            return result.success();

        } catch (Exception e) {
            return result.fail(e.getMessage());
        }
    }
}
