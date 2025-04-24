package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;

import java.util.List;

/** 
 * @Title: MainRoleTree
 * @Description: 角色列表清单
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/14
 * @version V1.0  
 */
public class MainRoleTree extends TreeNode {
    
    
    private static final long serialVersionUID = -8494038193425403433L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色代码
     */
    private String roleCde;

    /**
     * 备注
     */
    private String remark;

    private List<MainRoleTree> children;

    /**
     * 获取 角色id
     *
     * @return roleId 角色id
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 设置 角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取 角色代码
     *
     * @return roleCde 角色代码
     */
    public String getRoleCde() {
        return this.roleCde;
    }

    /**
     * 设置 角色代码
     *
     * @param roleCde 角色代码
     */
    public void setRoleCde(String roleCde) {
        this.roleCde = roleCde;
    }

    /**
     * 获取
     *
     * @return children
     */
    public List<MainRoleTree> getChildren() {
        return this.children;
    }

    /**
     * 设置
     *
     * @param children
     */
    public void setChildren(List<MainRoleTree> children) {
        this.children = children;
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
}
