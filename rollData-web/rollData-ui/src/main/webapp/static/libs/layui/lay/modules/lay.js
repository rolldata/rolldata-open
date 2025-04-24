!function(t) {
    "use strict";
    var e = "lay"
      , n = t.document
      , r = function(t) {
        return new i(t)
    }
      , i = function(t) {
        for (var e = 0, r = "object" == typeof t ? [t] : (this.selector = t,
        n.querySelectorAll(t || null)); e < r.length; e++)
            this.push(r[e])
    };
    i.prototype = [],
    i.prototype.constructor = i,
    r.extend = function() {
        var t = 1
          , e = arguments
          , n = function(t, e) {
            t = t || ("array" === layui._typeof(e) ? [] : {});
            for (var r in e)
                t[r] = e[r] && e[r].constructor === Object ? n(t[r], e[r]) : e[r];
            return t
        };
        for (e[0] = "object" == typeof e[0] ? e[0] : {}; t < e.length; t++)
            "object" == typeof e[t] && n(e[0], e[t]);
        return e[0]
    }
    ,
    r.v = "1.1",
    r.ie = function() {
        var e = navigator.userAgent.toLowerCase();
        return !!(t.ActiveXObject || "ActiveXObject"in t) && ((e.match(/msie\s(\d+)/) || [])[1] || "11")
    }(),
    r.layui = layui || {},
    r.getPath = layui.cache.dir,
    r.stope = layui.stope,
    r.each = function() {
        return layui.each.apply(layui, arguments),
        this
    }
    ,
    r.digit = function(t, e, n) {
        var r = "";
        t = String(t),
        e = e || 2;
        for (var i = t.length; i < e; i++)
            r += "0";
        return t < Math.pow(10, e) ? r + (0 | t) : t
    }
    ,
    r.elem = function(t, e) {
        var i = n.createElement(t);
        return r.each(e || {}, function(t, e) {
            i.setAttribute(t, e)
        }),
        i
    }
    ,
    r.hasScrollbar = function() {
        return n.body.scrollHeight > (t.innerHeight || n.documentElement.clientHeight)
    }
    ,
    r.style = function(t) {
        t = t || {};
        var e = r.elem("style")
          , n = t.text || ""
          , i = t.target || r("body")[0];
        if (n) {
            "styleSheet"in e ? (e.setAttribute("type", "text/css"),
            e.styleSheet.cssText = n) : e.innerHTML = n,
            r.style.index = r.style.index || 0,
            r.style.index++;
            var o = e.id = "LAY-STYLE-" + (t.id || "DF-" + r.style.index)
              , c = r(i).find("#" + o);
            c[0] && c.remove(),
            r(i).append(e)
        }
    }
    ,
    r.position = function(e, i, o) {
        if (i) {
            o = o || {},
            e !== n && e !== r("body")[0] || (o.clickType = "right");
            var c = "right" === o.clickType ? function() {
                var e = o.e || t.event || {};
                return {
                    left: e.clientX,
                    top: e.clientY,
                    right: e.clientX,
                    bottom: e.clientY
                }
            }() : e.getBoundingClientRect()
              , u = i.offsetWidth
              , a = i.offsetHeight
              , s = function(t) {
                return t = t ? "scrollLeft" : "scrollTop",
                n.body[t] | n.documentElement[t]
            }
              , l = function(t) {
                return n.documentElement[t ? "clientWidth" : "clientHeight"]
            }
              , f = 5
              , h = c.left
              , p = c.bottom;
            "center" === o.align ? h -= (u - e.offsetWidth) / 2 : "right" === o.align && (h = h - u + e.offsetWidth),
            h + u + f > l("width") && (h = l("width") - u - f),
            h < f && (h = f),
            p + a + f > l() && (c.top > a + f ? p = c.top - a - 2 * f : "right" === o.clickType && (p = l() - a - 2 * f,
            p < 0 && (p = 0)));
            var y = o.position;
            if (y && (i.style.position = y),
            i.style.left = h + ("fixed" === y ? 0 : s(1)) + "px",
            i.style.top = p + ("fixed" === y ? 0 : s()) + "px",
            !r.hasScrollbar()) {
                var d = i.getBoundingClientRect();
                !o.SYSTEM_RELOAD && d.bottom + f > l() && (o.SYSTEM_RELOAD = !0,
                setTimeout(function() {
                    r.position(e, i, o)
                }, 50))
            }
        }
    }
    ,
    r.options = function(t, e) {
        var n = r(t)
          , i = e || "lay-options";
        try {
            return new Function("return " + (n.attr(i) || "{}"))()
        } catch (o) {
            return hint.error("parseerror\uff1a" + o, "error"),
            {}
        }
    }
    ,
    r.isTopElem = function(t) {
        var e = [n, r("body")[0]]
          , i = !1;
        return r.each(e, function(e, n) {
            if (n === t)
                return i = !0
        }),
        i
    }
    ,
    i.addStr = function(t, e) {
        return t = t.replace(/\s+/, " "),
        e = e.replace(/\s+/, " ").split(" "),
        r.each(e, function(e, n) {
            new RegExp("\\b" + n + "\\b").test(t) || (t = t + " " + n)
        }),
        t.replace(/^\s|\s$/, "")
    }
    ,
    i.removeStr = function(t, e) {
        return t = t.replace(/\s+/, " "),
        e = e.replace(/\s+/, " ").split(" "),
        r.each(e, function(e, n) {
            var r = new RegExp("\\b" + n + "\\b");
            r.test(t) && (t = t.replace(r, ""))
        }),
        t.replace(/\s+/, " ").replace(/^\s|\s$/, "")
    }
    ,
    i.prototype.find = function(t) {
        var e = this
          , n = 0
          , i = []
          , o = "object" == typeof t;
        return this.each(function(r, c) {
            for (var u = o ? c.contains(t) : c.querySelectorAll(t || null); n < u.length; n++)
                i.push(u[n]);
            e.shift()
        }),
        o || (e.selector = (e.selector ? e.selector + " " : "") + t),
        r.each(i, function(t, n) {
            e.push(n)
        }),
        e
    }
    ,
    i.prototype.each = function(t) {
        return r.each.call(this, this, t)
    }
    ,
    i.prototype.addClass = function(t, e) {
        return this.each(function(n, r) {
            r.className = i[e ? "removeStr" : "addStr"](r.className, t)
        })
    }
    ,
    i.prototype.removeClass = function(t) {
        return this.addClass(t, !0)
    }
    ,
    i.prototype.hasClass = function(t) {
        var e = !1;
        return this.each(function(n, r) {
            new RegExp("\\b" + t + "\\b").test(r.className) && (e = !0)
        }),
        e
    }
    ,
    i.prototype.css = function(t, e) {
        var n = this
          , i = function(t) {
            return isNaN(t) ? t : t + "px"
        };
        return "string" == typeof t && void 0 === e ? function() {
            if (n.length > 0)
                return n[0].style[t]
        }() : n.each(function(n, o) {
            "object" == typeof t ? r.each(t, function(t, e) {
                o.style[t] = i(e)
            }) : o.style[t] = i(e)
        })
    }
    ,
    i.prototype.width = function(t) {
        var e = this;
        return void 0 === t ? function() {
            if (e.length > 0)
                return e[0].offsetWidth
        }() : e.each(function(n, r) {
            e.css("width", t)
        })
    }
    ,
    i.prototype.height = function(t) {
        var e = this;
        return void 0 === t ? function() {
            if (e.length > 0)
                return e[0].offsetHeight
        }() : e.each(function(n, r) {
            e.css("height", t)
        })
    }
    ,
    i.prototype.attr = function(t, e) {
        var n = this;
        return void 0 === e ? function() {
            if (n.length > 0)
                return n[0].getAttribute(t)
        }() : n.each(function(n, r) {
            r.setAttribute(t, e)
        })
    }
    ,
    i.prototype.removeAttr = function(t) {
        return this.each(function(e, n) {
            n.removeAttribute(t)
        })
    }
    ,
    i.prototype.html = function(t) {
        var e = this;
        return void 0 === t ? function() {
            if (e.length > 0)
                return e[0].innerHTML
        }() : this.each(function(e, n) {
            n.innerHTML = t
        })
    }
    ,
    i.prototype.val = function(t) {
        var e = this;
        return void 0 === t ? function() {
            if (e.length > 0)
                return e[0].value
        }() : this.each(function(e, n) {
            n.value = t
        })
    }
    ,
    i.prototype.append = function(t) {
        return this.each(function(e, n) {
            "object" == typeof t ? n.appendChild(t) : n.innerHTML = n.innerHTML + t
        })
    }
    ,
    i.prototype.remove = function(t) {
        return this.each(function(e, n) {
            t ? n.removeChild(t) : n.parentNode.removeChild(n)
        })
    }
    ,
    i.prototype.on = function(t, e) {
        return this.each(function(n, r) {
            r.attachEvent ? r.attachEvent("on" + t, function(t) {
                t.target = t.srcElement,
                e.call(r, t)
            }) : r.addEventListener(t, e, !1)
        })
    }
    ,
    i.prototype.off = function(t, e) {
        return this.each(function(n, r) {
            r.detachEvent ? r.detachEvent("on" + t, e) : r.removeEventListener(t, e, !1)
        })
    }
    ,
    t.lay = r,
    t.layui && layui.define && layui.define(function(t) {
        t(e, r)
    })
}(window, window.document);