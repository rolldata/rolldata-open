package com.rolldata.web.system.pojo;

import java.util.List;

import com.rolldata.web.system.entity.SysMessage;

/**
 * 消息交互实体
 * @Title: MessagePojo
 * @Description: 消息交互实体
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年3月5日
 * @version V1.0
 */
public class MessagePojo extends SysMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2474986257494148025L;

	/**
	 * 批量发送用户集合
	 */
	private List<String> toUsers;

	/**
	 * 是否发送系统消息，默认发送
	 */
	private Boolean isSendSystem = true;
	
	/**
	 * 是否发送微信
	 */
	private Boolean isSendWechat = false;
	
	/**
	 * 是否发送邮件
	 */
	private Boolean isSendEmail = false;
	
	/** 单个收件人*/
    private String toAddress;
    
	 /** 单个抄送人地址*/
    private String ccAddress;
    
    /**编码格式,默认UTF-8*/
    private String charset="UTF-8";
    
	/**  
	 * 获取批量发送用户集合  
	 * @return toUsers 批量发送用户集合  
	 */
	public List<String> getToUsers() {
		return toUsers;
	}

	/**  
	 * 设置批量发送用户集合  
	 * @param toUsers 批量发送用户集合  
	 */
	public void setToUsers(List<String> toUsers) {
		this.toUsers = toUsers;
	}

	/**  
	 * 获取是否发送微信  
	 * @return isSendWechat 是否发送微信  
	 */
	public Boolean getIsSendWechat() {
		return isSendWechat;
	}

	/**  
	 * 设置是否发送微信  
	 * @param isSendWechat 是否发送微信  
	 */
	public void setIsSendWechat(Boolean isSendWechat) {
		this.isSendWechat = isSendWechat;
	}
	
	/**  
	 * 获取是否发送邮件  
	 * @return isSendEmail 是否发送邮件  
	 */
	public Boolean getIsSendEmail() {
		return isSendEmail;
	}
	
	/**  
	 * 设置是否发送邮件  
	 * @param isSendEmail 是否发送邮件  
	 */
	public void setIsSendEmail(Boolean isSendEmail) {
		this.isSendEmail = isSendEmail;
	}

	/**  
	 * 获取是否发送微信  
	 * @return isSendSystem 是否发送微信  
	 */
	public Boolean getIsSendSystem() {
		return isSendSystem;
	}
	

	/**  
	 * 设置是否发送微信  
	 * @param isSendSystem 是否发送微信  
	 */
	public void setIsSendSystem(Boolean isSendSystem) {
		this.isSendSystem = isSendSystem;
	}

	/**  
	 * 获取单个抄送人地址  
	 * @return ccAddress 单个抄送人地址  
	 */
	public String getCcAddress() {
		return ccAddress;
	}
	

	/**  
	 * 设置单个抄送人地址  
	 * @param ccAddress 单个抄送人地址  
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	/**  
	 * 获取编码格式默认UTF-8  
	 * @return charset 编码格式默认UTF-8  
	 */
	public String getCharset() {
		return charset;
	}
	
	/**  
	 * 设置编码格式默认UTF-8  
	 * @param charset 编码格式默认UTF-8  
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**  
	 * 获取单个收件人  
	 * @return toAddress 单个收件人  
	 */
	public String getToAddress() {
		return toAddress;
	}
	
	/**  
	 * 设置单个收件人  
	 * @param toAddress 单个收件人  
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
}
