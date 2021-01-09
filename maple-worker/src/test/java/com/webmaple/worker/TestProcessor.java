package com.webmaple.worker;

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
    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }
}
