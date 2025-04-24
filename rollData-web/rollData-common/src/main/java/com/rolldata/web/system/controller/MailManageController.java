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
import org.springframework.web.servlet.ModelAndView;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.pojo.MailManagePojo;
import com.rolldata.web.system.service.MailManageService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.email.EmailResult;

/**
 * 
 * @Title: MailManageController
 * @Description: 邮箱设置控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年4月1日
 * @version V1.0
 */
@Controller
@RequestMapping("/mailManageController")
@RequiresPathPermission("sys:mailManage")
public class MailManageController {
	private Logger log = LogManager.getLogger(MailManageController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MailManageService mailManageService;
	
	/**
	 * 跳转邮箱管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/setUpMailManage")
    @RequiresMethodPermissions(value = "setUpMailManage")
    public ModelAndView setUpMailManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/system/mailManage");
    }
	
	/**
	 * 查询邮箱设置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryMailManageInfo")
	public AjaxJson queryMailManageInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询邮箱设置信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			MailManagePojo mailManagePojo = mailManageService.queryMailManageInfo();
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(mailManagePojo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询邮箱设置信息操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
	
	/**
	 * 修改邮箱设置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "updateMailManage")
    @RequestMapping(value = "/updateMailManage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateMailManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("操作修改邮箱设置信息");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        try {
        	MailManagePojo mailManagePojo = JsonUtil.fromJson(data, MailManagePojo.class);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
            ajax.setObj((MailManagePojo)mailManageService.updateMailManage(mailManagePojo));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
            log.error("操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	/**
	 * 发送测试邮件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "testMail")
    @RequestMapping(value = "/testMail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson testMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("操作发送测试邮件");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        try {
        	MailManagePojo mailManagePojo = JsonUtil.fromJson(data, MailManagePojo.class);
        	EmailResult emailResult=mailManageService.testMail(mailManagePojo);
        	if(emailResult.isSuccess()) {
        		ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.email.send.test.success"));
        	}else {
        		ajax.setSuccessAndMsg(false,MessageUtils.getMessage("common.email.send.test.error"));
			}
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.send.test.error"));
            log.error("操作发送测试邮件失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
}
