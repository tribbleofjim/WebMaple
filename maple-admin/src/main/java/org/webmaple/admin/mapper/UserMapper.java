package org.webmaple.admin.mapper;

import org.webmaple.admin.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/2
 */
@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM maple_user WHERE phone = #{phone}")
    User getUserByPhone(String phone);

    @Select("SELECT * FROM maple_user")
    List<User> userList();

    @Select("SELECT * FROM maple_user WHERE auth = '0'")
    List<User> commanderList();
    
    @Insert("INSERT INTO maple_user (phone, nickname, password, question, answer) " +
            "VALUES (#{phone}, #{nickname}, #{password}, #{question}, #{answer})")
    void insertUser(User user);

    @Update("UPDATE maple_user SET nickname = #{nickname} WHERE phone = #{phone}")
    void updateNickname(User user);

    @Update("UPDATE maple_user SET password = #{password} WHERE phone = #{phone}")
    void updatePassword(@Param("phone") String phone, @Param("password") String password);

    @Update("UPDATE maple_user SET auth = '0' WHERE phone = #{phone}")
    void authUser(String phone);

    @Delete("DELETE FROM maple_user WHERE phone = #{phone}")
    void deleteUser(String phone);
}
