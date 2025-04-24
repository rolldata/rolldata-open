/** EasyWeb iframe v3.1.1 data:2019-03-24 License By http://easyweb.vip */

layui.define(["layer", 'setter', 'element', 'wdUtil', 'eleTree', 'form', 'notice', 'contextMenu'], function(f) {
	
	var h = layui.jquery
	,k = layui.layer
	,a = ".layui-layout-admin>.layui-body"
	,l = a + ">.layui-tab"
	,e = ".layui-layout-admin>.layui-side>.layui-side-scroll"
	,j = ".layui-layout-admin>.layui-header"
	,b = "admin-pagetabs"
	,d = "admin-side-nav"
	,c = "theme-admin"
	,element = layui.element
	,form = layui.form
	,myUtil = layui.wdUtil
	,setter = layui.setter
	,eleTree = layui.eleTree
	,sidemenu = "LAY-system-side-menu"
	,sidetreeid = "LAY-system-side-tree"
	,m = {
		defaultTheme: setter.defaultTheme,
		tableName: setter.tableName,
		flexible: function(n) {
			var o = h(".layui-layout-admin").hasClass("admin-nav-mini");
			if (o == !n) {
				return
			}
			if (n) {
				h(".layui-layout-admin").removeClass("admin-nav-mini")
			} else {
				h(".layui-layout-admin").addClass("admin-nav-mini")
			}
			m.removeNavHover()
		},
		activeNav: function(n) {
			if (!n) {
				n = window.location.pathname;
				n = n.substring(n.indexOf("/"))
			}
			if (n && n != "") {
				if(n.indexOf('/homePageController/homePage') > -1){
					if(h("#homeType").val() != '1' && h("#homeType").val() == '4'){
						h(".layui-more-side").remove();
						h(".layui-nav-item-navitem").remove();
						h('.admin-tabs-nav-control').addClass('layui-hide');
					}
					m.flexible(false);
				}else{
					var navname = '';
					if(n.indexOf("nodeid")>=0){
						var params = n.split("&"),
						navname = '',
						node = '';
						params.forEach(function(v, i){
							var param = v.split("=");
							if(param[0] == 'navname'){
								navname = param[1];
							}
							if(param[0] == 'nodeid'){
								node = param[1];
							}
						});
						
					}else{
						var params = n.split("?")[1],
						navname = '',
						node = '';
						params = params && params.split('&') || [];
						params.forEach(function(v, i){
							var param = v.split("=");
							if(param[0] == 'navname'){
								navname = param[1];
							}
						});
						
					}
					if(navname && navname == h('.layui-nav-item-navitem.layui-this a').attr('data-name')){
						if(h(e+'>.layui-nav').hasClass('layui-hide')) return;
						h(e + ">.layui-nav .layui-nav-item .layui-nav-child dd").removeClass("layui-this");
						h(e + ">.layui-nav .layui-nav-item").removeClass("layui-this");
						var r = h(e + '>.layui-nav a[lay-href="' + n + '"]');
						if (r && r.length > 0) {
							var parentEl = r.parents("li.layui-nav-item");
							if (h(e + ">.layui-nav").attr("lay-accordion") == "true") {
								h(e + ">.layui-nav .layui-nav-itemed").removeClass("layui-nav-itemed")
							}
							r.parent().addClass("layui-this");
							if(parentEl[0]){
								parentEl.siblings().removeClass("layui-nav-itemed").end().addClass("layui-nav-itemed");
								var dl = parentEl.find("dl.layui-nav-child").eq(0),
								sideHeight = h("#layui-side-scroll").height(),
								nodeLen = h(".layui-nav-tree > .layui-nav-item").length,
								cheight = sideHeight - (43*nodeLen);
								if(cheight < 200) cheight = 200;
								dl.css("height", cheight+"px");
							}
							h('ul[lay-filter="' + d + '"]').addClass("layui-hide");
							var p = r.parents(".layui-nav");
							p.removeClass("layui-hide");
							if(h("#homeType").val() == '4') h(j + ">.layui-nav>.layui-nav-item").removeClass("layui-this");
							h(j + '>.layui-nav>.layui-nav-item>a[nav-bind="' + p.attr("nav-id") + '"]').parent().addClass("layui-this");
							var o = r.offset().top + r.outerHeight() + 30 - m.getPageHeight();
							var q = 50 + 65 - r.offset().top;
							if (o > 0) {
								h(e).animate({
									"scrollTop": h(e).scrollTop() + o
								}, 100)
							} else {
								if (q > 0) {
									h(e).animate({
										"scrollTop": h(e).scrollTop() - q
									}, 100)
								}
							}
						} else {}
						if(h(".layui-layout-admin").hasClass("admin-nav-mini")) m.flexible(true);
					}else{
						if(h('.layui-nav-item-navitem')[0]){
							h('.layui-nav-item-navitem').removeClass('layui-this');
							h('.layui-nav-item-navitem a[data-name="'+navname+'"]').trigger('click.navMore');
							m.rollNavPage('auto');
						}else{
							var items = h('.layui-tab-item').eq(0).find('iframe').contents().find(".nav-item"),
							item = '';
							items.each(function(i, v){
								if(h(v).attr('data-name') == navname){
									item = h(v);
									return false;
								}
							})
							if(item) m.loadMoreSide(item, true);					
						}
					}
				}
			} else {
				console.warn("active url is null")
			}
		},
		popupRight: function(n) {
			if (n.title == undefined) {
				n.title = false;
				n.closeBtn = false
			}
			if (n.anim == undefined) {
				n.anim = 2
			}
			if (n.fixed == undefined) {
				n.fixed = true
			}
			n.isOutAnim = false;
			n.offset = "r";
			n.shadeClose = true;
			if (n.area == undefined) {
				n.area = "336px";
			}
			n.skin = "layui-layer-adminRight";
			n.move = false;
			return m.open(n)
		},
		open: function(o) {
			if (!o.area) {
				o.area = (o.type == 2) ? ["360px", "300px"] : "360px"
			}
			if (!o.skin) {
				o.skin = "layui-layer-admin"
			}
			if (!o.offset) {
				//huwei 分辨率低的弹出框位置为10
				if(h('body').hasClass("layui-layout-sm-body")){
					o.offset = "10px"
				}else{
					o.offset = "100px"
				}
			}
			if (o.fixed == undefined) {
				o.fixed = false
			}
			o.resize = o.resize != undefined ? o.resize : false;
			o.shade = o.shade != undefined ? o.shade : 0.1;
			o.anim = -1;
			o.isOutAnim = false;
			var n = o.end;
			o.end = function() {
				k.closeAll("tips");
				n && n()
			};
			return k.open(o)
		},
		req: function(n, o, p, q) {
			m.ajax({
				url: n,
				data: o,
				type: q,
				dataType: "json",
				success: p
			})
		},
		ajax: function(o) {
			var n = o.success;
			o.success = function(p, q, s) {
				var r;
				if ("json" == o.dataType.toLowerCase()) {
					r = p
				} else {
					r = m.parseJSON(p)
				}
				r && (r = p);
				if (m.ajaxSuccessBefore(r, o.url) == false) {
					return
				}
				n(p, q, s)
			};
			o.error = function(p) {
				o.success({
					code: p.status,
					msg: p.statusText
				})
			};
			o.beforeSend = function(r) {
				var q = m.getAjaxHeaders(o.url);
				for (var p = 0; p < q.length; p++) {
					r.setRequestHeader(q[p].name, q[p].value)
				}
			};
			h.ajax(o)
		},
		ajaxSuccessBefore: function(n, o) {
			return true
		},
		getAjaxHeaders: function(n) {
			var o = new Array();
			return o
		},
		parseJSON: function(p) {
			if (typeof p == "string") {
				try {
					var o = JSON.parse(p);
					if (typeof o == "object" && o) {
						return o
					}
				} catch (n) {}
			}
		},
		showLoading: function(q, p, o) {
			var n = ['<div class="ball-loader"><span></span><span></span><span></span><span></span></div>', '<div class="rubik-loader"></div>', '<div class="progress-loader"></div>'];
			if (!q) {
				q = "body"
			}
			if (p == undefined) {
				p = 1
			}
			h(q).addClass("page-no-scroll");
			var r = h(q).children(".page-loading");
			if (r.length <= 0) {
				h(q).append('<div class="page-loading">' + n[p - 1] + "</div>");
				r = h(q).children(".page-loading")
			}
			o && r.css("background-color", "rgba(255,255,255," + o + ")");
			r.show()
		},
		showLoading1: function(q, p, o) {
			var n = ['<div class="ball-loader"><span></span><span></span><span></span><span></span></div>', '<div class="rubik-loader"></div>'];
			if (!q) {
				q = "body"
			}
			if (p == undefined) {
				p = 1
			}
			h(q).addClass("page-no-scroll");
			var r = h(q).children(".page-loading");
			if (r.length <= 0) {
				h(q).append('<div class="page-loading">' + n[p - 1] + "</div>");
				r = h(q).children(".page-loading")
			}
			o && r.css("background-color", "rgba(255,255,255,1" + o + ")");
			r.show()
		},
		removeLoading: function(o, q, n) {
			if (!o) {
				o = "body"
			}
			if (q == undefined) {
				q = true
			}
			var p = h(o).children(".page-loading");
			if (n) {
				p.remove()
			} else {
				q ? p.fadeOut() : p.hide()
			}
			h(o).removeClass("page-no-scroll")
		},
		putTempData: function(n, o) {
			if (o != undefined && o != null) {
				layui.sessionData("tempData", {
					key: n,
					value: o
				})
			} else {
				layui.sessionData("tempData", {
					key: n,
					remove: true
				})
			}
		},
		getTempData: function(n) {
			var o = layui.sessionData("tempData");
			if (o) {
				return o[n]
			} else {
				return false
			}
		},
		rollPage: function(q) {
			var o = h(l + ">.layui-tab-title");
			var p = o.scrollLeft();
			if ("left" === q) {
				o.animate({
					"scrollLeft": p - 120
				}, 100)
			} else {
				if ("auto" === q) {
					var n = 0;
					o.children("li").each(function() {
						if (h(this).hasClass("layui-this")) {
							return false
						} else {
							n += h(this).outerWidth()
						}
					});
					o.animate({
						"scrollLeft": n - 120
					}, 100)
				} else {
					o.animate({
						"scrollLeft": p + 120
					}, 100)
				}
			}
		},
		rollNavPage: function(q) {
			var o = h(".layui-header .layui-nav.layui-layout-center");
			var p = o.scrollLeft();
			if ("left" === q) {
				o.animate({
					"scrollLeft": p - 120
				}, 100)
			} else {
				if ("auto" === q) {
					var n = 0;
					o.children("li").each(function() {
						if (h(this).hasClass("layui-this")) {
							return false
						} else {
							n += h(this).outerWidth()
						}
					});
					o.animate({
						"scrollLeft": n - 120
					}, 100)
				} else {
					o.animate({
						"scrollLeft": p + 120
					}, 100)
				}
			}
		},
		refresh: function(n) {
			var p;
			if (!n) {
				p = h(l + ">.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe");
				if (!p || p.length <= 0) {
					p = h(a + ">div>.admin-iframe")
				}
			} else {
				p = h(l + '>.layui-tab-content>.layui-tab-item>.admin-iframe[lay-id="' + n + '"]');
				if (!p || p.length <= 0) {
					p = h(a + ">.admin-iframe")
				}
			}
			if (p && p[0]) {
				try {
					p[0].contentWindow.location.reload(true)
				} catch (o) {
					p.attr("src", encodeURI(p.attr("src")))
				}
			} else {
				console.warn(n + " is not found")
			}
		},
		closeThisTabs: function(n) {
			m.closeTabOperNav();
			var selflay = parent === self ? layui : top.layui,
			o = selflay.jquery(l + ">.layui-tab-title");
			if (!n) {
				if (o.find("li").first().hasClass("layui-this")) {
					myUtil.msg({type: "warning", message: "主页不能关闭"});
					return
				}
				o.find("li.layui-this").find(".layui-tab-close").trigger("click")
			} else {
				if (n == o.find("li").first().attr("lay-id")) {
					myUtil.msg({type: "warning", message: "主页不能关闭"});
					return
				}
				o.find('li[lay-id="' + n + '"]').find(".layui-tab-close").trigger("click")
			}
		},
		updateThisTab: function(tabs){
			var selflay = parent === self ? layui : top.layui,
			li = selflay.jquery(l + ">.layui-tab-title li.layui-this");
			li.attr("lay-id", tabs.menuId);
			li.children('span').text(tabs.menuName);
			selflay.jquery(".layui-tab-item.layui-show iframe").remove();
			setTimeout(function(){
				selflay.jquery(".layui-tab-item.layui-show").html('<iframe lay-id="' + tabs.menuId + '" src="' + encodeURI(tabs.menuId) + '" frameborder="0" class="admin-iframe"></iframe>');
			}, 80)
        },
		closeOtherTabs: function(n) {
			if (!n) {
				h(l + ">.layui-tab-title li:gt(0):not(.layui-this)").find(".layui-tab-close").trigger("click")
			} else {
				h(l + ">.layui-tab-title li:gt(0)").each(function() {
					if (n != h(this).attr("lay-id")) {
						h(this).find(".layui-tab-close").trigger("click")
					}
				})
			}
			m.closeTabOperNav()
		},
		closeAllTabs: function() {
			h(l + ">.layui-tab-title li:gt(0)").find(".layui-tab-close").trigger("click");
			h(l + ">.layui-tab-title li:eq(0)").trigger("click");
			m.closeTabOperNav()
		},
		closeTabOperNav: function() {
			h(".layui-icon-down .layui-nav .layui-nav-child").removeClass("layui-show")
		},
		changeTheme: function(t) {
			if (t) {
				layui.data(m.tableName, {
					key: "theme",
					value: t
				});
				if (c == t) {
					t = undefined
				}
			} else {
				layui.data(m.tableName, {
					key: "theme",
					remove: true
				})
			}
			m.removeTheme(top);
			!t || top.layui.link(m.getThemeDir() + t + ".css", t);
			var u = top.window.frames;
			for (var p = 0; p < u.length; p++) {
				var r = u[p];
				try {
					m.removeTheme(r)
				} catch (s) {}
				if (t && r.layui) {
					r.layui.link(m.getThemeDir() + t + ".css", t)
				}
				var q = r.frames;
				for (var o = 0; o < q.length; o++) {
					var n = q[o];
					try {
						m.removeTheme(n)
					} catch (s) {}
					if (t && n.layui) {
						n.layui.link(m.getThemeDir() + t + ".css", t)
					}
				}
			}
		},
		removeTheme: function(n) {
			if (!n) {
				n = window
			}
			if (n.layui) {
				var o = "layuicss-theme";
				n.layui.jquery('link[id^="' + o + '"]').remove()
			}
		},
		getThemeDir: function() {
			return layui.cache.base + "theme/"
		},
		maxThisTabs: function() {
			m.fullScreen(h(".layui-tab-content")[0]);
		},
		fullScreen: function(el) {
			var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen,
        	wscript;
 
			if(typeof rfs != "undefined" && rfs) {
				rfs.call(el);
				return;
			}
		
			if(typeof window.ActiveXObject != "undefined") {
				wscript = new ActiveXObject("WScript.Shell");
				if(wscript) {
					wscript.SendKeys("{F11}");
				}
			}
		},
		exitFullScreen: function(el) {
			var el= document,
				cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.mozCancelFullScreen || el.exitFullScreen,
				wscript;
		 
			if (typeof cfs != "undefined" && cfs) {
			  cfs.call(el);
			  return;
			}
		 
			if (typeof window.ActiveXObject != "undefined") {
				wscript = new ActiveXObject("WScript.Shell");
				if (wscript != null) {
					wscript.SendKeys("{F11}");
				}
		    }
		},
		closeThisDialog: function() {
			parent.layer.close(parent.layer.getFrameIndex(window.name))
		},
		closeDialog: function(n) {
			var o = h(n).parents(".layui-layer").attr("id").substring(11);
			k.close(o)
		},
		iframeAuto: function() {
			parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name))
		},
		getPageHeight: function() {
			return document.documentElement.clientHeight || document.body.clientHeight
		},
		getPageWidth: function() {
			return document.documentElement.clientWidth || document.body.clientWidth
		},
		removeNavHover: function() {
			h(".admin-nav-hover>.layui-nav-child").css({
				"top": "auto",
				"max-height": "none",
				"overflow": "auto"
			});
			h(".admin-nav-hover").removeClass("admin-nav-hover")
			
		},
		setNavHoverCss: function(p) {
			var n = h(".admin-nav-hover>.layui-nav-child");
			if (p && n.length > 0) {
				var r = (p.offset().top + n.outerHeight()) > window.innerHeight;
				if (r) {
					var o = p.offset().top - n.outerHeight() + p.outerHeight();
					if (o < 50) {
						var q = m.getPageHeight();
						if (p.offset().top < q / 2) {
							n.css({
								"top": "50px",
								"max-height": q - 50 + "px",
								"overflow": "auto"
							})
						} else {
							n.css({
								"top": p.offset().top,
								"max-height": q - p.offset().top,
								"overflow": "auto"
							})
						}
					} else {
						n.css("top", o)
					}
				} else {
					n.css("top", p.offset().top)
				}
				i = true
			}
		},
		loadMoreSide: function(item, scrollto){
			if(parent !== self){
				h = top.layui.jquery;
				element = top.layui.element;
				form = top.layui.form;
				myUtil = top.layui.wdUtil;
				setter = top.layui.setter;
				eleTree = top.layui.eleTree;
				k = top.layui.layer;
			}
			var url = setter.basePath + 'functionController/queryUserAvailable',
			homeType = h("#homeType").val(),
			callback = function(data){
				if(data){
					if(homeType == '6'){
						m.renderRootSideMenu(data.obj);
						var n = h(".layui-layout-admin").hasClass("admin-nav-mini");
						if(n) m.flexible(true)
					}else{
						var ul = h('.layui-layout-admin .layui-nav.layui-layout-center'),
						temp = [], temp1 = [],
						liswidth = 0;
						if(data.obj.treeNodes && data.obj.treeNodes.length){
							data.obj.treeNodes.forEach(function(v, i){
								temp.push('<li class="layui-nav-item layui-nav-item-navitem" lay-unselect><a href="javascript:void(0);" nav-bind="xt'+i+'" data-type="'+v.hrefType+'" data-path="'+v.hrefLink+'" data-name="'+v.name+'" data-funcid="'+v.funcId+'" data-id="'+v.id+'">'+v.name+'</a></li>');
							});
						}
						ul.empty();
						ul.append(temp);
						h('.layui-nav-item-navitem').each(function(i, v){
							liswidth += h(v).width();
						})
						if(liswidth > h('.layui-nav-box.layui-nav-box-center').width()){
							h('.admin-tabs-nav-control').removeClass('layui-hide');
						}else{
							h('.admin-tabs-nav-control').addClass('layui-hide');
						}
						element.render('nav', 'layui-layout-center');
						element.on('nav(layui-layout-center)', function(elem,a){
							elem.parent().siblings().removeClass('layui-this');
						});
						if(item){
							var navitem = h('.layui-nav-item-navitem a[data-funcid="'+h(item).attr('data-funcid')+'"]');
							navitem.trigger('click');
							if(scrollto){
								m.rollNavPage('auto');
							}
						}
					}
				}
			};
		
			if(h(".layui-nav-item-funbtn").hasClass("layui-hide")) h(".layui-nav-item-funbtn").removeClass("layui-hide");
			h(".layui-nav-item-navitem").remove();
			h(".layui-more-side").remove();
			if(homeType == '6' && item.url){
				url = setter.basePath + item.url;
			}
			myUtil.send(url, {parentId:''}, callback);
		},
        switchModule: function(el){
			if(parent !== self){
				h = top.layui.jquery;
				element = top.layui.element;
				form = top.layui.form;
				myUtil = top.layui.wdUtil;
				setter = top.layui.setter;
				eleTree = top.layui.eleTree;
				k = top.layui.layer;
				if(h(".layui-nav-item-funbtn").hasClass("layui-hide")) h(".layui-nav-item-funbtn").removeClass("layui-hide");
			}
            var el = el || h(this),
            hrefType = el.attr("data-type"),
            name = el.attr("data-name"),
            path = el.attr("data-path"),
			funcid=el.attr("data-funcid")
			
            url = setter.basePath + path,
            ajaxdata = {parentId: el.attr("data-funcid")},
            callback = function(data){
                if(data){
                    var obj = typeof data.obj == "string" ? JSON.parse(data.obj) : data.obj,
					navtype = hrefType == '0' ? ''  : name == '报表版本' ?  '0': name == '数据源管理' ?  'datasources' :  name == '模型管理' ? 'models' : 'resources',
					nodes = hrefType == '0' ? obj.treeNodes : name == '报表版本' ? obj['datareports'] : obj[navtype];
				
					m.renderSideMenu(name,funcid, hrefType, nodes, navtype);
					var n = h(".layui-layout-admin").hasClass("admin-nav-mini");
					if(n) m.flexible(true)
                }
            };
            myUtil.send(url, ajaxdata, callback);
			function updateNodesType(nodes, type){
				if(nodes && nodes.length){
					nodes.forEach(function(vv, ii){
						if(vv.children && vv.children.length){
							updateNodesType(vv.children, type);
						}
						vv.nodeType = type;
					})
				}
			}
        },
        renderSideMenu: function(name, funcid,hrefType, nodes, navtype){
        	var iconArr = '',
			selectedFirstChildren = false, //是否默认选中第一子节点
			iconMaps = {
				
			};
			
			if(navtype){
				h('.layui-nav-titlebox').removeClass('layui-hide');
				h(".layui-nav-titlebox .nav-tree-add").addClass("layui-hide");
				h(".layui-nav-titlebox .nav-tree-add.nav-"+navtype+"-add").removeClass("layui-hide");
				h("#LAY-tree-search-input").val("");
				// h(".layui-nav-tree-toolbar-searchbox").hide();
				h('.layui-side-scroll').css('height', 'calc(100% - 60px)');
			}else{
				h('.layui-nav-titlebox').addClass('layui-hide');
				h('.layui-side-scroll').css('height', '');
			}
			var temp = '<li class="layui-nav-item"><a class="itemds" style="{{indent}}" lay-href="{{url}}" data-id="{{id}}" data-type="{{type}}" data-pid="{{pid}}" data-name="{{name}}"><i class="{{iconSkin}}"></i><cite title="{{text}}">{{text}}</cite>{{diybtn}}</a>{{children}}</li>',
			childrenTemp = '<dd><a class="itemds" style="{{indent}}" lay-href="{{url}}" data-id="{{id}}" data-type="{{type}}" data-pid="{{pid}}" data-name="{{name}}"  ><i class="{{iconSkin}}"></i><cite title="{{text}}">{{text}}</cite>{{diybtn}}</a>{{children}}</dd>',
			str = "", level = 0, cid = '', keys = [],
			updateDataSourceFolder = h("#updateDataSourceFolder")[0],
			deleteDataSourceFolder = h("#deleteDataSourceFolder")[0],
			updateModelFolder = h("#updateModelFolder")[0],
			deleteModelFolder = h("#deleteModelFolder")[0];
			diytemp = '';
			
			if(hrefType == '1' && ((name == '数据源管理' && (updateDataSourceFolder || deleteDataSourceFolder)) || (name == '模型管理' && (updateModelFolder || deleteModelFolder)))){
				diytemp ='<i class="diybtn iconfont icon-gear" style="{{style}}"></i>';
			}
            h("#" + sidemenu).removeClass("layui-hide").html("");
            
            if(nodes && nodes.length){
				setNodePid(nodes, {id: 0, level: 0});
				
				nodes.forEach(function(v,i){
					var children = '',
					diybtn = '';
					if(v.children && v.children.length){
						children = renderNodeChildren(v.children, v.id, (v.menuResourceType || ""));
						children = '<dl class="layui-nav-child">'+children+'</dl>';
						if(diytemp) diybtn = diytemp.replace(/{{style}}/g, 'right: 35px;');
					}else{
						if(diytemp) diybtn = diytemp.replace(/{{style}}/g, '');
					}
					str += temp.replace(/{{url}}/g, v.hrefLink).replace(/{{text}}/g, v.name).replace(/{{name}}/g, name).replace(/{{pid}}/g, v.parentid || '0').replace(/{{id}}/g, v.id).replace(/{{type}}/g, v.type).replace(/{{indent}}/g, 'padding-left:24px;').replace(/{{iconSkin}}/g,v.iconSkin || '').replace(/{{diybtn}}/g, diybtn).replace(/{{children}}/g,children);
				})
				temp = temp.replace(/{{child_innerHTML}}/g, str ||"");
				h("#" + sidemenu).html(str);
				element.render('nav');
				if(hrefType == '1'){
					var itemds = h(".layui-nav-tree .itemds")
					itemds.off('click.activeItem').on('click.activeItem', function(){
						var el = h(this);
						itemds.removeClass('layui-active-item');
						el.addClass('layui-active-item');
					})
				}
				h('.itemds .diybtn').off('click').on('click', function(e){
					var $this = h(this),
					x =$this.offset().left,
					y =$this.offset().top +$this.outerHeight(),
					edit = {
						name: '编辑目录',
						icon: 'layui-icon layui-icon-edit',
						click: function() {
							var node = $this.parent(),
							nodename = node.text(),
							pid = node.attr('data-pid'),
							id = node.attr('data-id'),
							type = node.attr('data-type'),
							content = '<form action="" class="layui-form" id="LAY-admin-editForm" lay-filter="eletree_editNode_form">'
								+'<div class="layui-form-item">'
								+    '<label for="" class="layui-form-label">当前选中：</label>'
								+    '<div class="layui-input-block">'
								+        '<input class="layui-input" value="{{name}}" name="editName" readonly />'
								+    '</div>'
								+'</div>'
								+'<div class="layui-form-item">'
								+    '<label for="" class="layui-form-label">编辑目录：</label>'
								+    '<div class="layui-input-block">'
								+        '<input type="text" name="foldername" placeholder="请输入" autocomplete="off" lay-verType="tips" lay-verify="required" class="layui-input">'
								+    '</div>'
								+'</div>'
								+    '<button type="button" id="eletree_editNode_form" class="layui-btn layui-hide" lay-submit lay-filter="eletree_editNode_form">确认</button>'
							+'</form>';
							m.open({
								title: '编辑目录',
								id: "LAY-admin-editForm",
								skin: 'layui-layer-admin',
								content: content.replace(/{{name}}/g, nodename),
								success: function(layero, index){
									form.render();
									form.on("submit(eletree_editNode_form)",function(formdatas){
										if(formdatas.field.foldername == nodename){
											myUtil.msg({type:"info", message: '文件名相同！'});
										}else{
											var url = name == '模型管理' ? setter.basePath +'modelController/updateModelFolder':setter.basePath +"dataSourceController/updateDataSourceFolder",
											addbtn = h(".nav-tree-add:not(.layui-hide)"),
											ajaxdata =  {"eventType":"update-folder","folderName":formdatas.field.foldername,"treeType":addbtn.attr("data-type"),"treeParentId":pid, 'treeId': id},
											callback = function(data){
												if(data){
													node.find('cite').text(formdatas.field.foldername);
													myUtil.msg({type: "success", message: data.msg});
												}
												layui.layer.close(index);
											}
											myUtil.send(url, ajaxdata, callback);
										}
							
										return false;
									});
								},
								yes: function(layero, index){
									h('#eletree_editNode_form').trigger('click');
								}
							});
					
						}
					},
					del = {
						name: '删除目录',
						icon: 'layui-icon layui-icon-delete',
						click: function() {
							var node = $this.parent(),
							nodename = node.text(),
							pid = node.attr('data-pid'),
							id = node.attr('data-id'),
							type = node.attr('data-type');
							layui.layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+nodename+'</span>吗',{icon: 3, title:'删除目录', skin: 'layui-layer-admin'},
							    function(index, layero){
							        var url = setter.basePath + (name == '模型管理'? 'modelController/deleteModelFolder':"dataSourceController/deleteDataSourceFolder"),
							        ajaxdata = type == "folder" ? {"eventType":"delete-folder","treeId":id} :{"event":"delete-ds","id":id,"settings":{"dsid":dsid}},
							        callback = function(data){
							            if(data){
											var tabid = name == '模型管理' ? setter.basePath +"biController/viewDataModel?cid=null&navname=模型管理&nodeid="+id+ '&id=' + id + '&pid='+pid + '&type=insertAfter&mdtype=1':
											setter.basePath +"biController/viewDataSource?cid=null&navname=数据源管理&nodeid="+id + '&id=' + id + '&pid='+pid + '&type=insertAfter';
											m.closeThisTabs(tabid);
											myUtil.msg({type: "success", message: data.msg});
											node.parent().remove();
											
											if(h('.layui-nav-item-navitem')[0]){
												m.switchModule(h('.layui-nav-item-navitem a[data-name="'+name+'"]'));
											}else{
												var items = h('.layui-tab-item').eq(0).find('iframe').contents().find(".nav-item"),
												item = '';
												items.each(function(i, v){
													if(h(v).attr('data-name') == name){
														item = h(v);
														return false;
													}
												})
												if(item) m.loadMoreSide(item, true);
											}
										}
										layui.layer.close(index);
							        };
							        myUtil.send(url, ajaxdata, callback);
							});
						}
					},
					menus = [];
					if((name == '数据源管理' && updateDataSourceFolder) || (name == '模型管理' && updateModelFolder)){
						menus.push(edit);
					}
					if((name == '数据源管理' && deleteDataSourceFolder) || (name == '模型管理' && deleteModelFolder)){
						menus.push(del);
					}
					layui.contextMenu.show(menus, x, y);
					
					e.preventDefault();
					e.stopPropagation();
				})
				var li = h(l + ">.layui-tab-title li.layui-this"),
				navid = li.attr('lay-id'),
				navparams = navid.split('?'),
				navname = '';
				if(navparams.length && navparams[1]){
					var navparams1 = navparams[1].split('&');
					if(navparams1.length){
						for(var key = 0;key < navparams1.length;key++){
							var arr1 = navparams1[key].split('=');
							if(arr1[0] && arr1[0] == 'navname'){
								navname = arr1[1];
							}
						}
					}
					
				}
				if(h('.layui-nav-box-center').attr('nodeid')){
					var navitem = h('.layui-nav-tree .layui-nav-item a[data-id="'+h('.layui-nav-box-center').attr('nodeid')+'"]');
					navitem.trigger('click');
					navitem.parents('.layui-nav-item').addClass('layui-nav-itemed');
					navitem.parents('dd').addClass('layui-nav-itemed');
					h('.layui-nav-box-center').attr('nodeid', '');
				}else if(li[0] && li.attr('lay-id').indexOf('homePageController') < 0 && navname == name){
					li.trigger('click');
					var lid = li.attr("lay-id"),
						item = h('.itemds[lay-href="'+lid+'"]'),
						parentEl = item && item.parents(".layui-nav-child");
						if(parentEl[0]){
							var sideHeight = h("#layui-side-scroll").height(),
							nodeLen = h(".layui-nav-tree > .layui-nav-item").length,
							cheight = sideHeight - (43*nodeLen);
							if(cheight < 200) cheight = 200;
							parentEl.eq(parentEl.length-1).css("height", cheight+"px");
						}
				}else{
					if(selectedFirstChildren){
						if(hrefType == '1' && name != "数据源管理" && name != "模型管理"){
							findNode(nodes);
							if(keys.length && keys.length > 1){
								for(var k = 0;k < keys.length -1;k++){
									h('.layui-nav-tree .layui-nav-item a[data-id="'+keys[k]+'"]').parent().addClass('layui-nav-itemed');
								}
							}
							if(cid){
								h('.layui-nav-tree .layui-nav-item a[data-id="'+cid+'"]').trigger('click');
							}
						}else{
							h(".layui-nav-tree .layui-nav-item a").eq(0).trigger("click");
						}
					}else{
						var sideHeight = h("#layui-side-scroll").height(),
						nodeLen = h(".layui-nav-tree > .layui-nav-item").length,
						cheight = sideHeight - (43*nodeLen);
						if(cheight < 200) cheight = 200;
						h(".layui-nav-tree .layui-nav-item .layui-nav-child").eq(0).css("height", cheight+"px").parent().addClass("layui-nav-itemed");
					}
				}
			}else{
				h("#" + sidemenu).html('<li class="layui-nav-item layui-nav-itemed" style="text-align: center; color: #ccc;"><a class="itemds" style="background-color:#fff;color:#333 !important;">暂无数据</a></li>');
			}
			function findNode(node){
				if(node.length){
					for(var i = 0; i< node.length; i++){
						if(cid){
							break;
						}else{
							findNode(node[i]);
						}
					}
				}else{
					if(node.children && node.children.length){
						keys.push(node.id);
						findNode(node.children[0]);
					}else if(node.type == '0'){
						return false;
					}else{
						keys.push(node.id);
						cid = node.id;
						return false;
					}
				}
			}
			function renderNodeChildren(items, pid, menuResourceType){
				var itemTemp = '';
				if(items && items.length){
				
					items.forEach(function(v,i){
						var children = '',
						diybtn = '',
						level = v.level ? parseInt(v.level) : 0,
						indent = v.level && v.level !== '0' ? 9 + (level*15) : 24;
						if(v.children && v.children.length){
							children = renderNodeChildren(v.children);
							children = '<dl class="layui-nav-child">'+children+'</dl>';
							if(diytemp) diybtn = diytemp.replace(/{{style}}/g, 'right: 35px;');
						}else{
							if(diytemp) diybtn = diytemp.replace(/{{style}}/g, '');
						}
						if(v.hrefLink.indexOf("reportingSituationController/reportFormDataStatistics") > -1){
							v.hrefLink = (v.hrefLink + "&frequency=" + pid + "&menuResourceType="+ menuResourceType);
						}
						itemTemp += childrenTemp.replace(/{{url}}/g, v.hrefLink).replace(/{{text}}/g, v.name).replace(/{{name}}/g, name).replace(/{{pid}}/g, v.parentid || '0').replace(/{{id}}/g, v.id).replace(/{{type}}/g, v.type).replace(/{{indent}}/g, ('padding-left:'+indent+'px;')).replace(/{{iconSkin}}/g,v.iconSkin || '').replace(/{{diybtn}}/g,diybtn).replace(/{{children}}/g,children);
					})
					return itemTemp;
				}
			}
			
			function setNodePid(items, parent){
				if(items && items.length){
					
					items.forEach(function(v, i){
						v.parentid = parent.id;
						v.level = parent.level + 1;
						if(hrefType == '0'){
							if(!v.iconSkin) v.iconSkin = iconMaps[v.name] || '';
							if(v.hrefLink){
								if(v.hrefLink.indexOf("http") > -1 && v.hrefLink){
									v.hrefLink = v.hrefLink;
								}else{
									v.hrefLink = setter.basePath + v.hrefLink
								}
								if(v.hrefLink.indexOf('?') > -1){
									v.hrefLink = v.hrefLink +"&navname="+name
								}else{
									v.hrefLink = v.hrefLink +"?navname="+name
								}
							} 
						}else{
							if(name == '模型管理'){
								v.iconSkin = v.type == "folder" ? 'iconfont icon-folder-open' : 'iconfont icon-moxing';
								v.hrefLink = setter.basePath +"biController/viewDataModel?cid="+v.mdid +"&navname=模型管理&nodeid="+v.id+ '&id=' + v.id + '&pid='+parent.id + '&type=insertAfter&mdtype=1';
							}else if(name == '数据源管理'){
								v.iconSkin = v.type == "folder" ? 'iconfont icon-folder-open' : 'iconfont icon-datasource-manage';
								v.hrefLink = setter.basePath +"biController/viewDataSource?cid="+v.dsid +"&navname=数据源管理&nodeid="+v.id + '&id=' + v.id + '&pid='+parent.id + '&type=insertAfter';
							}else {
								
								switch (v.type){
									case '0':
										v.iconSkin = v.parentid == "0" ? '' : 'iconfont icon-folder-open layui-font-orange';
										v.hrefLink = '';
										break;
									case '1':
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-ico_jiashicang' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "reportController/viewReport?reportId="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&viewType="+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid;
										break;
									case '2':
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-biaodan' : "iconbox iconimg" + v.type;
										if(name=='报送情况'){
											v.hrefLink = setter.basePath +v.rsUrl+"?formId=" + v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid;
										}else{
											v.hrefLink = setter.basePath +"reportFormController/reportFormSubmit_new?formId=" + v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid;	
										}
										break;
									case '3':
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-lianjie' : "iconbox iconimg" + v.type;
										if(v.rsUrl.indexOf("http") > -1 && v.rsUrl){
											v.hrefLink = v.rsUrl;
										}else{
											v.hrefLink = setter.basePath + v.rsUrl
										}
										if(v.hrefLink.indexOf('?') > -1){
											v.hrefLink = v.hrefLink +"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										}else{
											v.hrefLink = v.hrefLink +"?navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										}
										break;
									case '4':
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-more-report' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + (name == '报表版本' ? ("dataReportVersionController/viewDataReportVersion?cid=" + v.rpid+"&navname="+name+"&nodeid="+v.id) :("dataReportController/preview?cid="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid))
										break;
									case "5":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-Group' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "dataReportController/previewGroup?cid="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										break;
									case "6":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-ico_jiashicang' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "reportController/viewReportBigScreen?reportId="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										break;
									case "7":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-ico_jiashicang' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "reportController/viewReportMobile?reportId="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										break;
									case "8":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-biaodan' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "reportFormController/reportFormSubmit_form?formId="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										break;
									case "9":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-more-report' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "multidimensionalController/reportPreview?cid="+v.rsid+"&nodeid="+v.id
										break;	
									case "10":
										v.iconSkin = v.parentid == "0" ? 'iconfont icon-more-report' : "iconbox iconimg" + v.type;
										v.hrefLink = setter.basePath + "simpleDataReportController/simpleDataReportView?cid="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
										break;				
								}
							}
						}
						if(v.children && v.children.length){
							setNodePid(v.children, v);
						}
					})
				}
			}
           
        },
        renderRootSideMenu: function(nodes){
        	var height = 0;
        	
			h('.layui-nav-titlebox').removeClass('layui-hide');
			h(".layui-nav-titlebox .nav-tree-add").addClass("layui-hide");
			if(h(".layui-nav-titlebox .nav-tree-add.nav-datasources-add")[0]){
				height += 30;
				h(".layui-nav-titlebox .nav-tree-add.nav-datasources-add").removeClass("layui-hide");
			} 
			if(h(".layui-nav-titlebox .nav-tree-add.nav-models-add")[0]){
				height += 30;
				h(".layui-nav-titlebox .nav-tree-add.nav-models-add").removeClass("layui-hide");
			}
			if(h(".layui-nav-titlebox .nav-tree-add.nav-resources-add")[0]){
				height += 30;
				h(".layui-nav-titlebox .nav-tree-add.nav-resources-add").removeClass("layui-hide");
			}
			if(height) height += 30;
			h("#LAY-tree-search-input").val("");
			// h(".layui-nav-tree-toolbar-searchbox").hide();
			h('.layui-side-scroll').css('height', 'calc(100% - '+height+'px)');
        	
        	var temp = '<li class="layui-nav-item"><a class="itemds" style="{{indent}}" lay-href="{{url}}" data-id="{{id}}" data-type="{{type}}" data-pid="{{pid}}" data-name="{{name}}"><i class="{{iconSkin}}"></i><cite title="{{text}}">{{text}}</cite>{{diybtn}}</a>{{children}}</li>',
        	childrenTemp = '<dd><a class="itemds" style="{{indent}}" lay-href="{{url}}" data-id="{{id}}" data-type="{{type}}" data-pid="{{pid}}" data-name="{{name}}" ><i class="{{iconSkin}}"></i><cite title="{{text}}">{{text}}</cite>{{diybtn}}</a>{{children}}</dd>',
        	str = "", level = 0, cid = '', keys = [],
			pname = '',
        	updateDataSourceFolder = h("#updateDataSourceFolder")[0],
        	deleteDataSourceFolder = h("#deleteDataSourceFolder")[0],
        	updateModelFolder = h("#updateModelFolder")[0],
        	deleteModelFolder = h("#deleteModelFolder")[0];
        	diytemp = '';
        	
        	// if(hrefType == '1' && ((name == '数据源管理' && (updateDataSourceFolder || deleteDataSourceFolder)) || (name == '模型管理' && (updateModelFolder || deleteModelFolder)))){
        	// 	diytemp ='<i class="diybtn iconfont icon-gear" style="{{style}}"></i>';
        	// }
            h("#" + sidemenu).removeClass("layui-hide").html("");
            
            if(nodes && nodes.length){
        		setNodePid(nodes, {id: 0, level: 0});
        		
        		nodes.forEach(function(v,i){
        			var children = '',
        			diybtn = '';
        			
        			if(v.children && v.children.length){
        				children = renderNodeChildren(v.children);
        				children = '<dl class="layui-nav-child">'+children+'</dl>';
        				// if(diytemp) diybtn = diytemp.replace(/{{style}}/g, 'right: 35px;');
        			}else{
        				// if(diytemp) diybtn = diytemp.replace(/{{style}}/g, '');
        			}
        			str += temp.replace(/{{url}}/g, v.hrefLink).replace(/{{text}}/g, v.name).replace(/{{name}}/g, name).replace(/{{pid}}/g, v.parentid || '0').replace(/{{id}}/g, v.id).replace(/{{type}}/g, v.type).replace(/{{indent}}/g, 'padding-left:24px;').replace(/{{iconSkin}}/g,v.iconSkin || '').replace(/{{diybtn}}/g, diybtn).replace(/{{children}}/g,children);
        		})
        		temp = temp.replace(/{{child_innerHTML}}/g, str ||"");
        		h("#" + sidemenu).html(str);
        		element.render('nav');
				var itemds = h(".layui-nav-tree .itemds")
				itemds.off('click.activeItem').on('click.activeItem', function(){
					var el = h(this);
					itemds.removeClass('layui-active-item');
					el.addClass('layui-active-item');
				})
        		h('.itemds .diybtn').off('click').on('click', function(e){
        			var $this = h(this),
        			x =$this.offset().left,
        			y =$this.offset().top +$this.outerHeight(),
        			edit = {
        				name: '编辑目录',
        				icon: 'layui-icon layui-icon-edit',
        				click: function() {
        					var node = $this.parent(),
        					nodename = node.text(),
        					pid = node.attr('data-pid'),
        					id = node.attr('data-id'),
        					type = node.attr('data-type'),
        					content = '<form action="" class="layui-form" id="LAY-admin-editForm" lay-filter="eletree_editNode_form">'
        						+'<div class="layui-form-item">'
        						+    '<label for="" class="layui-form-label">当前选中：</label>'
        						+    '<div class="layui-input-block">'
        						+        '<input class="layui-input" value="{{name}}" name="editName" readonly />'
        						+    '</div>'
        						+'</div>'
        						+'<div class="layui-form-item">'
        						+    '<label for="" class="layui-form-label">编辑目录：</label>'
        						+    '<div class="layui-input-block">'
        						+        '<input type="text" name="foldername" placeholder="请输入" autocomplete="off" lay-verType="tips" lay-verify="required" class="layui-input">'
        						+    '</div>'
        						+'</div>'
        						+    '<button type="button" id="eletree_editNode_form" class="layui-btn layui-hide" lay-submit lay-filter="eletree_editNode_form">确认</button>'
        					+'</form>';
        					m.open({
        						title: '编辑目录',
        						id: "LAY-admin-editForm",
        						skin: 'layui-layer-admin',
        						content: content.replace(/{{name}}/g, nodename),
        						success: function(layero, index){
        							form.render();
        							form.on("submit(eletree_editNode_form)",function(formdatas){
        								if(formdatas.field.foldername == nodename){
        									myUtil.msg({type:"info", message: '文件名相同！'});
        								}else{
        									var url = name == '模型管理' ? setter.basePath +'modelController/updateModelFolder':setter.basePath +"dataSourceController/updateDataSourceFolder",
        									addbtn = h(".nav-tree-add:not(.layui-hide)"),
        									ajaxdata =  {"eventType":"update-folder","folderName":formdatas.field.foldername,"treeType":addbtn.attr("data-type"),"treeParentId":pid, 'treeId': id},
        									callback = function(data){
        										if(data){
        											node.find('cite').text(formdatas.field.foldername);
        											myUtil.msg({type: "success", message: data.msg});
        										}
        										layui.layer.close(index);
        									}
        									myUtil.send(url, ajaxdata, callback);
        								}
        					
        								return false;
        							});
        						},
        						yes: function(layero, index){
        							h('#eletree_editNode_form').trigger('click');
        						}
        					});
        			
        				}
        			},
        			del = {
        				name: '删除目录',
        				icon: 'layui-icon layui-icon-delete',
        				click: function() {
        					var node = $this.parent(),
        					nodename = node.text(),
        					pid = node.attr('data-pid'),
        					id = node.attr('data-id'),
        					type = node.attr('data-type');
        					layui.layer.confirm('确认要删除<span style="padding:0 5px;color:#F2944F;font-weight: 600;">'+nodename+'</span>吗',{icon: 3, title:'删除目录', skin: 'layui-layer-admin'},
        					    function(index, layero){
        					        var url = setter.basePath + (name == '模型管理'? 'modelController/deleteModelFolder':"dataSourceController/deleteDataSourceFolder"),
        					        ajaxdata = type == "folder" ? {"eventType":"delete-folder","treeId":id} :{"event":"delete-ds","id":id,"settings":{"dsid":dsid}},
        					        callback = function(data){
        					            if(data){
        									var tabid = name == '模型管理' ? setter.basePath +"biController/viewDataModel?cid=null&navname=模型管理&nodeid="+id+ '&id=' + id + '&pid='+pid + '&type=insertAfter&mdtype=1':
        									setter.basePath +"biController/viewDataSource?cid=null&navname=数据源管理&nodeid="+id + '&id=' + id + '&pid='+pid + '&type=insertAfter';
        									m.closeThisTabs(tabid);
        									myUtil.msg({type: "success", message: data.msg});
        									node.parent().remove();
        									
        									if(h('.layui-nav-item-navitem')[0]){
        										m.switchModule(h('.layui-nav-item-navitem a[data-name="'+name+'"]'));
        									}else{
        										var items = h('.layui-tab-item').eq(0).find('iframe').contents().find(".nav-item"),
        										item = '';
        										items.each(function(i, v){
        											if(h(v).attr('data-name') == name){
        												item = h(v);
        												return false;
        											}
        										})
        										if(item) m.loadMoreSide(item, true);
        									}
        								}
        								layui.layer.close(index);
        					        };
        					        myUtil.send(url, ajaxdata, callback);
        					});
        				}
        			},
        			menus = [];
        			if((name == '数据源管理' && updateDataSourceFolder) || (name == '模型管理' && updateModelFolder)){
        				menus.push(edit);
        			}
        			if((name == '数据源管理' && deleteDataSourceFolder) || (name == '模型管理' && deleteModelFolder)){
        				menus.push(del);
        			}
        			layui.contextMenu.show(menus, x, y);
        			
        			e.preventDefault();
        			e.stopPropagation();
        		})
        		
				findNode(nodes);
				if(keys.length && keys.length > 1){
					for(var k = 0;k < keys.length -1;k++){
						h('.layui-nav-tree .layui-nav-item a[data-id="'+keys[k]+'"]').parent().addClass('layui-nav-itemed');
					}
				}
				if(cid){
					h('.layui-nav-tree .layui-nav-item a[data-id="'+cid+'"]').trigger('click');
				}
        		
        	}else{
        		h("#" + sidemenu).html('<li class="layui-nav-item layui-nav-itemed" style="text-align: center; color: #ccc;"><a class="itemds" style="background-color:#fff;color:#333 !important;">暂无数据</a></li>');
        	}
        	function findNode(node){
        		if(node.length){
        			for(var i = 0; i< node.length; i++){
        				if(cid){
        					break;
        				}else{
        					findNode(node[i]);
        				}
        			}
        		}else{
        			if(node.children && node.children.length){
        				keys.push(node.id);
        				findNode(node.children[0]);
        			}else if(node.type == '0'){
        				return false;
        			}else{
        				keys.push(node.id);
        				cid = node.id;
        				return false;
        			}
        		}
        	}
        	function renderNodeChildren(items){
        		var itemTemp = '';
        		if(items && items.length){
        			items.forEach(function(v,i){
						
        				var children = '',
        				diybtn = '',
        				level = v.level ? parseInt(v.level) : 0,
        				indent = v.level && v.level !== '0' ? 9 + (level*15) : 24;
        				if(v.children && v.children.length){
        					children = renderNodeChildren(v.children);
        					children = '<dl class="layui-nav-child">'+children+'</dl>';
        					// if(diytemp) diybtn = diytemp.replace(/{{style}}/g, 'right: 35px;');
        				}else{
        					// if(diytemp) diybtn = diytemp.replace(/{{style}}/g, '');
        				}
        				itemTemp += childrenTemp.replace(/{{url}}/g, v.hrefLink).replace(/{{text}}/g, v.name).replace(/{{name}}/g, name).replace(/{{pid}}/g, v.parentid || '0').replace(/{{id}}/g, v.id).replace(/{{type}}/g, v.type).replace(/{{indent}}/g, ('padding-left:'+indent+'px;')).replace(/{{iconSkin}}/g,v.iconSkin || '').replace(/{{diybtn}}/g,diybtn).replace(/{{children}}/g,children);
        			})
        			return itemTemp;
        		}
        	}
        	
        	function setNodePid(items, parent){
        		if(items && items.length){
        			items.forEach(function(v, i){
        				v.parentid = parent.id;
        				v.level = parent.level + 1;
        				if(v.children && v.children.length){
							v.hrefLink = '';
							if(v.level == 1) pname = v.name;
        					setNodePid(v.children, v);
        				}else{
							if(v.hrefLink == 'functionController/queryUserAvailable' || v.hrefLink == 'modelController/queryModelFolderTree' || v.hrefLink == 'dataSourceController/queryDataSourcesFolderTree' || v.hrefLink == 'resourceController/queryMenusByMyFolderTree'
							 || v.hrefLink == 'resourceController/queryResourceTreeByMenuId'){
								v.hrefLink = '';
							}else if(parent.hrefType == '0'){
								if(v.hrefLink){
									if(v.hrefLink.indexOf("http") > -1 && v.hrefLink){
										v.hrefLink = v.hrefLink;
									}else{
										v.hrefLink = setter.basePath + v.hrefLink
									}
									if(v.hrefLink.indexOf('?') > -1){
										v.hrefLink = v.hrefLink
									}else{
										v.hrefLink = v.hrefLink
									}
								} 
							}else{
								if(name == '模型管理'){
									v.iconSkin = v.type == "folder" ? 'iconfont icon-folder-open' : 'iconfont icon-moxing';
									v.hrefLink = setter.basePath +"biController/viewDataModel?cid="+v.mdid +"&nodeid="+v.id+ '&id=' + v.id + '&pid='+parent.id + '&type=insertAfter&mdtype=1';
								}else if(name == '数据源管理'){
									v.iconSkin = v.type == "folder" ? 'iconfont icon-folder-open' : 'iconfont icon-datasource-manage';
									v.hrefLink = setter.basePath +"biController/viewDataSource?cid="+v.dsid +"&nodeid="+v.id + '&id=' + v.id + '&pid='+parent.id + '&type=insertAfter';
								}else {
									switch (v.type){
										case '0':
											v.iconSkin = 'iconfont icon-folder-open';
											v.hrefLink = '';
											break;
										case '1':
											v.iconSkin = 'iconfont icon-more-report';
											v.hrefLink = setter.basePath + "reportController/viewReport?reportId="+v.rsid+"&nodeid="+v.id+"&viewType=";
											break;
										case '2':
											v.iconSkin = 'iconfont icon-biaodan';
											v.hrefLink = setter.basePath +"reportFormController/reportFormSubmit_new?formId=" + v.rsid+"&nodeid="+v.id
											break;
										case '3':
											v.iconSkin = 'iconfont icon-lianjie';
											if(v.rsUrl.indexOf("http") > -1 && v.rsUrl){
												v.hrefLink = v.rsUrl;
											}else{
												v.hrefLink = setter.basePath + v.rsUrl
											}
											if(v.hrefLink.indexOf('?') > -1){
												v.hrefLink = v.hrefLink +"&nodeid="+v.id
											}else{
												v.hrefLink = v.hrefLink +"&nodeid="+v.id
											}
											break;
										case '4':
											v.iconSkin = 'iconfont icon-ico_jiashicang';
											v.hrefLink = setter.basePath + (pname == '报表版本' ? ("dataReportVersionController/viewDataReportVersion?cid=" + v.rpid+"&nodeid="+v.id) :("dataReportController/preview?cid="+v.rsid+"&nodeid="+v.id))
											break;
										case "5":
											v.iconSkin = 'iconfont icon-Group';
											v.hrefLink = setter.basePath + "dataReportController/previewGroup?cid="+v.rsid+"&nodeid="+v.id
											break;
										case "6":
											v.iconSkin = 'iconfont icon-ico_jiashicang';
											v.hrefLink = setter.basePath + "reportController/viewReportBigScreen?cid="+v.rsid+"&nodeid="+v.id
											break;
										case "7":
											v.iconSkin = 'iconfont icon-ico_jiashicang';
											v.hrefLink = setter.basePath + "reportController/viewReportMobile?reportId="+v.rsid+"&nodeid="+v.id
											break;
										case "8":
											v.iconSkin = 'iconfont icon-biaodan';
											v.hrefLink = setter.basePath + "reportFormController/reportFormSubmit_form?formId="+v.rsid+"&nodeid="+v.id
											break;				
										case "9":
											v.iconSkin = 'iconfont icon-more-report';
											v.hrefLink = setter.basePath + "multidimensionalController/reportPreview?cid="+v.rsid+"&nodeid="+v.id
											break;		
										case "10":
											v.iconSkin = 'iconfont icon-more-report';
											v.hrefLink = setter.basePath + "simpleDataReportController/simpleDataReportView?cid="+v.rsid+"&navname="+name+"&nodeid="+v.id+"&relationName="+v.name+"&resourcePath="+v.rsPath+"&menuId="+funcid
											break;			
									}
								}
							}
						}
        			})
        		}
        	}
           
        },
        selectedNode: function(id){
			var selflay = parent === self ? layui : top.layui;
			selflay.setter.eleTreeObj.selected(selflay.jquery("#"+ sidetreeid +' .eleTree-node[data-id="'+id+'"]').children(".eleTree-node-content"));
        },
        createNode: function(nodeData){
            var selflay = parent === self ? layui : top.layui;
			selflay.setter.eleTreeObj[nodeData.type](nodeData.id, nodeData.node);
		},
		removeNode: function(id){
			var selflay = parent === self ? layui : top.layui;
			selflay.setter.eleTreeObj.remove(id);
		}
	};
	m.events = {
		flexible: function(o) {
			var n = h(".layui-layout-admin").hasClass("admin-nav-mini");
			m.flexible(n)
		},
		refresh: function() {
			m.refresh()
		},
		back: function() {
			history.back()
		},
		theme: function() {
			var n = h(this).attr("data-url");
			m.popupRight({
				id: "layer-theme",
				type: 2,
				content: n ? n : "page/tpl/tpl-theme.html"
			})
		},
		note: function() {
			var n = h(this).attr("data-url");
			m.popupRight({
				id: "layer-note",
				title: "便签",
				type: 2,
				closeBtn: false,
				content: n ? n : "page/tpl/tpl-note.html"
			})
		},
		message: function() {
			var n = h(this).attr("data-url");
			m.popupRight({
				id: "layer-notice",
				type: 2,
				content: n ? n : "page/tpl/tpl-message.html"
			})
		},
		psw: function() {
			var n = h(this).attr("data-url");
			m.open({
				id: "pswForm",
				type: 2,
				title: "修改密码",
				shade: 0,
				area:["290px","262px"],
				content: n ? n : "page/tpl/tpl-password.html",
				cancel: function(index, layero){ 
					layer.close(index)
					h('.maskpass').hide()
					return false; 
				}
			})
		},
		logout: function() {
			var n = h(this).attr("data-url");
			k.confirm("确定要退出登录吗？", {
				title: "温馨提示",
				skin: "layui-layer-admin"
			}, function() {
				location.replace(n ? n : "/")
			})
		},
		// logo:function(){
		// 	var url = setter.basePath + "http://localhost:8080/rollDataWeb/homePageController/homePage"
		// 	h(location).attr('href', url);
		// },
		fullScreen: function(t) {
			var v = "layui-icon-screen-full",
				p = "layui-icon-screen-restore";
			var n = h(this).find("i");
			var s = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
			if (s) {
				var r = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
				if (r) {
					r.call(document)
				} else {
					if (window.ActiveXObject) {
						var u = new ActiveXObject("WScript.Shell");
						u && u.SendKeys("{F11}")
					}
				}
				n.addClass(v).removeClass(p)
			} else {
				var o = document.documentElement;
				var q = o.requestFullscreen || o.webkitRequestFullscreen || o.mozRequestFullScreen || o.msRequestFullscreen;
				if (q) {
					q.call(o)
				} else {
					if (window.ActiveXObject) {
						var u = new ActiveXObject("WScript.Shell");
						u && u.SendKeys("{F11}")
					}
				}
				n.addClass(p).removeClass(v)
			}
		},
		leftPage: function() {
			m.rollPage("left")
		},
		rightPage: function() {
			m.rollPage()
		},
		leftNavPage: function() {
			m.rollNavPage("left")
		},
		rightNavPage: function() {
			m.rollNavPage()
		},
		maxThisTabs: function() {
			m.maxThisTabs()
		},
		closeThisTabs: function() {
			m.closeThisTabs()
		},
		updateThisTab: function() {
			m.updateThisTab()
		},
		closeOtherTabs: function() {
			m.closeOtherTabs()
		},
		closeAllTabs: function() {
			m.closeAllTabs()
		},
		closeDialog: function() {
			m.closeThisDialog()
		},
		closePageDialog: function() {
			m.closeDialog(this)
		},
		openSearch: function(e){
            h(".layui-nav-tree-toolbar-searchbox").show();
        },
        closeSearch: function(e){
            h("#LAY-tree-search-input").val("");
            h(".layui-nav-tree-toolbar-searchbox").hide();
            h("#LAY-tree-search").trigger("click");
		},
		addTreeNode: function(e){
            var type = e.attr("data-type"),
            content = '<form action="" class="layui-form" id="LAY-admin-addform">'
                +'<div class="layui-form-item layui-form-item-sm">'
                +    '<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>父节点：</label>'
                +    '<div class="layui-input-block">'
                +        '<select name="dataposition" id="LAY-addform-position" lay-filter="newposition">'
                +            '<option value="0">根节点</option>'
                +            '<option value="1">当前节点</option>'
                +        '</select>'
                +    '</div>'
                +'</div>'
                +'<div class="layui-form-item layui-form-item-sm">'
                +    '<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>目录名称：</label>'
                +    '<div class="layui-input-block">'
                +        '<input type="text" id="LAY-addform-name" name="foldername" placeholder="请输入" autocomplete="off" lay-filter="foldername" class="layui-input">'
                +    '</div>'
				+'</div>'
            +'</form>';
            types = {
                datasources: {
                    title: "新建数据源目录",
                    type: "datasource",
                    name: "数据源",
                    url: setter.basePath +'biController/viewDataSource'
                },
                models: {
                    title: "新建模型",
                    type: "model",
                    name: "模型",
					url: setter.basePath +'biController/viewDataModel',
					select: '<div class="layui-form-item layui-form-select-box layui-hide">'
							+    '<label for="" class="layui-form-label">模型类型</label>'
							+    '<div class="layui-input-block">'
							+        '<select name="mdtype" id="LAY-addform-mdtype" lay-filter="mdtype">'
							+            '<option value="1">设计模型</option>'
							+            '<option value="2">SQL数据集</option>'
							+        '</select>'
							+    '</div>'
							+'</div>'
				}
			};
			if(type == 'models' || type == 'datasources'){
				m.open({
					title: types[type].title,
					id: "LAY-admin-createNode",
					content: content.replace(/{{name}}/g, types[type].name),
					success: function(layero, index){
						form.render();
						form.on('select(newtype)', function(data){
							if(data.value == 'folder'){
								h("#LAY-addform-name").removeClass("layui-disabled layui-unselect").attr("disabled", false);
								if(type == 'models'){
									layero.find(".layui-form-select-box").addClass("layui-hide")
								}
							}else{
								h("#LAY-addform-name").addClass("layui-disabled layui-unselect").attr("disabled",true);
								if(type == 'models'){
									layero.find(".layui-form-select-box").removeClass("layui-hide")
								}
							}
						});
					},
					yes: function(index, layero){
						var position = h('#LAY-addform-position').val(),
						node = h('.layui-nav-tree .itemds.layui-active-item'),
						name = h('#LAY-addform-name').val().trim(),
						pid = position == '0' ? '0' : node[0] ? (node.attr('data-id') || '0') : '0';
						if(!name){
							h('#LAY-addform-name').addClass("layui-form-danger").focus();
						}else{
							h('#LAY-addform-name').removeClass("layui-form-danger");
							var url = type == 'models' ? setter.basePath + "modelController/createModelFolder" : setter.basePath + "dataSourceController/createDataSourceFolder",
							ajaxdata =  {"eventType":"create-folder","folderName":name,"treeType": h(".nav-tree-add:not(.layui-hide)").attr("data-type"),"treeParentId":pid},
							callback = function(data){
								if(data){
									var navname = type == 'datasources' ? '数据源管理' : '模型管理'
									
									h('.layui-nav-box-center').attr('nodeid', data.obj);
									if(h('.layui-nav-item-navitem')[0]){
										m.switchModule(h('.layui-nav-item-navitem a[data-name="'+navname+'"]'));
									}else{
										var items = h('.layui-tab-item').eq(0).find('iframe').contents().find(".nav-item"),
										item = '';
										items.each(function(i, v){
											if(h(v).attr('data-name') == navname){
												item = h(v);
												return false;
											}
										})
										if(item) m.loadMoreSide(item, true);
									}
								}
								layer.close(index);
							}
							myUtil.send(url, ajaxdata, callback);
						}
					}
				})
			}else{
				var content = '<form action="" class="layui-form" id="LAY-admin-addreport" lay-filter="LAY-admin-addreport">'
					+'<div class="layui-form-item layui-form-item-sm">'
					+    '<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>主题名称：</label>'
					+    '<div class="layui-input-block">'
					+        '<input type="text" name="rname" placeholder="请输入" autocomplete="off" lay-vertype="tips" lay-verify="required" lay-filter="foldername" class="layui-input">'
					+    '</div>'
					+'</div>'
					+'<div class="layui-form-item layui-form-item-sm">'
					+    '<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>新建类型：</label>'
					+    '<div class="layui-input-block">'
					+        '<select name="rtype" id="LAY-addform-type" lay-filter="createReportType">{{option}}</select>'
					+    '</div>'
					+'</div>'
					+'<div class="layui-form-input-box layui-hide">'
					+'<div class="layui-form-item layui-form-item-sm">'
					+	'<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>行数：</label>'
					+	'<div class="layui-input-block"><input type="number" name="row" lay-vertype="tips" lay-verify="" placeholder="" autocomplete="off" disabled="disabled" class="layui-input row-col-input layui-disabled layui-unselect"></div>'
					+'</div>'
					+'<div class="layui-form-item layui-form-item-sm">'
					+	'<label class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>列数：</label>'
					+	'<div class="layui-input-block"><input type="number" name="col" lay-vertype="tips" lay-verify="" placeholder="" autocomplete="off" disabled="disabled" class="layui-input row-col-input layui-disabled layui-unselect"></div>'
					+'</div>'
					+'</div>'
					+'<div class="layui-form-item layui-hide">'
					+	'<button class="layui-btn createReportSubmit" lay-submit lay-filter="createReportSubmit">立即提交</button>'
					+'</div>'
					+'</form>';
				form.verify({
					positiveNumber: function(value, item) { //value：表单的值、item：表单的DOM对象
						if (!/^\d+$/.test(value)) {
							return '不能为空或负数';
						}
						if (value == 0) {
							return '不能为0';
						}
					}
				});
				var reportTypeObj = {
					'1': '仪表板',
					'4': '复杂报表',
					'5': '报表组',
					'9': '多维报表',
					'6': '大屏',
					'7': '移动端',
					'10': '列式报表'
				},
				options = '';
				h(".createReportTypeOption").each(function(i, v){
					options += ('<option value="'+v.value+'">'+reportTypeObj[v.value]+'</option>');
				})
				content = content.replace(/{{option}}/g, options)
			
				m.open({
					title: '新建主题',
					id: "LAY-admin-createReport",
					content: content,
					success: function(layero, index){
						form.render(null, 'LAY-admin-addreport');
						form.on('select(createReportType)', function(data){
							if(data.value == '4' || data.value == '9'){
								h(".row-col-input").val("").removeClass("layui-disabled layui-unselect").attr({"disabled":false,'lay-verify':"positiveNumber"}).closest(".layui-form-input-box").removeClass("layui-hide");
							}else{
								h(".row-col-input").val("").addClass("layui-disabled layui-unselect").attr({"disabled":true, 'lay-verify':""}).closest(".layui-form-input-box").addClass("layui-hide");
							}
							form.render(null, 'LAY-admin-addreport');
						});
						form.on('submit(createReportSubmit)', function(data){
							if(data.field.rtype == '4' || data.field.rtype == '9'){
								var url = setter.basePath +'dataReportController/dataReportList_new?row='+data.field.row+'&col='+data.field.col+'&type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建报表"
								})
							}else if(data.field.rtype == '5'){
								var url = setter.basePath +'dataReportController/dataReportGroupList?type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建报表组"
								})
							}else if(data.field.rtype == '10'){
								var url = setter.basePath +'simpleDataReportController/simpleDataReportList?type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建列式报表"
								})
							}else if(data.field.rtype == '7'){
								var url = setter.basePath +'biController/viewMobileVisualization?cid=&id=0&pid=0&type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建移动端"
								})
							}else if(data.field.rtype == '6'){
								var url = setter.basePath +'biController/viewBigScreenVisualization?cid=&id=0&pid=0&type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建大屏"
								})
							}else{
								var url = setter.basePath +'biController/viewVisualization?cid=&id=0&pid=0&type='+data.field.rtype+'&name='+data.field.rname;
								top.layui.index.loadView({
									menuId: url,
									menuPath: url,
									menuName: "新建主题"
								})
							}
							layer.close(index);
							return false;
						})
					},
					yes: function(index, layero){
						layero.find(".createReportSubmit").trigger('click');
					}
				})
			}
        }
	};
	h("body").on("click", "*[ew-event]", function() {
		var n = h(this).attr("ew-event");
		var o = m.events[n];
		o && o.call(this, h(this))
	});
	h("body").on("click", "*[ew-href]", function() {
		var n = h(this).attr("ew-href");
		var o = h(this).text();
		top.layui.index.openTab({
			title: o,
			url: n
		})
	});
	h("#LAY-tree-search-open").addClass('layui-hide');
	h(".layui-nav-tree-toolbar-searchbox").show();
	h("#LAY-tree-search-close").addClass('layui-hide');
	h("#LAY-tree-search").css({right: "10px"});
	// h("body").on("mouseenter", "*[lay-tips]", function() {
	// 	var n = h(this).attr("lay-tips");
	// 	var o = h(this).attr("lay-direction");
	// 	var p = h(this).attr("lay-bg");
	// 	k.tips(n, this, {
	// 		tips: [o || 3, p || "#333333"],
	// 		time: -1
	// 	})
	// }).on("mouseleave", "*[lay-tips]", function() {
	// 	k.closeAll("tips")
	// });
	// var i = false;
	// h("body").on("mouseenter", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a", function() {
	// 	if (m.getPageWidth() > 750) {
	// 		var p = h(this);
	// 		h(".admin-nav-hover>.layui-nav-child").css("top", "auto");
	// 		h(".admin-nav-hover").removeClass("admin-nav-hover");
	// 		p.parent().addClass("admin-nav-hover");
	// 		var n = h(".admin-nav-hover>.layui-nav-child");
	// 		if (n.length > 0) {
	// 			m.setNavHoverCss(p)
	// 		} else {
	// 			var o = p.find(".itemds").context.innerText;
	// 			if(o){
	// 				k.tips(o, p, {
	// 					tips: [2, "#333333"],
	// 					time: -1
	// 				})
	// 			}
	// 		}
	// 	}
	// }).on("mouseleave", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a", function() {
	// 	k.closeAll("tips")
	// });
	h("body").on("mouseleave", ".layui-layout-admin.admin-nav-mini .layui-side", function() {
		i = false;
		setTimeout(function() {
			if (!i) {
				m.removeNavHover()
			}
		}, 500)
	});
	h("body").on("mouseenter", ".layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item.admin-nav-hover .layui-nav-child", function() {
		i = true
	});
	
	if (!layui.contextMenu) {
		h(document).off("click.ctxMenu").on("click.ctxMenu", function() {
			var q = top.window.frames;
			for (var n = 0; n < q.length; n++) {
				var o = q[n];
				try {
					o.layui.jquery(".ctxMenu").remove()
				} catch (p) {}
			}
			top.layui.jquery(".ctxMenu").remove()
		})
	}
	var g = layui.data(m.tableName);
	if (g && g.theme) {
		(g.theme == c) || layui.link(m.getThemeDir() + g.theme + ".css", g.theme)
	} else {
		if (c != m.defaultTheme) {
			layui.link(m.getThemeDir() + m.defaultTheme + ".css", m.defaultTheme)
		}
	}
	h("#LAY-tree-search").off("click").on("click", function(){
        var value = h("#LAY-tree-search-input").val();
        if(value){
        	var items = h('#LAY-system-side-menu .itemds cite'),
        	firstItem = '';
        	if(items && items.length){
        		items.each(function(i, v){
        			if(v.title.indexOf(value) > -1){
        				var el = h(v);
        				if(!firstItem) firstItem = el.parent().parent();
        				var titles = v.title.split(value);
        				for(var j = 1; j < titles.length; j+=2){
							titles.splice(j, 0, '<span class="filterResultItem" style="color: whitesmoke;background-color: darkred;">'+value+'</span>');
						}
						v.innerHTML = titles.join('');
						openParent(el);
        			}
        		})
        		if(firstItem){
        			h("#layui-side-scroll").scrollTop(firstItem[0].offsetTop);
        		}
        	}
        }else{
            var items = h('#LAY-system-side-menu .itemds cite .filterResultItem');
            if(items[0]){
            	items.each(function(i, v){
            		h(v).parent().text(h(v).parent().attr('title'));
            	})
            }
        }
        function openParent(el){
        	var childEl = el.closest('.layui-nav-child');
        	if(childEl[0]){
        		var parentEl = childEl.parent();
        		if(parentEl[0]){
        			if(!parentEl.hasClass('layui-nav-itemed')) parentEl.addClass('layui-nav-itemed');
         			openParent(parentEl);
        		}
        	}
        }
	});
	
	f("admin", m)
});