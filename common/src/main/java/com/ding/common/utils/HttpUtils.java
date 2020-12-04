package com.ding.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author ding
 * @version V3.0
 * @Classname HttpUtils
 * @Description http请求工具类
 * @Date 2020/6/15 下午5:05
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

    // 默认配置
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
            .setConnectionRequestTimeout(6000).setSocketTimeout(6000).build();

    public static String doGet(String url, Map<String, String> headers, boolean isHttps) {
        return http("get", url, null, headers, isHttps);
    }

    public static String doDelete(String url, Map<String, String> headers, boolean isHttps) {
        return http("delete", url, null, headers, isHttps);
    }

    public static String doPost(String url, String body, Map<String, String> headers, boolean isHttps) {
        return http("post", url, body, headers, isHttps);
    }

    public static String doPut(String url, String body, Map<String, String> headers, boolean isHttps) {
        return http("put", url, body, headers, isHttps);
    }

    private static String http(String method, String url, String body,
                               Map<String, String> headers, boolean isHttps) {
        long start = System.currentTimeMillis();
        try {
            if (log.isDebugEnabled()) {
                log.info("请求入参：method = {},url = {},body = {},headers = {},isHttps = {}", method, url, body, headers, isHttps);
            }
            HttpClient httpClient;
            if (isHttps) {
                httpClient = createSSLClientDefault();
            } else {
                httpClient = HttpClients.createDefault();
            }
            url = restEncode(url);
            if ("post".equalsIgnoreCase(method)) {
                HttpPost post = new HttpPost(url);
                post.setConfig(requestConfig);
                if (null != headers) {
                    headers.forEach(post::setHeader);
                }
                post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
                HttpResponse response = httpClient.execute(post);
                return parseRes(response);
            } else if ("get".equalsIgnoreCase(method)) {
                HttpGet get = new HttpGet(url);
                get.setConfig(requestConfig);
                if (null != headers) {
                    headers.forEach(get::setHeader);
                }
                HttpResponse response = httpClient.execute(get);
                return parseRes(response);
            } else if ("delete".equalsIgnoreCase(method)) {
                HttpDelete delete = new HttpDelete(url);
                delete.setConfig(requestConfig);
                if (null != headers) {
                    headers.forEach(delete::setHeader);
                }
                HttpResponse response = httpClient.execute(delete);
                return parseRes(response);
            } else if ("put".equalsIgnoreCase(method)) {
                HttpPut put = new HttpPut(url);
                put.setConfig(requestConfig);
                if (null != headers) {
                    headers.forEach(put::setHeader);
                }
                put.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
                HttpResponse response = httpClient.execute(put);
                return parseRes(response);
            } else {
                throw new IllegalArgumentException("Unsupported request method");
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("请求异常：url=[%s],error=[%s]", url, e.getMessage()));
        } finally {
            if (log.isDebugEnabled()) {
                log.info("请求耗时：{}", System.currentTimeMillis() - start + " ms");
            }
        }
    }

    /**
     * 解析响应结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String parseRes(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }

    /**
     * 创建https
     *
     * @return
     */
    private static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, (x509Certificates, s) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    private static String restEncode(String url) {
        return url.replaceAll(" ", "%20");
    }

}
