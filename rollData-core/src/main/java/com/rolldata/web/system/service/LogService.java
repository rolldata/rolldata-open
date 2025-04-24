package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysLog;

/**
 * 
 * @Title:LogService
 * @Description:日志服务层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-25
 * @version V1.0
 */
public interface LogService {

	public void save(SysLog sysLog) throws Exception;

}
