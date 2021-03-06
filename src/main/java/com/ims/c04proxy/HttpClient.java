package com.ims.c04proxy;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.InputStream;

public class HttpClient {

    public static void main(String[] args) throws Exception {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        // 使用代理ip
        HttpHost proxy = new HttpHost("223.82.106.253", 3128,"http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
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
