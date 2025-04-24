package com.rolldata.web.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;

import com.rolldata.web.system.pojo.UserDetailedJson;
import com.rolldata.web.system.service.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.service.PasswordSecurityService;
import com.rolldata.web.system.service.SystemService;

/**
 * 密码安全控制层
 * @Title: PasswordSecurityController
 * @Description: 密码安全控制层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
@Controller
@RequestMapping("/passwordSecurityController")
@RequiresPathPermission("sys:passwordSecurity")
public class PasswordSecurityController {
	
	private Logger log = LogManager.getLogger(PasswordSecurityController.class);
			
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private PasswordSecurityService passwordSecurityService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/setUpPasswordSecurity")
    @RequiresMethodPermissions(value = "setUpPasswordSecurity")
    public ModelAndView setUpPasswordSecurity(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/system/passwordSecurity");
    }
	
	/**
	 * 查询密码安全信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryPasswordSecurity")
	public AjaxJson queryPasswordSecurity(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询密码安全信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			PasswordSecurityPojo passwordPojo = passwordSecurityService.queryPasswordSecurity();
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(passwordPojo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询密码安全信息操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
	
	/**
	 * 修改密码安全
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "updatePasswordSecurity")
    @RequestMapping(value = "/updatePasswordSecurity", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updatePasswordSecurity(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        PasswordSecurityPojo passwoPojo = JsonUtil.fromJson(data, PasswordSecurityPojo.class);
        try {
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
            ajax.setObj((PasswordSecurityPojo)passwordSecurityService.updatePasswordSecurity(passwoPojo));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
            log.error("操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }

	/**
	 * 管理员重置用户的访问资源密码，用户id集合
	 *
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetIsBrowsePassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson resetIsBrowsePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("重置用户的访问资源密码");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		try {
			UserDetailedJson rUserJson = JsonUtil.fromJson(data, UserDetailedJson.class);
			userService.updateResetIsBrowsePassword(rUserJson);
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("重置用户的访问资源密码操作失败：" + e.getMessage());
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}
}
