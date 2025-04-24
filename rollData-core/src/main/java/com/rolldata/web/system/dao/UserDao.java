package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.UserDetailedJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/** 
 * @Title: UserDao
 * @Description: 用户Dao层 接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0  
 */
public interface UserDao extends JpaRepository<SysUser, String>,JpaSpecificationExecutor<SysUser> {
    
    /**
     * 根据userCde查询User
     * @param userCde 用户cde
     * @return SysUser
     * @throws Exception
     */
    public SysUser getUserByUserCde(String userCde) throws Exception;
    
    /**
     * 通过父id和用户名查询User
     * @return SysUser
     * @throws Exception
     */
    public SysUser getUserByParentIdAndUserName(String parentId, String userName) throws Exception;
    
    /**
     * 修改密码(根据用户id)
     * @param password
     * @param salt 盐
     * @param isactive 状态
     * @param updateUser 修改人Id
     * @param updateTime 修改时间
     * @param id 用户id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.password = :password, sc.salt = :salt, sc.isactive = :isactive, sc.updateUser = :updateUser, sc.updateTime = :updateTime,sc.updatePasswordTime = :updatePasswordTime where sc.id = :id")
    public void updatePassword(@Param("password") String password, @Param("salt") String salt, @Param("isactive") String isactive, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("updatePasswordTime") Date updatePasswordTime, @Param("id") String id) throws Exception;

    /**
     * 修改用户基本信息
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.userName = :userName, sc.orgId = :orgId, sc.company = :company, sc.department = "
        + ":department, sc.position = :position, sc.mobilePhone = :mobilePhone, sc.areaCode = :areaCode, sc.telephone"
        + " = :telephone, sc.gender = :gender, sc.employType = :employType, sc.mail = :mail, sc.isactive = :isactive,"
        + " sc.updateUser = :updateUser, sc.updateTime = :updateTime, sc.thirdPartyCode = :thirdPartyCode where sc.id = :id")
    public void updateUserInfo(
            @Param("userName") String userName, @Param("orgId") String orgId, @Param("company") String company,
            @Param("department") String department, @Param("position") String position,
            @Param("mobilePhone") String mobilePhone, @Param("areaCode") String areaCode,
            @Param("telephone") String telephone, @Param("gender") String gender, @Param("employType") String employType,
            @Param("mail") String mail, @Param("isactive") String isactive, @Param("updateUser") String updateUser,
            @Param("updateTime") Date updateTime, @Param("thirdPartyCode") String thirdPartyCode,
            @Param("id") String id) throws Exception;


    /**
     * 修改文件夹基本信息
     * @param userName 用户名
     * @param updateUser 修改人Id
     * @param updateTime 修改时间
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.userName = :userName, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :id")
    public void updateFolderInfo(@Param("userName") String userName, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("id") String id) throws Exception;

    /**
     * 删除用户
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUser sc where sc.id = :id")
    public void deleteUserById(@Param("id") String id) throws Exception;

    /**
     * 删除
     *
     * @param ids 用户id集合
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysUser sc where sc.id in (:ids)")
    public void deleteUserByIds(@Param("ids") List<String> ids) throws Exception;

    /**
     * 修改用户状态
     * @param isactive 状态
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.isactive = :isactive,sc.updateTime = :updateTime,sc.updateUser = :updateUser where sc.id = :id")
    public void updateIsactive(@Param("isactive") String isactive, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("id") String id) throws Exception;

    /**
     * 修改用户parentId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.parentId = :parentId where sc.id = :id")
    public void updateParentId(@Param("parentId") String parentId, @Param("id") String id) throws Exception;

    /**
     * 角色右侧用户树
     * @param userId 当前登录人用户ID
     * @throws Exception
     */
    @Query(value="SELECT new com.rolldata.web.system.pojo.UserDetailedJson( sysuser0_.id AS userId, torg.orgName AS orgName, sysuser0_.userName AS userName, sysuser0_.orgId AS orgId ) FROM SysUser sysuser0_, SysOrg torg WHERE sysuser0_.orgId = torg.id AND sysuser0_.orgId IN ( SELECT sysorgrole2_.orgId FROM SysOrgRole sysorgrole2_ WHERE sysorgrole2_.roleId IN ( SELECT userrole3_.roleId FROM UserRole userrole3_, SysRole sysrole4_ WHERE userrole3_.roleId = sysrole4_.id AND sysrole4_.state = '1' AND userrole3_.userId = :userId )) ORDER BY sysuser0_.createTime asc ")
    public List<UserDetailedJson> queryUserTree(@Param("userId") String userId) throws Exception;

    /**
     * 用户id集合
     * @param userId
     * @return
     * @throws Exception
     */
    @Query(value="SELECT s.id FROM SysUser s WHERE s.orgId IN ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId )))")
    public List<String> queryUserIds(@Param("userId") String userId) throws Exception;

    /**
     * 管理员查全部id
     *
     * @return
     * @throws Exception
     */
    @Query(value="SELECT s.id FROM SysUser s ")
    public List<String> queryUserIds() throws Exception;

    /**
     * 获取当前用户最大权限下所有的用户信息
     * @param userId 当前登录人用户ID
     * @throws Exception
     */
    @Query(value="FROM SysUser s WHERE (s.userCde like :search or s.userName like :search2) and s.orgId IN ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId ))) and s.orgId = :orgId order by s.createTime asc",
                              countQuery="select count(s) FROM SysUser s WHERE (s.userCde like :search or s.userName like :search2) and s.orgId IN ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId ))) and s.orgId = :orgId")
    public Page<SysUser> queryUserList(@Param("search") String search, @Param("search2") String search2, @Param("userId") String userId, @Param("orgId") String orgId, Pageable pageable) throws Exception;

    /**
     * 公司下的用户
     *
     * @param userId 用户id
     * @param orgId  组织id
     * @return
     * @throws Exception
     */
    @Query(value = "FROM SysUser s WHERE s.orgId IN ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId" +
                   " FROM SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower" +
                   " WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId )))" +
                   " and s.orgId = :orgId order by s.createTime asc")
    List<SysUser> queryUserList(@Param("userId") String userId, @Param("orgId") String orgId) throws Exception;


    /**
     * 部门下的用户集合
     *
     * @param search
     * @param search2
     * @param userId
     * @param orgId
     * @param pageable
     * @return
     * @throws Exception
     */
    @Query(value = "FROM SysUser s WHERE (s.userCde like :search or s.userName like :search2) and s.department IN ( "
                   + "SELECT  o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ("
                   + " SELECT  u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND"
                   + " srpower.state = '1' AND u.userId = :userId ))) and s.department = :orgId"
                   + " order by s.createTime asc",
          countQuery = "select count(s) FROM SysUser s WHERE (s.userCde like :search or s.userName like :search2)"
                       + " and s.department IN ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM"
                       + " SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE"
                       + " u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId )))"
                       + " and s.department = :orgId")
    Page<SysUser> queryUserListByDepartmentOrgId(@Param("search") String search, @Param("search2") String search2,
                                                 @Param("userId") String userId, @Param("orgId") String orgId,
                                                 Pageable pageable) throws Exception;


    /**
     * 不分页查询部门下的用户集合
     *
     * @param userId 用户id
     * @param orgId  部门id
     * @return
     * @throws Exception
     */
    @Query(value = " FROM SysUser s WHERE s.department IN ( SELECT  o.id FROM SysOrg o WHERE o.id IN ( SELECT" +
                   " r.orgId FROM SysOrgRole r WHERE r.roleId IN ( SELECT  u.roleId FROM UserRole u, SysRole srpower" +
                   " WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId )))" +
                   " and s.department = :orgId order by s.createTime asc")
    List<SysUser> queryUserListByDepartmentOrgId(@Param("userId") String userId,
                                                 @Param("orgId") String orgId) throws Exception;

    @Query(value = "FROM SysUser s WHERE  (s.userCde like :search or s.userName like :search2) and s.orgId IN " +
                   "( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ( " +
                   "SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id" +
                   " AND srpower.state = '1' AND u.userId = :userId )) order by s.createTime asc ",
        countQuery = "select count(s) FROM SysUser s WHERE  (s.userCde like :search or s.userName like " +
                     ":search2) and s.orgId IN ( SELECT r.orgId FROM" +
                     " SysOrgRole r WHERE r.roleId IN ( SELECT u.roleId FROM UserRole u, SysRole srpower " +
                     "WHERE u.roleId = srpower.id AND srpower.state = '1' AND u.userId = :userId ))")
    public Page<SysUser> queryUserList(
            @Param("search") String search,
            @Param("search2") String search2,
            @Param("userId") String userId,
            Pageable pageable
    ) throws Exception;

    /**
     * 判断是否有子节点
     * @author shenshilong
     * @param id
     * @return
     * @throws Exception
     * @createDate 2018-8-1
     */
    @Query(value="from SysUser s where s.parentId = :id order by s.createTime asc ")
    public List<SysUser> isHaveChildren(@Param("id") String id)throws Exception;

    /**
     * 查唯一用户
     * @param id
     * @return
     * @throws Exception
     */
    public SysUser querySysUserById(String id) throws Exception;

    /**
     * 根据用户类型查询所有数量
     * @param userType
     * @return
     * @throws Exception
     */
    @Query(value="select count(s) from SysUser s where s.type = :userType")
	public int queryAllUserListByUserType(@Param("userType") String userType) throws Exception;

    /**
     * 获取当前用户最大权限下所有的用户信息(admin管理员用)
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like :search2) order by s.createTime asc ",
    		countQuery="select count(s) from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like :search2)")
	public Page<SysUser> queryUserList(@Param("search") String search, @Param("search2") String search2, Pageable pageable) throws Exception;

    @Query(value="select s from SysUser s where s.type ='USER' order by s.createTime asc ")
    public List<SysUser> queryUserList() throws Exception;

    /**
     * 非管理员可见得所有用户
     *
     * @param userId 用户的id
     * @return
     * @throws Exception
     */
    @Query(value = "FROM SysUser s WHERE s.orgId IN (SELECT r.orgId FROM SysOrgRole r WHERE r.roleId IN ( SELECT " +
                   "u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND srpower.state = '1'" +
                   " AND u.userId = :userId )) order by s.createTime asc ")
    public List<SysUser> queryUserList(@Param("userId") String userId) throws Exception;

    /**
     * 修改头像
     * @param headPhoto 图片名称
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update SysUser sc set sc.headPhoto = :headPhoto,sc.updateTime = :updateTime,sc.updateUser = :updateUser where sc.id = :id")
    public void updateHeadPhoto(@Param("headPhoto") String headPhoto, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("id") String id) throws Exception;

    /**
     * 初始用户修改密码及init字段
     * @param password
     * @param salt
     * @param isInit
     * @param updateUser
     * @param updateTime
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.password = :password, sc.salt = :salt, sc.isInit = :isInit, sc.updateUser = :updateUser, sc.updateTime = :updateTime,sc.updatePasswordTime = :updatePasswordTime where sc.id = :id")
    public void updateInitPassword(@Param("password") String password, @Param("salt") String salt, @Param("isInit") String isInit, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("updatePasswordTime") Date updatePasswordTime, @Param("id") String id) throws Exception;


    /**
     * 重置密码
     * @param password
     * @param salt
     * @param updateUser
     * @param updateTime
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.password = :password, sc.salt = :salt, sc.updateUser = :updateUser, sc.updateTime = :updateTime,sc.updatePasswordTime = :updatePasswordTime where sc.id = :id")
    public void resetPassword(@Param("password") String password, @Param("salt") String salt, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("updatePasswordTime") Date updatePasswordTime, @Param("id") String id) throws Exception;

    /**
     * 根据组织id查用户列表
     * @param orgId 组织id
     * @param pageable
     * @param search
     * @param search2
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like :search2) and s.orgId = :orgId",
    		countQuery="select count(s) from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like :search2) and s.orgId = :orgId")
	public Page<SysUser> querySysUsersByOrgId(@Param("search") String search, @Param("search2") String search2, @Param("orgId") String orgId, Pageable pageable) throws Exception;

    /**
     * 部门下的用户集合
     *
     * @param search
     * @param search2
     * @param orgId
     * @param pageable
     * @return
     * @throws Exception
     */
    @Query(value = "select s from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like "
                   + ":search2) and s.department = :orgId",
            countQuery = "select count(s) from SysUser s where s.type ='USER' and (s.userCde like :search"
                       + " or s.userName like :search2) and s.department = :orgId")
    Page<SysUser> querySysUsersByDepartmentOrgId(@Param("search") String search, @Param("search2") String search2,
                                                 @Param("orgId") String orgId, Pageable pageable) throws Exception;


    /**
     * 超级管理员部门下的用户集合
     *
     * @param orgId 部门id
     * @return
     * @throws Exception
     */
    @Query(value = "select s from SysUser s where s.type ='USER' and s.department = :orgId")
    List<SysUser> querySysUsersByDepartmentOrgId(@Param("orgId") String orgId) throws Exception;

    /**
     * 部门下的用户
     *
     * @param departmentId 部门id
     * @return
     * @throws Exception
     */
    @Query(value = "select s.id from SysUser s where s.type ='USER' and s.department = :departmentId")
    List<String> queryUserIdByDepartmentId(@Param("departmentId") String departmentId) throws Exception;

    @Query(value="select s from SysUser s where s.type ='USER' and s.isInit = :isInit order by s.createTime asc ")
	public List<SysUser> querySysUserListByIsInit(@Param("isInit") String isinit) throws Exception;

//    /**
//     * 修改用户锁定状态
//     * @param islocked
//     * @param updateUser
//     * @param updateTime
//     * @param id
//     * @throws Exception
//     */
//    @Modifying(clearAutomatically = true)
//    @Query("update SysUser sc set sc.islocked = :islocked,sc.updateTime = :updateTime,sc.updateUser = :updateUser where sc.id = :id")
//    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false,rollbackFor=Exception.class)
//    public void updateIslockedById(@Param("islocked")String islocked,@Param("updateUser")String updateUser, @Param("updateTime")Date updateTime, @Param("id")String id) throws Exception;

    /**
     * 修改用户锁定状态
     * @param islocked
     * @param updateUser
     * @param updateTime
     * @param userCde
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.islocked = :islocked,sc.updateTime = :updateTime,sc.updateUser = :updateUser where sc.userCde = :userCde")
    public void updateIslockedByCde(@Param("islocked") String islocked, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("userCde") String userCde) throws Exception;

    /**
     * 个人更新用户信息 手机、电话、邮箱
     *
     * @param mobilePhone 手机号
     * @param areaCode    区域码
     * @param telephone   号
     * @param mail        邮箱
     * @param gender      性别
     * @param updateUser  修改人
     * @param updateTime  修改时间
     * @param id
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.mobilePhone = :mobilePhone, sc.areaCode = :areaCode," +
           " sc.telephone = :telephone, sc.mail = :mail, sc.gender = :gender, sc.updateUser = :updateUser," +
           " sc.updateTime = :updateTime where sc.id = :id")
    public void updateUserInfo(
            @Param("mobilePhone") String mobilePhone,
            @Param("areaCode") String areaCode,
            @Param("telephone") String telephone,
            @Param("mail") String mail,
            @Param("gender") String gender,
            @Param("updateUser") String updateUser,
            @Param("updateTime") Date updateTime,
            @Param("id") String id
    ) throws Exception;

	@Query(value="select s from SysUser s where s.type ='USER' and s.unionId = :unionId and s.id <> :id")
	public SysUser querySysUserByUnionid(@Param("unionId") String unionId, @Param("id") String id) throws Exception;

	@Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.unionId = :unionId, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :id")
    public void updateUnionidByUserId(@Param("unionId") String unionId, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("id") String id) throws Exception;

	/**
	 * 根据微信id查询用户信息
	 * @param unionId
	 * @return
	 * @throws Exception
	 */
	@Query(value="select s from SysUser s where s.type ='USER' and s.unionId = :unionId")
	public SysUser queryUserByUnionid(@Param("unionId") String unionId) throws Exception;

	/**
	 * 根据第三方用户编码或id查询用户信息
	 * @param thirdPartyCode
	 * @return
	 * @throws Exception
	 */
	@Query(value="select s from SysUser s where s.type ='USER' and s.thirdPartyCode = :thirdPartyCode")
	public SysUser queryUserByThirdPartyCode(@Param("thirdPartyCode") String thirdPartyCode) throws Exception;

    /**
     * 组织id下的用户集合(删除组织下的用户使用)
     * @param orgId 组织id
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and s.orgId = :orgId")
    public List<SysUser> querySysUsersByOrgId(@Param("orgId") String orgId) throws Exception;

    /**
     * 查询角色所能控制的用户集合
     * @param roleIds 角色id
     * @return
     * @throws Exception
     */
    @Query(value="select wsu from SysUser wsu where wsu.orgId in (select wsor.orgId from SysOrgRole wsor where"
                 + " wsor.roleId in (:roleIds)) order by wsu.createTime asc ")
    public List<SysUser> querySysUsersByRoleIds(@Param("roleIds") List<String> roleIds) throws Exception;

    /**
     * 修改用户职务
     *
     * @param postId     职务id
     * @param userId     用户id
     * @param updateUser 修改人
     * @param updateTime 修改时间
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.position = :postId, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :userId")
    public void updatePosition(@Param("postId") String postId, @Param("updateUser") String updateUser,
                               @Param("updateTime") Date updateTime, @Param("userId") String userId) throws Exception;

    /**
     * 根据登录名,更新导入的用户
     *
     * @param orgId       公司id
     * @param department  部门id
     * @param position    职位
     * @param employType  用工类别
     * @param mail        邮箱
     * @param mobilePhone 手机号
     * @param areaCode    区号
     * @param telephone   固定电话
     * @param updateUser  更新人
     * @param updateDate  更新时间
     * @param userCde     用户登录名
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.orgId = :orgId, sc.department = :department, sc.employType = :employType," +
           " sc.position = :position, sc.mail = :mail, sc.mobilePhone = :mobilePhone, sc.areaCode = :areaCode," +
           " sc.telephone = :telephone, sc.updateUser =:updateUser, sc.updateTime = :updateDate " +
           " where sc.userCde = :userCde")
    public void updateImportUser(
            @Param("orgId") String orgId,
            @Param("department") String department,
            @Param("position") String position,
            @Param("employType") String employType,
            @Param("mail") String mail,
            @Param("mobilePhone") String mobilePhone,
            @Param("areaCode") String areaCode,
            @Param("telephone") String telephone,
            @Param("updateUser") String updateUser,
            @Param("updateDate") Date updateDate,
            @Param("userCde") String userCde
    ) throws Exception;

    /**
     * 通过职务id查询当前管理员可见用户集合
     *
     * @param postId 职务id
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Query(value = " SELECT postuser0_.id FROM SysUser postuser0_ WHERE postuser0_.position = :postId "
                   + " AND postuser0_.id IN ( SELECT sysuser0_.id FROM SysUser sysuser0_ WHERE"
                   + " sysuser0_.orgId IN ( SELECT sysorg1_.id FROM SysOrg sysorg1_ WHERE ( "
                   + " sysorg1_.id IN ( SELECT sysorgrole2_.orgId FROM SysOrgRole sysorgrole2_ WHERE"
                   + " sysorgrole2_.roleId IN ( SELECT userrole3_.roleId FROM UserRole userrole3_,"
                   + " SysRole sysrole4_ WHERE userrole3_.roleId = sysrole4_.id AND sysrole4_.state = '1'"
                   + " AND userrole3_.userId = :userId ))))) order by postuser0_.createTime asc ")
    public List<String> queryPostUserIdsByPostId(
            @Param("postId") String postId,
            @Param("userId") String userId
    ) throws Exception;

    /**
     * 通过职务id查询用户集合
     *
     * @param postId 职务id
     * @return
     * @throws Exception
     */
    @Query(value = " SELECT postuser0_.id FROM SysUser postuser0_ WHERE postuser0_.position = :postId"
                   + " order by postuser0_.createTime asc")
    public List<String> queryPostUserIdsByPostId(@Param("postId") String postId) throws Exception;

    /**
     * 所有用户
     *
     * @param search 模糊查询字段
     * @return
     * @throws Exception
     */
    @Query(value = "select s from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like "
                   + ":search) order by s.createTime asc ")
    public List<SysUser> querySysUsers(@Param("search") String search) throws Exception;

    /**
     * 通过职务id集合查询用户集合
     *
     * @param postIds 职务id
     * @return
     * @throws Exception
     */
    @Query(value = " SELECT postuser0_.id FROM SysUser postuser0_ WHERE postuser0_.position in( :postIds)")
    public List<String> queryPostUserIdsByPostIds(@Param("postIds") List<String> postIds) throws Exception;

    /**
     * 公司和部门下所有用户列表
     *
     * @param search      模糊查询字段
     * @param departments 部门id集合
     * @param companys    组织id集合
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and (s.userCde like :search or"
                 + " s.userName like :search) and (s.orgId in (:companys) or s.department in (:departments))")
    public List<SysUser> querySysUsersByOrgId(
            @Param("search") String search,
            @Param("departments") List<String> departments,
            @Param("companys") List<String> companys
    ) throws Exception;

    /**
     * 查询组织和部门下所有用户列表
     *
     * @param search     模糊查询字段
     * @param userId     用户id
     * @param department 部门id集合
     * @param orgIds     组织id集合
     * @return
     * @throws Exception
     */
    @Query(value="FROM SysUser s WHERE (s.userCde like :search or s.userName like :search) and s.orgId IN"
                 + " ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId"
                 + " IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND"
                 + " srpower.state = '1' AND u.userId = :userId ))) and (s.orgId in (:orgIds)" +
                 " or s.department in (:department)) order by s.createTime asc")
    public List<SysUser> queryUserList(
            @Param("search") String search,
            @Param("userId") String userId,
            @Param("department") List<String> department,
            @Param("orgIds") List<String> orgIds
    ) throws Exception;

    /**
     * 根据用户id修改组织id
     * @param userId
     * @param orgId
     * @param updateUser
     * @param updateTime
     */
	@Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.orgId = :orgId, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :userId")
    public void updateUserOrgIdByUserId(@Param("userId") String userId, @Param("orgId") String orgId, @Param("updateUser") String updateUser,
                                        @Param("updateTime") Date updateTime) throws Exception;

	/**
	 * 根据用户id修改部门id
	 * @param userId
	 * @param deptId
	 * @param updateUser
	 * @param updateTime
	 */
	@Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.department = :deptId, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :userId")
    public void updateUserDeptIdByUserId(@Param("userId") String userId, @Param("deptId") String deptId, @Param("updateUser") String updateUser,
                                         @Param("updateTime") Date updateTime) throws Exception;

    /**
     * 公司和部门下所有配置过访问资源密码的用户列表
     *
     * @param search      模糊查询字段
     * @param departments 部门id集合
     * @param companys    组织id集合
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and (s.userCde like :search or"
            + " s.userName like :search) and (s.orgId in (:companys) or s.department in (:departments)) and (s.browsePassword is not null or s.browsePassword<>'')")
    public List<SysUser> queryIsBrowseSysUsersByOrgId(
            @Param("search") String search,
            @Param("departments") List<String> departments,
            @Param("companys") List<String> companys
    ) throws Exception;

    /**
     * 查询组织和部门下所有配置过访问资源密码的用户列表
     *
     * @param search     模糊查询字段
     * @param userId     用户id
     * @param department 部门id集合
     * @param orgIds     组织id集合
     * @return
     * @throws Exception
     */
    @Query(value="FROM SysUser s WHERE (s.userCde like :search or s.userName like :search) and s.orgId IN"
            + " ( SELECT o.id FROM SysOrg o WHERE o.id IN ( SELECT r.orgId FROM SysOrgRole r WHERE r.roleId"
            + " IN ( SELECT u.roleId FROM UserRole u, SysRole srpower WHERE u.roleId = srpower.id AND"
            + " srpower.state = '1' AND u.userId = :userId ))) and (s.orgId in (:orgIds)" +
            " or s.department in (:department)) and (s.browsePassword is not null or s.browsePassword<>'') order by s.createTime asc")
    public List<SysUser> queryIsBrowseUserList(
            @Param("search") String search,
            @Param("userId") String userId,
            @Param("department") List<String> department,
            @Param("orgIds") List<String> orgIds
    ) throws Exception;

    /**
     * 所有配置过访问资源密码的用户
     *
     * @param search 模糊查询字段
     * @return
     * @throws Exception
     */
    @Query(value = "select s from SysUser s where s.type ='USER' and (s.userCde like :search or s.userName like "
            + ":search) and (s.browsePassword is not null or s.browsePassword<>'') order by s.createTime asc ")
    public List<SysUser> queryIsBrowseSysUsers(@Param("search") String search) throws Exception;

    /**
     * 管理员重置用户的访问资源密码，状态改为未修改过默认的访问密码
     * @param userIds
     * @param browsePassword
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.browsePassword = :browsePassword,sc.isUpdateBrowse='0'  where sc.id in (:userIds)")
    public void updateResetIsBrowsePassword(@Param("userIds") List<String> userIds, @Param("browsePassword") String browsePassword) throws Exception;

    /**
     * 用户修改访问资源密码，状态改为已修改过默认的访问密码
     * @param userCde
     * @param browsePassword
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.browsePassword = :browsePassword,sc.isUpdateBrowse='1' where sc.userCde = :userCde")
    public void updateIsBrowsePassword(@Param("userCde") String userCde, @Param("browsePassword") String browsePassword) throws Exception;

    /**
     * 根据企业微信userid查询是否存在
     * @param wxWorkUserId
     * @param id
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and s.wxWorkUserId = :wxWorkUserId and s.id <> :id")
    public SysUser querySysUserByWxWorkUserId(@Param("wxWorkUserId") String wxWorkUserId, @Param("id") String id) throws Exception;

    /**
     * 修改用户绑定企业微信
     * @param wxWorkUserId
     * @param updateUser
     * @param updateTime
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.wxWorkUserId = :wxWorkUserId, sc.updateUser = :updateUser, sc.updateTime = :updateTime where sc.id = :id")
    public void updateWxWorkUserIdByUserId(@Param("wxWorkUserId") String wxWorkUserId, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime, @Param("id") String id) throws Exception;

    /**
     * 根据企业微信userid查询用户信息
     * @param wxWorkUserId
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and s.wxWorkUserId = :wxWorkUserId")
    public SysUser queryUserByWxWorkUserId(@Param("wxWorkUserId") String wxWorkUserId) throws Exception;

    /**
     * 查询已关联企业微信的用户集合
     * @return
     * @throws Exception
     */
    @Query(value="select s from SysUser s where s.type ='USER' and (s.wxWorkUserId is not null or s.wxWorkUserId<>'')")
    public List<SysUser> queryWXWorkUser() throws Exception;

    /**
     * 根据userid更新第三方用户编码
     * @param userId
     * @param thirdPartyCode
     * @param updateTime
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.thirdPartyCode = :thirdPartyCode, sc.updateTime = :updateTime where sc.id = :userId")
    public void updateUserThirdPartyCodeByUserId(@Param("userId") String userId, @Param("thirdPartyCode") String thirdPartyCode, @Param("updateTime") Date updateTime) throws Exception;

    /**
     * 根据userid更新企业微信userid
     * @param userId
     * @param wxWorkUserId
     * @param thirdPartyCode
     * @param updateTime
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysUser sc set sc.thirdPartyCode = :thirdPartyCode,sc.wxWorkUserId = :wxWorkUserId, sc.updateTime = :updateTime where sc.id = :userId")
    public void updateUserWxWorkUserIdByUserId(@Param("userId") String userId, @Param("wxWorkUserId") String wxWorkUserId, @Param("thirdPartyCode") String thirdPartyCode, @Param("updateTime") Date updateTime) throws Exception;

}
