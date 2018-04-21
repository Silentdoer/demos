<%--
  Created by IntelliJ IDEA.
  User: silentdoer
  Date: 4/21/18
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Student Info</title>
</head>
<body>
    <!-- just search requestScope, can be replace requestScope["requestAttrKey"] -->
    <!-- jie zhe san tiao dou shi shu chu : value8888888 -->
    <p>${requestScope.get("requestAttrKey")}</p>
    <p>${requestScope["requestAttrKey"]}</p>
    <!-- will search all scope -->
    <p>${requestAttrKey}</p>
    <!-- zhe liang tiao shu chu : silentdoer -->
    <p>${requestScope["student"].name}</p>
    <p>${student.name}</p>
</body>
</html>
