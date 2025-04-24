package com.rolldata.web.system.security.web;

/**
 * 
 * @Title: UsernamePasswordToken
 * @Description: 用户和密码（包含验证码）令牌类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;
	private boolean appLogin;
	//默认0用户名密码登陆
	private String loginType;

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha,
			boolean mobileLogin,boolean appLogin,String loginType) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.appLogin = appLogin;
		this.loginType = loginType;
	}
	/**免密登录*/
    public UsernamePasswordToken(String username,boolean mobileLogin,boolean appLogin,String loginType) {
        super(username, "", false, null);
        this.mobileLogin = mobileLogin;
		this.appLogin = appLogin;
        this.loginType = loginType;
    }

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}
	
	public boolean isAppLogin() {
		return appLogin;
	}

	/**  
	 * 获取loginType  
	 * @return loginType loginType  
	 */
	public String getLoginType() {
		return loginType;
	}
	
	/**  
	 * 设置loginType  
	 * @param loginType loginType  
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
}