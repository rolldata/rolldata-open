package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: SysRoleMenuDao
 * @Description: 角色目录权限表 工厂类
 * @Company:www.wrenchdata.com
 * @author shenshilong[shilong_shen@163.com]
 * @date 2019-5-13
 * @version V1.0
 */
public interface SysRoleMenuDao extends JpaRepository<SysRoleMenu, String>{
    
    /**
     * 根据角色id删除实体数据
     * @param roleId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysRoleMenu s where s.roleId = :roleId")
    public void deleteByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 根据角色查询实体集合
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    @Query("select s from SysRoleMenu s where s.roleId = :roleId")
    public List<SysRoleMenu> queryByRoleId(@Param("roleId") String roleId) throws Exception;

	@Modifying(clearAutomatically = true)
    @Query("delete from SysRoleMenu s where s.roleId in (:roleIds) and s.relationId in (:resourceIds)")
    public void deleteByRoleIdsAndRelatitonIds(@Param("roleIds") List<String> roleIds, @Param("resourceIds") List<String> resourceIds);

	@Modifying(clearAutomatically = true)
    @Query("delete from SysRoleMenu s where s.relationId in (:resourceIds)")
    public void deleteRoleMenuByRelatitonIds(@Param("resourceIds") List<String> deleteResourceIds);

    /**
     * 根据关联关系id查询结果集
     *
     * @param relationId 关联关系id
     * @return
     */
    @Query("select s from SysRoleMenu s where s.relationId = :relationId")
	List<SysRoleMenu> querySysRoleMenusByRelationId(@Param("relationId") String relationId);

    /**
     * 根据关联id删除
     *
     * @param resourceId 资源id
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysRoleMenu s where s.relationId = :resourceId")
    void deleteByResourceId(@Param("resourceId") String resourceId);
}
