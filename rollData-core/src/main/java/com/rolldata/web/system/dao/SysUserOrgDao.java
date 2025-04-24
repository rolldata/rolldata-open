package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysUserOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title:SysUserOrgDao
 * @Description:SysUserOrgDao
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-4-23
 * @version:V1.0
 */
public interface SysUserOrgDao extends JpaRepository<SysUserOrg, String>, JpaSpecificationExecutor<SysUserOrg> {

    /**
     * 根据用户id删除
     *
     * @param userIds 用户id集合
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUserOrg t where t.userId in (:userIds)")
    void deleteByUserIds(@Param("userIds") List<String> userIds);

    /**
     * 删除用户数据权限
     *
     * @param userId 用户id
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUserOrg t where t.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);

    /**
     * 删除角色的数据权限(原版SQL慢，改成left join)
     *
     * @param roleId 角色id
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from wd_sys_user_org where id in (select temp.id from (  select t.id" +
           " from wd_sys_user_org t left join wd_sys_userrole ur on t.user_id = ur.user_id" +
           " left join wd_sys_orgrole sor on t.department_id = sor.org_id where" +
           " ur.role_id = :roleId and sor.role_id = :roleId) temp)", nativeQuery = true)
    void deleteByRoleId(@Param("roleId") String roleId);

    /**
     * 修改用户名称
     *
     * @param userId   用户id
     * @param userName 用户名称
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUserOrg t set t.userName = :userName where t.userId = :userId")
    void updateUserNameByUserId(@Param("userId") String userId, @Param("userName") String userName);

    /**
     * 修改组织名称
     *
     * @param orgId   组织id
     * @param orgName 组织名称
     * @param orgCde  组织编码
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUserOrg t set t.orgName = :orgName, t.orgCode = :orgCde where t.orgId = :orgId")
    void updateOrgNameByOrgId(
            @Param("orgId") String orgId,
            @Param("orgName") String orgName,
            @Param("orgCde") String orgCde
    );

    /**
     * 修改部门名称
     *
     * @param departmentId   部门id
     * @param departmentName 部门名称
     * @param departmentCode 部门编码
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUserOrg t set t.departmentName = :departmentName, t.departmentCode = :departmentCode" +
           " where t.departmentId = :departmentId")
    void updateOrgNameByDepartmentId(
            @Param("departmentId") String departmentId,
            @Param("departmentName") String departmentName,
            @Param("departmentCode") String departmentCode
    );

    /**
     * 删除
     *
     * @param userIds 用户id集合
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUserOrg t where t.userId in (:userIds)")
    void deleteEntitysByUserIds(@Param("userIds") List<String> userIds);

    /**
     * 删除
     *
     * @param orgIds 组织id集合
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUserOrg t where t.orgId in (:orgIds)")
    void deleteEntitysByOrgIds(@Param("orgIds") List<String> orgIds);

    /**
     * 删除
     *
     * @param departmentIds 部门id集合
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUserOrg t where t.departmentId in (:departmentIds)")
    void deleteEntitysByDepartmentIds(@Param("departmentIds") List<String> departmentIds);

    /**
     * 修改用户组织部门
     *
     * @param userCde         用户编码
     * @param orgId           组织id
     * @param departmentId    部门id
     * @param oldOrgId        改前组织id
     * @param oldDepartmentId 改前部门id
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUserOrg t set t.orgId = :orgId, t.departmentId = :departmentId" +
           " where t.userCode = :userCode and t.orgId = :oldOrgId and t.departmentId = :oldDepartmentId")
    void updateEntityByOrgInfo(
            @Param("userCode") String userCode,
            @Param("orgId") String orgId,
            @Param("departmentId") String departmentId,
            @Param("oldOrgId") String oldOrgId,
            @Param("oldDepartmentId") String oldDepartmentId
    );

    /**
     * 修改用户名称
     *
     * @param userCde  用户编码
     * @param userName 用户名称
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUserOrg t set t.userName = :userName where t.userCode = :userCode")
    void updateUserNameByUserCode(@Param("userCode") String userCode, @Param("userName") String userName);

    //    void deleteByPostId(String postId);
}
