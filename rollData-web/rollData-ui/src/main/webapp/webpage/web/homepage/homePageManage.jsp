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
		<title>首页管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/zTreeStyle.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/layZTreeStyle.css?v=<%=jspUpdateVersion%>">
		<style>
			.formbodymob .pageuser-item{
				position: relative;
				height: 35px;
				line-height: 35px;
				padding: 0 15px;
				cursor: pointer;
			}
			.authorityTable1 .content{
				width: calc(100% - 30px);
				height: 35px;
				line-height: 35px;
				padding: 0 15px;
				cursor: pointer;
			}
			.formbodymob .pageuser-item:hover,
			.authorityTable1 .content:hover {
				background-color: #eef1fc;
			}
			body .homePageManage .searchRoleBtn1mob{
				margin-left: 0;
				position: absolute;
				top: 0;
				right: 0;
				border: none;
				background: 0 0;
			}
			.resourceset:hover:after{
				content: '资源配置';
				position: absolute;
				right: 32px;
				top: -16px;
				width: 57px;
				height: 27px;
				background: #707172;
				box-shadow: 0px 3px 8px 0px rgb(102 102 102 / 35%);
				opacity: 0.8;
				border-radius: 2px;
				font-size: 12px;
				font-family: Microsoft YaHei;
				font-weight: 400;
				color: #fff;
				text-align: center;
            }
		</style>
	</head>
	<body>
		<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
		<input type="hidden" name="productType" id="productType" value="0,1,2">
		<div class="layui-body homePageManage">
			<div class="layui-height-full-30 layui-fluid">
				<div class="layui-card layui-height-full">
					<div class="layui-card-header">
						<div class="TitleVertical"><span class="vertical"></span>首页管理</div>
						<div class="layui-right-float">
							<shiro:hasPermission name="sys:homepageManage:saveHomepageInfo">
								<button class="layui-btn layui-btn-sm layui-btn-cardTop save-role" id="btn-save">
									<i class="iconfont icon-save"></i>
									保存
								</button>
								<button class="layui-btn layui-btn-sm layui-btn-cardTop save-rolemob layui-hide" id="btn-savemob">
									<i class="iconfont icon-save"></i>
									保存
								</button>
							</shiro:hasPermission>
						</div>
					</div>
					<div class="layui-height-full-50 layui-fluid layui-body" style="margin-top:0;overflow: hidden;">
						<div class="layui-height-full layui-card-content homePageWrap">
							<div class="layui-tab layui-tab-brief layui-height-full" lay-filter="docDemoTabBrief">
								<ul class="layui-tab-title">
								  <li class="layui-this" style="margin-left:44%;">PC端</li>
								  <li>移动端</li>
								</ul>
								<div class="layui-tab-content layui-height-full-50">
								  <div class="layui-tab-item layui-show layui-height-full">
									<div class="layui-card-body layui-height-full-20">
										<div class="layui-tab layui-tab-brief layui-height-full" lay-filter="homeManageTab">
											<ul class="layui-tab-title">
												<li class="layui-this">外观风格</li>
												<li>首页权限</li>
											</ul>
											<div class="layui-tab-content layui-height-full-50">
												<div class="layui-tab-item layui-height-full layui-show" style="overflow-y: auto;">
													<form action="" class="layui-form portalInfo-form" lay-filter="portalInfo-form">
														<div class="layui-form-item">
															<label class="layui-form-label">登录标题</label>
															<div class="layui-input-block">
																<input type="text" name="loginTitle" placeholder="请输入标题" autocomplete="off" lay-verify="required" class="layui-input loginTitle-input" lay-vertype="tips">
															</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">登录logo</label>
															<div class="layui-input-inline" style="width: 55px;">
																<div class="iconfont icon-close-circle clearimg-btn layui-hide" title="清除"></div>
																<div class="uploadimgbox loginLogo-box">
																	<i class="iconfont icon-cloudupload"></i>
																</div>
																<input type="file" name="loginLogo" class="layui-input layui-file-input layui-hide loginLogo-file-input">
															</div>
															<div class="layui-form-mid layui-word-aux">提示：为了使效果较佳，请选择尺寸不小于50*50的图片，支持png、jpg等格式</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">显示品牌信息</label>
															<div class="layui-input-inline" style="width: 50px;">
																<input type="checkbox" value="1" name="isShow" lay-skin="switch" lay-filter="switchTest" lay-text="是|否">
															</div>
															<div class="layui-form-mid layui-word-aux">控制登录页底部是否显示品牌信息</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">背景图片</label>
															<div class="layui-input-inline" style="width: 245px;">
																<div class="iconfont icon-close-circle clearimg-btn layui-hide" title="清除"></div>
																<div class="uploadbgbox">
																	<div class="iconfontbox">
																		<i class="iconfont icon-cloudupload"></i>
																	</div>
																</div>
																<input type="file" name="loginBackground" class="layui-input layui-file-input layui-hide bg-file-input">
															</div>
															<div class="layui-form-mid layui-word-aux">建议上传16/9比例且不大于5M的图片，支持png、jpg等格式</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">平台标题</label>
															<div class="layui-input-block">
																<input type="text" name="title" placeholder="请输入标题" autocomplete="off" lay-verify="required" class="layui-input title-input" lay-vertype="tips">
															</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">平台logo</label>
															<div class="layui-input-inline" style="width: 55px;">
																<div class="iconfont icon-close-circle clearimg-btn layui-hide" title="清除"></div>
																<div class="uploadimgbox logo-box">
																	<i class="iconfont icon-cloudupload"></i>
																</div>
																<input type="file" name="logo" class="layui-input layui-file-input layui-hide logo-file-input">
															</div>
															<div class="layui-form-mid layui-word-aux">提示：为了使效果较佳，请选择尺寸不小于50*50的图片，支持png、jpg等格式</div>
														</div>
														<div class="layui-form-item">
															<label class="layui-form-label">门户类型</label>
															<div class="layui-input-block">
																<div class="layui-inline newPortal portalItem-box active" data-type="2">
																	<img alt="" src="<%=basePath%>static/images/portal/menhu.png" class="portalType-img">
																	<div class="mark-img"></div>
																	<div class="portal-title">办公门户</div>
																</div>
																<div class="layui-inline oldPortal portalItem-box" data-type="1">
																	<img alt="" src="<%=basePath%>static/images/portal/jingdian.png" class="portalType-img">
																	<div class="mark-img"></div>
																	<div class="portal-title">传统门户</div>
																</div>
																<div class="layui-inline multifnPortal portalItem-box" data-type="3">
																	<img alt="" src="<%=basePath%>static/images/portal/menhu1.png" class="portalType-img">
																	<div class="mark-img"></div>
																	<div class="portal-title">多功能集成门户</div>
																</div>
															</div>
														</div>
														<input type="hidden" name="data" class="portalData-input" />
													</form>
												</div>
												<div class="layui-tab-item layui-height-full">
													<div class="layui-row layui-height-full layui-col-space15">
														<div class="layui-col-md4 layui-height-full">
															<div class="authorized-list list-box" >
																<div class="authorized-item authorized-tab" id="staticHome"
																	data-page="defaultPage">静态首页</div>
																<div class="authorized-item authorized-tab" id="adminHome"
																	data-page="systemPage">常规首页</div>
																<div class="authorized-item authorized-tab" id="dsHome"
																	data-page="dsPage">综合首页</div>
																<div class="authorized-item authorized-tab" id="biHome"
																	data-page="biPage">分析首页</div>
																<div class="authorized-item authorized-tab" id="simHome"
																	data-page="simplePage">简易首页</div>
															</div>
														</div>
														<div class="layui-col-md4  layui-height-full">
															<div class="list-box" id="functab">
																<div class="layui-card-header">
																	<div class="TitleVertical"><span
																			class="vertical"></span>已选角色</div>
																</div>
																<div class="formMenu-body layui-height-full-50"
																	style="position: relative;margin-top: 10px">
																	<div class="formbody">
																	</div>
																</div>
															</div>
														</div>
														<div class="layui-col-md4 layui-height-full">
															<div class="list-box" >
																<div class="layui-card-header">
																	<div class="TitleVertical"><span class="vertical"></span>全部角色</div>
																</div>
																<div class="layui-form-item layui-form-item-sm searchRoleWrap">
																	<input type="text" placeholder="请输入要搜索的关键字"
																		autocomplete="off" class="layui-input SearchRole">
																	<button class="searchRoleBtn1 layui-btn-sm"><i
																			class="layui-icon layui-icon-search"
																			style="color:#4785e6"></i></button>
																</div>
															
																<div class="authorityTable" id="authorityTable" style="height:calc(100% - 84px)">
																	<ul class="userlist"></ul>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								  </div>
								  <div class="layui-tab-item" style="height: 100%;">
									<div class="layui-card-body layui-height-full-20">
										<div class="layui-row layui-height-full layui-col-space15">
											<div class="layui-col-md4 layui-height-full">
												
												<div class="authorized-list list-box" >
													<div class="layui-card-header">
														<div class="TitleVertical"><span
																class="vertical"></span>首页类型</div>
													</div>
													<div class="authorized-item authorized-mob" id="staticHome1"
														data-page="defaultPage">静态首页</div>
												</div>
											</div>
											<div class="layui-col-md4  layui-height-full">
												<div class="list-box" id="functab1">
													<div class="layui-card-header">
														<div class="TitleVertical"><span
																class="vertical"></span>已选角色</div>
													</div>
													<div class="formMenu-body layui-height-full-50"
														style="position: relative;margin-top: 10px">
														<div class="formbodymob">
														</div>
													</div>
												</div>
											</div>
											<div class="layui-col-md4 layui-height-full">
												<div class="list-box" >
													<div class="layui-card-header">
														<div class="TitleVertical"><span class="vertical"></span>全部角色</div>
													</div>
													<div class="layui-form-item layui-form-item-sm searchRoleWrap">
														<input type="text" placeholder="请输入要搜索的关键字"
															autocomplete="off" class="layui-input SearchRolemob">
														<button class="searchRoleBtn1mob layui-btn-sm"><i
																class="layui-icon layui-icon-search"
																style="color:#4785e6"></i></button>
													</div>
												
													<div class="authorityTable1" id="authorityTable1" style="height:calc(100% - 84px)">
														<ul class="userlist"></ul>
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
	</body>
	<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/source/homePageManage.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/html" id="checkbox">
		<!-- <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a> -->
	<form class="layui-form" action="">
		<input type="checkbox" name="fd" title="" lay-skin="primary">
	</form>
	</script>
	<script type="text/html" id="repType">
	{{# if(d.resourceType === '1'){ }}
		分析图表
		{{# } else if(d.resourceType === "2") { }}
			填报表单
			{{# } else if(d.resourceType === "3") { }}
				外部链接
				{{# } }}
	</script>
	<script type="text/html" id="userbar">
		<a class="layui-btn layui-btn-sm top" lay-event="top" id="topup" style="width:30px">
			<span>▲</span>
		</a>
		<a class="layui-btn layui-btn-sm bot" lay-event="bot" id="botup" style="width:30px">
			<span>▼</span>
		</a>
	</script>
</html>
