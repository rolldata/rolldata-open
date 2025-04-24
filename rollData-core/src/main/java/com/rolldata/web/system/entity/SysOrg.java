package com.rolldata.web.system.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: SysOrg
 * @Description: 组织机构
 * @author shenshilog
 * @date 2018-05-25
 * @version V1.0
 *
 */
@Entity
@Table(name = "wd_sys_org")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysOrg implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6336217235032024666L;

	/**
     * 类型：0根目录(总览)
     */
    public static final String TYPE_ROOT = "0";

    /**
     * 类型：1公司
     */
    public static final String TYPE_COMPANY = "1";

    /**
     * 类型：2部门
     */
    public static final String TYPE_DEPARTMENT = "2";

    /**
     * 总览
     */
    public static final String TYPE_ROOT_ZH_CN = "总览";

    /**
     * 公司
     */
    public static final String TYPE_COMPANY_ZH_CN = "公司";

    /**
     * 部门
     */
    public static final String TYPE_DEPARTMENT_ZH_CN = "部门";

    /**是否同步，默认0否(手动创建)*/
    public static final String ISSYNC0 = "0";
    /**是否同步，1是(自动同步)*/
    public static final String ISSYNC1 = "1";

    private String id;
    
    /**父节点id*/
    private String parentId;

    /**组织名称*/
    private String orgName;

    /**0根目录、1公司组织、2部门（汇总）*/
    private String type;

    /**
     * 组织代码
     */
    private String orgCde;

    /**
     * 本级组织id
     */
    private String sameLevelId;

    /**
     * 是否参与自动汇总0否1是
     */
    private String automaticSummary;

    /**创建时间*/
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /**创建用户*/
    private String createUser;

    /** 修改用户 */
    private String updateUser;

    /**图标*/
    private String icon;

    /*节点自定义图标的 className*/
    private String iconSkin;

    /**外部安装模型的id*/
    private String wdModelId;

    /**是否同步，默认0否(手动创建)，1是*/
    private String isSync="0";

    @Id
    @Column(name ="ID",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  父节点id
     */
    @Column(name = "parent_id", length = 32)
    public String getParentId(){
        return this.parentId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  父节点id
     */
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  组织名称
     */
    @Column(name = "org_name", length = 50)
    public String getOrgName(){
        return this.orgName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  组织名称
     */
    public void setOrgName(String orgName){
        this.orgName = orgName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  0根目录、1公司组织、2部门（汇总）
     */
    @Column(name = "c_type", length = 1)
    public String getType(){
        return this.type;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  0根目录、1公司组织、2部门（汇总）
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  创建时间
     */
    @Column(name = "create_time")
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
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建用户
     */
    @Column(name = "create_user", length = 32)
    public String getCreateUser(){
        return this.createUser;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  创建用户
     */
    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public SysOrg setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return updateUser;
    }

    public SysOrg setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    @Column(name = "icon", length = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "icon_skin", length = 100)
    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    /**
     * 获取本级组织id
     * @return sameLevelId 本级组织id
     */
    @Column(name = "same_level_id", length = 32)
    public String getSameLevelId() {
        return sameLevelId;
    }


    /**
     * 设置本级组织id
     * @param sameLevelId 本级组织id
     */
    public void setSameLevelId(String sameLevelId) {
        this.sameLevelId = sameLevelId;
    }

    /**
     * 获取 是否参与自动汇总0否1是
     *
     * @return automaticSummary 是否参与自动汇总0否1是
     */
    @Column(name = "automatic_summary", length = 1)
    public String getAutomaticSummary() {
        return this.automaticSummary;
    }

    /**
     * 设置 是否参与自动汇总0否1是
     *
     * @param automaticSummary 是否参与自动汇总0否1是
     */
    public void setAutomaticSummary(String automaticSummary) {
        this.automaticSummary = automaticSummary;
    }

    /**
     * 获取 组织代码
     *
     * @return orgCde 组织代码
     */
    @Column(name = "org_cde", length = 20)
    public String getOrgCde () {
        return this.orgCde;
    }

    /**
     * 设置 组织代码
     *
     * @param orgCde 组织代码
     */
    public void setOrgCde (String orgCde) {
        this.orgCde = orgCde;
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
     * 获取 是否同步，默认0否(手动创建)，1是
     *
     * @return isSync 是否同步，默认0否(手动创建)，1是
     */
    @Column(name = "is_sync", length = 2)
    public String getIsSync() {
        return this.isSync;
    }

    /**
     * 设置 是否同步，默认0否(手动创建)，1是
     *
     * @param isSync 是否同步，默认0否(手动创建)，1是
     */
    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }
}
