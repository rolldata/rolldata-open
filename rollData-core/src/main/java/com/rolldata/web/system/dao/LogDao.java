package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @Title: LogDao
 * @Description: 日志操作工厂
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public interface LogDao  extends JpaRepository<SysLog,String>{

}
