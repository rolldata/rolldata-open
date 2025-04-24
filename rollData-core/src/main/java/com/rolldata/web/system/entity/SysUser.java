package com.rolldata.web.system.entity;

import com.rolldata.core.util.StringUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title: SysUser
 * @Description: 用户实体
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-24
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_user")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysUser implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4584962952450199621L;

	/**
     * 根节点
     */
    public static final String TREE_ROOT = "0";

	/**
	 * 用户状态（2：初始；1：启用；0：停用；）
	 */
	//public static final String STATUS_INIT = "2";
	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_DELETE = "0";
	
	/**
	 * 是否锁定（0：被锁定；1：可使用；）
	 */
	public static final String LOCKED = "0";
	public static final String NO_LOCKED = "1";
	
	public static final String USER_TYPE="USER";
	
	public static final String UFOLDER_TYPE="FOLDER";
    
    /**
     * 初始化：0初始化；1正常
     */
	public static final String ISINIT = "0";
	
	public static final String ISINIT_NORMAL = "1";
    
    /**
     * 默认头像
     */
	public static final String HEADPHOTO_MAN = "avatar_image_1.png";
	public static final String HEADPHOTO_WOMAN = "avatar_image_2.png";

	/**是否同步，默认0否(手动创建)*/
	public static final String ISSYNC0 = "0";
	/**是否同步，1是(自动同步)*/
	public static final String ISSYNC1 = "1";

	private String id;

	/**父id*/
	private String parentId;

	/** 用户登陆代码 */
	private String userCde;
	
	/** 用户名称 */
	private String userName;
	
	/** 密码 */
	private String password;
	
	/** 盐 */
	private String salt;
    
    /** 1启用，0停用*/
    private String isactive;
    
    /**是否锁定：0被锁定；1可使用*/
    private String islocked;
    
    /**初始化：0初始化；1正常*/
    private String isInit;
    
    /**
     * 组织id
     */
    private String orgId;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	/** 创建用户 */
	private String createUser;
	
	/** 修改用户 */
	private String updateUser;
	
	/**用户类型*/
	private String type;
	
	/**公司*/
	private String company;
	
	/**部门*/
	private String department;
	
	/**职位*/
	private String position;
	
	/**手机*/
	private String mobilePhone;
	
	/**区号*/
	private String areaCode;
	
	/**固定电话*/
	private String telephone;
	
	/**邮箱*/
	private String mail;
	
	/**性别:1为男性，2为女性*/
	private String gender;
	
	/**用工类别*/
	private String employType;
	
	/**修改密码时间*/
	private Date updatePasswordTime;
	
    /**
     * 头像
     */
	private String headPhoto;
	
	/**
	 * 微信唯一id
	 */
	private String unionId;

	/**第三方用户编码或id*/
	private String thirdPartyCode;

	/**外部安装模型的id*/
	private String wdModelId;

	/**资源访问密码*/
	private String browsePassword;

	/**是否修改过默认的访问密码1:是，0:否*/
	private String isUpdateBrowse;

	/**企业微信用户id*/
	private String wxWorkUserId;

	/**是否同步，默认0否(手动创建)，1是*/
	private String isSync="0";

	/**显示名*/
	private String title;

	@Id
	@Column(name ="ID",nullable=false,length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "parent_id", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
    
    @Column(name = "user_cde", length = 20)
    public String getUserCde() {
        return userCde;
    }
    
    public SysUser setUserCde(String userCde) {
        this.userCde = userCde;
        return this;
    }
    
    @Column(name = "user_name", length = 50)
    public String getUserName() {
        return userName;
    }
    
    public SysUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }
    
    @Column(name = "password", length = 100)
    public String getPassword() {
        return password;
    }
    
    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }
    
    @Column(name = "salt", length = 100)
    public String getSalt() {
        return salt;
    }
    
    public SysUser setSalt(String salt) {
        this.salt = salt;
        return this;
    }
    
    @Column(name = "isactive", length = 1)
    public String getIsactive() {
        return isactive;
    }
    
    public SysUser setIsactive(String isactive) {
        this.isactive = isactive;
        return this;
    }
    
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }
    
    public SysUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public SysUser setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    @Column(name = "create_user", length = 32)
    public String getCreateUser() {
        return createUser;
    }
    
    public SysUser setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }
    
    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return updateUser;
    }
    
    public SysUser setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }
    
    @Transient
    public String getCredentialsSalt() {
        return userCde + salt;
    }

    @Column(name= "islocked", length = 1)
	public String getIslocked() {
		return islocked;
	}

	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}

	@Column(name="user_type", length = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="company", length = 50)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "department", length = 50)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "c_position", length = 50)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "mail", length = 50)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "update_password_time")
	public Date getUpdatePasswordTime() {
		return updatePasswordTime;
	}

	public void setUpdatePasswordTime(Date updatePasswordTime) {
		this.updatePasswordTime = updatePasswordTime;
	}

	@Column(name = "is_init", length = 1)
	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
    
    /**
     * 获取 头像
     */
    @Column(name = "head_photo", length = 50)
    public String getHeadPhoto() {
        return this.headPhoto;
    }
    
    /**
     * 设置 头像
     */
    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

	/**  
	 * 获取组织id  
	 * @return orgId 组织id  
	 */
    @Column(name = "org_id", length = 32)
	public String getOrgId() {
		return orgId;
	}
	

	/**  
	 * 设置组织id  
	 * @param orgId 组织id  
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**  
	 * 获取mobilePhone  
	 * @return mobilePhone mobilePhone  
	 */
	@Column(name = "mobile_phone", length = 15)
	public String getMobilePhone() {
		return mobilePhone;
	}
	

	/**  
	 * 设置mobilePhone  
	 * @param mobilePhone mobilePhone  
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	

	/**  
	 * 获取区号  
	 * @return areaCode 区号  
	 */
	@Column(name = "area_code", length = 5)
	public String getAreaCode() {
		return areaCode;
	}

	/**  
	 * 设置区号  
	 * @param areaCode 区号  
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**  
	 * 获取固定电话  
	 * @return telephone 固定电话  
	 */
	@Column(name = "telephone", length = 10)
	public String getTelephone() {
		return telephone;
	}
	
	/**  
	 * 设置固定电话  
	 * @param telephone 固定电话  
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**  
	 * 获取性别:1为男性，2为女性  
	 * @return gender 性别:1为男性，2为女性
	 */
	@Column(name = "gender", length = 2)
	public String getGender() {
		return gender;
	}
	
	/**  
	 * 设置性别:1为男性，2为女性
	 * @param gender 性别:1为男性，2为女性
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**  
	 * 获取用工类别  
	 * @return employType 用工类别  
	 */
	@Column(name = "employ_type", length = 20)
	public String getEmployType() {
		return employType;
	}
	
	/**  
	 * 设置用工类别  
	 * @param employType 用工类别  
	 */
	public void setEmployType(String employType) {
		this.employType = employType;
	}

	/**  
	 * 获取微信唯一id  
	 * @return unionId 微信唯一id  
	 */
	@Column(name = "union_id", length = 50)
	public String getUnionId() {
		return unionId;
	}
	

	/**  
	 * 设置微信唯一id 
	 * @param unionId 微信唯一id  
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * 获取第三方用户编码或id
	 * @return thirdPartyCode 第三方用户编码或id
	 */
	@Column(name = "third_party_code", length = 50)
	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	/**
	 * 设置第三方用户编码或id
	 * @param thirdPartyCode 第三方用户编码或id
	 */
	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
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

	/**
	 * 获取 资源访问密码
	 *
	 * @return browsePassword 资源访问密码
	 */
	@Column(name = "browse_password", length = 20)
	public String getBrowsePassword() {
		return this.browsePassword;
	}

	/**
	 * 设置 资源访问密码
	 *
	 * @param browsePassword 资源访问密码
	 */
	public void setBrowsePassword(String browsePassword) {
		this.browsePassword = browsePassword;
	}

	/**
	 * 获取 是否修改过默认的访问密码1:是，0:否
	 *
	 * @return isUpdateBrowse 是否修改过默认的访问密码1:是，0:否
	 */
	@Column(name = "is_update_browse", length = 2)
	public String getIsUpdateBrowse() {
		return this.isUpdateBrowse;
	}

	/**
	 * 设置 是否修改过默认的访问密码1:是，0:否
	 *
	 * @param isUpdateBrowse 是否修改过默认的访问密码1:是，0:否
	 */
	public void setIsUpdateBrowse(String isUpdateBrowse) {
		this.isUpdateBrowse = isUpdateBrowse;
	}

	/**
	 * 获取 企业微信用户id
	 *
	 * @return wxWorkUserId 企业微信用户id
	 */
	@Column(name = "wxwork_user_id", length = 100)
	public String getWxWorkUserId() {
		return this.wxWorkUserId;
	}

	/**
	 * 设置 企业微信用户id
	 *
	 * @param wxWorkUserId 企业微信用户id
	 */
	public void setWxWorkUserId(String wxWorkUserId) {
		this.wxWorkUserId = wxWorkUserId;
	}

	/**
	 * 获取 是否同步，默认0否(手动创建)，1是
	 *
	 * @return isSync 是否同步，默认0否(手动创建)，1是
	 */
	@Column(name = "is_sync", length = 2)
	public String getIsSync() {
		return this.isSync;
	}

	/**
	 * 设置 是否同步，默认0否(手动创建)，1是
	 *
	 * @param isSync 是否同步，默认0否(手动创建)，1是
	 */
	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}


	/**
	 * 获取 显示名
	 *
	 * @return title 显示名
	 */
	@Transient
	public String getTitle() {
		if(StringUtil.isNotEmpty(this.userCde)) {
			return this.userCde + "-" + this.userName;
		}else {
			return this.userName;
		}
	}

	/**
	 * 设置 显示名
	 *
	 * @param title 显示名
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
