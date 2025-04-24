package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;

import java.util.List;

/**
 * 用户树节点信息
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class UserTree extends TreeNode{

	private static final long serialVersionUID = -1099675097393379687L;
	
	private String userId;

	private List<UserTree> children;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * 获取
     *
     * @return children
     */
    public List<UserTree> getChildren() {
        return this.children;
    }

    /**
     * 设置
     *
     * @param children
     */
    public void setChildren(List<UserTree> children) {
        this.children = children;
    }
}
