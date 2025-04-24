package com.rolldata.web.system.controller;

import com.rolldata.core.util.*;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.security.web.UsernamePasswordToken;
import com.rolldata.web.system.service.PasswordSecurityService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.service.UserService;
import com.rolldata.web.system.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 
 * @Title: ThirdPartyController
 * @Description: 第三方登陆控制器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月21日
 * @version V1.0
 */
@Controller
@RequestMapping("/thirdPartyController")
public class ThirdPartyController {

	private Logger log = LogManager.getLogger(ThirdPartyController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordSecurityService passwordSecurityService;

    /**
     * 用户账号单点登录
     * <p>只要用户名和系统userCde匹配上,即可登录
     * <p>权限有点大,在配置文件中加个开关
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userSinglePoint")
    public ModelAndView userSinglePoint(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        this.log.info("用户账号单点登录操作");
        Map<String, String> msgMap = new HashMap<>();

        // 单点登录标记 默认0失败
        model.addAttribute("singlePointLogin", "0");
        msgMap.put("singlePointLogin", "0");
        try {
            String userCode = request.getParameter("userCode");
            this.log.info("获取用户账号单点登录userCode:" + userCode);

            // 判断是否开启
            if (!AdministratorUtils.getUserSinglePoint()) {
                throw new Exception("配置未开启");
            }
            if (StringUtil.isNotEmpty(userCode)) {
                this.log.info("执行用户账号单点登录");
                //其他用户直接匹配登录名，遇到管理员的，匹配第三方编码
				SysUser sysUser = null;
				if(AdministratorUtils.getAdministratorName().equals(userCode)){
					sysUser = userService.queryUserByThirdPartyCode(userCode);
				} else {
					sysUser = userService.getUserByUserCde(userCode);
				}
                if (StringUtil.isNotEmpty(sysUser)) {
                    Subject subject = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(
                        sysUser.getUserCde(),
                        true,
                        false,
                        LoginType.TYPE_WECHAT
                    );
                    subject.login(token);
                    LogUtil.info(token.getUsername() + "登录成功（用户账号单点）");
                    LogUtils.saveLog(
                        ContextHolderUtils.getRequest(),
                        "登录成功",
                        token.getUsername() + "登录成功（用户账号单点登录）",
                        SysLog.LEVEL2
                    );
                    model.addAttribute("success", "用户账号单点登录成功");
                    msgMap.put("success", "用户账号单点登录成功");

                    // 单点登录标记 1成功
                    model.addAttribute("singlePointLogin", "1");
                    msgMap.put("singlePointLogin", "1");

                    // 重定向想要页面接收,还需在indexController里接收并传递
                    return new ModelAndView("redirect:/indexController/index", msgMap);
                } else {
                    model.addAttribute(
                        "errorMsg",
                        URLEncoder.encode(URLEncoder.encode("用户账号单点登录失败：用户不存在，请联系系统管理员", "UTF-8"), "UTF-8")
                    );
                    msgMap.put(
                       "errorMsg",
                       URLEncoder.encode(URLEncoder.encode("用户账号单点登录失败：用户不存在，请联系系统管理员", "UTF-8"), "UTF-8")
                    );
                }
            } else {

                // 用户禁止授权
                throw new Exception("参数异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.log.error("执行用户账号单点登录操作：" + e.getMessage(), e);
            this.systemService.addErrorLog(e);
            model.addAttribute(
                "errorMsg",
                URLEncoder.encode(URLEncoder.encode("单点登录失败：" + e.getMessage(), "UTF-8"), "UTF-8")
            );
            msgMap.put(
                "errorMsg",
                URLEncoder.encode(URLEncoder.encode("单点登录失败：" + e.getMessage(), "UTF-8"), "UTF-8")
            );
        }
        return new ModelAndView("redirect:/loginController/login", msgMap);
    }

	/**
	 * 来自第三方的单点登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ssoByThird")
	public ModelAndView ssoByThird(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		log.info("执行第三方登陆PC操作");
		try {
			String ssokey = request.getParameter("ssokey");
			if (StringUtil.isNotEmpty(ssokey)) {
				log.info("执行第三方单点PC登陆ssokey：" + ssokey);
				//解密获取用户名和时间戳,按照约定的密码
				String decrypt = DESUtil.decrypt(SysPropertiesUtil.getConfig("thirdparty.decrypt.password"),ssokey);
				log.info("执行第三方单点PC登陆解密后：" + decrypt);
				String[] decryptStr = decrypt.split(",");
				if(decryptStr.length==2) {
					String thirdPartyCode = decryptStr[0];
					String timeStr = decryptStr[1];
					long nowDateTime = System.currentTimeMillis();
					long updatetime = Long.parseLong(timeStr) + 5 * 60 * 1000;
					if(nowDateTime > updatetime) {
						model.addAttribute("errorMsg", URLEncoder.encode(
								URLEncoder.encode("第三方单点PC登陆失败：已超时", "UTF-8"), "UTF-8"));
					}else {
						log.info("执行第三方单点PC登陆thirdPartyCode：" + thirdPartyCode);
						SysUser sysUser = userService.queryUserByThirdPartyCode(thirdPartyCode);
						if (StringUtil.isNotEmpty(sysUser)) {
							Subject subject = SecurityUtils.getSubject();
							UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUserCde(), true, false,
									LoginType.TYPE_WECHAT);
							subject.login(token);
							LogUtil.info(token.getUsername() + "登录成功PC（第三方）");
							LogUtils.saveLog(ContextHolderUtils.getRequest(), "登录成功", token.getUsername() + "登录成功（第三方）", SysLog.LEVEL2);

							Map<String, String> map = new HashMap<String, String>();
							map.put("userName", sysUser.getUserName());
							model.addAttribute("success", "第三方单点PC登陆成功");
							return new ModelAndView("redirect:/indexController/index");
						} else {
							model.addAttribute("errorMsg", URLEncoder.encode(
									URLEncoder.encode("第三方单点PC登陆失败：用户不存在，请联系系统管理员", "UTF-8"), "UTF-8"));
						}
					}
				}else {
					// 用户禁止授权
					throw new Exception("参数异常");
				}
			}else {
				// 用户禁止授权
				throw new Exception("参数异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("执行第三方单点PC登陆操作：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			model.addAttribute("errorMsg", URLEncoder.encode(
					URLEncoder.encode("第三方单点PC登陆失败：" + e.getMessage(), "UTF-8"), "UTF-8"));
		}
		return new ModelAndView("redirect:/loginController/login");
	}

}
