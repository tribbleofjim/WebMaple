package org.webmaple.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.webmaple.admin.model.Source;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/26
 */
@Mapper
@Component
public interface UserSourceMapper {
    @Select("SELECT t2.* FROM maple_user_source t1, maple_source t2 WHERE t1.source_name = t2.source_name and t1.phone = #{phone}")
    List<Source> queryUserSources(String phone);
}
