<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 2017/8/21
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<p>Welcome to home</p>
<p><a href="/hello/aaa.html">GoTo Hello</a></p>
<form>
    <button formaction="hello/bbb.html" formmethod="post" type="submit">PostGoTo</button>
</form>
</body>
</html>
