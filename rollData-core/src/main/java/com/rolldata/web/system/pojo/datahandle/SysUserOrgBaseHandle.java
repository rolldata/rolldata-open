package com.rolldata.web.system.pojo.datahandle;

import com.rolldata.web.system.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 把用的相关数据,处理成Map方便获取
 *
 * @Title: SysUserOrgBase
 * @Description: SysUserOrgBase
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-04-25
 * @version: V1.0
 */
public class SysUserOrgBaseHandle {

    /**
     * 当前数据源链接
     */
    private Connection connection;

    /**
     * 当前处理的角色id
     */
    private String roleId;

    /**
     * 当前处理的职务id
     */
    private String postId;

    /**
     * 前台传过来的用户id集合
     */
    private List<String> userIds;

    /**
     * 用户集合
     */
    private List<SysUser> sysUsers;

    /**
     * 基础数据：公司信息
     */
    private Map<String, SysOrg> company = new HashMap<>();

    /**
     * 基础数据：系统全部的部门id-部门数据;因为最终存的是部门,所有其他可以直接用id去这里的数据
     */
    private Map<String, SysUserOrg> departmentInfo = new HashMap<>();

    /**
     * 基础数据：职务id相应数据权限
     */
    private Map<String, List<String>> postOrg = new HashMap<>();

    /**
     * 基础数据：用户id相应用户信息
     */
    private final Map<String, SysUser> userInfo = new HashMap<>();

    /**
     * 基础数据：所有角色的数据权限
     */
    private Map<String, List<String>> roleOrgIdsInfo = new HashMap<>();

    /**
     * 基础数据：用户有几个角色
     */
    private Map<String, List<String>> userRoleIdsInfo = new HashMap<>();

    /**
     * 库中已存在的用户部门
     */
    private Map<String, Set<String>> repateUserDpIdMap = new HashMap<>();

    public SysUserOrgBaseHandle() {
    }

    public SysUserOrgBaseHandle(Connection connection) {
        this.connection = connection;
    }

    public void handleUserInfo(List<SysUser> users) {

        if (null == users) {
            this.sysUsers = Collections.emptyList();
            return;
        }
        this.sysUsers = users;
        for (SysUser user : users) {
            this.userInfo.put(user.getId(), user);
        }
    }

    public void handleUserInfo(ResultSet resultSet) throws SQLException {

        if (null == resultSet) {
            this.sysUsers = Collections.emptyList();
            return;
        }
        this.sysUsers = new ArrayList<>();
        while (resultSet.next()) {
            SysUser user = new SysUser();
            user.setId(resultSet.getString("ID"));
            user.setUserName(resultSet.getString("user_name"));
            user.setUserCde(resultSet.getString("user_cde"));
            user.setOrgId(resultSet.getString("org_id"));
            user.setCompany(resultSet.getString("company"));
            user.setDepartment(resultSet.getString("department"));
            user.setPosition(resultSet.getString("c_position"));
            this.userInfo.put(user.getId(), user);
            this.sysUsers.add(user);
        }
    }

    public void handleDepartmentInfo(List<SysOrg> sysOrgs) {

        for (SysOrg sysOrg : sysOrgs) {
            if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                this.company.put(sysOrg.getId(), sysOrg);
            } else {
                SysOrg parentOrg = this.company.get(sysOrg.getParentId());
                if (null == parentOrg) {
                    continue;
                }
                SysUserOrg sysUserOrg = new SysUserOrg();
                sysUserOrg.setOrgId(parentOrg.getId());
                sysUserOrg.setOrgCode(parentOrg.getOrgCde());
                sysUserOrg.setOrgName(parentOrg.getOrgName());
                sysUserOrg.setDepartmentId(sysOrg.getId());
                sysUserOrg.setDepartmentCode(sysOrg.getOrgCde());
                sysUserOrg.setDepartmentName(sysOrg.getOrgName());

                // 因为公司数据在上面put了,所以这里尽情get
                this.departmentInfo.put(sysOrg.getId(), sysUserOrg);
            }
        }
    }

    /**
     * 用户有几个角色
     *
     * @param userRoles 表里所以数据
     */
    public void handleUserRoleIdsInfo(List<UserRole> userRoles) {

        if (null == userRoles) {
            return;
        }
        for (UserRole userRole : userRoles) {
            List<String> roleIds = this.userRoleIdsInfo.get(userRole.getUserId());
            if (null == roleIds) {
                roleIds = new ArrayList<>();
                this.userRoleIdsInfo.put(userRole.getUserId(), roleIds);
            }
            roleIds.add(userRole.getRoleId());
        }
    }

    /**
     * 处理角色拥有数据权限
     *
     * @param orgRoles 表中所有数据
     */
    public void handleRoleOrgIdsInfo(List<SysOrgRole> orgRoles) {

        if (null == orgRoles) {
            return;
        }
        for (SysOrgRole orgRole : orgRoles) {
            List<String> orgIds = this.roleOrgIdsInfo.get(orgRole.getRoleId());
            if (null == orgIds) {
                orgIds = new ArrayList<>();
                this.roleOrgIdsInfo.put(orgRole.getRoleId(), orgIds);
            }
            orgIds.add(orgRole.getOrgId());
        }
    }

    public void handleRepateUserDpIdMap(List<SysUserOrg> oldDatas) {

        for (SysUserOrg oldData : oldDatas) {
            if (!getUserIds().contains(oldData.getUserId())) {
                continue;
            }
            Set<String> oldDepartmentIds = this.repateUserDpIdMap.get(oldData.getUserId());
            if (null == oldDepartmentIds) {
                oldDepartmentIds = new HashSet<>();
                this.repateUserDpIdMap.put(oldData.getUserId(), oldDepartmentIds);
            }
            oldDepartmentIds.add(oldData.getDepartmentId());
        }
    }

    /**
     * 获取数据库中用户已存在的部门信息
     * <p>如果没任何信息,返回空集合</p>
     *
     * @param userId 用户id
     * @return 部门集合
     */
    public Set<String> _getoldDepartureIds(String userId) {

        Set<String> oldDepartureIds = null;
        if (null != this.repateUserDpIdMap) {
            oldDepartureIds = this.repateUserDpIdMap.get(userId);
        }
        if (null == oldDepartureIds) {
            oldDepartureIds = Collections.emptySet();
        }
        return oldDepartureIds;
    }

    public Map<String, SysUserOrg> getDepartmentInfo() {
        return this.departmentInfo;
    }

    public Map<String, List<String>> getPostOrg() {
        return this.postOrg;
    }

    public Map<String, SysUser> getUserInfo() {
        return this.userInfo;
    }

    public Map<String, List<String>> getRoleOrgIdsInfo() {
        return this.roleOrgIdsInfo;
    }

    public Map<String, List<String>> getUserRoleIdsInfo() {
        return this.userRoleIdsInfo;
    }

    public SysUserOrgBaseHandle setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public SysUserOrgBaseHandle setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    /**
     * 如果为null,返回空串
     *
     * @return 角色id
     */
    public String getRoleId() {
        return null == this.roleId ? "" : this.roleId;
    }

    /**
     * 如果为null,返回空串
     *
     * @return 职务id
     */
    public String getPostId() {
        return null == this.postId ? "" : this.postId;
    }

    public SysUserOrgBaseHandle setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public List<String> getUserIds() {
        if (null == this.userIds) {
            return Collections.emptyList();
        }
        return this.userIds;
    }

    public Map<String, Set<String>> getRepateUserDpIdMap() {
        return this.repateUserDpIdMap;
    }

    public List<SysUser> getSysUsers() {
        return this.sysUsers;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
