;layui.define(["wdUtil", 'admin', "table", "form", "laydate", "eleTree", 'notice', 'pscrollbar'], function () {
    var $ = layui.jquery,
        element = layui.element,
        setter = layui.setter,
        device = layui.device(),
        form = layui.form,
        myUtil = layui.wdUtil,
        pscrollbar = layui.pscrollbar,
        cockpit = 0,
        mobile = 0,
        largesize = 0,
        reportform = 0,
        simpleReportform = 0,
        reportformList = 0,
        multidimensionalDesignIndex = 0,
        icons = [
            setter.basePath+'static/images/console/xtgl.png',
            setter.basePath+'static/images/console/sjjm.png',
            setter.basePath+'static/images/console/sjsb.png',
            setter.basePath+'static/images/console/sjjgc.png',
            setter.basePath+'static/images/console/pzcl.png',
            setter.basePath+'static/images/console/pzcl.png'
        ],
    iconIndex = 1;

    function getNav(){
        var url = setter.basePath + 'functionController/queryUserAvailableConsole',
        callback = function(data){
            if(data){
                var wrap = $('.DSwrap'),
                temp = [],
                temp1 = [];
                if(data.obj.treeNodes && data.obj.treeNodes.length){
					layui.sessionData("userAvailable1", {
						key: 'navs1',
						value: data.obj.treeNodes
					});
                    data.obj.treeNodes.forEach(function(v, i){
                        temp.push('<div class="cols"><img src="'+icons[i]+'"/><p>'+v.name+'</p> <div class="colsdiv"></div></div>')

                    });
                 
                    wrap.html(temp.join(''));
                    data.obj.treeNodes.forEach(function(v, i){
                        temp1=[]
                        if($('.cols p').eq(i).text()==v.name){
                            if(v.children!=null && v.children.length>0){
                                for(var z=0;z<v.children.length;z++){
                                    temp1.push('<div class="clickdiv" data-type="'+v.children[z].hrefType+'" data-path="'+v.children[z].hrefLink+'" data-name="'+v.children[z].name+'" data-funcid="'+v.children[z].funcId+'" data-id="'+v.children[z].id+'"><div class="divtext">'+v.children[z].name+'</div></div>');
                                 }
                            }
                            $('.cols .colsdiv').eq(i).html(temp1)
                        }

                    });
                   
                }
               
                event();
                // resize();
               
            }
        };
        myUtil.send(url, {parentId:''}, callback);
    }
    function event(){
        var content = '<form action="" class="layui-form" id="LAY-admin-addreport" lay-filter="LAY-admin-addreport">'
					+'<div class="layui-form-item layui-form-item-sm">'
					+    '<label for="" class="layui-form-label"><i style="color: red;margin-right: 5px;">*</i>页面名称：</label>'
					+    '<div class="layui-input-block">'
					+        '<input type="text" name="rname" placeholder="请输入" autocomplete="off" lay-vertype="tips" lay-verify="required" lay-filter="foldername" class="layui-input">'
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
        $('.clickdiv').off('click').on('click', function(){
           
            var item = $(this),
            items = $("clickdiv"),
            path = item.attr("data-path");
            if(item.attr("data-id")=='4028b88169d695860169d6975a8f0020'){   //驾驶舱
                cockpit=cockpit+1
                window.open(setter.basePath +'biController/viewVisualization?cid=&id=0&pid=0&type=0&name=驾驶舱'+cockpit);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0021'){  //移动端
                mobile=mobile+1
                window.open(setter.basePath +'biController/viewMobileVisualization?cid=&id=0&pid=0&type=4&name=移动端'+mobile);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0022'){   //数字大屏
                largesize=largesize+1
                window.open(setter.basePath +'biController/viewBigScreenVisualization?cid=&id=0&pid=0&type=5&name=大屏'+largesize);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0023'){   //企业报表
                reportform=reportform+1
                window.open(setter.basePath +'dataReportController/dataReportList_new?row=10&col=10&type=1&name=报表'+reportform);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0030'){   //企业报表
                simpleReportform=simpleReportform+1
                window.open(setter.basePath +'simpleDataReportController/simpleDataReportList?type=1&name=简易报表'+simpleReportform);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0031'){   //企业报表
                reportformList=reportformList+1
                window.open(setter.basePath +'dataReportController/dataReportGroupList?name=报表组'+reportformList);
            }else if(item.attr("data-id")=='4028b88169d695860169d6975a8f0032'){   //多维报表
                multidimensionalDesignIndex =multidimensionalDesignIndex + 1;
                window.open(setter.basePath +'multidimensionalController/design?name=多维报表'+multidimensionalDesignIndex );
            }else{
                var url = path.indexOf("http:") > -1 ? path+"?navname="+item.attr('data-name') : setter.basePath +path+"?navname="+item.attr('data-name');
                top.layui.index.loadView({
                    menuId: url,
                    menuPath: url,
                    menuName: item.attr('data-name')
                })
            }
          
            
			// if(path){
			// 	layui.admin.loadMoreSide(item, true);
			// }
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
        // $(document).resize(function(){resize()});
    }()
   
})