package org.webmaple.common.network;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lyifee
 * on 2021/1/10
 */
@Component
public class RequestSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSender.class);

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public CloseableHttpResponse request(HttpUriRequest request) throws IOException {
        return httpClient.execute(request);
    }
}