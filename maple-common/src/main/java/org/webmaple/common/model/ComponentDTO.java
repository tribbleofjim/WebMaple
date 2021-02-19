package org.webmaple.common.model;

import java.io.Serializable;

/**
 * @author lyifee
 * on 2021/1/24
 */
public class ComponentDTO implements Serializable {
    private static final long serialVersionUID = 2556099771141926148L;

    /**
     * 组件类型，如downloader/pipeline/processor
     */
    private String type;

    /**
     * 节点名称
     */
    private String worker;

    /**
     * 组件全类名
     */
    private String name;

    /**
     * 对应的site
     */
    private String site;

    private Integer num;

    @Override
    public String toString() {
        return "ComponentDTO{" +
                "type='" + type + '\'' +
                ", worker='" + worker + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", num=" + num +
                '}';
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
