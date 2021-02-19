package org.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/2/9
 */
public class Template {
    private String html;

    private String templateName;

    private String formUrl;

    @Override
    public String toString() {
        return "Template{" +
                "html='" + html + '\'' +
                ", templateName='" + templateName + '\'' +
                ", formUrl='" + formUrl + '\'' +
                '}';
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }
}
