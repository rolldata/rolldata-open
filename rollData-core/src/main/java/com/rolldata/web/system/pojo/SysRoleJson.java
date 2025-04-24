package com.rolldata.web.system.pojo;

/** 
 * @Title: SysRoleJson
 * @Description: SysRoleJson
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/1
 * @version V1.0  
 */
public class SysRoleJson extends EventJson {
    
    private static final long serialVersionUID = 7193086398414986121L;
    
    private RoleDetailedInfo settings;
    
    public RoleDetailedInfo getSettings() {
        return this.settings;
    }
    
    public void setSettings(RoleDetailedInfo settings) {
        this.settings = settings;
    }
}
