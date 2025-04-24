package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/** 
 * @Title: SysMessage
 * @Description: 消息管理表
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/19
 * @version V1.0  
 */
@Entity
@Table(name = "wd_sys_message")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysMessage extends IdEntity implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8964178534503637517L;

	/**
     * 消息类型0系统消息1任务填报2任务办理3任务回退4任务重报5其他,6ETL,7预警
     */
    public static final String TYPE_SYS = "0";
    
    public static final String TYPE_APPLY = "1";
    
    public static final String TYPE_DEAL = "2";
    
    public static final String TYPE_BACK = "3";
    
    public static final String TYPE_REFILL = "4";

    public static final String TYPE_DPF = "6";
    
    public static final String TYPE_OTHER = "5";

	public static final String TYPE_WARN = "7";

    /**
     * 任务催报
     */
	public static final String TYPE_REMINDER = "8";

	/**
	 * 撤回
	 */
	public static final String TYPE_WITHDRAW = "9";

    /**状态0未读*/
    public static final String STATE_NOREAD = "0";
    /**状态1已读*/
    public static final String STATE_READ = "1";
    
	/**标题*/
	private String title;
    
	/**消息内容*/
	private String content;
    
	/**消息类型0系统消息1任务填报2任务办理3任务回退4其他*/
	private String type;
    
	/**指向查看用户*/
	private String toUser;
	
	/**状态0未读1已读*/
	private String state;
	
	/**创建时间*/
	private java.util.Date createTime;
    
	/**创建人*/
	private String createUser;
    
	/**更新时间*/
	private java.util.Date updateTime;
    
	/**修改人*/
	private String updateUser;
    
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name = "title", length = 50)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息内容
	 */
	@Column(name = "content", length = 1000)
	public String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息类型0系统消息1任务填报2任务办理3任务回退4其他
	 */
	@Column(name = "c_type", length = 1)
	public String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息类型0系统消息1任务填报2任务办理3任务回退4其他
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name = "create_time")
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name = "create_user", length = 32)
	public String getCreateUser(){
		return this.createUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name = "update_time")
	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name = "update_user", length = 32)
	public String getUpdateUser(){
		return this.updateUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

	/**  
	 * 获取指向查看用户  
	 * @return toUser 指向查看用户  
	 */
	@Column(name = "to_user", length = 32)
	public String getToUser() {
		return toUser;
	}
	

	/**  
	 * 设置指向查看用户  
	 * @param toUser 指向查看用户  
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	

	/**  
	 * 获取状态0未读1已读  
	 * @return state 状态0未读1已读  
	 */
	@Column(name = "state", length = 2)
	public String getState() {
		return state;
	}
	

	/**  
	 * 设置状态0未读1已读  
	 * @param state 状态0未读1已读  
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
