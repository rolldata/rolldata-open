package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.common.pojo.tree.ResponseTree;

import java.util.List;

/** 
 * @Title: MainFunctionTree
 * @Description: 主菜单(应前端要求,保留children格式)
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/13
 * @version V1.0  
 */
public class MainFunctionTree extends TreeNode {
    
    private static final long serialVersionUID = 2849005064285202589L;
    
    /*菜单id*/
    private String funcId;
    
    private List<MainFunctionTree> children;

    /**
     * 响应式菜单列表节点
     */
    private List<ResponseTree> largeMenu;

    /*菜单链接*/
    private String hrefLink;
    
    /**
     * 链接类型，0菜单，1树
     */
    private String hrefType;
    
    /**系统内外(0系统内1系统外)*/
    private String isSystem;

    /**排序*/
    private Integer sort;

    public List<MainFunctionTree> getChildren() {
        return this.children;
    }

    public MainFunctionTree setChildren(List<MainFunctionTree> children) {
        this.children = children;
        return this;
    }

    public String getHrefLink() {
        return this.hrefLink;
    }

    public MainFunctionTree setHrefLink(String hrefLink) {
        this.hrefLink = hrefLink;
        return this;
    }

    /*菜单id*/
    public String getFuncId() {
        return this.funcId;
    }

    /*菜单id*/
    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    /**
     * 获取 链接类型
     */
    public String getHrefType() {
        return this.hrefType;
    }

    /**
     * 设置 链接类型
     */
    public void setHrefType(String hrefType) {
        this.hrefType = hrefType;
    }

	/**
	 * 获取系统内外(0系统内1系统外)
	 * @return isSystem 系统内外(0系统内1系统外)
	 */
	public String getIsSystem() {
		return isSystem;
	}

	/**
	 * 设置系统内外(0系统内1系统外)
	 * @param isSystem 系统内外(0系统内1系统外)
	 */
	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

    /**
     * 获取 排序
     *
     * @return sort 排序
     */
    public Integer getSort() {
        return this.sort;
    }

    /**
     * 设置 排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取 响应式菜单列表节点
     *
     * @return largeMenu 响应式菜单列表节点
     */
    public List<ResponseTree> getLargeMenu() {
        return this.largeMenu;
    }

    /**
     * 设置 响应式菜单列表节点
     *
     * @param largeMenu 响应式菜单列表节点
     */
    public void setLargeMenu(List<ResponseTree> largeMenu) {
        this.largeMenu = largeMenu;
    }
}
