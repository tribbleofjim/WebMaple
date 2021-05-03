package org.webmaple.admin.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webmaple.admin.mapper.MessageMapper;
import org.webmaple.admin.model.Message;
import org.webmaple.admin.model.User;
import org.webmaple.admin.service.MessageService;
import org.webmaple.admin.service.UserService;
import org.webmaple.common.enums.MessageType;
import org.webmaple.common.model.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author lyifee
 * on 2021/5/3
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Override
    public Result<Void> accuse(String accuseId, String accuseReason, String userId) {
        Result<Void> result = new Result<>();

        List<User> commanders = userService.commanderList().getModel();
        List<Message> messages = new ArrayList<>();

        for (User commander : commanders) {
            Message message = new Message();
            message.setFromUser(userId);
            message.setToUser(commander.getPhone());
            message.setContent(accuseReason);
            message.setType(MessageType.ACCUSE.getType());
            message.setValid(true);
            message.setDate(new Date());
            messages.add(message);
        }

        messageMapper.insertMany(messages);
        return result.success();
    }

    @Override
    public Result<Void> applyAuth(String userId, String reason) {
        Result<Void> result = new Result<>();

        List<User> commanders = userService.commanderList().getModel();
        List<Message> messages = new ArrayList<>();

        for (User commander : commanders) {
            Message message = new Message();
            message.setFromUser(userId);
            message.setToUser(commander.getPhone());
            message.setContent(reason);
            message.setType(MessageType.APPLY.getType());
            message.setValid(true);
            message.setDate(new Date());
            messages.add(message);
        }

        messageMapper.insertMany(messages);
        return result.success();
    }
}
