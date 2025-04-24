package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 组织请求包含字段
 * @author shenshilong
 * @createDate 2018-7-30
 */

public class RequestOrgJson implements Serializable{

	private static final long serialVersionUID = 5617545290252386491L;

	private String orgId;
	
	private String pId;
	
	private String id;
	
	private String orgCde;
	
	private String orgName;
	
	private String orgType;
		
	private List<String> ids;

	/**创建人*/
	private String createUser;
	
    /**
     * 是否参与自动汇总
     */
	private String automaticSummary;

	/**外部安装模型的id*/
	private String wdModelId;

	/**是否同步，默认0否(手动创建)，1是*/
	private String isSync = "0";

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgCde() {
		return orgCde;
	}

	public void setOrgCde(String orgCde) {
		this.orgCde = orgCde;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

    /**
     * 获取 是否参与自动汇总
     *
     * @return automaticSummary 是否参与自动汇总
     */
    public String getAutomaticSummary() {
        return this.automaticSummary;
    }

    /**
     * 设置 是否参与自动汇总
     *
     * @param automaticSummary 是否参与自动汇总
     */
    public void setAutomaticSummary(String automaticSummary) {
        this.automaticSummary = automaticSummary;
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

	/**
	 * 获取 外部安装模型的id
	 *
	 * @return wdModelId 外部安装模型的id
	 */
	public String getWdModelId() {
		return this.wdModelId;
	}

	/**
	 * 设置 外部安装模型的id
	 *
	 * @param wdModelId 外部安装模型的id
	 */
	public void setWdModelId(String wdModelId) {
		this.wdModelId = wdModelId;
	}

	/**
	 * 获取 是否同步，默认0否(手动创建)，1是
	 *
	 * @return isSync 是否同步，默认0否(手动创建)，1是
	 */
	public String getIsSync() {
		return this.isSync;
	}

	/**
	 * 设置 是否同步，默认0否(手动创建)，1是
	 *
	 * @param isSync 是否同步，默认0否(手动创建)，1是
	 */
	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}
}
