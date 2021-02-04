package com.webmaple.admin.mapper;

import com.webmaple.admin.model.Source;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author lyifee
 * on 2021/2/4
 */
@Mapper
@Component
public interface SourceMapper {
    @Insert("INSERT INTO maple_source (source_name, source_type, ip, account, pass) " +
            "VALUES (#{sourceName}, #{sourceType}, #{ip}, #{account}, #{pass})")
    void insertSource(Source source);

    @Update("UPDATE maple_source where source_name = #{sourceName} and source_type = #{sourceType}")
    void modifySource(Source source);

    @Delete("DELETE maple_source where source_name = #{sourceName} and source_type = #{sourceType}")
    void deleteSource(String sourceName, char sourceType);
}
