package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: TableName
 * @Description: 数据库系统表字段对象
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-11-19
 * @version: V1.0
 */
public class SysTableInfo implements Serializable {

    private static final long serialVersionUID = -7809336571579170125L;

    /**
     * 字段名称-因为oracle as别名查出来是大写
     */
    private String COLUMNNAME;

    /**
     * 字段
     */
    private String COLUMN;

    /**
     * 表名
     */
    private String TABLENAME;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 插入语句预编译的位置
     */
    private int parameterIndex;

    /**
     * 字段长度
     */
    private int length;

    /**
     * 字段是否可空
     * <br>0 (columnNoNulls) - 该列不允许为空
     * <br>1 (columnNullable) - 该列允许为空
     * <br>2 (columnNullableUnknown) - 不确定该列是否为空
     */
    private int isNullAble;

    /**
     * 是否是主键
     */
    private boolean primaryKey;

    /**
     * 精度
     */
    private int precision;

    public SysTableInfo() {
    }

    public SysTableInfo(String COLUMNNAME, String COLUMN) {
        this.COLUMNNAME = COLUMNNAME;
        this.COLUMN = COLUMN;
        this.TABLENAME = "";
    }

    /**
     * 获取 字段名称
     *
     * @return columnName 字段名称
     */
    public String getCOLUMNNAME() {
        return this.COLUMNNAME;
    }

    /**
     * 设置 字段名称
     *
     * @param COLUMNNAME 字段名称
     */
    public void setCOLUMNNAME(String COLUMNNAME) {
        this.COLUMNNAME = COLUMNNAME;
    }

    /**
     * 获取 表名
     *
     * @return tableName 表名
     */
    public String getTABLENAME() {
        return this.TABLENAME.toUpperCase();
    }

    /**
     * 设置 表名
     *
     * @param TABLENAME 表名
     */
    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    /**
     * 获取 字段
     *
     * @return COLUMN 字段
     */
    public String getCOLUMN() {
        return this.COLUMN;
    }

    /**
     * 设置 字段
     *
     * @param COLUMN 字段
     */
    public void setCOLUMN(String COLUMN) {
        this.COLUMN = COLUMN;
    }

    /**
     * 获取 字段类型
     *
     * @return type 字段类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 字段类型
     *
     * @param type 字段类型
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * 获取 插入语句预编译的位置
     *
     * @return parameterIndex 插入语句预编译的位置
     */
    public int getParameterIndex() {
        return this.parameterIndex;
    }

    /**
     * 设置 插入语句预编译的位置
     *
     * @param parameterIndex 插入语句预编译的位置
     */
    public void setParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    /**
     * 获取 字段长度
     *
     * @return length 字段长度
     */
    public int getLength() {
        return this.length;
    }

    /**
     * 设置 字段长度
     *
     * @param length 字段长度
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 获取 字段是否可空      <br>0 (columnNoNulls) - 该列不允许为空      <br>1 (columnNullable) - 该列允许为空      <br>2
     * (columnNullableUnknown) - 不确定该列是否为空
     *
     * @return isNullAble 字段是否可空      <br>0 (columnNoNulls) - 该列不允许为空      <br>1 (columnNullable) - 该列允许为空      <br>2
     * (columnNullableUnknown) - 不确定该列是否为空
     */
    public int getIsNullAble() {
        return this.isNullAble;
    }

    /**
     * 设置 字段是否可空      <br>0 (columnNoNulls) - 该列不允许为空      <br>1 (columnNullable) - 该列允许为空      <br>2
     * (columnNullableUnknown) - 不确定该列是否为空
     *
     * @param isNullAble 字段是否可空      <br>0 (columnNoNulls) - 该列不允许为空      <br>1 (columnNullable) - 该列允许为空      <br>2
     *                   (columnNullableUnknown) - 不确定该列是否为空
     */
    public void setIsNullAble(int isNullAble) {
        this.isNullAble = isNullAble;
    }

    /**
     * 获取 是否是主键
     *
     * @return primaryKey 是否是主键
     */
    public boolean isPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * 设置 是否是主键
     *
     * @param primaryKey 是否是主键
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * 获取 精度
     *
     * @return precision 精度
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     * 设置 精度
     *
     * @param precision 精度
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "SysTableInfo{" +
            "COLUMNNAME='" + COLUMNNAME + '\'' +
            ", COLUMN='" + COLUMN + '\'' +
            ", TABLENAME='" + TABLENAME + '\'' +
            ", type='" + type + '\'' +
            ", parameterIndex=" + parameterIndex +
            ", length=" + length +
            ", isNullAble=" + isNullAble +
            ", primaryKey=" + primaryKey +
            ", precision=" + precision +
            '}';
    }
}
