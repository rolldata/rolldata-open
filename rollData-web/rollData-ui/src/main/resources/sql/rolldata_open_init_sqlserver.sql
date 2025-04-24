 CREATE TABLE WD_BI_HOME_CONFIG (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    name NVARCHAR(50)  NULL ,
    resource_id NVARCHAR(32)  NULL ,
    sort NVARCHAR(5)  NULL ,
    img_name NVARCHAR(50)  NULL ,
    img_url NVARCHAR(200)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_BUSINESS_DEMO (
    id NVARCHAR(32)  NULL ,
    demo_code NVARCHAR(50)  NULL ,
    demo_name NVARCHAR(100)  NULL ,
    c_state NVARCHAR(2)  NULL ,
    create_time DATETIME  NULL 
);
INSERT INTO WD_BUSINESS_DEMO (id,demo_code,demo_name,c_state,create_time) VALUES('2c9196fa9511abf101954640ea0737ee','aaa','示例1','1','2025-04-01 00:00:00');
INSERT INTO WD_BUSINESS_DEMO (id,demo_code,demo_name,c_state,create_time) VALUES('2c9196fa9511abf101957b36e8de6bfd','bbb','示例2','0','2025-04-01 00:00:00');
INSERT INTO WD_BUSINESS_DEMO (id,demo_code,demo_name,c_state,create_time) VALUES('40288186964339aa01964339aa280000','ccc','示例3','0','2025-04-01 00:00:00');

 CREATE TABLE WD_SYS_ANNOUNCEMENT (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    title NVARCHAR(50)  NULL ,
    content NVARCHAR(MAX)  NULL ,
    is_file NVARCHAR(2)  NULL ,
    file_name NVARCHAR(100)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_CONFIG (
    name NVARCHAR(50)  NOT NULL  PRIMARY KEY ,
    c_value NVARCHAR(MAX)  NULL ,
    c_type NVARCHAR(20)  NULL 
);
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.isOpenResourceWatermark','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.isShow','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.loginBackground',null,'appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.loginBackground.isInit','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.loginLogo',null,'appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.loginLogo.isInit','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.loginTitle','荣培数据平台','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.logo',null,'appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.logo.isInit','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.pageLayout','2','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.style','2','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.themeColor','722ED1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.title','荣培数据平台','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.topBarThemeColorBanner','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.topBarUsrThemeColor','0','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('appearance.portal.type','1','appearance');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('jsp.update.version','v0.0.1','update');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.displayName','荣培数据平台管理员','mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.password',null,'mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.senderAddress',null,'mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.serverPort',null,'mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.SMTPServer',null,'mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.SSLIS','0','mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('mail.verify','0','mail');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.login.limit.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.browse',null,'security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.browse.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.errnumber','5','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.errnumber.is','1','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.init.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.length','6','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.loginNum','2','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.loginNum.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.period','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.period.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.request','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.setting','1','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('security.password.validate.is','0','security');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('synchronous.config','{"systemType":"4","isOpen":"0","dataSource":"","orgSynType":"1","orgDbName":"","orgSqlServerParam":"","orgTableName":"","orgCode":"","orgName":"","orgTreeType":"","orgParentCode":"","deptSynType":"1","deptDbName":"","deptSqlServerParam":"","deptTableName":"","deptCode":"","deptName":"","deptInOrg":"","deptTreeType":"","deptParentCode":"","userDbName":"","userSqlServerParam":"","userTableName":"","userCode":"","userName":"","password":"","isAutoCreatePassword":"","role":"","organization":"","department":"","position":"","gender":"","mobilePhone":"","mail":"","synFrequence":"1","synTime":"","synWeek":"","synDate":"","nextTime":"","corpId":"","agentId":"","appKey":"","appSecret":"","matchType":"","isAutoSync":"","prefix":""}','synchronous');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('synchronous.isAutoSync','0','synchronous');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('synchronous.isopen','0','synchronous');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('system.last.updatetime',null,'system');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('system.release.time','2025-03-10','system');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('system.upload.fileSize','50','system');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('system.upload.imgSize','5','system');
INSERT INTO WD_SYS_CONFIG (name,c_value,c_type) VALUES('system.version.num','4.2.0','system');

 CREATE TABLE WD_SYS_DICT_CONTENT (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    dict_type_id NVARCHAR(32)  NULL ,
    dict_cde NVARCHAR(20)  NULL ,
    dict_name NVARCHAR(50)  NULL ,
    parent_id NVARCHAR(32)  NULL ,
    c_level NVARCHAR(50)  NULL ,
    c_index NVARCHAR(50)  NULL ,
    ext1 NVARCHAR(200)  NULL ,
    ext2 NVARCHAR(200)  NULL ,
    ext3 NVARCHAR(200)  NULL ,
    ext4 NVARCHAR(200)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    ext10 NVARCHAR(200)  NULL ,
    ext11 NVARCHAR(200)  NULL ,
    ext12 NVARCHAR(200)  NULL ,
    ext13 NVARCHAR(200)  NULL ,
    ext14 NVARCHAR(200)  NULL ,
    ext5 NVARCHAR(200)  NULL ,
    ext6 NVARCHAR(200)  NULL ,
    ext7 NVARCHAR(200)  NULL ,
    ext8 NVARCHAR(200)  NULL ,
    ext9 NVARCHAR(200)  NULL ,
    wd_model_id NVARCHAR(32)  NULL ,
    ext15 NVARCHAR(100)  NULL ,
    ext16 NVARCHAR(100)  NULL ,
    ext17 NVARCHAR(100)  NULL ,
    ext18 NVARCHAR(100)  NULL ,
    ext19 NVARCHAR(100)  NULL ,
    ext20 NVARCHAR(100)  NULL 
);
 CREATE TABLE WD_SYS_DICT_LEVEL (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    dict_type_id NVARCHAR(32)  NULL ,
    c_sequence NVARCHAR(1)  NULL ,
    digit NVARCHAR(50)  NULL ,
    level_name NVARCHAR(50)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    code_level NVARCHAR(255)  NULL ,
    end_num NVARCHAR(20)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_DICT_TYPE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    dict_type_cde NVARCHAR(20)  NULL ,
    dict_type_name NVARCHAR(50)  NULL ,
    show_type NVARCHAR(1)  NULL ,
    dict_type NVARCHAR(1)  NULL ,
    property_count NVARCHAR(2)  NULL ,
    property_name NVARCHAR(200)  NULL ,
    code_level NVARCHAR(1)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    rely_type NVARCHAR(1)  NULL ,
    c_table_name NVARCHAR(30)  NULL ,
    show_value NVARCHAR(50)  NULL ,
    real_value NVARCHAR(50)  NULL ,
    parent_value NVARCHAR(50)  NULL ,
    property_column NVARCHAR(200)  NULL ,
    c_type NVARCHAR(1)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_EMAIL_CODE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    user_code NVARCHAR(20)  NULL ,
    to_address NVARCHAR(50)  NULL ,
    verify_code NVARCHAR(10)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL 
);
 CREATE TABLE WD_SYS_FUNCTION (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    func_name NVARCHAR(20)  NULL ,
    parent_id NVARCHAR(32)  NULL ,
    href_link NVARCHAR(100)  NULL ,
    sort DECIMAL(10)  NULL ,
    state NVARCHAR(2)  NULL ,
    c_type NVARCHAR(2)  NULL ,
    relation_id NVARCHAR(32)  NULL ,
    is_href NVARCHAR(2)  NULL ,
    is_system NVARCHAR(2)  NULL ,
    power_flag NVARCHAR(100)  NULL ,
    c_system_type NVARCHAR(10)  NULL ,
    terminal_pc NVARCHAR(1)  NULL ,
    terminal_ipad NVARCHAR(1)  NULL ,
    terminal_mobile NVARCHAR(1)  NULL ,
    icon_class NVARCHAR(50)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    is_admin NVARCHAR(2)  NULL ,
    wd_model_id NVARCHAR(32)  NULL ,
    business_type NVARCHAR(2)  NULL 
);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('2c9a20819623008601962304ed890003','业务模块','0','functionController/queryUserAvailable','1','1','0',null,null,'0',null,null,null,null,null,null,null,null,null,null,'0',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('2c9a20819623008601962304ed890004','demo管理','2c9a20819623008601962304ed890003','businessController/demoManage','1','1','0',null,null,'0','sys:business:demoManage','business',null,null,null,'iconfont icon-peizhi','2025-04-01 00:00:00','4028b88163953a1b01639563f09c0005',null,null,'0',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b881647da6f201647dabd8e0000c','用户管理','4028b88169d695860169d6975a8f0018','userController/userManage','2','1','0',null,null,'0','sys:user:userManage','Public',null,null,null,'iconfont icon-user-list','2018-07-09 14:12:52','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b881647da6f201647dabf784000d','权限管理','4028b88169d695860169d6975a8f0018','roleController/roleList','3','1','0',null,null,'0','sys:role:roleList','Public',null,null,null,'iconfont icon-role-list','2018-07-09 14:12:59','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b881647da6f201647dac4996000e','密码安全','4028b88169d695860169d6975a8f0017','passwordSecurityController/setUpPasswordSecurity','4','1','0',null,null,'0','sys:passwordSecurity:setUpPasswordSecurity','Public',null,null,null,'iconfont icon-password-security','2018-07-09 14:13:20','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b881647da6f201647dac7c20000f','档案管理','4028b88169d695860169d6975a8f0017','sysDictionaryController/dictManage','2','1','0',null,null,'0','sys:dictionary:dictManage','Public',null,null,null,'iconfont icon-dic-list','2018-07-09 14:13:33','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b8816614934f016614a812f7002e','公告管理','4028b88169d695860169d6975a8f0017','announcementController/announcementManage','3','1','0',null,null,'0','sys:announcement:announcementManage','Public',null,null,null,'iconfont icon-notice-manage','2019-05-10 21:26:16','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b88169c758360169c7846f030001','邮箱管理','4028b88169d695860169d6975a8f0017','mailManageController/setUpMailManage','12','1','0',null,null,'0','sys:mailManage:setUpMailManage','Public',null,null,null,'iconfont icon-mail-list','2019-03-29 11:35:39','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b88169d695860169d6975a8f0001','首页管理','4028b88169d695860169d6975a8f0018','homePageController/setUpHomepageManage','13','1','0',null,null,'0','sys:homepageManage:setUpHomepageManage','Public',null,null,null,'iconfont icon-index-message','2019-04-01 09:50:37','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b88169d695860169d6975a8f0017','配置策略','0','functionController/queryUserAvailable','9999','1','0',null,null,'0','','Public',null,null,null,'iconfont icon-peizhi','2021-06-24 14:29:50','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('4028b88169d695860169d6975a8f0018','系统管理','0','functionController/queryUserAvailable','9998','1','0',null,null,'0',null,'Public',null,null,null,null,'2018-07-09 14:11:04','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);
INSERT INTO WD_SYS_FUNCTION (id,func_name,parent_id,href_link,sort,state,c_type,relation_id,is_href,is_system,power_flag,c_system_type,terminal_pc,terminal_ipad,terminal_mobile,icon_class,create_time,create_user,update_time,update_user,is_admin,wd_model_id,business_type) VALUES('d8f33a77eec911ebb6e7509a4c2cae76','系统监控','4028b88169d695860169d6975a8f0018','devMonitorController/systemMonitor','20','1','0',null,null,'0','sys:devMonitor:systemMonitor','Public',null,null,null,'iconfont icon-frequency','2025-03-11 14:29:50','4028b88163953a1b01639563f09c0005',null,null,'1',null,null);

 CREATE TABLE WD_SYS_FUNCTION_OPER (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    func_id NVARCHAR(32)  NULL ,
    oper_name NVARCHAR(20)  NULL ,
    power_flag NVARCHAR(100)  NULL ,
    create_time DATETIME  NULL 
);
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('402881826bf3699d016bf5775d46000d','4028b881647da6f201647dac4996000e','保存系统配置','sys:sysConfig:updateSysConfig','2019-11-01 20:34:40');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82ccc50000e','4028b881647da6f201647dabf784000d','新增角色','sys:role:save','2018-06-07 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82cf5050010','4028b881647da6f201647dabf784000d','保存角色','sys:role:update','2018-06-07 10:56:55');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82d19960012','4028b881647da6f201647dabf784000d','删除角色','sys:role:del','2018-06-07 10:57:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82d19960b10','4028b881647da6f201647dabf784000d','创建职务','sys:post:createPost','2018-06-07 10:57:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82d19960b12','4028b881647da6f201647dabf784000d','修改职务','sys:post:updatePost','2018-06-07 10:57:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88163d825e10163d82d19960b13','4028b881647da6f201647dabf784000d','删除职务','sys:post:deletePost','2018-06-07 10:57:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dabd8e0000a','4028b881647da6f201647dabd8e0000c','新增用户','sys:user:saveUser','2018-09-16 17:51:01');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dabd8e0000b','4028b881647da6f201647dabd8e0000c','保存用户','sys:user:update','2018-09-16 17:56:17');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dabd8e0000d','4028b881647da6f201647dabd8e0000c','删除用户','sys:user:delete','2018-09-16 17:51:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dac4996000a','4028b881647da6f201647dac4996000e','保存','sys:passwordSecurity:updatePasswordSecurity','2018-11-29 20:55:42');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dac499600za','4028b88169c758360169c7846f030001','保存','sys:mailManage:updateMailManage','2019-04-01 15:39:32');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881647da6f201647dac499656za','4028b88169c758360169c7846f030001','发送测试邮件','sys:mailManage:testMail','2019-05-18 18:20:46');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e1d88f4f0003','4028b881647da6f201647dac7c20000f','增加档案目录','sys:dictionary:save','2018-09-16 18:06:31');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e1db848b0005','4028b881647da6f201647dac7c20000f','增加档案','sys:dictionary:data:save','2018-09-16 18:09:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e540ac940122','4028b881647da6f201647dac7c20000f','删除档案目录','sys:dictionary:delete','2018-09-16 18:06:33');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e540ffa20123','4028b881647da6f201647dac7c20000f','修改档案','sys:dictionary:data:update','2018-09-17 09:59:27');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e5414f7b0124','4028b881647da6f201647dac7c20000f','删除档案','sys:dictionary:data:delete','2018-09-17 09:59:47');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e5418ef90125','4028b881647da6f201647dac7c20000f','导入档案','sys:dictionary:data:uploadFile','2018-09-17 10:00:04');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e5494eb8013b','4028b881647da6f201647dabd8e0000c','新增组织','sys:org:saveOrg','2018-09-17 10:08:31');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e54984b7013c','4028b881647da6f201647dabd8e0000c','删除组织','sys:org:deleteOrgs','2018-09-17 10:08:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e549bbf1013d','4028b881647da6f201647dabd8e0000c','修改组织','sys:org:updateOrg','2018-09-17 10:08:59');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e549bbf1013h','4028b881647da6f201647dabd8e0000c','用户导入','sys:user:importOrgUser','2018-09-17 10:08:59');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165e1bd100165e549bbf1064h','4028b881647da6f201647dabd8e0000c','重置密码','sys:user:resetPassword','2018-09-17 10:09:59');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88165f136260165f2284ac20000','4028b881647da6f201647dac7c20000f','编辑档案目录','sys:dictionary:update','2018-09-16 18:06:32');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b8816614934f016614a812fzxv2a','4028b8816614934f016614a812f7002e','修改公告','sys:announcement:updateAnnouncementInfo','2019-05-11 14:36:10');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b8816614934f016614a812fzxv2c','4028b8816614934f016614a812f7002e','删除公告','sys:announcement:deleteAnnouncements','2019-05-11 14:36:48');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b8816614934f016614a812fzxv2e','4028b8816614934f016614a812f7002e','新增公告','sys:announcement:saveAnnouncementInfo','2019-05-11 14:35:14');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88169d695860169d6975a8f0vc1','4028b88169d695860169d6975a8f0001','首页配置保存','sys:homepageManage:saveHomepageInfo','2019-05-08 16:45:34');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88169d695860169d6975a8f0vc2','4028b88169d695860169d6975a8f0001','BI配置保存','sys:homepageManage:saveBIHomepageInfo','2019-05-09 13:14:01');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88169d695860169d6975a8f0vc3','4028b88169d695860169d6975a8f0001','BI配置删除','sys:homepageManage:deleteBIHomepageInfo','2019-05-09 21:32:59');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88169d695860169d6975a8f0vc4','4028b88169d695860169d6975a8f0001','BI配置修改','sys:homepageManage:updateBIHomepageInfo','2019-05-09 21:33:31');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b88169d695860169d6975a8f0vc5','4028b88169d695860169d6975a8f0001','移动端首页','sys:homepageManage:saveTerminalHomepageInfo','2019-05-08 16:45:35');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881zxcda6f201647daa234xf207','4028b881647da6f201647dac7c20000f','上移下移','sys:dictionary:data:exchangeOrder','2023-01-16 10:00:00');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881zxcda6f201647daa234xf222','d8f33a77eec911ebb6e7509a4c2cae76','查询',':query','2025-04-15 18:44:11');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881zxcda6f201647daa234xf223','2c9a20819623008601962304ed890004','查询','sys:business:query','2025-04-15 18:44:11');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('4028b881zxcda6f201647daa234xf224','2c9a20819623008601962304ed890004','启停','sys:business:updateBusinessState','2025-04-15 18:44:11');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('96f7409228aa11eb819b025041000001','4028b881647da6f201647dabd8e0000c','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('96fc6ec528aa11eb819b025041000001','4028b881647da6f201647dabf784000d','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('97031f1f28aa11eb819b025041000001','4028b881647da6f201647dac4996000e','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('970a614828aa11eb819b025041000001','4028b881647da6f201647dac7c20000f','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('9793803428aa11eb819b025041000001','4028b8816614934f016614a812f7002e','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('97b1176828aa11eb819b025041000001','4028b88169c758360169c7846f030001','查询',':query','2018-06-06 10:56:45');
INSERT INTO WD_SYS_FUNCTION_OPER (id,func_id,oper_name,power_flag,create_time) VALUES('97b76d8828aa11eb819b025041000001','4028b88169d695860169d6975a8f0001','查询',':query','2018-06-06 10:56:45');

 CREATE TABLE WD_SYS_LAST_ONLINE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    create_time DATETIME  NULL ,
    host NVARCHAR(100)  NULL ,
    last_login_timestamp DATETIME  NULL ,
    last_stop_timestamp DATETIME  NULL ,
    login_count NVARCHAR(255)  NULL ,
    system_host NVARCHAR(100)  NULL ,
    total_online_time NVARCHAR(255)  NULL ,
    update_time DATETIME  NULL ,
    user_agent NVARCHAR(MAX)  NULL ,
    user_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_LOG (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    broswer NVARCHAR(50)  NULL ,
    title NVARCHAR(255)  NULL ,
    note NVARCHAR(MAX)  NULL ,
    c_type NVARCHAR(2)  NULL ,
    user_id NVARCHAR(32)  NULL ,
    log_content NVARCHAR(MAX)  NULL ,
    log_level int  NULL ,
    operate_time DATETIME  NOT NULL 
);
 CREATE TABLE WD_SYS_MESSAGE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    title NVARCHAR(50)  NULL ,
    content NVARCHAR(MAX)  NULL ,
    c_type NVARCHAR(1)  NULL ,
    to_user NVARCHAR(32)  NULL ,
    state NVARCHAR(2)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_ORG (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    parent_id NVARCHAR(32)  NULL ,
    org_name NVARCHAR(50)  NULL ,
    org_cde NVARCHAR(20)  NULL ,
    c_type NVARCHAR(1)  NULL ,
    icon NVARCHAR(100)  NULL ,
    icon_skin NVARCHAR(100)  NULL ,
    automatic_summary NVARCHAR(1)  NULL ,
    same_level_id NVARCHAR(32)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    iconskin NVARCHAR(100)  NULL ,
    wd_model_id NVARCHAR(32)  NULL ,
    is_sync NVARCHAR(2)  NULL 
);
INSERT INTO WD_SYS_ORG (id,parent_id,org_name,org_cde,c_type,icon,icon_skin,automatic_summary,same_level_id,create_user,create_time,update_user,update_time,iconskin,wd_model_id,is_sync) VALUES('4028818d81f5b9060181f6d192a90006','0','数据管理','data_ascription','1',null,null,null,null,'4028b88163953a1b01639563f09c0005','2022-07-13 17:08:59','4028b88163953a1b01639563f09c0005','2022-07-13 17:13:06',null,null,null);
INSERT INTO WD_SYS_ORG (id,parent_id,org_name,org_cde,c_type,icon,icon_skin,automatic_summary,same_level_id,create_user,create_time,update_user,update_time,iconskin,wd_model_id,is_sync) VALUES('4028818d81f5b9060181f6d280350008','4028818d81f5b9060181f6d192a90006','数据管理','data_ascription_dept','2',null,null,null,null,'4028b88163953a1b01639563f09c0005','2022-07-13 17:09:59','4028b88163953a1b01639563f09c0005','2022-07-13 17:13:10',null,null,null);

 CREATE TABLE WD_SYS_ORGROLE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    org_id NVARCHAR(32)  NULL ,
    role_id NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL 
);
 CREATE TABLE WD_SYS_POST (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    post_code NVARCHAR(20)  NULL ,
    post_name NVARCHAR(50)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_POST_MENU (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    post_id NVARCHAR(32)  NULL ,
    relation_id NVARCHAR(32)  NULL ,
    c_type NVARCHAR(1)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_POST_POWER (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    post_id NVARCHAR(32)  NULL ,
    power_type NVARCHAR(2)  NULL ,
    power_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_POST_USER (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    post_id NVARCHAR(32)  NULL ,
    user_id NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL 
);
 CREATE TABLE WD_SYS_ROLE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    role_cde NVARCHAR(20)  NULL ,
    role_name NVARCHAR(50)  NULL ,
    state NVARCHAR(1)  NULL ,
    c_remark NVARCHAR(MAX)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    is_admin NVARCHAR(2)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
INSERT INTO WD_SYS_ROLE (id,role_cde,role_name,state,c_remark,create_time,create_user,update_time,update_user,is_admin,wd_model_id) VALUES('4028b88165848c240165848f01960009','adminRole','超级管理员','1','','2018-08-29 15:21:29','4028b88163953a1b01639563f09c0005','2018-10-13 12:53:00','4028b88165806c4001658076b26f0009','1',null);

 CREATE TABLE WD_SYS_ROLE_HOMEPAGE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    c_type NVARCHAR(2)  NULL ,
    role_id NVARCHAR(32)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    resource_id NVARCHAR(32)  NULL ,
    terminal_type NVARCHAR(2)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
 CREATE TABLE WD_SYS_ROLE_MENU (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    role_id NVARCHAR(32)  NULL ,
    relation_id NVARCHAR(32)  NULL ,
    c_type NVARCHAR(1)  NULL ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('2c92c0818f12d8b1018f12daf7d40022','4028b88165848c240165848f01960009','2c92c0818f12d8b1018f12daf7870006','0','2024-04-25 09:24:51','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('40288185963d3e4701963d837f64001a','4028b88165848c240165848f01960009','40288185963d3e4701963d837c6d0018','0','2025-04-16 15:32:30','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bc9a80d017bcd48040000b3','4028b88165848c240165848f01960009','402881897a5675f1017a56d3cfe20024','1','2021-09-10 09:17:34','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bc9a80d017bcead6e630144','4028b88165848c240165848f01960009','402881897bc8211f017bc82c91a7002e','1','2021-09-10 15:47:58','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bde9b67017be28856e90165','4028b88165848c240165848f01960009','402881897b8f824a017b8ff500df0055','1','2021-09-14 12:19:51','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bde9b67017be289d1af01a6','4028b88165848c240165848f01960009','402881897b8f824a017b8ff500df0055','1','2021-09-14 12:21:28','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bec76ee017bed2a59e705e8','4028b88165848c240165848f01960009','402881897bec76ee017bed25b8f600fb','1','2021-09-16 13:53:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bf1ce00017bf2f25d820822','4028b88165848c240165848f01960009','4028818979fa7bc2017a18e48dcd00fc','1','2021-09-17 16:49:35','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bf1ce00017bf2f293510868','4028b88165848c240165848f01960009','402881897bec76ee017bee5f64e508c1','1','2021-09-17 16:49:49','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897bf1ce00017bf7d1e0740a40','4028b88165848c240165848f01960009','402881897bf1ce00017bf70201a409e9','1','2021-09-18 15:32:12','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c0b11c2017c1bbf44651683','4028b88165848c240165848f01960009','402881897c0b11c2017c0cc174bb006e','1','2021-09-25 14:58:12','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c0b11c2017c1bbf44661685','4028b88165848c240165848f01960009','402881897c0b11c2017c1082b25b05b2','1','2021-09-25 14:58:12','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c0b11c2017c1bbf44671687','4028b88165848c240165848f01960009','402881897c0b11c2017c16732a440b6e','1','2021-09-25 14:58:12','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c0b11c2017c1bbf44681689','4028b88165848c240165848f01960009','402881897c0b11c2017c1bbed036118c','1','2021-09-25 14:58:12','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c3042c0f20673','4028b88165848c240165848f01960009','402881897bf1ce00017bf873ac680a9a','1','2021-09-29 14:34:14','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c3042c0f80679','4028b88165848c240165848f01960009','402881897bf1ce00017bf7ca0b8d0a33','1','2021-09-29 14:34:14','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a7a070b','4028b88165848c240165848f01960009','402881897bbef9a7017bbf5f1cd00043','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a7c070d','4028b88165848c240165848f01960009','402881897bec76ee017bec938e77001a','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a800713','4028b88165848c240165848f01960009','402881897a5675f1017a56931ba90016','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a810715','4028b88165848c240165848f01960009','402881897bc9a80d017bceacbf580137','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a830717','4028b88165848c240165848f01960009','402881897bbef9a7017bc350669f00c5','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a840719','4028b88165848c240165848f01960009','402881897bbef9a7017bbeff7dd60002','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a85071b','4028b88165848c240165848f01960009','402881897bc9a80d017bcd479f0b0093','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a86071d','4028b88165848c240165848f01960009','402881897bc44ace017bc4cade7c003d','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a87071f','4028b88165848c240165848f01960009','4028818979fa7bc2017a18e46a5500fa','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a890721','4028b88165848c240165848f01960009','402881897bec76ee017bec91dff00019','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a8b0723','4028b88165848c240165848f01960009','402881897bc8211f017bc85a681c004e','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a8c0725','4028b88165848c240165848f01960009','402881897bc8211f017bc85def5f004f','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a8d0727','4028b88165848c240165848f01960009','402881897bc8211f017bc85e25d50050','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a8e0729','4028b88165848c240165848f01960009','402881897bc8211f017bc88408160070','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a90072b','4028b88165848c240165848f01960009','402881897bc8211f017bc8a908a3007d','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a91072d','4028b88165848c240165848f01960009','402881897bc8211f017bc951428a00c3','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34840a9e072f','4028b88165848c240165848f01960009','402881897bc9a80d017bdcf7712801c1','1','2021-09-30 10:24:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881897c2a65aa017c34aad50b0742','4028b88165848c240165848f01960009','402881897c2a65aa017c348f6f5e0735','1','2021-09-30 11:06:23','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202500b2','4028b88165848c240165848f01960009','4028818b7954dc6d01795e764fe90061','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202500b4','4028b88165848c240165848f01960009','4028818b7954dc6d01795e76510a0062','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202500b6','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7651a00063','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202500b8','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7652c20064','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202f00ba','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7653800065','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202f00bc','4028b88165848c240165848f01960009','4028818b7954dc6d01795e76547a0066','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202f00be','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7655100067','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82202f00c0','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7656000068','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e82203900c2','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7656be0069','1','2021-05-12 10:57:37','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc5e0145','4028b88165848c240165848f01960009','4028818b7954dc6d01795e9386d70135','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc5e0147','4028b88165848c240165848f01960009','4028818b7954dc6d01795e9388df0136','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc5e0149','4028b88165848c240165848f01960009','4028818b7954dc6d01795e9389930137','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc5e014b','4028b88165848c240165848f01960009','4028818b7954dc6d01795e938b7c0138','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc5e014d','4028b88165848c240165848f01960009','4028818b7954dc6d01795e938c9e0139','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc68014f','4028b88165848c240165848f01960009','4028818b7954dc6d01795e938e24013a','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc680151','4028b88165848c240165848f01960009','4028818b7954dc6d01795e938ea6013b','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc680153','4028b88165848c240165848f01960009','4028818b7954dc6d01795e938ff0013c','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01795e94bc680155','4028b88165848c240165848f01960009','4028818b7954dc6d01795e93911c013d','1','2021-05-12 11:17:56','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('4028818b7954dc6d01796a1f34f301f6','4028b88165848c240165848f01960009','4028818b7954dc6d01795e7d25fe009d','1','2021-05-14 17:05:01','4028b88163953a1b01639563f09c0005',null);
INSERT INTO WD_SYS_ROLE_MENU (id,role_id,relation_id,c_type,create_time,create_user,wd_model_id) VALUES('402881a07e8adbdc017e8b3c3fc200a6','4028b88165848c240165848f01960009','402881a07e8adbdc017e8b3c3ea60082','0','2022-01-24 16:38:09','4028b88163953a1b01639563f09c0005',null);

 CREATE TABLE WD_SYS_ROLEPOWER (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    role_id NVARCHAR(32)  NULL ,
    power_type NVARCHAR(2)  NULL ,
    power_id NVARCHAR(32)  NULL 
);
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('05e1f67628c24df2a8887455e900a805','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx5d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x09','4028b88165848c240165848f01960009','2','4028b88163df4b350163df5efd790412');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x19','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e7d314371');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x20','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaad01f6x378');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x21','4028b88165848c240165848f01960009','2','4028b88165f136260165f2284ac21c98');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x22','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xcf26');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x23','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xf121');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x24','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xf129');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x25','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xcfx5');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x26','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xcfx5');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x27','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xcfx1');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x28','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f91fxe');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x29','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4307');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x30','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4308');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x31','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z19');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x32','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z18');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x33','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z17');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x34','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z16');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x35','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z15');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x36','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z14');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x37','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4312');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x38','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4311');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x39','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z20');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x40','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z21');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x41','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z22');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x42','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z23');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x43','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z24');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x44','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9112c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x45','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9114e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x46','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9115e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x47','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9116e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x48','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9113d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f01960x49','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z25');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb09','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc00g');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb10','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fcdf210003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb11','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fcdf210002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb12','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fcdf210004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb13','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4303');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb14','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4304');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165848f0196vb15','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z13');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('1028b88165848c240165sd8f01960009','4028b88165848c240165848f01960009','2','402881826bf3699d016bf5775d46000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('18b7f13902324a918041992e38cb3653','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx6i');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('2028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('2c92c0818f12d8b1018f12daf7cf0021','4028b88165848c240165848f01960009','1','2c92c0818f12d8b1018f12daf7870006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('2d3bb4fb6ae7403795c2f41b3c3606e8','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx0c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01911189','4028b88165848c240165848f01960009','2','4028b88165f136260165f2284ac20000');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01922289','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaad01f60378');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01933110','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7012c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01933111','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7012d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01933112','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7012f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01933389','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e41c60373');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01944489','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f70003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01955589','4028b88165848c240165848f01960009','2','402881826bf3699d016bf5775d46000c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01966669','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e549bbf1013h');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01967779','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e549bbf1064h');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c240165848f01968889','4028b88165848c240165848f01960009','2','4028b88163df4b350163df5e5bdc000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933111','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc022');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933112','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc023');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933113','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc024');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933114','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc025');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933115','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc026');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933116','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc027');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933117','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc028');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3028b88165848c24zx65848f01933118','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc029');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('3ea88f37762745969505e820c04b3355','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx3y');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846baccf12016bacd60581063d','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5674c0c0145');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846cbc0ac2016cbe22807c0070','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xc001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846cbc0ac2016cbe22807c0071','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xc002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846cbc0ac2016cbe22807c0072','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xc003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846cbc0ac2016cbe22807c0073','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xc004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846cbc0ac2016cbe22807c0074','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa234xc005');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846e0d3c36016e11bf20b0033d','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa0cd00xff');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846e0d3c36016e11bf20b00z1d','4028b88165848c240165848f01960009','1','4028b8816493b649016493bbfeb00112');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846e0d3c36016e11bf20b00z2d','4028b88165848c240165848f01960009','1','4028b8816493b649016493bbfeb00222');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881846e0d3c36016e11bf20b00z3d','4028b88165848c240165848f01960009','1','4028b8816493b649016493bbfeb00332');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c2535016a6c2899910005','4028b88165848c240165848f01960009','1','402881856a6c2535016a6c2899820002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c2535016a6c29f3dc0008','4028b88165848c240165848f01960009','1','402881856a6c2535016a6c29f3d60006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c2535016a6c2b80c7000b','4028b88165848c240165848f01960009','1','402881856a6c2535016a6c2b80c30009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c2535016a6c2bb801000e','4028b88165848c240165848f01960009','1','402881856a6c2535016a6c2bb7fc000c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c2535016a6c312ac10028','4028b88165848c240165848f01960009','1','402881856a6c2535016a6c312abd0026');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3452016a6c35cce70004','4028b88165848c240165848f01960009','1','402881856a6c3452016a6c35ccd20001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3452016a6c386d21000a','4028b88165848c240165848f01960009','1','402881856a6c3452016a6c386d1d0008');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3452016a6c39483a000d','4028b88165848c240165848f01960009','1','402881856a6c3452016a6c394837000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3452016a6c3abd190010','4028b88165848c240165848f01960009','1','402881856a6c3452016a6c3abd16000e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3b9c016a6c3c10080003','4028b88165848c240165848f01960009','1','402881856a6c3b9c016a6c3c0ff30001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3b9c016a6c3c882a0006','4028b88165848c240165848f01960009','1','402881856a6c3b9c016a6c3c88250004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c3b9c016a6c47eabd0009','4028b88165848c240165848f01960009','1','402881856a6c3b9c016a6c47eab80007');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c5ce8016a6c5d80550004','4028b88165848c240165848f01960009','1','402881856a6c5ce8016a6c5d80430001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c5ce8016a6c6001c70008','4028b88165848c240165848f01960009','1','402881856a6c5ce8016a6c6001c10006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c5ce8016a6c60281c000b','4028b88165848c240165848f01960009','1','402881856a6c5ce8016a6c6028180009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c5ce8016a6c663a8b0015','4028b88165848c240165848f01960009','1','402881856a6c5ce8016a6c663a870013');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c5ce8016a6c6809ec001b','4028b88165848c240165848f01960009','1','402881856a6c5ce8016a6c6809e70019');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c6b48016a6d20a73a000f','4028b88165848c240165848f01960009','1','402881856a6c6b48016a6d20a736000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881856a6c6b48016a6d20cb330012','4028b88165848c240165848f01960009','1','402881856a6c6b48016a6d20cb2b0010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40288185722bf74101722bf8c9000024','4028b88165848c240165848f01960009','1','40288185722bf74101722bf8c8800001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40288185963d3e4701963d837e450019','4028b88165848c240165848f01960009','1','40288185963d3e4701963d837c6d0018');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43c1','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa337zx00e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43s1','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z1');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43s2','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z2');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43z5','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4305');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43z6','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z3');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43z7','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z4');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43z8','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z5');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f43z9','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z6');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4h01','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4301');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z10','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z7');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z11','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z8');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z12','4028b88165848c240165848f01960009','1','402881866a646455016a6465537f4306');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z13','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f43z9');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z14','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z10');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z15','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z11');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881866a646455016a6465537f4z16','4028b88165848c240165848f01960009','2','402881866a646455016a6465537f4z12');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881876f456f4c016f463e44d5000f','4028b88165848c240165848f01960009','1','402881876f456f4c016f463e44af000a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40288187710f3ac0017110b108ed006f','4028b88165848c240165848f01960009','1','40288187710f3ac0017110b108d9006c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818970eb327f0170eceb13c90051','4028b88165848c240165848f01960009','1','4028818970eb327f0170eceb133b003e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818971103e43017110bcf9d20022','4028b88165848c240165848f01960009','1','4028818971103e43017110bcf9c3001b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818971103e43017110bf74a5002f','4028b88165848c240165848f01960009','1','4028818971103e43017110bf74840024');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818971103e43017110bfe4d20038','4028b88165848c240165848f01960009','1','4028818971103e43017110bfe4cc0031');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818971103e43017110c063930041','4028b88165848c240165848f01960009','1','4028818971103e43017110c0638e003a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028818b6bf10f2d016bf51330c926e8','4028b88165848c240165848f01960009','1','4028818b6bf10f2d016bf513307126dc');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('402881a07e8adbdc017e8b3c3fb900a5','4028b88165848c240165848f01960009','1','402881a07e8adbdc017e8b3c3ea60082');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2565db4d720165ea9e02a4037f','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e02a4037f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2565db4d720165ea9e02a4037x','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7002d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b01e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc012');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b02e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc013');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b03e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc014');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b04e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc015');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b05e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc016');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b06e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc017');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b07e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc018');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b08e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc019');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b09e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc020');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b10e00bx','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc021');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b77e007b','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7001a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b77e007u','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b77e00a4','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40008');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b77e00bx','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7001b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b783005t','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b78300a4','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339b78300a5','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d339xc83zx7u','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f003x');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d3456a130087','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40011');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d3456a1300fb','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d3456a1f016h','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d3456a1f02ba','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004g');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168d3456a1fbnba','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40012');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f2568b162c10168dcvb6a13005g','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f003z');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168ad6e1d340028','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168ad6e1dc400cv','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168ad6e1dc400xc','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168ad6e1dc44528','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40014');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168ad6e1dc46y27','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40013');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289f426888d35a0168adcvbd7u0027','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f004r');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53e9500c0','4028b88165848c240165848f01960009','1','4028b881647da6f201647da7e37f0001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53e9c00c1','4028b88165848c240165848f01960009','1','4028b8816493b649016493bb89d90001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53ea500c2','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fcdf210002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53eae00c3','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fc99c20001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53eae00x4','4028b88165848c240165848f01960009','1','4028b88165b6e64a0165b6fc99c200xp');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53ee900c4','4028b88165848c240165848f01960009','1','4028b881647da6f201647da854da0002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53ef200c5','4028b88165848c240165848f01960009','1','4028b8816493b649016493bbfeb00002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f0800c7','4028b88165848c240165848f01960009','2','4028b88163ded86b0163ded8ea250008');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f1400c7','4028b88165848c240165848f01960009','2','4028b88163df4b350163df5e5bdc000e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f2500c8','4028b88165848c240165848f01960009','2','4028b88163df4b350163df5e948d0010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f2c00c9','4028b88165848c240165848f01960009','2','4028b88163df4b350163df5efd790012');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f2c11a9','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9111c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f2c11b9','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9111d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f2c11c9','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f9111e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f3400ca','4028b88165848c240165848f01960009','2','4028b88165ead16c0165eaf1e1c60001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f3c00cb','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f0006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f5600ce','4028b88165848c240165848f01960009','1','4028b8816614934f016614a651220001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f7300d1','4028b88165848c240165848f01960009','2','40289fde666b9b4e01666bb8ed92000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f7800d2','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa0cd00009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f9300d3','4028b88165848c240165848f01960009','1','4028b8816493b649016493bfbcc60009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53f9f00d5','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e41c60370');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53fad00d6','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e7d310371');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53fb500d7','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9eba810372');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53fbd00d8','4028b88165848c240165848f01960009','2','4028b88165eab5f40165eacf936c0045');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53fc300d9','4028b88165848c240165848f01960009','2','4028b88165eab5f40165eacfbfd60046');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53fd400da','4028b88165848c240165848f01960009','1','4028b88165af20bc0165af22bab70000');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc53ff100dc','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaac2b800374');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5400700dd','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaac759e0375');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5401000de','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaaca48a0376');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5401800df','4028b88165848c240165848f01960009','2','40289f2565db4d720165eaad01f60377');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5402900e0','4028b88165848c240165848f01960009','1','4028b88165b2cac90165b3ff34d9002e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5402f00e1','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabaf42000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5403f00e3','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5674c0c0141');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5404600e4','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5677ec90142');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5407100e5','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e567fc6d0143');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5408000e6','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e56839d20144');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5408c00e7','4028b88165848c240165848f01960009','1','systemMenus');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5409700e8','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabd8e0000c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc540a100e9','4028b88165848c240165848f01960009','2','4028b881647da6f201647dabd8e0000a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc540d700ea','4028b88165848c240165848f01960009','2','4028b881647da6f201647dabd8e0000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc540e700eb','4028b88165848c240165848f01960009','2','4028b881647da6f201647dabd8e0000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5410300ee','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabf784000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5411000ef','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82ccc50000e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5413000f0','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82cf5050010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5413e00f1','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82d19960012');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5413e01f0','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82d19960b10');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5413e01f1','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82d19960b12');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5413e01f2','4028b88165848c240165848f01960009','2','4028b88163d825e10163d82d19960b13');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5415e00f3','4028b88165848c240165848f01960009','1','4028b881647da6f201647dac7c20000f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5416d00f4','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e1d88f4f0003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5417b00f5','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e1db848b0005');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5419300f6','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e540ac940122');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zx10','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc009');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zx11','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zx12','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc011');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf1','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc00e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf2','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf3','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf4','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf5','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf6','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc005');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf7','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf8','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc007');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc54193zxf9','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc008');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc541cf00f7','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e540ffa20123');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc541vbzxf7','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc006');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc541vbzxf8','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc007');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5421700f8','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5414f7b0124');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5422200f9','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5418ef90125');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5423500fb','4028b88165848c240165848f01960009','1','4028b881647da6f201647dacaae70010');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5425500fc','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e5494eb8013b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5427200fd','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e54984b7013c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc5428000fe','4028b88165848c240165848f01960009','2','4028b88165e1bd100165e549bbf1013d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde666bc1a601666bc542fa0100','4028b88165848c240165848f01960009','1','4028b881647da6f201647daccbb90011');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde66a653480166a68336ab0041','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabaf42000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('40289fde66a653480166a68336e30048','4028b88165848c240165848f01960009','2','4028b88165f136260165f2284ac20098');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647da95c0f0036','4028b88165848c240165848f01960009','1','4028b881647da6f201647da95c0f0032');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378000c','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa3378000c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378000d','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa3378000d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378000e','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa3378000e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378000p','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabf784000p');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378000x','4028b88165848c240165848f01960009','1','4028b881647da6f201647dabf784000x');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa337800cd','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx0d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa3378zx0c','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx0c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daa337xc12f','4028b88165848c240165848f01960009','1','4028b881647da6f201647daa337xc00f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daazx7zx00f','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa337zx00f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647dac4994600a','4028b88165848c240165848f01960009','2','4028b881647da6f201647dac499600za');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647dac4996000a','4028b88165848c240165848f01960009','2','4028b881647da6f201647dac4996000a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647dac4996000e','4028b88165848c240165848f01960009','1','4028b881647da6f201647dac4996000e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647dac49zx56za','4028b88165848c240165848f01960009','2','4028b881647da6f201647dac499656za');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b881647da6f201647daccbzxzx11','4028b88165848c240165848f01960009','2','4028b881647da6f201647daccbb9zx11');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816493b649016493bee1f4007h','4028b88165848c240165848f01960009','1','4028b8816493b649016493bee1f40015');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88165848c240165848f01960xc0','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f70002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7002c','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7002c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f70043','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f70005');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7004z','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7000b');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700cf','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f70003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700p1','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f700x2');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700p2','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f70000');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700p3','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f70001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700zx','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f70004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f700zz','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7000x');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7cx2a','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv2a');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7cx2c','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv2e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7cx2e','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv2c');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816614934f016614a812f7zx2e','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7002e');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169c758360169c7846f030001','4028b88165848c240165848f01960009','1','4028b88169c758360169c7846f030001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975a8f0001','4028b88165848c240165848f01960009','1','4028b88169d695860169d6975a8f0001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975a8fzvc2','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8f0vc2');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975a8fzvc3','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8f0vc3');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975a8fzvc4','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8f0vc4');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975a8zxc02','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8fxc02');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975azx0002','4028b88165848c240165848f01960009','1','4028b88169d695860169d6975a8f0002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b88169d695860169d6975azx0vc1','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8f0vc1');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816zxc95860169d6975azx0vc1','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc001');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816zxc95860169d6975azx0vc2','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc002');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816zxc95860169d6975azx0vc3','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc003');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816zxc95860169d6975azx0vc4','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc004');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('4028b8816zxc95860169d6975azx0vc5','4028b88165848c240165848f01960009','2','4028b881zxcda6f201647daa234xc005');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('5028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('5062f5ce927c47bb935025d2460130b0','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx2t');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('6028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3f');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('63385b64d67949f8a42acfc3718e3882','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx4i');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960009','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812fzxv3j');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960993','4028b88165848c240165848f01960009','1','4028b8816614934f016614a812f7002d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960994','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f70021');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960995','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f70020');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960996','4028b88165848c240165848f01960009','2','4028b8816614934f016614a812f70022');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960997','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e02a4037g');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960998','4028b88165848c240165848f01960009','2','40289f2565db4d720165ea9e02a4037h');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('7028b88165848c240165848f01960x34','4028b88165848c240165848f01960009','2','4028b88169d695860169d6975a8fxcb3');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('8600d7040607404fbfa6b977d49a9cb5','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx2d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('a63c72f95e40421e9647afdbc9906e2e','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx0d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('be227cd4c91d4e1381bcc8f2872fd26a','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx1d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('cfe75313a171487f9ec5f36c925162ae','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx4d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('f4ed00546c3b49fa900f8d5547b8432e','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx3d');
INSERT INTO WD_SYS_ROLEPOWER (id,role_id,power_type,power_id) VALUES('f874d4705d3541eb8a655865467681e6','4028b88165848c240165848f01960009','2','4028b881647da6f201647daa3378zx5i');

 CREATE TABLE WD_SYS_TIMETASK (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    job_name NVARCHAR(255)  NULL ,
    description NVARCHAR(255)  NULL ,
    cron_expression NVARCHAR(255)  NULL ,
    method_name NVARCHAR(255)  NULL ,
    bean_class NVARCHAR(255)  NULL ,
    is_concurrent NVARCHAR(5)  NULL ,
    job_status NVARCHAR(5)  NULL ,
    job_group NVARCHAR(50)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL 
);
 CREATE TABLE WD_SYS_USER (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    user_cde NVARCHAR(15)  NULL ,
    user_name NVARCHAR(30)  NULL ,
    password NVARCHAR(100)  NULL ,
    isactive NVARCHAR(1)  NULL ,
    salt NVARCHAR(100)  NULL ,
    org_id NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    update_user NVARCHAR(32)  NULL ,
    islocked NVARCHAR(1)  NULL ,
    company NVARCHAR(50)  NULL ,
    department NVARCHAR(50)  NULL ,
    mail NVARCHAR(50)  NULL ,
    parent_id NVARCHAR(32)  NULL ,
    c_position NVARCHAR(50)  NULL ,
    user_type NVARCHAR(10)  NULL ,
    update_password_time DATETIME  NULL ,
    update_password_user NVARCHAR(32)  NULL ,
    is_init NVARCHAR(1)  NULL ,
    head_photo NVARCHAR(50)  NULL ,
    mobile_phone NVARCHAR(15)  NULL ,
    area_code NVARCHAR(5)  NULL ,
    telephone NVARCHAR(10)  NULL ,
    gender NVARCHAR(2)  NULL ,
    employ_type NVARCHAR(20)  NULL ,
    union_id NVARCHAR(50)  NULL ,
    third_party_code NVARCHAR(50)  NULL ,
    wd_model_id NVARCHAR(32)  NULL ,
    wxwork_user_id NVARCHAR(100)  NULL ,
    is_sync NVARCHAR(2)  NULL ,
    browse_password NVARCHAR(20)  NULL ,
    is_update_browse NVARCHAR(2)  NULL 
);
INSERT INTO WD_SYS_USER (id,user_cde,user_name,password,isactive,salt,org_id,create_time,update_time,create_user,update_user,islocked,company,department,mail,parent_id,c_position,user_type,update_password_time,update_password_user,is_init,head_photo,mobile_phone,area_code,telephone,gender,employ_type,union_id,third_party_code,wd_model_id,wxwork_user_id,is_sync,browse_password,is_update_browse) VALUES('4028b88163953a1b01639563f09c0005','admin','超级管理员','56bb4c5181f624da71e4d1f9e2da39ca','1','28acb14d13421c4faccde461f71f95be','4028818d81f5b9060181f6d192a90006','2018-05-25 11:42:25','2025-04-16 17:59:30','4028b88163953a1b01639563f09c0005',null,'1','北京','4028818d81f5b9060181f6d280350008','','0','402881876ec643e8016ec67946ae0002','USER','2020-05-18 18:04:04','','1','avatar_image_4.png','','','','2','','',null,null,null,null,null,null);

 CREATE TABLE WD_SYS_USER_ORG (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    create_time DATETIME  NULL ,
    create_user NVARCHAR(32)  NULL ,
    department_code NVARCHAR(50)  NULL ,
    department_id NVARCHAR(32)  NULL ,
    department_name NVARCHAR(100)  NULL ,
    is_system NVARCHAR(1)  NULL ,
    org_code NVARCHAR(50)  NULL ,
    org_id NVARCHAR(32)  NULL ,
    org_name NVARCHAR(100)  NULL ,
    user_code NVARCHAR(50)  NULL ,
    user_id NVARCHAR(32)  NULL ,
    user_name NVARCHAR(100)  NULL 
);
 CREATE TABLE WD_SYS_USERONLINE (
    id NVARCHAR(50)  NOT NULL  PRIMARY KEY ,
    user_id NVARCHAR(32)  NULL ,
    host NVARCHAR(100)  NULL ,
    system_host NVARCHAR(100)  NULL ,
    user_agent NVARCHAR(MAX)  NULL ,
    status NVARCHAR(50)  NULL ,
    start_timestsamp DATETIME  NULL ,
    last_access_time DATETIME  NULL ,
    online_timeout NVARCHAR(20)  NULL ,
    online_session NVARCHAR(MAX)  NULL ,
    create_user NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    update_user NVARCHAR(32)  NULL ,
    update_time DATETIME  NULL ,
    remarks NVARCHAR(255)  NULL 
);
 CREATE TABLE WD_SYS_USERROLE (
    id NVARCHAR(32)  NOT NULL  PRIMARY KEY ,
    role_id NVARCHAR(32)  NULL ,
    user_id NVARCHAR(32)  NULL ,
    create_time DATETIME  NULL ,
    wd_model_id NVARCHAR(32)  NULL 
);
INSERT INTO WD_SYS_USERROLE (id,role_id,user_id,create_time,wd_model_id) VALUES('40289fde666bc1a601666bc53e8200bf','4028b88165848c240165848f01960009','4028b88163953a1b01639563f09c0005','2018-10-13 12:53:01',null);

