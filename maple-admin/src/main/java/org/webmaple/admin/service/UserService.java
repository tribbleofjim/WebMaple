package org.webmaple.admin.service;

import org.webmaple.admin.model.User;
import org.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/2
 */
public interface UserService {
    Result<Void> login(User user);

    Result<Void> register(User user);

    Result<User> searchUser(String phone);

    Result<Void> authUser(String phone);

    Result<Void> delUser(String phone);

    Result<Void> modifyNickname(User user);

    Result<Void> modifyPassword(String phone, String oldPass, String newPass);

    Result<List<User>> userList();

    Result<List<User>> commanderList();
}
