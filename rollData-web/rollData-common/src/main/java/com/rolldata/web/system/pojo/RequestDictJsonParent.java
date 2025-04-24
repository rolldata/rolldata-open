package com.rolldata.web.system.pojo;

import java.io.Serializable;

/** 
 * @Description: 基础档案内容请求json格式解析类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 
 * @version V1.0  
 */

public class RequestDictJsonParent extends EventJson implements Serializable{
    	
	private static final long serialVersionUID = -1377620447017237747L;
	
	private RequestDictJson settings;

	public RequestDictJson getSettings() {
		return settings;
	}

	public void setSettings(RequestDictJson settings) {
		this.settings = settings;
	}

	
}
