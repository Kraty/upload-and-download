<%--
  Created by IntelliJ IDEA.
  User: 14576
  Date: 2020/7/14
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<html>
<head>
    <title>文件下载</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/download?filename=1.png">文件下载</a>
<a href="${pageContext.request.contextPath}/download?filename=<%=URLEncoder.encode("截图.png", StandardCharsets.UTF_8)%>">中文文件下载</a>
</body>
</html>
