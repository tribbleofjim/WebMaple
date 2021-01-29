package com.webmaple.worker.test;

import com.webmaple.worker.annotation.MapleProcessor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author lyifee
 * on 2021/1/9
 */
@MapleProcessor(site = "https://jd.search.com")
public class TestProcessor implements PageProcessor {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) " +
            "AppleWebKit/537.31 (KHTML, like Gecko) " +
            "Chrome/26.0.1410.65 " +
            "Safari/537.31";

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(3000)
            .setUserAgent(USER_AGENT)
            ;

    @Override
    public void process(Page page) {
        System.out.println("test_process_success");
    }

    @Override
    public Site getSite() {
        return site;
    }
}
