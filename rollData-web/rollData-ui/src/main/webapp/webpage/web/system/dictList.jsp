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
		<title>基础档案</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/zTreeStyle.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/libs/zTree/zTreeStyle/layZTreeStyle.css?v=<%=jspUpdateVersion%>">
		<style>
			@media screen and (min-device-width:1366px) {
				.IndexDirectory{
					position: relative;
					text-align: left;height: 40px;
					line-height: 40px;color: #333;
					font-size: 14px;font-family:Verdana, Arial, Helvetica, AppleGothic, sans-serif;
					font-weight: bolder;
					padding-left: 40px;
				}
				.IndexDirectory::before{
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
				.layui-nav-treebody>.layui-nav-treebox{
					top: 40px;
					right: 0;
					left: 0;
				}
			}
			@media screen and (max-device-width:1366px) {
				.IndexDirectory{
					position: relative;
					text-align: left;height: 40px;
					line-height: 32px;color: #333;
					font-size: 12px;font-family:Verdana, Arial, Helvetica, AppleGothic, sans-serif;
					font-weight: bolder;
					padding-left: 40px;
				}
				.IndexDirectory::before{
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
				.layui-nav-treebody>.layui-nav-treebox{
					top: 30px;
					right: 0;
					left: 0;
				}
				/* .HierarchicalName1,.digit1,.inputName{
					height: 26px;
				}
				.select1{
					margin-bottom: 0px
				} */
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
							<shiro:hasPermission name="sys:dictionary:save">
								<div class="layui-nav-treebar-item btn-add" id="LAY-tree-add" title="新建档案">
									<i class="iconfont icon-create"></i>
								</div>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:dictionary:update">
								<div class="layui-nav-treebar-item btn-del" id="LAY-tree-edit" title="编辑档案">
									<i class="iconfont icon-edit"></i>
								</div>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:dictionary:delete">
								<div class="layui-nav-treebar-item btn-del" id="LAY-tree-remove" title="删除档案">
									<i class="iconfont icon-delete"></i>
								</div>
							</shiro:hasPermission>
							<div class="layui-nav-treebar-item btn-search" id="LAY-tree-openSearch" title="查询档案">
								<i class="iconfont icon-search"></i>
							</div>
							<div class="layui-nav-searchbox layui-hide">
								<input class="tree-search layui-input" name="" value="" id="LAY-tree-search" />
								<div class="layui-nav-search-close" id="LAY-tree-closeSearch"><i class="iconfont icon-close"></i></div>
							</div>
						</div>
						<div class="layui-nav-treebox">
							<div class="IndexDirectory">
								档案目录
							</div>
							<div class="layui-nav-tree layui-mylayui1-tree" id="LAY-dictList-tree" lay-filter="layui-dictList-tree"></div>
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
					<div class="layui-fluid layui-height-full-30">
						<div class="box-main layui-height-full">
							<div class="layui-card layui-height-full">
								<div class="layui-card-header">
									<div class="TitleVertical"><span class="vertical"></span>基础档案</div>
									<div class="layui-right-float">
										<div class="layui-input-inline layui-form-item-sm" style="position:relative;">
											<input type="text" class="layui-input searchInput" name="searchInput" style="width: 170px; margin-right: 3px;">
											<botton type="button" class="layui-btn-sm dictionary-search" style="margin-left:0;position: absolute;top:0;right:0;">
												<i class="layui-icon layui-icon-search" style="color:#4785e6"></i>
											</botton>
										</div>
										<shiro:hasPermission name="sys:dictionary:data:save">
											<button type="button" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-add"><i class="layui-icon layui-icon-add-circle"></i>新增</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:dictionary:data:update">
											<button type="button" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-edit"><i class="layui-icon layui-icon-edit"></i>编辑</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:dictionary:data:exchangeOrder">
											<button type="button" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-up"><i class="layui-icon layui-icon-up"></i>上移</button>
											<button type="button" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-down"><i class="layui-icon layui-icon-down"></i>下移</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:dictionary:data:uploadFile">
											<button type="button" id="imp" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-import"><i class="icon iconfont icon-import2" style="font-size:12px!important;margin-right:6px"></i>导入</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:dictionary:data:delete">
											<button type="button" id="del-detail" class="layui-btn layui-btn-cardTop layui-btn-sm dictionary-del"><i class="layui-icon layui-icon-delete"></i>删除</button>
										</shiro:hasPermission>
									</div>
								</div>
								<div class="layui-card-body layui-height-full-62">
									<div class="layui-height-full treeWrap dictListSec-box" style="width: 100%;position:relative" id="LAY-dictListSec-tree" lay-filter="layui-dictListSec-tree">
										<table lay-filter="dictListSec" id="dictListSecTable"></table>
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
												<div class="downText layui-right-float">填写导入档案的信息</p>
													<p class="layui-hide"><a style="color: #4785e6;cursor: pointer;" class="downTxtfirst">下载txt模板</a></p>
													<p><a style="color: #4785e6;cursor: pointer;" class="downExcelfirst">下载Excel模板</a></p>
													<iframe id="exceliframes" style="display:none"></iframe>
												</div>
											</div>
											<div class="uploading">
												<div class="downIcon">
													<i class="iconfont icon-cloudupload"></i>
												</div>
												<div class="downText layui-right-float">上传填写好的档案信息表<span style="color:#999">(仅支持.txt/.xls/.xlsx格式，且文件大小不能超过5M)</span></p>
													<p><a style="color: #4785e6;cursor: pointer;" class="uploadTemplateFirst">上传模板</a><span class="fristUploadFilename"></span></p>
													<form class="layui-form layui-hide" action="" id="firstuploadForm" method="post" enctype="multipart/form-data">
														<input type="file" name="importExcel" id="uploadTemplateFirst" class="layui-btn layui-btn-sm" style="opacity:0;position:absolute;top:0;left:0;bottom:0;right:0;width:200px;height:30px">
														<input type="text" name="dictTypeId" id="dictTypeIds" class="layui-btn layui-btn-sm" style="opacity:0;position:absolute;top:0;left:0;bottom:0;right:0;width:200px;height:30px">
												  </form>
												</div>
											</div>
											<button type="button" class="layui-btn firstStepButton">下一步</button>
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
													<i class="iconfont icon-unusual" ></i>
												</div>
												<div class="textContent layui-right-float">
														<p>本次不可导入条数：<span class="bad"></span></p>
												</div>
											</div>
											<div class="buttonWrap">
												<button type="button" class="layui-btn secondStepButton">下一步</button>
												<a href="javaScript:void(0)" style="color: #4785e6;vertical-align:bottom" class="goBack">返回重新上传</a>
											</div>
											<div class="table-box">
												<table class="secondStepTable" id="secondStepTable" lay-filter="secondStepTable"></table>
											</div>
										</div>
										<div class="finish layui-hide">
											<div class="finishText">
												<div class="icon">
													<i class="iconfont icon-normal" ></i>
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
		<form id="uploadexcelstyle-form" action="" enctype="multipart/form-data" method="post" style="position: absolute; top: -1000px; left: -9999px;">
			<input id="importExcel" class="" type="file" name="importExcel">
			<input type="hidden" name="formId" id="formId-input">
		</form>
		<iframe id="export-iframe" src="" style="position: absolute; top: -1000px; left: -9999px;"></iframe>
		
		<div class="newArchivesDataCreate" style="display: none;">

		</div>
	</body>
	<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery-1.4.4.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/libs/zTree/jquery.ztree.all.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/libs/zTree/fuzzysearch.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/source/dictList.js?v=<%=jspUpdateVersion%>"></script>

</html>
