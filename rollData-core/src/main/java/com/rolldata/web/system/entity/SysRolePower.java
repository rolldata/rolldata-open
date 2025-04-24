package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/** 
 * @Title: SysRolePower
 * @Description: 角色权限表
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/2
 * @version V1.0  
 */
@Entity
@Table(name = "wd_sys_rolepower")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysRolePower extends IdEntity implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7463904406383424512L;

	/*权限类型 （1功能权限,2按钮权限）*/
    public static final String TYPE_FUN = "1";
    
    public static final String TYPE_OPER = "2";
    
	/**角色id*/
	private String roleId;
	
	/**权限类型（1功能权限,2按钮权限）*/
	private String powerType;
	
	/**权限id（funcid,operid）*/
	private String powerId;
    
    @Column(name ="role_id",length=32)
    public String getRoleId() {
        return roleId;
    }
    
    public SysRolePower setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }
    
    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  权限类型（1功能权限,2按钮权限）
	 */
	@Column(name ="power_type",length=2)
	public String getPowerType(){
		return this.powerType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  权限类型（1功能权限,2按钮权限）
	 */
	public void setPowerType(String powerType){
		this.powerType = powerType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  权限id（funcid,operid）
	 */
	@Column(name ="power_id",length=32)
	public String getPowerId(){
		return this.powerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  权限id（funcid,operid）
	 */
	public void setPowerId(String powerId){
		this.powerId = powerId;
	}
}
