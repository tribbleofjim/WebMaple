package com.webmaple.admin.service.Impl;

import com.webmaple.admin.mapper.TemplateMapper;
import com.webmaple.admin.model.Template;
import com.webmaple.admin.service.TemplateService;
import com.webmaple.common.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/9
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public Result<Void> addTemplate(Template template) {
        Result<Void> result = new Result<>();
        if (template == null || StringUtils.isBlank(template.getHtml())) {
            return result.fail("模板不能为空");
        }
        if (StringUtils.isBlank(template.getTemplateName())) {
            return result.fail("模板名称不能为空");
        }
        templateMapper.insertTemplate(template);
        return result.success("创建成功！");
    }

    @Override
    public Result<Template> getTemplate(String templateName) {
        Result<Template> result = new Result<>();
        if (StringUtils.isBlank(templateName)) {
            return result.fail("模板名称不能为空");
        }
        Template template = templateMapper.getTemplate(templateName);
        return result.success(template);
    }

    @Override
    public Result<List<Template>> queryTemplateList() {
        Result<List<Template>> result = new Result<>();
        List<Template> templateList = templateMapper.queryTemplates();
        return result.success(templateList);
    }
}
