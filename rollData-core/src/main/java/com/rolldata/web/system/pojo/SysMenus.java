package com.rolldata.web.system.pojo;

import java.util.List;

public class SysMenus {
	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 父节点id
	 */
	private String parentId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 链接
	 */
	private String hrefLink;

	/**类型，0菜单目录，1树*/
	private String type;

	/**
	 * 子级
	 */
	private List<SysMenus> children;

	/**
	 * 交换至新位置的id
	 */
	private String toId;

    /**
     * PC终端
     */
    private String terminalPC;

    /**
     * ipad终端
     */
    private String terminalIpad;

    /**
     * 移动端
     */
    private String terminalMobile;

	/**外部安装模型的id*/
	private String wdModelId;

    /**
     * 显示到业务类型0不显示(或空)1显示
     */
    private String businessType;

    public SysMenus() {
    }

    public SysMenus(String id, String parentId, String name, String hrefLink, String type, String businessType) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.hrefLink = hrefLink;
        this.type = type;
        this.businessType = businessType;
    }

    /**
	 * 获取主键id
	 *
	 * @return id 主键id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置主键id
	 *
	 * @param id 主键id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取父节点id
	 *
	 * @return parentId 父节点id
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置父节点id
	 *
	 * @param parentId 父节点id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取名称
	 *
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 *
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取链接
	 *
	 * @return hrefLink 链接
	 */
	public String getHrefLink() {
		return hrefLink;
	}

	/**
	 * 设置链接
	 *
	 * @param hrefLink 链接
	 */
	public void setHrefLink(String hrefLink) {
		this.hrefLink = hrefLink;
	}

	/**
	 * 获取子级
	 *
	 * @return childrens 子级
	 */
	public List<SysMenus> getChildren() {
		return children;
	}

	/**
	 * 设置子级
	 *
	 * @param children 子级
	 */
	public void setChildren(List<SysMenus> children) {
		this.children = children;
	}

	/**
	 * 获取类型，0菜单目录，1树
	 * @return type 类型，0菜单目录，1树
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型，0菜单目录，1树
	 * @param type 类型，0菜单目录，1树
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取交换至新位置的id
	 * @return toId 交换至新位置的id
	 */
	public String getToId() {
		return toId;
	}


	/**
	 * 设置交换至新位置的id
	 * @param toId 交换至新位置的id
	 */
	public void setToId(String toId) {
		this.toId = toId;
	}

    /**
     * 获取 PC终端
     *
     * @return terminalPC PC终端
     */
    public String getTerminalPC() {
        return this.terminalPC;
    }

    /**
     * 设置 PC终端
     *
     * @param terminalPC PC终端
     */
    public void setTerminalPC(String terminalPC) {
        this.terminalPC = terminalPC;
    }

    /**
     * 获取 ipad终端
     *
     * @return terminalIpad ipad终端
     */
    public String getTerminalIpad() {
        return this.terminalIpad;
    }

    /**
     * 设置 ipad终端
     *
     * @param terminalIpad ipad终端
     */
    public void setTerminalIpad(String terminalIpad) {
        this.terminalIpad = terminalIpad;
    }

    /**
     * 获取 移动端
     *
     * @return terminalMobile 移动端
     */
    public String getTerminalMobile() {
        return this.terminalMobile;
    }

    /**
     * 设置 移动端
     *
     * @param terminalMobile 移动端
     */
    public void setTerminalMobile(String terminalMobile) {
        this.terminalMobile = terminalMobile;
    }

	/**
	 * 获取 外部安装模型的id
	 *
	 * @return wdModelId 外部安装模型的id
	 */
	public String getWdModelId() {
		return this.wdModelId;
	}

	/**
	 * 设置 外部安装模型的id
	 *
	 * @param wdModelId 外部安装模型的id
	 */
	public void setWdModelId(String wdModelId) {
		this.wdModelId = wdModelId;
	}

	/**
	 * 获取 显示到业务类型0不显示(或空)1显示
	 *
	 * @return businessType 显示到业务类型0不显示(或空)1显示
	 */
	public String getBusinessType() {
		return this.businessType;
	}

	/**
	 * 设置 显示到业务类型0不显示(或空)1显示
	 *
	 * @param businessType 显示到业务类型0不显示(或空)1显示
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
}
