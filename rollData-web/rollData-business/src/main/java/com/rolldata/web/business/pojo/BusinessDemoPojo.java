package com.rolldata.web.business.pojo;

import com.rolldata.web.business.entity.BusinessDemoEntity;

import java.util.List;

/**
 * @Title: BusinessDemoPojo
 * @Description: 业务demo对象
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-18
 * @version: V1.0
 */
public class BusinessDemoPojo extends BusinessDemoEntity {

    /**id集合*/
    private List<String> ids;

    /**
     * 获取 id集合
     *
     * @return ids id集合
     */
    public List<String> getIds() {
        return this.ids;
    }

    /**
     * 设置 id集合
     *
     * @param ids id集合
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
