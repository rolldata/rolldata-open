package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * SQL条件过滤配置
 *
 * @Title: ModelFilterConf
 * @Description: ModelFilterConf
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-02-25
 * @version: V1.0
 */
public class ModelFilterConf implements Serializable {

    private static final long serialVersionUID = -758070972306628687L;

    /**
     * 条件
     */
    private List<ConditionsBean> conditions;

    /**
     * 排序
     */
    private List<ConditionsBean> orders;

    /**
     * 预览限制100条
     */
    private boolean limit;

    /**
     * 获取 条件
     *
     * @return conditions 条件
     */
    public List<ConditionsBean> getConditions() {
        return null == this.conditions ? Collections.emptyList() : this.conditions;
    }

    /**
     * 设置 条件
     *
     * @param conditions 条件
     */
    public void setConditions(List<ConditionsBean> conditions) {
        this.conditions = conditions;
    }

    /**
     * 获取 排序
     *
     * @return orders 排序
     */
    public List<ConditionsBean> getOrders() {
        return null == this.orders ? Collections.emptyList() : this.orders;
    }

    /**
     * 设置 排序
     *
     * @param orders 排序
     */
    public void setOrders(List<ConditionsBean> orders) {
        this.orders = orders;
    }

    /**
     * 获取 预览限制100条
     *
     * @return limit 预览限制100条
     */
    public boolean isLimit() {
        return this.limit;
    }

    /**
     * 设置 预览限制100条
     *
     * @param limit 预览限制100条
     */
    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "ModelFilterConf{" +
            "conditions=" + conditions +
            ", orders=" + orders +
            ", limit=" + limit +
            '}';
    }
}
