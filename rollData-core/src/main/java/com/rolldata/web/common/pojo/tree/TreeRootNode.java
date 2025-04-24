package com.rolldata.web.common.pojo.tree;

import java.util.List;

/** 
 * @Title: TreeRootNode
 * @Description: 树的根节点
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2016-6-21上午11:56:35
 * @version V1.0  
 */
public class TreeRootNode {

	/**
	 * 数据源树根节点
	 */
	private List<ResponseTree> datasources;
	
	/**
	 * 模型树根节点
	 */
	private List<ResponseTree> models;
	
	/**
	 * 资源树根节点
	 */
	private List<ResponseTree> resources;

    /**
     * 数据报表节点
     */
	private List<ResponseTree> datareports;

	/**  
	 * 获取数据源树根节点  
	 * @return datasources 数据源树根节点  
	 */
	public List<ResponseTree> getDatasources() {
		return datasources;
	}

	/**  
	 * 设置数据源树根节点  
	 * @param datasources 数据源树根节点  
	 */
	public void setDatasources(List<ResponseTree> datasources) {
		this.datasources = datasources;
	}

	/**  
	 * 获取模型树根节点  
	 * @return models 模型树根节点  
	 */
	public List<ResponseTree> getModels() {
		return models;
	}

	/**  
	 * 设置模型树根节点  
	 * @param models 模型树根节点  
	 */
	public void setModels(List<ResponseTree> models) {
		this.models = models;
	}

	/**  
	 * 获取报表树根节点  
	 * @return reports 报表树根节点  
	 */
	public List<ResponseTree> getResources() {
		return resources;
	}

	/**  
	 * 设置报表树根节点  
	 * @param reports 报表树根节点  
	 */
	public void setResources(List<ResponseTree> resources) {
		this.resources = resources;
	}

    /**
     * 获取 数据报表节点
     *
     * @return datareports 数据报表节点
     */
    public List<ResponseTree> getDatareports () {
        return this.datareports;
    }

    /**
     * 设置 数据报表节点
     *
     * @param datareports 数据报表节点
     */
    public void setDatareports (List<ResponseTree> datareports) {
        this.datareports = datareports;
    }
}
