package com.rolldata.web.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.pojo.MailManagePojo;
import com.rolldata.web.system.service.MailManageService;
import com.rolldata.web.system.util.PasswordUtil;
import com.rolldata.web.system.util.email.EmailResult;
import com.rolldata.web.system.util.email.EmailUtil;
/**
 * 
 * @Title: MailManageServiceImpl
 * @Description: 邮箱管理服务实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年4月1日
 * @version V1.0
 */
@Service("mailManageService")
@Transactional
public class MailManageServiceImpl implements MailManageService{

	@Autowired
    private SysConfigDao sysConfigDao;
	
	/**
	 * 查询邮箱设置信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public MailManagePojo queryMailManageInfo() throws Exception {
		//如果内存中有值不进行查库
		MailManagePojo mailManageMap = (MailManagePojo) Constants.property.get(Constants.mailManageInfo);
		if(StringUtil.isNotEmpty(mailManageMap)) {
			return mailManageMap;
		}else {
			List<SysConfig> sysConfigList = sysConfigDao.querySysConfigByType(SysConfig.TYPE_MAIL);
			MailManagePojo mailManagePojo = new MailManagePojo();
			for (int i = 0; i < sysConfigList.size(); i++) {
				SysConfig sysConfig = sysConfigList.get(i);
				String value = sysConfig.getValue();
				switch (sysConfig.getName()) {
				case SysConfig.MAIL_SMTPSERVER:
					mailManagePojo.setSMTPServer(value);
					break;
				case SysConfig.MAIL_SERVERPORT:
					mailManagePojo.setServerPort(value);
					break;
				case SysConfig.MAIL_SENDERADDRESS:
					mailManagePojo.setSenderAddress(value);
					break;
				case SysConfig.MAIL_PASSWORD:
					mailManagePojo.setPassword(value);
					break;
				case SysConfig.MAIL_DISPLAYNAME:
					mailManagePojo.setDisplayName(value);
					break;
				case SysConfig.MAIL_SSLIS:
					mailManagePojo.setSslIs(value);
					break;
				case SysConfig.MAIL_VERIFY:
					mailManagePojo.setVerify(value);
					break;
				default:
					break;
				}
			}
			//查询后放置内存中的
			Constants.property.put(Constants.mailManageInfo,mailManagePojo);
			return mailManagePojo;
		}
	}

	/**
	 * 修改邮箱设置信息
	 * @param mailManagePojo
	 * @return
	 * @throws Exception
	 */
	@Override
	public MailManagePojo updateMailManage(MailManagePojo mailManagePojo) throws Exception {
		MailManagePojo mailManageMap = (MailManagePojo) Constants.property.get(Constants.mailManageInfo);
		sysConfigDao.updateValueByName(SysConfig.MAIL_SMTPSERVER, mailManagePojo.getSMTPServer());
		sysConfigDao.updateValueByName(SysConfig.MAIL_SERVERPORT, mailManagePojo.getServerPort());
		sysConfigDao.updateValueByName(SysConfig.MAIL_SENDERADDRESS, mailManagePojo.getSenderAddress());

		// 设置缓存对象
        mailManageMap.setSMTPServer(mailManagePojo.getSMTPServer());
        mailManageMap.setServerPort(mailManagePojo.getServerPort());
        mailManageMap.setSenderAddress(mailManagePojo.getSenderAddress());
        mailManageMap.setPassword(mailManagePojo.getSenderAddress());
        mailManageMap.setDisplayName(mailManagePojo.getDisplayName());
        mailManageMap.setSslIs(mailManagePojo.getSslIs());

		if(!mailManagePojo.getPassword().equals(mailManageMap.getPassword())) {
			byte[] salt = PasswordUtil.getStaticSalt();
			//密码加密保存，用时解密
			String password = PasswordUtil.encrypt(mailManagePojo.getPassword(), mailManagePojo.getSenderAddress(),salt);
			sysConfigDao.updateValueByName(SysConfig.MAIL_PASSWORD, password);
			mailManageMap.setPassword(password);
		}
		sysConfigDao.updateValueByName(SysConfig.MAIL_DISPLAYNAME, mailManagePojo.getDisplayName());
		sysConfigDao.updateValueByName(SysConfig.MAIL_SSLIS,mailManagePojo.getSslIs());
		//修改同时修改内存中的
		Constants.property.put(Constants.mailManageInfo,mailManageMap);
		return mailManagePojo;
	}

	/**
	 * 测试邮件发送
	 * @param mailManagePojo
	 * @return
	 * @throws Exception
	 */
	@Override
	public EmailResult testMail(MailManagePojo mailManagePojo) throws Exception {
		String content = "<html lang=\"en\">" + 
				"<head>" + 
				"    <meta charset=\"UTF-8\">" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + 
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" + 
				"    <title>测试邮件</title>" +
				"    <style>" + 
				"    </style>" + 
				"</head>" + 
				"<body style=\"margin: 0;padding:0;width:100%;height:100%;background: #f1f1f1\">" + 
				"    <div class=\"big\" style=\"width: 100%;height: 500px;padding: 0;margin: 0;box-sizing: border-box;padding-top: 40px;background: #f1f1f1;padding-bottom: 220px;\">" +
				"        <div class=\"title\" style=\"margin:0 5%;padding:7px 20px;width: 80%;height:25px;line-height:25px;background: #009688;" + 
				"        color:#fff;font-family: '黑体';font-weight: bold\"></div>" +
				"        <div style=\"margin:0 5%;padding:0px 20px;width: 80%;height: 210px;background: #ffffff;border: 1px solid #e5e5e5\">     " + 
				"            <h3 style=\"font-weight: normal;font-size: 14px;color:sandybrown\">Hi,你好:</h3>" + 
				"            <div style=\"font-size: 12px;text-indent: 2em;line-height: 20px;padding-bottom: 40px;\">" + 
				"                此邮件为测试邮件，若成功收到本邮件则表示测试成功。" +
				"            </div>" + 
				"        </div>" + 
				"    </div>" + 
				"</body>" + 
				"</html>";
		String subject = "测试邮件";
		EmailUtil email = new EmailUtil();
		MailManagePojo mailManageMap = (MailManagePojo) Constants.property.get(Constants.mailManageInfo);
		mailManagePojo.setVerify(mailManageMap.getVerify());//页面不存值
		EmailResult result = email.testSend(mailManagePojo, mailManagePojo.getToAddress(), subject, content);
		if(result.isSuccess()) {
			//成功更新为通过
			mailManageMap.setVerify("1");
			sysConfigDao.updateValueByName(SysConfig.MAIL_VERIFY,"1");
		}else {
			mailManageMap.setVerify("0");
			sysConfigDao.updateValueByName(SysConfig.MAIL_VERIFY,"0");
		}
		//修改同时修改内存中的
		Constants.property.put(Constants.mailManageInfo,mailManageMap);
		return result;
	}

}
