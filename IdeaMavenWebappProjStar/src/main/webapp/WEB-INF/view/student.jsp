<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 2017/8/21
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Manager</title>
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
<p>Hello</p>
<form action="/student/add" method="post">
    <p>姓名：<input name="stdName" type="text" /></p>
    <p>性别：<input name="stdSex" type="text" /></p>
    <input value="添加" type="submit" />
</form>
</body>
</html>
