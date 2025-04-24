package com.rolldata.web.system.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.rolldata.core.extend.datasource.DataSourceType;
import com.rolldata.core.util.*;
import com.rolldata.web.system.pojo.DataSourceInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;


/**
 * @Description : 连接数据库的工具类
 * @author : zhaibx
 * @time : 2019-1-9
 */
public class DBUtil {
	private static final Logger log = LogManager.getLogger(DBUtil.class);

	/**
	 * 阿里连接池
	 */
	public static final DruidDataSource DRUID_DATA_SOURCE = SpringContextHolder.getBean(
		DataSourceType.dataSource_formal.name()
	);

	public static final DataSourceInfo DATA_SOURCE_INFO = new DataSourceInfo();

    static {
        String filename = "dbconfig.properties";
        String path = ResourceUtil.getSysClassesPath();
        PropertiesUtil properties = new PropertiesUtil(path + filename);
        String dbType = properties.getString(DataSourceTool.formalJdbcDbType).toUpperCase();
        DATA_SOURCE_INFO.setDbDriver(properties.getString(DataSourceTool.formalJdbcDriver));
        DATA_SOURCE_INFO.setDbUrl(properties.getString(DataSourceTool.formalJdbcUrlWd));
        DATA_SOURCE_INFO.setDbUserName(properties.getString(DataSourceTool.formalJdbcUsernameWd));
        DATA_SOURCE_INFO.setDbPassword(properties.getString(DataSourceTool.formalJdbcPasswordWd));
        DATA_SOURCE_INFO.setDbType(dbType);
        String dbUrl = DATA_SOURCE_INFO.getDbUrl();
        if (DataSourceTool.DBTYPE_MYSQL.equalsIgnoreCase(dbType) &&
            dbUrl.indexOf("rewriteBatchedStatements") == -1) {

            // 最后一个字符
            if (!dbUrl.endsWith("&")) {
                dbUrl += "&";
            }

            // mysql不加这个串,预编译批量执行也很慢, 加之前1w=16s, 后1w=3s
            DATA_SOURCE_INFO.setDbUrl(dbUrl + "rewriteBatchedStatements=true&useServerPrepStmts=true");
        }
    }
	
	/**
	 * 获得链接（动态）
	 * @param driver 驱动
	 * @param url 驱动
	 * @param usr 数据库用户名
	 * @param pwd 密码
	 * @return conn
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @date:2016-5-18下午8:44:44
	 */
	public static Connection getConnection(String driver, String url, String usr, String pwd) throws ClassNotFoundException, SQLException {
		
		Connection conn = null;
		Class.forName(driver);
//		log.info("驱动:" + driver + "URL:" + url +  "用户名" + usr + "密码"+ pwd);
		conn = DriverManager.getConnection(url, usr, pwd);

		return conn;

	}
	/**
	 * 关闭数据库连接
	 * @author : zhaibx
	 * @param conn
	 */
	public static void closeConnection(Connection conn){
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭 PreparedStatement 对象
	 * @author : zhaibx
	 * @param statement
	 */
	public static void closePreparedStatement(PreparedStatement statement){
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭 ResultSet 对象
	 * @author : zhaibx
	 * @param resultSet
	 */
	public static void closeResultSet(ResultSet resultSet){
		
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 连接数据库时  各项信息不能为空的判断
	 * @param
	 * @return
	 */
	public static boolean filtrationConnDB(DataSourceInfo info){
		if(null == info.getDbType() || "".equals(info.getDbType())){
			return false;
		}
		if(null == info.getDbIp() || "".equals(info.getDbIp())){
			return false;
		}
		if(null == info.getDbPort() || "".equals(info.getDbPort())){
			return false;
		}
		if(null == info.getDbName() || "".equals(info.getDbName())){
			return false;
		}
		if(null == info.getDbUserName() || "".equals(info.getDbUserName())){
			return false;
		}
		/**mysql root密码可能为空字符*/
		if(null == info.getDbPassword()){
			return false;
		}
		return true;
	}
	public static void closeStatement(Statement statement) {
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据配置文件获取系统的conn连接
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getSystemConn() throws ClassNotFoundException, SQLException{

        return DBUtil.getConnection(
            DATA_SOURCE_INFO.getDbDriver(),
            DATA_SOURCE_INFO.getDbUrl(),
            DATA_SOURCE_INFO.getDbUserName(),
            DATA_SOURCE_INFO.getDbPassword()
        );
	}
	
	public static DataSourceInfo getSystemDataSourceInfo() {
		String filename = "dbconfig.properties";
		DataSourceInfo dbInfo = new DataSourceInfo();
		String path = ResourceUtil.getSysClassesPath();
		PropertiesUtil properties = new PropertiesUtil(path + filename);
		String dbType = properties.getString(DataSourceTool.formalJdbcDbType).toUpperCase();
		dbInfo.setDbDriver(properties.getString(DataSourceTool.formalJdbcDriver));
		dbInfo.setDbUrl(properties.getString(DataSourceTool.formalJdbcUrlWd));
		dbInfo.setDbUserName(properties.getString(DataSourceTool.formalJdbcUsernameWd));
		dbInfo.setDbPassword(properties.getString(DataSourceTool.formalJdbcPasswordWd));
		dbInfo.setDbType(dbType);
		return dbInfo;
	}

	public static DataSourceInfo getSystemDataSourceInfoByUrl(){
		DataSourceInfo dbInfo = DBUtil.getSystemDataSourceInfo();
		String dbType = dbInfo.getDbType().toLowerCase();
		String dbUrl = dbInfo.getDbUrl();
		if(dbUrl.indexOf("?")>=0){
			String[] advancedArr = dbUrl.split("\\?");
			dbInfo.setAdvancedStr("?"+advancedArr[1]);
			dbUrl = advancedArr[0];
		}
		if(dbType.equals(DataSourceTool.DBTYPE_MYSQL)) {
//                dbUrl = "jdbc:mysql://"+info.getDbIp()+":"+info.getDbPort()+"/"+info.getDbName();//jdbc:mysql://127.0.0.1:3306/mysql
			dbUrl = dbUrl.replace("jdbc:mysql://","");
			String[] tempStrArr = dbUrl.split(":");
			dbInfo.setDbIp(tempStrArr[0]);
			String lastTempStr = tempStrArr[1];
			String[] lastArr = lastTempStr.split("/");
			dbInfo.setDbPort(lastArr[0]);
			dbInfo.setDbName(lastArr[1]);
		} else if (dbType.equals(DataSourceTool.DBTYPE_H2)) {
            //                //jdbc:h2:tcp://localhost:9094/rolldata
            dbUrl = dbUrl.replace("jdbc:h2:tcp://","");
            String[] tempStrArr = dbUrl.split(":");
            dbInfo.setDbIp(tempStrArr[0]);
            String lastTempStr = tempStrArr[1];
            String[] lastArr = lastTempStr.split("/");
            dbInfo.setDbPort(lastArr[0]);
            dbInfo.setDbName(lastArr[1]);
        } else if(dbType.equals(DataSourceTool.DBTYPE_ORACLE)) {
//                dbUrl = "jdbc:oracle:thin:@"+info.getDbIp()+":"+info.getDbPort()+":"+info.getDbName();//jdbc:oracle:thin:@127.0.0.1:1521:ORCL
			dbUrl = dbUrl.replace("jdbc:oracle:thin:@","");
			String[] tempStrArr = dbUrl.split(":");
			dbInfo.setDbIp(tempStrArr[0]);
			String lastTempStr = tempStrArr[1];
			String[] lastArr = lastTempStr.split("/");
			dbInfo.setDbPort(lastArr[0]);
			dbInfo.setDbName(lastArr[1]);
		}else if(dbType.equals(DataSourceTool.DBTYPE_SQLSERVER)){
//                dbUrl = "jdbc:sqlserver://"+info.getDbIp()+":"+info.getDbPort()+";DatabaseName="+info.getDbName();//jdbc:sqlserver://192.168.1.6:1433;DatabaseName=RP
			dbUrl = dbUrl.replace("jdbc:sqlserver://","");
			String[] tempStrArr = dbUrl.split(":");
			dbInfo.setDbIp(tempStrArr[0]);
			String lastTempStr = tempStrArr[1];
			String[] lastArr = lastTempStr.split(";DatabaseName=");
			dbInfo.setDbPort(lastArr[0]);
			dbInfo.setDbName(lastArr[1]);
		}else if(dbType.equals(DataSourceTool.DBTYPE_DM8)) {
//                dbUrl = "jdbc:mysql://"+info.getDbIp()+":"+info.getDbPort()+"/"+info.getDbName();//jdbc:mysql://127.0.0.1:3306/mysql
			dbUrl = dbUrl.replace("jdbc:dm://","");
			String[] tempStrArr = dbUrl.split(":");
			dbInfo.setDbIp(tempStrArr[0]);
			String lastTempStr = tempStrArr[1];
			String[] lastArr = lastTempStr.split("/");
			dbInfo.setDbPort(lastArr[0]);
			dbInfo.setDbName(lastArr[1]);
		}
		return dbInfo;
	}

	/**
	 * 设置预编译参数
	 *
	 * @param pstmt  预编译 SQL 语句的对象
	 * @param params 预编译集合
	 * @throws SQLException
	 */
	public static void addParams(PreparedStatement pstmt, List<String> params) throws SQLException {

		int index = 1;
		if (params != null && !params.isEmpty()) {
			log.info("预编译参数:{}", JsonUtil.format(params));
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
	}

    /**
     * 提交事务,报错回滚
     *
     * @param conn
     * @throws SQLException
     */
    public static void commit(Connection conn) throws SQLException {

        if (null == conn) {
            return;
        }
        conn.setAutoCommit(false);
        try {
            conn.commit();
        } catch (SQLException e) {
            log.error("提交失败", e);
            rollback(conn);
            throw e;
        }
    }

    public static void rollback(Connection conn) {

        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("回滚失败", e);
            }
        }
    }

	public static String columnTransfer( String dbType, String columnName, String columnType, String columnlength, String precision, String isNull, String isPK) {
		String newColumnSql = "";
		String newColumnLength = "";
		String newColumnTypeName = "";
		String newColumnName = "";
		String noNull = "NOT NULL ";
		Integer length = null;
		if(StringUtil.isNotEmpty(columnlength)){
			length = Integer.valueOf(columnlength);
		}
		if(dbType.equalsIgnoreCase(DataSourceTool.DBTYPE_MYSQL)){
			// 字段类型,不要长度
			if ("datetime".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "datetime";
				newColumnLength = "";
			} else if ("datetime2".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "datetime";
				newColumnLength = "";
			} else if ("int".equalsIgnoreCase(columnType) || "TINYINT".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "int";
				newColumnLength = "";
			} else if("money".equalsIgnoreCase(columnType) ){
				newColumnTypeName = "decimal";
				newColumnLength = "(" + length + "," + precision + ")";
			} else if("bigint".equalsIgnoreCase(columnType) ){
				newColumnTypeName = "decimal";
				newColumnLength = "(" + length + "," + precision + ")";
			} else if ("NVARCHAR".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "varchar";
				newColumnLength = "(" + length + ")";
			}  else if ("VARCHAR2".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "varchar";
				newColumnLength = "(" + length + ")";
			} else if ("LONGBLOB".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR";
			} else if ("SMALLINT".equalsIgnoreCase(columnType)) {
				newColumnLength = "(" + length + ")";
				newColumnTypeName = "NVARCHAR";
			} else {
				newColumnTypeName = columnType;
				if(StringUtil.isNotEmpty(precision)){
					newColumnLength = "(" + length + "," + precision + ")";
				}else {
					newColumnLength = "(" + length + ")";
				}
			}
			if(StringUtil.isNotEmpty(columnlength)) {
				if (length >= 21845) {
					newColumnTypeName = "TEXT";
					newColumnLength = "";
				} else if (length == 715827882) {
					newColumnTypeName = "TEXT";
					newColumnLength = "";
				} else if (length >= 5000 && columnType.equalsIgnoreCase("NVARCHAR2")) {
					newColumnTypeName = "TEXT";
//                            newColumnLength = "(4000)";
					newColumnLength = "";
				}
			}
			// 0=not null 1=可以为空
			if (isPK.equals("Y") || isNull.equals("NO") ) {
				noNull = "NOT NULL ";
			} else {
				noNull = "NULL ";
			}
		}else if(dbType.equalsIgnoreCase(DataSourceTool.DBTYPE_ORACLE) ||
				dbType.equalsIgnoreCase(DataSourceTool.DBTYPE_DM8)){
			// 字段类型,不要长度
			if ("datetime".equalsIgnoreCase(columnType)) {
				newColumnLength = "";
				newColumnTypeName = "DATE";
			} else if ("datetime2".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "DATE";
				newColumnLength = "";
			} else if ("int".equalsIgnoreCase(columnType)) {
				newColumnLength = "(" + length + ")";
				newColumnTypeName = "NUMBER";
			} else if ("varchar".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR2";
				newColumnLength = "(" + length + ")";
			} else if ("text".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "CLOB";
				newColumnLength = "";
			} else if ("tinyint".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NUMBER";
				newColumnLength = "(" + length + ")";
			} else if ("longblob".equalsIgnoreCase(columnType)) {
				newColumnLength = "";
				newColumnTypeName = "CLOB";
			} else if ("SMALLINT".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR2";
				newColumnLength = "(" + length + ")";
			} else {
				newColumnTypeName = columnType;
				if(StringUtil.isNotEmpty(precision)){
					newColumnLength = "(" + length + "," + precision + ")";
				}else {
					newColumnLength = "(" + length + ")";
				}
			}
			if(StringUtil.isNotEmpty(columnlength)) {
				// mysql text=21845,这里也用clob
				if (length >= 21845) {
					newColumnTypeName = "CLOB";
					newColumnLength = "";
				} else if (length == 715827882) {
					newColumnTypeName = "CLOB";
					newColumnLength = "";
				} else if (length >= 5000 && columnType.equalsIgnoreCase("NVARCHAR2")) {
					newColumnTypeName = "CLOB";
//                            newColumnLength = "(4000)";
					newColumnLength = "";
				}
			}
			// 0=not null 1=可以为空
			if (isPK.equals("Y") || isNull.equals("0")) {
				noNull = "NOT NULL ";
			} else {
				noNull = "NULL ";
			}
		} else if(dbType.equalsIgnoreCase(DataSourceTool.DBTYPE_SQLSERVER)) {
			// 字段类型,不要长度
			if ("datetime".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "datetime";
				newColumnLength = "";
			} else if ("datetime2".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "datetime";
				newColumnLength = "";
			} else if ("int".equalsIgnoreCase(columnType) || "TINYINT".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "int";
				newColumnLength = "";
			} else if("money".equalsIgnoreCase(columnType) ){
				newColumnTypeName = "money";
				newColumnLength = "";
			} else if("bigint".equalsIgnoreCase(columnType) ){
				newColumnTypeName = "bigint";
				newColumnLength = "";
			} else if ("varchar".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR";
				newColumnLength = "(" + length + ")";
				if (length >= 4000) {
					newColumnLength = "(MAX)";
				}
			} else if ("text".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR";
				newColumnLength = "(MAX)";
			} else if ("LONGBLOB".equalsIgnoreCase(columnType)) {
				newColumnTypeName = "NVARCHAR";
			} else if ("SMALLINT".equalsIgnoreCase(columnType)) {
				newColumnLength = "(" + length + ")";
				newColumnTypeName = "NVARCHAR";
			} else {
				newColumnTypeName = columnType;
				if(StringUtil.isNotEmpty(precision)){
					newColumnLength = "(" + length + "," + precision + ")";
				}else {
					newColumnLength = "(" + length + ")";
				}
			}
			if(StringUtil.isNotEmpty(columnlength)) {
				// mysql text=21845
				if (length >= 21845) {
					newColumnLength = "(MAX)";
				} else if (length == 715827882) {
					newColumnLength = "(MAX)";
				}else if (length == -1) {
					newColumnLength = "(MAX)";
				}
			}
			// 0=not null 1=可以为空
			if (isPK.equals("Y") || isNull.equals("0")) {
				noNull = "NOT NULL ";
			} else {
				noNull = "NULL ";
			}
		}
		newColumnSql = " " + columnName + " " + newColumnTypeName
				+ newColumnLength + " " + noNull ;
		return newColumnSql;
    }
}
