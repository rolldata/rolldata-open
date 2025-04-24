package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.SysFunction;
import com.rolldata.web.system.entity.SysFunctionOper;
import com.rolldata.web.system.pojo.FunctionResourcePojo;
import com.rolldata.web.system.pojo.SysMenus;
import com.rolldata.web.system.service.FunctionService;
import com.rolldata.web.system.service.SystemService;
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
import java.util.List;
import java.util.Map;

/**
 * @Title: FunctionController
 * @Description: 功能控制器
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
@Controller
@RequestMapping("/functionController")
@RequiresPathPermission("sys:function")
public class FunctionController {

    private Logger log = LogManager.getLogger(FunctionController.class);

    @Autowired
    private SystemService systemService;
    
    @Autowired
    private FunctionService functionService;
    
    /**
     * 添加页面
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequiresMethodPermissions(value = "funcManage")
    @RequestMapping(value = "/funcManage")
    public ModelAndView addFunction(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/system/funcList");
    }

    
    /**
     * 角色挂菜单时查询的数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryFunctionByRole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryFunctionByRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        Map map = JsonUtil.getMap4Json(data);
        try {
            ajax.setObj(functionService.getRoleHaveMenu(map.get("roleId").toString()));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }
    
    /**
     * 用户登录后拥有的菜单，逐级调用
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserAvailable", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserAvailable(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysFunction sysFunction = JsonUtil.fromJson(data, SysFunction.class);
        try {
        	String parentId="";
        	//默认查询一级目录，如传参有查子级，递归全查
        	if(sysFunction==null || sysFunction.getParentId() == null || sysFunction.getParentId().equals("")) {
        		parentId = SysFunction.ROOT;
        	}else {
        		parentId = sysFunction.getParentId();
        	}
            ajax.setObj(functionService.findHierFuncByUserId(request, parentId,UserUtils.getUser().getId(),SysFunction.NOT_IS_ADMIN));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 查询控制台中内容
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserAvailableConsole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserAvailableConsole(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        try {
            //默认查询一级目录，如传参有查子级，递归全查
            ajax.setObj(functionService.findConsoleFuncByUserId(request,UserUtils.getUser().getId()));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 用户登录后拥有的全部菜单
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserAllAvailable", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserAllAvailable(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(UserUtils.getFunctionList());
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 用户登录后拥有的全部菜单(树形结构)
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserAllAvailableTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserAllAvailableTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        try {
            List<FunctionResourcePojo> sysFunctions = functionService.queryUserAllAvailableTree(request);
            ajax.setObj(sysFunctions);
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 配置权限的菜单树和按钮
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryConfigPowerTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryConfigPowerTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(functionService.findRoleHaveMenu());
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("配置权限的菜单树和按钮失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 配置权限的按钮列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryFuncButton", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryFuncButton(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysFunctionOper sysFunctionOper = JsonUtil.fromJson(data, SysFunctionOper.class);
        try {
            ajax.setObj(functionService.queryFuncButton(sysFunctionOper.getFuncId()));
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            systemService.addErrorLog(e);
            log.error("配置权限的按钮失败：" + e.getMessage(),e);
        }
        return ajax;
    }
    
	 /**
     * 查询目录结构树
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryMenuTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryMenuTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("查询目录结构树");
    	AjaxJson ajax = new AjaxJson();
        try {
            List<SysMenus> sysMenuLists = functionService.queryMenuTree(request);
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(sysMenuLists);
        } catch (Exception e) {
            e.printStackTrace();
			log.error("查询目录结构树操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 目录管理(不带终端控制)
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryMenuManageTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryMenuManageTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询目录结构树");
        AjaxJson ajax = new AjaxJson();
        try {
            List<SysMenus> sysMenuLists = functionService.queryMenuManageTree();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(sysMenuLists);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询目录结构树操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }
    
    /**
     * 查询角色配置目录结构树
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRoleMenuTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryRoleMenuTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询角色配置目录结构树");
        AjaxJson ajax = new AjaxJson();
        try {
            List<SysMenus> sysMenuLists = functionService.queryRoleMenuTree();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(sysMenuLists);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询角色配置目录结构树操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

	 /**
    * 查询单个目录详细
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequestMapping(value = "/queryMenuInfo", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson queryMenuInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
   	log.info("查询单个目录详细");
   	AjaxJson ajax = new AjaxJson();
    String data = request.getParameter("data");
    SysMenus sysMenuReq = JsonUtil.fromJson(data, SysMenus.class);
       try {
           SysMenus sysMenu = functionService.queryMenuInfo(sysMenuReq.getId());
           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
           ajax.setObj(sysMenu);
       } catch (Exception e) {
           e.printStackTrace();
			log.error("查询单个目录详细操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
       }
       return ajax;
   }
   
   /**
    * 保存单个目录
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequiresMethodPermissions(value = "saveMenuInfo")
   @RequestMapping(value = "/saveMenuInfo", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson saveMenuInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

       log.info("保存单个目录");
       AjaxJson ajax = new AjaxJson();
       String data = request.getParameter("data");
       SysMenus sysMenuReq = JsonUtil.fromJson(data, SysMenus.class);
       try {

           //根据名称查询是否重复
           List<SysFunction> nameIsUnique = functionService.queryFunctionObjByName(sysMenuReq);
           if (!nameIsUnique.isEmpty()) {
               ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.name.repeat", (String) sysMenuReq.getName()));
               return ajax;
           }
           SysMenus sysMenu = functionService.saveMenuInfo(sysMenuReq);
           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.save.success"));
           ajax.setObj(sysMenu);
       } catch (Exception e) {
           e.printStackTrace();
           log.error("保存单个目录操作失败：{}", e.getMessage(),e);
           systemService.addErrorLog(e);
           ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
       }
       return ajax;
   }
   
   /**
    * 修改单个目录
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequiresMethodPermissions(value = "updateMenuInfo")
   @RequestMapping(value = "/updateMenuInfo", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson updateMenuInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
   	log.info("更新单个目录详细");
   	AjaxJson ajax = new AjaxJson();
    String data = request.getParameter("data");
    SysMenus sysMenuReq = JsonUtil.fromJson(data, SysMenus.class);
       try {
    	 //根据名称查询是否重复
			List<SysFunction> nameIsUnique = functionService.queryFunctionObjByNameAndNotOwn(sysMenuReq);
			if (nameIsUnique.size()==0) {
	           functionService.updateMenuInfo(sysMenuReq);
	           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
			}else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.name.repeat", (String)sysMenuReq.getName()));
			}
       } catch (Exception e) {
           e.printStackTrace();
			log.error("更新单个目录详细操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
       }
       return ajax;
   }
   
   /**
    * 删除单个目录
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequiresMethodPermissions(value = "deleteMenuInfo")
   @RequestMapping(value = "/deleteMenuInfo", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson deleteMenuInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
   	log.info("删除单个目录");
   	AjaxJson ajax = new AjaxJson();
    String data = request.getParameter("data");
    SysMenus sysMenuReq = JsonUtil.fromJson(data, SysMenus.class);
       try {
           functionService.deleteMenuInfo(sysMenuReq.getId());
           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
       } catch (Exception e) {
           e.printStackTrace();
			log.error("删除单个目录操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
       }
       return ajax;
   }
   
   /**
    * 交换目录顺序
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequestMapping(value = "/exchangeOrder", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson exchangeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
   	log.info("交换目录顺序");
   	AjaxJson ajax = new AjaxJson();
    String data = request.getParameter("data");
    SysMenus sysMenuReq = JsonUtil.fromJson(data, SysMenus.class);
       try {
           functionService.updateExchangeOrder(sysMenuReq);
           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.save.success"));
       } catch (Exception e) {
           e.printStackTrace();
			log.error("交换目录顺序操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
       }
       return ajax;
   }
   
   /**
    * 目录管理(不带终端控制，无权限,资源监控历史用)
    *
    * @param request
    * @param response
    * @return AjaxJson
    * @throws Exception
    */
   @RequestMapping(value = "/queryMenuManageTreeNoPermission", method = RequestMethod.POST)
   @ResponseBody
   public AjaxJson queryMenuManageTreeNoPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
       log.info("查询目录结构树");
       AjaxJson ajax = new AjaxJson();
       try {
           List<SysMenus> sysMenuLists = functionService.queryMenuManageTreeNoPermission();
           ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
           ajax.setObj(sysMenuLists);
       } catch (Exception e) {
           e.printStackTrace();
           log.error("查询目录结构树操作失败：" +e.getMessage(),e);
           systemService.addErrorLog(e);
           ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
       }
       return ajax;
   }

    /**
     * 跳转财务管理和指标管理页获取左侧菜单（排除根目录）
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserAvailableByParam", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserAvailableByParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysFunction sysFunction = JsonUtil.fromJson(data, SysFunction.class);
        try {
            //默认查询一级目录，如传参有查子级，递归全查
            String parentId = SysFunction.ROOT;
            ajax.setObj(functionService.findHierFuncByUserIdAndParam(request, parentId,UserUtils.getUser().getId(),sysFunction.getIsAdmin()));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 用户登录后拥有的菜单，门户用，递归出子目录
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryPortalMenuByUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryPortalMenuByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(functionService.findHierFuncByUserIdToRe(request, SysFunction.ROOT,UserUtils.getUser().getId(),SysFunction.IS_Portal));
            ajax.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：" + e.getMessage(),e);
        }
        return ajax;
    }

    /**
     * 查询勾选业务目录
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBusinessMenu", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryBusinessMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        try {
            List<SysMenus> sysMenus = functionService.queryBusinessMenu(request);
            ajax.setObj(sysMenus);
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            systemService.addErrorLog(e);
            log.error("查询勾选业务目录失败：{}", e.getMessage(),e);
        }
        return ajax;
    }


    /**
     * 用户登录后拥有的菜单，奇迹门户用，递归出子目录
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryMiraclePortalMenus", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryMiraclePortalMenus(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setObj(functionService.queryMiraclePortalMenus(request, SysFunction.ROOT, UserUtils.getUser().getId(), SysFunction.IS_MIRACLE_PORTAL));
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            systemService.addErrorLog(e);
            log.error("查询失败：{}", e.getMessage(),e);
        }
        return ajax;
    }

}
