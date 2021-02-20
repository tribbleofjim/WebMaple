package org.webmaple.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author lyifee
 * on 2021/2/20
 */
@SpringBootTest(classes = AdminApplication.class)
public class CreateTable {
    @Test
    @Sql("/table.sql")
    public void createTable() {}
}
