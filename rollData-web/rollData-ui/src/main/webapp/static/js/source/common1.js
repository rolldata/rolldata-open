// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    base: getProjectUrl() + 'static/module/',
    basePath: getProjectUrl()
}).extend({
    setter: '../config',
    formSelects: 'formSelects/formSelects-v4',
    treeGrid: 'treeGrid/treeGrid',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown.min',
    dropdown1: 'dropdown/dropdown1',
    notice: 'notice/notice.min',
    step: 'step-lay/step',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    eleTree: 'eleTree/eleTree.min',
    pscrollbar: '../libs/perfect-scrollbar/perfect-scrollbar.min'
}).use(['layer', 'admin1'], function () {
    var $ = layui.jquery;
    if(window.screen.availHeight > 900 && window.screen.availWidth > 1365){
        $('body').removeClass("layui-layout-sm-body");
    }else{
        $('body').addClass("layui-layout-sm-body");
    }

});

// 获取当前项目的根路径，通过获取layui.js全路径截取static之前的地址
function getProjectUrl() {
    return document.getElementById("basePath").value;
}