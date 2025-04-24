package com.rolldata.web.system.util;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.CacheUtils;
import com.rolldata.core.util.SpringContextHolder;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @Title: UserUtils
 * @Description: 用户工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class UserUtils {
	public static Logger log = LogManager.getLogger(UserUtils.class);
	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);
	private static FunctionService functionService = SpringContextHolder.getBean(FunctionService.class);
	private static FunctionOperService functionOperService = SpringContextHolder.getBean(FunctionOperService.class);
	private static OrgService orgService = SpringContextHolder.getBean(OrgService.class);
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_USER_NAME_ = "username_";
	public static final String MENU_CACHE_URL_ = "menu_url_";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_RESOUECE_LIST = "resourceList";
	public static final String CACHE_FUNCTION_OPER_LIST = "functionOperList";

	/**
	 * 用户所能操作的用户集合
	 */
	public static final String CACHE_USERPERMISSION_LIST = "userPermission";

    /**
     * 用户的数据权限
     */
	public static final String CACHE_DATA_RIGHTS_LIST = "orgPermission";

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
//	public static User get(String id) {
//		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
//		if (user == null) {
//			user = userService.get(id);
//			if (user == null) {
//				return null;
//			}
//			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//			CacheUtils.put(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserCde(), user);
//		}
//		return user;
//	}

	/**
	 * 根据用户名获取用户
	 * 
	 * @param username
	 * @return
	 */
	public static SysUser getByUserName(String username) {
		SysUser user = (SysUser) CacheUtils.get(USER_CACHE, USER_CACHE_USER_NAME_ + username);
		if (user == null) {
            try {
                user = userService.getUserByUserCde(username);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
            if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserCde(), user);
		}
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache() {
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_FUNCTION_OPER_LIST);
		removeCache(CACHE_USERPERMISSION_LIST);
		removeCache(CACHE_DATA_RIGHTS_LIST);
		removeCache(CACHE_RESOUECE_LIST);
		UserUtils.clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(SysUser user) {
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_USER_NAME_ + user.getUserCde());
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static SysUser getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
//			User user = get(principal.getId());
			SysUser user = getByUserName(principal.getUserCde());
			if (user != null) {
				return user;
			}
			return new SysUser();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}
	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static SysOrg getUserDetail() {
		Principal principal = getPrincipal();
		if (principal != null) {
//			User user = get(principal.getId());
			SysUser user = getByUserName(principal.getUserCde());
			if (user != null) {
				try {
					SysOrg sysOrg = orgService.getSysOrgById(user.getOrgId());
					return sysOrg;
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
			}
			return new SysOrg();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysOrg();
	}
	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public static List<SysRole> getRoleList() {
		List<SysRole> roleList = (List<SysRole>) getCache(CACHE_ROLE_LIST);
		if (roleList == null) {
			SysUser user = getUser();
            try {
                roleList = roleService.findListByUserId(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
				log.error(e.getMessage(),e);
            }
            putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	public static List<String> getRolesIdList() {
		Set<SysRole> roles = Sets.newConcurrentHashSet(getRoleList());
		if (roles.isEmpty()) {
		    return Collections.singletonList("");
        }
		return new ArrayList<>(Sets.newHashSet(Collections2.transform(roles, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole role) {
				return role.getId();
			}
		})));
	}
	public static Set<String> getRoleStringList() {
		Set<SysRole> roles = Sets.newConcurrentHashSet(getRoleList());
		return Sets.newHashSet(Collections2.transform(roles, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole role) {
				return role.getRoleCde();
			}
		}));
	}
	public static List<String> getRolesNameList() {
		Set<SysRole> roles = Sets.newConcurrentHashSet(getRoleList());
		return new ArrayList<>(Sets.newHashSet(Collections2.transform(roles, new Function<SysRole, String>() {
			@Override
			public String apply(SysRole role) {
				return role.getRoleName();
			}
		})));
	}
	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<SysFunction> getFunctionList() {
		List<SysFunction> functionList = (List<SysFunction>) getCache(CACHE_MENU_LIST);
		if (functionList == null) {
			SysUser user = getUser();
            try {
                functionList = functionService.findFuncByUserId(user.getId(), user.getPosition());
            } catch (Exception e) {
                e.printStackTrace();
				log.error(e.getMessage(),e);
            }
            putCache(CACHE_MENU_LIST, functionList);
		}
		return functionList;
	}
    
    /**
     * 获取当前用户授权按钮
     * @return
     */
    public static List<SysFunctionOper> getFunctionOperList() {
        List<SysFunctionOper> functionOperList = (List<SysFunctionOper>) getCache(CACHE_FUNCTION_OPER_LIST);
        if (functionOperList == null) {
        	SysUser user = getUser();
            try {
                functionOperList = functionOperService.findFuncnOperByUserId(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
				log.error(e.getMessage(),e);
            }
            putCache(CACHE_FUNCTION_OPER_LIST, functionOperList);
        }
        return functionOperList;
    }
    
//
//	/**
//	 * 获取当前菜单
//	 * 
//	 * @return
//	 */
//	public static Menu getCurrentMenu() {
//		String url = ServletUtils.getRequest().getServletPath();
//		if (url.endsWith(".jsp")) {
//			return null;
//		}
//		String adminUrlPrefix = PropertiesUtil.getConfig("admin.url.prefix");
//		url = url.substring(adminUrlPrefix.length() + 1, url.length());
//		url = StringUtils.trimFirstAndLastChar(url, '/');
//		if (StringUtils.isEmpty(url)) {
//			return null;
//		}
//		// 全匹配查找
//		List<Menu> menuList = getMenuList();
//		return getCurrentMenu(menuList, url);
//	}
//
//	private static Menu getCurrentMenu(List<Menu> menuList, String url) {
//		for (Menu menu : menuList) {
//			if (!StringUtils.isEmpty(menu.getUrl())
//					&& url.equals(StringUtils.trimFirstAndLastChar(menu.getUrl(), '/'))) {
//				return menu;
//			}
//		}
//		/*if (StringUtils.isEmpty(url)) {
//		return null;
//		}
//		url = url.substring(0, url.lastIndexOf("/"));
//		return getCurrentMenu(menuList, url);*/
//		return null;
//	}
//
//	/**
//	 * 通过ID获得菜单信息
//	 * 
//	 * @return
//	 */
//	public static Menu getMenuById(String menuid) {
//		if (StringUtils.isEmpty(menuid)) {
//			return null;
//		}
//		List<Menu> menuList = getMenuList();
//		for (Menu menu : menuList) {
//			if (menuid.equals(menu.getId())) {
//				return menu;
//			}
//		}
//		return null;
//	}
//
	public static Set<String> getPermissionsList() {
		List<SysFunction> sysFunctionList = UserUtils.getFunctionList();
        List<SysFunctionOper> sysFunctionOperList = UserUtils.getFunctionOperList();
		Set<String> permissionsList = Sets.newConcurrentHashSet();
		for (SysFunction sysFunction : sysFunctionList) {
			if(StringUtils.isNotBlank(sysFunction.getPowerFlag())){
				for (String permission : StringUtils.split(sysFunction.getPowerFlag(), ",")) {
					if (StringUtils.isNotBlank(permission)) {
						permissionsList.add(permission);
					}
				}
			}
		}
        for (SysFunctionOper sysFunctionOper : sysFunctionOperList) {
            if (StringUtils.isNotBlank(sysFunctionOper.getPowerFlag())) {
                
                for (String permission : StringUtils.split(sysFunctionOper.getPowerFlag(), ",")) {
                    if (StringUtils.isNotBlank(permission)) {
                        permissionsList.add(permission);
                    }
                }
            }
        }
		return permissionsList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static SysFunction getTopMenu() {
        SysFunction topMenu = getFunctionList().get(0);
		return topMenu;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {
			log.error(e.getMessage(),e);
		} catch (InvalidSessionException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	// ============== User Cache ==============
	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getSession().removeAttribute(key);
	}

    /**
     * 当前登录用户所能管理的用户集合
     * @return
     */
    public static List<SysUser> getUserPermission () {

        List<SysUser> userList = (List<SysUser>) getCache(CACHE_USERPERMISSION_LIST);
        if (userList == null) {
            try {
                userList = AdministratorUtils.getAdministratorName().equals(getUser().getUserCde()) 
                                        ? userService.queryAllSysUsers()
                                        : userService.querySysUsersByRoleIds(UserUtils.getRolesIdList());

                //把登录人加到集合中
                userList.add(getUser());
            } catch (Exception e) {
                e.printStackTrace();
				log.error(e.getMessage(),e);
            }
            putCache(CACHE_USERPERMISSION_LIST, userList);
        }
        return userList;
    }

    /**
     * 当前登录用户所能管理的用户id集合
     * @return
     */
    public static List<String> getUserIdPermissionList() {
        Set<SysUser> users = Sets.newConcurrentHashSet(getUserPermission());
        return new ArrayList<>(Sets.newHashSet(Collections2.transform(users, new Function<SysUser, String>() {

            @Override
            public String apply(SysUser user) {
                return user.getId();
            }
        })));
    }

    /**
     * 当前登录人的数据权限,组织的集合
     *
     * @return
     */
    public static List<SysOrg> getOrgPermission() {

        List<SysOrg> orgList = null;
        /*if (orgList == null) {
            
            putCache(CACHE_DATA_RIGHTS_LIST, orgList);
        }*/

		try {
			SysOrg userDetail = getUserDetail();
			orgList = orgService.querySysOrgList();
			if (null != userDetail) {
				boolean isExist = false;
				for (SysOrg sysOrg : orgList) {
					if (userDetail.getId().equals(sysOrg.getId())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					orgList.add(userDetail);//加上当前登录人本身所在的组织
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
        return orgList;
    }

    /**
     * 当前登录人的数据权限,组织id的集合
     *
     * @return
     */
    public static List<String> getOrgIdPermissionList() {

        Set<SysOrg> orgs = Sets.newConcurrentHashSet(getOrgPermission());
        return new ArrayList(Sets.newHashSet(Collections2.transform(orgs, SysOrg::getId)));
    }

    public static String getIsAdminConsole(){
		List<SysRole> roleList = getRoleList();
		String isAdmin="0";//默认无控制台权限
		for (int i = 0; i < roleList.size(); i++) {
			SysRole sysRole = roleList.get(i);
			if(StringUtil.isNotEmpty(sysRole.getIsAdmin()) && sysRole.getIsAdmin().equals(SysRole.IS_ADMIN)){
				isAdmin = sysRole.getIsAdmin();
				break;
			}
		}
		return isAdmin;
	}

	/**
     *  查全部组织,通过递归找他可管理的下级组织(包括本级)
     *
     * @return
     * @throws Exception
     */
    public static List<String> getPostOrgIdPermissionList() {

        List<String> sysOrgIds = new ArrayList<>();
		try {
			List<SysOrg> sysOrgCompanyList = orgService.queryAllSysOrg();
			sysOrgIds.add(UserUtils.getUser().getDepartment());
			findDepartmentOrg(UserUtils.getUser().getDepartment(), sysOrgCompanyList, sysOrgIds);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		return sysOrgIds;
    }

	private static void findDepartmentOrg(String orgId, List<SysOrg> sysOrgCompanyList, List<String> sysOrgIds) {

		sysOrgIds.addAll(sysOrgCompanyList.stream()
				.filter(sysOrg -> sysOrg.getParentId().equals(orgId))
				.peek(sysOrg -> findDepartmentOrg(sysOrg.getId(), sysOrgCompanyList, sysOrgIds))
				.map(SysOrg::getId)
				.collect(Collectors.toList())
		);
	}

}
