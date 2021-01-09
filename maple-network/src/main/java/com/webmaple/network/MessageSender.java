package com.webmaple.network;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author lyifee
 * on 2021/1/9
 */
public class MessageSender {
    private CloseableHttpClient httpClient;

    public MessageSender() {
        httpClient = HttpClients.createDefault();
    }

    public void send(Message message) {

    }
}