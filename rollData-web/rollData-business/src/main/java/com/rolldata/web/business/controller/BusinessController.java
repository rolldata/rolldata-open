package com.rolldata.web.business.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.business.entity.BusinessDemoEntity;
import com.rolldata.web.business.pojo.BusinessDemoPojo;
import com.rolldata.web.business.service.BusinessService;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Title: BusinessController
 * @Description: 业务demo控制器
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-16
 * @version: V1.0
 */
@Controller
@RequestMapping("/businessController")
@RequiresPathPermission("sys:business")
public class BusinessController {
    private Logger log = LogManager.getLogger(BusinessController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private BusinessService businessService;

    /**
     * 跳转demo页面
     * @param request
     * @param response
     * @return
     */
    @RequiresMethodPermissions(value = "demoManage")
    @RequestMapping(value = "/demoManage")
    public ModelAndView demoManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/business/demoList");
    }

    /**
     * 查询demo列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryBusinessList")
    @RequiresMethodPermissions(value = "query")
    public AjaxJson queryBusinessList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("操作查询demo列表");
        AjaxJson ajaxJson = new AjaxJson();
        String jsonStr = request.getParameter("data");
        try {
            PageJson pageJson = JsonUtil.fromJson(jsonStr, PageJson.class);
            pageJson = businessService.queryBusinessList(pageJson);
            ajaxJson.setObj(pageJson);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询demo列表操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }

    /**
     * 修改demo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateBusinessState")
    @RequiresMethodPermissions(value = "updateBusinessState")
    public AjaxJson updateBusinessState(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("操作修改demo");
        AjaxJson ajaxJson = new AjaxJson();
        String jsonStr = request.getParameter("data");
        try {
            BusinessDemoPojo businessDemoPojo = JsonUtil.fromJson(jsonStr, BusinessDemoPojo.class);
            businessService.updateBusinessState(businessDemoPojo);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改demo操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
        }
        return ajaxJson;
    }
}
