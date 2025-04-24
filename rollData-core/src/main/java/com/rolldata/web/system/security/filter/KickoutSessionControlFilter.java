package com.rolldata.web.system.security.filter;

import com.rolldata.core.util.ContextHolderUtils;
import com.rolldata.core.util.LogUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.util.LogUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

public class KickoutSessionControlFilter extends AccessControlFilter {
	private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
//    private Map<String, Deque<Serializable>> cacheManager;

    private Cache<String, Deque<Serializable>> cache;
    
    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

//    public void setCacheManager(CacheManager cacheManager) {
//        this.cacheManager = (Map<String, Deque<Serializable>>) cacheManager.getCache("shiro-kickout-session");
//    }
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
        //不限制登陆直接跳过
        if(passwordSecurityMap.getIsLimitLogin().equals("0")) {
        	return true;
        }
        Session session = subject.getSession();
        Principal user = (Principal)subject.getPrincipal();
        String userCde = user.getUserCde();
        Serializable sessionId = session.getId();
//		if (cacheManager==null) {
//			cacheManager = new HashMap<String, Deque<Serializable>>();
//		}
        // 同步控制
        Deque<Serializable> deque = cache.get(userCde);
        if(deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(userCde, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                LogUtils.saveLog(ContextHolderUtils.getRequest(), "被踢出成功",userCde+"被踢出成功", SysLog.LEVEL2);
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
            LogUtil.info(userCde+"被踢出成功");
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            if (isAjax(httpRequest)) {
                return false;
            } else {
                WebUtils.issueRedirect(request, response, kickoutUrl);
                return false;
            }
        }

        return true;
    }
    
    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
    	String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
    	if("XMLHttpRequest".equalsIgnoreCase(header)){
    		LogUtil.info( "当前请求为Ajax请求");
    		return Boolean.TRUE;
    	}
    	LogUtil.info("当前请求非Ajax请求");
    	return Boolean.FALSE;
    }
}
