<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rolldata.core.util.SysPropertiesUtil" %>
<%@ page import="com.rolldata.core.util.JspCodeUtils" %>
<%@ page import="com.rolldata.core.util.StringUtil" %>
<%  
    String jspUpdateVersion = JspCodeUtils.getJspVersion();
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";
    String configBasePath = SysPropertiesUtil.getConfig("basePath");
    if(StringUtil.isNotEmpty(configBasePath)){
        basePath = configBasePath;
    }
%> 
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="jspUpdateVersion" value="<%=jspUpdateVersion%>" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>404</title>
</head>

<body style="background-color: #fff;">

<!-- 正文开始 -->
<div class="error-page">
    <div class="error-page-info">
        <div style="width: 400px;height: 400px;position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%);">
            <img src="<%=basePath%>static/images/wuyemian.png" style="position: absolute;top: 35%;left: 50%;transform: translate(-50%, -50%);width: 250px;">
            <h3 class="font-bold" style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);margin-top: 50px;color: #666;font-size: 16px;font-weight: 400;">啊哦，你访问的页面不存在</h3>
        </div>
    </div>
</div>
</body>

</html>
