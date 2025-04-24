package com.rolldata.web.system.pojo;

import java.util.List;


/**
 * 用户目录节点返回json格式
 * @author shenshilong
 * @createDate 2018-7-28
 */

public class AllUserListParent extends PageJson implements java.io.Serializable {

	private static final long serialVersionUID = 450939914651793533L;

	 /**
     * 组织id
     */
	private String orgId;
	
	private String totalUser;
	
	private String activeUser;

	private String disableUser;
	
	private String lockedUser;
	
	private String userType;
	
	private String name;
	
	private List<AllUserList> lists;

    /**
     * 职务id
     */
	private List<String> postIds;

	/**
	 * 组织编码
	 */
	private String orgCde;

	public String getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(String totalUser) {
		this.totalUser = totalUser;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}

	public String getDisableUser() {
		return disableUser;
	}

	public void setDisableUser(String disableUser) {
		this.disableUser = disableUser;
	}

	public String getLockedUser() {
		return lockedUser;
	}

	public void setLockedUser(String lockedUser) {
		this.lockedUser = lockedUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AllUserList> getLists() {
		return lists;
	}

	public void setLists(List<AllUserList> lists) {
		this.lists = lists;
	}
	
	/**
     * 获取 组织id
     *
     * @return orgId 组织id
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 设置 组织id
     *
     * @param orgId 组织id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取 职务id
     *
     * @return postIds 职务id
     */
    public List<String> getPostIds() {
        return this.postIds;
    }

    /**
     * 设置 职务id
     *
     * @param postIds 职务id
     */
    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }

	/**
	 * 获取 组织编码
	 *
	 * @return orgCde 组织编码
	 */
	public String getOrgCde() {
		return this.orgCde;
	}

	/**
	 * 设置 组织编码
	 *
	 * @param orgCde 组织编码
	 */
	public void setOrgCde(String orgCde) {
		this.orgCde = orgCde;
	}
}
