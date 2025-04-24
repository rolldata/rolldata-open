package com.rolldata.web.system.controller;

import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.SysRole;
import com.rolldata.web.system.pojo.FunctionOneToMany;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.pojo.SysRoleJson;
import com.rolldata.web.system.service.RoleService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.EntitySqlUtils;
import com.rolldata.web.system.util.UserUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: RoleController
 * @Description: 角色控制器
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
@Controller
@RequestMapping("/roleController")
@RequiresPathPermission("sys:role")
public class RoleController {
    
    private Logger log = LogManager.getLogger(RoleController.class);
    
    @Autowired
    private SystemService systemService;
    
    @Autowired
    private RoleService roleService;

    /**
     * 角色页面跳转
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/roleList")
    @RequiresMethodPermissions(value = "roleList")
    public ModelAndView roleList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		// 初始化,放到其他地方感觉都不合适
        EntitySqlUtils.initEntityAttr();
        return new ModelAndView("web/system/roleList",map);
    }

    /**
     * 添加、删除、修改
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysRoleJson sysRoleJson = JsonUtil.fromJson(data, SysRoleJson.class);
        try {
            if (EventType.CREATE_MD.toString().equals(sysRoleJson.getEvent())) {
                roleService.before(ajax, sysRoleJson.getSettings().getRoleName(), sysRoleJson.getSettings().getRoleCde(), sysRoleJson.getSettings().getRoleId());
                if (ajax.isSuccess()) {
                    ajax.setObj(roleService.save(sysRoleJson.getSettings()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 修改角色
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysRoleJson sysRoleJson = JsonUtil.fromJson(data, SysRoleJson.class);
        try {
            if (AdministratorUtils.getAdminRoleCde().equals(sysRoleJson.getSettings().getRoleCde())) {
                ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.admin.role.prohibit"));
            } else {
                if (EventType.UPDATE_MD.toString().equals(sysRoleJson.getEvent())) {
                    roleService.before(ajax, sysRoleJson.getSettings().getRoleName(), sysRoleJson.getSettings().getRoleCde(), sysRoleJson.getSettings().getRoleId());
                    if (ajax.isSuccess()) {
                        long startTime = System.currentTimeMillis();
                        ajax.setObj(roleService.update(sysRoleJson.getSettings()));
                        System.out.println("----shenshilong--update----" + (System.currentTimeMillis() - startTime) + " ms.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "del")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson del(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysRoleJson sysRoleJson = JsonUtil.fromJson(data, SysRoleJson.class);
        List<SysRole> sysRoles = new ArrayList<SysRole>();
        try {
            ajax.setSuccess(true);
            if (EventType.DELETE_MD.toString().equals(sysRoleJson.getEvent())) {
                if (AdministratorUtils.getAdminRoleCde().equals(sysRoleJson.getSettings().getRoleCde())) {
                    ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.admin.role.prohibit"));
                } else {
                    for (String rId : sysRoleJson.getSettings().getIds()) {
    
                        SysRole sysRole = roleService.queryById(rId);
                        if (AdministratorUtils.getAdminRoleCde().equals(sysRole.getRoleCde())) {
                            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.admin.role.prohibit"));
                            return ajax;
                        }
                        sysRoles.add(sysRole);
                    }
                    roleService.delete(sysRoles);
//                    roleService.del(sysRoleJson.getSettings().getIds());
                    ajax.setMsg(MessageUtils.getMessage("common.sys.delete.success"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 全部角色
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        try {
            
            ajax.setObj(roleService.queryAllRoleList());
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 用户配置角色的角色列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryConfigList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryConfigList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        try {
            
            ajax.setObj(roleService.queryConfigList());
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }


    /**
     * 角色详细
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRoleDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryRoleDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        Map map = JsonUtil.getMap4Json(data);
        try {
            
            ajax.setObj(roleService.queryRoleDetailedInfo(map.get("roleId").toString()));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }


    /**
     * 查询可用的角色
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAvailable", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryAvailable(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        PageJson pageJson = JsonUtil.fromJson(data, PageJson.class);
        try {
            roleService.queryAvailable(pageJson);
            ajax.setObj(pageJson);
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 改变状态
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson changeState(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysRole role = JsonUtil.fromJson(data, SysRole.class);
        
        try {
            roleService.changeState(role);
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 保存配置的菜单和按钮
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveFunctionRolePower", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveFunctionRolePower(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        FunctionOneToMany functionOneToMany = JsonUtil.fromJson(data, FunctionOneToMany.class);
        try {
            roleService.saveFunctionRolePower(functionOneToMany);
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 查登录用户的角色
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findListByUserId", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson findListByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        AjaxJson ajax = new AjaxJson();
        try {
            List<SysRole> sysRoleList = roleService.findListByUserId(UserUtils.getUser().getId());
            ajax.setObj(sysRoleList);
            ajax.setSuccess(true);
        } catch (Exception e) {
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
}
