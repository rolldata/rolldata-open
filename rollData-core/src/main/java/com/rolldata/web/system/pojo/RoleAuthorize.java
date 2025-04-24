package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysRoleMenu;

import java.io.Serializable;
import java.util.List;

/** 
 * @Title: RoleAuthorize
 * @Description: 角色授权中,已有的权限
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/31
 * @version V1.0  
 */
public class RoleAuthorize implements Serializable {
    
    private static final long serialVersionUID = 2095730255616305550L;
    
    /**
     * 菜单功能权限集合
     */
    private List<String> funcIds;

    /**
     * 选择的按钮id集合
     */
    private List<String> buttonIds;

    /**
     * 选中的部门id集合
     */
    private List<String> departmentIds;
    
    /**
     * 数据权限
     */
    private List<String> orgIds;

    /**
     * 分析权限的id集合
     */
    private List<SysRoleMenu> menuIds;

    /**
     * 获取 数据权限
     */
    public List<String> getOrgIds() {
        return this.orgIds;
    }
    
    /**
     * 设置 数据权限
     */
    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }
    
    /**
     * 获取 菜单功能权限集合
     *
     * @return funcIds 菜单功能权限集合
     */
    public List<String> getFuncIds() {
        return this.funcIds;
    }

    /**
     * 设置 菜单功能权限集合
     *
     * @param funcIds 菜单功能权限集合
     */
    public void setFuncIds(List<String> funcIds) {
        this.funcIds = funcIds;
    }

    /**
     * 获取 选择的按钮id集合
     *
     * @return buttonIds 选择的按钮id集合
     */
    public List<String> getButtonIds() {
        return this.buttonIds;
    }

    /**
     * 设置 选择的按钮id集合
     *
     * @param buttonIds 选择的按钮id集合
     */
    public void setButtonIds(List<String> buttonIds) {
        this.buttonIds = buttonIds;
    }

    /**
     * 获取 选中的部门id集合
     *
     * @return departmentIds 选中的部门id集合
     */
    public List<String> getDepartmentIds() {
        return this.departmentIds;
    }

    /**
     * 设置 选中的部门id集合
     *
     * @param departmentIds 选中的部门id集合
     */
    public void setDepartmentIds(List<String> departmentIds) {
        this.departmentIds = departmentIds;
    }

    /**
     * 获取 分析权限的id集合
     *
     * @return menuIds 分析权限的id集合
     */
    public List<SysRoleMenu> getMenuIds () {
        return this.menuIds;
    }

    /**
     * 设置 分析权限的id集合
     *
     * @param menuIds 分析权限的id集合
     */
    public void setMenuIds (List<SysRoleMenu> menuIds) {
        this.menuIds = menuIds;
    }
}
