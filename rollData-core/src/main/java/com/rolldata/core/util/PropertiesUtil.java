package com.rolldata.core.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @Title: PropertiesUtil
 * @Description: Properties文件工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class PropertiesUtil extends ObjectSwitchHelper {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private Properties properties;
	private String[] resourcesPaths;
	private String properiesName = "";

	public PropertiesUtil() {

	}

	public static PropertiesUtil create(String... resourcesPaths) {
		PropertiesUtil propertiesUtil = new PropertiesUtil(resourcesPaths);
		return propertiesUtil;
	}

	public PropertiesUtil(String... resourcesPaths) {
		load(resourcesPaths);
	}

	public void load(String... resourcesPaths) {
		this.resourcesPaths = resourcesPaths;
		properties = new Properties();
		for (String location : resourcesPaths) {
			logger.debug("Loading properties file from:" + location);
			InputStream is = null;
			try {
				is = new FileInputStream(getAbsolutePath(location));
				properties.load(is);
			} catch (IOException ex) {
				logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
			} catch (Exception ex) {
				logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
	}

	/**
	 * 更新与删除时指定文件名称
	 * 
	 * @param properiesName
	 */
	public void setOptProperiesName(String properiesName) {
		this.properiesName = properiesName;
	}

	@Override
	public Object get(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return "";
	}

	public String getAbsolutePath(String filename) {
		if (!FileUtil.isAbsolutePath(filename)) {
			filename = PropertiesUtil.class.getClassLoader().getResource(filename).getPath();
		}
		return filename;
	}

	@Override
	public void set(String key, Object value) {
		if (StringUtil.isEmpty(this.properiesName)) {
			this.properiesName = this.resourcesPaths[0];
		}
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			FileUtil.createFile(getAbsolutePath(this.properiesName));
			is = new FileInputStream(getAbsolutePath(this.properiesName));
			p.load(is);
			os = new FileOutputStream(getAbsolutePath(this.properiesName));
			p.setProperty(key, value + "");
			p.store(os, "====配置更新====");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		this.properiesName = "";
	}
	
	public void set(Map<String, Object> dataMap) {
		if (StringUtil.isEmpty(this.properiesName)) {
			this.properiesName = this.resourcesPaths[0];
		}
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			FileUtil.createFile(getAbsolutePath(this.properiesName));
			is = new FileInputStream(getAbsolutePath(this.properiesName));
			p.load(is);
			os = new FileOutputStream(getAbsolutePath(this.properiesName));
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				p.setProperty(entry.getKey(), entry.getValue() + "");
			}
			p.store(os, "====配置更新====");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		this.properiesName = "";
	}

	@Override
	public boolean remove(String key) {
		if (StringUtil.isEmpty(this.properiesName)) {
			this.properiesName = this.resourcesPaths[0];
		}
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(getAbsolutePath(this.properiesName));
			p.load(is);
			os = new FileOutputStream(getAbsolutePath(this.properiesName));
			p.remove(key);
			p.store(os, "");
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		this.properiesName = "";
		return false;
	}
	
	public String readProperty(String key) {
		String value = "";
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					properiesName);
			Properties p = new Properties();
			p.load(is);
			value = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	public static void main(String[] args) {
		String properiesName = "sysConfig.properties";
		PropertiesUtil propertiesUtil = new PropertiesUtil(properiesName);
		String sdfsdf = propertiesUtil.getString("web.staticFile");
		System.out.println(sdfsdf);
		// 指定更新与删除时指定文件名称
		// propertiesUtil.setOptProperiesName("generation/generation_config.properties");
		// propertiesUtil.set("project_path", "sdfsdfsd");

	}

}
