package com.rolldata.core.task.listener;

import com.rolldata.core.util.SpringContextHolder;
import com.rolldata.web.system.service.ScheduleJobService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 
 * @Title: ScheduleJobInitListener
 * @Description: 定时任务监听
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class ScheduleJobInitListener implements ApplicationListener<ContextRefreshedEvent> {

	protected ScheduleJobService scheduleJobService = SpringContextHolder.getApplicationContext()
			.getBean(ScheduleJobService.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
				scheduleJobService.initSchedule();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}