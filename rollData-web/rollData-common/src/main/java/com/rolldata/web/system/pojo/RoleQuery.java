package com.rolldata.web.system.pojo;


import java.io.Serializable;

/** 
 * @Title: RoleQuery
 * @Description: RoleQuery
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/16
 * @version V1.0  
 */
public class RoleQuery implements Serializable {
    
    private static final long serialVersionUID = 6418054124822926274L;
    
    private String type;
    
    private String userId;
    
    public String getType() {
        return this.type;
    }
    
    public RoleQuery setType(String type) {
        this.type = type;
        return this;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public RoleQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
