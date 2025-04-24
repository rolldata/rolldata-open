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
		<title>公告管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
  </head>
  
  <body>
	<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
	<!-- 公告管理 <br> -->
	<!-- ============================================================== -->
	<!-- layui-body - style you can find in pages.css -->
	<!-- ============================================================== -->
	<div class="layui-body" id="LAY-role-body">
		<div class="layui-fluid layui-height-full-30">
			<div class="layui-card layui-height-full">
				<div class="layui-card-header">
					<div class="TitleVertical"><span class="vertical"></span>公告管理</div>
					<div class="layui-right-float">
						<div class="layui-input-inline layui-form-item-sm" style="position:relative;">
							<input type="text" class="layui-input" id= "allSearchInput" name="searchInput" style="width: 170px; margin-right: 3px;">
							<botton type="button" id="allSearch" class="layui-btn-sm" style="margin-left:0;position: absolute;top:0;right:0;">
								<i class="layui-icon layui-icon-search" style="color:#4785e6"></i>
							</botton>
						</div>
						<shiro:hasPermission name="sys:announcement:saveAnnouncementInfo">
							<button class="layui-btn layui-btn-cardTop layui-btn-sm save-role" id="btn-save-theme"  style="margin-left:0">
								<i class="iconfont icon-save"></i>
								新建
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:announcement:updateAnnouncementInfo">
							<button class="layui-btn layui-btn-cardTop layui-btn-sm save-role" id="btn-edit-theme" style="margin-left:0">
								<i class="layui-icon layui-icon-edit"></i>
								编辑
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:announcement:deleteAnnouncements">
							<button class="layui-btn layui-btn-cardTop layui-btn-sm save-role" id="btn-del-theme" style="margin-left:0">
								<i class="layui-icon layui-icon-delete"></i>
								刪除
							</button>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="layui-card-body layui-height-full-62">
					<div class="table-box">
						<table class="announcementList" id="announcementList" lay-filter="announcementList"></table>
						<div class="pageCount" style="width: 96%">
							<div class="pageCountText"></div>
							<div id="pageCount"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ============================================================== -->
	<!-- End layui-body -->
	<!-- ============================================================== -->
				
  </body>
  <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/source/announcementManage.js?v=<%=jspUpdateVersion%>"></script>
</html>
