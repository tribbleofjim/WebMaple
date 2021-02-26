package org.webmaple.admin.service;

import org.webmaple.admin.model.Source;
import org.webmaple.admin.model.UserSource;
import org.webmaple.common.model.Result;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/4
 */
public interface SourceService {
    Result<Void> addSource(Source source);

    Result<List<UserSource>> queryUserSources(String phone);

    Result<List<Source>> querySources();

    Result<Void> editSource(Source source);

    Result<Void> deleteSource(String sourceName, Character sourceType);
}
