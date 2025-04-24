package com.rolldata.web.system.service;


import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @Title: UserService
 * @Description:
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-24
 * @version V1.0
 */
public interface UserService {
    
    /**
     * 保存用户
     * @param user
     * @throws Exception
     */
    public SysUser save(SysUser user) throws Exception;
    
    /**
     * 新建用户目录（文件夹）
     * @param user
     * @throws Exception
     */
    public void saveFolder(SysUser user)throws Exception;
        
    /**
     * 当前节点下文件夹是否重名
     * @param user
     * @throws Exception
     * @createDate 2018-6-19
     */
    public Boolean folderIsReName(SysUser user) throws Exception;
    
    /**
     * 修改用户启停状态
     * @param user
     * @throws Exception
     * @createDate 2018-6-19
     */
    public void updateIsactive(SysUser user) throws Exception;
    
    /**
     * 验证用户唯一
     * @param user
     * @return
     * @throws Exception
     */
    public Boolean doValid(SysUser user) throws Exception;
    
    /**
     * 根据用户名查用户
     * @param userCde
     * @return
     * @throws Exception
     */
    public SysUser getUserByUserCde(String userCde) throws Exception;
    
    /**
     * 根据id查用户
     * @param userCde
     * @return
     * @throws Exception
     */
    public SysUser getUserById(String id) throws Exception;
    
    /**
     * 用户分页信息
     * @param pageJson
     * @return
     * @throws Exception
     */
    public void getUserList(PageJson pageJson) throws Exception;
    
    /**
     * 修改用户基本信息
     * @param user
     * @throws Exception
     */
    public void updateUserInfo(SysUser user) throws Exception;
    
    /**
     * 修改文件夹基本信息
     * @param user
     * @throws Exception
     */
    public void updateFolderInfo(SysUser user) throws Exception;
    
    /**
     * 修改用户parentId
     * @throws Exception
     */
    public void updateParentId(SysUser sysUser) throws Exception;
    
    
    /**
     * 修改密码
     * @param user
     * @throws Exception
     */
    public void updatePassword(SysUser user) throws Exception;

    /**
     * 初始用户修改密码
     * @param user
     * @throws Exception
     */
    public void updateInitPassword(SysUser user) throws Exception;

    /**
     * 删除用户
     * @param user
     * @throws Exception
     */
    public void delete(SysUser user) throws Exception;

    /**
     * 权限分配tree
     * @throws Exception
     */
    public UserTreeParent queryUserLimitTree() throws Exception;
    
    /**
     * 获取当前用户最大权限下所有的用户信息
     * @param aList 
     * @throws Exception
     */
    public AllUserListParent queryAllUserList(AllUserListParent aList) throws Exception;
    
    /**
     * 获取用户信息
     * @throws Exception
     */
    public UserDetailedJson queryUserDetailed(String userId) throws Exception;
    
    /**
     * 拼接返回对象ResponseUserJson
     * @throws Exception
     */
    public ResponseUserJson changeResponseUserJson(SysUser sysUser) throws Exception;
    
    /**
     * 判断是否有子节点
     * @author shenshilong
     * @param id
     * @return
     * @throws Exception
     * @createDate 2018-8-1
     */
    public Boolean isHaveChildren(String id)throws Exception;
    
    /**
     * 批量删除
     * @param sysUsers
     * @throws Exception
     */
    public void delete(List<String> userIds) throws Exception;

    /**
     * 根据用户类型查询所有数量
     * @param userType
     * @return
     * @throws Exception
     */
    public int queryAllUserListByUserType(String userType) throws Exception;
    
    /**
     * 修改头像
     * @param headPhotoName
     * @throws Exception
     */
    public void updateHeadPhoto(String headPhotoName) throws Exception;

    /**
     * 重置密码
     * @param user
     * @throws Exception
     */
    public void updateResetPassword(SysUser user) throws Exception;

    public void updateAllUserPassword(String password) throws Exception ;

    public UserDetailedJson queryNowUserInfo() throws Exception ;

    /**
     * 解锁用户
     * @param sysUser
     * @throws Exception
     */
//    public void updateIslockedById(SysUser sysUser) throws Exception ;

    /**
     * 解锁用户
     * @param sysUser
     * @throws Exception
     */
    public void updateIslockedByCde(SysUser sysUser) throws Exception ;

    /**
     * 个人更新用户信息 手机、电话
     * @param rUserJson
     * @throws Exception
     */
    public void updateUserPhone(UserDetailedJson rUserJson) throws Exception ;

    /**
     * 用户组织导入
     * @param ajax 储存信息的对象
     * @param uploadFile 模版文件
     * @throws Exception
     */
    public void importOrgUsers(AjaxJson ajax, CommonsMultipartFile uploadFile) throws Exception;

    /**
     * 执行导入操作
     * @param _uuid 唯一标识
     * @throws Exception
     */
    public void complyImportOrgUser(String _uuid) throws Exception;

    /**
     * 根据微信id查询用户信息
     * @param unionid
     * @return
     * @throws Exception
     */
    public SysUser queryUserByUnionid(String unionid) throws Exception;

    /**
     * 根据第三方用户编码或id查询用户信息
     * @param thirdPartyCode
     * @return
     * @throws Exception
     */
    public SysUser queryUserByThirdPartyCode(String thirdPartyCode) throws Exception;

    /**
     * 查询角色所能控制的用户集合
     * @param roleIds 角色id集合
     * @throws Exception
     */
    public List<SysUser> querySysUsersByRoleIds(List<String> roleIds) throws Exception;

    /**
     * 查询全部用户
     * @return
     * @throws Exception
     */
    public List<SysUser> queryAllSysUsers() throws Exception;

    /**
     * 公司或部门下用户集合
     *
     * @param orgId   组织id
     * @param orgType 组织类型
     * @return
     * @throws Exception
     */
    public List<SysUser> queryUsersByOrgIdAndType(String orgId, String orgType) throws Exception;

    /**
     * 查询组织下所有用户列表
     *
     * @param roleSelectOrg 查询多想
     * @return
     * @throws Exception
     */
    public List<SysUser> allUsers(RoleSelectOrg roleSelectOrg) throws Exception;

    public List<SysUser> deptIdUsers(RoleSelectOrg roleSelectOrg) throws Exception;

    /**
     * 根据用户id修改组织id
     * @param rUserJson
     * @throws Exception
     */
    public void updateUserOrgIdByUserId(UserDetailedJson rUserJson) throws Exception;
    
    /**
     * 根据用户id修改部门id
     * @param rUserJson
     * @throws Exception
     */
    public void updateUserDeptIdByUserId(UserDetailedJson rUserJson) throws Exception;

    /**
     * 根据勾选的组织部门，查询下属配置过资源密码的用户列表
     * @param roleSelectOrg
     * @return
     * @throws Exception
     */
    public List<SysUser> queryIsBrowseUsers(RoleSelectOrg roleSelectOrg) throws Exception;

    /**
     * 管理员重置用户的访问资源密码，用户id集合，状态改为未修改过默认的访问密码
     * @param rUserJson
     * @throws Exception
     */
    public void updateResetIsBrowsePassword(UserDetailedJson rUserJson) throws Exception;

    /**
     * 根据企业微信userid查询是否存在
     * @param userId
     * @return
     * @throws Exception
     */
    public SysUser queryUserByWxWorkUserId(String userId) throws Exception;

    /**
     * 根据userid更新企业微信userid
     * @param userId
     * @param wxWorkUserId
     * @throws Exception
     */
    public void updateUserWxWorkUserIdByUserId(String userId, String wxWorkUserId) throws Exception;

    /**
     * 根据userid更新第三方用户编码
     * @param userId
     * @param thirdPartyCode
     * @throws Exception
     */
    public void updateUserThirdPartyCodeByUserId(String userId, String thirdPartyCode) throws Exception;
}
