package com.webmaple.admin.model;

import com.webmaple.common.enums.SourceType;

/**
 * @author lyifee
 * on 2021/2/2
 */
public class Source {
    /**
     * 资源名称，主键
     */
    private String sourceName;

    /**
     * 资源类型，主键
     * @see SourceType
     */
    private Character sourceType;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Character getSourceType() {
        return sourceType;
    }

    public void setSourceType(Character sourceType) {
        this.sourceType = sourceType;
    }
}
