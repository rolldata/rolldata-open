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
	<title>目录管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/zTreeStyle.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/layZTreeStyle.css?v=<%=jspUpdateVersion%>">
	<style>
		@media screen and (min-device-width:1366px) {
			.layui-nav-treebody>.layui-nav-treebox {
				top: 40px;
				right: 0;
				left: 0;
			}

			.IndexDirectory {
				position: relative;
				text-align: left;
				height: 40px;
				line-height: 40px;
				color: #333;
				font-size: 14px;
				font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif;
				font-weight: bolder;
				padding-left: 40px;
			}

			.IndexDirectory::before {
				content: "";
				background: url(<%=basePath%>static/images/icon.png) no-repeat center center;
				height: 15px;
				width: 15px;
				font-weight: bold;
				background-size: 100%;
				display: inline-block;
				position: absolute;
				top: 13px;
				left: 20px;
			}

			.layui-nav-treebody>.layui-nav-treebox {
				top: 40px;
				right: 0;
				left: 0;
			}
		}
		@media screen and (max-device-width:1366px) {
			.layui-nav-treebody>.layui-nav-treebox {
				top: 30px;
				right: 0;
				left: 0;
			}

			.IndexDirectory {
				position: relative;
				text-align: left;
				height: 32px;
				line-height: 32px;
				color: #333;
				font-size: 12px;
				font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif;
				font-weight: bolder;
				padding-left: 40px;
			}

			.IndexDirectory::before {
				content: "";
				background: url(<%=basePath%>static/images/icon.png) no-repeat center center;
				height: 12px;
				width: 12px;
				font-weight: bold;
				background-size: 100%;
				display: inline-block;
				position: absolute;
				top: 10px;
				left: 24px;
			}

			.layui-nav-treebody>.layui-nav-treebox {
				top: 30px;
				right: 0;
				left: 0;
			}
		}
		.activeList{
			background: #f2f2f2;
		}
	</style>
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
	<div id="LAY_app" class="wrapper funcManage-page show-layui-navbar">
		<!-- ============================================================== -->
		<!-- layui-layout - style you can find in pages.css -->
		<!-- ============================================================== -->
		<div class="layui-layout">
			<!-- ============================================================== -->
			<!-- layui-side - style you can find in pages.css -->
			<!-- ============================================================== -->
			<div class="layui-side">
				<div class="layui-nav-treebody">
					<div class="layui-nav-treebar">
						<shiro:hasPermission name="sys:function:saveMenuInfo">
							<div class="layui-nav-treebar-item btn-add" id="LAY-tree-add" title="新建目录">
								<i class="iconfont icon-create"></i>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:function:deleteMenuInfo">
							<div class="layui-nav-treebar-item btn-del" id="LAY-tree-remove" title="删除目录">
								<i class="iconfont icon-delete"></i>
							</div>
						</shiro:hasPermission>
						<div class="layui-nav-treebar-item btn-search" id="LAY-tree-openSearch" title="查询目录">
							<i class="iconfont icon-search"></i>
						</div>
						<div class="layui-nav-searchbox layui-hide">
							<input class="tree-search layui-input" name="" value="" id="LAY-tree-search" />
							<div class="layui-nav-search-close" id="LAY-tree-closeSearch"><i
									class="iconfont icon-close"></i></div>
						</div>
					</div>
					<div class="layui-nav-treebox">
						<div class="IndexDirectory publicResourcel" style="cursor: pointer">
							管理目录
						</div>
						<div class="layui-nav-tree layui-mylayui1-tree" id="LAY-func-tree" lay-filter="layui-func-tree">
						</div>
					</div>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End layui-side -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- layui-body - style you can find in pages.css -->
			<!-- ============================================================== -->
			<div class="layui-body" id="LAY-func-body">
				<div class="layui-fluid layui-height-full-30">
					<div class="box-func layui-height-full">

						<div class="layui-card layui-height-full">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>目录及配置</div>
								<div class="layui-right-float">
									<shiro:hasPermission name="sys:function:updateMenuInfo">
										<button class="layui-btn layui-btn-sm layui-btn-cardTop save-role"
												id="btn-edit-theme">
											<i class="layui-icon layui-icon-edit"></i>
											编辑
										</button>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="layui-card-body layui-height-full-62">
								<div class="table-box">
									<table class="layui-hide" id="funcList" lay-filter="funcList"></table>
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
</body>
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery-1.4.4.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery.ztree.all.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/fuzzysearch.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/source/func.js?v=<%=jspUpdateVersion%>"></script>

</html>
