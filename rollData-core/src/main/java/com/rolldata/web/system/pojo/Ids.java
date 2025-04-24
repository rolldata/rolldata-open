package com.rolldata.web.system.pojo;

import java.util.List;

/** 
 * @Title: Ids
 * @Description: Ids
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/8/8
 * @version V1.0  
 */
public class Ids {
    
    private List<String> ids;

    private String attribute;
    
    public List<String> getIds() {
        return this.ids;
    }
    
    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getAttribute() {
        return attribute;
    }

    public Ids setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }
}
