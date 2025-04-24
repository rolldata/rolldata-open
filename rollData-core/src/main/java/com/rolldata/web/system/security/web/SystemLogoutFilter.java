package com.rolldata.web.system.security.web;

import com.rolldata.core.util.ContextHolderUtils;
import com.rolldata.core.util.LogUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.service.UserOnlineService;
import com.rolldata.web.system.util.LogUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;

public class SystemLogoutFilter extends LogoutFilter {
	
	private Cache<String, Deque<Serializable>> cache;
	
	public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Autowired
    private UserOnlineService userOnlineService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
        //在这里执行退出系统前需要清空的数据
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }
        Principal user = (Principal)subject.getPrincipal();
        String userCde = user.getUserCde();
        // 同步控制
        Deque<Serializable> deque = cache.get(userCde);
        if(deque != null) {
            cache.remove(userCde);
        }
        String redirectUrl=getRedirectUrl(request,response,subject);
        ServletContext context= request.getServletContext();
        try {
//            SysUserOnline oldOnline = userOnlineDao.findSysUserByUserId(user.getId());
            PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
            LogUtils.saveLog(ContextHolderUtils.getRequest(), "退出",userCde+"退出成功", SysLog.LEVEL2);
            subject.logout();
            if(passwordSecurityMap.getIsLimitLogin().equals("1")){
                //限制同一端登陆再删除
                userOnlineService.deleteSysUserOnlineByUserId(user.getId());
            }
            LogUtil.info(userCde+"退出");
            context.removeAttribute("error");
        }catch (SessionException e){
            e.printStackTrace();
        }
        issueRedirect(request,response,redirectUrl);
        return false;
    }

}
