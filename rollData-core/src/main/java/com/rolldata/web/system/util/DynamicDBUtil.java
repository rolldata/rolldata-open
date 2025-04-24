package com.rolldata.web.system.util;

import com.rolldata.core.util.*;
import com.rolldata.web.system.pojo.ProcedureParams;
import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.service.JdbcQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * <p>Title:HHDynamicDBUtil</p>
 * <p>Description:Spring JDBC 实时数据库访问</p>
 * <p>Company:www.wrenchdata.com</p>
 * @author:申世龙
 * @date:2016-5-10下午8:16:00
 */
public class DynamicDBUtil {

    private static final Logger logger = LogManager.getLogger(DynamicDBUtil.class);

    /**
     * 数据库类型
     */
    public static final String DATABSE_TYPE_MYSQL = "MYSQL";
    public static final String DATABSE_TYPE_POSTGRE = "POSTGRESQL";
    public static final String DATABSE_TYPE_ORACLE = "ORACLE";
    public static final String DATABSE_TYPE_SQLSERVER = "SQLSERVER";
	public static final String DATABSE_TYPE_DM8 = "DM8";
	public static final String DATABSE_TYPE_H2 = "H2";
	public static final String DATABSE_TYPE_SQLITE = "SQLITE";
	public static final String DATABSE_TYPE_POSTGRESQL = "POSTGRESQL";
	public static final String DATABSE_TYPE_DERBY = "DERBY";

	public static final String DATABSE_TYPE_DB2 = "DB2";

	public static final String DATABSE_TYPE_ACCESS = "ACCESS";

	public static final String DATABSE_TYPE_SYBASE = "SYBASE";

	public static final String DATABSE_TYPE_HIVE = "HIVE";

    /**
     * 分页SQL
     */
    public static final String MYSQL_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";         //mysql
    public static final String H2_SQL = "select * from ( {0}) sel_tab00 limit {1},{2}";
    public static final String POSTGRE_SQL = "select * from ( {0}) sel_tab00 limit {2} offset {1}";//postgresql
    public static final String ORACLE_SQL = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}"; //oracle
    public static final String SQLSERVER_SQL = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}"; //sqlserver

    //add-end--Author:luobaoli  Date:20150620 for：增加返回值为List的方法

    public static final Map<String, String> SQL_PAGE_TEMPLATE = new HashMap<>(9);

    static {
        SQL_PAGE_TEMPLATE.put(DATABSE_TYPE_MYSQL, MYSQL_SQL);
        SQL_PAGE_TEMPLATE.put(DATABSE_TYPE_H2, H2_SQL);
        SQL_PAGE_TEMPLATE.put(DATABSE_TYPE_POSTGRE, POSTGRE_SQL);
        SQL_PAGE_TEMPLATE.put(DATABSE_TYPE_ORACLE, "select * from (select row_.*,rownum rownum_ from ({0}) row_ where"
            + " rownum <= {2}) where rownum_>{1}");
        SQL_PAGE_TEMPLATE.put(DATABSE_TYPE_SQLSERVER, "select * from ( select row_number() over(order by tempColumn) "
            + "tempRowNumber, * from (select top {2} tempColumn = 0, {0}) t ) tt where tempRowNumber > {1}");
    }

	/**
	 * 按照数据库类型，封装SQL 为了查询数据  dbsUrl 数据库URL  不可为空
	 * @return sql
	 */
	public static String hhCreatePageSql(String dbsUrl, String sql, int page, int rows, String sort, String asc){
		int beginNum = (page - 1) * rows;
		String[] sqlParam = new String[3];
		sqlParam[0] = sql;
		sqlParam[1] = beginNum + "";
		sqlParam[2] = rows + "";

		if(dbsUrl.indexOf(DATABSE_TYPE_MYSQL) != -1){

			sql = MessageFormat.format(MYSQL_SQL, sqlParam);
		} else if (dbsUrl.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = MessageFormat.format(H2_SQL, sqlParam);
        } else if(dbsUrl.indexOf(DATABSE_TYPE_POSTGRE)!=-1){

			sql = MessageFormat.format(POSTGRE_SQL, sqlParam);
		}else {

			int beginIndex = (page-1) * rows;
			int endIndex = beginIndex + rows;
			sqlParam[2] = Integer.toString(beginIndex);
			sqlParam[1] = Integer.toString(endIndex);
			if(dbsUrl.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
					dbsUrl.indexOf(DATABSE_TYPE_DM8) != -1) {
				sql = MessageFormat.format(ORACLE_SQL, sqlParam);
			} else if(dbsUrl.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
				sqlParam[0] = sql.substring(getAfterSelectInsertPoint(sql));
				sql = MessageFormat.format(SQLSERVER_SQL, sqlParam);
			}
		}

		/** for update 申世龙 添加 排序字段*/
		if(null != sort && !"".equals(sort)){
			sql = "(" + sql + ")" + " order by " + sort + " " + asc;
		}
		return sql;
	}

	public static int getAfterSelectInsertPoint(String sql) {
	    int selectIndex = sql.toLowerCase().indexOf("select");
	    int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
	    return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }

	/**
	 * 组装:查询表的字段的sql
	 *
	 * @param tbName - 表名
	 * @param dbType - 数据库类型(可以用数据库的URL)
	 * @return
	 */
	public static SQLContainer hhCreateTCSql(String tbName, String dbType){

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
			sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = (select database());";
		} else if (dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema ='PUBLIC';";
        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
			sql = " select us.column_name as column_name from user_tab_columns us where us.table_name=? ";
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = " SELECT name FROM SysColumns WHERE id=Object_Id(?);";
		}
		sqlContainer.addPrecompiledValues(tbName);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 组装:查询表的字段的sql
	 * @param tbName - 表名
	 * @param dbName - 数据库名(拥有者)
	 * @param dbType - 数据库类型(可以用数据库的URL)
	 * @return sql
	 */
	public static SQLContainer getColumNameSql(String tbName, String dbName, String dbType, String sqlServerParam){

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if (StringUtil.isEmpty(dbType)) {
            dbType = DATABSE_TYPE_MYSQL;
        }
        dbType = dbType.toUpperCase();
		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
			sql = " select COLUMN_NAME, DATA_TYPE from information_schema.COLUMNS where table_name = ? and table_schema = ?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(dbName);
		} else if (dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = " select COLUMN_NAME, DATA_TYPE from information_schema.COLUMNS where table_name = ? and table_schema = ?";
            sqlContainer.addPrecompiledValues(tbName);
            sqlContainer.addPrecompiledValues("PUBLIC");
        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){

			sql = " select COLUMN_NAME, DATA_TYPE from all_tab_columns where Table_Name=? and owner = ?  ";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(dbName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			//废：SELECT name FROM SysColumns WHERE id=Object_Id('" + tbName + "')
			sql = " select COLUMN_NAME, DATA_TYPE from [" + dbName + "].information_schema.COLUMNS where table_name = ? and table_schema = ?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(sqlServerParam);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLITE) != -1) {
            sql = "  PRAGMA table_info('" + tbName + "')";
        } else if(dbType.indexOf(DATABSE_TYPE_POSTGRESQL) != -1) {
			sql = " select COLUMN_NAME, DATA_TYPE from information_schema.COLUMNS where table_name = ? and table_schema = ?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues("public");
		} else if(dbType.indexOf(DATABSE_TYPE_DERBY) != -1){
            sql = " SELECT COLUMNNAME,COLUMNDATATYPE FROM SYS.SysColumns t WHERE t.REFERENCEID = (SELECT TABLEID FROM SYS.SysTables t WHERE t.TABLENAME = ?)";
            sqlContainer.addPrecompiledValues(tbName);
        } else if(dbType.indexOf(DATABSE_TYPE_DB2) != -1) {
			sql = "SELECT COLNAME, TYPENAME FROM SYSCAT.COLUMNS WHERE TABSCHEMA IN (SELECT GRANTEE FROM SYSIBM.SYSDBAUTH WHERE DBADMAUTH = 'Y') AND TABNAME = ?";
			sqlContainer.addPrecompiledValues(tbName);
		} else if (dbType.indexOf(DATABSE_TYPE_ACCESS) != -1) {
			sql = "select COLUMN_NAME, DATA_TYPE from information_schema.COLUMNS where table_name = ? and table_schema = 'PUBLIC'";

			// 测试 ACCESS 库只认大写的表,虽然看着是小写
			sqlContainer.addPrecompiledValues(StringUtil.isNotEmpty(tbName) ? tbName.toUpperCase() : "");
		} else if (dbType.indexOf(DATABSE_TYPE_SYBASE) != -1) {
			sql = "SELECT c.name AS column_name, t.name AS data_type FROM syscolumns c left join systypes t ON c.type = t.type and c.usertype = t.usertype WHERE id = OBJECT_ID(?)";

			// 不知道为啥Dbeaver可以执行,代码运行不能为空,赋值个逗号
			sqlContainer.addPrecompiledValues(StringUtil.isEmpty(tbName) ? "'" : tbName);
		} else if (dbType.indexOf(DATABSE_TYPE_HIVE) != -1) {
			sql = "DESCRIBE " + tbName;

//			sqlContainer.addPrecompiledValues(StringUtil.isEmpty(tbName) ? "'" : tbName);
		}


        logger.info("获取表字段和类型的sql:{}", sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 简单组装查询sql-字段的集合
	 * @param list 字段的集合
	 * @param dsType 数据库类型
	 * @param tbName 表名
	 * @param dbName 数据库名
	 * @return sql 返回  <B>select xx, xx from dbName.tbName</B>
	 */

	public static String getTableOrViewSql(List<String> list, String dsType, String tbName, String dbName, String sqlServerParam){

		String param = "", sql = "";
		if(null == list || list.size() ==0){
			param = " * ,";
		}else{

			//如果是mysql  每个字段前加个表名 因为 mysql里关键字可以当作字段 用sql查询时报错
			if(DATABSE_TYPE_MYSQL.equals(dsType.toUpperCase())){
				for (int i = 0; i < list.size(); i++) {
					param += tbName + "." + list.get(i) + ",";
				}
				sql = " select " + param.substring(0, param.length() - 1) + " from  " + dbName + "." + tbName + "";
			} else if(DATABSE_TYPE_H2.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += tbName + "." + list.get(i) + ",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from  " + dbName + "." + tbName + "";
            } else if(DATABSE_TYPE_SQLSERVER.equals(dsType.toUpperCase())){
				/*
				 * 基本格式 select * from [erqi].[dbo].[t_s_function]
				 */
				for (int i = 0; i < list.size(); i++) {
					param += tbName + "." + list.get(i) + ",";
				}
				sql = " select " + param.substring(0, param.length() - 1) + " from  [" + dbName + "].[" + sqlServerParam + "].[" + tbName + "]";
			} else if(DATABSE_TYPE_SQLITE.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "\"" + list.get(i) + "\",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from  \"" + tbName + "\"";
            } else if(DATABSE_TYPE_POSTGRESQL.equals(dsType.toUpperCase())){
				for (int i = 0; i < list.size(); i++) {
					param += "\"" + tbName + "\".\"" + list.get(i) + "\",";
				}
				sql = " select " + param.substring(0, param.length() - 1) + " from  " + "public.\"" + tbName + "\"";
			} else if(DATABSE_TYPE_DERBY.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "\"" + list.get(i) + "\",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from  " + "APP.\"" + tbName + "\"";
            } else if(DATABSE_TYPE_DB2.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "" + list.get(i) + ",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from  " + "\"" + tbName + "\"";
            } else if(DATABSE_TYPE_ACCESS.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "\"" + list.get(i) + "\",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from " + tbName;
            } else if(DATABSE_TYPE_SYBASE.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "\"" + list.get(i) + "\",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from "+ "\"" + tbName + "\"";
            } else if(DATABSE_TYPE_HIVE.equals(dsType.toUpperCase())){
                for (int i = 0; i < list.size(); i++) {
                    param += "" + list.get(i) + ",";
                }
                sql = " select " + param.substring(0, param.length() - 1) + " from " + dbName + "." + tbName + "";
            } else {
				for (int i = 0; i < list.size(); i++) {
					param += "\"" + list.get(i) + "\",";
				}
				String db = dbName + ".";
				if (StringUtil.isEmpty(dbName)) {
                    db = "";
                }
				sql = " select " + param.substring(0, param.length() - 1) + " from  " + db + "\"" + tbName + "\"";
			}

		}
        logger.info("查询sql:{}", sql);
		return sql;
	}

    /**
     * <P>根据jdbc查出的结果集      <B>*只获取第一个字段</B> </P>
     *
     * @param rs
     * @return String
     * @throws SQLException
     */
	public static List<String> getResultOne(ResultSet rs) throws SQLException{

		List<String> list = new ArrayList<>();
		if(null != rs){
			while (rs.next()) {
                list.add(rs.getString(1));
			}
		}
		return list;
	}

	/**
	 * <P>根据jdbc查出的结果集      <B>*获取指定序号字段值</B> </P>
	 *
	 * @param rs
	 * @return String
	 * @throws SQLException
	 */
	public static List<String> getResultOne(ResultSet rs,int columnNum) throws SQLException{

		List<String> list = new ArrayList<>();
		if(null != rs){
			while (rs.next()) {
				list.add(rs.getString(columnNum));
			}
		}
		return list;
	}

	/**
	 * <P>根据jdbc查出的结果集      <B>*获取指定列名字段值</B> </P>
	 *
	 * @param rs
	 * @return String
	 * @throws SQLException
	 */
	public static List<String> getResultOne(ResultSet rs,String columnName) throws SQLException{

		List<String> list = new ArrayList<>();
		if(null != rs){
			while (rs.next()) {
				list.add(rs.getString(columnName));
			}
		}
		return list;
	}

	/**
	 * 根据数据库类型返回 查询所有库数据库的sql
	 * @param dbType 数据库类型（可以是数据库url）
	 * @return sql
	 */

	public static String getAllDataBaseSql(String dbType){

		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1 ||
            dbType.indexOf(DATABSE_TYPE_H2) != -1){
			sql = " show databases ";
		}else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
			sql = " select username from all_users  order by username ";
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			//TODO 可调整（参考dbeaver） SELECT db.* FROM sys.databases db ORDER BY db.name
			//已调整，原语句select * from Master..SysDatabases where dbid > 4 order by name 需要sa用户权限
			sql = " SELECT db.* FROM sys.databases db ORDER BY db.name";
		} else if(dbType.indexOf(DATABSE_TYPE_POSTGRESQL) != -1) {
			sql = " SELECT datname FROM pg_database";
		} else if(dbType.indexOf(DATABSE_TYPE_SQLITE) != -1) {
			sql = " PRAGMA database_list";
		} else if(dbType.indexOf(DATABSE_TYPE_DB2) != -1) {
			sql = " SELECT DB_NAME FROM sysibmadm.snapdb";
		} else if(dbType.indexOf(DATABSE_TYPE_SYBASE) != -1) {
			sql = " SELECT name FROM sysdatabases";
		} else if(dbType.indexOf(DATABSE_TYPE_HIVE) != -1) {
			sql = " show databases";
		}

		return sql;

	}

	/**
	 * 获取当前连接所使用的库
	 *
	 * @param dbType 数据库类型
	 * @return
	 */
	public static String getCurrentDataBaseSQL(String dbType) {

        String sql = "";
        if (DATABSE_TYPE_MYSQL.equals(dbType)) {
            sql = " select database() ";
        } else if (DATABSE_TYPE_H2.equals(dbType)) {
            sql = "select 'PUBLIC'";
        } else if (DATABSE_TYPE_ORACLE.equals(dbType) ||
            DATABSE_TYPE_DM8.equals(dbType)) {

            // TODO:
            sql = "";
        } else if (DATABSE_TYPE_SQLSERVER.equals(dbType)) {
            //TODO 可简化 select db_name()
            sql =
                " select name from master..SysDataBases where dbId=(Select dbid From master..sysprocesses Where spid = @@spid) ";
        }

        return sql;
    }

    /**
     * 根据拥有者,查询库里面的表
     *
     * @param dbType          数据库类型
     * @param owner           拥有者
     * @param sqlServerParam  支持SQLSERVER库
     * @param searchTableName 过滤表名
     * @return sql
     */
    public static SQLContainer getAllTableSql(String dbType, String owner, String sqlServerParam,
        String searchTableName) {

        SQLContainer sqlContainer = new SQLContainer();
        String sql = "";
        String tableName = "table_name";

        if (dbType.indexOf(DATABSE_TYPE_MYSQL) != -1 ||
            dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = " select t.table_name from information_schema.tables t where t.table_schema=? ";
            sqlContainer.addPrecompiledValues(owner);
        }

        if (dbType.indexOf(DATABSE_TYPE_SQLITE) != -1) {
            sql = " select name from sqlite_master where type='table'";
            tableName = "name";
        }

        if (dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
            dbType.indexOf(DATABSE_TYPE_DM8) != -1) {
            sql = " select table_name from all_tables where owner = ?";
            sqlContainer.addPrecompiledValues(owner);
        }

        if (dbType.indexOf(DATABSE_TYPE_POSTGRE) != -1) {
            sql = "select t.table_name from information_schema.tables t where t.table_schema = 'public'";
        }

        if (dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
            sql = " select t.table_name from [" + owner + "].information_schema.tables t where t.table_schema=? and t.table_type=? ";
            sqlContainer.addPrecompiledValues(sqlServerParam);
            sqlContainer.addPrecompiledValues("BASE TABLE");
        }
        if (dbType.indexOf(DATABSE_TYPE_DERBY) != -1) {
            sql = " SELECT TABLENAME FROM SYS.SysTables t WHERE t.TABLETYPE = 'T'";
            tableName = "TABLENAME";
        }
		if (dbType.indexOf(DATABSE_TYPE_DB2) != -1) {
//            sql = " SELECT tabname AS name FROM syscat.tables WHERE tabschema IN (SELECT CURRENT SCHEMA FROM SYSIBM.SYSDUMMY1)";
            sql = " SELECT tabname AS name FROM syscat.tables WHERE tabschema IN (SELECT GRANTEE FROM SYSIBM.SYSDBAUTH WHERE DBADMAUTH = 'Y')";
            tableName = "tabname";
        }
		if (dbType.indexOf(DATABSE_TYPE_ACCESS) != -1) {
            sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'";
            tableName = "table_name";
        }
		if (dbType.indexOf(DATABSE_TYPE_SYBASE) != -1) {
            sql = "SELECT name  FROM sysobjects t WHERE type = 'U'";
            tableName = "name";
        }

        // 过滤
        if (StringUtil.isNotEmpty(searchTableName)) {
            sql += " and " + tableName + " like ?";
            sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
        }
        sql += " order by  " + tableName;

		// hive 不能执行过滤,先放最后面,不拼 and了
		if (dbType.indexOf(DATABSE_TYPE_HIVE) != -1) {
            sql = "show VIEWS";
        }

        logger.info("查数据库所有表的sql:{}", sql);
        sqlContainer.setSql(sql);
        return sqlContainer;
    }

    /**
     * 表模式下  根据拥有者,查询库里面的 视图
     *
     * @param dbType          数据库类型（可以是数据库url）
     * @param owner
     * @param sqlServerParam
     * @param searchTableName 过滤表名
     * @return
     */
    public static SQLContainer getAllViewSql(String dbType, String owner, String sqlServerParam,
        String searchTableName) {

        SQLContainer sqlContainer = new SQLContainer();
        String sql = "";
        String orderTableName = " order by t.table_name ";
        String filterTableName = " and table_name like ?";
        if (dbType.indexOf(DATABSE_TYPE_MYSQL) != -1 ||
            dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = " select t.table_name from information_schema.VIEWS t where t.table_schema=?";
            sqlContainer.addPrecompiledValues(owner);
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += filterTableName;
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += orderTableName;
        }
        if (dbType.indexOf(DATABSE_TYPE_SQLITE) != -1) {
            sql = " select name from sqlite_master where type='view' ";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and name like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by name ";
        }
        if (dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
            dbType.indexOf(DATABSE_TYPE_DM8) != -1) {
            sql = " select view_name from all_views where owner = ?";
            sqlContainer.addPrecompiledValues(owner);
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and view_name like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by view_name ";
        }
        if (dbType.indexOf(DATABSE_TYPE_POSTGRE) != -1) {
            //			sql = "SELECT distinct c.relname AS  table_name FROM pg_class c";
            sql = "select t.table_name from information_schema.views t where t.table_schema = 'public'  ";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and table_name like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by table_name ";
        }
        if (dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
            sql = "select t.table_name from [" + owner + "].information_schema.VIEWS t where t.table_schema=?";
            sqlContainer.addPrecompiledValues(sqlServerParam);
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += filterTableName;
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += orderTableName;
        }
        if (dbType.indexOf(DATABSE_TYPE_DERBY) != -1 ) {
            sql = " SELECT TABLENAME FROM SYS.SysTables t WHERE t.TABLETYPE = 'V' ";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and TABLENAME like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by TABLENAME ";
        }
        if (dbType.indexOf(DATABSE_TYPE_DB2) != -1 ) {
//            sql = " SELECT viewname AS name FROM syscat.views WHERE viewschema IN (SELECT CURRENT SCHEMA FROM SYSIBM.SYSDUMMY1) ";
            sql = " SELECT viewname AS name FROM syscat.views WHERE viewschema IN (SELECT GRANTEE FROM SYSIBM.SYSDBAUTH WHERE DBADMAUTH = 'Y') ";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and viewname like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by viewname ";
        }
        if (dbType.indexOf(DATABSE_TYPE_ACCESS) != -1 ) {
            sql = "SELECT table_name FROM information_schema.VIEWs WHERE table_schema = 'PUBLIC'";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and table_name like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by table_name ";
        }
        if (dbType.indexOf(DATABSE_TYPE_SYBASE) != -1 ) {
            sql = "SELECT name FROM sysobjects WHERE type = 'V' ";
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and name like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by name ";
        }
        if (dbType.indexOf(DATABSE_TYPE_HIVE) != -1 ) {
            sql = "show tables";

            // hive 执行sql不带where
        }

        logger.info("获取数据库下所有视图的sql:{}", sql);
        sqlContainer.setSql(sql);
        return sqlContainer;
    }

	/**
	 * 拼装查询表里数据的总记录数
	 * for update 20160805 jdbc连接 表名前面应该加上数据库的名字 ;
	 * @param dbName
	 * @param tableName
	 */
	public static String getDataCount(String dbName, String tableName) {
		String sql = " select count(*) from " + dbName + "." + tableName;
		return sql;
	}

	/**
	 * 组装:查询表的字段详细信息的sql
	 *
	 * @param tbName         - 表名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getColumInfoSql(String tbName, String dbName, String dbType, String sqlServerParam) {

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){

			sql = " SELECT t.COLUMN_NAME, t.DATA_TYPE, case when t.CHARACTER_MAXIMUM_LENGTH is null then t.NUMERIC_PRECISION else t.CHARACTER_MAXIMUM_LENGTH end CHARACTER_MAXIMUM_LENGTH, t.NUMERIC_SCALE, t.IS_NULLABLE, CASE WHEN t.COLUMN_KEY = '' THEN 'N' ELSE 'Y' END AS isPK "
					+ "FROM information_schema.COLUMNS t WHERE t.table_name = ? AND t.table_schema = ?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(dbName);
		} else if (dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = "  select t.COLUMN_NAME,t.DATA_TYPE,t.CHARACTER_MAXIMUM_LENGTH,t.NUMERIC_PRECISION,t.IS_NULLABLE,case when xxt.column_name is null then 'N' else 'Y' end as isPK "
                + "from information_schema.COLUMNS t LEFT JOIN (SELECT COLUMN_NAME FROM information_schema.KEY_COLUMN_USAGE WHERE TABLE_NAME=?) xxt "
                + "ON xxt.COLUMN_NAME = t.column_name where t.table_name = ? and t.table_schema =?";
            sqlContainer.addPrecompiledValues(tbName);
            sqlContainer.addPrecompiledValues(tbName);
            sqlContainer.addPrecompiledValues("PUBLIC");
        }else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){

			sql = " SELECT allt.column_name, allt.data_type, allt.data_length, allt.data_precision, allt.nullable, CASE WHEN xxt.column_name IS NULL THEN 'N' ELSE 'Y' END AS isPK "
					+ "FROM all_tab_columns allt LEFT JOIN ( SELECT col.table_name, col.OWNER, col.column_name FROM all_constraints con, all_cons_columns col WHERE "
					+ "con.constraint_name = col.constraint_name AND con.constraint_type = 'P' AND col.table_name = ? AND col. OWNER = ? ) xxt "
					+ "ON xxt.COLUMN_NAME = allt.column_name WHERE allt.Table_Name = ? AND allt. OWNER = ?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(dbName);
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(dbName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			//废：SELECT name FROM SysColumns WHERE id=Object_Id('" + tbName + "')
			sql = "  select t.COLUMN_NAME,t.DATA_TYPE,case when t.CHARACTER_MAXIMUM_LENGTH is null then t.NUMERIC_PRECISION else t.CHARACTER_MAXIMUM_LENGTH end ,t.NUMERIC_SCALE,t.IS_NULLABLE,case when xxt.column_name is null then 'N' else 'Y' end as isPK "
					+ "from [" + dbName + "].information_schema.COLUMNS t LEFT JOIN (SELECT COLUMN_NAME FROM [" + dbName + "].INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME=?) xxt "
					+ "ON xxt.COLUMN_NAME = t.column_name where t.table_name = ? and t.table_schema =?";
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(tbName);
			sqlContainer.addPrecompiledValues(sqlServerParam);
		} else if(dbType.indexOf(DATABSE_TYPE_DB2) != -1) {
			sql = "SELECT COLNAME FROM SYSCAT.COLUMNS WHERE TABSCHEMA IN (SELECT GRANTEE FROM SYSIBM.SYSDBAUTH WHERE DBADMAUTH = 'Y') AND TABNAME = ?";
			sqlContainer.addPrecompiledValues(tbName);
		} else if(dbType.indexOf(DATABSE_TYPE_SYBASE) != -1) {
			sql = "SELECT c.name AS column_name, t.name AS data_type FROM syscolumns c" +
					" left join systypes t ON c.type = t.type and c.usertype = t.usertype" +
					" WHERE id = OBJECT_ID(?)";
			sqlContainer.addPrecompiledValues(tbName);
		}
        logger.info("获取表字段详细的sql:{}", sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 查询表中所有字段名
	 *
	 * @param tbNames 表名集合
	 * @param dbName  数据库名
	 * @param dbType  类型
	 * @return
	 */
	public static SQLContainer getTableColumnNameSQL(List<String> tbNames, String dbName, String dbType) {

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if (null == tbNames || tbNames.isEmpty()) {
			return sqlContainer;
		}
		String tbName = String.join(",", Collections.nCopies(tbNames.size(), "?"));
		if(DATABSE_TYPE_MYSQL.equals(dbType)){
			sql = " select t.COLUMN_NAME as COLUMNNAME, t.TABLE_NAME as TABLENAME FROM information_schema.COLUMNS t WHERE" +
				" t.table_name in (" + tbName + ") AND t.table_schema = ?";
			sqlContainer.addPrecompiledValues(tbNames);
			sqlContainer.addPrecompiledValues(dbName);
		} else if(DATABSE_TYPE_H2.equals(dbType)) {
            sql = " select t.COLUMN_NAME as COLUMNNAME, t.TABLE_NAME as TABLENAME FROM information_schema.COLUMNS t WHERE" +
                " t.table_name in (" + tbName + ") AND t.table_schema = ?";
            sqlContainer.addPrecompiledValues(tbNames);
            sqlContainer.addPrecompiledValues("PUBLIC");
        }else if(DATABSE_TYPE_ORACLE.equals(dbType) ||
				DATABSE_TYPE_DM8.equals(dbType)){

			// ORACLE直接有当前库所属表
			sql = "select t.column_name as COLUMNNAME, t.TABLE_NAME as TABLENAME from user_tab_columns t WHERE" +
				" t.TABLE_NAME in (" + tbName + ")";
			sqlContainer.addPrecompiledValues(tbNames);
		} else if(DATABSE_TYPE_SQLSERVER.equals(dbType)) {
			sql = "select t.column_name as COLUMNNAME, t.TABLE_NAME as TABLENAME from [" + dbName + "].information_schema.COLUMNS t" +
				" where t.table_name in (" + tbName + ") and t.table_schema = ?";
			sqlContainer.addPrecompiledValues(tbNames);
			sqlContainer.addPrecompiledValues("dbo");
		}
		logger.info("获取表字段名的SQL:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

    /**
     * 获取当前连接所见表集合
     *
     * @return
     */
	public static String getCurrentSysTableSQL() {

	    String sql = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL)) {
            sql = "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = ( select database() ) ";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE)
				|| DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8)) {
            sql = "select TABLE_NAME from user_tables WHERE 1 = 1 ";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER)) {
            sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE 1 = 1 ";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            sql = "SELECT TABLE_NAME,TABLE_SCHEMA FROM information_schema.TABLES WHERE TABLE_SCHEMA = ( 'PUBLIC')";
        }
        return sql;
    }

    /**
     * 表名在系统中的SQL
     *
     * @param tableNames 表名集合
     * @return
     */
    public static String getCurrentSysTableSQL(List<String> tableNames) {

        String currentSysTableSQL = getCurrentSysTableSQL();
        String whereSQL = currentSysTableSQL + " AND TABLE_NAME ";
        if (null == tableNames || tableNames.isEmpty()) {
            return whereSQL + " = ''";
        }
        whereSQL += " in ( " + String.join(",", Collections.nCopies(tableNames.size(), "?"));
        whereSQL += ")";
        return whereSQL;
    }

	/**
	 * 获取库中存储过程列表sql
	 *
	 * @param dataSourceType
	 * @param owner
	 * @param sqlServerParam
	 * @param searchTableName
	 * @return
	 */
	public static SQLContainer getProcedureSql(String dataSourceType, String owner, String sqlServerParam, String searchTableName) {

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if (dataSourceType.equalsIgnoreCase(DATABSE_TYPE_MYSQL) ) {
//			sql = " SELECT name FROM MySQL.proc WHERE db = ? AND type = 'PROCEDURE'  ";
//			sqlContainer.addPrecompiledValues(owner);
//			// 过滤
//			if (StringUtil.isNotEmpty(searchTableName)) {
//				sql += " and name like ?";
//				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
//			}
//			sql += " order by name ";
			//mysql8.0高版本无MySQL.proc，zhaibx改
			sql = " show procedure status where db=? ";
			sqlContainer.addPrecompiledValues(owner);
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " and name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
		}
        if (dataSourceType.equalsIgnoreCase(DATABSE_TYPE_H2) ) {
            sql = " SELECT ALIAS_NAME FROM information_schema.FUNCTION_ALIASES  WHERE 1=1  ";
            sqlContainer.addPrecompiledValues(owner);
            // 过滤
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and ALIAS_NAME like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by ALIAS_NAME ";
        }
		if (dataSourceType.equalsIgnoreCase(DATABSE_TYPE_ORACLE) ||
				dataSourceType.equalsIgnoreCase(DATABSE_TYPE_DM8) ) {//也可用user_procedures 表，只获取当前库的
			sql = "SELECT t_temp.object_name FROM (" +
					"select object_name AS object_name from all_procedures WHERE OBJECT_TYPE='PROCEDURE' and owner= ? " +
					"UNION " +
					"select object_name||'.'||procedure_name AS object_name from all_procedures WHERE OBJECT_TYPE='PACKAGE' AND procedure_name IS NOT NULL and owner= ?) t_temp";
			sqlContainer.addPrecompiledValues(owner);
			sqlContainer.addPrecompiledValues(owner);
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " WHERE t_temp.object_name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
			sql += " ORDER BY t_temp.object_name ";
		}
		if (dataSourceType.equalsIgnoreCase(DATABSE_TYPE_SQLSERVER) ) {
			sql = " SELECT name FROM [" + owner + "].sys.sysobjects where type='P' ";
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " and name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
			sql += " order by name ";
		}
		logger.info("查数据库所有存储过程的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 获取存储过程参数信息列表sql
	 *
	 * @param procedureName
	 * @param dbName
	 * @param dbType
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getParamsSqlByProcedure(String procedureName, String dbName, String dbType, String sqlServerParam) {

		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if(dbType.equalsIgnoreCase(DATABSE_TYPE_MYSQL)){
			sql = " SELECT PARAMETER_NAME,DATA_TYPE,PARAMETER_MODE FROM information_schema.PARAMETERS p where SPECIFIC_SCHEMA =? and ROUTINE_TYPE ='PROCEDURE' and SPECIFIC_NAME =? order by ORDINAL_POSITION ";
			sqlContainer.addPrecompiledValues(dbName);
			sqlContainer.addPrecompiledValues(procedureName);
		} else if (dbType.equalsIgnoreCase(DATABSE_TYPE_H2)) {
            sql = " SELECT PARAMETER_NAME,DATA_TYPE,PARAMETER_MODE FROM information_schema.PARAMETERS p where SPECIFIC_SCHEMA =? and ROUTINE_TYPE ='PROCEDURE' and SPECIFIC_NAME =? order by ORDINAL_POSITION ";
            sqlContainer.addPrecompiledValues(dbName);
            sqlContainer.addPrecompiledValues(procedureName);
        } else if(dbType.equalsIgnoreCase(DATABSE_TYPE_ORACLE) ||
				dbType.equalsIgnoreCase(DATABSE_TYPE_DM8)){
			String packageName = "";
			if(procedureName.indexOf(".")> 0 ){//如果有.表示存在包体的情况
				packageName = procedureName.split(".")[0];
				procedureName = procedureName.split(".")[1];
			}
			sql = " select t.argument_name,t.data_type,t.in_out from all_arguments t where t.owner=? and t.object_name=? ";
			sqlContainer.addPrecompiledValues(dbName);
			sqlContainer.addPrecompiledValues(procedureName);
			if(StringUtil.isNotEmpty(packageName)){
				sql+=" and t.package_name=?";
				sqlContainer.addPrecompiledValues(packageName);
			}
			sql+=" ORDER BY SEQUENCE ";
		} else if(dbType.equalsIgnoreCase(DATABSE_TYPE_SQLSERVER) ) {
			sql = "SELECT PARAMETER_NAME ,DATA_TYPE , PARAMETER_MODE FROM [" + dbName + "].INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = ? AND SPECIFIC_NAME = ? order by ORDINAL_POSITION";
			sqlContainer.addPrecompiledValues(sqlServerParam);
			sqlContainer.addPrecompiledValues(procedureName);
		}
		logger.info("获取存储过程参数信息列表sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	public static int dealDataType(String dataType, String dbType) {
		int sqlType= Types.OTHER;
		if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_MYSQL) ){
			if(dataType.toLowerCase().startsWith("bigint")){
				sqlType= Types.BIGINT;
			}else if(dataType.toLowerCase().startsWith("tinyblob")){
				sqlType= Types.BINARY;
			}else if(dataType.toLowerCase().startsWith("bit")){
				sqlType= Types.BIT;
			}else if(dataType.toLowerCase().startsWith("char") || dataType.toLowerCase().startsWith("enum") || dataType.toLowerCase().startsWith("set")){
				sqlType= Types.CHAR;
			}else if(dataType.toLowerCase().startsWith("datetime") || dataType.toLowerCase().startsWith("timestamp")){
				sqlType= Types.TIMESTAMP;
			}else if(dataType.toLowerCase().startsWith("time")){
				sqlType= Types.TIME;
			}else if(dataType.toLowerCase().startsWith("date") || dataType.toLowerCase().startsWith("year")){
				sqlType= Types.DATE;
			}else if(dataType.toLowerCase().startsWith("numeric") || dataType.toLowerCase().startsWith("decimal")){
				sqlType= Types.DECIMAL;
			}else if(dataType.toLowerCase().startsWith("double") || dataType.toLowerCase().startsWith("real")){
				sqlType= Types.DOUBLE;
			}else if(dataType.toLowerCase().startsWith("int") || dataType.toLowerCase().startsWith("mediumint")){
				sqlType= Types.INTEGER;
			}else if(dataType.toLowerCase().startsWith("blob") || dataType.toLowerCase().startsWith("mediumblob")){
				sqlType= Types.BLOB;
			}else if(dataType.toLowerCase().startsWith("float") ){
				sqlType= Types.REAL;
			}else if(dataType.toLowerCase().startsWith("smallint") ){
				sqlType= Types.SMALLINT;
			}else if(dataType.toLowerCase().startsWith("tinyint") ){
				sqlType= Types.TINYINT;
			}else if(dataType.toLowerCase().startsWith("varbinary") || dataType.toLowerCase().startsWith("binary")){
				sqlType= Types.VARBINARY;
			}else if(dataType.toLowerCase().startsWith("tinytext") || dataType.toLowerCase().startsWith("varchar") || dataType.toLowerCase().startsWith("text")){
				sqlType= Types.VARCHAR;
			}
		} else if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_H2) ){
            if(dataType.toLowerCase().startsWith("bigint")){
                sqlType= Types.BIGINT;
            }else if(dataType.toLowerCase().startsWith("tinyblob")){
                sqlType= Types.BINARY;
            }else if(dataType.toLowerCase().startsWith("bit")){
                sqlType= Types.BIT;
            }else if(dataType.toLowerCase().startsWith("char") || dataType.toLowerCase().startsWith("enum") || dataType.toLowerCase().startsWith("set")){
                sqlType= Types.CHAR;
            }else if(dataType.toLowerCase().startsWith("datetime") || dataType.toLowerCase().startsWith("timestamp")){
                sqlType= Types.TIMESTAMP;
            }else if(dataType.toLowerCase().startsWith("time")){
                sqlType= Types.TIME;
            }else if(dataType.toLowerCase().startsWith("date") || dataType.toLowerCase().startsWith("year")){
                sqlType= Types.DATE;
            }else if(dataType.toLowerCase().startsWith("numeric") || dataType.toLowerCase().startsWith("decimal")){
                sqlType= Types.DECIMAL;
            }else if(dataType.toLowerCase().startsWith("double") || dataType.toLowerCase().startsWith("real")){
                sqlType= Types.DOUBLE;
            }else if(dataType.toLowerCase().startsWith("int") || dataType.toLowerCase().startsWith("mediumint")){
                sqlType= Types.INTEGER;
            }else if(dataType.toLowerCase().startsWith("blob") || dataType.toLowerCase().startsWith("mediumblob")){
                sqlType= Types.BLOB;
            }else if(dataType.toLowerCase().startsWith("float") ){
                sqlType= Types.REAL;
            }else if(dataType.toLowerCase().startsWith("smallint") ){
                sqlType= Types.SMALLINT;
            }else if(dataType.toLowerCase().startsWith("tinyint") ){
                sqlType= Types.TINYINT;
            }else if(dataType.toLowerCase().startsWith("varbinary") || dataType.toLowerCase().startsWith("binary")){
                sqlType= Types.VARBINARY;
            }else if(dataType.toLowerCase().startsWith("tinytext") || dataType.toLowerCase().startsWith("varchar") || dataType.toLowerCase().startsWith("text")){
                sqlType= Types.VARCHAR;
            }
        } else if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_SQLSERVER)) {
			if(dataType.toLowerCase().startsWith("bigint") ){
				sqlType= Types.BIGINT;
			}else if(dataType.toLowerCase().startsWith("timstamp") || dataType.toLowerCase().startsWith("binary")){
				sqlType= Types.BINARY;
			}else if(dataType.toLowerCase().startsWith("bit")){
				sqlType= Types.BIT;
			}else if(dataType.toLowerCase().startsWith("char") || dataType.toLowerCase().startsWith("nchar") || dataType.toLowerCase().startsWith("unqualified")){
				sqlType= Types.CHAR;
			}else if(dataType.toLowerCase().startsWith("datetime")){
				sqlType= Types.DATE;
			}else if(dataType.toLowerCase().startsWith("money") || dataType.toLowerCase().startsWith("smallmoney") || dataType.toLowerCase().startsWith("decimal")){
				sqlType= Types.DECIMAL;
			}else if(dataType.toLowerCase().startsWith("float")){
				sqlType= Types.DOUBLE;
			}else if(dataType.toLowerCase().startsWith("int")){
				sqlType= Types.INTEGER;
			}else if(dataType.toLowerCase().startsWith("image")){
				sqlType= Types.LONGVARBINARY;
			}else if(dataType.toLowerCase().startsWith("text") || dataType.toLowerCase().startsWith("ntext") || dataType.toLowerCase().startsWith("xml") ){
				sqlType= Types.LONGVARCHAR;
			}else if(dataType.toLowerCase().startsWith("numeric") ){
				sqlType= Types.NUMERIC;
			}else if(dataType.toLowerCase().startsWith("real") ){
				sqlType= Types.REAL;
			}else if(dataType.toLowerCase().startsWith("smallint") ){
				sqlType= Types.SMALLINT;
			}else if(dataType.toLowerCase().startsWith("datetime") || dataType.toLowerCase().startsWith("smalldatetime")){
				sqlType= Types.TIMESTAMP;
			}else if(dataType.toLowerCase().startsWith("tinyint") ){
				sqlType= Types.TINYINT;
			}else if(dataType.toLowerCase().startsWith("varbinary") ){
				sqlType= Types.VARBINARY;
			}else if(dataType.toLowerCase().startsWith("nvarchar") || dataType.toLowerCase().startsWith("varchar") ){
				sqlType= Types.VARCHAR;
			}
		}else if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_ORACLE) ||
				dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_DM8)) {
			if(dataType.toLowerCase().startsWith("blob") ){
				sqlType= Types.BLOB;
			}else if(dataType.toLowerCase().startsWith("char")){
				sqlType= Types.CHAR;
			}else if(dataType.toLowerCase().startsWith("clob")){
				sqlType= Types.CLOB;
			}else if(dataType.toLowerCase().startsWith("date")){
				sqlType= Types.DATE;
			}else if(dataType.toLowerCase().startsWith("number")){
				sqlType= Types.DECIMAL;
			}else if(dataType.toLowerCase().startsWith("long") || dataType.toLowerCase().startsWith("raw")){
				sqlType= Types.VARBINARY;
			}else if(dataType.toLowerCase().startsWith("nclob") || dataType.toLowerCase().startsWith("nvarchar")){
				sqlType= Types.OTHER;
			}else if(dataType.toLowerCase().startsWith("smallint")){
				sqlType= Types.SMALLINT;
			}else if(dataType.toLowerCase().startsWith("timestamp")){
				sqlType= Types.TIMESTAMP;
			}else if(dataType.toLowerCase().startsWith("varchar") ){
				sqlType= Types.VARCHAR;
			}else if(dataType.toLowerCase().indexOf("cursor")!=-1){
				sqlType=oracle.jdbc.OracleTypes.CURSOR;
			}
		}
		return sqlType;
	}

	/**
	 * 处理存储过程入参和出参
	 * @param st
	 * @param procedureParams
	 * @param dbType
	 * @throws SQLException
	 */
	public static void dealProcedureParams(CallableStatement st, List<ProcedureParams> procedureParams, String dbType) throws SQLException {
		if(StringUtil.isNotEmpty(procedureParams)){
			for (int i=0;i<procedureParams.size();i++){
				int index = i+1;
				ProcedureParams procedureParam = procedureParams.get(i);
				String inout = procedureParam.getParameterMode();
				if(inout.toLowerCase().indexOf("in")!=-1){
					st.setObject(index,procedureParam.getParameterValue());
				}
				//有in和out同时的存在
				if(inout.toLowerCase().indexOf("out")!=-1){
					st.registerOutParameter(index,DynamicDBUtil.dealDataType(procedureParam.getDataType(),dbType));
				}
			}
		}
	}

	/**
	 * 执行存储过程返回结果集
	 * @param st
	 * @param dbType
	 * @param rs
	 * @param oracleOut
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet callProcedure(CallableStatement st, String dbType, ResultSet rs, int oracleOut) throws SQLException {
		boolean bl = st.execute();
		if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_MYSQL) ||
            dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_SQLSERVER) ||
            dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_H2)){
			//获取最后一个结果集
//								while(bl){
			rs=st.getResultSet();//取得第一个结果集
//									if(rs != null){
//										rs.next();
//									}
//					                bl=st.getMoreResults();//继续去取结果集，若还还能取到结果集，则bl=true了。然后回去循环。
//					            }
		}else if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_ORACLE) ||
				dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_DM8)) {
			rs = (ResultSet) st.getObject(oracleOut);
		}
		return rs;
	}

	/**
	 * 处理Oracle返回参数的位置
	 * @param procedureParams
	 * @param dbType
	 * @return
	 */
	public static int dealOracleOutParamsNum(List<ProcedureParams> procedureParams, String dbType) {
		int oracleOut = -1;
		if(StringUtil.isNotEmpty(procedureParams)){
			for (int i=0;i<procedureParams.size();i++){
				ProcedureParams procedureParam = procedureParams.get(i);
				if(dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_ORACLE) ||
						dbType.equalsIgnoreCase(DynamicDBUtil.DATABSE_TYPE_DM8)) {
					if(procedureParam.getDataType().toLowerCase().indexOf("cursor")!=-1){
						oracleOut = (i+1);
					}
				}
			}
		}
		return oracleOut;
	}

    /**
     * 执行查询,并且可以对查询结果用{@link JdbcQuery}进行操作
     *
     * @param connection JDBC连接
     * @param sql        SQL脚本
     * @param pValues    预编译参数集合
     * @param jdbcQuery  对查询结果操作的接口
     * @throws Exception
     */
    public static void executeQuery(Connection connection, String sql, List<String> pValues,
            JdbcQuery jdbcQuery) throws Exception {

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            DBUtil.addParams(stmt, pValues);
            try (ResultSet rs = stmt.executeQuery()) {
                jdbcQuery.queryList(rs);
            }
        }
    }

    /**
     * 执行查询后,返回结果集,结果集在{@link JdbcQuery} 自定义
     *
     * @param connection JDBC连接
     * @param sql        SQL脚本
     * @param pValues    预编译参数集合
     * @param jdbcQuery  对查询结果操作的接口
     * @return
     * @throws Exception
     */
    public static List<?> queryListBySQL(Connection connection, String sql, List<String> pValues,
            JdbcQuery jdbcQuery) throws Exception {

        List<?> resList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            DBUtil.addParams(stmt, pValues);
            try (ResultSet rs = stmt.executeQuery()) {
                resList = jdbcQuery.queryList(rs);
            }
        }
        return resList;
    }

    /**
     * 通过连接查询SQL第一个字段的数据集
     *
     * @param connection JDBC连接
     * @param sql        SQL脚本
     * @param pValues    预编译参数集合
     * @return SQL第一个字段的数据集
     * @throws Exception
     */
    public static List<String> queryListBySQL(Connection connection, String sql, List<String> pValues) throws Exception {

        return (List<String>) queryListBySQL(
            connection,
            sql,
            pValues,
            (resultSet) -> {
                return getResultOne(resultSet);
            }
        );
    }

    /**
     * 仅执行SQL脚本
     *
     * @param connection JDBC连接
     * @param sql        SQL脚本
     * @param pValues    预编译参数集合
     * @throws Exception
     */
    public static void executeSQL(Connection connection, String sql, List<String> pValues) throws Exception {

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            DBUtil.addParams(stmt, pValues);
            stmt.executeUpdate();
        }
        logger.info("执行SQL[{}]", sql);
    }

    /**
     * 格式化数据
     *
     * @param data   数据
     * @param format 格式
     * @return
     */
    public static Object formatNumberData(Object data, String format) {

        if (StringUtils.isBlank(format) || data == null || StringUtils.isBlank(data.toString())) {
            return data;
        }
        Object formatData = data;

        // 根据前台格式化字符串分析,数字的都带点[.]所以用点判断
        if (format.indexOf(".") >= 0) {
            BigDecimal bd = null;
            try {
                bd = toBigDecimal(data);
            } catch (Exception ex) {
            }
            if (bd != null) {
                DecimalFormat df = new DecimalFormat(format);
                formatData = df.format(bd.doubleValue());
            }
        } else {
            String dataStr = (String) data;
            try {
                formatData = DateUtils.parseDateStrToString(dataStr, format);
            } catch (ParseException e) {
                formatData = data;
            }
        }
        String formatDataStr = (String) formatData;
        boolean aDouble = BaseCheckUtils.isDouble(formatDataStr, true);
        boolean integer = BaseCheckUtils.isInteger(formatDataStr, true);

        // 格式化后如果是负零,弄成正的
        if (aDouble || integer) {
            BigDecimal b1 = new BigDecimal(formatDataStr);
            if (b1.compareTo(new BigDecimal(0)) == 0) {
                formatData = b1.toString();
            }
        }
        return formatData;
    }

    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else if (obj instanceof String) {
            if (obj.toString().trim().equals("")) {
                return new BigDecimal(0);
            }
            try {
                String str = obj.toString().trim();
                return new BigDecimal(str);
            } catch (Exception ex) {
                return new BigDecimal("0");
                //                logger.info();
                //				throw new ConvertException("Can not convert "+obj+" to BigDecimal.");
            }
        } else if (obj instanceof Number) {
            Number n = (Number) obj;
            return new BigDecimal(n.doubleValue());
        }
        throw new RuntimeException("格式失败");
    }

    public static Object getProperty(Object obj, String property) {
        if (obj == null) return null;
        try {
            if (obj instanceof Map && property.indexOf(".") == -1) {
                Map<?, ?> map = (Map<?, ?>) obj;
                return map.get(property);
            }
            return PropertyUtils.getProperty(obj, property);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	/**
	 * 组装:查询视图内容的sql
	 *
	 * @param viewName       - 视图名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getViewInfoSql(String viewName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
			sql = "SHOW CREATE VIEW " + viewName;//不需要单引号拼接
		} else if(dbType.indexOf(DATABSE_TYPE_H2) != -1){
            sql = "SELECT VIEW_DEFINITION FROM information_schema.VIEWS t WHERE t.TABLE_NAME = ?";
            sqlContainer.addPrecompiledValues(viewName);
        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
			sql = "select dbms_metadata.get_ddl('VIEW',?) from dual";
			sqlContainer.addPrecompiledValues(viewName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "EXEC sp_helptext ? ";
			sqlContainer.addPrecompiledValues(viewName);
		}
		logger.info("获取视图内容的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
    }

	/**
	 * 组装:查询函数内容的sql
	 *
	 * @param functionName   - 函数名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getFunctionInfoSql(String functionName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
			sql = "show create function " + functionName;
		} else if(dbType.indexOf(DATABSE_TYPE_H2) != -1){
            sql = "SELECT JAVA_CLASS FROM information_schema.FUNCTION_ALIASES WHERE ALIAS_NAME = ?";
            sqlContainer.addPrecompiledValues(functionName);
        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
			sql = "select dbms_metadata.get_ddl('FUNCTION',?) from dual";
			sqlContainer.addPrecompiledValues(functionName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "EXEC sp_helptext ? ";
			sqlContainer.addPrecompiledValues(functionName);
		}
		logger.info("获取函数内容的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
    }

	/**
	 * 组装:查询存储过程内容的sql
	 *
	 * @param procedureName   - 存储过程名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getProcedureInfoSql(String procedureName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
			sql = "show create procedure " + procedureName;
		} else if (dbType.indexOf(DATABSE_TYPE_H2) != -1) {
            sql = "SELECT JAVA_CLASS FROM information_schema.FUNCTION_ALIASES WHERE ALIAS_NAME = ?";
            sqlContainer.addPrecompiledValues(procedureName);
        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
			sql = "select dbms_metadata.get_ddl('PROCEDURE',?) from dual";
			sqlContainer.addPrecompiledValues(procedureName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "EXEC sp_helptext ? ";
			sqlContainer.addPrecompiledValues(procedureName);
		}
		logger.info("获取存储过程内容的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	public static String safeGetString(ResultSet dbResult, String columnName){
		try {
			return dbResult.getString(columnName);
		} catch (SQLException e) {
			return null;
		}
	}

	public static String safeGetString(ResultSet dbResult, int columnIndex){
		try {
			return dbResult.getString(columnIndex);
		} catch (SQLException e) {
			return null;
		}
	}

	public static String safeGetObjectToString(ResultSet dbResult, String columnName){
		try {
			return StringUtil.dealResultTypeToString(dbResult.getObject(columnName));
		} catch (SQLException e) {
			return null;
		}
	}

	public static String safeGetObjectToString(ResultSet dbResult, int columnIndex){
		try {
			return StringUtil.dealResultTypeToString(dbResult.getObject(columnIndex));
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 获取删除数据库sql
	 * @param dbType
	 * @param dbName
	 * @return
	 */
	public static SQLContainer getDeleteDBSql(String dbType, String dbName) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){

//			sql = " SELECT t.COLUMN_NAME, t.DATA_TYPE, t.CHARACTER_MAXIMUM_LENGTH, t.NUMERIC_PRECISION, t.IS_NULLABLE, CASE WHEN t.COLUMN_KEY = '' THEN 'N' ELSE 'Y' END AS isPK "
//					+ "FROM information_schema.COLUMNS t WHERE t.table_name = ? AND t.table_schema = ?";
//			sqlContainer.addPrecompiledValues(tbName);
//			sqlContainer.addPrecompiledValues(dbName);
		} else if (dbType.indexOf(DATABSE_TYPE_H2) != -1){


        } else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1){

//			sql = " SELECT allt.column_name, allt.data_type, allt.data_length, allt.data_precision, allt.nullable, CASE WHEN xxt.column_name IS NULL THEN 'N' ELSE 'Y' END AS isPK "
//					+ "FROM all_tab_columns allt LEFT JOIN ( SELECT col.table_name, col.OWNER, col.column_name FROM all_constraints con, all_cons_columns col WHERE "
//					+ "con.constraint_name = col.constraint_name AND con.constraint_type = 'P' AND col.table_name = ? AND col. OWNER = ? ) xxt "
//					+ "ON xxt.COLUMN_NAME = allt.column_name WHERE allt.Table_Name = ? AND allt. OWNER = ?";
//			sqlContainer.addPrecompiledValues(tbName);
//			sqlContainer.addPrecompiledValues(dbName);
//			sqlContainer.addPrecompiledValues(tbName);
//			sqlContainer.addPrecompiledValues(dbName);
		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "drop database "+ dbName;
//			sqlContainer.addPrecompiledValues(dbName);
		}
		logger.info("获取删除数据库sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 获取库中函数列表sql
	 * @param dbType
	 * @param owner
	 * @param sqlServerParam
	 * @param searchTableName
	 * @return
	 */
	public static SQLContainer getAllFuncSql(String dbType, String owner, String sqlServerParam, String searchTableName) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if (dbType.equalsIgnoreCase(DATABSE_TYPE_MYSQL) ) {
			sql = " SELECT name FROM MySQL.proc WHERE db = ? AND type = 'FUNCTION'  ";
			sqlContainer.addPrecompiledValues(owner);
			// 过滤
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " and name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
			sql += " order by name ";
		}
        if (dbType.equalsIgnoreCase(DATABSE_TYPE_H2) ) {
            sql = " SELECT ALIAS_NAME FROM information_schema.FUNCTION_ALIASES  WHERE 1=1  ";
            sqlContainer.addPrecompiledValues(owner);
            // 过滤
            if (StringUtil.isNotEmpty(searchTableName)) {
                sql += " and ALIAS_NAME like ?";
                sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
            }
            sql += " order by ALIAS_NAME ";
        }
		if (dbType.indexOf(DATABSE_TYPE_ORACLE) != -1 ||
				dbType.indexOf(DATABSE_TYPE_DM8) != -1) {
			sql ="select object_name AS object_name from all_procedures WHERE OBJECT_TYPE='FUNCTION' and owner= ? ";
			sqlContainer.addPrecompiledValues(owner);
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " WHERE object_name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
			sql += " ORDER BY object_name ";
		}

		if (dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = " SELECT name FROM [" + owner + "].sys.sysobjects where type in ('FN','AF','FS','FT','IF','TF') ";//只FN还不行，查询不出表值函数或其他函数
			if (StringUtil.isNotEmpty(searchTableName)) {
				sql += " and name like ?";
				sqlContainer.addPrecompiledValues(SpecialSymbolConstants.bundleSearch(searchTableName));
			}
			sql += " order by name ";
		}
		logger.info("查数据库所有函数的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	public static SQLContainer getCreateDBUserSql(String dbType, String dbName) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";
		if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1){
			sql = "create user "+dbName+" identified by "+dbName+" default tablespace "+dbName+" temporary tablespace TEMP profile DEFAULT";
		} else if(dbType.indexOf(DATABSE_TYPE_DM8) != -1) {
			sql = "create user "+dbName+" identified by "+dbName+" default tablespace "+dbName+" temporary tablespace TEMP ";
		}
		logger.info("获取创建数据库用户sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 组装:删除表的sql
	 *
	 * @param tableName       - 表名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getDropTableSql(String tableName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

//		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
//			sql = "SHOW CREATE VIEW " + viewName;//不需要单引号拼接
//		}else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
//				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
//			sql = "select dbms_metadata.get_ddl('VIEW',?) from dual";
//			sqlContainer.addPrecompiledValues(viewName);
//		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "DROP TABLE " + tableName;
//		}
//		logger.info("获取删除表的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 组装:删除视图的sql
	 *
	 * @param viewName       - 视图名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getDropViewSql(String viewName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

//		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
//			sql = "SHOW CREATE VIEW " + viewName;//不需要单引号拼接
//		}else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
//				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
//			sql = "select dbms_metadata.get_ddl('VIEW',?) from dual";
//			sqlContainer.addPrecompiledValues(viewName);
//		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "DROP VIEW " + viewName;
//			sqlContainer.addPrecompiledValues(viewName);
//		}
//		logger.info("获取删除视图的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 组装:删除函数的sql
	 *
	 * @param functionName   - 函数名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getDropFunctionSql(String functionName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

//		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
//			sql = "show create function " + functionName;
//		}else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
//				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
//			sql = "select dbms_metadata.get_ddl('FUNCTION',?) from dual";
//			sqlContainer.addPrecompiledValues(functionName);
//		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "DROP FUNCTION " + functionName;
//			sqlContainer.addPrecompiledValues(functionName);
//		}
//		logger.info("获取删除函数的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}

	/**
	 * 组装:删除存储过程的sql
	 *
	 * @param procedureName   - 存储过程名
	 * @param dbName         - 数据库名(拥有者)
	 * @param dbType         - 数据库类型(可以用数据库的URL)
	 * @param sqlServerParam
	 * @return
	 */
	public static SQLContainer getDropProcedureSql(String procedureName, String dbName, String dbType, String sqlServerParam) {
		SQLContainer sqlContainer = new SQLContainer();
		String sql = "";

//		if(dbType.indexOf(DATABSE_TYPE_MYSQL) != -1){
//			sql = "show create procedure " + procedureName;
//		}else if(dbType.indexOf(DATABSE_TYPE_ORACLE) != -1||
//				dbType.indexOf(DATABSE_TYPE_DM8) != -1){
//			sql = "select dbms_metadata.get_ddl('PROCEDURE',?) from dual";
//			sqlContainer.addPrecompiledValues(procedureName);
//		} else if(dbType.indexOf(DATABSE_TYPE_SQLSERVER) != -1) {
			sql = "DROP PROCEDURE " + procedureName;
//			sqlContainer.addPrecompiledValues(procedureName);
//		}
//		logger.info("获取存储过程内容的sql:" + sql);
		sqlContainer.setSql(sql);
		return sqlContainer;
	}
}
