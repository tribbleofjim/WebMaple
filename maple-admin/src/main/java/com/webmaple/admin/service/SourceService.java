package com.webmaple.admin.service;

import com.webmaple.admin.model.Source;
import com.webmaple.common.model.Result;

/**
 * @author lyifee
 * on 2021/2/4
 */
public interface SourceService {
    Result<Void> addSource(Source source);

    Result<Void> editSource(Source source);

    Result<Void> deleteSource(String sourceName, Character sourceType);
}
