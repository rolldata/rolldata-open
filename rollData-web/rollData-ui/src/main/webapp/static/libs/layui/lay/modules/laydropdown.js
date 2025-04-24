layui.define(["jquery", "laytpl", "lay"], function(e) {
    "use strict";
    var i = layui.$
      , n = layui.laytpl
      , t = layui.hint()
      , a = layui.device()
      , l = a.mobile ? "click" : "mousedown"
      , r = "laydropdown"
      , o = "layui_" + r + "_index"
      , u = {
        config: {},
        index: layui[r] ? layui[r].index + 1e4 : 0,
        set: function(e) {
            var n = this;
            return n.config = i.extend({}, n.config, e),
            n
        },
        on: function(e, i) {
            return layui.onevent.call(this, r, e, i)
        }
    }
      , d = function() {
        var e = this
          , i = e.config
          , n = i.id;
        return d.that[n] = e,
        {
            config: i,
            reload: function(i) {
                e.reload.call(e, i)
            }
        }
    }
      , s = "layui-dropdown"
      , m = "layui-menu-item-up"
      , c = "layui-menu-item-down"
      , p = "layui-menu-body-title"
      , y = "layui-menu-item-group"
      , f = "layui-menu-item-parent"
      , v = "layui-menu-item-divider"
      , g = "layui-menu-item-checked"
      , h = "layui-menu-item-checked2"
      , w = "layui-menu-body-panel"
      , C = "layui-menu-body-panel-left"
      , V = "." + y + ">." + p
      , k = function(e) {
        var n = this;
        n.index = ++u.index,
        n.config = i.extend({}, n.config, u.config, e),
        n.init()
    };
    k.prototype.config = {
        trigger: "click",
        content: "",
        className: "",
        style: "",
        show: !1,
        isAllowSpread: !0,
        isSpreadItem: !0,
        data: [],
        delay: 300
    },
    k.prototype.reload = function(e) {
        var n = this;
        n.config = i.extend({}, n.config, e),
        n.init(!0)
    }
    ,
    k.prototype.init = function(e) {
        var n = this
          , t = n.config
          , a = t.elem = i(t.elem);
        if (a.length > 1)
            return layui.each(a, function() {
                u.render(i.extend({}, t, {
                    elem: this
                }))
            }),
            n;
        if (!e && a[0] && a.data(o)) {
            var l = d.getThis(a.data(o));
            if (!l)
                return;
            return l.reload(t)
        }
        t.id = "id"in t ? t.id : n.index,
        t.show && n.render(e),
        n.events()
    }
    ,
    k.prototype.render = function(e) {
        var t = this
          , a = t.config
          , r = i("body")
          , s = function() {
            var e = i('<ul class="layui-menu layui-dropdown-menu"></ul>');
            return a.data.length > 0 ? m(e, a.data) : e.html('<li class="layui-menu-item-none">no menu</li>'),
            e
        }
          , m = function(e, t) {
            return layui.each(t, function(t, l) {
                var r = l.child && l.child.length > 0
                  , o = "isSpreadItem"in l ? l.isSpreadItem : a.isSpreadItem
                  , u = l.templet ? n(l.templet).render(l) : a.templet ? n(a.templet).render(l) : l.title
                  , d = function() {
                    return r && (l.type = l.type || "parent"),
                    l.type ? {
                        group: "group",
                        parent: "parent",
                        "-": "-"
                    }[l.type] || "parent" : ""
                }();
                if ("-" === d || l.title || l.id || r) {
                    var s = i(["<li" + function() {
                        var e = {
                            group: "layui-menu-item-group" + (a.isAllowSpread ? o ? " layui-menu-item-down" : " layui-menu-item-up" : ""),
                            parent: f,
                            "-": "layui-menu-item-divider"
                        };
                        return r || d ? ' class="' + e[d] + '"' : ""
                    }() + ">", function() {
                        var e = "href"in l ? '<a href="' + l.href + '" target="' + (l.target || "_self") + '">' + u + "</a>" : u;
                        return r ? '<div class="' + p + '">' + e + function() {
                            return "parent" === d ? '<i class="layui-icon layui-icon-right"></i>' : "group" === d && a.isAllowSpread ? '<i class="layui-icon layui-icon-' + (o ? "up" : "down") + '"></i>' : ""
                        }() + "</div>" : '<div class="' + p + '">' + e + "</div>"
                    }(), "</li>"].join(""));
                    if (s.data("item", l),
                    r) {
                        var c = i('<div class="layui-panel layui-menu-body-panel"></div>')
                          , y = i("<ul></ul>");
                        "parent" === d ? (c.append(m(y, l.child)),
                        s.append(c)) : s.append(m(y, l.child))
                    }
                    e.append(s)
                }
            }),
            e
        }
          , c = ['<div class="layui-dropdown layui-border-box layui-panel layui-anim layui-anim-downbit">', "</div>"].join("");
        ("contextmenu" === a.trigger || lay.isTopElem(a.elem[0])) && (e = !0),
        !e && a.elem.data(o + "_opened") || (t.elemView = i(c),
        t.elemView.append(a.content || s()),
        a.className && t.elemView.addClass(a.className),
        a.style && t.elemView.attr("style", a.style),
        u.thisId = a.id,
        t.remove(),
        r.append(t.elemView),
        a.elem.data(o + "_opened", !0),
        t.position(),
        d.prevElem = t.elemView,
        d.prevElem.data("prevElem", a.elem),
        t.elemView.find(".layui-menu").on(l, function(e) {
            layui.stope(e)
        }),
        t.elemView.find(".layui-menu li").on("click", function(e) {
            var n = i(this)
              , l = n.data("item") || {}
              , r = l.child && l.child.length > 0;
            r || "-" === l.type || (t.remove(),
            "function" == typeof a.click && a.click(l, n))
        }),
        t.elemView.find(V).on("click", function(e) {
            var n = i(this)
              , t = n.parent()
              , l = t.data("item") || {};
            "group" === l.type && a.isAllowSpread && d.spread(t)
        }),
        "mouseenter" === a.trigger && t.elemView.on("mouseenter", function() {
            clearTimeout(d.timer)
        }).on("mouseleave", function() {
            t.delayRemove()
        }))
    }
    ,
    k.prototype.position = function(e) {
        var i = this
          , n = i.config;
        lay.position(n.elem[0], i.elemView[0], {
            position: n.position,
            e: i.e,
            clickType: "contextmenu" === n.trigger ? "right" : null,
            align: n.align || null
        })
    }
    ,
    k.prototype.remove = function() {
        var e = this
          , i = (e.config,
        d.prevElem);
        i && (i.data("prevElem") && i.data("prevElem").data(o + "_opened", !1),
        i.remove(), ("function" == typeof e.config.remove && e.config.remove(e.prevElem)))
    }
    ,
    k.prototype.delayRemove = function() {
        var e = this
          , i = e.config;
        clearTimeout(d.timer),
        d.timer = setTimeout(function() {
            e.remove()
        }, i.delay)
    }
    ,
    k.prototype.events = function() {
        var e = this
          , i = e.config;
        "hover" === i.trigger && (i.trigger = "mouseenter"),
        e.prevElem && e.prevElem.off(i.trigger, e.prevElemCallback),
        e.prevElem = i.elem,
        e.prevElemCallback = function(n) {
            clearTimeout(d.timer),
            e.e = n,
            e.render(),
            n.preventDefault(),
            "function" == typeof i.ready && i.ready(e.elemView, i.elem, e.e.target)
        }
        ,
        i.elem.on(i.trigger, e.prevElemCallback),
        "mouseenter" === i.trigger && i.elem.on("mouseleave", function() {
            e.delayRemove()
        })
    }
    ,
    d.that = {},
    d.getThis = function(e) {
        var i = d.that[e];
        return i || t.error(e ? r + " instance with ID '" + e + "' not found" : "ID argument required"),
        i
    }
    ,
    d.spread = function(e) {
        var i = e.children("." + p).find(".layui-icon");
        e.hasClass(m) ? (e.removeClass(m).addClass(c),
        i.removeClass("layui-icon-down").addClass("layui-icon-up")) : (e.removeClass(c).addClass(m),
        i.removeClass("layui-icon-up").addClass("layui-icon-down"))
    }
    ,
    !function() {
        var e = i(window)
          , n = i(document);
        e.on("resize", function() {
            if (u.thisId) {
                var e = d.getThis(u.thisId);
                if (e) {
                    if (!e.elemView[0] || !i("." + s)[0])
                        return !1;
                    var n = e.config;
                    "contextmenu" === n.trigger ? e.remove() : e.position()
                }
            }
        }),
        n.on(l, function(e) {
            if (u.thisId) {
                var i = d.getThis(u.thisId);
                if (i) {
                    var n = i.config;
                    !lay.isTopElem(n.elem[0]) && "contextmenu" !== n.trigger && (e.target === n.elem[0] || n.elem.find(e.target)[0] || e.target === i.elemView[0] || i.elemView && i.elemView.find(e.target)[0]) || i.remove()
                }
            }
        });
        var t = ".layui-menu:not(.layui-dropdown-menu) li";
        n.on("click", t, function(e) {
            var n = i(this)
              , t = n.parents(".layui-menu").eq(0)
              , a = n.hasClass(y) || n.hasClass(f)
              , l = t.attr("lay-filter") || t.attr("id")
              , o = lay.options(this);
            n.hasClass(v) || a || (t.find("." + g).removeClass(g),
            t.find("." + h).removeClass(h),
            n.addClass(g),
            n.parents("." + f).addClass(h),
            layui.event.call(this, r, "click(" + l + ")", o))
        }),
        n.on("click", t + V, function(e) {
            var n = i(this)
              , t = n.parents("." + y + ":eq(0)")
              , a = lay.options(t[0]);
            "isAllowSpread"in a && !a.isAllowSpread || d.spread(t)
        });
        var a = ".layui-menu ." + f;
        n.on("mouseenter", a, function(n) {
            var t = i(this)
              , a = t.find("." + w);
            if (a[0]) {
                var l = a[0].getBoundingClientRect();
                l.right > e.width() && (a.addClass(C),
                l = a[0].getBoundingClientRect(),
                l.left < 0 && a.removeClass(C)),
                l.bottom > e.height() && a.eq(0).css("margin-top", -(l.bottom - e.height()))
            }
        }).on("mouseleave", a, function(e) {
            var n = i(this)
              , t = n.children("." + w);
            t.removeClass(C),
            t.css("margin-top", 0)
        })
    }(),
    u.reload = function(e, i) {
        var n = d.getThis(e);
        return n ? (n.reload(i),
        d.call(n)) : this
    }
    ,
    u.render = function(e) {
        var i = new k(e);
        return d.call(i)
    }
    ,
    e(r, u)
});