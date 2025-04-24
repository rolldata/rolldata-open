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
	<title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/zTreeStyle.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/layZTreeStyle.css?v=<%=jspUpdateVersion%>">
	<style>
		@media screen and (min-device-width:1366px) {
			.layui-nav-treebox .clickBar {
				height: 40px;
				line-height: 40px;
				background: #fff;
				text-align: left;
				border-bottom: 1px solid #F4F5F6;
				font-weight: bold;
				padding-left: 40px;
				/* position: fixed; */
				top: 38px;
				left: 0;
				width: 160px;
				/* z-index: 2; */
			}
			.layui-nav-treebox .clickBar.active{
				background: #f2f2f2;
			}

			.layui-nav-treebox .clickBar::before {
				content: "";
				background: url(<%=basePath%>static/images/icon.png) no-repeat center center;
				height: 15px;
				width: 15px;
				font-weight: bold;
				background-size: 100%;
				display: inline-block;
				position: absolute;
				top: 12px;
				left: 20px;
			}

			.layui-nav-treebox>.layui-mylayui1-tree {
				position: relative;
				top: 40px;
				left: 10px;
			}

			.layui-nav-treebody>.layui-nav-treebox {
				top: 40px;
				right: 0;
				left: 0;
			}
			.card2 .table-box{
				height:calc(100% - 20px)!important;
				margin-top: 0px;
				position: relative;
			}
		}

		@media screen and (max-device-width:1366px) {
			.layui-nav-treebox .clickBar {
				height: 32px;
				line-height: 32px;
				background: #fff;
				text-align: left;
				border-bottom: 1px solid #F4F5F6;
				font-weight: bold;
				padding-left: 40px;
				/* position: fixed; */
				top: 30px;
				left: 0;
				width: 155px;
				/* z-index: 2; */
			}
			.layui-nav-treebox .clickBar.active{
				background: #f2f2f2;
			}
			.layui-nav-treebox .clickBar::before {
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

			.layui-nav-treebox>.layui-mylayui1-tree {
				position: relative;
				top: 30px;
				left: 8px;
			}

			.layui-nav-treebody>.layui-nav-treebox {
				top: 30px;
				right: 0;
				left: 0;
			}
			.userdetail .layui-input-block{
				width: 60%
			}
			.card2 .table-box{
				height:calc(100% - 20px)!important;
				margin-top: 0px
			}
			.card3 .table-box{
				height:100%!important;
				margin-top: 0px
			}
			.card2 .table-box .layui-form.layui-border-box.layui-table-view{
				/* height: 220px !important */
				/* 加高表格高度 */
				height: 295px !important
			}
			.card3 .table-box .layui-form.layui-border-box.layui-table-view{
				/* height: 240px !important */
				/* 加高表格高度 */
				height: 325px !important
			}
			.card2 .table-box .layui-form.layui-border-box.layui-table-view .layui-table-body{
				/* height: 218px !important */
				/* 加高表格高度 */
				height: 255px !important
			}
			.card3 .table-box .layui-form.layui-border-box.layui-table-view .layui-table-body{
				/* height: 200px !important */
				/* 加高表格高度 */
				height: 280px !important
			}
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
	<div id="LAY_app" class="wrapper userManage-page show-layui-navbar">
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
						<shiro:hasPermission name="sys:org:saveOrg">
							<div class="layui-nav-treebar-item btn-add" id="LAY-tree-add" title="新建组织">
								<i class="iconfont icon-create"></i>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:org:deleteOrgs">
							<div class="layui-nav-treebar-item btn-del" id="LAY-tree-remove" title="删除组织">
								<i class="iconfont icon-delete"></i>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:org:updateOrg">
							<%--修改--%>
						</shiro:hasPermission>
						<div class="layui-nav-treebar-item btn-search" id="LAY-tree-openSearch" title="查询组织">
							<i class="iconfont icon-search"></i>
						</div>
						<div class="layui-nav-searchbox layui-hide">
							<input class="tree-search layui-input" name="" value="" id="LAY-org-tree-search" />
							<div class="layui-nav-search-close" id="LAY-tree-closeSearch"><i
									class="iconfont icon-close"></i></div>
						</div>
					</div>
					<div class="layui-nav-treebox" id="LAY-org-tree-box">
						<div class="clickBar active" id="clickBar" style="cursor: pointer;">组织结构</div>
						<div class="treeWrap" style="height: calc(100% - 45px);position: relative;">
							<div class="layui-nav-tree layui-mylayui1-tree lay-ztree ztree" id="LAY-org-tree" lay-filter="layui-org-tree">
							</div>
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
			<div class="layui-body" id="LAY-user-body">
				<div class="layui-fluid layui-height-full-20">
					<div class="box-main layui-height-full">
						<div class="layui-card card1">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>基本信息</div>
								<div class="layui-right-float">
									<shiro:hasPermission name="sys:user:importOrgUser">
										<button type="button" id="imp"
												class="layui-btn layui-btn-cardTop layui-btn-sm impButton"><i
												class="icon iconfont icon-import2"></i>导入</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:user:delete">
										<button class=" layui-btn layui-btn-cardTop layui-btn-sm del-all-user">
											<i class="layui-icon">&#xe640;</i>
											删除用户
										</button>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="layui-card-body">
								<div class="cardWrap layui-row">
									<div class="layui-col-md6">
										<div class="layui-form-item layui-form-item-sm">
											<label for="" class="layui-form-label">类型：</label>
											<div class="layui-input-block">
												<span class="layui-form-text">总览</span>
											</div>
										</div>
									</div>
									<div class="layui-col-md6">
										<div class="layui-form-item layui-form-item-sm">
											<label for="" class="layui-form-label">名称：</label>
											<div class="layui-input-block"><span class="layui-form-text">用户目录</span></div>
										</div>
									</div>
								</div>
								<div class="layui-height-full impWrap layui-hide">
									<div class="impTitle" style="padding:30px 20px ">
										<div class="firstStep">
											<p><span>01</span>&nbsp;上传文件</p>
											<!-- 三角形 -->
											<em></em>
										</div>
										<div class="secondStep">
											<p><span>02</span>&nbsp;执行导入</p>
											<!-- 三角形 -->
											<em></em>
										</div>
										<div class="thirdStep">
											<p><span>03</span>&nbsp;完成</p>
											<!-- 三角形 -->
											<em></em>
										</div>
									</div>
									<div class="impcoutent">
										<div class="downmodult">
											<div class="downIcon">
												<i class="iconfont icon-clouddownload"></i>
											</div>
											<div class="downText layui-right-float">
												<p class="downTextTile">填写导入档案的信息</p>
												<p><a class="downTxtfirst layui-hide" style="cursor: pointer">下载txt模板</a></p>
												<p><a class="downExcelfirst" style="cursor: pointer">下载Excel模板</a>
												</p>
												<iframe id="exceliframes" style="display:none"></iframe>
											</div>
										</div>
										<div class="uploading">
											<div class="downIcon">
												<i class="iconfont icon-cloudupload"></i>
											</div>
											<div class="downText layui-right-float">
												<p class="downTextTile">上传填写好的档案信息表<span
														style="color:#999">(仅支持.txt/.xls/.xlsx格式，且文件大小不能超过5M)</span></p>
												<p><a class="uploadTemplateFirst" style="cursor: pointer">上传模板</a><span class="fristUploadFilename" style="margin-left: 30px"></span></p>
												<form class="layui-form layui-hide" action="" id="firstuploadForm" method="post" enctype="multipart/form-data">
                 					 				<input type="file" name="importExcel" id="uploadTemplateFirst" class="layui-btn layui-btn-sm" style="opacity:0;position:absolute;top:0;left:0;bottom:0;right:0;width:200px;height:30px">
												</form>

											</div>
										</div>
										<button type="button" class="layui-btn firstStepButton firstButton">下一步</button>
									</div>
									<div class="performImport layui-hide">
										<div class="successImport">
											<div class="icon">
												<i class="iconfont icon-normal"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>本次可导入条数：<span class="rightTem"></span></p>
											</div>
										</div>
										<div class="errorImport">
											<div class="icon">
												<i class="iconfont icon-unusual"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>本次不可导入条数：<span class="bad"></span></p>
											</div>
										</div>
										<div class="buttonWrap">
											<button type="button" class="layui-btn secondStepButton sButton">下一步</button>
											<a href="javaScript:void(0)" class="goBack">返回重新上传</a>
										</div>
										<div class="secondtable-box">
											<div class="layui-tab layui-tab-brief" style="margin-top: 20px">
												<ul class="layui-tab-title">
													<li class="layui-this">用户</li>
													<li>组织</li>
												</ul>
												<div class="layui-tab-content">
													<div class="layui-tab-item layui-show">
															<table class="secondStepTable" id="secondStepTable" lay-filter="secondStepTable"></table>
													</div>
													<div class="layui-tab-item">
															<table class="secondStepTable" id="secondStepOrgTable" lay-filter="secondStepTable"></table>
													</div>
												</div>
											</div>
											
										</div>
									</div>
									<div class="finish layui-hide">
										<div class="finishText">
											<div class="icon">
												<i class="iconfont icon-normal"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>成功导入条数：<span class="successNum"></span>条</p>
											</div>
										</div>
										<button type="button" class="layui-btn finishButton">完成</button>
									</div>
								</div>
							</div>
						</div>
						<div class="layui-card card2" style="height: calc(100% - 110px);">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>用户列表</div>
								<div class="layui-right-float">
									<div class="layui-input-inline layui-form-item-sm" style="position:relative;">
										<input type="text" class="layui-input" id="allUserSearchInput"
											name="searchInput" style="width: 170px; margin-right: 3px;">
										<botton type="button" id="allUserSearch" class="layui-btn-sm"
											style="margin-left:0;position: absolute;top:0;right:0;" data-type="reload">
											<i class="layui-icon layui-icon-search" style="color:#4785e6"></i>
										</botton>
									</div>
								</div>
							</div>
							<div class="layui-card-body layui-height-full-62">
								<div class="layui-row">
									<div class="layui-col-xs3 layui-form-item-sm">
										<label for="" class="layui-form-label">用户总数</label>
										<div class="layui-input-inline"><span
												class="layui-form-text total-user">0</span></div>
									</div>
									<div class="layui-col-xs3 layui-form-item-sm">
										<label for="" class="layui-form-label">停用用户</label>
										<div class="layui-input-inline"><span
												class="layui-form-text disable-user">0</span></div>
									</div>
									<div class="layui-col-xs3 layui-form-item-sm">
										<label for="" class="layui-form-label">活动用户</label>
										<div class="layui-input-inline"><span
												class="layui-form-text active-user">0</span></div>
									</div>
									<div class="layui-col-xs3 layui-form-item-sm">
										<label for="" class="layui-form-label">锁定用户</label>
										<div class="layui-input-inline"><span class="layui-form-text lock-user">0</span>
										</div>
									</div>
								</div>
								<div class="table-box">
									<table class="layui-hide" id="allUserList" lay-filter="allUserList"></table>
									<div class="pageCount">
										<div class="pageCountText"></div>
										<div id="pageCount"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="box-folder layui-hide  layui-height-full">
						<div class="layui-card topWrap">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>基本信息</div>
								<div class="layui-right-float">
									<button class="layui-btn layui-btn-sm layui-btn-cardTop add-user">
										<i class="layui-icon">&#xe61f;</i>
										新建用户
									</button>
									<!-- <button type="button" id="imps" class="layui-btn layui-btn-cardTop layui-btn-sm">
										<i class="icon iconfont icon-import2"
											style="font-size:12px!important;margin-right:6px"></i>导入</button> -->
                                    <shiro:hasPermission name="sys:user:delete">
                                        <button class="layui-btn layui-btn-cardTop layui-btn-sm del-all-user">
                                            <i class="layui-icon">&#xe640;</i>
                                            删除用户
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="sys:user:update">
                                        <button class="layui-btn layui-btn-sm layui-btn-cardTop save-folder">
                                            <i class="iconfont icon-save"></i>
                                            保存
                                        </button>
                                    </shiro:hasPermission>
								</div>
							</div>
							<div class="layui-card-body">
								<form action="" class="layui-form forms layui-row" lay-filter="folder-form">
									<div class="layui-col-md4">
										<div class="layui-form-item layui-form-item-sm">
											<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i><span class="orgCde-name-txt"></span>编码：</label>
											<div class="layui-input-inline">
												<input type="text" name="orgCde" class="layui-input" id="orgCde-input" required lay-verType="tips" lay-verify="required" placeholder="请输入编码"
													autocomplete="off">
											</div>
										</div>
									</div>
									<div class="layui-col-md4">
										<div class="layui-form-item layui-form-item-sm">
											<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>名称：</label>
											<div class="layui-input-inline">
												<input type="text" name="orgName" class="layui-input" id="orgName-input" required lay-verType="tips" lay-verify="required" placeholder="请输入名称"
													autocomplete="off">
												<input type="hidden" class="layui-input layui-hide" name="orgId" id="orgId-input">
											</div>
										</div>
									</div>
									<div class="layui-col-md4">
										<div class="layui-form-item layui-form-item-sm">
											<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>父节点：</label>
											<div class="layui-input-inline">
												<input type="text" name="orgPname" class="layui-input orgPname-input" id="orgPname-input" required lay-verType="tips" lay-verify="required" placeholder="请输入名称" autocomplete="off">
												<input type="hidden" class="layui-input layui-hide" name="orgPid" id="orgPid-input">
											</div>
										</div>
									</div>
									<input type="hidden" class="layui-input layui-hide" name="nodeId">
									<button class="layui-btn layui-hide layui-btn-sm" id="save-folder-btn" lay-submit
										lay-filter="saveFolder"></button>
								</form>
								<div class="layui-height-full impWrap layui-hide">
									<div class="impTitle" style="padding:30px 20px ">
										<div class="firstStep">
											<p><span>01</span>&nbsp;上传文件</p>
											<!-- 三角形 -->
											<em></em>
										</div>
										<div class="secondStep">
											<p><span>02</span>&nbsp;执行导入</p>
											<!-- 三角形 -->
											<em></em>
										</div>
										<div class="thirdStep">
											<p><span>03</span>&nbsp;完成</p>
											<!-- 三角形 -->
											<em></em>
										</div>
									</div>
									<div class="impcoutent" style="padding:30px 20px">
										<div class="downmodult">
											<div class="downIcon">
												<i class="iconfont icon-clouddownload"></i>
											</div>
											<div class="downText layui-right-float">
												<p class="downTextTile">填写导入档案的信息</p>
												<p class="layui-hide"><a class="downTxtsecond"  style="cursor: pointer">下载txt模板</a></p>
												<p><a class="downExceldecond"  style="cursor: pointer">下载Excel模板</a>
												</p>
												<iframe id="exceliframes2" style="display:none"></iframe>
											</div>
										</div>
										<div class="uploading">
											<div class="downIcon">
												<i class="iconfont icon-cloudupload"></i>
											</div>
											<div class="downText layui-right-float">
												<p class="downTextTile">上传填写好的档案信息表<span
														style="color:#999">(仅支持.txt/.xls/.xlsx格式，且文件大小不能超过5M)</span></p>
														<p><a class="uploadTemplatesecond" style="cursor: pointer">上传模板</a><span class="secondUploadFilename" style="margin-left: 30px"></span></p>
														<form class="layui-form layui-hide" action="" id="seconduploadForm" method="post" enctype="multipart/form-data">
															  <input type="file" name="importExcel" id="uploadTemplatesecond" class="layui-btn layui-btn-sm" style="opacity:0;position:absolute;top:0;left:0;bottom:0;right:0;width:200px;height:30px">
														</form>

											</div>
										</div>
										<button type="button" class="layui-btn firstStepButton secondbutton">下一步</button>
									</div>
									<div class="performImport layui-hide">
										<div class="successImport">
											<div class="icon">
												<i class="iconfont icon-normal"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>本次可导入条数：<span class="rightTem2"></span></p>
											</div>
										</div>
										<div class="errorImport">
											<div class="icon">
												<i class="iconfont icon-unusual"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>本次不可导入条数：<span class="bad2"></span>条</p>
											</div>
										</div>
										<div class="buttonWrap" style="padding:0 20px">
											<button type="button" class="layui-btn secondStepButton secondStepButton2">下一步</button>
											<a href="javaScript:void(0)"
												class="goBack">返回重新上传</a>
										</div>
										<div class="secondtable-box">
												<div class="layui-tab layui-tab-brief" style="margin-top: 20px">
													<ul class="layui-tab-title">
														<li class="layui-this">用户</li>
														<li>组织</li>
													</ul>
													<div class="layui-tab-content">
														<div class="layui-tab-item layui-show">
																<table class="secondStepTable" id="secondStepTable2" lay-filter="secondStepTable2"></table>
														</div>
														<div class="layui-tab-item">
																<table class="secondStepTable" id="secondStepOrgTable2" lay-filter="secondStepOrgTable2"></table>
														</div>
													</div>
												</div>
												
											</div>
									</div>
									<div class="finish layui-hide">
										<div class="finishText">
											<div class="icon">
												<i class="iconfont icon-normal"></i>
											</div>
											<div class="textContent layui-right-float">
												<p>成功导入条数：<span class="successNum2"></span></p>
											</div>
										</div>
										<button type="button" class="layui-btn">完成</button>
									</div>
								</div>
							</div>
						</div>
						<div class="layui-card card3" style="height: calc(100% - 110px);">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>用户列表</div>
								<div class="layui-right-float">
									<div class="layui-input-inline layui-form-item-sm" style="position:relative;">
										<input type="text" class="layui-input" id="allSearchInput" name="searchInput"
											style="width: 170px; margin-right: 3px;">
										<botton type="button" class="layui-btn-sm" id="allSearch"
											style="margin-left:0;position: absolute;top:0;right:0;" data-type="reload">
											<i class="layui-icon layui-icon-search" style="color:#4785e6"></i>
										</botton>
									</div>

								</div>
							</div>
							<div class="layui-card-body layui-height-full-62">
								<div class="table-box" style="margin-top:0">
									<table class="layui-hide" id="userList" lay-filter="userList"></table>
									<div class="pageCount" style="width: 96%">
										<div class="pageCountText"></div>
										<div id="pageCounts"></div>
									</div>
								</div>
							</div>

						</div>
					</div>
					<div class="box-userinfo layui-hide layui-height-full">
						<div class="layui-right-float">
							<button class="layui-btn layui-btn-sm layui-btn-cardTop close-user">
								<i class="layui-icon">&#xe65c;</i>
								返回
							</button>
							<shiro:hasPermission name="sys:user:delete">
								<button class="layui-btn layui-btn-sm layui-btn-cardTop del-user">
									<i class="layui-icon">&#xe640;</i>
									删除
								</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:user:update">
								<button class="layui-btn layui-btn-sm layui-btn-cardTop save-user">
									<i class="iconfont icon-save"></i>
									保存
								</button>
							</shiro:hasPermission>
                            <shiro:hasPermission name="sys:user:resetPassword">
                                <button class="layui-btn layui-btn-sm layui-btn-cardTop reset-psd">
                                    <i class="layui-icon">&#xe669;</i>
                                    重置密码
                                </button>
                            </shiro:hasPermission>
						</div>
						<form action="" class="layui-form" lay-filter="userinfo-form" id="userinfo-form">
							<div class="layui-card">
								<div class="layui-card-header">
									<div class="TitleVertical"><span class="vertical"></span>基本信息</div>
								</div>
								<div class="layui-card-body">
									<div class="layui-fluid">
										<div class="layui-row">
											<div class="layui-col-md6">
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label">类型：</label>
													<div class="layui-input-block">
														<span class="layui-form-text userinfo-type">用户</span>
													</div>
												</div>
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>名称：</label>
													<div class="layui-input-block">
														<span class="layui-form-text userinfo-userCde"></span>
													</div>
												</div>
											</div>
											<div class="layui-col-md6">
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label">用户锁定：</label>
													<div class="layui-input-block">
														<span class="layui-form-text userinfo-lock">正常</span>
														<botton class="layui-btn layui-btn-sm unlockUser layui-hide">
															解锁用户</botton>
													</div>
												</div>
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label">用户状态：</label>
													<div class="layui-input-inline">
														<select name="isactive" id="isactive" lay-verify="required">
															<option value="0">停用</option>
															<option value="1">启用</option>
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-card layui-height-full-180">
								<div class="layui-card-header">
									<div class="TitleVertical"><span class="vertical"></span>详细信息</div>
								</div>
								<div class="layui-card-body layui-height-full-62">
									<div class="layui-tab layui-tab-brief layui-height-full">
										<ul class="layui-tab-title">
											<li class="layui-this">联系信息</li>
											<li>角色授权</li>
										</ul>
										<div class="layui-tab-content layui-height-full-50" style="position:relative">
											<div class="layui-tab-item layui-show">
												<div class="layui-fluid">
													<div class="layui-row userdetail">
														<div class="layui-col-md6">
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>姓名：</label>
																<div class="layui-input-block">
																	<input type="text" class="layui-input"
																		name="userName">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>性别：</label>
																<div class="layui-input-block">
																	<select name="gender" lay-filter="gender">
																		<option value="1" selected="">男</option>
																		<option value="2">女</option>
																	</select>
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>部门：</label>
																<div class="layui-input-block">
																	<div class="layui-model-treeinput department-select-input"><span class="layui-model-node">请选择部门</span><i class="layui-icon layui-icon-right"></i></div>
																	
																	<input type="text" name="department"
																		class="layui-input layui-hide department-input">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm" style="position: relative;">
																<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>职务：</label>
																<div class="layui-input-block">
																	<select name="position" class="layui-input" id="postList-select"></select>
																</div>
																<div class="layui-input-inline" style="position: absolute;right: -50px;top: 3px;width: 30px;">
																	<div class="add-dict-btn" title="新增职务"><i class="iconfont icon-zengjia"></i></div>
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label">电子邮件：</label>
																<div class="layui-input-block">
																	<input type="email" name="mail" class="layui-input" lay-verType="tips" lay-verify="email">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label">用工类别：</label>
																<div class="layui-input-block">
																	<input type="text" name="employType"
																		class="layui-input">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label">移动电话：</label>
																<div class="layui-input-block">
																	<input type="text" name="mobilePhone"
																		class="layui-input" lay-verType="tips"
																		lay-verify="phone">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label">固定电话：</label>
																<div class="layui-input-block">
																	<input type="text" name="areaCode"
																		class="layui-input layui-input-inline"
																		style="width:180px" lay-verify="areaCode"
																		lay-verType="tips">
																	<span class="layui-input-inline"
																		style="padding:0 5px;width:5px;height: 30px;line-height: 30px;">-</span>
																	<input type="text" name="telephone"
																		class="layui-input layui-input-inline"
																		style="width:calc(100% - 195px);margin-right:0"
																		lay-verify="telephone" lay-verType="tips">
																</div>
															</div>
															<div class="layui-form-item layui-form-item-sm">
																<label for="" class="layui-form-label">第三方用户：</label>
																<div class="layui-input-block">
																	<input type="text" name="thirdPartyCode"
																		class="layui-input"  ">
																</div>
															</div>
														</div>
													</div>
												</div>

											</div>
											<div class="layui-tab-item layui-height-full user-fallinto-box">
												<div class="layui-height-full">
													<div class="layui-height-full user-fallinto-left">
														<div class="list-title">
															<span class="list-title-content">未选择角色</span>
															<i class="list-all-btn iconfont icon-select-all"></i>
														</div>
														<div class="list-box">
															<div class="search-box layui-form-item-sm">
																<input type="text" class="layui-input" placeholder="输入角色名称搜索"
																	id="LAY-role-tree-search">
																<i class="iconfont icon-search search-btn"></i>
															</div>
															<div class="tree-box">
																<div class="layui-mylayui1-tree lay-ztree ztree"
																	id="LAY-role-right-tree"
																	lay-filter="layui-role-right-tree"></div>
															</div>
														</div>
													</div>
													<div class="layui-height-full user-fallinto-right">
														<div class="list-title">
															<span class="list-title-content">已选择角色</span>
															<i class="list-all-btn iconfont icon-quxiaoquanxuan"></i>
														</div>
														<div class="authorized-list list-box">
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<botton class="layui-btn layui-hide" id="saveUserinfo" lay-submit lay-filter="saveUserinfo">
							</botton>
						</form>
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
<script type="text/javascript" src="<%=basePath%>static/js/source/user.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/html" id="statusTemp">
	{{#  if(d.status === '0'){ }}
	<span class="iconfont icon-normal" style="color:#53d96e;"></span> 活动
	{{#  } else if(d.status === "1") { }}
	<span class="iconfont icon-normal" style="color:#53d96e;"></span> 启用
	{{#  } else if(d.status === "2") { }}
	<span class="iconfont icon-lock-solid" style="color:rgba(40, 63, 85, 1);"></span> 锁定
	{{#  } else if(d.status === "3") { }}
	<span class="iconfont icon-unusual" style="color:#ff9800;"></span> 停用
	{{#  } }}
</script>
<script type="text/html" id="userbar">
	<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>

</html>
