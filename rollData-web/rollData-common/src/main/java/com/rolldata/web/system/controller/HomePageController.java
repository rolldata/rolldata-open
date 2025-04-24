package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.pojo.AppearanceConfigPojo;
import com.rolldata.web.system.pojo.BiHomePagePojo;
import com.rolldata.web.system.pojo.HomePageManageInfo;
import com.rolldata.web.system.service.HomePageService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @Title: HomePageController
 * @Description: 首页
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/12
 * @version V1.0  
 */
@Controller
@RequestMapping("/homePageController")
@RequiresPathPermission("sys:homepageManage")
public class HomePageController {
	
	private Logger log = LogManager.getLogger(HomePageController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private HomePageService homePageService;
	
	@RequestMapping(value = "/setUpHomepageManage")
    @RequiresMethodPermissions(value = "setUpHomepageManage")
    public ModelAndView setUpHomepageManage(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
        return new ModelAndView("web/homepage/homePageManage",map);
    }

    /**
     * 首页
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/homePage")
    public ModelAndView homePage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String homeType = "1";
        HomePageManageInfo.HomeResource homeResource = null;

        // 超级管理员默认DS首页
        if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

            homeResource = homePageService.queryHomepageManageInfoByRoleId(request, AppearanceConfigPojo.PORTALTYPE1);
            homeType = homeResource.getHomeType();
        }

        if("1".equals(homeType)) {
    		return new ModelAndView("/web/homepage/homePage_static");
    	}else if("2".equals(homeType)) {
    		return new ModelAndView("/web/homepage/homePage_BI");
    	}else if("3".equals(homeType)) {
    		 return new ModelAndView("/web/homepage/homePage");
    	}else if("4".equals(homeType)) {
    		return new ModelAndView("/web/homepage/homePage_DS");
    	}else if("6".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage_system");
        }else {
    		//默认改为DS
    		return new ModelAndView("/web/homepage/homePage_DS");
    	}
    }

    /**
     * 移动端首页
     *
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/mobileHomePage")
    public ModelAndView mobileHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String homeType = "1";
        HomePageManageInfo.HomeResource homeResource = null;

        // 超级管理员默认DS首页
        if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

            homeResource = homePageService.queryHomepageManageInfoByRoleId(request, AppearanceConfigPojo.PORTALTYPE1);
            homeType = homeResource.getHomeType();
        }

        if("1".equals(homeType)) {
            return new ModelAndView("/web/mobile/homepage/static");
        }else {
            return new ModelAndView("/web/mobile/homepage/static");
        }
    }

	/**
	 * 管理控制台主页
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/consoleHomePage")
	public ModelAndView consoleHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("/web/homepage/consoleHomePage");
	}
    
    /**
     * note
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/note")
    public ModelAndView note(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/tpl/note");
    }
    
    /**
     * password
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/password")
    public ModelAndView password(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/tpl/password");
    }
    
    /**
     * theme
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/theme")
    public ModelAndView theme(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/tpl/theme");
    }
    
    /**
	 * 查询首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryHomepageManageInfo")
	public AjaxJson queryHomepageManageInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询PC首页配置信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			HomePageManageInfo homePageManageInfo = homePageService.queryHomepageManageInfo("1");
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(homePageManageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询PC首页配置信息操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

    /**
     * 查询终端首页配置信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryTerminalHomepageManageInfo")
    public AjaxJson queryTerminalHomepageManageInfo(HttpServletRequest request,
        HttpServletResponse response) throws Exception{

        log.info("操作查询终端首页配置信息");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            HomePageManageInfo homePageManageInfo = homePageService.queryHomepageManageInfo("3");
            ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(homePageManageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询终端首页配置信息操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
	
	/**
	 * 保存首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "saveHomepageInfo")
    @RequestMapping(value = "/saveHomepageInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveHomepageInfo(@RequestParam("loginLogo")CommonsMultipartFile loginLogoImgFile,@RequestParam("logo")CommonsMultipartFile logoImgFile,
                                     @RequestParam("loginBackground")CommonsMultipartFile loginBackgroundImgFile,HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("保存首页配置信息");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        HomePageManageInfo homePageManageInfo = JsonUtil.fromJson(data, HomePageManageInfo.class);
        try {
            HomePageManageInfo returnObj = homePageService.saveHomepageInfo(loginLogoImgFile,logoImgFile,loginBackgroundImgFile,homePageManageInfo);
            ajax.setObj(returnObj);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            log.error("保存首页配置信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 保存终端首页信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "saveTerminalHomepageInfo")
    @RequestMapping(value = "/saveTerminalHomepageInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveTerminalHomepageInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        this.log.info("保存终端首页配置信息");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        HomePageManageInfo homePageManageInfo = JsonUtil.fromJson(data, HomePageManageInfo.class);
        try {
            this.homePageService.saveTerminalHomepageInfo(homePageManageInfo);
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            this.log.error("保存终端首页配置信息操作失败：{}", e.getMessage());
            this.systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	/**
	 * 保存BI首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "saveBIHomepageInfo")
    @RequestMapping(value = "/saveBIHomepageInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveBIHomepageInfo(@RequestParam("uploadImgFile")CommonsMultipartFile uploadImgFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("保存BI首页配置信息");
		AjaxJson ajax = new AjaxJson();
        try {
        	String resourceId = request.getParameter("resourceId");
        	String name = request.getParameter("biName");
        	BiHomePagePojo biHomePagePojo = new BiHomePagePojo();  
        	biHomePagePojo.setName(name);
        	biHomePagePojo.setResourceId(resourceId);
        	homePageService.saveBIHomepageInfo(uploadImgFile,biHomePagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error")+":"+e.getMessage());
            log.error("保存BI首页配置信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
    /**
	 * 查询BI首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryBIHomepageList")
	public AjaxJson queryBIHomepageList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询BI首页配置信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			List<BiHomePagePojo> returnList = homePageService.queryBIHomepageList();
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(returnList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询BI首页配置信息操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
	
	
	/**
	 * 批量删除首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "deleteBIHomepageInfo")
    @RequestMapping(value = "/deleteBIHomepageInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson deleteBIHomepageInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("批量删除首页配置信息");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        BiHomePagePojo biHomePagePojo = JsonUtil.fromJson(data, BiHomePagePojo.class);
        try {
        	homePageService.deleteBIHomepageInfo(biHomePagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
            log.error("批量删除首页配置信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	/**
	 * 修改BI首页配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "updateBIHomepageInfo")
    @RequestMapping(value = "/updateBIHomepageInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateBIHomepageInfo(@RequestParam("uploadImgFile")CommonsMultipartFile uploadImgFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("修改BI首页配置信息");
		AjaxJson ajax = new AjaxJson();
        try {
        	String resourceId = request.getParameter("resourceId");
        	String name = request.getParameter("biName");
        	String id = request.getParameter("biId");
        	BiHomePagePojo biHomePagePojo = new BiHomePagePojo();  
        	biHomePagePojo.setId(id);
        	biHomePagePojo.setName(name);
        	biHomePagePojo.setResourceId(resourceId);
        	biHomePagePojo.setIsReUpload(request.getParameter("isReUpload"));
        	homePageService.modifyBIHomepageInfo(uploadImgFile,biHomePagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error")+":"+e.getMessage());
            log.error("修改BI首页配置信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
    /**
	 * BI首页配置交换顺序
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/exchangeOrder", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson exchangeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("BI首页配置交换顺序");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        BiHomePagePojo biHomePagePojo = JsonUtil.fromJson(data, BiHomePagePojo.class);
        try {
        	homePageService.updateExchangeOrder(biHomePagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            log.error("BI首页配置交换顺序操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 门户主页
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/main")
    public ModelAndView portalMain(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String homeType = "7";//新门户默认类型，与其他先区分开
        String portalType = AppearanceConfigPojo.PORTALTYPE2;
        return portalPage(request, homeType, portalType);
    }


    /**
     * 奇迹主页
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping(value = "/miracle")
    public ModelAndView miracle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String homeType = "8";
        String portalType = AppearanceConfigPojo.PORTALTYPE3;
        return portalPage(request, homeType, portalType);
    }

    private ModelAndView portalPage(HttpServletRequest request, String homeType, String portalType) throws Exception {

        HomePageManageInfo.HomeResource homeResource = null;
        // 超级管理员默认DS首页
        if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

            homeResource = homePageService.queryHomepageManageInfoByRoleId(request, portalType);
            homeType = homeResource.getHomeType();
        }
        if ("1".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage_static");
        } else if ("2".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage_BI");
        } else if ("3".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage");
        } else if ("4".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage_DS");
        }  else if ("6".equals(homeType)) {
            return new ModelAndView("/web/homepage/homePage_system");
        } else if ("8".equals(homeType)) {

            //奇迹门户默认主页
            return new ModelAndView("/portal/miracle");
        } else {

            //新门户默认主页
            return new ModelAndView("/portal/main");
        }
    }
}
