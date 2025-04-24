package com.rolldata.web.system.controller;


import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Locale;

/**
 * 
 * @Title: LoginController
 * @Description: 登陆初始化控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController{
	private Logger log = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private LocaleContextResolver localeResolver;

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.toString();
		model.addAttribute("language", language);
		String kickout = request.getParameter("kickout");
		String blocked = request.getParameter("blocked");
		if (kickout!=null) {
			model.addAttribute("error", MessageUtils.getMessageOrSelf("common.sys.login.killout"));
		}
		if (blocked!=null) {
			model.addAttribute("error", MessageUtils.getMessageOrSelf("common.sys.login.locked"));
		}
		// 我的电脑有缓存问题
		Principal principal = UserUtils.getPrincipal(); // 如果已经登录，则跳转到管理首页
		if (principal != null ) {
			return new ModelAndView("redirect:/indexController/index");
		}
		if(StringUtil.isNotEmpty(request.getParameter("errorMsg"))) {
			model.addAttribute("error", URLDecoder.decode(URLDecoder.decode(request.getParameter("errorMsg"),"UTF-8"),"UTF-8"));
		}
		// 是否开启验证码
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		model.addAttribute("showCaptcha", passwordSecurityMap.getPsdValidateIs());
		model.addAttribute("psdRequest", passwordSecurityMap.getPsdRequest());
		//默认使用用户名密码登陆
		model.addAttribute("loginType", LoginType.TYPE_PASSWORD);
			AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
			model.addAttribute("appearanceConfig",appearanceConfigPojo);
			//验证错误返回用户名
			model.addAttribute("userName", request.getAttribute("userName"));
			return new ModelAndView("login/login");
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.toString();
		model.addAttribute("language", language);
		// 是否开启验证码
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		model.addAttribute("showCaptcha", passwordSecurityMap.getPsdValidateIs());
		model.addAttribute("psdRequest", passwordSecurityMap.getPsdRequest());
		//默认使用用户名密码登陆
		model.addAttribute("loginType", LoginType.TYPE_PASSWORD);
		AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
		model.addAttribute("appearanceConfig",appearanceConfigPojo);
			return new ModelAndView("login/login");
	}
	/**
	 * 语言切换
	 */
	@RequestMapping(value = "/language")
	public ModelAndView language(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String language = (String) request.getParameter("language");
		model.addAttribute("language", language);
		PasswordSecurityPojo passwordSecurityMap = (PasswordSecurityPojo) Constants.property.get(Constants.passwordSecurity);
		model.addAttribute("showCaptcha", passwordSecurityMap.getPsdValidateIs());
		model.addAttribute("psdRequest", passwordSecurityMap.getPsdRequest());
		AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
		model.addAttribute("appearanceConfig",appearanceConfigPojo);
		if (language == null || language.equals("")) {
		        return new ModelAndView("login/login");
		} else {
			if (language.equals(LanguageType.zh_CN)){
			    localeResolver.setLocale(request, response, Locale.CHINA);
			} else if (language.equals(LanguageType.en_US)) {
			    localeResolver.setLocale(request, response, Locale.US);
			} else {
			    localeResolver.setLocale(request, response, Locale.CHINA);
			}
		}
		return new ModelAndView("login/login");
	}

}