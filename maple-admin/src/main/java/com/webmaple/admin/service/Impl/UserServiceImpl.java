package com.webmaple.admin.service.Impl;

import com.webmaple.admin.mapper.UserMapper;
import com.webmaple.admin.model.User;
import com.webmaple.admin.service.UserService;
import com.webmaple.common.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyifee
 * on 2021/2/2
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<Void> login(User user) {
        Result<Void> result = new Result<>();
        if (user == null || StringUtils.isBlank(user.getPhone())) {
            return result.fail("用户手机号码不能为空");
        }
        User mapleUser = userMapper.getUserByPhone(user.getPhone());
        if (mapleUser == null) {
            return result.fail("用户不存在");
        }
        if (!mapleUser.getPassword().equals(user.getPassword())) {
            return result.fail("用户名或密码不正确");
        }
        return result.success();
    }

    @Override
    public Result<Void> register(User user) {
        Result<Void> result = new Result<>();
        if (user == null
                || StringUtils.isBlank(user.getPhone())
                || StringUtils.isBlank(user.getNickname())
                || StringUtils.isBlank(user.getPassword())) {
            return result.fail("用户信息不能为空");
        }
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            LOGGER.error("error_register", e);
            return result.fail("创建用户失败");
        }
        return result.success();
    }
}
