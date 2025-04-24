<%--
  Created by IntelliJ IDEA.
  User: EDZ
  Date: 2021/8/10
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>

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
<html>
<head>
  <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>管理控制台主页</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.css?v=<%=jspUpdateVersion%>"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
    <style>
      .cols{
        display: inline-block;
        width: 18.5%;
        height: 100%;
        float: left;
       
      }
    
      .cols p{
        display: inline-block;
        margin-left: 21px;
        font-size: 20px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #333333;
        vertical-align: middle;
      }
      .cols .colsdiv{
        height: auto;
        border-left: 2px solid #EBEFF7;
        margin-left: 30px;
        margin-top: 49px;
      }
      .colsdiv .clickdiv{
        height: 40px;
        font-size: 16px;
        color: #3F525F;
        cursor: pointer;
        padding-top: 15px;
        border-left: 1px solid #B9C6CF;
        
      }
      .colsdiv .clickdiv .divtext{
        padding-left: 50px;
        height: 30px;
        line-height: 30px;
      }
     
      .colsdiv .clickdiv .divtext:hover{
        border-left: 2px solid #2880F2;
        color: #4785e6;
        padding-left: 48px;
      }

      .DSwrap .cols:first-child{
        margin-left:98px;
      }
    </style>
</head>
<body>
  <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
  <div id="LAY_app" class="wrapper homePageDS-page">
    <div class="DSwrap" style="padding-top: 77px;letter-spacing: normal;height: 100%;min-width: 1650px;">
       
    </div>
  </div>

  <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
  <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
  <script type="text/javascript" src="<%=basePath%>static/js/consoleHomePage.min.js?v=<%=jspUpdateVersion%>"></script>

</body>
</html>
