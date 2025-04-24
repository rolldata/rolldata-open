package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysFunction;

/** 
 * @Title: SysFunctionJson
 * @Description: SysFunctionJson
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/5/31
 * @version V1.0  
 */
public class SysFunctionJson extends EventJson {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7583167231345811614L;
	
	private SysFunction settings;
    
    public SysFunction getSettings() {
        return settings;
    }
    
    public SysFunctionJson setSettings(SysFunction settings) {
        this.settings = settings;
        return this;
    }
}
