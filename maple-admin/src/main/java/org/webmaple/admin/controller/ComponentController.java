package org.webmaple.admin.controller;

import com.alibaba.fastjson.JSON;
import org.webmaple.admin.service.ComponentService;
import org.webmaple.common.model.ComponentDTO;
import org.webmaple.common.model.DataTableDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/12
 */
@Controller
public class ComponentController {
    @Autowired
    private ComponentService componentService;

    @RequestMapping("/componentList")
    @ResponseBody
    public DataTableDTO queryComponentList(@RequestParam int page, @RequestParam int limit) {
        List<ComponentDTO> componentList = componentService.queryComponentList();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        if (CollectionUtils.isEmpty(componentList)) {
            dataTableDTO.setCount(0);
        } else {
            dataTableDTO.setCount(componentList.size());
        }
        dataTableDTO.setData(JSON.toJSON(componentList));
        return dataTableDTO;
    }
}
