package com.rolldata.web.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.rolldata.core.common.entity.IdEntity;

/**
 * 
 * @Title: SysRoleHomePage
 * @Description: 主页配置角色实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月8日
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_role_homepage")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysRoleHomePage extends IdEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5661179023487758751L;

    /**
     * 主页类型5资源
     */
	public static final String TYPE5 = "5";

	/**
	 * 主页类型：1默认主页，2BI，3DS，4系统菜单,5资源
	 */
	private String type;

    /**
     * 终端类型1PC2ipad3手机
     */
	private String terminalType;

	/**
	 * 角色id
	 */
	private String roleId;

    /**
     * 资源
     */
	private String resourceId;

	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**外部安装模型的id*/
	private String wdModelId;

	/**  
	 * 获取主页类型：1默认主页，2BI，3DS，4系统菜单,5资源  
	 * @return type 主页类型：1默认主页，2BI，3DS，4系统菜单,5资源  
	 */
	@Column(name = "c_type", length = 2)
	public String getType() {
		return type;
	}
	
	/**  
	 * 设置主页类型：1默认主页，2BI，3DS，4系统菜单,5资源  
	 * @param type 主页类型：1默认主页，2BI，3DS，4系统菜单,5资源  
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**  
	 * 获取角色id  
	 * @return roleId 角色id  
	 */
	@Column(name = "role_id", length = 32)
	public String getRoleId() {
		return roleId;
	}
	
	/**  
	 * 设置角色id  
	 * @param roleId 角色id  
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**  
	 * 获取创建人  
	 * @return createUser 创建人  
	 */
	@Column(name = "create_user", length = 32)
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
	 * 获取创建时间  
	 * @return createTime 创建时间  
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置创建时间  
	 * @param createTime 创建时间  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    /**
     * 获取 终端类型1PC2ipad3手机
     *
     * @return terminalType 终端类型1PC2ipad3手机
     */
    @Column(name = "terminal_type", length = 2)
    public String getTerminalType() {
        return this.terminalType;
    }

    /**
     * 设置 终端类型1PC2ipad3手机
     *
     * @param terminalType 终端类型1PC2ipad3手机
     */
    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    /**
     * 获取 资源
     *
     * @return resourceId 资源
     */
    @Column(name = "resource_id", length = 32)
    public String getResourceId() {
        return this.resourceId;
    }

    /**
     * 设置 资源
     *
     * @param resourceId 资源
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
