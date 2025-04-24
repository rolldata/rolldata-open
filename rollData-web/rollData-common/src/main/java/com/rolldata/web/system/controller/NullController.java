package com.rolldata.web.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.DateUtils;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;

/**
 * 
 * @Title: NullController
 * @Description: 空的控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年6月25日
 * @version V1.0
 */
@Controller
@RequestMapping("/nullController")
public class NullController {
private Logger log = LogManager.getLogger(NullController.class);
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 空方法，用于长时间停留的界面，定时调用，保持会话不超时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/run", method = RequestMethod.POST)
	public AjaxJson run(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作空方法");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String str = "用户："+UserUtils.getUser().getUserCde()+" 在线操作，当前时间："+DateUtils.getDate(DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
			System.out.println(str);
			log.info(str);
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("空方法操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
}
