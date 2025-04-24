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
        <title>控制台BI</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
        <style>
            body .homePageBI-page .homePageBIbox .homePageBImain{
                width: 19.6%;
                margin-bottom: 25px;
                
            }
            body .homePageBI-page .homePageBIbox .homePageBIlist{
                width: 89%;
                box-shadow: none;
            }
            body .homePageBI-page .homePageBItext{
                background: transparent;
                margin-top: 10px;
            }
            body .homePageBI-page .homePageBIimg img{
                border-radius: 10px;
            }
            body .homePageBI-page .homePageBItext p{
                color: #000;
            }
        </style>
    </head>
    
    <body>
        <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
        <div id="LAY_app" class="wrapper homePageBI-page">
            <div class="wrap layui-height-full">
                <div class="homePageBIbox" style="padding: 0;padding-top: 30px;padding-left: 80px;">
                    <!-- <div class="homePageBImain">
                        <dl class="homePageBIlist">
                            <dd class="homePageBIimg">
                                <img src="<%=basePath%>static/images/表格-4.png" alt="">
                            </dd>
                            <dt class="homePageBItext">
                                <p>fdsf</p>
                                <p>fdsfd</p>
                            </dt>
                        </dl>
                    </div> -->
                </div>
            </div>
            
        </div>
        
        <!-- js部分 -->
        <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
        <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
        <script>
            layui.define(['wdUtil','layer', 'element','laydate','setter'], function () {
                var $ = layui.jquery;
                var layer = layui.layer;
                var element = layui.element;
                var laydate = layui.laydate;
                var setter = layui.setter;
                var myUtil = layui.wdUtil;
                var url = setter.basePath + "homePageController/queryBIHomepageList",     
                callback = function(data){
                    var obj = data.obj
                    var str = '';
                    for(var i=0;i<obj.length;i++){
                        str += '<div class="homePageBImain" data-type="'+obj[i].resourceType+'" data-url="'+obj[i].resourceUrl+'" data-id="'+obj[i].id+'" data-rId="'+obj[i].relationId+'" data-name="'+obj[i].name+'"><dl class="homePageBIlist"><dd class="homePageBIimg"><img src="<%=basePath%>static/images/upload/'+obj[i].imgUrl+'" alt=""></dd><dt class="homePageBItext"><p>'+obj[i].name+'</p></dt></dl></div>'
                    }
                    $('.homePageBIbox').html(str)

                    $(".homePageBImain").off("click").on("click",function(){
                        var that = $(this),
                        src = "",
                        type = that.attr("data-type"),
                        path = that.attr("data-url"),
                        rid = that.attr("data-rId"),
                        id = that.attr("data-id"),
                        title = that.attr("data-name");
                        // 0：文件夹	1：分析图表		2：填报表单		3：外部链接
                        switch(type){
                            case "0" : 
                                break;
                            case "1" : 
                                src = setter.basePath + "reportController/viewReport?reportId="+rid+"&navname=分析首页&nodeid="+id+"&viewType="+"&relationName="+title+"&resourcePath="+path+"&menuId=''";
                                break;
                            case "2" : 
                                src = setter.basePath + "reportFormController/reportFormSubmit_new?formId="+rid+"&navname=分析首页&nodeid="+id+"&relationName="+title+"&resourcePath="+path+"&menuId=''";	
                                break;
                            case "3" : 
                                if(path.indexOf("http") > -1 && path){
                                    src = path;
                                }else{
                                    src = setter.basePath + path
                                }
                                break;
                            case '4' :  
                                src = setter.basePath + "dataReportController/preview?cid="+rid+"&navname=分析首页&nodeid="+id+"&relationName="+title+"&resourcePath="+path+"&menuId=''"
                                break;
                            case "5":
                                src = setter.basePath + "dataReportController/previewGroup?cid="+rid+"&navname=分析首页&nodeid="+id
                                break;
                            case "6":
                                src = setter.basePath + "reportController/viewReportBigScreen?reportId="+rid+"&navname=分析首页&nodeid="+id
                                break;
                            case "7":
                                src = setter.basePath + "reportController/viewReportMobile?cid="+rid+"&navname=分析首页&nodeid="+id
                                break;
                            case "8":
                                src = setter.basePath + "mobileFormController/mobileFormDesignPage?formId="+rid+"&navname=分析首页&nodeid="+id
                                break;	
                            case "10":
                                src = setter.basePath + "simpleDataReportController/simpleDataReportView?cid="+rid+"&navname=分析首页&nodeid="+id+"&relationName="+title+"&resourcePath="+path+"&menuId=''"
                                break;		
                        }
                        if(top.layui && top.layui.index && top.layui.index.loadView){
                            top.layui.index.loadView({
                                menuId: src,
                                menuPath: src,
                                menuName: title
                            });
                        }else{
                            window.open(src, title, "", true);
                        }
                    })
                    calculationHeight();
                }
                myUtil.send(url,{},callback)
                
                function calculationHeight(){
                    var img = $(".homePageBIimg"),
                    width = img.width(),
                    height = width / (16/9);
                    img.height(height);
                }
                $(document).resize(function(){calculationHeight()})
            });
            
        </script>
    </body>
    
</html>
