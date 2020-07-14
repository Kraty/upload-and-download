<%--
  Created by IntelliJ IDEA.
  User: 14576
  Date: 2020/7/13
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>文件上传</title>
    <script type="text/javascript">
        function check() {
            const name = document.getElementById("name").value;
            const file = document.getElementById("file").value;
            if (name === "") {
                alert("填写上传人")
                return false;
            }
            return !(file.length === 0 || file === "");
            
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/fileUpload" method="post" enctype="multipart/form-data"
      onsubmit="return check()">
    上传人：<label for="name"></label><input id="name" type="text" name="name"><br/>
    <%--多文件上传multiple属性--%>
    请选择文件：<input id="file" type="file" name="upfile" multiple="multiple">
    <input type="submit" value="上传">
</form>
</body>
</html>
