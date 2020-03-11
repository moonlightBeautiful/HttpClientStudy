package com.ims.c02simulationBrowser;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HelloClient {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/"); // 创建httpget实例
        CloseableHttpResponse response = null;

        //模拟浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        response = httpClient.execute(httpGet); // 执行http get请求
        //响应状态
        System.out.println("Status:" + response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity(); // 获取返回实体
        //响应类型
        System.out.println("Content-Type:" + entity.getContentType().getValue());
        //响应内容
        System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8")); // 获取网页内容

        response.close(); // response关闭
        httpClient.close(); // httpClient关闭

    }
}
