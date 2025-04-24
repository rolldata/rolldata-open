package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.service.DevMonitorService;
import com.rolldata.web.system.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: DevMonitorController
 * @Description: 监控控制器
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-02-06
 * @version: V1.0
 */
@Controller
@RequestMapping("/devMonitorController")
@RequiresPathPermission("sys:devMonitor")
public class DevMonitorController {
    private Logger log = LogManager.getLogger(DevMonitorController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private DevMonitorService devMonitorService;

    @RequestMapping(value = "/systemMonitor")
    @RequiresMethodPermissions(value = "systemMonitor")
    public ModelAndView addOrg(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/system/systemMonitor");
    }

    @RequestMapping(value = "/queryServerInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryServerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询服务器监控信息");
        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(devMonitorService.queryServerInfo());
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            log.error("查询失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    @RequestMapping(value = "/queryNetworkInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryNetworkInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询服务器网络情况");
        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(devMonitorService.queryNetworkInfo());
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            log.error("查询失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
}
