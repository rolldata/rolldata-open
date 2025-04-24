package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysFunction;
import com.rolldata.web.system.pojo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Title: FunctionService
 * @Description: 功能
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
public interface FunctionService {
    
    /**
     * 获取可用的菜单
     * @param pageJson 分页信息
     * @throws Exception
     */
    public void getAvailableFunList(PageJson pageJson) throws Exception;
    
    /**
     * 根据角色id查寻菜单
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    public Result getRoleHaveMenu(String roleId) throws Exception;

    /**
     * 根据用户id 查菜单
     *
     * @param userId   用户id
     * @param position 用户职位id
     * @return
     * @throws Exception
     */
    public List<SysFunction> findFuncByUserId(String userId, String position) throws Exception;
    
    /**
     * 用户拥有的菜单
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    public MainFunctionTreeParent findHierFuncByUserId(HttpServletRequest request, String parentId, String userId, String isAdmin)
        throws Exception;

    /**
     * 拼菜单
     * @return
     * @throws Exception
     */
    public List<FunctionList> getAllAndButton() throws Exception;

    /**
     * 菜单功能权限树
     * @return
     * @throws Exception
     */
    public Map findRoleHaveMenu() throws Exception;

    /**
     * 查询目录结构树
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
	public List<SysMenus> queryMenuTree(HttpServletRequest request) throws Exception;

    /**
     * 目录管理(不带终端控制)
     *
     * @return
     * @throws Exception
     */
	public List<SysMenus> queryMenuManageTree() throws Exception;

	/**
	 * 查询单个目录详细
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public SysMenus queryMenuInfo(String menuId) throws Exception;

	/**
	 * 保存单个目录信息
	 * @param sysMenu
	 * @return
	 * @throws Exception
	 */
	public SysMenus saveMenuInfo(SysMenus sysMenu) throws Exception;

	/**
	 * 修改单个目录信息
	 * @param sysMenu
	 * @return
	 * @throws Exception
	 */
	public void updateMenuInfo(SysMenus sysMenu) throws Exception;

	/**
	 * 删除单个目录
	 * @param id
	 * @throws Exception
	 */
	public void deleteMenuInfo(String id) throws Exception;

	/**
	 * 根据名称查询结果集
	 * @param sysMenuReq
	 * @return
	 * @throws Exception
	 */
	public List<SysFunction> queryFunctionObjByName(SysMenus sysMenuReq) throws Exception;

	/**
	 * 根据名称查询结果集(排除自己)
	 * @param sysMenuReq
	 * @return
	 * @throws Exception
	 */
	public List<SysFunction> queryFunctionObjByNameAndNotOwn(SysMenus sysMenuReq) throws Exception;

	/**
	 * 查询角色配置目录结构树
	 * @return
	 * @throws Exception
	 */
	public List<SysMenus> queryRoleMenuTree() throws Exception;

	/**
	 * 交换目录顺序
	 * @param sysMenuReq
	 * @throws Exception
	 */
	public void updateExchangeOrder(SysMenus sysMenuReq) throws Exception;

    /**
     * 菜单下按钮列表
     *
     * @param funcId 菜单id
     * @return
     * @throws Exception
     */
	public List<MainFunctionTree> queryFuncButton(String funcId) throws Exception;

	/**
	 * 用户登录后拥有的全部菜单(树形结构)
	 * @return
	 * @throws Exception
	 */
	public List<FunctionResourcePojo> queryUserAllAvailableTree(HttpServletRequest request) throws Exception;


	/**
     * 目录管理(不带终端控制，无权限)
     *
     * @return
     * @throws Exception
     */
	public List<SysMenus> queryMenuManageTreeNoPermission() throws Exception;

	/***
	 * 根据用户id查询管理控制台目录节点
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public MainFunctionTreeParent findConsoleFuncByUserId(HttpServletRequest request, String userId) throws Exception;

    /**
     * 根据id查询菜单
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysFunction queryFuncById(String id) throws Exception;

	/**
	 * 财务管理和指标管理页获取左侧菜单（排除根目录）
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 */
	public MainFunctionTreeParent findHierFuncByUserIdAndParam(HttpServletRequest request, String parentId, String userId, String isAdmin) throws Exception;

	/**
	 * 用户拥有的菜单（含主目录+递归）
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 * @throws Exception
	 */
	public MainFunctionTreeParent findHierFuncByUserIdToRe(HttpServletRequest request, String parentId, String userId, String isAdmin)
			throws Exception;

	/**
	 * 用户拥有的菜单（含主目录+递归）
	 *
	 * @param request
	 * @param parentId
	 * @param userId
	 * @param isAdmin
	 * @return
	 * @throws Exception
	 */
	MainFunctionTreeParent queryMiraclePortalMenus(HttpServletRequest request, String parentId, String userId, String isAdmin) throws Exception;

	List<SysMenus> queryBusinessMenu(HttpServletRequest request) throws Exception;
}
