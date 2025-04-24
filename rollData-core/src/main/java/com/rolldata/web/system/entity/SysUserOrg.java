package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户所拥有的数据权限,每次修改用户、权限、组织等操作,都更新此表
 * <p>更新再插入</p>
 *
 * @Title:WdSysUserOrg
 * @Description:WdSysUserOrg
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-4-23
 * @version:V1.0
 */
@Entity
@Table(name = "wd_sys_user_org")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysUserOrg extends IdEntity implements java.io.Serializable {

    private static final long serialVersionUID = 3019014338232197311L;

    /**
     * 系统内外(0系统内)
     */
    static public final String SYSTEM0 = "0";

    /**
     * 系统内外(1系统外)
     */
    static public final String SYSTEM1 = "1";

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 部门编码
     */
    private String departmentCode;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 系统内外(0系统内1系统外)
     */
    private String isSystem;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    public SysUserOrg() {
        this.isSystem = SYSTEM0;
    }

    static public SysUserOrg newInstanceData(SysUserOrg sysUserOrg) {

        SysUserOrg sysUserOrgNew = new SysUserOrg();
        if (null == sysUserOrg) {
            return sysUserOrgNew;
        }
        sysUserOrgNew.setUserId(sysUserOrg.getUserId());
        sysUserOrgNew.setUserCode(sysUserOrg.getUserCode());
        sysUserOrgNew.setUserName(sysUserOrg.getUserName());
        sysUserOrgNew.setOrgId(sysUserOrg.getOrgId());
        sysUserOrgNew.setOrgCode(sysUserOrg.getOrgCode());
        sysUserOrgNew.setOrgName(sysUserOrg.getOrgName());
        sysUserOrgNew.setDepartmentId(sysUserOrg.getDepartmentId());
        sysUserOrgNew.setDepartmentCode(sysUserOrg.getDepartmentCode());
        sysUserOrgNew.setDepartmentName(sysUserOrg.getDepartmentName());
        sysUserOrgNew.setIsSystem(sysUserOrg.getIsSystem());
        sysUserOrgNew.setCreateTime(sysUserOrg.getCreateTime());
        sysUserOrgNew.setCreateUser(sysUserOrg.getCreateUser());
        return sysUserOrgNew;
    }

    public SysUserOrg _setUserInfo(String userId, String userCode, String userName) {

        this.setUserId(userId);
        this.setUserCode(userCode);
        this.setUserName(userName);
        return this;
    }

    /**
     * 获取 用户id
     *
     * @return userId 用户id
     */
    @Column(name="user_id", length = 32)
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置 用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户编码
     *
     * @return userCode 用户编码
     */
    @Column(name="user_code", length = 50)
    public String getUserCode() {
        return this.userCode;
    }

    /**
     * 设置 用户编码
     *
     * @param userCode 用户编码
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取 用户姓名
     *
     * @return userName 用户姓名
     */
    @Column(name="user_name", length = 100)
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置 用户姓名
     *
     * @param userName 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取 组织id
     *
     * @return orgId 组织id
     */
    @Column(name="org_id", length = 32)
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

    /**
     * 获取 组织编码
     *
     * @return orgCode 组织编码
     */
    @Column(name="org_code", length = 50)
    public String getOrgCode() {
        return this.orgCode;
    }

    /**
     * 设置 组织编码
     *
     * @param orgCode 组织编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 组织名称
     *
     * @return orgName 组织名称
     */
    @Column(name="org_name", length = 100)
    public String getOrgName() {
        return this.orgName;
    }

    /**
     * 设置 组织名称
     *
     * @param orgName 组织名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 部门id
     *
     * @return departmentId 部门id
     */
    @Column(name="department_id", length = 32)
    public String getDepartmentId() {
        return this.departmentId;
    }

    /**
     * 设置 部门id
     *
     * @param departmentId 部门id
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取 部门编码
     *
     * @return departmentCode 部门编码
     */
    @Column(name="department_code", length = 50)
    public String getDepartmentCode() {
        return this.departmentCode;
    }

    /**
     * 设置 部门编码
     *
     * @param departmentCode 部门编码
     */
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    /**
     * 获取 部门名称
     *
     * @return departmentName 部门名称
     */
    @Column(name="department_name", length = 100)
    public String getDepartmentName() {
        return this.departmentName;
    }

    /**
     * 设置 部门名称
     *
     * @param departmentName 部门名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 获取 系统内外(0系统内1系统外)
     *
     * @return isSystem 系统内外(0系统内1系统外)
     */
    @Column(name="is_system", length = 1)
    public String getIsSystem() {
        return this.isSystem;
    }

    /**
     * 设置 系统内外(0系统内1系统外)
     *
     * @param isSystem 系统内外(0系统内1系统外)
     */
    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    /**
     * 获取 创建时间
     *
     * @return createTime 创建时间
     */
    @Column(name="create_time")
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
     * 获取 创建人
     *
     * @return createUser 创建人
     */
    @Column(name="create_user", length = 32)
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 设置 创建人
     *
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
