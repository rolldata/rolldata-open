package com.rolldata.web.system.pojo;

/**
 * 用户基本信息类
 * @author shenshilong
 * @createDate 2018-7-28
 */

public class AllUserList implements java.io.Serializable{

	private static final long serialVersionUID = 6646543966479298978L;
	
	private String userId;
	
	private String userCde;
	
	private String userName;
	
	private String status;
	
	private String mail;
	
	private String loginInformation;

    /**
     * 部门名称
     */
	private String departmentName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCde() {
		return userCde;
	}

	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLoginInformation() {
		return loginInformation;
	}

	public void setLoginInformation(String loginInformation) {
		this.loginInformation = loginInformation;
	}

    /**
     * 获取 部门名称
     *
     * @return departmentName 部门名称
     */
    public String getDepartmentName() {
        return this.departmentName;
    }

    /**
     * 设置 部门名称
     *
     * @param departmentName 部门名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
