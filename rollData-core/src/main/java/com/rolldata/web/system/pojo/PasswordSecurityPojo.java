package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 密码安全交互类
 * @Title: PasswordSecurityPojo
 * @Description: 密码安全交互类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
public class PasswordSecurityPojo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7500889487124374060L;

	/**
	 * 密码长度 默认6位
	 */
	private String psdLength = "6";
	
	/**
	 * 密码组合配置 1:数字（默认），2:字母+数字，3:特殊字符+字母+数字
	 */
	private String psdSetting = "1";
	
	/**
	 * 修改密码期限 单位（天）
	 */
	private String psdPeriod = "10";
	
	/**
	 * 是否开启修改密码期限 1:是，0:否
	 */
	private String psdPeriodIs = "0";
	
	/**
	 * 忘记密码是否发送请求 1:是，0:否
	 */
	private String psdRequest  = "0";
	
	/**
	 * 输入密码错误锁定用户次数
	 */
	private String psdErrNumber = "3";

	/**
	 * 是否启用输入密码错误锁定用户次数 1:是，0:否
	 */
	private String psdErrNumberIs  = "0";
	
	/**
	 * 是否开启验证码1:是，0:否
	 */
	private String psdValidateIs  = "0";
	
	/**
	 * 是否启用强制修改初始密码1:是，0:否
	 */
	private String psdInitIs  = "0";
	
	/**
	 * 是否限制一处登陆1:是，0:否
	 */
	private String isLimitLogin = "0";

	/**是否开启访问密码1:是，0:否*/
	private String isBrowsePassword = "0";

	/**资源访问密码*/
	private String browsePassword;

	/**
	 * 是否限制登录人数1:是，0:否
	 */
	private String isLoginNum = "0";

	/**
	 * 限制的登录人数
	 */
	private String loginNum = "2";

	/**  
	 * 获取密码长度  
	 * @return psdLength 密码长度  
	 */
	public String getPsdLength() {
		return psdLength;
	}
	
	/**  
	 * 设置密码长度  
	 * @param psdLength 密码长度  
	 */
	public void setPsdLength(String psdLength) {
		this.psdLength = psdLength;
	}

	/**  
	 * 获取密码组合配置1:数字，2:字母+数字，3:特殊字符+字母+数字  
	 * @return psdSetting 密码组合配置1:数字，2:字母+数字，3:特殊字符+字母+数字  
	 */
	public String getPsdSetting() {
		return psdSetting;
	}
	
	/**  
	 * 设置密码组合配置1:数字，2:字母+数字，3:特殊字符+字母+数字  
	 * @param psdSetting 密码组合配置1:数字，2:字母+数字，3:特殊字符+字母+数字  
	 */
	public void setPsdSetting(String psdSetting) {
		this.psdSetting = psdSetting;
	}
	
	/**  
	 * 获取修改密码期限  
	 * @return psdPeriod 修改密码期限  
	 */
	public String getPsdPeriod() {
		return psdPeriod;
	}
	
	/**  
	 * 设置修改密码期限  
	 * @param psdPeriod 修改密码期限  
	 */
	public void setPsdPeriod(String psdPeriod) {
		this.psdPeriod = psdPeriod;
	}
	
	/**  
	 * 获取忘记密码是否发送请求1:是，0:否  
	 * @return psdRequest 忘记密码是否发送请求1:是，0:否  
	 */
	public String getPsdRequest() {
		return psdRequest;
	}
	
	/**  
	 * 设置忘记密码是否发送请求1:是，0:否  
	 * @param psdRequest 忘记密码是否发送请求1:是，0:否  
	 */
	public void setPsdRequest(String psdRequest) {
		this.psdRequest = psdRequest;
	}

	/**  
	 * 获取输入密码错误锁定用户次数  
	 * @return psdErrNumber 输入密码错误锁定用户次数  
	 */
	public String getPsdErrNumber() {
		return psdErrNumber;
	}

	/**  
	 * 设置输入密码错误锁定用户次数  
	 * @param psdErrNumber 输入密码错误锁定用户次数  
	 */
	public void setPsdErrNumber(String psdErrNumber) {
		this.psdErrNumber = psdErrNumber;
	}

	/**  
	 * 获取修改密码期限1:是，0:否  
	 * @return psdPeriodIs 修改密码期限1:是，0:否  
	 */
	public String getPsdPeriodIs() {
		return psdPeriodIs;
	}

	/**  
	 * 设置修改密码期限1:是，0:否  
	 * @param psdPeriodIs 修改密码期限1:是，0:否  
	 */
	public void setPsdPeriodIs(String psdPeriodIs) {
		this.psdPeriodIs = psdPeriodIs;
	}
	
	/**  
	 * 获取是否启用输入密码错误锁定用户次数1:是，0:否  
	 * @return psdErrNumberIs 是否启用输入密码错误锁定用户次数1:是，0:否  
	 */
	public String getPsdErrNumberIs() {
		return psdErrNumberIs;
	}

	/**  
	 * 设置是否启用输入密码错误锁定用户次数1:是，0:否  
	 * @param psdErrNumberIs 是否启用输入密码错误锁定用户次数1:是，0:否  
	 */
	public void setPsdErrNumberIs(String psdErrNumberIs) {
		this.psdErrNumberIs = psdErrNumberIs;
	}

	/**  
	 * 获取是否开启验证码1:是，0:否  
	 * @return psdValidateIs 是否开启验证码1:是，0:否  
	 */
	public String getPsdValidateIs() {
		return psdValidateIs;
	}
	
	/**  
	 * 设置是否开启验证码1:是，0:否  
	 * @param psdValidateIs 是否开启验证码1:是，0:否  
	 */
	public void setPsdValidateIs(String psdValidateIs) {
		this.psdValidateIs = psdValidateIs;
	}

	/**  
	 * 获取是否启用强制修改初始密码1:是，0:否  
	 * @return psdInitIs 是否启用强制修改初始密码1:是，0:否  
	 */
	public String getPsdInitIs() {
		return psdInitIs;
	}
	
	/**  
	 * 设置是否启用强制修改初始密码1:是，0:否  
	 * @param psdInitIs 是否启用强制修改初始密码1:是，0:否  
	 */
	public void setPsdInitIs(String psdInitIs) {
		this.psdInitIs = psdInitIs;
	}

	/**  
	 * 获取是否限制一处登陆1:是，0:否  
	 * @return isLimitLogin 是否限制一处登陆1:是，0:否  
	 */
	public String getIsLimitLogin() {
		return isLimitLogin;
	}
	
	/**
	 * 设置是否限制一处登陆1:是，0:否  
	 * @param isLimitLogin 是否限制一处登陆1:是，0:否  
	 */
	public void setIsLimitLogin(String isLimitLogin) {
		this.isLimitLogin = isLimitLogin;
	}

	/**
	 * 获取 是否开启访问密码1:是，0:否
	 *
	 * @return isBrowsePassword 是否开启访问密码1:是，0:否
	 */
	public String getIsBrowsePassword() {
		return this.isBrowsePassword;
	}

	/**
	 * 设置 是否开启访问密码1:是，0:否
	 *
	 * @param isBrowsePassword 是否开启访问密码1:是，0:否
	 */
	public void setIsBrowsePassword(String isBrowsePassword) {
		this.isBrowsePassword = isBrowsePassword;
	}

	/**
	 * 获取 资源访问密码
	 *
	 * @return browsePassword 资源访问密码
	 */
	public String getBrowsePassword() {
		return this.browsePassword;
	}

	/**
	 * 设置 资源访问密码
	 *
	 * @param browsePassword 资源访问密码
	 */
	public void setBrowsePassword(String browsePassword) {
		this.browsePassword = browsePassword;
	}

	/**
	 * 获取 是否限制登录人数1:是，0:否
	 *
	 * @return isLoginNum 是否限制登录人数1:是，0:否
	 */
	public String getIsLoginNum() {
		return this.isLoginNum;
	}

	/**
	 * 设置 是否限制登录人数1:是，0:否
	 *
	 * @param isLoginNum 是否限制登录人数1:是，0:否
	 */
	public void setIsLoginNum(String isLoginNum) {
		this.isLoginNum = isLoginNum;
	}

	/**
	 * 获取 限制的登录人数
	 *
	 * @return loginNum 限制的登录人数
	 */
	public String getLoginNum() {
		return this.loginNum;
	}

	/**
	 * 设置 限制的登录人数
	 *
	 * @param loginNum 限制的登录人数
	 */
	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}
}
