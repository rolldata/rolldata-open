package com.rolldata.web.system.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @Title: SysConfig
 * @Description: 系统配置信息
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_config")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysConfig implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6359614331592107288L;

	/**邮箱的相关配置*/
	public static final String TYPE_MAIL = "mail";

	/**密码安全的相关配置*/
	public static final String TYPE_SECURITY = "security";

	/**系统的相关配置*/
	public static final String TYPE_SYSTEM = "system";

	/**外观配置*/
	public static final String TYPE_APPEARANCE = "appearance";
	
	public static final String POINT = ".";

	/**密码安全相关配置*/
	public static final String PASSWORD_SECURITY = TYPE_SECURITY + POINT + "password";
	/** 密码长度 */
	public static final String PASSWORD_LENGTH = PASSWORD_SECURITY + POINT + "length";
	/** 密码组合配置 1:数字，2:字母+数字，3:特殊字符+字母+数字 */
	public static final String PASSWORD_SETTING = PASSWORD_SECURITY + POINT + "setting";
	/** 是否启用修改密码期限 */
	public static final String PASSWORD_PERIOD_IS = PASSWORD_SECURITY + POINT + "period" + POINT + "is";
	/** 修改密码期限 1:是，0:否 */
	public static final String PASSWORD_PERIOD = PASSWORD_SECURITY + POINT + "period";
	/** 忘记密码是否发送请求 1:是，0:否 */
	public static final String PASSWORD_REQUEST = PASSWORD_SECURITY + POINT + "request";
	/** 是否启用输入密码错误锁定用户次数 1:是，0:否 */
	public static final String PASSWORD_ERRNUMBER_IS = PASSWORD_SECURITY + POINT + "errnumber" + POINT + "is";
	/** 输入密码错误锁定用户次数 */
	public static final String PASSWORD_ERRNUMBER = PASSWORD_SECURITY + POINT + "errnumber";
	/** 是否启用验证码 1:是，0:否 */
	public static final String PASSWORD_VALIDATE_IS = PASSWORD_SECURITY + POINT + "validate" + POINT + "is";
	/** 是否启用强制修改初始密码 */
	public static final String PASSWORD_INIT_IS = PASSWORD_SECURITY + POINT + "init" + POINT + "is";

	/**是否限制一处登陆*/
	public static final String LOGIN_LIMIT =  TYPE_SECURITY + POINT + "login" + POINT + "limit" + POINT + "is";

	/**是否开启访问密码1:是，0:否*/
	public static final String BROWSE_PASSWORD_IS =  PASSWORD_SECURITY + POINT + "browse" + POINT + "is";

	/**资源访问密码*/
	public static final String BROWSE_PASSWORD =  PASSWORD_SECURITY + POINT + "browse";

	/**是否限制登录人数1:是，0:否*/
	public static final String LOGINNUM_PASSWORD_IS =  PASSWORD_SECURITY + POINT + "loginNum" + POINT + "is";

	/**限制的登录人数*/
	public static final String LOGINNUM_PASSWORD =  PASSWORD_SECURITY + POINT + "loginNum";

	/** 邮箱SMTP服务器 */
	public static final String MAIL_SMTPSERVER = TYPE_MAIL + POINT + "SMTPServer";
	/** 端口号 */
	public static final String MAIL_SERVERPORT = TYPE_MAIL + POINT + "serverPort";
	/** 发件人地址 */
	public static final String MAIL_SENDERADDRESS = TYPE_MAIL + POINT + "senderAddress";
	/** 密码 */
	public static final String MAIL_PASSWORD = TYPE_MAIL + POINT + "password";
	/** 显示姓名 */
	public static final String MAIL_DISPLAYNAME = TYPE_MAIL + POINT + "displayName";
	/** 是否启用SSL安全连接1:是，0:否 */
	public static final String MAIL_SSLIS = TYPE_MAIL + POINT + "SSLIS";

	/** 是否验证通过1:是，0:否 */
	public static final String MAIL_VERIFY = TYPE_MAIL + POINT + "verify";
	
	/**
	 * 微信公众平台标识
	 */
	public static final String WEBCHAT_PUBLICPLATFORM = "publicPlatform";

	/**
	 * 微信开放平台标识
	 */
	public static final String WEBCHAT_OPENPLATFORM = "openPlatform";

	/**
	 * 微信应用id标识
	 */
	public static final String WEBCHAT_APPID = "appId";

	/**
	 * 微信应用密码标识
	 */
	public static final String WEBCHAT_APPSECRET = "appSecret";

	/**上传文件的相关配置*/
	public static final String SYSTEM_UPLOAD = TYPE_SYSTEM + POINT + "upload";

	/**上传文件大小，单位兆*/
	public static final String UPLOAD_FILESIZE = SYSTEM_UPLOAD + POINT + "fileSize";
	
	/**上传图片大小，单位兆*/
	public static final String UPLOAD_IMGSIZE = SYSTEM_UPLOAD + POINT + "imgSize";
	
//	/**上传图片最小宽度，单位px*/
//	public static final String UPLOAD_IMGMINWIDTH = SYSTEM_UPLOAD + POINT + "imgMinWidth";
//	
//	/**上传图片最大宽度，单位px*/
//	public static final String UPLOAD_IMGMAXWIDTH = SYSTEM_UPLOAD + POINT + "imgMaxWidth";
//	
//	/**上传图片最小高度，单位px*/
//	public static final String UPLOAD_IMGMINHIGHT = SYSTEM_UPLOAD + POINT + "imgMinHight";
//	
//	/**上传图片最大高度，单位px*/
//	public static final String UPLOAD_IMGMAXHIGHT = SYSTEM_UPLOAD + POINT + "imgMaxHight";
	

	public static final String SYSTEM_VERSION = TYPE_SYSTEM + POINT + "version" + POINT + "num";

	public static final String SYSTEM_RELEASETIME = TYPE_SYSTEM + POINT + "release" + POINT + "time";

	public static final String SYSTEM_LAST_UPDATE_TIME = TYPE_SYSTEM + POINT + "last" + POINT + "updatetime";

	public static final String SYSTEM_CRON = TYPE_SYSTEM + POINT +"cron";

	public static final String SYSTEM_CRON_FILLTASK = SYSTEM_CRON + POINT + "fillTask";

	public static final String SYSTEM_CRON_DPTASK = SYSTEM_CRON + POINT + "dpTask";

	public static final String SYSTEM_CRON_WARNTASK = SYSTEM_CRON + POINT + "warnTask";

	public static final String SYSTEM_CRON_SENDMESTASK = SYSTEM_CRON + POINT + "sendMesTask";

	public static final String SYSTEM_CRON_SYNACCOUNTDATA = SYSTEM_CRON + POINT + "synAccountData";

	public static final String SYSTEM_CRON_THIRDPARTYINTERFACEDATA = SYSTEM_CRON + POINT + "thirdpartyInterfaceData";

	public static final String SYSTEM_CRON_CALCULATEITEMDISPATCH = SYSTEM_CRON + POINT + "calculateItemDispatch";

	public static final String ISINIT = "isInit";

	public static final String APPEARANCE_PORTAL = TYPE_APPEARANCE + POINT +"portal";

	/**登陆标题*/
	public static final String APPEARANCE_PORTAL_LOGINTITLE = APPEARANCE_PORTAL + POINT + "loginTitle";

	/**登陆logo*/
	public static final String APPEARANCE_PORTAL_LOGINLOGO = APPEARANCE_PORTAL + POINT + "loginLogo";

	/**是否初始登陆logo*/
	public static final String APPEARANCE_PORTAL_LOGINLOGO_ISINIT = APPEARANCE_PORTAL_LOGINLOGO + POINT + ISINIT;

	/**平台标题*/
	public static final String APPEARANCE_PORTAL_TITLE = APPEARANCE_PORTAL + POINT + "title";

	/**平台logo*/
	public static final String APPEARANCE_PORTAL_LOGO = APPEARANCE_PORTAL + POINT + "logo";

	/**是否初始平台logo*/
	public static final String APPEARANCE_PORTAL_LOGO_ISINIT = APPEARANCE_PORTAL_LOGO + POINT + ISINIT;

	/**是否显示品牌信息，0否，1是*/
	public static final String APPEARANCE_PORTAL_ISSHOW = APPEARANCE_PORTAL + POINT + "isShow";

	/**登陆背景图*/
	public static final String APPEARANCE_PORTAL_LOGINBACKGROUND = APPEARANCE_PORTAL + POINT + "loginBackground";

	/**是否初始登陆背景图*/
	public static final String APPEARANCE_PORTAL_LOGINBACKGROUND_ISINIT = APPEARANCE_PORTAL_LOGINBACKGROUND + POINT + ISINIT;

	/**门户类型*/
	public static final String APPEARANCE_PORTAL_TYPE = APPEARANCE_PORTAL + POINT + "type";

	/**整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格*/
	public static final String APPEARANCE_PORTAL_STYLE = APPEARANCE_PORTAL + POINT + "style";

	/**页面布局，1经典，2双排菜单，3顶部菜单*/
	public static final String APPEARANCE_PORTAL_PAGE_LAYOUT = APPEARANCE_PORTAL + POINT + "pageLayout";

	/**主题色*/
	public static final String APPEARANCE_PORTAL_THEME_COLOR = APPEARANCE_PORTAL + POINT + "themeColor";

	/**顶栏应用主题色,0否1是*/
	public static final String APPEARANCE_PORTAL_TOPBARUSRTHEMECOLOR = APPEARANCE_PORTAL + POINT + "topBarUsrThemeColor";

	/**顶栏主题色通栏,0否1是*/
	public static final String APPEARANCE_PORTAL_TOPBARTHEMECOLORBANNER = APPEARANCE_PORTAL + POINT + "topBarThemeColorBanner";

	/**是否开启资源水印，0否1是*/
	public static final String APPEARANCE_PORTAL_ISOPENRESOURCEWATERMARK = APPEARANCE_PORTAL + POINT + "isOpenResourceWatermark";

	/**
     * 前台文件版本(用的多再拆,目前就这一串)
     */
    public static final String JSP_UPDATE_VERSION = "jsp.update.version";

    /**
     * 没有版本时,默认给值
     */
    public static final String DEFAULT_JSP_UPDATE_VERSION = "v1.0.0";


	/**
	 * 代码名称
	 */
	@Id
	private String name;

	/**
	 * 值
	 */
	@Column(name = "c_value", nullable = true, length = 100)
	private String value;

	/**
	 * 类型（mail邮箱,security安全,webchat微信）
	 */
	@Column(name = "c_type", nullable = true, length = 20)
	private String type;

	/**
	 * 获取代码名称
	 * 
	 * @return name 代码名称
	 */
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	/**
	 * 设置代码名称
	 * 
	 * @param name 代码名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取值
	 * 
	 * @return value 值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value 值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取类型（mail邮箱security安全）
	 * 
	 * @return type 类型（mail邮箱security安全）
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型（mail邮箱security安全）
	 * 
	 * @param type 类型（mail邮箱security安全）
	 */
	public void setType(String type) {
		this.type = type;
	}

}
