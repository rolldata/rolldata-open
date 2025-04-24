package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysPostMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: SysPostMenuDao
 * @Description: 职务菜单功能权限操作工厂
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
public interface SysPostMenuDao extends JpaRepository<SysPostMenu, String> {

    /**
     * 通过职务id查询集合
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    List<SysPostMenu> querySysPostMenusByPostId(String postId) throws Exception;

    /**
     * 通过职务id删除
     *
     * @param postId 职务id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostMenu u where u.postId = :postId")
    void deleteByPostId(@Param("postId") String postId) throws Exception;

    @Modifying(clearAutomatically = true)
    @Query("delete from SysPostMenu s where s.relationId in (:resourceIds)")
    void deletePostMenuByRelatitonIds(@Param("resourceIds") List<String> resourceIds);

    /**
     * 删除关联的菜单
     *
     * @param relationId 关联id
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysPostMenu s where s.relationId = :resourceId")
    void deleteByResourceId(@Param("resourceId") String resourceId);
}
