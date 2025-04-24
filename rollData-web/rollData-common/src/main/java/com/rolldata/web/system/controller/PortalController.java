package com.rolldata.web.system.controller;

import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.web.system.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: PortalController
 * @Description: 门户相关控制器
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2022-11-04
 * @version: V1.0
 */
@Controller
@RequestMapping("/portalController")
@RequiresPathPermission("sys:portal")
public class PortalController {

    private Logger log = LogManager.getLogger(PortalController.class);

    @Autowired
    private SystemService systemService;

    /**
     * 跳转看板中心
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/biCenter")
    @RequiresMethodPermissions(value = "biCenter")
    public ModelAndView biCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId =  request.getParameter("menuId");
        String menuName =  request.getParameter("menuName");
        Map<String, String> map = new HashMap<String, String>();
        map.put("menuId", menuId);
        map.put("menuName", menuName);
        return new ModelAndView("portal/system/biCenter",map);
    }

    /**
     * 跳转报表中心
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/rpCenter")
    @RequiresMethodPermissions(value = "rpCenter")
    public ModelAndView rpCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId =  request.getParameter("menuId");
        String menuName =  request.getParameter("menuName");
        Map<String, String> map = new HashMap<String, String>();
        map.put("menuId", menuId);
        map.put("menuName", menuName);
        return new ModelAndView("portal/system/rpCenter",map);
    }

    /**
     * 跳转数据维护列表页
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/toDataMaintain")
    @RequiresMethodPermissions(value = "toDataMaintain")
    public ModelAndView toDataMaintain(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("portal/report/dataMaintain");
    }

    /**
     * 门户跳转个人中心页
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/personalInformation")
    public ModelAndView personalInformation(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/portal/system/personalInformation");
    }
}
