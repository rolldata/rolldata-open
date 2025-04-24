;layui.define(["wdUtil", 'admin', "table", "form", "laydate", "eleTree", 'notice', 'pscrollbar'], function () {
    var $ = layui.jquery,
        element = layui.element,
        setter = layui.setter,
        device = layui.device(),
        myUtil = layui.wdUtil,
        pscrollbar = layui.pscrollbar;
    var icons = {
        '数据采集': setter.basePath+'static/images/nav/shujufenxi.png',
        '数据查询': setter.basePath+'static/images/nav/shujuchaxun.png',
        '上报办理': setter.basePath+'static/images/nav/shangbaobanli.png',
        '财务分析': setter.basePath+'static/images/nav/caiwufenxi.png',
        '销售分析': setter.basePath+'static/images/nav/xiaoshoufenxi.png',
        '企业报表': setter.basePath+'static/images/nav/qiyebaobiao.png',
        '我的办理': setter.basePath+'static/images/nav/wodemulu.png',
        '数据源管理': setter.basePath+'static/images/nav/shujuyuanguanli.png',
        '模型管理': setter.basePath+'static/images/nav/moxingguanli.png',
        '表单设计': setter.basePath+'static/images/nav/biaodansheji.png',
        '配置管理': setter.basePath+'static/images/nav/peizhiguanli.png',
        '自定义1': setter.basePath+'static/images/nav/caiwufenxi.png',
        '自定义2': setter.basePath+'static/images/nav/xiaoshoufenxi.png',
        '自定义3': setter.basePath+'static/images/nav/qiyebaobiao.png',
        '自定义4': setter.basePath+'static/images/nav/wodemulu.png'
    },
    iconIndex = 1;

    function getNav(){
        var url = setter.basePath + 'functionController/queryUserAvailable',
        callback = function(data){
            if(data){
                var wrap = $('.DSwrap'),
                temp = [];
                if(data.obj.treeNodes && data.obj.treeNodes.length){
					layui.sessionData("userAvailable", {
						key: 'navs',
						value: data.obj.treeNodes
					});
                    data.obj.treeNodes.forEach(function(v, i){
                        var icon = icons[v.name];
                        if(!icon){
                            icon = icons['自定义'+iconIndex];
                            iconIndex += 1;
                            if(iconIndex > 4) iconIndex = 1;
                        }
                        temp.push('<div class="nav-item-box"><div class="nav-item" data-type="'+v.hrefType+'" data-path="'+v.hrefLink+'" data-name="'+v.name+'" data-funcid="'+v.funcId+'" data-id="'+v.id+'"><div class="nav-icon nav-icon-1" style="background-image:url('+icon+');"></div><div class="nav-txt">'+v.name+'</div></div></div>');
                    });
                }
                wrap.html(temp.join(''));
                event();
                resize();
            }
        };
        myUtil.send(url, {parentId:''}, callback);
    }
    function event(){
        $('.nav-item').off('click').on('click', function(){
            var item = $(this),
            items = $(".nav-item"),
            path = item.attr("data-path");
			if(path){
				layui.admin.loadMoreSide(item, true);
			}
        })
    }
    function resize(){
        var width = $(".DSwrap").width(),
        box = $(".nav-item-box");
        if(width > 1500){
            box.css('width', '20%');
        }else if(width < 1500 && width > 1000){
            box.css('width', '25%');
        }else if(width < 1000){
            box.css('width', '33.3%');
        }
    }
    !function (){
        getNav();
        $(".weatherWrap").each(function (i, v) {
            if (!$(v).hasClass("ps")) {
                new pscrollbar(v);
            }
        });
        $(document).resize(function(){resize()});
    }()
    // exports("homePage_DS", {})
})