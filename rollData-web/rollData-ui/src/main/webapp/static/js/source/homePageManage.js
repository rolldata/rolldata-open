;
layui.define(["wdUtil", 'admin', "table", "form", "eleTree", 'notice', 'setter', 'jquery', 'contextMenu', 'pscrollbar'], function(
	exports) {
	var $ = layui.jquery,
		laytpl = layui.laytpl,
		element = layui.element,
		setter = layui.setter,
		device = layui.device(),
		table = layui.table,
		form = layui.form,
		myUtil = layui.wdUtil,
		cxt = layui.contextMenu,
		eleTree = layui.eleTree,
		tableData = '',
		pscrollbar = layui.pscrollbar,
		treeId = "LAY-role-tree",
		storeTableName = 'homePageManage',
		roleArr = '',
		eleTreeObjList = {},
		daobj = '',
		isSm = $('body').hasClass("layui-layout-sm-body"),
		pageInfo = '',
		pageInfomob = '',
		tabchangeindex=0,
		userTreeNodes = '',
		userTreeNodes1 = '',
		userTreeNodesmob='',
		userTreeNodesmob1='',
		pcsourcename='',
		pcid=[],
		pcid1=[],
		themeObj = {
			1: "dark",
			2: "light",
			3: "realdark",
		},
		layoutObj = {
			1: "classical",
			2: "doublerow",
			3: "top",
		},
		themeColors = [
			{title: "薄暮", color: 'F5222D'},
			{title: "火山", color: 'FA541C'},
			{title: "胭脂粉", color: 'EB2F96'},
			{title: "日暮", color: 'FAAD14'},
			{title: "明青", color: '13C2C2'},
			{title: "极光绿", color: '52C41A'},
			{title: "深绿", color: '009688'},
			{title: "拂晓蓝(默认)", color: '1677FF'},
			{title: "极客蓝", color: '2F54EB'},
			{title: "酱紫", color: '722ED1'},
			{title: "主题黑", color: '001529'},
		],
		AllowImgFileSize = 5242880, //上传图片最大值(单位字节)（ 2 M = 2097152 B ）超过2M上传失败
		NewProcess =
		'<div action="" class="layui-form" lay-filter="createItem-form" autocomplete="off" style="padding:15px;">' +
		' <div class="layui-form-item layui-form-item-sm resourceName">' +
		' <label for="resourceName" class="layui-form-label">资源名称：</label>' +
		' <div class="layui-input-block">' +
		' <input type="text" name="resourceName" id="resourceName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="资源名称">' +
		' </div>' +
		' </div>' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
		'<label  class="layui-form-label">资源选择：</label>' +
		'<div class="layui-input-block">' +
		'<input type="text" name="choiceResource" required="" lay-verify="required" placeholder="请选择资源" readonly=""  lay-verType="tips" autocomplete="off" class="layui-input" id="selectResource"  style= "cursor: pointer;">' +
		'</div>' +
		' </div>' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
		'<label  class="layui-form-label">图片上传：</label>' +
		'<div class="layui-input-block">' +
		'<div class="upload-files">' +
		'<form  id="upload-form"  method="post"  enctype="multipart/form-data">' +
		'<input class="file_input" name="uploadImgFile" type="file" id="uploadImgFile" style= "cursor: pointer; height:30px" >' +
		'<input type="hidden" id="biName" name="biName" class="layui-input" required lay-verType="tips" lay-verify="required"/>' +
		'<input type="hidden" id="resourceId" name="resourceId" />' +
		'<input type="hidden" id="isReUpload" name="isReUpload" value="0"/>' +
		'</form>' +
		'<div class="PictureUpload"></div>' +
		'<div class="upload-tips">请上传16/9比例且不大于2M图片，例如320*180</div>' +
		'</div>'+
		'</div>' +
		' </div>' +
		' </div>',
		editProcess =
		'<div action="" class="layui-form" lay-filter="createItem-form" autocomplete="off" style="padding:15px;">' +
		' <div class="layui-form-item layui-form-item-sm resourceName">' +
		' <label for="resourceName" class="layui-form-label">资源名称：</label>' +
		' <div class="layui-input-block">' +
		' <input type="text" name="resourceName" id="resourceName" class="layui-input" required lay-verType="tips" lay-verify="required" placeholder="资源名称">' +
		' </div>' +
		' </div>' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
		'<label  class="layui-form-label">资源选择：</label>' +
		'<div class="layui-input-block">' +
		'<input type="text" name="choiceResource" required="" lay-verify="required" placeholder="请选择资源" readonly=""  lay-verType="tips" autocomplete="off" class="layui-input" id="selectResource"  style= "cursor: pointer;">' +
		'</div>' +
		' </div>' +
		'<form  id="upload-form-edit"  method="post"  enctype="multipart/form-data">' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
		'<label  class="layui-form-label">图片上传：</label>' +
		'<div class="layui-input-block">' +
		'<div class="upload-file">'
		// +'<input class="file_input" name="uploadImgFile" type="file" id="uploadImgFile" style= "cursor: pointer;" >'
		+
		'<input type="hidden" id="biName" name="biName" class="layui-input" required lay-verType="tips" lay-verify="required"/>' +
		'<input type="hidden" id="resourceId" name="resourceId" />' +
		'<input type="hidden" id="isReUpload" name="isReUpload" value="0"/>' +
		'<input type="hidden" id="biId" name="biId"/>' +
		'<div class="PictureUpload"></div>  ' +
		'<div class="upload-tips">请上传16/9比例且不大于2M图片，例如320*180</div>' +
		'<input class="file_input" name="uploadImgFile" type="file" id="uploadImgFile" style= "top:0;" >' +
		'<div class="ReUpload">重新上传</div>' +
		'</div>' +
		'</div>' +
		'</form>' +
		' </div>' +
		' </div>',

		leftresourceselpc =
		'<div action="" class="layui-form" lay-filter="createItem-form1" autocomplete="off" style="padding:15px;">' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
			'<label  class="layui-form-label">资源选择：</label>' +
			'<div class="layui-input-block">' +
				'<input type="text" name="choiceResource" required="" lay-verify="required" placeholder="请选择资源" readonly=""  lay-verType="tips" autocomplete="off" data-ids="" class="layui-input" id="selectResourcepc"  style= "cursor: pointer;">' +
			'</div>' +
		' </div>' +	
		' </div>',
		leftresourceselmob =
		'<div action="" class="layui-form" lay-filter="createItem-form1" autocomplete="off" style="padding:15px;">' +
		' <div class="layui-form-item layui-form-item-sm selectRes">' +
			'<label  class="layui-form-label">资源选择：</label>' +
			'<div class="layui-input-block">' +
				'<input type="text" name="choiceResource" required="" lay-verify="required" placeholder="请选择资源" readonly=""  lay-verType="tips" autocomplete="off" data-ids="" class="layui-input" id="selectResourcemob"  style= "cursor: pointer;">' +
			'</div>' +
		' </div>' +	
		' </div>';
	
    //    PC端
		function renderInfo(){
			var url = setter.basePath + "homePageController/queryHomepageManageInfo",
			callback = function(data){
				if(data.success){
					pageInfo = myUtil.cloneObj(data.obj);
					if(pageInfo.homeResource==null){
						pageInfo.homeResource={
							roleId:[]
						}
					}
					// editdatasources(pageInfo.homeResource.resourceId)
					var tabpageInfo=myUtil.cloneObj(data.obj)
					pcid=tabpageInfo.biPage.concat(tabpageInfo.defaultPage).concat(tabpageInfo.dsPage).concat(tabpageInfo.simplePage).concat(tabpageInfo.homeResource.roleId).concat(tabpageInfo.systemPage);
					if(pageInfo && pageInfo.appearanceConfig){
						form.val("portalInfo-form", {
							loginTitle: pageInfo.appearanceConfig.loginTitle,
							title: pageInfo.appearanceConfig.title,
							topBarUsrThemeColor: pageInfo.appearanceConfig.topBarUsrThemeColor == "1" ? true : false,
							topBarThemeColorBanner: pageInfo.appearanceConfig.topBarThemeColorBanner == "1" ? true : false,
							isOpenResourceWatermark: pageInfo.appearanceConfig.isOpenResourceWatermark == "1" ? true : false,
						})
						$(".portalItem-box").removeClass("active");
						$(".themeItem-box").removeClass("active");
						$(".xnPortalStting").addClass("layui-hide");
						if(pageInfo.appearanceConfig.portalType == "2"){
							$(".newPortal").addClass("active");
						}else if(pageInfo.appearanceConfig.portalType == "3"){
							$(".multifnPortal").addClass("active");
						}else if(pageInfo.appearanceConfig.portalType == "4"){
							$(".xnPortal").addClass("active");
							$(".xnPortalStting").removeClass("layui-hide");
							if(pageInfo.appearanceConfig.pageLayout == "3"){
								$(".topbarTheme-box").addClass("layui-hide");
							}else{
								$(".topbarTheme-box").removeClass("layui-hide");
							}
						}else{
							$(".oldPortal").addClass("active");
						}
						if(pageInfo.appearanceConfig.pageLayout){
							$(".layoutItemBox .themeItem-box."+layoutObj[pageInfo.appearanceConfig.pageLayout]+"-layout").addClass("active");
						}
						if(pageInfo.appearanceConfig.protalStyle){
							$(".themeItemBox .themeItem-box."+themeObj[pageInfo.appearanceConfig.protalStyle]+"-theme").addClass("active");
						}
						if(pageInfo.appearanceConfig.themeColor){
							$('.themeColor-box[data-color="'+pageInfo.appearanceConfig.themeColor+'"]').addClass("active");
						}
						if(pageInfo.appearanceConfig.isInitLoginLogo == "0" && pageInfo.appearanceConfig.loginLogo){
							$(".loginLogo-box").css({"background-image": "url("+setter.basePath+"static/images/upload/"+pageInfo.appearanceConfig.loginLogo+")"})
						}else{
							$(".loginLogo-box").css({"background-image": "url("+setter.basePath+"static/images/logo.png)"})
						}
						if(pageInfo.appearanceConfig.isInitLogo == "0" && pageInfo.appearanceConfig.logo){
							$(".logo-box").css({"background-image": "url("+setter.basePath+"static/images/upload/"+pageInfo.appearanceConfig.logo+")"})
						}else{
							$(".logo-box").css({"background-image": "url("+setter.basePath+"static/images/logo_portal.png)"})
						}
						if(pageInfo.appearanceConfig.isInitLoginBackground == "0" && pageInfo.appearanceConfig.loginBackground){
							$(".uploadbgbox").css({"background-image": "url("+setter.basePath+"static/images/upload/"+pageInfo.appearanceConfig.loginBackground+")"})
						}else{
							$(".uploadbgbox").css({"background-image": "url("+setter.basePath+"static/images/bg_dly.png)"})
						}
						form.render(null, "portalInfo-form");
						form.on('switch(switchTest)', function (obj) {
							if (obj.elem.checked == true) {
								pageInfo.appearanceConfig.isShow = "1";
							} else {
								pageInfo.appearanceConfig.isShow = "0";
							}
						});
						form.on('switch(isOpenResourceWatermarkCox)', function (obj) {
							if (obj.elem.checked == true) {
								pageInfo.appearanceConfig.isOpenResourceWatermark = "1";
							} else {
								pageInfo.appearanceConfig.isOpenResourceWatermark = "0";
							}
						});
						form.on('switch(topBarThemeColorBannerCox)', function (obj) {
							if (obj.elem.checked == true) {
								pageInfo.appearanceConfig.topBarThemeColorBanner = "1";
							} else {
								pageInfo.appearanceConfig.topBarThemeColorBanner = "0";
							}
						});
						form.on('switch(topBarThemeColorBannerCox)', function (obj) {
							if (obj.elem.checked == true) {
								pageInfo.appearanceConfig.topBarThemeColorBanner = "1";
							} else {
								pageInfo.appearanceConfig.topBarThemeColorBanner = "0";
							}
						});
					}
				}
				layui.admin.removeLoading('body', true, true);
			}
			myUtil.send(url, {}, callback);
		}
		function rendertrees(treeNodes){
			var temp = [];
			if(treeNodes && treeNodes.length){
				treeNodes.forEach(function(v, i){
					temp.push('<li class="content" data-name="'+v.name+'" data-id="' + v.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + v.name + '</li>')
				})
			}
			$(".authorityTable .userlist").html(temp.join(''));
			eventUserItem();
		}
		function renderTree(treeNodes){
			$(".authorityTable .userlist").html('');
			
			var url = setter.basePath + "roleController/queryAll",
			callback = function(data){
				if(data.success){
					var temp = [];
					var tabid=[]
					
					userTreeNodes = myUtil.cloneObj(data.obj.treeNodes);
					userTreeNodes1=myUtil.cloneObj(data.obj.treeNodes);
					for(var a=0;a<pcid.length;a++){
						for(var c=0;c<userTreeNodes1.length;c++){
							if(userTreeNodes1[c].id==pcid[a]){
								userTreeNodes1.splice(c,1)
							}
						}
						
					}
					if(userTreeNodes1 && userTreeNodes1.length){
						userTreeNodes1.forEach(function(v, i){
							temp.push('<li class="content" data-name="'+v.name+'" data-id="' + v.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + v.name + '</li>')
						})
					}
					$(".authorityTable .userlist").html(temp.join(''));
					$(".authorized-tab").eq(0).trigger('click')
					eventUserItem();
					// renderPageUser($(".authorized-tab.active").attr('data-page'));
					
					
				}
			}
			if(!treeNodes){
				renderInfo();
				layui.admin.showLoading('body', 2, '.8')
				setTimeout(function(){
					myUtil.send(url, {}, callback);
				},300)
				
				
			}else{
				callback({success:true, obj: treeNodes});
			}
		}
		function eventUserItem(){
			$(".authorityTable .content").off('click').on('click', function(){
				var el = $(this),
				id = el.attr('data-id'),
				name = el.attr('data-name'),
				page = $(".authorized-tab.active").attr('data-page');
				if(page=='homeResource'){
					if(pageInfo && pageInfo[page] && pageInfo[page].roleId.indexOf(id) < 0){
						pageInfo[page].roleId.push(id);
						
						addPageUser({name: name, id: id}, page);
					}
					
				}else{
					if(pageInfo && pageInfo[page] && pageInfo[page].indexOf(id) < 0){
						pageInfo[page].push(id);
						addPageUser({name: name, id: id}, page);
					}
				}
				el.remove()
				userTreeNodes1 = userTreeNodes1.filter(item => item.id !== id)
				
			})
		}
		
		function eventItemTab() {
			$(".authorized-tab").off("click").on("click", function() {
				var item = $(this),
					type = item.attr("data-page");
					
				if (!item.hasClass("active")){
					item.siblings().removeClass('active').end().addClass('active');
					if(type=='homeResource'){
						$('.resourcesetpc').css('display','block')
					}else{
						$('.resourcesetpc').css('display','none')
					}
					$(".formbody").html('');
					renderPageUser(type);
				} 
			});
		}
		function renderThemeColor(){
			if(themeColors && themeColors.length){
				var strs = [];
				themeColors.forEach(function(v, i){
					strs.push('<div class="layui-inline themeColor-box" title="'+v.title+'" style="background-color: #'+v.color+';" data-color="'+v.color+'"><i class="layui-icon layui-icon-ok"></i></div>')
				})
				$(".themeColorBox").html(strs.join(""));
			}
		}
		
		$('.resourcesetpc').off("click").on("click", function() {
			layui.admin.open({
				title: "资源配置",
				content: leftresourceselpc,
				btn: ['确认', '取消'],
				success: function(layero, index) {
					form.render();
					$("#selectResourcepc").attr('data-ids',$('.resourcesetpc').attr('data-id'))
					$("#selectResourcepc").val($('.resourcesetpc').attr('data-name'))
					
				},
				yes: function(index, layero) {
					//按钮【按钮一】的回
					$('.resourcesetpc').attr('data-id',$("#selectResourcepc").attr('data-ids'))
					$('.resourcesetpc').attr('data-name',$("#selectResourcepc").val())
					pageInfo.homeResource.resourceId=$("#selectResourcepc").attr('data-ids')
					layer.close(index)
					
					
					return false;
				},
				btn2: function(index, layero) {
					//按钮【按钮二】的回调
				},
			})
		})
		function renderPageUser(type){
			if(type=='homeResource'){
				if(pageInfo && pageInfo[type] && pageInfo[type].roleId.length){
					pageInfo[type].roleId.forEach(function(v, i){
						for(var k = 0; k < userTreeNodes.length; k ++){
							if(v == userTreeNodes[k].id){
								addPageUser(userTreeNodes[k], type);
								break;
							}
						}
					})
				}
			}else{
				if(pageInfo && pageInfo[type] && pageInfo[type].length){
					pageInfo[type].forEach(function(v, i){
						for(var k = 0; k < userTreeNodes.length; k ++){
							if(v == userTreeNodes[k].id){
								addPageUser(userTreeNodes[k], type);
								break;
							}
						}
					})
				}
				
			}
		}
		function addPageUser(node, type){
			if(node){
				$(".formbody").append('<div class="pageuser-item" data-id="' + node.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + node.name + '<span data-id="'+node.id+'" data-page="'+type+'" class="layui-icon layui-icon-close"></span></div>')
			}
			eventUserItemClose();
		}
		function eventUserItemClose(){
			$(".formbody .pageuser-item .layui-icon-close").off("click").on("click", function(){
				var el = $(this),
				id = el.attr('data-id'),
				pagetype = el.attr('data-page');
				if(pagetype=='homeResource'){
					if(pageInfo[pagetype] && pageInfo[pagetype].roleId.indexOf(id) > -1){
						pageInfo[pagetype].roleId.splice(pageInfo[pagetype].roleId.indexOf(id), 1);
						el.parent().remove();
					}
				}else{
					if(pageInfo[pagetype] && pageInfo[pagetype].indexOf(id) > -1){
						pageInfo[pagetype].splice(pageInfo[pagetype].indexOf(id), 1);
						el.parent().remove();
					}
				}
				userTreeNodes1.push({ 
					id:id,
					name:el.parent().text()
				})
				rendertrees(userTreeNodes1)
				// userTreeNodes1
				
			})
		}

		// 移动端
		function renderInfomob(){
			var url = setter.basePath + "homePageController/queryTerminalHomepageManageInfo",
			callback = function(data){
				if(data.success){
					pageInfomob = myUtil.cloneObj(data.obj);
					if(pageInfomob.homeResource==null){
						pageInfomob.homeResource={
							roleId:[]
						}
					}
					var tabpageInfo=myUtil.cloneObj(data.obj)
					pcid1=tabpageInfo.biPage.concat(tabpageInfo.defaultPage).concat(tabpageInfo.dsPage).concat(tabpageInfo.simplePage).concat(tabpageInfo.homeResource.roleId).concat(tabpageInfo.systemPage)
				
				}
				layui.admin.removeLoading('body', true, true);
			}
			myUtil.send(url, {}, callback);
		}
		function renderTreemobs(treeNodes){
			var temp = [];
			if(treeNodes && treeNodes.length){
				treeNodes.forEach(function(v, i){
					temp.push('<li class="content" data-name="'+v.name+'" data-id="' + v.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + v.name + '</li>')
				})
			}
			$(".authorityTable1 .userlist").html(temp.join(''));
			eventUserItemmob();
		}
		function renderTreemob(treeNodes){
			$(".authorityTable1 .userlist").html('');
			
			var url = setter.basePath + "roleController/queryAll",
			callback = function(data){
				if(data.success){
						var temp = [];
						userTreeNodesmob = myUtil.cloneObj(data.obj.treeNodes);
						userTreeNodesmob1=myUtil.cloneObj(data.obj.treeNodes);
						for(var a=0;a<pcid1.length;a++){
							for(var c=0;c<userTreeNodesmob1.length;c++){
							
								if(userTreeNodesmob1[c].id==pcid1[a]){
									userTreeNodesmob1.splice(c,1)
								}
							}
							
						}
						if(userTreeNodesmob1 && userTreeNodesmob1.length){
							userTreeNodesmob1.forEach(function(v, i){
								temp.push('<li class="content" data-name="'+v.name+'" data-id="' + v.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + v.name + '</li>')
							})
						}
						$(".authorityTable1 .userlist").html(temp.join(''));
						$(".authorized-mob").eq(0).trigger('click')
						// renderPageUsermob($(".authorized-mob.active").attr('data-page'));
						eventUserItemmob();
						
					
					
				}
			}
			if(!treeNodes){
				renderInfomob();
				layui.admin.showLoading('body', 2, '.8')
				setTimeout(function(){
					myUtil.send(url, {}, callback);
				},300)
				
			}else{
				callback({success:true, obj: treeNodes});
			}
		}
		function eventUserItemmob(){
			$(".authorityTable1 .content").off('click').on('click', function(){
				var el = $(this),
				id = el.attr('data-id'),
				name = el.attr('data-name'),
				page = $(".authorized-mob.active").attr('data-page');
				if(page=='homeResource'){
					if(pageInfomob && pageInfomob[page] && pageInfomob[page].roleId.indexOf(id) < 0){
						pageInfomob[page].roleId.push(id);
						addPageUsermob({name: name, id: id}, page);
					}
				}else{
					if(pageInfomob && pageInfomob[page] && pageInfomob[page].indexOf(id) < 0){
						pageInfomob[page].push(id);
						addPageUsermob({name: name, id: id}, page);
					}
				}
				el.remove()
				userTreeNodesmob1 = userTreeNodesmob1.filter(item => item.id !== id)
			})
		}
		
		function eventItemTabmob() {
			$(".authorized-mob").off("click").on("click", function() {
				var item = $(this),
					type = item.attr("data-page");
				if (!item.hasClass("active")){
					item.siblings().removeClass('active').end().addClass('active');
					if(type=='homeResource'){
						$('.resourcesetmob').css('display','block')
					}else{
						$('.resourcesetmob').css('display','none')
					}
					$(".formbodymob").html('');
					renderPageUsermob(type);
				} 
			});
		}
		
		$('.resourcesetmob').off("click").on("click", function() {
			layui.admin.open({
				title: "资源配置",
				content: leftresourceselmob,
				btn: ['确认', '取消'],
				success: function(layero, index) {
					form.render();
					$("#selectResourcemob").attr('data-ids',$('.resourcesetmob').attr('data-id'))
					$("#selectResourcemob").val($('.resourcesetmob').attr('data-name'))
					
				},
				yes: function(index, layero) {
					//按钮【按钮一】的回
					$('.resourcesetmob').attr('data-id',$("#selectResourcemob").attr('data-ids'))
					$('.resourcesetmob').attr('data-name',$("#selectResourcemob").val())
					pageInfomob.homeResource.resourceId=$("#selectResourcemob").attr('data-ids')
					layer.close(index)
					
					
					return false;
				},
				btn2: function(index, layero) {
					//按钮【按钮二】的回调
				},
			})
		})
		function renderPageUsermob(type){
			if(type=='homeResource'){
				if(pageInfomob && pageInfomob[type] && pageInfomob[type].roleId.length){
					pageInfomob[type].roleId.forEach(function(v, i){
						for(var k = 0; k < userTreeNodesmob.length; k ++){
							if(v == userTreeNodesmob[k].id){
								addPageUsermob(userTreeNodesmob[k], type);
								break;
							}
						}
					})
				}
			}else{
				if(pageInfomob && pageInfomob[type] && pageInfomob[type].length){
					pageInfomob[type].forEach(function(v, i){
						for(var k = 0; k < userTreeNodesmob.length; k ++){
							if(v == userTreeNodesmob[k].id){
								addPageUsermob(userTreeNodesmob[k], type);
								break;
							}
						}
					})
				}
			}
			
		}
		function addPageUsermob(node, type){
			if(node){
				$(".formbodymob").append('<div class="pageuser-item" data-id="' + node.id + '"><i class="iconfont icon-user-list" style="color:#f90;padding-right:7px;"></i>' + node.name + '<span data-id="'+node.id+'" data-page="'+type+'" class="layui-icon layui-icon-close"></span></div>')
			}
			eventUserItemClosemob();
		}
		function eventUserItemClosemob(){
			$(".formbodymob .pageuser-item .layui-icon-close").off("click").on("click", function(){
				var el = $(this),
				id = el.attr('data-id'),
				pagetype = el.attr('data-page');
				if(pagetype=='homeResource'){
					if(pageInfomob[pagetype] && pageInfomob[pagetype].roleId.indexOf(id) > -1){
						pageInfomob[pagetype].roleId.splice(pageInfomob[pagetype].roleId.indexOf(id), 1);
						el.parent().remove();
					}
				}else{
					if(pageInfomob[pagetype] && pageInfomob[pagetype].indexOf(id) > -1){
						pageInfomob[pagetype].splice(pageInfomob[pagetype].indexOf(id), 1);
						el.parent().remove();
					}
				}
				userTreeNodesmob1.push({ 
					id:id,
					name:el.parent().text()
				})
				renderTreemobs(userTreeNodesmob1)
				
			})
		}
	
	

    // BI
	function initTable($data) {
		table.render({
			elem: "#reporProcessList",
			cellMinWidth: 80,
			height: "full-266",
			autoSort: false,
			cols: [
				[{
						type: "checkbox"
					},
					{
						field: "name",
						title: "名称",
						align: "center",
						event: 'setSign',
						unresize: true,
						sort: false,
						style: "font-size:12px"
					},
					{
						field: "resourceName",
						title: "资源名称",
						align: "center",
						event: 'setSign',
						unresize: true,
						sort: false,
						style: "font-size:12px"
					},
					{
						field: "resourceType",
						title: "类型",
						align: "center",
						templet: "#repType",
						unresize: true,
						sort: false,
						style: "font-size:12px"
					}, {
						field: "createUser",
						title: "操作人",
						align: "center",
						templet: "setSign",
						unresize: true,
						sort: false,
						style: "font-size:12px"
					}, {
						field: "operation",
						title: "操作",
						templet: "#userbar",
						align: "center",
						unresize: true,
						sort: false,
						style: "font-size:12px"
					}
				]
			],
			data: $data,
			skin: "nob",
			even: "true",
			limit: $data.length

		});
		table.on('checkbox(reporProcessList)', function(obj) {
			var checkStatus = table.checkStatus('reporProcessList');
			tableData = checkStatus.data
		});
		$(".layui-table-body.layui-table-main").each(function(i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
			}
		});
	}
	function updateList($data) {
		var url = setter.basePath + "homePageController/queryBIHomepageList",
			callback = function(data) {
				initTable(data.obj);
				clicktop(data.obj)
				layui.admin.removeLoading('body', true, true);
			};
		layui.admin.showLoading('body', 2, '.8')
		myUtil.send(url, {}, callback);
	};

	//编辑
	function uopdateItemDialog(val) {
		layui.admin.open({
			title: "编辑BI配置",
			content: editProcess,
			btn: ['确认', '取消'],
			success: function(layero, index) {
				form.render();
				

			},
			yes: function(index, layero) {
				//按钮【按钮一】的回调
				editbaocun(index)
				
				return false;
			},
			btn2: function(index, layero) {
				//按钮【按钮二】的回调
			},

		});
	};

	//修改BI保存
	function editbaocun(index) { 
		var vals = $('#resourceName');
		for (var i = 0; i < vals.length; i++) {
			var bigs = vals[i].value
		}
		$('#biName').val(bigs)
		for (var i = 0; i < tableData.length; i++) {
			var tabid = tableData[i].id
		}
		$('#biId').val(tabid)
		var img = $('#uploadImgFile')[0];
		if(img.files.length>0){
			if (img && img.files[0].size > (2 * 1024 * 1024)) {
				myUtil.msg({
					type: "warning",
					message: '上传图片不能大于2M!'
				});
				return false;
			}
		}
		
		// testImgWidthHeight(img, function(falg) {
			// if (falg) {
				var formData = new FormData($("#upload-form-edit")[0]);
				$.ajax({
					url: setter.basePath + "homePageController/updateBIHomepageInfo",
					method: 'POST',
					data: formData,
					contentType: false,
					processData: false,
					success: function(resp) {
						if (resp.success) {
							updateList()
							myUtil.msg({
								type: "success",
								message: resp.msg
							})
							layer.close(index)
						} else {
							myUtil.msg({
								type: "warning",
								message: resp.msg
							})
						}
					}
				});
			// } else {
			// 	myUtil.msg({
			// 		type: "warning",
			// 		message: '图片比率不符合16/9，请上传正确比率图片！'
			// 	});
			// 	return false;
			// }
		// })
	}

	function testImgWidthHeight(input, callback) {
		if (input.files) {
			//读取图片数据
			var f = input.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var data = e.target.result;
				//加载图片获取图片真实宽度和高度
				var image = new Image();
				image.onload = function() {
					var width = image.width;
					var height = image.height;
					if ((width / height) != (16 / 9)) {
						callback && callback(false);
					} else {
						callback && callback(true);
					}
				};
				image.src = data;
			};
			reader.readAsDataURL(f);

		} else {
			var image = new Image();
			image.onload = function() {
				var width = image.width;
				var height = image.height;
				if ((width / height) != (16 / 9)) {
					callback && callback(false);
				} else {
					callback && callback(true);
				}
			}
			image.src = input.value;
		}
	}
	//图片名称赋值
	function assignmen() {
		$('body').on("change", 'input[type=file]', function() {
			var str = $(this).val();
			if (str !== "") {
				var arr = str.split("\\");
				var file_name = arr[arr.length - 1];
				$('.PictureUpload').html(file_name);
			}
		})
	}

	function clicktop(notData) {
		if (notData != undefined) {
			window.notDataS = notData;
		}
		$('body .top').on('click', function() {
			var p = $(this).parent().parent().parent();
			var index = $("tbody tr").index(p);
			if (index == 0) {
				alert("已经在最上面");
				return;
			} else {
				$("tbody tr").eq(index - 1).before($("tbody tr").eq(index));
				var dataIndex = $(this).next().parent().parent().parent().index();
				var currentid = window.notDataS[dataIndex - 1].id; //当前id 
				var nextid = window.notDataS[dataIndex].id; //下一个id  
				zxc(currentid, nextid)
			}
		})

		$('body').off('click').on('click', '.bot', function() {
			var p = $(this).parent().parent().parent();
			var count = $("tbody tr").length;
			var index = $("tbody tr").index(p);
			if (index == (count - 1)) {
				alert("已经在最下面");
				return;
			} else {
				$("tbody tr").eq(index + 1).after($("tbody tr").eq(index));
				var dataIndex = $(this).prev().parent().parent().parent().index();
				var currentid = window.notDataS[dataIndex - 1].id; //当前id 
				var nextid = window.notDataS[dataIndex].id; //下一个id   
				zxc(currentid, nextid)
			}
		});
	} 
	function eventTop(){
		$(".layui-btn-add").off('click').on('click', function(){
			layui.admin.open({
				title: ["新增"],
				id: "LAY-admin-createOrg",
				skin: 'layui-layer-admin',
				content: NewProcess,
				zIndex: layer.zIndex,
				btn: ['确定', '取消'],
				success: function(layero, index) {
					form.render();
				},
				yes: function(index, layero) {
					var vals = $('#resourceName');
					for (var i = 0; i < vals.length; i++) {
						var bigs = vals[i].value
					}
					$('#biName').val(bigs);
					var img = $('#uploadImgFile')[0];
					if(img.files.length>0){
						if (img && img.files[0].size > (2 * 1024 * 1024)) {
							myUtil.msg({
								type: "warning",
								message: '上传图片不能大于2M!'
							});
							return false;
						}
					}
					
					// testImgWidthHeight(img, function(falg) {
					// 	if (falg) {
							var formData = new FormData($("#upload-form")[0]);
							$.ajax({
								url: setter.basePath + "homePageController/saveBIHomepageInfo",
								method: 'POST',
								data: formData,
								contentType: false,
								processData: false,
								success: function(resp) {
									if (resp.success) {
										updateList()
										myUtil.msg({
											type: "success",
											message: resp.msg
										})
										layer.close(index)
									} else {
										myUtil.msg({
											type: "warning",
											message: resp.msg
										})
									}
								}
							});
						// } else {
						// 	myUtil.msg({
						// 		type: "warning",
						// 		message: '图片比率不符合16/9，请上传正确比率图片！'
						// 	});
						// 	return false;
						// }
					// })
					
				},
				btn2: function(index, layero) {
					layui.admin.removeLoading('body', true, true);
				}
			});
		})
		
		$(".layui-btn-del").off('click').on('click', function(){
			if (tableData.length) {
				var names = [],
					ids = [];
				tableData.forEach(function(v, i) {
					names.push(v.name)
					ids.push(v.id)
				})
				names = names.join(",")
				layer.confirm('确定删除名为:' + names + '的BI配置吗？', {
					icon: 3,
					title: '删除BI配置',
					skin: 'layui-layer-admin'
				}, function(index) {
					var url = setter.basePath + "homePageController/deleteBIHomepageInfo",
						ajaxData = {
							ids: ids
						},
						callback = function(data) {
							if (data.success) {
								myUtil.msg({
									type: "success",
									message: data.msg,
								});
								layer.close(index)
								updateList()
							}
							tableData = ''
							layui.admin.removeLoading('body', true, true);
						}
					layui.admin.showLoading('body', 2, '.8')
					myUtil.send(url, ajaxData, callback);
				});
			} else {
				myUtil.msg({
					type: "warning",
					message: "请选择需要删除的指标",
				});
			}
		})
		
		$(".save-role").off("click").on("click", function() {
			var loginTitle = $(".loginTitle-input").val(),
			title = $(".title-input").val();
			if(loginTitle && loginTitle.length > 25){
				myUtil.msg({
					type: "warning",
					message: "登录标题不可超过20位"
				});
				return false;
			}else if(title && title.length > 15){
				myUtil.msg({
					type: "warning",
					message: "平台标题不可超过15位"
				});
				return false;
			}
			pageInfo.appearanceConfig.loginTitle = loginTitle;
			pageInfo.appearanceConfig.title = title;
			layui.admin.showLoading('body', 2, '1');
			$(".portalData-input").val(JSON.stringify(myUtil.cloneObj(pageInfo)));
            var formData = new FormData($(".portalInfo-form")[0]);
			$.ajax({
				url: setter.basePath+ "homePageController/saveHomepageInfo",
				method: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				success: function (resp) {
					if(resp.success){
						myUtil.msg({
							type: "success",
							message: resp.msg
						});
						if(resp.obj && resp.obj.appearanceConfig){
							pageInfo.appearanceConfig.loginLogo = resp.obj.appearanceConfig.loginLogo;
							pageInfo.appearanceConfig.logo = resp.obj.appearanceConfig.logo;
							pageInfo.appearanceConfig.loginBackground = resp.obj.appearanceConfig.loginBackground;
						}
						$(".clearimg-btn").addClass("layui-hide");
						$(".layui-file-input").val("");
						$(".portalData-input").val("");
					}else{
						myUtil.msg({
							type: "warning",
							message: resp.msg
						});
					}
					layui.admin.removeLoading('body', true, true);
				}
			});
		});
		$(".save-rolemob").off("click").on("click", function() {
			var url = setter.basePath + "homePageController/saveTerminalHomepageInfo",
				ajaxData =  myUtil.cloneObj(pageInfomob),
				callback = function(data) {
					if(data.success){
						myUtil.msg({
							type: "success",
							message: data.msg
						});
					}
					layui.admin.removeLoading('body', true, true);
				}
			layui.admin.showLoading('body', 2, '.8');
			myUtil.send(url, ajaxData, callback)
		});

		$(".searchRoleBtn1").off('click').on("click", function(){
			var value = $(".SearchRole").val().trim();
				if(value){
					var nodes = [];
					if(userTreeNodes1 && userTreeNodes1.length){
						userTreeNodes1.forEach(function(v, i){
							if(v.name.indexOf(value) > -1){
								nodes.push(v);
							}
						})
					}
					
					rendertrees(nodes)
				}else{
					rendertrees(userTreeNodes1)
				}
		})
		$(".searchRoleBtn1mob").off('click').on("click", function(){
			var value = $(".SearchRolemob").val().trim();
			if(value){
				var nodes1 = [];
				if(userTreeNodesmob1 && userTreeNodesmob1.length){
					userTreeNodesmob1.forEach(function(v, i){
						if(v.name.indexOf(value) > -1){
							nodes1.push(v);
						}
					})
				}
				renderTreemobs(nodes1);
				
			}else{
				renderTreemobs(userTreeNodesmob1);
			}
			
		})
		$(".portalItem-box").off("click").on("click", function(){
			var that = $(this),
			type = that.attr("data-type");
			if(!that.hasClass("active")){
				$(".portalItem-box").removeClass("active")
				that.addClass("active");
				pageInfo.appearanceConfig.portalType = type;
				if(type == "4"){
					$(".xnPortalStting").removeClass("layui-hide");
				}else{
					$(".xnPortalStting").addClass("layui-hide");
				}
			}
		})
		$(".themeColor-box").off("click").on("click", function(){
			var that = $(this),
			color = that.attr("data-color");
			if(!that.hasClass("active")){
				$(".themeColor-box").removeClass("active")
				that.addClass("active");
				pageInfo.appearanceConfig.themeColor = color;
			}
		})
		$(".themeItemBox .themeItem-box").off("click").on("click", function(){
			var that = $(this),
			type = that.attr("data-type");
			if(!that.hasClass("active")){
				$(".themeItemBox .themeItem-box").removeClass("active")
				that.addClass("active");
				pageInfo.appearanceConfig.protalStyle = type;
			}
		})
		$(".layoutItemBox .themeItem-box").off("click").on("click", function(){
			var that = $(this),
			type = that.attr("data-type");
			if(!that.hasClass("active")){
				$(".layoutItemBox .themeItem-box").removeClass("active")
				that.addClass("active");
				pageInfo.appearanceConfig.pageLayout = type;
				if(type == "3"){
					$(".topbarTheme-box").addClass("layui-hide");
				}else{
					$(".topbarTheme-box").removeClass("layui-hide");
				}
			}
		})
		$(".layui-file-input").off("change").on("change", function(e){
			var that = $(this),
			box = that.prev(),
			reader = new FileReader(),
			file = this.files[0],
			imgUrlBase64 = "";
			if (file) {
				//将文件以Data URL形式读入页面  
				imgUrlBase64 = reader.readAsDataURL(file);
				reader.onload = function (el) {
					if(that.hasClass("bg-file-input")){
						if(AllowImgFileSize != 0 && AllowImgFileSize < reader.result.length){
							myUtil.msg({ type: 'warning', message: '请上传不大于5M的图片！' });
							that.value = "";
							return;
						}else{
							box.css({"background-image": "url("+reader.result+")"});
							box.prev().removeClass("layui-hide");
							pageInfo.appearanceConfig.isInitLoginBackground = "0";
						}
					}else{
						if(box.hasClass("logo-box")){
							pageInfo.appearanceConfig.isInitLogo = "0";
						}else if(box.hasClass("loginLogo-box")){
							pageInfo.appearanceConfig.isInitLoginLogo = "0";
						}
						box.css({"background-image": "url("+reader.result+")"});
						box.prev().removeClass("layui-hide");
					}
				}
			}
		})
		$(".uploadimgbox, .uploadbgbox").off("click").on("click", function(e){
			e.stopPropagation();
			$(this).next().trigger("click");
		})
		$(".clearimg-btn").off("click").on("click", function(e){
			var that = $(this),
			box = that.next();
			if(box.hasClass("uploadbgbox")){
				pageInfo.appearanceConfig.isInitLoginBackground = "1";
				pageInfo.appearanceConfig.loginBackground = "";
				box.css({"background-image": "url("+setter.basePath+"static/images/bg_dly.png)"});
			}else if(box.hasClass("logo-box")){
				pageInfo.appearanceConfig.isInitLogo = "1";
				pageInfo.appearanceConfig.logo = "";
				box.css({"background-image": "url("+setter.basePath+"static/images/logo2_b_w@2x.png)"});
			}else if(box.hasClass("loginLogo-box")){
				pageInfo.appearanceConfig.isInitLoginLogo = "1";
				pageInfo.appearanceConfig.loginLogo = "";
				box.css({"background-image": "url("+setter.basePath+"static/images/logonew.png)"});
			}
			that.addClass("layui-hide");
			box.next().val("");
		});
	}
	function zxc(currentid, nextid) {
		var url = setter.basePath + "homePageController/exchangeOrder",
			ajaxData = {
				"id": currentid,
				"toId": nextid
			},
			callback = function(data) {
				layui.admin.removeLoading('body', true, true);
			}
		layui.admin.showLoading('body', 2, '.8');
		myUtil.send(url, ajaxData, callback)
	}
	
	! function() {
		
		if (isSm) {
			$(".layui-col-space15").removeClass("layui-col-space15").addClass("layui-col-space10");
		}
		$(".tree-box").each(function(i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
				$(v).addClass("treebox-scroll");
			}
		});
		$(".authorityTable").each(function(i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
			}
		});
		$(".authorityTable1").each(function(i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
			}
		});
		$('.formMenu-body').each(function(i, v) {
			if (!$(v).hasClass("ps")) {
				new pscrollbar(v);
			}
		});
		layui.sessionData("configTree", null);

		var productType = $("#productType").val();
		$(".authorized-item").addClass('layui-hide');
		switch (productType) {
			case '0':
				$('#staticHome').removeClass("layui-hide")
				$('#adminHome').removeClass("layui-hide")
				break;
			case '0,1':
				$('#dsHome').removeClass("layui-hide")
				$('#staticHome').removeClass("layui-hide")
				$('#adminHome').removeClass("layui-hide")
				break;
			case '2':
			case '0,1,2':
				$('#biHome').removeClass("layui-hide")
				$('#dsHome').removeClass("layui-hide")
				$('#staticHome').removeClass("layui-hide")
				$('#adminHome').removeClass("layui-hide")
				$('#resHome').removeClass("layui-hide")
				$('#simHome').removeClass("layui-hide")

				$('#staticHome1').removeClass("layui-hide")
				$('#resHome1').removeClass("layui-hide")
				break;
		}
		element.on('tab(homeManageTab)', function(data){
			if(data.index==0){
				$("#btn-save").removeClass('layui-hide');
			}else{
				$("#btn-save").addClass('layui-hide');
			}
		});
		$('#resHome').hover(function() {
				// 鼠标移入时添加hover类
				$('#resHome i').css('display','block')
		}, function() {
			// 鼠标移出时移出hover类
			$('#resHome i').css('display','none')
		});
		$('#resHome1').hover(function() {
			// 鼠标移入时添加hover类
				$('#resHome1 i').css('display','block')
		}, function() {
			// 鼠标移出时移出hover类
			$('#resHome1 i').css('display','none')
		});
		renderThemeColor();
		renderTree();
		eventItemTab();
		renderTreemob();
		eventItemTabmob();
		element.on('tab(docDemoTabBrief)', function(data){
			tabchangeindex=data.index
			if(data.index==0){
				$("#btn-save").removeClass('layui-hide');
				$("#btn-savemob").addClass('layui-hide');
			}else{
				$("#btn-save").addClass('layui-hide');
				$("#btn-savemob").removeClass('layui-hide');
			}
			
		});
		
		eventTop();
		updateList();
		assignmen();
		clicktop()
	}();
	exports("homePageManage", {})
});
