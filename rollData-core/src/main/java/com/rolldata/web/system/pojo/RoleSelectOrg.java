package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: RoleSelectOrg
 * @Description: RoleSelectOrg
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-02-26
 * @version:V1.0
 */
public class RoleSelectOrg implements Serializable {

    private static final long serialVersionUID = -5517421372839639753L;

    /**
     * 模糊查询字段
     */
    private String search;

    /**
     * 角色多选组织
     */
    private List<AllUserListParent> org;

    /**
     * 获取 模糊查询字段
     *
     * @return search 模糊查询字段
     */
    public String getSearch() {
        if (null != this.search) {
            return this.search;
        } else {
            return "";
        }
    }

    /**
     * 设置 模糊查询字段
     *
     * @param search 模糊查询字段
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * 获取 角色多选组织
     *
     * @return org 角色多选组织
     */
    public List<AllUserListParent> getOrg() {
        return this.org;
    }

    /**
     * 设置 角色多选组织
     *
     * @param org 角色多选组织
     */
    public void setOrg(List<AllUserListParent> org) {
        this.org = org;
    }
}
