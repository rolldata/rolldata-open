package com.rolldata.web.system.util;

import com.rolldata.core.util.DataSourceTool;
import com.rolldata.core.util.SpringContextHolder;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.enums.DataBaseSortEnum;
import com.rolldata.web.system.pojo.ConditionsBean;
import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.BaseService;
import com.rolldata.web.system.service.impl.QueryVOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static com.rolldata.core.util.DataSourceTool.*;

/**
 * SQLDML语句操作工具
 *
 * @Title:SQLDMLPretreatmentUtil
 * @Description:SQLDMLPretreatmentUtil
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-11-19
 * @version:V1.0
 */
public class SQLDMLPretreatmentUtil {

    private static final Logger logger = LogManager.getLogger(SQLDMLPretreatmentUtil.class);

    /**
     * 不同库case函数关键字
     */
    public static final String[] CASE_TYPE = { "signed", "number(10,0)", "integer"};

    /**
     * case标号
     */
    public static final Integer CASE_NUMBER = getDBType();

    public static final String UNION_ALL = "union all";
    private static final Pattern COMPILE_SINGLE = Pattern.compile("'", Pattern.LITERAL);

    /**
     * 基本公式和数据库交互相关服务层
     */
    private static BaseService baseService = SpringContextHolder.getBean(BaseService.class);

    /**
     * 根据sql获取数据(适合一个字段的SQL)
     *
     * @param entityManager
     * @param sql           查询sql
     * @return
     */
    public static List<String> queryFirstColumnDatas(EntityManager entityManager, String sql) {

        if (StringUtil.isEmpty(sql)) {
            return Collections.emptyList();
        }
        if (null == entityManager) {
            throw new RuntimeException("系统管理器异常");
        }
        entityManager.getTransaction().begin();// 这个不加会报错
        Query query = null;
        query = entityManager.createNativeQuery(sql);
        List<String> listRe = query.getResultList();
        entityManager.getTransaction().commit();
        return listRe;
    }

    /**
     * 查询一行一列数据
     *
     * @param entityManager
     * @param sql           查询sql
     * @return
     */
    public static String queryUniqueData(EntityManager entityManager, String sql) {

        String data = "";
        if (StringUtil.isEmpty(sql)) {
            return data;
        }
        try {
            if (null == entityManager) {
                List<Object> listRe = baseService.queryValueObjectsBySQL((query) ->{
                    return query.getResultList();
                }, sql, null);
                if (!listRe.isEmpty()) {
                    data = (String) listRe.get(0);
                }
            } else {
                Query query = null;
                query = entityManager.createNativeQuery(sql);
                List<Object> listRe = query.getResultList();
                if (listRe.size() > 0) {
                    data = (String) listRe.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行SQL失败：[{}]", sql, e);
        }
        return data;
    }

    /**
     * 查询一行一列数据
     *
     * @param entityManager
     * @param sql           查询sql
     * @param pValues       问号值
     * @return
     */
    public static String queryUniqueData(EntityManager entityManager, String sql, List<String> pValues) throws Exception {

        String data = "";
        if (StringUtil.isEmpty(sql)) {
            return data;
        }
        if (null == entityManager) {
            List<Object> listRe = baseService.queryValueObjectsBySQL((query) ->{
                return query.getResultList();
            }, sql, pValues);
            if (!listRe.isEmpty()) {
                data = (String) listRe.get(0);
            }
        } else {
            Query query = createEntityQuery(sql, pValues, entityManager);
            List<Object> listRe = query.getResultList();
            if (listRe.size() > 0) {
                data = (String) listRe.get(0);
            }
        }
        return data;
    }

    /**
     * 查询表名的字段
     *
     * @param entityManager 实体管理器
     * @param tbNames       表名集合
     * @return
     */
    public static List<SysTableInfo> queryTableInfos(EntityManager entityManager, List<String> tbNames) {

        String database = queryUniqueData(
            entityManager,
            DynamicDBUtil.getCurrentDataBaseSQL(FORMALDBTYPE.toUpperCase())
        );
        SQLContainer sqlContainer = DynamicDBUtil.getTableColumnNameSQL(
            tbNames,
            database,
            FORMALDBTYPE.toUpperCase()
        );
        String tableColumnNameSQL = sqlContainer.getSql();

        // 发布表名字段数据
        List<SysTableInfo> sysTableInfos = new ArrayList<>();
        if (StringUtil.isNotEmpty(tableColumnNameSQL)) {
            Query query = createEntityQuery(tableColumnNameSQL, sqlContainer.getPrecompiledValues(), entityManager);
            sysTableInfos = new QueryVOImpl().queryList(query);
        }
        return sysTableInfos;
    }


    /**
     * 给sql的where字段的具体值
     *
     *  @param sb
     * @param operator          符号
     * @param parameValue       V
     * @param parameValues
     * @param precompiledValues SQL预编译值
     */
    public static void appendParame(StringBuffer sb, String operator, String parameValue, List<String> parameValues,
            List<String> precompiledValues) {

        // 拼 [in/like/=]  字段 + 过滤符号 + 值
        sb.append(SpecialSymbolConstants.SPACE).append(operator);
        if (operator.indexOf("in") >= 0) {
            sb.append(SpecialSymbolConstants.getInSqlParameBlock(parameValues, precompiledValues));
        } else {
            if (operator.indexOf("like") >= 0) {
                sb.append(SpecialSymbolConstants.getLikeSqlParameBlock(parameValue, precompiledValues));
            } else {
                precompiledValues.add(parameValue);
                sb.append(SpecialSymbolConstants.QUESTION_MARK);
            }
        }
    }

    /**
     * 给模型拼接order by
     *
     * @param tableAlias  表的别名
     * @param orders      排序集合
     * @param SQLSurround 字典包裹的关符号
     * @return
     */
    public static String appendSQLOrder(String tableAlias, List<ConditionsBean> orders, String SQLSurround) {

        StringBuffer orderSb = new StringBuffer(" ");
        if (null != orders && orders.size() > 0) {
            orderSb.append(" order by ");
            for (ConditionsBean order : orders) {
                if (StringUtil.isNotEmpty(tableAlias)) {
                    orderSb.append(tableAlias).append(SpecialSymbolConstants.POINT);
                }
                orderSb.append(SQLSurround).append(order.getName()).append(SQLSurround)
                       .append(SpecialSymbolConstants.SPACE)
                       .append(DataBaseSortEnum.getSort(order.getValue()))
                       .append(SpecialSymbolConstants.COMMA);
            }
        }
        return orderSb.substring(0, orderSb.length() - 1);
    }

    /**
     * 创建{@link EntityManager} 的查询
     *
     * @param sql           SQL语句
     * @param pValues       预编译值
     * @param entityManager 实体管理器
     * @return
     */
    public static Query createEntityQuery(String sql, List<String> pValues, EntityManager entityManager) {

        Query query = entityManager.createNativeQuery(sql);
        if (null != pValues) {
            for (int i = 0; i < pValues.size(); i++) {
                query.setParameter(i + 1, pValues.get(i));
            }
        }
        return query;
    }

    /**
     * 创建{@link EntityManager} 的查询
     *
     * @param hql           Hql语句
     * @param pValues       预编译值
     * @param entityManager 实体管理器
     * @return
     */
    public static Query createHqlQuery(String hql, List<String> pValues, EntityManager entityManager) {

        Query query = entityManager.createQuery(hql);
        if (null != pValues) {
            for (int i = 0; i < pValues.size(); i++) {
                query.setParameter(i + 1, pValues.get(i));
            }
        }
        return query;
    }

    public static int getDBType() {

        int i = 0;
        if (FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL)) {
            i = 0;
        } else if (FORMALDBTYPE.equals(DBTYPE_ORACLE) ||
                FORMALDBTYPE.equals(DBTYPE_DM8)) {
            i = 1;
        } else if (FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER)) {
            i = 2;
        }
        return i;
    }

    /**
     * 执行SQL不是预编译类时,如果值中包含单引号等特殊符号,导致SQL执行失败
     * <ul>结果示例：
     *  <li>asdfasdf''``[`faf₩ -----> asdfasdf''''``[`faf₩
     *
     * @param value
     * @return
     */
    public static String precompile(String value) {

        if (StringUtil.isEmpty(value)) {
            return "";
        }
        int stringLength = value.length();
        StringBuffer buf = new StringBuffer((int) (stringLength * 1.1));
        String parameterAsString = value;
        for (int i = 0; i < stringLength; ++i) {
            char c = value.charAt(i);

            switch (c) {
                case '\n': /* Must be escaped for logs */
                    buf.append('\\');
                    buf.append('n');

                    break;

                case '\r':
                    buf.append('\\');
                    buf.append('r');

                    break;

                case '\\':
                    buf.append('\\');
                    buf.append('\\');

                    break;

                case '\'':
                    buf.append('\'');
                    buf.append('\'');

                    break;

                case '"': /* Better safe than sorry */

                    buf.append('"');

                    break;

                case '\032': /* This gives problems on Win32 */
                    buf.append('\\');
                    buf.append('Z');

                    break;
                default:
                    buf.append(c);
            }
        }
        parameterAsString = buf.toString();
        return parameterAsString;
    }

    /**
     * 根据系统数据库类型,把字段单引号,改成双引号<br>
     * DBTYPE_ORACLE <br>
     * DBTYPE_DM8 <br>
     * DBTYPE_H2 <br>
     *
     * @param selectColumn sql 字段
     * @return
     */
    public static String convertChar(String selectColumn) {

        StringUtil.requireNonNull(selectColumn, "SQL 查询字段不能为空");
        if (FORMALDBTYPE.equals(DBTYPE_ORACLE) ||
            FORMALDBTYPE.equals(DBTYPE_DM8) ||
            FORMALDBTYPE.equals(DBTYPE_H2)) {
            selectColumn = COMPILE_SINGLE.matcher(selectColumn).replaceAll("\"");
        }
        return selectColumn;
    }
}
