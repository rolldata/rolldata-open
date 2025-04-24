package com.rolldata.web.system.security.web;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.LoginType;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @Title: RetryLimitHashedCredentialsMatcher
 * @Description: 密码重试次数限制
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	private Logger log = LogManager.getLogger(RetryLimitHashedCredentialsMatcher.class);
	private Cache<String, AtomicInteger> passwordRetryCache;
	private int maxRetryCount = 10;
	private int showCaptchaRetryCount = 2;

	@Autowired
	private UserService userService;
	
	public void setMaxRetryCount(int maxRetryCount) {
		this.maxRetryCount = maxRetryCount;
	}

	public void setShowCaptchaRetryCount(int showCaptchaRetryCount) {
		this.showCaptchaRetryCount = showCaptchaRetryCount;
	}

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		String username = authcToken.getUsername();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		//增加一次
		retryCount.incrementAndGet();
		if(passwordSecurityMap.getPsdErrNumberIs().equals("1")) {//开启错误密码次数验证
			//错误次数达到设置的锁定用户
			if (retryCount.get() > Integer.parseInt(passwordSecurityMap.getPsdErrNumber())) {
				SysUser sysUser = new SysUser();
    			sysUser.setUserCde(username);
    			sysUser.setIslocked(SysUser.LOCKED);
				try {
					userService.updateIslockedByCde(sysUser);
					throw new LockedAccountException(); // 帐号锁定
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		}else {
			//没设置锁定用户使用默认，禁止重复提交
			if (retryCount.get() > maxRetryCount) {
				throw new ExcessiveAttemptsException();
			}
		}
		boolean matches = false;
		if (StringUtil.isEmpty(authcToken.getLoginType())){
			matches = super.doCredentialsMatch(token, info);
    		if (matches) {
    			// clear retry count
    			passwordRetryCache.remove(username);
    		}
		}else {
			if(authcToken.getLoginType().equals(LoginType.TYPE_PASSWORD)) {
				 matches = super.doCredentialsMatch(token, info);
		    		if (matches) {
		    			// clear retry count
		    			passwordRetryCache.remove(username);
		    		}
			}else {
				//微信登陆直接放过
				matches = true;
				passwordRetryCache.remove(username);
			}
		}
		return matches;
	}

	/**
	 * 是否重复去登录
	 * 
	 * @param useruame
	 * @return
	 */
	public boolean isRepeatLogin(String useruame) {
		AtomicInteger retryCount = passwordRetryCache.get(useruame);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
		}
		return retryCount.get() >= showCaptchaRetryCount;
	}

	/**
	 * 刷新登录 做好相应的处理
	 * 
	 * @param useruame
	 *            用户名
	 * @return
	 */
	public boolean isForceLogin(String useruame) {
		AtomicInteger retryCount = passwordRetryCache.get(useruame);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
		}
		return retryCount.get() >= maxRetryCount;
	}

	/**
	 * 清除验证
	 * 
	 * @return
	 */
	public void clear(String useruame) {
		passwordRetryCache.remove(useruame);
	}

}
