package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysFunctionOper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** 
 * @Title: SysFunctionOperDao
 * @Description: 功能明细 Dao
 * @Company:www.wrenchdata.com 
 * @author shenshilong
 * @date 2018/6/5
 * @version V1.0  
 */
public interface SysFunctionOperDao extends JpaRepository<SysFunctionOper, String> {

    /**
     * 根据菜单id查询拥有的按钮
     * @param funcId 菜单id
     * @return
     * @throws Exception
     */
    List<SysFunctionOper> findSysFunctionOperByFuncId(String funcId) throws Exception;

    /**
     * 获取当前用户授权按钮
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo WHERE sfo.id IN ( SELECT srp.powerId FROM SysRolePower srp WHERE srp.powerType = '2' AND srp.roleId IN ( SELECT sor.roleId FROM UserRole sor WHERE sor.userId = :userId )) order by sfo.createTime asc ")
    List<SysFunctionOper> findFuncnOperByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 获取所有授权按钮
     *
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo order by sfo.createTime asc ")
    List<SysFunctionOper> findFuncnOper() throws Exception;

    /**
     * 获取当前用户授权按钮
     *
     * @param funcId 菜单id
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo WHERE sfo.funcId = :funcId and sfo.id IN (" +
                   " SELECT srp.powerId FROM SysRolePower srp WHERE srp.powerType = '2' AND srp.roleId IN" +
                   " ( SELECT sor.roleId FROM UserRole sor WHERE sor.userId = :userId )) order by sfo.createTime asc ")
    List<SysFunctionOper> findFuncnOperByUserId(
            @Param("funcId") String funcId,
            @Param("userId") String userId
    ) throws Exception;

    /**
     * 查询admin可控按钮
     *
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo where sfo.funcId = :funcId order by sfo.createTime asc ")
    List<SysFunctionOper> queryAdminSysFunctionOperList(@Param("funcId") String funcId) throws Exception;

    /**
     * 查询admin可控按钮
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo order by sfo.createTime asc ")
    List<SysFunctionOper> queryAdminSysFunctionOperList() throws Exception;

    /**
     * 查询按钮权限
     * <br>通过用户的职务
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sfo FROM SysFunctionOper sfo WHERE sfo.id IN ( SELECT srp.powerId FROM SysPostPower srp"
                   + " WHERE srp.powerType = '2' AND srp.postId = ( SELECT sor.position FROM SysUser sor WHERE"
                   + " sor.id = :userId )) order by sfo.createTime asc ")
    List<SysFunctionOper> findPostFuncnOperByUserId(@Param("userId") String userId) throws Exception;

}
