package org.webmaple.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.webmaple.admin.model.UserSource;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/26
 */
@Mapper
@Component
public interface UserSourceMapper {
    @Select("SELECT * FROM maple_user_source WHERE phone = #{phone}")
    List<UserSource> queryUserSources(String phone);
}
