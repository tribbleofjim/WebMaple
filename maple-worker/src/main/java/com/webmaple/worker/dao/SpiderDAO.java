package com.webmaple.worker.dao;

import com.alibaba.fastjson.JSON;
import com.webmaple.worker.dao.model.SpiderDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Json;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/8
 */
@Component
public class SpiderDAO {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void insertSpider(SpiderDO spiderDO) {
        String insertSql = "insert (uuid, ip, threadNum, urls, state, createDate, updateDate) " +
                "into spider (uuid, ip, threadNum, urls, state, createDate, updateDate)";
        jdbcTemplate.execute(insertSql);
    }

    public List<SpiderDO> querySpiderList() {
        String querySql = "select * from spider";
        return jdbcTemplate.query(querySql, new RowMapper<SpiderDO>() {
            @Override
            public SpiderDO mapRow(ResultSet resultSet, int i) throws SQLException {
                SpiderDO spiderDO = new SpiderDO();
                spiderDO.setUuid(resultSet.getString("uuid"));
                spiderDO.setIp(resultSet.getString("ip"));
                spiderDO.setState(resultSet.getString("state"));
                spiderDO.setThreadNum(resultSet.getInt("threadNum"));
                spiderDO.setCreateDate(resultSet.getDate("createDate"));
                spiderDO.setUpdateDate(resultSet.getDate("updateDate"));
                String rawUrls = resultSet.getString("urls");
                List<String> urls = JSON.parseArray(rawUrls, String.class);
                if (CollectionUtils.isNotEmpty(urls)) {
                    spiderDO.setUrls(urls);
                }
                return spiderDO;
            }
        });
    }
}
