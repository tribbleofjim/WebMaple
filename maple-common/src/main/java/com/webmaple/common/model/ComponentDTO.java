package com.webmaple.common.model;

/**
 * @author lyifee
 * on 2021/1/24
 */
public class ComponentDTO {
    /**
     * 组件类型，如downloader/pipeline/processor
     */
    private String type;

    /**
     * 组件全类名
     */
    private String name;

    /**
     * 对应的site
     */
    private String site;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
