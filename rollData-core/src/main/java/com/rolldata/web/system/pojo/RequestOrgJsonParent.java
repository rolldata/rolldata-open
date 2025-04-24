package com.rolldata.web.system.pojo;

import java.io.Serializable;

/** 
 * @Description: 组织请求json格式解析类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 
 * @version V1.0  
 */

public class RequestOrgJsonParent extends EventJson implements Serializable{
    	
	private static final long serialVersionUID = 2933229104236609582L;
	
	private RequestOrgJson settings;

	public RequestOrgJson getSettings() {
		return settings;
	}

	public void setSettings(RequestOrgJson settings) {
		this.settings = settings;
	}
    
}
