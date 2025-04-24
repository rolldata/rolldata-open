package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysPostMenu;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: SysPostAuthorize
 * @Description: 职务授权
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-12-02
 * @version: V1.0
 */
public class SysPostAuthorize implements Serializable {

    private static final long serialVersionUID = 4006843822165813000L;

    /**
     * 菜单功能权限集合
     */
    private List<String> funcIds;

    /**
     * 选择的按钮id集合
     */
    private List<String> buttonIds;

    /**
     * 分析权限的id集合
     */
    private List<SysPostMenu> menuIds;
    
    /**
     * 模型文件夹id集合
     */
    private List<String> modelFolderIds;

    /**
     * 模型id集合
     */
    private List<String> modelIds;

    /**
     * 数据源文件夹id集合
     */
    private List<String> dataSourceFolderIds;

    /**
     * 数据源id集合
     */
    private List<String> dataSourceIds;

    /**
     * 获取 菜单功能权限集合
     *
     * @return funcIds 菜单功能权限集合
     */
    public List<String> getFuncIds() {
        return this.funcIds;
    }

    /**
     * 设置 菜单功能权限集合
     *
     * @param funcIds 菜单功能权限集合
     */
    public void setFuncIds(List<String> funcIds) {
        this.funcIds = funcIds;
    }

    /**
     * 获取 分析权限的id集合
     *
     * @return menuIds 分析权限的id集合
     */
    public List<SysPostMenu> getMenuIds() {
        return this.menuIds;
    }

    /**
     * 设置 分析权限的id集合
     *
     * @param menuIds 分析权限的id集合
     */
    public void setMenuIds(List<SysPostMenu> menuIds) {
        this.menuIds = menuIds;
    }

    /**
     * 获取 选择的按钮id集合
     *
     * @return buttonIds 选择的按钮id集合
     */
    public List<String> getButtonIds() {
        return this.buttonIds;
    }

    /**
     * 设置 选择的按钮id集合
     *
     * @param buttonIds 选择的按钮id集合
     */
    public void setButtonIds(List<String> buttonIds) {
        this.buttonIds = buttonIds;
    }

    /**
     * 获取 模型文件夹id集合
     *
     * @return modelFolderIds 模型文件夹id集合
     */
    public List<String> getModelFolderIds() {
        return this.modelFolderIds;
    }

    /**
     * 设置 模型文件夹id集合
     *
     * @param modelFolderIds 模型文件夹id集合
     */
    public void setModelFolderIds(List<String> modelFolderIds) {
        this.modelFolderIds = modelFolderIds;
    }

    /**
     * 获取 模型id集合
     *
     * @return modelIds 模型id集合
     */
    public List<String> getModelIds() {
        return this.modelIds;
    }

    /**
     * 设置 模型id集合
     *
     * @param modelIds 模型id集合
     */
    public void setModelIds(List<String> modelIds) {
        this.modelIds = modelIds;
    }

    /**
     * 获取 数据源文件夹id集合
     *
     * @return dataSourceFolderIds 数据源文件夹id集合
     */
    public List<String> getDataSourceFolderIds() {
        return this.dataSourceFolderIds;
    }

    /**
     * 设置 数据源文件夹id集合
     *
     * @param dataSourceFolderIds 数据源文件夹id集合
     */
    public void setDataSourceFolderIds(List<String> dataSourceFolderIds) {
        this.dataSourceFolderIds = dataSourceFolderIds;
    }

    /**
     * 获取 数据源id集合
     *
     * @return dataSourceIds 数据源id集合
     */
    public List<String> getDataSourceIds() {
        return this.dataSourceIds;
    }

    /**
     * 设置 数据源id集合
     *
     * @param dataSourceIds 数据源id集合
     */
    public void setDataSourceIds(List<String> dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

}
