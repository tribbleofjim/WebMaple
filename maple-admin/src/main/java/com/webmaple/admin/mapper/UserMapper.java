package com.webmaple.admin.mapper;

import com.webmaple.admin.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/2/2
 */
@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM maple_user WHERE phone=#{phone}")
    User getUserByPhone(String phone);
    
    @Insert("INSERT INTO maple_user (phone, nickname, password) VALUES (#{phone}, #{nickname}, #{password})")
    void insertUser(User user);

    @Update("UPDATE maple_user SET nickname = #{nickname} WHERE phone = #{phone}")
    void updateNickname(User user);

    @Update("UPDATE maple_user SET password = #{password} WHERE phone = #{phone}")
    void updatePassword(User user);
}
