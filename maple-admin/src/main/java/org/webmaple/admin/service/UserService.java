package org.webmaple.admin.service;

import org.webmaple.admin.model.User;
import org.webmaple.common.model.Result;

/**
 * @author lyifee
 * on 2021/2/2
 */
public interface UserService {
    Result<Void> login(User user);

    Result<Void> register(User user);

    Result<Void> getNickname(String phone);

    Result<User> searchUser(String phone);

    Result<Void> modifyNickname(User user);

    Result<Void> modifyPassword(String phone, String oldPass, String newPass);
}
