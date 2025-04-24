package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: DictDigitRule
 * @Description: 导入基础档案数据定义层级位数规则
 * @Company:
 * @author shenshilong[shilong_shen@163.com]
 * @date 2019-05-20
 * @version V1.0
 */
public class DictDigitRule implements Serializable {
    
    private static final long serialVersionUID = -8002786231285262526L;
    
    /**
     * 层级最小数
     */
    private Integer min;
    
    /**
     * 层级最大数
     */
    private Integer max;
    
    /**
     * 位数
     */
    private Integer digit;
    
    /**
     * 递增变化的代码
     */
    private String cde;
    
    /**
     * 本级档案代码
     */
    private String thisCode;
    
    
    /**
     * 获取 层级最小数
     *
     * @return min 层级最小数
     */
    public Integer getMin () {
        return this.min;
    }
    
    /**
     * 设置 层级最小数
     *
     * @param min 层级最小数
     */
    public void setMin (Integer min) {
        this.min = min;
    }
    
    /**
     * 获取 层级最大数
     *
     * @return max 层级最大数
     */
    public Integer getMax () {
        return this.max;
    }
    
    /**
     * 设置 层级最大数
     *
     * @param max 层级最大数
     */
    public void setMax (Integer max) {
        this.max = max;
    }
    
    /**
     * 获取 位数
     *
     * @return digit 位数
     */
    public Integer getDigit () {
        return this.digit;
    }
    
    /**
     * 设置 位数
     *
     * @param digit 位数
     */
    public void setDigit (Integer digit) {
        this.digit = digit;
    }
    
    
    /**
     * 获取 递增变化的代码
     *
     * @return cde 递增变化的代码
     */
    public String getCde () {
        return this.cde;
    }
    
    /**
     * 设置 递增变化的代码
     *
     * @param cde 递增变化的代码
     */
    public void setCde (String cde) {
        this.cde = cde;
    }
    
    /**
     * 获取 本级档案代码
     *
     * @return thisCode 本级档案代码
     */
    public String getThisCode () {
        return this.thisCode;
    }
    
    /**
     * 设置 本级档案代码
     *
     * @param thisCode 本级档案代码
     */
    public void setThisCode (String thisCode) {
        this.thisCode = thisCode;
    }
}
