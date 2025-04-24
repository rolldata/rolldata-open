<%@ page import="com.rolldata.core.util.SysPropertiesUtil" %>
<%@ page import="com.rolldata.core.util.JspCodeUtils" %>
<%@ page import="com.rolldata.core.util.StringUtil" %>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/webpage/common/taglibs.jspf" %>
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
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>${appearanceConfig.loginTitle}</title>
    <link rel="stylesheet" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" href="<%=basePath%>static/css/login.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">  
	<style>
		.hint{
			font-size: 12px;
			color: #f82121;
			width: 300px;
			margin: 0 auto;
		}
	</style>
	<script>
		if (window != top && location.href.indexOf("indexController")>=0) {
			top.location.replace(location.href);
		}
	</script>	
</head>
<body>
<!-- <div class="bg_scenes">
	<img class="bg_img" src="<%=basePath%>static/images/bg-login.png" alt="">
</div> -->
<div class="login-wrapper-mobile layui-hide">
	<div style="width: 100%;position:absolute;top: 190px;">
		<form class="layui-form mobileFormLogin" id="mobileFormLogin" lay-filter="mobileloginform" action="<%=basePath%>loginController/login" method="post">
			<input type="hidden" name="loginType" id="loginType" value="${loginType}">
			<div style="margin: 16px 0 20px 0;">
				<img style="display:block;margin: 0 auto;" src="<%=basePath%>static/images/logo@2x.png" width="207">
			</div>
			<div class="layui-form-item">
	 			<label class="layui-form-label"><img src="<%=basePath%>static/images/mmob.png" width="20"></label>
				<div class="layui-input-block">
					<input name="username" type="text" placeholder="请输入用户名/手机号" class="layui-input" value="${userName}"
						   lay-verify="required" lay-verType="tips" required/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><img src="<%=basePath%>static/images/mmm.png" width="20"></label>
				<div class="layui-input-block">
					<input name="password" type="password" placeholder="请输入密码" class="layui-input layui-input-psd"
						   lay-verify="required" lay-verType="tips" required/>
					<div class="padshow-btn" data-type="password">
						<i class="iconfont icon-yanjing1"></i>
					</div>
				</div>
			</div>
			<c:if test="${showCaptcha eq 1}">
				<div class="layui-form-item" style="margin-bottom: 50px;">
					<label class="layui-form-label"><i class="iconfont" style="font-size: 20px;color: #1B93D8;">&#xe752;</i></label>
					<div class="layui-input-block">
						<div class="layui-row inline-block">
							<div class="layui-col-xs7">
								<input name="captcha" type="text" placeholder="验证码" class="layui-input"
									   lay-verify="required" required/>
							</div>
							<div class="layui-col-xs5" style="padding-left: 10px;height: 38px;line-height: 38px">
								<img id="login-captcha" style="width:100%;" class="login-captcha" src="<%=basePath%>static/images/kaptcha.jpg" />
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid" lay-submit style="background: linear-gradient(90deg, #0081CC, #2499DD);">登录</button>
			</div>
		</form>
	</div>
	
</div>
<div class="login-wrapper layui-hide">
	<header class="topbar">
		<nav class="navbar navbar-expand-md">
			<!-- ============================================================== -->
			<!-- Logo -->
			<!-- ============================================================== -->
			<div class="navbar-header login-header">
				<!-- Logo icon -->
				<a class="navbar-brand" href="javascripe:void(0)">
					<c:if test="${appearanceConfig.isInitLoginLogo eq 1}">
						<img src="<%=basePath%>static/images/logo.png">
					</c:if>
					<c:if test="${appearanceConfig.isInitLoginLogo eq 0}">
						<img src="<%=basePath%>static/images/upload/${appearanceConfig.loginLogo}">
					</c:if>
				</a>
				<!--End Logo icon -->
				<span class="logo-txt">${appearanceConfig.loginTitle}</span>
			</div>
			<!-- ============================================================== -->
			<!-- End Logo -->
			<!-- ============================================================== -->
			<div class="navbar-collapse navbar-right layui-hide">
				<!-- ============================================================== -->
				<!-- language -->
				<!-- ============================================================== -->
				<div class="languagebox">
					<c:if test="${language =='zh_CN' || language =='zh'}">
						<div class="language-item active" data-value="zh_CN">简体中文</div>
						<div class="language-item last-item" data-value="en_US">English</div>
					</c:if>
					<c:if test="${language =='en_US' || language =='en'}">
						<div class="language-item" data-value="zh_CN">简体中文</div>
						<div class="language-item last-item active" data-value="en_US">English</div>
					</c:if>
				</div>
				<!-- ============================================================== -->
				<!-- End language -->
				<!-- ============================================================== -->
			</div>
		</nav>
	</header>
	<c:if test="${appearanceConfig.isInitLoginBackground eq 1}">
   	<div class="login-body" style="background-image: url(<%=basePath%>static/images/bg_dly.png)">
	</c:if>
	<c:if test="${appearanceConfig.isInitLoginBackground eq 0}">
	<div class="login-body" style="background-image: url(<%=basePath%>static/images/upload/${appearanceConfig.loginBackground})">
	</c:if>
			<div class="layui-card">
            <form class="layui-card-body layui-form layui-form-pane" id="formLogin" action="<%=basePath%>loginController/login" method="post">
            	<input type="hidden" name="loginType" id="loginType" value="${loginType}">
                <div class="layui-form-item mainitem">
                    <label class="layui-form-label"><i class="iconfont">&#xe803;</i></label>
                    <div class="layui-input-block">
                        <input name="username" type="text" placeholder="请输入用户名" class="layui-input layui-input-username" value="" />
                    </div>
                </div>
                <div class="layui-form-item mainitem">
                    <label class="layui-form-label"><i class="iconfont">&#xe650;</i></label>
                    <div class="layui-input-block">
                        <input name="password" type="password" placeholder="请输入密码" value="" class="layui-input layui-input-password"/>
                    </div>
                </div>

                <c:if test="${showCaptcha eq 1}">
	                <div class="layui-form-item mainitem">
	                    <label class="layui-form-label"><i class="iconfont">&#xe752;</i></label>
	                    <div class="layui-input-block">
	                        <div class="layui-row inline-block">
	                            <div class="layui-col-xs7">
	                                <input name="captcha" type="text" placeholder="验证码" class="layui-input"/>
	                            </div>
	                            <div class="layui-col-xs5" style="padding-left: 10px;height: 38px;line-height: 38px">
									<img id="login-captcha" class="login-captcha" src="<%=basePath%>static/images/kaptcha.jpg" />
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </c:if>
				<div style="display: none;"  class="hint hit1"></div>
                <div class="layui-form-item" style="margin-bottom: 40px;">
                    <a href="javascript:;" class="layui-link layui-hide">帐号注册</a>
                    
                    <c:if test="${psdRequest eq 1}">
                    <a href="<%=basePath%>passwordBackController/passwordBack" class="layui-link pull-right" >忘记密码？</a>
                    </c:if>
                </div>
                <div class="layui-form-item">
                    <button lay-filter="login-submit" class="layui-btn layui-btn-fluid" lay-submit>登 录</button>
                </div>
                <div class="layui-form-item login-other layui-hide">
                    <label>第三方登录</label>
                    <a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
                    <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
                    <a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>
				</div>
				<div class="layui-form-item login-other layui-hide">
					<a id="webChatLoginPC" href="<%=basePath%>thirdPartyController/webChatLoginPC"><i class="iconfont icon-weixin-copy"></i><span>微信登录</span></a>
				</div>
            </form>
        </div>
    </div>
	<c:if test="${appearanceConfig.isShow eq 1}">
		<div class="loginfoot">Copyright © 2013-2021 powered by 北京小数嘀嗒信息技术有限公司 All Rights Reserved</div>
	</c:if>
    <!-- <div class="login-footer">
        <p>© 2019 WrenchData 版权所有</p>
    </div> -->
</div>

<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/libs/base64/base64.min.js?v=<%=jspUpdateVersion%>"></script>
<script>
	var error="${error}";
    layui.config({
		base: '${basePath}' + 'static/module/'
	}).extend({
		notice: 'notice/notice',
		mpopups: 'mobile-popups/popups'
	}).use(['layer', 'form', 'notice', 'mpopups'], function () {
		var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		isMobile = (/Mobi/.test(navigator.userAgent)) ? true : false,
		notice = layui.notice;
		const filterXSS = function(str){
			let s = "";
			if(str.length == 0) return "";
			s = str.replace(/&/g,"&amp;");
			s = s.replace(/</g,"");
			s = s.replace(/>/g,"");
			s = s.replace(/ /g,"");
			s = s.replace(/\'/g,"");
			s = s.replace(/\"/g,"");
			return s; 
		}
		if(isMobile){
			$(".login-wrapper").addClass('layui-hide');
			$(".bg_scenes").addClass('layui-hide');
			$(".login-wrapper-mobile").removeClass('layui-hide');
		}else{
			$(".login-wrapper").removeClass('layui-hide');
			$(".bg_scenes").removeClass('layui-hide');
			$(".login-wrapper-mobile").addClass('layui-hide');
		}
		
		if(error !=""){
			if(isMobile && layui.mpopups){
				layui.mpopups.jqtoast(error);
			}else{
				// layer.tips(error, $(".layui-input-password"),{tips:1})
			}
			$('.hit1').show()
			$('.hit1').text(error)
		}
		$(".padshow-btn").off('click').on('click', function(){
			var el = $(this),
			type = el.attr('data-type');
			if(type == 'text'){
				el.attr('data-type', 'password');
				$(".layui-input-psd").attr('type', 'password');
				el.find('.iconfont').removeClass('icon-yanjing').addClass('icon-yanjing1');
			}else{
				el.attr('data-type', 'text');
				$(".layui-input-psd").attr('type', 'text');
				el.find('.iconfont').addClass('icon-yanjing').removeClass('icon-yanjing1');
			}
		})
		
		$(".language-item").off("click").on("click", function(){
			if(!$(this).hasClass("active")){
				var value = $(this).attr("data-value");
				$("#language").val(value);
				$("#formLogin").attr("action","${basePath}"+"loginController/language"); 
				$("#formLogin").submit();
			}
		});
		form.on('submit(login-submit)', function(data){
			if(data.field.captcha!==undefined && data.field.captcha==''){
				$('.hit1').show()
				$('.hit1').text('验证码不能为空！')
				return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
			}
			let userName = filterXSS(data.field.username);
			let password = filterXSS(data.field.password);
			if(userName != data.field.username){
				$(".layui-input-username").val(userName);
			}
			if(password != data.field.password){
				$(".layui-input-password").val(password);
			}
			if(data.field.username=='' || data.field.password==''){
				$('.hit1').show()
				$('.hit1').text('用户名或密码不能为空！')
				return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
			}
			var psd = Base64.encode(password);
			if(psd) $(".layui-input-password").val(encodeURIComponent(psd));
		});
		$(".mainitem:last").css("margin-bottom",'5px') 

        // 图形验证码
        $('.login-captcha').click(function () {
			this.src = this.src + '?t=' + (new Date).getTime();
        });
    });
</script>
</body>
</html>
