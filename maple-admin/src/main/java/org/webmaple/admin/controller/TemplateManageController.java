package org.webmaple.admin.controller;

import org.webmaple.admin.model.Template;
import org.webmaple.admin.service.TemplateService;
import org.webmaple.common.model.DataTableDTO;
import org.webmaple.common.model.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/28
 */
@Controller
public class TemplateManageController {
    @Resource
    private TemplateService templateService;

    @PostMapping("/submitTemplate")
    @ResponseBody
    public Result<Void> submitTemplate(@RequestParam String html,
                                       @RequestParam String formUrl,
                                       @RequestParam String templateName) {
        Template template = new Template();
        template.setTemplateName(templateName);
        template.setHtml(html);
        template.setFormUrl(formUrl);
        return templateService.addTemplate(template);
    }

    @GetMapping("/templateList")
    @ResponseBody
    public DataTableDTO templateList() {
        Result<List<Template>> result = templateService.queryTemplateViewList();
        List<Template> templateViews = result.getModel();
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setCode(0);
        dataTableDTO.setMsg("");
        dataTableDTO.setCount((CollectionUtils.isEmpty(templateViews)) ? 0 : templateViews.size());
        dataTableDTO.setData(templateViews);
        return dataTableDTO;
    }

    @GetMapping("/getTemplate")
    @ResponseBody
    public Result<Template> getTemplate(@RequestParam String templateName) {
        Result<Template> result = new Result<>();
        if (StringUtils.isBlank(templateName)) {
            return result.fail("模板名称不能为空", null);
        }
        return templateService.getTemplate(templateName);
    }
}
