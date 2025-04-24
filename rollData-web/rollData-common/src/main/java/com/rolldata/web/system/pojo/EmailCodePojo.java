package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysEmailCode;

public class EmailCodePojo extends SysEmailCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5917048558278086581L;

	/**新密码*/
	private String newPassword;
	
	/**确认新密码*/
	private String confirmPassword;

	/**  
	 * 获取新密码  
	 * @return newPassword 新密码  
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**  
	 * 设置新密码  
	 * @param newPassword 新密码  
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**  
	 * 获取确认新密码  
	 * @return confirmPassword 确认新密码  
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**  
	 * 设置确认新密码  
	 * @param confirmPassword 确认新密码  
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
