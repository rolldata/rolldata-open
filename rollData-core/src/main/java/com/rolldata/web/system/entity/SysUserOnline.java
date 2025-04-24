package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import com.rolldata.web.system.security.session.OnlineSession;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;


/**
 * 
 * @Title:SysUserOnline
 * @Description:在线用户实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-6-12
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_useronline")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysUserOnline extends IdEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568543745151159602L;

	/**
	 * 当前登录的用户Id
	 */
	private String userId;
	
	/**
	 * 用户主机地址
	 */
	private String host;
	
	/**
	 * 用户登录时系统IP
	 */
	private String systemHost;

	/**
	 * 用户浏览器类型
	 */
	private String userAgent;
	
	/**
	 * 在线状态
	 */
	private OnlineSession.OnlineStatus status = OnlineSession.OnlineStatus.on_line;

	/**
	 * session创建时间
	 */
	private Date startTimestamp;
	/**
	 * session最后访问时间
	 */
	private Date lastAccessTime;

	/**
	 * 超时时间
	 */
	private Long timeout;

	/**
	 * 备份的当前用户会话
	 */
	private OnlineSession session;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	/** 创建用户 */
	private String createUser;
	
	/** 修改用户 */
	private String updateUser;
	
	
	/**  
	 * 获取当前登录的用户Id  
	 * @return userId 当前登录的用户Id  
	 */
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}


	/**  
	 * 设置当前登录的用户Id  
	 * @param userId 当前登录的用户Id  
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**  
	 * 获取用户主机地址  
	 * @return host 用户主机地址  
	 */
	@Column(name = "host")
	public String getHost() {
		return host;
	}


	/**  
	 * 设置用户主机地址  
	 * @param host 用户主机地址  
	 */
	public void setHost(String host) {
		this.host = host;
	}


	/**  
	 * 获取用户登录时系统IP  
	 * @return systemHost 用户登录时系统IP  
	 */
	@Column(name = "system_host")
	public String getSystemHost() {
		return systemHost;
	}


	/**  
	 * 设置用户登录时系统IP  
	 * @param systemHost 用户登录时系统IP  
	 */
	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}


	/**  
	 * 获取用户浏览器类型  
	 * @return userAgent 用户浏览器类型  
	 */
	@Column(name = "user_agent")
	public String getUserAgent() {
		return userAgent;
	}


	/**  
	 * 设置用户浏览器类型  
	 * @param userAgent 用户浏览器类型  
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}


	/**  
	 * 获取在线状态  
	 * @return status 在线状态  
	 */
	@Column(name = "status", length = 50)
	@Enumerated(EnumType.STRING)
	public OnlineSession.OnlineStatus getStatus() {
		return status;
	}


	/**  
	 * 设置在线状态  
	 * @param status 在线状态  
	 */
	public void setStatus(OnlineSession.OnlineStatus status) {
		this.status = status;
	}


	/**  
	 * 获取session创建时间  
	 * @return startTimestamp session创建时间  
	 */
	@Column(name = "start_timestsamp")
	public Date getStartTimestamp() {
		return startTimestamp;
	}


	/**  
	 * 设置session创建时间  
	 * @param startTimestamp session创建时间  
	 */
	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}


	/**  
	 * 获取session最后访问时间  
	 * @return lastAccessTime session最后访问时间  
	 */
	@Column(name = "last_access_time")
	public Date getLastAccessTime() {
		return lastAccessTime;
	}


	/**  
	 * 设置session最后访问时间  
	 * @param lastAccessTime session最后访问时间  
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}


	/**  
	 * 获取超时时间  
	 * @return timeout 超时时间  
	 */
	@Column(name = "online_timeout")
	public Long getTimeout() {
		return timeout;
	}


	/**  
	 * 设置超时时间  
	 * @param timeout 超时时间  
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}


	/**  
	 * 获取备份的当前用户会话  
	 * @return session 备份的当前用户会话  
	 */
	@Column(name = "online_session")
	@Type(type = "com.rolldata.core.repository.ObjectSerializeUserType")
	public OnlineSession getSession() {
		return session;
	}


	/**  
	 * 设置备份的当前用户会话  
	 * @param session 备份的当前用户会话  
	 */
	public void setSession(OnlineSession session) {
		this.session = session;
	}


	/**  
	 * 获取备注  
	 * @return remarks 备注  
	 */
	@Column(name = "remarks", length = 255)
	public String getRemarks() {
		return remarks;
	}


	/**  
	 * 设置备注  
	 * @param remarks 备注  
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * 获取修改时间  
	 * @return updateTime 修改时间  
	 */
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}


	/**  
	 * 设置修改时间  
	 * @param updateTime 修改时间  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	 * 获取修改用户  
	 * @return updateUser 修改用户  
	 */
	@Column(name = "update_user", length = 32)
	public String getUpdateUser() {
		return updateUser;
	}


	/**  
	 * 设置修改用户  
	 * @param updateUser 修改用户  
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Transient
	public static final SysUserOnline fromOnlineSession(OnlineSession session) {
		SysUserOnline online = new SysUserOnline();
		online.setId(String.valueOf(session.getId()));
		online.setUserId(session.getUserId());
		online.setStartTimestamp(session.getStartTimestamp());
		online.setLastAccessTime(session.getLastAccessTime());
		online.setTimeout(session.getTimeout());
		online.setHost(session.getHost());
		online.setUserAgent(session.getUserAgent());
		online.setSystemHost(session.getSystemHost());
		online.setSession(session);

		return online;
	}
}
