package com.webmaple.admin.controller;

import com.webmaple.admin.model.Template;
import com.webmaple.admin.service.TemplateService;
import com.webmaple.common.model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
}
