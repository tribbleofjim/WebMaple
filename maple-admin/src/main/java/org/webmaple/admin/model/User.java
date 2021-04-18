package org.webmaple.admin.model;

/**
 * @author lyifee
 * on 2021/2/2
 */
public class User {
    /**
     * 手机号，主键
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Character auth;

    /**
     * 密保问题
     */
    private Character question;

    /**
     * 答案
     */
    private String answer;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getAuth() {
        return auth;
    }

    public void setAuth(Character auth) {
        this.auth = auth;
    }

    public Character getQuestion() {
        return question;
    }

    public void setQuestion(Character question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
