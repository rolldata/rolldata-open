<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="com.rolldata.core.util.SysPropertiesUtil" %>
<%@ page import="com.rolldata.core.util.JspCodeUtils" %>
<%@ page import="com.rolldata.core.util.StringUtil" %>
<%@include file="/webpage/common/taglibs.jspf"%>
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
      <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>错误页面</title>
  </head>
  
  <body>
    <div style="width: 400px;height: 400px;position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%);">
        <img src="<%=basePath%>static/images/wuwangluo.png" style="position: absolute;top: 35%;left: 50%;transform: translate(-50%, -50%);width: 250px;">
        <h3 class="font-bold" style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);margin-top: 50px;color: #666;font-size: 16px;font-weight: 400;">访问页面错误 <br>${msg} <br></h3>
    </div>
  </body>
</html>
