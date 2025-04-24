package com.rolldata.web.system.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.rolldata.core.common.exception.JPAHandleException;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.DateUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.pojo.datahandle.SysUserOrgBaseHandle;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.util.DBUtil;
import com.rolldata.web.system.util.DynamicDBUtil;
import com.rolldata.web.system.util.EntitySqlUtils;
import com.rolldata.web.system.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 用户组织权限服务实现
 *
 * @Title:SysUserOrgServiceImpl
 * @Description:SysUserOrgServiceImpl
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-4-23
 * @version:V1.0
 */
@Service("sysUserOrgService")
@Transactional
public class SysUserOrgServiceImpl implements SysUserOrgService {

    private final Logger log = LogManager.getLogger(SysUserOrgServiceImpl.class);

    @Autowired
    private SysUserOrgDao sysUserOrgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private SysOrgRoleDao sysOrgRoleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 字段信息
     */
    private Map<String, SysTableInfo> entityMap;

    /**
     * 查询全部数据
     *
     * @return 数据集合
     */
    @Override
    public List<SysUserOrg> queryAll() {
        return this.sysUserOrgDao.findAll();
    }

    /**
     * 同步用户组织数据,更新全部的用户
     *
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void synchronousUserOrgData() throws Exception {

        this.log.info("初始化用户组织权限表wd_sys_user_org");
        if (!AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            this.log.info("登录用户不是超级管理员,但是调用,怀疑恶意执行！");
            return;
        }
        DruidDataSource druidDataSource = DBUtil.DRUID_DATA_SOURCE;
        try (DruidPooledConnection connection = druidDataSource.getConnection();) {
            boolean fl = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSql())) {
                // 数据处理对象
                SysUserOrgBaseHandle baseHandle = initHandle(connection);
                initHandleUserInfo(connection, baseHandle);
                List<SysUserOrg> oldDatas = this.sysUserOrgDao.findAll();
                baseHandle.handleRepateUserDpIdMap(oldDatas);

                List<SysUser> sysUsers = baseHandle.getSysUsers();
                Map<String, List<String>> userRoleIdsInfo = baseHandle.getUserRoleIdsInfo();
                for (int i = 0; i < sysUsers.size(); i++) {

                    // 重复的部门id,因为并集可能重复
                    Set<String> repateDpIdSet = new HashSet<>();
                    SysUser sysUser = sysUsers.get(i);

                    // 重新添加角色
                    saveUserRoles(preparedStatement, sysUser, repateDpIdSet, baseHandle, userRoleIdsInfo.get(sysUser.getId()));

                    // 保存用户本身部门权限
                    saveSelfDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle);
                }
                preparedStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(fl);
            } catch (Exception pe) {
                throw pe;
            }
        }
    }

    /**
     * 保存角色时,创建用户的组织权限
     * <p>删除当前角色上次的数据权限,不动其他角色权限,所以不用新增其他角色的数据权限
     * <p>当前角色组织权限+职务组织权限
     *
     * @param departmentIds 部门id集合
     * @param userIds       用户id集合
     * @param roleId        角色id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void createEntitysByRoleId(Connection connection,
            List<String> departmentIds, List<String> userIds, String roleId) throws Exception {

        Objects.requireNonNull(connection, "数据库连接异常");
        if (null == departmentIds ||
            null == userIds ||
            departmentIds.isEmpty() ||
            userIds.isEmpty() ||
            StringUtil.isEmpty(roleId)) {
            return;
        }

        // 数据处理对象
        SysUserOrgBaseHandle baseHandle = initHandle(connection);
        initHandleUserInfo(connection, baseHandle);
        baseHandle.setRoleId(roleId);
        baseHandle.setUserIds(userIds);
        deleteUserDepartmentByRole(connection, roleId);
        DynamicDBUtil.executeQuery(
            connection,
            "select t.user_id, t.department_id from wd_sys_user_org t",
            null,
            (resultSet) ->{
                while (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    String departmentId = resultSet.getString("department_id");
                    if (!userIds.contains(userId)) {
                        continue;
                    }
                    Set<String> oldDepartmentIds = baseHandle.getRepateUserDpIdMap().get(userId);
                    if (null == oldDepartmentIds) {
                        oldDepartmentIds = new HashSet<>();
                        baseHandle.getRepateUserDpIdMap().put(userId, oldDepartmentIds);
                    }
                    oldDepartmentIds.add(departmentId);
                }
                return null;
            }
        );
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSql())) {
            executeRoleHandle(preparedStatement, userIds, baseHandle, departmentIds);
            preparedStatement.executeBatch();
        } catch (Exception pe) {
            throw pe;
        }
    }

    /**w
     * 保存职务时,创建用户的组织权限
     * <p>删除当前职务上次的数据权限,不动其他职务权限,所以不用新增其他职务的数据权限
     * <p>当前职务组织权限+用户的角色组织权限
     *
     * @param departmentIds 部门id集合
     * @param userIds       用户id集合
     * @param postId        职务id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void createEntitysByPostId(List<String> departmentIds, List<String> userIds, String postId)
            throws Exception {

        if (null == departmentIds ||
            null == userIds ||
            departmentIds.isEmpty() ||
            userIds.isEmpty() ||
            StringUtil.isEmpty(postId)) {
            return;
        }
        DruidDataSource druidDataSource = DBUtil.DRUID_DATA_SOURCE;
        try (DruidPooledConnection connection = druidDataSource.getConnection();) {
            boolean fl = connection.getAutoCommit();
            connection.setAutoCommit(false);

            // 数据处理对象
            SysUserOrgBaseHandle baseHandle = initHandle(connection);
            initHandleUserInfo(connection, baseHandle);
            baseHandle.setPostId(postId);
            baseHandle.setUserIds(userIds);
            deleteUserDepartmentByPost(postId);
            List<SysUserOrg> oldDatas = this.sysUserOrgDao.findAll();
            baseHandle.handleRepateUserDpIdMap(oldDatas);
            executeDepartmentHandle(userIds, baseHandle, departmentIds);
            connection.commit();
            connection.setAutoCommit(fl);
        }
    }

    /**
     * 修改用户名称
     *
     * @param userId 用户id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void createUserInfo(SysUser sysUser) throws Exception {

        if (null == sysUser) {
            return;
        }
        SysOrg orgEntity = this.orgDao.queryEntityById(sysUser.getOrgId());
        Objects.requireNonNull(orgEntity, "公司不存在");
        SysOrg departmentEntity = this.orgDao.queryEntityById(sysUser.getDepartment());
        Objects.requireNonNull(orgEntity, "部门不存在");
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setUserId(sysUser.getId());
        sysUserOrg.setUserCode(sysUser.getUserCde());
        sysUserOrg.setUserName(sysUser.getUserName());
        sysUserOrg.setOrgId(orgEntity.getId());
        sysUserOrg.setOrgCode(orgEntity.getOrgCde());
        sysUserOrg.setOrgName(orgEntity.getOrgName());
        sysUserOrg.setDepartmentId(departmentEntity.getId());
        sysUserOrg.setDepartmentCode(departmentEntity.getOrgCde());
        sysUserOrg.setDepartmentName(departmentEntity.getOrgName());
        sysUserOrg.setCreateTime(new Date());
        sysUserOrg.setCreateUser(UserUtils.getUser().getId());
        this.sysUserOrgDao.saveAndFlush(sysUserOrg);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser 用户信息
     * @param roleIds 角色id集合
     * @throws Exception
     */
    @Override
    public void updateUserInfo(SysUser sysUser, List<String> roleIds) throws Exception {

        if (null == sysUser) {
            return;
        }
        DruidDataSource druidDataSource = DBUtil.DRUID_DATA_SOURCE;
        try (DruidPooledConnection connection = druidDataSource.getConnection();) {
            boolean fl = connection.getAutoCommit();
            connection.setAutoCommit(false);

            // 删除当前用户的数据
            String sql = "delete from wd_sys_user_org where user_id = ?";
            DynamicDBUtil.executeSQL(connection, sql, Collections.singletonList(sysUser.getId()));


            // 数据处理对象
            SysUserOrgBaseHandle baseHandle = initHandle(connection);
            Set<String> repateDpIdSet = new HashSet<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSql())) {

                // 重新添加角色
                saveUserRoles(preparedStatement, sysUser, repateDpIdSet, baseHandle, roleIds);

                // 保存用户本身部门权限
                saveSelfDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle);
                preparedStatement.executeBatch();
            } catch (Exception pe) {
                throw pe;
            }
            connection.commit();
            connection.setAutoCommit(fl);
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    private String getSql() throws SQLException {

        String name = SysUserOrg.class.getName();
        if (null == this.entityMap) {
            this.entityMap = EntitySqlUtils.getEntityColum().get(name);
            Objects.requireNonNull(this.entityMap, "初始化实体映射字段");
        }
        return EntitySqlUtils.insertSql(name);
    }

    /**
     * 修改用户名称
     *
     * @param userId   用户id
     * @param userName 用户名称
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void updateUserNameByUserId(String userId, String userName) throws Exception {

        if (StringUtil.isEmpty(userId) ||
            StringUtil.isEmpty(userName)) {
            return;
        }
        this.sysUserOrgDao.updateUserNameByUserId(userId, userName);
    }

    /**
     * 修改用户名称,并判断组织是否修改(在update用户前执行)
     *
     * @param sysUser 用户信息
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void updateUserNameByUserCode(SysUser sysUser) throws Exception {

        if (null == sysUser) {
            return;
        }
        SysUser oldUser = this.userDao.getUserByUserCde(sysUser.getUserCde());
        if (StringUtil.isEmpty(sysUser.getDepartment())) {
            throw new Exception("部门ID为空");
        }

        // 部门改变,删除公司部门,新增
        if (!sysUser.getDepartment().equals(oldUser.getDepartment())) {
            this.sysUserOrgDao.updateEntityByOrgInfo(
                sysUser.getUserCde(),
                sysUser.getOrgId(),
                sysUser.getDepartment(),
                oldUser.getOrgId(),
                oldUser.getDepartment()
            );
        }
        this.sysUserOrgDao.updateUserNameByUserCode(sysUser.getUserCde(), sysUser.getUserName());
    }

    /**
     * 修改组织名称
     *
     * @param orgId   组织id
     * @param orgName 组织名称
     * @param orgCde
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void updateOrgNameByOrgId(String orgId, String orgName, String orgCde) throws Exception {

        if (StringUtil.isEmpty(orgId) ||
            StringUtil.isEmpty(orgName) ||
            StringUtil.isEmpty(orgCde)) {
            return;
        }
        this.sysUserOrgDao.updateOrgNameByOrgId(orgId, orgName, orgCde);
        this.saveAdmin();
    }

    /**
     * 修改部门名称
     *
     * @param departmentId   部门id
     * @param departmentName 部门名称
     * @param orgCde
     */
    @Override
    public void updateOrgNameByDepartmentId(String departmentId, String departmentName, String orgCde) throws Exception {

        if (StringUtil.isEmpty(departmentId) ||
            StringUtil.isEmpty(departmentName) ||
            StringUtil.isEmpty(orgCde)) {
            return;
        }
        this.sysUserOrgDao.updateOrgNameByDepartmentId(departmentId, departmentName, orgCde);
        this.saveAdmin();
    }

    /**
     * 删除用户
     *
     * @param userIds 用户id集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteEntitysByUserIds(List<String> userIds) throws Exception {

        if (null == userIds ||
            userIds.isEmpty()) {
            return;
        }
        this.sysUserOrgDao.deleteEntitysByUserIds(userIds);
    }

    /**
     * 删除组织
     *
     * @param orgIds 组织id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteEntitysByOrgIds(List<String> orgIds) throws Exception {

        if (null == orgIds ||
            orgIds.isEmpty()) {
            return;
        }
        this.sysUserOrgDao.deleteEntitysByOrgIds(orgIds);
        this.saveAdmin();
    }

    /**
     * 删除部门
     *
     * @param departmentIds 部门id集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteEntitysByDepartmentIds(List<String> departmentIds) throws Exception {

        if (null == departmentIds ||
            departmentIds.isEmpty()) {
            return;
        }
        this.sysUserOrgDao.deleteEntitysByDepartmentIds(departmentIds);
        this.saveAdmin();
    }

    /**
     * 初始化基本参数
     *
     * @return SysUserOrgBaseHandle
     */
    private SysUserOrgBaseHandle initHandle(Connection connection) throws Exception {

        SysUserOrgBaseHandle baseHandle = new SysUserOrgBaseHandle(connection);
        try {

            // 组织表所有数据
            List<SysOrg> sysOrgs = this.orgDao.queryAllOrgsOrderByType();
            baseHandle.handleDepartmentInfo(sysOrgs);

            List<SysOrgRole> orgRoles = this.sysOrgRoleDao.findAll();
            baseHandle.handleRoleOrgIdsInfo(orgRoles);

            List<UserRole> userRoles = this.userRoleDao.findAll();
            baseHandle.handleUserRoleIdsInfo(userRoles);

        } catch (Exception e) {
            e.printStackTrace();
            throw new JPAHandleException("准备基础数据错误", e);
        }
        return baseHandle;
    }

    private void initHandleUserInfo(Connection connection, SysUserOrgBaseHandle baseHandle)
            throws Exception {

        // 所有用户
        DynamicDBUtil.executeQuery(
            connection,
            "select t.ID, t.user_name, t.user_cde, t.org_id, t.company, t.department,"
                + " t.c_position from wd_sys_user t where t.user_type = 'USER'",
            null,
            (resultSet) ->{
                baseHandle.handleUserInfo(resultSet);
                return null;
            }
        );
    }

    /**
     * 执行角色
     *
     * @param preparedStatement
     * @param userIds       用户id集合
     * @param baseHandle    基本参数对象
     * @param departmentIds 部门id集合
     */
    private void executeRoleHandle(PreparedStatement preparedStatement, List<String> userIds,
            SysUserOrgBaseHandle baseHandle, List<String> departmentIds) throws Exception {

        // 重复的部门id,因为并集可能重复
        Set<String> repateDpIdSet = new HashSet<>();
        try {
            for (String userId : userIds) {
                SysUser sysUser = baseHandle.getUserInfo().get(userId);
                if (null == sysUser) {
                    continue;
                }

                // 清空上一个人的信息,重新add
                repateDpIdSet.clear();

                // 前台传递的角色勾选的部门id集合
                saveDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle, departmentIds);

                // 保存用户本身部门权限
                saveSelfDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JPAHandleException("", e);
        }
    }


    /**
     * 执行职务
     *
     * @param userIds       用户id集合
     * @param baseHandle    基本参数对象
     * @param departmentIds 部门id集合
     */
    private void executeDepartmentHandle(List<String> userIds, SysUserOrgBaseHandle baseHandle,
            List<String> departmentIds) {

        return;
    }

    /**
     * 删除角色拥有权限的部门
     *
     * @param roleId 角色id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteUserDepartmentByRole(String roleId) throws Exception {

        /*
         * 根据角色删除所有用户的数据权限,重新插入(不用in删除人了,ORACLE有限制ORA-01795)
         * 在实现的过程中,又考虑了考虑,还是用前台传的id吧.
         * 至少这样肯定是对的.
         */

        // 删除角色管理的所有人 的数据权限
        this.sysUserOrgDao.deleteByRoleId(roleId);
    }

    public void deleteUserDepartmentByRole(Connection connection, String roleId) throws Exception {

        /*
         * 根据角色删除所有用户的数据权限,重新插入(不用in删除人了,ORACLE有限制ORA-01795)
         * 在实现的过程中,又考虑了考虑,还是用前台传的id吧.
         * 至少这样肯定是对的.
         */

        // 删除角色管理的所有人 的数据权限
        String sql = "delete from wd_sys_user_org where id in (select temp.id from (select t.id from wd_sys_user_org t" +
                     " where t.user_id in (" +
                     " select ur.user_id from wd_sys_userrole ur where ur.role_id = ?" +
                     " ) and t.department_id in (" +
                     " select sor.org_id from wd_sys_orgrole sor where sor.role_id = ?" +
                     " )) temp)";
        List<String> pValues = new ArrayList<>(2);
        pValues.add(roleId);
        pValues.add(roleId);
        DynamicDBUtil.executeSQL(connection, sql, pValues);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void deleteUserDepartmentByPost(String postId) {

        // TODO： 职务目前没有选数据权限
        // 删除职务管理的所有人 的数据权限
//        this.sysUserOrgDao.deleteByPostId(postId);
    }

    /**
     * 保存用户本身部门权限
     *
     * @param departmentInfo
     * @param preparedStatement
     * @param sysUser        用户对象
     * @param repateDpIdSet  部门重复id
     */
    private void saveSelfDepartment(PreparedStatement preparedStatement, SysUser sysUser,
            Set<String> repateDpIdSet, SysUserOrgBaseHandle baseHandle) throws Exception {

        if (StringUtil.isEmpty(sysUser.getDepartment())) {
            return;
        }
        Map<String, SysUserOrg> departmentInfo = baseHandle.getDepartmentInfo();
        if (repateDpIdSet.contains(sysUser.getDepartment())) {
            return;
        }
        Set<String> oldDepartureIds = baseHandle._getoldDepartureIds(sysUser.getId());
        if (oldDepartureIds.contains(sysUser.getDepartment())) {
            return;
        }
        SysUserOrg sysUserOrg = departmentInfo.get(sysUser.getDepartment());
        if (null == sysUserOrg) {

            // 用户在,部门没了
            return;
        }
        sysUserOrg._setUserInfo(sysUser.getId(), sysUser.getUserCde(), sysUser.getUserName());
        saveEntity(preparedStatement, sysUserOrg);
        repateDpIdSet.add(sysUser.getDepartment());
    }

    /**
     * 保存用户职务的部门
     *
     * @param sysUser       用户对象
     * @param repateDpIdSet 重复部门记录
     * @param baseHandle    基本处理对象
     */
    private void savePostDepartment(SysUser sysUser, Set<String> repateDpIdSet, SysUserOrgBaseHandle baseHandle)
            throws Exception {

        Map<String, List<String>> postOrg = baseHandle.getPostOrg();
        if (StringUtil.isEmpty(sysUser.getPosition()) ||
            null == postOrg) {
            return;
        }

        // 保存职务时,不处理当前的职务id
        if (baseHandle.getPostId().equals(sysUser.getPosition())) {
            return;
        }
        List<String> orgIds = postOrg.get(sysUser.getPosition());
//        saveDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle, orgIds);
    }

    /**
     * 保存用户角色的部门
     *
     * @param sysUser       用户对象
     * @param repateDpIdSet 重复部门记录
     * @param baseHandle    基本处理对象
     */
    private void saveRoleDepartment(SysUser sysUser, Set<String> repateDpIdSet,
            SysUserOrgBaseHandle baseHandle) throws Exception {

        Map<String, List<String>> userRoleIdsInfo = baseHandle.getUserRoleIdsInfo();
        List<String> userRoleIds = userRoleIdsInfo.get(sysUser.getId());
        if (null == userRoleIds) {
            return;
        }
        Map<String, List<String>> roleOrgIdsInfo = baseHandle.getRoleOrgIdsInfo();
        for (String userRoleId : userRoleIds) {
            if (baseHandle.getRoleId().equals(userRoleId)) {
                continue;
            }

            // 根据角色id获取角色的数据权限
            List<String> roleOrgIds = roleOrgIdsInfo.get(userRoleId);
//            saveDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle, roleOrgIds);
        }
    }

    /**
     * 保存部门信息
     *
     * @param preparedStatement
     * @param sysUser       用户对象
     * @param repateDpIdSet 重复部门记录
     * @param baseHandle    基本处理对象
     * @param orgIds        部门id集合
     */
    public void saveDepartment(PreparedStatement preparedStatement, SysUser sysUser, Set<String> repateDpIdSet,
                               SysUserOrgBaseHandle baseHandle, List<String> orgIds) throws Exception {

        if (null == orgIds) {
            return;
        }
        Map<String, SysUserOrg> departmentInfo = baseHandle.getDepartmentInfo();
        Set<String> oldDepartureIds = baseHandle._getoldDepartureIds(sysUser.getId());
        for (String orgId : orgIds) {
            if (repateDpIdSet.contains(orgId)) {
                continue;
            }
            SysUserOrg sysUserOrg = departmentInfo.get(orgId);

            // 可能是公司,跳过
            if (null == sysUserOrg) {
                continue;
            }
            if (oldDepartureIds.contains(sysUserOrg.getDepartmentId())) {
                continue;
            }
            sysUserOrg._setUserInfo(sysUser.getId(), sysUser.getUserCde(), sysUser.getUserName());
            saveEntity(preparedStatement, sysUserOrg);
            repateDpIdSet.add(orgId);
        }
    }

    /**
     * 保存用户所有角色的数据权限
     *
     * @param preparedStatement
     * @param sysUser       用户信息
     * @param repateDpIdSet 部门重复id
     * @param baseHandle    基本处理对象
     * @param roleIds       角色id集合
     */
    private void saveUserRoles(PreparedStatement preparedStatement, SysUser sysUser, Set<String> repateDpIdSet,
                               SysUserOrgBaseHandle baseHandle, List<String> roleIds) throws Exception {

        if (null == roleIds ||
            roleIds.isEmpty()) {
            return;
        }
        Map<String, List<String>> roleOrgIdsInfo = baseHandle.getRoleOrgIdsInfo();
        for (String roleId : roleIds) {
            List<String> roleOrgIds = roleOrgIdsInfo.get(roleId);
            if (null == roleOrgIds) {
                continue;
            }
            saveDepartment(preparedStatement, sysUser, repateDpIdSet, baseHandle, roleOrgIds);
        }
    }

    /**
     * 保存实体
     *
     * @param ps
     * @param sysUserOrg
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void saveEntity(PreparedStatement ps, SysUserOrg sysUserOrg) throws Exception {

        //sysUserOrg.setCreateTime(new Date());
        //sysUserOrg.setCreateUser(UserUtils.getUser().getId());
        //SysUserOrg newSysUserOrg = SysUserOrg.newInstanceData(sysUserOrg);
        //EntitySqlUtils.addBatch(newSysUserOrg, SysUserOrg.class.getName(), ps);
        EntitySqlUtils.parameterString(this.entityMap, ps, "id", UUIDGenerator.generate());
        EntitySqlUtils.parameterString(this.entityMap, ps, "userId", sysUserOrg.getUserId());
        EntitySqlUtils.parameterString(this.entityMap, ps, "userCode", sysUserOrg.getUserCode());
        EntitySqlUtils.parameterString(this.entityMap, ps, "userName", sysUserOrg.getUserName());
        EntitySqlUtils.parameterString(this.entityMap, ps, "orgId", sysUserOrg.getOrgId());
        EntitySqlUtils.parameterString(this.entityMap, ps, "orgCode", sysUserOrg.getOrgCode());
        EntitySqlUtils.parameterString(this.entityMap, ps, "orgName", sysUserOrg.getOrgName());
        EntitySqlUtils.parameterString(this.entityMap, ps, "departmentId", sysUserOrg.getDepartmentId());
        EntitySqlUtils.parameterString(this.entityMap, ps, "departmentCode", sysUserOrg.getDepartmentCode());
        EntitySqlUtils.parameterString(this.entityMap, ps, "departmentName", sysUserOrg.getDepartmentName());
        EntitySqlUtils.parameterString(this.entityMap, ps, "isSystem", sysUserOrg.getIsSystem());
        EntitySqlUtils.parameterString(this.entityMap, ps, "createTime", DateUtils.date2Str(new Date(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        EntitySqlUtils.parameterString(this.entityMap, ps, "createUser", UserUtils.getUser().getId());
        ps.addBatch();
//        ps.setString();
//        ps.addBatch();
//        this.sysUserOrgDao.saveAndFlush(newSysUserOrg);
    }

    /**
     * 新增,删除部门组织时,给超级管理员赋权限
     *
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void saveAdmin() throws Exception {

        SysUser sysUser = this.userDao.getUserByUserCde(AdministratorUtils.getAdministratorName());
        Objects.requireNonNull(sysUser, "超级管理员权限异常");

        // 删除超级管理员
        this.sysUserOrgDao.deleteByUserId(sysUser.getId());
        SysUserOrgBaseHandle baseHandle = new SysUserOrgBaseHandle();

        // 组织表所有数据
        List<SysOrg> sysOrgs = this.orgDao.queryAllOrgsOrderByType();
        baseHandle.handleDepartmentInfo(sysOrgs);
        Map<String, SysUserOrg> departmentInfo = baseHandle.getDepartmentInfo();

        // 没有组织部门时
        if (departmentInfo.isEmpty()) {
            return;
        }
        Iterator<Entry<String, SysUserOrg>> dpIter = departmentInfo.entrySet().iterator();
        List<SysUserOrg> newSysUserOrgs = new ArrayList<>();
        Date createTime = new Date();
        String userId = UserUtils.getUser().getId();

        // 添加
        while (dpIter.hasNext()) {
            Entry<String, SysUserOrg> row = dpIter.next();
            SysUserOrg sysUserOrg = row.getValue();
            sysUserOrg._setUserInfo(sysUser.getId(), sysUser.getUserCde(), sysUser.getUserName());
            sysUserOrg.setCreateTime(createTime);
            sysUserOrg.setCreateUser(userId);
            SysUserOrg newSysUserOrg = SysUserOrg.newInstanceData(sysUserOrg);
            newSysUserOrgs.add(newSysUserOrg);
        }
        this.sysUserOrgDao.saveAll(newSysUserOrgs);
    }
}
