package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysRolePower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** 
 * @Title: SysRolePowerDao
 * @Description: 角色权限表 DAO
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/4
 * @version V1.0  
 */
public interface SysRolePowerDao extends JpaRepository<SysRolePower, String>{

    public List<SysRolePower> getSysRolePowerByRoleId(String roleId) throws Exception;
    
    /**
     * 根据角色id删除
     * @param roleId
     * @throws Exception
     */
    @Modifying
    @Query("delete from SysRolePower srp where srp.roleId = :roleId")
    public void deleteSysRolePowerByRoleId(@Param("roleId") String roleId) throws Exception;
    
    /**
     * 根据菜单id删除
     * @param powerId
     * @throws Exception
     */
    @Modifying
    @Query("delete from SysRolePower srp where srp.powerId = :powerId")
    public void deleteSysRolePowerByPowerId(@Param("powerId") String powerId) throws Exception;
    
    /**
     * 根据角色id查询拥有的菜单
     * @param roleId
     * @param powerType
     * @return
     * @throws Exception
     */
    @Query(value = "select fsr.powerId from SysRolePower fsr where fsr.powerType = :powerType and fsr.roleId = :roleId", nativeQuery = true)
    public List findRolePower(@Param("roleId") String roleId, @Param("powerType") String powerType) throws Exception;

    /**
     * 根据角色id查询拥有的菜单
     * @param roleId
     * @param powerType
     * @return
     * @throws Exception
     */
    @Query(value = "select fsr from SysRolePower fsr where fsr.powerType = :powerType and fsr.roleId = :roleId", nativeQuery = true)
    public List<SysRolePower> findSysRolePowers(@Param("roleId") String roleId, @Param("powerType") String powerType) throws Exception;

    /**
     * 用户可见菜单
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query("select srp from SysRolePower srp where srp.roleId in ( select ur.roleId from UserRole ur, SysUser su where "
           + " ur.userId = su.id and su.id = :userId )")
    List<SysRolePower> querySysRolePowerListByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 用户可见菜单和按钮id集合
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query("select srp.powerId from SysRolePower srp where srp.roleId in ( select ur.roleId from UserRole ur, SysUser su where "
           + " ur.userId = su.id and su.id = :userId )")
    List<String> queryPowerIdListByUserId(@Param("userId") String userId) throws Exception;

}
