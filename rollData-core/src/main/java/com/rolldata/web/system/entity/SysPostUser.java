package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: SysPostUser
 * @Description:  归入用户
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
@Entity
@Table(name="wd_sys_post_user")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysPostUser extends IdEntity implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8116307377982703252L;

	/**
     * 职务id
     */
    private String postId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    @Column(name = "post_id", length = 32)
    public String getPostId() {
        return postId;
    }

    public SysPostUser setPostId(String postId) {
        this.postId = postId;
        return this;
    }


    @Column(name = "user_id", length = 32)
    public String getUserId() {
        return userId;
    }

    public SysPostUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public SysPostUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
