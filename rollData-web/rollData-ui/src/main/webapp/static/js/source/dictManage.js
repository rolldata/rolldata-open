;
layui.define(["wdUtil", "eleTree", "contextMenu", "table", "form", 'setter', 'admin', 'jquery', 'pscrollbar'], function (exports) {
	var $ = layui.jquery,
		setter = layui.setter,
		device = layui.device(),
		form = layui.form, 
		myUtil = layui.wdUtil,
		eleTree = layui.eleTree,
		table = layui.table,
		laypage = layui.laypage,
		cxt = layui.contextMenu,
		treeObj = '',
		uploadData = "",
		treeId = "LAY-dictList-tree",
		dectListTreeId = "LAY-dictManage-tree",
		pscrollbar = layui.pscrollbar,
		alltreetypes='',
		isSm = $('body').hasClass("layui-layout-sm-body"),
		createFloderTemp =
			'<form action="" class="layui-form" id="LAY-createOrg-form" lay-filter="createOrg-form" autocomplete="off">' +
			' <div class="layui-form-item layui-form-item-sm">' +
			' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>目录名称：</label>' +
			' <div class="layui-input-block">' +
			' <input type="text" name="folderName" class="layui-input folderName" required lay-verType="tips" lay-verify="required" placeholder="请输入目录名称" autocomplete="off">' +
			' </div>' +
			' </div>' +
			' <div class="layui-form-item layui-form-item-sm">' +
			' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>档案类型：</label>' +
			'<div class="layui-input-block dictType">' +
			'<select name="dictType" lay-verify="required" lay-filter="dictType">' +
			'<option value="item_dict">科目档案</option>' +
			'<option value="product_dict">项目档案</option>' +
			'<option value="company_dict">公司档案</option>' +
			'<option value="department_dict">部门档案</option>' +
			'<option value="person_dict">人员档案</option>' +
			'</select>' +
			'</div>' +
			' </div>' +
			'<button id="createOrg-submit" class="layui-hide" lay-submit lay-filter="createOrg">提交</button>' +
			'<button id="editItem-submit" class="layui-hide" lay-submit lay-filter="editItem">提交</button>' +
			' </form>',
		synchronousdataKemu = `
		<form class="layui-form synchronousData" action="" lay-filter="createSche" style="padding: 20px 30px;">
		<div class="layui-form-item layui-form-item-sm layui-inline" style="padding-right:7px;">
			<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>账套选择：</label>
			<span class="layui-input-block" style="margin-left: 0;width: 190px;">
				<input type="text" name="acountInfoId" required readonly="readonly" lay-verify="required" autocomplete="off"
					class="layui-input acountInfoId">
			</span>
		</div>
		<div class="layui-form-item layui-form-item-sm layui-inline" style="padding-right:0;">
			<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>年份选择：</label>
			<span class="layui-input-block" style="margin-left: 0;width: 190px;">
				<input type="text" name="MatchingYear" required readonly="readonly" lay-verify="required" autocomplete="off"
					class="layui-input MatchingYear">
			</span>
		</div>
		<div class="layui-form-item layui-form-item-sm layui-inline" style="padding-right:0;">
			<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>档案层级：</label>
			<span class="layui-input-block" style="margin-left: 0;width: 190px;">
			<input type="text" name="synGrade" required readonly="readonly" lay-verify="required" autocomplete="off"
					class="layui-input synGrade">
			</span>
		</div>
		<button id="synchronousData-submit" class="layui-hide" type="button" lay-submit
			lay-filter="synchronousData-submit">确认</button>
	</form>
		`,
		synchronousdataxiangmu = `
		<form class="layui-form synchronousData" action="" lay-filter="createSche" style="padding: 20px 30px;">
		<div class="layui-form-item layui-form-item-sm layui-inline" style="padding-right:7px;">
			<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>账套选择：</label>
			<span class="layui-input-block" style="margin-left: 0;width: 190px;">
				<input type="text" name="acountInfoId" required readonly="readonly" lay-verify="required" autocomplete="off"
					class="layui-input acountInfoId">
			</span>
		</div>
		<button id="synchronousData-submit" class="layui-hide" type="button" lay-submit
			lay-filter="synchronousData-submit">确认</button>
	</form>
		`,
		F = {
			 /**
			 * 新增目录
			 * @method createFloder
			 * @for dictManage
			 */
			createFloder: function (formdatas, index) {
				var url = setter.basePath + "u8DictController/saveDictFolder",
					ajaxData = {
						folderName: formdatas.field.folderName,
						dictType: formdatas.field.dictType,
					},
					callback = function (data) {
						if (data.success) {
							myUtil.msg({
								type: "success",
								message: data.msg,
							});
							layer.close(index)
							renderTree();
						}
						layui.admin.removeLoading('body', true, true);
					};
				layui.admin.showLoading("body", 2, "8")
				myUtil.send(url, ajaxData, callback);
			},
			 /**
			 * 编辑目录
			 * @method editFloder
			 * @for dictManage
			 */
			editFloder: function (formdatas, index) {
				var url = setter.basePath + "u8DictController/updateDictFolder",
					active = myUtil.getSelectedNode(treeId),
					ajaxData = {
						folderName: formdatas.field.folderName,
						id: active.id,
						dictType: formdatas.field.dictType,
					},
					callback = function (data) {
						if (data.success) {
							myUtil.msg({
								type: "success",
								message: data.msg,
							});
							layer.close(index);
							renderTree();
						}
						layui.admin.removeLoading('body', true, true);
					};
				layui.admin.showLoading("body", 2, ".8")
				myUtil.send(url, ajaxData, callback);
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
			 * @param datakeys data参数 id和name不同
			 */
			renderDialogzTree: function renderDialogzTree(id, left, top, url, ajaxData, nodeId, callback, key, treeData, removeBefore, datakeys) {
				var isSelectCheckbox = (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') ? true : false,
					treeTemp = '<div class="dialog-tree-box">' +
						'<div class="search-box layui-form-item-sm"><input id="LAY-' + id + '-tree-search" class="layui-input" style="background: #FAFAFA;height: 28px;margin: 0 auto;margin-top: 5px;border-radius: 15px;width: 95%;" autocomplete="off"> <i class="iconfont" id="LAY-' + id + '-tree-search-btn" style="position: absolute;top: 7px;right: 10px;font-size: 12px;cursor: pointer;">&#xe628;</i></div><div id="LAY-' + id + '-box"  class=" ztree-box treebox-scroll" style="' + (isSelectCheckbox ? 'top:30px' : '') + '"><ul class="lay-ztree ztree" id="LAY-' + id + '-tree" lay-filter="lay-' + id + '-tree"></ul></div></div>',
					ajaxData = ajaxData || { data: 'false' },
					date = new Date().getTime();
				cxt.show(treeTemp, left, top, true, removeBefore);
				layui.admin.showLoading('.dialog-tree-box', 2, '.8');
				var url = setter.basePath + url,
					box = $(".dialog-tree-box .ztree-box"),
					eventTree = function (treeDatas) {
						var treeEl = box.find('#LAY-' + id + '-tree'),
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
								data: datakeys,
								callback: {
									onClick: function (event, treeId, treeNode) {
										if (callback) callback('click', treeId, treeNode)
									},
									onBeforeFuzzy: function () {
										layui.admin.showLoading('#LAY-' + id + '-tree', 2, '.8');
									},
									onAfterFuzzy: function () {
										var ele = document.getElementById('LAY-' + id + '-box')
										ele.scrollTop = 0;
										layui.admin.removeLoading('#LAY-' + id + '-tree', true, true);
									},
								}
							};
						if (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') {
							ztreeobj.check = { enable: true };
							ztreeobj.callback['onCheck'] = function (event, treeId, treeNode) {
								if (callback) callback('check', treeId, treeNode);
							}
						}
						treeEl.off('click').on('click', function (e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						})
						if (treeData && treeData.treeIds) {
							var last = treeData.selectlast + '' === 'true' ? true : false;
							eachCheckNode(treeDatas, treeData.treeIds, last);
						}

						var treeObj = jQuery.fn.zTree.init($('#LAY-' + id + '-tree'), ztreeobj, treeDatas);
						treeObj.expandAll(true)
						$("#LAY-" + id + '-tree-search').off("click").on("click", function (e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						})
						$("#LAY-" + id + '-tree-search-btn').off("click").on("click", function (e) {
							var evt = window.event || arguments.callee.caller.arguments[0];
							evt.preventDefault();
							evt.stopPropagation();
						})
						fuzzySearch2('LAY-' + id + '-tree', "#LAY-" + id + '-tree-search', false, true, "#LAY-" + id + '-tree-search-btn');
						if (box && !box.hasClass("ps")) {
							new pscrollbar(box[0]);
							box.addClass("treebox-scroll");
							box.find('.ps__rail-x').on('click', function (e) {
								var evt = window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							});
							box.find('.ps__rail-y').on('click', function (e) {
								var evt = window.event || arguments.callee.caller.arguments[0];
								evt.preventDefault();
								evt.stopPropagation();
							});
						}
						if (nodeId) {
							setTimeout(function () {
								if (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') {
									var nodeIds = nodeId.split(',')
									if (nodeIds.length > 0) {
										for (var n = 0; n < nodeIds.length; n++) {
											var nodes = treeObj.getNodesByParam(datakeys.simpleData.idKey, nodeIds[n], null);
											treeObj.checkNode(nodes[0], true, true, true);
											if (n == 0) {
												treeObj.selectNode(nodes[0]);
											}
										}
									}
								} else {
									treeObj.selectNode(treeObj.getNodeByParam('id', nodeId, null));
								}
							}, 50)

						}
						layui.admin.removeLoading('.dialog-tree-box', true, true);
					};
				if (treeData && treeData.treeNodes) {
					eventTree(treeData.treeNodes);
				} else {
					myUtil.send(url, ajaxData, function (data) {
						if (data.success) {
							var treeNodes = key ? data.obj : data.obj.treeNodes;
							eventTree(treeNodes);
						}
					});
				}

				function eachCheckNode(nodes, ids, last) {
					if (nodes.length) {
						nodes.forEach(function (v, i) {
							if (ids.indexOf(v.id) > -1) {
								v.checked = 'true';
							} else {
								delete v.checked;
							}
							if (v.children && v.children.length) {
								if (last) {
									v.nocheck = true;
								} else {
									delete v.nocheck;
								}
								eachCheckNode(v.children, ids);
							} else {
								v.iconSkin = 'iconfont icon-iconfontfile2 file';
							}
						})
					}
				}
			},
		};



	/**
	* 绑定树操作
	* @method eventTreeBar
	* @for UserManage
	*/
	function eventTreeBar() {
		$("#LAY-tree-add").off("click").on("click", function () {
			layui.admin.open({
				title: "新建目录结构",
				id: "LAY-admin-createOrg",
				content: createFloderTemp,
				success: function (layero, index) {
					form.render();
					form.on('submit(createOrg)', function (data) {
						F.createFloder(data, index);
						return false;
					});
				},
				yes: function (index, layero) {
					$("#createOrg-submit").trigger("click");
				}
			});
		});
		$("#LAY-tree-edit").off("click").on("click", function () {
			var node = myUtil.getSelectedNode(treeId);
			var nodes = setter.eleTreeObj.getData()
			if (node.id == false || !node.id) {
				myUtil.msg({
					type: "warning",
					message: "请选择需要编辑的档案节点",
				});
				return;
			} else {
				layui.admin.open({
					title: "修改档案目录名称",
					content: createFloderTemp,
					success: function (layero, index) {
						for (var i = 0; i < nodes.length; i++) {
							if (nodes[i].id == node.id) {
								var node2 = nodes[i]
							}
						}
						$(".dictType select").addClass("layui-disabled").attr({"disabled": 'disabled', 'lay-verify': ''})
						form.render();
						form.val("createOrg-form", {
							"folderName": node.name,
							"dictType": node2.dictType,

						});
						form.on('submit(editItem)', function (data) {
							F.editFloder(data, index);
							return false;
						});
					},
					yes: function (index, layero) {
						$("#editItem-submit").trigger("click");
					}
				});
			}

		});
		//  删除节点
		$("#LAY-tree-remove").off("click").on("click", function () {
			var node = myUtil.getSelectedNode(treeId);
			if (node.id && node.id != "0") {
				layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">' + node.name + '</span>吗？', {
					icon: 3,
					title: '删除档案',
					skin: 'layui-layer-admin'
				},
					function (index, layero) {
						var url = setter.basePath + "u8DictController/deleteDictFolder",
							ajaxdata = {
								id: node.userId || node.id
							},
							callback = function (data) {
								if (data.success) {
									myUtil.msg({ type: "success", message: data.msg })
									var obj = {
										result: []
									}
									renderTree()
									layer.close(index)
								}
								layer.close(index)
								layui.admin.removeLoading('body', true, true);
							};
						layui.admin.showLoading('body', 2, ".8");
						myUtil.send(url, ajaxdata, callback);
					}
				);
			} else {
				myUtil.msg({
					type: "warning",
					message: "请选择需要删除的档案节点",
				});
			}
		});
		$("#LAY-tree-openSearch").off("click").on("click", function () {
			$(".layui-nav-searchbox").removeClass("layui-hide");
		});
		$("#LAY-tree-closeSearch").off("click").on("click", function () {
			$(".layui-nav-searchbox").addClass("layui-hide");
			$("#LAY-tree-search").val("").trigger("change");
		});
		$("#LAY-tree-search").off("change").on("change", function () {
			setter.eleTreeObj.search($(this).val());
		});
		
		$(".dict-add").off("click").on("click", function(){
			var nodeleft = myUtil.getSelectedNode(treeId),
			node = jQuery.fn.zTree.getZTreeObj(dectListTreeId).getSelectedNodes()[0],
			newData = {};
			if(nodeleft){
				if (node) {
					newData.node = node;
				} else {
					newData.node = {
						cIndex: '0'
					};
				}
				newData.Pdata = nodeleft;
				newArchivesDataCreate(newData);
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先增加财务档案目录"
				});
			}
		})
		$(".dict-del").off("click").on("click", function(){
			var node = jQuery.fn.zTree.getZTreeObj(dectListTreeId).getSelectedNodes()[0];
			var nodeleft = myUtil.getSelectedNode(treeId);
			if(nodeleft){
				if (node && node.id) {
					layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">' + node.dictName + '</span>吗？', {
						icon: 3,
						skin: 'layui-layer-admin',
						title: '删除节点'
					},
						function (index, layero) {
							var url = setter.basePath + "u8DictController/deleteDict",
								ajaxdata = {
									ids: [node.id],
									folderId: nodeleft.id
								},
								callback = function (data) {
									if (data.msg != "删除失败") {
										myUtil.msg({ type: "success", message: data.msg })
										renderSecTree(nodeleft)
										layer.close(index)
									} else {
										myUtil.msg({ type: "error", message: data.msg })
									}
									layui.admin.removeLoading('body', true, true);
								};
							layui.admin.showLoading('body', 2, '.8');
							myUtil.send(url, ajaxdata, callback);
						}
					);
				} else {
					myUtil.msg({ type: "warning", message: "请选择要删除的节点" })
				}
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先增加财务档案目录"
				});
			}
		})
		$(".dict-edit").off("click").on("click", function(){
			var newData = {};
			newData.node = jQuery.fn.zTree.getZTreeObj(dectListTreeId).getSelectedNodes()[0];
			newData.Pdata = myUtil.getSelectedNode(treeId);
			if(newData.Pdata){
				if (!newData.node) {
					myUtil.msg({
						type: "warning",
						message: "请先选择要编辑的档案数据"
					});
					return
				} else {
					newArchivesDataCreate(newData, "edit");
				}
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先增加财务档案目录"
				});
			}
		})
		/**
		 * 点击同步数据操作
		 * @method synData
		 * @for UserManage
		 */
		$('.dict-synData').off('click').on('click', function () {
			synData()
		})
		// 搜索表单列表
		$("#allSearch").off("click").on("click", function () {
			var node = myUtil.getSelectedNode(treeId)
			var accountInfoId = $('.acountInfoId').attr('data-id');
			var years = $('.MatchingYear').attr('data-id')
			var url = setter.basePath + "u8ItemFormulaController/queryItemFormulaByParentId",
				ajaxData = {
					acountInfoId: accountInfoId,
					itemYear: years,
					size: loadTableSize,
					page: "1",
					parentId: node.id || '',
					search: $("#allSearchInput").val()
				},
				callback = function (data) {
					if (data.success) {
						initTable(data.obj)
						initPageCount(data)
						layui.admin.removeLoading("body", true, true)
					}
					layui.admin.removeLoading("body", true, true)
				}
			layui.admin.showLoading("body", 2, ".8")
			myUtil.send(url, ajaxData, callback)
		})

	}

	/**
	 * 左侧菜单树推拉方法初始化
	 * @method navbarPushPull
	 * @for UserManage
	 */
	function navbarPushPull() {
		$(".layui-navbar-collapse").off("click").on("click", function () {
			$(".wrapper").toggleClass("show-layui-navbar");
		});
	}

	/**
	 * 左侧树初始化
	 * @method renderTree
	 * @for UserManage
	 */

	function renderTree() {
		var url = setter.basePath + "u8DictController/queryDictFolder",
			ajaxData = {
				dictType: ""
			};
		callback = function (data) {
			if (data.success) {
				if (data.obj && data.obj.length) {
					data.obj.forEach(function (v, i) {
						v.type=v.dictType
						
					})
				}
				if (JSON.stringify(data.obj) === '[]') {
					$(".IndexDirectory").addClass("active");
					var node = {};
					node.search = "";
					node.id = "0";
				}
				var leaf = {
					'item_dict': "iconfont icon-kemu",
					"product_dict":'iconfont icon-dic-list',
					"company_dict":'iconfont icon-gongsi',
					"department_dict":"iconfont icon-navicon-jgda",
					"person_dict":'iconfont icon-gerendangan',
					'item_project': 'iconfont icon-project'
				}
				var leaf = $.extend({}, setter.leafIcon, leaf);
				setter.eleTreeObj = eleTree.render({
					elem: "#" + treeId,
					data: data.obj,
					expandOnClickNode: false,
					highlightCurrent: true,
					renderAfterExpand: false,
					defaultExpandAll: true,
					request: {
						name: "folderName",
					},
					leafIcon: leaf,
					searchNodeMethod: function (value, data) {
						if (!value) return false;
						return data.folderName.indexOf(value) !== -1;
					}
				});
				eleTree.on('nodeClick(layui-dictList-trees)', function (d) {
					alltreetypes=d.data.currentData.dictType
					var node = d.data.currentData;
					node.search = "";

					renderSecTree(node);
					uploadData = ""
				});
				if (data.obj.length) {
					$("#" + treeId + ' .eleTree-node[data-id="' + data.obj[0].id + '"]').children(".eleTree-node-content").trigger("click");
				}
				layui.admin.removeLoading('body', true, true);
			}
			layui.admin.removeLoading('body', true, true);
		};
		layui.admin.showLoading('body', 2, '.8');
		myUtil.send(url, ajaxData, callback);
	}

	/**
	 * 渲染右侧树
	 * @method renderSecTree
	 * @param  {String} $data 
	 * @param  下面为arguments详细
	 * @param  {String} dictType
	 */
	function renderSecTree($data, dictCode) {
	
		var url = setter.basePath + "u8DictController/queryDictContent",
			ajaxData = {
				folderId: $data.id
			},
			ztreeScrollTop = $(".lay-ztree").scrollTop(),
			callback = function (data) {
				if (data.success) {
					var ztrees = `<ul class="lay-ztree ztree" style="position: relative;height: 100%;" id="LAY-dictManage-tree"></ul>`;
					$('.treeWrap').html(ztrees)

					$(".treeWrap").removeClass("layui-hide")
					$(".impWrap").addClass("layui-hide")
					$("#imp").removeClass("impactive")

					eventTree = function (treeData) {
						setter.DictManageTree = $('#LAY-dictManage-tree'),
							ztreeobj = {
								view: {
									selectedMulti: false,
									showLine: false,
									expandSpeed: 10,
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
										name: 'title'
									},
								},
								callback: {
									onBeforeFuzzy: function () {
										var zTreeId = 'treeWrap';
										layui.admin.showLoading('.' + zTreeId, 2, '.8');
									},
									onAfterFuzzy: function () {
										var zTreeId = 'treeWrap';
										var ele = document.getElementById('LAY-dictManage-tree')
										ele.scrollTop = 0;
										layui.admin.removeLoading('.' + zTreeId, true, true);
									},
								}
							};
						if (treeData && treeData.checkbox && (treeData.checkbox + "") === 'true') {
							ztreeobj.check = { enable: true };
						}
						treeObjObj = jQuery.fn.zTree.init($('#LAY-dictManage-tree'), ztreeobj, treeData);
						treeObjObj.expandAll(true)
						var box = $('#LAY-dictManage-tree');
						if (box && !box.hasClass("ps")) {
							new pscrollbar(box[0]);
							box.addClass("treebox-scroll");
						}
						if(ztreeScrollTop){
							box.scrollTop(ztreeScrollTop);
						}
						if(dictCode){
							treeObjObj.selectNode(treeObjObj.getNodeByParam("dictCode", dictCode, null), true, true);
						}
					}
					if (data && data.obj) {
						var last = data.selectlast + '' === 'true' ? true : false;
						eachCheckNode(data.obj, [], last);
					}
					function eachCheckNode(nodes, ids, last) {
						var active = myUtil.getSelectedNode(treeId);
						var nodess = setter.eleTreeObj.getData()
						for (var i = 0; i < nodess.length; i++) {
							if (nodess[i].id == active.id) {
								var node2 = nodess[i]
							}
						}
						
						if (nodes.length) {
							if (node2.dictType == 'item_dict') {
								var icons = 'iconfont icon-kemu ';
							} else if (node2.dictType == 'product_dict') {
								var icons = 'iconfont icon-dic-list ';
							}else if (node2.dictType == 'company_dict') {
								var icons = 'iconfont icon-gongsi ';
							}else if (node2.dictType == 'department_dict') {
								var icons = 'iconfont icon-navicon-jgda ';
							}else if (node2.dictType == 'person_dict') {
								var icons = 'iconfont icon-gerendangan ';
							}else{
								var icons = 'iconfont icon-project ';
							}

							nodes.forEach(function (v, i) {
								if (ids.indexOf(v.id) > -1) {
									v.checked = 'true';
								} else {
									delete v.checked;
								}
								if (v.children && v.children.length) {
									if (last) {
										v.nocheck = true;
									} else {
										delete v.nocheck;
									}
									v.iconSkin = icons;
									eachCheckNode(v.children, ids);
								} else {
									v.iconSkin = icons;
								}
							})
						}
					}
					if (data && data.obj) {
						eventTree(data.obj);
						fuzzySearch2('LAY-dictManage-tree', ".searchInput", false, true);
					}
				} else {
					$('#LAY-dictManage-tree').html('')
				}
				layui.admin.removeLoading('body', true, true);
			};
		layui.admin.showLoading('body', 2, '.8')
		myUtil.send(url, ajaxData, callback);
	}

	

	function synData() {
		var active = myUtil.getSelectedNode(treeId);
		var nodess = setter.eleTreeObj.getData();
		if(active){
			for (var i = 0; i < nodess.length; i++) {
				if (nodess[i].id == active.id) {
					var node2 = nodess[i]
				}
			}
			if (node2.dictType == 'item_dict') {
				layui.admin.open({
					title: ['数据同步'],
					type: 1,
					area: ['340px', '250px'],
					content: synchronousdataKemu,
					btn: ['确认', '取消'],
					shade: 0.3,
					success: function (layero, index2) {
						form.render();
						form.on('submit(synchronousData-submit)', function (data) {
	
							layer.confirm('同步将删除现有的数据，是否确定同步数据？', {
								icon: 3,
								title: '数据同步',
								skin: 'layui-layer-admin'
							}, function (index) {
								var acountInfoId = $('.acountInfoId').attr('data-id');
								var MatchingYear = $('.MatchingYear').attr('data-id');
								var synGrade = $('.synGrade').attr('data-id');
								var ajaxData = {
									"id": active.id,
									"synAccountInfoId": acountInfoId,
									"synItemYear": MatchingYear,
									"synGrade": synGrade
								}
								var url = setter.basePath + "u8DictController/synchronizeData";
								callback = function (data) {
									if (data.success) {
										myUtil.msg({
											type: "success",
											message: data.msg,
										});
										var datas = {
											id: active.id
										}
										renderSecTree(datas)
									}
									layui.admin.removeLoading('body', true, true);
								}
								layui.admin.showLoading('body', 2, '.8');
								layer.close(index)
								layer.close(index2)
								myUtil.send(url, ajaxData, callback);
							});
						});
						$(".acountInfoId").off('click').on('click', function (e) {
							e.stopPropagation();
							var that = $(this),
								left = that.offset().left,
								top = that.offset().top + that.height(),
								id = 'acountInfoId',
								url = "u8AccountController/queryAccounts",
								removeBefore = '',
								treeNodes = { treeNodes: treeNodes, checkbox: 'false', treeIds: id || [] },
								datakeys = {
									key: {
										name: 'accountName'
									},
									simpleData: {
										idKey: "id",
									}
								},
								callback = function (type, treeId, node) {
									event.stopPropagation();
									var id = $('.acountInfoId').attr('data-id')
									if (id) {
										if (id !== node.id) {
											$('.acountInfoId').val(node.accountName)
											$('.acountInfoId').attr('data-id', node.id)
											$('.MatchingYear').val('')
											$('.MatchingYear').attr('data-id', '')
										}
									} else {
										$('.acountInfoId').val(node.accountName)
										$('.acountInfoId').attr('data-id', node.id)
									}
									$(".ctxMenu").remove();
								}
							F.renderDialogzTree(id, left, top, url, '', that.attr('data-id'), callback, 'true', treeNodes, removeBefore, datakeys);
						})
	
						$(".MatchingYear").off('click').on('click', function (e) {
							var accountInfoId = $('.acountInfoId').attr('data-id');
							if (accountInfoId) {
								e.stopPropagation();
								var that = $(this),
									left = that.offset().left,
									top = that.offset().top + that.height(),
									id = 'MatchingYear',
									url = "u8DictController/queryU8ItemYear",
									removeBefore = '',
									treeNodes = { treeNodes: treeNodes, checkbox: 'false', treeIds: id || [] },
									datakeys = {
										key: {
											name: 'name'
										},
										simpleData: {
											idKey: "value",
										}
									},
									callback = function (type, treeId, node) {
										event.stopPropagation();
	
										$('.MatchingYear').val(node.name)
										$('.MatchingYear').attr('data-id', node.value)
										$(".ctxMenu").remove();
									}
								F.renderDialogzTree(id, left, top, url, { accountInfoId: accountInfoId }, that.attr('data-id'), callback, 'true', treeNodes, removeBefore, datakeys);
							} else {
								myUtil.msg({
									type: "error",
									message: '请先选择账套名称',
								});
							}
						})
	
						$('.synGrade').off('click').on('click', function (e) {
							var accountInfoId = $('.acountInfoId').attr('data-id');
							var MatchingYear = $('.MatchingYear').attr('data-id');
							if (accountInfoId && MatchingYear) {
								e.stopPropagation();
								var that = $(this),
									left = that.offset().left,
									top = that.offset().top + that.height(),
									id = 'MatchingYear',
									url = "u8DictController/queryU8ItemLevel",
									removeBefore = '',
									treeNodes = { treeNodes: treeNodes, checkbox: 'false', treeIds: id || [] },
									datakeys = {
										key: {
											name: 'name'
										},
										simpleData: {
											idKey: "value",
										}
									},
									callback = function (type, treeId, node) {
										event.stopPropagation();
										$('.synGrade').val(node.name)
										$('.synGrade').attr('data-id', node.value)
										$(".ctxMenu").remove();
									}
								F.renderDialogzTree(id, left, top, url, { accountInfoId: accountInfoId, iyear: MatchingYear }, that.attr('data-id'), callback, 'true', treeNodes, removeBefore, datakeys);
							} else {
								myUtil.msg({
									type: "error",
									message: '请先选择账套名称和年份',
								});
							}
						})
					},
					yes: function (index, layero) {
						$("#synchronousData-submit").trigger("click");
					}
				});
			} else if(node2.dictType =="company_dict"){
				layer.confirm('是否同步已启用的账套信息？',{icon: 3, title:'提示', skin: "layui-layer-admin"},
						function(index, layero){
							var url = setter.basePath + "u8DictController/synchronizeData";
							var ajaxData = {
								"id": active.id
							}
								callback = function (data) {
									if (data.success) {
										myUtil.msg({
											type: "success",
											message: data.msg,
										});
										var datas = {
											id: active.id
										}
										renderSecTree(datas)
									}
									layui.admin.removeLoading('body', true, true);
								}
								layui.admin.showLoading('body', 2, '.8');
								layer.close(index)
								myUtil.send(url, ajaxData, callback);
						}
					);
			}else {
				layui.admin.open({
					title: ['数据同步'],
					type: 1,
					area: ['340px', '200px'],
					content: synchronousdataxiangmu,
					btn: ['确认', '取消'],
					shade: 0.3,
					success: function (layero, index2) {
						form.render();
						form.on('submit(synchronousData-submit)', function (data) {
							layer.confirm('同步将删除现有的数据，是否确定同步数据？', {
								icon: 3,
								title: '数据同步',
								skin: 'layui-layer-admin'
							}, function (index) {
								var acountInfoId = $('.acountInfoId').attr('data-id');
								var ajaxData = {
									"id": active.id,
									"synAccountInfoId": acountInfoId,
									"synItemYear": '',
									"synGrade": ''
								}
								var url = setter.basePath + "u8DictController/synchronizeData";
								callback = function (data) {
									if (data.success) {
										myUtil.msg({
											type: "success",
											message: data.msg,
										});
										var datas = {
											id: active.id
										}
										renderSecTree(datas)
									}
									layui.admin.removeLoading('body', true, true);
								}
								layui.admin.showLoading('body', 2, '.8');
								layer.close(index)
								layer.close(index2)
								myUtil.send(url, ajaxData, callback);
							});
						});
						$(".acountInfoId").off('click').on('click', function (e) {
							e.stopPropagation();
							var that = $(this),
								left = that.offset().left,
								top = that.offset().top + that.height(),
								id = 'acountInfoId',
								url = "u8AccountController/queryAccounts",
								removeBefore = '',
								treeNodes = { treeNodes: treeNodes, checkbox: 'false', treeIds: id || [] },
								datakeys = {
									key: {
										name: 'accountName'
									},
									simpleData: {
										idKey: "id",
									}
								},
								callback = function (type, treeId, node) {
									event.stopPropagation();
									var id = $('.acountInfoId').attr('data-id')
									if (id) {
										if (id !== node.id) {
											$('.acountInfoId').val(node.accountName)
											$('.acountInfoId').attr('data-id', node.id)
											$('.MatchingYear').val('')
											$('.MatchingYear').attr('data-id', '')
										}
									} else {
										$('.acountInfoId').val(node.accountName)
										$('.acountInfoId').attr('data-id', node.id)
									}
									$(".ctxMenu").remove();
								}
							F.renderDialogzTree(id, left, top, url, '', that.attr('data-id'), callback, 'true', treeNodes, removeBefore, datakeys);
						})
					},
					yes: function (index, layero) {
						$("#synchronousData-submit").trigger("click");
					}
				});
			}
		}else{
			myUtil.msg({
				type: "warning",
				message: "请先增加财务档案目录"
			});
		}
	}

	/**
	 * 新增或编辑节点渲染
	 * @method newArchivesDataCreate
	 * @for UserManage
	 */
	function newArchivesDataCreate(val, methods) {
		var temp = '<div class="newArchivesDataCreate"><form class="layui-form" lay-filter="archivesForm" action="" style="padding: 20px 30px 0;">'+
			'<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:100px"><i style="color: red;margin-right: 5px;">*</i>档案数据编码：</label><div class="layui-input-inline"><input type="text" name="dictCode" value="" placeholder="请输入档案编码" required lay-verType="tips" lay-verify="required" autocomplete="off" class="layui-input"></div></div>'+
			'<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:100px"><i style="color: red;margin-right: 5px;">*</i>档案数据名称：</label><div class="layui-input-inline"><input type="text" name="dictName"  placeholder="请输入档案名称" required lay-verType="tips" lay-verify="required" autocomplete="off" class="layui-input"></div></div>'+
			'{{contents}}<div class="layui-form-item layui-form-item-sm layui-hide"><button class="layui-btn sub" lay-submit lay-filter="formDemo">立即提交</button></div></form></div>',
			contents = [];

		if(val.Pdata && val.Pdata.propertyCount){
			for (var a = 0; a < val.Pdata.propertyCount; a++) {
				contents.push('<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:100px">' + val.Pdata.propertyName[a] +'</label><div class="layui-input-inline"><input type="text"   name="ext' + (a + 1) +'"   placeholder="" autocomplete="off" class="layui-input"></div></div>');
			};
		}
		temp = temp.replace(/{{contents}}/g, contents.join(""));
		var newArchivesDataCreateDialog = layui.admin.open({
			type: 1,
			title: methods=="edit"?'编辑档案数据':'新建档案数据',
			content: temp,
			btn: ['确认', '取消',],
			success: function(layero, index){
				form.val("archivesForm",{
					dictName: val.node && val.node.dictName || "",
					dictCode: val.node && val.node.dictCode || "",
				});
				form.render(null, "archivesForm");
				form.on('submit(formDemo)', function (data) {
					if(data.field.dictCod==''||data.field.dictName==''){
						myUtil.msg({ type: "warning", message: "必填项不能为空" })
						return false;
					}
					if (methods == "edit") {
						var url = setter.basePath + "u8DictController/updateDict";
						var ajaxData = {
							dictCode: data.field.dictCode,
							dictName: data.field.dictName,
							id: val.node.id,
							folderId: val.Pdata.id,
						};
					} else {
						var url = setter.basePath + "u8DictController/saveDict";
						var level = Number(val.node.cIndex);
						var ajaxData = {
							parentId: val.node.id || "0",
							folderId: val.Pdata.id,
							dictCode: data.field.dictCode,
							dictName: data.field.dictName,
							level: level + 1 || "1",
						};
					}
					var callback = function (data) {
						if (data.success) {
							layer.close(newArchivesDataCreateDialog);
							renderSecTree(val.Pdata, ajaxData.dictCode);
						}
						layui.admin.removeLoading('body', true, true);
					}
					layui.admin.showLoading('body', 2, '.8')
					myUtil.send(url, ajaxData, callback);
					return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
				});

			},
			yes: function (index, layero) {
				layero.find(".layui-btn.sub").trigger("click");
			},
		});

	}

	/**
	 * 导入
	 * @method importData
	 * @for UserManage
	 */
	function importData() {
		var node = "";
		$("#imp").off("click").on("click", function () {
			node = myUtil.getSelectedNode(treeId);
			if (node) {
				$(".treeWrap").addClass("layui-hide")
				$(".impWrap").toggleClass("layui-hide")
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
				if (!$(".impWrap").is(".layui-hide")) {
					$("#imp").addClass("impactive")
				} else {
					$("#imp").removeClass("impactive")
					uploadData = ""
					renderSecTree({ id: node.id })
				}
			} else {
				myUtil.msg({ type: "warning", message: "请选择档案目录" })
			}

		})

		$(".goBack").off("click").on("click", function () {
			$(".performImport").addClass("layui-hide")
			$(".impcoutent").removeClass("layui-hide")
			$(".secondStep").css({
				"background": "#eee",
				"color": "#666"
			})
			$(".secondStep em").css({
				"border-left": "10px solid #eee"
			})
		})
		$(".downExcelfirst").off("click").on("click", function () {
			var subForm = $("#exceliframes").contents().find("#export-form"),
				formId = $("#exceliframes").contents().find("#formId")
			if (!subForm[0]) {
				$("#exceliframes").contents().find("body").html('<form id="export-form" action="' + setter.basePath +
					'u8DictController/downloadTemplate" enctype="multipart/form-data" method="post"><input id="formId" type="hidden" name="folderId"></form>'
				);
			}
			formId = $("#exceliframes").contents().find("#formId");
			subForm = $("#exceliframes").contents().find("#export-form");
			formId.val(node.id);
			subForm.submit();
		})
		// 点击上传模板
		$(".uploadTemplateFirst").off("click").on("click", function () {
			$("#uploadTemplateFirst").trigger("click")
		})
		$("#uploadTemplateFirst").off("change").on("change", function () {
			$(".fristUploadFilename").html($("#uploadTemplateFirst").val())
			$("#folderId").val(node.id)
			var forms = $("#firstuploadForm")[0],
				formsdata = new FormData(forms)
			layui.admin.showLoading("body", 2, ".8")
			$.ajax({
				type: "POST",
				url: setter.basePath + "u8DictController/uploadFile",
				data: formsdata,
				contentType: false,
				processData: false,
				success: function (data) {
					if (data.success) {
						myUtil.msg({ type: "success", message: data.msg })
						uploadData = data
					} else {
						myUtil.msg({ type: "error", message: data.msg })
					}
					layui.admin.removeLoading("body", true, true)
				},
				error: function (msg) {
					myUtil.msg({ type: "error", message: "文件上传失败" })
					layui.admin.removeLoading("body", true, true)
				}
			});
		});
		$(".firstStepButton").off("click").on("click", function () {
			if (uploadData) {
				$(".impcoutent").addClass("layui-hide")
				$(".performImport").removeClass("layui-hide")
				$(".secondStep").css({
					"background": "#4785e6",
					"color": "#fff"
				})
				$(".secondStep em").css({
					"border-left": "10px solid #4785e6"
				})
				$(".bad").html(uploadData.obj.bad)
				$(".rightTem").html(uploadData.obj.right)
				secondStepTable(uploadData.obj.dictList)
			} else {
				myUtil.msg({ type: "warning", message: "请上传模板" })
			}
		})
		$(".secondStepButton").off("click").on("click", function () {
			var url = setter.basePath + "u8DictController/complyImportDict",
				ajaxData = {
					"_uuid": uploadData.obj._uuid,
					"folderId": node.id
				},
				callback = function (data) {
					if (data.success) {
						$(".performImport").addClass("layui-hide")
						$(".finish").removeClass("layui-hide")
						$(".thirdStep").css({
							"background": "#4785e6",
							"color": "#fff"
						})
						$(".thirdStep em").css({
							"border-left": "10px solid #4785e6"
						})
						var successNum = uploadData.obj.dictList.length
						$(".successNum").html(successNum)
					}
					layui.admin.removeLoading('body', true, true);
				}
			layui.admin.showLoading('body', 2, '.8');
			myUtil.send(url, ajaxData, callback)

		})
		// 点击完成
		$(".finishButton").off("click").on("click", function () {
			$("#imp").removeClass("impactive")
			var datas = {
				id: node.id
			}
			renderSecTree(datas)
			uploadData = ""
		})
	}

	/**
	 * 渲染执行导入里面的表格
	 * @method secondStepTable
	 * @for UserManage
	 */
	function secondStepTable($data) {
		if (!$data) $data = [];
		table.render({
			elem: "#secondStepTable",
			cellMinWidth: 80,
			height: isSm ? "full-380" : "full-395",
			cols: [[
				{ field: "index", title: "序号", align: "center", type: "numbers", width: 80 },
				{ field: "dictName", title: "档案名称", unresize: true, sort: false, align: "center", style: "font-size:12px" },
				{ field: "dictCode", title: "档案编码", templet: "#type", unresize: true, sort: false, align: "center", style: "font-size:12px" },
				{ field: "level", title: "层级", unresize: true, sort: false, align: "center", style: "font-size:12px" },
			]],
			data: $data,
			limit: $data.length,
			id: "allList"
		});
	}

	function fuzzySearch2(zTreeId, searchField, isHighLight, isExpand) {
		var zTreeObj = jQuery.fn.zTree.getZTreeObj(zTreeId);
		var nameKey = zTreeObj.setting.data.key.name;
		isHighLight = isHighLight === false ? false : true;
		isExpand = isExpand ? true : false;
		zTreeObj.setting.view.nameIsHTML = isHighLight;
		var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]';
		var rexMeta = new RegExp(metaChar, 'gi');
		function ztreeFilter(zTreeObj, _keywords, callBackFunc) {
			if (!_keywords) {
				_keywords = '';
			}
			function filterFunc(node) {
				if (node && node.oldname && node.oldname.length > 0) {
					node[nameKey] = node.oldname;
				}
				zTreeObj.updateNode(node);
				if (_keywords.length == 0) {
					zTreeObj.showNode(node);
					zTreeObj.expandNode(node, isExpand);
					return true;
				}
				if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase()) != -1) {
					if (isHighLight) {
						var newKeywords = _keywords.replace(rexMeta, function (matchStr) {
							return '\\' + matchStr;
						});
						node.oldname = node[nameKey];
						var rexGlobal = new RegExp(newKeywords, 'gi');
						node[nameKey] = node.oldname.replace(rexGlobal, function (originalText) {
							var highLightText =
								'<span style="color: whitesmoke;background-color: darkred;">'
								+ originalText
								+ '</span>';
							return highLightText;
						});
						zTreeObj.updateNode(node);
					}
					zTreeObj.showNode(node);
					return true;
				}

				zTreeObj.hideNode(node);
				return false;
			}
			var nodesShow = zTreeObj.getNodesByFilter(filterFunc);
			processShowNodes(nodesShow, _keywords);
		}
		function processShowNodes(nodesShow, _keywords) {
			if (nodesShow && nodesShow.length > 0) {
				if (_keywords.length > 0) {
					$.each(nodesShow, function (n, obj) {
						var pathOfOne = obj.getPath();
						if (pathOfOne && pathOfOne.length > 0) {
							for (var i = 0; i < pathOfOne.length - 1; i++) {
								zTreeObj.showNode(pathOfOne[i]);
								zTreeObj.expandNode(pathOfOne[i], true);
							}
						}
					});
				} else {
					var rootNodes = zTreeObj.getNodesByParam('level', '0');
					$.each(rootNodes, function (n, obj) {
						zTreeObj.expandNode(obj, true);
					});
				}
			}
		}

		if ($(searchField + '-btn')[0]) {
			$(searchField + '-btn').bind('i click', function () {
				var evt = window.event || arguments.callee.caller.arguments[0];
				evt.preventDefault();
				evt.stopPropagation();
				var _keywords = $(searchField).val();
				searchNodeLazy(_keywords);
			})
		}
		$(searchField).bind('input keydown', function (e) {
			var theEvent = e || window.event;
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
			if (code == 13) {
				var _keywords = $(this).val();
				searchNodeLazy(_keywords);
			}
		});

		var timeoutId = null;
		var lastKeyword = '';
		function searchNodeLazy(_keywords) {
			if (timeoutId) {
				clearTimeout(timeoutId);
			}
			if (zTreeObj.setting.callback.onBeforeFuzzy) zTreeObj.setting.callback.onBeforeFuzzy();
			timeoutId = setTimeout(function () {
				if (lastKeyword === _keywords) {
					if (zTreeObj.setting.callback.onAfterFuzzy) zTreeObj.setting.callback.onAfterFuzzy();
					return;
				}
				ztreeFilter(zTreeObj, _keywords);
				if (zTreeObj.setting.callback.onAfterFuzzy) zTreeObj.setting.callback.onAfterFuzzy();
				lastKeyword = _keywords;
			}, 500);
		}
	}

	! function () {
		$(".layui-nav-treebox").each(function (i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
				$(v).addClass("treebox-scroll");
			}
		});

		layui.sessionData("configTree", null);
		navbarPushPull();
		renderTree();
		importData()
		eventTreeBar();
	}();
	exports("dictManage", {})
});
