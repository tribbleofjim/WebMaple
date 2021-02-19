package org.webmaple.admin.service;

import org.webmaple.admin.model.Template;
import org.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/9
 */
public interface TemplateService {
    Result<Void> addTemplate(Template template);

    Result<Template> getTemplate(String templateName);

    Result<List<Template>> queryTemplateViewList();

    Result<List<Template>> queryTemplateList();
}
