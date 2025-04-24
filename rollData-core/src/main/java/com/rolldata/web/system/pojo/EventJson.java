package com.rolldata.web.system.pojo;
/** 
 * @Title: EventJson
 * @Description: EventJson
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0  
 */
public class EventJson implements java.io.Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -6876661021274888836L;
	
	/** 请求事件 create-md : 新建; update-md : 更新; delete-md : 删除;  query-md：查询;stop-md:停用*/
    private String event;
    
    public String getEvent() {
        return event;
    }
    
    public EventJson setEvent(String event) {
        this.event = event;
        return this;
    }
}
