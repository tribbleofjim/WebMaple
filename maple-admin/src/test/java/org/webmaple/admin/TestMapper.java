package org.webmaple.admin;

import org.webmaple.admin.mapper.SpiderMapper;
import org.webmaple.admin.mapper.TemplateMapper;
import org.webmaple.admin.model.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/2/9
 */
@SpringBootTest(classes = AdminApplication.class)
public class TestMapper {
    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private SpiderMapper spiderMapper;

    @Test
    public void templates() {
        List<Template> templateList = templateMapper.queryTemplates();
        for (Template template : templateList) {
            System.out.println(template.toString());
        }
    }

    @Test
    public void templateViews() {
        List<Template> templateList = templateMapper.queryTemplateViewList();
        for (Template template : templateList) {
            System.out.println(template.toString());
        }
    }

    @Test
    public void spiderTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("worker1");
        list.add("worker2");
        spiderMapper.removeInvalidNodeSpiders(list);
    }
}
