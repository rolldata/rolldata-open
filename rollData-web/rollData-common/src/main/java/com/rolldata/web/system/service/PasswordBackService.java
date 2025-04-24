package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.EmailCodePojo;

/**
 * 
 * @Title: PasswordBackService
 * @Description: 密码找回服务类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月18日
 * @version V1.0
 */
public interface PasswordBackService {

	/**
	 * 发送邮箱验证码并保存
	 * @param emailCodePojo
	 */
	public void sendEmailVerifyCode(EmailCodePojo emailCodePojo) throws Exception;

	/**
	 * 验证邮箱验证码
	 * @param emailCodePojo
	 * @throws Exception
	 */
	public void verifyEmailCode(EmailCodePojo emailCodePojo) throws Exception;

	/**
	 * 验证邮箱重置密码
	 * @param emailCodePojo
	 * @throws Exception
	 */
	public void updateResetPassword(EmailCodePojo emailCodePojo) throws Exception;

}
