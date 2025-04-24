package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 
 * @Title: DataSourcePojo
 * @Description: 数据源交互类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2021年11月10日
 * @version V1.0
 */
public class DataSourcePojo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6195307381252942445L;
	/**数据源id*/
	private String dsId;
	
	/**数据源名称*/
	private String dsName;

	/**
	 * 数据类型 0文件夹 1数据源
	 */
	private String type;

	/**
	 * 父id
	 */
	private String parentId;

	/**数据库类型*/
	private String dbType;
	
	/**数据源类型，U8特殊处理*/
	private String dsType;
	
	/**数据库驱动*/
	private String dbDriver;

	/**数据库URL*/
	private String dbUrl;
	
	/**数据库用户名*/
	private String dbUserName;
	
	/**数据库密码*/
	private String dbPassword;

	/**IP地址*/
	private String dbIp;
	
	/**端口号*/
	private String dbPort;
	
	/**数据库名*/
	private String dbName;
	
	/**高级参数串*/
	private String advancedStr;
	
	/**数据库方言*/
	private String dialect;

	/**目录结构*/
	private String dsFolders;

	/**是否仅在有权限的用户上显示*/
	private String dsOnlyRoot;

	/**表结构模式*/
	private String dsTableModel;

	/**是否需要登录*/
	private String dsIsLogin;

	/**数据库最大连接数*/
	private String dbConnMaxNum;

	/**  
	 * 获取数据源名称  
	 * @return dsName 数据源名称  
	 */
	public String getDsName() {
		return dsName;
	}
	
	/**  
	 * 设置数据源名称  
	 * @param dsName 数据源名称  
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	/**  
	 * 获取数据库类型  
	 * @return dbType 数据库类型  
	 */
	public String getDbType() {
		return dbType;
	}
	
	/**  
	 * 设置数据库类型  
	 * @param dbType 数据库类型  
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**  
	 * 获取数据库驱动  
	 * @return dbDriver 数据库驱动  
	 */
	public String getDbDriver() {
		return dbDriver;
	}

	/**  
	 * 设置数据库驱动  
	 * @param dbDriver 数据库驱动  
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
	/**  
	 * 获取数据库URL  
	 * @return dbUrl 数据库URL  
	 */
	public String getDbUrl() {
		return dbUrl;
	}
	
	/**  
	 * 设置数据库URL  
	 * @param dbUrl 数据库URL  
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	/**  
	 * 获取数据库用户名  
	 * @return dbUserName 数据库用户名  
	 */
	public String getDbUserName() {
		return dbUserName;
	}
	
	/**  
	 * 设置数据库用户名  
	 * @param dbUserName 数据库用户名  
	 */
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	
	/**  
	 * 获取数据库密码  
	 * @return dbPassword 数据库密码  
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	
	/**  
	 * 设置数据库密码  
	 * @param dbPassword 数据库密码  
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	/**  
	 * 获取IP地址  
	 * @return dbIp IP地址  
	 */
	public String getDbIp() {
		return dbIp;
	}

	/**  
	 * 设置IP地址  
	 * @param dbIp IP地址  
	 */
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	
	/**  
	 * 获取端口号  
	 * @return dbPort 端口号  
	 */
	public String getDbPort() {
		return dbPort;
	}

	/**  
	 * 设置端口号  
	 * @param dbPort 端口号  
	 */
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	/**  
	 * 获取数据库名  
	 * @return dbName 数据库名  
	 */
	public String getDbName() {
		return dbName;
	}
	
	/**  
	 * 设置数据库名  
	 * @param dbName 数据库名  
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**  
	 * 获取高级参数串  
	 * @return advancedStr 高级参数串  
	 */
	public String getAdvancedStr() {
		return advancedStr;
	}

	/**  
	 * 设置高级参数串  
	 * @param advancedStr 高级参数串  
	 */
	public void setAdvancedStr(String advancedStr) {
		this.advancedStr = advancedStr;
	}
	
	/**  
	 * 获取数据库方言  
	 * @return dialect 数据库方言  
	 */
	public String getDialect() {
		return dialect;
	}
	
	/**  
	 * 设置数据库方言  
	 * @param dialect 数据库方言  
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	/**  
	 * 获取数据源类型，U8特殊处理  
	 * @return dsType 数据源类型，U8特殊处理  
	 */
	public String getDsType() {
		return dsType;
	}

	/**  
	 * 设置数据源类型，U8特殊处理  
	 * @param dsType 数据源类型，U8特殊处理  
	 */
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	/**  
	 * 获取数据源id  
	 * @return dsId 数据源id  
	 */
	public String getDsId() {
		return dsId;
	}
	
	/**  
	 * 设置数据源id  
	 * @param dsId 数据源id  
	 */
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	/**
	 * 获取 数据类型 0文件夹 1数据源
	 *
	 * @return type 数据类型 0文件夹 1数据源
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 数据类型 0文件夹 1数据源
	 *
	 * @param type 数据类型 0文件夹 1数据源
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 父id
	 *
	 * @return parentId 父id
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父id
	 *
	 * @param parentId 父id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取 目录结构
	 *
	 * @return dsFolders 目录结构
	 */
	public String getDsFolders() {
		return this.dsFolders;
	}

	/**
	 * 设置 目录结构
	 *
	 * @param dsFolders 目录结构
	 */
	public void setDsFolders(String dsFolders) {
		this.dsFolders = dsFolders;
	}

	/**
	 * 获取 是否仅在有权限的用户上显示
	 *
	 * @return dsOnlyRoot 是否仅在有权限的用户上显示
	 */
	public String getDsOnlyRoot() {
		return this.dsOnlyRoot;
	}

	/**
	 * 设置 是否仅在有权限的用户上显示
	 *
	 * @param dsOnlyRoot 是否仅在有权限的用户上显示
	 */
	public void setDsOnlyRoot(String dsOnlyRoot) {
		this.dsOnlyRoot = dsOnlyRoot;
	}

	/**
	 * 获取 表结构模式
	 *
	 * @return dsTableModel 表结构模式
	 */
	public String getDsTableModel() {
		return this.dsTableModel;
	}

	/**
	 * 设置 表结构模式
	 *
	 * @param dsTableModel 表结构模式
	 */
	public void setDsTableModel(String dsTableModel) {
		this.dsTableModel = dsTableModel;
	}

	/**
	 * 获取 是否需要登录
	 *
	 * @return dsIsLogin 是否需要登录
	 */
	public String getDsIsLogin() {
		return this.dsIsLogin;
	}

	/**
	 * 设置 是否需要登录
	 *
	 * @param dsIsLogin 是否需要登录
	 */
	public void setDsIsLogin(String dsIsLogin) {
		this.dsIsLogin = dsIsLogin;
	}

	/**
	 * 获取 数据库最大连接数
	 *
	 * @return dbConnMaxNum 数据库最大连接数
	 */
	public String getDbConnMaxNum() {
		return this.dbConnMaxNum;
	}

	/**
	 * 设置 数据库最大连接数
	 *
	 * @param dbConnMaxNum 数据库最大连接数
	 */
	public void setDbConnMaxNum(String dbConnMaxNum) {
		this.dbConnMaxNum = dbConnMaxNum;
	}
}
