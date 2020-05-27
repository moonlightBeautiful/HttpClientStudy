<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/10
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/c06uploadAndDDV/httpClientAction.jsp?op=uploadFiles" enctype="multipart/form-data" method="post"
      target="_blank">
    上传用户：<input type="text" name="userName"><br/>
    上传文件1：<input type="file" name="file1"><br/>
    上传文件2：<input type="file" name="file2"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
