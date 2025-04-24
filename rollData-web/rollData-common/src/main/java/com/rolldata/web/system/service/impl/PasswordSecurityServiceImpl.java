package com.rolldata.web.system.service.impl;

import java.util.List;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.dao.UserDao;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.UserDetailedJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.service.PasswordSecurityService;

/**
 * 密码安全服务层实现
 * @Title: PasswordSecurityServiceImpl
 * @Description: 密码安全服务层实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
@Service("passwordSecurityService")
@Transactional
public class PasswordSecurityServiceImpl implements PasswordSecurityService{
	
	@Autowired
    private SysConfigDao sysConfigDao;

	@Autowired
	private UserDao userDao;

	/**
	 * 查询密码安全信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public PasswordSecurityPojo queryPasswordSecurity() throws Exception {
		//如果内存中有值不进行查库
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		if(StringUtil.isNotEmpty(passwordSecurityMap)) {
			return passwordSecurityMap;
		}else {
			List<SysConfig> sysConfigList = sysConfigDao.querySysConfigByType(SysConfig.TYPE_SECURITY);
			PasswordSecurityPojo passwordSecurityPojo = new PasswordSecurityPojo();
			for (int i = 0; i < sysConfigList.size(); i++) {
				SysConfig sysConfig = sysConfigList.get(i);
				String value = sysConfig.getValue();
				switch (sysConfig.getName()) {
				case SysConfig.PASSWORD_LENGTH:
					passwordSecurityPojo.setPsdLength(value);
					break;
				case SysConfig.PASSWORD_SETTING:
					passwordSecurityPojo.setPsdSetting(value);
					break;
				case SysConfig.PASSWORD_PERIOD:
					passwordSecurityPojo.setPsdPeriod(value);
					break;
				case SysConfig.PASSWORD_REQUEST:
					passwordSecurityPojo.setPsdRequest(value);
					break;
				case SysConfig.PASSWORD_ERRNUMBER:
					passwordSecurityPojo.setPsdErrNumber(value);
					break;
				case SysConfig.PASSWORD_PERIOD_IS:
					passwordSecurityPojo.setPsdPeriodIs(value);
					break;
				case SysConfig.PASSWORD_ERRNUMBER_IS:
					passwordSecurityPojo.setPsdErrNumberIs(value);
					break;
				case SysConfig.PASSWORD_VALIDATE_IS:
					passwordSecurityPojo.setPsdValidateIs(value);
					break;
				case SysConfig.PASSWORD_INIT_IS:
					passwordSecurityPojo.setPsdInitIs(value);
					break;	
				case SysConfig.LOGIN_LIMIT:
					passwordSecurityPojo.setIsLimitLogin(value);
					break;
				case SysConfig.BROWSE_PASSWORD_IS:
					passwordSecurityPojo.setIsBrowsePassword(value);
					break;
				case SysConfig.BROWSE_PASSWORD:
					passwordSecurityPojo.setBrowsePassword(value);
					break;
				case SysConfig.LOGINNUM_PASSWORD_IS:
					passwordSecurityPojo.setIsLoginNum(value);
					break;
				case SysConfig.LOGINNUM_PASSWORD:
					passwordSecurityPojo.setLoginNum(value);
					break;
				default:
					break;
				}
			}
			//查询后放置内存中的
			Constants.property.put(Constants.passwordSecurity, passwordSecurityPojo);
			return passwordSecurityPojo;
		}
	}

	/**
	 * 修改密码安全信息
	 * @param passwoPojo
	 * @return
	 * @throws Exception
	 */
	@Override
	public PasswordSecurityPojo updatePasswordSecurity(PasswordSecurityPojo passwoPojo) throws Exception {
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_LENGTH, passwoPojo.getPsdLength());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_SETTING, passwoPojo.getPsdSetting());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_PERIOD, passwoPojo.getPsdPeriod());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_REQUEST, passwoPojo.getPsdRequest());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_ERRNUMBER, passwoPojo.getPsdErrNumber());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_PERIOD_IS, passwoPojo.getPsdPeriodIs());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_ERRNUMBER_IS,passwoPojo.getPsdErrNumberIs());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_VALIDATE_IS,passwoPojo.getPsdValidateIs());
		sysConfigDao.updateValueByName(SysConfig.PASSWORD_INIT_IS,passwoPojo.getPsdInitIs());
		sysConfigDao.updateValueByName(SysConfig.LOGIN_LIMIT,passwoPojo.getIsLimitLogin());
		sysConfigDao.updateValueByName(SysConfig.BROWSE_PASSWORD_IS,passwoPojo.getIsBrowsePassword());
		sysConfigDao.updateValueByName(SysConfig.BROWSE_PASSWORD,passwoPojo.getBrowsePassword());
		//修改同时修改内存中的
		Constants.property.put(Constants.passwordSecurity, passwoPojo);
		return passwoPojo;
	}

	/**
	 * 验证个人访问密码
	 * @param rUserJson
	 * @param ajax
	 * @return
	 * @throws Exception
	 */
	@Override
	public AjaxJson queryVerifyIsBrowsePassword(UserDetailedJson rUserJson, AjaxJson ajax) throws Exception {
		//根据用户编码获取配置的查询密码
		SysUser sysUser = userDao.getUserByUserCde(rUserJson.getUserCde());
		//用户输入的密码
		String browsePassword = rUserJson.getBrowsePassword();
		PasswordSecurityPojo passwordPojo = this.queryPasswordSecurity();
		//开启访问密码再验证
		if(StringUtil.isNotEmpty(passwordPojo.getIsBrowsePassword()) &&
				passwordPojo.getIsBrowsePassword().equals("1")){
			if(StringUtil.isNotEmpty(sysUser.getBrowsePassword())){
				//对比查询密码
				if(browsePassword.equals(sysUser.getBrowsePassword())){
					ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.password.browse.success"));
				}else{
					ajax.setSuccessAndMsg(false,MessageUtils.getMessage("common.sys.password.browse.error"));
				}
			}else{
				//如果没有设置查询密码，使用默认密码
				if(browsePassword.equals(passwordPojo.getBrowsePassword())){
					ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.password.browse.success"));
				}else{
					ajax.setSuccessAndMsg(false,MessageUtils.getMessage("common.sys.password.browse.error"));
				}
			}
		}else{
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.password.browse.success"));
		}
		return ajax;
	}

	/**
	 * 用户修改访问资源密码，状态改为已修改过默认的访问密码
	 * @param rUserJson
	 * @param ajax
	 * @throws Exception
	 */
	@Override
	public AjaxJson updateIsBrowsePassword(UserDetailedJson rUserJson, AjaxJson ajax) throws Exception {
		//先校验旧密码
		//根据用户编码获取配置的查询密码
		SysUser sysUser = userDao.getUserByUserCde(rUserJson.getUserCde());
		//用户输入的新密码
		String browsePassword = rUserJson.getBrowsePassword();
		//用户输入的旧密码
		String oldBrowsePassword = rUserJson.getOldBrowsePassword();
		PasswordSecurityPojo passwordPojo = this.queryPasswordSecurity();
		//为空就是没设置过访问密码，校验默认的密码
		if(StringUtil.isNotEmpty(sysUser.getBrowsePassword())){
			//设置过访问密码的进行个人的校验
			if(oldBrowsePassword.equals(sysUser.getBrowsePassword())){
				userDao.updateIsBrowsePassword(rUserJson.getUserCde(),browsePassword);
			}else{
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error")+"："+MessageUtils.getMessage("common.sys.password.error"));
			}
		}else{
			//没设置过的校验默认的密码
			if(oldBrowsePassword.equals(passwordPojo.getBrowsePassword())){
				userDao.updateIsBrowsePassword(rUserJson.getUserCde(),browsePassword);
			}else{
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error")+"："+MessageUtils.getMessage("common.sys.password.error"));
			}
		}
		return ajax;
	}
}
