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
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.pojo.SysConfigPojo;
import com.rolldata.web.system.service.SysConfigService;
import com.rolldata.web.system.service.SystemService;

/**
 * 系统配置控制器
 * @Title: SysConfigController
 * @Description: 系统配置控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年10月31日
 * @version V1.0
 */
@Controller
@RequestMapping("/sysConfigController")
@RequiresPathPermission("sys:sysConfig")
public class SysConfigController {
	private Logger log = LogManager.getLogger(SysConfigController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 查询系统配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/querySysConfig")
	public AjaxJson querySysConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询密码安全信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			SysConfigPojo sysConfigPojo = sysConfigService.querySysConfig();
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(sysConfigPojo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询密码安全信息操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
	
	/**
	 * 修改系统配置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "updateSysConfig")
    @RequestMapping(value = "/updateSysConfig", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateSysConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysConfigPojo sysConfigPojo = JsonUtil.fromJson(data, SysConfigPojo.class);
        try {
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
            ajax.setObj((SysConfigPojo)sysConfigService.updateSysConfig(sysConfigPojo));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
}
