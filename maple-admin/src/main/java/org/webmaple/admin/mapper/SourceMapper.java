package org.webmaple.admin.mapper;

import org.apache.ibatis.annotations.*;
import org.webmaple.admin.model.Source;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/4
 */
@Mapper
@Component
public interface SourceMapper {
    @Select("SELECT * FROM maple_source")
    List<Source> querySources();

    @Insert("INSERT INTO maple_source (source_name, source_type, ip, account, pass) " +
            "VALUES (#{sourceName}, #{sourceType}, #{ip}, #{account}, #{pass})")
    void insertSource(Source source);

    @Update("UPDATE maple_source where source_name = #{sourceName} and source_type = #{sourceType}")
    void modifySource(Source source);

    @Delete("DELETE maple_source where source_name = #{sourceName} and source_type = #{sourceType}")
    void deleteSource(String sourceName, char sourceType);
}
