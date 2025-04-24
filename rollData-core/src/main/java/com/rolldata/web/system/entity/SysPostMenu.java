package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Title: SysPostMenu
 * @Description: 菜单功能权限
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
@Entity
@Table(name = "wd_sys_post_menu")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysPostMenu extends IdEntity implements java.io.Serializable {
    
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -2243299485743268352L;

	/**职务id*/
    private String postId;
    
    /**
     * 关联关系id(目录id或资源id)
     */
    private String relationId;
    
    
    /**
     * 类型0目录1其他
     */
    private String type;
    
    /**
     *创建用户
     */
    private String createUser;
    
    /**
     *创建时间
     */
    private Date createTime;

    /**
     * 职务那用的子节点
     */
    private List<SysPostMenu> children;
    
    /**
     * 获取 职务id
     *
     * @return postId 职务id  
     */
    @Column(name = "post_id", length = 32)
    public String getPostId() {
        return this.postId;
    }

    /**
     * 设置 职务id
     *
     * @param postId 职务id  
     */
    public SysPostMenu setPostId(String postId) {
        this.postId = postId;
        return this;
    }
    
    /**
     *类型0文件夹1表单2指标
     */
    @Column(name = "c_type", length = 1)
    public String getType() {
        return this.type;
    }
    
    /**
     *类型0文件夹1表单2指标
     */
    public SysPostMenu setType(String type) {
        this.type = type;
        return this;
    }
    
    /**
     *创建用户
     */
    @Column(name = "create_user", length = 32)
    public String getCreateUser() {
        return this.createUser;
    }
    
    /**
     *创建用户
     */
    public SysPostMenu setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }
    
    /**
     *创建时间
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return this.createTime;
    }
    
    /**
     *创建时间
     */
    public SysPostMenu setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
    /**
     * 获取 关联关系id(目录id或资源id)
     *
     * @return relationId 关联关系id(目录id或资源id)
     */
    @Column(name = "relation_id", length = 32)
    public String getRelationId () {
        return this.relationId;
    }
    
    /**
     * 设置 关联关系id(目录id或资源id)
     *
     * @param relationId 关联关系id(目录id或资源id)
     */
    public SysPostMenu setRelationId (String relationId) {
        this.relationId = relationId;
        return this;
    }

    /**
     * 获取 职务那用的子节点
     *
     * @return children 职务那用的子节点  
     */
    @Transient
    public List<SysPostMenu> getChildren() {
        return this.children;
    }

    /**
     * 设置 职务那用的子节点
     *
     * @param children 职务那用的子节点  
     */
    public SysPostMenu setChildren(List<SysPostMenu> children) {
        this.children = children;
        return this;
    }
}
