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
 * @Title: AnnouncementEntity
 * @Description: 公告管理实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_announcement")
@DynamicUpdate(true)
@DynamicInsert(true)
public class AnnouncementEntity extends IdEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 446583577612057230L;

	/**
	 * 公告标题
	 */
	private String title;
	
	/**
	 * 公告内容
	 */
	private String content;
	
	/**
	 * 是否有附件，0无附件，1有附件
	 */
	private String isFile;
	
	/**
	 * 附件名称
	 */
	private String fileName;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public AnnouncementEntity() {
		super();
	}


	public AnnouncementEntity(String id) {
		super(id);
	}

	public AnnouncementEntity(String id, String title, String content, String isFile, String fileName,
			String createUser, Date createTime) {
		super(id);
		this.title = title;
		this.content = content;
		this.isFile = isFile;
		this.fileName = fileName;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	/**  
	 * 获取公告标题  
	 * @return title 公告标题  
	 */
	@Column(name = "title", length = 50)
	public String getTitle() {
		return title;
	}
	

	/**  
	 * 设置公告标题  
	 * @param title 公告标题  
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**  
	 * 获取公告内容  
	 * @return content 公告内容  
	 */
	@Column(name = "content", length = 2000)
	public String getContent() {
		return content;
	}

	/**  
	 * 设置公告内容  
	 * @param content 公告内容  
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**  
	 * 获取附件名称  
	 * @return fileName 附件名称  
	 */
	@Column(name = "file_name", length = 100)
	public String getFileName() {
		return fileName;
	}

	/**  
	 * 设置附件名称  
	 * @param fileName 附件名称  
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * 获取是否有附件，0无附件，1有附件  
	 * @return isFile 是否有附件，0无附件，1有附件  
	 */
	@Column(name = "is_file", length = 2)
	public String getIsFile() {
		return isFile;
	}
	


	/**  
	 * 设置是否有附件，0无附件，1有附件  
	 * @param isFile 是否有附件，0无附件，1有附件  
	 */
	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}
	
	
	
}
