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
  <meta charset="utf-8">
  <meta http-equiv="x-ua-compatible" content="IE=Edge,chrome=1">
  <meta name="renderer" content="webkit" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
  <title>demo管理</title>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/zTreeStyle.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/layZTreeStyle.css?v=<%=jspUpdateVersion%>">
</head>

<body>
<!-- ============================================================== -->
<!-- Configuration  - input:type=hidden -->
<!-- ============================================================== -->
<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
<input type="hidden" name="viewType" id="viewType" value="overview">
<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.css -->
<!-- ============================================================== -->
<div id="LAY_app" class="wrapper resourceListManage-page">
  <!-- ============================================================== -->
  <!-- layui-layout - style you can find in pages.css -->
  <!-- ============================================================== -->
  <div class="layui-layout">
    <!-- ============================================================== -->
    <!-- layui-side - style you can find in pages.css -->
    <!-- ============================================================== -->
    <div class="layui-side">
    </div>
    <!-- ============================================================== -->
    <!-- End layui-side -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- layui-body - style you can find in pages.css -->
    <!-- ============================================================== -->
    <div class="layui-body" id="LAY-theme-body">
      <div class="layui-fluid layui-height-full-30">
        <div class="box-theme layui-height-full">
          <div class="layui-card layui-height-full">
            <div class="layui-card-header">
              <div class="TitleVertical"><span class="vertical"></span>demo信息</div>
              <div class="layui-right-float">
                <div class="layui-input-inline layui-form-item-sm layui-hide" style="position:relative;">
                  <input type="text" class="layui-input" id="allSearchInput" name="searchInput"
                         style="width: 170px; margin-right: 3px;">
                  <botton type="button" id="allSearch" class="layui-btn-sm"
                          style="margin-left:0;position: absolute;top:0;right:0;">
                    <i class="layui-icon layui-icon-search" style="color:#4785e6"></i>
                  </botton>
                </div>
                <button class="layui-btn layui-btn-sm layui-btn-cardTop push-btn">
                  <i class="iconfont icon-faqi1"></i>
                  启停
                </button>
              </div>
            </div>
            <div class="layui-card-body layui-height-full-62">
              <div class="table-box" style="margin-top:0">
                <table class="layui-hide" id="themeList" lay-filter="themeList"></table>
                <div class="pageCount" style="width: 96%">
                  <div class="pageCountText"></div>
                  <div id="pageCount"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- ============================================================== -->
    <!-- End layui-body -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- layui-collapse - style you can find in pages.css -->
    <!-- ============================================================== -->
    <div class="layui-navbar-collapse layui-hide">
      <div class="navbar-collapse-inner">
        <div class="navbar-collapse-bg"></div>
        <div class="navbar-collapse">
          <span class="icon-collapse-left iconfont icon-push"></span>
          <span class="icon-collapse-right iconfont icon-rollout"></span>
        </div>
      </div>
    </div>
    <!-- ============================================================== -->
    <!-- End layui-collapse -->
    <!-- ============================================================== -->
  </div>
  <!-- ============================================================== -->
  <!-- End layui-layout -->
</div>

<!-- ============================================================== -->
<!-- End Wrapper -->
<!-- ============================================================== -->
<form id="uploadexcelstyle-form" action="" enctype="multipart/form-data" method="post"
      style="position: absolute; top: -1000px; left: -9999px;">
  <input id="importExcel" type="file" name="importSource">
  <input type="hidden" name="sourceId" id="sourceId-input">
</form>
<iframe id="export-iframe" src="" style="position: absolute; top: -1000px; left: -9999px;"></iframe>
</body>
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/source/demoList.js?v=<%=jspUpdateVersion%>"></script>

<script type="text/html" id="themebar">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>

</html>
