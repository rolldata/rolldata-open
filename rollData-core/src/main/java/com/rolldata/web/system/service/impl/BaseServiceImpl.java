package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.exception.SQLRuntimeException;
import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.BaseService;
import com.rolldata.web.system.service.EntityManagerCommand;
import com.rolldata.web.system.service.EntityManagerQuery;
import com.rolldata.web.system.service.QueryVOs;
import com.rolldata.web.system.util.SQLDMLPretreatmentUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService, EntityManagerCommand {
	private EntityManagerFactory emf;

    private final Logger log = LogManager.getLogger(BaseServiceImpl.class);

    // 使用这个标记来注入EntityManagerFactory
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public  EntityManagerFactory getEntityManagerFactory() {
        return this.emf;
    }

	@Override
	public <T> T queryList(String selectHql, String countHql, Map<String, Object> params,Object obj) {
		Pageable pageable = PageRequest.of((Integer)invokeGet(obj, "pageable"), (Integer)invokeGet(obj, "size"));
		EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Query countQuery = entityManager.createQuery(countHql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
		Long totalCount = (Long)countQuery.getSingleResult();
        Query query = entityManager.createQuery(selectHql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<T> rs = query.getResultList();
        Page<T> page = new PageImpl<>(rs, pageable, totalCount);
        invokeSet(obj,"totalPagets",page.getTotalPages());
        invokeSet(obj,"totalElements",page.getTotalElements());
        invokeSet(obj,"result",rs);
        entityManager.getTransaction().commit();
        entityManager.close();
		return (T) obj;
	}

	@Override
	public <T> T queryListNoPage(String selectHql, Map<String, Object> params,Object obj) {
		EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(selectHql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<T> rs = query.getResultList();
        invokeSet(obj,"result",rs);
        entityManager.getTransaction().commit();
        entityManager.close();
		return (T) obj;
	}
/** 
    * java反射bean的get方法 
    *  
    * @param objectClass 
    * @param fieldName 
    * @return 
    */ 
	public static Method getGetMethod(Class objectClass, String fieldName) {  
	       StringBuffer sb = new StringBuffer();  
	       sb.append("get");  
	       sb.append(fieldName.substring(0, 1).toUpperCase());  
	       sb.append(fieldName.substring(1));  
	       try {  
	           return objectClass.getMethod(sb.toString());  
	       } catch (Exception e) {  
	       }  
	       return null;  
	   } 

/** 
     * java反射bean的set方法 
     *  
     * @param objectClass 
     * @param fieldName 
     * @return 
     */ 
	public static Method getSetMethod(Class objectClass, String fieldName) {  
        try {  
            Class[] parameterTypes = new Class[1];  
            Field field = objectClass.getDeclaredField(fieldName);  
            parameterTypes[0] = field.getType();  
            StringBuffer sb = new StringBuffer();  
            sb.append("set");  
            sb.append(fieldName.substring(0, 1).toUpperCase());  
            sb.append(fieldName.substring(1));  
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);  
            return method;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 

/** 
     * 执行get方法 
     *  
     * @param o 执行对象 
     * @param fieldName 属性 
     */
	public static Object invokeGet(Object o, String fieldName) {  
        Method method = getGetMethod(o.getClass(), fieldName);  
        try {  
            return method.invoke(o, new Object[0]);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
	

/** 
     * 执行set方法 
     *  
     * @param o 执行对象 
     * @param fieldName 属性 
     * @param value 值 
     */  
    public static void invokeSet(Object o, String fieldName, Object value) {  
        Method method = getSetMethod(o.getClass(), fieldName);  
        try {  
            method.invoke(o, new Object[] { value });  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    /**
     * 初始化实体类
     */
    @Override
    public void initEntityAttr(Map<String, SingleTableEntityPersister> ENTITY_ATTR,
                               Map<String, Map<String, SysTableInfo>> ENTITY_COLUM) {

        // 非空校验
        if (null == ENTITY_ATTR ||
                null == ENTITY_COLUM) {
            return;
        }

        // 初始化过,直接返回
        if (!ENTITY_ATTR.isEmpty()) {
            return;
        }
//        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) this.emf.unwrap(SessionFactory.class);
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        Map<String, EntityPersister> entityPersisters = sessionFactory.getEntityPersisters();
        Iterator<Map.Entry<String, EntityPersister>> iterator = entityPersisters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EntityPersister> next = iterator.next();
            EntityPersister value = next.getValue();
            String key = next.getKey();
            SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) value;
            ENTITY_ATTR.put(key, singleTableEntityPersister);
            Iterable<AttributeDefinition> attributes = singleTableEntityPersister.getAttributes();
            Map<String, SysTableInfo> columns = new LinkedHashMap<>();
            int parameterIndex = 1;

            // 主键默认加上
            SysTableInfo idInfo = new SysTableInfo();
            idInfo.setCOLUMN("id");
            idInfo.setCOLUMNNAME("id");
            idInfo.setType("string");
            idInfo.setParameterIndex(parameterIndex);
            idInfo.setTABLENAME(((SingleTableEntityPersister) value).getTableName());
            columns.put("id", idInfo);
            for (AttributeDefinition attr : attributes) {
                parameterIndex++;
                String propertyName = attr.getName();
                String[] propertyColumnNames = singleTableEntityPersister.getPropertyColumnNames(propertyName);
                Type propertyType = singleTableEntityPersister.getPropertyType(propertyName);
                SysTableInfo sysTableInfo = new SysTableInfo();
                sysTableInfo.setCOLUMN(propertyColumnNames[0]);
                sysTableInfo.setCOLUMNNAME(propertyName);
                sysTableInfo.setType(propertyType.getName());
                sysTableInfo.setParameterIndex(parameterIndex);
                sysTableInfo.setTABLENAME(((SingleTableEntityPersister) value).getTableName());
                columns.put(propertyName, sysTableInfo);
            }
            ENTITY_COLUM.put(key, columns);
        }
    }

    /**
     * 获取SQL结果
     *
     * @param formulaParam SQL里的参数项
     * @return
     * @throws Exception
     */
    @Override
    public String getSQLValue(String formulaParam) throws Exception {

        return (String) this.command((q) -> {
            String value = "";
            List<Object> listRe = q.getResultList();

            // 取第一个值
            Object row = listRe.get(0);
            if (row instanceof Object[]) {
                Object[] l = (Object[]) row;
                value = String.valueOf(l[0]);
            } else {
                value = String.valueOf(row);
            }
            return value;
        }, formulaParam, null);
    }

    @Override
    public List queryValueObjectsBySQL(QueryVOs queryVOs, String sql, List<String> precompiledValues) {

        return (List) this.command((q) -> {
            return queryVOs.queryList(q);
        }, sql, precompiledValues);
    }

    @Override
    public List queryValueObjectsBySQL(EntityManager entityManager, QueryVOs queryVOs, String sql,
                                       List<String> precompilevdValues) {

        Query query = SQLDMLPretreatmentUtil.createEntityQuery(sql, precompilevdValues, entityManager);
        return queryVOs.queryList(query);
    }

    /**
     * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
     *
     * @param sql               SQL语句
     * @param precompiledValues 预编译参数
     * @param clazz             对象
     * @return 返回clazz对象集合
     * @throws Exception
     */
    @Override
    public <T> List<T> queryEntitiesBySQL(String sql, List<String> precompiledValues, Class<T> clazz) throws Exception {

        return this.queryValueObjectsBySQL((query) ->{
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
            return query.getResultList();
        }, sql, precompiledValues);
    }

    /**
     * 根据SQL,查询集合,SQl语句的as别名和对象属性名字对应
     *
     * @param entityManager      查询实体管理器
     * @param sql     SQL语句
     * @param pValues 预编译参数
     * @param clazz   对象
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> queryEntitiesBySQL(EntityManager entityManager, String sql, List<String> pValues,
                                          Class<T> clazz) throws Exception {

        Query query = SQLDMLPretreatmentUtil.createEntityQuery(sql, pValues, entityManager);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        return query.getResultList();
    }

    /**
     * 根据SQL,查询唯一数据
     *
     * @param sql               SQL语句
     * @param precompiledValues 预编译参数
     * @param clazz             对象
     * @return 返回clazz对象
     * @throws Exception
     */
    @Override
    public <T> T queryEntityBySQL(String sql, List<String> precompiledValues, Class<T> clazz) throws Exception {

        T entity = null;
        entity = (T) this.command((q) -> {
            return (T) q.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)).uniqueResult();
        }, sql, precompiledValues);
        return entity;
    }

    /**
     * 根据SQL,查询唯一数据
     *
     * @param entityManager      查询实体管理器
     * @param sql     SQL语句
     * @param pValues 预编译参数
     * @param clazz   对象
     * @return
     * @throws Exception
     */
    @Override
    public <T> T queryEntityBySQL(EntityManager entityManager, String sql, List<String> pValues, Class<T> clazz) throws Exception {

        if (null == entityManager) {
            return queryEntityBySQL(sql, pValues, clazz);
        }
        Query query = SQLDMLPretreatmentUtil.createEntityQuery(sql, pValues, entityManager);
        return (T) query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(clazz)).uniqueResult();
    }

    /**
     * 执行SQL语句
     *
     * @param sql               SQL语句
     * @param precompiledValues 预编译参数
     * @throws Exception
     */
    @Override
    public void executeSQL(String sql, List<String> precompiledValues) throws Exception {

        this.command((q) -> {
            return q.executeUpdate();
        }, sql, precompiledValues);
    }

    /**
     * 执行SQL语句
     *
     * @param entityManager     事务管理器
     * @param sql               SQL语句
     * @param precompiledValues 预编译参数
     * @throws Exception
     */
    @Override
    public void executeSQL(EntityManager entityManager, String sql, List<String> precompiledValues) throws Exception {

        Query query = SQLDMLPretreatmentUtil.createEntityQuery(sql, precompiledValues, entityManager);
        query.executeUpdate();
        this.log.info("执行SQL[{}]", sql);
    }

    /**
     * 执行多条SQL语句,不用每次重新创建em(一般是执行增删改)
     *
     * @param sqlContainers SQL信息容器
     * @throws Exception
     */
    @Override
    public void executeSQLList(List<SQLContainer> sqlContainers) throws Exception {

        Objects.requireNonNull(sqlContainers, "SQL容器不能为空");
        if (sqlContainers.isEmpty()) {
            return;
        }
        EntityManager entityManager = this.emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            for (SQLContainer sqlContainer : sqlContainers) {
                Query query = SQLDMLPretreatmentUtil.createEntityQuery(
                        sqlContainer.getSql(),
                        sqlContainer.getPrecompiledValues(),
                        entityManager
                );
                query.executeUpdate();
                this.log.info("执行SQL[{}]", sqlContainer.getSql());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.log.error(e);
            entityManager.getTransaction().rollback();
            throw new SQLRuntimeException("SQL执行失败", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * 打开关闭EntityManager实体管理器
     *
     * @param sql     执行的SQL语句
     * @param pValues 预编译值
     */
    @Override
    public Object command(EntityManagerQuery entityManagerQuery, String sql, List<String> pValues) {

        Object obj = null;
        EntityManager entityManager = this.emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Query query = SQLDMLPretreatmentUtil.createEntityQuery(sql, pValues, entityManager);
            obj = entityManagerQuery.qeury(query);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.log.error(e);
            entityManager.getTransaction().rollback();
            throw new SQLRuntimeException("SQL执行失败", e);
        } finally {
            this.log.info("执行SQL[{}]", sql);
            entityManager.close();
        }
        return obj;
    }
}
