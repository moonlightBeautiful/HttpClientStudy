package com.ims.c06passParamAndReceiveReturn;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: GaoXu
 * @date: 2020/5/24
 * @desc: 请对类进行描述
 */
public class App {

    public static void main(String[] args) throws IOException {


        // get方式的传统传值
       /* HttpGet get = new HttpGet("http://localhost:8080/HttpClientStudy/c06passParamAndReceiveReturn/test.jsp?name=jiaxin");
        get.setHeader("Accept","application/json;charset=utf-8");
        HttpResponse response = client.execute(get);
        System.out.println("响应码："+response.getStatusLine());
        System.out.println("响应body体："+ EntityUtils.toString(response.getEntity()));*/

        //post方式的传统传值
        /*HttpPost post = new HttpPost("http://localhost:8080/HttpClientStudy/c06passParamAndReceiveReturn/test.jsp");
        post.setHeader("Accept", "application/json;charset=utf-8");
        JSONObject params = new JSONObject();
        params.put("name", "zhangSan");
        // 传值时传递的是json字符串，这样的好处是在服务端无需建立参数模型，直接接收String，便于后期维护。
        StringEntity stringEntity = new StringEntity(params.toString(), "utf-8");
        post.setEntity(stringEntity);
        HttpResponse response = client.execute(post);
        System.out.println("响应码：" + response.getStatusLine());
        System.out.println("响应body体：" + EntityUtils.toString(response.getEntity()));*/


        //form方式

        //post
        // 创建请求参数
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/HttpClientStudy/c06passParamAndReceiveReturn/test.jsp");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
        BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
        list.add(param1);
        list.add(param2);
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
        HttpResponse response = client.execute(httpPost);
        System.out.println("响应码：" + response.getStatusLine());
        System.out.println("响应body体：" + EntityUtils.toString(response.getEntity()));
    }
}
