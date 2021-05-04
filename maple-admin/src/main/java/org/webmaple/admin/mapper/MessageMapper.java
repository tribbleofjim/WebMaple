package org.webmaple.admin.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.webmaple.admin.model.Message;

import java.util.List;

/**
 * @author lyifee
 * on 2021/5/3
 */
@Mapper
@Component
public interface MessageMapper {
    @Insert({
            "<script>",
            "INSERT INTO maple_message(from_user, to_user, content, type, valid, date) values",
            "<foreach collection='messages' item='item' index='index' separator=','>",
            "(#{item.fromUser}, #{item.toUser}, #{item.content}, #{item.type}, #{item.valid}, #{item.date})",
            "</foreach>",
            "</script>"
    })
    void insertMany(List<Message> messages);

    @Select("SELECT * FROM maple_message WHERE from_user = #{id} OR to_user = #{id}")
    List<Message> selectUserMsg(String id);
}
