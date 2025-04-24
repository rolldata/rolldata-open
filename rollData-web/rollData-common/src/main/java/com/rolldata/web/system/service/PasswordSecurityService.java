package com.rolldata.web.system.service;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.pojo.UserDetailedJson;

/**
 * 密码安全服务层
 * @Title: PasswordSecurityService
 * @Description: 密码安全服务层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
public interface PasswordSecurityService {

	/**
	 * 查询密码安全信息
	 * @return
	 * @throws Exception
	 */
	public PasswordSecurityPojo queryPasswordSecurity() throws Exception;

	/**
	 * 修改密码安全信息
	 * @param passwoPojo
	 * @return
	 * @throws Exception
	 */
	public PasswordSecurityPojo updatePasswordSecurity(PasswordSecurityPojo passwoPojo) throws Exception;

	/**
	 * 验证个人访问密码
	 * @param rUserJson
	 * @param ajax
	 * @return
	 * @throws Exception
	 */
	public AjaxJson queryVerifyIsBrowsePassword(UserDetailedJson rUserJson, AjaxJson ajax) throws Exception;

	/**
	 * 用户修改访问资源密码，状态改为已修改过默认的访问密码
	 * @param rUserJson
	 * @param ajax
	 * @throws Exception
	 */
	public AjaxJson updateIsBrowsePassword(UserDetailedJson rUserJson, AjaxJson ajax) throws Exception;
}
