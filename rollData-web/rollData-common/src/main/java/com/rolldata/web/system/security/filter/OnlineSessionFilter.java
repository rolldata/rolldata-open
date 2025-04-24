
package com.rolldata.web.system.security.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.security.session.OnlineSession;
import com.rolldata.web.system.security.session.OnlineSessionDAO;
import com.rolldata.web.system.security.shiro.ShiroConstants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 
 * @Title: OnlineSessionFilter
 * @Description: 在线
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class OnlineSessionFilter extends AccessControlFilter {

	/**
	 * 强制退出后重定向的地址
	 */
	private String forceLogoutUrl;
	
	@Autowired
	private OnlineSessionDAO onlineSessionDAO;

	public String getForceLogoutUrl() {
		return forceLogoutUrl;
	}

	public void setForceLogoutUrl(String forceLogoutUrl) {
		this.forceLogoutUrl = forceLogoutUrl;
	}

	public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO) {
		this.onlineSessionDAO = onlineSessionDAO;
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null || subject.getSession() == null) {
			return true;
		}
		Session session = onlineSessionDAO.readSession(subject.getSession().getId());
		if (session != null && session instanceof OnlineSession) {
			OnlineSession onlineSession = (OnlineSession) session;
			request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
			// 把user id设置进去
			//boolean isGuest = onlineSession.getUserId() == null  ;
			if (StringUtil.isEmpty(onlineSession.getUserId())) {
				SysUser user = (SysUser) request.getAttribute(Constants.CURRENT_USER);
				if (user != null) {
					onlineSession.setUserId(user.getId());
					onlineSession.setUserCde(user.getUserCde());
					onlineSession.markAttributeChanged();
				}
			}

			if (onlineSession.getStatus() == OnlineSession.OnlineStatus.force_logout) {
				return false;
			}
		}
		return true;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject != null) {
			subject.logout();
		}
		saveRequestAndRedirectToLogin(request, response);
		return true;
	}

	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		WebUtils.issueRedirect(request, response, getForceLogoutUrl());
	}

}
