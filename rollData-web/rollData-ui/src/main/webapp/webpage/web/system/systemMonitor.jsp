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
        <title>系统监控</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.min.css?v=<%=jspUpdateVersion%>">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/iconfont/iconfont.css?v=<%=jspUpdateVersion%>">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pages.min.css?v=<%=jspUpdateVersion%>">
    </head>
    
    <body>
        <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
        <!-- 系统监控 <br> -->
        <!-- ============================================================== -->
        <!-- layui-body - style you can find in pages.css -->
        <!-- ============================================================== -->
        <div class="layui-body" id="LAY_sysMonitro_body">
            <div class="layui-fluid layui-height-full-30">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md3">
                        <div class="layui-card cpu-card layui-card-align-center">
                            <div class="layui-card-header">CPU监控</div>
                            <div class="layui-card-body">
                                <div class="ant-progress ant-progress-circle ant-progress-show-info ant-progress-default ant-progress-status-normal">
                                    <div class="ant-progress-inner" style="width: 120px; height: 120px; font-size: 24px;">
                                        <svg class="ant-progress-circle" viewBox="0 0 100 100">
                                            <!---->
                                            <path d="M 50,50 m 0,47
                                                a 47,47 0 1 1 0,-94
                                                a 47,47 0 1 1 0,94" 
                                                stroke-linecap="round" stroke-width="6" fill-opacity="0" class="ant-progress-circle-trail" style="stroke-dasharray: 220.31px, 295.31px; stroke-dashoffset: -37.5px; transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity 0.3s;"></path>
                                            <path d="M 50,50 m 0,47
                                            a 47,47 0 1 1 0,-94
                                            a 47,47 0 1 1 0,94" stroke="" stroke-linecap="round" stroke-width="6" opacity="1" fill-opacity="0" class="ant-progress-circle-path" style="stroke-dasharray: 0, 295.31px;stroke-dashoffset: -37.5px;transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity;stroke: rgb(73, 170, 25);"></path>
                                           
                                        </svg>
                                        <span class="ant-progress-text" title="0%">0%</span>
                                    </div>
                                    <div class="ant-tooltip ant-tooltip-placement-top" style="top: -70px;">
                                        <div class="ant-tooltip-content">
                                            <div class="ant-tooltip-arrow"><span class="ant-tooltip-arrow-content"></span></div>
                                            <div class="ant-tooltip-inner" role="tooltip">
                                                <div><span>CPU系统使用率：</span><span class="cpuSysUseRate"></span></div>
                                                <div><span>CPU用户使用率：</span><span class="cpuUserUseRate"></span></div>
                                                <div><span>CPU当前总使用率：</span><span class="cpuTotalUseRate"></span>%</div>
                                                <div><span>CPU当前等待率：</span><span class="cpuWaitRate"></span></div>
                                                <div><span>CPU当前空闲率：</span><span class="cpuFreeRate"></span></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>CPU当前总使用率</div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card memory-card layui-card-align-center">
                            <div class="layui-card-header">内存信息</div>
                            <div class="layui-card-body">
                                <div class="ant-progress ant-progress-circle ant-progress-show-info ant-progress-default ant-progress-status-normal">
                                    <div class="ant-progress-inner" style="width: 120px; height: 120px; font-size: 24px;">
                                        <svg class="ant-progress-circle" viewBox="0 0 100 100">
                                            <!---->
                                            <path d="M 50,50 m 0,47
                                                a 47,47 0 1 1 0,-94
                                                a 47,47 0 1 1 0,94" 
                                                stroke-linecap="round" stroke-width="6" fill-opacity="0" class="ant-progress-circle-trail" style="stroke-dasharray: 220.31px, 295.31px; stroke-dashoffset: -37.5px; transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity 0.3s;"></path>
                                            <path d="M 50,50 m 0,47
                                            a 47,47 0 1 1 0,-94
                                            a 47,47 0 1 1 0,94" stroke="" stroke-linecap="round" stroke-width="6" opacity="1" fill-opacity="0" class="ant-progress-circle-path" style="stroke-dasharray: 0, 295.31px;stroke-dashoffset: -37.5px;transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity;stroke: rgb(73, 170, 25);"></path>
                                           
                                        </svg>
                                        <span class="ant-progress-text" title="0%">0%</span>
                                    </div>
                                    <div class="ant-tooltip ant-tooltip-placement-top" style="top: -70px;">
                                        <div class="ant-tooltip-content">
                                            <div class="ant-tooltip-arrow"><span class="ant-tooltip-arrow-content"></span></div>
                                            <div class="ant-tooltip-inner" role="tooltip">
                                                <div><span>内存总量：</span><span class="memoryTotal"></span></div>
                                                <div><span>内存已用：</span><span class="memoryUsed"></span></div>
                                                <div><span>内存剩余：</span><span class="memoryFree"></span></div>
                                                <div><span>内存使用率：</span><span class="memoryUseRate"></span>%</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>内存使用率</div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card storage-card layui-card-align-center">
                            <div class="layui-card-header">存储信息</div>
                            <div class="layui-card-body">
                                <div class="ant-progress ant-progress-circle ant-progress-show-info ant-progress-default ant-progress-status-normal">
                                    <div class="ant-progress-inner" style="width: 120px; height: 120px; font-size: 24px;">
                                        <svg class="ant-progress-circle" viewBox="0 0 100 100">
                                            <!---->
                                            <path d="M 50,50 m 0,47
                                                a 47,47 0 1 1 0,-94
                                                a 47,47 0 1 1 0,94" 
                                                stroke-linecap="round" stroke-width="6" fill-opacity="0" class="ant-progress-circle-trail" style="stroke-dasharray: 220.31px, 295.31px; stroke-dashoffset: -37.5px; transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity 0.3s;"></path>
                                            <path d="M 50,50 m 0,47
                                            a 47,47 0 1 1 0,-94
                                            a 47,47 0 1 1 0,94" stroke="" stroke-linecap="round" stroke-width="6" opacity="1" fill-opacity="0" class="ant-progress-circle-path" style="stroke-dasharray: 0, 295.31px;stroke-dashoffset: -37.5px;transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity;stroke: rgb(73, 170, 25);"></path>
                                           
                                        </svg>
                                        <span class="ant-progress-text" title="0%">0%</span>
                                    </div>
                                    <div class="ant-tooltip ant-tooltip-placement-top" style="top: -70px;">
                                        <div class="ant-tooltip-content">
                                            <div class="ant-tooltip-arrow"><span class="ant-tooltip-arrow-content"></span></div>
                                            <div class="ant-tooltip-inner" role="tooltip">
                                                <div><span>存储总量：</span><span class="storageTotal"></span></div>
                                                <div><span>存储已用：</span><span class="storageUsed"></span></div>
                                                <div><span>存储剩余：</span><span class="storageFree"></span></div>
                                                <div><span>存储使用率：</span><span class="storageUseRate"></span>%</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>存储使用率</div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card linkRate-card layui-card-align-center" style="position: relative;">
                            <div class="layui-card-header">网络信息 <button class="layui-btn layui-btn-sm layui-btn-primary updateLinkeRate-btn" title="刷新"><i class="layui-icon layui-icon-refresh"></i></button></div>
                            <div class="layui-card-body">
                                <div class="ant-spin-container" style="padding: 5px;">
                                    <div class="ant-statistic">
                                        <div class="ant-statistic-title" style="margin-bottom: 5px;font-size: 14px;">上行速率</div>
                                        <div class="ant-statistic-content" style="color: rgb(63, 134, 0);font-size: 24px;">
                                            <span class="ant-statistic-content-prefix">
                                                <span role="img" aria-label="arrow-up" class="anticon anticon-arrow-up">
                                                    <svg focusable="false" class="" data-icon="arrow-up" width="1em" height="1em" fill="currentColor" aria-hidden="true" viewBox="64 64 896 896"><path d="M868 545.5L536.1 163a31.96 31.96 0 00-48.3 0L156 545.5a7.97 7.97 0 006 13.2h81c4.6 0 9-2 12.1-5.5L474 300.9V864c0 4.4 3.6 8 8 8h60c4.4 0 8-3.6 8-8V300.9l218.9 252.3c3 3.5 7.4 5.5 12.1 5.5h81c6.8 0 10.5-8 6-13.2z"></path></svg>
                                                </span>
                                            </span>
                                            <span class="ant-statistic-content-value upLinkRate">0 B/S</span>
                                        <!---->
                                        </div>
                                    </div>
                                    <div class="ant-statistic mt-4" style="margin-top: 20px;">
                                        <div class="ant-statistic-title" style="margin-bottom: 5px;font-size: 14px;">下行速率</div>
                                        <div class="ant-statistic-content" style="color: rgb(207, 19, 34);font-size: 24px;">
                                            <span class="ant-statistic-content-prefix"><span role="img" aria-label="arrow-down" class="anticon anticon-arrow-down">
                                                <svg focusable="false" class="" data-icon="arrow-down" width="1em" height="1em" fill="currentColor" aria-hidden="true" viewBox="64 64 896 896"><path d="M862 465.3h-81c-4.6 0-9 2-12.1 5.5L550 723.1V160c0-4.4-3.6-8-8-8h-60c-4.4 0-8 3.6-8 8v563.1L255.1 470.8c-3-3.5-7.4-5.5-12.1-5.5h-81c-6.8 0-10.5 8.1-6 13.2L487.9 861a31.96 31.96 0 0048.3 0L868 478.5c4.5-5.2.8-13.2-6-13.2z"></path></svg></span></span>
                                                <span class="ant-statistic-content-value downLinkRate">0 B/S</span>
                                            <!---->
                                            </div>
                                        </div>
                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md12">
                        <div class="layui-card">
                            <div class="layui-card-header">CPU监控</div>
                            <div class="layui-card-body">
                                <div class="ant-descriptions ant-descriptions-middle ant-descriptions-bordered">
                                    <!---->
                                    <div class="ant-descriptions-view">
                                        <table>
                                            <tbody>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>CPU名称</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="cpuName"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>CPU数量</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="cpuNum"></span>
                                                    </td>
                                                </tr>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>CPU物理核心数</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="cpuPhysicalCoreNum"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>CPU逻辑核心数</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="cpuLogicalCoreNum"></span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md9">
                        <div class="layui-card">
                            <div class="layui-card-header">JVM信息</div>
                            <div class="layui-card-body">
                                <div class="ant-descriptions ant-descriptions-middle ant-descriptions-bordered"><!---->
                                    <div class="ant-descriptions-view">
                                        <table>
                                            <tbody>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>JVM名称</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="jvmName"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>JVM版本</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="jvmVersion"></span>
                                                    </td>
                                                </tr>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>JVM启动时间</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1">
                                                        <!---->
                                                        <span class="jvmStartTime"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>JVM运行时长</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="jvmRunTime"></span>
                                                    </td>
                                                </tr>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>Java版本</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="javaVersion"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>Java安装路径</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1"><!---->
                                                        <span class="javaPath"></span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card jvm-card layui-card-align-center">
                            <div class="layui-card-header">JVM监控</div>
                            <div class="layui-card-body">
                                <div class="ant-progress ant-progress-circle ant-progress-show-info ant-progress-default ant-progress-status-normal">
                                    <div class="ant-progress-inner" style="width: 120px; height: 120px; font-size: 24px;">
                                        <svg class="ant-progress-circle" viewBox="0 0 100 100">
                                            <!---->
                                            <path d="M 50,50 m 0,47
                                                a 47,47 0 1 1 0,-94
                                                a 47,47 0 1 1 0,94" 
                                                stroke-linecap="round" stroke-width="6" fill-opacity="0" class="ant-progress-circle-trail" style="stroke-dasharray: 220.31px, 295.31px; stroke-dashoffset: -37.5px; transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity 0.3s;"></path>
                                            <path d="M 50,50 m 0,47
                                            a 47,47 0 1 1 0,-94
                                            a 47,47 0 1 1 0,94" stroke="" stroke-linecap="round" stroke-width="6" opacity="1" fill-opacity="0" class="ant-progress-circle-path" style="stroke-dasharray: 0, 295.31px;stroke-dashoffset: -37.5px;transition: stroke-dashoffset 0.3s, stroke-dasharray 0.3s, stroke 0.3s, stroke-width 0.06s 0.3s, opacity;stroke: rgb(73, 170, 25);"></path>
                                           
                                        </svg>
                                        <span class="ant-progress-text" title="0%">0%</span>
                                    </div>
                                    <div class="ant-tooltip ant-tooltip-placement-top" style="top: -110px;">
                                        <div class="ant-tooltip-content">
                                            <div class="ant-tooltip-arrow"><span class="ant-tooltip-arrow-content"></span></div>
                                            <div class="ant-tooltip-inner" role="tooltip">
                                                <div><span>JVM总分配内存：</span><span class="jvmMemoryTotal"></span></div>
                                                <div><span>JVM已用内存：</span><span class="jvmMemoryUsed"></span></div>
                                                <div><span>JVM剩余内存：</span><span class="jvmMemoryFree"></span></div>
                                                <div><span>JVM内存使用率：</span><span class="jvmUseRate"></span>%</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>JVM内存使用率</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md12">
                        <div class="layui-card">
                            <div class="layui-card-header">服务器信息</div>
                            <div class="layui-card-body">
                                <div class="ant-descriptions ant-descriptions-middle ant-descriptions-bordered">
                                    <!---->
                                    <div class="ant-descriptions-view">
                                        <table>
                                            <tbody>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>服务器名称</span><!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1">
                                                        <!---->
                                                        <span class="serverName"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>服务器操作系统</span>
                                                    <!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1">
                                                        <!---->
                                                        <span class="serverOs"></span>
                                                    </td>
                                                </tr>
                                                <tr class="ant-descriptions-row">
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>服务器IP</span>
                                                    <!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1">
                                                        <!---->
                                                        <span class="serverIp"></span>
                                                    </td>
                                                    <th class="ant-descriptions-item-label" colspan="1">
                                                        <span>服务器架构</span>
                                                    <!---->
                                                    </th>
                                                    <td class="ant-descriptions-item-content" colspan="1">
                                                        <!---->
                                                        <span class="serverArchitecture"></span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
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
        <div class="page-loading">
			<div class="progress-loader"></div>
		</div>           
    </body>
·
    <script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/source/sysMonitor.js?v=<%=jspUpdateVersion%>"></script>
</html>
