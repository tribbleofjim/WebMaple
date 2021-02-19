package org.webmaple.worker.job.spider;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;

/**
 * @author lyifee
 * on 2021/1/12
 */
public class UrlNumListener implements SpiderListener {
    private int urlNum = 0;

    private final Spider spider;

    private int stopNum = 1000; // 默认爬取1000条url后停下来

    public UrlNumListener(Spider spider) {
        this.spider = spider;
    }

    public UrlNumListener(Spider spider, int stopNum) {
        this.spider = spider;
        this.stopNum = stopNum;
    }

    @Override
    public void onSuccess(Request request) {
        urlNum++;
        // 注意：此处可能会比stopNum多处理一条url，
        // 因为spider设置停止标志的动作没能及时传递到主线程处，导致主线程会多从工作队列中取出一条url。
        if (urlNum >= stopNum && spider.getStatus().equals(Spider.Status.Running)) {
            doStop();
            urlNum = 0;
        }
    }

    @Override
    public void onError(Request request) {}

    private void doStop() {
        spider.stop();
    }
}
