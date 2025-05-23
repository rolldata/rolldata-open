﻿/** EasyWeb iframe v3.1.1 data:2019-03-24 License By http://easyweb.vip */

layui.define(["jquery"], function(a) {
	var d = layui.jquery;
	var e = "hide-print";
	var c = "printing";
	var b = {
		print: function(h) {
			window.focus();
			if (!h) {
				h = {}
			}
			var j = h.hide;
			var g = h.horizontal;
			var m = h.iePreview;
			var o = h.close;
			var i = h.blank;
			if (m == undefined) {
				m = true
			}
			if (i == undefined && window != top && m && b.isIE()) {
				i = true
			}
			d("#page-print-set").remove();
			var f = '<div id="page-print-set">';
			f += '      <style type="text/css" media="print">';
			if (g) {
				f += "     @page { size: landscape; }"
			} else {
				f += "     @page { size: portrait; }"
			}
			f += "      </style>";
			if (m && b.isIE()) {
				f += '  <object id="WebBrowser" classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0"></object>';
				f += "  <script>WebBrowser.ExecWB(7, 1);";
				if (o) {
					f += "  window.close();"
				}
				f += "  <\/script>"
			}
			f += "   </div>";
			b.hideElem(j);
			var k, n;
			if (i) {
				k = window.open("", "_blank");
				n = k.document;
				k.focus();
				var l = document.getElementsByTagName("html")[0].innerHTML;
				l += f;
				n.open();
				n.write(l);
				n.close()
			} else {
				k = window;
				d("body").append(f)
			}(m && b.isIE()) || k.print();
			b.showElem(j)
		},
		printHtml: function(h) {
			if (!h) {
				h = {}
			}
			var j = h.html;
			var i = h.blank;
			var f = h.print;
			var o = h.close;
			var g = h.horizontal;
			var l = h.iePreview;
			if (f == undefined) {
				f = true
			}
			if (l == undefined) {
				l = true
			}
			if (b.isIE() && i == undefined) {
				i = true
			}
			if (o == undefined) {
				o = true;
				if (l && i && b.isIE()) {
					o = false
				}
			}
			var k, n;
			if (i) {
				k = window.open("", "_blank");
				n = k.document
			} else {
				var m = document.getElementById("printFrame");
				if (!m) {
					d("body").append('<iframe id="printFrame" style="display: none;"></iframe>');
					m = document.getElementById("printFrame")
				}
				k = m.contentWindow;
				n = m.contentDocument || m.contentWindow.document
			}
			k.focus();
			if (j) {
				j += ("<style>" + b.getCommonCss(true) + "</style>");
				j += '<style type="text/css" media="print">';
				if(h.printNotlayout){

				}else{
					if (g) {
						j += "@page { size: landscape; }"
					} else {
						j += "@page { size: portrait; }"
					}
				}
				j += "</style>";
				if (l && b.isIE()) {
					j += '<object id="WebBrowser" classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0"></object>';
					j += "<script>WebBrowser.ExecWB(7, 1);";
					if (o) {
						j += "window.close();"
					}
					j += "<\/script>"
				}
				n.open();
				n.write(j);
				n.close()
			}
			if (f) {
				(l && b.isIE()) || k.print();
				if (i && o && (!(l && b.isIE()))) {
					k.close()
				}
			}
			return k
		},
		printPage: function(k) {
			if (!k) {
				k = {}
			}
			var r = k.htmls;
			var h = k.style;
			var j = k.horizontal;
			var p = k.padding;
			var m = k.blank;
			var f = k.print;
			var t = k.close;
			var v = k.debug;
			var o = k.iePreview;
			if (f == undefined) {
				f = true
			}
			if (o == undefined) {
				o = true
			}
			if (b.isIE() && m == undefined) {
				m = true
			}
			if (t == undefined) {
				t = true;
				if (o && m && b.isIE()) {
					t = false
				}
			}
			if (b.isEdge() || b.isFirefox()) {
				p = undefined
			}
			var n, s;
			if (m) {
				n = window.open("", "_blank");
				s = n.document
			} else {
				var q = document.getElementById("printFrame");
				if (!q) {
					d("body").append('<iframe id="printFrame" style="display: none;"></iframe>');
					q = document.getElementById("printFrame")
				}
				n = q.contentWindow;
				s = q.contentDocument || q.contentWindow.document
			}
			n.focus();
			var g = '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>打印窗口</title>';
			h && (g += h);
			g += "<style>";
			g += "body {";
			g += "    margin: 0 !important;";
			g += "} ";
			g += ".print-page .print-page-item {";
			g += "    width: 793px !important;";
			g += "    height: 1122px !important;";
			g += "    padding: " + (p ? p : "55px") + " !important;";
			g += "    box-sizing: border-box !important;";
			g += "    overflow: hidden !important;";
			g += "    border: none !important;";
			g += "} ";
			g += ".print-page.page-horizontal .print-page-item {";
			g += "    width: 1122px !important;";
			g += "    height: 792px !important;";
			g += "} ";
			g += ".print-page.page-debug .print-page-item {";
			g += "    border: 1px solid red !important;";
			g += "} ";
			g += ".print-page.padding-default .print-page-item {";
			g += "    width: 713px !important;";
			g += "    height: 1042px !important;";
			g += "    padding: 0 !important;";
			g += "} ";
			g += ".print-page.padding-default.page-horizontal .print-page-item {";
			g += "    width: 1042px !important;";
			g += "    height: 712px !important;";
			g += "} ";
			g += ".print-page.padding-default.page-ie .print-page-item {";
			g += "    width: 645px !important;";
			g += "    height: 977px !important;";
			g += "} ";
			g += ".print-page.padding-default.page-ie.page-horizontal .print-page-item {";
			g += "    width: 978px !important;";
			g += "    height: 648px !important;";
			g += "} ";
			g += ".print-page.padding-default.page-firefox .print-page-item {";
			g += "    width: 720px !important;";
			g += "    height: 955px !important;";
			g += "} ";
			g += ".print-page.padding-default.page-firefox.page-horizontal .print-page-item {";
			g += "    width: 955px !important;";
			g += "    height: 720px !important;";
			g += "} ";
			g += b.getCommonCss(true);
			g += "</style>";
			g += '<style type="text/css" media="print">';
			if (j) {
				g += "@page { size: landscape; }"
			} else {
				g += "@page { size: portrait; }"
			}
			g += "</style>";
			g += "</head><body>";
			if (r && (r instanceof Array)) {
				var u = j ? " page-horizontal" : "";
				u += p == undefined ? " padding-default" : "";
				u += v ? " page-debug" : "";
				if (b.isIE() || b.isEdge()) {
					u += " page-ie"
				} else {
					if (b.isFirefox()) {
						u += " page-firefox"
					}
				}
				g += '<div class="print-page' + u + '">';
				for (var l = 0; l < r.length; l++) {
					g += '<div class="print-page-item">';
					g += r[l];
					g += "</div>"
				}
				g += "</div>"
			}
			if (o && b.isIE()) {
				g += '<object id="WebBrowser" classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0"></object>';
				g += "<script>WebBrowser.ExecWB(7, 1);";
				if (t) {
					g += "window.close();"
				}
				g += "<\/script>"
			}
			g += "</body></html>";
			s.open();
			s.write(g);
			s.close();
			if (f) {
				(o && b.isIE()) || n.print();
				if (m && t && (!(o && b.isIE()))) {
					n.close()
				}
			}
			return n
		},
		hideElem: function(f) {
			d("." + e).addClass(c);
			if (!f) {
				return
			}
			if (f instanceof Array) {
				for (var g = 0; g < f.length; g++) {
					d(f[g]).addClass(c)
				}
			} else {
				d(f).addClass(c)
			}
		},
		showElem: function(f) {
			d("." + e).removeClass(c);
			if (!f) {
				return
			}
			if (f instanceof Array) {
				for (var g = 0; g < f.length; g++) {
					d(f[g]).removeClass(c)
				}
			} else {
				d(f).removeClass(c)
			}
		},
		getCommonCss: function(g) {
			var f = ("." + e + "." + c + " {");
			f += "        display: none !important;";
			f += "   }";
			f += "   .print-table {";
			f += "        border: none;";
			f += "        border-collapse: collapse;";
			f += "        width: 100%;";
			f += "   }";
			f += "   .print-table td, .print-table th {";
			f += "        color: #333;";
			f += "        padding: 9px 15px;";
			f += "        word-break: break-all;";
			f += "        border: 1px solid #666;";
			f += "   }";
			if (g) {
				f += ("." + e + " {");
				f += "     display: none !important;";
				f += "}"
			}
			return f
		},
		makeHtml: function(j) {
			var i = j.title;
			var g = j.style;
			var f = j.body;
			if (i == undefined) {
				i = "打印窗口"
			}
			var h = '<!DOCTYPE html><html lang="en">';
			h += '    <head><meta charset="UTF-8">';
			h += ("        <title>" + i + "</title>");
			g && (h += g);
			h += "   </head>";
			h += "   <body>";
			f && (h += f);
			h += "   </body>";
			h += "   </html>";
			return h
		},
		isIE: function() {
			if ( !! window.ActiveXObject || "ActiveXObject" in window) {
				return true
			} else {
				return false
			}
		},
		isEdge: function() {
			return navigator.userAgent.indexOf("Edge") != -1
		},
		isFirefox: function() {
			return navigator.userAgent.indexOf("Firefox") != -1
		}
	};
	d("head").append("<style>" + b.getCommonCss() + "</style>");
	a("printer", b)
});