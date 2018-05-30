# 作用
- 实现对tomcat封装的HttpServletRequest.getInputStream()获得的流的可重复读取
- 实现当请求类型是POST且Content-Type是application/x-www-form-urlencoded时
getParameter()和getParameterMap()后仍能对请求流的重复读取