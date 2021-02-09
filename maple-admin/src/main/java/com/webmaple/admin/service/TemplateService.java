package com.webmaple.admin.service;

import com.webmaple.admin.model.Template;
import com.webmaple.common.model.Result;
import com.webmaple.common.view.TemplateView;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/9
 */
public interface TemplateService {
    Result<Void> addTemplate(Template template);

    Result<Template> getTemplate(String templateName);

    Result<List<TemplateView>> queryTemplateViewList();

    Result<List<Template>> queryTemplateList();
}
