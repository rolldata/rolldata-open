package com.rolldata.core.extend.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.rolldata.core.util.DataSourceTool;
import com.rolldata.web.system.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @Title: DynamicDataSource
 * @Description: 动态数据源类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private Logger log = LogManager.getLogger(DynamicDataSource.class);
	private Map<Object, Object> dynamicTargetDataSources;
	private Object dynamicDefaultTargetDataSource;
	/*
	 * 该方法必须要重写 方法是为了根据数据库标示符取得当前的数据库
	 */

	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType dataSourceType = DataSourceContextHolder.getDataSourceType();
		if(Constants.property == null) {
			new Constants();//初始化配置
		}
		if (Constants.property.get(Constants.dataSourceType) != null) {
//			String isInit = (String) Constants.property.get(Constants.isInit);
//			if (isInit.equals("true")) {
				dataSourceType = (DataSourceType) Constants.property.get(Constants.dataSourceType);
//			} else {
//				dataSourceType = DataSourceType.dataSource_wd;
//			}
			DataSourceContextHolder.setDataSourceType(dataSourceType);
			return dataSourceType;
		}
		DynamicDataSource dataSource = new DynamicDataSource();
		boolean isConnect = dataSource.testDatasource(DataSourceTool.FORMALDBDRIVER, DataSourceTool.FORMALDBURL,
				DataSourceTool.FORMALDBUSR, DataSourceTool.FORMALDBPWD);
		if (isConnect) {
			// 主数据库
			dataSourceType = DataSourceType.dataSource_formal;
			Constants.property.put(Constants.dataSourceType, dataSourceType);
		} else {
			boolean isConnectWd = dataSource.testDatasource(DataSourceTool.SPAREDBDRIVER, DataSourceTool.SPAREDBURL,
					DataSourceTool.SPAREDBUSR, DataSourceTool.SPAREDBPWD);
			if(isConnectWd) {
				if(DataSourceTool.SPAREDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
					// 从数据库
					dataSourceType = DataSourceType.dataSource_wd;
					Constants.property.put(Constants.dataSourceType, dataSourceType);
				}else {
					// 从数据库
					dataSourceType = DataSourceType.dataSource_wd;
					Constants.property.put(Constants.dataSourceType, dataSourceType);
				}
			}else {
				// 从数据库
				dataSourceType = DataSourceType.dataSource_wd;
				Constants.property.put(Constants.dataSourceType, dataSourceType);
			}
		}
		DataSourceContextHolder.setDataSourceType(dataSourceType);
		log.info("---当前数据源：" + dataSourceType + "---");
		return dataSourceType;
	}

	@Override
	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

	@Override
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
	}

	@Override
	public void setTargetDataSources(Map targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		this.dynamicTargetDataSources = targetDataSources;
		// 重点
		super.afterPropertiesSet();
	}

	// 创建数据源
	public boolean createDataSource(String key, String driveClass, String url, String username, String password) {
		try {
			try { // 排除连接不上的错误
				Class.forName(driveClass);
				DriverManager.getConnection(url, username, password);// 相当于连接数据库
			} catch (Exception e) {
				return false;
			}
			@SuppressWarnings("resource")
			DruidDataSource druidDataSource = new DruidDataSource();
			druidDataSource.setName(key);
			druidDataSource.setDriverClassName(driveClass);
			druidDataSource.setUrl(url);
			druidDataSource.setUsername(username);
			druidDataSource.setPassword(password);
			druidDataSource.setMaxWait(60000);
			druidDataSource.setFilters("stat");
			DataSource createDataSource = (DataSource) druidDataSource;
			druidDataSource.init();
			Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
			dynamicTargetDataSources2.put(key, createDataSource);// 加入map
			setTargetDataSources(dynamicTargetDataSources2);// 将map赋值给父类的TargetDataSources
			super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
			return true;
		} catch (Exception e) {
			log.error(e + "");
			return false;
		}
	}

	// 删除数据源
	public boolean delDatasources(String datasourceid) {
		Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
		if (dynamicTargetDataSources2.containsKey(datasourceid)) {
			Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
			for (DruidDataSource l : druidDataSourceInstances) {
				if (datasourceid.equals(l.getName())) {
					dynamicTargetDataSources2.remove(datasourceid);
					DruidDataSourceStatManager.removeDataSource(l);
					setTargetDataSources(dynamicTargetDataSources2);// 将map赋值给父类的TargetDataSources
					super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	// 测试数据源连接是否有效
	public boolean testDatasource(String driveClass, String url, String username, String password) {
		try {
			Class.forName(driveClass);
			DriverManager.getConnection(url, username, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return the dynamicTargetDataSources
	 */
	public Map<Object, Object> getDynamicTargetDataSources() {
		return dynamicTargetDataSources;
	}

	/**
	 * @param dynamicTargetDataSources the dynamicTargetDataSources to set
	 */
	public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
		this.dynamicTargetDataSources = dynamicTargetDataSources;
	}

	/**
	 * @return the dynamicDefaultTargetDataSource
	 */
	public Object getDynamicDefaultTargetDataSource() {
		return dynamicDefaultTargetDataSource;
	}

	/**
	 * @param dynamicDefaultTargetDataSource the dynamicDefaultTargetDataSource to
	 *                                       set
	 */
	public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
		this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
	}
}
