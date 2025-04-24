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
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
    <title>首页</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
    <style>
        @media screen and (min-width: 800px) {
                html {
                font-size: 100px !important;
                }
        }
        @media screen and (min-width: 1100px) {
            html {
                font-size: 120px !important;
            }
        }
        @media screen and (min-width: 1240px) {
            html {
                font-size: 140px !important;
            }
            .headleft {
                top: 1rem !important;
                left: 1.5rem !important;
            }
            .headright {
                margin-right: 0.2rem !important;
                margin-top: 0.2rem !important;
            }
            .footcont {
                margin-left: 1rem !important;
            }
            .footfontli{
                margin-right: 0 !important;
            }
        }
        @media screen and (min-width: 1400px) {
            html {
                font-size: 140px !important;
            }
            .headleft {
                top: 1.33571rem !important;
                left: 2.22857rem !important;
            }
            .headright {
                margin-right: 2.23571rem !important;
                margin-top: 0.69285rem !important;
            }
            .footcont {
                margin-left: 2.29285rem !important;
            }
            .footfontli{
                margin-right: 1.02142rem !important;
            }
        }
        .statichomefoot{
            width: 100%;
            height: 1.37857rem;
            background: rgba(255,255,255,0.4);
            position: absolute;
            bottom: 0;
        }
        .headleft{
            display: inline-block;
            position: absolute;
            top: 1.33571rem;
            left: 2.22857rem;
        }
        .showBiDemo1{
            width: 5rem;
            height: 3.5rem;
        }
        .showBiDemo{
            margin-bottom: 0.21428rem;
        }
        
        .onefont{
            margin-bottom: 0.34285rem;
            font-size: 0.18571rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #000000;
            line-height: 0.21428rem;
        }
        .twofont{
            width:3.2rem;
            height: 0.54285rem;
            font-size: 0.11428rem;
            font-family: PingFang SC;
            font-weight: 400;
            color: #000000;
            line-height: 0.21428rem;
        }
        .headright{
            float: right;
            margin-right: 2.23571rem;
            margin-top: 0.69285rem;
        }
        .footcont{
            margin-left: 2.29285rem;
        }
        .footfontli{
            display: inline-block;
            margin-right: 1.02142rem;
            width: 2.57142rem;
        }
        .footfontone{
            font-size: 0.12857rem;
            font-family: PingFang SC;
            font-weight: 500;
            color: #000000;
            line-height: 0.14285rem;
            margin-bottom: 0.10714rem;
        }
        .footfonttwo{
            font-size: 0.08571rem;
            font-family: PingFang SC;
            font-weight: 500;
            color: #333333;
            line-height: 0.14285rem;
        }
        .foottop{
            margin-top: 0.21428rem;
            margin-bottom: 0.19285rem;
        }
        .statichomePage{
            background-size: 100% 100%;
        }
        
    </style>

</head>

<body>
    <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
    <div id="LAY_app" class="wrapper statichomePage">
        <div class="statichomehead">
            <div class="headleft">
                <img class="showBiDemo" src="<%=basePath%>static/images/rolldata.png" alt="" />
                <div class="onefont">卓越技术，优质体验</div>
                <div class="twofont">助您打通企业数据上报与分析的任督二脉，使企业数据能够高效率、高质量、高标准的上报、汇集与分析，助力企业加速实现数字化管理。</div>
            </div>
            <div class="headright">
                <img class="showBiDemo1" src="<%=basePath%>static/images/static2.gif" alt="" />
            </div>

        </div>
        <div class="statichomefoot">
            <div class="footcont">
                <div class="foottop">
                    <div class="footfontli">
                        <div class="footfontone">数据可视化</div>
                        <div class="footfonttwo">智能数字大屏、领导驾驶舱、移动数据分析、自助可视化分析</div>
                    </div>
                    <div class="footfontli">
                        <div class="footfontone">查询报表</div>
                        <div class="footfonttwo">表格数据报表、数字报告、自助查询报表</div>
                    </div>
                    <div class="footfontli">
                        <div class="footfontone">数据上报</div>
                        <div class="footfonttwo">自定义上报、频率上报、流程控制、数据权限、档案管理</div>
                    </div>
                </div>
                <div class="footbot">
                    <div class="footfontli">
                        <div class="footfontone">数据整合</div>
                        <div class="footfonttwo">多数据源、抽取任务、抽取作业、调度计划、日志明细、执行监控</div>
                    </div>
                    <div class="footfontli">
                        <div class="footfontone">模型管理</div>
                        <div class="footfonttwo">智能模型构造、SQL模型构造、存储过程模型构造</div>
                    </div>
                    <div class="footfontli">
                        <div class="footfontone">平台管理</div>
                        <div class="footfonttwo">智能预警、数据权限、系统集成、数据云图、门户集成、访问监控</div>
                    </div>
                </div>
            </div>
           


        </div>
        
        
    </div>
    <!-- js部分 -->
    <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>


</body>

</html>
