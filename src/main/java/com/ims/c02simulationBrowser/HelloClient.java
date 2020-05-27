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

/**
 * @author Administrator
 */
public class HelloClient {
    public static void main(String[] args) throws IOException {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet httpGet = new HttpGet("http://www.tuicool.com/");
        //  模拟浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        // 执行http get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 响应状态
        System.out.println("Status:" + response.getStatusLine().getStatusCode());
        // 获取返回实体
        HttpEntity entity = response.getEntity();
        //响应类型
        System.out.println("Content-Type:" + entity.getContentType().getValue());
        //响应内容
        System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8"));
        // response关闭
        response.close();
        // httpClient关闭
        httpClient.close();
    }
}
