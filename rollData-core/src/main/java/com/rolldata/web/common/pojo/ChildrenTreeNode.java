package com.rolldata.web.common.pojo;

import java.util.List;

/**
 * @Title: ChildrenTreeNode
 * @Description: ChildrenTreeNode
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-07-17
 * @version: V1.0
 */
public class ChildrenTreeNode extends TreeNode {

    private static final long serialVersionUID = -524825835292851065L;

    private List<ChildrenTreeNode> children;

    /**
     * 树层级
     */
    private int level;

    public ChildrenTreeNode() {}

    public ChildrenTreeNode(String id, String pId, String name) {
        super(id, pId, name);
    }

    public ChildrenTreeNode(String id, String pId, String name, int level) {
        super(id, pId, name);
        this.setLevel(level);
    }

    /**
     * 获取
     *
     * @return children
     */
    public List<ChildrenTreeNode> getChildren() {
        return this.children;
    }

    /**
     * 设置
     *
     * @param children
     */
    public void setChildren(List<ChildrenTreeNode> children) {
        this.children = children;
    }

    /**
     * 获取 树层级
     *
     * @return level 树层级
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * 设置 树层级
     *
     * @param level 树层级
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
