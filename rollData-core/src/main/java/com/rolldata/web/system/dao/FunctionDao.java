package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Title: FunctionDao
 * @Description: 功能菜单Dao层 接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
public interface FunctionDao extends JpaRepository<SysFunction, String>, JpaSpecificationExecutor<SysFunction> {

    /**
     * 用户可管理的菜单
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.isSystem = '0'" +
                              "and (fsf.id in (select srp.powerId from SysRolePower srp where srp.powerType = '1' " +
                              "and srp.roleId in (select sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId " +
                              "and sor.userId = :userId and sysRole.state = '1'))  OR fsf.id IN ( " +
                              "SELECT menu.relationId FROM SysRoleMenu menu WHERE menu.roleId IN ( " +
                              "SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE sysrole2_.id = userrole3_.roleId " +
                              "AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))) order by fsf.sort asc")
    public List<SysFunction> findSysFunctionOrderBySort(@Param("userId") String userId) throws Exception;

    /**
     * 查admin数据并排序
     * @param parentId
     * @return
     */
    @Query(value = "select sf from SysFunction sf where sf.state = '1' and sf.isSystem = '0' ORDER BY sf.parentId, sf.sort asc")
    public List<SysFunction> findAdminSysFunctionOrderBySort() throws Exception;

    /**
     * 根据父id查菜单
     * @param parentId 父id
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.parentId = :parentId and sf.state = '1' ORDER BY sf.sort asc")
    public List<SysFunction> findChildren(@Param("parentId") String parentId) throws Exception;

    /**
     * 根据父id和是否管理端查菜单
     * @param parentId
     * @param isAdmin
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.parentId = :parentId and sf.isAdmin = :isAdmin and sf.state = '1' ORDER BY sf.sort asc")
    public List<SysFunction> findChildren(@Param("parentId") String parentId, @Param("isAdmin") String isAdmin) throws Exception;

    /**
     * 根据父id 和 状态 查菜单
     * @param parentId 父id
     * @param state 状态
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf  where sf.parentId = :parentId and sf.state = :state ORDER BY sf.sort asc")
    public List<SysFunction> findAvailableChildren(@Param("parentId") String parentId, @Param("state") String state) throws Exception;

    /**
     * 根据用户id 查菜单
     * @param userId
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and ( fsf.id in (select srp.powerId from " +
                   "SysRolePower srp where srp.powerType = '1' and srp.roleId in (select sor.roleId from UserRole sor" +
                   " where sor.userId = :userId)) or fsf.id in ( SELECT menu.relationId FROM SysRoleMenu menu " +
        " WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE" +
        " sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))) ORDER BY fsf.sort asc")
    public List<SysFunction> findFuncByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 所有菜单
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' ORDER BY fsf.sort asc")
    public List<SysFunction> findFunc() throws Exception;

    /**
     * 根据父id查用户拥有的菜单(PC)
     *
     * @param parentId 父id
     * @param userId   用户id
     * @param postId   职位id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and" +
                   " (fsf.terminalPC is null or fsf.terminalPC = '' or fsf.terminalPC = '1') and (fsf.id in" +
                   " (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select " +
                   "sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "or fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) OR fsf.id IN ( SELECT menu.relationId FROM SysPostMenu menu " +
                   "WHERE menu.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> findTerminalPCOwnFuncByUserId(
            @Param("parentId") String parentId,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 根据父id查用户拥有的菜单(PC)
     *
     * @param parentId 父id
     * @param userId   用户id
     * @param postId   职位id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and fsf.isAdmin = :isAdmin" +
            " and (fsf.terminalPC is null or fsf.terminalPC = '' or fsf.terminalPC = '1') and (fsf.id in" +
            " (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select " +
            "sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
            ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
            "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
            "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
            "or fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
            " srp.postId = :postId) OR fsf.id IN ( SELECT menu.relationId FROM SysPostMenu menu " +
            "WHERE menu.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> findTerminalPCOwnFuncByUserId(
            @Param("parentId") String parentId,
            @Param("userId") String userId,
            @Param("postId") String postId,
            @Param("isAdmin") String isAdmin
    ) throws Exception;

    /**
     * 根据父id查用户拥有的菜单(ipad)
     *
     * @param parentId 父id
     * @param userId   用户id
     * @param postId   职位id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and" +
                   " fsf.terminalIpad = '1' AND (fsf.id in" +
                   " (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select " +
                   "sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   " or fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) OR fsf.id IN ( SELECT menu.relationId FROM SysPostMenu menu" +
                   " WHERE menu.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> findTerminalIpadOwnFuncByUserId(
            @Param("parentId") String parentId,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 根据父id查用户拥有的菜单(移动)
     *
     * @param parentId 父id
     * @param userId   用户id
     * @param postId   职位id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and" +
                   " fsf.terminalMobile = '1' and (fsf.id in" +
                   " (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select " +
                   "sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   " or fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) OR fsf.id IN ( SELECT menu.relationId FROM SysPostMenu menu WHERE" +
                   " menu.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> findTerminalMobileOwnFuncByUserId(
            @Param("parentId") String parentId,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 通过用户id和菜单父节点id查询菜单(pc)
     * <br>通过职务id
     *
     * @param parentId 菜单父id
     * @param userId   用户id
     * @return
     * @throws Exception
     */
    /*@Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and"
                   + " (fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId)  OR fsf.id IN ( SELECT menu.relationId"
                   + " FROM SysPostMenu menu WHERE menu.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalPCFuncByUserId(
        @Param("parentId") String parentId,
        @Param("userId") String userId,
        @Param("postId") String postId
    ) throws Exception;*/

    /**
     * 通过用户id和菜单父节点id查询菜单(ipad)
     * <br>通过职务id
     *
     * @param parentId 菜单父id
     * @param userId   用户id
     * @return
     * @throws Exception
     */
    /*@Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and"
                   + " (fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and srp.postId"
                   + " in (select sysPost.id from SysPost sysPost, SysUser sor where sysPost.id = sor.position"
                   + "  AND sor.id = :userId ))  OR fsf.id IN ( SELECT menu.relationId"
                   + " FROM SysPostMenu menu WHERE menu.postId IN ( SELECT sysrole2_.id FROM SysPost sysrole2_ ,"
                   + " SysUser userrole3_ WHERE sysrole2_.id = userrole3_.position AND sysrole2_.id = :postId"
                   + "  ))) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalIpadFuncByUserId(
        @Param("parentId") String parentId,
        @Param("userId") String userId,
        @Param("postId") String postId
    ) throws Exception;*/

    /**
     * 通过用户id和菜单父节点id查询菜单(移动端)
     * <br>通过职务id
     *
     * @param parentId 菜单父id
     * @param userId   用户id
     * @return
     * @throws Exception
     */
    /*@Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and fsf.parentId = :parentId and"
                   + " (fsf.id in (select srp.powerId from SysPostPower srp where srp.powerType = '1' and srp.postId"
                   + " in (select sysPost.id from SysPost sysPost, SysUser sor where sysPost.id = sor.position"
                   + "  AND sor.id = :userId ))  OR fsf.id IN ( SELECT menu.relationId"
                   + " FROM SysPostMenu menu WHERE menu.postId IN ( SELECT sysrole2_.id FROM SysPost sysrole2_ ,"
                   + " SysUser userrole3_ WHERE sysrole2_.id = userrole3_.position AND sysrole2_.id = :postId"
                   + "  ))) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalMobileFuncByUserId(
        @Param("parentId") String parentId,
        @Param("userId") String userId,
        @Param("postId") String postId
    ) throws Exception;*/


    /**
     * 修改菜单
     * @param id
     * @param funcName 菜单名称
     * @param parentId 父id
     * @param hrefLink 链接
     * @param sort 排序
     * @param type 类型
     * @param relationId 表单id,报表id
     * @param updateTime 修改时间
     * @param updateUser 修改人
     */
    @Modifying
    @Query("update SysFunction sf set sf.funcName = :funcName, sf.parentId = :parentId, sf.hrefLink = :hrefLink, sf.sort = :sort, sf.type = :type, sf.relationId = :relationId, sf.updateTime = :updateTime, sf.updateUser = :updateUser where sf.id = :id")
    public void update(@Param("id") String id, @Param("funcName") String funcName, @Param("parentId") String parentId, @Param("hrefLink") String hrefLink, @Param("sort") Integer sort, @Param("type") String type, @Param("relationId") String relationId, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser) throws Exception;

    /**
     * 查询所有非系统目录
     * @param notIsSystem =1
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.state = '1' and sf.isSystem = :notIsSystem" +
                   " ORDER BY sf.sort asc")
	public List<SysFunction> queryAllMenus(@Param("notIsSystem") String notIsSystem) throws Exception;

    /**
     * 查询所有非系统目录，带模糊匹配目录名称
     * @param notIsSystem =1
     * @param functionName 模糊匹配目录名称
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.state = '1' and sf.isSystem = :notIsSystem and sf.funcName like :functionName " +
            " ORDER BY sf.sort asc")
    public List<SysFunction> queryAllMenus(@Param("notIsSystem") String notIsSystem, @Param("functionName") String functionName) throws Exception;

    /**
     * 查询角色配置非管理员的非系统目录，带模糊匹配目录名称
     * @param notIsSystem =1
     * @param userId
     * @param postId
     * @param functionName 模糊匹配目录名称
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.funcName like :functionName and fsf.state = '1' and (fsf.id" +
                   " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
                   " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
                   " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryRoleAllMenus(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId, @Param("functionName") String functionName
    ) throws Exception;

    /**
     * 查询角色配置非管理员的非系统目录
     *
     * @param notIsSystem =1
     * @param userId      用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.state = '1' and (fsf.id" +
            " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
            " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
            ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
            "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
            "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
            "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
            " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
            " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryRoleAllMenus(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 查询PC非管理员的非系统目录
     *
     * @param notIsSystem =1
     * @param userId      用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.state = '1' " +
                   " and (fsf.terminalPC is null or fsf.terminalPC = '' or fsf.terminalPC = '1') and (fsf.id" +
                   " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
                   " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
                   " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalPCAllMenus(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 查询ipad非管理员的非系统目录
     *
     * @param notIsSystem =1
     * @param userId      用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.state = '1'" +
                   " and fsf.terminalIpad = '1' and (fsf.id" +
                   " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
                   " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
                   " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalIpadAllMenus(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 目录管理(不带终端控制)
     *
     * @param notIsSystem =1
     * @param userId      用户id
     * @param postId      职务id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.state = '1'" +
                   " and (fsf.id" +
                   " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
                   " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
                   " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryMenuManageTree(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 查询非管理员的非系统目录
     *
     * @param notIsSystem =1
     * @param userId      用户id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.isSystem = :notIsSystem and fsf.state = '1'" +
                   " and fsf.terminalMobile = '1' and (fsf.id" +
                   " in (select srp.powerId from SysRolePower srp where srp.powerType = '1' and srp.roleId in (select" +
                   " sysRole.id from SysRole sysRole, UserRole sor where sysRole.id = sor.roleId and sor.userId = " +
                   ":userId and sysRole.state = '1'))  OR fsf.id IN ( SELECT menu.relationId FROM SysRoleMenu menu " +
                   "WHERE menu.roleId IN ( SELECT sysrole2_.id FROM SysRole sysrole2_ , UserRole userrole3_ WHERE " +
                   "sysrole2_.id = userrole3_.roleId AND userrole3_.userId = :userId AND sysrole2_.state = '1' ))" +
                   "OR fsf.id IN (select srp.powerId from SysPostPower srp where srp.powerType = '1' and" +
                   " srp.postId = :postId) or fsf.id in (select spm.relationId from SysPostMenu spm where" +
                   " spm.postId = :postId)) order by fsf.sort asc ")
    public List<SysFunction> queryTerminalMobileAllMenus(
            @Param("notIsSystem") String notIsSystem,
            @Param("userId") String userId,
            @Param("postId") String postId
    ) throws Exception;

    /**
     * 根据菜单id查询详情
     * @param menuId
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.id = :menuId")
	public SysFunction queryMenuInfo(@Param("menuId") String menuId) throws Exception;

    /**
     * 根据id删除目录
     * @param id
     * @throws Exception
     */
    @Modifying
    @Query("delete from SysFunction sf where sf.id = :id")
	public void deleteSysFunctionById(@Param("id") String id) throws Exception;

    /**
     * 修改单个目录信息
     *
     * @param id             主键
     * @param name           目录名称
     * @param terminalPC     pc终端是否显示
     * @param terminalIpad   ipad终端是否显示
     * @param terminalMobile 移动端是否显示
     * @param date           修改日期
     * @param userId         修改人
     * @throws Exception
     */
    @Modifying
    @Query("update SysFunction sf set sf.funcName = :name, sf.updateTime = :updateTime, sf.updateUser = :updateUser" +
           " ,sf.terminalPC = :terminalPC, sf.terminalIpad = :terminalIpad, sf.terminalMobile = :terminalMobile" +
           " ,sf.businessType = :businessType where sf.id = :id")
    public void updateMenuById(
            @Param("id") String id,
            @Param("name") String name,
            @Param("terminalPC") String terminalPC,
            @Param("terminalIpad") String terminalIpad,
            @Param("terminalMobile") String terminalMobile,
            @Param("businessType") String businessType,
            @Param("updateTime") Date date,
            @Param("updateUser") String userId
    )throws Exception;

    /**
     * 根据名称查询结果集
     * @param funcName
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.funcName = :funcName ORDER BY sf.sort asc")
	public List<SysFunction> queryFunctionObjByName(@Param("funcName") String funcName) throws Exception;

    /**
     * 根据名称和非自身id查询结果集
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    @Query(value = "select sf from SysFunction sf where sf.funcName = :funcName and sf.id <> :id")
	public List<SysFunction> queryFunctionObjByNameAndNotOwn(@Param("funcName") String name, @Param("id") String id) throws Exception;

    /**
     * 根据类型获取最大的序号
     * @param notIsSystem
     * @return
     * @throws Exception
     */
    @Query(value = "select COALESCE(MAX(sf.sort),0) from SysFunction sf where sf.isSystem = :notIsSystem")
	public int queryFunctionMaxSort(@Param("notIsSystem") String notIsSystem) throws Exception;

    /**
     * 根据id修改目录排序号
     * @param id
     * @param sort
     * @param userId
     * @param date
     * @throws Exception
     */
    @Modifying
    @Query("update SysFunction sf set sf.sort = :sort, sf.updateTime = :updateTime, sf.updateUser = :updateUser where sf.id = :id")
    public void updateSortById(@Param("id") String id, @Param("sort") Integer sort, @Param("updateUser") String userId, @Param("updateTime") Date date) throws Exception;

    /**
     * 通过用户id查询菜单
     * <br>通过职务
     *
     * @param postId 职位id
     * @return
     * @throws Exception
     */
    @Query(value = "select fsf from SysFunction fsf where fsf.state = '1' and (fsf.id in (select srp.powerId from " +
                   "SysPostPower srp where srp.powerType = '1' and srp.postId = :postId) or fsf.id in (select" +
                   " spm.relationId from SysPostMenu spm where spm.postId = :postId))")
    List<SysFunction> findPostFuncByUserId(
            @Param("postId") String postId
    ) throws Exception;

}
