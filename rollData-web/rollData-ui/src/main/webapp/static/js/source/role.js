; layui.define(["wdUtil", 'admin', "table", "form", "eleTree", 'notice', 'setter', 'jquery', 'pscrollbar'], function (exports) {
    var $ = layui.jquery
      , laytpl = layui.laytpl
      , element = layui.element
      , setter = layui.setter
      , device = layui.device()
      , form = layui.form
      , myUtil = layui.wdUtil
      , eleTree = layui.eleTree
      , table = layui.table
      , pscrollbar = layui.pscrollbar
      , rolePid = ""
      , itemsData = [] //存放指标右边列表的数据
      , formIds = [] //存放指标权限
      , formItems = [] //存放指标权限
      , formArr = []
      , formTreeData = '' // 存放formtree的treeNodes
      , allunreadable = true
      , allunwriteable = true
      , currentFormTreeNodeId = ''
      , currentMenuTreeNodeId = ''
      , menuIds = [] //
      , menuResIds = [] //存放分析对象
	  , authorizeObj = { //缓存2级树选中节点id数组
		  buttonIds: [], //菜单按钮选中id
		  departmentIds: [], // 数据权限部门选中id
		  modelIds: [], //模型权限模型选中
		  dataSourceIds: [] //数据源权限数据源选中
	  }
      , menusData = []
      , menuArr = []
	  , saveRoleinfoUpdateState = false //保存数据更新状态，保存数据后树表格都不做重新渲染
	  , currentAuthorizeUserIds = []
	  , uncheckedNodes = [] //未选择用户集合用户渲染列表
	  , checkedNodes = []   //选择用户集合渲染列表
      , analyzesTreeData = ''
      , roleTreeId = "LAY-role-tree"
	  , postTreeId = 'LAY-post-tree'
      , allmenureadable = true
      , allmenuwriteable = true
	  , reloadedRole = false
	  , reloadedPost = false
	  , currentEditState = 'role'
      , createFloderTemp = '<form action="" class="layui-form" id="LAY-createRole-form" lay-filter="createRole-form" autocomplete="off">'
             +' <div class="layui-form-item layui-form-item-sm">'
                +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>{{name}}编码：</label>'
                +' <div class="layui-input-block">'
                    +' <input type="text" name="roleCde" class="layui-input" required lay-verType="tips" lay-verify="required|length20" placeholder="请输入{{name}}编码" autocomplete="off">'
                +' </div>'
            +' </div>'
            +' <div class="layui-form-item layui-form-item-sm">'
                +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>{{name}}名称：</label>'
                +' <div class="layui-input-block">'
                    +' <input type="text" name="roleName" class="layui-input" required lay-verType="tips" lay-verify="required|length50" placeholder="请输入{{name}}名称" autocomplete="off">'
                +' </div>'
            +' </div>'
			+' <div class="layui-form-item layui-form-item-sm autoShow">'
                +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>角色类型：</label>'
                +' <div class="layui-input-block">'
					+'<select name="roletype" class="roletype">'
						+'<option value="0">普通角色</option>'
						+'<option value="1">管理角色</option>'
					+'</select>'
                +' </div>'
            +' </div>'
            +'<button id="createRole-submit" class="layui-hide" lay-submit lay-filter="createRole">提交</button>'
        +' </form>'
        , configtrees = {
            func: {
                url : setter.basePath + "functionController/queryConfigPowerTree",
                menuUrl : '',
                ajaxData : {},
				menuAjaxData : {"parentId":""},
                treeId: "LAY-func-configTree",
                menuTreeId: "LAY-func-menuTree",
                treeType: "func",
                menuTreeType: "funcmenu",
                searchId: "#LAY-func-configTree-search",
                menuSearchId: "#LAY-func-menuTree-search",
				searchBtnId: "#LAY-func-configTree-search-btn",
                menuSearchBtnId: "#LAY-func-menuTree-search-btn",
                key: "funcId",
                listId: "funcIds",
                menuListId: "buttonIds",
                idsName: "funcs",
				state: false
            },
            org: {
                url : setter.basePath + "orgController/queryRoleCompanyTree",
                menuUrl : setter.basePath + "orgController/queryRoleDepartments",
                ajaxData : {},
				menuAjaxData : {"parentId":""},
				treeId: "LAY-org-configTree",
				menuTreeId: "LAY-org-menuTree",
				treeType: "org",
				menuTreeType: "orgmenu",
				searchId: "#LAY-org-configTree-search",
				menuSearchId: "#LAY-org-menuTree-search",
				searchBtnId: "#LAY-org-configTree-search-btn",
				menuSearchBtnId: "#LAY-org-menuTree-search-btn",
				key: "orgId",
				listId: "orgIds",
				menuListId: "departmentIds",
				idsName: "orgs",
				state: false
            },
			datasource: {
			    url : setter.basePath + "dataSourceController/queryDataSourcesFolderTree",
			    menuUrl : setter.basePath + "dataSourceController/queryDataSourcesByForderId",
			    ajaxData : {"parentId":"4028b881647da6f201647daa3378000c"},
				menuAjaxData : {"parentId":""},
				treeId: "LAY-datasource-configTree",
				menuTreeId: "LAY-datasource-menuTree",
				treeType: "datasource",
				menuTreeType: "datasourcemenu",
				searchId: "#LAY-datasource-configTree-search",
				menuSearchId: "#LAY-datasource-menuTree-search",
				searchBtnId: "#LAY-datasource-configTree-search-btn",
				menuSearchBtnId: "#LAY-datasource-menuTree-search-btn",
				key: "datasourceId",
				listId: "dataSourceFolderIds",
				menuListId: "dataSourceIds",
				idsName: "datasources",
				state: false
			},
			model: {
			    url : setter.basePath + "modelController/queryModelFolderTree",
			    menuUrl : setter.basePath + "modelController/queryModelByForderId",
			    ajaxData : {"parentId":"4028b881647da6f201647daa3378000d"},
			    menuAjaxData : {"parentId":""},
				treeId: "LAY-model-configTree",
				menuTreeId: "LAY-model-menuTree",
				treeType: "model",
				menuTreeType: "modelmenu",
				searchId: "#LAY-model-configTree-search",
				menuSearchId: "#LAY-model-menuTree-search",
				searchBtnId: "#LAY-model-configTree-search-btn",
				menuSearchBtnId: "#LAY-model-menuTree-search-btn",
				key: "modelId",
				listId: "modelFolderIds",
				menuListId: "modelIds",
				idsName: "models",
				state: false
			},
            user: {
                url : setter.basePath + "userController/userLimitTreeList",
                ajaxData : {},
				menuAjaxData : {"parentId":""},
                treeId: "LAY-user-configTree",
                treeType: "user",
                searchId: "#LAY-user-configTree-search",
                searchBtnId: "#LAY-user-configTree-search-btn",
                key: "userId",
                listId: "userIds",
                idsName: "funcs",
				state: false
            },
			analyze: {
				treeType: 'analyze',
				state: false
			},
			form: {
				treeType: 'form',
				state: false
			}
        }
        , isSm =  $('body').hasClass("layui-layout-sm-body")
        , F = {
            createFloder: function(formdatas, index){
                var url = currentEditState == "role" ? setter.basePath + "roleController/save" : setter.basePath + 'postController/createPost',
                ajaxData = currentEditState == "role" ? {event: "create-md", settings:{"roleCde":formdatas.field.roleCde ||"", "roleName": formdatas.field.roleName,"isAdmin":formdatas.field.roletype ,"pId": "0"}} : {event: "create-md", settings:{"postCode":formdatas.field.roleCde ||"", "postName": formdatas.field.roleName, "pId": "0"}}, 
                callback= function(data){
                    if(data){
						if(currentEditState == 'role'){
							setter.roleTreeObj.append("0", data.obj.treeNodes[0]);
						}else{
							setter.postTreeObj.append("0", data.obj.treeNodes[0]);
						}
						var treeId = currentEditState == 'role' ? roleTreeId : postTreeId;
                        $("#"+ treeId +' .eleTree-node[data-id="'+data.obj.treeNodes[0].id+'"]').children(".eleTree-node-content").trigger("click");
                        myUtil.msg({type:"success", message: data.msg});
                        renderTree()
                        layer.close(index)
                        layui.admin.removeLoading('body', true, true);
                    }
                    layui.admin.removeLoading("body",true,true)
                };
                layui.admin.showLoading("body",2,"1")
                myUtil.send(url, ajaxData, callback);
                // layui.admin.removeLoading('body', true, true);
            },
            saveRoleinfo: function(formdatas){
			
                var treeId = currentEditState == 'role'? roleTreeId : postTreeId,
				info = $(".box-roleinfo").data("roleinfo"),
				name = currentEditState == 'role'? 'roleAuthorize' : 'postAuthorize',
                url = currentEditState == 'role'? setter.basePath + "roleController/update" : setter.basePath + "postController/updatePost",
                funcChecks = setter.funcTreeObj ? setter.funcTreeObj.getCheckedNodes(true) : '',
                orgChecks = setter.orgTreeObj ? setter.orgTreeObj.getCheckedNodes(true) : '',
                formChecks = currentEditState == 'role' ? setter.formsTree ? setter.formsTree.getChecked(false,true) : '':[],
                datasourceChecks = currentEditState == 'role' ? setter.datasourceTreeObj ? setter.datasourceTreeObj.getCheckedNodes(true): '':[],
                modelChecks = currentEditState == 'role' ? setter.modelTreeObj ? setter.modelTreeObj.getCheckedNodes(true) : '':[],
                analyzeChecks = setter.analyzesTree ? setter.analyzesTree.getCheckedNodes(true): '',
                ajaxData = currentEditState == 'role'? {"event":"update-md","settings":{
                    "ids": null,
                    "roleAuthorize":{
                        "buttonIds": authorizeObj.buttonIds || [],
						"departmentIds": authorizeObj.departmentIds || [],
						"modelFolderIds": [],
						"modelIds": authorizeObj.modelIds || [],
						"dataSourceFolderIds": [],
						"dataSourceIds": authorizeObj.dataSourceIds || [],
                        "formIds": [],
                        "formItems" : [],
                        "funcIds": [],
                        "orgIds": [],
                        "menuIds":[],
                        "menuResIds":menuResIds || []
                    },
                    "roleCde" : info.roleCde,
                    "roleId": info.roleId,
                    "roleName": info.roleName,
                    "remark": info.remark,
                    "state": formdatas.field.state,
					"isAdmin": formdatas.field.roletype1,
                    "userAuthorize": {
                        "userIds": currentAuthorizeUserIds || []
                    }
                }} : {"event":"update-md","settings":{
                    "ids": null,
                    "postAuthorize":{
                        "buttonIds": authorizeObj.buttonIds || [],
                        "departmentIds": authorizeObj.departmentIds || [],
                        "modelFolderIds": [],
                        "modelIds": authorizeObj.modelIds || [],
                        "dataSourceFolderIds": [],
                        "dataSourceIds": authorizeObj.dataSourceIds || [],
                        "formIds": [],
                        "formItems" : [],
                        "funcIds": [],
                        "orgIds": [],
                        "menuIds":[],
                        "menuResIds":menuResIds || []
                    },
                    "postCode" : info.postCode,
                    "postId": info.postId,
                    "postName": info.postName,
                    "state": formdatas.field.state,
                    "userAuthorize": {
                        "userIds": currentAuthorizeUserIds || []
                    }
                }}, 
                node = myUtil.getSelectedNode(treeId),
                callback= function(data){
                    if(data){
                        myUtil.msg({type:"success", message: data.msg});
						setter[currentEditState+'TreeObj'].updateKeySelf(rolePid, {name: (currentEditState == 'role' ? data.obj.info.roleName : data.obj.info.postName)});
						saveRoleinfoUpdateState = true;
                        renderInfo(rolePid)
                        layui.admin.removeLoading("body",true,true)
                    }
                    layui.admin.removeLoading("body",true,true)
                };
			
				if(!orgChecks && Object.prototype.toString.call(orgChecks) == '[object String]'){
					ajaxData.settings[name].orgIds = info[name].orgIds;
				}else{
					if(orgChecks && orgChecks.length){
						orgChecks.forEach(function(v, i){
							ajaxData.settings[name].orgIds.push(v.orgId);
						});
					}else {
						ajaxData.settings[name].orgIds = [];
					}
				}
				if(!funcChecks && Object.prototype.toString.call(funcChecks) == '[object String]'){
					ajaxData.settings[name].funcIds = info[name].funcIds;
				}else{
					if(funcChecks && funcChecks.length){
						funcChecks.forEach(function(v, i){
							ajaxData.settings[name].funcIds.push(v.id);
						});
					}else{
						ajaxData.settings[name].funcIds = [];
					}
				}
				
				if(!analyzeChecks && Object.prototype.toString.call(analyzeChecks) == '[object String]'){
					ajaxData.settings[name].menuIds = info[name].menuIds;
				}else{
					if(analyzeChecks && analyzeChecks.length){
						analyzeChecks.forEach(function(v, i){
							ajaxData.settings[name].menuIds.push({relationId:v.id, type: v.type});
						});
					}else{
						ajaxData.settings[name].menuIds = [];
					}
				}
				if(!datasourceChecks && Object.prototype.toString.call(datasourceChecks) == '[object String]'){
					ajaxData.settings[name].dataSourceFolderIds = info[name].dataSourceFolderIds;
				}else{
					if(datasourceChecks && datasourceChecks.length){
						datasourceChecks.forEach(function(v, i){
							ajaxData.settings[name].dataSourceFolderIds.push(v.id);
						});
					}else {
						ajaxData.settings[name].dataSourceFolderIds = [];
					}
				}
				if(!modelChecks && Object.prototype.toString.call(modelChecks) == '[object String]'){
					ajaxData.settings[name].modelFolderIds = info[name].modelFolderIds;
				}else{
					if(modelChecks && modelChecks.length){
						modelChecks.forEach(function(v, i){
							ajaxData.settings[name].modelFolderIds.push(v.id);
						});
					}else {
						ajaxData.settings[name].modelFolderIds = [];
					}
				}
				if(currentEditState == 'role'){
					ajaxData.settings.roleName = $(".roleinfo-roleName").val();
					ajaxData.settings.remark = $(".roleinfo-roleRemark").val() || '';
				}else{
					ajaxData.settings.postName = $(".roleinfo-roleName").val();
				}
                
                if(myUtil.isSame(info, ajaxData.settings)){
                    myUtil.msg({type:"success", message: "编辑成功！"});
                }else{
                    layui.admin.showLoading("body",2,"1")
                    myUtil.send(url, ajaxData, callback);
                }
            }
      };

    /**
     * 左侧菜单树推拉方法初始化
     * @method navbarPushPull
     * @for roleManage
     */
    function navbarPushPull(){
        $(".layui-navbar-collapse").off("click").on("click", function(){
            $(".wrapper").toggleClass("show-layui-navbar");
        });
    }

    /**
     * 绑定树操作
     * @method navbarPushPull
     * @for roleManage
     */
    function eventTreeBar(){
        $(".layui-nav-treebar-item.btn-add").off("click").on("click", function(){
			var temp = createFloderTemp.replace(/{{name}}/g, (currentEditState == 'role' ? "角色" : '职务'));
			currentEditState == 'role' ? $('.autoShow').css('display','block') : $('.autoShow').css('display','none')
			
            layui.admin.open({
                title: (currentEditState == 'role' ? "新建角色" : '新建职务'),
                id: "LAY-admin-createRole",
                content: temp,
                success: function(layero, index){
                    form.render();
                    form.on('submit(createRole)', function (data) {
                        F.createFloder(data, index);
                        return false;
                    });
					form.verify({
						length20: function(value){
							if(value.trim().length > 20){
								return '长度不超过20位'
							}
						},
						length50: function(value){
							if(value.trim().length > 50){
								return '长度不超过50位'
							}
						}
					});   
                },
                yes: function(index, layero){
                    $("#createRole-submit").trigger("click");
                }
            });
        });
        $(".layui-nav-treebar-item.btn-del").off("click").on("click", function(){
			var treeId = currentEditState == 'role'? roleTreeId : postTreeId,
			node = myUtil.getSelectedNode(treeId);
			if(node.id && node.id != "0"){
				layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+node.name+'</span>吗？',{icon: 3, title:(currentEditState == 'role' ? '删除角色' : '删除职务'), skin: "layui-layer-admin"},
					function(index, layero){
						var url = currentEditState == 'role' ? setter.basePath + "roleController/del" : setter.basePath+'postController/deletePost',
						ajaxdata = {"event":"delete-md", settings:{ids:[(node.roleId||node.id)]}},
						callback = function(data){
							if(data){
								myUtil.msg({type:"success", message: data.msg});
								var next = "";
								if(node.parent.next()[0]){
									next = node.parent.next();
								}else if(node.parent.prev()){
									next = node.parent.prev();
								}else{
									next = node.parent.parent().parent();
								}
								if(currentEditState == 'role'){
									setter.roleTreeObj.remove(node.id);
								}else{
									setter.postTreeObj.remove(node.id);
								}
								next.children(".eleTree-node-content").trigger("click");
							}
							layui.admin.removeLoading('body', true, true);
						};
						layui.admin.showLoading('body', true, true);
						myUtil.send(url, ajaxdata, callback);
						layer.close(index)
					}
				);
			}else{
				myUtil.msg({type:"warning", message: "请选择需要删除的节点！"});
			}
        });
        $(".layui-nav-treebar-item.btn-search").off("click").on("click", function(){
            $(this).next().removeClass("layui-hide");
        });
        $(".layui-nav-search-close").off("click").on("click", function(){
            $(this).parent().addClass("layui-hide");
            $(this).prev().val("").trigger("change");
        });
        $(".tree-search").off("change").on("change", function(){
			if(currentEditState == 'role'){
				setter.roleTreeObj.search($(this).val());
			}else{
				setter.postTreeObj.search($(this).val());
			}
        });
    }
    
    /**
     * 左侧树初始化
     * @method renderTree
     * @for roleManage
     */
    function renderTree(){
        var url = currentEditState == 'role' ? setter.basePath + "roleController/queryAll" : setter.basePath + "postController/queryPostList",
        index = 0,
		treeId = currentEditState == 'role' ?  roleTreeId: postTreeId,
        nodes = [{
            type: "ROOT",
            id: "0",
            name: currentEditState == 'role' ? '角色目录': "职务目录",
            icon: "",
            children: []
        }],
        callback = function(data){
            if(data){
                nodes[0].children = data.obj.treeNodes;
				var eleTreeObj = eleTree.render({
                    elem: "#"+ treeId,
                    data: data.obj.treeNodes,
                    expandOnClickNode: false,
                    highlightCurrent: true,
                    renderAfterExpand: false,
                    defaultExpandAll: true,
                    request: {
                        name: "name"
                    },
                    leafIcon: setter.leafIcon,
                    searchNodeMethod: function(value, data){
                        if (!value) return false;
                        return data.name.indexOf(value) !== -1;
                    }
                });
				if(currentEditState == 'role'){
					setter.roleTreeObj = eleTreeObj;
				}else{
					setter.postTreeObj = eleTreeObj;
				}
                eleTree.on('nodeClick(layui-role-tree)', function(d){
                    var node = d.data.currentData;
                    if(node.id == "0"){
                        $("#"+ treeId +' .eleTree-node[data-id="'+node.children[0].id+'"]').children(".eleTree-node-content").trigger("click");
                    }else{
                        rolePid = node.roleId;
						renderInfo(rolePid);
                    }
					$("#LAY-func-menuTree").html("");
					$('.tree-box').animate({'scrollTop':0},500);
                });
				eleTree.on('nodeClick(layui-post-tree)', function(d){
				    var node = d.data.currentData;
				    if(node.id == "0"){
				        $("#"+ treeId +' .eleTree-node[data-id="'+node.children[0].id+'"]').children(".eleTree-node-content").trigger("click");
				    }else{
				        rolePid = node.postId;
						renderInfo(rolePid);
				    }
					$("#LAY-func-menuTree").html("");
					$('.tree-box').animate({'scrollTop':0},500);
				});
				$("#"+ treeId +' .eleTree-node').eq(0).children(".eleTree-node-content").trigger("click");
                layui.admin.removeLoading('body', true, true);
                layui.admin.removeLoading('', true, true);
            }
        };
        layui.admin.showLoading('', 2, '1');
        myUtil.send(url, {}, callback);
    }
    /**
     * 渲染角色详情
     * @method renderInfo
     * @for roleManage
     */
    function renderInfo(id){
        var url = currentEditState == 'role'? setter.basePath + "roleController/queryRoleDetailed" : setter.basePath + "postController/queryPostDetailed",
        index = 0,
		ajaxData = currentEditState == 'role' ? {roleId: id} : {postId: id},
        callback = function(data){
            if(data){
                $("#roleinfo-form")[0].reset();
                $(".box-roleinfo").data("roleinfo", myUtil.cloneObj(data.obj));
                $(".roleinfo-roleCde").text(data.obj.roleCde || data.obj.postCode || "");
                $(".roleinfo-roleName").val(data.obj.roleName || data.obj.postName ||"");
                $(".roleinfo-roleRemark").val(data.obj.remark ||"");

				currentAuthorizeUserIds = data.obj.userAuthorize.userIds || [];
				if(currentEditState == 'role'){
					form.val("roleinfo-form", {state: data.obj.state});
					$('.roletype1').val(data.obj.isAdmin)
				}
				menuResIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].menuResIds || [];
				menuIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].menuIds || [];
				authorizeObj.buttonIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].buttonIds || [];
				authorizeObj.departmentIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].departmentIds || [];
				authorizeObj.modelIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].modelIds || [];
				authorizeObj.dataSourceIds = data.obj[(currentEditState == 'role' ? 'roleAuthorize' : 'postAuthorize')].dataSourceIds || [];
				initanalyzeTable([], false, false, false, true);
				if(!saveRoleinfoUpdateState){
					$(".authorized-tab-item").trigger('click');
					$(".authorized-list .authorized-tab").eq(0).trigger('click');
					if(configtrees){
						for(var i in configtrees){
							if(configtrees[i]) configtrees[i].state = false;
						}
					}
					renderItemTab($(".authorized-list .authorized-tab").eq(0).attr('data-type'))
				}else{
					saveRoleinfoUpdateState = false;
				}
				$("#functab .menu-right-box .tree-box").html('<ul class="layui-mylayui1-tree" id="LAY-func-menuTree" lay-filter="layui-func-menuTree"></ul>');
				$("#orgtab .menu-right-box .tree-box").html('<ul class="layui-mylayui1-tree" id="LAY-org-menuTree" lay-filter="layui-org-menuTree"></ul>');
				$("#datasourcetab .menu-right-box .tree-box").html('<ul class="layui-mylayui1-tree" id="LAY-datasource-menuTree" lay-filter="layui-datasource-menuTree"></ul>');
				$("#modeltab .menu-right-box .tree-box").html('<ul class="layui-mylayui1-tree" id="LAY-model-menuTree" lay-filter="layui-model-menuTree"></ul>');
				layui.admin.removeLoading('.box-roleinfo', true, true);
            }
            layui.admin.removeLoading('body', true, true);
            layui.admin.removeLoading('', true, true);
        }
		
        layui.admin.showLoading('', 2, '1');
        myUtil.send(url, ajaxData, callback);
    }
	
    function renderRightTree(obj){
		layui.admin.showLoading('.layui-tab-content.layui-height-full-50', 2, '1');
		var treeStore = layui.sessionData("configTree",{key:obj.treeId}),
		treeName = obj.treeType + "TreeObj",
		info = $(".box-roleinfo").data("roleinfo"),
		date = new Date().getTime();
		if(treeStore &&  (date - parseInt(treeStore.time)) < 1800000){
			renderConfigTree(treeStore.treeDatas);
		}else{
			myUtil.send(obj.url, obj.ajaxData, function(data){
				if(data){
					obj.state = true;
					layui.sessionData("configTree",{key:obj.treeId, value: {treeDatas: data.obj, time: new Date().getTime()}});
					renderConfigTree(data.obj);
				}
			});
		}
		function renderConfigTree(treeDatas){
			var formArry = [],
			ids = currentEditState == 'role' ? info && info.roleAuthorize || []:  info && info.postAuthorize | [],
			keys = obj.treeType == "user" ? info && info.userAuthorize[obj.listId] || [] : (ids[obj.listId] || []),
			treeNodes = [];
			switch (obj.treeType){
				case 'func':
					$("#"+obj.treeId).parent().data('treeData', treeDatas.buttons);
					treeNodes = treeDatas.funcTree.treeNodes;
					break;
				case 'org':
					treeNodes = treeDatas.treeNodes;
					break;
				case 'datasource':
					treeNodes = treeDatas.datasources;
					break;
				case 'model':
					treeNodes = treeDatas.models;
					break;
				case 'user':
					treeNodes = treeDatas.treeNodes;
					break;
				default:
					break;
			}
			var checkedNodes = [];
			var nodeObj = {},
			checkObj = {enable: true, chkStyle: 'checkbox', chkboxType: { "Y": "", "N": "" }};
			switch (obj.treeType){
				case 'func':
					nodeObj = {
						key: "funcId",
						listId: "funcId"
					};
					checkObj = {enable: true, chkStyle: 'checkbox', chkboxType: { "Y": "ps", "N": "ps" }};
					break;
				case 'org':
					nodeObj = {
						key: "orgId",
						listId: "orgId"
					};
					break;
				case 'datasource':
				case 'model':
					nodeObj = {
						key: "id",
						listId: "id"
					};
					break;
				case "user":
					checkObj = {enable: true, chkStyle: 'checkbox'};
					break;
			}
			var treeDatas = treeNodes,
			renderRightChildrenTreeed = true,
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
						// icoObj.parent().append('<span data-remark="'+( treeNode.remark|| '')+'" class="eleTree-node-content-diydom iconfont iconfont icon-beizhu"></span>');
						if (treeNode.level >= 1) {
							var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
							switchObj.before(spaceStr);
						}
					}
				},
				check: checkObj,
				callback: {
					onBeforeFuzzy: function () {
						layui.admin.showLoading('#' + obj.treeId, 2, '1');
					},
					onAfterFuzzy: function () {
						var ele = document.getElementById(obj.treeId)
						ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
						layui.admin.removeLoading('#' + obj.treeId, true, true);
					},
					onClick: function(event, treeId, treeNode){
						if(renderRightChildrenTreeed){
							if(obj.treeType != "user"){
								var pid = "";
								switch (obj.treeType){
									case 'func':
										pid = treeNode.funcId;
										break;
									case 'org':
										pid = treeNode.orgId;
										break;
									case 'datasource':
									case 'model':
										pid = treeNode.id;
										break;
								}
								obj.menuAjaxData.parentId = pid;
								renderRightChildrenTree(obj);
							}
						}else{
							renderRightChildrenTreeed = true;
						}
					},
					onCheck: function(event, treeId, treeNode){
						if(obj.treeType == "user"){
							var checkNodes = setter[obj.treeType+ "TreeObj"].getCheckedNodes(true);
							if(checkNodes && checkNodes.length){
								getUsers(checkNodes);
							}else{
								getUsers();
							}
						}else{
							var pid = "";
							switch (obj.treeType){
								case 'func':
									pid = treeNode.funcId;
									break;
								case 'org':
									pid = treeNode.orgId;
									break;
								case 'datasource':
								case 'model':
									pid = treeNode.id;
									break;
							}
							if(obj.treeType == "func"){
								var funcObj = myUtil.cloneObj(obj);
								eachTreeNodeChild(treeNode, treeNode.checked);
								eachTreeNodeParent(treeNode, treeNode.checked);
								obj.menuAjaxData.parentId = pid;
								renderRightChildrenTree(obj, {checked: treeNode.checked});
								
								function eachTreeNodeChild(node, checked){
									if(node){
										funcObj.menuAjaxData.parentId = node.funcId;
										renderRightChildrenTree(funcObj, {checked: checked}, true);
										if(node.children && node.children.length){
											node.children.forEach(function(vv, ii){
												eachTreeNodeChild(vv, checked);
											})
										}
									}
								}
								function eachTreeNodeParent(node, checked){
									if(node && node.getParentNode()){
										funcObj.menuAjaxData.parentId = node.getParentNode().funcId;
										renderRightChildrenTree(funcObj, {checked: checked}, true);
										eachTreeNodeChild(node.getParentNode(), checked);
									}
								}
							}else{
								obj.menuAjaxData.parentId = pid;
								renderRightChildrenTree(obj, {checked: treeNode.checked});
							}
						}
						renderRightChildrenTreeed = false;
						$("#"+ treeNode.tId+"_a").trigger("click");
					}
				}
			};
			eachCheckNode(treeDatas, keys, {"COMPANY":'iconfont icon-org file','DEPARTMENT': 'iconfont icon-bumen3 file', "FUNC": "iconfont icon-project ", "1": "iconfont icon-folder-close ", "folder": "iconfont icon-folder-close ",},false);
			$("#"+ obj.treeId).addClass('lay-ztree ztree');
			setter[treeName] = jQuery.fn.zTree.init($('#' + obj.treeId), ztreeobj, treeDatas);
			
			fuzzySearch ( obj.treeId, obj.searchId, false, true );
			$(obj.searchBtnId).off("click").on("click", function(){
			   
				fireKeyEvent($(obj.searchId)[0], 'keydown', 13);
				
				function fireKeyEvent(el, evtType, keyCode){  
					var doc = el.ownerDocument,  
						win = doc.defaultView || doc.parentWindow,  
						evtObj;  
					if(doc.createEvent){  
						if(win.KeyEvent) {  
							evtObj = doc.createEvent('KeyEvents');  
							evtObj.initKeyEvent( evtType, true, true, win, false, false, false, false, keyCode, 0 );  
						}  
						else {  
							evtObj = doc.createEvent('UIEvents');  
							Object.defineProperty(evtObj, 'keyCode', {  
								get : function() { return this.keyCodeVal; }  
							});       
							Object.defineProperty(evtObj, 'which', {  
								get : function() { return this.keyCodeVal; }  
							});  
							evtObj.initUIEvent( evtType, true, true, win, 1 );  
							evtObj.keyCodeVal = keyCode;  
							if (evtObj.keyCode !== keyCode) {  
								console.log("keyCode " + evtObj.keyCode + " 和 (" + evtObj.which + ") 不匹配");  
							}  
						}  
						el.dispatchEvent(evtObj);  
					}   
					else if(doc.createEventObject){  
						evtObj = doc.createEventObject();  
						evtObj.keyCode = keyCode;  
						el.fireEvent('on' + evtType, evtObj);  
					}  
				} 
			});
			setter[treeName].expandAll(true);
			if(obj.treeType == "user"){
				getUsers();
			}
			function eachCheckNode(nodes, ids, icons, last){
				if(nodes.length){
					nodes.forEach(function(v, i){
						if(ids && ids.length && ids.indexOf(v[nodeObj.key]) > -1){
							checkedNodes.push(v);
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
			
			layui.admin.removeLoading('.layui-tab-content.layui-height-full-50', true, true);
		}
	}
	//渲染右侧子级树，checkobj是否勾选
	function renderRightChildrenTree(obj, checkobj, notRenderTree){
		if(!notRenderTree) layui.admin.showLoading('.authorized-tab-pane.layui-show', 2, '1');
		var treeName = obj.menuTreeType + "TreeObj",
		info = $(".box-roleinfo").data("roleinfo");
		if(obj.menuTreeType == 'funcmenu'){
			var funcTreeData = $("#"+obj.treeId).parent().data('treeData'),
			funcindex = '';
			if(funcTreeData && funcTreeData.length){
				for(var k =0;k < funcTreeData.length; k++){
					if(funcTreeData[k].funcId == obj.menuAjaxData.parentId){
						funcindex = k;
						break;
					}
				}
			}
			if(funcindex || funcindex === 0){
				renderConfigTree(funcTreeData[funcindex].buttons);
			}else{
				renderConfigTree([]);
			}
		}else{
			myUtil.send(obj.menuUrl, obj.menuAjaxData, function(data){
				if(data){
					renderConfigTree(data.obj);
				}
			});
		}
		function renderConfigTree(treeDatas){
			var keys = authorizeObj[obj.menuListId] || [],
			treeDatas = treeDatas || [],
			defaultKeys = filterParentId(keys,treeDatas);
			datakeys = {
				key: {
					name: 'name'
				},
				simpleData: {
					idKey: "id",
				}
			},
			nodeObj = {
				key: "id",
				listId: "id"
			};
			switch (obj.treeType){
				case 'func':
					datakeys.key.name = 'operName';
					break;
				case 'org':
					nodeObj = {
						key: "orgId",
						listId: "orgId"
					};
					break;
				case 'datasource':
					datakeys.key.name = 'dsName';
					break;
				case 'model':
					datakeys.key.name = 'mdName';
					break;
			}
			if(checkobj){
				if(checkobj.checked){
					if(treeDatas.length){
						_each1(treeDatas);
						function _each1(nodes){
							if(nodes && nodes.length){
								nodes.forEach(function(v, i){
									defaultKeys.push(v.id);
									if(v.children && v.children.length){
										_each1(v.children);
									}
								})
							}
						}
					}
					if(defaultKeys.length){
						defaultKeys.forEach(function(v, i){
							if(keys.indexOf(v) < 0){
								keys.push(v);
							}
						})
						authorizeObj[obj.menuListId] = keys;
					}
				}else{
					if(defaultKeys.length){
						defaultKeys.forEach(function(v, i){
							if(keys.indexOf(v) > -1){
								keys.splice(keys.indexOf(v), 1);
							}
						})
						authorizeObj[obj.menuListId] = keys;
					}
					defaultKeys = [];
				}
			}
			if(!notRenderTree){
				var ztreeobj = {
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
							// icoObj.parent().append('<span data-remark="'+( treeNode.remark|| '')+'" class="eleTree-node-content-diydom iconfont iconfont icon-beizhu"></span>');
							if (treeNode.level >= 1) {
								var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
								switchObj.before(spaceStr);
							}
						}
					},
					check: {enable: true, chkStyle: 'checkbox'},
					data: datakeys,
					callback: {
						onBeforeFuzzy: function () {
							layui.admin.showLoading('#' + obj.treeId, 2, '1');
						},
						onAfterFuzzy: function () {
							var ele = document.getElementById(obj.treeId)
							ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
							layui.admin.removeLoading('#' + obj.treeId, true, true);
						},
						onCheck: function(event, treeId, treeNode){
							var checkNodes = setter[treeName].getCheckedNodes(true),
							parentNode = obj.menuAjaxData.parentId ? setter[obj.treeType+'TreeObj'].getNodeByParam("id", obj.menuAjaxData.parentId, null) :setter[obj.treeType+'TreeObj'].getSelectedNodes()[0];
							if(treeNode.checked){
								if(checkNodes && checkNodes.length){
									checkNodes.forEach(function(val, j){
										if(authorizeObj[obj.menuListId].indexOf(val.id) < 0){
											authorizeObj[obj.menuListId].push(val.id);
										}
									})
								}
								if(parentNode && !parentNode.checked){
									setter[obj.treeType+'TreeObj'].checkNode(parentNode, true, true);
								}
							}else{
								var nodes = setter[treeName].transformToArray(setter[treeName].getNodes());
								if(nodes && nodes.length){
									nodes.forEach(function(val, j){
										if(!val.checked && authorizeObj[obj.menuListId].indexOf(val.id) > -1){
											authorizeObj[obj.menuListId].splice(authorizeObj[obj.menuListId].indexOf(val.id), 1);
										}
									})
								}
								if(checkNodes.length <= 0 && parentNode){
									setter[obj.treeType+'TreeObj'].checkNode(parentNode, false, true);
								}
							}
						}
					}
				};
				eachCheckNode(treeDatas, keys, {"COMPANY":'iconfont icon-org file','DEPARTMENT': 'iconfont icon-bumen3 file', "FUNC": "iconfont icon-project ", "1": "iconfont icon-iconfontfile2 file", "folder": "iconfont icon-folder-close ",},false);
				$("#"+ obj.menuTreeId).addClass('lay-ztree ztree');
				setter[treeName] = jQuery.fn.zTree.init($('#' + obj.menuTreeId), ztreeobj, treeDatas);
				
				fuzzySearch ( obj.menuTreeId, obj.menuSearchId, false, true );
				
				setter[treeName].expandAll(true);
			}
			function eachCheckNode(nodes, ids, icons, last){
				if(nodes.length){
					nodes.forEach(function(v, i){
						if(ids && ids.length && ids.indexOf(v[nodeObj.key]) > -1){
							checkedNodes.push(v);
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
			
			layui.admin.removeLoading('.authorized-tab-pane.layui-show', true, true);
			
		}
		//从已选id集合中选择当前树已选中id
		function filterParentId(keys, treeData){
			var arr = [];
			_each(treeData);
			function _each(nodes){
				if(nodes && nodes.length){
					nodes.forEach(function(v, i){
						if(keys.indexOf(v.id) > -1){
							arr.push(v.id);
						}
						if(v.children && v.children.length){
							_each(v.children);
						}
					})
				}
			}
			return arr;
		}
	}
   
    // 渲染表单目录树
    function fromTree(treeData){
        var keys = []
        if(!treeData){
            keys = []
        }else{
            treeData.forEach(function(v,i){
                keys.push(v)
            })
        }
        var url = setter.basePath + "reportFormController/queryReportFormTree",
            callback = function(data){
				configtrees.form.state = true;
                formTreeData = data.obj.treeNodes
                removeParentId(keys,data.obj.treeNodes)
                var arr = [],
				arr1 = [];
                keys.forEach(function(v,j){
                    arr.push(v.id);
                })
                keys = arr;
                setter.formsTree = eleTree.render({
                    elem: "#LAY-form-configTree",
                    data: data.obj.treeNodes,
                    expandOnClickNode: false,
                    highlightCurrent: true,
                    renderAfterExpand: false,
                    defaultExpandAll: true,
                    defaultCheckedKeys: keys,
                    showCheckbox:true,
                    request: {
                        name: "name",
                        key:"id"
                    },
                    leafIcon: setter.leafIcon,
                    searchNodeMethod: function (value, data) {
                        if (!value) return false;
                        return data.name.indexOf(value) !== -1;
                    }
                });
                $("#LAY-form-configTree-search").off("change").on("change",function(){
                    setter.formsTree.search($(this).val())
                })
                eleTree.on('nodeClick(layui-form-configTree)', function(d){
                    itemsData= d.data.currentData.itemsData;
                    currentFormTreeNodeId = d.data.currentData.id;
                    updateItems({isview: true});
                })
                eleTree.on('nodeChecked(layui-form-configTree)', function(d){
                    itemsData= d.data.currentData.itemsData;
                    currentFormTreeNodeId = d.data.currentData.id;
                    updateItems({allreadChecked: d.isChecked?'1':'0'});
                })
                layui.admin.removeLoading(".layui-tab-content.layui-height-full-50",true,true)
            }
        layui.admin.showLoading(".layui-tab-content.layui-height-full-50",2,"1")
        myUtil.send(url,{},callback)
    }
     // 渲染指标权限列表
    function initauthorityTable(datas, allread, allwrite, isChecked, isview){
        var datas = datas ||[];
        if(datas && datas.length){
            datas.forEach(function(v,i){
                if(v.readable == "0"){
                    v.writeable = "0"
                }
            })
        }
        table.render({
            elem: "#authorityTable",
            cellMinWidth: "80",
            height: isSm ? "full-388" : "full-403",
            cols: [[
                {field:"name", title:"",align: "center",unresize: true, sort: false,
                style:"font-size:12px"},
                {field: "readable", title: "可读",align: "center",unresize: true, sort: false,templet: '#readable',
                style:"font-size:12px"},
                {field: "writeable", title: "可写",align: "center",unresize: true, sort: false,templet: '#writeable',
                style:"font-size:12px"}
            ]],
            data: datas,
            limit:datas.length
        });
        form.val('allindexable', {
            "allreadable": allread, //复选框选中状态
            'allwriteable': allwrite
        });
        if(setter.formsTree && !isview){
            var arr = setter.formsTree.getChecked(),
            checkids = allwrite || allread || isChecked ? [currentFormTreeNodeId] :[];
            arr = filterIds(arr);
            if(allwrite || allread || isChecked){
                checkids = [currentFormTreeNodeId].concat(arr);
            }else if(!allread && !allwrite && !isChecked){
                arr.forEach(function(v, i){
                    if(v != currentFormTreeNodeId){
                        checkids.push(v);
                    }
                })
            }
            checkids = filterPid(checkids, formTreeData);
            setter.formsTree.setChecked(checkids, true);
            selectedNode('LAY-form-configTree', currentMenuTreeNodeId);
        }
        // 点击触发可读
        form.on('checkbox(readable)', function(obj){
            updateItems({id:$(this).attr("data-id"), type:'readable', checked:obj.elem.checked});
        });
        // 点击触发可写
        form.on('checkbox(writeable)', function(obj){
            updateItems({id:$(this).attr("data-id"), type:'writeable', checked:obj.elem.checked});
        });
        // 单击全选可读
        form.on('checkbox(allreadable)', function(data){
            updateItems({allreadChecked: data.elem.checked ? '1':'0'});
        });
        // 单击全选可写
        form.on('checkbox(allwriteable)', function(data){
            updateItems({allwriteChecked: data.elem.checked ? '1':'0'});
        });
        
    }
    
    // 修改formTreeItems
    function updateItems(obj){
        var obj = obj || {},allread = true, allwrite = true, isChecked = false;
        if(itemsData && itemsData.length){
            itemsData.forEach(function(v, i){
                var item = '';
                if(formItems && formItems.length){
                    for(var k =0;k<formItems.length;k++){
                        if(formItems[k].formId == v.formId && formItems[k].itemId == v.id){
                            item = {data: formItems[k], index: k};
                            break;
                        }
                    }
                }
                if(obj.allwriteChecked){
                    if(obj.allwriteChecked == '1'){
                        v.writeable = '1';
                        v.readable = '1';
                        if(item){
                            formItems[item.index].writeable = '1';
                            formItems[item.index].readable = '1';
                        }else if(!item){
                            formItems.push({
                                "formId": v.formId,
                                "itemId": v.itemId|| v.id,
                                "readable":v.readable,
                                "writeable": v.writeable
                            });
                        }
                    }else{
                        v.writeable = '0';
                        if(item){
                            formItems[item.index].writeable = '0';
                        }
                    }
                }else if(obj.allreadChecked){
                    if(obj.allreadChecked == '1'){
                        v.readable = '1';
                        if(item){
                            formItems[item.index].readable = '1';
                        }else if(!item){
                            formItems.push({
                                "formId": v.formId,
                                "itemId": v.itemId|| v.id,
                                "readable":v.readable,
                                "writeable": v.writeable
                            });
                        }
                    }else{
                        v.readable = '0';
                        v.writeable = '0';
                        if(item){
                            formItems.splice(item.index, 1);
                        }
                    }
                }else if(obj.id && obj.id == v.id){
                    v[obj.type] = obj.checked ? '1':'0';
                    if(obj.type == 'readable' && !obj.checked){
                        v.writeable = '0';
                    }else if(obj.type == 'writeable' && obj.checked){
                        v.readable = '1';
                    }
                    if(item){
                        if(v.writeable == '0' && v.readable == '0'){
                            formItems.splice(item.index, 1);
                        }else{
                            formItems[item.index][obj.type] = obj.checked ? '1':'0';
                            if(obj.type == 'readable' && !obj.checked){
                                formItems[item.index].writeable = '0';
                            }
                        }
                    }else if(!item && (v.writeable == '1' || v.readable == '1')){
                        formItems.push({
                            "formId": v.formId,
                            "itemId": v.itemId || v.id,
                            "readable":v.readable,
                            "writeable": v.writeable
                        });
                    }
                }else if(obj.isview && item){
                    v.readable = item.data.readable;
                    v.writeable = item.data.writeable;
                }
                if(v.readable == '0'){
                    allread = false;
                    allwrite = false;
                }else if(v.writeable == '0'){
                    allwrite = false;
                }
                if(v.readable == "1" || v.writeable == "1"){
                    isChecked = true;
                }
            });
            initauthorityTable(itemsData, allread, allwrite, isChecked, obj.isview);
        }else{
            initauthorityTable([], false, false, obj.allreadChecked == '1' ? ture: false, obj.isview);
        }
    }
    
    // 渲染主题目录树
    function analyzeTree(treeData){
        var keys = []
        if(!treeData){
            keys = []
        }else{
            treeData.forEach(function(v,i){
                keys.push(v)
            })
        }
        var url = setter.basePath + "functionController/queryRoleMenuTree",
            callback = function(data){
				configtrees.analyze.state = true;
                analyzesTreeData = data.obj;
                if(keys.length){
                    var arr = []
                    keys.forEach(function(v,j){
                        arr.push(v.relationId)
                    })
                    keys = arr
                }
				var treeEl = $('#LAY-analyze-configTree'),
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
								checkObj.before(icoObj);
								icoObj.before(switchObj);
							}else{
								icoObj.before(switchObj);
							}
							
							if (treeNode.level >= 1) {
								var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
								switchObj.before(spaceStr);
							}
						}
					},
					check : {enable: true, chkStyle: 'checkbox'},
					callback: {
						onBeforeFuzzy: function () {
							layui.admin.showLoading('#LAY-analyze-configTree', 2, '1');
						},
						onAfterFuzzy: function () {
							var ele = document.getElementById('LAY-analyze-configTree')
							ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
							layui.admin.removeLoading('#LAY-analyze-configTree', true, true);
						},
						onClick: function(event, treeId, treeNode){
							menusData= treeNode.menusData;
							currentMenuTreeNodeId = treeNode.id;
							updateMenuItems({isview: true, initTable: true});
						},
						onCheck: function(event, treeId, treeNode){
							menusData= treeNode.menusData;
							currentMenuTreeNodeId = treeNode.id;
							if(treeNode.checked){
								eachParentMenusData(treeNode.getParentNode());
							}
							eachChildrenMenusData(treeNode.children, treeNode.checked);
							updateMenuItems({allreadChecked: treeNode.checked ?'1':'0', initTable: true});
							
						}
					}
				};
				
				treeEl.off('click').on('click', function(e){
					var evt = window.event || arguments.callee.caller.arguments[0];
					evt.preventDefault();
					evt.stopPropagation();
				})
				
				eachCheckNode(analyzesTreeData, keys);
				
				setter.analyzesTree = jQuery.fn.zTree.init($('#LAY-analyze-configTree'), ztreeobj, analyzesTreeData);
				$('#LAY-analyze-configTree-search').parent().off("click").on("click", function(e){
					var evt = window.event || arguments.callee.caller.arguments[0];
					evt.preventDefault();
					evt.stopPropagation();
				})
				
				fuzzySearch ( 'LAY-analyze-configTree', '#LAY-analyze-configTree-search', false, true );
               
                layui.admin.removeLoading(".layui-tab-content.layui-height-full-50",true,true)
				
				function eachCheckNode(nodes, ids, last){
					if(nodes && nodes.length){
						nodes.forEach(function(v, i){
							if(ids.indexOf(v.id) > -1){
								v.checked = 'true';
							}else{
								delete v.checked;
							}
							if(v.children && v.children.length){
								v.open = true;
								if(last){
									v.nocheck = true;
								} else {
									delete v.nocheck;
								}
								eachCheckNode(v.children, ids);
							}else{
								v.iconSkin = 'iconfont icon-folder-close folder';
							}
						})
					}
				}
				function eachChildrenMenusData(nodes, checked){
					if(nodes && nodes.length){
						nodes.forEach(function(v, i){
							if(v.menusData && v.menusData.length){
								v.menusData.forEach(function(vv, ii){
									var item = getMenuByMenuResId(vv.resourceId);
									if(checked){
										vv.readable = '1';
										if(item) menuResIds[item.index].readable = '1';
									}else{
										vv.writeable = '0';
										vv.readable = '0';
										if(item){
											menuResIds[item.index].writeable = '0';
											menuResIds[item.index].readable = '0';
										}
									}
								})
							}
							if(v.children && v.children.length){
								eachChildrenMenusData(v.children, checked);
							}
						})
					}
				}
				function eachParentMenusData(getParentNode){
					if(getParentNode && getParentNode.menusData){
						var allUnreadable = true,
						parent = getParentNode.getParentNode();
						for(var i = 0; i < getParentNode.menusData.length; i++){
							if(getParentNode.menusData[i].readable == '1'){
								allUnreadable = false;
								break;
							}
						}
						if(allUnreadable){
							getParentNode.menusData.forEach(function(v, i){
								var item = getMenuByMenuResId(v.resourceId);
								v.readable = '1';
								if(item) menuResIds[item.index].readable = '1';
							})
						}
						if(parent){
							eachParentMenusData(parent);
						}
					}
					
				}
				function getMenuByMenuResId(id){
					var item = '';
					if(menuResIds && menuResIds.length){
						for(var k = 0;k < menuResIds.length; k++){
							if(menuResIds[k].resourceId == id){
								item = {data: menuResIds[k], index: k};
								break;
							}
						}
					}
					return item;
				}
            }
        layui.admin.showLoading(".layui-tab-content.layui-height-full-50",2,"1")
        myUtil.send(url,{},callback)
    }
    // 渲染分析对象列表
    function initanalyzeTable(datas, allread, allwrite, isChecked, isview){
        var datas = datas || [],
		scrollTop = $("#analyzeTable").next()[0] && $("#analyzeTable").next().find(".layui-table-body")[0].scrollTop;
        if(datas && datas.length){
            datas.forEach(function(v,i){
                if(v.readable == "0"){
                    v.writeable == '0'
                }
            })
        }
        table.render({
            elem: "#analyzeTable",
            cellMinWidth: "80",
            height: isSm ? "full-408" : "full-423",
            cols: [[
                {field:"name", title:"",align: "center",unresize: true, sort: false,
                style:"font-size:12px"},
                {field: "readable", title: "可读",align: "center",unresize: true, sort: false,templet: '#readable',
                style:"font-size:12px"},
                {field: "writeable", title: "可写",align: "center",unresize: true, sort: false,templet: '#writeable',
                style:"font-size:12px"}

            ]],
            data: datas,
            limit:datas.length
        });
        form.val('allmenuable', {
            "allmenureadable": allread, //复选框选中状态
            "allmenuwriteable": allwrite //复选框选中状态
        })
        if(setter.analyzesTree && !isview){
			setter.analyzesTree.checkNode(setter.analyzesTree.getNodeByParam('id', currentMenuTreeNodeId, null), (allwrite || allread || isChecked ? true : false), true);
        }
		if(scrollTop){
			$("#analyzeTable").next().find(".layui-table-body")[0].scrollTop = scrollTop;
		}
        // 点击触发可读
        form.on('checkbox(readable)', function(obj){
            updateMenuItems({id:$(this).attr("data-id"), type:'readable', checked:obj.elem.checked});
        });
        // 点击触发可写
        form.on('checkbox(writeable)', function(obj){
            updateMenuItems({id:$(this).attr("data-id"), type:'writeable', checked:obj.elem.checked});
        });
        // 单击全选可读
        form.on('checkbox(allmenureadable)', function(data){
            updateMenuItems({allreadChecked: data.elem.checked ? '1':'0'});
        });
        // 单击全选可写
        form.on('checkbox(allmenuwriteable)', function(data){
            updateMenuItems({allwriteChecked: data.elem.checked ? '1':'0'});
        });

    }
   
    function updateMenuItems(obj){
        var obj = obj || {},allread = true, allwrite = true, isChecked = false;
        if(menusData && menusData.length){
            menusData.forEach(function(v, i){
                var item = '';
                if(menuResIds && menuResIds.length){
                    for(var k =0;k<menuResIds.length;k++){
                        if(menuResIds[k].resourceId == v.resourceId){
                            item = {data: menuResIds[k], index: k};
                            break;
                        }
                    }
                }
                if(obj.allwriteChecked){
                    if(obj.allwriteChecked == '1'){
                        v.writeable = '1';
                        v.readable = '1';
                        if(item){
                            menuResIds[item.index].writeable = '1';
                            menuResIds[item.index].readable = '1';
                        }else if(!item){
                            menuResIds.push({
                                "resourceId": v.resourceId,
                                "readable":v.readable,
                                "writeable": v.writeable
                            });
                        }
                    }else{
                        v.writeable = '0';
                        if(item){
                            menuResIds[item.index].writeable = '0';
                        }
                    }
                }else if(obj.allreadChecked){
                    if(obj.allreadChecked == '1'){
                        v.readable = '1';
                        if(item){
                            menuResIds[item.index].readable = '1';
                        }else if(!item){
                            menuResIds.push({
                                "resourceId": v.resourceId,
                                "readable":v.readable,
                                "writeable": v.writeable
                            });
                        }
                    }else{
                        v.readable = '0';
                        v.writeable = '0';
                        if(item){
                            menuResIds.splice(item.index, 1);
                        }
                    }
                }else if(obj.id && obj.id == v.resourceId){
                    v[obj.type] = obj.checked ? '1':'0';
                    if(obj.type == 'readable' && !obj.checked){
                        v.writeable = '0';
                    }else if(obj.type == 'writeable' && obj.checked){
                        v.readable = '1';
                    }
                    if(item){
                        if(v.writeable == '0' && v.readable == '0'){
                            menuResIds.splice(item.index, 1);
                        }else{
                            menuResIds[item.index][obj.type] = obj.checked ? '1':'0';
                            if(obj.type == 'readable' && !obj.checked){
                                menuResIds[item.index].writeable = '0';
                            }
                        }
                    }else if(!item && (v.writeable == '1' || v.readable == '1')){
                        menuResIds.push({
                            "resourceId": v.resourceId,
                            "readable":v.readable,
                            "writeable": v.writeable
                        });
                    }
                }else if(obj.isview && item){
                    v.readable = item.data.readable;
                    v.writeable = item.data.writeable;
                }
                if(v.readable == '0'){
                    allread = false;
                    allwrite = false;
                }else if(v.writeable == '0'){
                    allwrite = false;
                }
                if(v.readable == "1" || v.writeable == "1"){
                    isChecked = true;
                }
            });
            initanalyzeTable(menusData, allread, allwrite, isChecked, obj.isview);
        }else{
            initanalyzeTable([], false, false, obj.allreadChecked == '1' ? true: false, obj.isview);
        }
        
    }

    function selectedNode(tid, id){
        $("#"+tid).find(".eleTree-node-content").removeClass("eleTree-node-content-active");
        $("#"+tid +' .eleTree-node[data-id="'+id+'"]').find(".eleTree-node-content").addClass('eleTree-node-content-active')
    }

    
   
    /**
     * 过滤选择id集合返回纯id数组
     * @method filterIds
     * @for roleManage
     */
    function filterIds(keys){
        var ids = [];
        if(keys[0] && keys[0].id){
            keys.forEach(function(v,i){
                ids.push(v.id);
            })
        }else{
            ids = keys;
        }
        return ids;
    }
    /**
     * 过滤父id，树勾选框勾选父节点造成全选误操作
     * @method filterPid
     * @for roleManage
     */
    function filterPid(keys, nodes){
        if(keys.indexOf('0') > -1){
            keys.splice(keys.indexOf('0'), 1);
        }
        filterEach(keys, nodes);
        function filterEach(keys, arr){
            arr.forEach(function(v, i){
                if(v.children && v.children.length){
                    if(keys.indexOf(v.id) > -1){
                        keys.splice(keys.indexOf(v.id),1)
                    }
                    filterEach(keys, v.children);
                }
            })
        }
        return keys;
    }
    // 渲染是移除父级ID
    function removeParentId(keys,treeData){
        treeData.forEach(function(v,i){
            if(v.children){
                keys.forEach(function(m,j){
                    if(m == v.id){
                        keys.splice(j,1)
                    }
                    deep(keys,v.children)
                })
            }
        })
        function deep(keys,treeData){
            treeData.forEach(function(v,i){
                if(v.children){
                    keys.forEach(function(m,j){
                        if(m == v.id){
                            keys.splice(j,1)
                        }
                        deep(keys,v.children)
                    })
                }
            })
        }
    }
    /**
	 * 根据部门公司获取用户
	 * @method getUsers
	 * @for roleManage
	 * @param  {Object} obj 树属性对象
	 * @param  下面为arguments详细
	 * @param  {Array(JSON)}  nodes 需生成标签树节点属性
	 */
	function getUsers(checkedNodes){
		var url = setter.basePath + 'userController/allUsers',
		ajaxData = {
			search: '',
			org: []
		},
		callback = function($data){
			if($data.success){
				eachSplitCheckeds($data.obj);
			}
			layui.admin.removeLoading('.users-list-box', true, true);
		}
		
		if(checkedNodes && checkedNodes.length){
			var orgs = [];
			checkedNodes.forEach(function(v, i){
				orgs.push({type: v.type == 'COMPANY' ? '1' :'2', orgId: v.id})
			})
			ajaxData.org = orgs;
		}
		layui.admin.showLoading('.users-list-box', 2, '1');
		myUtil.send(url, ajaxData, callback);
		
	}
	/**
	 * 遍历拆分选择和未选择用户数组
	 * @method eachSplitCheckeds
	 * @for roleManage
	 * @param  {Object} userNodes 查询用户数组集合
	 */
	function eachSplitCheckeds(userNodes){
		uncheckedNodes = [];
		checkedNodes = [];
		if(userNodes && userNodes.length){
			userNodes.forEach(function(v, i){
				if(currentAuthorizeUserIds.indexOf(v.userId) > -1 || currentAuthorizeUserIds.indexOf(v.id) > -1){
					checkedNodes.push(v);
				}else{
					uncheckedNodes.push(v);
				}
			})
		}
		renderUsersItem(uncheckedNodes, checkedNodes);
	}
	/**
	 * 渲染选择和未选择用户
	 * @method renderUsersItem
	 * @for roleManage
	 * @param  {Array} nodes 未选择用户
	 * @param  {Array} nodes1 选择用户
	 */
	function renderUsersItem(nodes, nodes1){
		var uncheckeds = [],
		checkeds = [];
		
		if(nodes && nodes.length){
			nodes.forEach(function(v, i){
				uncheckeds.push('<div class="users-item" id="aid'+v.id+'" data-id="'+v.id+'"><i class="iconfont icon-user"></i><span>'+ (v.title || (v.userName+'-'+v.userCde)) +'</span></div>');
			})
		}
		if(nodes1 && nodes1.length){
			nodes1.forEach(function(v, i){
				checkeds.push('<div class="users-item" id="aid'+v.id+'" data-id="'+v.id+'"><i class="iconfont icon-user"></i><span>'+ (v.title || (v.userName+'-'+v.userCde))+'</span><span class="close-btn iconfont icon-delete users-close"></span></div>');
			})
		}
		$(".users-unchecked-list").html(uncheckeds.join(''));
		$(".users-checked-list").html(checkeds.join(''));
		eventUsersItem();
	}
	function eventUsersItem(){
		$('.users-item-list-all-btn').off('click').on('click', function(){
			var type = $(this).attr('data-type');
			if(type == 'checked'){
				layer.confirm('确认取消全部用户吗？',{icon: 3, title:'取消全选', skin: "layui-layer-admin"},
					function(index, layero){
						if(checkedNodes && checkedNodes.length){
							checkedNodes.forEach(function(v, i){
								uncheckedNodes.push(myUtil.cloneObj(v));
								var id = v.userId || v.id;
								if(currentAuthorizeUserIds.indexOf(id) > -1){
									currentAuthorizeUserIds.splice(currentAuthorizeUserIds.indexOf(id), 1);
								}
							})
							checkedNodes = [];
						}
						renderUsersItem(uncheckedNodes, checkedNodes);
						layer.close(index)
					}
				);
			}else{
				if(uncheckedNodes && uncheckedNodes.length){
					uncheckedNodes.forEach(function(v, i){
						checkedNodes.push(myUtil.cloneObj(v));
						currentAuthorizeUserIds.push(v.userId || v.id);
					})
					uncheckedNodes = [];
				}
				renderUsersItem(uncheckedNodes, checkedNodes);
			}
		});
		$(".users-item-list-sreach").off('change').on('change', function(){
			sreachValue($(this));
		})
		$(".users-item-list-sreach-btn").off('click').on('click', function(){
			var input = $(this).prev();
			sreachValue(input);
		})
		$(".users-unchecked-list .users-item").off('click').on('click', function(){
			var el = $(this),
			id = el.attr('data-id'),
			index = 0,
			node = '';
			for(var i = 0; i < uncheckedNodes.length; i++){
				if(uncheckedNodes[i].userId == id || uncheckedNodes[i].id == id){
					index = i;
					node = myUtil.cloneObj(uncheckedNodes[i]);
					uncheckedNodes.splice(index, 1);
					checkedNodes.push(node);
					currentAuthorizeUserIds.push(id);
					break;
				}
			}
			var inputs = [];
			$(".users-item-list-sreach").each(function(i, v){
				if(v.value){
					inputs.push($(v));
				}
			})
			if(inputs.length){
				inputs.forEach(function(v, i){
					sreachValue(v);
				})
			}else{
				renderUsersItem(uncheckedNodes, checkedNodes);
			}
		})
		$(".users-checked-list .users-close").off('click').on('click', function(){
			var el = $(this).parent(),
			id = el.attr('data-id'),
			index = 0,
			node = '';
			for(var i = 0; i < checkedNodes.length; i++){
				if(checkedNodes[i].userId == id || checkedNodes[i].id == id){
					index = i;
					node = myUtil.cloneObj(checkedNodes[i]);
					checkedNodes.splice(index, 1);
					currentAuthorizeUserIds.splice(currentAuthorizeUserIds.indexOf(id), 1);
					uncheckedNodes.push(node);
					break;
				}
			}
			$(".users-item-list-sreach").val('');
			renderUsersItem(uncheckedNodes, checkedNodes);
		})
	}
	function sreachValue(input){
		var val = input.val().trim(),
		type = input.attr('data-type'),
		nodes = [],
		nodes1 = [];
		if(val){
			if(type == 'checked'){
				if(checkedNodes && checkedNodes.length){
					checkedNodes.forEach(function(v, i){
						if(v.userName.indexOf(val) > -1 || v.userCde.indexOf(val) > -1){
							nodes1.push(v);
						}
					})
				}
				nodes = myUtil.cloneObj(uncheckedNodes);
			}else{
				if(uncheckedNodes && uncheckedNodes.length){
					uncheckedNodes.forEach(function(v, i){
						if(v.userName.indexOf(val) > -1 || v.userCde.indexOf(val) > -1){
							nodes.push(v);
						}
					})
				}
				nodes1 = myUtil.cloneObj(checkedNodes);
			}
		}else{
			nodes = myUtil.cloneObj(uncheckedNodes);
			nodes1 = myUtil.cloneObj(checkedNodes);
		}
		renderUsersItem(nodes, nodes1);
	}
    function eventItemTab(){
        var tab = $(".authorized-tab"),
        boxs = $(".authorized-tab-pane");
        tab.off("click").on("click", function(){
            var item = $(this),
            id = item.attr("data-id"),
			type = item.attr('data-type');
            if(item.hasClass("active")) return;
            tab.removeClass("active");
            item.addClass("active");
            boxs.removeClass("layui-show");
            $(id).addClass("layui-show");
            renderItemTab(type);
			$('.treebox-scroll').animate({'scrollTop':0},500);
        });
    }
    // 重置表格样式
    function renderItemTab(type){
		switch (type){
			case 'func':
			case 'org':
			case 'datasource':
			case 'model':
				if(!configtrees[type].state) renderRightTree(configtrees[type]);
				break;
			case 'form':
				if(configtrees[type].state){
					if(!$("#authorityTable").attr("data-status")){
						table.resize('authorityTable')
						$("#authorityTable").attr("data-status","1")
					}
				}else{
					fromTree(formIds);
				}
				break;
			case 'analyze':
				if(configtrees[type].state){
					if(!$("#analyzeTable").attr("data-status")){
						table.resize('analyzeTable')
						$("#analyzeTable").attr("data-status","1")
					}
				}else{
					analyzeTree(menuIds);
				}
				break;
		}
    }

    function eventTopbar(){
        form.on('submit(saveRoleinfo)', function (data) {
            F.saveRoleinfo(data);
            return false;
        });
        
        $(".save-role").off("click").on("click", function(){
            $("#saveRoleinfo").trigger("click");
        });
    }
	
	function eventRoleInfo(){
		element.on('tab(roleinfoTab)', function(){
			var type = $(this).attr('data-type');
			if(type == 'users' && !configtrees.user.state){
				renderRightTree(configtrees['user']);
			}
		});
	}
    !function(){ 
        $(".layui-nav-tree").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        $(".tree-box").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        $(".authorized-list.list-box.config-item-list").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
            }
        });
		element.on('tab(roleTab)', function(){
			currentEditState = $(this).attr('data-type');
			$(".save-role").addClass('layui-hide');
			if(currentEditState == 'role'){
				$(".layui-btn-save-role").removeClass('layui-hide');
				$('.orgTab').removeClass('layui-hide');
				$('.layui-form-item-state').removeClass('layui-hide');
				$('.layui-form-item-remark').removeClass('layui-hide');
				$(".roleinfo-item-label").each(function(){
					var text = this.innerHTML;
					text = text.replace(/职务/g, '角色');
					this.innerHTML = text;
				})
				$(".box-roleinfo .layui-card-content").removeClass("layui-height-full-150").addClass("layui-height-full-203");
				if(!setter.roleTreeObj){
					renderTree();
				}else{
					var node = $("#"+roleTreeId+ ' .eleTree-node-content.eleTree-node-content-active');
					if(node[0]){
						node.trigger('click');
					}else{
						$("#"+ roleTreeId +' .eleTree-node').eq(0).children(".eleTree-node-content").trigger("click");
					}
				}
			}else{
				$(".layui-btn-save-post").removeClass('layui-hide');
				$('.orgTab').addClass('layui-hide');
				$('.layui-form-item-state').addClass('layui-hide');
				$('.layui-form-item-remark').addClass('layui-hide');
				$(".roleinfo-item-label").each(function(){
					var text = this.innerHTML;
					text = text.replace(/角色/g, '职务');
					this.innerHTML = text;
				})
				$(".box-roleinfo .layui-card-content").removeClass("layui-height-full-203").addClass("layui-height-full-150");
				if(!setter.postTreeObj){
					renderTree();
				}else{
					var node = $("#"+postTreeId+ ' .eleTree-node-content.eleTree-node-content-active');
					if(node[0]){
						node.trigger('click');
					}else{
						$("#"+ postTreeId +' .eleTree-node').eq(0).children(".eleTree-node-content").trigger("click");
					}
				}
			}
		});
		
        layui.sessionData("configTree", null);
        navbarPushPull();
        eventTreeBar();
        eventTopbar();
        eventItemTab();
		eventRoleInfo();
        initanalyzeTable();
        initauthorityTable();
        renderTree();
    }();
    exports("role", {})
});