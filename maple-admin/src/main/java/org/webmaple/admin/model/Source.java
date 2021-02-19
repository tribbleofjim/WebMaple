package org.webmaple.admin.model;

import org.webmaple.common.enums.SourceType;

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

    /**
     * 资源ip
     */
    private String ip;

    /**
     * 访问资源的账号
     */
    private String account;

    /**
     * 访问资源的账号密码
     */
    private String pass;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
