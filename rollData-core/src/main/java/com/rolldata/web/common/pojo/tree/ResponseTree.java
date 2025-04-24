package com.rolldata.web.common.pojo.tree;

import java.util.Date;
import java.util.List;

/** 
 * @Title: ResponseTree
 * @Description: 响应 树
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2016-6-22下午6:27:07
 * @version V1.0  
 */

public class ResponseTree {
	
	/** 父节点 */
	private String parent;
	
	/**树id*/
	private String id;
	
	/**数据源id*/
	private String dsid;
	
	/** 模型id */
	private String mdid;
	
	/** 报表id */
	private String rpid;
	
	/**
	 * 关联的资源id
	 */
	private String rsid;
	
	/**资源链接*/
	private String rsUrl;
	
	/**当前节点名 (必须*)*/
	private String name;
	
	/**当前节点的类型  (必须*)*/
	private String type;
	
	/**当前节点的子节点*/
	private List children;
	
	/**sqlserver数据库用的参数  dbo guest*/
	private String sqlServerParam;
	
	/**数据源类型*/
	private String dsType;

	/**关联数据的类型，如模型类型*/
	private String relationType;

	/**资源路径*/
	private String rsPath;
	
	/**
	 * 是否有子节点
	 */
	private Boolean haveChildren = false;

    /**
     * 处理类型
     */
	private String handleType;

    /**
     * 是否挂出到资源上 0没挂1挂载
     */
    private String menuResourceType = "0";

	/**
	 * 树结点的层级级别
	 */
	private String level;

	private String showCenter;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public ResponseTree(){
		super();
	}

	/**
	 * 懒加载树用到的属性
	 * @param
	 * @return
	 * @date:2016-6-2
	 */
	public ResponseTree(String id, String name){
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @Description: 获取数据源id
	 * @return String
	 */
	public String getDsid() {
		return dsid;
	}

	/**
	 * @Description: 设置 数据源id
	 * @param dsid
	 */
	public void setDsid(String dsid) {
		this.dsid = dsid;
	}

	/**
	 * @Description: 获取当前节点名  (必须*)
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @Description: 设置 当前节点名  (必须*)
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @Description: 获取当前节点的类型  (必须*)
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * @Description: 设置 当前节点的类型  (必须*)
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @Description: 获取当前节点的子节点
	 * @return List<Object>
	 */
	public List getChildren() {
		return children;
	}

	/**
	 * @Description: 设置当前节点的子节点
	 * @param children
	 */
	public void setChildren(List children) {
		this.children = children;
	}

	/**  
	 * 获取树id  
	 * @return id 树id  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * 设置树id  
	 * @param id 树id  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * 获取模型id  
	 * @return mdid 模型id  
	 */
	public String getMdid() {
		return mdid;
	}

	/**  
	 * 设置模型id  
	 * @param mdid 模型id  
	 */
	public void setMdid(String mdid) {
		this.mdid = mdid;
	}

	/**  
	 * 获取报表id  
	 * @return rpid 报表id  
	 */
	public String getRpid() {
		return rpid;
	}

	/**  
	 * 设置报表id  
	 * @param rpid 报表id  
	 */
	public void setRpid(String rpid) {
		this.rpid = rpid;
	}

	/**  
	 * 获取父节点  
	 * @return parent 父节点  
	 */
	public String getParent() {
		return parent;
	}

	/**  
	 * 设置父节点  
	 * @param parent 父节点  
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**  
	 * 获取sqlserver数据库用的参数dboguest  
	 * @return sqlServerParam sqlserver数据库用的参数dboguest  
	 */
	public String getSqlServerParam() {
		return sqlServerParam;
	}
	
	/**  
	 * 设置sqlserver数据库用的参数dboguest  
	 * @param sqlServerParam sqlserver数据库用的参数dboguest  
	 */
	public void setSqlServerParam(String sqlServerParam) {
		this.sqlServerParam = sqlServerParam;
	}

	/**  
	 * 获取数据源类型  
	 * @return dsType 数据源类型  
	 */
	public String getDsType() {
		return dsType;
	}

	/**  
	 * 设置数据源类型  
	 * @param dsType 数据源类型  
	 */
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	/**  
	 * 获取关联的资源id  
	 * @return rsid 关联的资源id  
	 */
	public String getRsid() {
		return rsid;
	}
	
	/**  
	 * 设置关联的资源id  
	 * @param rsid 关联的资源id  
	 */
	public void setRsid(String rsid) {
		this.rsid = rsid;
	}
	
	/**  
	 * 获取资源链接  
	 * @return rsUrl 资源链接  
	 */
	public String getRsUrl() {
		return rsUrl;
	}

	/**  
	 * 设置资源链接  
	 * @param rsUrl 资源链接  
	 */
	public void setRsUrl(String rsUrl) {
		this.rsUrl = rsUrl;
	}

    /**
     * 获取 是否有子节点
     *
     * @return haveChildren 是否有子节点
     */
    public Boolean getHaveChildren () {
        return this.haveChildren;
    }

    /**
     * 设置 是否有子节点
     *
     * @param haveChildren 是否有子节点
     */
    public void setHaveChildren (Boolean haveChildren) {
        this.haveChildren = haveChildren;
    }

	/**
	 * 获取 关联数据的类型，如模型类型
	 *
	 * @return relationType 关联数据的类型，如模型类型
	 */
	public String getRelationType() {
		return this.relationType;
	}

	/**
	 * 设置 关联数据的类型，如模型类型
	 *
	 * @param relationType 关联数据的类型，如模型类型
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**  
	 * 获取资源路径  
	 * @return rsPath 资源路径  
	 */
	public String getRsPath() {
		return rsPath;
	}
	
	/**  
	 * 设置资源路径  
	 * @param rsPath 资源路径  
	 */
	public void setRsPath(String rsPath) {
		this.rsPath = rsPath;
	}

    /**
     * 获取 处理类型
     *
     * @return handleType 处理类型
     */
    public String getHandleType() {
        return this.handleType;
    }

    /**
     * 设置 处理类型
     *
     * @param handleType 处理类型
     */
    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    /**
     * 获取 是否挂出到资源上 0没挂1挂载
     *
     * @return menuResourceType 是否挂出到资源上 0没挂1挂载
     */
    public String getMenuResourceType() {
        return this.menuResourceType;
    }

    /**
     * 设置 是否挂出到资源上 0没挂1挂载
     *
     * @param menuResourceType 是否挂出到资源上 0没挂1挂载
     */
    public void setMenuResourceType(String menuResourceType) {
        this.menuResourceType = menuResourceType;
    }

	/**
	 * 获取 树结点的层级级别
	 *
	 * @return level 树结点的层级级别
	 */
	public String getLevel() {
		return this.level;
	}

	/**
	 * 设置 树结点的层级级别
	 *
	 * @param level 树结点的层级级别
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	public String getShowCenter() {
		return showCenter;
	}

	public ResponseTree setShowCenter(String showCenter) {
		this.showCenter = showCenter;
		return this;
	}

	/**
	 * 获取 创建时间
	 *
	 * @return createTime 创建时间
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置 创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 更新时间
	 *
	 * @return updateTime 更新时间
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 设置 更新时间
	 *
	 * @param updateTime 更新时间
	 */
	public void setUpdateTime(Date updateTime) {

		this.updateTime = null == updateTime ? this.createTime : updateTime;
	}

	@Override
	public String toString() {
		return "ResponseTree{" +
				"parent='" + parent + '\'' +
				", id='" + id + '\'' +
				", dsid='" + dsid + '\'' +
				", mdid='" + mdid + '\'' +
				", rpid='" + rpid + '\'' +
				", rsid='" + rsid + '\'' +
				", rsUrl='" + rsUrl + '\'' +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", children=" + children +
				", sqlServerParam='" + sqlServerParam + '\'' +
				", dsType='" + dsType + '\'' +
				", relationType='" + relationType + '\'' +
				", rsPath='" + rsPath + '\'' +
				", haveChildren=" + haveChildren +
				", handleType='" + handleType + '\'' +
				", menuResourceType='" + menuResourceType + '\'' +
				", level='" + level + '\'' +
				", showCenter='" + showCenter + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
