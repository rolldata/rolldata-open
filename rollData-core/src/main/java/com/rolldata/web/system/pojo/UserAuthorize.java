package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/** 
 * @Title: UserAuthorize
 * @Description: UserAuthorize
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/31
 * @version V1.0  
 */
public class UserAuthorize implements Serializable {
    
    
    private static final long serialVersionUID = 8122662893440107847L;
    
    private List<String> userIds;
    
    public List<String> getUserIds() {
        return null == this.userIds ? Collections.emptyList() : this.userIds;
    }
    
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
