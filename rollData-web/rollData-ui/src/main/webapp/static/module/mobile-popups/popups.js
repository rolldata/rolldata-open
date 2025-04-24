layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;

    var PLUGIN_NAME = 'iziToast';
    var mpopups = {};
    var supports = !!document.querySelector; // Feature test
    var isMobile = (/Mobi/.test(navigator.userAgent)) ? true : false;
    var mobileWidth = 568;
    var config = {};
	/*alert弹出层*/
	mpopups.jqalert = function jqalert(param) {
		var title = param.title,
			content = param.content,
			yestext = param.yestext,
			notext = param.notext,
			yesfn = param.yesfn,
			nofn = param.nofn,
			nolink = param.nolink,
			yeslink = param.yeslink,
			prompt = param.prompt,
			click_bg = param.click_bg;

		if (click_bg === undefined){
			click_bg = true;
		}
		if (yestext === undefined){
			yestext = '确认';
		}
		if (!nolink){
			nolink = 'javascript:void(0);';
		}
		if (!yeslink){
			yeslink = 'javascript:void(0);';
		}

		var htm = '';
		htm +='<div class="jq-alert" id="jq-alert"><div class="alert">';
		if(title) htm+='<h2 class="title">'+title+'</h2>';
		if (prompt){
			htm += '<div class="content"><div class="prompt">';
			htm += '<p class="prompt-content">'+prompt+'</p>';
			htm += '<input type="text" class="prompt-text"></div>';
			htm +='</div>';
		}else {
			htm+='<div class="content">'+content+'</div>';
		}
		if (!notext){
			htm+='<div class="fd-btn"><a href="'+yeslink+'" class="confirm" id="yes_btn">'+yestext+'</a></div>'
			htm+='</div>';
		}else {
			htm+='<div class="fd-btn">'+
				'<a href="'+nolink+'"  data-role="cancel" class="cancel">'+notext+'</a>'+
				'<a href="'+yeslink+'" class="confirm"  id="yes_btn">'+yestext+'</a>'+
				'</div>';
			htm+='</div>';
		}
		$('body').append(htm);
		var al = $('#jq-alert');
		al.on('click','[data-role="cancel"]',function () {
			al.remove();
			if (nofn){
				param.nofn();
				nofn = '';
			}
			param = {};
		});
		$(document).delegate('.alert','click',function (event) {
			event.stopPropagation();
		});
		$(document).delegate('#yes_btn','click',function () {
			setTimeout(function () {
				al.remove();
			},300);
			if (yesfn){
				param.yesfn();
				yesfn ='';
			}
			param = {};
		});
		if(click_bg === true){
			$(document).delegate('#jq-alert','click',function () {
				setTimeout(function () {
					al.remove();
				},300);
				yesfn ='';
				nofn = '';
				param = {};
			});
		}

	}
	/*toast 弹出提示*/
	mpopups.jqtoast = function jqtoast(text,sec) {
		var _this = text;
		var this_sec = sec;
		var htm = '';
		htm += '<div class="jq-toast" style="display: none;">';
		if (_this){
			htm +='<div class="toast">'+_this+'</div></div>';
			$('body').append(htm);
			$('.jq-toast').fadeIn();

		}else {
			jqalert({
				title:'提示',
				content:'提示文字不能为空',
				yestext:'确定'
			})
		}
		if (!sec){
			this_sec = 2000;
		}
		setTimeout(function () {
			$('.jq-toast').fadeOut(function () {
				$(this).remove();
			});
			_this = '';
		},this_sec);
	}
	layui.link(layui.cache.base + 'mobile-popups/style.css');
	
	exports('mpopups', mpopups);
})