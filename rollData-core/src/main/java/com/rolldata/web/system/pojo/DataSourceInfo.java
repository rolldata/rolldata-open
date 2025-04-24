package com.rolldata.web.system.pojo;
/**
 * 
 * @Title: DataSourceInfo
 * @Description: 数据源交互实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class DataSourceInfo {
	/**
	 * 是否初始化数据库，0否，1是
	 */
	private String isInitDB = "0";

    /**
     * 是否内置数据库，0否，1是
     */
	private String isBuiltInDatabase = "0";

	/**数据库类型*/
	private String dbType;
	
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
	
	/**
	 * 数据库方言
	 */
	private String dialect;

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
	 * 获取用户名  
	 * @return dbUserName 用户名  
	 */
	public String getDbUserName() {
		return dbUserName;
	}

	/**  
	 * 设置用户名  
	 * @param dbUserName 用户名  
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
	 * 获取是否初始化数据库，0否，1是  
	 * @return isInitDB 是否初始化数据库，0否，1是  
	 */
	public String getIsInitDB() {
		return isInitDB;
	}
	
	/**  
	 * 设置是否初始化数据库，0否，1是  
	 * @param isInitDB 是否初始化数据库，0否，1是  
	 */
	public void setIsInitDB(String isInitDB) {
		this.isInitDB = isInitDB;
	}
	
	/**
	 * 获取 数据库方言
	 *
	 * @return dialect 数据库方言
	 */
	public String getDialect () {
		return this.dialect;
	}
	
	/**
	 * 设置 数据库方言
	 *
	 * @param dialect 数据库方言
	 */
	public void setDialect (String dialect) {
		this.dialect = dialect;
	}

    /**
     * 获取 是否内置数据库，0否，1是
     *
     * @return isBuiltInDatabase 是否内置数据库，0否，1是
     */
    public String getIsBuiltInDatabase() {
        return this.isBuiltInDatabase;
    }

    /**
     * 设置 是否内置数据库，0否，1是
     *
     * @param isBuiltInDatabase 是否内置数据库，0否，1是
     */
    public void setIsBuiltInDatabase(String isBuiltInDatabase) {
        this.isBuiltInDatabase = isBuiltInDatabase;
    }
}
