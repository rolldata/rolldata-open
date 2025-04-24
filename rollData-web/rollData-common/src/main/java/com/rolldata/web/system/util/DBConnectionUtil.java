package com.rolldata.web.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.rolldata.core.util.StringUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



/** 
 * <p>Title:DBConnection</p> 
 * <p>Description:数据库连接工具类</p> 
 * <p>Company:www.wrenchdata.com</p> 
 * @author:申世龙
 * @date:2016-5-18下午8:29:07
 */
public class DBConnectionUtil {

	private static final Logger logger = LogManager.getLogger(DBConnectionUtil.class);
	
	public DBConnectionUtil() {
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
//		logger.info("驱动:" + driver + "URL:" + url +  "用户名" + usr + "密码"+ pwd);
		conn = DriverManager.getConnection(url, usr, pwd);

		return conn;

	}
	
	/**
	 * 获得链接-密码未解密
	 * @param id 数据源id  解密用
	 * @param driver 驱动
	 * @param url 驱动
	 * @param usr 数据库用户名
	 * @param pwd 密码
	 * @return conn
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static Connection getConnection(String id, String driver, String url, String usr, String pwd) throws ClassNotFoundException, SQLException {
		
		Connection conn = null;
		byte[] salt = PasswordUtil.getStaticSalt();
		Class.forName(driver);
        String pw = PasswordUtil.decrypt(pwd, id, salt);
//        System.out.println("驱动-" + driver + "url-" + url + "-用户名-" + usr + "-密码-" + pw);
		conn = DriverManager.getConnection(url, usr, pw);
		return conn;

	}

	/**
	 * 关闭Connection
	 * @param conn
	 * @throws SQLException 
	 * @date:2016-5-18下午8:50:40
	 */
	public static void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 关闭Statement
	 * @param stmt
	 * @throws SQLException 
	 * @date:2016-5-18下午8:51:08
	 */
	public static void closeStatement(Statement stmt) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
	}

	/**
	 * 关闭PreparedStatement
	 * @param pstmt
	 * @return void
	 * @throws SQLException 
	 * @date:2016-5-18下午8:52:17
	 */
	public static void closePreStatement(PreparedStatement pstmt) throws SQLException {
		
		if (pstmt != null) {
			pstmt.close();
		}
	}

	/**
	 * 关闭ResultSet
	 * @param rs
	 * @throws SQLException 
	 * @date:2016-5-18下午8:52:52
	 */
	public static void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	/**
	 * 关闭所有连接
	 * @param rs
	 * @param ps
	 * @param conn
	 * @throws SQLException
	 */
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
	}
	
	/**
	 * 关闭所有连接
	 * @param rs
	 * @param stat
	 * @param conn
	 * @throws SQLException
	 */
	public static void closeAll(ResultSet rs, Statement stat, Connection conn) throws SQLException {
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (conn != null) {
				conn.close();
			}
	}
	
	/**
	 * 获取一个执行sql语句的对象
	 * @param conn
	 * @return Statement
	 * @throws SQLException 
	 * @date:2016-5-18下午8:53:20
	 */
	public static Statement getStatement(Connection conn) throws SQLException {
		Statement stat = null;
		stat = conn.createStatement();
		return stat;
	}
	
	/**
	 * 执行sql语句 <B>仅查询语句</B> 容易sql注入
	 * @param stat
	 * @param sql
	 * @return ResultSet
	 * @throws SQLException 
	 * @date:2016-5-18下午8:53:20
	 */
	public static ResultSet executeQuerySql(Statement stat, String sql) throws SQLException {
		ResultSet result = null;
		result = stat.executeQuery(sql);
		return result;
	}
	
	/**
	 * 获取PreparedStatement
	 * @param
	 * @return
	 * @throws SQLException 
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException{
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return pstmt;
		
	}
	
	/**
	 * 
	 * @param
	 * @return
	 * @throws SQLException 
	 */
	public static ResultSet findResult(PreparedStatement pstmt, List params) throws SQLException{

        DBUtil.addParams(pstmt, params);
        ResultSet result = pstmt.executeQuery();
		return result;
	}

	/**
	 * 关闭PreparedStatement
	 * @param pstmt
	 * @throws SQLException 
	 * @date:2016-5-18下午8:52:52
	 */
	public static void closePreparedStatement(PreparedStatement pstmt) throws SQLException {
		if (pstmt != null) {
			pstmt.close();
		}
	}
	
	public static void main(String[] args) {
		ResultSet r1 = null;
		ResultSet r2 = null;
		Statement s = null;
		Connection c = null;
		try {
//			c = getConnection("4028b88155239d190155239d19490000", "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.47/rolldata", "root", "3e4b82e48b4a52fa");
			c = getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.1.47/rolldata?useUnicode=true&characterEncoding=utf8?useUnicode=true&characterEncoding=utf8", "root", "");
//			c = getConnection("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521:orcl?useUnicode=true&characterEncoding=UTF-8", "SSL_HH", "Shen0920");
			s = getStatement(c);
//			String sql = DynamicDBUtil.getCharacterSetConnection("MYSQL");
			String sql =" select * from 部门管理";
			ResultSet rrr = executeQuerySql(s, sql);
//			findResult(c, sql, params);
			while (rrr.next()) {
				System.out.println("rrr=========" + rrr.getString(1));
				
			}
//			r1 = executeQuerySql(s, " select table_name from all_tables where owner = 'APEX_030200' order by table_name ");
//			while (r1.next()) {
//				System.out.println("1=========" + r1.getString(1));
//				
//			}
//			r1 = executeQuerySql(s, "select view_name from all_views where owner = 'APEX_030200' order by view_name");
//			while (r1.next()) {
//				System.out.println("2=========" + r1.getString(1));
//				
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				closeAll(r2, s, c);
				closeAll(r1, s, c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
