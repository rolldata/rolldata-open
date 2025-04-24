package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Title: SysFunction
 * @Description: 功能
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 *
 */
@Entity
@Table(name = "wd_sys_function")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysFunction extends IdEntity implements java.io.Serializable {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3837544230102805125L;

	/*一级根节点*/
    public static final String ROOT = "0";
    
    /**
     * state（0停用1启用）
     */
    public static final String STATUS_NORMAL = "1";
    
    public static final String STATUS_DELETE = "0";
    
    /*is_system*/
    public static final String IS_SYSTEM = "0";
    
    public static final String NOT_IS_SYSTEM = "1";
    
    /**0目录,1树*/
    public static final String TYPE_FOLDER = "0";
    
    public static final String TYPE_TREE = "1";

    /**普通菜单is_admin=0*/
    public static final String NOT_IS_ADMIN = "0";
    /**控制台管理IS_ADMIN=1*/
    public static final String IS_ADMIN = "1";
    /**财务数据管理平台*/
    public static final String IS_Finance = "2";
    /**指标管理平台*/
    public static final String IS_Item = "3";
    /**门户*/
    public static final String IS_Portal = "4";

    /**
     * 奇迹门户
     */
    public static final String IS_MIRACLE_PORTAL = "5";

    /**
     * 展示到业务
     */
    public static final String BUSINESS_TYPE_1 = "1";

    /**功能名称*/
	private String funcName;

	/**父级id*/
	private String parentId;

	/**类型，0菜单目录，1树*/
	private String type;

    /**表单id,报表id*/
    private String relationId;

    /**功能链接地址*/
    private String hrefLink;

	/**排序*/
	private Integer sort;

    /**系统内外(0系统内1系统外)*/
    private String isSystem;

    /**是否管理节点，0否，1是*/
    private String isAdmin;

    /*权限标识*/
    private String powerFlag;

    /**状态0停用1启用*/
    private String state;

    /**
     * 所属的系统模块，Public,BI，DS，Report,DPF，
     */
    private String systemType;

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

    /**
     * 图标类名
     */
    private String iconClass;

    /**创建时间*/
    private Date createTime;

    /**修改时间*/
    private Date updateTime;

    /**创建人*/
    private String createUser;

    /**修改人*/
    private String updateUser;

    /*jpa排序*/
    private Integer jpaSort;

    /**外部安装模型的id*/
    private String wdModelId;

    /**
     * 显示到业务类型0不显示(或空)1显示
     */
    private String businessType;

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  功能名称
     */
    @Column(name = "func_name", length = 20)
    public String getFuncName(){
        return this.funcName;
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能名称
	 */
	public void setFuncName(String funcName){
		this.funcName = funcName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父级id
	 */
    @Column(name = "parent_id", length = 32)
	public String getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父级id
	 */
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  功能链接地址
	 */
    @Column(name = "href_link", length = 100)
	public String getHrefLink(){
		return this.hrefLink;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能链接地址
	 */
	public void setHrefLink(String hrefLink){
		this.hrefLink = hrefLink;
	}

    @Column(name = "sort", length = 2)
    public Integer getSort() {
        return sort;
    }

    public SysFunction setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态0停用1启用
	 */
    @Column(name = "state", length = 2)
	public String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态0停用1启用
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
    @Column(name ="create_time")
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

    @Column(name ="update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysFunction setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Column(name = "create_user", length = 32)
    public String getCreateUser() {
        return createUser;
    }

    public SysFunction setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return updateUser;
    }

    public SysFunction setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Transient
    public Integer getJpaSort() {
        return this.getSort();
    }

    public SysFunction setJpaSort(Integer jpaSort) {
        this.jpaSort = jpaSort;
        return this;
    }

    @Column(name = "is_system", length = 2)
    public String getIsSystem() {
        return isSystem;
    }

    public SysFunction setIsSystem(String isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    @Column(name = "power_flag", length = 100)
    public String getPowerFlag() {
        return powerFlag;
    }

    public SysFunction setPowerFlag(String powerFlag) {
        this.powerFlag = powerFlag;
        return this;
    }

    @Column(name = "c_type", length = 2)
    public String getType() {
        return type;
    }

    public SysFunction setType(String type) {
        this.type = type;
        return this;
    }

    @Column(name = "relation_id", length = 32)
    public String getRelationId() {
        return relationId;
    }

    public SysFunction setRelationId(String relationId) {
        this.relationId = relationId;
        return this;
    }

    /**
     * 获取 PC终端
     *
     * @return terminalPC PC终端
     */
    @Column(name = "terminal_pc", length = 1)
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
    @Column(name = "terminal_ipad", length = 1)
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
    @Column(name = "terminal_mobile", length = 1)
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
     * 获取 所属的系统模块，PublicBI，DS，ReportDPF，
     *
     * @return systemType 所属的系统模块，PublicBI，DS，ReportDPF，
     */
    @Column(name = "c_system_type", length = 10)
    public String getSystemType() {
        return this.systemType;
    }

    /**
     * 设置 所属的系统模块，PublicBI，DS，ReportDPF，
     *
     * @param systemType 所属的系统模块，PublicBI，DS，ReportDPF，
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

	/**  
	 * 获取图标类名  
	 * @return iconClass 图标类名  
	 */
    @Column(name = "icon_class", length = 50)
	public String getIconClass() {
		return iconClass;
	}
	

	/**  
	 * 设置图标类名  
	 * @param iconClass 图标类名  
	 */
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}


    /**
     * 获取 是否管理节点，0否，1是
     *
     * @return isAdmin 是否管理节点，0否，1是
     */
    @Column(name = "is_admin", length = 2)
    public String getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * 设置 是否管理节点，0否，1是
     *
     * @param isAdmin 是否管理节点，0否，1是
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取 外部安装模型的id
     *
     * @return wdModelId 外部安装模型的id
     */
    @Column(name = "wd_model_id", length = 32)
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
    @Column(name = "business_type", length = 2)
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
