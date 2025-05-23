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
<html lang="en" class="bg-white">
<head>
    <title>消息</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
    <style>
        .layui-card-body {
            padding: 0;
        }

        .theme-div {
            padding-left: 15px;
            padding-top: 20px;
            margin-bottom: 10px;
        }

        .btnTheme {
            display: inline-block;
            margin: 0 6px 15px 0;
            padding: 4px;
            border: 1px solid #fff;
        }

        .btnTheme img {
            width: 80px;
            height: 50px;
            border: 1px solid #f2f2f2;
            background: #F2F2F2;
            cursor: pointer;
        }

        .btnTheme:hover, .btnTheme.active {
            border-color: #5FB878;
        }

        .more-menu-item {
            display: block;
            height: 50px;
            line-height: 50px;
            font-size: 16px;
            border-bottom: 1px solid #e8e8e8;
            color: #333;
            padding: 0px 25px;
            font-style: normal;
        }

        .more-menu-item:first-child {
            border-top: 1px solid #e8e8e8;
        }

        .more-menu-item:hover {
            background: #F2F2F2;
            color: #333;
        }

        .more-menu-item .layui-icon {
            padding-right: 10px;
            font-size: 18px;
        }

        .more-menu-item:after {
            content: "\e602";
            font-family: layui-icon !important;
            position: absolute;
            right: 16px;
        }

        .more-menu-item.no-icon:after {
            content: "";
        }

        /** 设置表单样式 */
        .set-item-label {
            display: inline-block;
            height: 38px;
            line-height: 38px;
            padding-left: 20px;
            color: #333333;
        }

        .set-item-ctrl {
            display: inline-block;
            height: 38px;
            line-height: 38px;
        }

        .set-item-ctrl > * {
            margin: 0;
        }
    </style>
</head>
<body>
<div class="layui-card-header">设置主题</div>
<div class="layui-card-body">
    <!-- 主题列表 -->
    <div class="theme-div"></div>

    <!-- 导航 -->
    <div>
        <a class="more-menu-item" href="http://192.168.1.2/web/iframe/" target="_blank">
            <i class="layui-icon layui-icon-read" style="font-size: 19px;"></i> 开发文档
        </a>
        <a class="more-menu-item" href="https://www.layui.com/doc" target="_blank">
            <i class="layui-icon layui-icon-read" style="font-size: 19px;"></i> layui开发文档
        </a>
        <a class="more-menu-item" href="https://www.layui.com/demo/" target="_blank">
            <i class="layui-icon layui-icon-read" style="font-size: 19px;"></i> layui示例
        </a>
        <a class="more-menu-item" href="https://layuiextend.hsianglee.cn/eletree/" target="_blank">
            <i class="layui-icon layui-icon-read" style="font-size: 19px;"></i> eleTree开发文档
        </a>
        <a class="more-menu-item layui-hide" href="https://demo.easyweb.vip/spa" target="_blank">
            <i class="layui-icon layui-icon-tabs" style="font-size: 16px;"></i> &nbsp;spa版本
        </a>
        <a class="more-menu-item" href="https://demo.easyweb.vip/theme" target="_blank">
            <i class="layui-icon layui-icon-theme"></i> 主题生成器
        </a>
    </div>

    <!-- 控制开关 -->
    <div class="layui-form" style="margin: 25px 0;">
        <div class="layui-form-item">
            <label class="set-item-label">页脚：</label>
            <div class="set-item-ctrl" style="margin-right: 15px;">
                <input id="setFooter" lay-filter="setFooter" type="checkbox" lay-skin="switch" lay-text="开启|关闭">
            </div>
            <label class="set-item-label" style="width: auto;">Tab记忆：</label>
            <div class="set-item-ctrl">
                <input id="setTab" lay-filter="setTab" type="checkbox" lay-skin="switch" lay-text="开启|关闭">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="set-item-label" style="width: auto;">多标签：</label>
            <div class="set-item-ctrl">
                <input id="setMoreTab" lay-filter="setMoreTab" type="checkbox" lay-skin="switch" lay-text="开启|关闭">
            </div>
            <label class="set-item-label">切换刷新：</label>
            <div class="set-item-ctrl">
                <input id="setRefresh" lay-filter="setRefresh" type="checkbox" lay-skin="switch" lay-text="开启|关闭">
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script>
    layui.use(['layer', 'form', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var admin = layui.admin;

        var themes = [
            {title: '黑白主题', theme: 'admin'},
            {title: '黑色主题', theme: 'black'},
            {title: '蓝色主题', theme: 'blue'},
            {title: '藏青主题', theme: 'cyan'},
            {title: '黄色主题', theme: 'yellow'},
            {title: '绿色主题', theme: 'green'},
            {title: '粉红主题', theme: 'pink'},
            {title: '紫白主题', theme: 'purple-white'},
            {title: '紫色主题', theme: 'purple'},
            {title: '白色主题', theme: 'white'},
            {title: '红白主题', theme: 'red-white'},
            {title: '红色主题', theme: 'red'}
        ];
        for (var i = 0; i < themes.length; i++) {
            var str = '<div class="btnTheme" theme="theme-' + themes[i].theme + '" title="' + themes[i].title + '">';
            str += '      <img src="<%=basePath%>static/module/theme/img/theme-' + themes[i].theme + '.png">';
            str += '   </div>';
            $('.theme-div').append(str)
        }

        // 切换主题
        var mTheme = layui.data(admin.tableName).theme;
        $('.btnTheme[theme=' + (mTheme ? mTheme : admin.defaultTheme) + ']').addClass('active');
        $('.btnTheme').click(function () {
            $('.btnTheme').removeClass('active');
            $(this).addClass('active');
            admin.changeTheme($(this).attr('theme'));
        });

        // 关闭/开启页脚
        var openFooter = layui.data(admin.tableName).openFooter;
        $('#setFooter').prop('checked', openFooter == undefined ? true : openFooter);
        form.on('switch(setFooter)', function (data) {
            var checked = data.elem.checked;
            layui.data(admin.tableName, {key: 'openFooter', value: checked});
            checked ? top.layui.jquery('body.layui-layout-body').removeClass('close-footer') : top.layui.jquery('body.layui-layout-body').addClass('close-footer');
        });

        // 关闭/开启Tab记忆功能
        $('#setTab').prop('checked', top.layui.index.cacheTab);
        form.on('switch(setTab)', function (data) {
            top.layui.index.setTabCache(data.elem.checked);
        });

        // 切换Tab自动刷新
        var tabAutoRefresh = layui.data(admin.tableName).tabAutoRefresh;
        $('#setRefresh').prop('checked', tabAutoRefresh == undefined ? false : tabAutoRefresh);
        form.on('switch(setRefresh)', function (data) {
            var checked = data.elem.checked;
            layui.data(admin.tableName, {key: 'tabAutoRefresh', value: checked});
            checked ? top.layui.jquery('.layui-body>.layui-tab[lay-filter="admin-pagetabs"]').attr('lay-autoRefresh', 'true') : top.layui.jquery('.layui-body>.layui-tab[lay-filter="admin-pagetabs"]').removeAttr('lay-autoRefresh');
        });

        // 关闭/开启多标签
        var openTab = layui.data(admin.tableName).openTab;
        $('#setMoreTab').prop('checked', openTab == undefined ? top.layui.index.pageTabs : openTab);
        form.on('switch(setMoreTab)', function (data) {
            var checked = data.elem.checked;
            layui.data(admin.tableName, {key: 'openTab', value: checked});
            admin.putTempData('indexTabs', undefined);  // 清除缓存的Tab
            top.location.reload();
        });

        form.render('checkbox');
    });
</script>
</body>
</html>
