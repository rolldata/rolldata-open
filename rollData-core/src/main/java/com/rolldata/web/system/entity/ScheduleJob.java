package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 
 * @Title: ScheduleJob
 * @Description: 任务
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_timetask")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ScheduleJob extends IdEntity implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048055774756979138L;

	/** 任务名 */
	private String jobName;
	
	/** 任务描述 */
	private String description;
	
	/** cron表达式 */
	private String cronExpression;
	
	/** 任务调用的方法名 */
	private String methodName;
	
	/** 任务执行时调用哪个类的方法 包名+类名 */
	private String beanClass;
	
	/** 任务是否有状态 */
	private String isConcurrent;
	
	/** 任务状态0停用，1启用 */
	private String jobStatus;
	
	/** 任务分组 */
	private String jobGroup;
//	/** 更新者 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "update_by")
//	private User updateBy;
	
	/** 创建时间 */
	private Date createDate;
	
	/** 更新时间 */
	private Date updateDate;
	/** 创建者 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "create_by")
//	private User createBy;
	

	/**
	 * 获取 cronExpression
	 * 
	 * @return: String cron表达式
	 */
    @Column(name = "cron_expression", length = 50, scale = 0)
	public String getCronExpression() {
		return this.cronExpression;
	}

	/**
	 * 设置 cronExpression
	 * 
	 * @param: cronExpression
	 *             cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 获取 methodName
	 * 
	 * @return: String 任务调用的方法名
	 */
    @Column(name = "method_name", length = 255, scale = 0)
	public String getMethodName() {
		return this.methodName;
	}

	/**
	 * 设置 methodName
	 * 
	 * @param: methodName
	 *             任务调用的方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 获取 isConcurrent
	 * 
	 * @return: String 任务是否有状态
	 */
    @Column(name = "is_concurrent", length = 2, scale = 0)
	public String getIsConcurrent() {
		return this.isConcurrent;
	}

	/**
	 * 设置 isConcurrent
	 * 
	 * @param: isConcurrent
	 *             任务是否有状态
	 */
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	/**
	 * 获取 description
	 * 
	 * @return: String 任务描述
	 */
    @Column(name = "description", length = 255, scale = 0)
	public String getDescription() {
		return this.description;
	}

	/**
	 * 设置 description
	 * 
	 * @param: description
	 *             任务描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * 获取 updateBy
	 * 
	 * @return: String 更新者
	 */
//	public User getUpdateBy() {
//		return this.updateBy;
//	}

	/**
	 * 设置 updateBy
	 * 
	 * @param: updateBy
	 *             更新者
	 */
//	public void setUpdateBy(User updateBy) {
//		this.updateBy = updateBy;
//	}

	/**
	 * 获取 beanClass
	 * 
	 * @return: String 任务执行时调用哪个类的方法 包名+类名
	 */
    @Column(name = "bean_class", length = 255, scale = 0)
	public String getBeanClass() {
		return this.beanClass;
	}

	/**
	 * 设置 beanClass
	 * 
	 * @param: beanClass
	 *             任务执行时调用哪个类的方法 包名+类名
	 */
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	/**
	 * 获取 createDate
	 * 
	 * @return: Date 创建时间
	 */
    @Column(name = "create_time")
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置 createDate
	 * 
	 * @param: createDate
	 *             创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取 jobStatus
	 * 
	 * @return: String 任务状态
	 */
    @Column(name = "job_status", length = 2, scale = 0)
	public String getJobStatus() {
		return this.jobStatus;
	}

	/**
	 * 设置 jobStatus
	 * 
	 * @param: jobStatus
	 *             任务状态
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * 获取 jobGroup
	 * 
	 * @return: String 任务分组
	 */
    @Column(name = "job_group", length = 50, scale = 0)
	public String getJobGroup() {
		return this.jobGroup;
	}

	/**
	 * 设置 jobGroup
	 * 
	 * @param: jobGroup
	 *             任务分组
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * 获取 updateDate
	 * 
	 * @return: Date 更新时间
	 */
    @Column(name = "update_time")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 设置 updateDate
	 * 
	 * @param: updateDate
	 *             更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 获取 createBy
	 * 
	 * @return: String 创建者
	 */
//	public User getCreateBy() {
//		return this.createBy;
//	}

	/**
	 * 设置 createBy
	 * 
	 * @param: createBy
	 *             创建者
	 */
//	public void setCreateBy(User createBy) {
//		this.createBy = createBy;
//	}

	/**
	 * 获取 jobName
	 * 
	 * @return: String 任务名
	 */
    @Column(name = "job_name", length = 255, scale = 0)
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * 设置 jobName
	 * 
	 * @param: jobName
	 *             任务名
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
