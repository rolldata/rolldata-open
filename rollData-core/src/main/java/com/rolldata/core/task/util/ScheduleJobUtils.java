package com.rolldata.core.task.util;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.entity.ScheduleJob;

/**
 * 
 * @Title: ScheduleJobUtils
 * @Description: 定时任务工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class ScheduleJobUtils {

	public static com.rolldata.core.task.pojo.ScheduleJob entityToData(ScheduleJob scheduleJobEntity) {
		com.rolldata.core.task.pojo.ScheduleJob scheduleJob = new com.rolldata.core.task.pojo.ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		return scheduleJob;
	}

}
