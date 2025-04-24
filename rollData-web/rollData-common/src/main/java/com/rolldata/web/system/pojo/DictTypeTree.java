package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.entity.WdSysDictLevel;

import java.util.List;

/**
 * 基础档案目录树节点信息
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class DictTypeTree extends TreeNode {

    private static final long serialVersionUID = -3649201086316057002L;
    
    private String dictTypeId;
    
    private String dictTypeName;
    
    private String dictTypeCde;
    
    private String showType;
    
    /**
     * 属性数量
     */
    private String propertyCount;
    
    /**
     * 属性名称
     */
    private String[] propertyName;

    /**
     * 挂载层级信息
     */
    private List<WdSysDictLevel> levelTable;


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
     * 属性字段
     */
    private String[] propertyColumns;

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public String getDictTypeCde() {
        return dictTypeCde;
    }

    public void setDictTypeCde(String dictTypeCde) {
        this.dictTypeCde = dictTypeCde;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }
    
    /**
     * 获取 属性数量
     *
     * @return propertyCount 属性数量  
     */
    public String getPropertyCount () {
        return this.propertyCount;
    }
    
    /**
     * 设置 属性数量
     *
     * @param propertyCount 属性数量  
     */
    public void setPropertyCount (String propertyCount) {
        this.propertyCount = propertyCount;
    }
    
    /**
     * 获取 属性名称
     *
     * @return propertyName 属性名称  
     */
    public String[] getPropertyName () {
        return this.propertyName;
    }
    
    /**
     * 设置 属性名称
     *
     * @param propertyName 属性名称  
     */
    public void setPropertyName (String[] propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 获取 挂载层级信息
     *
     * @return levelTable 挂载层级信息
     */
    public List<WdSysDictLevel> getLevelTable () {
        return this.levelTable;
    }

    /**
     * 设置 挂载层级信息
     *
     * @param levelTable 挂载层级信息
     */
    public void setLevelTable (List<WdSysDictLevel> levelTable) {
        this.levelTable = levelTable;
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
     * 获取 属性字段
     *
     * @return propertyColumns 属性字段
     */
    public String[] getPropertyColumns() {
        return this.propertyColumns;
    }

    /**
     * 设置 属性字段
     *
     * @param propertyColumns 属性字段
     */
    public void setPropertyColumns(String[] propertyColumns) {
        this.propertyColumns = propertyColumns;
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
}
