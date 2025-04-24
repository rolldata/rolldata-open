package com.rolldata.web.system.service.impl;

import com.rolldata.core.task.QuartzManager;
import com.rolldata.core.task.util.ScheduleJobUtils;
import com.rolldata.core.util.SysPropertiesUtil;
import com.rolldata.web.system.dao.ScheduleJobDao;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.ScheduleJob;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.pojo.SysConfigPojo;
import com.rolldata.web.system.service.ScheduleJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @Title: ScheduleJobServiceImpl
 * @Description: 任务操作接口实现类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Service("scheduleJobService")
@Transactional
public class ScheduleJobServiceImpl implements ScheduleJobService {
	private QuartzManager quartzManager;
	
	@Autowired
	private ScheduleJobDao scheduleJobDao;

	@Autowired
	private SysConfigDao sysConfigDao;

	@Override
	public void updateCron(String jobId) throws SchedulerException {
		ScheduleJob scheduleJobEntity = scheduleJobDao.getOne(jobId);
		if (scheduleJobEntity == null) {
			return;
		}
		if (com.rolldata.core.task.pojo.ScheduleJob.STATUS_RUNNING.equals(scheduleJobEntity.getJobStatus())) {
			quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJobEntity));
		}
		scheduleJobDao.saveAndFlush(scheduleJobEntity);
	}

	@Override
	public void changeStatus(String jobId, String cmd) throws SchedulerException {
		ScheduleJob scheduleJobEntity = scheduleJobDao.getOne(jobId);
		if (scheduleJobEntity == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJobEntity));
			scheduleJobEntity.setJobStatus(com.rolldata.core.task.pojo.ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			scheduleJobEntity.setJobStatus(com.rolldata.core.task.pojo.ScheduleJob.STATUS_RUNNING);
			quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJobEntity));
		}
		scheduleJobDao.saveAndFlush(scheduleJobEntity);
	}

	public void delete(ScheduleJob entity) {
		try {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(entity));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		scheduleJobDao.delete(entity);
	}

	public void deleteById(String id) {
		delete(scheduleJobDao.getOne(id));
	}

	@Override
	public void initSchedule() throws Exception {
		// 这里获取任务信息数据
		quartzManager = new QuartzManager();
		List<SysConfig> sysConfigList = sysConfigDao.querySysConfigByType(SysConfig.TYPE_SYSTEM);
		SysConfigPojo sysConfigPojo = new SysConfigPojo();
		for (int i = 0; i < sysConfigList.size(); i++) {
			SysConfig sysConfig = sysConfigList.get(i);
			String value = sysConfig.getValue();
			switch (sysConfig.getName()) {
				case SysConfig.SYSTEM_CRON_FILLTASK:
					sysConfigPojo.setFillTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_DPTASK:
					sysConfigPojo.setDpTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_WARNTASK:
					sysConfigPojo.setWarnTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_SENDMESTASK:
					sysConfigPojo.setSendMesTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_SYNACCOUNTDATA:
					sysConfigPojo.setSynAccountDataCron(value);
					break;
				case SysConfig.SYSTEM_CRON_THIRDPARTYINTERFACEDATA:
					sysConfigPojo.setThirdpartyInterfaceDataCron(value);
					break;
				case SysConfig.SYSTEM_CRON_CALCULATEITEMDISPATCH:
					sysConfigPojo.setCalculateItemDispatchCron(value);
					break;
				default:
					break;
			}
		}
		//最后再增加表里任务
		List<ScheduleJob> jobList = scheduleJobDao.findAll();
		for (ScheduleJob scheduleJobEntity : jobList) {
			if(scheduleJobEntity.getJobStatus().equals("1")) {//启用的再添加
				quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJobEntity));
			}
		}
	}

	@Override
	public void save(ScheduleJob scheduleJob) throws SchedulerException {
		scheduleJobDao.saveAndFlush(scheduleJob);
	}

	@Override
	public void update(ScheduleJob oldEntity) {
		scheduleJobDao.saveAndFlush(oldEntity);
	}

	@Override
	public ScheduleJob get(String id) {
		return scheduleJobDao.getOne(id);
	}

}
