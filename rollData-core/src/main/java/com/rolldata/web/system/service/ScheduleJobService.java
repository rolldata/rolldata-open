package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.ScheduleJob;
import org.quartz.SchedulerException;

/**
 * 
 * @Title: ScheduleJobService
 * @Description: 任务操作接口类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public interface ScheduleJobService {
	/**
	 * 
	 * @title: initSchedule
	 * @description: 初始化任务
	 * @throws SchedulerException
	 * @return: void
	 */
	public void initSchedule() throws Exception;

	/**
	 * 更改状态
	 * 
	 * @throws SchedulerException
	 */
	public void changeStatus(String jobId, String cmd) throws SchedulerException;

	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	public void updateCron(String jobId) throws SchedulerException;

	public void save(ScheduleJob scheduleJob) throws SchedulerException;

	public void update(ScheduleJob oldEntity);

	public ScheduleJob get(String id);
	
	
}
