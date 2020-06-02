<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Arrays" %><%--
  User: Administrator
  Date: 2020/6/2
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    //get接收参数
    String name = request.getParameter("name");
    System.out.println("name：" + name);
    //post接收参数
    /*InputStream in = request.getInputStream();
    byte[] b = new byte[1024];
    StringBuilder sb = new StringBuilder();
    int len = -1;
    while( -1 != (len =in.read(b)))
    {
        byte[] temp = null;
        temp = Arrays.copyOf(b, len); // 针对最后一次数据读入，防止从流中读入的数据中包含空格。
        sb.append(new String(temp));
    }
    in.close();
    System.out.println("name：" + sb.toString());*/
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
