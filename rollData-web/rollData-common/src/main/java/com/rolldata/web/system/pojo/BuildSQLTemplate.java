package com.rolldata.web.system.pojo;

import com.rolldata.core.common.pojo.WdStringJoiner;
import com.rolldata.core.util.DataSourceTool;
import com.rolldata.core.util.DateUtils;
import com.rolldata.web.system.enums.Op;
import com.rolldata.web.system.enums.SQLKeySchema;
import com.rolldata.web.system.util.SpecialSymbolConstants;
import java.util.Date;
import java.util.List;

/**
 * 构建SQL语句
 * @Title: BuildSQLTemplate
 * @Description: BuildSQLTemplate
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-01-18
 * @version: V1.0
 */
public class BuildSQLTemplate {

    public static final String INSERT_TEMP = "INSERT INTO {0} ({1}) VALUES ({2})";

    /**
     * 最终可执行的SQL语句
     */
    private StringBuilder sqlStatement;

    /**
     * 配置SQL语句类型(CURD)
     */
    private SQLKeySchema keySchema;

    /**
     * 列
     */
    private WdStringJoiner columns;

    /**
     * 列对应的值
     */
    private WdStringJoiner values;

    /**
     * where条件
     */
    private WdStringJoiner whereConditions;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 优化插入语句
     */
    private boolean isOptimInsert;

    /**
     * 插入语句的多values
     */
    private StringBuffer insertValues;

    /**
     * 操作符
     */
    private Op op;

    public BuildSQLTemplate() {
    }

    public BuildSQLTemplate(SQLKeySchema keySchema) {

        this.keySchema = keySchema;
        this.columns = new WdStringJoiner(SpecialSymbolConstants.COMMA);
        this.values = new WdStringJoiner(SpecialSymbolConstants.COMMA);
        this.sqlStatement = new StringBuilder();
        this.whereConditions = new WdStringJoiner(SpecialSymbolConstants.SPACE + "AND" + SpecialSymbolConstants.SPACE);
        this.op = Op.SQLEqual;
    }

    /**
     * 在原对象上清空属性,重新赋值
     *
     * @param keySchema SQL语句类型
     * @return
     */
    public BuildSQLTemplate newTemplate(SQLKeySchema keySchema) {

        this.keySchema = keySchema;
        this.clear();
        return this;
    }

    public void clear() {
        this.columns.setLength(0);
        this.values.setLength(0);
        this.sqlStatement.setLength(0);
    }

    /**
     * 动态追加字段名
     *
     * @param str
     * @return
     */
    public BuildSQLTemplate appendColumn(String str) {

        if (this.isOptimInsert) {
            return this;
        }
        this.columns.add(SpecialSymbolConstants.bundleColumn(str));
        return this;
    }

    /**
     * 追加字段名
     *
     * @param columns 字段名集合
     * @return
     */
    public BuildSQLTemplate appendColumn(List<String> columns) {

        if (this.isOptimInsert) {
            return this;
        }
        for (String column : columns) {
            this.columns.add(SpecialSymbolConstants.bundleColumn(column));
        }
        return this;
    }

    /**
     * 追加字段名,如：ID,SUMMARY_PLAN_ID,FREQUENCY,DATETIME拼接的字符串
     *
     * @param columns 特殊符号拼接的字段
     * @param sign    特殊符号
     * @return
     */
    public BuildSQLTemplate appendColumn(String columns, String sign) {

        if (this.isOptimInsert) {
            return this;
        }
        for (String column : columns.split(sign)) {
            this.columns.add(SpecialSymbolConstants.bundleColumn(column));
        }
        return this;
    }

    public BuildSQLTemplate appendValue(Date date) {

        this.values.append(transformDateType(date));
        return this;
    }

    public static String transformDateType(Date date) {
        String temp = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL)) {
            temp = "'" + DateUtils.date2Str(date, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "'";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            temp = "'" + DateUtils.date2Str(date, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "'";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE)
                || DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8)) {
            temp = "to_date('" + DateUtils.date2Str(date, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "','yyyy-mm-dd hh24:mi:ss')";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER)) {
            temp = "'" + DateUtils.date2Str(date, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS) + "'";
        }
        return temp;
    }

    /**
     * 追加字段相应值(insert语句)
     *
     * @param str
     * @return
     */
    public BuildSQLTemplate appendValue(String str) {

        this.values.add(SpecialSymbolConstants.APOSTROPHE)
                   .append(str)
                   .append(SpecialSymbolConstants.APOSTROPHE);
        return this;
    }


    public BuildSQLTemplate appendValue(String column, String value) {

        this.values.add(column)
                   .append(SpecialSymbolConstants.EQUALS)
                   .append(SpecialSymbolConstants.APOSTROPHE)
                   .append(value)
                   .append(SpecialSymbolConstants.APOSTROPHE);
        return this;
    }

    public BuildSQLTemplate appendValue(String column, Date value) {

        this.values.add(column).append(SpecialSymbolConstants.EQUALS);
        this.appendValue(value);
        return this;
    }

    /**
     * 添加where条件
     *
     * @param column 字段
     * @param value  值
     * @return
     */
    public BuildSQLTemplate addCondition(String column, String value) {

        this.whereConditions.add(column)
                            .append(SpecialSymbolConstants.EQUALS)
                            .append(SpecialSymbolConstants.APOSTROPHE)
                            .append(value)
                            .append(SpecialSymbolConstants.APOSTROPHE);
        return this;
    }

    /**
     * 自定义连接符
     *
     * @param column 字段
     * @param op     连接符
     * @param value  值-需要调用者自己拼接('a','b')
     * @return
     */
    public BuildSQLTemplate addCondition(String column, Op op, String value) {

        this.whereConditions.add(column)
                            .append(op.toString())
                            .append(value);
        return this;
    }

    /**
     * 获取 最终可执行的SQL语句
     *
     * @return sqlStatement 最终可执行的SQL语句
     */
    public String getSqlStatement() {
        return this.sqlStatement.toString();
    }

    /**
     * 获取 配置SQL语句类型(CURD)
     *
     * @return keySchema 配置SQL语句类型(CURD)
     */
    public SQLKeySchema getKeySchema() {
        return this.keySchema;
    }

    public BuildSQLTemplate setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public BuildSQLTemplate setOptimInsert(boolean optimInsert) {
        this.isOptimInsert = optimInsert;
        this.values.setLength(0);
        return this;
    }

    private void afert() {
        if (null == this.insertValues) {
            this.insertValues = new StringBuffer();
        }
    }

    /**
     * 根据类型构建SQL语句
     *
     * @return 可执行的SQL语句
     */
    public BuildSQLTemplate build() {

        if (SQLKeySchema.INSERT.equals(this.keySchema)) {
            afert();
            if (this.isOptimInsert) {
                this.sqlStatement.append(",(")
                                 .append(this.values)
                                 .append(")");
                this.values.setLength(0);
            } else {
                this.sqlStatement.setLength(0);
                this.sqlStatement.append("INSERT INTO ")
                                 .append(this.tableName)
                                 .append("(")
                                 .append(this.columns)
                                 .append(") VALUES (")
                                 .append(this.values)
                                 .append(")");
            }
        } else if (SQLKeySchema.UPDATE.equals(this.keySchema)) {
            this.sqlStatement.setLength(0);
            this.sqlStatement.append("UPDATE ")
                             .append(this.tableName)
                             .append(" SET ")
                             .append(this.values);
            if (this.whereConditions.length() > 0) {
                this.sqlStatement.append(" WHERE ")
                                 .append(this.whereConditions);
            }
        } else if (SQLKeySchema.DELETE.equals(this.keySchema)) {
            this.sqlStatement.setLength(0);
            this.sqlStatement.append("DELETE FROM ")
                             .append(this.tableName);
            if (this.whereConditions.length() > 0) {
                this.sqlStatement.append(" WHERE ")
                                 .append(this.whereConditions);
            }
        }
        return this;
    }
}
