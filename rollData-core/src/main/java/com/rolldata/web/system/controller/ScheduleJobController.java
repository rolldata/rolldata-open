package com.rolldata.web.system.controller;


import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.MyBeanUtils;
import com.rolldata.core.util.ObjectUtils;
import com.rolldata.web.system.entity.ScheduleJob;
import com.rolldata.web.system.service.ScheduleJobService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title: ScheduleJobController
 * @Description: 任务控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Controller
@RequestMapping("/scheduleJobController")
//@RequiresPathPermission("task:schedulejob")
public class ScheduleJobController {

	@Autowired
	private ScheduleJobService scheduleJobService;

	@RequestMapping(value = "/changeJobStatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson changeJobStatus(ScheduleJob scheduleJob, HttpServletRequest request,
			HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		AjaxJson ajaxJson = new AjaxJson();
		String label = "停止";
		if (cmd.equals("start")) {
			label = "启动";
		} else {
			label = "停止";
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setMsg("任务" + label + "成功");
		try {
			scheduleJobService.changeStatus(scheduleJob.getId(), cmd);
		} catch (Exception e) {
			ajaxJson.setSuccess(false);
			e.printStackTrace();
			ajaxJson.setMsg("任务" + label + "失败" + e.getMessage());
		}
		return ajaxJson;
	}

	@RequestMapping(value = "/updateCron", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateCron(ScheduleJob scheduleJob, HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setMsg("任务更新成功");
		try {
			ajaxJson.setSuccess(true);
			scheduleJobService.updateCron(scheduleJob.getId());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("任务更新失败" + e.getMessage());
		}
		return ajaxJson;
	}
	
	@RequestMapping(value = "/saveScheduleJob", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveScheduleJob(ScheduleJob scheduleJob, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setMsg("保存成功");
		ajaxJson.setSuccess(true);
		if (!CronExpression.isValidExpression(scheduleJob.getCronExpression())) {
			ajaxJson.setMsg("cron表达式格式不对");
			ajaxJson.setSuccess(false);
	    	return ajaxJson;
		}
		try {
			 if (ObjectUtils.isNullOrEmpty(scheduleJob.getId())) {
				 scheduleJobService.save(scheduleJob);
			} else {
				// FORM NULL不更新
				ScheduleJob oldEntity = scheduleJobService.get(scheduleJob.getId());
				MyBeanUtils.copyBeanNotNull2Bean(scheduleJob, oldEntity);
				scheduleJobService.update(oldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("保存失败"+e.getMessage());
		}
		return ajaxJson;
	}
 
}
