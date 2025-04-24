package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;

/**
 * @Title: SysPostTree
 * @Description: SysPostTree
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-11-30
 * @version:V1.0
 */
public class SysPostTree extends TreeNode {

    private static final long serialVersionUID = 5335484064346129083L;

    /**
     * 职务id
     */
    private String postId;

    /**
     * 职务代码
     */
    private String postCode;

    /**
     * 获取 职务id
     *
     * @return postId 职务id
     */
    public String getPostId() {
        return this.postId;
    }

    /**
     * 设置 职务id
     *
     * @param postId 职务id
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }

    /**
     * 获取 职务代码
     *
     * @return postCode 职务代码
     */
    public String getPostCode() {
        return this.postCode;
    }

    /**
     * 设置 职务代码
     *
     * @param postCode 职务代码
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "SysPostTree{" + "postId='" + postId + '\'' + ", postCode='" + postCode + '\'' + '}';
    }
}
