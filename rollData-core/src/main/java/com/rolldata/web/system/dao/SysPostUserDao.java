package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysPostUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: SysPostUserDao
 * @Description: 职务归入用户操作工厂
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
public interface SysPostUserDao extends JpaRepository<SysPostUser, String> {

    /**
     * 通过职务id删除
     *
     * @param postId 职务id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostUser u where u.postId = :postId")
    void deleteByPostId(@Param("postId") String postId) throws Exception;

    /**
     * 用户ID集合删除数据
     *
     * @param userIds 用户id集合
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostUser u where u.userId in (:userIds)")
    void deleteByUserIds(@Param("userIds") List<String> userIds) throws Exception;

    /**
     * 通过职务id和用户id集合删除
     *
     * @param postId     职务id
     * @param userIdList 用户id集合
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPostUser u where u.postId = :postId and u.userId in (:userIdList)")
    void deleteByPostIdAndUserIds(@Param("postId") String postId, @Param("userIdList") List<String> userIdList)
                              throws Exception;
}
