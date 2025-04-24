package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 组织机构基本信息
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class OrgDetailedJson implements Serializable{

	private static final long serialVersionUID = 2229279970085596877L;
	
	private String orgId;
	
	private String orgType;
	
	private String orgName;

    /**
     * 是否参与自动汇总0否1是
     */
	private String automaticSummary;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


    /**
     * 获取 是否参与自动汇总0否1是
     *
     * @return automaticSummary 是否参与自动汇总0否1是
     */
    public String getAutomaticSummary() {
        return this.automaticSummary;
    }

    /**
     * 设置 是否参与自动汇总0否1是
     *
     * @param automaticSummary 是否参与自动汇总0否1是
     */
    public void setAutomaticSummary(String automaticSummary) {
        this.automaticSummary = automaticSummary;
    }
}
