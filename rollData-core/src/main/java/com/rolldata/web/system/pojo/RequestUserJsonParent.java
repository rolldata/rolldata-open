package com.rolldata.web.system.pojo;

import java.io.Serializable;

/** 
 * @Description: 用户请求json格式解析类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 
 * @version V1.0  
 */
public class RequestUserJsonParent extends EventJson implements Serializable{
    	
	private static final long serialVersionUID = 6519450795677302509L;
	
	private UserDetailedJson settings;
    
    public UserDetailedJson getSettings() {
        return settings;
    }
    
    public RequestUserJsonParent setSettings(UserDetailedJson settings) {
        this.settings = settings;
        return this;
    }
}
