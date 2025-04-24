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
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>控制台DS</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
</head>

<body>
    <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
    <div id="LAY_app" class="wrapper homePageDS-page">
        <div class="DSwrap">
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">数据采集</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">数据查询</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">上报办理</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">财务分析</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">销售分析</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">企业报表</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">我的目录</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">数据源管理</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">模型管理</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">表单设计</div>
            </div>
            <div class="nav-item">
                <div class="nav-icon nav-icon-1"></div>
                <div class="nav-txt">配置管理</div>
            </div>
        </div>
        
    </div>
    <!-- js部分 -->
    <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/homePage_DS.min.js?v=<%=jspUpdateVersion%>"></script>
</body>

</html>
