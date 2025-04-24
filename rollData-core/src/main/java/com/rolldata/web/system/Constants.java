package com.rolldata.web.system;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	// 字典缓存KEY
	public static final String CACHE_DICT_MAP = "cacheDictMap";
	public static final String CURRENT_USER = "systemuser";
	public static final String CURRENT_USERNAME = "systemusername";

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String MESSAGE = "message";
	
	/**数据源*/
	public static final String dataSourceType = "dataSourceType";

	/**
	 * 注册信息标识
	 */
	public static final String registerInfo = "registerInfo";
	
	/**
	 * 邮箱配置标识
	 */
	public static final String mailManageInfo = "mailManageInfo";
	
	/**
	 * 密码安全标识
	 */
	public static final String passwordSecurity = "passwordSecurity";
	
	/**
	 * 系统配置标识
	 */
	public static final String sysConfigInfo = "systemConfig";
	
	/**
	 * 微信配置标识
	 */
	public static final String webChatInfo = "webChatInfo";

	/**
	 * 外观配置标识
	 */
	public static final String appearanceConfig = "appearanceConfig";

	/**
	 * 初始化安装状态，1安装中，2成功，3失败
	 */
	public static final String installState = "installState";
	
	/**
	 * 初始化安装信息
	 */
	public static final String installMsg = "installMsg";
	
	/**
	 * 初始化安装进度
	 */
	public static final String installNum = "installNum";

	/**
	 * 切换数据源标识
	 */
	public static final String switchDataSource = "switchDataSource";

	/**
	 * 更新产品状态，1更新中，2成功，3失败
	 */
	public static final String updateState = "updateState";

	/**
	 * 更新产品信息
	 */
	public static final String updateMsg = "updateMsg";

	/**
	 * 更新产品进度
	 */
	public static final String updateNum = "updateNum";

	/**
	 * 组件内资源授权信息
	 */
	public static final String plugResourceLic = "plugResourceLic";

	/**
	 * 包含配置文件中全部信息map
	 */
	public static Map<String, Object> property;
	
	public Constants() {
		super();
		this.init();
	}
	public void init() {
		if (property != null) {
			return;
		}
		property = new HashMap<String, Object>();
	}
}
