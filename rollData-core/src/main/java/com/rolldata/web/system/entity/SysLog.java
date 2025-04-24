package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;


/**
 * 
 * @Title:SysLog
 * @Description:log日志
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-25
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_log")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysLog extends IdEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2878937828917580719L;
	// 日志类型（1：错误日志；2：接入日志）
	public static final String TYPE_EXCEPTION = "1";
	public static final String  TYPE_ACCESS= "2";
	//日志级别 1异常级  2系统级
	public static final Short LEVEL1 = 1;
	public static final Short LEVEL2 = 2;
	
	/**
	 * 用户浏览器类型
	 */
	private String broswer;
	
	/**
	 * 日志标题
	 */
	private String title;
	
	/**
	 * 日志级别
	 */
	private Short logLevel;
	
	/**
	 * 日志内容
	 */
	private String logContent;
	
	/**
	 * ip
	 */
	private String note;
	
	/**
	 * 日志类型  
	 */
	private String type;
	
	/**
	 * 操作时间
	 */
	private Timestamp operateTime;
	
	/** 请求URI */
	private String requestUri;
	
	/** 异常信息 */
	private String exception;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 上下文路径
	 */
	private String contextPath;
	/**  
	 * 获取用户浏览器类型
	 * @return broswer 用户浏览器类型
	 */
    @Column(name = "broswer", length = 100)
	public String getBroswer() {
		return broswer;
	}

	/**  
	 * 设置用户浏览器类型
	 * @param broswer 用户浏览器类型
	 */
	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

	/**  
	 * 获取日志标题  
	 * @return title 日志标题  
	 */
    @Column(name = "title", length = 255)
	public String getTitle() {
		return title;
	}

	/**  
	 * 设置日志标题  
	 * @param title 日志标题  
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**  
	 * 获取日志级别  
	 * @return logLevel 日志级别  
	 */
    @Column(name = "log_level", length = 6)
	public Short getLogLevel() {
		return logLevel;
	}

	/**  
	 * 设置日志级别  
	 * @param logLevel 日志级别  
	 */
	public void setLogLevel(Short logLevel) {
		this.logLevel = logLevel;
	}

	/**  
	 * 获取日志内容  
	 * @return logContent 日志内容  
	 */
    @Column(name = "log_content", length = 2000)
	public String getLogContent() {
		return logContent;
	}

	/**  
	 * 设置日志内容  
	 * @param logContent 日志内容  
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	/**  
	 * 获取ip  
	 * @return note ip  
	 */
    @Column(name = "note", length = 300)
	public String getNote() {
		return note;
	}

	/**  
	 * 设置ip  
	 * @param note ip  
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**  
	 * 获取日志类型  
	 * @return type 日志类型  
	 */
    @Column(name = "c_type", length = 2)
	public String getType() {
		return type;
	}

	/**  
	 * 设置日志类型  
	 * @param type 日志类型  
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**  
	 * 获取操作时间  
	 * @return operateTime 操作时间  
	 */
    @Column(name = "operate_time", length = 35)
	public Timestamp getOperateTime() {
		return operateTime;
	}

	/**  
	 * 设置操作时间  
	 * @param operateTime 操作时间  
	 */
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	/**  
	 * 获取请求URI  
	 * @return requestUri 请求URI  
	 */
	@Transient
	public String getRequestUri() {
		return requestUri;
	}

	/**  
	 * 设置请求URI  
	 * @param requestUri 请求URI  
	 */
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	/**  
	 * 获取异常信息  
	 * @return exception 异常信息  
	 */
	@Transient
	public String getException() {
		return exception;
	}

	/**  
	 * 设置异常信息  
	 * @param exception 异常信息  
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**  
	 * 获取用户id  
	 * @return userId 用户id  
	 */
    @Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	/**  
	 * 设置用户id  
	 * @param userId 用户id  
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**  
	 * 获取上下文路径  
	 * @return contextPath 上下文路径  
	 */
	@Transient
	public String getContextPath() {
		return contextPath;
	}

	/**  
	 * 设置上下文路径  
	 * @param contextPath 上下文路径  
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

}
