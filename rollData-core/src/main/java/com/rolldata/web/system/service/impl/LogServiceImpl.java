package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.dao.LogDao;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志服务层实现类
 * @Title: LogServiceImpl
 * @Description: TODO
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年11月28日
 * @version V1.0
 */
@Service("logService")
@Transactional
public class LogServiceImpl implements LogService{
	
	@Autowired
	private LogDao logDao;

	/**
	 * 保存日志
	 */
	@Override
    public void save(SysLog sysLog) throws Exception {
		logDao.saveAndFlush(sysLog);
	}

}
