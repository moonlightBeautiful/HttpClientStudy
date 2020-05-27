<%@ page import="com.ims.c06uploadAndDownload.HttpClient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");

    String op = request.getParameter("op");
//    String userName = request.getParameter("userName");  //文件二进制提交的form内容这样获取不到了
    if ("uploadFiles".equals(op)) {
        String message = HttpClient.uploadFilesToFileServer(request);
        session.setAttribute("message", message);
        response.sendRedirect("message.jsp");
    } else if ("downloadFiles".equals(op)) {
        String message = HttpClient.downloadFilesToFileServer(request, response);
        out.clear();
        out = pageContext.pushBody();
    }
%>
