package com.rolldata.web.system.security.web;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.*;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.LastOnlineDao;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.entity.SysLastOnline;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.pojo.LoginType;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.security.exception.RepeatAuthenticationException;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.util.LogUtils;
import com.rolldata.web.system.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Date;

import static com.rolldata.web.system.entity.SysConfig.DEFAULT_JSP_UPDATE_VERSION;
import static com.rolldata.web.system.entity.SysConfig.JSP_UPDATE_VERSION;

/**
 * 
 * @Title: FormAuthenticationFilter
 * @Description: 表单验证
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_ERROR_PARAM = "error";
	public static final String DEFAULT_MESSAGE_SUCCESS_PARAM = "success";
	public static final String DEFAULT_MESSAGE_NORMAL_PARAM = "normal";
	public static final String DEFAULT_APPMOBILE_PARAM = "appLogin";
	public static final String DEFAULT_LOGINTYPE_PARAM = "loginType";
	
    
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String appeLoginParam = DEFAULT_APPMOBILE_PARAM;
	private String messageErrorParam = DEFAULT_MESSAGE_ERROR_PARAM;
	private String messageSuccessParam = DEFAULT_MESSAGE_SUCCESS_PARAM;
	private String messageNormallParam = DEFAULT_MESSAGE_NORMAL_PARAM;
	private String loginTypeParam = DEFAULT_LOGINTYPE_PARAM;
	
	@Autowired
	private LastOnlineDao lastOnlineDao;

    @Autowired
	private SysConfigDao sysConfigDao;

//	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);
    /**
     * 登录验证
     */
	@Override
	protected boolean executeLogin(ServletRequest request,
            ServletResponse response) throws Exception {
		UsernamePasswordToken token = createToken(request, response);
        try {
            /*图形验证码验证*/
        	//判断是否进行验证码验证
        	PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
        	if(passwordSecurityMap.getPsdValidateIs().equals("1")) {
        		doCaptchaValidate((HttpServletRequest) request, token);
        	}
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证
            LogUtil.info(token.getUsername()+"登录成功");
            LogUtils.saveLog(ContextHolderUtils.getRequest(), "登录成功",token.getUsername()+"登录成功",SysLog.LEVEL2);

            // 更新版本号
            SysConfig sysConfig = sysConfigDao.querySysConfigByName(JSP_UPDATE_VERSION);
            CacheUtils.put(JSP_UPDATE_VERSION, null == sysConfig? DEFAULT_JSP_UPDATE_VERSION : sysConfig.getValue());

            //记录登录时间等信息
            optLastOnline(request);
			boolean isRedirect = true;
			String isRedirectStr = SysPropertiesUtil.getConfig("isRedirect");
			if(StringUtil.isNotEmpty(isRedirectStr) && isRedirectStr.equals("false")){
				isRedirect = false;
			}
			if(isRedirect){
				//获取登陆前url，登录后直接跳转
				String serverName = request.getServletContext().getContextPath();
				SavedRequest savedRequest = WebUtils.getSavedRequest(request);
				if(StringUtil.isNotEmpty(savedRequest)){
					String backUrl = savedRequest.getRequestUrl();
					//判断首次登陆时无URL情况
					if(StringUtil.isNotEmpty(backUrl) &&
							StringUtil.isNotEmpty(backUrl.replace(serverName+"/",""))
							&& !backUrl.endsWith(".js") && !backUrl.endsWith(".css") && !backUrl.contains("images") && !backUrl.contains("static")
					){
						super.issueSuccessRedirect(request, response);
						return false;
					}
				}
			}
            return onLoginSuccess(token, subject, request, response);
        }catch (AuthenticationException e) {
        	e.printStackTrace();
        	LogUtil.error(token.getUsername()+"登录失败--" + e.getMessage(),e);
        	LogUtils.saveLog(ContextHolderUtils.getRequest(), "登录失败",token.getUsername()+"登录失败",SysLog.LEVEL1);
            return onLoginFailure(token, e, request, response);
        }
    }

	// 验证码校验
	protected void doCaptchaValidate(HttpServletRequest request, UsernamePasswordToken token) {

		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute("ROLLDATA_UUID");

		// session中的图形码字符串
		String cacheVerifyCode = (String) CacheUtils.get("ROLLDATA_PICCODE" + uuid);
		if (token.getCaptcha() == null) {
			throw new RepeatAuthenticationException(MessageUtils.getMessage("common.sys.login.captcha.notnull"));
		}
		if (!token.getCaptcha().equalsIgnoreCase(cacheVerifyCode)) {
			CacheUtils.put("ROLLDATA_PICCODE" + uuid, null);
			throw new RepeatAuthenticationException(MessageUtils.getMessage("common.sys.login.captcha.error"));
		}
		CacheUtils.put("ROLLDATA_PICCODE" + uuid, null);
	}

    /**账号密码登录*/
	@Override
	protected UsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		username = XssShieldUtil.stripXss(username);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		password = XssShieldUtil.stripXss(password);
		//前台加密传输，后台密码解密
		try {
			password = URLDecoder.decode(password,"UTF-8");
			byte[] decodedBytes = Base64.getDecoder().decode(password);
			password = new String(decodedBytes);
			byte[] decodedBytes2 = Base64.getDecoder().decode(password);
			password = new String(decodedBytes2);
		}catch (Exception e){
			e.printStackTrace();
			LogUtil.error(e.getMessage(),e);
		}
		boolean rememberMe = false;//isRememberMe(request);
		String host = IpUtils.getIpAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		captcha = XssShieldUtil.stripXss(captcha);
//		String loginType = getLoginType(request);
		String loginType = LoginType.TYPE_PASSWORD;
		boolean appMobile = isMobileLogin(request);
		boolean isMobile = BrowserUtils.isMobile((HttpServletRequest) request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, isMobile,appMobile,loginType);
	}
	/**免密登录*/
	protected UsernamePasswordToken createToken(ServletRequest request) {
		String username = getUsername(request);
		String loginType = getLoginType(request);
		boolean appMobile = isMobileLogin(request);
		boolean isMobile = BrowserUtils.isMobile((HttpServletRequest) request);
		return new UsernamePasswordToken(username, isMobile,appMobile,loginType);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	public void setMobileLoginParam(String mobileLoginParam) {
		this.mobileLoginParam = mobileLoginParam;
	}

	/**  
	 * 获取loginTypeParam  
	 * @return loginTypeParam loginTypeParam  
	 */
	public String getLoginTypeParam() {
		return loginTypeParam;
	}
	

	/**  
	 * 设置loginTypeParam  
	 * @param loginTypeParam loginTypeParam  
	 */
	public void setLoginTypeParam(String loginTypeParam) {
		this.loginTypeParam = loginTypeParam;
	}
	

	/**  
	 * 获取appeLoginParam  
	 * @return appeLoginParam appeLoginParam  
	 */
	public String getAppeLoginParam() {
		return appeLoginParam;
	}
	

	/**  
	 * 设置appeLoginParam  
	 * @param appeLoginParam appeLoginParam  
	 */
	public void setAppeLoginParam(String appeLoginParam) {
		this.appeLoginParam = appeLoginParam;
	}
	

	public void setMessageErrorParam(String messageErrorParam) {
		this.messageErrorParam = messageErrorParam;
	}

	public void setMessageSuccessParam(String messageSuccessParam) {
		this.messageSuccessParam = messageSuccessParam;
	}

	public void setMessageNormallParam(String messageNormallParam) {
		this.messageNormallParam = messageNormallParam;
	}

	public String getMessageErrorParam() {
		return messageErrorParam;
	}

	public String getMessageSuccessParam() {
		return messageSuccessParam;
	}

	public String getMessageNormallParam() {
		return messageNormallParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected String getLoginType(ServletRequest request) {
		return WebUtils.getCleanParam(request, getLoginTypeParam());
	}
	
	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, getMobileLoginParam());
	}

	/**
	 * 登录成功之后跳转URL
	 */
	@Override
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		Principal p = UserUtils.getPrincipal();
		UserUtils.clearCache();
		if (p != null && !p.isAppLogin()) {
			WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		} else {//app
			AjaxJson ajaxJson = new AjaxJson();
			ajaxJson.setSuccess(true);
			ajaxJson.setMsg(MessageUtils.getMessage("common.sys.login.success"));
			ajaxJson.put("userCde", p.getUserCde());
			ajaxJson.put("userName", p.getUserName());
			ajaxJson.put("mobileLogin", p.isMobileLogin());
			ajaxJson.put("appLogin", p.isAppLogin());
			ajaxJson.put("rolldata_uid", p.getSessionid());
			StringUtil.printJson((HttpServletResponse) response, ajaxJson);
		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		super.onLoginFailure(token, e, request, response);
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
//		if (!authcToken.isMobileLogin()) {
			String className = e.getClass().getName(), message = "";
			if (IncorrectCredentialsException.class.getName().equals(className)) {
				message = MessageUtils.getMessage("common.sys.login.nopass");
			}else if(UnknownAccountException.class.getName().equals(className)){
				message = MessageUtils.getMessage("common.sys.login.nopass");
			} else if (RepeatAuthenticationException.class.getName().equals(className)) {
				message = e.getMessage();
			}else if (LockedAccountException.class.getName().equals(className)) {
				message = MessageUtils.getMessage("common.sys.login.locked");
			} else if (ExcessiveAttemptsException.class.getName().equals(className)) {
				message = MessageUtils.getMessage("common.sys.login.resubmit");
			} else if (StringUtils.isNoneBlank(e.getMessage())) {
				message = e.getMessage();
			} else {
				message = MessageUtils.getMessage("common.sys.login.error");
				e.printStackTrace(); // 输出到控制台
			}
			request.setAttribute(getFailureKeyAttribute(), className);
			request.setAttribute(getMessageErrorParam(), message);
			//验证错误返回用户名
			request.setAttribute("userName", authcToken.getUsername());
			return true;
//		} else {
//			// 登录失败返回false
//			AjaxJson ajaxJson = new AjaxJson();
//			ajaxJson.setSuccess(false);
//			ajaxJson.setMsg(MessageUtils.getMessage("common.sys.login.fail"));
//			ajaxJson.put("mobileLogin", authcToken.isMobileLogin());
//			ajaxJson.put("JSESSIONID", UserUtils.getSession().getId());
//			StringUtil.printJson((HttpServletResponse) response, ajaxJson);
//			return false;
//		}
	}
    
    
    /**
     * 操作最后在线用户表
     * @param request
     * @throws Exception
     */
    private void optLastOnline(ServletRequest request) throws Exception {
        SysLastOnline sysLastOnline = lastOnlineDao.querySysLastOnlineByUserId(UserUtils.getUser().getId());
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (null != sysLastOnline) {
            sysLastOnline.setLoginCount(String.valueOf(Integer.parseInt(sysLastOnline.getLoginCount()) + 1));
            saveAndFlushLastOnline(sysLastOnline, httpRequest);
        } else {
            SysLastOnline newSysLastOnline = new SysLastOnline();
            newSysLastOnline.setCreateTime(new Date());
            newSysLastOnline.setLoginCount("1");
            newSysLastOnline.setUserId(UserUtils.getUser().getId());
            saveAndFlushLastOnline(newSysLastOnline, httpRequest);
        }
    }
    
    /**
     * 保存或修改 SysLastOnline实体
     * @param sysLastOnline
     * @param httpRequest
     */
    private void saveAndFlushLastOnline(SysLastOnline sysLastOnline, HttpServletRequest httpRequest) {
        
        sysLastOnline.setUpdateTime(new Date());
        sysLastOnline.setLastLoginTimestamp(new Date());
        if (httpRequest != null) {
            sysLastOnline.setHost(IpUtils.getIpAddr(httpRequest));
            sysLastOnline.setUserAgent(httpRequest.getHeader("User-Agent"));
            sysLastOnline.setSystemHost(httpRequest.getLocalAddr() + ":" + httpRequest.getLocalPort());
        }
        lastOnlineDao.saveAndFlush(sysLastOnline);
    }

	/**
	 * 配合前台调试前后分离的异步请求添加
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//
//		if(this.isLoginRequest(request, response)) {
//			if(this.isLoginSubmission(request, response)) {
//				if(log.isTraceEnabled()) {
//					log.trace("Login submission detected. Attempting to execute login.");
//				}
//
//				return this.executeLogin(request, response);
//			} else {
//				if(log.isTraceEnabled()) {
//					log.trace("Login page view.");
//				}
//
//				return true;
//			}
//		} else {
//			if(log.isTraceEnabled()) {
//				log.trace("Attempting to access a path which requires authentication. Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
//			}
//
//			this.saveRequestAndRedirectToLogin(request, response);
//			return false;
//		}
//	}
}
