package com.webmaple.worker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lyifee
 * on 2021/1/9
 */
@SpringBootTest(classes = WorkerApplication.class)
public class CommonTest {
    @Autowired
    private ComponentRegister componentRegister;

    @Test
    public void componentRegisterTest() {
        componentRegister.register();
    }
}
