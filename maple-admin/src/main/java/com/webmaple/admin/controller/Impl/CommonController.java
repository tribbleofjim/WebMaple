package com.webmaple.admin.controller.Impl;

import com.alibaba.fastjson.JSON;
import com.webmaple.admin.model.DataTableDTO;
import com.webmaple.admin.model.SpiderDTO;
import com.webmaple.admin.service.SpiderManageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/6
 */
@Controller
public class CommonController {
    @Resource
    private SpiderManageService spiderManageService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/spiderList")
    @ResponseBody
    public DataTableDTO querySpiderList(@RequestParam int page, @RequestParam int limit) {
        List<SpiderDTO> spiderList = spiderManageService.querySpiderList();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(spiderList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(spiderList.size());
        }
        dataTableDTO.setData(JSON.toJSON(spiderList));
        return dataTableDTO;
    }
}
