package com.ims.c01hello;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HelloHttpPost {
    public static void main(String[] args) throws IOException {
        // httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // httpPost
        HttpPost httpPost = new HttpPost("http://www.baidu.com/");
        // 执行httpPost请求
        CloseableHttpResponse response =httpClient.execute(httpPost);
        // 获取httpPost请求返回的httpEntity
        HttpEntity httpEntity = response.getEntity();
        // 解析httpEntity
        System.out.println("网页内容：" + EntityUtils.toString(httpEntity, "utf-8"));
        // 释放资源response关闭 httpClient关闭
        response.close();
        httpClient.close();
    }
}
