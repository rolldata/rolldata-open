/** EasyWeb iframe v3.1.1 data:2019-03-24 License By http://easyweb.vip */

layui.define(["jquery"], function(a) {
	var c = layui.jquery;
	var b = {
		bind: function(e, d) {
			c(e).bind("contextmenu", function(f) {
				b.show(d, f.clientX, f.clientY);
				return false
			})
		},
		getHtml: function(e, d) {
			var h = "";
			for (var f = 0; f < e.length; f++) {
				var g = e[f];
				g.itemId = "ctxMenu-" + d + f;
				if (g.subs && g.subs.length > 0) {
					h += '<div class="ctxMenu-item haveMore" lay-id="' + g.itemId + '" lay-value="'+g.value+'">';
					h += "<a>";
					if (g.icon) {
						h += '<i class="' + g.icon + ' ctx-icon"></i>'
					}
					h += g.name;
					h += '<i class="layui-icon layui-icon-right icon-more"></i>';
					h += "</a>";
					h += '<div class="ctxMenu-sub" style="display: none;">';
					h += b.getHtml(g.subs, d + f);
					h += "</div>"
				}else if(g.type && g.type == 'input'){
					h += '<div class="ctxMenu-item" lay-id="' + g.itemId + '" lay-value="'+g.value+'">';
					h += "<a>";
					if (g.icon) {
						h += '<i class="' + g.icon + ' ctx-icon"></i>';
					}
					h += '<input class="ctxMenu-item-input" type="'+(g.inputType || 'text')+'" value="'+(g.value ||'')+'" />';
					h += '<div class="ctxMenu-item-btn layui-icon layui-icon-ok '+g.btnicon+'" title="确定"></div>';
					h += "</a>";
				} else {
					h += '<div class="ctxMenu-item" lay-id="' + g.itemId + '" lay-value="'+g.value+'">';
					h += "<a>";
					if (g.icon) {
						h += '<i class="' + g.icon + ' ctx-icon"></i>'
					}
					h += g.name;
					h += "</a>"
				}
				h += "</div>";
				if (g.hr == true) {
					h += "<hr/>"
				}
			}
			return h
		},
		setEvents: function(d) {
			for (var e = 0; e < d.length; e++) {
				var f = d[e];
				if(f.protoObj && !c('.ctxMenu').data("protoObj")){
					c('.ctxMenu').data("protoObj", f.protoObj);
				}
				if(f.change){
					c(".ctxMenu").on("change", '[lay-id="' + f.itemId + '"] .ctxMenu-item-input', f.change);
					c(".ctxMenu").on("click", '[lay-id="' + f.itemId + '"] .ctxMenu-item-btn', f.click);
					c(".ctxMenu").on("click", '[lay-id="' + f.itemId + '"]', function(e){
						e.preventDefault();
						e.stopPropagation()
					})
				}else if(f.click){
					c(".ctxMenu").on("click", '[lay-id="' + f.itemId + '"]', f.click)
				}
				if (f.subs && f.subs.length > 0) {
					b.setEvents(f.subs)
				}
			}
		},
		remove: function() {
			if(b.removeBefore) b.removeBefore()
			var h = top.window.frames;
			for (var d = 0; d < h.length; d++) {
				var f = h[d];
				try {
					f.layui.jquery(".ctxMenu").remove()
				} catch (g) {}
			}
			try {
				top.layui.jquery(".ctxMenu").remove()
			} catch (g) {}
			
		},
		getPageHeight: function() {
			return document.documentElement.clientHeight || document.body.clientHeight
		},
		getPageWidth: function() {
			return document.documentElement.clientWidth || document.body.clientWidth
		},
		show: function(e, d, i, t, r) {
			var f = "left: " + d + "px; top: " + i + "px;";
			var h = '<div class="ctxMenu" style="' + f + '">';
			h += !t ? b.getHtml(e, "") : e;
			h += "</div>";
			b.remove();
			c("body").append(h);
			var g = c(".ctxMenu");
			if (d + g.outerWidth() > b.getPageWidth()) {
				d -= g.outerWidth()
			}
			if (i + g.outerHeight() > b.getPageHeight()) {
				i = i - g.outerHeight();
				if (i < 0) {
					i = 0
				}
			}
			g.css({
				"top": i,
				"left": d
			});
			if(r) b.removeBefore = r;
			if(!!t) return;
			b.setEvents(e);
			c(".ctxMenu-item.haveMore").on("mouseenter", function() {
				var j = c(this).find(">a");
				var k = c(this).find(">.ctxMenu-sub");
				var m = j.offset().top;
				var l = j.offset().left + j.outerWidth();
				if (l + k.outerWidth() > b.getPageWidth()) {
					l = j.offset().left - k.outerWidth()
				}
				if (m + k.outerHeight() > b.getPageHeight()) {
					m = m - k.outerHeight() + j.outerHeight();
					if (m < 0) {
						m = 0
					}
				}
				c(this).find(">.ctxMenu-sub").css({
					"top": m,
					"left": l,
					"display": "block"
				})
			}).on("mouseleave", function() {
				c(this).find(">.ctxMenu-sub").css("display", "none")
			})
		}
	};
	c(document).off("click.ctxMenu").on("click.ctxMenu", function() {
		b.remove()
	});
	c("body").off("click.ctxMenuMore").on("click.ctxMenuMore", ".ctxMenu-item", function(d) {
		if (c(this).hasClass("haveMore")) {
			if (d !== void 0) {
				d.preventDefault();
				d.stopPropagation()
			}
		} else {
			b.remove()
		}
	});
	a("contextMenu", b)
});