<%--
  Created by IntelliJ IDEA.
  User: Silentdoer
  Date: 2017/11/22
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="silentdoer.taglib.tld.silentdoer-so" prefix="so" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<p>Hello, strangle.</p>
<c:out value="${ok}">sss</c:out>
<c:out value="${ok}" default="sss" />
<p>mmm${testId}</p>
<so:select id="${testId}" name="aaccabb" allowMulti="false" showLines="${count}" options="options">
    你好${elTest}啊mmjHello
    <c:out value="${elTest}" default="bbb"/>
</so:select>
</body>
</html>
