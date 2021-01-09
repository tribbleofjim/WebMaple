package com.webmaple.worker.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lyifee
 * on 2021/1/9
 */
@Component
public class ComponentDAO {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void insertComponent() {

    }
}
