package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: ProductModelPojo
 * @Description: 产品安装的模型交互类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-11-05
 * @version: V1.0
 */
public class ProductModelPojo implements Serializable {

    private static final long serialVersionUID = 7254557687842497494L;

    /**模型id*/
    private String modelId;

    /**模型文件名*/
    private String modelFile;

    /**库名*/
    private String dbName;

    /*创建时间*/
    private Date createTime;

    /**模型是否存在，true存在，false不存在*/
    private boolean exist;

    /**安装的模型使用期限，开始时间*/
    private String startTime;

    /**安装的模型使用期限，结束时间*/
    private String endTime;

    /**
     * 获取 模型id
     *
     * @return modelId 模型id
     */
    public String getModelId() {
        return this.modelId;
    }

    /**
     * 设置 模型id
     *
     * @param modelId 模型id
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * 获取 模型文件名
     *
     * @return modelFile 模型文件名
     */
    public String getModelFile() {
        return this.modelFile;
    }

    /**
     * 设置 模型文件名
     *
     * @param modelFile 模型文件名
     */
    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    /**
     * 获取 创建时间
     *
     * @return createTime 创建时间
     */
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

    /**
     * 获取 模型是否存在，true存在，false不存在
     *
     * @return exist 模型是否存在，true存在，false不存在
     */
    public boolean isExist() {
        return this.exist;
    }

    /**
     * 设置 模型是否存在，true存在，false不存在
     *
     * @param exist 模型是否存在，true存在，false不存在
     */
    public void setExist(boolean exist) {
        this.exist = exist;
    }

    /**
     * 获取 库名
     *
     * @return dbName 库名
     */
    public String getDbName() {
        return this.dbName;
    }

    /**
     * 设置 库名
     *
     * @param dbName 库名
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 获取 安装的模型使用期限，开始时间
     *
     * @return startTime 安装的模型使用期限，开始时间
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 设置 安装的模型使用期限，开始时间
     *
     * @param startTime 安装的模型使用期限，开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 安装的模型使用期限，结束时间
     *
     * @return endTime 安装的模型使用期限，结束时间
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 安装的模型使用期限，结束时间
     *
     * @param endTime 安装的模型使用期限，结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
