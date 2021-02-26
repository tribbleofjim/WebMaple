package org.webmaple.common.view;

/**
 * @author lyifee
 * on 2021/2/26
 */
public class SourceAuthView {
    /**
     * 在数组中的顺序，用int值表示，如1，2，3
     */
    private String value;

    /**
     * 资源名称
     */
    private String title;

    /**
     * 穿梭框-是否禁用
     * 可为空字符串
     */
    private String disabled = "";

    /**
     * 穿梭框-是否打勾
     * 可为空字符串
     */
    private String checked = "";

    /**
     * 该用户是否拥有权限
     */
    private Boolean auth = false;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源ip
     */
    private String ip;

    /**
     * 资源子类型
     */
    private String stype;

    /**
     * 访问资源的账户
     */
    private String user;

    /**
     * 访问资源的账户密码
     */
    private String password;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
