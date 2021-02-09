package com.webmaple.common;

import ch.ethz.ssh2.Connection;
import com.webmaple.common.enums.WebProtocol;
import com.webmaple.common.network.RequestSender;
import com.webmaple.common.network.RequestUtil;
import com.webmaple.common.util.SSHUtil;
import com.webmaple.common.view.SpiderView;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author lyifee
 * on 2021/2/8
 */
@SpringBootTest(classes = Application.class)
public class BaseTest {
    @Autowired
    private RequestSender requestSender;

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

    @Test
    public void getTest() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("testStr", "lyifee");
        Request request = RequestUtil.getRequest(WebProtocol.http.name(), "127.0.0.1", 8080, "testGet", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        CloseableHttpResponse response = requestSender.request(httpUriRequest);
        System.out.println(RequestUtil.getResponseText(response));
    }

    @Test
    public void postTest() throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        SpiderView spiderView = new SpiderView();
        spiderView.setUuid("test_uuid");
        spiderView.setThreadNum(3);
        spiderView.setWorker("worker0");
        spiderView.setStartUrls(Arrays.asList("url_test1", "url_test2"));
        spiderView.setProcessor("com.ecspider.processor.JDProcessor");
        params.put("spiderView", spiderView);
        Request request = RequestUtil.postRequest(WebProtocol.http.name(), "127.0.0.1", 8080, "testPost", params);
        HttpUriRequest httpUriRequest = RequestUtil.getHttpUriRequest(request);
        CloseableHttpResponse response = requestSender.request(httpUriRequest);
        System.out.println(RequestUtil.getResponseText(response));
    }
}
