; layui.define(["wdUtil", 'admin', "table", "form", "eleTree", 'notice', 'jquery', 'contextMenu', 'pscrollbar', "lay"], function (exports) {
    var $ = layui.jquery
      , laytpl = layui.laytpl
      , element = layui.element
      , setter = layui.setter
      , device = layui.device()
      , form = layui.form
      , myUtil = layui.wdUtil
      , eleTree = layui.eleTree
      , laypage = layui.laypage
      , table = layui.table
	  , cxt = layui.contextMenu
      , pscrollbar = layui.pscrollbar
      , editId = ""
      , searchArr = []
      , rootTable = ""
      , editcheck = []
      , psdErrNumber = "" // 输入密码错误锁定用户次数
      , psdLength = ""// 密码长度
      , psdPeriod = ""// 修改密码期限
      , psdRequest = "" // 忘记密码是否发送请求
      , psdSetting = "" // 密码组合设置
      , treeId = "LAY-org-tree"
	  , storeTableName = 'userInfo'
	  , eleTreeObjList = []
	  , loadTableSize = 50
	  , currentNode = ''
	  , treeWrapSollbar = ''
      , createFloderTemp = '<form action="" class="layui-form" id="LAY-createOrg-form" lay-filter="createOrg-form" autocomplete="off">'
			+' <div class="layui-form-item layui-form-item-sm">'
			    +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>组织编码：</label>'
			    +' <div class="layui-input-block">'
			        +' <input type="text" name="orgCde" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入组织编码" autocomplete="off">'
			    +' </div>'
			+' </div>'
            +' <div class="layui-form-item layui-form-item-sm">'
                +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>组织名称：</label>'
                +' <div class="layui-input-block">'
                    +' <input type="text" name="orgName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入组织名称" autocomplete="off">'
                +' </div>'
            +' </div>'
			+' <div class="layui-form-item layui-form-item-sm">'
			    +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>组织类型：</label>'
			    +' <div class="layui-input-block">'
			        +'<select class="layui-input" name="orgType" id="orgType" lay-filter="orgType"><option value="1">公司</option><option value="2">部门</option></select>'
			    +' </div>'
			+' </div>'
            +'<button id="createOrg-submit" class="layui-hide" lay-submit lay-filter="createOrg">提交</button>'
        +' </form>'
      , resetPsdTemp = '<form action="" class="layui-form" id="LAY-resetPsd-form" lay-filter="resetPsd-form" autocomplete="off">'
        +' <div class="layui-form-item layui-form-item-sm">'
            +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>新密码：</label>'
            +' <div class="layui-input-block">'
                +' <input type="password" name="newPassword" id="newPassword" class="layui-input" required lay-verType="tips" lay-verify="psd" placeholder="请输入新密码" autocomplete="off">'
            +' </div>'
        +' </div>'
        +' <div class="layui-form-item layui-form-item-sm">'
            +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>确认密码：</label>'
            +' <div class="layui-input-block">'
                +' <input type="password" name="realResetUserPassword" class="layui-input" required lay-verType="tips" lay-verify="realPsd" placeholder="请确认密码" autocomplete="off">'
            +' </div>'
        +' </div>'
        +'<button id="resetPsd-submit" class="layui-hide" lay-submit lay-filter="resetPsd">提交</button>'
     +' </form>'
	 , createRoleFloderTemp = '<form action="" class="layui-form" id="LAY-createRole-form" lay-filter="createRole-form" autocomplete="off">'
		       +' <div class="layui-form-item layui-form-item-sm">'
		          +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>{{name}}编码：</label>'
		          +' <div class="layui-input-block">'
		              +' <input type="text" name="roleCde" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入{{name}}编码" autocomplete="off">'
		          +' </div>'
		      +' </div>'
		      +' <div class="layui-form-item layui-form-item-sm">'
		          +' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>{{name}}名称：</label>'
		          +' <div class="layui-input-block">'
		              +' <input type="text" name="roleName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入{{name}}名称" autocomplete="off">'
		          +' </div>'
		      +' </div>'
		      +'<button id="createRole-submit" class="layui-hide" lay-submit lay-filter="createRole">提交</button>'
		  +' </form>'
      , createUserTemp = '<form action="" class="layui-form" id="LAY-createUser-form" lay-filter="createUser-form" autocomplete="off" style="padding: 20px 0;">'
            +'<div class="layui-form-item layui-form-item-sm" style="padding-bottom:15px;border-bottom:1px solid #ddd;padding-left: 20px;">'
                +'<div class="layui-inline" style="margin-right: 30px;">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>用户名：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="userCde" class="layui-input" required lay-verType="tips" lay-verify="required|length30" placeholder="请输入用户名" autocomplete="off">'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>密码：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="password" name="password" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入密码" autocomplete="new-password">'
                    +'</div>'
                +'</div>'
            +'</div>'
            +'<div class="layui-form-item layui-form-item-sm" style="padding-left: 20px;">'
                +'<div class="layui-inline" style="margin-right: 30px;">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>姓名：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="userName" class="layui-input" autocomplete="off" required lay-verType="tips" lay-verify="required|length30">'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>性别：</label>'
                    +'<div class="layui-input-inline">'
                        +'<select name="gender" lay-filter="gender">'
                            +'<option value="1" selected="">男</option>'
                            +'<option value="2">女</option>'
                        +'</select>'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline" style="margin-right: 30px;">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>组织：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="orgName" class="layui-input layui-disabled" disabled="disabled" required lay-verType="tips" lay-verify="required" >'
                        +'<input type="text" name="orgId" class="layui-input layui-hide orgId-input" >'
                        +'<input type="text" name="department" class="layui-input layui-hide department-input" >'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline">'
                    +'<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>职务：</label>'
                    +'<div class="layui-input-inline">'
						+'<select class="layui-input postList" name="position" required lay-verType="tips" lay-verify="required" lay-filter="position"></select>'
                    +'</div>'
					+'<div class="layui-input-inline" style="width:30px;">'
						+'<div class="add-dict-btn" title="新增职务"><i class="iconfont icon-zengjia"></i></div>'
					+'</div>'
                +'</div>'
                +'<div class="layui-inline" style="margin-right: 30px;">'
                    +'<label for="" class="layui-form-label">电子邮件：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="mail" class="layui-input" lay-verify="email"  lay-verType="tips">'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline">'
                    +'<label for="" class="layui-form-label">用工类别：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="employType" class="layui-input">'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline" style="margin-right: 30px;">'
                    +'<label for="" class="layui-form-label">移动电话：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="mobilePhone" lay-verify="phone"  lay-verType="tips" class="layui-input">'
                    +'</div>'
                +'</div>'
                +'<div class="layui-inline">'
                    +'<label for="" class="layui-form-label">固定电话：</label>'
                    +'<div class="layui-input-inline">'
                        +'<input type="text" name="areaCode" class="layui-input layui-input-inline" style="width:60px" lay-verify="areaCode"  lay-verType="tips">'
                        +'<span class="layui-input-inline" style="padding:0 5px;height:30px;line-height:30px;width:5px">-</span>'
                        +'<input type="text" name="telephone" class="layui-input layui-input-inline" style="width:115px;margin-right:0" lay-verify="telephone"  lay-verType="tips">'
                    +'</div>'
                +'</div>'
                +'<input type="hidden" name="orgId" class="layui-input">'
                +'<button id="createUser-submit" class="layui-hide" lay-submit lay-filter="createUser">提交</button>'
            +'</div>'
        +'</form>'
        ,createPage = '<div class="pageCount">'
                            +'<div class="pageCountText"></div>'
                            +'<div id="pageCount"></div>'
                        +'</div>'
      , isSm =  $('body').hasClass("layui-layout-sm-body")
      ,F = {
			createRoleFloder: function(formdatas, index, fn){
			    var url = setter.basePath + 'postController/createPost',
			    ajaxData = {event: "create-md", settings:{"postCode":formdatas.field.roleCde ||"", "postName": formdatas.field.roleName, "pId": "0"}}, 
			    callback= function(data){
			        if(data){
						fn();
			            layer.close(index)
			            layui.admin.removeLoading('body', true, true);
			        }
			        layui.admin.removeLoading("body",true,true)
			    };
			    layui.admin.showLoading("body",2,".8")
			    myUtil.send(url, ajaxData, callback);
			},
            createFloder: function(formdatas, index){
                var url = setter.basePath + "orgController/saveOrg",
                ajaxData = {event: "create-md", settings:{"orgCde":formdatas.field.orgCde ||"", "orgName": formdatas.field.orgName, "orgType": formdatas.field.orgType||"", "pId": (currentNode && currentNode.id) || "0"}}, 
                callback= function(data){
                    if(data){
						var newNode = {
							id: data.obj.treeNodes[0].id,
							parentId: data.obj.treeNodes[0].pId,
							type: data.obj.treeNodes[0].type == "DEPARTMENT" ? '2': '1',
							name: data.obj.treeNodes[0].name,
							orgId: data.obj.treeNodes[0].orgId,
							orgCde: formdatas.field.orgCde,
							iconSkin: data.obj.treeNodes[0].type == "DEPARTMENT" ? 'iconfont icon-gongsi file' : 'iconfont icon-bumen3 file',
							children: '',
						}
						if(currentNode.id){
							var treeObj = jQuery.fn.zTree.getZTreeObj("LAY-org-tree");
							newNode = treeObj.addNodes(currentNode, -1, newNode);
							$("#"+newNode[0].tId + '> a').trigger('click');
						}else{
							renderTree(newNode.id);
						}
                        myUtil.msg({type: "success", message: data.msg});
                        layer.close(index)
                    }
                };
				if(ajaxData.settings.orgType == '2' && ajaxData.settings.pId == '0'){
					myUtil.msg({type:'warning', message: '新建部门需选中组织!'});
					return false;
				}
                myUtil.send(url, ajaxData, callback);
                layui.admin.removeLoading('body', true, true);
            },
            createUser: function(formdatas, index){
                var url = setter.basePath + "userController/saveUser",
                ajaxData = {
                    "event":"create-md",
                    "settings":{
                        "userCde":formdatas.field.userCde,
                        "orgId":formdatas.field.orgId,
                        "password":formdatas.field.password,
                        "gender":formdatas.field.gender||"",
                        "userName":formdatas.field.userName||"",
                        "department":formdatas.field.department||"",
                        "position":formdatas.field.position||"",
                        "mail":formdatas.field.mail||"",
                        "employType":formdatas.field.employType || "",
                        "mobilePhone":formdatas.field.mobilePhone||"",
                        "telephone":formdatas.field.telephone||"",
                        "areaCode":formdatas.field.areaCode||"",
                        "pId":formdatas.field.nodeId
                    }
                }, 
                callback= function(data){
                    if(data){
                        F.select(currentNode);
                        myUtil.msg({type: "success", message: data.msg});
                        layer.close(index)
                    }
                };
				if(!formdatas.field.position){
					myUtil.msg({type: 'warning', message: '请选择职务,如没有职务请先增加职务!'});
					layui.admin.removeLoading('body', true, true);
					return false;
				}
                myUtil.send(url, ajaxData, callback);
                layui.admin.removeLoading('body', true, true);

            },
            saveFolder: function(formdatas){
                var url = setter.basePath + "orgController/updateOrg",
                ajaxData = {"event":"update-md","settings":{"orgName":formdatas.field.orgName,"orgCde":formdatas.field.orgCde,"orgId":formdatas.field.orgId, "pId": formdatas.field.orgPid}}, 
                callback= function(data){
                    if(data){
                        renderTree(currentNode.id);
                        myUtil.msg({type: "success", message: data.msg});
                    }
                };
                myUtil.send(url, ajaxData, callback);
            },
            resetPsd: function(formdatas, index){
                var info = $(".box-userinfo").data("userinfo"),
                url = setter.basePath + "userController/resetPassword",
                ajaxData = {"event":"update-md","userId":info.userId,"newPassword":formdatas.field.newPassword},
                callback = function(data){
                    if(data){
                        myUtil.msg({type: "success", message: data.msg});
                        if(data){
                            layer.close(index)
                        }
                    }
                    layui.admin.removeLoading('body', true, true);
                }
                myUtil.send(url, ajaxData, callback);
            },
            deleteUser: function(nodes){
                var url = setter.basePath+"userController/deleteUser",
                ajaxData = {"event":"delete-md", settings:{ids:[]}},
                names = [],
                callback = function(data){
                    if(data){
						if(editId == '0'){
							$("#clickBar").trigger('click');
						}else{
							if(currentNode){
								F.select(currentNode);
							}
						}
                        myUtil.msg({type: "success", message: data.msg});
                        
                    }
                };
                if(nodes.length){
                    nodes.forEach(function(v, i){
                        ajaxData.settings.ids.push(v.userId);
                        names.push(v.userCde);
                    });
                    names = names.join(",");
                    
                    layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+names+'</span>吗？',{icon: 3, title:'删除用户',skin: "layui-layer-admin"},
                        function(index, layero){
                            myUtil.send(url, ajaxData, callback);
                            setTimeout(function(){
                                layer.close(index)
                            },500)
                            layui.admin.removeLoading('body', true, true);
                            layer.close(index)
                    });
                }else{
                    myUtil.msg({type:"warning",message:"请选择要删除的用户！"})
                }
            },
            saveUserinfo: function(formdatas){
                var info = $(".box-userinfo").data("userinfo"),
                url = setter.basePath + "userController/updateUser",
				treeObj = jQuery.fn.zTree.getZTreeObj('LAY-role-right-tree'),
                checks = treeObj.getCheckedNodes(true),
                ajaxData = {"event":"update-md",
                "settings":{
                    "orgId":info.orgId,
                    "islocked":info.islocked,
                    "isactive":formdatas.field.isactive||"",
                    "userName":formdatas.field.userName||"",
                    "gender":formdatas.field.gender||"",
                    "department":formdatas.field.department||"",
                    "position":formdatas.field.position||"",
                    "mail":formdatas.field.mail||"",
                    "employType":formdatas.field.employType || "",
                    "mobilePhone":formdatas.field.mobilePhone||"",
                    "telephone":formdatas.field.telephone||"",
                    "areaCode":formdatas.field.areaCode||"",
                    "thirdPartyCode":formdatas.field.thirdPartyCode||"",
                    "roles": [],
                    "userId":info.userId,
                    "userCde":info.userCde,
                    "userType": info.userType
                }}, 
                callback= function(data){
                    if(data){
						if(currentNode){
							F.select(currentNode);
						}else if($("#clickBar").hasClass('active')){
							$("#clickBar").trigger('click');
						}
                        myUtil.msg({type: "success", message: data.msg});
                    }
                };
                if(ajaxData.phone){
                    $("#phone".attr({
                        "lay-verType":"tips",
                        "lay-verify":"phone"
                    }))
                }
                if(checks && checks.length){
                    checks.forEach(function(v, i){
                        ajaxData.settings.roles.push(v.id);
                    });
                }
                if(myUtil.isSame(info, ajaxData.settings)){
                    F.tab("");
                }else{
                    myUtil.send(url, ajaxData, callback);
                }
            },
            select: function(node){
				var orgObj = {orgId: '', name: ''};
				currentNode = node;
				editId = currentNode.orgId;
				if(node.type == '1'){
					$(".orgCde-name-txt").text('公司');
					$(".layui-btn-cardTop.add-user").addClass('layui-hide');
				}else{
					function getParent(pnode){
                        if(!pnode) return false;
						if(pnode.type == '1'){
							return pnode;
						}else {
							return getParent(pnode.getParentNode());
						}
					}
					orgObj = getParent(node);
					$(".orgCde-name-txt").text('部门');
					$(".layui-btn-cardTop.add-user").removeClass('layui-hide');
				}
                var main = $(".box-main"),
                folder = $(".box-folder"),
                url = setter.basePath + "userController/allUserDetailed",
                ajaxData = {
                    "orgId": node.orgId,
                    "search":"",
                    "page": $(".box-folder .layui-laypage-skip-input")[0] ? $(".box-folder .layui-laypage-skip-input").val() : "1",
                    "size": $(".layui-laypage-limits-input")[0] ? $(".layui-laypage-limits-input").val() :loadTableSize,
					'type': node.type
                },
                index = 0,
                callback = function(data){
                    if(data.obj){
                        if(node.type == "ROOT"){
                            $(".total-user").text(data.obj.totalUser);
                            $(".disable-user").text(data.obj.disableUser);
                            $(".active-user").text(data.obj.activeUser);
                            $(".lock-user").text(data.obj.lockedUser);
                            $("#imp").removeClass("impactive")
                        }else{
                            var parentNode = node.getParentNode() || {name: "无", orgId: "0"};
                            form.val("folder-form",{orgName: node.name, orgCde: node.orgCde, orgId: node.orgId, nodeId: node.id, orgPid: parentNode.orgId, orgPname: parentNode.name});
                            initTable(node,data)
                            PageCount(node,data)
                        }
                    }
                    layui.admin.removeLoading('body', true, true);
                };
                F.tab(node.type);
                layui.admin.showLoading('', 2, '.8')
                myUtil.send(url, ajaxData, callback);
            },
            tab: function(type){
                var main = $(".box-main"),
                folder = $(".box-folder"),
                user = $(".box-userinfo");
                if(type == "ROOT"){
                    main.removeClass("layui-hide");
                    folder.addClass("layui-hide");
                    user.addClass("layui-hide");
                    main.removeClass("layui-hide");
                    $(".impWrap").addClass("layui-hide")
                    $(".card1").removeClass("layui-height-full")
                    $(".cardWrap").removeClass("layui-hide")
                    $(".card2").removeClass("layui-hide")
                }else if(type == "USER"){
                    main.addClass("layui-hide");
                    folder.addClass("layui-hide");
                    user.removeClass("layui-hide");
                    $(".impWrap").addClass("layui-hide")
                    $(".topWrap").removeClass("layui-height-full")
                    $(".layui-form").removeClass("layui-hide")
                    $(".card3").removeClass("layui-hide")
                }else if(type == "close"){
                    if(editId == "0"){
                        main.removeClass("layui-hide");
                        folder.addClass("layui-hide");
                        user.addClass("layui-hide");
                    }else{
                        main.addClass("layui-hide");
                        folder.removeClass("layui-hide");
                        user.addClass("layui-hide");
                    }
                }else{
                    main.addClass("layui-hide");
                    folder.removeClass("layui-hide");
                    user.addClass("layui-hide");
                    $(".impWrap").addClass("layui-hide")
                    $(".topWrap").removeClass("layui-height-full")
                    $(".layui-form").removeClass("layui-hide")
                    $(".card3").removeClass("layui-hide")
                }
            },
			loadPostList(fn){
				var url = setter.basePath + 'postController/queryPostList',
				callback = function(data){
					if(data){
						var postList = data.obj.treeNodes || [];
						fn(postList);
					}else{
						fn([]);
					}
				};
				
				myUtil.send(url, {}, callback);
			},
			/**
			 * 渲染对话框树
			 * @method renderDialogTree
			 * @for DesignerManage
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
			 */
			renderDialogTree: function(id, left, top, url, ajaxData, nodeId, callback, key, treeData, removeBefore, myrequest, noeach) {
				var treeTemp = '<div class="dialog-tree-box"><div class="search-box layui-form-item-sm"><input id="LAY-' + id +
					'-tree-search" class="layui-input" autocomplete="off"></div><div class="tree-box treebox-scroll"><ul class="layui-mylayui1-tree" id="LAY-' +
					id + '-tree" lay-filter="lay-' + id + '-tree"></ul></div></div>',
					treeStore = layui.sessionData(storeTableName)[id],
					ajaxData = ajaxData || {
						data: 'false'
					},
					request = myrequest || {
								name: "name",
								id: "id"
							},
					date = new Date().getTime();
				cxt.show(treeTemp, left, top, true, removeBefore);
				layui.admin.showLoading('.dialog-tree-box', 2, '.8');
				var url = setter.basePath + url,
					box = $(".dialog-tree-box .tree-box"),
					eventTree = function(treeDatas) {
						var treeObj = {
							elem: '#LAY-' + id + '-tree',
							data: treeDatas,
							expandOnClickNode: false,
							highlightCurrent: true,
							renderAfterExpand: false,
							defaultExpandAll: true,
							request: request,
							leafIcon: setter.leafIcon,
							searchNodeMethod: function(value, data) {
								if (!value) return false;
								return data.name.indexOf(value) !== -1;
							}
						}
						if (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') {
							treeObj.showCheckbox = true;
							treeObj.checkOnClickNode = true;
						}
						if (treeData && treeData.treeIds && treeData.treeIds.length) {
							treeObj.defaultCheckedKeys = treeData.treeIds;
						}
						eleTreeObjList[id] = eleTree.render(treeObj);
						eleTree.on("nodeClick(lay-" + id + "-tree)", function(d) {
							if (callback) callback(d.data.currentData);
						});
						$("#LAY-" + id + '-tree-search').off("click").on("click", function(e) {
							var evt =  window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						})
						$("#LAY-" + id + '-tree-search').off("change").on("change", function() {
							eleTreeObjList[id].search($(this).val());
						});
						if (box && !box.hasClass("ps")) {
							new pscrollbar(box[0]);
							box.addClass("treebox-scroll");
							box.find('.ps__rail-x').on('click', function(e) {
								var evt =  window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							});
							box.find('.ps__rail-y').on('click', function(e) {
								var evt =  window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							});
						}
						if (nodeId) {
							$('#LAY-' + id + '-tree .eleTree-node[data-id="' + nodeId + '"]').children(".eleTree-node-content").addClass(
								"eleTree-node-content-active");
							$('#LAY-' + id + '-tree .eleTree-node[data-id="' + nodeId + '"]')[0].scrollIntoView();
						}
						layui.admin.removeLoading('.dialog-tree-box', true, true);
					};
				if (treeData && treeData.treeNodes) {
					eventTree(treeData.treeNodes);
				} else if (treeStore && (date - parseInt(treeStore.time)) < 1800000 && !noeach) {
					eventTree(treeStore.treeDatas);
				} else {
					myUtil.send(url, ajaxData, function(data) {
						if (data) {
							var treeNodes = key ? key == "none" ?data.obj : data.obj[key] : data.obj.treeNodes;
							layui.sessionData(storeTableName, {
								key: id,
								value: {
									treeDatas: treeNodes,
									time: new Date().getTime()
								}
							});
							eventTree(treeNodes);
						}
					});
				}
			},
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
			 */
            renderDialogzTree: function renderDialogzTree(id, el, evt, url, ajaxData, nodeId, callback, key, treeData, removeBefore, datakeys, noeach){
				if($('.ctxMenu .dialog-tree-box')[0]){
					$('.ctxMenu .dialog-tree-box').parent().remove();
				}
				if($('.layui-laydate')[0]){
					$('.layui-laydate').remove();
				}
				if($(".seltree-box")[0]){
					$(".seltree-box").remove();
				}
				if($(".seltree")[0]){
					$(".seltree").remove();
				}
				var isSelectCheckbox = (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') ? true: false,
				treeTemp =  '<div class="seltree-box"><div class="seltree '+(treeData.theme || "")+'"><div class="dialog-tree-box">'+
						'<div class="xm-select-tips '+(isSelectCheckbox ? '': 'layui-hide')+'"><div class="xm-cz-group"><div class="xm-cz close-cxt" data-type="close" title="完成"><span>完成</span></div><div class="xm-cz" data-type="all" title="全选"><i class="iconfont icon-select-all"></i><span>全选</span></div><div class="xm-cz" data-type="none" title="清空"><i class="iconfont icon-quxiaoquanxuan"></i><span>清空</span></div></div></div>'+
						'<div class="search-box layui-form-item-sm search-fillet-box"><input id="LAY-'+id+'-tree-search" class="layui-input" placeholder="请输入内容搜索" autocomplete="off"><i id="LAY-'+id+'-tree-search-btn" class="iconfont icon-tongyongchaxun"></i></div><div class="ztree-box treebox-scroll" style="'+(isSelectCheckbox ? 'top:70px':'top:40px')+'"><ul class="lay-ztree ztree" id="LAY-'+id+'-tree" lay-filter="lay-'+id+'-tree"></ul></div><div class="btns"style="display:none"><button type="button" class="layui-btn layui-btn-xs yes1">确定</button><button type="button" style="margin-right: 15px;" class="layui-btn layui-btn-primary no1">取消</button></div><span class="moveItem_resize"></span></div></div></div>',
				treeStore = layui.sessionData(storeTableName)[id],
				ajaxData = ajaxData || {data: 'false'},
				date = new Date().getTime();
				$("#LAY_app").append(treeTemp)
				layui.lay.position(el, $(".seltree")[0], {
					position: null,
					e: evt,
					clickType: null,
					align: null
				});
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
						callback: {
							onBeforeFuzzy: function () {
								layui.admin.showLoading('#LAY-' + id + '-tree', 2, '.8');
							},
							onAfterFuzzy: function () {
								var ele = document.getElementById('LAY-' + id + '-tree')
								ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
								layui.admin.removeLoading('#LAY-' + id + '-tree', true, true);
							},
							onClick: function(event, treeId, treeNode){
								if(callback) callback('click', treeId, treeNode)
							}
						}
					};
					if(datakeys){
						ztreeobj.data = datakeys;
					}
					if(treeData && treeData.checkbox && (treeData.checkbox + "") === 'true'){
						$('.dialog-tree-box .ztree-box').css('bottom','39px')
						$('.btns').css('display','block')
						ztreeobj.check = {enable: true, chkStyle: 'checkbox'};
						ztreeobj.callback['onCheck'] = function(event, treeId, treeNode){
							if(callback) callback('check', treeId, treeNode);
						}
					}else{
						$('.dialog-tree-box .ztree-box').css('bottom','0px')
						$('.btns').css('display','none')
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
					if(treeData.expandAll){
						treeObj.expandAll(true)
					}
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
						}
					})
					$(".seltree-box").off("click").on("click", function(e){
						if(e.target.className == "seltree-box"){
							if(removeBefore) removeBefore();
							$(".seltree-box").remove();
						}
					})
					$('.seltree-box .no1').off('click').on('click', function (e) {
						$(".seltree-box").remove();
					})
					
					$('.seltree-box .yes1').off('click').on('click', function (e) {
						if(callback) callback("submit", '', '');
						$(".seltree-box").remove();
					})
					$(".seltree-box .seltree .moveItem_resize").bind("mousedown", function(e1){
						var _this = $(this).parents(".seltree");
						var treebox = _this.find(".dialog-tree-box");
						var oldpageX = e1.pageX;
						var oldpageY = e1.pageY;
						var oldWidth = parseInt(_this.css("width"));
						var oldHeight = parseInt(_this.css("height"));
						$(document).bind("mousemove.seltree", function(e2){
							if((e2.pageX - oldpageX + oldWidth) > 252 && (e2.pageY - oldpageY + oldHeight) > 332){
								_this.css("width", e2.pageX - oldpageX + oldWidth + "px");
								_this.css("height", e2.pageY - oldpageY + oldHeight + "px");
								treebox.css("width", e2.pageX - oldpageX + oldWidth + "px");
								treebox.css("height", e2.pageY - oldpageY + oldHeight + "px");
							}
						});
						$(document).bind("mouseup.seltree", function(e2){
							$(document).unbind("mousemove.seltree");
							$(document).unbind("mouseup.seltree");
						});
					});
					fuzzySearch ( 'LAY-'+id+'-tree', "#LAY-" + id+'-tree-search', false, true );
					if(box && !box.hasClass("ps")){
						new pscrollbar(box[0]);
						box.addClass("treebox-scroll");
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
						treeObj.selectNode(treeObj.getNodeByParam((treeData.selectNodeKey || 'id'), nodeId, null));
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
                title: "新建组织结构",
                id: "LAY-admin-createOrg",
                content: createFloderTemp,
                success: function(layero, index){
					if(currentNode && currentNode.type == '2'){
						$("#orgType").addClass("layui-disabled").attr({"disabled": 'disabled', 'lay-verify': ''});
						form.render(null, 'createOrg-form');
						form.val('createOrg-form', {
							orgType: '2'
						})
						$("#orgType").next().find('.layui-input').addClass("layui-disabled").attr({"disabled": 'disabled', 'lay-verify': ''});
					}else{
						form.render(null, 'createOrg-form');
					}
                    form.on('submit(createOrg)', function (data) {
                        F.createFloder(data, index);
                        return false;
                    });
                },
                yes: function(index, layero){
                    $("#createOrg-submit").trigger("click");
                }
            });
        });
        $("#LAY-tree-remove").off("click").on("click", function(){
            if(currentNode.id && currentNode.id != "0"){
                layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+currentNode.name+'</span>吗？',{icon: 3, title:'删除组织', skin: "layui-layer-admin"},
                    function(index, layero){
                        var url = setter.basePath + "orgController/deleteOrgs",
                        ajaxdata = {"event":"delete-md", settings:{ids:[(currentNode.orgId || currentNode.id)]}},
                        callback = function(data){
                            if(data){
                                myUtil.msg({type: "success", message: data.msg});
								var treeObj = jQuery.fn.zTree.getZTreeObj("LAY-org-tree"),
								tId = '';
								if(currentNode.getNextNode()){
									tId =currentNode.getNextNode().tId;
								}else if(currentNode.getPreNode()){
									tId =currentNode.getPreNode().tId;
								}else if(currentNode.getParentNode()){
									tId =currentNode.getParentNode().tId;
								}else {
									$('#clickBar').trigger('click');
								}
                                treeObj.removeNode(currentNode);
								$("#"+tId+' >a').trigger('click');
                                layui.admin.removeLoading('body', true, true);
                                layer.close(index)
                            }
                        };
                        myUtil.send(url, ajaxdata, callback);
                    }
                );
            }else{
                myUtil.msg({type: "warning", message: '请选择需要删除的组织节点！'});
            }
        });
        $("#LAY-tree-openSearch").off("click").on("click", function(){
            $(".layui-nav-searchbox").removeClass("layui-hide");
        });
        $("#LAY-tree-closeSearch").off("click").on("click", function(){
            $(".layui-nav-searchbox").addClass("layui-hide");
            $("#LAY-org-tree-search").val("");
			fireKeyEvent($("#LAY-org-tree-search")[0], 'keydown', 13);
			
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
		
    }
    
    /**
     * 左侧树初始化
     * @method renderTree
     * @for UserManage
     */
    function renderTree(nodeId){
        var url = setter.basePath + "orgController/orgTreeList",
        index = 0,
        callback = function(data){
            if(data.success){ 
                if(data.obj){
                    var treeDatas = data.obj,
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
                        callback: {
                            onBeforeFuzzy: function () {
                                layui.admin.showLoading('#LAY-org-tree', 2, '.8');
                            },
                            onAfterFuzzy: function () {
                                var ele = document.getElementById('LAY-org-tree')
                                ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
                                layui.admin.removeLoading('#LAY-org-tree', true, true);
                            },
                            onClick: function(event, treeId, treeNode){
                                $("#allUserSearchInput").val("");
                                $("#allSearchInput").val("");
                                $(".layui-laypage-limits-input").val(loadTableSize);
                                $(".box-folder .layui-laypage-skip-input").val('1');
                                F.select(treeNode);
                                $("#clickBar").removeClass("active")
                            }
                        }
                    };
                    eachCheckNode(treeDatas, [], false);
                    var treeObj = jQuery.fn.zTree.init($('#LAY-org-tree'), ztreeobj, treeDatas);
                    fuzzySearch ( 'LAY-org-tree', '#LAY-org-tree-search', false, true );
                    treeObj.expandAll(false);
                    
                    if(nodeId){
                        var node = treeObj.getNodeByParam('id', nodeId, null);
                        if(node){
                            $(".box-folder .layui-laypage-skip-input").val('1');
                            treeObj.selectNode(node);
                            $("#"+node.tId +' >a').trigger('click');
                        }
                    }else{
                        $(".box-main .layui-laypage-skip-input").val('1');
                        $("#clickBar").trigger('click');
                    }
                    treeWrapSollbar.update();
                    
                }
				function eachCheckNode(nodes, ids, last){
					if(nodes.length){
						nodes.forEach(function(v, i){
							v.iconSkin = v.type == "1"? 'iconfont icon-gongsi file' : 'iconfont icon-bumen3 file';
							if(v.children && v.children.length){
								eachCheckNode(v.children, ids);
							}
						})
					}
				}
            }
            layui.admin.removeLoading('body', true, true);
            layui.admin.removeLoading('', true, true);
        };
        layui.admin.showLoading('', 2, '.8')
        myUtil.send(url, {}, callback);
    }
    /**
     * 渲染用户详情
     * @method renderInfo
     * @for UserManage
     */
    function renderInfo(userId,editId){
        F.tab("USER");
        var url = setter.basePath + "userController/userDetailed",
        index = 0,
        callback = function(data){
            if(data){
                $("#userinfo-form")[0].reset();
				var postList = layui.sessionData('postList').list,
                userinfo = data.obj;
				if(!userinfo.department && !userinfo.departmentName){
					userinfo.departmentName = currentNode.name || '';
					userinfo.department = currentNode.orgId;
				}else if(userinfo.department && !userinfo.departmentName){
					var treeObj = jQuery.fn.zTree.getZTreeObj("LAY-org-tree"),
					node = treeObj.getNodeByParam('id', userinfo.department, null);
					userinfo.departmentName = node ? node.name :'';
				}
                
				layui.admin.showLoading('#userinfo-form .layui-tab-content', 2, '.8');
				function runfn(){
					
					F.loadPostList(function(postList){
						layui.admin.removeLoading('#userinfo-form .layui-tab-content', true, true)
						var options = [];
						if(postList && postList.length){
							postList.forEach(function(v, i){
								options.push('<option value="'+v.postId+'">'+v.name+'</option>');
							})
						}
						$("#postList-select").html(options.join(""));
						$(".department-select-input").attr('title', userinfo.departmentName).attr('data-id', userinfo.department).attr('data-orgId', userinfo.department).children('.layui-model-node').text(userinfo.departmentName);
						form.val("userinfo-form" ,userinfo);
						$(".department-select-input").off("click").on('click', function(e){
                       
							var evt =  window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
							var that = $(this),
								url = 'orgController/orgTreeList',
								nodeId = that.attr("data-id"),
                                treeNodes = { expandAll: true,icons: {file: "iconfont eletree-icon-normal-file ","2": "iconfont icon-bumen3 ","1": "iconfont icon-gongsi "}},
                                callback = function (type, treeId, treeNode) {
                                    event.stopPropagation();
                                    if(type == 'click'){
										if (treeNode.type == "2") {
											var cid = that.attr("data-id"),
												name = that.attr("title");
											if (cid && cid != treeNode.id) {
												loadModelTree(treeNode);
											} 
								
											function loadModelTree(node) {
												var info = $(".box-userinfo").data("userinfo");
												info.orgId = getOrgId(node);
												that.attr('title', node.name);
												that.find(".layui-model-node").text(node.name);
												that.attr('data-orgId', node.orgId);
												that.attr('data-id', node.id);
												$(".department-input").val(node.orgId);
												
											}
											function getOrgId(node){
												var parent = node.getParentNode();
												if(parent.type == '2'){
													return getOrgId(parent);
												}else{
													return parent.orgId;
												}
											}
                                            $(".seltree-box").remove();
										}else{
                                            myUtil.msg({type:"warning",message:"请选择部门！"})
                                        }
									}
                                }
                                F.renderDialogzTree("userInfoDepartMent", that[0], e, url, {}, nodeId, callback, "none", treeNodes, "", "", true);
                                
						})
						$(".box-userinfo").data("userinfo", userinfo);
						$(".userinfo-userCde").text(userinfo.userCde);
						if(userinfo.islocked == "0"){
							$(".unlockUser").removeClass("layui-hide");
							$(".userinfo-lock").text("锁定");
						}else{
							$(".unlockUser").addClass("layui-hide");
							$(".userinfo-lock").text("正常");
						}
						setTimeout(function(){
							renderRightTree(userinfo.roles);
						}, 500);
					});
				}
				runfn();
				$(".add-dict-btn").off('click').on('click', function(){
					var temp = createRoleFloderTemp.replace(/{{name}}/g, '职务');
					layui.admin.open({
					    title:  '新建职务',
					    id: "LAY-admin-createRole",
					    content: temp,
					    success: function(layero, index){
					        form.render();
					        form.on('submit(createRole)', function (data) {
					            F.createRoleFloder(data, index, runfn);
					            return false;
					        });
					    },
					    yes: function(index, layero){
					        $("#createRole-submit").trigger("click");
					    }
					});
				});
            }else{
                F.tab("FOLDER");
            }
            layui.admin.removeLoading('body', true, true);
            layui.admin.removeLoading('', true, true);
        }
        layui.admin.showLoading('', 2, '.8')
        myUtil.send(url, {userId: userId}, callback);
    }
    function rootInitTable(node,$data){
        if(!$data) $data = []
        rootTable = table.render({
            elem: "#allUserList",
            cellMinWidth: 80,
            height: isSm ? "full-290" : "full-298",
            cols: [[
                {type: "checkbox"},
                {field:"userCde", title:"用户名",align: "center",unresize: true, sort: false,style:"font-size:12px"},
				{field:"departmentName", title:"部门",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "userName", title: "姓名",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "loginInformation", title: "登录",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "mail", title: "邮件",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "status", title: "状态", templet: "#statusTemp", unresize: true, sort: false,align: "center",style:"font-size:12px"},
                {field: "operation", title: "操作", templet: "#userbar", width: 80,unresize: true, sort: false,align: "center",style:"font-size:12px"}
            ]],
            data: $data.obj.lists,
            id:"allUserList",
            limit:$data.obj.lists.length,
            skin: "nob",
            even: "true",
            
        });
       
        table.on('tool(allUserList)', function(obj){
            if(obj.event === 'edit'){
                renderInfo(obj.data.userId,'0');
            }
        });
        
        $('#allUserSearch').data("list", $data);
        $('#allUserSearch').off("click").on('click', function(){

            var value = $("#allUserSearchInput").val().trim()
            var url = setter.basePath + "userController/allUserDetailed",
            ajaxData = {
                "size": $(".box-main .layui-laypage-limits-input")[0] ? $(".box-main .layui-laypage-limits-input").val() : loadTableSize,
                "page": "1",
                "search": value,
                "orgId" : node.orgId,
            },
            callback = function(data) {
                if(data.obj){
                    rootInitTable(node,data);
                    rootPageCount(node,data)
                }
                layui.admin.removeLoading('body', true, true);
            };
            layui.admin.showLoading('body', 2, '.8')
            myUtil.send(url, ajaxData, callback);
            
        });
        $(".layui-table-body").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        $('#clickBar').off("click").on('click', function(){
            $("#LAY-org-tree .curSelectedNode").removeClass("curSelectedNode")
            var node = {"orgId": '0','id':'0',"type":'ROOT'}  
            editId = '0';
			currentNode = '';
            F.tab('ROOT');
            $(this).addClass("active")
			$("#allUserSearchInput").val("");
			$("#allSearchInput").val("");
            Initialization(node)
        });
    }
     // 分页
    function rootPageCount(node,$data){
        laypage.render({
            elem: 'pageCount',
            count: $data.obj.totalElements,
            curr: $data.obj.page,
            limits: [$data.obj.size || loadTableSize],limitType: 'auto',
            limit: $data.obj.size || loadTableSize,
            layout: ['limit', 'skip' , 'prev', 'page', 'next'],
            prev:"<",
            next:">",
            jump: function(obj, first) {
                if(!first){
                    $data.obj.page = obj.curr;
                    $data.obj.size = obj.limit;
                    RootupdateList(node,$data)
                }
                if($data.obj.totalElements/$data.obj.size <= 0){
                    $data.obj.totalPagets = 1
                }else{
                    $data.obj.totalPagets = parseInt($data.obj.totalElements/$data.obj.size)+1
                }
                $(".pageCount").find(".pageCountText").text("显示 第 " + (($data.obj.size * ($data.obj.page - 0 - 1)) < 0 ? 0 :$data.obj.size * ($data.obj.page - 0 - 1) + 1) + " 到 " + $data.obj.size * $data.obj.page + " 条 共 " + $data.obj.totalPagets + " 页 共 " + $data.obj.totalElements + " 条");
                
            }
        });
    }
    function RootupdateList(node,$data) {
        var url = setter.basePath + "userController/allUserDetailed",
            ajaxData = {
                "size": $data.obj.size,
                "page": $data.obj.page,
                "search": $("#allUserSearchInput").val().trim(),
                "orgId" : node.orgId,
            },
            callback = function(data) {
                if(data.obj){
                    rootInitTable(node,data);
                }
                layui.admin.removeLoading('body', true, true);
            };
        layui.admin.showLoading('body', 2, '.8')
        myUtil.send(url, ajaxData, callback);
    };

    function initTable(node,$data){
        if(!$data) $data = [];
        table.render({
            elem: "#userList",
            cellMinWidth: 80,
            height: isSm ? "full-260" : "full-263",
            cols: [[
                {type: "checkbox"},
                {field:"userCde", title:"用户名",align: "center",unresize: true, sort: false,style:"font-size:12px"},
				{field:"departmentName", title:"部门",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "userName", title: "姓名",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "mail", title: "邮件",align: "center",unresize: true, sort: false,style:"font-size:12px"},
                {field: "status", title: "操作", templet: "#userbar", width: 80,align: "center",unresize: true, sort: false,style:"font-size:12px"}
            ]],
            data: $data.obj.lists,
            id:"userList",
            skin: "nob",
            even: "true",
            limit:$data.obj.lists.length

        });
        $('#allSearch').off("click").on('click', function(){
            var value = $("#allSearchInput").val().trim()
            var url = setter.basePath + "userController/allUserDetailed",
            sizeInput = $(".clickBar").hasClass("active") ? $(".box-main .layui-laypage-limits-input") : $(".box-folder .layui-laypage-limits-input"),
            ajaxData = {
                "size": sizeInput[0] ? sizeInput.val() : loadTableSize,
                "page": "1",
                "search": value,
                "orgId" : node.orgId,
				"type": node.type
            },
            callback = function(data) {
                if(data.obj){
                    initTable(node,data);
                    PageCount(node,data)
                }
                layui.admin.removeLoading('body', true, true);
            };
            layui.admin.showLoading('body', 2, '.8')
            myUtil.send(url, ajaxData, callback);
            
        });
        $(".layui-table-body").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
        table.on('tool(userList)', function(obj){
            if(obj.event === 'edit'){
                renderInfo(obj.data.userId,editId);
            }
        });
    }
    
    function Initialization(node){
        var url = setter.basePath + "userController/allUserDetailed",
        ajaxData = {
            "orgId": '0',
            "search":$("#allUserSearchInput").val().trim(),
            "page":$(".box-main .layui-laypage-skip-input")[0] ? $(".box-main .layui-laypage-skip-input").val() : "1",
            "size": loadTableSize,
        },
        index = 0,
        callback = function(data){
            if(data.obj){
                $(".total-user").text(data.obj.totalUser);
                $(".disable-user").text(data.obj.disableUser);
                $(".active-user").text(data.obj.activeUser);
                $(".lock-user").text(data.obj.lockedUser);
                rootInitTable(node,data)
                rootPageCount(node,data)
            }
			layui.admin.removeLoading('body', true, true)
        };
        F.tab("ROOT");
        layui.admin.showLoading('body', 2, '.8')
        myUtil.send(url, ajaxData, callback);
        var node = {"orgId": '0','id':'0',"type":'ROOT'}  
            editId = '0'
            // F.select(node)
    }
    
     // 分页
    function PageCount(node,$data){
        laypage.render({
            elem: 'pageCounts',
            count: $data.obj.totalElements,
            curr: $data.obj.page,
            limits: [$data.obj.size || loadTableSize],limitType: 'auto',
            limit: $data.obj.size || loadTableSize,
            layout: ['limit', 'skip' , 'prev', 'page', 'next'],
            prev:"<",
            next:">",
            jump: function(obj, first) {
                if(!first){
                    $data.obj.page = obj.curr;
                    $data.obj.size = obj.limit;
                    updateList(node,$data)
                }
                if($data.obj.totalElements/$data.obj.size <= 0){
                    $data.obj.totalPagets = 1
                }else{
                    $data.obj.totalPagets = parseInt($data.obj.totalElements/$data.obj.size)+1
                }
                $(".pageCount").find(".pageCountText").text("显示 第 " + (($data.obj.size * ($data.obj.page - 0 - 1)) < 0 ? 0 :$data.obj.size * ($data.obj.page - 0 - 1) + 1) + " 到 " + $data.obj.size * $data.obj.page + " 条 共 " + $data.obj.totalPagets + " 页 共 " + $data.obj.totalElements + " 条");
                
            }
        });
    }
    function updateList(node,$data) {
        var url = setter.basePath + "userController/allUserDetailed",
            ajaxData = {
                "size": $data.obj.size,
                "page": $data.obj.page,
                "search": $("#allSearchInput").val().trim(),
                "orgId" : node.orgId,
				"type": node.type
            },
            callback = function(data) {
                if(data.obj){
                    initTable(node,data);
                }
                layui.admin.removeLoading('body', true, true);
            };
        layui.admin.showLoading('body', 2, '.8')
        myUtil.send(url, ajaxData, callback);
    };
    function renderRightTree(roles){
        var url = setter.basePath + "roleController/queryConfigList",
        callback = function(data){
            if(data){
                var checkedNodes = [];
                var nodeObj = {
                    key: "roleId",
                    listId: "roles"
                }
				var treeDatas = data.obj.treeNodes,
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
							icoObj.parent().append('<span data-remark="'+( treeNode.remark|| '')+'" class="eleTree-node-content-diydom iconfont iconfont icon-beizhu"></span>');
							if (treeNode.level >= 1) {
								var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
								switchObj.before(spaceStr);
							}
						}
					},
					check: {enable: true, chkStyle: 'checkbox'},
					callback: {
                        onBeforeFuzzy: function () {
                            layui.admin.showLoading('#LAY-role-right-tree', 2, '.8');
                        },
                        onAfterFuzzy: function () {
                            var ele = document.getElementById('LAY-role-right-tree')
                            ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
                            layui.admin.removeLoading('#LAY-role-right-tree', true, true);
                        },
						onClick: function(event, treeId, treeNode){
							
						},
						onCheck: function(event, treeId, treeNode){
							pushField(nodeObj, [treeNode], treeNode.checked);
						}
					}
				};
				eachCheckNode(treeDatas, roles, {"ROLE":'iconfont icon-user-list file'},false);
				var treeObj = jQuery.fn.zTree.init($('#LAY-role-right-tree'), ztreeobj, treeDatas);
				
				fuzzySearch ( 'LAY-role-right-tree', '#LAY-role-tree-search', false, true );
				$(".user-fallinto-left .search-btn").off("click").on("click", function(){
				   
					fireKeyEvent($("#LAY-role-tree-search")[0], 'keydown', 13);
					
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
				treeObj.expandAll(true);

				layui.admin.removeLoading('.user-fallinto-left .list-box', true, true);
				
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
               
                $("#LAY-role-right-tree .eleTree-node-content-diydom").off('click').on('click', function(e){
                	var el = $(this),
                	remark = el.attr('data-remark');
                	if(remark){
                		layer.tips(remark, e.target, {
                			tips: [1, "#66686d"]
                		})
                	}
                })
				$(".icon-select-all").off('click').on('click',function(){
					var treeObj = jQuery.fn.zTree.getZTreeObj('LAY-role-right-tree'),
					nodes = treeObj.getNodes(),
					checks = treeObj.getCheckedNodes(true);
					if(nodes.length > checks.length){
						treeObj.checkAllNodes(true);
						$(".authorized-list").html("");
						pushField(nodeObj, nodes, true);
					}
				})
				$(".icon-quxiaoquanxuan").off('click').on('click',function(){
					var treeObj = jQuery.fn.zTree.getZTreeObj('LAY-role-right-tree'),
					checks = treeObj.getCheckedNodes(true);
					if(checks.length){
						treeObj.checkAllNodes(false);
						$(".authorized-list").html("");
					}
				})
                $(".authorized-list").html("");
                pushField(nodeObj, checkedNodes, true);
               
                return false
            }
        }
		layui.admin.showLoading('.user-fallinto-left .list-box', 2, '.8')
        myUtil.send(url, {}, callback);
    }
    /**
     * 生成|删除关联属性标签
     * @method pushField
     * @for UserManage
     * @param  {Object} $data 方法调用arguments集合
     * @param  下面为arguments详细
     * @param  {Array(JSON)}  nodes 需生成标签树节点属性
     * @param  {String}  isAdd  true 生成 false 删除
     */
    function pushField($data, nodes, isAdd){
        var temp = '<div class="authorized-item" id="aid{{'+$data.key+'}}" data-id="{{'+$data.key+'}}">{{name}}<span class="close-btn iconfont icon-close authorized-close"></span></div>',
        list = $(".authorized-list"),
        arr = list.data($data.listId) || [];
        if(nodes && nodes.length){
            for(var i=0;i<nodes.length;i++){
                if(isAdd){
                    if(arr.indexOf(nodes[i][$data.key]) < 0) arr.push(nodes[i][$data.key]);
                    list.append('<div class="authorized-item" id="aid'+nodes[i][$data.key]+'" data-id="'+nodes[i][$data.key]+'">'+nodes[i].name+'<span class="close-btn iconfont icon-close authorized-close"></span></div>');
                }else{
                    arr.splice(arr.indexOf(nodes[i][$data.key]), 1);
                    $("#aid"+nodes[i][$data.key]).remove();
                }
            }
            list.data($data.listId, arr);
            $(".authorized-close").off("click").on("click", function(){
                var id = $(this).parent().attr("data-id"),
				treeObj = jQuery.fn.zTree.getZTreeObj('LAY-role-right-tree'),
                newNodes = [];
				treeObj.checkNode(treeObj.getNodeByParam('id', id, null), false, true);
                
                $(this).parent().remove();
            });
        }
    }
    function eventTopbar(){
        form.on('submit(saveFolder)', function (data) {
            F.saveFolder(data);
            return false;
        });
        form.verify({
            phone: function(value){
              if(value){
                var Vphone = /^1\d{10}$/
                if(!Vphone.test(value)){
                    return "请输入正确的电话号码"
                }
              }
            }
            ,email: function(value){
                if(value){
                  var Vemail = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/
                  if(!Vemail.test(value)){
                    return "请输入正确的邮箱格式"
                  }
                }
              }
            ,areaCode:function(value){
                if(value){
                    var VareaCode = /^\d{3,4}$/
                    if(!VareaCode.test(value)){
                      return "请输入正确的区号"
                    }
                }
            }
            ,telephone:function(value){
                if(value){
                    var Vtelephone = /^\d{6,7}$/
                    if(!Vtelephone.test(value)){
                        return "请输入正确的固话号码"
                    }
                }
            }
          });
        form.on('submit(saveUserinfo)', function (data) {
            F.saveUserinfo(data);
            return false;
        });
        
        $(".del-all-user").off("click").on("click", function(){
            if(editId == "0"){
                var checkStatus = table.checkStatus('allUserList');
                F.deleteUser(checkStatus.data)
            }else{
                var checkStatus = table.checkStatus('userList');
                F.deleteUser(checkStatus.data);
            }
            
        });
        $(".del-user").off("click").on("click", function(){
            var info = $(".box-userinfo").data("userinfo");
            F.deleteUser([{userId: info.userId, userCde: info.userCde}]);
        });
        $(".close-user").off("click").on("click", function(){
            F.tab("close");
        });
        $(".unlockUser").off("click").on("click", function(){
            var el = $(this),
            info = $(".box-userinfo").data("userinfo"),
            url = setter.basePath + "userController/unlock",
            ajaxData = {event: "update-md", settings: {userCde:info.userCde}},
            callback = function(data){
                if(data){
                    $(".userinfo-lock").text("正常");
                    el.addClass('layui-hide');
                    myUtil.msg({type: "success", message: data.msg});
                }
            };
            myUtil.send(url, ajaxData, callback);
        });
        $(".reset-psd").off("click").on("click", function(){
            layui.admin.open({
                id: "LAY-resetPsd-form",
                title: "重置密码",
                content: resetPsdTemp,
                success: function(layero, index){
                    form.render();
                    form.verify({
                        realPsd: function(value, item){
                            if(value != $("#newPassword").val()){
                                return "两次密码不一致！";
                            }
                        }
                    });
                    form.on('submit(resetPsd)', function (data) {
                        F.resetPsd(data, index);
                        return false;
                    });
                },
                yes: function(index, layero){
                    $("#resetPsd-submit").trigger("click");
                    
                }
            });
        });
        $(".save-folder").off("click").on("click", function(){
            $("#save-folder-btn").trigger("click");
        });
        $(".save-user").off("click").on("click", function(){
            $("#saveUserinfo").trigger("click");
        });
        $(".add-user").off("click").on("click", function(e){
            layui.admin.open({
                id: "LAY-admin-adduser",
                title: "新建用户",
				type: 1,
                content: createUserTemp,
                area: ["650px", "340px"],
				btn: ['确认', '取消'],
                success: function(layero, index){
					layui.admin.showLoading('#LAY-admin-adduser', 2, '.8');
					function runfn(){
						F.loadPostList(function (postList){
							layui.admin.removeLoading('#LAY-admin-adduser', true, true)
							var options = [];
							if(postList && postList.length){
								postList.forEach(function(v, i){
									options.push('<option value="'+v.postId+'">'+v.name+'</option>');
								})
							}
							layero.find(".postList").html(options.join(""));
							form.val('createUser-form', {orgName: $("#orgName-input").val(), orgId: $("#orgPid-input").val(), department: $("#orgId-input").val()})
							form.render(null, 'createUser-form');
							form.verify({
							    length30: function(value, item){
							        if(value.trim().length > 30){
							            return "长度不超过30！";
							        }
							    }
							});
							form.on('submit(createUser)', function (data) {
								F.createUser(data, index);
								return false;
							});
						});
					}
					runfn();
					$(".add-dict-btn").off('click').on('click', function(){
						var temp = createRoleFloderTemp.replace(/{{name}}/g, '职务');
						layui.admin.open({
						    title:  '新建职务',
						    id: "LAY-admin-createRole",
						    content: temp,
						    success: function(layero, index){
						        form.render();
						        form.on('submit(createRole)', function (data) {
						            F.createRoleFloder(data, index, runfn);
						            return false;
						        });
						    },
						    yes: function(index, layero){
						        $("#createRole-submit").trigger("click");
						    }
						});
					});
                },
                yes: function(index, layero){
                    $("#createUser-submit").trigger("click");
                }
            });
        });
        // 导入
        $("#imp").off("click").on("click",function(){
            $(".cardWrap").toggleClass("layui-hide")
            $(".card2").toggleClass("layui-hide")
            $(".card1").toggleClass("layui-height-full")
            $(".impWrap").toggleClass("layui-hide")
            // 导入界面在切换到列表再次进入，要进入到初始界面
            $(".impcoutent").removeClass("layui-hide")
            $(".performImport").addClass("layui-hide")
            $(".finish").addClass("layui-hide")
            $(".fristUploadFilename").html("")
            $("#uploadTemplateFirst").val("")
            $(".secondStep").css({
                "background-color": "#eee",
                "color": "#fff"
            })
            $(".secondStep").find("em").css({
                "border-left": "10px solid #eee"
            })
            $(".thirdStep").css({
                "background-color": "#eee",
                "color": "#fff"
            })
            $(".thirdStep").find("em").css({
                "border-left": "10px solid #eee"
            })
            if(!$(".impWrap").is(".layui-hide")){
				// $("#imp").css({
				// 	"background":"#4785e6",
				// 	"color":"#fff"
                // })
                $("#imp").addClass("impactive")
			}else{
				// $("#imp").css({
				// 	"color":"#4785e6",
				// 	"border": "1px solid #4785e6",
				// 	"background": "#fff"
                // })
                $("#imp").removeClass("impactive")
                
			}
        })
        $("#imps").off("click").on("click",function(){
            $(".forms").toggleClass("layui-hide")
            $(".card3").toggleClass("layui-hide")
            $(".topWrap").toggleClass("layui-height-full")
            $(".impWrap").toggleClass("layui-hide")
            if(!$(".impWrap").is(".layui-hide")){
				// $("#imps").css({
				// 	"background":"#4785e6",
				// 	"color":"#fff"
                // })
                $("#imps").addClass("impactive")
			}else{
				// $("#imps").css({
				// 	"color":"#4785e6",
				// 	"border": "1px solid #4785e6",
				// 	"background": "#fff"
                // })
                $("#imps").removeClass("impactive")
			}
        })
       
		$(".goBack").off("click").on("click",function(){
			$(".performImport").addClass("layui-hide")
			$(".impcoutent").removeClass("layui-hide")
			$(".secondStep").css({
				"background":"#eee",
				"color":"#666"
			})
			$(".secondStep em").css({
				"border-left":"10px solid #eee"
			})
		})
		$(".orgPname-input").off("click").on("click", function(e){
            var evt = window.event || arguments.callee.caller.arguments[0];
            evt.preventDefault();
            evt.stopPropagation();
            var that = $(this),
                orgPidInput = $("#orgPid-input"),
                url =  setter.basePath + 'orgController/orgTreeList',
                node = jQuery.fn.zTree.getZTreeObj(treeId).getSelectedNodes()[0],
			    parentNode = node && node.getParentNode();
            this.blur();
            myUtil.send(url, {}, function(data){
                if(data.success){
                    var removeBefore = '',
                    nodeId = orgPidInput.val(),
                    treeNodes = { treeNodes: data.obj, expandAll: true,icons: {
                        file: "iconfont eletree-icon-normal-file ",
                        "1": "iconfont icon-gongsi file ",
                        "2": "iconfont icon-bumen3 file ",
                    }},
                    callback = function (type, treeId, node) {
                        event.stopPropagation();
                        if(node.type == '0') return;
                        if(currentNode.type == "1" && node.type == "2"){
                            myUtil.msg({
                                type: "warning",
                                message: '公司不可移动部门下！',
                            });
                            return false;
                        }
                        that.val(node.name);
                        orgPidInput.val(node.id);
                        
                        $(".seltree-box").remove();
                    }
                    treeNodes.treeNodes.unshift({id:'0', name: '无', type: 'folder'});
                    F.renderDialogzTree("multiselect-tree", that[0], e, url, {}, nodeId || "0", callback, "none", treeNodes, removeBefore, "", true);
                   
                }
            })
        })
        secondStepTable()
        secondStepTable2()
        secondStepOrgTable()
        secondStepOrgTable2()
    }
    	// 渲染执行导入里面的表格
	function secondStepTable($data) {
		if (!$data) $data = [];
		table.render({
			elem: "#secondStepTable",
			cellMinWidth: 80,
			height: isSm ? "full-450" : "full-460",
			cols: [[
				// { type: "checkbox" },
				{ field: "index", title: "序号", align: "center", type: "numbers", width: 80,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "company", title: "组织", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "userCde", title: "登录名称", templet:"#type", unresize: true, sort: false, align: "center",style:"font-size:12px" },
                { field: "userName", title: "姓名", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "gender", title: "性别", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "department", title: "部门", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "position", title: "职位", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "mail", title: "邮箱", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "telephone", title: "手机", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
			]],
            data: $data,
            skin: "nob",
            even: "true",
			limit: $data.length,
		});
		

    }
    function secondStepOrgTable($data){
        if (!$data) $data = [];
		table.render({
			elem: "#secondStepOrgTable",
			cellMinWidth: 80,
			height: isSm ? "full-450" : "full-460",
			cols: [[
				// { type: "checkbox" },
				{ field: "index", title: "序号", align: "center", type: "numbers", width: 80,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "orgName", title: "组织名称", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
			]],
            data: $data,
            skin: "nob",
            even: "true",
			limit: $data.length,
		});
    }
    function secondStepTable2($data) {
		if (!$data) $data = [];
		table.render({
			elem: "#secondStepTable2",
			cellMinWidth: 80,
			height: isSm ? "full-450" : "full-460",
			cols: [[
				// { type: "checkbox" },
				{ field: "index", title: "序号", align: "center", type: "numbers", width: 80,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "company", title: "组织", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "userCde", title: "登录名称", templet:"#type", unresize: true, sort: false, align: "center" ,style:"font-size:12px"},
                { field: "userName", title: "姓名", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "gender", title: "性别", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "department", title: "部门", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "position", title: "职位", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "mail", title: "邮箱", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
                { field: "telephone", title: "手机", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
			]],
            data: $data,
            skin: "nob",
            even: "true",
			limit: $data.length,
		});
		$(".layui-form").removeClass("layui-hide")

    }
    function secondStepOrgTable2($data){
        if (!$data) $data = [];
		table.render({
			elem: "#secondStepOrgTable2",
			cellMinWidth: 80,
			height: isSm ? "full-450" : "full-460",
			cols: [[
				// { type: "checkbox" },
				{ field: "index", title: "序号", align: "center", type: "numbers", width: 80,unresize: true, sort: false,style:"font-size:12px"},
				{ field: "orgName", title: "组织名称", align: "center" ,unresize: true, sort: false,style:"font-size:12px"},
			]],
            data: $data,
            skin: "nob",
            even: "true",
			limit: $data.length,
		});
    }
    function searchpassword(){
        var url = setter.basePath + "passwordSecurityController/queryPasswordSecurity",
        callback = function(data){
          if(data){
                psdErrNumber = data.obj.psdErrNumber;
                psdLength = data.obj.psdLength;
                psdPeriod = data.obj.psdPeriod;
                psdRequest = data.obj.psdRequest;
                psdSetting = data.obj.psdSetting;
            };
        }
    
        myUtil.send(url,{},callback)
    }

    // 组织结构根节点下的导入
    function getToLead(){
        var uploadData = ""
        // 点击下载Excel
        $(".downExcelfirst").off("click").on("click",function(){
            var subForm = $("#exceliframes").contents().find("#export-form"),
                formId = $("#exceliframes").contents().find("#formId");
                if(!subForm[0]){
                    $("#exceliframes").contents().find("body").html('<form id="export-form" action="' + setter.basePath +
						'userController/downloadTemplate" enctype="multipart/form-data" method="post"><input id="formContent" type="hidden" name="formContent"><input id="formId" type="hidden" name="formId"></form>'
					);
                }
                formContent = $("#exceliframes").contents().find("#formContent");
				formId = $("#exceliframes").contents().find("#formId");
				subForm = $("#exceliframes").contents().find("#export-form");
                formId.val("rollDataWeb/template");
                subForm.submit();

        })
        // 点击上传模板
        $(".uploadTemplateFirst").off("click").on("click",function(){
            $("#uploadTemplateFirst").trigger("click")
        })
        $("#uploadTemplateFirst").off("change").on("change",function(){
            $(".fristUploadFilename").html($("#uploadTemplateFirst").val())
            var form = $("#firstuploadForm")[0],
                formsdata = new FormData(form)

            $.ajax({
                type: "POST",
                url: setter.basePath + "userController/importOrgUser",
                data:formsdata,
                contentType: false,
                processData: false,
                success: function (data) {
                    if(data.success){
                        myUtil.msg({type:"success",message:data.msg})
                        uploadData = data
                    }else{
                        myUtil.msg({type:"error",message:data.msg})
                    }
                  
                },
                error: function (msg) {
                    myUtil.msg({type:"warning",message:"文件上传失败"})
                //   alert(XMLHttpRequest.status);
                //   alert(XMLHttpRequest.readyState);
                //   alert(textStatus);
                //   alert(errorThrown)
                }
              });
        });
        // 点击下一步进入执行导入页面
         $(".firstStepButton").off("click").on("click",function(){
			if(uploadData){
                $(".impcoutent").addClass("layui-hide")
                $(".performImport").removeClass("layui-hide")
                $(".secondStep").css({
                    "background":"#4785e6",
                    "color":"#fff"
                })
                $(".secondStep em").css({
                    "border-left":"10px solid #4785e6"
                })
                $(".bad").html(uploadData.obj.bad)
                $(".rightTem").html(uploadData.obj.right)
                secondStepTable(uploadData.obj.userList)
                secondStepOrgTable(uploadData.obj.orgList)
            }else{
                myUtil.msg({type:"warning",message:"请上传文件模板"})
            }
        })
        // 点击下一步进入完成页面
        $(".sButton").off("click").on("click",function(){
            var url = setter.basePath + "userController/complyImportOrgUser",
                ajaxData = {
                    "_uuid":uploadData.obj._uuid
                },
                callback = function (data) {  
                    if(data){
                        $(".performImport").addClass("layui-hide")
                        $(".finish").removeClass("layui-hide")
                        $(".thirdStep").css({
                            "background":"#4785e6",
                            "color":"#fff"
                        })
                        $(".thirdStep em").css({
                            "border-left":"10px solid #4785e6"
                        })
                        var successNum = uploadData.obj.userList.length + uploadData.obj.orgList.length
                        $(".successNum").html(successNum)
                    }
                }
            myUtil.send(url,ajaxData,callback)
			
        })
        // 点击完成返回
        $(".finishButton").off("click").on("click",function(){
			renderTree();
			
        })
    }
    // 字节点下的导入
    function getToLead2(){
        var uploadData = ""
        // 点击下载Excel
        $(".downExcelsecond").off("click").on("click",function(){
            // $("#exceliframes").attr("src",setter.basePath + "userController/downloadTemplate")
            var formContent = $("#exceliframes2").contents().find("body"),
                subForm = $("#exceliframes2").contents().find("#export-forms"),
                formId = $("#exceliframes2").contents().find("#formId"),
                formData = new FormData(formId);
                if(!subForm[0]){
                    $("#exceliframes2").contents().find("body").html('<form id="export-forms" action="' + setter.basePath +
						'userController/downloadTemplate" enctype="multipart/form-data" method="post"><input id="formContent2" type="hidden" name="formContent"><input id="formId" type="hidden" name="formId"></form>'
					);
                }
                formContent = $("exceliframes").contents().find("#formContent2");
				formId = $("exceliframes").contents().find("#formId");
				form = $("exceliframes").contents().find("#export-forms");
                formContent.val(formData);
                formId.val("rollDataWeb/template");
                subForm.submit();

        })
        // 点击上传模板
        $(".uploadTemplatesecond").off("click").on("click",function(){
            $("#uploadTemplatesecond").trigger("click")
        })
        $("#uploadTemplatesecond").off("change").on("change",function(){
            $(".secondUploadFilename").html($("#uploadTemplatesecond").val())
            var form = $("#seconduploadForm")[0],
                formsdata = new FormData(form)

            $.ajax({
                type: "POST",
                url: setter.basePath + "userController/importOrgUser",
                data:formsdata,
                contentType: false,
                processData: false,
                success: function (data) {
                    if(data){
                        myUtil.msg({type:"success",message:data.msg})
                        uploadData = data
                    }
                  
                },
                error: function (msg) {
                    myUtil.msg({type:"warning",message:"文件上传失败"})
                }
              });
        });
        // 点击下一步进入执行导入页面
         $(".secondbutton").off("click").on("click",function(){
			if(uploadData){
                $(".impcoutent").addClass("layui-hide")
                $(".performImport").removeClass("layui-hide")
                $(".forms").addClass("layui-hide")
                $(".secondStep").css({
                    "background":"#4785e6",
                    "color":"#fff"
                })
                $(".secondStep em").css({
                    "border-left":"10px solid #4785e6"
                })
                $(".bad2").html(uploadData.obj.bad)
                $(".rightTem2").html(uploadData.obj.right)
                secondStepTable2(uploadData.obj.userList)
                secondStepOrgTable2(uploadData.obj.orgList)
                $(".forms").addClass("layui-hide")
            }else{
                myUtil.msg({type:"warning",message:"请上传文件模板"})
            }
        })
        // 点击下一步进入完成页面
        $(".secondStepButton2").off("click").on("click",function(){
            var url = setter.basePath + "userController/complyImportOrgUser",
                ajaxData = {
                    "_uuid":uploadData.obj._uuid
                },
                callback = function (data) {  
                    if(data){
                        $(".performImport").addClass("layui-hide")
                        $(".finish").removeClass("layui-hide")
                        $(".thirdStep").css({
                            "background":"#4785e6",
                            "color":"#fff"
                        })
                        $(".thirdStep em").css({
                            "border-left":"10px solid #4785e6"
                        })
                        var successNum = uploadData.obj.userList.length + uploadData.obj.orgList.length
                        $(".successNum2").html(successNum)
                    }
                }
            myUtil.send(url,ajaxData,callback)
			
		})
    }
    !function(){
        if(isSm){
            $("#LAY-user-body > .layui-fluid").removeClass("layui-height-full-30").addClass("layui-height-full-20");
        }else{
            $("#LAY-user-body > .layui-fluid").removeClass("layui-height-full-20").addClass("layui-height-full-30");
        }
        if(!$(".treeWrap").hasClass("ps")){
            treeWrapSollbar = new pscrollbar(".treeWrap");
            $(".tree-box").each(function(i, v){
                if(!$(v).hasClass("ps")){
                    new pscrollbar(v);
                    $(v).addClass("treebox-scroll");
                }
            });
            
            $(".treeWrap").addClass("treebox-scroll");
        }
     
        $(".layui-tab-content").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
            }
        });
        layui.sessionData("configTree", null);
        layui.sessionData("postList", null);
        layui.sessionData(storeTableName, null);
    
        navbarPushPull();
        eventTreeBar();
        eventTopbar();
        renderTree();
        searchpassword(); // 获取密码
        Initialization(); // 渲染右侧列表数据
        getToLead()  //组织结构根节点下的导入
    }();
    exports("user", {})
});
