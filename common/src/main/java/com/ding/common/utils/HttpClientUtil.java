package com.ding.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description  http工具类
 * @@copyright
 * @ClassName HttpClientUtil
 * @date 2020-03-14 17:48
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * Http request get method
     * @param uri example "https://www.baidu.com/"
     * @return
     */
    public static String getMethod(String uri){
        String result = null;
        CloseableHttpClient httpClient= HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000)
                    .setConnectionRequestTimeout(1000).setSocketTimeout(3000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Content-Type","text/html; charset=UTF-8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            System.out.println("executing request:" + httpGet.getURI());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 打印响应内容
                result = EntityUtils.toString(entity,"UTF-8");
            }
//            logger.info(" GET {}, Response --->状态码:{},Content-Type:{},result:{}", httpGet.getURI(),code,entity.getContentType(),result);
        }catch (Exception e){
            logger.error("GET failure :caused by-->" + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Http request Post method
     * @param uri example "https://www.baidu.com/"
     * @param headerMap  https 头
     * @param requestBody  请求参数
     * @return
     */
    public static String postMethod(String uri, Map<String, String> headerMap, String requestBody){
        String result = null;
        CloseableHttpClient httpClient=HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(uri);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000)
                    .setConnectionRequestTimeout(1000).setSocketTimeout(3000).build();
            httpPost.setConfig(requestConfig);
            logger.info("executing request:[{}]", httpPost.getURI());


            for (Map.Entry<String, String> header : headerMap.entrySet()) { httpPost.setHeader(header.getKey(),header.getValue()); }

            HttpEntity entity = new StringEntity(requestBody, "UTF-8");
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                // 打印响应内容
                result = EntityUtils.toString(httpEntity,"UTF-8");
            }
            logger.info(" POST [{}], Response --->状态码:{},Content-Type:{},result:{}", httpPost.getURI(),code,entity.getContentType(),result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("POST failure :caused by-->" + e.getMessage());
        }finally {
            if (httpClient!=null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Http request Post method
     * @param uri example "https://www.baidu.com/"
     * @param headerMap  https 头
     * @param requestBody  请求参数
     * @return
     */
    public static String postMethod(String uri, String requestBody){
        Map map = new HashMap();
        map.put("Content-Type","application/json;charset=UTF-8");
        return postMethod(uri,map,requestBody);
    }

}
