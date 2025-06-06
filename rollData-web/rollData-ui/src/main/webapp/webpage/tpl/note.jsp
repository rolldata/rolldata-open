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
<html lang="en">
<head>
    <title>便签</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/libs/layui/css/layui.css?v=<%=jspUpdateVersion%>" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/module/admin.min.css?v=<%=jspUpdateVersion%>"/>
    <style>
        html, body {
            background-color: #F8F8F8;
        }

        .note-wrapper {
            padding-left: 15px;
            padding-top: 20px;
            margin-bottom: 10px;
        }

        .note-item {
            width: 113px;
            height: 100px;
            display: inline-block;
            margin: 0 6px 15px 0;
            padding: 13px;
            border: 1px solid #dddddd;
            border-radius: 8px;
            background-color: #ffffff;
            position: relative;
            cursor: pointer;
        }

        .note-item:hover {
            background-color: #f1f1f1;
        }

        .note-item .note-item-content {
            font-size: 14px;
            color: #666666;
            height: 78px;
            overflow: hidden;
            word-wrap: break-word;
        }

        .note-item .note-item-time {
            font-size: 12px;
            color: #999999;
            margin-top: 10px;
        }

        .note-empty {
            text-align: center;
            color: rgba(0, 0, 0, .45);
            padding: 73px 0 88px;
            display: none;
        }

        .note-empty .layui-icon {
            margin-bottom: 10px;
            display: inline-block;
            font-size: 60px;
        }

        .note-item-del {
            position: absolute;
            right: 3px;
            top: 3px;
            display: none;
            color: #FF5722;
        }

        .note-item-del.show {
            display: inline-block;
        }

        .note-item-del .layui-icon {
            font-size: 22px;
        }
    </style>
</head>
<body>
    <input type="hidden" name="basePath" id="basePath" value="<%=basePath%>">
<div class="note-wrapper">
</div>
<div class="note-empty">
    <i class="layui-icon layui-icon-face-surprised"></i>
    <div>没有便签</div>
</div>
<div class="btn-circle" id="btnAdd" title="添加便签"><i class="layui-icon layui-icon-add-1"></i></div>

<script type="text/javascript" src="<%=basePath%>static/libs/layui/layui.js?v=<%=jspUpdateVersion%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.min.js?v=<%=jspUpdateVersion%>"></script>
<script>
    var dataList = [];  // 标签列表

    layui.use(['layer', 'form', 'util', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var util = layui.util;
        var admin = layui.admin;

        renderList();  // 渲染列表

        // 添加
        $('#btnAdd').click(function () {
            showNote();
        });

        // 显示编辑弹窗
        function showNote(object) {
            var id, content = '';
            if (object) {
                id = object.id;
                content = object.content;
            }
            top.layui.admin.open({
                id: 'layer-note-item-edt',
                title: '便签',
                type: 1,
                area: '300px',
                offset: '50px',
                shadeClose: true,
                content: '<textarea id="edtNote" placeholder="请输入内容" style="width: 260px;height: 112px;border: none;color: #666666;word-wrap: break-word;padding: 10px 20px;resize: none;">' + content + '</textarea>',
                success: function () {
                    top.layui.jquery('#edtNote').change(function () {
                        content = top.layui.jquery(this).val();
                    });
                },
                end: function () {
                    if (id != undefined) {
                        if (!content) {
                            dataList.splice(id, 1);
                            for (var i = 0; i < dataList.length; i++) {
                                dataList[i].id = i;
                            }
                        } else if (content != dataList[id].content) {
                            dataList[id].content = content;
                            dataList[id].time = util.toDateString(new Date(), 'yyyy/MM/dd HH:mm');
                        }
                    } else if (content) {
                        dataList.push({
                            id: dataList.length,
                            content: content,
                            time: util.toDateString(new Date(), 'yyyy/MM/dd HH:mm')
                        });
                    }
                    putDataList();
                    renderList();
                }
            });
        }

        // 更新缓存
        function putDataList() {
            layui.data(admin.tableName, {
                key: 'notes',
                value: dataList
            });
        }

        // 渲染列表
        function renderList() {
            $('.note-wrapper').empty();
            dataList = layui.data(admin.tableName).notes;
            if (dataList == undefined) {
                dataList = [];
            }
            for (var i = 0; i < dataList.length; i++) {
                var item = dataList[i];
                var str = '<div class="note-item" data-id="' + item.id + '">';
                str += '<div class="note-item-content">' + item.content + '</div>';
                str += '<div class="note-item-time">' + item.time + '</div>';
                str += '<span class="note-item-del"><i class="layui-icon layui-icon-close-fill"></i></span>';
                str += '</div>';
                $('.note-wrapper').prepend(str);
            }

            if (dataList.length == 0) {
                $('.note-empty').css('display', 'block');
            } else {
                $('.note-empty').css('display', 'none');
            }

            // 点击修改
            $('.note-item').click(function () {
                var position = parseInt($(this).attr('data-id'));
                showNote(dataList[position]);
            });

            // 鼠标经过显示删除按钮
            $('.note-item').mouseenter(function () {
                $(this).find('.note-item-del').addClass('show');
            });
            $('.note-item').mouseleave(function () {
                $(this).find('.note-item-del').removeClass('show');
            });

            // 点击删除
            $('.note-item-del').click(function () {
                var position = parseInt($(this).parent().attr('data-id'));
                dataList.splice(position, 1);
                for (var i = 0; i < dataList.length; i++) {
                    dataList[i].id = i;
                }
                putDataList();
                renderList();
            });
        }

    });
</script>
</body>
</html>
