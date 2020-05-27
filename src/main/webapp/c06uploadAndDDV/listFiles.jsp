<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/10
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>下载文件显示页面</title>
</head>

<body>
<!-- 遍历Map集合 -->
<c:forEach var="me" items="${fileNameMap}">
    ${me.value}<a href="${pageContext.request.contextPath}/c06uploadAndDDV/httpClientAction.jsp?op=downloadFiles&fileName=${me.key}" target="_blank">下载</a>
    <br/>
</c:forEach>
</body>
</html>
