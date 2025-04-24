package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: SysPost
 * @Description: 职务
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-11-30
 * @version: v1.0
 */
@Entity
@Table(name = "wd_sys_post")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysPost extends IdEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9011403593088697674L;

	/**
	 * 职务代码
	 */
	private String postCode;

    /**
     * 职务名称
     */
	private String postName;

    /**
     * 创建时间
     */
	private Date createTime;

    /**
     * 创建用户
     */
	private String createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**外部安装模型的id*/
    private String wdModelId;

    /**
     * 获取 职务代码
     *
     * @return postCode 职务代码
     */
    @Column(name = "post_code", length = 20)
    public String getPostCode() {
        return this.postCode;
    }

    /**
     * 设置 职务代码
     *
     * @param postCode 职务代码
     */
    public SysPost setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    /**
     * 获取 职务名称
     *
     * @return postName 职务名称
     */
    @Column(name = "post_name", length = 50)
    public String getPostName() {
        return this.postName;
    }

    /**
     * 设置 职务名称
     *
     * @param postName 职务名称
     */
    public SysPost setPostName(String postName) {
        this.postName = postName;
        return this;
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
	public SysPost setCreateTime(Date createTime){
		this.createTime = createTime;
        return this;
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
	public SysPost setCreateUser(String createUser){
		this.createUser = createUser;
        return this;
	}

	@Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public SysPost setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return updateUser;
    }
    
    public SysPost setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
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
