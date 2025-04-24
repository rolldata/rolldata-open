;
layui.define(['jquery', 'layer', 'mpopups', "notice", "pscrollbar", "lay"], function(exports) {
	var $ = layui.jquery,
		laytpl = layui.laytpl,
		layer = layui.layer,
		element = layui.element,
		pscrollbar = layui.pscrollbar,
		isMobile = (/Mobi/.test(navigator.userAgent)) ? true : false,
		util = {
			open: function(obj) {
				if (!obj.area) {
					obj.area = (obj.type == 2) ? ["360px", "300px"] : "360px"
				}
				if (!obj.skin) {
					obj.skin = "layui-layer-admin"
				}
				if (!obj.offset) {
					//huwei 分辨率低的弹出框位置为10
					if($('body').hasClass("layui-layout-sm-body")){
						obj.offset = "10px"
					}else{
						obj.offset = "100px"
					}
				}
				if (obj.fixed == undefined) {
					obj.fixed = false
				}
				obj.resize = obj.resize != undefined ? obj.resize : false;
				obj.shade = obj.shade != undefined ? obj.shade : 0.1;
				obj.anim = -1;
				obj.isOutAnim = false;
				var fn = obj.end;
				obj.end = function() {
					layer.closeAll("tips");
					fn && fn()
				};
				return layer.open(obj)
			},
			/**
			 * 渲染对话框树ztree模板
			 * @method renderDialogzTree
			 * @for TaskApplyManage
			 * @param id 用于input，tree,tree-search元素id主要参数
			 * @param el 弹框触发元素
			 * @param evt 触发元素event对象
			 * @param url 树数据路径
			 * @param ajaxData 请求参数
			 * @param nodeId 树节点选中id
			 * @param callback 节点触发回调方法
			 * @param key 回调树数据key名称
			 * @param treeData 已有树数据集合包含treeNodes 以及是否勾选框
			 * @param removeBefore 右键弹框删除回调
			 * @param datakeys ztree渲染name属性别名
			 * @param noeach 不做缓存每次都获取后台最新
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
				treeTemp =  '<div class="seltree-box"><div class="seltree '+(treeData.theme || "")+'" style="'+((treeData.treeBoxStyle || ""))+'"><div class="dialog-tree-box">'+
						'<div class="xm-select-tips '+(isSelectCheckbox ? '': 'layui-hide')+'"><div class="xm-cz-group '+(treeData.isMobilePhone ? "xm-cz-group-mobile" : "")+'"><div class="xm-cz close-cxt" data-type="close" title="完成"><i class="iconfont icon-guanbi"></i></div><div class="xm-cz" data-type="all" title="全选"><i class="iconfont icon-select-all"></i><span>全选</span></div><div class="xm-cz" data-type="none" title="清空"><i class="iconfont icon-quxiaoquanxuan"></i><span>清空</span></div><div class="xm-cz" data-type="submit" title="确定"><span>确定</span></div></div></div>'+
						'<div class="search-box layui-form-item-sm search-fillet-box"><input id="LAY-'+id+'-tree-search" class="layui-input" placeholder="请输入内容搜索" autocomplete="off"><i id="LAY-'+id+'-tree-search-btn" class="iconfont icon-tongyongchaxun"></i></div><div class="ztree-box treebox-scroll" style="'+(isSelectCheckbox ? 'top:70px':'top:40px')+'"><ul class="lay-ztree ztree" id="LAY-'+id+'-tree" lay-filter="lay-'+id+'-tree"></ul></div><div class="btns"style="display:none"><button type="button" class="layui-btn layui-btn-xs yes1">确定</button><button type="button" style="margin-right: 15px;" class="layui-btn layui-btn-primary no1">取消</button></div><span class="moveItem_resize"></span></div></div></div>',
				storeTableName = "renderDialogzTree",
				treeStore = layui.sessionData(storeTableName)[id],
				ajaxData = ajaxData || {data: 'false'},
				keyName = datakeys && datakeys.simpleData && datakeys.simpleData.idKey ? datakeys.simpleData.idKey : "id",
				date = new Date().getTime();
				$("body").append(treeTemp);
				layui.lay.position(el, $(".seltree")[0], {
					position: null,
					e: evt,
					clickType: null,
					margin: 0,
					align: null
				});
				el.blur();
				if(treeData.minWidth){
					$(".seltree-box .seltree").css("width", treeData.minWidth +"px");
				}
				if(treeData.isMobilePhone){
					$(".seltree").css("height", '400px');
					$(".seltree-box").css({'background-color': 'rgba(0,0,0, 0.3)'})
					$(".seltree-box .seltree").css({"width": 'auto', top: 'auto', bottom: 0, left: 0, right: 0});
				}
				layui.admin.showLoading('.dialog-tree-box', 2, '1');
				var url = layui.cache.basePath + url,
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
								layui.admin.showLoading('#LAY-' + id + '-tree', 2, '1');
							},
							onAfterFuzzy: function () {
								var ele = document.getElementById('LAY-' + id + '-tree')
								ele && ele.parentElement && (ele.parentElement.scrollTop = 0);
								layui.admin.removeLoading('#LAY-' + id + '-tree', true, true);
							},
							onClick: function(event, treeId, treeNode){
								if(treeData && treeData.checkbox && (treeData.checkbox + "") === 'true'){
									treeObj.checkNode(treeNode, !treeNode.checked, true);
								}else if(callback){
									callback('click', treeId, treeNode)
								}
							}
						}
					};
					if(datakeys){
						ztreeobj.data = datakeys;
					}
					if(treeData && treeData.checkbox && (treeData.checkbox + "") === 'true'){
						if(!treeData.isMobilePhone){
							$('.dialog-tree-box .ztree-box').css('bottom','39px')
							$('.btns').css('display','block')
						}
						ztreeobj.check = {enable: true, chkStyle: 'checkbox', chkboxType: {"Y": "", "N": ""}};
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
					if(treeDatas && treeData.rootNode){
                        treeDatas.unshift(treeData.rootNode)
                    }
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
						}else if(type == "close"){
							$(".seltree-box").remove();
						}else if(type == "submit"){
							if(callback) callback("submit", '', '', treeObj);
							$(".seltree-box").remove();
						}
					})
					$(".seltree-box").off("click").on("click", function(e){
						if(e.target.className == "seltree-box"){
							if(removeBefore) removeBefore(treeObj);
							$(".seltree-box").remove();
						}
					})
					$('.seltree-box .no1').off('click').on('click', function (e) {
						$(".seltree-box").remove();
					})
					
					$('.seltree-box .yes1').off('click').on('click', function (e) {
						if(callback) callback("submit", '', '', treeObj);
						$(".seltree-box").remove();
					})
					$(".seltree-box .seltree .moveItem_resize").bind("mousedown", function(e1){
						var _this = $(this).parents(".seltree");
						var treebox = _this.find(".dialog-tree-box");
						var oldpageX = e1.pageX;
						var oldpageY = e1.pageY;
						var oldWidth = parseInt(_this.css("width"));
						var oldHeight = parseInt(_this.css("height"));
						var minWidth = treeData.minWidth ? 280: 250;
						$(document).bind("mousemove.seltree", function(e2){
							if((e2.pageX - oldpageX + oldWidth) > minWidth && (e2.pageY - oldpageY + oldHeight) > 332){
								_this.css("width", e2.pageX - oldpageX + oldWidth + "px");
								_this.css("height", e2.pageY - oldpageY + oldHeight + "px");
								treebox.css("width", e2.pageX - oldpageX + oldWidth + "px");
								treebox.css("height", e2.pageY - oldpageY + oldHeight + "px");
								if(isQueryRender){
									if(layZtreeWidth2) layZtreeWidth2 = e2.pageX - oldpageX + oldWidth;
								}else{
									if(layZtreeWidth) layZtreeWidth = e2.pageX - oldpageX + oldWidth;
								}
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
					if(nodeId || nodeId === 0){
						treeObj.selectNode(treeObj.getNodeByParam(keyName || "id", nodeId, null));
					}
					layui.admin.removeLoading('.dialog-tree-box', true, true);
				};

				
				if(treeData && treeData.treeNodes){
					eventTree(treeData.treeNodes);
				}else if(treeStore &&  (date - parseInt(treeStore.time)) < 1800000 && !noeach){
					eventTree(treeStore.treeDatas);
				}else{
					util.send(url, ajaxData, function(data){
						if (data.success) {
							var treeNodes = key ? (data.obj[key]  || data.obj): data.obj.treeNodes;
							if(typeof data.obj == "string"){
								try {
									treeNodes = JSON.parse(data.obj);
								} catch (error) {
									treeNodes = [];
								}
							}
							layui.sessionData(storeTableName, {key: id, value: {treeDatas: treeNodes, time: new Date().getTime()}});
							eventTree(treeNodes);
						}
					});
				}
				if(treeData.topLayer && (".seltree-box")[0]){
					$(".seltree-box").css('z-index', "99891000");
				}
				
				function eachCheckNode(nodes, ids, icons, last){
					if(nodes.length){
						nodes.forEach(function(v, i){
							if(ids && ids.length && (ids.indexOf(v[keyName || "id"]) > -1 || ids.indexOf(v[keyName || "id"] + "") > -1)){
								v.checked = true;
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
			showLoading: function(name, type, opacity) {
				var arr = ['<div class="ball-loader"><span></span><span></span><span></span><span></span></div>', '<div class="rubik-loader"></div>', '<div class="progress-loader"></div>'];
				if (!name) {
					name = "body"
				}
				if (type == undefined) {
					type = 1
				}
				$(name).addClass("page-no-scroll");
				var doms = $(name).children(".page-loading");
				if (doms.length <= 0) {
					$(name).append('<div class="page-loading">' + arr[type - 1] + "</div>");
					doms = $(name).children(".page-loading")
				}
				opacity && doms.css("background-color", "rgba(255,255,255," + opacity + ")");
				doms.show()
			},
			removeLoading: function(name, showHide, allRemove) {
				if (!name) {
					name = "body"
				}
				if (showHide == undefined) {
					showHide = true
				}
				var dom = $(name).children(".page-loading");
				if (allRemove) {
					dom.remove()
				} else {
					showHide ? dom.fadeOut() : dom.hide()
				}
				$(name).removeClass("page-no-scroll")
			},
			/**
			 * 深度复刻对象，新对象不是属性修改不影响被复刻对象
			 * @method cloneObj
			 * @for Util
			 * @param  {Object} cobj 需要复刻对象
			 * @return {Object}      返回新的复刻对象
			 */
			cloneObj: function(target, noJson) { //克隆对象
				if(noJson){
					var copyed_objs = [];//此数组解决了循环引用和相同引用的问题，它存放已经递归到的目标对象
					function _deepCopy(target){ 
						if((typeof target !== 'object')||!target){return target;}
						for(var i= 0 ;i<copyed_objs.length;i++){
							if(copyed_objs[i].target === target){
								return copyed_objs[i].copyTarget;
							}
						}
						var obj = {};
						if(Array.isArray(target)){
							obj = [];//处理target是数组的情况 
						}
						copyed_objs.push({target:target,copyTarget:obj}) 
						Object.keys(target).forEach(function(key){ 
							if(obj[key]){ return;} 
							obj[key] = _deepCopy(target[key]);
						}); 
						return obj;
					} 
					return _deepCopy(target);
				}else{
					var str, newobj = target.constructor === Array ? [] : {};
					if (typeof target !== 'object') {
						return;
					} else if (window.JSON) {
						str = JSON.stringify(target), //序列化对象
							newobj = JSON.parse(str); //还原
					} else {
						for (var i in target) {
							newobj[i] = typeof target[i] === 'object' ? cloneObj(target[i]) : target[i];
						}
					}
					return newobj;
				}
				
			},
			/**
			 * 判断数学公式是否正确
			 * @method checkCalcExpressionValid
			 * @for Util
			 * @param  {Object} cobj 需要复刻对象
			 * @return {Object}      返回新的复刻对象
			 */
			checkCalcExpressionValid: function checkCalcExpressionValid(string){
				// 剔除空白符
				string = string.replace(/\s/g, '');
		
				// 错误情况，空字符串
				if("" === string){
					return false;
				}
		
				// 错误情况，运算符连续
				if( /[\+\-\*\/]{2,}/.test(string) ){
					return false;
				}
		
				// 空括号
				if(/\(\)/.test(string)){
					return false;
				}
		
				//空中括号
				if (/\[\]/.test(string)){
					return false;
				}
		
				// 错误情况，括号不配对
				var stack = [];
				for(var i = 0, item; i < string.length; i++){
					item = string.charAt(i);
					if('(' === item){
						stack.push('(');
					}else if(')' === item){
						if(stack.length > 0){
							stack.pop();
						}else{
							return false;
						}
					}
				}
				if(0 !== stack.length){
					return false;
				}
		
				// 错误情况，中括号不配对
				var stack2 = [];
				for(var i = 0, item; i < string.length; i++){
					item = string.charAt(i);
					if('[' === item){
						stack2.push('[');
					}else if(']' === item){
						if(stack2.length > 0){
							stack2.pop();
						}else{
							return false;
						}
					}
				}
				if(0 !== stack2.length){
					return false;
				}
		
				// 错误情况，(后面是运算符
				if(/\([\+\-\*\/]/.test(string)){
					return false;
				}
		
				// 错误情况，)前面是运算符
				if(/[\+\-\*\/]\)/.test(string)){
					return false;
				}
		
				// 错误情况，[后面是运算符
				if(/\[[\+\-\*\/]/.test(string)){
					return false;
				}
		
				// 错误情况，]前面是运算符
				if(/[\+\-\*\/]\]/.test(string)){
					return false;
				}
		
				//错误情况，运算符号不能在首末位
				if(/^[\+\-\*\/.]|[\+\-\*\/.]$/.test(string)){
					return false;
				}
				//错误情况，.前面不是数字
				if(/[^0-9]\./.test(string)){
					return false;
				}
				//错误情况，.后面不是数字
				if(/\.[^0-9]/.test(string)){
					return false;
				}
		
				//错误情况，中括号结尾到下一个开始之间没有运算符
				if(/\]\[/.test(string)){
					return false;
				}
		
				return true;
		
			},
			isBase64: function isBase64(str){
				if(str === "" || str.trim() === ""){
					return false;
				}
				try {
					return btoa(atob(str)) == str;
				} catch (error) {
					return false;
				}
			},
			/**
			 * 对比两个对象中的内容(除function)是否相同，非内存存地址<br/>
			 * 兼容性IE9+
			 * @method isSame
			 * @for Util
			 * @param {Object | JSONString} x 对比的对象1。
			 * @param {Object | JSONString} y 对比的对象2。
			 * @return {Boolean} 返回true，则为其对比相等，返回false，则为其对比不等。
			 */
			isSame: function(x, y) {
				var i, l, leftChain, rightChain;

				function compare2Objects(x, y) {
					var p;

					// remember that NaN === NaN returns false
					// and isNaN(undefined) returns true
					if (isNaN(x) && isNaN(y) && typeof x === 'number' && typeof y === 'number') {
						return true;
					}

					// Compare primitives and functions.     
					// Check if both arguments link to the same object.
					// Especially useful on the step where we compare prototypes
					if (x === y) {
						return true;
					}

					// Works in case when functions are created in constructor.
					// Comparing dates is a util scenario. Another built-ins?
					// We can even handle functions passed across iframes
					if ((typeof x === 'function' && typeof y === 'function') ||
						(x instanceof Date && y instanceof Date) ||
						(x instanceof RegExp && y instanceof RegExp) ||
						(x instanceof String && y instanceof String) ||
						(x instanceof Number && y instanceof Number)) {
						return x.toString() === y.toString();
					}

					// At last checking prototypes as good as we can
					if (!(x instanceof Object && y instanceof Object)) {
						return false;
					}

					if (x.isPrototypeOf(y) || y.isPrototypeOf(x)) {
						return false;
					}

					if (x.constructor !== y.constructor) {
						return false;
					}

					if (x.prototype !== y.prototype) {
						return false;
					}

					// Check for infinitive linking loops
					if (leftChain.indexOf(x) > -1 || rightChain.indexOf(y) > -1) {
						return false;
					}

					// Quick checking of one object being a subset of another.
					// todo: cache the structure of arguments[0] for performance
					for (p in y) {
						if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
							return false;
						} else if (typeof y[p] !== typeof x[p]) {
							return false;
						}
					}

					for (p in x) {
						if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
							return false;
						} else if (typeof y[p] !== typeof x[p]) {
							return false;
						}

						switch (typeof(x[p])) {
							case 'object':
							case 'function':

								leftChain.push(x);
								rightChain.push(y);

								if (!compare2Objects(x[p], y[p])) {
									return false;
								}

								leftChain.pop();
								rightChain.pop();
								break;

							default:
								if (x[p] !== y[p]) {
									return false;
								}
								break;
						}
					}

					return true;
				}

				if (arguments.length < 1) {
					return true; //Die silently? Don't know how to handle such case, please help...
					// throw "Need two or more arguments to compare";
				}

				for (i = 1, l = arguments.length; i < l; i++) {

					leftChain = []; //Todo: this can be cached
					rightChain = [];

					if (!compare2Objects(arguments[0], arguments[i])) {
						return false;
					}
				}

				return true;
			},
			ajax: function($data) {
				if (typeof $data !== 'object' || !$data.url || !$data.success) return false;
				var tmp,
					def = {
						type: 'POST',
						contentType: 'application/x-www-form-urlencoded; charset=utf-8',
						dataType: 'json',
						data: '',
						async: true,
						error: function(jqXHR, textStatus, errorThrown) {
							if (jqXHR.responseText.indexOf("loginController/login")) {
								top.location.href = layui.cache.basePath + "indexController/index";
							} else {
								console.log(textStatus + ' ' + errorThrown, 'ajax-Error');
								if (jqXHR && jqXHR.responseText) console.log(eval("(" + jqXHR.responseText + ")").exception.message);
							}
						}
					};
				$data = layui.jquery.extend({}, def, $data);
				tmp = $data.success;
				$data.success = function(data) {
					if (!!data && typeof data !== 'object')
						data = layui.jquery.parseJSON(data);
					tmp(data);
				};
				return layui.jquery.ajax($data);
			},
			send: function(url, obj, callback, error, attrs) {
				if (!url) return false;
				var i, data = {};
				if (attrs) {
					for (i in attrs) {
						data[i] = attrs[i];
					}
				}
				data.url = url;
				obj ? data["data"] = {
					data: typeof obj == "string" ? JSON.parse(obj) : JSON.stringify(obj)
				} : data["data"] = {
					data: ""
				};
				if (callback) {
					data.success = function(data) {
						if (data && data.success) {
							callback(data);
						} else {
							util.msg({
								type: "error",
								message: data.msg
							});
							callback(data && data.obj && (data.obj == 'resave' || data.obj.checkLogs) ? data : false);
						}
					}
				}
				if (error) data.error = error;
				util.ajax(data);
			},
			getSelectedNode: function(treeid) {
				var active = layui.jquery("#" + treeid + " .eleTree-node-content.eleTree-node-content-active"),
					parent = active.parent(),
					id = parent.attr("data-id"),
					name = active.children(".eleTree-node-content-label").text();
				return {
					id: id,
					name: name,
					node: active,
					parent: parent
				};
			},
			msg: function(obj) {
				var type = obj.type || "info";
				delete obj.type;
				if (!obj.position) {
					obj.position = 'topCenter';
				}
				if (obj.progressBar == undefined) {
					obj.progressBar = false;
				}
				if(isMobile && layui.mpopups){
					layui.mpopups.jqtoast(obj.message);
				}else{
					layui.notice.destroy();
					obj.timeout = obj.timeout || 3000;
					if(type == 'error'){
						layui.notice['warning'](obj);
					}else{
						layui.notice[type](obj);
					}
				}
			},
			/**
			 * 数组元素交换位置
			 * @param {array} arr 数组
			 * @param {number} index1 添加项目的位置
			 * @param {number} index2 删除项目的位置
			 * index1和index2分别是两个数组的索引值，即是两个要交换元素位置的索引值，如1，5就是数组中下标为1和5的两个元素交换位置
			 */
			swapArray: function (arr, index1, index2) {
				arr[index1] = arr.splice(index2, 1, arr[index1])[0];
				return arr;
			},
			//上移 将当前数组index索引与后面一个元素互换位置，向数组后面移动一位
			zIndexUp: function (arr, index, length) {
				if (index + 1 != length) {
					util.swapArray(arr, index, index + 1);
				} else {
					console.log('已经处于置顶，无法上移');
				}
			},
			//下移 将当前数组index索引与前面一个元素互换位置，向数组前面移动一位
			zIndexDown: function (arr, index, length) {
				if (index != 0) {
					util.swapArray(arr, index, index - 1);
				} else {
					console.log('已经处于置底，无法下移');
				}
			},
			//置顶，即将当前元素移到数组的最后一位
			zIndexTop: function (arr, index, length) {
				if (index + 1 != length) {
					//首先判断当前元素需要上移几个位置,置底移动到数组的第一位
					var moveNum = length - 1 - index;
					//循环出需要一个一个上移的次数
					for (var i = 0; i < moveNum; i++) {
						util.swapArray(arr, index, index + 1);
						index++;
					}
				} else {
					console.log('已经处于置顶');
				}
			},
			//置底，即将当前元素移到数组的第一位
			zIndexBottom: function (arr, index, length) {
				if (index != 0) {
					//首先判断当前元素需要上移几个位置,置底移动到数组的第一位
					var moveNum = index - 0;
					//循环出需要一个一个上移的次数
					for (var i = 0; i < moveNum; i++) {
						util.swapArray(arr, index, index - 1);
						index--;
					}
				} else {
					console.log('已经处于置底');
				}
			},
			/**
			 * 将对象转化为JSON字符<br/>
			 * 将已转化的JSON字符串解析成对象<br/>
			 * 浏览器兼容IE9+
			 * @method json
			 * @for Util
			 * @param {JSONString | Object} obj 需要转化对象或者解析字符串。
			 * @return {JSONString | Object} 返回转化后的字符串或解析后的最新。
			 */
			json: function(obj, type) {
				if (typeof obj === 'undefined' || JSON.stringify(obj) === '{}') return false;
				if (typeof obj === 'object') {
					return JSON.stringify(obj);
				} else if (typeof obj === 'string') {
					return JSON.parse(obj);
				}
			},
			/**
			 * 去重
			 * @method unique
			 * @for UserManage
			 */
			unique: function(arr) {
				for (var i = 0; i < arr.length; i++) {
					for (var j = i + 1; j < arr.length; j++) {
						if (arr[i] == arr[j]) {
							arr.splice(j, 1);
							j--;
						}
					}
				}
				return arr;
			},
			//判断是否公式表达式
			isFormulaExpression: function(expression) {
				return typeof expression === 'string' && expression.length >= 2 && expression.charAt(0) === '=' && expression.indexOf(
					"!") === -1;
			},
			//判断是否多sheet公式表达式
			isSheetsFormulaExpression: function(expression) {
				return typeof expression === 'string' && expression.length >= 2 && expression.charAt(0) === '=' && expression.indexOf(
					"!") > -1;
			},
			/**
			 * 定时调用后台，保持长连接
			 * @method TimingCall
			 * @for Util
			 */
			TimingCall: function (){
				var url = layui.setter.basePath + 'nullController/run';
				util.send(url, {}, function() {});
			},
			/**
			 * 添加文本设置光标位置
			 * @method moveCursor
			 * @for Util
			 * @param  {DOM} $el 输入域dom对象
			 * @return  {Ojbect} 输入域光标位置
			 */
			insertText:function (obj, str) {
				if (document.selection) {
					obj.focus();
					var sel = document.selection.createRange();
					// 设置或获取范围内包含的文本
					sel.text = str;
					return sel.text.legnth;
				} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
			
					// 这是一种复杂的方法，可一直接用上面讲到的方法代替
			
					// console.log(window.getSelection())
					// 获取选区的其实位置
					var startPos = obj.selectionStart,
						// 获取选区的结束位置
						endPos = obj.selectionEnd,
						// 设置光标位置
						cursorPos = startPos,
						// 传入obj的内容
						tmpStr = obj.value;
			
					// 插入内容
					obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
					// 光标现在位置
					cursorPos += str.length;
			
					// 重新设置选中区域
					obj.selectionStart = obj.selectionEnd = cursorPos;
					return cursorPos;
				} else {
					obj.value += str;
					return obj.value.length;
				}
			},
			/**
			 * 添加文本设置光标位置
			 * @method moveCursor
			 * @for Util
			 * @param  {DOM} obj 输入域dom对象
			 * @param  {Object} position 光标位置
			 */
			moveEnd: function (obj, pos) {
				obj.focus();
				// 获取整个obj对象的内容（value值）
				if (document.selection) {
					// IE
					var sel = obj.createTextRange();
					// 更改范围的开始位置
					sel.moveStart('character', pos.start);
					// 更改范围的结束位置，开始位置和结束位置一样即为重合
					sel.moveEnd('character', pos.end);
					// 将插入点移动到当前范围的开始或结尾
					sel.collapse();
					// 将当前选择区置为当前对象
					// sel.select();
			
				} else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
					// 其他浏览器
					// 文本选中
					// obj.setSelectionRange(len-4, len);
					// obj.setRangeText();
					// 设置光标位置
					obj.selectionStart = pos.start
					obj.selectionEnd = pos.end;
				}
			},
			newGuid: function newGuid() {
				var S4 = function () {
					return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
				};
				return (S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
			},
			/**
			 * 自动补充对象键，如果下级没有该键值，设置默认
			 * @method fillUpObjectAttr
			 * @for Util
			 * @param  {Object} obj 需要填充属性对象
			 * @param  {Array} 填充属性键数组
			 * @param  {Number} 键下标，对象级别
			 */
			fillUpObjectAttr: function (obj, arr, level) {
				var key = '';
				if (!obj || level == arr.length) return;
				key = arr[level];
				if(level < arr.length -1){
					if(!obj[key]) obj[key] = {};
				}else{
					if(obj[key] === undefined) obj[key] = '';
				}
				util.fillUpObjectAttr(obj[key], arr, ++level);
			},
			/**
			 * remove $ from formula
			 * @param {String} formula
			 * @returns {String|void}
			 */
			clearFormula: function(formula) {
				return formula.replace(/\$/g, '');
			},

			/**
			 * convert string char to number e.g A => 0, Z => 25, AA => 27
			 * @param {String} chr
			 * @returns {Number}
			 */
			toNum: function(chr) {
				chr = util.clearFormula(chr);
				var base = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
					i, j, result = 0;

				for (i = 0, j = chr.length - 1; i < chr.length; i += 1, j -= 1) {
					result += Math.pow(base.length, j) * (base.indexOf(chr[i]) + 1);
				}

				if (result) {
					--result;
				}

				return result;
			},

			/**
			 * convert number to string char, e.g 0 => A, 25 => Z, 26 => AA
			 * @param {Number} num
			 * @returns {String}
			 */
			toChar: function(num) {
				var s = '';

				while (num >= 0) {
					s = String.fromCharCode(num % 26 + 97) + s;
					num = Math.floor(num / 26) - 1;
				}

				return s.toUpperCase();
			},

			/**
			 * get cell coordinates
			 * @param {String} cell A1
			 * @returns {{row: Number, col: number}}
			 */
			cellCoords: function(cell) {
				try {
					if(cell && isNaN(cell)){
						var num = cell.match(/\d+$/),
							alpha = cell.replace(num, '');
		
						return {
							row: parseInt(num[0], 10) - 1,
							col: util.toNum(alpha)
						};
					}else{
						return false;
					}
				} catch (error) {
					return false;
				}
			},

			/**
			 * translate cell coordinates to merged form {row:0, col:0} -> A1
			 * @param coords
			 * @returns {string}
			 */
			translateCellCoords: function(coords) {
				try {
					return util.toChar(coords.col) + '' + parseInt(coords.row + 1, 10);
				} catch (error) {
					return false;
				}
			},
			getfileSuffixName: function(fileSuffix) {
				var suffix = fileSuffix ? fileSuffix.toLocaleUpperCase() : "";
				switch (suffix) {
					case "JS":
					case "CSS":
					case "XML":
					case "CONF":
					case "PROPERTIES":
					case "TXT":
						return "TXT";
						break;
					case "RTF":
						return "RTF";
						break;
					case "DOC":
					case "DOCX":
					case "DOT":
					case "WPS":
						return "DOC";
						break;
					case "XLS":
					case "ET":
						return "XLS";
						break;
					case "PPT":
					case "PPTX":
					case "DPS":
						return "PPT";
						break;
					case "JPG":
					case "JPEG":
					case "GIF":
					case "PNG":
					case "BMP":
						return "JPG";
						break;
					case "ZIP":
					case "GZIP":
					case "RAR":
						return "ZIP";
						break;
					default:
						return "FILE";
						break;
				}
			},
			/**
			 * 避免小数计算精度过长计算法
			 * @param {Number} num1 值
			 * @param {Number} num2 相加减 值
			 * @param {Boolean} type 0 + 1 - 2 * 3 /
			 * @returns {Number}
			 */
			precisionCalculation: function(num1, num2, type){
				var r1 = '',
					r2 = '',
					m = 0,
					m1 = 0;
				try {
					r1 = num1.toString().split(".")[1].length;
				} catch (e) {
					r1 = 0;
				}
				try {
					r2 = num2.toString().split(".")[1].length;
				} catch (e) {
					r2 = 0;
				}
				switch (type) {
					case "0":
						m = Math.pow(10, Math.max(r1,r2));
						m1 = (num1 * m + num2 * m)/m;
						break;
					case "1":
						m = Math.pow(10, Math.max(r1,r2));
						m1 = (num1 * m - num2 * m)/m;
						break;
					case "2":
						m1 = Number(num1.toString().replace('.', "")) * Number(num2.toString().replace('.', ""))/ Math.pow(10, m);
						break;
					case "3":
						m1 =( Number(num1.toString().replace('.', "")) / Number(num2.toString().replace('.', ""))) * Math.pow(10, r2 - r1);
						break;
				}
				return m1;
			},
			/**
			 * 获取指定日期前后 日期
			 * @param {Number} date 指定日期，如果没有则以当前日期为准
			 * @param {Number} day 相加减 值
			 * @param {Boolean} isShort 是否减日期
			 * @param {String} format 日期格式
			 * @returns {Number}
			 */
			doHandleDate:function (date, day, isShort, format) {
                var myDate = new Date();
                if(date){
					if(date.indexOf("年") > -1){
						date = date.replace(/年/g,"-").replace(/月/g,"-").replace(/日/g,"").replace(/时/g,":").replace(/分/g,":").replace(/秒/g,"");
					}
                    myDate = new Date(date);
                }
				if(isShort){
					myDate = myDate - (day * 24 * 60 * 60 * 1000);
				}else{
					myDate = myDate + (day * 24 * 60 * 60 * 1000);
				}
                myDate = new Date(myDate);
				var format = format || "yyyy-MM-dd";
                var tYear = myDate.getFullYear();
                var tMonth = myDate.getMonth() + 1;
                var tDate = myDate.getDate();
                var tHours = myDate.getHours();
                var tMinutes = myDate.getMinutes();
                var tSeconds = myDate.getSeconds();
				var dateValue = '';
                
                if (tMonth.toString().length == 1) {
                    tMonth = "0" + tMonth;
                }
                if (tDate.toString().length == 1) {
                    tDate = "0" + tDate;
                }
				if(format){
					dateValue = format.replace('yyyy', tYear).replace('MM', tMonth).replace('dd', tDate).replace('HH', tHours).replace('mm', tMinutes).replace('ss', tSeconds);
				}else{
					dateValue = tYear + '-' + tMonth + '-' + tDate;
				}
				
                return dateValue;
            },
			getNowFormatDate: function getNowFormatDate(sdate,num,format) {
				var patt1 = /^d{4}-([0-1]?[0-9])-([0-3]?[0-9])$/; //判断输入的日期是否符合格式正则表达式
				if(!(sdate && typeof(sdate) == "string")){
					sdate = new Date(); //不满足日期的则使用当前年月日
				}else if(sdate.indexOf("年") > -1){
					sdate = sdate.replace(/年/g,"-").replace(/月/g,"-").replace(/日/g,"").replace(/时/g,":").replace(/分/g,":").replace(/秒/g,"");
				}
				num = isNaN(parseInt(num)) ? 0 : parseInt(num);//若没有输入间隔，则使用当前日
				var gdate = new Date(sdate).getTime();//获取指定年月日
				gdate = gdate + 1000 * 60 * 60 * 24 * num; //加减相差毫秒数
				var speDate = new Date(gdate);//获取指定好毫秒数时间
				var preYear = speDate.getFullYear();
				var preMonth = speDate.getMonth() + 1;
				var preDay = speDate.getDate();
				preMonth = (preMonth < 10) ? ("0" + preMonth) :preMonth;
				preDay = (preDay < 10) ? ("0" + preDay) :preDay;
				var preDate = format ? format.replace('yyyy', preYear).replace('MM', preMonth).replace('dd', preDay) : preYear + '-' + preMonth + '-' + preDay;
				return preDate;
			},
			getPreMonth: function getPreMonth(date, num, format) {
				if(date.indexOf("年") > -1){
					date = date.replace(/年/g,"-").replace(/月/g,"-").replace(/日/g,"").replace(/时/g,":").replace(/分/g,":").replace(/秒/g,"");
				}
				var arr = date.split('-');
				var year = arr[0]; //获取当前日期的年份
				var month = arr[1]; //获取当前日期的月份
				var day = arr[2]; //获取当前日期的日
				var days = new Date(year, month, 0);
				days = days.getDate(); //获取当前日期中月的天数
				var year2 = year;
				var month2 = parseInt(month) - num;
				if (month2 == 0) {
					year2 = parseInt(year2) - 1;
					month2 = 12;
				}
				var day2 = day;
				var days2 = new Date(year2, month2, 0);
				days2 = days2.getDate();
				if (day2 > days2) {
					day2 = days2;
				}
				if (month2 < 10) {
					month2 = '0' + month2;
				}
				var t2 = format ? format.replace('yyyy', year2).replace('MM', month2).replace('dd', day2) : year2 + '-' + month2 + '-' + day2;
				return t2;
			},
			getNextMonth: function getNextMonth(date, num, format) {
				if(date.indexOf("年") > -1){
					date = date.replace(/年/g,"-").replace(/月/g,"-").replace(/日/g,"").replace(/时/g,":").replace(/分/g,":").replace(/秒/g,"");
				}
				var arr = date.split('-');
				var year = arr[0]; //获取当前日期的年份
				var month = arr[1]; //获取当前日期的月份
				var day = arr[2]; //获取当前日期的日
				var days = new Date(year, month, 0);
				days = days.getDate(); //获取当前日期中的月的天数
				var year2 = year;
				var month2 = parseInt(month) + num;
				if (month2 == 13) {
					year2 = parseInt(year2) + 1;
					month2 = 1;
				}
				var day2 = day;
				var days2 = new Date(year2, month2, 0);
				days2 = days2.getDate();
				if (day2 > days2) {
					day2 = days2;
				}
				if (month2 < 10) {
					month2 = '0' + month2;
				}
				
				var t2 = format ? format.replace('yyyy', year2).replace('MM', month2).replace('dd', day2) : year2 + '-' + month2 + '-' + day2;
				return t2;
			},
			numToChinese: function(num){
				// 定义个位数的中文大写数字
				var chineseNums = ["零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"];

				// 定义金额单位
				var chineseUnits = ["", "拾", "佰", "仟", "万", "拾万", "佰万", "仟万", "亿", "拾亿", "佰亿", "仟亿"];

				var result = "";
				var numStr = num.toString();
				var len = numStr.length;
				var zeroFlag = false; // 用于处理连续的零

				for (var i = 0; i < len; i++) {
					var digit = numStr[i]; // 当前位数字
					var unit = chineseUnits[len - i - 1]; // 当前位对应的单位

					if (digit === "0") {
						zeroFlag = true;
						continue; // 零不读
					}

					if (zeroFlag) {
						result += "零"; // 处理连续的零
						zeroFlag = false;
					}

					result += chineseNums[digit] + unit;
				}

				return result;
			},
			getPathParams: function(location) {
				var url = decodeURI(location.search),
				pathnames = url.slice(1, url.length).split("&"),
					pathobj = {};
				for (var i = 0; i < pathnames.length; i++) {
					var params = pathnames[i].split("=");
					pathobj[(params[0] ? decodeURIComponent(decodeURIComponent(params[0])) : params[0])] = params[1] ? decodeURIComponent(decodeURIComponent(params[1])) : params[1];
				}
				return pathobj;
			},
			/**
			 * 将颜色值HEX格式转换为rgb的格式
			 * @param {hex} hex 需要转换的rgb字符串
			 * @return {string}  ;
			 */
			hexToRgb: function hexToRgb(hex) {
				let str = hex.replace("#", "");
				if (str.length % 3) {
					return "hex格式不正确！";
				}
				//获取截取的字符长度
				let count = str.length / 3;
				//根据字符串的长度判断是否需要 进行幂次方
				let power = 6 / str.length;
				let r = parseInt("0x" + str.substring(0 * count, 1 * count)) ** power;
				let g = parseInt("0x" + str.substring(1 * count, 2 * count)) ** power;
				let b = parseInt("0x" + str.substring(2 * count)) ** power;
		
				return `rgb(${r}, ${g}, ${b})`;
			}
		};
	exports("wdUtil", util)
});
