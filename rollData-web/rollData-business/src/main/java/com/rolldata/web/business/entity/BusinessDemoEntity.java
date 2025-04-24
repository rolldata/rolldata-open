package com.rolldata.web.business.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: businessDemoEntity
 * @Description: 业务示例实体
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-18
 * @version: V1.0
 */
@Entity
@Table(name = "wd_business_demo")
@DynamicUpdate(true)
@DynamicInsert(true)
public class BusinessDemoEntity extends IdEntity implements java.io.Serializable{

    /**编码*/
    private String demoCode;

    /**名称*/
    private String demoName;

    /*状态，0否（停用）1是（启用）*/
    private String state;

    /*创建时间*/
    private Date createTime;

    /**
     * 获取 编码
     *
     * @return demoCode 编码
     */
    @Column(name = "demo_code", length = 50)
    public String getDemoCode() {
        return this.demoCode;
    }

    /**
     * 设置 编码
     *
     * @param demoCode 编码
     */
    public void setDemoCode(String demoCode) {
        this.demoCode = demoCode;
    }

    /**
     * 获取 名称
     *
     * @return demoName 名称
     */
    @Column(name = "demo_name", length = 100)
    public String getDemoName() {
        return this.demoName;
    }

    /**
     * 设置 名称
     *
     * @param demoName 名称
     */
    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    /**
     * 获取 状态，0否（停用）1是（启用）
     *
     * @return state 状态，0否（停用）1是（启用）
     */
    @Column(name = "c_state", length = 2)
    public String getState() {
        return this.state;
    }

    /**
     * 设置 状态，0否（停用）1是（启用）
     *
     * @param state 状态，0否（停用）1是（启用）
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取 创建时间
     *
     * @return createTime 创建时间
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
