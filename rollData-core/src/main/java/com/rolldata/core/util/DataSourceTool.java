package com.rolldata.core.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Title: HDataSourceTool
 * @Description:数据源工具类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
public class DataSourceTool {
    /**产品默认H2端口号*/
    public static final String H2PORT = "9094";
    public static final String DBTYPE_MYSQL = "mysql";
    public static final String DBTYPE_ORACLE = "oracle";
    public static final String DBTYPE_SQLSERVER = "sqlserver";
    public static final String DBTYPE_DM8 = "dm8";
    public static final String DBTYPE_H2 = "h2";
    public static final String DBTYPE_POSTGRESQL = "postgresql";
    public static final String DBTYPE_SQLITE = "sqlite";

    public static final String DBTYPE_DB2 = "db2";

    public static final String DBTYPE_ACCESS = "access";

    public static List<String> DBTYPELIST = Arrays.asList(DBTYPE_MYSQL, DBTYPE_ORACLE, DBTYPE_SQLSERVER,DBTYPE_DM8);

    public static final String DB_URL_MYSQL = "jdbc:mysql://{0}:{1}/{2}";
    public static final String DB_URL_ORACLE = "jdbc:oracle:thin:@//{0}:{1}/{2}";
    public static final String DB_URL_SQLSERVER = "jdbc:sqlserver://{0}:{1};DatabaseName={2}";
    public static final String DB_URL_DM8 = "jdbc:dm://{0}:{1}/{2}";
    public static final String DB_URL_H2 = "jdbc:h2:tcp://{0}:{1}/{2}";
    public static final String DB_URL_POSTGRESQL = "jdbc:postgresql://{0}:{1}/{2}";

    public static final String DB_URL_SQLITE = "jdbc:sqlite:{0}";
    public static final String DB_URL_DB2 = "jdbc:db2://{0}:{1}/{2}";

    public static final String DB_URL_ACCESS = "jdbc:ucanaccess://{0}";

    public static final String DB_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    public static final String DB_DRIVER_ORACLE = "oracle.jdbc.OracleDriver";
    public static final String DB_DRIVER_SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DB_DRIVER_DM8 = "dm.jdbc.driver.DmDriver";
    public static final String DB_DRIVER_H2 = "org.h2.Driver";
    public static final String DB_DRIVER_POSTGRESQL = "org.postgresql.Driver";

    public static final String DB_DRIVER_SQLITE = "org.sqlite.JDBC";

    public static final String DB_DRIVER_DB2 = "com.ibm.db2.jcc.DB2Driver";
    public static final String DB_DRIVER_ACCESS = "net.ucanaccess.jdbc.UcanaccessDriver";

    public static final String DB_DIALECT_MYSQL = "org.hibernate.dialect.MySQL5Dialect";
    public static final String DB_DIALECT_ORACLE = "org.hibernate.dialect.OracleDialect";
    public static final String DB_DIALECT_SQLSERVER = "org.hibernate.dialect.SQLServer2008Dialect";
    public static final String DB_DIALECT_DM8 = "org.hibernate.dialect.DmDialect";
    public static final String DB_DIALECT_H2 = "org.hibernate.dialect.H2Dialect";

    public static final String properiesName = "dbconfig.properties";
    
    public static final String formalValidationQuerySqlserver= "formal.validationQuery.sqlserver";
    public static final String formalJdbcUrlWd = "formal.jdbc.url.wd";
    public static final String formalJdbcUsernameWd = "formal.jdbc.username.wd";
    public static final String formalJdbcPasswordWd = "formal.jdbc.password.wd";
    public static final String formalJdbcDbType = "formal.jdbc.dbType";
    public static final String formalJdbcDriver = "formal.jdbc.driver";
    public static final String formalDialect = "formal.jdbc.dialect";
    
    public static final String validationQuerySqlserver = "validationQuery.sqlserver";
    public static final String jdbcUrlWd = "jdbc.url.wd";
    public static final String jdbcUsernameWd = "jdbc.username.wd";
    public static final String jdbcPasswordWd = "jdbc.password.wd";
    public static final String jdbcDbType = "jdbc.dbType";
    public static final String jdbcDriver = "jdbc.driver";
    
    public static PropertiesUtil propertiesUtil = new PropertiesUtil(properiesName);
    /**
     * 正式
     */
    public static final String FORMALVALIDATIONQUERY = propertiesUtil.getString(formalValidationQuerySqlserver);
    
    public static final String FORMALDBURL = propertiesUtil.getString(formalJdbcUrlWd);
    
    public static final String FORMALDBUSR = propertiesUtil.getString(formalJdbcUsernameWd);
    
    public static final String FORMALDBPWD = propertiesUtil.getString(formalJdbcPasswordWd);
    
    public static final String FORMALDBTYPE = propertiesUtil.getString(formalJdbcDbType).toLowerCase();
    
    public static final String FORMALDBDRIVER = propertiesUtil.getString(formalJdbcDriver);
    
    /**
     * 备用
     */
    public static final String SPAREVALIDATIONQUERY = propertiesUtil.getString(validationQuerySqlserver);
    
    public static final String SPAREDBURL = propertiesUtil.getString(jdbcUrlWd);
    
    public static final String SPAREDBUSR = propertiesUtil.getString(jdbcUsernameWd);
    
    public static final String SPAREDBPWD = propertiesUtil.getString(jdbcPasswordWd);
    
    public static final String SPAREDBTYPE = propertiesUtil.getString(jdbcDbType).toLowerCase();
    
    public static final String SPAREDBDRIVER = propertiesUtil.getString(jdbcDriver);

    public static String getH2port() {
        String h2port = SysPropertiesUtil.getConfig("system.h2db.port");
        if(StringUtil.isNotEmpty(h2port)){
            return h2port;
        }else{
            return H2PORT;
        }
    }
}