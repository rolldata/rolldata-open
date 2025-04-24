; layui.define(["wdUtil", 'admin', "table", "form",'setter', 'jquery', 'pscrollbar'], function (exports) {
    var $ = layui.jquery
        , laytpl = layui.laytpl
        , element = layui.element
        , setter = layui.setter
        , device = layui.device()
        , form = layui.form
        , myUtil = layui.wdUtil
        , table = layui.table
        , laypage = layui.laypage
        , pscrollbar = layui.pscrollbar
		, loadTableSize = 50
        , isSm = $('body').hasClass("layui-layout-sm-body"),
        create = '<div>'
                    +'<form  class="layui-form" action="" method="post" enctype="multipart/form-data" lay-filter="createItem-form" autocomplete="off" id="announcementTable">'
                        +'<input type="hidden" id="annId" name="annId" style="opacity:0"/>'
                        +'<div class = "layui-form-item layui-form-item-sm">'
                            +'<label for="title" class="layui-form-label" style="width:95px;"><i style="color: red;margin-right: 5px;">*</i>公告标题：</label>'
                            +'<div class="layui-input-inline">'
                                +'<input type="text" name="title" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入公告标题" autocomplete="off" id="title">'
                            +'</div>'
                        +'</div>'
                        +'<div class = "layui-form-item layui-form-item-sm">'
                            +'<label for="content" class="layui-form-label" style="width:95px;"><i style="color: red;margin-right: 5px;">*</i>公告内容：</label>'
                            +'<div class="layui-input-block" style="margin-left:95px;">'
                                +'<textarea name="content" id="content" required lay-verify="required" lay-verType="tips" placeholder="请在这里输入公告内容" class="layui-textarea" style="min-height: 200px;resize:none;"></textarea>'
                            +'</div>'
                        +'</div>'
                        +'<div class = "layui-form-item layui-form-item-sm">'
                            +'<label class="layui-form-label" style="width:95px;">添加附件：</label>'
                            +'<div class="layui-input-inline">'
                                +'<div class="layui-btn layui-btn-cardTop layui-btn-sm uploadFile-btn uploadPhotoBtn"><i class="iconfont icon-shangchuan"></i>上传</div>'
                                +'<div class="layui-btn layui-btn-cardTop layui-btn-sm uploadFile-btn reUploadPhotoBtn layui-hide"><i class="iconfont icon-gf-fileEmpty"></i><span class="fileName layui-elip"></span><div class="iconfont icon-guanbi2fill clearPhotoBtn"></div></div>'
                                +'<input type="file" name="uploadFile" id="uploadFile" class="layui-input" style="opacity:0;width:0px;padding:0" value="">'
                                +'<input type="text" name="isReUpload" id="isReUpload" class="layui-input layui-hide" value="0">'
                                +'<input type="text" name="isFile" id="isFile" class="layui-input layui-hide" value="0">'
                            +'</div>'
                        +'</div>'
                        +'<button id="editUser-submit" class="layui-hide" lay-submit lay-filter="editUser">编辑</button>'
                    +'</form>'
                +'</div>'

    // 初始化列表
    function initTable($data) {
        if(!$data) $data = []
        $data.forEach(function(v,i){
            if(v.isFile == "0"){
                v.isFileName = "否"
            }else if(v.isFile == "1"){
                v.isFileName = "是"
            }
        })
        table.render({
            elem: "#announcementList",
            cellMinWidth: 80,
            height: isSm ? "full-150" : "full-165",
            cols: [[
                { type: "checkbox" },
                { field: "index", title: "序号", align: "center", type: "numbers", width: 80 ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "title", title: "名称", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "content", title: "内容", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "isFileName", title: "是否有附件", templet:"#type", unresize: true, sort: false, align: "center",style:"font-size:12px" },
                { field: "fileName", title: "附件名", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "createUser", title: "创建人", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "createTime", title: "创建时间", align: "center" ,unresize: true, sort: false,style:"font-size:12px"}
            ]],
            data: $data,
            skin: "nob",
            even: "true",
            limit: $data.length,
            id:"announcementList"
        });
        $("#allSearch").data("list",$data)
        $(".layui-table-body.layui-table-main").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
            }
        });
    }
    // 分页
    function initPageCount($data) {
        if (!$data.success) {
            laypage.render({
                elem: 'pageCount',
                count: 0,
                limits: [loadTableSize],limitType: 'auto',
				limit: loadTableSize,
                layout: ['limit', 'skip' , 'prev', 'page', 'next'],
                prev: "<",
                next: ">",
                jump: function(obj, first) {
                    $(".pageCount").find(".pageCountText").text("显示 第 0 到 0 条 共 0 页 共 0 条");
                }
            });
            return
        }
        laypage.render({
            elem: 'pageCount',
            count: $data.obj.totalElements,
            curr: $data.obj.page,
            limits: [$data.obj.size || loadTableSize],limitType: 'auto',
            limit: $data.obj.size || loadTableSize,
            layout: ['limit', 'skip' , 'prev', 'page', 'next'],
            prev: "<",
            next: ">",
            jump: function(obj, first) {
                if (!first) {
                    $data.obj.page = obj.curr;
                    $data.obj.size = obj.limit;
                    updateList($data)
                };
                if($data.obj.totalElements/$data.obj.size <= 0){
                    $data.obj.totalPagets = 1
                }else{
                    $data.obj.totalPagets = parseInt($data.obj.totalElements/$data.obj.size)+1
                }
                $(".pageCount").find(".pageCountText").text("显示 第 " + (($data.obj.size * ($data.obj.page - 0 - 1)) < 0 ? 0 :$data.obj.size * ($data.obj.page - 0 - 1) + 1) + " 到 " + $data.obj.size * $data.obj.page + " 条 共 " + $data.obj.totalPagets + " 页 共 " + $data.obj.totalElements + " 条");
            }
        });
    };
    
    function updateList($data) {
        var url = setter.basePath + "announcementController/queryAnnouncementListByPage",
            ajaxData = {
                size: $data.obj.size,
                page: $data.obj.page,
                search: $("#allSearchInput").val().trim()
            },
            callback = function(data) {
                if(data.obj){
                    initTable(data.obj.result);
                }
                layui.admin.removeLoading('body', true, true);
            };
            layui.admin.showLoading('body', 2, '.8');
        myUtil.send(url, ajaxData, callback);
    };
    // 查询公告管理列表按时间倒序
    function queryAnnouncementList(){
        var url = setter.basePath + "announcementController/queryAnnouncementListByPage",
            ajaxData = {
                "search":$("#allSearchInput").val().trim(),
                "page":"1",//第几页
                "size":$(".layui-laypage-limits-input")[0] ? $(".layui-laypage-limits-input").val() : loadTableSize//每页条数
            },
            callback = function (data){
                if(data){
                    initTable(data.obj.result)
                    initPageCount(data)

                }
                layui.admin.removeLoading('body', true, true);
            }
        layui.admin.showLoading('body', 2, '.8');
        myUtil.send(url,ajaxData,callback)
            
    }
    function eventBar(){
        // 点击查询按钮
        $("#allSearch").off("click").on("click",function(){
            var value = $("#allSearchInput").val().trim(),
                 url = setter.basePath + "announcementController/queryAnnouncementListByPage",
                ajaxData = {
                    size: $(".layui-laypage-limits-input")[0] ? $(".layui-laypage-limits-input").val() : loadTableSize,
                    page: "1",
                    search: value
                },
                callback = function(data) {
                    if(data){
                        initTable(data.obj.result);
                        initPageCount(data)
                    }
                    layui.admin.removeLoading('body', true, true);
                };
            layui.admin.showLoading('body', 2, '.8')
            myUtil.send(url, ajaxData, callback);

        })
        // 点击新建按钮
        $("#btn-save-theme").off("click").on("click",function(){
            renderAnnouncementInfo()
        })
        
        // 点击编辑按钮
        $("#btn-edit-theme").off("click").on("click",function(){
            var tableData = table.checkStatus("announcementList");
            if(tableData && tableData.data && tableData.data[0]){
                renderAnnouncementInfo(tableData.data[0]);
            }else{
                myUtil.msg({type:"warning",message:"请选择一项进行编辑"})
            }
        })
        
        // 点击删除按钮
        $("#btn-del-theme").off("click").on("click",function(){
            var tableData = table.checkStatus("announcementList")
            var ids = [],names = []
            tableData.data.forEach(function(v,i){
                ids.push(v.id)
                names.push(v.title)
            });
            if(!tableData.data.length){
                myUtil.msg({type:"warning",message:"请选择要删除的项"})
                return false
            }else{
                layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+names+'</span>吗？',{icon: 3, title:'删除公告',skin: "layui-layer-admin"},
                    function(index, layero){
                        var url = setter.basePath + "announcementController/deleteAnnouncements",
                            ajaxData = {
                                "ids":""
                            },
                            callback = function(data){
                                if(data){
                                    queryAnnouncementList()
                                    myUtil.msg({type:"success",message:data.msg})
                                    layer.close(index)
                                }
                                layui.admin.removeLoading('body', true, true);
			                    layui.admin.removeLoading('', true, true);
                            }
                        ajaxData.ids = ids
                        layui.admin.showLoading('body', 2, '.8');
                        myUtil.send(url, ajaxData, callback);
                        // layui.admin.removeLoading('body', true, true);
                    });
            }
        })
    
    }
    function renderAnnouncementInfo(obj){
        var info = obj || {
            title: "",
            content: ""
        };
        layui.admin.open({
            title: obj ? "编辑公告" : "新建公告",
            content: create,
            area: ['690px'],
            btn: ['确定', '取消'],
            success: function (layero, index) {
                form.val("createItem-form", {
                    "title": info.title,
                    "content": info.content,
                    "annId": info.id,
                    "isFile" : info.fileName ? "1" : "0",
                    "isReUpload" : info.fileName ? "1" : "0"
                })
                form.render()
                if(info.fileName){
                    $(".reUploadPhotoBtn").removeClass("layui-hide");
                    $(".uploadPhotoBtn").addClass("layui-hide");
                    $(".reUploadPhotoBtn .fileName").attr('title', info.fileName).text(info.fileName);
                }
                form.on('submit(editUser)', function(data){
                    var form = $("#announcementTable")[0];
                    var formData = new FormData(form);
                    layui.admin.showLoading('body', 2, '.8');
                    $.ajax({
                        url: setter.basePath+ (obj ? "announcementController/updateAnnouncementInfo" :"announcementController/saveAnnouncementInfo"),  
                        method: 'POST',  
                        data: formData,   
                        contentType: false,  
                        processData: false,  
                        success: function (data) {
                            if(data.success){
                                queryAnnouncementList()
                                myUtil.msg({type:"success",message:data.msg})
                                layer.close(index)
                            }else{
                                myUtil.msg({type:"error",message:data.msg})
                            }
                            layui.admin.removeLoading('body', true, true);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log(XMLHttpRequest.readyState);
                            layui.admin.removeLoading('body', true, true);
                        }
                    });
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                });
                $(".reUploadPhotoBtn, .uploadPhotoBtn").off("click").on("click", function(e){
                    e.stopPropagation();
                    $("#uploadFile").trigger("click");
                })
                $("#uploadFile").off("change").on("change", function() {
					$("#isReUpload").val("1");
					$("#isFile").val("1");
                    $(".reUploadPhotoBtn .fileName").attr('title', this.value).text(this.value);
                    $(".reUploadPhotoBtn").removeClass("layui-hide");
                    $(".uploadPhotoBtn").addClass("layui-hide");
				});
                $(".clearPhotoBtn").off("click").on("click", function(e){
                    e.stopPropagation();
                    $(".isReUpload").val("0");
                    $(".isFile").val("0");
                    $("#uploadFile").val("");
                    $(".reUploadPhotoBtn").addClass("layui-hide");
                    $(".uploadPhotoBtn").removeClass("layui-hide");
                })
            },
            yes: function (index, layero) {
                $("#editUser-submit").trigger("click");
            }
        })
    }

    ! function() {
        queryAnnouncementList()
        eventBar()
    }();
    exports("announcementManage", {})
})