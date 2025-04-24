package com.rolldata.web.system.pojo;

/**
 * 用户修改密码前台传后台josn格式
 * @author shenshilong
 * @createDate 2018-6-29
 */
public class UserPojo extends EventJson{

	private static final long serialVersionUID = -5521147783967204200L;

	/*用户ID*/
	private String userId;
	
	/*旧密码*/
	private String oldPassword;
	
	/*新密码*/
	private String newPassword;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
