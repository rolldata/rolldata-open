package com.rolldata.web.system.pojo;

import java.io.Serializable;

/** 
 * @Description: 基础档案请求json格式解析类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 
 * @version V1.0  
 */

public class RequestDictTypeJsonParent extends EventJson implements Serializable{
    	
	private static final long serialVersionUID = 1065197195595719529L;
	
	private RequestDictTypeJson settings;

	public RequestDictTypeJson getSettings() {
		return settings;
	}

	public void setSettings(RequestDictTypeJson settings) {
		this.settings = settings;
	}

}
