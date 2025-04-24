package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.entity.SysUserOrg;

import java.sql.Connection;
import java.util.List;

/**
 * 用户组织权限服务层
 *
 * @Title:SysUserOrgService
 * @Description:SysUserOrgService
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-4-23
 * @version:V1.0
 */
public interface SysUserOrgService {

    /**
     * 查询全部数据
     *
     * @return 数据集合
     */
    List<SysUserOrg> queryAll() throws Exception;

    /**
     * 同步用户组织数据,更新全部的用户
     *
     * @throws Exception
     */
    void synchronousUserOrgData() throws Exception;

    /**
     * 保存角色时,创建用户的组织权限
     * <p>当前角色组织权限+用户的其他角色组织权限+职务组织权限
     *
     * @param connection    当前连接
     * @param departmentIds 部门id集合
     * @param userIds       用户id集合
     * @param roleId        角色id
     */
    void createEntitysByRoleId(
            Connection connection,
            List<String> departmentIds,
            List<String> userIds,
            String roleId
    ) throws Exception;

    /**
     * 保存职务时,创建用户的组织权限
     * <p>当前职务组织权限+用户的角色组织权限
     *
     * @param departmentIds 部门id集合
     * @param userIds       用户id集合
     * @param postId        职务id
     */
    void createEntitysByPostId(List<String> departmentIds, List<String> userIds, String postId) throws Exception;

    /**
     * 新增,删除部门组织时,给超级管理员赋权限
     *
     * @throws Exception
     */
    void saveAdmin() throws Exception;

    /**
     * 创建用户
     *
     * @param sysUser 用户信息
     */
    void createUserInfo(SysUser sysUser) throws Exception;

    /**
     * 修改用户信息
     *
     * @param sysUser 用户信息
     * @param roleIds 角色id集合
     * @throws Exception
     */
    void updateUserInfo(SysUser sysUser, List<String> roleIds) throws Exception;

    /**
     * 修改用户名称
     *
     * @param userId   用户id
     * @param userName 用户名称
     */
    void updateUserNameByUserId(String userId, String userName) throws Exception;

    /**
     * 修改用户名称,并判断组织是否修改(在update用户前执行)
     *
     * @param sysUser 用户信息
     * @throws Exception
     */
    void updateUserNameByUserCode(SysUser sysUser) throws Exception;

    /**
     * 修改组织名称
     *
     * @param orgId   组织id
     * @param orgName 组织名称
     * @param orgCde  组织编码
     */
    void updateOrgNameByOrgId(String orgId, String orgName, String orgCde) throws Exception;

    /**
     * 修改部门名称
     *
     * @param departmentId   部门id
     * @param departmentName 部门名称
     * @param orgCde         部门编码
     */
    void updateOrgNameByDepartmentId(String departmentId, String departmentName, String orgCde) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds 用户id集合
     */
    void deleteEntitysByUserIds(List<String> userIds) throws Exception;

    /**
     * 删除组织
     *
     * @param orgIds 组织id
     */
    void deleteEntitysByOrgIds(List<String> orgIds) throws Exception;

    /**
     * 删除部门
     *
     * @param departmentIds 部门id集合
     */
    void deleteEntitysByDepartmentIds(List<String> departmentIds) throws Exception;

    /**
     * 删除角色拥有权限的部门
     *
     * @param roleId 角色id
     * @throws Exception
     */
    void deleteUserDepartmentByRole(String roleId) throws Exception;

}
