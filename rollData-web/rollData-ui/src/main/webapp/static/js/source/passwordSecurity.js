;
layui.define(['layer', "wdUtil", "setter", "jquery", "form", "admin", "notice", "pscrollbar"], function (exports) {
	var $ = layui.jquery
		, layer = layui.layer
		, myUtil = layui.wdUtil
		, setter = layui.setter
		, pscrollbar = layui.pscrollbar
		, form = layui.form
		, admin = layui.admin
		temp = '<div class="selectWrap2-box" style="padding: 15px 15px 0 15px;width:100%; height:100%;box-sizing: border-box">'
			+'<div class="dialog-tree-boxs box-left" style="width: 285px;height: 100%;display:inline-block;border-right:1px solid #eee;position: relative;vertical-align: top;">'
			+' <div class="search-box layui-form-item-sm" style="position:relative">'
				+'<input id="selectResourseTree2_search" class="layui-input selectResourseTree2_search" style="background: #FAFAFA;height: 28px;margin: 0 auto;margin-top: 5px;border-radius: 15px;width: 95%;" autocomplete="off">'
				+'<i class="iconfont selectResourseTree2_search-btn" style="position: absolute;top: 7px;right: 10px;font-size: 12px;cursor: pointer;"></i>'
			+'</div>'
			+'<div id="LAY-daima-box" class="ztree-box treebox-scroll" style="position: absolute;top: 40px;left: 0;right: 0;bottom: 0;">'
				+'<ul class="lay-ztree ztree selectResourseTree2 treebox-scroll" id="selectResourseTree2" style="height: 100%;position: relative;"></ul>'
			+'</div>'
			+'</div>'
			+'<div class="dialog-tree-boxs box-right" style="width: 284px;height: 100%;display:inline-block;position: relative;vertical-align: top;">'
			+'<div class="search-box layui-form-item-sm" style="position:relative">'
				+'<input id="selecteleTree-search4" class="layui-input selecteleTree-search4" style="background: #FAFAFA;height: 28px;margin: 0 auto;margin-top: 5px;border-radius: 15px;width: 95%;" autocomplete="off">'
			+' <i class="iconfont selecteleTree-search4-btn" id="selecteleTree-search4-btn" style="position: absolute;top: 7px;right: 10px;font-size: 12px;cursor: pointer;"></i>'
			+'</div>'
			+'<div id="LAY-daima-box" class="ztree-box treebox-scroll" style="position: absolute;top: 40px;left: 0;right: 0;bottom: 0;">'
				+'<ul class="lay-ztree ztree selectResoursetable2" id="selectResoursetable2" style="height: 100%;position: relative;"></ul>'
			+'</div>'
			+'</div>'
		+'</div>';
	var ajaxData = {};
	var queryData = {};
	
	function queryPassword() {
		var url = setter.basePath + "passwordSecurityController/queryPasswordSecurity";
		var callback = function(data){
			// layui.admin.removeLoading('body', 2, '.8');
			var datas = data.obj
			if(datas.psdPeriodIs == "0"){
				$(".psdPeriodWrap").addClass("layui-hide")
			}else{
				$(".psdPeriodWrap").removeClass("layui-hide")
			}
			if(datas.psdErrNumberIs == "0"){
				$(".psdErrNumberWrap").addClass("layui-hide")
			}else{
				$(".psdErrNumberWrap").removeClass("layui-hide")
			}
			if(datas.isBrowsePassword == "0"){
				$(".browsePasswordWrap").addClass("layui-hide");
				$("#browsePassword").val("").attr("lay-verify", "");
			}else{
				$(".browsePasswordWrap").removeClass("layui-hide");
				$("#browsePassword").attr("lay-verify", "required|password");
			}

			form.val('example', {
				"psdLength": datas.psdLength
				, "psdSetting": datas.psdSetting
				, "psdPeriod": datas.psdPeriod
				, "psdRequest": datas.psdRequest
				, "psdErrNumber": datas.psdErrNumber,
				"psdPeriodIs": datas.psdPeriodIs,
				"psdErrNumberIs": datas.psdErrNumberIs,
				"psdValidateIs": datas.psdValidateIs,
				"psdInitIs":datas.psdInitIs,
				"isLimitLogin":datas.isLimitLogin,
				"isBrowsePassword":datas.isBrowsePassword,
				"browsePassword":datas.browsePassword || "",
			})
			queryData = datas;
			form.on('radio(psdPeriodRadio)', function(data){
				if(data.value == "0"){
					$(".psdPeriodWrap").addClass("layui-hide")
					$("#psdPeriod").val("0")
				}else{
					$("#psdPeriod").val(datas.psdPeriod)
					$(".psdPeriodWrap").removeClass("layui-hide")
				}
			});  
			form.on('radio(psdErrNumberRadio)', function(data){
				if(data.value == "0"){
					$(".psdErrNumberWrap").addClass("layui-hide")
					$("#psdErrNumber").val("3")
				}else{
					$(".psdErrNumberWrap").removeClass("layui-hide")
					$("#psdErrNumber").val(datas.psdErrNumber)
				}
			});
			form.on('radio(isBrowsePasswordRadio)', function(data){
				if(data.value == "0"){
					$(".browsePasswordWrap").addClass("layui-hide");
					$("#browsePassword").val("").attr("lay-verify", "");
				}else{
					$(".browsePasswordWrap").removeClass("layui-hide");
					$("#browsePassword").attr("lay-verify", "required|password");
				}
			});  
			form.on('submit(savePasswordSecurity)', function(data){
				var formData = {
					"psdLength": data.field.psdLength,
					"psdSetting": data.field.psdSetting,
					"psdPeriod": data.field.psdPeriod,
					"psdRequest": data.field.psdRequest,
					"psdErrNumber": data.field.psdErrNumber,
					"psdPeriodIs": data.field.psdPeriodIs,
					"psdErrNumberIs": data.field.psdErrNumberIs,
					"psdValidateIs": data.field.psdValidateIs,
					"psdInitIs":data.field.psdInitIs,
					"isLimitLogin":data.field.isLimitLogin,
					"isBrowsePassword":data.field.isBrowsePassword,
					"browsePassword":data.field.browsePassword,
				}
				if (!myUtil.isSame(queryData, formData)) {
					var url = setter.basePath + "passwordSecurityController/updatePasswordSecurity",
						callback = function(data){
							if (data) {
								myUtil.msg({ type: "success", message: data.msg });
								layui.admin.removeLoading('body', true, true);
							}
						}
					layui.admin.showLoading('body', 2, '.8');
					myUtil.send(url, formData, callback)
				}
				return false;
			});
			form.verify({
				password: function (value) {
					if(value.length < 6){
					  return '密码至少得'+6+'个字符';
					}
				},
				repeatpassword: function(value){
					if(value != $(".newPassword-input").val()){
						return "两次输入密码不一致！";
					}
				}
	
			});
			layui.admin.removeLoading('.layui-card', true, true);
			// layui.admin.removeLoading('', true, true);
			// return queryData
		}
		layui.admin.showLoading('.layui-card', 2, '.8');
		myUtil.send(url, ajaxData, callback)
	}
	
	/**
	 * 新建下拉列表树!!新增里的源和目标--源表 （左侧树）
	 * @method createselectTree2
	 * @for UserManage
	 * @param node 节点数据
	 */
	 function createselectTree2() {
		layui.admin.open({
			title: '重置用户访问密码',
			type: 1,
			id: "LAY-admin-setPassword",
			content: temp,
			area: ['600px', '480px'],
			btn: ["确认", "取消"],
			success: function(layero, index){
				var url = setter.basePath + "userController/userLimitTreeList",
				ajaxData = {},
				icons = {COMPANY: "iconfont icon-gongsi ", DEPARTMENT: "iconfont icon-bumen3 ", PRODUCT: "iconfont icon-project "},
				callback = function(data){
					if(data.success){
						var treeNodes = data.obj.treeNodes,
						eventTree = function (treeData) {
							var treeEl = $('.selectResourseTree2'),
								ztreeobj = {
									view: {
										selectedMulti: false,
										showLine: false,
										expandSpeed: '',
										addDiyDom: function (treeId, treeNode) {
											var spaceWidth = 15;
											var switchObj = $("#" + treeNode.tId + "_switch"),
												checkObj = $("#" + treeNode.tId + "_check"),
												icoObj = $("#" + treeNode.tId + "_ico");
											switchObj.remove()
											if (checkObj[0]) {
												icoObj.before(checkObj);
												checkObj.append('<i class="layui-icon"></i>');
												checkObj.find('i').off('click').on('click', function () {
													$(this).parent().trigger('click');
												})
												checkObj.before(switchObj);
												if (treeNode.children && treeNode.children.length > 0) {
												} else {
													switchObj.css('color', 'transparent')
												}
											} else {
												icoObj.before(switchObj);
												if (treeNode.children && treeNode.children.length > 0) {
												} else {
													switchObj.css('color', 'transparent')
												}
											}
											if (treeNode.level >= 1) {
												var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
												switchObj.before(spaceStr);
											}
										}
									},
									callback: {
										onBeforeFuzzy: function () {
											var zTreeId = 'selectResourseTree2';
											layui.admin.showLoading('#' + zTreeId, 2, '.8');
										},
										onAfterFuzzy: function () {
											var zTreeId = 'selectResourseTree2';
											var ele = document.getElementById(zTreeId)
											ele.scrollTop = 0;
											layui.admin.removeLoading('#' + zTreeId, true, true);
										},
										onClick: function (event, treeId, treeNode) {
											event.stopPropagation();
										}
									}
								};
							ztreeobj.check = {
								enable: true,
								chkStyle: "checkbox"
							};
							ztreeobj.callback.onCheck = function (event) {
								event.stopPropagation();
								var treeObjs = jQuery.fn.zTree.getZTreeObj("selectResourseTree2"),
								nodes = treeObjs.getCheckedNodes();
								laodLazy2(nodes);
							}
							treeEl.off('click').on('click', function (e) {
								var evt = window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							})
							var treeObj = jQuery.fn.zTree.init($('#selectResourseTree2'), ztreeobj, treeData);
							treeObj.expandAll(true);
							laodLazy2([]);
							$('.selectResourseTree2_search, .selectResourseTree2_search-btn').off("click").on("click", function (e) {
								e.stopPropagation()
								var evt = window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							})
		
							if ($('.selectResourseTree2') && !$('.selectResourseTree2').hasClass("ps")) {
								new pscrollbar($('.selectResourseTree2')[0]);
								$('.selectResourseTree2').addClass("treebox-scroll");
								$('.selectResourseTree2').find('.ps__rail-x').on('click', function (e) {
									var evt = window.event || arguments.callee.caller.arguments[0];
									evt.preventDefault();
									evt.stopPropagation();
								});
								$('.selectResourseTree2').find('.ps__rail-y').on('click', function (e) {
									var evt = window.event || arguments.callee.caller.arguments[0];
									evt.preventDefault();
									evt.stopPropagation();
								});
							}
						}
		
						eachCheckNode(treeNodes, [], icons, false);
		
						function eachCheckNode(nodes, ids, icons, last){
							if(nodes.length){
								nodes.forEach(function(v, i){
									if(ids && ids.length && ids.indexOf(v.id) > -1){
										v.checked = 'true';
									}else{
										delete v.checked;
									}
									if(v.children && v.children.length){
										if(icons){
											if(icons[v.type]){
												v.iconSkin = icons[v.type];
											}
										}
										if(last){
											v.nocheck = true;
										} else {
											delete v.nocheck;
										}
										eachCheckNode(v.children, ids, icons, last);
									}else{
										if(icons){
											if(icons[v.type]){
												v.iconSkin = icons[v.type];
		
											}
										}else{
											v.iconSkin = 'iconfont icon-iconfontfile2 file';
										}
									}
								})
							}
						}
		
						if (treeNodes) {
							eventTree(treeNodes);
							fuzzySearch('selectResourseTree2', ".selectResourseTree2_search", false, true);
						}
					}
					layui.admin.removeLoading('.dialog-tree-boxs.box-left', true, true);
				};
				myUtil.send(url, ajaxData, callback);
				layui.admin.showLoading('.dialog-tree-boxs.box-left', 2, '.8');
			},
			yes: function(index, layero){
				var treeObjs = jQuery.fn.zTree.getZTreeObj("selectResoursetable2"),
				nodes = treeObjs && treeObjs.getCheckedNodes() || "",
				ids = [];
				if(nodes && nodes.length){
					nodes.forEach(function(v, i){
						ids.push(v.id);
					})
					layui.admin.open({
						title: '重置访问密码',
						id: "LAY-admin-updatePassword",
						content: '<form action="" lay-filter="update-password-security" class="layui-form" enctype="multipart/form-data" style="padding-top:15px">'+
							'<div class="layui-form-item-sm">'+
								'<label class="layui-form-label" for="newPassword">新密码：</label>'+
								'<div class="layui-input-inline">'+
									'<input type="password" name="newPassword" placeholder="请输入新密码" autocomplete="off" lay-verify="required|password"  class="layui-input newPassword-input" lay-vertype="tips">'+
								'</div>'+
							'</div>'+
							'<div class="layui-form-item-sm">'+
								'<label class="layui-form-label" for="repeatPassword">确认密码：</label>'+
								'<div class="layui-input-inline">'+
									'<input type="password" name="repeatPassword" placeholder="请再次输入新密码" autocomplete="off" lay-verify="required|repeatpassword" class="layui-input repeatPassword-input" lay-vertype="tips">'+
								'</div>'+
							'</div>'+
							'<button class="layui-btn layui-hide" id="updatePasswordSecurity" lay-submit="" lay-filter="updatePasswordSecurity">确认</button>'+
						'</form>',
						btn: ["确认", "取消"],
						success: function(layero, index1){
							form.on('submit(updatePasswordSecurity)', function (data) {
								var url1 = setter.basePath + "passwordSecurityController/resetIsBrowsePassword",
								ajaxData1 = {
									ids: ids,
									browsePassword: data.field.newPassword
								},
								callback1 = function(data1){
									if(data1.success){
										myUtil.msg({type: "success", message: data1.msg});
										layer.close(index1);
										layer.close(index);
										queryPassword();
									}
									layui.admin.removeLoading('body', true, true);
								}
								layui.admin.showLoading("body",2,".8")
								myUtil.send(url1, ajaxData1, callback1)
								return false;
							});
						},
						yes: function(index, layero){
							$("#updatePasswordSecurity").trigger("click");
						}
					});
				}else{
					myUtil.msg({type: "warning", message: "请选择需要重置密码用户！"});
				}
			}
		});
		
	}
	/**
	 * 新建下拉列表树!!新增里的源和目标--源表 （右侧树）
	 * @method laodLazy2
	 * @for UserManage
	 * @param node 节点数据
	 */
	function laodLazy2(checkNodes) {
		var url = setter.basePath + "userController/queryIsBrowseUsers",
		checkIds = [],
		icons = {COMPANY: "iconfont icon-gongsi ", DEPARTMENT: "iconfont icon-bumen3 ", USER: "iconfont icon-user ", PRODUCT: "iconfont icon-project "},
		ajaxData = {
			search: '',
			org: []
		}, 
		callback = function (data) {
			if (data.success) {
				var treeNodes = data.obj,
				eventTrees = function (treeData) {
					var treeEl = $('#selectResoursetable2'),
					ztreeobj = {
						view: {
							selectedMulti: false,
							showLine: false,
							expandSpeed: '',
							addDiyDom: function (treeId, treeNode) {
								var spaceWidth = 15;
								var switchObj = $("#" + treeNode.tId + "_switch"),
									checkObj = $("#" + treeNode.tId + "_check"),
									icoObj = $("#" + treeNode.tId + "_ico");
								switchObj.remove()
								if (checkObj[0]) {
									icoObj.before(checkObj);
									checkObj.append('<i class="layui-icon"></i>');
									checkObj.find('i').off('click').on('click', function () {
										$(this).parent().trigger('click');
									})
									checkObj.before(switchObj);
									if (treeNode.children && treeNode.children.length > 0) {
									} else {
										switchObj.css('color', 'transparent')
									}
								} else {
									icoObj.before(switchObj);
									if (treeNode.children && treeNode.children.length > 0) {
									} else {
										switchObj.css('color', 'transparent')
									}
								}
								if (treeNode.level >= 1) {
									var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
									switchObj.before(spaceStr);
								}
							}
						},
						data: {
							key: {
								name: 'userName'
							},
							simpleData: {
								idKey: 'id',
							}
						},
						callback: {
							onBeforeFuzzy: function () {
								var zTreeId = 'selectResoursetable2';
								layui.admin.showLoading('#' + zTreeId, 2, '.8');
							},
							onAfterFuzzy: function () {
								var zTreeId = 'selectResoursetable2';
								var ele = document.getElementById(zTreeId)
								ele.scrollTop = 0;
								layui.admin.removeLoading('#' + zTreeId, true, true);
							},
							onClick: function (event, treeId, treeNode) {
								
							},
						}
					};
					ztreeobj.check = {
						enable: true,
						chkStyle: "checkbox"
					};
					ztreeobj.callback.onCheck = function (event) {
						event.stopPropagation();
					}
					treeEl.off('click').on('click', function (e) {
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
					})
					var treeObjs = jQuery.fn.zTree.init($('#selectResoursetable2'), ztreeobj, treeData);
					treeObjs.expandAll(true)
					
					$('.selecteleTree-search4, .selecteleTree-search4-btn').off("click").on("click", function (e) {
						var evt = window.event || arguments.callee.caller.arguments[0];
						evt.preventDefault();
						evt.stopPropagation();
					})

					if ($('.selectResoursetable2') && !$('.selectResoursetable2').hasClass("ps")) {
						new pscrollbar($('.selectResoursetable2')[0]);
						$('.selectResoursetable2').addClass("treebox-scroll");
						$('.selectResoursetable2').find('.ps__rail-x').on('click', function (e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						});
						$('.selectResoursetable2').find('.ps__rail-y').on('click', function (e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						});
					}
				}

				eachCheckNode(treeNodes, [], icons, false);

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

				if (treeNodes) {
					eventTrees(treeNodes);
					fuzzySearch('selectResoursetable2', "#selecteleTree-search4", false, true);
				}
			}
			layui.admin.removeLoading(".selectWrap2 .dialog-tree-boxs.box-right", true, true)
		}
		if (checkNodes && checkNodes.length) {
			checkNodes.forEach(function (v, i) {
				ajaxData.org.push({ type: v.type == 'COMPANY' ? '1' : '2', orgId: v.id || v.userId });
			})
		}
		myUtil.send(url, ajaxData, callback)
		layui.admin.showLoading('.selectWrap2 .dialog-tree-boxs.box-right', 2, '.8');
	}
	!function () {
		$(".update-browsePassword-btn").on("click", function(){
			createselectTree2();
		})
		$("#btn-save").on("click", function(){
			$("#savePasswordSecurity").trigger("click");
		});
		queryPassword()
	}();
});