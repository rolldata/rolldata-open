package com.rolldata.web.system.pojo;

/**
 * 
 * @Title: MailUserInfo
 * @Description: 收件人交互实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class MailUserInfo {
	

	/** 收件人*/
	private String toAddress;
	/** 显示姓名*/
	private String displayName;
	
	public MailUserInfo() {
		super();
	}
	
	public MailUserInfo(String toAddress, String displayName) {
		super();
		this.toAddress = toAddress;
		this.displayName = displayName;
	}
	
	/**  
	 * 获取收件人  
	 * @return toAddress 收件人  
	 */
	public String getToAddress() {
		return toAddress;
	}
	
	/**  
	 * 设置收件人  
	 * @param toAddress 收件人  
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
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
	
}
