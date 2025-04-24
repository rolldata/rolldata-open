package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import com.rolldata.web.system.util.UserUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Title: SysRole
 * @Description: 角色
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 *
 */
@Entity
@Table(name = "wd_sys_role")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysRole extends IdEntity implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7796694315980862942L;

	/**
     * state（0停用1启用）
     */
    public static final String STATUS_NORMAL = "1";
    
    public static final String STATUS_DELETE = "0";
    
    /**
     * 角色树跟节点
     */
    public static final String TREENODE_ROOT = "0";

	/*is_admin*/
	public static final String NOT_IS_ADMIN = "0";

	public static final String IS_ADMIN = "1";

	/**role_cde 属于 角色表*/
	private String roleCde;

	/**角色名称*/
	private String roleName;

	/**创建时间*/
	private Date createTime;

	/**创建用户*/
	private String createUser;

	/**状态0停用1启用*/
	private String state;

    /**
     * 备注
     */
	private String remark;

	/**是否管理角色，0否，1是*/
	private String isAdmin;

    /**修改时间*/
    private Date updateTime;

    /**修改人*/
    private String updateUser;

	/**外部安装模型的id*/
	private String wdModelId;

	public SysRole() {
	}

	public SysRole(String id) {
		super(id);
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  role_cde 属于 角色表
	 */
    @Column(name = "role_cde", length = 20)
	public String getRoleCde(){
		return this.roleCde;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  role_cde 属于 角色表
	 */
	public void setRoleCde(String roleCde){
		this.roleCde = roleCde;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色名称
	 */
    @Column(name = "role_name", length = 50)
	public String getRoleName(){
		return this.roleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色名称
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
    @Column(name = "create_time")
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建用户
	 */
    @Column(name = "create_user", length = 32)
	public String getCreateUser(){
		return this.createUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建用户
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态0停用1启用
	 */
    @Column(name = "state", length = 1)
	public String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态0停用1启用
	 */
	public void setState(String state){
		this.state = state;
	}
    
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public SysRole setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return updateUser;
    }
    
    public SysRole setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }
    
    @Transient
    public Date getPresentDate() {
        return new Date();
    }
    
    @Transient
    public String getgetPresentUser() {
        return UserUtils.getUser().getId();
    }

    /**
     * 获取 备注
     *
     * @return remark 备注
     */
    @Column(name = "c_remark", length = 2000)
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


	/**
	 * 获取 是否管理角色，0否，1是
	 *
	 * @return isAdmin 是否管理角色，0否，1是
	 */
	@Column(name = "is_admin", length = 2)
	public String getIsAdmin() {
		return this.isAdmin;
	}

	/**
	 * 设置 是否管理角色，0否，1是
	 *
	 * @param isAdmin 是否管理角色，0否，1是
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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
