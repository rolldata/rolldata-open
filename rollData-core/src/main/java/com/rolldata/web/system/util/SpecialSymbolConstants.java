package com.rolldata.web.system.util;

import com.rolldata.core.util.DataSourceTool;
import com.rolldata.core.util.StringUtil;

import java.util.Collections;
import java.util.List;

/**
 * @Title: SpecialSymbolConstants
 * @Description: 特殊符号常量类
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-11-18
 * @version: V1.0
 */
public class SpecialSymbolConstants {

    /**
     * Don't let anyone instantiate this class.
     */
    private SpecialSymbolConstants() {}

    /**
     * 左方括号
     */
    public static final char CH_LEFT_SQUARED_BRACKET = '[';

    public static final char CH_RIGHT_SQUARED_BRACKET = ']';

    /**
     * 左括号
     */
    public static final char CH_LEFT_BRACKETS = '(';

    /**
     * 右括号
     */
    public static final char CH_RIGHT_BRACKETS = ')';

    /**
     * 等号
     */
    public static final char CH_EQUALS = '=';

    /**
     * 逗号
     */
    public static final char CH_COMMA = ',';

    /**
     * 左括号
     */
    public static final String LEFT_BRACKETS = "(";

    /**
     * 右括号
     */
    public static final String RIGHT_BRACKETS = ")";

    /**
     * 间隔
     */
    public static final String SPACE = "\u0009";

    /**
     * 单引号
     */
    public static final String APOSTROPHE = "'";

    /**
     * 百分号
     */
    public static final String PERCENT = "%";

    /**
     * 点
     */
    public static final String POINT = ".";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 双引号
     */
    public static final String DOUBLEQUOTES = "\"";

    /**
     * 波浪/间隔符号(tab上面的按钮)
     */
    public static final String WAVE = "`";

    /**
     * 问号
     */
    public static final String QUESTION_MARK = "?";

    /**
     * SQL别名关键字
     */
    public static final String SQL_AS = " as ";

    /**
     * 等号
     */
    public static final String EQUALS = "=";

    public static final String LEFTLIKE = "开始以";
    public static final String RIGHTLIKE = "结束以";
    /**
     * 系统库字段的特殊字符
     */
    public static final String SYSTEM_COLUMN_SURROUND = getSQLSurround(DBUtil.getSystemDataSourceInfo().getDbType());

    /**
     * " ("
     */
    public static final String SQL_LEFT_BRACKETS = SpecialSymbolConstants.SPACE + SpecialSymbolConstants.LEFT_BRACKETS;

    public static final String AND = "and";

    public static final String AND_SPACE = " and ";

    /**
     * 拼sql的前后括号 ('sqlValue')
     * <br>info: 后来商量单引号都让用户自己写
     * <br>info: 目前用预编译形式,单引号不用写
     *
     * @param sqlValues         SQL参数值
     * @param precompiledValues SQL预编译值
     * @return
     */
    public static String getInSqlParameBlock(List<String> sqlValues, List<String> precompiledValues) {

        StringBuilder inSQL = new StringBuilder(SPACE + LEFT_BRACKETS);
        if (null == sqlValues) {
            sqlValues = Collections.emptyList();
        }
        if (sqlValues.isEmpty()) {
            inSQL.append(QUESTION_MARK + COMMA);
            precompiledValues.add("");
        } else {

            // TODO: 处理SQL，in的
            for (String sqlValue : sqlValues) {
                inSQL.append(QUESTION_MARK + COMMA);
                precompiledValues.add(sqlValue);
            }
        }
        inSQL.deleteCharAt(inSQL.length() - 1);
        inSQL.append(RIGHT_BRACKETS + SPACE);
        return inSQL.toString();
    }

    /**
     *
     * 拼sql的前后括号和like的百分号 ('%sqlValue%')
     * <br>info: 后来商量单引号和百分号等都让用户自己写
     * <br>info: 2020-8-06 参数用户没法写,后台拼下
     *
     * @param sqlValue          SQL参数值
     * @param precompiledValues SQL预编译值
     * @return
     */
    public static String getLikeSqlParameBlock(String sqlValue, List<String> precompiledValues) {

//        if (StringUtil.isNotEmpty(sqlValue)) {
//            StringBuffer stringBuffer = new StringBuffer(sqlValue);
//            int lastIndex = stringBuffer.lastIndexOf("'");
//            stringBuffer.insert(lastIndex, "%");
//            sqlValue = stringBuffer.toString();
//            sqlValue = sqlValue.replaceFirst("'", "'%");
//        }
        precompiledValues.add("%" + sqlValue + "%");
        return SPACE + LEFT_BRACKETS + QUESTION_MARK
               + RIGHT_BRACKETS + SPACE;
    }

    /**
     * 获取SQL语句列名包围字符
     *
     * @param dbType 数据库类型
     * @return
     */
    public static String getSQLSurround(String dbType) {

        if (DataSourceTool.DBTYPE_MYSQL.equalsIgnoreCase(dbType) ||
            DataSourceTool.DBTYPE_H2.equalsIgnoreCase(dbType)) {
            return WAVE;
        } else {
            return DOUBLEQUOTES;
        }
    }

    /**
     * SQL字段包裹引号
     *
     * @param column 字段
     * @return
     */
    public static String bundleColumn(String column) {

        return SYSTEM_COLUMN_SURROUND + column + SYSTEM_COLUMN_SURROUND;
    }

    /**
     * SQL字段包裹引号
     *
     * @param column 字段
     * @return
     */
    public static String bundleColumn(String column,String dbType) {
        String COLUMN_SURROUND = getSQLSurround(dbType);
        return COLUMN_SURROUND + column + COLUMN_SURROUND;
    }

    /**
     * 模糊查询前后包百分符
     *
     * @param search 模糊查询的数据
     * @return
     */
    public static String bundleSearch(String search) {

        return PERCENT + (StringUtil.isNotEmpty(search) ? search : "") + PERCENT;
    }

    /**
     * SQL value值包裹引号
     *
     * @param value 字段
     * @return
     */
    public static String bundleValue(String value) {
        return APOSTROPHE + value + APOSTROPHE;
    }
}
