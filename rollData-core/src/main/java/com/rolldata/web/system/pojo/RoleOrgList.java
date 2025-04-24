package com.rolldata.web.system.pojo;

import java.util.List;

/**
 * @Title: SysOrgList
 * @Description: 角色机构：给前台的json
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @createDate 2018-6-4
 * @version V1.0 
 */
public class RoleOrgList implements java.io.Serializable{
	
	private static final long serialVersionUID = 7364485892894074221L;

	/*id*/
    private String id;

    /**
     * 组织id
     */
    private String orgId;

	/**父节点id*/
	private String parentId;

	/**组织代码*/
	private String orgCde;

	/**组织名称*/
	private String name;

	/**1集团2公司3部门4岗位*/
	private String type;

	/**状态0停用1启用*/
	//private java.lang.String state;

	/**图标*/
	private String icon;

	/*是否选中*/
	private boolean isChecked = false;

	/*子菜单对象*/
	private List<RoleOrgList> children;

	public RoleOrgList() {

	}

	public RoleOrgList(String id, String orgName, List<RoleOrgList> children) {
		super();
		this.id = id;
		this.name = orgName;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgCde() {
		return orgCde;
	}

	public void setOrgCde(String orgCde) {
		this.orgCde = orgCde;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}*/

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<RoleOrgList> getChildren() {
		return children;
	}

	public void setChildren(List<RoleOrgList> children) {
		this.children = children;
	}

    /**
     * 获取 组织名称
     *
     * @return name 组织名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 组织名称
     *
     * @param name 组织名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 是否选中
     *
     * @return isChecked 是否选中
     */
    public boolean isIsChecked() {
        return this.isChecked;
    }

    /**
     * 设置 是否选中
     *
     * @param isChecked 是否选中
     */
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    /**
     * 获取 组织id
     *
     * @return orgId 组织id
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 设置 组织id
     *
     * @param orgId 组织id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
