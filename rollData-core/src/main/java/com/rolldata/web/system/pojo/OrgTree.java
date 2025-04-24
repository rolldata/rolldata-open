package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;

import java.util.List;

/**
 * 组织树节点信息
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class OrgTree extends TreeNode{

	private static final long serialVersionUID = 8911123972712583555L;
	
	private String orgId;

	private List<OrgTree> children;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

    /**
     * 获取
     *
     * @return children
     */
    public List<OrgTree> getChildren() {
        return this.children;
    }

    /**
     * 设置
     *
     * @param children
     */
    public void setChildren(List<OrgTree> children) {
        this.children = children;
    }
}
