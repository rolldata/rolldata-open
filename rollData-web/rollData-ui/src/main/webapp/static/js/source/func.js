; layui.define(["wdUtil", 'admin', "table", "form", "eleTree", 'contextMenu', 'notice','pscrollbar'], function (exports) {
    var $ = layui.jquery
      , laytpl = layui.laytpl
      , element = layui.element
      , setter = layui.setter
      , device = layui.device()
      , form = layui.form
      , myUtil = layui.wdUtil
      , eleTree = layui.eleTree
      , table = layui.table
      , treeId = "LAY-func-tree"
      , pscrollbar = layui.pscrollbar
	  , cxt = layui.contextMenu
      , idindex = 7
      , treeDatas = ''
	  , storeTableName = 'funcStore'
      , themeId = []
      , themeVal = []
      , keys = []
      , themeId1 = []
      , themeVal1 = []
      , keys1 = []
      , firstRenderTreeState = false
      , renderedTrees = 0 
      
      , treeselect = ''
      , selectData = ""
      , allTreeData = []
      , createFloderTemp = '<form action="" class="layui-form" id="LAY-createFunc-form" lay-filter="createFunc-form" autocomplete="off" style="padding:20px 30px;">'
            +' <div class="layui-form-item layui-form-item-sm">'
                +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>目录名称：</label>'
                +' <div class="layui-input-block">'
                    +' <input type="text" name="funcName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入目录名称" autocomplete="off">'
                +' </div>'
            +' </div>'
            +' <div class="layui-form-item layui-form-item-sm">'
                +' <label for="" class="layui-form-label">资源选择：</label>'
                +' <div class="layui-input-block">'
                    + '<input type="text" name="resourceName"  lay-verType="tips"  placeholder="请选择资源" readonly="" autocomplete="off" class="layui-input" id="selectResource" style= "cursor: pointer;">'
                +' </div>'
            +' </div>'
			+ ' <div class="layui-form-item  layui-form-item-sm">'
			    + ' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>终端展示：</label>'
			        + ' <div class="layui-input-block">'
			            + ' <div class="layui-input-inline" style="width:50px;"><input type="checkbox" name="terminalPC" title="PC" lay-skin="primary" checked ></div>'
			            + ' <div class="layui-input-inline" style="margin-left: 10px;width:55px;"><input type="checkbox" name="terminalIpad" title="平板" lay-skin="primary" checked ></div>'
			            + ' <div class="layui-input-inline" style="margin-left: 10px;width:55px;"><input type="checkbox" name="terminalMobile" title="手机" lay-skin="primary" checked ></div>'
			        + ' </div>'
			+ ' </div>'
			+ ' <div class="layui-form-item  layui-form-item-sm">'
			    + ' <label for="" class="layui-form-label">业务处理：</label>'
			        + ' <div class="layui-input-block">'
			            + ' <div class="layui-input-inline" style="margin-top:-4px;width:50px;"><input type="checkbox" name="businessType" lay-skin="switch" lay-filter="businessType" lay-text="是|否" ></div>'
			        + ' </div>'
			+ ' </div>'
            // +' <div class="layui-form-item layui-form-item-sm">' 
			// 		+' <label for="" class="layui-form-label">父级目录：</label>' 
			// 		+' <div class="layui-input-block">' 
			// 		+' <div class="layui-model-treeinput parent-input" title="" data-id=""><span class="layui-model-node"></span><i class="layui-icon layui-icon-right"></i></div>' 
			// 		+' </div>' 
			// +' </div>'
            +'<button id="createFunc" class="layui-hide" lay-submit lay-filter="createFunc">提交</button>'
            +'<button id="editFunc" class="layui-hide" lay-submit lay-filter="editFunc">编辑</button>'
        +' </form>'
		+'<div class="searchTreeWrap layui-hide" style=" position: absolute; width:230px; top:120px;  left:125px;z-index: 1;">'
			+'<input type="text" placeholder="请输入关键字进行搜索" class="layui-input selecteleTree-search" style="position:absolute;top:-37px;z-index:999;left:0px">'
			+'<div class="tree-box2" style="border:1px solid #eee;height:250px;overflow:hidden;overflow-y:auto;z-index:9999;background:#fff;top:5px;left:0px;width:99%;">'
				+'<div class="eleTree selectResourseTree layui-nav-treebox" lay-filter="selectResourseTree" style="top:0;height: 250px;"></div>'
			+'</div>'
			+'<input type="text" placeholder="请输入关键字进行搜索" class="layui-input selecteleTree-search1" style="position:absolute;top:-37px;z-index:999;left:229px">'
			+'<div class="tree-table2" style="border:1px solid #eee;height:250px;overflow:hidden;overflow-y:auto;z-index:9999;background:#fff;position: absolute; top:0px;left:229px;width:99%;">'
				+'<div class="eleTree selectResourseTree1 layui-nav-treebox" lay-filter="selectResourseTree1" style="top:0;height: 250px;"></div>'
			+'</div>'
		+'</div>'
      , isSm =  $('body').hasClass("layui-layout-sm-body")
      , F = {
			/**
			 * 渲染对话框树ztree模板
			 * @method renderDialogzTree
			 * @for TaskApplyManage
			 * @param id 用于input，tree,tree-search元素id主要参数
			 * @param left 弹出树对话框坐标位置
			 * @param top 弹出树对话框坐标位置
			 * @param url 树数据路径
			 * @param ajaxData 请求参数
			 * @param nodeId 树节点选中id
			 * @param callback 节点触发回调方法
			 * @param key 回调树数据key名称
			 * @param treeData 已有树数据集合包含treeNodes 以及是否勾选框
			 * @param removeBefore 右键弹框删除回调
			 * @param datakeys 树数据对象显示name字段名之类属性配置
			 * @param noeach 不缓存获取最新数据
			 */
			renderDialogzTree: function renderDialogzTree(id, left, top, url, ajaxData, nodeId, callback, key, treeData, removeBefore, datakeys, noeach){
				var isSelectCheckbox = (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') ? true: false,
				treeTemp =  '<div class="dialog-tree-box">'+
				'<div class="xm-select-tips '+(isSelectCheckbox ? '': 'layui-hide')+'"><div class="xm-cz-group"><div class="xm-cz close-cxt" data-type="close" title="完成"><span>完成</span></div><div class="xm-cz" data-type="all" title="全选"><i class="iconfont icon-select-all"></i><span>全选</span></div><div class="xm-cz" data-type="none" title="清空"><i class="iconfont icon-quxiaoquanxuan"></i><span>清空</span></div></div></div>'+
				'<div class="search-box layui-form-item-sm search-fillet-box"><input id="LAY-'+id+'-tree-search" class="layui-input" placeholder="请输入内容搜索" autocomplete="off"><i id="LAY-'+id+'-tree-search-btn" class="iconfont icon-tongyongchaxun"></i></div><div class="ztree-box treebox-scroll" style="'+(isSelectCheckbox ? 'top:70px':'top:40px')+'"><ul class="lay-ztree ztree" id="LAY-'+id+'-tree" lay-filter="lay-'+id+'-tree"></ul></div></div>',
				treeStore = layui.sessionData(storeTableName)[id],
				ajaxData = ajaxData || {data: 'false'},
				date = new Date().getTime();
				cxt.show(treeTemp, left, top, true, removeBefore);
				layui.admin.showLoading('.dialog-tree-box', 2, '.8');
				var url = setter.basePath + url,
				box = $(".dialog-tree-box .ztree-box"),
				eventTree = function(treeDatas){
					var treeEl = box.find('#LAY-'+id+'-tree'),
					ztreeobj = {
						view: {
							selectedMulti: false,
							showLine: false,
							expandSpeed: '',
                            includeNocheck: true,
							addDiyDom: function(treeId, treeNode){
								var spaceWidth = 15;
								var switchObj = $("#" + treeNode.tId + "_switch"),
								checkObj = $("#" + treeNode.tId + "_check"),
								icoObj = $("#" + treeNode.tId + "_ico");
								switchObj.remove();
								if(checkObj[0]){
									icoObj.before(checkObj);
									checkObj.before(switchObj);
								}else{
									icoObj.before(switchObj);
								}
								
								if (treeNode.level >= 1) {
									var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
									switchObj.before(spaceStr);
								}
							}
						},
						data: datakeys || {},
						callback: {
						    onClick: function (event, treeId, treeNode) {
						        if (callback) callback('click', treeId, treeNode)
						    },
						    onBeforeFuzzy: function () {
						        layui.admin.showLoading('#LAY-' + id + '-tree', 2, '.8');
						    },
						    onAfterFuzzy: function () {
						        var ele = $('#LAY-' + id + '-tree')
								ele[0].scrollTop = 0;
								var psc = ele.parent().data('psc');
								if(psc) psc.update();
						        layui.admin.removeLoading('#LAY-' + id + '-tree', true, true);
						    },
						}
					};
					if(treeData && treeData.checkbox && (treeData.checkbox + "") === 'true'){
						ztreeobj.check = {enable: true, chkStyle: 'checkbox'};
						ztreeobj.callback['onCheck'] = function(event, treeId, treeNode){
							if(callback) callback('check', treeId, treeNode);
						}
					}
					treeEl.off('click').on('click', function(e){
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
					})
					if(treeData && (treeData.treeIds|| treeData.icons)){
						var last = treeData.selectlast + '' === 'true' ? true: false;
						eachCheckNode(treeDatas, treeData.treeIds|| [], treeData.icons, last);
					}
					
					var treeObj = jQuery.fn.zTree.init($('#LAY-'+id+'-tree'), ztreeobj, treeDatas);
					$("#LAY-" + id+'-tree-search').parent().off("click").on("click", function(e){
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
					})
					
					$('.xm-select-tips').off("click").on("click", function(e){
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
					})
					$('.xm-select-tips .xm-cz').off("click").on("click", function(e){
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
						var el = $(this),
						type = el.attr('data-type');
						if(type == 'all'){
							treeObj.checkAllNodes(true);
						}else if(type == 'none'){
							treeObj.checkAllNodes(false);
						}else{
							removeBefore();
							$(".ctxMenu").remove();
							return;
						}
						if(callback) callback('check', '', '');
					})
					fuzzySearch ( 'LAY-'+id+'-tree', "#LAY-" + id+'-tree-search', false, true );
					if(box && !box.hasClass("ps")){
						var psc = new pscrollbar(box[0]);
						box.addClass("treebox-scroll").data('psc', psc);
						box.find('.ps__rail-x').on('click', function(e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						});
						box.find('.ps__rail-y').on('click', function(e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						});
					}
					if(nodeId){
						treeObj.selectNode(treeObj.getNodeByParam('id', nodeId, null));
					}
					layui.admin.removeLoading('.dialog-tree-box', true, true);
				};
				if(treeData && treeData.treeNodes){
					eventTree(treeData.treeNodes);
				}else if(treeStore &&  (date - parseInt(treeStore.time)) < 1800000 && !noeach){
					eventTree(treeStore.treeDatas);
				}else{
					myUtil.send(url, ajaxData, function(data){
						if (data) {
							var treeNodes = key ? (data.obj[key]  || data.obj): data.obj.treeNodes;
							layui.sessionData(storeTableName, {key: id, value: {treeDatas: treeNodes, time: new Date().getTime()}});
							if(treeData.openFirst && treeNodes.length){
								treeNodes.forEach(function(v, i){
									v.open = true;
								})
							}
							eventTree(treeNodes);
						}
					});
				}
				
				function eachCheckNode(nodes, ids, icons, last){
					if(nodes.length){
						nodes.forEach(function(v, i){
							if(ids && ids.length && ids.indexOf(v.id) > -1){
								v.checked = 'true';
							}else{
								delete v.checked;
							}
							if(v.children && v.children.length){
								if(icons && icons[v.type]){
									v.iconSkin = icons[v.type]
								}
								if(last){
									v.nocheck = true;
								} else {
									delete v.nocheck;
								}
								eachCheckNode(v.children, ids, icons, last);
							}else{
								if(icons && icons[v.type]){
									v.iconSkin = icons[v.type]
								}else{
									v.iconSkin = 'iconfont icon-iconfontfile2 file';
								}
							}
						})
					}
				}
			},
			createFloder: function(formDatas,index){
                var node = myUtil.getSelectedNode(treeId),
                    url = setter.basePath + "functionController/saveMenuInfo",
                    ajaxData = {
                        "parentId":node.id,
                        "name": formDatas.field.funcName,
						"terminalPC": formDatas.field.terminalPC && formDatas.field.terminalPC == "on" ? '1' : '0',
						"terminalIpad": formDatas.field.terminalIpad && formDatas.field.terminalIpad == "on" ? '1' : '0',
						"terminalMobile": formDatas.field.terminalMobile && formDatas.field.terminalMobile == "on" ? '1' : '0',
						"businessType": formDatas.field.businessType ? '1' : '0',
                        "resourceIds":themeId1
                    },
                    callback = function(data){
                        if(data.success){
                            layer.close(index)
                            var newNode = myUtil.cloneObj(data.obj);
                            newNode.children = [];
                            newNode.type = "1";
                            delete newNode.resourceIds;
                            if(newNode.parentId && newNode.parentId != "0"){
                                setter.eleTreeObj.append(newNode.parentId, newNode);
                                $("#" + treeId + ' .eleTree-node[data-id="' +newNode.id + '"]').children(".eleTree-node-content").trigger("click");
                            }else{
                                var nodes =  setter.eleTreeObj.getData();
                                if(nodes.length){
                                    setter.eleTreeObj.insertAfter(nodes[nodes.length-1].id, newNode);
                                    $("#" + treeId + ' .eleTree-node[data-id="' +newNode.id + '"]').children(".eleTree-node-content").trigger("click");
                                }else{
                                    renderTree();
                                }
                            }
                            $(".IndexDirectory").removeClass("activeList")
                            myUtil.msg({type:"success",message:data.msg})
                        }
                        layui.admin.removeLoading('body', true, true);
                    }

                if(!node.id){
                    ajaxData.parentId = "0"
                }
                layui.admin.showLoading('body', 2, '.8');
                myUtil.send(url,ajaxData,callback)
                return  false;
            }, 
            
            select: function(node){
                var url = setter.basePath + "functionController/queryMenuInfo",
                ajaxData = {"id": node.id},
                index = 0,
                callback = function(data){
                    if(data){
                        selectData = data.obj
                        themeId = []
                        keys = []
                        themeVal = []
                        themeId1 = []
                        keys1 = []
                        themeVal1 = []
                        selectData.resources.forEach(function(v,i){
                            themeId.push(v.id)
                            keys1.push(v.id)
                            themeVal1.push(v.resourceName)
                        })
                        table.render({
                            elem: "#funcList",
                            cellMinWidth: 80,
                            // height: 'full-200',
                            height: isSm ? "full-100" : "full-115",
                            cols: [[
                                {field:"resourceUrl", title:"主题路径",align: "center",unresize: true, sort: false, style:"font-size:12px"},
                                {field: "resourceName", title: "已选主题名称",align: "center",unresize: true, sort: false, style:"font-size:12px"}
                            ]],
                            data: data.obj.resources,
                            limit:data.obj.resources.length,
                            skin: "nob",
                            even: "true",
                        });
                        $(".layui-table-body.layui-table-main").each(function(i, v){
                            if(!$(v).hasClass("ps")){
                                new pscrollbar(v);
                            }
                        });
                        // layui.admin.removeLoading('', true, true);
                    }
                    layui.admin.removeLoading('body', true, true);
                    // layui.admin.removeLoading('', true, true);
                };
                layui.admin.showLoading('body', 2, '.8');
                myUtil.send(url, ajaxData, callback);
            },
            editFloder:function(node,formDatas,index){
                var ids = themeId || [],
                url = setter.basePath + "functionController/updateMenuInfo",
                    ajaxData = {
                        "id":node.id,
                        "name":formDatas.field.funcName,
						"terminalPC": formDatas.field.terminalPC && formDatas.field.terminalPC == "on" ? '1' : '0',
						"terminalIpad": formDatas.field.terminalIpad && formDatas.field.terminalIpad == "on" ? '1' : '0',
						"terminalMobile": formDatas.field.terminalMobile && formDatas.field.terminalMobile == "on" ? '1' : '0',
						"businessType": formDatas.field.businessType ? '1' : '0',
                        "resourceIds":ids
                    },
                    callback = function (data) {  
                        if(data){
                            F.select(node)
                            myUtil.msg({type:"success",message:data.msg})
                            layer.close(index)
                        }
                        layui.admin.removeLoading('body', true, true);
                    }
                layui.admin.showLoading('body', 2, '.8');
                myUtil.send(url,ajaxData,callback)

            }
      };
    

    /**
     * 左侧菜单树推拉方法初始化
     * @method navbarPushPull
     * @for UserManage
     */
    function navbarPushPull(){
        $(".layui-navbar-collapse").off("click").on("click", function(){
            $(".wrapper").toggleClass("show-layui-navbar");
        });
    }

    /**
     * 绑定树操作
     * @method navbarPushPull
     * @for UserManage
     */
    function eventTreeBar(){

        $("#LAY-tree-add").off("click").on("click", function(){
            layui.admin.open({
                type: 1,
                title: "新建目录结构",
                id: "LAY-admin-createFunc",
                skin: 'layui-layer-admin',
                content: createFloderTemp,
                zIndex: layer.zIndex,
                // area: ["400px", "280px"],
                width:400,
                btn: ['确定', '取消'],
                success: function(layero, index){
                    layer.setTop(layero); 
                    keys = []
                    // form.render();
                    themeId1 = [];
                    selectTree([])
					form.render(null, 'createFunc-form');
                    form.on('submit(createFunc)', function(data){
                        F.createFloder(data,index)
                        return false;
                      });
                },
                yes: function(index, layero){
                    $("#createFunc").trigger("click");
                },
                btn2: function(index, layero){
                    layui.admin.removeLoading('body', true, true);
                }
            });
        });
        $("#LAY-tree-remove").off("click").on("click", function(){
            var node = myUtil.getSelectedNode(treeId);
            if(node.id && node.id != "0"){
                layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+node.name+'</span>吗？',{icon: 3, title:'删除目录', skin: 'layui-layer-admin'},
                    function(index, layero){
                        var url = setter.basePath + "functionController/deleteMenuInfo",
                        ajaxdata = {"id":node.id},
                        callback = function(data){
                            if(data){
                                myUtil.msg({type:"success", message: data.msg});
                                var next = "";
                                if(node.parent.next()[0]){
                                    next = node.parent.next();
                                }else if(node.parent.prev()[0]){
                                    next = node.parent.prev();
                                }else{
                                    next = node.parent.parent().parent();
                                }
                                setter.eleTreeObj.remove(node.id);
                                
                                next.children(".eleTree-node-content").trigger("click");
                            }
                            layui.admin.removeLoading('body', true, true);
                        };
                        layui.admin.showLoading('body', 2, '.8');
                        myUtil.send(url, ajaxdata, callback);
                        // callback({msg:"删除成功"});
                        layer.close(index)
                    }
                );
            }else{
                myUtil.msg({type:"warning", message: "请选择需要删除的目录节点！"});
            }
        });
        $("#LAY-tree-openSearch").off("click").on("click", function(){
            $(".layui-nav-searchbox").removeClass("layui-hide");
        });
        $("#LAY-tree-closeSearch").off("click").on("click", function(){
            $(".layui-nav-searchbox").addClass("layui-hide");
            $("#LAY-tree-search").val("").trigger("change");
        });
        $("#LAY-tree-search").off("change").on("change", function(){
            setter.eleTreeObj.search($(this).val());
        });

        // 点击编辑 
        $("#btn-edit-theme").off("click").on("click",function(){
			
            var node = myUtil.getSelectedNode(treeId)
            if(node.id){
                layui.admin.open({
                    type:1 ,
                    title:"编辑目录结构",
                    skin: 'layui-layer-admin',
                    width:400,
                    // area: ["400px", "280px"],
                    content: createFloderTemp,
                    zIndex: layer.zIndex,
                    btn: ['确定', '取消'],
                    success: function (layero, index) {
                        selectTree(selectData)
                        form.val("createFunc-form",{
                            "funcName": selectData.name,
                            "resourceName": themeVal1,
							"terminalPC": selectData.terminalPC == "1" ? true : false,
							"terminalIpad": selectData.terminalIpad == "1" ? true : false,
							"terminalMobile": selectData.terminalMobile == "1" ? true : false,
							"businessType": selectData.businessType == "1" ? true : false,
                        })
                        form.on('submit(editFunc)', function(data){
                            F.editFloder(node,data,index)
                            return false;
                        });
                    },
                    yes: function (index, layero) {
                        $("#editFunc").trigger("click");
                    },
                    btn2: function (index, layero) {
                        layui.admin.removeLoading('body', true, true);
                    }
                })
            }else{
                myUtil.msg({type:"warning",message:"请选择要编辑的目录节点"})
                return false
            }
            
        })
                
    }
    
    /**
     * 左侧树初始化
     * @method renderTree
     * @for UserManage
     */
    function renderTree(index){
        var url = setter.basePath + "functionController/queryMenuManageTree",
        index = 0,
        callback = function(data){
            if(data){
                setter.eleTreeObj = eleTree.render({
                    elem: "#"+ treeId,
                    data: data.obj,
                    expandOnClickNode: false,
                    highlightCurrent: true,
                    renderAfterExpand: false,
                    // defaultExpandAll: true,
                    request: {
                        name: "name"
                    },
                    diyHoverDom: function(){
                        return '<span class="eleTree-node-content-diydom iconfont icon-dropup-line eleTree-hide" data-type="up" style="right:20px;"></span><span class="eleTree-node-content-diydom iconfont icon-dropdown-line eleTree-hide"  data-type="down"></span>';
                    },
                    leafIcon: setter.leafIcon,
                    searchNodeMethod: function(value, data){
                        if (!value) return false;
                        return data.name.indexOf(value) !== -1;
                    }
                });
                layui.admin.showLoading('body', 2, '.8');
                if(data.obj.length){
                    if(!firstRenderTreeState){
                        $("#" + treeId + ' .eleTree-node[data-id="' +data.obj[0].id + '"]').children(".eleTree-node-content").trigger("click");
                    }else{
                        renderedTrees += 1;
                        if(renderedTrees == 1){
                            var node = $("#" + treeId + ' .eleTree-node').eq(0),
                            nid = node.attr('data-id');
                            F.select({id: nid})
                            $(".eleTree-node").eq(0).children().first().trigger('click')
                            firstRenderTreeState = false;
                        }
                    }
                }
                $(".publicResourcel").off("click").on("click",function(){
                    $(this).addClass("activeList")
                    $(".eleTree-node-content.eleTree-node-content-active").removeClass("eleTree-node-content-active")
                })
                
                eleTree.on('nodeClick(layui-func-tree)', function (d) {
                    $(".publicResourcel").removeClass("activeList");
                    var node = d.data.currentData;
                    F.select(node);
                    // $("#"+ treeId + ' .eleTree-node-content-active').removeClass('eleTree-node-content-active');
                    layui.admin.removeLoading('body', true, true);
                });
                // $(".eleTree-node").css({
                //     "position":"relative"
                // })
                eleTree.on('nodeDiyDomClick(layui-func-tree)', function(d){
                    var type = $(event.target).attr('data-type'),
                    node = type == 'up' ? d.node.prev(): d.node.next(),
                    obj = {
                        id: d.data.currentData.id,
                        toId: node[0] ? node.attr('data-id') : ''
                    }
                    if(node[0]){
                        changFunSite(obj)
                    }
                });
                $("#LAY-func-tree").children().eq(0).children().eq(0).children('.icon-dropup-line').css({
                    "display":"none",
                })
                $("#LAY-func-tree").children().eq(0).children().eq(0).children('.icon-dropdown-line').css({
                    "width":"50px",
                })
                $("#LAY-func-tree").children().eq($("#LAY-func-tree").children().length-1).children().eq(0).children(".icon-dropdown-line").css({
                    "display":"none",
                })
                $("#LAY-func-tree").children().eq($("#LAY-func-tree").children().length-1).children().eq(0).children(".icon-dropup-line").css({
                    "width":"50px",
                    "right":'0px'
                })
                for(var c=0;c<$("#LAY-func-tree").children().length;c++){
                    if($("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().length>0){
                        $("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().eq(0).children().eq(0).children('.icon-dropup-line').css({
                            "display":"none",
                        })
                        $("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().eq(0).children().eq(0).children('.icon-dropdown-line').css({
                            "width":"50px",
                        })
                        $("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().eq($("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().length-1).children().eq(0).children('.icon-dropdown-line').css({
                            "display":"none",
                        })
                        $("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().eq($("#LAY-func-tree").children().eq(c).children('.eleTree-node-group').children().length-1).children().eq(0).children('.icon-dropup-line').css({
                            "width":"50px",
                            "right":'0px'
                        })
                    }
                }
                layui.admin.removeLoading('body', true, true);
            }
            layui.admin.removeLoading("body",true,true)
        };
        layui.admin.showLoading('body', 2, '.8');
        myUtil.send(url, {}, callback);
       
        
    }
    function changFunSite(ajaxData){
        var url = setter.basePath + "functionController/exchangeOrder",
        callback = function(data){
            if(data){
                myUtil.msg({type:"success",message:data.msg})
                renderTree()
            }
            layui.admin.removeLoading("body",true,true)
        } 
        layui.admin.showLoading("body",2,".8")
        myUtil.send(url,ajaxData,callback)
    }
    
    function initTable(){
        table.render({
            elem: "#funcList",
            cellMinWidth: 80,
            height: isSm ? "full-100" : "full-115",
            cols: [[
                {field:"themeUrl", title:"主题路径",align: "center",unresize: true, sort: false, style:"font-size:12px"},
                {field: "themeName", title: "已选主题名称",align: "center",unresize: true, sort: false, style:"font-size:12px"}
            ]],
            data: [],
            limit:"",
            skin: "nob",
            even: "true",
        });
       
    }
 
    

    // 下拉列表树
    function selectTree(check) {
       
        $("#selectResource").on("click", function (e) {
            var checks=[]
            if(check.length!=0){
                if(keys.length==0){
                    checks=keys1
                }else{
                    checks=keys 
                }
                
            }else{
                checks=keys
            }
			e.stopPropagation();
			var that = $(this),
				left = that.offset().left,
				top = that.offset().top + that.height(),
				ids = that.data('treeIds'),
				id = 'resourceTree',
				url = "resourceController/queryResourceTree",
				removeBefore = '',
				treeNodes = { treeNodes: '',icons: {
                    file: "iconfont eletree-icon-normal-file ",
                    "1": "iconfont icon-ico_jiashicang ",
                    "2": "iconfont icon-biaodan ", 
                    "3": "iconfont icon-lianjie ", 
                    "4": "iconfont icon-more-report ",
                    "5": "iconfont icon-Group ",
                    "6": "iconfont icon-ico_jiashicang ",
                    "7": "iconfont icon-ico_jiashicang ",
                    "8": "iconfont icon-biaodan ",
                    "9": "iconfont icon-more-report ",
                    "10": "iconfont icon-more-report ",
                }, checkbox: true, treeIds: checks || [] , openFirst: false},
				datakeys = {
					key: {
						name: 'resourceName'
					},
					simpleData: {
						idKey: "id",
					}
				},
				callback = function (type, treeId, node) {
					event.stopPropagation();
					var treeObj = jQuery.fn.zTree.getZTreeObj('LAY-'+id+'-tree');
					if(type == 'check'){
						var nodes = treeObj.getCheckedNodes();
						themeVal = [];
						themeId = [];
						themeId1 = [];
						keys = [];
						nodes.forEach(function(v,i){
							themeId.push(v.id)
							themeId1.push(v.id)
							keys.push(v.id)
							themeVal.push(v.resourceName)
						});
							
						$("#selectResource").val(themeVal)
					}
				}
			if($('.ctxMenu .dialog-tree-box')[0]){
				$('.ctxMenu .dialog-tree-box').parent().remove();
			}
			F.renderDialogzTree(id, left, top, url, '', (ids && ids.length && ids[0] ? ids[0]: ''), callback, 'true', treeNodes, removeBefore, datakeys, true);
			$(".ctxMenu").css({'max-width': '400px', width: '400px'});
			$('.ctxMenu .dialog-tree-box').css({'width': '100%', height: '500px'});
        })
       
    }
    !function(){
        if(isSm){
            $("#LAY-func-body > .layui-fluid").removeClass("layui-height-full-30").addClass("layui-height-full-20");
        }else{
            $("#LAY-func-body > .layui-fluid").removeClass("layui-height-full-20").addClass("layui-height-full-30");
        }
        $(".layui-nav-treebox").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        layui.sessionData("configTree", null);
        firstRenderTreeState = true;
        navbarPushPull();
        eventTreeBar();
        renderTree();
        // initTable();
    }();
    exports("func", {})
});