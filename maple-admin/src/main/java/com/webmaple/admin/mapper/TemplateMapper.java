package com.webmaple.admin.mapper;

import com.webmaple.admin.model.Template;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/9
 */
@Mapper
@Component
public interface TemplateMapper {
    @Insert("INSERT INTO maple_template (html, template_name, form_url) " +
            "VALUES (#{html}, #{templateName}, #{formUrl})")
    void insertTemplate(Template template);

    @Select("SELECT * FROM maple_template WHERE template_name = #{templateName}")
    Template getTemplate(String templateName);

    @Select("SELECT * FROM maple_template")
    List<Template> queryTemplates();
}
