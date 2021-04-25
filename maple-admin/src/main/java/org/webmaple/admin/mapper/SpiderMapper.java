package org.webmaple.admin.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.webmaple.admin.model.MapleSpider;

import java.util.List;

/**
 * @author lyifee
 * on 2021/4/23
 */
@Mapper
@Component
public interface SpiderMapper {
    @Select("SELECT * from maple_spider")
    List<MapleSpider> spiderList();

    @Insert("INSERT INTO maple_spider (worker, urls, uuid, state, thread_num, processor, pipeline, downloader)" +
            "VALUES (#{worker}, #{urls}, #{uuid}, #{state}, #{threadNum}, #{processor}, #{pipeline}, #{downloader})")
    void createSpider(MapleSpider spider);

    @Delete("DELETE FROM maple_spider WHERE uuid = #{uuid} and worker = #{worker}")
    void removeSpider(String uuid, String worker);

    @Delete("DELETE FROM maple_spider")
    void removeAllSpider();

    @Delete({"<script>",
            "DELETE FROM maple_spider WHERE worker not in",
                    "<foreach collection=\"workers\" item=\"worker\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
                    "#{worker}",
                    "</foreach>",
                    "</script>"})
    void removeInvalidNodeSpiders(@Param("workers") List<String> workers);

    @Update("UPDATE maple_spider SET state = 'running' WHERE uuid = #{uuid} AND worker = #{worker}")
    void startSpider(@Param("uuid") String uuid, @Param("worker") String worker);

    @Update("UPDATE maple_spider SET state = 'stop' WHERE uuid = #{uuid} AND worker = #{worker}")
    void stopSpider(@Param("uuid") String uuid, @Param("worker") String worker);

    @Update("UPDATE maple_spider SET state = 'timed' WHERE uuid = #{uuid} AND worker = #{worker}")
    void timedSpider(@Param("uuid") String uuid, @Param("worker") String worker);

    @Update("UPDATE maple_spider SET state = 'exception' WHERE uuid = #{uuid} AND worker = #{worker}")
    void exceptedSpider(@Param("uuid") String uuid, @Param("worker") String worker);
}
