package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysPost;

import java.util.List;

/**
 * @Title: PostDetailedInfo
 * @Description: PostDetailedInfo
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-11-30
 * @version: V1.0
 */
public class SysPostDetailedInfo extends SysPost {

    private static final long serialVersionUID = -2685249878449407981L;

    /**
     * 职务id(其实就是id,防止前台歧义给他一个)
     */
    private String postId;

    /**
     * 职务授权
     */
    private SysPostAuthorize postAuthorize;

    /**
     * 归入用户
     */
    private UserAuthorize userAuthorize;

    /**
     * 删除id集合
     */
    private List<String> ids;

    public String getPostId() {
        return postId;
    }

    public SysPostDetailedInfo setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public SysPostAuthorize getPostAuthorize() {
        return postAuthorize;
    }

    public SysPostDetailedInfo setPostAuthorize(SysPostAuthorize postAuthorize) {
        this.postAuthorize = postAuthorize;
        return this;
    }

    public UserAuthorize getUserAuthorize() {
        return userAuthorize;
    }

    public SysPostDetailedInfo setUserAuthorize(UserAuthorize userAuthorize) {
        this.userAuthorize = userAuthorize;
        return this;
    }

    public List<String> getIds() {
        return ids;
    }

    public SysPostDetailedInfo setIds(List<String> ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public String toString() {
        return "SysPostDetailedInfo{" + "postId='" + postId + '\'' + ", postAuthorize=" + postAuthorize
               + ", userAuthorize=" + userAuthorize + ", ids=" + ids + '}';
    }
}
