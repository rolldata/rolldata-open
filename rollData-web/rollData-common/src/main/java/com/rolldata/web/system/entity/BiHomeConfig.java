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
 * @Title: BiHomeConfig
 * @Description: Bi首页配置信息实体
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
@Entity
@Table(name = "wd_bi_home_config")
@DynamicUpdate(true)
@DynamicInsert(true)
public class BiHomeConfig extends IdEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1489285655719415786L;

	public BiHomeConfig(String id, String name, String resourceId, String imgName, String createUser) {
		super(id);
		this.name = name;
		this.resourceId = resourceId;
		this.imgName = imgName;
		this.createUser = createUser;
	}

	public BiHomeConfig() {
		super();
	}

	public BiHomeConfig(String id) {
		super(id);
	}

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 资源id
	 */
	private String resourceId;
	
	/**
	 * 排序
	 */
	private String sort;
	
	/**
	 * 图片名称
	 */
	private String imgName;
	
	/**
	 * 图片存放相对路径
	 */
	private String imgUrl;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新用户
	 */
	private String updateUser;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**  
	 * 获取名称  
	 * @return name 名称  
	 */
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	/**  
	 * 设置名称  
	 * @param name 名称  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 获取资源id  
	 * @return resourceId 资源id  
	 */
	@Column(name = "resource_id", length = 32)
	public String getResourceId() {
		return resourceId;
	}

	/**  
	 * 设置资源id  
	 * @param resourceId 资源id  
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	/**  
	 * 获取排序  
	 * @return sort 排序  
	 */
	@Column(name = "sort", length = 5)
	public String getSort() {
		return sort;
	}

	/**  
	 * 设置排序  
	 * @param sort 排序  
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/**  
	 * 获取图片名称  
	 * @return imgName 图片名称  
	 */
	@Column(name = "img_name", length = 50)
	public String getImgName() {
		return imgName;
	}
	
	/**  
	 * 设置图片名称  
	 * @param imgName 图片名称  
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	/**  
	 * 获取创建用户  
	 * @return createUser 创建用户  
	 */
	@Column(name = "create_user", length = 32)
	public String getCreateUser() {
		return createUser;
	}

	/**  
	 * 设置创建用户  
	 * @param createUser 创建用户  
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
	 * 获取更新用户  
	 * @return updateUser 更新用户  
	 */
	@Column(name = "update_user", length = 32)
	public String getUpdateUser() {
		return updateUser;
	}

	/**  
	 * 设置更新用户  
	 * @param updateUser 更新用户  
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**  
	 * 获取更新时间  
	 * @return updateTime 更新时间  
	 */
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**  
	 * 设置更新时间  
	 * @param updateTime 更新时间  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**  
	 * 获取图片存放相对路径 
	 * @return imgUrl 图片存放相对路径
	 */
	@Column(name = "img_url", length = 200)
	public String getImgUrl() {
		return imgUrl;
	}

	/**  
	 * 设置图片存放相对路径 
	 * @param imgUrl 图片存放相对路径 
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
