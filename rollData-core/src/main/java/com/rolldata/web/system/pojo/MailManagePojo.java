package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 
 * @Title: MailManagePojo
 * @Description: 邮箱管理信息
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年4月1日
 * @version V1.0
 */
public class MailManagePojo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3614357658899721429L;
	
	/**
	 * SMTP服务器
	 */
	private String SMTPServer;
	
	/**
	 * 端口号
	 */
	private String serverPort;
	
	/**
	 * 发件人地址
	 */
	private String senderAddress;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 显示姓名
	 */
	private String displayName;
	
	/**
	 * 是否启用SSL安全连接1:是，0:否
	 */
	private String sslIs;

	/**
	 * 是否验证通过1:是，0:否 
	 */
	private String verify;
	
	/** 单个收件人*/
    private String toAddress;
	
	/**  
	 * 获取sMTPServer  
	 * @return sMTPServer sMTPServer  
	 */
	public String getSMTPServer() {
		return SMTPServer;
	}
	

	/**  
	 * 设置sMTPServer  
	 * @param sMTPServer sMTPServer  
	 */
	public void setSMTPServer(String sMTPServer) {
		SMTPServer = sMTPServer;
	}
	

	/**  
	 * 获取端口号  
	 * @return serverPort 端口号  
	 */
	public String getServerPort() {
		return serverPort;
	}
	

	/**  
	 * 设置端口号  
	 * @param serverPort 端口号  
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	

	/**  
	 * 获取发件人地址  
	 * @return senderAddress 发件人地址  
	 */
	public String getSenderAddress() {
		return senderAddress;
	}
	

	/**  
	 * 设置发件人地址  
	 * @param senderAddress 发件人地址  
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	

	/**  
	 * 获取密码  
	 * @return password 密码  
	 */
	public String getPassword() {
		return password;
	}
	

	/**  
	 * 设置密码  
	 * @param password 密码  
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	/**  
	 * 获取显示姓名  
	 * @return displayName 显示姓名  
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**  
	 * 设置显示姓名  
	 * @param displayName 显示姓名  
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**  
	 * 获取是否开启ssl1:是，0:否  
	 * @return sslIs 是否开启ssl1:是，0:否  
	 */
	public String getSslIs() {
		return sslIs;
	}
	
	/**  
	 * 设置是否开启ssl1:是，0:否  
	 * @param sslIs 是否开启ssl1:是，0:否  
	 */
	public void setSslIs(String sslIs) {
		this.sslIs = sslIs;
	}

	/**  
	 * 获取是否验证通过1:是，0:否  
	 * @return verify 是否验证通过1:是，0:否  
	 */
	public String getVerify() {
		return verify;
	}
	
	/**  
	 * 设置是否验证通过1:是，0:否  
	 * @param verify 是否验证通过1:是，0:否  
	 */
	public void setVerify(String verify) {
		this.verify = verify;
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

