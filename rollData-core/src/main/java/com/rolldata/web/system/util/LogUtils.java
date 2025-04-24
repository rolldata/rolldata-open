package com.rolldata.web.system.util;

import com.google.common.collect.Maps;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.*;
import com.rolldata.web.system.entity.SysLog;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.FunctionList;
import com.rolldata.web.system.pojo.ProductUseLogPojo;
import com.rolldata.web.system.service.FunctionService;
import com.rolldata.web.system.service.LogService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LogUtils {
	
	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
	private static LogService logService = SpringContextHolder.getBean(LogService.class);
	private static FunctionService functionService = SpringContextHolder.getBean(FunctionService.class);
	private static Logger logger = LogManager.getLogger(LogUtils.class);
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title, String content,Short loglevel) {
		saveLog(request, null, null, title, content,loglevel);
	}

	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title,Short loglevel) {
		saveLog(request, handler, ex, title, null,loglevel);
	}

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title, String content,Short loglevel) {
		SysUser user = UserUtils.getUser();
		if (user != null && user.getId() != null) {
			SysLog log = new SysLog();
			String broswer = BrowserUtils.checkBrowse(request);
			String contextPath = request.getContextPath();
			log.setTitle(title);
			log.setType(loglevel == 1 ? SysLog.TYPE_EXCEPTION : SysLog.TYPE_ACCESS);
			log.setLogContent(content);
			log.setLogLevel(loglevel);
			log.setNote(IpUtils.getIpAddr(request));//oConvertUtils.getIp()
			log.setBroswer(broswer);
			log.setOperateTime(DateUtils.gettimestamp());
			log.setUserId(user.getId());
//			log.setRemoteAddr(IpUtils.getIpAddr(request));
//			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
//			log.setParams(request.getParameterMap());
//			log.setMethod(request.getMethod());
//			log.setContent(content);
			log.setContextPath(contextPath);
			if (StringUtils.isBlank(log.getLogContent())) {
				log.setLogContent("操作IP:"+IpUtils.getIpAddr(request)+" 请求URI:"+log.getRequestUri()+" 操作方式:"+request.getMethod()+" 操作参数:"+request.getParameterMap());
			}
			if (loglevel.equals(SysLog.LEVEL1)) {
				log.setLogContent("操作IP:"+IpUtils.getIpAddr(request)+" 请求URI:"+log.getRequestUri()+" 操作方式:"+request.getMethod()+" 操作参数:"+request.getParameterMap()+" "+log.getLogContent());
			}
			if (log.getRequestUri().indexOf("/static")!=-1 || log.getRequestUri().indexOf(".js")!=-1 || log.getRequestUri().indexOf(".css")!=-1 || log.getRequestUri().indexOf(".png")!=-1 || log.getRequestUri().indexOf(".gif")!=-1 || log.getRequestUri().indexOf(".jpg")!=-1) {
				return;
			}
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		}
	}

	/**
	 * 保存使用情况至服务器
	 * @param machineCode 机器码
	 * @param type 类型，1初始化安装，2启动服务，3正常使用
	 */
	public static void saveUseLog(String machineCode,String type){
		// 异步保存
		new SaveUseLogThread(machineCode, type).start();
	}
	/**
	 * 保存日志线程
	 */
	public static class SaveUseLogThread extends Thread {
		private Logger logger = LogManager.getLogger(SaveUseLogThread.class);
		/**机器码*/
		private String machineCode;

		/**类型，1初始化安装，2启动服务，3正常使用*/
		private String type;
		public SaveUseLogThread(String machineCode,String type){
			super(SaveUseLogThread.class.getSimpleName());
			this.machineCode = machineCode;
			this.type = type;
		}

		@Override
		public void run() {
			String url = HttpRequestUtil.getSysUrl();
			url += "productController/saveUseLog";
			ProductUseLogPojo productUseLogPojo = new ProductUseLogPojo();
			productUseLogPojo.setMachineCode(machineCode);
			productUseLogPojo.setType(type);
			JSONObject jsonObject = JSONObject.fromObject(productUseLogPojo);
			String result = "";
			try{
				result = HttpRequestUtil.httpsPost(url).body(jsonObject.toString()).sendPost();
			}catch (Exception e){
				logger.error(MessageUtils.getMessage("common.sys.update.product.nonet"),e);
			}
			AjaxJson ajaxJson = JsonUtil.fromJson(result, AjaxJson.class);
			if(!ajaxJson.isSuccess()){
				logger.error(ajaxJson.getMsg());
			}
		}
	}
	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread {

		private SysLog log;
		private Object handler;
		private Exception ex;
		private Logger logger = LogManager.getLogger(SaveLogThread.class);
		
		public SaveLogThread(SysLog log, Object handler, Exception ex) {
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}

		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())) {
				String permission = "";
				if (handler instanceof HandlerMethod) {
					Method m = ((HandlerMethod) handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
				}
				log.setTitle(getMenuNamePath(log, permission));
			}
			// 如果有异常，设置异常信息  异常设置级别为1级，其他通过获取，如果没有就默认2级
			String exceptionStr=Exceptions.getStackTraceAsString(ex);
			if (StringUtils.isNotBlank(exceptionStr)) {
				log.setLogLevel(SysLog.LEVEL1);
				log.setException(exceptionStr);
				log.setLogContent(log.getLogContent()+" 异常信息："+ex.getMessage());
			}else {
				if (StringUtils.isNotBlank(log.getLogLevel()+"")) {
					log.setLogLevel(log.getLogLevel());
				}else {
					log.setLogLevel(SysLog.LEVEL2);
				}
			}
			// 如果无标题并无异常日志，则不保存信息
//			if (StringUtils.isEmpty(log.getTitle()) && StringUtils.isEmpty(log.getException())) {
//				return;
//			}
			// 保存日志信息
			try {
				if (log.getTitle()!=null && !log.getTitle().equals("")) {
					logService.save(log);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("日志写入失败："+e.getMessage());
			}
		}
	}

	/**
	 * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(SysLog log, String permission) {
		String url = StringUtils.substringAfter(log.getRequestUri(), "/");
		String contextPath = StringUtils.substringAfter(log.getContextPath(), "/");
		@SuppressWarnings("unchecked")
		Map<String, String> menuMap = (Map<String, String>) CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
		if (menuMap == null) {
			menuMap = Maps.newHashMap();
			List<FunctionList> menuList = new ArrayList<FunctionList>();
			try {
				menuList = functionService.getAllAndButton();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取菜单名称路径："+e.getMessage(),e);
			}
			for (FunctionList menu : menuList) {
				// 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
				String namePath = menu.getFuncName();
				// 设置菜单名称路径
				if (StringUtils.isNotBlank(menu.getHrefLink())) {
					//拼上项目名
					menuMap.put(contextPath+"/"+menu.getHrefLink(), namePath);
				} 
//				else if (StringUtils.isNotBlank(menu.getPowerFlag())) {
//					for (String p : StringUtils.split(menu.getPowerFlag())) {
//						menuMap.put(p, namePath);
//					}
//				}

			}
			CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
		}
		String menuNamePath = menuMap.get(url);
		if (menuNamePath == null) {
//			for (String p : StringUtils.split(permission)) {
//				menuNamePath = menuMap.get(p);
//				if (StringUtils.isNotBlank(menuNamePath)) {
//					break;
//				}
//			}
//			if (menuNamePath == null) {
				return "";
//			}
		}
		return menuNamePath;
	}
}
