package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysUser;

import java.io.Serializable;

/** 
 * @Title: SysUserJson
 * @Description: SysUserJson
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 
 * @version V1.0  
 */
public class SysUserJson extends EventJson implements Serializable{
    
	private static final long serialVersionUID = 2377287016152389765L;
	
	private SysUser settings;
    
    public SysUser getSettings() {
        return settings;
    }
    
    public SysUserJson setSettings(SysUser settings) {
        this.settings = settings;
        return this;
    }
}
