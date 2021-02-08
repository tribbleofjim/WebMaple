package com.webmaple.common;

import ch.ethz.ssh2.Connection;
import com.webmaple.common.util.SSHUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lyifee
 * on 2021/2/8
 */
@SpringBootTest(classes = Application.class)
public class BaseTest {
    @Test
    public void execCommandTest() {
        Connection conn = SSHUtil.getConnection("101.42.89.100", "root", "");
        SSHUtil.execCommand(conn, "ls");
        SSHUtil.close(conn);
    }

    @Test
    public void uploadFileTest() {
        Connection conn = SSHUtil.getConnection("", "root", "#");
        SSHUtil.uploadFile(conn, "/Users/lyifee/Projects/ECSpider/target/ECSpider-1.0-SNAPSHOT.jar");
        SSHUtil.execCommand(conn, "ls");
        SSHUtil.close(conn);
    }
}
