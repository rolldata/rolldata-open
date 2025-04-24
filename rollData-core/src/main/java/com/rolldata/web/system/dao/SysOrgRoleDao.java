package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysOrgRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 角色组织表dao层接口
 * @author shenshilong
 * @createDate 2018-6-5
 */
public interface SysOrgRoleDao extends JpaRepository<SysOrgRole, String>, JpaSpecificationExecutor<SysOrgRole>{

    /**
     * 根据角色id查询实体集合
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<SysOrgRole> findSysOrgRolesByRoleId(String roleId) throws Exception;

    /**
     * 根据角色id删除
     * @param roleId
     * @throws Exception
     * @createDate 2018-6-6
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysOrgRole s where s.roleId = :roleId")
    public void deleteSysOrgRoleByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 根据用户所有的角色id查询并去重
     * @param roleIds
     * @return List<String>
     * @throws Exception
     * @createDate 2018-6-6
     */
    public List<SysOrgRole> findByRoleIdIn(String[] roleIds) throws Exception;

    /**
     * 根据orgId查询roleId集合
     */
    @Query(value="select o.roleId from SysOrgRole o where o.orgId = :orgId")
    public List<String> getRoleIdByOrgId(@Param("orgId") String orgId) throws Exception;

    /**
     * 根据组织id查询数据集合
     * @param orgId 组织id
     * @return
     * @throws Exception
     */
    @Query(value="select o from SysOrgRole o where o.orgId = :orgId")
    public List<SysOrgRole> querySysOrgRolesByOrgId(@Param("orgId") String orgId) throws Exception;

    /**
     * 根据组织id删除关联数据
     * @param orgId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysOrgRole s where s.orgId = :orgId ")
    public void deleteSysOrgRoleByOrgId(@Param("orgId") String orgId) throws Exception;
}
