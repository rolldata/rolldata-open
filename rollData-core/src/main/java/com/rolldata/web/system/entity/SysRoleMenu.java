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
 * @Title: SysRoleMenu
 * @Description: 角色目录权限表
 * @Company:www.wrenchdata.com
 * @author shenshilong[shilong_shen@163.com]
 * @date 2019-5-13
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_role_menu")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysRoleMenu extends IdEntity implements java.io.Serializable {
    
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 2671840232045733599L;

	/**角色id*/
    private String roleId;
    
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

    /**外部安装模型的id*/
    private String wdModelId;

    /**
     * 角色那用的子节点
     */
    private List<SysRoleMenu> children;
    
    
    /**角色id*/
    @Column(name = "role_id", length = 32)
    public String getRoleId() {
        return this.roleId;
    }
    
    /**角色id*/
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
    public void setType(String type) {
        this.type = type;
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
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    public void setRelationId (String relationId) {
        this.relationId = relationId;
    }

    /**
     * 获取 角色那用的子节点
     *
     * @return children 角色那用的子节点
     */
    @Transient
    public List<SysRoleMenu> getChildren () {
        return this.children;
    }

    /**
     * 设置 角色那用的子节点
     *
     * @param children 角色那用的子节点
     */
    public void setChildren (List<SysRoleMenu> children) {
        this.children = children;
    }

    /**
     * 获取 外部安装模型的id
     *
     * @return wdModelId 外部安装模型的id
     */
    @Column(name = "wd_model_id", length = 32)
    public String getWdModelId() {
        return this.wdModelId;
    }

    /**
     * 设置 外部安装模型的id
     *
     * @param wdModelId 外部安装模型的id
     */
    public void setWdModelId(String wdModelId) {
        this.wdModelId = wdModelId;
    }
}
