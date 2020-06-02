package com.ims.c07uploadAndDownload;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DownLoadServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String message = "";

        //得到要下载的文件名
        String fileName = request.getHeader("fileName");
        fileName = URLDecoder.decode(fileName, "utf-8");
        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
        String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        //通过文件名找出文件的所在目录
        int hashcode = fileName.substring(fileName.indexOf("_") + 1).hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        String path = fileSaveRootPath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
        //得到要下载的文件
        File file = new File(path + "\\" + fileName);
        //如果文件不存在
        if (!file.exists()) {
            response.addHeader("message", "文件不存在！");
            return;
        }
        //处理文件名
        //String realname = fileName.substring(fileName.indexOf("_") + 1);
        //读取要下载的文件，保存到文件输入流
        inputStream = new FileInputStream(file);
        //创建输出流
        outputStream = response.getOutputStream();
        response.addHeader("message", URLEncoder.encode("下载文件成功！","utf-8"));       //必须在IOUtils.copy(inputStream, outputStream);    之前
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }
}
