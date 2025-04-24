package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Title: PostDao
 * @Description: 职务操作工厂
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-11-30
 * @version:V1.0
 */
public interface PostDao extends JpaRepository<SysPost, String> {

    /**
     * 通过id获取职务对象
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    SysPost querySysPostById(@Param("postId") String postId) throws Exception;

    /**
     * 通过职务代码查询职务实体
     * <br><b>注：如果查出来数据有2条,则报错
     *
     * @param postCode 职务代码
     * @return
     * @throws Exception
     */
    SysPost querySysPostByPostCode(@Param("postCode") String postCode) throws Exception;

    /**
     * 通过职务名称查询职务实体
     * <br><b>注：如果查出来数据有2条,则报错
     *
     * @param postName 职务名称
     * @return
     * @throws Exception
     */
    SysPost querySysPostByPostName(@Param("postName") String postName) throws Exception;

    /**
     * 修改职务信息
     *
     * @param postCode   职务代码
     * @param postName   职务名称
     * @param updateTime 更新时间
     * @param updateUser 更新用户
     * @param id         职务id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update SysPost u set u.postCode = :postCode, u.postName = :postName,"
                   + " u.updateTime = :updateTime, u.updateUser =:updateUser where u.id = :id")
    void update(@Param("postCode") String postCode, @Param("postName") String postName,
                @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser, @Param("id") String id)
                              throws Exception;

    /**
     * 删除职务
     *
     * @param postId 职务id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from SysPost sp where sp.id = :postId")
    void deleteSysPostById(@Param("postId") String postId) throws Exception;

    /**
     * 查询全部职务集合
     * <br>更新时间排序
     *
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sp FROM SysPost sp order by sp.createTime asc ")
    List<SysPost> querySysPostList() throws Exception;

    /**
     * 查询用户包含的职务
     *
     * @param userIds 用户id集合
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT sp FROM SysPost sp where sp.id in (:userIds) order by sp.createTime asc ")
    List<SysPost> querySysPostByUserIds(@Param("userIds") List<String> userIds) throws Exception;
}
