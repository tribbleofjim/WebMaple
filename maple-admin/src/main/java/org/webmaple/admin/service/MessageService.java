package org.webmaple.admin.service;

import org.webmaple.common.model.Result;

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
}
