package com.rolldata.web.system.service;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.entity.SysRole;
import com.rolldata.web.system.pojo.*;

import java.util.List;

/**
 * @Title: RoleService
 * @Description: 角色
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
public interface RoleService {

    /**
     * 保存
     *
     * @param roleDetailedInfo
     * @return
     * @throws Exception
     */
    public MainRoleTreeResponse save(RoleDetailedInfo roleDetailedInfo) throws Exception;

    /**
     * 删除
     *
     * @param roleIds
     * @throws Exception
     */
    public void del(List<String> roleIds) throws Exception;

    /**
     * 校验rolecde
     *
     * @param ajax
     * @param name
     * @param code
     * @param id
     * @return
     * @throws Exception
     */
    public void before(AjaxJson ajax, String name, String code, String id) throws Exception;

    /**
     * 更新
     *
     * @param roleDetailedInfo
     * @return
     * @throws Exception
     */
    public MainRoleTreeResponse update(RoleDetailedInfo roleDetailedInfo) throws Exception;

    /**
     * 改变状态
     *
     * @param role
     * @throws Exception
     */
    public void changeState(SysRole role) throws Exception;

    /**
     * 查询所有角色(子节点形式的数据)
     *
     * @return
     * @throws Exception
     */
    public MainRoleTreeParent queryAllRoleList() throws Exception;

    /**
     * 分页查询可用角色
     *
     * @param pageJson
     * @throws Exception
     */
    public void queryAvailable(PageJson pageJson) throws Exception;

    /**
     * 保存菜单和按钮
     *
     * @param functionOneToMany
     * @throws Exception
     */
    public void saveFunctionRolePower(FunctionOneToMany functionOneToMany) throws Exception;

    /**
     * 根据用户id 查询角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<SysRole> findListByUserId(String userId) throws Exception;

    /**
     * 角色详细信息
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public RoleDetailedInfo queryRoleDetailedInfo(String roleId) throws Exception;

    /**
     * 用户配置角色的角色列表
     *
     * @return
     * @throws Exception
     */
    public MainRoleTreeParent queryConfigList() throws Exception;

    /**
     * 根据角色id查询实体
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    public SysRole queryById(String roleId) throws Exception;

    /**
     * 批量删除
     *
     * @param sysRoles
     * @throws Exception
     */
    public void delete(List<SysRole> sysRoles) throws Exception;

    /**
     * 获取管理员角色及当前用户的角色id集合
     *
     * @return
     * @throws Exception
     */
    public List<String> getRoleList() throws Exception;

}
