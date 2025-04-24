package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

public class UserDetailedJson implements Serializable{
	
	private static final long serialVersionUID = -92557504363252323L;

	private String userId;
	
	private String userCde;
	
	private String userName;
	
	private String userType;
	
	private String isactive;
	
	private String islocked;
	
	private String company;
	
	private String department;

	/**
	 * 部门名称
	 */
	private String departmentName;
	
	private String position;

	/**
	 * 职位名称
	 */
	private String positionName;
	
	private String mail;
	
	/**手机*/
	private String mobilePhone;
	
	/**区号*/
	private String areaCode;
	
	/**固定电话*/
	private String telephone;
	
	/**性别:1为男性，2为女性*/
	private String gender;
	
	/**用工类别*/
	private String employType;
	
	private List<String> roles;
	
	private List<String> roleNames;
	
	private String orgId;
	
	private String pId;
	
	private String id;
	
	private String password;
	
	private String roleId;
	
	private String oldPassword;
	
	private String newPassword;
	
	private List<String> ids;
	
    /**
     * 头像
     */
	private String headPhoto;
	
	/**
	 * 组织名称
	 */
	private String orgName;
	
	/**
	 * 微信唯一id
	 */
	private String unionId;
	
	/**
	 * 创建人
	 */
	private String createUser;

    /**
     * 第三方用户编码或id
     */
    private String thirdPartyCode;

	/**旧的资源访问密码*/
	private String oldBrowsePassword;

	/**资源访问密码*/
	private String browsePassword;

	/**
	 * 回调地址
	 */
	private String tokenUrl;

	public UserDetailedJson () {
	}
	
	/**
	 * dao里用到
	 * @param userId
	 * @param userName
	 * @param orgId
	 * @param orgName
	 */
	public UserDetailedJson (String userId, String orgName, String userName, String orgId) {
		this.userId = userId;
		this.userName = userName;
		this.orgId = orgId;
		this.orgName = orgName;
	}

	public UserDetailedJson(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getIslocked() {
		return islocked;
	}
	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
	/**
	 * 获取 组织名称
	 *
	 * @return orgName 组织名称
	 */
	public String getOrgName () {
		return this.orgName;
	}
	
	/**
	 * 设置 组织名称
	 *
	 * @param orgName 组织名称
	 */
	public void setOrgName (String orgName) {
		this.orgName = orgName;
	}

	/**  
	 * 获取手机  
	 * @return mobilePhone 手机  
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**  
	 * 设置手机  
	 * @param mobilePhone 手机  
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	/**  
	 * 获取区号  
	 * @return areaCode 区号  
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**  
	 * 设置区号  
	 * @param areaCode 区号  
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**  
	 * 获取固定电话  
	 * @return telephone 固定电话  
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**  
	 * 设置固定电话  
	 * @param telephone 固定电话  
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	/**  
	 * 获取性别:1为男性，2为女性
	 * @return gender 性别:1为男性，2为女性  
	 */
	public String getGender() {
		return gender;
	}
	
	/**  
	 * 设置性别:1为男性，2为女性  
	 * @param gender 性别:1为男性，2为女性
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**  
	 * 获取用工类别  
	 * @return employType 用工类别  
	 */
	public String getEmployType() {
		return employType;
	}

	/**  
	 * 设置用工类别  
	 * @param employType 用工类别  
	 */
	public void setEmployType(String employType) {
		this.employType = employType;
	}

	/**  
	 * 获取pId  
	 * @return pId pId  
	 */
	public String getPId() {
		return pId;
	}

	/**  
	 * 设置pId  
	 * @param pId pId  
	 */
	public void setPId(String pId) {
		this.pId = pId;
	}
	
	/**  
	 * 获取id  
	 * @return id id  
	 */
	public String getId() {
		return id;
	}
	
	/**  
	 * 设置id  
	 * @param id id  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * 获取password  
	 * @return password password  
	 */
	public String getPassword() {
		return password;
	}
	
	/**  
	 * 设置password  
	 * @param password password  
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**  
	 * 获取roleId  
	 * @return roleId roleId  
	 */
	public String getRoleId() {
		return roleId;
	}
	
	/**  
	 * 设置roleId  
	 * @param roleId roleId  
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**  
	 * 获取oldPassword  
	 * @return oldPassword oldPassword  
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	
	/**  
	 * 设置oldPassword  
	 * @param oldPassword oldPassword  
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	/**  
	 * 获取newPassword  
	 * @return newPassword newPassword  
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**  
	 * 设置newPassword  
	 * @param newPassword newPassword  
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**  
	 * 获取ids  
	 * @return ids ids  
	 */
	public List<String> getIds() {
		return ids;
	}

	/**  
	 * 设置ids  
	 * @param ids ids  
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**  
	 * 获取头像  
	 * @return headPhoto 头像  
	 */
	public String getHeadPhoto() {
		return headPhoto;
	}

	/**  
	 * 设置头像  
	 * @param headPhoto 头像  
	 */
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	/**  
	 * 获取微信唯一id  
	 * @return unionId 微信唯一id  
	 */
	public String getUnionId() {
		return unionId;
	}

	/**  
	 * 设置微信唯一id  
	 * @param unionId 微信唯一id  
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**  
	 * 获取roleNames  
	 * @return roleNames roleNames  
	 */
	public List<String> getRoleNames() {
		return roleNames;
	}

	/**  
	 * 设置roleNames  
	 * @param roleNames roleNames  
	 */
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	/**  
	 * 获取创建人  
	 * @return createUser 创建人  
	 */
	public String getCreateUser() {
		return createUser;
	}
	
	/**  
	 * 设置创建人  
	 * @param createUser 创建人  
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public UserDetailedJson setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
		return this;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public UserDetailedJson setPositionName(String positionName) {
		this.positionName = positionName;
		return this;
	}

    /**
     * 获取 第三方用户编码或id
     *
     * @return thirdPartyCode 第三方用户编码或id
     */
    public String getThirdPartyCode() {
        return this.thirdPartyCode;
    }

    /**
     * 设置 第三方用户编码或id
     *
     * @param thirdPartyCode 第三方用户编码或id
     */
    public void setThirdPartyCode(String thirdPartyCode) {
        this.thirdPartyCode = thirdPartyCode;
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
	 * 获取 旧的资源访问密码
	 *
	 * @return oldBrowsePassword 旧的资源访问密码
	 */
	public String getOldBrowsePassword() {
		return this.oldBrowsePassword;
	}

	/**
	 * 设置 旧的资源访问密码
	 *
	 * @param oldBrowsePassword 旧的资源访问密码
	 */
	public void setOldBrowsePassword(String oldBrowsePassword) {
		this.oldBrowsePassword = oldBrowsePassword;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public UserDetailedJson setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
		return this;
	}
}
