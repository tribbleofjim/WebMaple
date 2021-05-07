package org.webmaple.worker;

import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.worker.util.ModelUtil;
import org.webmaple.worker.container.SpiderContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
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

    public Result<Void> createSpider(SpiderDTO spiderDTO) {
        Result<Void> result = new Result<>();

        if (spiderDTO == null) {
            return result.fail("爬虫对象为空");
        }

        // fix bug:spiderDTO传过来时url头尾会有[]符号
        List<String> urls = spiderDTO.getUrls();
        int size;
        if ((size = urls.size()) == 0) {
            return result.fail("url不能为空");
        }
        if (size == 1) {
            String url = urls.get(0);
            urls.set(0, url.substring(1, url.length() - 1));

        } else {
            String urlStart = urls.get(0);
            urls.set(0, urlStart.substring(1));
            String urlEnd = urls.get(size - 1);
            urls.set(size - 1, urlEnd.substring(0, urlEnd.length() - 1));
        }

        if (StringUtils.isBlank(spiderDTO.getUuid())) {
            return result.fail("参数不能为空");
        }
        SpiderContainer.createSpider(spiderDTO.getUuid(), spiderDTO);
        return result.success("创建爬虫成功！");
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
        SpiderContainer.createExecutableSpider(spider);
        threadPool.execute(spider);
    }

    public Result<List<SpiderDTO>> getSpiders() {
        Result<List<SpiderDTO>> result = new Result<>();
        List<SpiderDTO> spiderList =  SpiderContainer.getSpiderList();
        return result.success(spiderList);
    }

    public Result<Void> modifySpider(SpiderDTO spiderDTO) {
        Result<Void> result = new Result<>();
        if (spiderDTO == null || StringUtils.isBlank(spiderDTO.getUuid())) {
            return result.fail("爬虫或爬虫uuid不能为空");
        }
        SpiderDTO oldSpiderDTO = SpiderContainer.getSpiderDTO(spiderDTO.getUuid());
        if (oldSpiderDTO == null) {
            SpiderContainer.createSpider(spiderDTO.getUuid(), spiderDTO);
        } else {
            oldSpiderDTO.setThreadNum(spiderDTO.getThreadNum());
            SpiderContainer.createSpider(spiderDTO.getUuid(), oldSpiderDTO);
        }
        return result.success("修改成功！");
    }

    public Result<Void> removeSpider(String uuid) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(uuid)) {
            return result.fail("uuid不能为空");
        }
        SpiderContainer.removeSpider(uuid);
        return result.success("删除成功！");
    }

    public Result<Void> stopSpider(String uuid) {
        Result<Void> result = new Result<>();
        if (StringUtils.isBlank(uuid)) {
            return result.fail("uuid不能为空");
        }
        SpiderContainer.stopSpider(uuid);
        return result.success("暂停成功！");
    }
}
