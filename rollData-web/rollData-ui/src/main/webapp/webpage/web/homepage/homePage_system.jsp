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
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="<%=basePath%>static/images/favicon.ico" rel="icon">
        <title>控制台System</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
        <style>
            body{width: 100%;height: 100%;background:url(<%=basePath%>static/images/timg.jpg) no-repeat center center;background-size: 100% 100%}
            .box{width: 100%;height: 100%;}
            .main{padding: 100px;display: flex;flex-wrap: wrap;padding-bottom: 0}
            .main dl{width: 130px;height: 130px;border: 1px solid #fff;margin: 50px;border-radius: 10px;text-align: center;padding: 20px;cursor: pointer;}
            .main dl dd{margin-bottom:5px; }
            .main dl dt{font-size: 14px;color: #fff;}
            .main dl dd img{width: 100px;height: 100px}
            .main dl:hover{background: rgba(255,255,255,.2)}
            @media screen and (max-width: 1300px) {
                .main dl{width: 100px;height: 100px;border: 1px solid #fff;margin: 50px;border-radius: 10px;text-align: center;padding: 20px;cursor: pointer;}
                .main dl dd img{width: 80px;height: 80px}
            }
            @media screen and (max-width: 1200px) {
                .main dl{width: 80px;height: 80px;border: 1px solid #fff;margin: 40px;border-radius: 10px;text-align: center;padding: 20px;cursor: pointer;}
                .main dl dd img{width: 60px;height: 60px}
            }
        </style>
    </head>
    
    <body>
        <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
        <div class="box">
            <div class="main">
            </div>
        </div>
        
        <!-- js部分 -->
        <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
        <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
        <script>
            layui.use(['wdUtil','admin'], function(){
                var $ = layui.$,
                icons = [
                    {"name":"待办任务","icon":"daiban"}, 
                    {"name":"填报查询","icon":"chaxun"},
                    {"name":"明细查询","icon":"mingxi"}, 
                    {"name":"填报任务","icon":"renwu"}, 
                    {"name":"表单设计","icon":"biaodan"},
                    {"name":"汇总方案","icon":"zuzhi"},
                    {"name":"权限分配","icon":"quanxian"},
                    {"name":"智能分析","icon":"zhineng"},  
                ],
                links = [
                    {
                        menuId: "waitNeedController/taskApply",
                        menuPath: "waitNeedController/taskApply",
                        menuName: '待办任务',
                        icon: 'daiban'
                    },
                    {
                        menuId: "reportFormDataQueryController/reportFormDataQuery",
                        menuPath: "reportFormDataQueryController/reportFormDataQuery",
                        menuName: '填报查询',
                        icon: 'chaxun'
                    },
                    {
                        menuId: "reportFormDataStatisticsController/reportFormDataStatisticsQuery",
                        menuPath: "reportFormDataStatisticsController/reportFormDataStatisticsQuery",
                        menuName: '明细查询',
                        icon: 'mingxi'
                    },
                    {
                        menuId: "reportFillTaskController/reportFillTaskList",
                        menuPath: "reportFillTaskController/reportFillTaskList",
                        menuName: '填报任务',
                        icon: 'renwu'
                    },
                    {
                        menuId: "reportFormController/reportFormIndex",
                        menuPath: "reportFormController/reportFormIndex",
                        menuName: '表单设计',
                        icon: 'biaodan'
                    },
                    {
                        menuId: "wdReportSummaryPlanController/wdReportSummaryPlanList",
                        menuPath: "wdReportSummaryPlanController/wdReportSummaryPlanList",
                        menuName: '汇总方案',
                        icon: 'zuzhi'
                    },
                    {
                        menuId: "roleController/roleList",
                        menuPath: "roleController/roleList",
                        menuName: '权限分配',
                        icon: 'quanxian'
                    },
                    {
                        menuId: "analysisController/financeAnalysis",
                        menuPath: "analysisController/financeAnalysis",
                        menuName: '智能分析',
                        icon: 'zhineng'
                    }
                ],
                url = layui.cache.basePath + 'functionController/queryUserAllAvailable',
                callback = function(data){
                    if(data){
                        var arr = [], temp = [];
                        if(data.obj.length){
                            data.obj.forEach(function(v, i){
                                for(var k =0; k<links.length;k++){
                                    if(links[k].menuPath == v.hrefLink){
                                        arr.push(links[k]);
                                        break;
                                    }
                                }
                            });
                            if(arr.length){
                                arr.forEach(function(v,i){
                                    temp.push('<dl class="dllink-btn dl'+i+'" data-url="'+v.menuPath+'" data-name="'+v.menuName+'"><dd><img src="<%=basePath%>static/images/'+v.icon+'.png"/></dd><dt>'+v.menuName+'</dt></dl>')
                                });
                                $('.main').html(temp.join(""));
                                $(".main .dllink-btn").off("click").on("click", function(){
                                    var that = $(this),
                                    url = that.attr("data-url"),
                                    name = that.attr("data-name");
                                    if(top.layui && top.layui.index && top.layui.index.loadView){
                                        top.layui.index.loadView({
                                            menuId: layui.cache.basePath + url,
                                            menuPath: layui.cache.basePath + url,
                                            menuName: name
                                        })

                                    }else{
                                        window.open(layui.cache.basePath + url, name, "", true);
                                    }
                                });
                            }
                        }
                    }
                };
                // layui.wdUtil.send(url, {}, callback);
				layui.admin.loadMoreSide({url: 'functionController/queryUserAllAvailableTree'}, true);
            })
        </script>
    </body>
    
</html>
