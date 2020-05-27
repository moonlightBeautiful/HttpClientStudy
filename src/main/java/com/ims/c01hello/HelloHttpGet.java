package com.ims.c01hello;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HelloHttpGet {
    public static void main(String[] args) throws IOException {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        // 执行http get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 获取返回实体
        HttpEntity entity = response.getEntity();
        // 获取网页内容
        System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8"));

        response.close();
        httpClient.close();
    }
}
