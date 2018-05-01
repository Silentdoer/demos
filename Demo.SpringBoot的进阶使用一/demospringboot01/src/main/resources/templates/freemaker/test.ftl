<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello, Freemaker</title>
</head>

<!-- 其实纯html文件一样可以写成.ftl为后缀 -->
<body>
<!-- 若date存在则输出date，否则输出!后面的默认值 -->
Date: ${date!"date not set."}<br>
<!-- xx message??>是用来判断message是否存在，存在则进入freemaker的if块 -->
Message: <#if message??>${message}</#if><br>
<!-- 浏览器解释空格和换行应该不是以txt文本的形式而是比如<br>表示换行，空格是&nbsp;，因此在html文件上的二进制值为\n的不会被解释为换行 -->
<table border="2">
    <tr>
        <td>标识</td>
        <td>姓名</td>
        <td>性别</td>
    </tr>


    <!-- 不会被解释为换行 -->


    <!-- 注意studs要和model中pair的key一致 -->
<#list studs as stud>
    <tr>
        <td>
        ${stud.uid}
        </td>
        <td>
        ${stud.name}
        </td>
        <td>
        ${stud.gender}
        </td>
    </tr>
</#list>
</table>

</body>
</html>