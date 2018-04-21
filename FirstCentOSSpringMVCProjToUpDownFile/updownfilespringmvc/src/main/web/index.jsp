<%--
  Created by IntelliJ IDEA.
  User: silentdoer
  Date: 4/3/18
  Time: 9:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UploadFile</title>
</head>
<body>
    <h2>Upload you file</h2>
    <!-- $\{\} can has three scope:1,requestScope[request];2,sessionScope[session];3,applicationScope[context]; -->
    <form action="/updownfileapp/upload" enctype="multipart/form-data" method="post">
        <table>
            <!-- this key-value is a file type descriptor -->
            <input type="hidden" name="_fileType" value="image"/>
            <tr>
                <td>Description: </td>
                <td><input type="text" name="description"/></td>
            </tr>
            <tr>
                <td>Please choose file: </td>
                <input type="file" name="file"/>
            </tr>
            <tr>
                <td><button type="submit">Submit</button></td>
            </tr>
        </table>
    </form>
</body>
</html>
