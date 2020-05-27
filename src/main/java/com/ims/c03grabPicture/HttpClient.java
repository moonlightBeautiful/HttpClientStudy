package com.ims.c03grabPicture;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.InputStream;

public class HttpClient {

    public static void main(String[] args) throws Exception {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet httpGet = new HttpGet("http://www.java1234.com/gg/dljd4.gif");
        // 模拟火狐浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        // 执行http get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 获取返回实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 获取返回实体类型
            System.out.println("ContentType:" + entity.getContentType().getValue());
            InputStream inputStream = entity.getContent();
            FileUtils.copyToFile(inputStream, new File("ljd4.gif"));
        }
        // 释放资源
        response.close();
        httpClient.close();
    }
}
