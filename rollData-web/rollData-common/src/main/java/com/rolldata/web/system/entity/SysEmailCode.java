package com.rolldata.web.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.rolldata.core.common.entity.IdEntity;

/**
 * 
 * @Title: SysEmailCode
 * @Description: 邮箱验证码记录
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月18日
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_email_code")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysEmailCode extends IdEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6565086734420593750L;

	/**
	 * 用户登陆名
	 */
	private String userCode;
	
	/**
	 * 邮箱地址
	 */
	private String toAddress;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**  
	 * 获取用户登陆名  
	 * @return userCode 用户登陆名  
	 */
	@Column(name = "user_code", length = 20)
	public String getUserCode() {
		return userCode;
	}
	
	/**  
	 * 设置用户登陆名  
	 * @param userCode 用户登陆名  
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**  
	 * 获取邮箱地址  
	 * @return toAddress 邮箱地址  
	 */
	@Column(name = "to_address", length = 50)
	public String getToAddress() {
		return toAddress;
	}

	/**  
	 * 设置邮箱地址  
	 * @param toAddress 邮箱地址  
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**  
	 * 获取验证码  
	 * @return verifyCode 验证码  
	 */
	@Column(name = "verify_code", length = 10)
	public String getVerifyCode() {
		return verifyCode;
	}
	
	/**  
	 * 设置验证码  
	 * @param verifyCode 验证码  
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**  
	 * 获取创建用户  
	 * @return createUser 创建用户  
	 */
	@Column(name = "create_user", length = 32)
	public String getCreateUser() {
		return createUser;
	}

	/**  
	 * 设置创建用户  
	 * @param createUser 创建用户  
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	/**  
	 * 获取创建时间  
	 * @return createTime 创建时间  
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	
	/**  
	 * 设置创建时间  
	 * @param createTime 创建时间  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
