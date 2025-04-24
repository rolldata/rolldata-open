package com.rolldata.web.system.pojo;

import com.rolldata.web.common.enums.TreeNodeType;

import java.io.Serializable;
import java.util.List;

/** 
 * @Title: AuthorizeBase
 * @Description: AuthorizeBase
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/31
 * @version V1.0  
 */
public class AuthorizeBase<T> implements Serializable {
    
    private static final long serialVersionUID = 4845985186237350015L;
    
    /**
     * id
     */
    private String id;
    
    /**
     * 类型
     */
    private TreeNodeType type;

    /**
     * 子节点
     */
    private List<T> children;
    
    
    /**
     * 获取 id
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * 设置 id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 获取 类型
     */
    public TreeNodeType getType() {
        return this.type;
    }
    
    /**
     * 设置 类型
     */
    public void setType(TreeNodeType type) {
        this.type = type;
    }

    /**
     * 获取 子节点
     *
     * @return children 子节点
     */
    public List<T> getChildren () {
        return this.children;
    }

    /**
     * 设置 子节点
     *
     * @param children 子节点
     */
    public void setChildren (List<T> children) {
        this.children = children;
    }
}
