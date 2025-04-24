package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SysLastOnline
 * @Description: 最后在线用户表
 * @author shenshilong
 * @createDate 2018-7-25
 */

@Entity
@Table(name = "wd_sys_last_online")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysLastOnline extends IdEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 296428138751398199L;

	/*用户id*/
	private String userId;
	
	/*用户主机ip*/
	private String host;
	
	/*用户头*/
	private String userAgent;
	
	/*系统主机ip*/
	private String systemHost;
	
	/*最后登录时间*/
	private Date lastLoginTimestamp;
	
	/*最后退出时间*/
	private Date lastStopTimestamp;
	
	/*登录次数*/
	private String loginCount;
	
	/*登录总时长*/
	private String totalOnlineTime;
	
	/*创建时间*/
	private Date createTime;
	
	/*更新时间*/
	private Date updateTime;

	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "host", length = 100)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "user_agent", length = 500)
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name = "system_host", length = 100)
	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	@Column(name = "last_login_timestamp")
	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	@Column(name = "last_stop_timestamp")
	public Date getLastStopTimestamp() {
		return lastStopTimestamp;
	}

	public void setLastStopTimestamp(Date lastStopTimestamp) {
		this.lastStopTimestamp = lastStopTimestamp;
	}

	@Column(name = "login_count", length = 255)
	public String getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "total_online_time", length = 255)
	public String getTotalOnlineTime() {
		return totalOnlineTime;
	}

	public void setTotalOnlineTime(String totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
