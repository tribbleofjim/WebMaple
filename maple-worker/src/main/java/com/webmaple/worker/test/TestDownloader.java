package com.webmaple.worker.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;

/**
 * @author lyifee
 * on 2021/1/9
 */
public class TestDownloader implements Downloader {
    @Override
    public Page download(Request request, Task task) {
        System.out.println("test_download_success");
        return null;
    }

    @Override
    public void setThread(int threadNum) {

    }
}
