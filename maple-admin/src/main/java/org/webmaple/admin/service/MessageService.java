package org.webmaple.admin.service;

import org.webmaple.admin.model.Message;
import org.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/5/3
 */
public interface MessageService {
    /**
     * 举报用户
     * @param accuseId 被举报用户id
     * @param accuseReason 被举报理由
     * @param userId 举报人id
     * @return 举报结果
     */
    Result<Void> accuse(String accuseId, String accuseReason, String userId);

    /**
     * 申请成为管理员
     * @param userId 申请人id
     * @param reason 申请理由
     * @return 申请结果
     */
    Result<Void> applyAuth(String userId, String reason);

    /**
     * 查找与某个用户相关的消息
     * @param id 用户id
     * @return 消息列表
     */
    Result<List<Message>> userMessages(String id);

    /**
     * 批量删除消息
     * @param messages 消息列表
     */
    void deleteMessages(List<Message> messages);
}
