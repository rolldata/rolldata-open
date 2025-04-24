package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.BrowserUtils;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.AppearanceConfigPojo;
import com.rolldata.web.system.pojo.HomePageManageInfo;
import com.rolldata.web.system.pojo.PasswordSecurityPojo;
import com.rolldata.web.system.security.shiro.UserRealm;
import com.rolldata.web.system.service.HomePageService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Scope("prototype")
@Controller
@RequestMapping("/indexController")
public class IndexController{
	private Logger log = LogManager.getLogger(IndexController.class);
	
	@Autowired
	private HomePageService homePageService;

	@Autowired
	private SystemService systemService;

	/**
	 * 主页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = UserUtils.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(MessageUtils.getMessage("common.sys.login.success"));
		System.out.println(MessageUtils.getMessageOrSelf("common.sys.index",(String)sysUser.getUserName()));
        HomePageManageInfo.HomeResource homeResource = homePageService.queryHomepageManageInfoByRoleId(request, AppearanceConfigPojo.PORTALTYPE1);
        String homeType = homeResource.getHomeType();
        map.put("homeType", homeType);
        map.put("resourceType", homeResource.getResourceType());
        map.put("resourceId", homeResource.getResourceId());
		map.put("userName", sysUser.getUserName());
		//增加开关是否开启强制修改初始密码
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
    	if(passwordSecurityMap.getPsdInitIs().equals("1")) {
	        //强制修改密码
	        if ( SysUser.ISINIT.equals(sysUser.getIsInit()) ) {
	        	map.put("title", MessageUtils.getMessage("common.sys.password.init"));
	            return new ModelAndView("/initIndex", map);
	        }
    	}
    	//判断是否开启密码过期验证
    	if(passwordSecurityMap.getPsdPeriodIs().equals("1")) {
    		String period = passwordSecurityMap.getPsdPeriod();
    		Date updatePasswordDate = sysUser.getUpdatePasswordTime();
    		try {
    			//根据上次修改密码时间加上期限与系统当前时间比较
	    		long updatetime = updatePasswordDate.getTime() + Long.parseLong(period) * 24 * 60 * 60 * 1000;
	    		long nowtime = new Date().getTime();
	    		if(nowtime > updatetime) {
	    			map.put("title", MessageUtils.getMessage("common.sys.password.overdue"));
		            return new ModelAndView("/initIndex", map);
	    		}
    		}catch (Exception e) {
    			map.put("title", MessageUtils.getMessage("common.sys.password.overdue"));
	            return new ModelAndView("/initIndex", map);
			}
    	}
        // 解决服务器重启无角色问题
        List<String> roles = UserUtils.getRolesIdList();
        if(roles.size()>0 && !StringUtil.isNotEmpty(roles.get(0))
				&& !StringUtil.isNotEmpty(sysUser.getPosition()))
            return new ModelAndView("redirect:/loginController/logout");
        if(BrowserUtils.isMobile(request)) {
        	return new ModelAndView("/mobileIndex",map);
        }else {
			AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
			map.put("appearanceConfig",appearanceConfigPojo);
			String viewName = "/index";//默认为经典门户（新门户）
			//判断配置的门户类型
			if(StringUtil.isNotEmpty(appearanceConfigPojo.getPortalType()) && appearanceConfigPojo.getPortalType().equals(AppearanceConfigPojo.PORTALTYPE2)){
				//赋值门户页
				viewName = "/portal";
			}
			if(StringUtil.isNotEmpty(appearanceConfigPojo.getPortalType()) && appearanceConfigPojo.getPortalType().equals(AppearanceConfigPojo.PORTALTYPE3)){
				//赋值奇迹门户页
				viewName = "/miraclePortal";
			}
        	return new ModelAndView(viewName,map);
        }
	}

	/**
	 * 临时方法调转门户页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/portal")
	public ModelAndView portal(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/portal");
	}

	/**
	 * 移动端测试页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/mobileIndex")
	public ModelAndView mobileIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobileIndex");
	}
	
	/**
	 * PC测试页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index2")
	public ModelAndView index2(HttpServletRequest request, HttpServletResponse response) {
		SysUser sysUser = UserUtils.getUser();
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(MessageUtils.getMessage("common.sys.login.success"));
		System.out.println(MessageUtils.getMessageOrSelf("common.sys.index",(String)sysUser.getUserName()));
		map.put("userName", sysUser.getUserName());
		return new ModelAndView("/index2",map);
	}
	
	/**
	 * 系统错误页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", true);
		map.put("msg", URLDecoder.decode(URLDecoder.decode(request.getParameter("errorMsg"),"UTF-8"),"UTF-8"));
		return new ModelAndView("/common/error",map);
	}
	
	/**
	 * BI预览
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/biPreview")
	public ModelAndView biPreview(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("biId", request.getParameter("biId"));
		return new ModelAndView("/web/demo/biPreview",map);
	}

	/**
	 * 跳转控制台页面
	 * @param  request
	 * @param  response
	 * @returns ModelAndView
	 */
	@RequestMapping(value = "/consoleIndex")
//	@RequiresMethodPermissions(value = "consoleIndex")
	public ModelAndView toConsoleIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
		map.put("appearanceConfig",appearanceConfigPojo);
		return new ModelAndView("/consoleIndex",map);
	}

	/**
	 * 跳转财务数据管理页面
	 * @param  request
	 * @param  response
	 * @returns ModelAndView
	 */
	@RequestMapping(value = "/financeIndex")
//	@RequiresMethodPermissions(value = "consoleIndex")
	public ModelAndView toFinanceIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/financeIndex");
	}

	/**
	 * 跳转指标管理平台页面
	 * @param  request
	 * @param  response
	 * @returns ModelAndView
	 */
	@RequestMapping(value = "/itemIndex")
//	@RequiresMethodPermissions(value = "consoleIndex")
	public ModelAndView toItemIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/itemIndex");
	}

	/**
	 * 查询门户配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryHomepageConfig")
	public AjaxJson queryHomepageManageInfo(HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		log.info("操作查询门户配置信息");
		AjaxJson ajaxJson = new AjaxJson();
		try {
			AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(appearanceConfigPojo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询门户配置信息操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

}
