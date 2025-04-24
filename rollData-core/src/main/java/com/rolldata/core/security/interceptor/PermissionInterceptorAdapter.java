package com.rolldata.core.security.interceptor;

import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.SysPropertiesUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title: PermissionInterceptorAdapter
 * @Description: 权限拦截器
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class PermissionInterceptorAdapter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 解决安全漏洞：检测到目标服务器启用了OPTIONS方法
//		response.setHeader("Access-Control-Allow-Origin", "*");
		// Access-Control-Allow-Credentials跨域问题
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Max-Age", "86400");
//		response.setHeader("Access-Control-Allow-Headers", "*");
		String isThirdPartyTask = SysPropertiesUtil.getConfig("security.open");
		if(StringUtil.isNotEmpty(isThirdPartyTask) && isThirdPartyTask.equals("true")){
			// 点击劫持：X-Frame-Options未配置
			response.addHeader("X-Frame-Options","SAMEORIGIN");
			// 检测到目标Referrer-Policy响应头缺失
			response.addHeader("Referer-Policy","origin");
			// Content-Security-Policy响应头确实
			response.addHeader("Content-Security-Policy","object-src 'self'");
			// 检测到目标X-Permitted-Cross-Domain-Policies响应头缺失
			response.addHeader("X-Permitted-Cross-Domain-Policies","master-only");
			// 检测到目标X-Content-Type-Options响应头缺失
			response.addHeader("X-Content-Type-Options","nosniff");
			// 检测到目标X-XSS-Protection响应头缺失
			response.addHeader("X-XSS-Protection","1; mode=block");
			// 检测到目标X-Download-Options响应头缺失
			response.addHeader("X-Download-Options","noopen");
			// HTTP Strict-Transport-Security缺失
			response.addHeader("Strict-Transport-Security","max-age=63072000; includeSubdomains; preload");

			// 如果是OPTIONS则结束请求
			if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
				response.setStatus(HttpStatus.NO_CONTENT.value());
				return false;
			}
		}else{
			String whiteList = SysPropertiesUtil.getConfig("security.cors.white.list");
			// 解决安全漏洞：检测到目标服务器启用了OPTIONS方法
			if(StringUtil.isNotEmpty(whiteList)){
				response.setHeader("Access-Control-Allow-Origin", whiteList);
			}else{
				response.setHeader("Access-Control-Allow-Origin", "*");
			}
			// Access-Control-Allow-Credentials跨域问题
			String credentials = SysPropertiesUtil.getConfig("security.allow.credentials");
			if(StringUtil.isNotEmpty(credentials)) {
				response.setHeader("Access-Control-Allow-Credentials", credentials);
			}else{
				response.setHeader("Access-Control-Allow-Credentials", "true");
			}
			String methods = SysPropertiesUtil.getConfig("security.allow.methods");
			if(StringUtil.isNotEmpty(methods)){
				response.setHeader("Access-Control-Allow-Methods", methods);
			}else{
				response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
			}
			response.setHeader("Access-Control-Max-Age", "86400");
			String headers = SysPropertiesUtil.getConfig("security.allow.headers");
			if(StringUtil.isNotEmpty(headers)){
				response.setHeader("Access-Control-Allow-Headers", headers);
			}else{
				response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type, exception, Origin, Content-Length, Accept-Encoding");
			}
		}
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			RequiresMethodPermissions requiresMethodPermissions = handlerMethod
					.getMethodAnnotation(RequiresMethodPermissions.class);
			if (requiresMethodPermissions != null) {
				RequiresPathPermission requiresPathPermission = AnnotationUtils
						.findAnnotation(handlerMethod.getBean().getClass(), RequiresPathPermission.class);
				String basePermission = "";
				if (requiresPathPermission != null) {
					basePermission = requiresPathPermission.value();
				}
				String[] perms = requiresMethodPermissions.value();

				if (perms.length == 1) {
					String permission = perms[0];
					if (!StringUtil.isEmpty(basePermission)) {
						permission = basePermission + ":" + perms[0];
					}
					getSubject().checkPermission(permission);
					return true;
				}
				if (Logical.AND.equals(requiresMethodPermissions.logical())) {
					String[] newPerms = new String[perms.length];
					for (int i = 0; i < perms.length; i++) {
						String perm = perms[i];
						if (!StringUtil.isEmpty(basePermission)) {
							perm = basePermission + ":" + perm;
						}
						newPerms[i] = perm;
					}
					getSubject().checkPermissions(newPerms);
					return true;
				}
				if (Logical.OR.equals(requiresMethodPermissions.logical())) {
					boolean hasAtLeastOnePermission = false;
					for (String permission : perms) {
						if (!StringUtil.isEmpty(basePermission)) {
							permission = basePermission + ":" + permission;
						}
						if (getSubject().isPermitted(permission)) {
							hasAtLeastOnePermission = true;
						}
					}
					if (!hasAtLeastOnePermission) {
						String permission = perms[0];
						if (!StringUtil.isEmpty(basePermission)) {
							permission = basePermission + ":" + permission;
						}
						getSubject().checkPermission(permission);
					}
					return true;
				}
			}
		}
		return true;

	}

	protected Subject getSubject() {
		return SecurityUtils.getSubject();
	}

}