package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: SysPostPower
 * @Description: 按钮权限
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-12-02
 * @version:V1.0
 */
@Entity
@Table(name = "wd_sys_post_power")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysPostPower extends IdEntity implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8194231954767306491L;

	/*权限类型 （1功能权限,2按钮权限）*/
    public static final String TYPE_FUN = "1";

    public static final String TYPE_OPER = "2";

    /**
     * 职务id
     */
    private String postId;

    /**
     * 权限类型（1功能权限,2按钮权限）
     */
    private String powerType;

    /**
     * 权限id（funcid,operid）
     */
    private String powerId;

    @Column(name = "post_id", length = 32)
    public String getPostId() {
        return postId;
    }

    public SysPostPower setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    @Column(name = "power_type", length = 2)
    public String getPowerType() {
        return powerType;
    }

    public SysPostPower setPowerType(String powerType) {
        this.powerType = powerType;
        return this;
    }

    @Column(name = "power_id", length = 32)
    public String getPowerId() {
        return powerId;
    }

    public SysPostPower setPowerId(String powerId) {
        this.powerId = powerId;
        return this;
    }
}
