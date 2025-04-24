package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.AppearanceConfigPojo;

/**
 * 
 * @Title:SystemService
 * @Description:系统服务层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-25
 * @version V1.0
 */
public interface SystemService{

	/**
	 * 日志添加
	 * @param logTitle
	 * @param logContent
	 * @param logLevel
	 * @throws Exception
	 */
	public void addLog(String logTitle, String logContent, Short logLevel) throws Exception;
	
	/**
	 * 添加异常日志
	 */
	public void addErrorLog(Throwable ex) throws Exception;
	
	/**
	 * 查询系统配置信息
	 * @throws Exception
	 */
	public void querySysConfigInfo() throws Exception;

	/**
	 * 查询外观配置详细
	 * @return
	 * @throws Exception
	 */
	public AppearanceConfigPojo queryAppearanceConfig() throws Exception;

    /**
     * 更新 jsp 引入文件版本
     *
     * @param value
     * @throws Exception
     */
	public void updateJspVersion(String value) throws Exception;

}
