package org.webmaple.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.webmaple.admin.mapper.SpiderMapper;

/**
 * @author lyifee
 * on 2021/4/23
 */
@Component
public class ExitCode implements ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ExitCode.class);

//    @Autowired
//    private SpiderMapper spiderMapper;

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        logger.info("=======run close event=======");
        // logger.info("removing all spiders...");
        // spiderMapper.removeAllSpider();
        // logger.info("========close success=======");
    }
}
