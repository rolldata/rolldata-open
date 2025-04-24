package com.rolldata.web.system.dao;


import com.rolldata.web.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** 
 * @Title: UserRoleDao
 * @Description: UserRoleDao
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/31
 * @version V1.0  
 */
public interface UserRoleDao extends JpaRepository<UserRole, String> {
    
    /**
     * 根据角色id删除关联用户
     * @param roleId
     * @throws Exception
     */
    @Modifying
    @Query( value = " delete from UserRole ur where ur.roleId = :roleId " )
    public void deleteByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 通过userId获取roleId集合
     * @author shenshilong
     * @param userId
     * @return
     * @throws Exception
     * @createDate 2018-7-30
     */
    @Query(value = "select ur.roleId from UserRole ur where ur.userId = :userId")
    public List<String> getUserRoleByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 通过userId删除UserRole对象
     * @author shenshilong
     * @param userId
     * @return
     * @throws Exception
     * @createDate 2018-7-30
     */
    @Modifying
    @Query("delete from UserRole ur where ur.userId = :userId")
    public void deleteUserRoleByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 删除用户关联
     *
     * @param userIds 用户id集合
     * @throws Exception
     */
    @Modifying
    @Query("delete from UserRole ur where ur.userId in (:userIds)")
    public void deleteUserRoleByUserIds(@Param("userIds") List<String> userIds) throws Exception;

    /**
     * 通过用户id集合 删除UserRole
     *
     * @param roleId
     * @param userIds
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from UserRole ur where ur.roleId = :roleId and ur.userId in (:userIds)")
    public void deleteUserRoleByUserIds(@Param("roleId") String roleId, @Param("userIds") List<String> userIds) throws Exception;

    /**
     * 根据角色id 用户id 查关联数据
     * (原始sql在注释里)
     *
     * @param roleId
     * @param userId
     * @return
     * @throws Exception
     */
    // @Query(value = " SELECT userrole0_.userId FROM UserRole userrole0_ WHERE userrole0_.roleId = :roleId AND userrole0_.userId IN ( SELECT sysuser0_.id FROM SysUser sysuser0_ WHERE sysuser0_.orgId IN ( SELECT sysorg1_.id FROM SysOrg sysorg1_ WHERE ( sysorg1_.id IN ( SELECT sysorgrole2_.orgId FROM SysOrgRole sysorgrole2_ WHERE sysorgrole2_.roleId IN ( SELECT userrole3_.roleId FROM UserRole userrole3_, SysRole sysrole4_ WHERE userrole3_.roleId = sysrole4_.id AND sysrole4_.state = '1' AND userrole3_.userId = :userId ))))) ")
    @Query(value = "select distinct userrole0_.userId from UserRole userrole0_ , SysUser sysuser1_, SysOrgRole" +
        " sysorgrole3_, UserRole userrole4_ , SysRole sysrole5_ where userrole0_.roleId " +
        "=:roleId and sysuser1_.orgId = sysorgrole3_.orgId and sysorgrole3_.roleId  = " +
        "userrole4_.roleId and userrole4_.roleId=sysrole5_.id and sysrole5_.state='1' and userrole4_" +
        ".userId=:userId and userrole0_.userId = sysuser1_.id")
    public List<String> getUserRoleByRoleId(@Param("roleId") String roleId, @Param("userId") String userId) throws Exception;

    /**
     * 根据角色id 用户id
     *
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT distinct userrole0_.userId FROM UserRole userrole0_ WHERE userrole0_.roleId = :roleId ")
    public List<String> getUserRoleByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 角色id查询用户id集合
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    @Query(value = " SELECT ur.userId FROM UserRole ur where ur.roleId = :roleId")
    public List<String> queryUserIdsByRoleId(@Param("roleId") String roleId) throws Exception;
}
