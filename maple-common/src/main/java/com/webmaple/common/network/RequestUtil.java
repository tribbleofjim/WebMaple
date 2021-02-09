package com.webmaple.common.network;

import com.webmaple.common.enums.WebProtocol;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EncodingUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpConstant;
import us.codecraft.webmagic.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 把webmagic中一些request转化为HttpUriRequest的方法挪了过来
 * 因为原框架中这些是和requestContext绑定在一起的，不太好直接用
 *
 * @author lyifee
 * on 2021/1/10
 */
public class RequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            LOGGER.error("null_httpservletrequest");
            return null;
        }
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }

    public static Request getRequest(String protocol, String ip, int port, String mapping, HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isBlank(protocol)) {
            protocol = WebProtocol.http.name();
        }
        builder.append(protocol).append("://");
        builder.append(ip)
                .append(":")
                .append(port)
                .append("/")
                .append(mapping);
        if (params != null && params.size() > 0) {
            builder.append("?");
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.append(param.getKey()).append("=").append(param.getValue()).append("&");
            }
        }
        String requestUrl = builder.toString();
        if (requestUrl.endsWith("&")) {
            String url = requestUrl.substring(0, requestUrl.length() - 1);
            return new Request(url);
        }
        return new Request(requestUrl);
    }

    public static Request postRequest(String protocol, String ip, int port, String mapping, HashMap<String, Object> params) {
        if (StringUtils.isBlank(protocol)) {
            protocol = WebProtocol.http.name();
        }
        String url = protocol + "://" + ip + ":" + port + "/" + mapping;
        HttpRequestBody httpRequestBody;
        Request request = new Request(url).setMethod(HttpConstant.Method.POST);
        if (params != null && params.size() > 0) {
            httpRequestBody = HttpRequestBody.form(params, "UTF-8");
            request.setRequestBody(httpRequestBody);
        }
        return request;
    }

    public static String getResponseText(CloseableHttpResponse response) throws IOException {
        byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
        String contentType = response.getEntity().getContentType() == null ? "" :
                response.getEntity().getContentType().getValue();
        String charset = getHtmlCharset(contentType, bytes);
        return new String(bytes, charset);
    }

    public static String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {

        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            LOGGER.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }

    public static HttpUriRequest getHttpUriRequest(Request request) {
        return RequestUtil.convertHttpUriRequest(request, null, null);
    }

    public static HttpUriRequest convertHttpUriRequest(Request request, Site site, Proxy proxy) {
        RequestBuilder requestBuilder = selectRequestMethod(request).setUri(UrlUtils.fixIllegalCharacterInUrl(request.getUrl()));
        if (site != null && site.getHeaders() != null) {
            for (Map.Entry<String, String> headerEntry : site.getHeaders().entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (site != null) {
            requestConfigBuilder.setConnectionRequestTimeout(site.getTimeOut())
                    .setSocketTimeout(site.getTimeOut())
                    .setConnectTimeout(site.getTimeOut())
                    .setCookieSpec(CookieSpecs.STANDARD);
        }

        if (proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        requestBuilder.setConfig(requestConfigBuilder.build());
        HttpUriRequest httpUriRequest = requestBuilder.build();
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return httpUriRequest;
    }

    private static RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(),request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    private static RequestBuilder addFormParams(RequestBuilder requestBuilder, Request request) {
        if (request.getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(request.getRequestBody().getBody());
            entity.setContentType(request.getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }
}
