package com.webmaple.admin.service.Impl;

import com.webmaple.admin.mapper.SourceMapper;
import com.webmaple.admin.model.Source;
import com.webmaple.admin.service.SourceService;
import com.webmaple.common.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyifee
 * on 2021/2/4
 */
@Service
public class SourceServiceImpl implements SourceService {
    @Autowired
    private SourceMapper sourceMapper;

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