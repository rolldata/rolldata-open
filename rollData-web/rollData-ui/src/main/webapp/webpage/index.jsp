<%@page import="com.rolldata.web.system.util.UserUtils"%>
<%@ page import="com.rolldata.core.util.SysPropertiesUtil" %>
<%@ page import="com.rolldata.core.util.JspCodeUtils" %>
<%@ page import="com.rolldata.core.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
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
    String headPhoto = UserUtils.getUser().getHeadPhoto();
    String userCode = UserUtils.getUser().getUserCde();
    String isAdmin = UserUtils.getIsAdminConsole();
%>
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="jspUpdateVersion" value="<%=jspUpdateVersion%>" />
<c:set var="userCode" value="<%=userCode%>" />
<c:set var="isAdmin" value="<%=isAdmin%>" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>${appearanceConfig.title}</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/style.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
	<style>
		.theme-skyblue .layui-layout-admin .layui-header .layui-logo {
			color: #fff;
			box-shadow: none;
			background-color: transparent
		}

		.theme-skyblue .layui-layout-admin .layui-header {
			background-color: rgb(4 153 154);
		}

		.theme-skyblue .layui-layout-admin .layui-header a {
			color: #fff
		}

		.theme-skyblue .layui-layout-admin .layui-header a:hover {
			color: #fff
		}

		.theme-skyblue .layui-layout-admin .layui-header .layui-nav-child a {
			color: #595959
		}

		.theme-skyblue .layui-layout-admin .layui-header .layui-nav .layui-nav-more {
			border-color: #eee transparent transparent
		}

		.theme-skyblue .layui-layout-admin .layui-header .layui-nav .layui-nav-mored {
			border-color: transparent transparent #eee
		}
		.theme-skyblue .layui-layout-admin .layui-header .layui-this {
			background-color: #15B3B4;
		}
		body.theme-skyblue .layui-nav-tree .layui-nav-child dd.layui-this, body.theme-skyblue .layui-nav-tree .layui-nav-child dd.layui-this a, body.theme-skyblue .layui-nav-tree .layui-this, body.theme-skyblue .layui-nav-tree .layui-this>a {
			background-color: #1AA3A4;
		}
		.theme-skyblue .layui-layout-admin .layui-header .layui-nav .layui-this:after,.theme-skyblue .layui-layout-admin .layui-header .layui-nav-bar {
			background-color: #fff
		}
		.theme-skyblue input#LAY-tree-search-input::placeholder {
			color: #fff;
		}
		.theme-skyblue .layui-layout-admin .layui-side {
			background-color: #15B3B4;
		}
		.theme-skyblue .layui-nav-tree .layui-nav-bar {
			background-color: #0A7B74;
		}
		.theme-skyblue .layui-nav-tree-toolbar-searchbox input {
			background-color: #51CACB;
			color: #fff;
		}
		.theme-skyblue .layui-nav-tree-toolbar-searchbox span {
			color: #fff;
		}
		body.theme-skyblue .layui-side .layui-nav .layui-nav-item a {
			color: #fff !important;
		}

		.theme-skyblue .layui-nav-tree .layui-nav-child dd.layui-this a,.theme-skyblue .layui-nav-tree .layui-this>a,.theme-skyblue .layui-nav-tree .layui-this>a:hover {
			color: #fff;
			background-color: #1AA3A4;
		}

		.theme-skyblue .layui-nav-tree>.layui-nav-item>a:before,
		.theme-skyblue .layui-layout-admin .layui-side .layui-nav.layui-nav-tree .layui-this:after {
			background-color: #0A7B74;
		}
		.layui-nav-titlebox {
			background-color: #fff;
		}
		.layui-nav-tree-toolbar-searchbox {
			background-color: #f7f7f7;
		}
		
	</style>
</head>

<body class="layui-layout-body">
	<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
	<shiro:hasPermission name="data:report:dataReport">
		<input type="hidden" name="TYPE_REPORT" class="createReportTypeOption" id="TYPE_REPORT" value="1">
	</shiro:hasPermission>
	<shiro:hasPermission name="data:report:dataReportList">
		<input type="hidden" name="TYPE_DATA_REPORT" class="createReportTypeOption" id="TYPE_DATA_REPORT" value="4">
	</shiro:hasPermission>
	<shiro:hasPermission name="data:report:dataReportGroupList">
		<input type="hidden" name="TYPE_REPORT_GROUP" class="createReportTypeOption" id="TYPE_REPORT_GROUP" value="5">
	</shiro:hasPermission>
	<shiro:hasPermission name="data:report:olapReport">
		<input type="hidden" name="TYPE_OLAP_REPORT" class="createReportTypeOption" id="TYPE_OLAP_REPORT" value="9">
	</shiro:hasPermission>
	<shiro:hasPermission name="data:report:dataReportBig">
		<input type="hidden" name="TYPE_BIG_SCREEN" class="createReportTypeOption" id="TYPE_BIG_SCREEN" value="6">
	</shiro:hasPermission>
	<shiro:hasPermission name="data:report:dataReportMobile">
		<input type="hidden" name="TYPE_MOBILE" class="createReportTypeOption" id="TYPE_MOBILE" value="7">
	</shiro:hasPermission>
	<shiro:hasPermission name="simple:data:report:simpleDataReportList">
		<input type="hidden" name="TYPE_SEMPLE_DATA_REPORT" class="createReportTypeOption" id="TYPE_SEMPLE_DATA_REPORT" value="10">
	</shiro:hasPermission>
	<!-- ============================================================== -->
	<!-- Configuration  - input:type=hidden -->
	<!-- ============================================================== -->
	<div id="LAY_app">
		<!-- ============================================================== -->
		<!-- Main wrapper - style you can find in style.scss -->
		<!-- ============================================================== -->
		<div id="main-wrapper" class="layui-layout layui-layout-admin admin-nav-mini">
			
			<!-- ============================================================== -->
			<!-- Header - style you can find in style.scss -->
			<!-- ============================================================== -->
			<header class="layui-header">
				<div class="layui-logo" ew-event="logo" style="cursor: pointer" ew-href="<%=basePath%>homePageController/homePage">
					<c:if test="${appearanceConfig.isInitLogo eq 1}">
						<img src="<%=basePath%>static/images/logo.png"/>
					</c:if>
					<c:if test="${appearanceConfig.isInitLogo eq 0}">
						<img src="<%=basePath%>static/images/upload/${appearanceConfig.logo}"/>
					</c:if>
					<cite>${appearanceConfig.title}</cite>
				</div>
				<!-- ============================================================== -->
				<!--  Nav-left  -->
				<!-- ============================================================== -->
				<ul class="layui-nav layui-layout-left layui-nav-large" style="position:relative">
					<li class="layui-nav-item layui-hide layui-nav-item-funbtn" lay-unselect>
						<a ew-event="flexible" title="侧边伸缩"><i class="layui-icon layui-icon-shrink-right"></i></a>
					</li>
					<li class="layui-nav-item layui-hide layui-nav-item-funbtn" lay-unselect>
						<a ew-event="refresh" title="刷新"><i class="layui-icon layui-icon-refresh-3"></i></a>
					</li>
				</ul>
				<!-- ============================================================== -->
				<!-- End  Nav-left  -->
				<!-- ============================================================== -->
				<!-- ============================================================== -->
				<!--  Nav-center  -->
				<!-- ============================================================== -->
				<div class="iconfont icon-zashuangsanjiaojiantou1 admin-tabs-nav-control layui-icon-prev layui-hide" ew-event="leftNavPage"></div>
				<div class="layui-nav-box layui-nav-box-center" style="right: 324px;">
					<ul class="layui-nav layui-layout-center layui-nav-large" style="position:relative" lay-filter="layui-layout-center">
						
					</ul>
				</div>
				<div class="iconfont icon-zashuangsanjiaojiantou admin-tabs-nav-control layui-icon-next layui-hide" ew-event="rightNavPage" style="right: 290px;"></div>
				<!-- ============================================================== -->
				<!-- End  Nav-center  -->
				<!-- ============================================================== -->
				<!-- ============================================================== -->
				<!-- Nav-right  -->
				<!-- ============================================================== -->
				<ul class="layui-nav layui-layout-right" style="padding-right: 20px;">
					<c:if test="${isAdmin=='1'}">
						<li class="layui-nav-item" lay-unselect>
							<a ew-event="" title="控制台" href="<%=basePath%>indexController/consoleIndex"  data-url="<%=basePath%>indexController/consoleIndex" target="consoleIndex" style="
								width: 68px;
								border: 1px solid #FFF;
								height: 24px;
								line-height: 24px;
								text-align: center;
								border-radius: 12px;cursor: pointer;padding: 0;">控制台</a>
					
						</li>
					</c:if>
					<li class="layui-nav-item" lay-unselect>
						<a ew-event="message" title="消息" data-url="<%=basePath%>sysMessageController/messageeList"><i class="layui-icon layui-icon-notice"></i></a>
					</li>
					<!-- <li class="layui-nav-item" lay-unselect>
						<a ew-event="note" title="便签" data-url="<%=basePath%>homePageController/note"><i class="layui-icon layui-icon-note"></i></a>
					</li>
					<li class="layui-nav-item layui-hide-xs" lay-unselect>
						<a ew-event="fullScreen" title="全屏"><i class="layui-icon layui-icon-screen-full"></i></a>
					</li> -->
					<li class="layui-nav-item" lay-unselect>
						<a>
							<img src="<%=basePath%>static/images/headPhoto/<%=headPhoto%>" class="layui-nav-img" id="user-icon">
							<cite><shiro:principal property="userName"/></cite>
							<input type="hidden" name="homeType" id="homeType" value="${homeType}">
						</a>
						<dl class="layui-nav-child">
							<dd lay-unselect>
								<a ew-event="userinfo" ew-href="<%=basePath%>userController/personalInformation">个人中心</a>
							</dd>
							<dd lay-unselect>
								<a ew-event="psw" id="password" data-url="<%=basePath%>homePageController/password">修改密码</a>
							</dd>
							<hr>
							<dd lay-unselect>
								<a ew-event="logout" data-url="<%=basePath%>loginController/logout">退出</a>
							</dd>
						</dl>
					</li>
					<!-- <li class="layui-nav-item" lay-unselect>
						<a ew-event="theme" title="主题" data-url="<%=basePath%>homePageController/theme"><i class="layui-icon layui-icon-more-vertical"></i></a>
					</li> -->
				</ul>
				<!-- ============================================================== -->
				<!-- End  Nav-right  -->
				<!-- ============================================================== -->
			</header>
			<!-- ============================================================== -->
			<!-- End Header -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Side  -->
			<!-- ============================================================== -->
			<!-- 侧边栏 -->
			<div class="layui-side layui-side-menu">
				<div class="layui-nav-titlebox layui-hide">
					<shiro:hasPermission name="sys:bi:theme:createDataSourceFolder">
						<div data-type="datasources" class="nav-tree-add nav-datasources-add layui-hide" id="LAY-nav-datasources-add" ew-event="addTreeNode">
							新建数据源目录
						</div>
					</shiro:hasPermission>

					<%-- 修改数据源文件夹隐藏域 --%>
					<shiro:hasPermission name="sys:bi:theme:updateDataSourceFolder">
						<div id="updateDataSourceFolder"></div>
					</shiro:hasPermission>

					<%-- 删除数据源文件夹隐藏域 --%>
					<shiro:hasPermission name="sys:bi:theme:deleteDataSourceFolder">
						<div id="deleteDataSourceFolder"></div>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:bi:model:createModelFolder">
						<div data-type="models" class="nav-tree-add nav-models-add layui-hide" id="LAY-nav-models-add" ew-event="addTreeNode">
							新建模型目录
						</div>
					</shiro:hasPermission>

					<%-- 修改模型文件夹隐藏域 --%>
					<shiro:hasPermission name="sys:bi:model:updateModelFolder">
						<div id="updateModelFolder"></div>
					</shiro:hasPermission>

					<%-- 删除模型文件夹隐藏域 --%>
					<shiro:hasPermission name="sys:bi:model:deleteModelFolder">
						<div id="deleteModelFolder"></div>
					</shiro:hasPermission>
					<shiro:hasPermission name="system:bi:createVisualization">
						<div data-type="resources" class="nav-tree-add nav-resources-add" id="LAY-nav-resources-add" ew-event="addTreeNode">
							新建主题
						</div>
					</shiro:hasPermission>
					<div class="layui-nav-tree-toolbar-btn open-search-btn" id="LAY-tree-search-open" title="打开查询">
						<i class="iconfont icon-search"></i>
					</div>
					<div class="layui-nav-tree-toolbar-searchbox">
						<input type="text" name="value" placeholder="请输入条件" autocomplete="off" class="layui-input search-input" id="LAY-tree-search-input">
						<span class="iconfont icon-search search-btn" id="LAY-tree-search" title="查询"></span>
						<span class="iconfont icon-close-search close-search-btn search-del-btn" style="right: 36px;" id="LAY-tree-search-close" title="关闭查询" ></span>
					</div>
				</div>
				<div class="layui-nav-child-box resultsListBox isEmptyState layui-hide">
					<div class="resultsListCard">
						<ul class="resultsList"></ul>
						<div class="resultsEmpty">
							<img src="<%=basePath%>static/images/portal/kong.png" alt="" class="resultsEmpty-img">
							<p class="resultsEmpty-txt">没有找到相关的内容 换个<span style="color:#4785E6;">关键词</span>试试</p>
						</div>
					</div>
				</div>
				<div class="layui-side-scroll" id="layui-side-scroll">
					<!-- 系统一的菜单 -->
					<ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="admin-side-nav" lay-expand="accordion" nav-id="xt0">
						<li class="layui-nav-item layui-nav-itemed">
							<a href="javascript:void(0);">
								<i class="layui-icon layui-icon-component"></i>
								<cite>配置管理</cite>
								<span class="layui-nav-move"></span>
							</a>
							<dl class="layui-nav-child">
								<p style="text-align:center;color:#999;">无数据</p>
							</dl>
						</li>
					</ul>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End Side  -->
			<!-- ============================================================== -->
			
			<!-- 主体部分 -->
			<div class="layui-body"></div>
			<!-- 底部 -->
			<!-- <div class="layui-footer">
				copyright © 2019 easyweb.vip all rights reserved. <span class="pull-right">Version 3.1.1</span>
			</div> -->
		</div>
		<!-- ============================================================== -->
		<!-- End Wrapper -->
		<!-- ============================================================== -->
	</div>
	
	<!-- 加载动画，移除位置在common.js中 -->
	<div class="page-loading">
		<div class="rubik-loader">
			<span></span><span></span><span></span><span></span>
		</div>
	</div>
	
	<!-- js部分 -->
	<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
	<script>
		var basePath = '${basePath}';
		var error="${error}";
		var success = "${success}";
		var singlePointLogin = "${singlePointLogin}"
		layui.use(['index', 'admin','pscrollbar', 'notice', "wdUtil"], function () {
			var $ = layui.jquery;
			var setter = layui.setter;
			var index = layui.index;
			var pscrollbar = layui.pscrollbar;
			var notice = layui.notice;
			var myUtil = layui.wdUtil;
    		var isIe = myBrowser();
    		var params = getPathParams();
				
			
    		init();
			function events(){
				$(".search-del-btn").off("click").on("click", function(e){
					e.stopPropagation();
					$(".search-input").val("");
					$(".search-btn").trigger("click");
				})
				$(".search-btn").off("click").on("click", function(e){
					e.stopPropagation();
					var value = $(".search-input").val(),
					url = basePath + "resourceController/queryResourceToSearch",
					ajaxData = {
						resourceName: value
					},
					callback = function(data){
						if(data.success){
							renderSearchList(data.obj);
						}
					}
					if(value){
						$(".search-del-btn").removeClass("layui-hide");
						$(".resultsListBox").removeClass("layui-hide");
						myUtil.send(url, ajaxData, callback);
					}else{
						$(".search-del-btn").addClass("layui-hide");
						$(".resultsListBox").addClass("layui-hide");
						renderSearchList();
					}
				})
				$(".search-input").off("focus").on("focus", function(e){
					$(".search-btn").trigger("click");
				})
				$(".search-input").off("keyup").on("keyup", function(e){
					e.preventDefault();
					e.stopPropagation();
					if(e.keyCode != "13" || e.keyCode != "108"){
						$(".search-btn").trigger("click");
					}
				})
			}
			function renderSearchList(nodes){
				if(nodes && nodes.length){
					var list = [],
					value = $(".search-input").val();
					nodes.forEach(function(v, i){
						list.push('<li class="list-inline-title"><div class="item-title">'+v.resourceName+'</div></li>');
						if(v.children && v.children.length){
							v.children.forEach(function(val, j){
								var names = val.resourceName.split(value),
								str = '',
								path = "",
								icon = '<div class="iconbox iconimg'+(v.resourceName == "目录" ? "11" : v.resourceName == "数据维护" ? "12" : val.type)+'"></div>';
								if(val.resourceName.indexOf(value) == 0){
									for(var j = 0; j < names.length; j++){
										if(names[j]) str += ('<span style="color:#FF6600;">'+value+'</span>' + names[j]);
									}
								}else{
									for(var j = 0; j < names.length; j++){
										if(j == names.length -1){
											if(names[j]) str += names[j];
										}else{
											if(names[j])  str += ( names[j] + '<span style="color:#FF6600;">'+value+'</span>');
										}
									}
								}
								if(val.type == "0"){
									path = val.funcResType == "1" ? "portalController/biCenter?isMultifnPortal=true&menuId=" + val.id : "portalController/rpCenter?isMultifnPortal=true&menuId=" + val.id;
								}
								list.push('<li class="list-inline-item">'+icon+'<div class="itembox"><a href="javascript:void(0);" data-type="'+val.type+'" data-id="'+val.relationId+'" data-rid="'+val.relationId+'" data-fid="'+val.funcId+'" data-mid="'+(val.menuId||"")+'" data-mname="'+(val.menuName||"")+'" data-path="'+(val.resourceUrl||path)+'" title="'+val.resourceName+'" class="list-item-link layui-elip">'+str+'</a></div></li>');
							})
						}
					})
					$(".resultsList").html(list.join(""));
					$(".resultsListBox").removeClass("isEmptyState");
					$(".resultsListCard")[0].scrollTop = 0;
					eventSearchLink();
				}else{
					$(".resultsListBox").addClass("isEmptyState");
					$(".resultsList").html("");
				}
			}
			function eventSearchLink(){
				$(".resultsList .list-item-link").off("click").on("click", function(){
					var that = $(this),
					id = that.attr("data-id"),
					type = that.attr("data-type"),
					title = that.attr("title"),
					rid = that.attr("data-rid"),
					menuId = that.attr("data-mid"),
					menuName = that.attr("data-mname"),
					path = that.attr("data-path"),
					selectTabId = "",
					url = "",
					path = "";
					if(type == "0"){
						// if(path){
						// 	$(".admin-iframe").attr("src", basePath + path);
						// 	$(".largeMenu-box").addClass("layui-hide");
						// 	setTimeout(function(){
						// 		$(".largeMenu-box").removeClass("layui-hide");
						// 	}, 800)
						// }
					}else{
						switch (type){
							case '1':
								url = basePath + "reportController/viewReport";
								path = url + "?reportId="+rid+"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '2':
								url = basePath + "reportFormController/reportFormSubmit_new";
								path = url + "?formId="+rid+"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '3':
								path = path.indexOf("http") > -1 ? path : basePath + path;
								url = path;
								if(path.indexOf('?') > -1){
									path = path +"&navname="+menuName+"&nodeid="+id+"&relationName="+title+"&menuId="+menuId
								}else{
									path = path +"?navname="+menuName+"&nodeid="+id+"&relationName="+title+"&menuId="+menuId
								}
								break;
							case '4':
								url = basePath + "dataReportController/preview";
								path = url + "?cid="+rid+"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '5':
								url = basePath + "dataReportController/previewGroup";
								path = url + "?cid=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '6':
								url = basePath + "reportController/viewReportBigScreen";
								path = url + "?reportId=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '7':
								url = basePath + "reportController/viewReportMobile";
								path = url + "?reportId=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '8':
								url = basePath + "reportFormController/reportFormSubmit_form";
								path = url + "?formId=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '9':
								url = basePath + "multidimensionalController/reportPreview";
								path = url + "?cid=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
							case '10':
								url = basePath + "simpleDataReportController/simpleDataReportView";
								path = url + "?cid=" + rid +"&opener=true&navname="+menuName+"&nodeid="+id+"&viewType=&relationName="+title+"&resourcePath="+path+"&menuId="+menuId;
								break;
						}
						$('.layui-layout-admin>.layui-body>.layui-tab .layui-tab-title>li').each(function(){
							var thistabid = $(this).attr('lay-id');
							if(thistabid){
								var pathArr = thistabid.split("?");
								if(pathArr[0] == url){
									var params = pathArr[1].split("&"),
										paramObj = {};
									if(params && params.length){
										params.forEach(function(vv, ii){
											var str = vv.split("=");
											paramObj[str[0]] = str[1]; 
										})
									}
									if(paramObj['relationName'] == title){
										if(paramObj['cid'] || paramObj['formId'] || paramObj['reportId']){
											if(paramObj['cid'] == rid || paramObj['formId'] == rid || paramObj['reportId'] == rid){
												selectTabId = thistabid;
											}
										}else{
											selectTabId = thistabid;
										}
										return false;
									}
								}
							}
						})
						index.loadView({
							menuId: selectTabId || path,
							menuPath: selectTabId || path,
							menuName: title
						});
						$(".resultsListBox").addClass("layui-hide");
					}
					
				})
			}
    		function myBrowser() {
			    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			    var isOpera = userAgent.indexOf("Opera") > -1 
			               || userAgent.indexOf("OPR") > -1; //判断是否Opera浏览器

			    var isQQ = userAgent.indexOf("QQBrowser") > -1; //判断是否QQBrowser浏览器

			    var isUC = userAgent.indexOf("UBrowser") > -1; //判断是否UC浏览器

			    var isIE = userAgent.indexOf("compatible") > -1
			            && userAgent.indexOf("MSIE") > -1; //判断是否IE7~IE10浏览器

			    var isIE11 = userAgent.indexOf("compatible") === -1
			              && userAgent.indexOf("Trident") > -1; //判断是否IE11浏览器

			    var isEdge = userAgent.indexOf("Edge") > -1; //判断是否IE的Edge浏览器

			    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器

			    var isSafari = userAgent.indexOf("Safari") > -1
			                && userAgent.indexOf("Chrome") === -1; //判断是否Safari浏览器

			    var isChrome = userAgent.indexOf("Chrome") > -1 
			                && userAgent.indexOf("; Win") > -1
			                && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器

			    var is360 = userAgent.indexOf("Chrome") > -1 
			             && userAgent.indexOf("; WOW") > -1
			             && userAgent.indexOf("Safari") > -1; //判断360浏览器


			    if (isIE) {
			      var reIE = /MSIE (\d+)\.\d+;/;
			      // match() 返回一个数组。数组第一项是匹配到的所有文本；数组第二项是正则中小括号匹配到的文本
			      var matchReg = userAgent.match(reIE)
			      var fIEVersion = matchReg[1];
			      if (fIEVersion == 7) {
			        return "IE7及其以下";
			      } else if (fIEVersion == 8) {
			        return "IE8";
			      } else if (fIEVersion == 9) {
			        return "IE9";
			      } else if (fIEVersion == 10) {
			        return "IE10";
			      } else {
			        return "0";
			      }//IE版本过低
			      return "IE";
			    }
			    if (isUC) {
			      return "UC";
			    }
			    if (isQQ) {
			      return "QQBrowser";
			    }
			    if (isIE11) {
			      return "IE11";
			    }
			    if (isOpera) {
			      return "Opera";
			    }
			    if (isEdge) {
			      return "Edge";
			    }
			    if (isFF) {
			      return "Firefox";
			    }
			    if (isSafari) {
			      return "Safari";
			    }
			    if (isChrome) {
			      return "Chrome";
			    }
			    if (is360) {
			      return "360";
			    }    
		    }
    		function getPathParams (){
					var url = location.search,
		 			pathnames = url.slice(1, url.length).split("&"),
					pathobj = {};
					for(var i =0;i< pathnames.length; i++){
							var params = pathnames[i].split("=");
							pathobj[(params[0] ? decodeURIComponent(decodeURIComponent(params[0])) : params[0])] = params[1] ? decodeURIComponent(decodeURIComponent(params[1])) : params[1];
					}
					return pathobj;
			}
			function init(){
				// 移除loading动画
				setTimeout(function () {
					layui.admin.removeLoading();
				},600 );
				$('#password').on('click',function(){
					$('.maskpass').show()
				})
				$("#off").click(function () {
					$('.maskpass').hide()
				})
				// 默认加载主页
				index.loadHome({
					menuPath: basePath+'homePageController/homePage',
					menuName: '<i class="layui-icon layui-icon-home"></i>',
				});
				var sumMenuWidth = 0
				//  获取每个菜单项的宽度
				if(error !=""){
					notice.error({
						title: "错误提示",
						message: error,
						position: 'topCenter',
						progressBar: false,
					});
				}
				if(success !=""){
					notice.success({
						title: "成功提示",
						message: success,
						position: 'topCenter',
						progressBar: false,
					});
				}
				$.ajax({
					type: "POST",
					url: setter.basePath + "sysMessageController/queryPersonalMessageNum",
					data: '',
					contentType: false,
					processData: false,
					success: function (data) {
						if(data.success){
							if(data.obj && data.obj > 0) {
								$(".layui-icon-notice").parent().append('<span class="layui-badge unread-message-num">'+data.obj+'</span>')
							}else{
								$(".layui-icon-notice").parent().find('.unread-message-num').remove();
							}
						}
					  
					},
					error: function (msg) {
						notice.error({
							title: "错误提示",
							message: error,
							position: 'topCenter',
							progressBar: false,
						});
					}
				});
				events();
			}
			if($('#homeType').val()!='4'&& $('#homeType').val()!='6' && $('#homeType').val()!=''){
				layui.admin.loadMoreSide('', true);
			}
			
		});
		
	</script>
	<script
			async
			defer
			src="http://192.168.1.6:8080/api/application/embed?protocol=http&host=192.168.1.6:8080&token=8b596cb589a69191">
	</script>

</body>
</html>
