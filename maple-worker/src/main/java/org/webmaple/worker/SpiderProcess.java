package org.webmaple.worker;

import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.util.ModelUtil;
import org.webmaple.worker.container.SpiderContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderStatus;
import us.codecraft.webmagic.thread.CountableThreadPool;
import java.util.List;

/**
 * run a spider
 * and manage information of all spiders
 * basically one spring boot project
 * a worker can have more than one spider process
 *
 * @author lyifee
 * on 2021/1/5
 */
@Component
public class SpiderProcess {
    @Autowired
    private ModelUtil modelUtil;

    protected CountableThreadPool threadPool;

    protected int threadNum = 0;

    private static final int DEFAULT_THREAD_NUM = 5;

    public SpiderProcess() {
        init();
    }

    private void init() {
        if (threadNum <= 0) {
            threadPool = new CountableThreadPool(DEFAULT_THREAD_NUM);
        } else {
            threadPool = new CountableThreadPool(threadNum);
        }
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public void createSpider(SpiderDTO spiderDTO) {
        if (spiderDTO == null || StringUtils.isBlank(spiderDTO.getUuid())) {
            return;
        }
        SpiderContainer.createSpider(spiderDTO.getUuid(), spiderDTO);
    }

    public Result<Void> startSpider(String uuid) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(uuid)) {
            return result.fail("uuid不能为空！");
        }

        Spider spider;
        if ((spider = SpiderContainer.getSpider(uuid)) != null) {
            if (Spider.Status.Running.equals(spider.getStatus())) {
                return result.fail("该爬虫已经在运行");

            } else {
                run(spider);
                return result.success("启动成功！");
            }
        }

        SpiderDTO spiderDTO = SpiderContainer.getSpiderDTO(uuid);
        if (spiderDTO == null) {
            return result.fail("该爬虫不存在，请先创建");
        }
        spider = modelUtil.getSpiderFromDTO(spiderDTO);
        if (spider == null) {
            return result.fail("获取spider错误");
        }
        run(spider);
        return result.success("启动成功！");
    }

    private void run(Spider spider) {
        SpiderContainer.runSpider(spider);
        threadPool.execute(spider);
    }

    public List<SpiderDTO> getSpiders() {
        return SpiderContainer.getSpiderList();
    }

    public void modifySpider(SpiderDTO spiderDTO) {
        if (spiderDTO == null || StringUtils.isBlank(spiderDTO.getUuid())) {
            return;
        }
        SpiderContainer.createSpider(spiderDTO.getUuid(), spiderDTO);
    }

    public void removeSpider(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        SpiderContainer.removeSpiderDTO(uuid);
    }

    public void stopSpider(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return;
        }
        SpiderContainer.stopSpider(uuid);
    }
}
