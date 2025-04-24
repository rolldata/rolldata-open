package com.rolldata.web.system.pojo;

/**
 * @Title: ParentId
 * @Description: ParentId
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/31
 * @version V1.0  
 */
public class AuthorizeParentId extends AuthorizeBase {
    
    private static final long serialVersionUID = -5863733148641412773L;

    private String pid;

    public String getPid () {
        return pid;
    }

    public AuthorizeParentId setPid (String pid) {
        this.pid = pid;
        return this;
    }
}
