package org.webmaple.admin.controller;

import org.webmaple.admin.service.SpiderManageService;
import org.webmaple.admin.view.JDSpiderView;
import org.webmaple.common.model.Result;
import org.webmaple.common.model.SpiderDTO;
import org.webmaple.common.util.UrlUtil;
import org.webmaple.common.view.SpiderView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author lyifee
 * on 2021/2/9
 */
@Controller
public class TestController {
    private static final String BASE_URL = "https://search.jd.com/Search?";

    @Resource
    private SpiderManageService spiderManageService;

    @GetMapping("/testGet")
    @ResponseBody
    public Result<Void> testGet(@RequestParam String testStr) {
        Result<Void> result = new Result<>();
        return result.success("success! " + testStr);
    }

    @PostMapping("/testPost")
    @ResponseBody
    public Result<Void> testPost(SpiderView spiderView) {
        Result<Void> result = new Result<>();
        System.out.println(spiderView);
        return result.success("success! ");
    }

    @PostMapping("/JDSpider")
    @ResponseBody
    public Result<Void> jdSpider(JDSpiderView fields) {
        SpiderDTO spiderDTO = new SpiderDTO();
        spiderDTO.setWorker(fields.getWorker());
        spiderDTO.setThreadNum(Integer.parseInt(fields.getThreadNum()));
        spiderDTO.setUuid("jd.com");
        spiderDTO.setProcessor("com.ecspider.common.processor.JDProcessor");
        spiderDTO.setDownloader("com.ecspider.common.downloader.SeleniumDownloader");
        spiderDTO.setPipeline("com.ecspider.common.pipeline.JDPipeline");
        ArrayList<String> urls = new ArrayList<>();
        urls.add(getRootUrl(fields.getKeyword(), Integer.parseInt(fields.getStartPage())));
        spiderDTO.setUrls(urls);
        return spiderManageService.createSpider(spiderDTO);
    }

    private String getRootUrl(String keyword, int startPage) {
        String url;
        url = UrlUtil.addParamToUrl(BASE_URL, "keyword", keyword);
        url = UrlUtil.addParamToUrl(url, "wq", keyword);
        url = UrlUtil.addParamToUrl(url, "page", String.valueOf(startPage * 2 - 1));
        if (startPage == 1) {
            url = UrlUtil.addParamToUrl(url, "s", String.valueOf(1));
        } else {
            url = UrlUtil.addParamToUrl(url, "s", String.valueOf(56 + 60 * (startPage - 2)));
        }
        url = UrlUtil.addParamToUrl(url, "click", "0");
        return url;
    }
}
