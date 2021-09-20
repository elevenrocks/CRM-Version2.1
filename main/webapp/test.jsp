<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2021/9/20
  Time: 1:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>

    String createTime = DateTimeUtil.getSysTime();
    //创建人从session作用域中拿出来
    String createBy = ((User) req.getSession().getAttribute("user")).getName();
</head>