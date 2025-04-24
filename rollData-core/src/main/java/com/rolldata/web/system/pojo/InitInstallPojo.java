package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: InitInstallPojo
 * @Description: 初始化安装交互类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2022-10-19
 * @version: V1.0
 */
public class InitInstallPojo implements Serializable {
    private static final long serialVersionUID = 2531554040525324183L;

    /**数据源配置信息*/
    private DataSourceInfo dataSourceInfo;

    /**注册信息*/
    private FreeLicPojo freeLicPojo;

    /**
     * 获取 数据源配置信息
     *
     * @return dataSourceInfo 数据源配置信息
     */
    public DataSourceInfo getDataSourceInfo() {
        return this.dataSourceInfo;
    }

    /**
     * 设置 数据源配置信息
     *
     * @param dataSourceInfo 数据源配置信息
     */
    public void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
        this.dataSourceInfo = dataSourceInfo;
    }

    /**
     * 获取 注册信息
     *
     * @return freeLicPojo 注册信息
     */
    public FreeLicPojo getFreeLicPojo() {
        return this.freeLicPojo;
    }

    /**
     * 设置 注册信息
     *
     * @param freeLicPojo 注册信息
     */
    public void setFreeLicPojo(FreeLicPojo freeLicPojo) {
        this.freeLicPojo = freeLicPojo;
    }
}
