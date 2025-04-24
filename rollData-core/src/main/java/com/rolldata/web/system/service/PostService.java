package com.rolldata.web.system.service;


import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.entity.SysPost;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.SysPostDetailedInfo;
import com.rolldata.web.system.pojo.SysPostTreeResponse;

import java.util.List;

/**
 * @Title:
 * @Description: 职务服务层
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-11-30
 * @version:V1.0
 */
public interface PostService {

    /**
     * 保存前校验名称代码是否重复
     *
     * @param ajax     前台数据容器
     * @param postName 职务名称
     * @param postCode 职务代码
     * @param id       职务id-新建时传null
     * @throws Exception
     */
    void before(AjaxJson ajax, String postName, String postCode, String id) throws Exception;

    /**
     * 创建职务
     * @param sysPostDetailedInfo 前台传递的职务信息容器
     * @return
     * @throws Exception
     */
    SysPostTreeResponse createPost(SysPostDetailedInfo sysPostDetailedInfo) throws Exception;

    /**
     * 修改职务
     *
     * @param sysPostDetailedInfo 前台传递的职务信息容器
     * @return
     * @throws Exception
     */
    SysPostTreeResponse updatePost(SysPostDetailedInfo sysPostDetailedInfo) throws Exception;

    /**
     * 删除职务
     *
     * @param ids 职务id集合
     * @throws Exception
     */
    void delete(List<String> ids) throws Exception;

    /**
     * 查询职务详细
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    SysPostDetailedInfo queryPostDetailedInfo(String postId) throws Exception;

    /**
     * 查询全部职务,结果在treeNodes
     *
     * @return
     * @throws Exception
     */
    SysPostTreeResponse queryPostList() throws Exception;

    /**
     * 查询全部职务（同步用户用）
     * @return
     * @throws Exception
     */
    List<SysPost> queryAllPost() throws Exception;
    
    /**
     * 查询组织下用户的职务
     *
     * @param users 用户集合
     * @return
     * @throws Exception
     */
    List<SysPost> queryUserPosts(List<SysUser> users) throws Exception;

    /**
     * 查询职务用户列表
     *
     * @param users   用户列表
     * @param postIds 职务id集合
     * @return
     * @throws Exception
     */
    List<SysUser> queryUsersByPostIds(List<SysUser> users, List<String> postIds) throws Exception;
    
    /**
     * 新建职务（同步用户用）
     * @param sysPost 
     * @return
     * @throws Exception
     */
    SysPost savePost(SysPost sysPost) throws Exception;

    /**
     * 根据名称创建职务(编码自动生成)
     *
     * @param name 职务名称
     * @return
     * @throws Exception
     */
    SysPost createPostByName(String name) throws Exception;
}
