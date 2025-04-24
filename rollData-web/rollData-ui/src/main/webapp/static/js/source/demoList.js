; layui.define(["wdUtil", "admin", "table", 'notice','pscrollbar'], function (exports) {

    // 资源管理
    var $ = layui.jquery
        , setter = layui.setter
        , device = layui.device()
        , myUtil = layui.wdUtil
        , table = layui.table
        , laypage = layui.laypage
        , pscrollbar = layui.pscrollbar
        , tableData = '' // 保存要删除的table项
		, loadTableSize = 50
		, storeTableName = 'voucher'
		, detailTemp = `<div class="table-title" style="position: relative;padding: 15px 15px 0;font-size: 16px;"><div class="title-left" style="display: inline-block;"><span>凭证编号：</span><span class="voucherNumTxt"></span></div><div class="title-right" style="display: inline-block;position: absolute;right: 15px;top: 15px"><span>凭证时间：</span><span class="voucherDateTxt"></span></div></div><div class="table-box"><table class="layui-hide" id="voucherDetailList" lay-filter="voucherDetailList"></table></div>`
        , isSm = $('body').hasClass("layui-layout-sm-body")
        , F = {
            select: function (search) {
                var url = setter.basePath + "businessController/queryBusinessList",
                    ajaxData = {
                        "page":$(".layui-laypage-skip-input")[0] ? $(".layui-laypage-skip-input").val() : "1",//第几页
                        "size":$(".layui-laypage-limits-input")[0] ? $(".layui-laypage-limits-input").val() : loadTableSize//每页条数
                    },
                    callback = function (data) {
                        if (data.obj) {
							initTable(data.obj.result);
                            initPageCount(data)
                        }
                        layui.admin.removeLoading('body', true, true);
                    };
				if(search){
					ajaxData.search = search;
				}
                layui.admin.showLoading('body', 2, '.8')
                myUtil.send(url, ajaxData, callback);
                
            },
            push:function () {  
                var url = setter.basePath+"businessController/updateBusinessState",
				ids = [];
                if(tableData.length){
                    tableData.forEach(function(v, i){
                        ids.push(v.id);
                    });
					myUtil.send(url, {ids: ids},  function(data){
						if(data.success){
							myUtil.msg({type: "success", message: data.msg});
							if($(".layui-laypage-skip-input")[0] && $(".layui-laypage-skip-input").val() > 1 && $("#themeList").next().find(".layui-table-body tr").length == 1){
								$(".layui-laypage-skip-input").val($(".layui-laypage-skip-input").val() - 1);
							}
							F.select();
						}
						layui.admin.removeLoading('body', true, true);
					});
                }else{
					myUtil.msg({type: "success", message: '请选择需要修改的demo信息'});
				}
            }
        };

    function initTable($data) {
		$('#allSearch').off("click").on('click', function(){
			F.select($("#").val().trim() || '')
		});
        if (!$data) $data = [];
        table.render({
            elem: "#themeList",
			id:'themeList',
            cellMinWidth: 80,
            height: isSm ? "full-150" : "full-165",
            cols: [[
                { type: "checkbox" },
                { field: "demoCode", title: "编号", templet:"#type", unresize: true, sort: false, align: "center",
                style:"font-size:12px" },
                { field: "demoName", title: "名称", align: "center" ,unresize: true, sort: false,
                style:"font-size:12px"},
                { field: "state", title: "状态", align: "center" ,unresize: true, sort: false,
					templet: function (d) {
						return d.state === "0" ? "停用" : d.state === "1" ? "启用" : "其他"
					},
                	style:"font-size:12px"},
				{ field: "createTime", title: "操作", align: "center" ,unresize: true, sort: false,
				style:"font-size:12px",
				width: 100,
				templet: '<div><button class="layui-btn layui-btn-xs" lay-event="editReport">编辑</button></div>'},
            ]],
            data: $data,
            limit: $data.length,
            id:"allList",
            skin: "nob",
            even: "true",
        }); 
		table.on('checkbox(themeList)', function(obj){
		    var checkStatus = table.checkStatus('allList');
		    tableData = checkStatus.data
		});
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
		var url = setter.basePath + "businessController/queryBusinessList",
			ajaxData = {
				size: $data.obj.size,
				page: $data.obj.page,
			},
			callback = function(data) {
                if(data.obj){
				    initTable(data.obj.result);
                }
				layui.admin.removeLoading('body', true, true);
			};
		if($("#allSearchInput").val().trim()){
			ajaxData.search = $("#allSearchInput").val().trim();
		}
		layui.admin.showLoading('body', 2, '.8')
		myUtil.send(url, ajaxData, callback);
	};

    function eventTopbar() {
		$(".push-btn").off("click").on('click', function(){
			F.push()
		})
	}

    !function () {
        if (isSm) {
            $("#LAY-theme-body > .layui-fluid").removeClass("layui-height-full-30").addClass("layui-height-full-20");
        } else {
            $("#LAY-theme-body > .layui-fluid").removeClass("layui-height-full-20").addClass("layui-height-full-30");
        }
        $(".treeBoxWrap").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        layui.sessionData("configTree", null);
        eventTopbar();
		F.select()
    }();
    exports("theme", {})
});