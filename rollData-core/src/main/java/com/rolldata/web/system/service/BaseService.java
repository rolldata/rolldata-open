package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.pojo.SysTableInfo;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: BaseService
 * @Description: 基础服务接口
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2021年6月29日
 * @version V1.0
 */
public interface BaseService {

	EntityManagerFactory getEntityManagerFactory();

	public <T>T queryList(String selectHql, String countHql, Map<String, Object> params, Object obj);

	public <T>T queryListNoPage(String selectHql, Map<String, Object> params, Object obj);
	/**
	 * 初始化实体类
	 */
	void initEntityAttr(
            Map<String, SingleTableEntityPersister> ENTITY_ATTR,
            Map<String, Map<String, SysTableInfo>> ENTITY_COLUM
    );

	/**
	 * 获取SQL结果
	 *
	 * @param formulaParam SQL里的参数项
	 * @return
	 * @throws Exception
	 */
	String getSQLValue(String formulaParam) throws Exception;

	/**
	 * 执行SQL返回结果集,返回的结果数据类型可以根据实现 {@link QueryVOs} 接口来定
	 *
	 * @param queryVO 回调函数
	 * @param sql     执行SQL
	 * @param pValues 预编译参数
	 * @return
	 * @throws Exception
	 */
	List queryValueObjectsBySQL(QueryVOs queryVO, String sql, List<String> pValues) throws Exception;

	List queryValueObjectsBySQL(EntityManager entityManager, QueryVOs queryVO, String sql, List<String> pValues) throws Exception;

	/**
	 * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
	 *
	 * @param sql               SQL语句
	 * @param pValues 预编译参数
	 * @param clazz             对象
	 * @return 返回clazz对象集合
	 * @throws Exception
	 */
	<T> List<T> queryEntitiesBySQL(String sql, List<String> pValues, Class<T> clazz) throws Exception;

	/**
	 * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
	 *
	 * @param em      查询实体管理器
	 * @param sql     SQL语句
	 * @param pValues 预编译参数
	 * @param clazz   对象
	 * @return
	 * @throws Exception
	 */
	<T> List<T> queryEntitiesBySQL(EntityManager em, String sql, List<String> pValues, Class<T> clazz) throws Exception;

	/**
	 * 根据SQL,查询唯一数据
	 *
	 * @param sql               SQL语句
	 * @param pValues 预编译参数
	 * @param clazz             对象
	 * @return 返回clazz对象
	 * @throws Exception
	 */
	<T> T queryEntityBySQL(String sql, List<String> pValues, Class<T> clazz) throws Exception;

	/**
	 * 根据SQL,查询唯一数据
	 *
	 * @param em      查询实体管理器
	 * @param sql     SQL语句
	 * @param pValues 预编译参数
	 * @param clazz   对象
	 * @return
	 * @throws Exception
	 */
	<T> T queryEntityBySQL(EntityManager em, String sql, List<String> pValues, Class<T> clazz) throws Exception;

	/**
	 * 执行SQL语句
	 *
	 * @param sql               SQL语句
	 * @param pValues 预编译参数
	 * @throws Exception
	 */
	void executeSQL(String sql, List<String> pValues) throws Exception;

	/**
	 * 执行SQL语句
	 *
	 * @param entityManager     事务管理器
	 * @param sql               SQL语句
	 * @param pValues 预编译参数
	 * @throws Exception
	 */
	void executeSQL(EntityManager entityManager, String sql, List<String> pValues) throws Exception;

	/**
	 * 执行多条SQL语句,不用每次重新创建em(一般是执行增删改)
	 *
	 * @param sqlContainers SQL信息容器
	 * @throws Exception
	 */
	void executeSQLList(List<SQLContainer> sqlContainers) throws Exception;

}
