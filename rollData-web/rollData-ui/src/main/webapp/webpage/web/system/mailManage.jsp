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
  <title>邮箱管理</title>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
  <style>
    @media screen and (max-device-width:1366px){
      #smtpserver{
        height:22px !important;
        line-height:22px !important;
      }
    }
  </style>
</head>
<body>
  <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
  <div class="layui-body">
    <div class="layui-height-full-30 layui-fluid">
      <div class="layui-card layui-height-full">
        <div class="layui-card-header">
          <div class="TitleVertical"><span class="vertical"></span>发件人账户</div>
          <div class="layui-right-float">
            <shiro:hasPermission name="sys:mailManage:updateMailManage">
              <button class="layui-btn layui-btn-sm layui-btn-cardTop save-role" id="btn-save">
                <i class="iconfont icon-save"></i>
                保存
              </button>
            </shiro:hasPermission>
          </div>
        </div>
        <div class="layui-height-full-62 layui-fluid layui-body" style="margin-top:20px">
          <form class="layui-form layui-height-full-30" action="" lay-filter="mailManage">
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px"><i style="color: red;margin-right: 5px;">*</i>邮件服务器(SMTP)：</label>
              <div class="layui-input-inline" style="width:400px;position: relative;">
                <div class="selectBox" style="position: absolute;width: 100%">
                  <select name="selectValue" lay-verify="required" id="selectValue" lay-filter="email-selected">
                    <option value="SMTP.gmail.com">SMTP.gmail.com</option>
                    <option value="SMTP.sina.com">SMTP.sina.com</option>
                    <option value="SMTP.163.com">SMTP.163.com</option>
                    <option value="SMTP.qq.com">SMTP.qq.com</option>
                    <option value="SMTP.sohu.com">SMTP.sohu.com</option>
                    <option value="SMTP.live.com">SMTP.live.com</option>
                    <option value="SMTP.mail.yahoo.com">SMTP.mail.yahoo.com</option>
                    <option value="SMTP.netease.com">SMTP.netease.com</option>
                    <option value="SMTP.sina.com.cn">SMTP.sina.com.cn</option>
                    <option value="SMTP.263.net">SMTP.263.net</option>
                    <option value="SMTP.126.com">SMTP.126.com</option>
                  </select>
                </div>
                <div class="inputBox"
                  style="position:absolute;width: 90%;height: 28px; line-height: 28px; top: 1px; left: 1px;">
                  <input type="text" name="smtpserver" lay-verify="required" placeholder="请输入/选择邮箱服务器" autocomplete="off" class="layui-input" style="border:none;height: 28px;" id="smtpserver"  lay-vertype="tips">
                </div>
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px"><i style="color: red;margin-right: 5px;">*</i>端口：</label>
              <div class="layui-input-inline" style="width:400px">
                <input type="text" name="serverPort" lay-verify="required" placeholder="请输入端口号" autocomplete="off"   lay-vertype="tips" class="layui-input" id="serverPort" value="25">
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px"><i style="color: red;margin-right: 5px;">*</i>发件人地址：</label>
              <div class="layui-input-inline" style="width:400px">
                <input type="text" name="senderAddress" id="senderAddress" lay-verify="email" autocomplete="off" class="layui-input"  lay-vertype="tips" placeholder="发件人地址">
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px"><i style="color: red;margin-right: 5px;">*</i>密码：</label>
              <div class="layui-input-inline" style="width:400px">
                <input type="password" name="password" id="password" placeholder="请输入密码" autocomplete="off"  lay-vertype="tips" class="layui-input" lay-verify="required">
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px;padding: 8px 0;"><i style="color: red;margin-right: 5px;">*</i>是否启用SSL安全连接：</label>
              <div class="layui-input-inline" style="margin-top: -3px;width:400px">
                <input type="radio" name="sslIs" value="0" title="否" id="sslIsf" checked="">
                <input type="radio" name="sslIs" value="1" title="是" id="sslIst">
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
              <label class="layui-form-label" style="width:150px"><i style="color: red;margin-right: 5px;">*</i>显示姓名：</label>
              <div class="layui-input-inline" style="width: 400px">
                <input type="text" name="displayName" id="displayName" lay-verify="required" autocomplete="off"  lay-vertype="tips" class="layui-input layui-input-inline" placeholder="姓名" style="width:100%">
                <!-- <label class="layui-form-label"
                  style="position:absolute; right:-120px;text-align:left;width:90px;color: #01AAED;cursor: pointer;" id="test" lay-filter="demo1" lay-submit="">发送测试邮件</label> -->
                <shiro:hasPermission name="sys:mailManage:testMail">
                  <label class="layui-form-label" style="position:absolute; right:-120px;text-align:left;width:90px;color: #4785e6;cursor: pointer;" id="test">发送测试邮件</label>
                </shiro:hasPermission>
              </div>
            </div>
            <div class="layui-form-item layui-form-item-sm layui-hide">
              <button class="layui-btn" lay-submit lay-filter="savemail" id="savemail" data-type="1">立即提交</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/source/mailManage.js?v=<%=jspUpdateVersion%>"></script>
</html>
