package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * @Title:RoleDao
 * @Description:角色操作工厂类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-30
 * @version V1.0
 */
public interface RoleDao extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {
    
//    public SysRole findById(@Param("roleId")String roleId) throws Exception;

    /**
     * 根据角色id更新信息
     *
     * @param id
     * @param roleCde    角色代码
     * @param roleName   角色名称
     * @param updateTime 修改时间
     * @param updateUser 修改人
     * @param state      状态
     * @param remark     备注
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update SysRole u set u.roleCde = :roleCde,u.roleName = :roleName,u.updateTime = :updateTime," +
                   "u.updateUser =:updateUser,u.state = :state,u.remark = :remark,u.isAdmin = :isAdmin where u.id = :id")
    public void updateById(
            @Param("id") String id,
            @Param("roleCde") String roleCde,
            @Param("roleName") String roleName,
            @Param("updateTime") Date updateTime,
            @Param("updateUser") String updateUser,
            @Param("state") String state,
            @Param("remark") String remark,
            @Param("isAdmin") String isAdmin
    ) throws Exception;

    /**
     *  修改状态
     * @param id
     * @param state 角色状态
     * @param updateTime 修改时间
     * @param updateUser 修改人
     * @throws Exception
     */
    @Modifying
    @Query(value = "update SysRole u set u.updateTime = :updateTime,u.updateUser =:updateUser,u.state = :state where u.id = :id")
    public void updateStatus(@Param("id") String id, @Param("state") String state, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser) throws Exception;

    /**
     * 根据角色cde 查角色
     * @param roleCde 角色代码
     * @return
     * @throws Exception
     */
    public SysRole getSysRoleByRoleCde(@Param("roleCde") String roleCde) throws Exception;

    /**
     * 名称查实体
     * @param name
     * @return
     * @throws Exception
     */
    public SysRole querySysRoleByRoleName(String name) throws Exception;

    /**
     * 根据用户id 查询可用角色
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select sr from SysRole sr where sr.state = '1' and  sr.id in (select ur.roleId from UserRole ur where ur.userId = :userId) order by sr.createTime asc ")
    public List<SysRole> getSysRolesByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 删除角色
     * @param id
     */
    @Modifying
    @Query( value = "delete from SysRole sr where sr.id = :id")
    public void deleteSysRoleById(@Param("id") String id) throws Exception;

    /**
     * 根据状态查角色列表(过滤掉系统配置的角色)
     * @param state
     * @param roleCde
     * @return
     * @throws Exception
     */
    public List<SysRole> queryAllByStateAndRoleCdeNotOrderByCreateTimeAsc(String state, String roleCde) throws Exception;

    /**
     * 查询全部角色(角色管理)
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT r FROM SysRole r order by r.createTime asc ")
    public List<SysRole> queryAllOrderByCreateTimeAsc() throws Exception;

    /**
     * 非管理员可见角色
     * @param userIdPermissionList 可管理的用户id集合
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT r FROM SysRole r where r.createUser in (:userIdPermissionList) order by r.createTime asc ")
    public List<SysRole> queryAllOrderByCreateTimeAsc(@Param("userIdPermissionList") List<String> userIdPermissionList) throws Exception;
}
