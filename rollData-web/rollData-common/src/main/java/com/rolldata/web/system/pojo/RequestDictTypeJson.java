package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.WdSysDictLevel;
import java.io.Serializable;
import java.util.List;

/**
 * 基础档案请求包含字段
 * @author shenshilong
 * @createDate 2018-7-30
 */

public class RequestDictTypeJson implements Serializable{
    
    private static final long serialVersionUID = -6912546676875745854L;

    private String dictTypeId;
    
    private String pId;
    
    private String dictTypeCde;
    
    private String dictTypeName;
    
    private List<String> ids;

    /**
     * 编码层级
     */
    private String codeLevel;

    /**
     * 层级对应的表格
     */
    private List<WdSysDictLevel> levelTable;

    /**
     * 档案属性个数
     */
    private String propertyCount;

    /**
     * 属性个数对应的属性名称
     */
    private List<String> propertyTable;


    /**
     * 字典类型:0基础档案1数据档案
     */
    private String cType;

    /**
     * 构建类型:0依赖父节点构建1依赖长度构建
     */
    private String relyType;

    /**
     * 库表
     */
    private String tableName;

    /**
     * 显示值字段
     */
    private String showValue;

    /**
     * 实际值字段
     */
    private String realValue;

    /**
     * 父节点字段
     */
    private String parentValue;

    /**
     * 属性字段集合
     */
    private List<String> propertyColumns;

    /**
     * 过滤条件配置
     */
    private ModelFilterConf filterConf;

    /**
     * 任务详细id
     */
    private String taskDetailId;

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictTypeCde() {
        return dictTypeCde;
    }

    public void setDictTypeCde(String dictTypeCde) {
        this.dictTypeCde = dictTypeCde;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * 获取
     *
     * @return pId
     */
    public String getPId () {
        return this.pId;
    }

    /**
     * 设置
     *
     * @param pId
     */
    public void setPId (String pId) {
        this.pId = pId;
    }

    /**
     * 获取 编码层级
     *
     * @return codeLevel 编码层级
     */
    public String getCodeLevel () {
        return this.codeLevel;
    }

    /**
     * 设置 编码层级
     *
     * @param codeLevel 编码层级
     */
    public void setCodeLevel (String codeLevel) {
        this.codeLevel = codeLevel;
    }

    /**
     * 获取 层级对应的表格
     *
     * @return levelTable 层级对应的表格
     */
    public List<WdSysDictLevel> getLevelTable () {
        return this.levelTable;
    }

    /**
     * 设置 层级对应的表格
     *
     * @param levelTable 层级对应的表格
     */
    public void setLevelTable (List<WdSysDictLevel> levelTable) {
        this.levelTable = levelTable;
    }

    /**
     * 获取 档案属性个数
     *
     * @return propertyCount 档案属性个数
     */
    public String getPropertyCount () {
        return this.propertyCount;
    }

    /**
     * 设置 档案属性个数
     *
     * @param propertyCount 档案属性个数
     */
    public void setPropertyCount (String propertyCount) {
        this.propertyCount = propertyCount;
    }

    /**
     * 获取 属性个数对应的属性名称
     *
     * @return propertyTable 属性个数对应的属性名称
     */
    public List<String> getPropertyTable () {
        return this.propertyTable;
    }

    /**
     * 设置 属性个数对应的属性名称
     *
     * @param propertyTable 属性个数对应的属性名称
     */
    public void setPropertyTable (List<String> propertyTable) {
        this.propertyTable = propertyTable;
    }


    /**
     * 获取 字典类型:0基础档案1数据档案
     *
     * @return cType 字典类型:0基础档案1数据档案
     */
    public String getCType() {
        return this.cType;
    }

    /**
     * 设置 字典类型:0基础档案1数据档案
     *
     * @param cType 字典类型:0基础档案1数据档案
     */
    public void setCType(String cType) {
        this.cType = cType;
    }

    /**
     * 获取 构建类型:0依赖父节点构建1依赖长度构建
     *
     * @return relyType 构建类型:0依赖父节点构建1依赖长度构建
     */
    public String getRelyType() {
        return this.relyType;
    }

    /**
     * 设置 构建类型:0依赖父节点构建1依赖长度构建
     *
     * @param relyType 构建类型:0依赖父节点构建1依赖长度构建
     */
    public void setRelyType(String relyType) {
        this.relyType = relyType;
    }

    /**
     * 获取 库表
     *
     * @return tableName 库表
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * 设置 库表
     *
     * @param tableName 库表
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取 显示值字段
     *
     * @return showValue 显示值字段
     */
    public String getShowValue() {
        return this.showValue;
    }

    /**
     * 设置 显示值字段
     *
     * @param showValue 显示值字段
     */
    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    /**
     * 获取 实际值字段
     *
     * @return realValue 实际值字段
     */
    public String getRealValue() {
        return this.realValue;
    }

    /**
     * 设置 实际值字段
     *
     * @param realValue 实际值字段
     */
    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    /**
     * 获取 父节点字段
     *
     * @return parentValue 父节点字段
     */
    public String getParentValue() {
        return this.parentValue;
    }

    /**
     * 设置 父节点字段
     *
     * @param parentValue 父节点字段
     */
    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    /**
     * 获取 属性字段集合
     *
     * @return propertyColumns 属性字段集合
     */
    public List<String> getPropertyColumns() {
        return this.propertyColumns;
    }

    /**
     * 设置 属性字段集合
     *
     * @param propertyColumns 属性字段集合
     */
    public void setPropertyColumns(List<String> propertyColumns) {
        this.propertyColumns = propertyColumns;
    }

    /**
     * 获取 过滤条件配置
     *
     * @return filterConf 过滤条件配置
     */
    public ModelFilterConf getFilterConf() {
        return this.filterConf;
    }

    /**
     * 设置 过滤条件配置
     *
     * @param filterConf 过滤条件配置
     */
    public void setFilterConf(ModelFilterConf filterConf) {
        this.filterConf = filterConf;
    }

    /**
     * 获取 任务详细id
     *
     * @return taskDetailId 任务详细id
     */
    public String getTaskDetailId() {
        return this.taskDetailId;
    }

    /**
     * 设置 任务详细id
     *
     * @param taskDetailId 任务详细id
     */
    public void setTaskDetailId(String taskDetailId) {
        this.taskDetailId = taskDetailId;
    }
}
