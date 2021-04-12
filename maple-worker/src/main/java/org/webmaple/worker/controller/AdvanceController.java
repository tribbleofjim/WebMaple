package org.webmaple.worker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.webmaple.common.model.SpiderAdvance;
import org.webmaple.worker.SpiderAdvanceCache;

import java.util.List;

/**
 * @author lyifee
 * on 2021/4/12
 */
@Controller
public class AdvanceController {
    @RequestMapping("/advance")
    public List<SpiderAdvance> advances() {
        return SpiderAdvanceCache.getAdvanceList();
    }
}
