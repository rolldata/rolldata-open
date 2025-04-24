package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/** 
 * @Title: RoleDetailedInfo
 * @Description: 角色详细信息
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/14
 * @version V1.0  
 */
public class RoleDetailedInfo implements Serializable {
    
    private static final long serialVersionUID = -1689485577104590881L;
    
    /**
     * 角色id
     */
    private String roleId;
    
    /**
     * 角色代码
     */
    private String roleCde;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色状态
     */
    private String state;
    
    /**
     * 角色授权
     */
    private RoleAuthorize roleAuthorize;
    
    /**
     * 归入用户
     */
    private UserAuthorize userAuthorize;
    
    /**
     * 删除id集合
     */
    private List<String> ids;

    /**
     * 备注
     */
    private String remark;

    /**是否管理角色，0否，1是*/
    private String isAdmin;

    /**外部安装模型的id*/
    private String wdModelId;

    /**
     * 获取 角色代码
     */
    public String getRoleCde() {
        return this.roleCde;
    }
    
    /**
     * 设置 角色代码
     */
    public void setRoleCde(String roleCde) {
        this.roleCde = roleCde;
    }
    
    /**
     * 获取 角色名称
     */
    public String getRoleName() {
        return this.roleName;
    }
    
    /**
     * 设置 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    /**
     * 获取 角色状态
     */
    public String getState() {
        return this.state;
    }
    
    /**
     * 设置 角色状态
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * 获取 角色授权
     */
    public RoleAuthorize getRoleAuthorize() {
        return this.roleAuthorize;
    }
    
    /**
     * 设置 角色授权
     */
    public void setRoleAuthorize(RoleAuthorize roleAuthorize) {
        this.roleAuthorize = roleAuthorize;
    }
    
    /**
     * 获取 归入用户
     */
    public UserAuthorize getUserAuthorize() {
        return this.userAuthorize;
    }
    
    /**
     * 设置 归入用户
     */
    public void setUserAuthorize(UserAuthorize userAuthorize) {
        this.userAuthorize = userAuthorize;
    }
    
    /**
     * 获取 角色id
     */
    public String getRoleId() {
        return this.roleId;
    }
    
    /**
     * 设置 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    /**
     * 获取 删除id集合
     */
    public List<String> getIds() {
        return this.ids;
    }
    
    /**
     * 设置 删除id集合
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * 获取 备注
     *
     * @return remark 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 是否管理角色，0否，1是
     *
     * @return isAdmin 是否管理角色，0否，1是
     */
    public String getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * 设置 是否管理角色，0否，1是
     *
     * @param isAdmin 是否管理角色，0否，1是
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
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
}
