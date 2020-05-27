package com.ims.c06uploadAndDownload;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class UploadServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");

        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        //web应用的硬盘路径为起点
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            //创建保存目录
            saveFile.mkdir();
        }
        //上传时生成的临时文件保存目录
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists()) {
            //创建临时目录
            tmpFile.mkdir();
        }

        //消息提示
        String message = "";
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //      设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
            //设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            factory.setSizeThreshold(1024 * 100);
            //      设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //      监听文件上传进度
            upload.setProgressListener(new ProgressListener() {
                @Override
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                }
            });
            //      解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //      设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
            upload.setFileSizeMax(1024 * 1024);
            //      设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
            upload.setSizeMax(1024 * 1024 * 10);
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                return;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项

            //方式2 最终文件 流式
            /*FileItemIterator fileItemIterator = upload.getItemIterator(request);
            while(fileItemIterator.hasNext()) {
                FileItemStream fileItemStream = fileItemIterator.next();
                InputStream stream = fileItemStream.openStream();
                String fileName;
                String fileValue;
                if (fileItemStream.isFormField()) {
                    fileName = fileItemStream.getFieldName();
                    fileValue = Streams.asString(stream, "utf-8");
                } else  {
                    fileName = fileItemStream.getFieldName();
                    fileName = fileItemStream.getName();
                    OutputStream fileOutputStream = new FileOutputStream(new File("c:/"
                            + fileName));
                    IOUtils.copy(stream, fileOutputStream);
                    IOUtils.closeQuietly(stream);
                    IOUtils.closeQuietly(fileOutputStream);
                }
            }*/

            //方式1 传统方式 临时文件+最终文件
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println("普通项：" + name + "=" + value);
                } else {//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    System.out.println("文件项：" + filename);
                    //得到上传文件的扩展名,如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    //String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //得到文件保存的名称
                    String saveFilename = UUID.randomUUID().toString() + "_" + filename;
                    //得到文件保存目录，例如：upload\2\3  upload\3\5
                    int hashcode = filename.hashCode();
                    int dir1 = hashcode & 0xf;  //0--15
                    int dir2 = (hashcode & 0xf0) >> 4;  //0-15
                    String realSavePath = savePath + "\\" + dir1 + "\\" + dir2;  //
                    File realSaveFile = new File(realSavePath);
                    if (!realSaveFile.exists()) {
                        realSaveFile.mkdirs();
                    }
                    //创建一个文件输出流
                    FileOutputStream fos = new FileOutputStream(realSavePath + "\\" + saveFilename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        fos.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    fos.close();
                     /*IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    IOUtils.closeQuietly(fos);*/
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }
            PrintWriter out = response.getWriter();
            out.print(message);
            out.print(message);
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "文件上传失败！！！");
        }
    }
}