package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @Title: ScheduleJobDao
 * @Description: 定时任务操作工厂
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public interface ScheduleJobDao extends JpaRepository<ScheduleJob,String>{

}
