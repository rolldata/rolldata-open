/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rolldata.web.system.security.sync;

import org.apache.shiro.web.filter.PathMatchingFilter;

import com.rolldata.web.system.security.session.OnlineSession;
import com.rolldata.web.system.security.session.OnlineSessionDAO;
import com.rolldata.web.system.security.shiro.ShiroConstants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @Title: SyncOnlineSessionFilter
 * @Description: 同步当前会话数据到数据库
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter {
	
	private OnlineSessionDAO onlineSessionDAO;

	public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO) {
		this.onlineSessionDAO = onlineSessionDAO;
	}

	/**
	 * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到Shiro过滤器之前
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
		// 如果session stop了 也不同步
		if (session != null && session.getStopTimestamp() == null) {
			onlineSessionDAO.syncToDb(session);
		}
		return true;
	}

}
