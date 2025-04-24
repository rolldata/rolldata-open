;
layui.define(['layer', "wdUtil", "setter", "jquery", "form", "admin", "notice"], function (exports) {
  var $ = layui.jquery,
    layer = layui.layer,
    myUtil = layui.wdUtil,
    setter = layui.setter,
    form = layui.form,
    admin = layui.admin,
    smtpserverValue = "",
    createTestEmail = `<form class="layui-form layui-height-full-30" action="">
                            <div class="layui-form-item layui-form-item-sm" style="margin-bottom:15px">
                              <label class="layui-form-label">邮件接收人</label>
                              <div class="layui-input-inline" style="width:200px">
                                <input type="text" name="email" id="email" lay-verify="email" autocomplete="off" class="layui-input" placeholder="邮件接收人">
                              </div>
                            </div>
                          </form>`;

  // 查询邮箱管理信息
  var queryurl = setter.basePath + "mailManageController/queryMailManageInfo"
  var mailData = {};
  var querycallback = function (data) {
    if (data) {
      var datas = data.obj
      form.val('mailManage', {
        "smtpserver": datas.smtpserver,
        "serverPort": datas.serverPort,
        "senderAddress": datas.senderAddress,
        "password": datas.password,
        "displayName": datas.displayName,
        "sslIs":datas.sslIs
      })

      mailData = datas;
    }
    layui.admin.removeLoading('body', true, true);
    // layui.admin.removeLoading('', true, true);
  }
  layui.admin.showLoading('body', 2, '.8');
  myUtil.send(queryurl, mailData, querycallback)
  // 将select的值赋给input
  form.on('select(email-selected)', function (d) {
    $("#smtpserver").val(d.value);
    smtpserverValue = d.value;
  });
  // 保存
  $("#btn-save").on("click", function () {
    $("#savemail").attr("data-type", "1");
    $("#savemail").trigger("click");
  });
  // 测试
  $("#test").on("click", function () {
    $("#savemail").attr("data-type", "0");
    $("#savemail").trigger("click");
  });

  form.on('submit(savemail)', function (data) {
    var type = $("#savemail").attr("data-type");
    // 验证端口是否为数字
    var portReg = /^[0-9]*$/
    if(!portReg.test(Number($(serverPort).val()))){
      myUtil.msg({
        type: "warning",
        message: "端口格式不正确"
      });
      return false
    }
    if (type === "1") {
      // 保存
      var updateUrl = setter.basePath + "mailManageController/updateMailManage"
      var updateData = {
        "SMTPServer": data.field.smtpserver,
        "serverPort": data.field.serverPort,
        "senderAddress": data.field.senderAddress,
        "password": data.field.password,
        "displayName": data.field.displayName,
        "sslIs":data.field.sslIs
      }
      if (myUtil.isSame(mailData,updateData)) {
        myUtil.msg({
          type: "warning",
          message: "未作修改"
        });
        return false
      }
      var updataCallback = function (data) {
        if (data) {
          myUtil.msg({
            type: "success",
            message: data.msg
          });
        }
        layui.admin.removeLoading('body', true, true);
			  layui.admin.removeLoading('', true, true);
      }
      layui.admin.showLoading('body', 2, '.8');
      myUtil.send(updateUrl, updateData, updataCallback)
    } else {
      // 测试
      admin.open({
        title: "发送测试邮件",
        content: createTestEmail,
        area:["390px","180px"],
        btn: ['确认', '取消'],
        success: function (layero, index) {
        },
        yes: function (index) {
          var formemail = $("input[name='email']").val()
          var updateUrl = setter.basePath + "mailManageController/testMail"
          var updateData = {
            "toAddress":formemail,//邮件接收人
            "SMTPServer": data.field.smtpserver,
            "serverPort": data.field.serverPort,
            "senderAddress": data.field.senderAddress,
            "password": data.field.password,
            "displayName": data.field.displayName,
            "sslIs":data.field.sslIs
          }
          callback = function(data){
            if(data){
                // 验证通过，进入下一步
                myUtil.msg({type:"success",message:data.msg})
                layer.close(index)
            }
            layui.admin.removeLoading('body', true, true);
			      layui.admin.removeLoading('', true, true);
          }
          layui.admin.showLoading('body', 2, '.8');
          myUtil.send(updateUrl, updateData, callback)
        },
        btn2: function () {
        }
      })
    }
    return false
  })


})