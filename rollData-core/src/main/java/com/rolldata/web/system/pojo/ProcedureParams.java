package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: ProcedureParams
 * @Description: ProcedureParams
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-01-28
 * @version: V1.0
 */
public class ProcedureParams implements Serializable {

    private static final long serialVersionUID = 8724002227334132020L;

    /**参数名称*/
    private String parameterName;

    /**参数的数据类型*/
    private String dataType;

    /**参数的方向*/
    private String parameterMode;

    /**参数值*/
    private String parameterValue;

    /**条件类型 0参数,1字符串,2公式*/
    private String type;

    /**
     * 公式体
     */
    private String formula;

    public ProcedureParams() {
    }

    public ProcedureParams(String parameterName, String dataType, String parameterMode) {
        this.parameterName = parameterName;
        this.dataType = dataType;
        this.parameterMode = parameterMode;
    }

    /**
     * 获取 参数名称
     *
     * @return parameterName 参数名称
     */
    public String getParameterName() {
        return this.parameterName;
    }

    /**
     * 设置 参数名称
     *
     * @param parameterName 参数名称
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * 获取 参数的数据类型
     *
     * @return dataType 参数的数据类型
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     * 设置 参数的数据类型
     *
     * @param dataType 参数的数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取 参数的方向
     *
     * @return parameterMode 参数的方向
     */
    public String getParameterMode() {
        return this.parameterMode;
    }

    /**
     * 设置 参数的方向
     *
     * @param parameterMode 参数的方向
     */
    public void setParameterMode(String parameterMode) {
        this.parameterMode = parameterMode;
    }

    /**
     * 获取 参数值
     *
     * @return parameterValue 参数值
     */
    public String getParameterValue() {
        return this.parameterValue;
    }

    /**
     * 设置 参数值
     *
     * @param parameterValue 参数值
     */
    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    /**
     * 获取 条件类型 0参数1字符串2公式
     *
     * @return type 条件类型 0参数1字符串2公式
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 条件类型 0参数1字符串2公式
     *
     * @param type 条件类型 0参数1字符串2公式
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 公式体
     *
     * @return formula 公式体
     */
    public String getFormula() {
        return this.formula;
    }

    /**
     * 设置 公式体
     *
     * @param formula 公式体
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }
}
