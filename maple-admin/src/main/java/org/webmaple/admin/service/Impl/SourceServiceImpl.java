package org.webmaple.admin.service.Impl;

import org.webmaple.admin.mapper.SourceMapper;
import org.webmaple.admin.mapper.UserMapper;
import org.webmaple.admin.mapper.UserSourceMapper;
import org.webmaple.admin.model.Source;
import org.webmaple.admin.model.UserSource;
import org.webmaple.admin.service.SourceService;
import org.webmaple.common.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/4
 */
@Service
public class SourceServiceImpl implements SourceService {
    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private UserSourceMapper userSourceMapper;

    @Override
    public Result<Void> addSource(Source source) {
        Result<Void> result = new Result<>();
        if (source == null) {
            return result.fail("资源为空");
        }
        sourceMapper.insertSource(source);
        return result.success("添加资源成功!");
    }

    @Override
    public Result<List<UserSource>> queryUserSources(String phone) {
        Result<List<UserSource>> result = new Result<>();
        List<UserSource> sourceList = userSourceMapper.queryUserSources(phone);
        return result.success(sourceList);
    }

    @Override
    public Result<List<Source>> querySources() {
        Result<List<Source>> result = new Result<>();
        List<Source> sourceList = sourceMapper.querySources();
        return result.success(sourceList);
    }

    @Override
    public Result<Void> editSource(Source source) {
        Result<Void> result = new Result<>();
        if (source == null) {
            return result.fail("资源为空");
        }
        sourceMapper.modifySource(source);
        return result.success("修改资源成功！");
    }

    @Override
    public Result<Void> deleteSource(String sourceName, Character sourceType) {
        Result<Void> result = new Result<>();
        if (sourceType == null || StringUtils.isBlank(sourceName)) {
            return result.fail("资源名称或资源类型不能为空！");
        }
        sourceMapper.deleteSource(sourceName, sourceType);
        return result.success("删除资源成功！");
    }
}
