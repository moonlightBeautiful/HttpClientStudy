package com.ims.c06passParamAndReceiveReturn;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: GaoXu
 * @date: 2020/5/24
 * @desc: 请对类进行描述
 */
public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {

        //form方式
        //post
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/HttpClientStudy/c06passParamAndReceiveReturn/test.jsp");
        /** 第一种添加参数的形式 */
        /*uriBuilder.addParameter("name", "root");
        uriBuilder.addParameter("password", "123456");*/
        /** 第二种添加参数的形式 */
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
        BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
        list.add(param1);
        list.add(param2);
        uriBuilder.setParameters(list);
        /** 第三种 直接放在url后面 */
        /*HttpGet get = new HttpGet("http://localhost:8080/HttpClientStudy/c06passParamAndReceiveReturn/test.jsp?name=jiaxin");*/

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        System.out.println("响应码：" + response.getStatusLine());
        System.out.println("响应body体：" + EntityUtils.toString(response.getEntity()));

        //post
        // 创建请求参数
        /*CloseableHttpClient client = HttpClients.createDefault();
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
        System.out.println("响应body体：" + EntityUtils.toString(response.getEntity()));*/
    }
}
