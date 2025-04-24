; layui.define(["wdUtil", 'admin', "table", "form",'setter', 'jquery', 'pscrollbar'], function (exports) {
    var $ = layui.jquery
        , setter = layui.setter
        , myUtil = layui.wdUtil
        , F = {
            init: function(){
                F.event();
                F.getSysInfo();
                F.getNewWork();
            },
            event: function(){
                $(".updateLinkeRate-btn").off('click').on("click", function(){
                    F.getNewWork();
                })
            },
            getSysInfo: function(){
                layui.admin.showLoading('body', 2, '1')
                myUtil.send(setter.basePath + "devMonitorController/queryServerInfo", {}, function($data){
					if($data.success && $data.obj){
                        var obj = $data.obj;
                        for(var key in obj){
                            if(obj[key]){
                                for(var key1 in obj[key]){
                                    $("."+key1).text(obj[key][key1] || "")
                                }
                            }
                            switch (key) {
                                case 'devMonitorCpuInfo':
                                    F.updateProgress(obj[key].cpuTotalUseRate || 0, 'cpu');
                                    break;
                                case 'devMonitorMemoryInfo':
                                    F.updateProgress(obj[key].memoryUseRate || 0, 'memory');
                                    break;
                                case 'devMonitorStorageInfo':
                                    F.updateProgress(obj[key].storageUseRate || 0, 'storage');
                                    break;
                                case 'devMonitorJvmInfo':
                                    F.updateProgress(obj[key].jvmUseRate || 0, 'jvm');
                                    break;
                            
                            }
                        
                        }
					}
                    layui.admin.removeLoading('body', true, true);
                    
				});
            },
            getNewWork: function(){
                layui.admin.showLoading('.linkRate-card', 2, '1')
                myUtil.send(setter.basePath + "devMonitorController/queryNetworkInfo", {}, function($data){
					if($data.success && $data.obj){
                        $(".upLinkRate").text($data.obj.devMonitorNetworkInfo && $data.obj.devMonitorNetworkInfo.upLinkRate || "0 B/S")
                        $(".downLinkRate").text($data.obj.devMonitorNetworkInfo && $data.obj.devMonitorNetworkInfo.downLinkRate || "0 B/S")
					}
                    layui.admin.removeLoading('.linkRate-card', true, true);
				});
            },
            updateProgress: function(percent, className){
                const progress = percent / 100;
                const totalLength = 220.31;
                const currentLength = totalLength * progress;
                const progressPath = $('.'+className+'-card .ant-progress-circle-path')[0]
                const textElement = $('.'+className+'-card .ant-progress-text')[0]
                // 更新进度条
                progressPath.style.strokeDasharray = currentLength+'px, 295.31px';
                
                // 更新颜色（超过50%变红色）
                const color = percent >= 80 ? 'rgb(230, 0, 0)' : percent <= 30 ? 'rgb(73, 170, 25)' : 'rgb(24, 144, 254)';
                progressPath.style.stroke = color; 
                
                // 更新文字
                textElement.textContent = percent+'%';
                textElement.title = percent+'%';
                textElement.style.color = color;
            }
        }

    F.init();
    exports("sysMonitor", {})
})