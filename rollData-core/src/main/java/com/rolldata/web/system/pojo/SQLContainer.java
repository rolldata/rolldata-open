package com.rolldata.web.system.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL预编译信息
 *
 * @Title: SQLContainer
 * @Description: SQLContainer
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-02-24
 * @version: V1.0
 */
public class SQLContainer {

    /**
     * 执行SQL
     */
    private String sql;

    /**
     * 预编译信息
     */
    private List<String> precompiledValues;

    private List<Object> objectValues;

    public SQLContainer() {
        this.precompiledValues = new ArrayList<>();
        this.objectValues = new ArrayList<>();
    }

    public void addPrecompiledValues(String value) {
        this.precompiledValues.add(value);
    }

    public void addPrecompiledValues(List<String> values) {
        this.precompiledValues.addAll(values);
    }

    public void addObjectValues(List<Object> values) {
        this.objectValues.addAll(values);
    }

    /**
     * 获取 执行SQL
     *
     * @return sql 执行SQL
     */
    public String getSql() {
        return this.sql;
    }

    /**
     * 设置 执行SQL
     *
     * @param sql 执行SQL
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获取 预编译信息
     *
     * @return precompiledValues 预编译信息
     */
    public List<String> getPrecompiledValues() {
        return this.precompiledValues;
    }

    /**
     * 设置 预编译信息
     *
     * @param precompiledValues 预编译信息
     */
    public void setPrecompiledValues(List<String> precompiledValues) {
        this.precompiledValues = precompiledValues;
    }

    public List<Object> getObjectValues() {
        return objectValues;
    }

    public SQLContainer setObjectValues(List<Object> objectValues) {
        this.objectValues = objectValues;
        return this;
    }
}
