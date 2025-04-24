package com.rolldata.web.system.util;

import com.rolldata.core.common.pojo.WdStringJoiner;
import com.rolldata.core.util.DataSourceTool;
import com.rolldata.core.util.DateUtils;
import com.rolldata.core.util.SpringContextHolder;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * 根据实体类构建相关SQL
 *
 * @Title: EntitySqlUtils
 * @Description: EntitySqlUtils
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-06-16
 * @version: V1.0
 */
public class EntitySqlUtils {

    private static final Logger log = LogManager.getLogger(EntitySqlUtils.class);

    /**
     * 类名对应实体属性
     */
    private static final Map<String, SingleTableEntityPersister> ENTITY_ATTR = new HashMap<>();

    /**
     * 类名对应表字段
     */
    private static final Map<String, Map<String, SysTableInfo>> ENTITY_COLUM = new LinkedHashMap<>();

    /**
     * 实体插入SQL语句
     */
    private static final Map<String, String> ENTITY_INSERT_SQL = new HashMap<>();
    private static final BaseService baseService = SpringContextHolder.getBean(BaseService.class);
    /**
     * 初始化实体类
     */
    public static void initEntityAttr() {

        // 由于需要emf,所以用这个开启
        baseService.initEntityAttr(ENTITY_ATTR, ENTITY_COLUM);
    }

    public static String insertSql(String className) throws SQLException {

        String sql = ENTITY_INSERT_SQL.get(className);

        // 生成过,直接返回
        if (StringUtil.isNotEmpty(sql)) {
            log.info("实体SQL:{}", sql);
            return sql;
        }
        SingleTableEntityPersister singleTableEntityPersister = ENTITY_ATTR.get(className);
        Objects.requireNonNull(singleTableEntityPersister, "未找到实体");
        Map<String, SysTableInfo> columnMap = ENTITY_COLUM.get(className);
        WdStringJoiner columnJoiner = new WdStringJoiner(", ");
        WdStringJoiner columnValueJoiner = new WdStringJoiner(", ");
        Iterator<Map.Entry<String, SysTableInfo>> entityInfo = columnMap.entrySet().iterator();
        while (entityInfo.hasNext()) {
            Map.Entry<String, SysTableInfo> next = entityInfo.next();
            SysTableInfo sysTableInfo = next.getValue();
            columnJoiner.add(sysTableInfo.getCOLUMN());
            if ((DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
                    DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8)) &&
                "timestamp".equals(sysTableInfo.getType())) {
                columnValueJoiner.add("to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            } else {
                columnValueJoiner.add("?");
            }
        }
        String insertFixedSql = "INSERT INTO " + singleTableEntityPersister.getTableName() + " (" + columnJoiner + ") ";
        String valueFixedSql = "VALUES (" + columnValueJoiner + ")";
        sql = insertFixedSql + valueFixedSql;
        ENTITY_INSERT_SQL.put(className, sql);
        log.info("实体SQL:{}", sql);
        return sql;
    }

    /**
     * 生成的SQL是根据map生成的,所以顺序可以保证
     *
     * @param entity
     * @param className
     * @param targetPstmt
     * @throws SQLException
     */
    public static void addBatch(Object entity, String className, PreparedStatement targetPstmt) throws SQLException {

        if (null == targetPstmt) {
            return;
        }
        SingleTableEntityPersister singleTableEntityPersister = ENTITY_ATTR.get(className);
        Objects.requireNonNull(singleTableEntityPersister, "未找到实体");
        Map<String, SysTableInfo> columnMap = ENTITY_COLUM.get(className);
        Iterator<Map.Entry<String, SysTableInfo>> iterator = columnMap.entrySet().iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, SysTableInfo> next = iterator.next();
            String key = next.getKey();
            SysTableInfo sysTableInfo = next.getValue();
            Object propertyValue = null;
            if ("id".equals(key)) {
                propertyValue = singleTableEntityPersister.getIdentifier(entity);
            } else {
                propertyValue = singleTableEntityPersister.getPropertyValue(entity, key);
            }
            if ("timestamp".equals(sysTableInfo.getType())) {
                targetPstmt.setString(index, DateUtils.date2Str((Date) propertyValue, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
            } else {
                if (propertyValue instanceof String) {
                    targetPstmt.setString(index, (String) propertyValue);
                } else if (propertyValue instanceof Date) {
                    Date date = (Date) propertyValue;
                    targetPstmt.setDate(index, new java.sql.Date(date.getTime()));
                } else if (propertyValue instanceof Integer) {
                    targetPstmt.setInt(index, (Integer) propertyValue);
                } else if (propertyValue instanceof Float) {
                    targetPstmt.setFloat(index, (Float) propertyValue);
                } else if (propertyValue instanceof Double) {
                    targetPstmt.setDouble(index, (Double) propertyValue);
                } else {
                    targetPstmt.setObject(index, propertyValue);
                }
                //if (null == propertyValue) {
                //    targetPstmt.setString(index, null);
                //} else {
                //    targetPstmt.setString(index, String.valueOf(propertyValue));
                //}
            }
            index++;
        }
        targetPstmt.addBatch();
    }

    public static Map<String, SingleTableEntityPersister> getEntityAttr() {

        return ENTITY_ATTR;
    }

    public static Map<String, Map<String, SysTableInfo>> getEntityColum() {

        return ENTITY_COLUM;
    }

    public static Map<String, String> getEntityInsertSql() {

        return ENTITY_INSERT_SQL;
    }

    /**
     * 设置预编译值
     * <p>由于直接用此方法,如果实体类加字段或减字段就报错,所以加 {@link SimpleProphet} 来判断下</p>
     *
     * @param entityMap
     * @param ps
     * @param column
     * @param value
     */
    public static void parameterString(Map<String, SysTableInfo> entityMap, PreparedStatement ps,
            String column, String value) throws Exception {

        Objects.requireNonNull(entityMap, "JDBC Exception");
        Objects.requireNonNull(ps, "JDBC Exception");
        SysTableInfo sysTableInfo = entityMap.get(column);
        Objects.requireNonNull(sysTableInfo, "JDBC Exception");
        ps.setString(sysTableInfo.getParameterIndex(), value);
    }

    /**
     * DIY 设置 SQL 问号的参数
     *
     * @param className 实体 class 名
     * @return
     * @throws Exception
     */
    public static SimpleProphet diy(String className) throws Exception {

        SimpleProphet simpleProphet = new SimpleProphet();
        simpleProphet.insertSql = insertSql(className);
        Map<String, SysTableInfo> stringSysTableInfoMap = EntitySqlUtils.getEntityColum().get(className);
        simpleProphet.columnMap = new HashMap<>(stringSysTableInfoMap);
        simpleProphet.banareColumnMap = stringSysTableInfoMap;
        return simpleProphet;
    }

    public static class SimpleProphet {

        private String insertSql;

        private Map<String, SysTableInfo> columnMap;

        private Map<String, SysTableInfo> banareColumnMap;

        private PreparedStatement ps;

        public String getInsertSql() {
            return insertSql;
        }

        public SimpleProphet setPs(PreparedStatement ps) {
            this.ps = ps;
            return this;
        }

        public void setString(String column, String value) throws Exception {
            parameterString(columnMap, ps, column, value);
            columnMap.remove(column);
        }

        public void addBatch() throws Exception {
            after();
            ps.addBatch();
        }

        public void executeUpdate() throws Exception {
            after();
            ps.executeUpdate();
        }

        /**
         * 插入时,把没有的字段设置成空,因为生产 insert 语句是所有字段都生成
         *
         * @throws Exception
         */
        private void after() throws Exception {
            for (Map.Entry<String, SysTableInfo> e : columnMap.entrySet()) {
                parameterString(columnMap, ps, e.getKey(), null);
            }
            columnMap = new HashMap<>(banareColumnMap);
        }
    }
}
