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
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.pojo.EmailCodePojo;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.service.PasswordBackService;
import com.rolldata.web.system.service.SystemService;

/**
 * 
 * @Title: PasswordBackController
 * @Description: 密码找回控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月17日
 * @version V1.0
 */
@Controller
@RequestMapping("/passwordBackController")
public class PasswordBackController {
	
	private Logger log = LogManager.getLogger(PasswordBackController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private PasswordBackService passwordBackService;
	
	/**
	 * 跳转密码找回页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/passwordBack")
    public ModelAndView announcementManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/web/system/passwordBack");
    }
	
	/**
	 * 发送邮箱验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/sendEmailVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson sendEmailVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("操作发送邮箱验证码");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        EmailCodePojo emailCodePojo = JsonUtil.fromJson(data, EmailCodePojo.class);
        try {
        	PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
        	if(passwordSecurityMap.getPsdRequest().equals("1")) {//开启过找回密码功能的再放过
        		passwordBackService.sendEmailVerifyCode(emailCodePojo);
        		ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.email.send.success"));
        	}else {
        		 ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.send.noverify"));
        	}
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.send.error")+":"+e.getMessage());
            log.error("发送邮箱验证码操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
    /**
     * 验证邮箱验证码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/verifyEmailCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson verifyEmailCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("操作验证邮箱验证码");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        EmailCodePojo emailCodePojo = JsonUtil.fromJson(data, EmailCodePojo.class);
        try {
        	PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
        	if(passwordSecurityMap.getPsdRequest().equals("1")) {//开启过找回密码功能的再放过
        		passwordBackService.verifyEmailCode(emailCodePojo);
        		ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.email.verify.success"));
        	}else {
        		 ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.send.noverify"));
        	}
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.verify.error")+":"+e.getMessage());
            log.error("验证邮箱验证码操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
    /**
     * 验证邮箱重置密码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("操作验证邮箱重置密码");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        EmailCodePojo emailCodePojo = JsonUtil.fromJson(data, EmailCodePojo.class);
        try {
        	PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
        	if(passwordSecurityMap.getPsdRequest().equals("1")) {//开启过找回密码功能的再放过
        		passwordBackService.updateResetPassword(emailCodePojo);
        		ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.password.reset.success"));
        	}else {
        		 ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.email.send.noverify"));
        	}
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.password.reset.error")+":"+e.getMessage());
            log.error("验证邮箱重置密码操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
}
