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
  <title>密码安全</title>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
  <style>
    .layui-form-label {
      width: 220px;
      font-size: 14px;
    }
  </style>
</head>


<body>
  <!-- ============================================================== -->
  <!-- Configuration  - input:type=hidden -->
  <!-- ============================================================== -->
  <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
  <input type="hidden" name="pagename" id="requireScriptNames" value="bs-dropdown,waves,psd">

  <!-- ============================================================== -->
  <!-- viewFramework-body - style you can find in style.css -->
  <!-- ============================================================== -->
  <div class="layui-fluid layui-height-full-30">
    <!-- ============================================================== -->
    <!-- Topbar - style you can find in style.css -->
    <!-- ============================================================== -->

    <!-- ============================================================== -->
    <!-- End Topbar -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Console-Container -->
    <!-- ============================================================== -->
    <!-- Overview-Tab -->
    <div class="console-tab layui-height-full">
      <!-- Console-Content -->
      <div class="layui-card layui-height-full">
        <!-- Console Panel Title-->
        <div class="layui-card-header">
          <div class="TitleVertical"><span class="vertical"></span>密码安全</div>
          <div class="layui-right-float">
            <shiro:hasPermission name="sys:passwordSecurity:updatePasswordSecurity">
              <button class="layui-btn layui-btn-sm layui-btn-cardTop save-role" id="btn-save">
                <i class="iconfont icon-save"></i>
                保存
              </button>
            </shiro:hasPermission>
            </div>
        </div>
        <!-- End Console Panel Title-->
        <!-- Console Panel Inner -->
        <div class="layui-card-body layui-height-full-50">
          <form action="" method="POST" class="layui-form layui-height-full-30" enctype="multipart/form-data"
            lay-filter="example">
            <div class="layui-height-full">
              <div class="layui-height-full">
                <div class="layui-form-item">
                  <label for="psdLength" class="layui-form-label"><span>密码长度：</span></label>
                  <div class="layui-input-inline">
                    <select name="psdLength" id="psdLength">
                      <option value=""></option>
                      <option value="6">6</option>
                      <option value="8">8</option>
                      <option value="10">10</option>
                    </select>
                  </div>
                </div>
                <div class="layui-form-item">
                  <label for="psdSetting" class="layui-form-label"><span>密码组合设置：</span></label>
                  <div class="layui-input-inline">
                    <select name="psdSetting" id="psdSetting">
                      <option value="1" selected>数字</option>
                      <option value="2">字母+数字</option>
                      <option value="3">特殊字符+字母+数字</option>
                    </select>
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否启用验证码：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" name="psdValidateIs" value="0" title="否" id="psdValidateIsf" checked="">
                    <input type="radio" name="psdValidateIs" value="1" title="是" id="psdValidateIst">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否启用强制修改初始密码：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" name="psdInitIs" value="0" title="否" id="psdInitIsf" checked="">
                    <input type="radio" name="psdInitIs" value="1" title="是" id="psdInitIst">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否启用修改密码期限：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" lay-filter="psdPeriodRadio" name="psdPeriodIs" value="0" title="否" id="psdPeriodIsf" checked="">
                    <input type="radio" lay-filter="psdPeriodRadio" name="psdPeriodIs" value="1" title="是" id="psdPeriodIst">
                  </div>
                </div>
                <div class="layui-form-item psdPeriodWrap">
                  <label for="psdPeriod" class="layui-form-label"><span>修改密码期限：</span></label>
                  <input class="layui-input-inline layui-input" type="number" value="0" name="psdPeriod"
                    id="psdPeriod" />
                  <span style="padding-left:10px;line-height: 38px;">天</span>
                </div>
                 <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否启用密码错误锁定用户：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" name="psdErrNumberIs" lay-filter="psdErrNumberRadio" value="0" title="否" id="psdErrNumberIsf" checked="">
                    <input type="radio" name="psdErrNumberIs" lay-filter="psdErrNumberRadio" value="1" title="是" id="psdErrNumberIst" >
                  </div>
                </div>
                <div class="layui-form-item psdErrNumberWrap">
                  <label for="psdErrNumber" class="layui-form-label"><span>输入密码错误锁定用户次数：</span></label>
                  <div class="layui-input-inline">
                    <select name="psdErrNumber" id="psdErrNumber">
                      <option value="3" selected>3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                    </select>
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>忘记密码是否发送请求：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" name="psdRequest" value="0" title="否" id="psdRequestf" checked="">
                    <input type="radio" name="psdRequest" value="1" title="是" id="psdRequestt">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否限制一处登陆：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" name="isLimitLogin" value="0" title="否" id="isLimitLogin" checked="">
                    <input type="radio" name="isLimitLogin" value="1" title="是" id="isLimitLogin">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label"><span>是否启用资源访问密码：</span></label>
                  <div class="layui-input-inline">
                    <input type="radio" lay-filter="isBrowsePasswordRadio" name="isBrowsePassword" value="0" title="否" id="isBrowsePasswordf" checked="">
                    <input type="radio" lay-filter="isBrowsePasswordRadio" name="isBrowsePassword" value="1" title="是" id="isBrowsePasswordt">
                  </div>
                </div>
                <div class="layui-form-item browsePasswordWrap layui-hide">
                  <label for="browsePassword" class="layui-form-label"><span>设置默认访问密码：</span></label>
                  <input class="layui-input-inline layui-input" type="text" value="" name="browsePassword" id="browsePassword" placeholder="请输入密码" autocomplete="off" lay-verify="required|password"  lay-vertype="tips" />
                  <span style="padding-left:10px;line-height: 38px;"><a href="javascript:void(0);" style="color: #4785e6;text-decoration: underline" class="update-browsePassword-btn">密码管理</a></span>
                </div>
                <button class="layui-btn layui-hide" lay-submit id="savePasswordSecurity" lay-filter="savePasswordSecurity">提交</button>
              </div>
            </div>
          </form>
        </div>

        <!-- End Console Panel Inner -->
      </div>
      <!--End Console-Content -->
    </div>
    
    <!-- ============================================================== -->
    <!-- End Console-Container -->
    <!-- ============================================================== -->
  </div>
  <!-- ============================================================== -->
  <!-- End viewFramework-body -->
  <!-- ============================================================== -->
  <!-- ============================================================== -->

</body>
<!-- <script type="text/javascript" src="<%=basePath%>static/js/require.js" data-main="<%=basePath%>static/js/main?v=<%=jspUpdateVersion%>"></script> -->
<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/source/passwordSecurity.js?v=<%=jspUpdateVersion%>"></script>

</html>
