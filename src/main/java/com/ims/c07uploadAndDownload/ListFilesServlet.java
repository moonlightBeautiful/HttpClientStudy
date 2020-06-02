package com.ims.c07uploadAndDownload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ListFileServlet
 * @Description: 列出Web系统中所有下载文件
 * @author: hdb
 * @date: 2017-11-14 19:54:40
 */
public class ListFilesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取上传文件的目录
        String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        //存储要下载的文件名
        Map<String, String> fileNameMap = new HashMap<String, String>();
        //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
        listFiles(new File(uploadFilePath), fileNameMap);
        //将Map集合发送到downloadFiles.jsp页面进行显示
        request.setAttribute("fileNameMap", fileNameMap);
        request.getRequestDispatcher("/c06uploadAndDDV/listFiles.jsp").forward(request, response);
    }

    /**
     * @param file 即代表一个文件，也代表一个文件目录
     * @param map  存储文件名的Map集合
     * @Method: listfile
     * @Description: 递归遍历指定目录下的所有文件
     * @Anthor:hdb
     */
    public void listFiles(File file, Map<String, String> map) {
        if (file.exists()) {
            if (!file.isFile()) {//如果file是一个目录
                //列出该目录下的所有文件和目录
                File files[] = file.listFiles();
                //遍历files[]数组
                for (File f : files) {
                    //递归
                    listFiles(f, map);
                }
            } else { //如果file是一个文件
                /**
                 * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的
                 */
                String realName = file.getName().substring(file.getName().indexOf("_") + 1);
                //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
                map.put(file.getName(), realName);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}