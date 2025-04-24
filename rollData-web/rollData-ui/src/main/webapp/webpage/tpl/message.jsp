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
<html lang="en" class="bg-white">
	<head>
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link href="<%=basePath%>static/images/favicon.ico" rel="icon">
		<title>消息</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
		<style>
			/** 消息列表样式 */
			.message-list {
				position: absolute;
				top: 51px;
				bottom: 95px;
				left: 0;
				right: 0;
				overflow-y: auto;
			}
	
			.message-list-item {
				padding: 10px 24px;
				border-bottom: 1px solid #e8e8e8;
				-ms-flex-align: start;
				align-items: flex-start;
				display: flex;
				-ms-flex: 1 1;
				flex: 1 1;
			}
	
			.message-list-item:hover, .message-btn-clear:hover, .message-btn-more:hover {
				background: #F2F2F2;
			}
	
			.message-item-icon {
				width: 40px;
				height: 40px;
				margin-right: 16px;
				display: block;
				margin-top: 4px;
			}
	
			.message-item-right {
				display: block;
				flex: 1 0;
				line-height: 24px;
			}
			.message-list-item.unread-item .message-item-title {
				font-weight: bold;
				color: rgba(0, 0, 0, 1);
			}
			.message-item-title {
				font-size: 14px;
				color: rgba(0, 0, 0, .65);
			}
			
			.message-item-text {
				color: rgba(0, 0, 0, .45);
				font-size: 12px;
			}
			.miralceWrapper .message-item-title {
				color: #333;
			}
			.pull-right {
				float: right;
			}
	
			.message-btn-clear, .message-btn-more {
				display: block;
				padding: 10px 5px;
				text-align: center;
				line-height: 24px;
				color: #333;
			}
	
			.message-btn-clear {
				position: absolute;
				bottom: 0;
				left: 0;
				background: white;
				right: 0;
				border-top: 1px solid #e8e8e8;
			}
	
			.message-btn-more {
				position: absolute;
				bottom: 40px;
				left: 0;
				width: 100%;
				height: 25px;
				background: #fff;
				line-height: 22px;
				text-align: center;
				border-top: 1px solid #e5e5e5;
				color: #666;
				font-size: 13px;
			}
	
			.message-list-empty {
				text-align: center;
				color: rgba(0, 0, 0, .45);
				padding: 73px 0 88px;
			}
	
			.message-list-empty img {
				height: 76px;
				margin-bottom: 16px;
			}
			.content-text{
				width: 250px;
				/* height: 45px;
				overflow-x: hidden;
				overflow-y: hidden; */
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				word-break: break-all;
				overflow: hidden;
			}
			.miralceWrapper .content-text {
				color: #111;
			}
			.loadmore{
				width: 250px;
				text-align: right;
				font-size:12px;
				color:#231815;
			}
			/** //消息列表样式结束 */
		</style>
	</head>
	<body>
		<input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
	<div class="layui-tab layui-tab-brief" style="padding: 5px 0;margin: 0;">
	
		<ul class="layui-tab-title" style="text-align: center;">
			<li class="layui-this">公告</li>
			<li>消息</li>
		</ul>
	
		<div class="layui-tab-content" style="padding: 5px 0;">
	
			<!-- tab1 -->
			<div class="layui-tab-item layui-show">
	
				<div class="message-list notice-list">
	
					<!-- 列表为空 -->
					<div class="message-list-empty" style="display: none;">
						<img src="<%=basePath%>static/images/img_msg_notice.svg">
						<div>没有通知</div>
					</div>
	
				</div>
	
				<a class="message-btn-clear cleares" href="javascript:;">全部标记已读</a>
	
			</div>
			<!-- tab2 -->
			<div class="layui-tab-item">
	
				<div class="message-list news-list">
					<!-- <a id="btn-more1" class="message-btn-more" href="javascript:;"
					style="background: red">加载更多</a> -->
	
					<!-- 列表为空 -->
					<div class="message-list-empty" style="display: none;">
						<img src="<%=basePath%>static/images/img_msg_pri.svg">
						<div>没有消息</div>
					</div>
	
				</div>
				<a id="btn-more1" class="message-btn-more" href="javascript:;">加载更多</a>
				<a class="message-btn-clear" id="closeclear" href="javascript:;">清空消息</a>
	
			</div>
	
		</div>

	</div>	
	<iframe id="export-iframe" src="" style="position: absolute; top: -1000px; left: -9999px;"></iframe>
	</body>
	<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/messageList.min.js?v=<%=jspUpdateVersion%>"></script>
</html>
