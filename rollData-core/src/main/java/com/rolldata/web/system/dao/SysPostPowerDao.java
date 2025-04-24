package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysPostPower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: SysPostPowerDao
 * @Description: 职务菜单和按钮权限操作工厂
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
public interface SysPostPowerDao extends JpaRepository<SysPostPower, String> {

    /**
     * 通过职务id查询集合
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    List<SysPostPower> querySysPostPowersByPostId(String postId) throws Exception;

    /**
     * 查询该职务可管理的菜单和按钮id集合
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    @Query(value = "select spp.powerId from SysPostPower spp where spp.postId = :postId")
    List<String> queryPowerIdList(@Param("postId") String postId) throws Exception;

    /**
     * 通过职务id删除
     * @param postId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostPower u where u.postId = :postId")
    void deleteByPostId(@Param("postId") String postId) throws Exception;

    /**
     * 删除关联的菜单和按钮
     * <br>条件:职务id和菜单集合
     *
     * @param postId          职务id
     * @param postPowerIdList 菜单按钮集合
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostPower u where u.postId = :postId and u.powerId in (:postPowerIdList)")
    void deleteByPostIdAndPowerIdList(
            @Param("postId") String postId,
            @Param("postPowerIdList") List<String> postPowerIdList
    ) throws Exception;

    /**
     * 根据菜单id删除
     *
     * @param powerId
     * @throws Exception
     */
    @Modifying
    @Query("delete from SysPostPower srp where srp.powerId = :powerId")
    public void deleteSysPostPowerByPowerId(@Param("powerId") String powerId) throws Exception;
}
