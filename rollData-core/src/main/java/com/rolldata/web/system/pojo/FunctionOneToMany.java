package com.rolldata.web.system.pojo;

import java.util.List;

/** 
 * @Title: FunctionOneToMany
 * @Description: 一主多从
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/4
 * @version V1.0  
 */
public class FunctionOneToMany {
    
    /*主表ID*/
    private String roleId;
    
    /*菜单id集合*/
    private List<String> funcIds;
    
    /*按钮id集合*/
    private List<String> powerIds;
    
    public String getRoleId() {
        return roleId;
    }
    
    public FunctionOneToMany setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }
    
    public List<String> getFuncIds() {
        return funcIds;
    }
    
    public FunctionOneToMany setFuncIds(List<String> funcIds) {
        this.funcIds = funcIds;
        return this;
    }
    
    public List<String> getPowerIds() {
        return powerIds;
    }
    
    public FunctionOneToMany setPowerIds(List<String> powerIds) {
        this.powerIds = powerIds;
        return this;
    }
}
