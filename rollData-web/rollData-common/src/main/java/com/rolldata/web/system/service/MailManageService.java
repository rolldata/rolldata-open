package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.MailManagePojo;
import com.rolldata.web.system.util.email.EmailResult;

/**
 * @Title: MailManageService
 * @Description: 邮箱管理服务接口
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年4月1日
 * @version V1.0
 */
public interface MailManageService {
	
	/**
	 * 查询邮箱设置信息
	 * @return
	 * @throws Exception
	 */
	public MailManagePojo queryMailManageInfo() throws Exception;
	
	/**
	 * 修改邮箱设置信息
	 * @param mailManagePojo
	 * @return
	 * @throws Exception
	 */
	public MailManagePojo updateMailManage(MailManagePojo mailManagePojo) throws Exception;

	/**
	 * 测试邮件发送
	 * @param mailManagePojo
	 * @return
	 * @throws Exception
	 */
	public EmailResult testMail(MailManagePojo mailManagePojo) throws Exception;
}
