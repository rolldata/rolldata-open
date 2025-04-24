package com.rolldata.web.system.controller;

import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.*;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.entity.UserRole;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.service.UserRoleService;
import com.rolldata.web.system.service.UserService;
import com.rolldata.web.system.service.impl.PasswordService;
import com.rolldata.web.system.util.DownloadUtil;
import com.rolldata.web.system.util.UserUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: UserController
 * @Description: 用户操作
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-24
 * @version V1.0
 */
@Controller
@RequestMapping("/userController")
@RequiresPathPermission("sys:user")
public class UserController {
	private Logger log = LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private SysUserOrgService sysUserOrgService;

	/**
	 * 添加页面
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userManage")
	@RequiresMethodPermissions(value = "userManage")
	public ModelAndView userManage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("web/system/userList");
	}

	/**
	 * 个人信息页
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/personalInformation")
	public ModelAndView personalInformation(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/web/system/personalInformation");
	}

	/**
	 * 个人信息页 移动端
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/personalInformationByMobile")
	public ModelAndView personalInformationByMobile(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/web/mobile/personal_mobile");
	}

	/**
	 * 超级管理员调用初始化用户部门接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	@RequiresMethodPermissions(value = "userManage")
	public void initUserOrg(HttpServletRequest request, HttpServletResponse response) {

		String msg = "执行成功";
		try {
			this.sysUserOrgService.synchronousUserOrgData();
		} catch (Exception e) {
			e.printStackTrace();
			msg = "执行失败:" + e.getMessage();
		}
		StringUtil.printJson(response, msg);
	}

	/**
	 * 权限分配tree
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLimitTreeList", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson userLimitTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		log.info("查询用户权限分配树");
		try {
            long startTime = System.currentTimeMillis();
            UserTreeParent userTreeParent = userService.queryUserLimitTree();
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(userTreeParent);
            long endTime = System.currentTimeMillis();
            log.info("Time-查询用户权限分配树:" + (endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户权限分配树操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	/**
	 * 所有用户详情
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/allUserDetailed", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson allUserDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxJson ajaxJson = new AjaxJson();
		log.info("查询所有用户详情");
		String data = request.getParameter("data");
		AllUserListParent aList = JsonUtil.fromJson(data, AllUserListParent.class);
		try {
			AllUserListParent allUserListParent = userService.queryAllUserList(aList);
			ajaxJson.setObj(allUserListParent);
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询所有用户详情操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	@RequestMapping(value = "/allUsers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson allUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		AjaxJson ajaxJson = new AjaxJson();
		log.info("查询所有用户列表");
		String data = request.getParameter("data");
        RoleSelectOrg roleSelectOrg = JsonUtil.fromJson(data, RoleSelectOrg.class);
		try {
			List<SysUser> allUserListParent = userService.allUsers(roleSelectOrg);
			ajaxJson.setObj(allUserListParent);
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询所有用户列表操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	@RequestMapping(value = "/deptIdUsers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson deptIdUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		AjaxJson ajaxJson = new AjaxJson();
		log.info("查询部门下用户列表");
		String data = request.getParameter("data");
        RoleSelectOrg roleSelectOrg = JsonUtil.fromJson(data, RoleSelectOrg.class);
		try {
			List<SysUser> allUserListParent = userService.deptIdUsers(roleSelectOrg);
			ajaxJson.setObj(allUserListParent);
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询部门下用户列表操作失败：{}", e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	/**
	 * 用户详情
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/userDetailed", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson userDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("查询用户详情");
		AjaxJson ajaxJson = new AjaxJson();
		String data = request.getParameter("data");
		UserDetailedJson aList = JsonUtil.fromJson(data, UserDetailedJson.class);
		try {
			aList = userService.queryUserDetailed(aList.getUserId());
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
			ajaxJson.setObj(aList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户详情操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	/**
	 * 新建用户
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "saveUser")
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("新建用户");
		String msg = "";
		Boolean valid = Boolean.FALSE;
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		try {
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			UserDetailedJson rUserJson = requestUserJsonParent.getSettings();
			SysUser sysUser = new SysUser();
			sysUser.setUserCde(rUserJson.getUserCde());
			sysUser.setPassword(rUserJson.getPassword());
			if (rUserJson.getPId() == null || "".equals(rUserJson.getPId())) {
				sysUser.setParentId("0");
			} else {
				sysUser.setParentId(rUserJson.getPId());
			}
			sysUser.setUserName(rUserJson.getUserName());
			sysUser.setCompany(rUserJson.getCompany());
			sysUser.setDepartment(rUserJson.getDepartment());
			sysUser.setPosition(rUserJson.getPosition());
			sysUser.setMobilePhone(rUserJson.getMobilePhone());
			sysUser.setAreaCode(rUserJson.getAreaCode());
			sysUser.setTelephone(rUserJson.getTelephone());
			sysUser.setGender(rUserJson.getGender());
			sysUser.setEmployType(rUserJson.getEmployType());
			sysUser.setMail(rUserJson.getMail());
			sysUser.setOrgId(rUserJson.getOrgId());
			if (EventType.CREATE_MD.toString().equals(requestUserJsonParent.getEvent())) {
				if (userService.doValid(sysUser)) { // 验证登录名唯一性
					msg = "common.sys.user.registered";
					valid = false;
				} else {
					userService.save(sysUser);
					ResponseUserJson rJson = userService.changeResponseUserJson(sysUser);
					msg = "common.sys.save.success";
					valid = true;
					ajax.setObj(rJson);
				}
			} else {
				msg = "common.sys.error";
				valid = false;
			}
			ajax.setMsg(MessageUtils.getMessage(msg));
			ajax.setSuccess(valid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新建用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error") + e.getMessage());
		}
		return ajax;
	}

	/**
	 * 新建文件夹
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@Deprecated
	public AjaxJson saveFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("新建用户目录");
		String msg = "";
		Boolean valid = Boolean.FALSE;
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		try {
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			UserDetailedJson rUserJson = requestUserJsonParent.getSettings();
			SysUser sysUser = new SysUser();
			if (rUserJson.getPId() == null || "".equals(rUserJson.getPId())) {
				sysUser.setParentId("0");
			} else {
				sysUser.setParentId(rUserJson.getPId());
			}
			sysUser.setUserName(rUserJson.getUserName());

			if (EventType.CREATE_MD.toString().equals(requestUserJsonParent.getEvent())) {

				if (userService.folderIsReName(sysUser)) { // 验证当前节点下文件夹下是否重名
					msg = "common.sys.name.repeat";
					valid = false;
				} else {
					userService.saveFolder(sysUser);
					ResponseUserJson rJson = userService.changeResponseUserJson(sysUser);
					msg = "common.sys.Registration.Successful";
					valid = true;
					ajax.setObj(rJson);
				}
			} else {
				msg = "common.sys.error";
				valid = false;
			}
			ajax.setMsg(MessageUtils.getMessage(msg));
			ajax.setSuccess(valid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("新建用户目录操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error")+e.getMessage());
		}
		return ajax;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "update")
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("更新用户");
		String msg = "";
		Boolean valid = Boolean.FALSE;
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			UserDetailedJson rUserJson = requestUserJsonParent.getSettings();

			// 查询用户信息,前台没传userCode
			SysUser oldUser = this.userService.getUserById(rUserJson.getUserId());
			SysUser sysUser = new SysUser();
			sysUser.setId(rUserJson.getUserId());
			sysUser.setUserCde(oldUser.getUserCde());
			sysUser.setUserName(rUserJson.getUserName());
			sysUser.setCompany(rUserJson.getCompany());
			sysUser.setDepartment(rUserJson.getDepartment());
			sysUser.setPosition(rUserJson.getPosition());
			sysUser.setMobilePhone(rUserJson.getMobilePhone());
			sysUser.setAreaCode(rUserJson.getAreaCode());
			sysUser.setTelephone(rUserJson.getTelephone());
			sysUser.setGender(rUserJson.getGender());
			sysUser.setEmployType(rUserJson.getEmployType());
			sysUser.setMail(rUserJson.getMail());
			sysUser.setIsactive(rUserJson.getIsactive());
			sysUser.setOrgId(rUserJson.getOrgId());
			sysUser.setThirdPartyCode(rUserJson.getThirdPartyCode());
			List<String> list = rUserJson.getRoles();
			if (EventType.UPDATE_MD.toString().equals(requestUserJsonParent.getEvent())) {

				if (AdministratorUtils.getAdministratorName().equals(rUserJson.getUserCde())) {
					msg = "common.sys.admin.prohibit";
					valid = false;
				} else {

					// 操作wd_sys_user_org
					this.sysUserOrgService.updateUserInfo(sysUser, list);
					userService.updateUserInfo(sysUser);
					userRoleService.deleteByUserId(rUserJson.getUserId()); // 删除现有的用户角色对照关系
					for (String roleId : list) {
						UserRole userRole = new UserRole();
						userRole.setRoleId(roleId);
						userRole.setUserId(rUserJson.getUserId());
						userRole.setCreateTime(new Date());
						userRoleService.save(userRole); // 保存用户角色对照关系
					}
					// 拼接返回json对象
					ResponseUserJson responseUserJson = userService
							.changeResponseUserJson(userService.getUserById(rUserJson.getUserId()));
					msg = "common.sys.update.success";
					valid = true;
					ajax.setObj(responseUserJson);
				}
			} else {
				msg = "common.sys.error";
				valid = false;
			}
			ajax.setMsg(MessageUtils.getMessage(msg));
			ajax.setSuccess(valid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error")+ e.getMessage());
		}
		return ajax;
	}

	/**
	 * 更新文件夹
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "update")
	@RequestMapping(value = "/updateFolder", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("更新用户目录");
		String msg = "";
		Boolean valid = Boolean.FALSE;
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			UserDetailedJson rUserJson = requestUserJsonParent.getSettings();
			SysUser sysUser = new SysUser();
			sysUser.setId(rUserJson.getUserId());
			sysUser.setUserName(rUserJson.getUserName());

			if (EventType.UPDATE_MD.toString().equals(requestUserJsonParent.getEvent())) {
				SysUser sUser = userService.getUserById(rUserJson.getUserId());
				sysUser.setParentId(sUser.getParentId());
				if (userService.folderIsReName(sysUser)) { // 验证当前节点下文件夹下是否重名
					msg = "common.sys.name.repeat";
					valid = false;
				} else {
					userService.updateFolderInfo(sysUser);
					// 拼接返回json对象
					ResponseUserJson responseUserJson = userService
							.changeResponseUserJson(userService.getUserById(rUserJson.getUserId()));
					msg = "common.sys.update.success";
					valid = true;
					ajax.setObj(responseUserJson);
				}
			} else {
				msg = "common.sys.error";
				valid = false;
			}
			ajax.setMsg(MessageUtils.getMessage(msg));
			ajax.setSuccess(valid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新用户目录操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error") + e.getMessage());
		}
		return ajax;
	}

	/**
	 * 校验重复(userCde)
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson validate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("校验重复");
		String data = request.getParameter("data");
		AjaxJson ajax = new AjaxJson();
		Boolean valid = Boolean.FALSE;
		try {
			SysUser sysUser = JsonUtil.fromJson(data, SysUser.class);
			valid = userService.doValid(sysUser);

			if (!valid) {
				ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.authentication.success"));
			} else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.information.duplication"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("校验重复操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.authentication.error"));
		}
		return ajax;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "delete")
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("删除用户");
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			UserDetailedJson rUserJson = requestUserJsonParent.getSettings();
			if (EventType.DELETE_MD.toString().equals(requestUserJsonParent.getEvent())) {
				List<String> idList = rUserJson.getIds();
				List<SysUser> sysUsers = new ArrayList<SysUser>();

				for (String id : idList) {
					if (userService.isHaveChildren(id)) {
						ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.not.permissions"));
						return ajax;
					}
				}
				for (String id : idList) {
					SysUser sysUser = userService.getUserById(id);
					if (AdministratorUtils.getAdministratorName().equals(sysUser.getUserCde())) {
						ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.admin.prohibit"));
						return ajax;
					} else {
						sysUsers.add(sysUser);
					}
				}
				userService.delete(idList);
				ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
			} else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
		}
		return ajax;
	}

	/**
	 * 拖拽
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/dragUsers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson dragUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("拖拽用户");
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			UserDetailedJson rUserJson = JsonUtil.fromJson(data, UserDetailedJson.class);
			List<String> idList = rUserJson.getIds();
			List<SysUser> userList = new ArrayList<SysUser>();
			for (String id : idList) { // 获取用户对象
				SysUser sUser = userService.getUserById(id);
				sUser.setParentId(rUserJson.getPId());
				userList.add(sUser);
			}
			for (SysUser sysUser : userList) { // 验证当前节点下文件夹名称是否重复
				if ("FOLDER".equals(sysUser.getType())) {
					if (userService.folderIsReName(sysUser)) {
						ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.name.repeat"));
						return ajax;
					}
				}
			}
			for (SysUser sysUser : userList) { // 修改父Id
				userService.updateParentId(sysUser);
			}
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("拖拽用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
		}
		return ajax;
	}

	/**
	 * 管理员修改用户状态（0初始，1活跃，2停用）
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateIsActive", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateIsActive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("管理员修改用户状态");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		SysUserJson sysUserJson = JsonUtil.fromJson(data, SysUserJson.class);
		SysUser user = sysUserJson.getSettings();
		// user.setIsactive(SysUser.STATUS_DELETE);
		try {
			userService.updateIsactive(user);
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("管理员修改用户状态户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 解锁用户
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson unlock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("解锁用户");
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
			if (EventType.UPDATE_MD.toString().equals(requestUserJsonParent.getEvent())) {
				UserDetailedJson rUserJson = requestUserJsonParent.getSettings();
				SysUser sysUser = new SysUser();
				sysUser.setUserCde(rUserJson.getUserCde());
				sysUser.setIslocked(SysUser.NO_LOCKED);
				userService.updateIslockedByCde(sysUser);
				// 解锁用户清除缓存
				CacheUtils.remove("passwordRetryCache", sysUser.getUserCde());
				ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
			} else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解锁用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 用户管理
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/userAllList", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson getUserAllList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("用户管理");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		PageJson pageJson = JsonUtil.fromJson(data, PageJson.class);
		try {
			userService.getUserList(pageJson);
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户管理操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		ajax.setObj(pageJson);
		return ajax;
	}

	/**
	 * 普通用户修改密码(非初始状态下修改密码)
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateUserPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("普通用户修改密码");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		UserPojo userPojo = JsonUtil.fromJson(data, UserPojo.class);
		SysUser user = null;
		try {
			if (userPojo.getUserId() == null) {
				user = UserUtils.getUser();
			} else {
				user = userService.getUserById(userPojo.getUserId());
			}
			if (passwordService.verifyPassword(userPojo.getOldPassword(), user)) {
				user.setPassword(userPojo.getNewPassword());
				userService.updatePassword(user);
				ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
			} else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.password.error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("普通用户修改密码操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 用户第一次登录后修改密码(初始状态下修改密码)
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInitPassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateInitPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("用户第一次登录后修改密码");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		SysUserJson sysUserJson = JsonUtil.fromJson(data, SysUserJson.class);
		SysUser user = sysUserJson.getSettings();
		user.setIsInit(SysUser.ISINIT_NORMAL);
		try {
			SysUser oldUser = null;
			if (StringUtil.isNotEmpty(user.getId())) {
				oldUser = userService.getUserById(user.getId());
			} else {
				oldUser = UserUtils.getUser();
			}
			user.setId(oldUser.getId());
			user.setUserCde(oldUser.getUserCde());
			userService.updateInitPassword(user);
			UserUtils.getUser().setIsInit(user.getIsInit());

			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户第一次登录后修改密码操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 修改头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHeadPhoto", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateHeadPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("修改图片");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		RequestUserJsonParent requestUserJsonParent = JsonUtil.fromJson(data, RequestUserJsonParent.class);
		try {
			if (EventType.UPDATE_MD.toString().equals(requestUserJsonParent.getEvent())) {
				userService.updateHeadPhoto(requestUserJsonParent.getSettings().getHeadPhoto());
				ajax.setSuccessAndMsg(Boolean.TRUE, MessageUtils.getMessage("common.sys.update.success"));
			} else {
				ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改图片失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 重置密码同时解锁用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "resetPassword")
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("重置密码并解锁用户");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		UserPojo userPojo = JsonUtil.fromJson(data, UserPojo.class);
		SysUser user = new SysUser();
		try {
			SysUser oldUser = null;
			if (StringUtil.isNotEmpty(userPojo.getUserId())) {
				oldUser = userService.getUserById(userPojo.getUserId());
				if (AdministratorUtils.getAdministratorName().equals(oldUser.getUserCde())) {
					ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("common.sys.admin.prohibit"));
					return ajax;
				}
			} else {
				ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("common.sys.password.reset.success"));
				return ajax;
			}
			user.setPassword(userPojo.getNewPassword());
			user.setUserCde(oldUser.getUserCde());
			user.setId(oldUser.getId());
			userService.updateResetPassword(user);
			SysUser sysUser = new SysUser();
			sysUser.setUserCde(oldUser.getUserCde());
			sysUser.setIslocked(SysUser.NO_LOCKED);
			userService.updateIslockedByCde(sysUser);
			// 重置密码清除缓存
			CacheUtils.remove("passwordRetryCache", oldUser.getUserCde());
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.password.reset.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("重置密码并解锁用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.password.reset.error"));
		}
		return ajax;
	}

	/**
	 * 重置所有用户密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAllUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateAllUserPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("重置所有用户密码");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		UserDetailedJson json = JsonUtil.fromJson(data, UserDetailedJson.class);
		try {
			userService.updateAllUserPassword(json.getPassword());
			ajax.setSuccessAndMsg(Boolean.TRUE, MessageUtils.getMessage("common.sys.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("重置所有用户密码：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 查询个人中心用户详情
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryNowUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson queryNowUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("查询个人中心用户详情");
		AjaxJson ajaxJson = new AjaxJson();

		try {
			UserDetailedJson aList = userService.queryNowUserInfo();
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
			ajaxJson.setObj(aList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询个人中心用户详情操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

	/**
	 * 个人更新用户信息 手机、电话
	 * 
	 * @param request
	 * @param response
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUserPhone", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateUserPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("个人更新用户信息 手机、电话");
		AjaxJson ajax = new AjaxJson();
		try {
			String data = request.getParameter("data");
			UserDetailedJson rUserJson = JsonUtil.fromJson(data, UserDetailedJson.class);
			userService.updateUserPhone(rUserJson);
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
		}
		return ajax;
	}

	/**
	 * 下载用户组织模版
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
	@ResponseBody
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = ResourceUtil.getTemplatePath() + "user.xlsx";
		log.info("下载模板");
		try {
			DownloadUtil.downloadFile(filePath, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("下载模板操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
		}
	}

	/**
	 * 导入组织用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequiresMethodPermissions(value = "importOrgUser")
	@RequestMapping(value = "/importOrgUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importOrgUser(@RequestParam("importExcel") CommonsMultipartFile uploadImgFile,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("导入组织用户");
		AjaxJson ajax = new AjaxJson();
		try {
			userService.importOrgUsers(ajax, uploadImgFile);
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.file.upload.success"));
		} catch (Exception e) {
			e.printStackTrace();
			ajax.setSuccessAndMsg(false,
					MessageUtils.getMessage("common.sys.file.upload.error") + ":" + e.getMessage());
			log.error("导入组织用户操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
		}
		return ajax;
	}

	/**
	 * 执行导入操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequiresMethodPermissions(value = "importOrgUser")
	@RequestMapping(value = "/complyImportOrgUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson complyImportOrgUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("执行导入操作");
		AjaxJson ajax = new AjaxJson();
		String data = request.getParameter("data");
		Map map = JsonUtil.fromJson(data, Map.class);
		try {
			userService.complyImportOrgUser((String) map.get("_uuid"));
			ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.file.upload.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("执行导入操作：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.file.upload.error"));
		}
		return ajax;
	}

	/**
	 * 根据勾选的组织部门，查询下属配置过资源密码的用户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryIsBrowseUsers", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson queryIsBrowseUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		AjaxJson ajaxJson = new AjaxJson();
		log.info("查询所有配置过资源密码的用户列表");
		String data = request.getParameter("data");
		RoleSelectOrg roleSelectOrg = JsonUtil.fromJson(data, RoleSelectOrg.class);
		try {
			List<SysUser> allUserListParent = userService.queryIsBrowseUsers(roleSelectOrg);
			ajaxJson.setObj(allUserListParent);
			ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询所有配置过资源密码的用户列表操作失败：" + e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

}
