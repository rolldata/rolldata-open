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
	<title>角色管理</title>
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
	<input type="hidden" name="productType" id="productType" value="${productType}">
	<input type="hidden" name="pagename" id="requireScriptNames" value="bs-dropdown,bs-tab,waves,role">
	<!-- ============================================================== -->
	<!-- Main wrapper - style you can find in style.css -->
	<!-- ============================================================== -->
	<div class="wrapper roleManage-page show-layui-navbar layui-height-full">
		<!-- ============================================================== -->
		<!-- layui-layout - style you can find in pages.css -->
		<!-- ============================================================== -->
		<div class="layui-layout">
			<div class="layui-tab layui-tab-brief" lay-filter="roleTab">
				<ul class="layui-tab-title">
					<li class="layui-this" data-type="role">角色</li>
					<li data-type="post">职务</li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-tab-role layui-show">
						<!-- ============================================================== -->
						<!-- layui-side - style you can find in pages.css -->
						<!-- ============================================================== -->
						<div class="layui-side">
							<div class="layui-nav-treebody">
								<div class="layui-nav-treebar">
									<shiro:hasPermission name="sys:role:save">
										<div class="layui-nav-treebar-item btn-add" id="" title="新建角色">
											<i class="iconfont icon-create"></i>
										</div>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:role:del">
										<div class="layui-nav-treebar-item btn-del" id="" title="删除角色">
											<i class="iconfont icon-delete"></i>
										</div>
									</shiro:hasPermission>
									
									<div class="layui-nav-treebar-item btn-search" id="" title="查询角色">
										<i class="iconfont icon-search"></i>
									</div>
									<div class="layui-nav-searchbox layui-hide">
										<input class="tree-search layui-input" name="" value="" id="" />
										<div class="layui-nav-search-close" id=""><i
												class="iconfont icon-close"></i></div>
									</div>
								</div>
								<div class="layui-nav-treebox">
										<div class="IndexDirectory publicResourcel" style="cursor: pointer">
												角色目录
											</div>
									<div class="layui-nav-tree layui-mylayui1-tree" id="LAY-role-tree" lay-filter="layui-role-tree" style="height: calc(100% - 45px)">
									</div>
								</div>
							</div>
						</div>
						<!-- ============================================================== -->
						<!-- End layui-side -->
						<!-- ============================================================== -->
					</div>
					<div class="layui-tab-item layui-tab-position">
						<!-- ============================================================== -->
						<!-- layui-side - style you can find in pages.css -->
						<!-- ============================================================== -->
						<div class="layui-side">
							<div class="layui-nav-treebody">
								<div class="layui-nav-treebar">
									<shiro:hasPermission name="sys:post:createPost">
										<div class="layui-nav-treebar-item btn-add" id="" title="新建职务">
											<i class="iconfont icon-create"></i>
										</div>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:post:deletePost">
										<div class="layui-nav-treebar-item btn-del" id="" title="删除职务">
											<i class="iconfont icon-delete"></i>
										</div>
									</shiro:hasPermission>

									<div class="layui-nav-treebar-item btn-search" id="" title="查询职务">
										<i class="iconfont icon-search"></i>
									</div>
									<div class="layui-nav-searchbox layui-hide">
										<input class="tree-search layui-input" name="" value="" id="" />
										<div class="layui-nav-search-close" id=""><i
												class="iconfont icon-close"></i></div>
									</div>
								</div>
								<div class="layui-nav-treebox">
										<div class="IndexDirectory publicResourcel" style="cursor: pointer">
												职务目录
											</div>
									<div class="layui-nav-tree layui-mylayui1-tree" id="LAY-post-tree" lay-filter="layui-post-tree" style="height: calc(100% - 45px)">
									</div>
								</div>
							</div>
						</div>
						<!-- ============================================================== -->
						<!-- End layui-side -->
						<!-- ============================================================== -->
					</div>
					
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- layui-body - style you can find in pages.css -->
			<!-- ============================================================== -->
			<div class="layui-body" id="">
				<div class="layui-fluid layui-height-full-30">
					<div class="box-roleinfo layui-height-full">
						<div class="layui-card">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>基本信息</div>
								<div class="layui-right-float">
									<shiro:hasPermission name="sys:role:update">
										<button class="layui-btn layui-btn-cardTop layui-btn-sm layui-btn-save-role save-role"><i class="iconfont icon-save"></i>保存</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:post:updatePost">
										<button class="layui-btn layui-btn-cardTop layui-btn-sm layui-btn-save-post save-role layui-hide"><i class="iconfont icon-save"></i>保存</button>
									</shiro:hasPermission>
								</div>
							</div>
							<form action="" class="layui-form layui-height-full" lay-filter="roleinfo-form"
								id="roleinfo-form">
								<div class="layui-card-body">
									<div class="layui-fluid">
										<div class="layui-row">
											<div class="layui-col-md6">
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label roleinfo-item-label"><i style="color: red;margin-right: 5px;">*</i>角色编码：</label>
													<div class="layui-input-block">
														<span class="layui-form-text roleinfo-roleCde"></span>
													</div>
												</div>
												<div class="layui-form-item layui-form-item-sm layui-form-item-state">
													<label for="" class="layui-form-label "><i style="color: red;margin-right: 5px;">*</i>角色类型：</label>
													<div class="layui-input-block" style="width: 190px;">
														<select name="roletype1" class="roletype1">
															<option value="0">普通角色</option>
															<option value="1">管理角色</option>
														</select>
													</div>
												</div>
												<div class="layui-form-item layui-form-item-sm layui-form-item-remark" style="margin-bottom: 0;">
													<label for="" class="layui-form-label roleinfo-item-label">角色描述：</label>
													<div class="layui-input-block">
														<textarea autocomplete="off" required="" lay-vertype="tips" class="layui-textarea layui-form-text roleinfo-roleRemark" placeholder="请输入描述" style="width: 80%;padding-left:10px;min-height: 55px;height: 55px;resize: none;"></textarea>
													</div>
												</div>
											</div>
											<div class="layui-col-md6">
												<div class="layui-form-item layui-form-item-sm">
													<label for="" class="layui-form-label roleinfo-item-label"><i style="color: red;margin-right: 5px;">*</i>角色名称：</label>
													<div class="layui-input-block">
														<!-- <span class="layui-form-text roleinfo-roleName"></span> -->
														<input type="text"  class="layui-input layui-form-text roleinfo-roleName" required="" lay-vertype="tips" lay-verify="required" placeholder="请输入名称" autocomplete="off" style="width: 190px;padding-left:10px">
													</div>
												</div>
												<div class="layui-form-item layui-form-item-sm layui-form-item-state">
													<label for="" class="layui-form-label">角色状态：</label>
													<div class="layui-input-inline">
														<select name="state" lay-verify="required">
															<option value="0">停用</option>
															<option value="1">启用</option>
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<button class="layui-btn layui-hide" id="saveRoleinfo" lay-submit
									lay-filter="saveRoleinfo"></button>
							</form>
						</div>
						<div class="layui-card layui-height-full-203 layui-card-content">
							<div class="layui-card-header">
								<div class="TitleVertical"><span class="vertical"></span>目录及配置</div>
							</div>
							<div class="layui-card-body layui-height-full-62">
								<div class="layui-tab layui-tab-brief layui-height-full" lay-filter="roleinfoTab">
									<ul class="layui-tab-title" style="width:100%;">
										<li class="layui-this roleinfo-item-label authorized-tab-item" data-type="authorizeds">角色授权</li>
										<li data-type="users">归入用户</li>
									</ul>
									<div class="layui-tab-content layui-height-full-30">
										<div class="layui-tab-item layui-height-full layui-show">
											<div class="layui-row layui-height-full layui-col-space15">
												<div class="layui-col-md4 layui-height-full">
													<div class="authorized-list list-box">
														<div class="authorized-item authorized-tab active"
															data-type="func" data-id="#functab">菜单功能权限</div>
														<input type="hidden" name="funcIds" value="" id="funcIds"
															class="roleinfo-field">
														<div class="authorized-item authorized-tab orgTab layui-hide" data-type="org" data-id="#orgtab">
															数据权限</div>
														<input type="hidden" name="orgIds" value="" id="orgIds"
															class="roleinfo-field">
														
														<div class="authorized-item authorized-tab layui-hide"
															data-type="analyze" data-id="#analyzetab">资源权限</div>
														<div class="authorized-item authorized-tab layui-hide"
															data-type="datasource" data-id="#datasourcetab">数据源权限</div>
														<div class="authorized-item authorized-tab layui-hide"
															data-type="model" data-id="#modeltab">模型权限</div>
														<input type="hidden" name="analyzeIds" value="" id="analyzeIds"
															class="roleinfo-field">
														<input type="hidden" name="modelIds" value="" id="modelIds"
															class="roleinfo-field">
														<input type="hidden" name="dataSourceIds" value="" id="dataSourceIds"
															class="roleinfo-field">
														
													</div>
												</div>
												<div class="layui-col-md8 layui-height-full">
													<!-- --- -->
													<div class="list-box authorized-tab-pane layui-show" id="functab">
														<div class="funcMenu layui-height-full config-left-box">
															<div class="layui-card-header">菜单授权</div>
															<div class="funcMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-func-configTree-search" placeholder="请输入目录"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-func-configTree-search-btn" data-type="func"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-func-configTree"
																		lay-filter="layui-func-configTree"></ul>
																</div>
															</div>
														
														</div>
														<!-- 分析对象 -->
														<div class="authority layui-right-float layui-height-full menu-right-box">
															<div class="layui-card-header">按钮授权
															</div>
															<div class="funcMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-func-menuTree-search"  placeholder="请输入按钮"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-func-menuTree-search-btn" data-type="funcmenu"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-func-menuTree"
																		lay-filter="layui-func-menuTree"></ul>
																</div>
															</div>
														</div>
														
													</div>
													<!-- --- -->
													<div class="list-box authorized-tab-pane" id="orgtab">
														<div class="orgMenu layui-height-full config-left-box">
															<div class="layui-card-header">机构目录</div>
															<div class="orgMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-org-configTree-search"  placeholder="请输入目录"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-org-configTree-search-btn" data-type="org"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-org-configTree"
																		lay-filter="layui-org-configTree"></ul>
																</div>
															</div>
														
														</div>
														<!-- 分析对象 -->
														<div class="authority layui-right-float layui-height-full menu-right-box">
															<div class="layui-card-header">部门授权
															</div>
															<div class="orgMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-org-menuTree-search"  placeholder="请输入部门"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-org-menuTree-search-btn" data-type="orgmenu"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-org-menuTree"
																		lay-filter="layui-org-menuTree"></ul>
																</div>
															</div>
														</div>
														
													</div>
													<!-- --- -->
													<div class="list-box authorized-tab-pane" id="analyzetab">
														<div class="analyzeMenu layui-height-full config-left-box">
															<div class="layui-card-header">资源目录</div>
															<div class="analyzeMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-analyze-configTree-search"  placeholder="请输入目录"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-analyze-configTree-search-btn" data-type="analyze"></i>
																</div>
																<div class="tree-box">
																	<ul class="lay-ztree ztree"
																		id="LAY-analyze-configTree"
																		lay-filter="layui-analyze-configTree"></ul>
																</div>
															</div>
	
														</div>
														<!-- 分析对象 -->
														<div class="authority layui-right-float layui-height-full menu-right-box">
															<div class="layui-card-header">分析对象
																<form class="layui-form layui-right-float" action=""
																	lay-filter="allmenuable">
																	<input type="checkbox" name="allmenureadable"
																		id="allmenureadable" title="全部可读"
																		lay-skin="primary" lay-filter="allmenureadable">
																	<input type="checkbox" name="allmenuwriteable"
																		id="allmenuwriteable" title="全部可写"
																		lay-skin="primary"
																		lay-filter="allmenuwriteable">
																</form>
															</div>
															<table class="analyzeTable" id="analyzeTable"
																lay-filter="analyzeTable"></table>
														</div>
	
													</div>
													<!-- --- -->
													<!-- --- -->
													<div class="list-box authorized-tab-pane" id="datasourcetab">
														<div class="datasourceMenu layui-height-full config-left-box">
															<div class="layui-card-header">数据源目录</div>
															<div class="datasourceMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-datasource-configTree-search"  placeholder="请输入目录"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-datasource-configTree-search-btn" data-type="datasource"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-datasource-configTree"
																		lay-filter="layui-datasource-configTree"></ul>
																</div>
															</div>
														
														</div>
														<!-- 分析对象 -->
														<div class="authority layui-right-float layui-height-full menu-right-box">
															<div class="layui-card-header">数据源授权</div>
															<div class="datasourceMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-datasource-menuTree-search" placeholder="请输入数据源"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-datasource-menuTree-search-btn" data-type="datasourcemenu"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-datasource-menuTree"
																		lay-filter="layui-datasource-menuTree"></ul>
																</div>
															</div>
														</div>
														
													</div>
													<!-- --- -->
													<!-- --- -->
													<div class="list-box authorized-tab-pane" id="modeltab">
														<div class="modelMenu layui-height-full config-left-box">
															<div class="layui-card-header">模型目录</div>
															<div class="modelMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-model-configTree-search" placeholder="请输入目录"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-model-configTree-search-btn" data-type="model"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-model-configTree"
																		lay-filter="layui-model-configTree"></ul>
																</div>
															</div>
														
														</div>
														<!-- 分析对象 -->
														<div class="authority layui-right-float layui-height-full menu-right-box">
															<div class="layui-card-header">模型授权</div>
															<div class="modelMenu-body layui-height-full-50"
																style="position: relative;margin-top: 10px">
																<div class="search-box layui-form-item-sm">
																	<input id="LAY-model-menuTree-search"  placeholder="请输入模型"
																		class="layui-input" />
																	<i class="iconfont icon-search search-btn" id="LAY-model-menuTree-search-btn" data-type="modelmenu"></i>
																</div>
																<div class="tree-box">
																	<ul class="layui-mylayui1-tree"
																		id="LAY-model-menuTree"
																		lay-filter="layui-model-menuTree"></ul>
																</div>
															</div>
														</div>
														
													</div>
													<!-- --- -->
												</div>
											</div>
										</div>
										<div class="layui-tab-item layui-height-full">
											<div class="layui-row layui-height-full layui-col-space15">
												<div class="layui-col-md4 layui-height-full">
													<div class="users-list-title" style="
													    position: absolute;
													    top: 11px;
													    left: 5px;
													    right: 5px;
													">组织目录</div>
													<div class="list-box" style="top: 41px;">
														<div class="search-box layui-form-item-sm" style="padding: 10px 15px;">
															<input type="text" class="layui-input" id="LAY-user-configTree-search" data-type="dictlist" placeholder="请输入目录名称搜索">
															<i class="iconfont icon-search search-btn" id="LAY-user-configTree-search-btn"></i>
														</div>
														<div class="tree-box" style="top:45px;">
															<div class="layui-mylayui1-tree" id="LAY-user-configTree"
																lay-filter="layui-user-configTree"></div>
														</div>
													</div>
												</div>
												<div class="layui-col-md8 layui-height-full">
													<div class="authorized-list users-list-box">
														<div class="users-list-title">未选择用户</div>
														<div class="users-list-box-border">
															<div class="users-item-list-all-btn iconfont icon-select-all" data-type="unchecked" title="全选"></div>
															<div class="search-box">
																<input type="text" class="users-item-list-sreach" data-type="unchecked" placeholder="请输入用户名称搜索">
																<i class="iconfont icon-search search-btn users-item-list-sreach-btn"></i>
															</div>
															<div class="users-item-list-box">
																<div class="users-item-list-scroll">
																	<div class="users-item-list users-unchecked-list">
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="authorized-list users-list-box" style="left: 298px;">
														<div class="users-list-title">已选择用户</div>
														<div class="users-list-box-border">
															<div class="users-item-list-all-btn iconfont icon-quxiaoquanxuan" data-type="checked" title="全选"></div>
															<div class="search-box">
																<input type="text" class="users-item-list-sreach" data-type="checked" placeholder="请输入用户名称搜索">
																<i class="iconfont icon-search search-btn users-item-list-sreach-btn"></i>
															</div>
															<div class="users-item-list-box">
																<div class="users-item-list-scroll">
																	<div class="users-item-list users-checked-list">
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
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
</body>
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery-1.4.4.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery.ztree.all.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/zTree/fuzzysearch.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/source/role.js?v=<%=jspUpdateVersion%>"></script>

<script type="text/html" id="readable">
	<input type="checkbox" name="readable" title="" lay-skin="primary" {{ d.readable == "1" ? 'checked' : '' }}
		lay-filter="readable" data-id="{{d.id || d.resourceId}}">
</script>
<script type="text/html" id="writeable">
	<input type="checkbox" name="writeable" title="" lay-skin="primary" {{ d.writeable == "1" ? 'checked' : '' }}
		lay-filter="writeable" data-id="{{d.id || d.resourceId}}">
</script>

</html>
