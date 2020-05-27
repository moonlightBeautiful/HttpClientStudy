package com.ims.c05timeout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://mvnrepository.com/");

        // 设置连接超时时间 10秒钟 读取超时时间 10秒钟
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        httpGet.setConfig(config);
        // 模拟火狐浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        // 执行http get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 获取返回实体
        HttpEntity entity = response.getEntity();
        // 获取网页内容
        System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8"));
        // 释放资源
        response.close();
        httpClient.close();
    }
}
