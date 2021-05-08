package org.webmaple.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.webmaple.admin.util.RedisUtil;

/**
 * @author lyifee
 * on 2021/2/20
 */
@SpringBootTest(classes = AdminApplication.class)
public class CreateTable {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    @Sql("/table.sql")
    public void createTable() {}

    @Test
    public void redisTest() throws InterruptedException {
        redisUtil.set("test", "test", 30, 0);
        Thread.sleep(15000);
        System.out.println("reset redis ttl");
        redisUtil.reset("test", "test", 30, 0);
    }
}
