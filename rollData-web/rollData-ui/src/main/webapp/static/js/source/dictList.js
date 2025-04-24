;
layui.define(["wdUtil", "eleTree", "table", "form", 'setter', 'admin', 'jquery' , 'contextMenu' , 'pscrollbar', "treetable"], function(exports) {
	var $ = layui.jquery,
		laytpl = layui.laytpl,
		element = layui.element,
		setter = layui.setter,
		device = layui.device(),
		form = layui.form,
		myUtil = layui.wdUtil,
		eleTree = layui.eleTree,
		table = layui.table,
		laypage = layui.laypage,
		treetable = layui.treetable,
		cxt = layui.contextMenu,
		treeId = "LAY-dictList-tree",
		dectListTreeId = "LAY-dictListSec-tree",
		pscrollbar = layui.pscrollbar ,
		isSm = $('body').hasClass("layui-layout-sm-body"), 
		currentParentTreeNode = '',
		currentTableNodes = [],
		currentTreeNode = '',
		currentTableClickIndex = '',
		storeTableName = 'dictList', //本地存储表名
		uploadData = "",
		archivesDataType = '0',
		tierTableDatas = [],
		attributeTableDatas = [],
		currentTreeNodeParentDictCde = "0",
		archivesTemp = '<form action="" class="layui-form editArchives-form h100" id="editArchives-form" lay-filter="editArchives-form" autocomplete="off">'
			+'<div class="layui-form-item layui-form-item-sm">'
				+'<div class="layui-inline" style="margin-right: 44px;">'
					+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>档案编码：</label>'
					+' <div class="layui-input-block" style="width:219px;">'
						+' <input type="text" name="dictTypeCde" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入档案编码" autocomplete="off">'
					+' </div>'
			    +' </div>'
				+' <div class="layui-inline">'
					+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>档案名称：</label>'
					+' <div class="layui-input-block" style="width:219px;">'
						+' <input type="text" name="dictTypeName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="请输入档案名称" autocomplete="off">'
					+' </div>'
				+' </div>'
			+' </div>'
			+' <div class="layui-form-item layui-form-item-sm">'
				+'<div class="layui-inline" style="margin-right: 50px;">'
					+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>档案类型：</label>'
					+' <div class="layui-input-block">'
						+'<select class="layui-input" name="cType" id="cType" lay-filter="cType-form"><option value="0">基础档案</option><option value="1">数据档案</option></select>'
					+' </div>'
			    +' </div>'
				+'<div class="layui-inline tableName-box layui-hide">'
					+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>库表选择：</label>'
					+' <div class="layui-input-block layui-form-select">'
						+' <input type="text" name="tableName" class="layui-input tableName-input" required lay-verType="tips" lay-verify="required" placeholder="请输入库表名称" autocomplete="off">'
						+'<i class="layui-edge"></i>'
					+' </div>'
				+' </div>'
			+' </div>'
			+'<div class="cType-datafile-box layui-hide">'
				+'<div class="layui-form-item layui-form-item-sm">'
					+'<div class="layui-inline" style="margin-right: 44px;">'
						+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>依赖类型：</label>'
						+' <div class="layui-input-block">'
							+' <select class="layui-input relyType-select" name="relyType" lay-filter="relyType-form"><option value="0">无</option><option value="1">依赖父节点构建</option><option value="2">依赖长度构建</option></select>'
						+' </div>'
					+' </div>'
					+' <div class="layui-inline parentValue-box">'
						+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>父节点：</label>'
						+' <div class="layui-input-block layui-form-select">'
							+' <input type="text" name="parentValue" class="layui-input parentValue-input" required lay-verType="tips" lay-verify="required" placeholder="请输入父节点" autocomplete="off">'
							+'<i class="layui-edge"></i>'
						+' </div>'
					+' </div>'
				+' </div>'
				+' <div class="layui-form-item layui-form-item-sm">'
					+'<div class="layui-inline" style="margin-right: 50px;">'
						+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>实际值：</label>'
						+' <div class="layui-input-block layui-form-select">'
							+' <input type="text" name="realValue" class="layui-input realValue-input" required lay-verType="tips" lay-verify="required" placeholder="请输入实际值" autocomplete="off">'
							+'<i class="layui-edge"></i>'
						+' </div>'
					+' </div>'
					+'<div class="layui-inline">'
						+' <label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>显示值：</label>'
						+' <div class="layui-input-block layui-form-select">'
							+' <input type="text" name="showValue" class="layui-input showValue-input" required lay-verType="tips" lay-verify="required" placeholder="请输入显示值" autocomplete="off">'
							+'<i class="layui-edge"></i>'
						+' </div>'
					+' </div>'
				+' </div>'
			+' </div>'
			+'<div class="archivesInfo-box">'
				+'<div class="archivesInfo-left-box">'
					+'<div class="archivesInfo-box-title">层级属性设置</div>'
					+'<div class="archivesInfo-box-content">'
						+'<div style="height:50%">'
							+'<div class="layui-form-item layui-form-item-sm">'
								+'<label class="layui-form-label">层级数：</label>'
								+'<div class="layui-input-inline" style="width: 60px;">'
									+'<select class="codeLevel-input layui-select" name="codeLevel" lay-filter="codeLevel">'
										+'<option value="1" selected="selected">1</option>'
										+'<option value="2">2</option>'
										+'<option value="3">3</option>'
										+'<option value="4">4</option>'
										+'<option value="5">5</option>'
										+'<option value="6">6</option>'
										+'<option value="7">7</option>'
										+'<option value="8">8</option>'
										+'<option value="9">9</option>'
										+'<option value="10">10</option>'
										+'<option value="11">11</option>'
										+'<option value="12">12</option>'
										+'<option value="13">13</option>'
										+'<option value="14">14</option>'
										+'<option value="15">15</option>'
										+'<option value="16">16</option>'
										+'<option value="17">17</option>'
										+'<option value="18">18</option>'
										+'<option value="19">19</option>'
										+'<option value="20">20</option>'
									+'</select>'
								+'</div>'
								+'<div class="layui-btn layui-btn-sm layui-createLevel-btn layui-hide" style="position:absolute;right:10px;">生成层级属性</div>'
							+'</div>'
							+'<div class="tierTablebox layui-form-item-sm">'
								+'<table class="layui-table" id="tierTable" lay-filter="tierTable"></table>'
							+'</div>'
						+'</div>'
						+'<div style="height:50%">'
							+'<div class="layui-form-item layui-form-item-sm">'
								+'<label class="layui-form-label">属性数：</label>'
								+'<div class="layui-input-inline" style="width: 60px;">'
									+'<select name="propertyCount" class="propertyCount layui-select" lay-filter="propertyCount">'
										+'<option value="" selected="selected">无</option>'
										+'<option value="1">1</option>'
										+'<option value="2">2</option>'
										+'<option value="3">3</option>'
										+'<option value="4">4</option>'
										+'<option value="5">5</option>'
										+'<option value="6">6</option>'
										+'<option value="7">7</option>'
										+'<option value="8">8</option>'
										+'<option value="9">9</option>'
										+'<option value="10">10</option>'
										+'<option value="11">11</option>'
										+'<option value="12">12</option>'
										+'<option value="13">13</option>'
										+'<option value="14">14</option>'
										+'<option value="15">15</option>'
										+'<option value="16">16</option>'
										+'<option value="17">17</option>'
										+'<option value="18">18</option>'
										+'<option value="19">19</option>'
										+'<option value="20">20</option>'
									+'</select>'
								+'</div>'
							+'</div>'
							+'<div class="attributeTablebox layui-form-item-sm">'
								+'<table class="layui-table attributeTable" id="attributeTable" lay-filter="attributeTable"></table>'
							+'</div>'
						+'</div>'
					+'</div>'
				+'</div>'
				+'<div class="archivesInfo-right-box">'
					+'<div class="archivesInfo-box-title">结果预览</div>'
					+'<div class="archivesInfo-box-content">'
						+'<div class="showTree"><div class="lay-ztree ztree" id="dict-result-tree" lay-filter="dict-result-tree"></div></div>'
					+'</div>'
				+'</div>'
			+'</div>'
            +'<div class="layui-hide"><input type="text" name="dictTypeId" class="layui-input dictTypeId-input" autocomplete="off"><button id="editArchivesSub" class="layui-hide" lay-submit lay-filter="editArchivesSub">提交</button></div>'
        +' </form>',
		DEFAULT = {
			archivesObj : {
				"dictTypeId": "",
				"dictTypeName": "",
				"dictTypeCde": "",
				"codeLevel": "1",
				"levelTable": [
					{
						"sequence": 1,
						"digit": "1",
						"levelName": "层级一"
					}
				],
				"propertyCount": "",
				"propertyTable": [],
				"cType": '0',
				"relyType": '0',
				"tableName": "",
				"showValue": "",
				"realValue": "",
				"parentValue": "",
				"propertyColumns": []
			}
		},
		F = {
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
			renderDialogzTree: function renderDialogzTree(id, left, top, url, ajaxData, nodeId, callback, key, treeData, removeBefore, myrequest, noeach){
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
							if(treeNodes.length && typeof treeNodes[0] == 'string'){
								treeNodes.forEach(function(v, i){
									var obj = {id: v, name: v};
									treeNodes[i] = obj;
								})
							}
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
			editArchives: function(id){
				var title = id ? '编辑档案': '新建档案',
				chars = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十"]
				layui.admin.open({
				    title: title,
				    id: "LAY-editArchives",
					offset: '10px',
				    content: archivesTemp,
					area: ['720px', '680px'],
				    success: function(layero, index){
						var archivesData = myUtil.cloneObj(DEFAULT.archivesObj),
						createLevelPorpertyState = false;
						tierTableDatas = [];
						attributeTableDatas = [];
						if(id){
							var url = setter.basePath + "sysDictionaryController/queryDictDataById",
								ajaxData = {
									event: "query-md",
									settings: {dictTypeId: id}
								},
								callback = function(data) {
									if (data.success) {
										archivesData = $.extend({}, archivesData, data.obj);
										archivesData.cType = data.obj.ctype;
										renderform();
									}
									layui.admin.removeLoading('.editArchives-form', true, true);
								};
								layui.admin.showLoading('.editArchives-form', 2, '.8')
								myUtil.send(url, ajaxData, callback)
						}else{
							renderform();
						}
						function renderform(){
							form.val('editArchives-form',{
								"dictTypeId": archivesData.dictTypeId,
								"dictTypeName": archivesData.dictTypeName,
								"dictTypeCde": archivesData.dictTypeCde,
								"codeLevel": archivesData.codeLevel,
								"propertyCount": archivesData.propertyCount,
								"cType": archivesData.cType,
								"relyType": archivesData.relyType,
								"tableName": archivesData.tableName,
								"showValue": archivesData.showValue,
								"realValue": archivesData.realValue,
								"parentValue": archivesData.parentValue
							})
							form.render(null, 'editArchives-form');
							archivesDataType = archivesData.cType;
							F.editArchivesState();
							if(archivesDataType == '1'){
								var levelPorpertyObj = {
									"dictTypeId": archivesData.dictTypeId,
									"dictTypeName": archivesData.dictTypeName,
									"dictTypeCde": archivesData.dictTypeCde,
									"propertyCount": archivesData.propertyCount,
									"propertyTable": archivesData.propertyTable || [],
									"cType": archivesData.cType,
									"relyType": archivesData.relyType,
									"tableName": archivesData.tableName,
									"showValue": archivesData.showValue,
									"realValue": archivesData.realValue,
									"parentValue": archivesData.parentValue,
									"propertyColumns": archivesData.propertyColumns || []
								}
								F.getDigitData(levelPorpertyObj);
							}else{
								tierTableDatas = archivesData.levelTable || [];
								F.renderTierTable();
								F.renderLevelPropertyPrev();
							}
							var count = archivesData.propertyCount ? parseInt(archivesData.propertyCount) : 0;
							if(count){
								for(var i = 0; i < count; i++){
									attributeTableDatas[i] = {
										propertyTable: archivesData.propertyTable && archivesData.propertyTable.length && archivesData.propertyTable[i] ? archivesData.propertyTable[i] : '属性' + chars[i]
									}
									if(archivesDataType == '1'){
										attributeTableDatas[i].propertyColumns = archivesData.propertyColumns && archivesData.propertyColumns.length && archivesData.propertyColumns[i] &&  archivesData.propertyColumns[i] !== undefined ? archivesData.propertyColumns[i] : '';
									}
								}
							}
							F.renderAttrbuteTable();
							
							$(".layui-createLevel-btn").off('click').on('click', function(){
								createLevelPorpertyState = true;
								$("#editArchivesSub").trigger("click");
							})
							$(".tableName-input").off('click').on('click', function(){
								F.selectTableColumn($(this), '', [$(this).val() || ''], true);
							})
							$(".parentValue-input").off('click').on('click', function(){
								var value = $(".tableName-input").val();
								if(value){
									F.selectTableColumn($(this), value, [$(this).val() || ''], true);
								}else{
									myUtil.msg({type: 'warning', message: '请先选择库表字段！'})
								}
							})
							$(".realValue-input").off('click').on('click', function(){
								var value = $(".tableName-input").val();
								if(value){
									F.selectTableColumn($(this), value, [$(this).val() || ''], true);
								}else{
									myUtil.msg({type: 'warning', message: '请先选择库表字段！'})
								}
							})
							$(".showValue-input").off('click').on('click', function(){
								var value = $(".tableName-input").val();
								if(value){
									F.selectTableColumn($(this), value, [$(this).val() || ''], true);
								}else{
									myUtil.msg({type: 'warning', message: '请先选择库表字段！'})
								}
							})
							form.on('select(codeLevel)', function(data){
								var arr = [];
								for(var i = 0; i < data.value; i++){
									arr.push({
										sequence: (i + 1),
										digit: tierTableDatas[i] && tierTableDatas[i].digit ? tierTableDatas[i].digit : '1',
										levelName: tierTableDatas[i] && tierTableDatas[i].levelName ? tierTableDatas[i].levelName : '层级' + chars[i]
									})
								}
								tierTableDatas = arr;
								F.renderTierTable();
								F.renderLevelPropertyPrev();
							})
							form.on('select(propertyCount)', function(data){
								var arr = [];
								for(var i = 0; i < data.value; i++){
									var cobj = {
										propertyTable: attributeTableDatas[i] && attributeTableDatas[i].propertyTable ? attributeTableDatas[i].propertyTable : '属性' + chars[i]
									}
									if(archivesDataType == '1'){
										cobj.propertyColumns = attributeTableDatas[i] && attributeTableDatas[i].propertyColumns ? attributeTableDatas[i].propertyColumns : ''
									}
									arr.push(cobj)
								}
								attributeTableDatas = arr;
								F.renderAttrbuteTable();
							})
							form.on('select(relyType-form)', function(data){
								if(data.value === '1'){
									$(".parentValue-box").removeClass('layui-hide');
									$(".parentValue-input").attr("lay-verify", 'required').attr('disabled', false).removeClass("layui-disabled")
								}else{
									$(".parentValue-box").addClass('layui-hide');
									$(".parentValue-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
								}
							})
							form.on('select(cType-form)', function(data){
								if(archivesDataType !== data.value){
									archivesDataType = data.value;
									F.editArchivesState();
									if(data.value == '0'){
										tierTableDatas = [
											{
												"sequence": 1,
												"digit": "1",
												"levelName": "层级一"
											}
										]
										if(attributeTableDatas.length){
											for(var i = 0; i< attributeTableDatas.length; i++){
												delete attributeTableDatas[i].propertyColumns;
											}
										}
									}else{
										if(attributeTableDatas.length){
											for(var i = 0; i< attributeTableDatas.length; i++){
												attributeTableDatas[i].propertyColumns = '';
											}
										}
										tierTableDatas = [];
									}
									form.val('editArchives-form',{"codeLevel": 1});
									F.renderTierTable();
									F.renderAttrbuteTable();
									F.renderLevelPropertyPrev();
								}
							})
							form.on('submit(editArchivesSub)', function (data) {
								if(createLevelPorpertyState){
									var levelPorpertyObj = {
										"dictTypeId": data.field.dictTypeId,
										"dictTypeName": data.field.dictTypeName,
										"dictTypeCde": data.field.dictTypeCde,
										"propertyCount": data.field.propertyCount,
										"propertyTable": [],
										"cType": data.field.cType,
										"relyType": data.field.relyType,
										"tableName": data.field.tableName,
										"showValue": data.field.showValue,
										"realValue": data.field.realValue,
										"parentValue": data.field.parentValue,
										"propertyColumns": []
									},
									names = [],
									names1 = [],
									nullName = false,
									nullName1 = false;
									if(attributeTableDatas.length){
										attributeTableDatas.forEach(function(v, i){
											if(archivesDataType == '1'){
												if(!v.propertyColumns){
													nullName = true;
													return false;
												}
												levelPorpertyObj.propertyColumns.push(v.propertyColumns);
											}
											if(!v.propertyTable){
												nullName1 = true;
												return false;
											} 
											names.push(v.propertyTable);
											levelPorpertyObj.propertyTable.push(v.propertyTable);
										})
									}
									names1 = unique(names);
									if(nullName){
										myUtil.msg({type: 'warning', message: '属性字段不能为空！'});
										return false;
									}else if(nullName1){
										myUtil.msg({type: 'warning', message: '属性名称不能为空！'});
										return false;
									}else if(names1.length != names.length){
										myUtil.msg({type: 'warning', message: '属性名称不能重复！'});
										return false;
									}else{
										F.getDigitData(levelPorpertyObj);
										createLevelPorpertyState = false;
									}
								}else{
									var url = setter.basePath + (data.field.dictTypeId ? 'sysDictionaryController/update' : 'sysDictionaryController/save'),
									ajaxData = data.field.dictTypeId ? {event: 'update-md', settings: {}} : {event: 'create-md', settings: {}},
									callback = function(data){
										if(data.success){
											myUtil.msg({type: "success",message: data.msg});
											layer.close(index);
											renderTree(id);
										}
										layui.admin.removeLoading('body', true, true);
									}
									ajaxData.settings = {
										"dictTypeId": data.field.dictTypeId || '',
										"dictTypeName": data.field.dictTypeName,
										"dictTypeCde": data.field.dictTypeCde,
										"codeLevel": data.field.codeLevel,
										"levelTable": [],
										"propertyCount": data.field.propertyCount,
										"propertyTable": [],
										"cType": data.field.cType,
										"relyType": data.field.relyType,
										"tableName": data.field.tableName,
										"showValue": data.field.showValue,
										"realValue": data.field.realValue,
										"parentValue": data.field.parentValue,
										"propertyColumns": []
									},
									names = [],
									names1 = [],
									nullName = false;
									
									if(tierTableDatas.length){
										tierTableDatas.forEach(function(v, i){
											if(!v.levelName){
												nullName = true
												return false;
											}
											ajaxData.settings.levelTable.push({
												sequence: v.sequence,
												digit: v.digit,
												levelName: v.levelName
											})
											names.push(v.levelName);
										})
									}else{
										myUtil.msg({type: 'warning', message: '请先生成层级属性！'});
										return false;
									}
									names1 = unique(names);
									if(nullName){
										myUtil.msg({type: 'warning', message: '层级名称不能为空！'});
										return false;
									}else if(names1.length != names.length){
										myUtil.msg({type: 'warning', message: '层级名称不能重复！'});
										return false;
									}
									nullName = false;
									nullName1 = false;
									names = [];
									names1 = [];
									if(attributeTableDatas.length){
										attributeTableDatas.forEach(function(v, i){
											if(archivesDataType == 1){
												if(!v.propertyColumns){
													nullName = true;
													return false;
												} 
												ajaxData.settings.propertyColumns.push(v.propertyColumns);
											}
											if(!v.propertyTable){
												nullName1 = true;
												return false;
											} 
											ajaxData.settings.propertyTable.push(v.propertyTable);
											names.push(v.propertyTable)
										})
									}
									names1 = unique(names);
									if(nullName){
										myUtil.msg({type: 'warning', message: '属性字段不能为空！'});
										return false;
									}else if(nullName1){
										myUtil.msg({type: 'warning', message: '属性名称不能为空！'});
										return false;
									}else if(names1.length != names.length){
										myUtil.msg({type: 'warning', message: '属性名称不能重复！'});
										return false;
									}
									layui.admin.showLoading('body', 2, '.8')
									myUtil.send(url, ajaxData, callback);
								}
								function unique(arr){
									var arr1 = [];       // 新建一个数组来存放arr中的值
									for(var i=0,len=arr.length;i<len;i++){
										if(arr1.indexOf(arr[i]) === -1){
											arr1.push(arr[i]);
										}
									}
									return arr1;
								}
								return false;
							});
						}
				    },
				    yes: function(index, layero){
				        $("#editArchivesSub").trigger("click");
				    }
				});
			},
			getDigitData: function(obj){
				var url = setter.basePath + 'sysDictionaryController/previewDictData',
				callback = function(data){
					if(data.success){
						myUtil.msg({type: "success",message: data.msg});
						tierTableDatas = data.obj.levelTables || [];
						form.val('editArchives-form',{
							"codeLevel": data.obj.levelTables.length || 1
						})
						F.renderTierTable();
						var treeNodes = data.obj.tree && data.obj.tree.treeNodes && data.obj.tree.treeNodes.length ? data.obj.tree.treeNodes : [];
						F.renderLevelPropertyPrev(treeNodes)
					}
					layui.admin.removeLoading('.tierTablebox', true, true);
					layui.admin.removeLoading('.showTree', true, true);
				}
				layui.admin.showLoading('.tierTablebox', 2, '.8')
				layui.admin.showLoading('.showTree', 2, '.8')
				myUtil.send(url, obj, callback);
			},
			renderTierTable: function(){
				var height = $(".tierTablebox").height(),
				cols = [
						{field: 'sequence', align: 'center', title: '层级序列', unresize: true},
						{field: 'digit', align: 'center', title: '位数', unresize: true},
						{field: 'levelName', align: 'center', title: '层级名称', unresize: true},
					];
				if(archivesDataType == '0'){
					cols[1].event = 'editDigit';
					cols[1].templet = function(d){
						return '<div class="layui-form-select">'+d.digit+'<i class="layui-edge"></i></div>'
					}
					cols[2].edit = 'text';
				}
				table.render({
					elem: '#tierTable',
					height: height,
					cols:[cols],
					data: tierTableDatas,
					skin: 'nob',
					even: true,
					size: 'sm',
					limit: tierTableDatas.length,
				})
				table.on('tool(tierTable)', function(obj){
					var evt = window.event || arguments.callee.caller.arguments[0],
					td = $(this);
					evt.preventDefault();
					evt.stopPropagation();
					if(obj.event == 'editDigit'){
						var x = td.offset().left;
						var y = td.offset().top + td.outerHeight();
						if($(".ctxMenu")[0]) $(".ctxMenu").remove();
						cxt.show([{
							name: '1',
							click: function(e, event) {
								if($(e.target).parent().hasClass('layui-this')){
									cxt.remove();
								}else{
									updateDigit('1');
								}
							}
						}, {
							name: '2',
							click: function(e, event) {
								if($(e.target).parent().hasClass('layui-this')){
									cxt.remove();
								}else{
									updateDigit('2');
								}
							}
						}, {
							name: '3',
							click: function(e, event) {
								if($(e.target).parent().hasClass('layui-this')){
									cxt.remove();
								}else{
									updateDigit('3');
								}
							}
						}, {
							name: '4',
							click: function(e, event) {
								if($(e.target).parent().hasClass('layui-this')){
									cxt.remove();
								}else{
									updateDigit('4');
								}
							}
						}], x, y);
						$(".ctxMenu").addClass('layui-form-select').width(140);
						$('.ctxMenu-item[lay-value="'+obj.data.digit+'"]').addClass('layui-this');
						function updateDigit(value){
							obj.update({digit: value});
							obj.data.digit = value;
							form.render();
							F.renderLevelPropertyPrev();
						}
					}
				})
			},
			renderAttrbuteTable: function(){
				var height = $(".attributeTablebox").height(),
				cols = [
						{type: 'numbers', title: '属性序列', width: 100},
						{field: 'propertyTable', align: 'center', title: '属性名称', edit: 'text', unresize: true},
					];
				if(archivesDataType == '1'){
					cols.splice(1, 0, {field: 'propertyColumns', align: 'center', title: '字段'});
					cols[1].event = 'editColumn';
					cols[1].unresize = true;
					cols[1].templet = function(d){
						return '<div class="layui-form-select" title="'+d.propertyColumns+'">'+d.propertyColumns+'<i class="layui-edge"></i></div>'
					}
				}
				table.render({
					elem: '#attributeTable',
					height: height,
					cols:[cols],
					data: attributeTableDatas,
					skin: 'nob',
					even: true,
					size: 'sm',
					limit: attributeTableDatas.length,
				})
				table.on('tool(attributeTable)', function(obj){
					if(obj.event == 'editColumn'){
						var value = $(".tableName-input").val();
						if(value){
							F.selectTableColumn($(this), value, [obj.data.propertyColumns || ''], false, obj);
						}else{
							myUtil.msg({type: 'warning', message: '请先选择库表字段！'})
						}
					}
				})
			},
			renderLevelPropertyPrev: function(nodes){
				var treeNodes = [];
				if(!nodes){
					treeNodes = [
						{name: "1" },
						{name: "2" },
						{name: "3" },
					]
					var digitArr = '000';
					for(var j = 0 ; j < treeNodes.length ; j++){
						for(var i = 0 ; i < tierTableDatas.length ; i++){
							if(i == 0){
								var digit = tierTableDatas[i].digit;
								treeNodes[j].name = (digit == '4' ? '000': digit == '3' ? '00': digit == '2' ? '0': '') + treeNodes[j].name;
							}else if(i == 1){
								if(!treeNodes[j].children){
									treeNodes[j].children = [];
								}
								treeNodes[j].children.push({
									name: treeNodes[j].name + digitArr.slice(0,tierTableDatas[i].digit-1) + "1"},
									{name: treeNodes[j].name + digitArr.slice(0,tierTableDatas[i].digit-1) + "2"},
									{name: treeNodes[j].name + digitArr.slice(0,tierTableDatas[i].digit-1) + "3"})
							}else if(i > 1){
								deepLabel(i,treeNodes[j].children,tierTableDatas[i].digit)
							}
						}
					}
					function deepLabel(i,node,arrDigit){
						if(node){
							if(i == 2){
								for(var i = 0 ; i < node.length ; i++){
									if(!node[i].children){
										node[i].children = []
									}
									node[i].children.push({name: node[i].name + digitArr.slice(0,arrDigit-1) + "1"},
									{name: node[i].name + digitArr.slice(0,arrDigit-1) + "2"},
									{name: node[i].name + digitArr.slice(0,arrDigit-1) + "3"})
								}
							}else{
								for(var j = 0 ; j < node.length ; j++){
									deepLabel(i-1,node[j].children,arrDigit)
								}
							}
						}
					}
				}else{
					treeNodes = nodes;
				}
				var icons = { FOLDER: "iconfont icon-folder-close ", table: "iconfont icon-table1 ", procedure: "iconfont icon-table1 "},
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
								var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
								switchObj.before(spaceStr);
							}
						}
					}
				};
				
				
				var treeObj = jQuery.fn.zTree.init($('#dict-result-tree'), ztreeobj, treeNodes);
				
			},
			selectTableColumn: function(el, tableName, ids, isInput, tableobj){
				var evt = window.event || arguments.callee.caller.arguments[0];
				evt.preventDefault();
				evt.stopPropagation();
				var input = el,
				left = input.offset().left,
				top = input.offset().top + input.outerHeight() + 5,
				url = tableName ? 'shareController/querySysTableColunms' : 'shareController/querySysTables',
				ajaxData = tableName ? {tableName: tableName} : {},
				removeBefore = function () {
					if ($(".ctxMenu")[0]) {}
				},
				callback = function(type, treeId, node){
					event.stopPropagation();
					if(ids && ids.length == 1 && ids[0] == node.name){
						$(".ctxMenu").remove();
						return;
					}
					if(isInput){
						input.val(node.name);
						if(!tableName){
							$(".parentValue-input").val('');
							$(".realValue-input").val('');
							$(".showValue-input").val('');
							if(attributeTableDatas.length){
								attributeTableDatas.forEach(function(v, i){
									v.propertyColumns = '';
								})
								F.renderAttrbuteTable();
							}
						}
						$(".ctxMenu").remove();
					}else{
						var isError = false;
						if(attributeTableDatas.length){
							for(var i = 0; i< attributeTableDatas.length;i++){
								if(attributeTableDatas[i].propertyColumns == node.name){
									isError = true;
									break;
								}
							}
						}
						if(isError){
							myUtil.msg({type: 'warning', message: '属性字段名称重复！'})
						}else{
							tableobj.update({propertyColumns: node.name});
							tableobj.data.propertyColumns = node.name;
							form.render();
							$(".ctxMenu").remove();
						}
					}
				};
				if(isInput){
					input.blur();
				}
				if($('.ctxMenu .dialog-tree-box')[0]){
					$('.ctxMenu .dialog-tree-box').parent().remove();
				}
				F.renderDialogzTree('multiselect-tree', left, top, url, ajaxData, (ids && ids.length && ids[0] ? ids[0]: ''), callback, 'treeNodes', { treeIds: ids || []}, removeBefore, '', true);
			},
			editArchivesState: function(){
				if(archivesDataType == '0'){
					$(".archivesInfo-box").css('height', '');
					$(".tableName-box").addClass('layui-hide');
					$(".cType-datafile-box").addClass('layui-hide');
					form.val('editArchives-form',{"relyType": '0'})
					$(".layui-createLevel-btn").addClass('layui-hide');
					$(".showValue-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
					$(".parentValue-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
					$(".realValue-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
					$(".tableName-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
					$(".codeLevel-input").attr('disabled', false).removeClass("layui-disabled").next().find('.layui-input').removeClass("layui-disabled").attr({"disabled": false});
				}else{
					$(".archivesInfo-box").css('height', 'calc(100% - 188px)');
					$(".tableName-box").removeClass('layui-hide');
					$(".cType-datafile-box").removeClass('layui-hide');
					$(".layui-createLevel-btn").removeClass('layui-hide');
					$(".codeLevel-input").attr('disabled', 'disabled').addClass("layui-disabled").next().find('.layui-input').addClass("layui-disabled").attr({"disabled": 'disabled'});
					$(".showValue-input").attr("lay-verify", 'required').attr('disabled', false).removeClass("layui-disabled");
					$(".realValue-input").attr("lay-verify", 'required').attr('disabled', false).removeClass("layui-disabled");
					$(".tableName-input").attr("lay-verify", 'required').attr('disabled', false).removeClass("layui-disabled");
					if($(".relyType-select option:selected").val() === '1'){
						$(".parentValue-box").removeClass('layui-hide');
						$(".parentValue-input").attr("lay-verify", 'required').attr('disabled', false).removeClass("layui-disabled");
					}else{
						$(".parentValue-box").addClass('layui-hide');
						$(".parentValue-input").attr("lay-verify", '').attr('disabled', 'disabled').addClass("layui-disabled").val('');
					}
				}
				form.render(null, 'editArchives-form');
			}
		};


	/**
	 * 左侧菜单树推拉方法初始化
	 * @method navbarPushPull
	 * @for UserManage
	 */
	function navbarPushPull() {
		$(".layui-navbar-collapse").off("click").on("click", function() {
			$(".wrapper").toggleClass("show-layui-navbar");
		});
	}

	/**
	 * 绑定树操作
	 * @method navbarPushPull
	 * @for UserManage
	 */
	function eventTreeBar() {
		$("#LAY-tree-add").off("click").on("click", function() {
			F.editArchives();
		});
		$("#LAY-tree-edit").off("click").on("click", function() {
			var node = myUtil.getSelectedNode(treeId);
			if (node && node.id) {
				F.editArchives(node.id);
			} else {
				myUtil.msg({
					type: "warning",
					message: "请先选择要编辑的基础档案"
				});
			}
		});
		$("#LAY-tree-remove").off("click").on("click", function() {
			var node = myUtil.getSelectedNode(treeId);
			if (node && node.id && node.id != "0") {
				layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">"' + node.name + '"</span>吗？', {
						icon: 3,
						skin: 'layui-layer-admin',
						title: '删除节点'
					},
					function(index, layero) {
						var url = setter.basePath + "sysDictionaryController/delete",
							ajaxdata = {
								"event": "delete-md",
								settings: {
									ids: [(node.userId || node.id)]
								}
							},
							callback = function(data) {
								if (data.msg != "删除失败") {
									myUtil.msg({type:"success",message:data.msg})
									var next = "";
									if (node.parent.next()[0]) {
										next = node.parent.next();
									} else if (node.parent.prev()) {
										next = node.parent.prev();
									} else {
										next = node.parent.parent().parent();
									}
									setter.eleTreeObj.remove(node.id);
									layer.close(index)
									next.children(".eleTree-node-content").trigger("click");
								} else {
									myUtil.msg({type:"error",message:data.msg})
								}
								layui.admin.removeLoading('body', true, true);
							};
						layui.admin.showLoading('body', 2, '.8');	
						myUtil.send(url, ajaxdata, callback);
					}
				);
			} else {
				myUtil.msg({type:"warning",message:"请选择要删除的节点"})
			}
		});
		$("#LAY-tree-openSearch").off("click").on("click", function() {
			$(".layui-nav-searchbox").removeClass("layui-hide");
		});
		$("#LAY-tree-closeSearch").off("click").on("click", function() {
			$(".layui-nav-searchbox").addClass("layui-hide");
			$("#LAY-tree-search").val("").trigger("change");
		});
		$("#LAY-tree-search").off("change").on("change", function() {
			setter.eleTreeObj.search($(this).val());
		});

	}
	function eventTopbar(){
		$(".dictionary-search").on("click", function(){
			setter.eleTreeObjSec.filterData($(".searchInput").val());
		})
		$(".dictionary-del").on("click", function(){
			if (currentTreeNode && currentTreeNode.id) {
				layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">' + currentTreeNode.name + '</span>吗？', {
						icon: 3,
						skin: 'layui-layer-admin',
						title: '删除节点'
					},
					function(index, layero) {
						var url = setter.basePath + "sysDictionaryDataController/delete",
							ajaxdata = {
								"event": "delete-md",
								settings: {
									ids: [currentTreeNode.id]
								}
							},
							callback = function(data) {
								if (data.success) {
									myUtil.msg({type:"success",message:data.msg})
									currentTreeNode = "";
									currentTableClickIndex -= 1;
									renderSecTree();
									layer.close(index)
								} else {
									myUtil.msg({type:"error",message:data.msg})
								}
								layui.admin.removeLoading('body', true, true);
							};
							layui.admin.showLoading('body', 2, '.8');	
						myUtil.send(url, ajaxdata, callback);
					}
				);
			} else {
				myUtil.msg({type:"warning",message:"请选择要删除的节点"})
			}

		})
		$(".dictionary-add").on("click", function(){
			var node = myUtil.getSelectedNode(treeId);
			if (node && node.id && node.id != "0") {
				newArchivesData();
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先增加基础档案目录!"
				});
			}
		})
		$(".dictionary-edit").on("click", function(){
			if (currentTreeNode && currentTreeNode.id) {
				newArchivesData("edit");
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先选择要编辑的基础档案数据"
				});
			}
		})
		$(".dictionary-up").on("click", function(){
			if (currentTreeNode && currentTreeNode.dictId) {
				mobileNode(currentTreeNode.dictId, false);
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先选择要编辑的基础档案数据"
				});
			}
		})
		$(".dictionary-down").on("click", function(){
			if (currentTreeNode && currentTreeNode.dictId) {
				mobileNode(currentTreeNode.dictId, true);
			}else{
				myUtil.msg({
					type: "warning",
					message: "请先选择要移动的基础档案数据"
				});
			}
		})
		
	}
	function mobileNode(id, next){
		var td = $("#LAY-dictListSec-tree tr.layui-table-click"),
		index = td.attr("data-index"),
		indent = td.attr("data-indent"),
		toTd = next ? td.next() : td.prev(),
		toId = "",
		indexs = index.indexOf("-") > -1 ? index.split("-") : [index];
		if(toTd[0]){
			var lastIndex = "";
			if(next){
				lastIndex = parseInt(indexs[indexs.length-1]) + 1;
				indexs[indexs.length-1] = lastIndex;
				toId = _getToId(indexs);
				if(toId){
					_goTo(toId);
				}else{
					myUtil.msg({
						type: "warning",
						message: "已是同级最下层！"
					});
				}
			}else{
				lastIndex = parseInt(indexs[indexs.length-1]) - 1;
				indexs[indexs.length-1] = lastIndex;
				if(lastIndex >= 0){
					toId = _getToId(indexs);
					if(toId){
						_goTo(toId);
					}else{
						myUtil.msg({
							type: "warning",
							message: "已是同级最上层！"
						});
					}
				}else{
					myUtil.msg({
						type: "warning",
						message: "已是同级最上层！"
					});
				}
			}
			
		}else{
			myUtil.msg({
				type: "warning",
				message: "请先确认移动到的基础档案数据位置正确！"
			});
		}
		function _getToId(indexs){
			try {
				var node = currentTableNodes;
				for(var i = 0; i < indexs.length; i++){
					node = i == 0 ? node[indexs[i]] : node.children[indexs[i]];
				}
				return node && node.dictId || "";
			} catch (error) {
				return ;
			}
		}
		function _goTo(toId){
			var url = setter.basePath + "sysDictionaryDataController/exchangeOrder",
			ajaxData = {
				dictId: id,
				toId : toId
			};
			layui.admin.showLoading('body', 2, '.8')
			myUtil.send(url, ajaxData, function(data){
				if(data.success){
					myUtil.msg({ type: "success", message: data.msg });
					currentTableNodes = [];
					currentTreeNode = "";
					renderSecTree();
				}else{
					layui.admin.removeLoading('body', true, true);
				}
			})
		}
	}
	/**
	 * 左侧树初始化
	 * @method renderTree
	 * @for UserManage
	 */
	function renderTree(tid) {
		var url = setter.basePath + "sysDictionaryController/dictTreeList",
			index = 0,
			callback = function(data) {
				layui.admin.removeLoading('body', true, true);
				if (data) {
					var treeNodes = data.obj.treeNodes;
					eachCheckNode(treeNodes);
					setter.eleTreeObj = eleTree.render({
						elem: "#" + treeId,
						data: treeNodes,
						expandOnClickNode: false,
						highlightCurrent: true,
						renderAfterExpand: false,
						defaultExpandAll: true,
						request: {
							name: "name",
						},
						leafIcon: {
							dict: 'iconfont icon-dic-list',
							dict1: 'iconfont icon-dangan'
						},
						searchNodeMethod: function(value, data) {
							if (!value) return false;
							return data.name.indexOf(value) !== -1;
						}
					});

					eleTree.on('nodeClick(layui-dictList-tree)', function(d) {
						var node = d.data.currentData;
						node.search = "";
						currentParentTreeNode = d.data.currentData;
						currentTreeNode = "";
						currentTreeNodeParentDictCde = "0";
						currentTableClickIndex = "";
						$(".searchInput").val('');
						if(currentParentTreeNode.ctype == '1'){
							$(".layui-btn-cardTop").addClass('layui-hide');
						}else{
							$(".layui-btn-cardTop").removeClass('layui-hide');
						}
						if(currentParentTreeNode){
							uploadData = "";
							renderSecTree()
						}
					});
					layui.admin.removeLoading('body', true, true);
					if(tid){
						$("#" + treeId + ' .eleTree-node[data-id="' + tid + '"]').children(".eleTree-node-content").trigger("click");
					}else{
						if(treeNodes.length){
							$("#" + treeId + ' .eleTree-node[data-id="' + treeNodes[0].id + '"]').children(".eleTree-node-content").trigger("click");
						}
					}
				}
				layui.admin.removeLoading('body', true, true);
				function eachCheckNode(nodes) {
					if (nodes.length) {
						nodes.forEach(function (v, i) {
							if (v.children && v.children.length) {
								eachCheckNode(v.children);
							} 
							if (v.ctype == '0') {
								v.type = 'DICT';
							} else {
								v.type = 'DICT1';
							}
						})
					}
				}
			};
		layui.admin.showLoading('body', 2, '.8')
		myUtil.send(url, {}, callback);
	}

	/**
	 * 渲染右侧树
	 * @method renderSecTree
	 * @param  {String} $data 
	 * @param  下面为arguments详细
	 * @param  {String} dictTypeId
	 */

	function renderSecTree($data) {
		$(".searchInput").val('');
		var url = setter.basePath + "sysDictionaryController/dictTypeDetailed",
			index = 0,
			ajaxData = {
				dictTypeId: currentParentTreeNode.dictTypeId
			},
			cols = [
				{ type: 'numbers', title: '序号' },
				{ field: 'dictName', sort: false, title: '档案名称' },
				{ field: 'dictCde', align: "center", sort: false, title: '档案编码' },
			],
			callback = function(data) {
				if (data.success) {
					if(currentParentTreeNode.propertyName && currentParentTreeNode.propertyName.length){
						for(var i = 0; i < currentParentTreeNode.propertyName.length; i++){
							cols.push({ field: 'ext'+ (i + 1), sort: false, align: "center", title: currentParentTreeNode.propertyName[i] })
						}
					}
					_renderTreeTable(cols, data.obj.treeNodes||[]);
				}
				layui.admin.removeLoading('body', true, true);
			};
			
		function _renderTreeTable(cols, nodes){
			currentTableNodes = nodes;
			$(".treeWrap").removeClass("layui-hide");
			$(".impWrap").addClass("layui-hide");					
			$("#imp").removeClass("impactive");
			setter.eleTreeObjSec = treetable.render({
				treeColIndex: 1,
				elem: '#dictListSecTable',
				cellMinWidth: 100,
				data: nodes,
				limit: nodes.length,
				height: "full-62",
				tree: {
					iconIndex: 1,           // 折叠图标显示在第几列
					isPidData: false,        // 是否是id、pid形式数据
					idName: 'dictId',  // id字段名称
					childName: 'children',     // pid字段名称
					getIcon: function (d) {  // 自定义图标
						if (d.children && d.children.length) {
							return '<i class="ew-tree-icon iconfont" style="color:#FF9C2E;font-size:14px;">&#xe69b;</i>';
						}else{
							return '<i class="ew-tree-icon iconfont" style="color:#FF9C2E;font-size:14px;">&#xe629;</i>';
						}
					}
				},
				cols: [cols],
				done: function(res, curr, count){
					$("#dictListSecTable").next().find(".ew-tree-table-head .ew-tree-table-cell-content").attr("align", "center");
				}
			});
			setter.eleTreeObjSec.expandAll();
			treetable.on('row(dictListSecTable)', function(obj){
				if(obj.tr.hasClass("layui-table-click")){
					obj.tr.removeClass("layui-table-click");
					currentTableClickIndex = "";
					currentTreeNode = "";
					currentTreeNodeParentDictCde = "0";
				}else{
					obj.tr.siblings().removeClass('layui-table-click').end().addClass('layui-table-click');
					currentTableClickIndex = obj.tr.index();
					currentTreeNode = myUtil.cloneObj(obj.data);
					currentTreeNodeParentDictCde =currentTreeNode.dictCde || "0";
				}
			})
			if(currentTableClickIndex && currentTableClickIndex > 0){
				if($("#dictListSecTable").next().find(".ew-tree-table-box tr").eq(currentTableClickIndex)[0]) $("#dictListSecTable").next().find(".ew-tree-table-box tr").eq(currentTableClickIndex)[0].scrollIntoView();
				currentTableClickIndex = "";
			}
		}
		if(currentParentTreeNode.ctype == "0"){
			layui.admin.showLoading('body', 2, '.8')
			myUtil.send(url, ajaxData, callback);
		}else{
			_renderTreeTable(cols, []);
		}
	}
	
	function newArchivesData(methods) {
		if (methods && methods == "edit") {
			newArchivesDataCreate("edit");
		}else{
			var url = setter.basePath + "sysDictionaryDataController/getDictCdeData",
				index = 0,
				ajaxData = {
					event: "query-md",
					settings: {
						pId: currentTreeNode.id || "0",
						dictTypeId: currentParentTreeNode.dictTypeId,
						level: currentTreeNode.level || "1",
						parentDictCde: currentTreeNodeParentDictCde || "0",
					}
				},
				callback = function(data) {
					layui.admin.removeLoading('body', true, true);
					if (data.success) {
						if(currentTreeNode){
							currentTreeNode.dictCde = data.obj;
						}else{
							currentTreeNode = {
								dictCde: data.obj,
								dictName: ""
							}
						}
						newArchivesDataCreate("create");
					}
					layui.admin.removeLoading("body",true,true)
				};
			layui.admin.showLoading('body', 2, '.8')
			myUtil.send(url, ajaxData, callback);
		}
	};

	function newArchivesDataCreate(methods) {
		var temp =  '<form class="layui-form" action="" style="padding: 20px 30px 0 30px;" lay-filter="archivesForm">'+
			'<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:95px"><i style="color: red;margin-right: 5px;">*</i>档案数据编码：</label><div class="layui-input-inline"><input type="text" class="layui-input layui-disabled" disabled name="dictCde" value="" required lay-verify="required" lay-verType="tips" placeholder="" autocomplete="off" class="layui-input"></div></div>'+
		'<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:95px"><i style="color: red;margin-right: 5px;">*</i>档案数据名称：</label><div class="layui-input-inline"><input type="text" name="dictName" required lay-verify="required" lay-verType="tips" placeholder=""autocomplete="off" class="layui-input"></div></div>'+
		'{{attr}}<button class="layui-btn sub layui-hide" lay-submit lay-filter="formDemo">立即提交</button></form>',
		attrTemp = [],
		area = '360px';
		
		for (var a = 0; a < currentParentTreeNode.propertyCount; a++) {
			var attrName = "ext" + (a + 1);
			attrTemp.push(
				'<div class="layui-form-item layui-form-item-sm"><label class="layui-form-label" style="width:95px">' + currentParentTreeNode.propertyName[a] +
				'：</label><div class="layui-input-inline"><input type="text"   name="'+ attrName +
				'" value="'+(currentTreeNode[attrName] || "")+'"  placeholder="" autocomplete="off" class="layui-input"></div></div>'
			);
			if(currentParentTreeNode.propertyCount > 5){
				area = ['360px', '400px']
			}
		};
	
		temp = temp.replace(/{{attr}}/g, attrTemp.join(""));
		layui.admin.open({
			type: 1,
			title: methods == "edit" ? '编辑档案数据' : '新建档案数据',
			content: temp,
			area: area,
			btn: ['确认', '取消', ],
			success: function(layero, index){
				layero.find(".layui-layer-content").css("overflow-y", 'scroll')
				form.val("archivesForm", {
					dictCde: currentTreeNode.dictCde,
					dictName: currentTreeNode.dictName
				})
				form.render(null, "archivesForm");
				form.on('submit(formDemo)', function(data) {
					if (methods == "edit") {
						var url = setter.basePath + "sysDictionaryDataController/update";
						var ajaxData = {
							event: "update-md",
							settings: {
								dictId: currentTreeNode.dictId,
								dictCde: currentTreeNode.dictCde,
								dictName: data.field.dictName,
								dictTypeId: currentParentTreeNode.dictTypeId,
								pId: currentTreeNode.pId
							}
						};
					} else {
						var url = setter.basePath + "sysDictionaryDataController/save";
						var ajaxData = {
							event: "create-md",
							settings: {
								pId: currentTreeNode.id || "0",
								dictTypeId: currentParentTreeNode.dictTypeId,
								level: currentTreeNode.level || "1",
								parentDictCde: currentTreeNodeParentDictCde || "0",
							}
						};
					}
					var fields = data.field;
					var callback = function(data) {
						layui.admin.removeLoading('body', true, true);
						if (data.success) {
							layer.close(index);
							if(methods == "edit"){
								var td = $("#dictListSecTable").next().find("tr.layui-table-click td");
								var updateNode = {};
								td.each(function(i, v){
									var fieldName = $(v).attr('data-field');
									if(fieldName && fieldName != "dictCde" &&  fields[fieldName] != currentTreeNode[fieldName]){
										if(fieldName == "dictName"){
											$(v).find(".ew-tree-table-cell-content .ew-tree-pack > span").text(fields[fieldName] || "");
										}else{
											$(v).find(".ew-tree-table-cell-content").text(fields[fieldName] || "");
										}
										updateNode[fieldName] = fields[fieldName];
										currentTreeNode[fieldName] = fields[fieldName];
									}
								})
								setter.eleTreeObjSec.update(currentTreeNode.dictId, updateNode);
							}else{
								if(currentTableClickIndex || currentTableClickIndex === 0) currentTableClickIndex += 1;
								renderSecTree();
							}
						} 
					}
					for (var a in fields) {
						ajaxData.settings[a] = fields[a];
					}
					layui.admin.showLoading('body', 2, '.8')
					myUtil.send(url, ajaxData, callback);
					return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
				});
			},
			yes: function(index, layero) {
				layero.find(".layui-btn.sub").trigger("click");
			}
		});

	}

	// 导入
	function importData(){
		var node = ""
		$("#imp").off("click").on("click",function(){
			node = myUtil.getSelectedNode(treeId);
			if(node && node.id){
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
				// is(‘.classname’)
				if(!$(".impWrap").is(".layui-hide")){
					$("#imp").addClass("impactive")
				}else{					
					$("#imp").removeClass("impactive");
					$('.eleTree-node[data-id="'+node.id+'"]>.eleTree-node-content').trigger("click");
				}
			}else{
				myUtil.msg({type:"warning",message:"请选择档案目录"})
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
		// secondStepTable()

		// 点击下载Excel
		$(".downExcelfirst").off("click").on("click",function(){
            var subForm = $("#exceliframes").contents().find("#export-form"),
                formId = $("#exceliframes").contents().find("#formId")
                // formData = new FormData(formId);
                if(!subForm[0]){
                    $("#exceliframes").contents().find("body").html('<form id="export-form" action="' + setter.basePath +
						'sysDictionaryDataController/downloadTemplate" enctype="multipart/form-data" method="post"><input id="formId" type="hidden" name="dictTypeId"></form>'
					);
                }
				formId = $("#exceliframes").contents().find("#formId");
				subForm = $("#exceliframes").contents().find("#export-form");
                formId.val(node.id);
				subForm.submit();
		})
		// 点击上传模板
        $(".uploadTemplateFirst").off("click").on("click",function(){
            $("#uploadTemplateFirst").trigger("click")
		})
		
        $("#uploadTemplateFirst").off("change").on("change",function(){
			$(".fristUploadFilename").html($("#uploadTemplateFirst").val())
			$("#dictTypeIds").val(node.id)
            var forms = $("#firstuploadForm")[0],
				formsdata = new FormData(forms)	
			layui.admin.showLoading("body",2,".8")
            $.ajax({
                type: "POST",
                url: setter.basePath + "sysDictionaryDataController/uploadFile",
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
					layui.admin.removeLoading("body",true,true)
                },
                error: function (msg) {
					myUtil.msg({type:"error",message:"文件上传失败"})
					layui.admin.removeLoading("body",true,true)
                }
              });
		});
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
				secondStepTable(uploadData.obj.dictList)
			}else{
				myUtil.msg({type:"warning",message:"请上传模板"})
			}
		})
		$(".secondStepButton").off("click").on("click",function(){
            var url = setter.basePath + "sysDictionaryDataController/complyImportDict",
                ajaxData = {
					"_uuid":uploadData.obj._uuid,
					"dictTypeId":node.id
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
                        var successNum = uploadData.obj.dictList.length
						$(".successNum").html(successNum)
						layui.admin.removeLoading('body', true, true);
                    }
				}
			layui.admin.showLoading('body', 2, '.8');	
            myUtil.send(url,ajaxData,callback)
			
		})
		// 点击完成
		$(".finishButton").off("click").on("click",function(){
			$("#imp").removeClass("impactive");
			node = myUtil.getSelectedNode(treeId);
			if(node && node.id) $('.eleTree-node[data-id="'+node.id+'"]>.eleTree-node-content').trigger("click");
		})
	}

	// 渲染执行导入里面的表格
	function secondStepTable($data) {
		if (!$data) $data = [];
		table.render({
			elem: "#secondStepTable",
			cellMinWidth: 80,
			height: isSm ? "full-380" : "full-395",
			cols: [[
				{ type: "checkbox" },
				// { field: "index", title: "序号", align: "center", type: "numbers", width: 80 },
				{ field: "dictName", title: "档案名称", unresize: true, sort: false,align: "center" ,style:"font-size:12px"},
				{ field: "dictCde", title: "档案编码", templet:"#type", unresize: true, sort: false, align: "center" ,style:"font-size:12px"},
				{ field: "ext1", title: "属性1", unresize: true, sort: false,align: "center",style:"font-size:12px" },
				{ field: "ext2", title: "属性2", unresize: true, sort: false,align: "center",style:"font-size:12px" },
				{ field: "ext3", title: "属性3", unresize: true, sort: false,align: "center",style:"font-size:12px" },
				{ field: "ext4", title: "属性4", unresize: true, sort: false,align: "center",style:"font-size:12px" }
			]],
			data: $data,
			limit: $data.length,
			id:"allList"
		});
	}
	
	! function() {
		
		$(".dictListSec-box").each(function(i, v){
            if(!$(v).hasClass("ps")){
				new pscrollbar(v);
				$(v).addClass("treebox-scroll");
			}
		});
		$(".layui-nav-treebox").each(function(i, v){
            if(!$(v).hasClass("ps")){
                new pscrollbar(v);
                $(v).addClass("treebox-scroll");
            }
        });
		
		layui.sessionData("configTree", null);
		navbarPushPull();
		eventTreeBar();
		eventTopbar();
		renderTree();
		importData()
	}();
	exports("dictList", {})
});
