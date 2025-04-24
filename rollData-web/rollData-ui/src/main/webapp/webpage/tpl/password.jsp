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
	<title>password</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/eleTree/eleTree.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/eleTree/font/eletreefont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
</head>

<body>
	<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
	<div class="changepassword" style="background-color:#fff;">
		<div class="layui-fluid">
			<form action="" method="POST" class="layui-form layui-height-full-30" enctype="multipart/form-data" style="padding-top:15px">
				<div class="layui-form-item-sm">
					<label class="layui-form-label" for="oldpassword">当前密码：</label>
					<div class="layui-input-inline">
						<input type="password" name="oldpassword" placeholder="请输入当前密码" autocomplete="off" lay-verify="required" class="layui-input oldPassword-input" lay-vertype="tips">
					</div>
				</div>
				<div class="layui-form-item-sm">
					<label class="layui-form-label" for="newPassword">新密码：</label>
					<div class="layui-input-inline">
						<input type="password" name="newPassword" placeholder="请输入新密码" autocomplete="off" lay-verify="required|newpassword"  class="layui-input newPassword-input" lay-vertype="tips">
					</div>
				</div>
				<div class="layui-form-item-sm">
					<label class="layui-form-label" for="repeatPassword">确认密码：</label>
					<div class="layui-input-inline">
						<input type="password" name="repeatPassword" placeholder="请再次输入新密码" autocomplete="off" lay-verify="required|repeatpassword" class="layui-input repeatPassword-input" lay-vertype="tips">
					</div>
				</div>
				<div class="layui-form-item-sm" style="padding-top:20px;text-align:right">
					<button class="layui-btn layui-btn-sm" id="sure" lay-submit="" lay-filter="sure">确认</button>
					<button class="layui-btn layui-btn-sm layui-btn-primary" id="off">取消</button>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/password.min.js?v=<%=jspUpdateVersion%>"></script>
</html>
