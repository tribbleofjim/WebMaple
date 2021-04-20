package org.webmaple.admin.model;

/**
 * 站内信
 * @author lyifee
 * on 2021/4/20
 */
public class Message {
    /**
     * 发出站内信的用户id
     */
    private String fromUser;

    /**
     * 收到站内信的用户id
     */
    private String toUser;

    /**
     * 站内信内容
     */
    private String content;

    /**
     * 是否有效
     */
    private Boolean valid;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
