package com.rolldata.web.system.security.shiro;

import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.security.exception.RepeatAuthenticationException;
import com.rolldata.web.system.security.web.UsernamePasswordToken;
import com.rolldata.web.system.service.UserService;
import com.rolldata.web.system.util.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @Title: UserRealm
 * @Description: 用户认证
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionDAO sessionDao;

	/**
	 * 授权的回调方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(UserUtils.getRoleStringList());
		authorizationInfo.setStringPermissions(UserUtils.getPermissionsList());
		return authorizationInfo;
	}

	/**
	 * 认证的回调方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		//限制同时在线用户数
		if(passwordSecurityMap.getIsLoginNum().equals("1")){
			Collection sessions = sessionDao.getActiveSessions();
			//根据session数量判断在线人数
			List<String> onlineUserList = new ArrayList<String>();
			Iterator iterator = sessions.iterator();
			while (iterator.hasNext()) {
				Object s =  iterator.next();
				if(s instanceof LinkedList){
					LinkedList l = (LinkedList) s;
					String id = (String) l.get(0);
					onlineUserList.add(id);
				}
			}
			if(onlineUserList.size()>= Integer.parseInt(passwordSecurityMap.getLoginNum())){
				SecurityUtils.getSubject().getSession().setAttribute("msg", MessageUtils.getMessage("common.sys.login.limit"));
				throw new RepeatAuthenticationException(MessageUtils.getMessage("common.sys.login.limit"));
			}
		}
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		String username = authcToken.getUsername();
        SysUser user = null;
        try {
            user = userService.getUserByUserCde(username);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new Exception();
        }
//		if (user == null) {
//			user = userService.findByEmail(username);
//			if (user == null) {
//				user = userService.findByPhone(username);
//			}
//		}
		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (SysUser.STATUS_DELETE.equals(user.getIsactive())) {
			throw new RepeatAuthenticationException(MessageUtils.getMessage("common.sys.login.stop")); // 帐号停用
		}
		if (SysUser.LOCKED.equals(user.getIslocked())) {
			throw new LockedAccountException(); // 帐号锁定
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				new Principal(user, authcToken.isMobileLogin(),authcToken.isAppLogin()), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);
		// 记录登录日志
//		LogUtils.saveLog(ServletUtils.getRequest(), "系统登录",username+"登录成功",SysLog.LEVEL2);
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String userCde; // 登录名
		private String userName; // 姓名
		private boolean mobileLogin; // 是否手机web登录
		private boolean appLogin;//是否app登陆

		public Principal(SysUser user, boolean mobileLogin,boolean appLogin) {
			this.id = user.getId();
			this.userCde = user.getUserCde();
			this.userName = user.getUserName();
			this.mobileLogin = mobileLogin;
			this.appLogin = appLogin;
		}

		public String getId() {
			return id;
		}

		public String getUserCde() {
			return userCde;
		}

		public String getUserName() {
			return userName;
		}

		public boolean isAppLogin() {
			return appLogin;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}
		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try {
				return (String) UserUtils.getSession().getId();
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
