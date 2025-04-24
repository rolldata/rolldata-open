package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户角色关系表
 * @author shenshilong
 * @createDate 2018-6-6
 */

@Entity
@Table(name="wd_sys_userrole")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserRole extends IdEntity implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4379200087371026517L;

	/*角色ID*/
	private String roleId;
	
	/*用户ID*/
	private String userId;
	
	/*创建时间*/
	private Date createTime;

	/**外部安装模型的id*/
	private String wdModelId;
	
	@Column(name = "role_id", length = 32)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 外部安装模型的id
	 *
	 * @return wdModelId 外部安装模型的id
	 */
	@Column(name = "wd_model_id", length = 32)
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
}
