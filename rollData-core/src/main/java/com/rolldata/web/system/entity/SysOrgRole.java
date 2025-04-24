package com.rolldata.web.system.entity;


import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title SysOrgRole
 * @Description: 角色组织表
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @createDate 2018-6-5
 * @version V1.0 
 */
@Entity
@Table(name = "wd_sys_orgrole")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysOrgRole extends IdEntity implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -102895917294596502L;
	//组织id
	private String orgId;
	//角色id
	private String roleId;
	//创建时间
	private Date createTime;
	
	@Column(name = "org_id",length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Column(name="role_id",length=32)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
