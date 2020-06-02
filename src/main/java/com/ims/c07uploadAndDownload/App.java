package com.ims.c07uploadAndDownload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.UUID;

public class App {

    /**
     * 请求上传
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String uploadFilesToFileServer(ServletRequest request) throws UnsupportedEncodingException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.setCharacterEncoding("utf-8");
        String result = "";
        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iter = upload.getItemIterator(httpServletRequest);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                InputStream stream = item.openStream();
                String fieldName;
                String fieldValue;
                if (item.isFormField()) {
                    fieldName = item.getFieldName();
                    fieldValue = Streams.asString(stream, "utf-8");
                    System.out.println("普通字段，fieldName：" + fieldName + "，fieldValue：" + fieldValue);
                } else {
                    fieldName = item.getName();
                    if (fieldName == null || fieldName.trim().equals("")) {
                        continue;
                    }
                    // fieldName = item.getFieldName();
                    // fieldName = new String(fieldName.getBytes("gbk"), "utf-8");
                    System.out.println("文件字段，fieldName：" + fieldName);


                    System.out.println("httpClient模拟浏览器请求上传文件开始=================================");
                    // 创建httpClient实例
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    // 创建httpPost实例
                    HttpPost httpPost = new HttpPost("http://localhost:8080/HttpClientStudy/uploadFiles");
                    RequestConfig config = RequestConfig.custom()
                            .setConnectTimeout(10000)
                            .setSocketTimeout(10000)
                            .build();
                    httpPost.setConfig(config);
                    httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
                    //入参相关↓
                    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                    entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    entityBuilder.setCharset(Charset.forName("utf-8"));
                    ContentBody body = new InputStreamBody(stream, fieldName);
                    /*reqEntity.addPart("file", new FileBody(file));*/
                    entityBuilder.addPart(UUID.randomUUID().toString(), body); //文件参数
                    entityBuilder.addTextBody("fileName", fieldName);  //文件参数
                    HttpEntity httpEntity = entityBuilder.build();
                    httpPost.setEntity(httpEntity);
                    //入参相关↑
                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity, "utf-8");
                    response.close();
                    httpClient.close();
                    System.out.println("httpClient模拟浏览器请求上传文件结束=================================");
                }
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("获取http请求中的上传文件失败", ex);
        }
    }


    /**
     * 请求下载
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String downloadFilesToFileServer(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String fileName = httpServletRequest.getParameter("fileName");
        String result = "";
        OutputStream outputStream = null;
        InputStream inputStream = null;

        System.out.println("httpClient模拟浏览器请求下载文件开始=================================");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/HttpClientStudy/downloadFiles");
        httpGet.addHeader("fileName", URLEncoder.encode(fileName, "utf-8")); //get方式参数+头上
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        httpGet.setConfig(config);
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        HttpEntity entity = response1.getEntity();
        inputStream = entity.getContent();  //返回的文件
        result = URLDecoder.decode(response1.getFirstHeader("message").getValue(), "UTF-8");    //返回的文本
        outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.reset();
        httpServletResponse.setContentType("application/x-download; charset=UTF-8");
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.substring(fileName.lastIndexOf("_") + 1),
                "utf-8"));
        IOUtils.copy(inputStream, outputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        System.out.println("httpClient模拟浏览器请求下载文结束=================================");
        return result;
    }
}
