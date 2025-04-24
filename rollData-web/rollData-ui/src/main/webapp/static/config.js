layui.define([
    'laytpl',
    'layer',
    'element',
    'util'
], function(exports) {
    'use strict';
    
    exports('setter', {
        container: 'LAY_app' //容器ID
        ,base: layui.cache.base                    //记录静态资源所在路径
        ,basePath: layui.cache.basePath            //绝对路径base
        ,extends: layui.cache.base + 'libs/'       //扩展插件所在路径
        ,views: layui.cache.base + 'tpl/'          //动态模板所在目录
        ,entry: 'index'                            //默认视图文件名
        ,engine: '.html'                           //视图文件后缀名
        ,pageTabs: true                            //是否开启页面选项卡功能。iframe版推荐开启
        ,cacheTab: false                            //是否记忆打开的选项卡
        ,openTabCtxMenu: true                      //是否开启Tab右键菜单
        ,maxTabNum: 20                             //最多打开多少个tab
        ,eleTreeObj: ""                            //侧栏树对象 

        ,name: 'rollData'                        //
        ,tableName: 'rollData'                   //本地存储表名
        ,MOD_NAME: 'admin'                         //模块事件名
        
        ,debug: true                               //是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息
    
        //自定义请求字段
        ,request: {
            tokenName: false //自动携带 token 的字段名（如：access_token）。可设置 false 不携带。
        }
        
        //扩展的第三方模块
        ,extend: [
            'echarts', //echarts 核心包
            'echartsTheme' //echarts 主题
        ]
        //自定义图标
        ,leafIcon: {
            user: "eletree-iconfont-icon-user",
            role: "eletree-iconfont-icon-role",
            company: "eletree-iconfont-icon-org",
            folder: "eletree-icon-weibiaoti5",
            datasource: "eletree-iconfont-icon-datasource",
            func: "eletree-iconfont-icon-func",
            oper: "eletree-iconfont-icon-button",
            model: "eletree-iconfont-icon-model",
            dimension: "eletree-iconfont-icon-dimension",
            measure: "eletree-iconfont-icon-dimension",
            custommeasure: "eletree-iconfont-icon-dimension",
            database: "iconfont icon-datasource-manage",
            databases: "iconfont icon-datasource-manage",
            table: 'iconfont icon-table1',
            account:'iconfont icon-icon-zhangbu',
            product:'iconfont icon-project',
            department:'iconfont icon-bumen3',
            u8item:'iconfont icon-kemu',
        }
        //默认主题
        ,defaultTheme: 'theme-admin'
    });
});