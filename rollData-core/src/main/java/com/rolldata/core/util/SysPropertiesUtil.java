package com.rolldata.core.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 
 * @Title: SysPropertiesUtil
 * @Description: 全局配置文件工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class SysPropertiesUtil extends PropertiesUtil {
	private static String FINANCE_BPROPERTIES_FILENAME = "sysConfig.properties";
	private static SysPropertiesUtil propertiesUtil = new SysPropertiesUtil();
	// 保存全局属性值
	private static Map<String, String> configMap = Maps.newHashMap();

	public SysPropertiesUtil() {
		load(FINANCE_BPROPERTIES_FILENAME);
	}

	public static SysPropertiesUtil getSysProperties() {
		if (propertiesUtil != null) {
			propertiesUtil = new SysPropertiesUtil();
		}
		return propertiesUtil;
	}

	/**
	 * 获得配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		String value = configMap.get(key);
		if (value == null) {
			value = propertiesUtil.getString(key);
			configMap.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 设置配置
	 * 
	 * @param key
	 * @param value
	 */
	public static void setConfig(String key, Object value) {
		propertiesUtil.set(key, value);
	}

	/**
	 * 移除配置
	 * 
	 * @param key
	 * @return
	 */
	public static boolean removeConfig(String key) {
		return propertiesUtil.remove(key);
	}
}
