package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.SysConfigPojo;

/**
 * 系统配置服务层
 * @Title: SysConfigService
 * @Description: 系统配置服务层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年10月31日
 * @version V1.0
 */
public interface SysConfigService {
	
	/**
	 * 查询系统相关配置信息
	 * @return
	 * @throws Exception
	 */
	public SysConfigPojo querySysConfig() throws Exception;
	
	/**
	 * 修改系统相关配置信息
	 * @param sysConfigPojo
	 * @return
	 * @throws Exception
	 */
	public SysConfigPojo updateSysConfig(SysConfigPojo sysConfigPojo) throws Exception;
	
}
