package com.rolldata.core.common.enums;

/**
 * @Title: EventType
 * @Description: 基础标识类型
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
public enum EventType {
    
    /** 新增标识 */
    CREATE_MD("create-md"),
    
    /** 删除标识 */
    DELETE_MD("delete-md"),
    
    /** 更新标识 */
    UPDATE_MD("update-md"),
    
    /** 查询标识 */
    QUERY_MD("query-md"),
    
    /** 停用标识 */
    STOP_MD("stop-md");
    
    private String type;
    
    EventType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
}
