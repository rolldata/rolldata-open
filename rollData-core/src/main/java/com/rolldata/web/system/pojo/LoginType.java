package com.rolldata.web.system.pojo;

/**
 * 
 * @Title: LoginType
 * @Description: 登陆类型
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class LoginType {
	/**密码登陆*/
	public static final String TYPE_PASSWORD = "0";
	/**微信登陆*/
	public static final String TYPE_WECHAT = "1";
	/**钉钉登陆*/
	public static final String TYPE_DING = "2";
	/**单点登陆*/
	public static final String TYPE_SSO = "3";
	/**飞书登陆*/
	public static final String TYPE_FEISHU = "4";
}
