package com.rolldata.web.system.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.*;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.RoleService;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.util.DBUtil;
import com.rolldata.web.system.util.DynamicDBUtil;
import com.rolldata.web.system.util.EntitySqlUtils;
import com.rolldata.web.system.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @Title: RoleServiceImpl
 * @Description:角色实现类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private SysRolePowerDao sysRolePowerDao;
    
    @Autowired
    private SysOrgRoleDao sysOrgRoleDao;

    @Autowired
    private UserRoleDao userRoleDao;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;
    
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    
    @Autowired
    private SysUserOrgService sysUserOrgService;

    private EntityManagerFactory emf;

    // 使用这个标记来注入EntityManagerFactory
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * 保存
     * @param roleDetailedInfo
     * @return
     * @throws Exception
     */
    @Override
    public MainRoleTreeResponse save(RoleDetailedInfo roleDetailedInfo) throws Exception {
    
        MainRoleTreeResponse mainRoleTreeResponse = new MainRoleTreeResponse();
        List<MainRoleTree> treeNodes = new ArrayList<MainRoleTree>();
        MainRoleTree mainRoleTree = new MainRoleTree();
        SysRole role = new SysRole();
        role.setState(SysRole.STATUS_NORMAL);
        role.setRoleCde(roleDetailedInfo.getRoleCde());
        role.setRoleName(roleDetailedInfo.getRoleName());
        role.setCreateTime(new Date());
        role.setCreateUser(UserUtils.getUser().getId());
        role.setUpdateTime(new Date());
        role.setUpdateUser(UserUtils.getUser().getId());
        role.setRemark(roleDetailedInfo.getRemark());
        role.setIsAdmin(roleDetailedInfo.getIsAdmin());
        if(StringUtil.isNotEmpty(roleDetailedInfo.getWdModelId())){
            role.setWdModelId(roleDetailedInfo.getWdModelId());
        }
        role = roleDao.save(role);
        roleDetailedInfo.setRoleId(role.getId());
        roleDetailedInfo.setState(SysRole.STATUS_NORMAL);
        mainRoleTree.setName(roleDetailedInfo.getRoleName());
        mainRoleTree.setType(TreeNodeType.ROLE);
        mainRoleTree.setId(role.getId());
        mainRoleTree.setRoleId(role.getId());
        mainRoleTree.setpId(SysRole.TREENODE_ROOT);
        treeNodes.add(mainRoleTree);
        RoleAuthorize roleAuthorize = new RoleAuthorize();
        UserAuthorize userAuthorize = new UserAuthorize();
        /*roleAuthorize.setEnterpriseReportIds(new ArrayList<AuthorizeParentId>());
        roleAuthorize.setOrgIds(new ArrayList<String>());
        roleAuthorize.setFormIds(new ArrayList<AuthorizeParentId>());
        roleAuthorize.setFuncIds(new ArrayList<AuthorizeBase>());
        userAuthorize.setUserIds(new ArrayList<String>());*/
        roleDetailedInfo.setUserAuthorize(userAuthorize);
        roleDetailedInfo.setRoleAuthorize(roleAuthorize);
        mainRoleTreeResponse.setInfo(roleDetailedInfo);
        mainRoleTreeResponse.setTreeNodes(treeNodes);
        return mainRoleTreeResponse;
    }
    
    /**
     * 删除
     * @param roleIds
     * @throws Exception
     */
    @Override
    public void del(List<String> roleIds) throws Exception {
        
        for (String roleId : roleIds) {
            delete(roleId);
        }
    }
    
    /**
     * 批量删除
     *
     * @param sysRoles
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void delete(List<SysRole> sysRoles) throws Exception {
        for (SysRole sysRole : sysRoles) {
            delete(sysRole.getId());
        }
    }

    /**
     * 获取管理员角色及当前用户的角色集合
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getRoleList () throws Exception {

        List<String> roleIds = new ArrayList<String>();
        List<SysRole> roleObjs = UserUtils.getRoleList();
        boolean isAdminRole = false;
        for (int i = 0; i < roleObjs.size(); i++) {
            SysRole sysRole = roleObjs.get(i);
            if(AdministratorUtils.getAdminRoleCde().equals(sysRole.getRoleCde())) {//判断是否有管理员角色，没有管理员角色要给管理员另外增加
                isAdminRole = true;
            }
            roleIds.add(sysRole.getId());
        }
        if(!isAdminRole) {
            SysRole existRole = roleDao.getSysRoleByRoleCde(AdministratorUtils.getAdminRoleCde());
            roleIds.add(existRole.getId());
        }
        return roleIds;
    }

    /**
     * 删除
     * @param roleId
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void delete (String roleId) throws Exception {

        // 删除wd_sys_user_org
        this.sysUserOrgService.deleteUserDepartmentByRole(roleId);
        
        //删除角色表
        roleDao.deleteSysRoleById(roleId);
    
        //删除关联的菜单和按钮
        sysRolePowerDao.deleteSysRolePowerByRoleId(roleId);
    
        //删除数据权限
        sysOrgRoleDao.deleteSysOrgRoleByRoleId(roleId);
    
        //删除分析权限 表
        sysRoleMenuDao.deleteByRoleId(roleId);
        
        //删除归入用户
        userRoleDao.deleteByRoleId(roleId);

    }
    
    /**
     * 校验rolecde
     *
     * @param ajax
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public void before (AjaxJson ajax, String name, String code, String id) throws Exception {
        
        SysRole existRole = roleDao.getSysRoleByRoleCde(code);
        SysRole namerepeatRole = roleDao.querySysRoleByRoleName(name);
        if (null != existRole) {
            if (!existRole.getId().equals(id)) {
                ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessageOrSelf("common.sys.code.exis", code));
            }
        }
        if (null != namerepeatRole) {
            if (!namerepeatRole.getId().equals(id)) {
                ajax.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessageOrSelf("common.sys.code.exis", name));
            }
        }
    }
    
    /**
     * 更新
     *
     * @param roleDetailedInfo
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation= Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public MainRoleTreeResponse update(RoleDetailedInfo roleDetailedInfo) throws Exception{

        MainRoleTreeResponse mainRoleTreeResponse = new MainRoleTreeResponse();
        List<MainRoleTree> treeNodes = new ArrayList<>();
        MainRoleTree mainRoleTree = new MainRoleTree();
        RoleAuthorize roleAuthorize = roleDetailedInfo.getRoleAuthorize();
        UserAuthorize userAuthorize = roleDetailedInfo.getUserAuthorize();
        mainRoleTree.setName(roleDetailedInfo.getRoleName());
        mainRoleTree.setType(TreeNodeType.ROLE);
        mainRoleTree.setId(roleDetailedInfo.getRoleId());
        mainRoleTree.setRoleId(roleDetailedInfo.getRoleId());
        treeNodes.add(mainRoleTree);
        mainRoleTreeResponse.setInfo(roleDetailedInfo);
        mainRoleTreeResponse.setTreeNodes(treeNodes);
        DruidDataSource druidDataSource = DBUtil.DRUID_DATA_SOURCE;
        try (DruidPooledConnection connection = druidDataSource.getConnection();) {
            boolean fl = connection.getAutoCommit();
            connection.setAutoCommit(false);

            // 保存wd_sys_user_org
            this.sysUserOrgService.createEntitysByRoleId(
                connection,
                roleAuthorize.getDepartmentIds(),
                userAuthorize.getUserIds(),
                roleDetailedInfo.getRoleId()
            );

            // 修改角色
            SysUser user = UserUtils.getUser();
            Date createTime = new Date();
            String strTime = "update_time=?";
            if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE)
                    || DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8)) {
                strTime = "update_time = to_date(?,'yyyy-mm-dd hh24:mi:ss')";
            }
            String updateSql = "update wd_sys_role set role_cde=?, role_name=?, " + strTime + "," +
                               " update_user=?, state=?, c_remark=?, is_admin=? where ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setString(1, roleDetailedInfo.getRoleCde());
                preparedStatement.setString(2, roleDetailedInfo.getRoleName());
                preparedStatement.setString(3, DateUtils.date2Str(createTime, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
                preparedStatement.setString(4, user.getId());
                preparedStatement.setString(5, roleDetailedInfo.getState());
                preparedStatement.setString(6, roleDetailedInfo.getRemark());
                preparedStatement.setString(7, roleDetailedInfo.getIsAdmin());
                preparedStatement.setString(8, roleDetailedInfo.getRoleId());
                preparedStatement.executeUpdate();
            } catch (Exception pe) {
                throw pe;
            }
            saveOrgRole(connection, roleDetailedInfo, roleAuthorize, user);
            saveRoleMenu(connection, roleDetailedInfo, roleAuthorize, user, createTime);
            saveMenuResource(connection, roleDetailedInfo, user, createTime);
            saveUserRole(connection, roleDetailedInfo, user, createTime);
            saveRolePower(connection, roleDetailedInfo);

            connection.commit();
            connection.setAutoCommit(fl);
        } catch (Exception ce) {
            log.error(ce);
            throw ce;
        }
        return mainRoleTreeResponse;
    }

    private void saveRolePower(DruidPooledConnection connection, RoleDetailedInfo roleDetailedInfo) throws Exception {

        RoleAuthorize roleAuthorize = roleDetailedInfo.getRoleAuthorize();
        DynamicDBUtil.executeSQL(
            connection,
            "delete from wd_sys_rolepower where role_id = ?",
            Collections.singletonList(roleDetailedInfo.getRoleId())
        );

        // 修改菜单功能权限  先删除 再插入
        List<String> funcIds = roleAuthorize.getFuncIds();

        // 保存按钮
        List<String> buttonIds = roleAuthorize.getButtonIds();
        if (funcIds.isEmpty() ||
            buttonIds.isEmpty()) {
            return;
        }
        //String insertSql = "INSERT INTO wd_sys_rolepower (id, role_id, power_type, power_id)" +
        //                       " VALUES(?, ?, ?, ?);";
        String className = SysRolePower.class.getName();
        String insertSql = EntitySqlUtils.insertSql(className);
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            Map<String, SysTableInfo> entityMap = EntitySqlUtils.getEntityColum().get(className);

            // 保存菜单
            for (String funcId : funcIds) {
                EntitySqlUtils.parameterString(entityMap, ps, "id", UUIDGenerator.generate());
                EntitySqlUtils.parameterString(entityMap, ps, "roleId", roleDetailedInfo.getRoleId());
                EntitySqlUtils.parameterString(entityMap, ps, "powerType", SysRolePower.TYPE_FUN);
                EntitySqlUtils.parameterString(entityMap, ps, "powerId", funcId);
                ps.addBatch();
            }

            // 保存按钮
            for (String buttonId : buttonIds) {
                EntitySqlUtils.parameterString(entityMap, ps, "id", UUIDGenerator.generate());
                EntitySqlUtils.parameterString(entityMap, ps, "roleId", roleDetailedInfo.getRoleId());
                EntitySqlUtils.parameterString(entityMap, ps, "powerType", SysRolePower.TYPE_OPER);
                EntitySqlUtils.parameterString(entityMap, ps, "powerId", buttonId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception pe) {
            throw pe;
        }
    }

    private void saveUserRole(DruidPooledConnection connection, RoleDetailedInfo roleDetailedInfo,
            SysUser user, Date createTime) throws Exception {

        UserAuthorize userAuthorize = roleDetailedInfo.getUserAuthorize();
        if (AdministratorUtils.getAdministratorName().equals(user.getUserCde())) {

            // 超管直接删除用户角色关联表
            DynamicDBUtil.executeSQL(
                connection,
                "delete from wd_sys_userrole where role_id = ?",
                Collections.singletonList(roleDetailedInfo.getRoleId())
            );
        } else {
            deleteUserRole(connection, roleDetailedInfo.getRoleId());
        }
        List<String> userIds = userAuthorize.getUserIds();
        if (userIds.isEmpty()) {
            return;
        }
        //String insertSql = "INSERT INTO wd_sys_userrole (id, role_id, user_id, create_time)" +
        //                       " VALUES(?, ?, ?, ?);";
        String className = UserRole.class.getName();
        String insertSql = EntitySqlUtils.insertSql(className);
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleDetailedInfo.getRoleId());
            userRole.setCreateTime(createTime);
            for (String userId : userIds) {
                userRole.setId(UUIDGenerator.generate());
                userRole.setUserId(userId);
                EntitySqlUtils.addBatch(userRole, className, ps);
            }
            ps.executeBatch();
        } catch (Exception pe) {
            throw pe;
        }
    }

    private void saveMenuResource(DruidPooledConnection connection, RoleDetailedInfo roleDetailedInfo,
            SysUser user, Date createTime) throws Exception {

        RoleAuthorize roleAuthorize = roleDetailedInfo.getRoleAuthorize();
        DynamicDBUtil.executeSQL(
            connection,
            "delete from wd_sys_role_menu_resource where role_id = ?",
            Collections.singletonList(roleDetailedInfo.getRoleId())
        );

    }

    private void saveRoleMenu(DruidPooledConnection connection, RoleDetailedInfo roleDetailedInfo,
             RoleAuthorize roleAuthorize, SysUser user, Date createTime) throws Exception {

        DynamicDBUtil.executeSQL(
            connection,
            "delete from wd_sys_role_menu where role_id = ?",
            Collections.singletonList(roleDetailedInfo.getRoleId())
        );

        // 修改 角色目录权限表(分析权限)
        List<SysRoleMenu> menuIds = roleAuthorize.getMenuIds();
        if (menuIds.isEmpty()) {
            return;
        }
        //String insertSql = "INSERT INTO wd_sys_role_menu (id, role_id, relation_id," +
        //                   " c_type, create_time, create_user) VALUES(?, ?, ?, ?, ?, ?);";
        String className = SysRoleMenu.class.getName();
        String insertSql = EntitySqlUtils.insertSql(className);
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            Map<String, SysTableInfo> entityMap = EntitySqlUtils.getEntityColum().get(className);
            for (SysRoleMenu sysRoleMenu : menuIds) {
                EntitySqlUtils.parameterString(entityMap, ps, "id", UUIDGenerator.generate());
                EntitySqlUtils.parameterString(entityMap, ps, "roleId", roleDetailedInfo.getRoleId());
                EntitySqlUtils.parameterString(entityMap, ps, "relationId", sysRoleMenu.getRelationId());
                EntitySqlUtils.parameterString(entityMap, ps, "type", "0");
                EntitySqlUtils.parameterString(entityMap, ps, "createTime", DateUtils.date2Str(createTime, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
                EntitySqlUtils.parameterString(entityMap, ps, "createUser", UUIDGenerator.generate());
                EntitySqlUtils.parameterString(entityMap, ps, "wdModelId", "");
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception pe) {
            throw pe;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void saveOrgRole(DruidPooledConnection connection, RoleDetailedInfo roleDetailedInfo,
            RoleAuthorize roleAuthorize, SysUser user) throws Exception {

        List<String> orgIds = roleAuthorize.getOrgIds();
        List<String> departmentIds = roleAuthorize.getDepartmentIds();
        if (null == orgIds) {
            orgIds = Collections.emptyList();
        }
        if (null == departmentIds) {
            departmentIds = Collections.emptyList();
        }
        int initialCapacity = orgIds.size() + departmentIds.size();

        List<String> ids = new ArrayList<>(initialCapacity);
        ids.addAll(orgIds);
        ids.addAll(departmentIds);
        if (AdministratorUtils.getAdministratorName().equals(user.getUserCde())) {
            String deleteSql = "delete from wd_sys_orgrole where role_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, roleDetailedInfo.getRoleId());
                preparedStatement.executeUpdate();
            } catch (Exception pe) {
                throw pe;
            }
        } else {
            String deleteSql = "delete from wd_sys_orgrole where role_id = ? and org_id in (" +
                               " select temp.org_id from ( select r.org_id from wd_sys_orgrole r where" +
                               " r.role_id in( select u.role_id from wd_sys_userrole u," +
                               " wd_sys_role srpower where u.role_id = srpower.id and srpower.state = '1'" +
                               " and u.user_id = ?)) temp )";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, roleDetailedInfo.getRoleId());
                preparedStatement.setString(2, user.getId());
                preparedStatement.executeUpdate();
            } catch (Exception pe) {
                throw pe;
            }
        }
        if (initialCapacity == 0) {
            return;
        }
        //String insertSql = " insert into wd_sys_orgrole (ID, role_id, org_id, create_time) values (?, ?, ?, ?) ";
        String className = SysOrgRole.class.getName();
        String insertSql = EntitySqlUtils.insertSql(className);
        Date createTime = new Date();
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            Map<String, SysTableInfo> entityMap = EntitySqlUtils.getEntityColum().get(className);
            for (String orgId : ids) {
                EntitySqlUtils.parameterString(entityMap, ps, "id", UUIDGenerator.generate());
                EntitySqlUtils.parameterString(entityMap, ps, "orgId", orgId);
                EntitySqlUtils.parameterString(entityMap, ps, "roleId", roleDetailedInfo.getRoleId());
                EntitySqlUtils.parameterString(
                    entityMap,
                    ps,
                    "createTime",
                    DateUtils.date2Str(createTime, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS)
                );
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception pe) {
            throw pe;
        }
    }

    private void deleteUserRole(DruidPooledConnection connection, String roleId) throws Exception {

        // 不想考虑 in 的限制,直接查出来就删除,如果慢的话,再优化
        String deleteSql =
            " delete from wd_sys_userrole where role_id = ? and user_id in ( select tmp.del from ( select s.id as del"
                + " from wd_sys_user s where s.org_id in ( select o.id from wd_sys_org o where o.id in ( select r"
                + ".org_id from wd_sys_orgrole r where r.role_id in ( select u.role_id from wd_sys_userrole u, "
                + "wd_sys_role srpower where u.role_id = srpower.id and srpower.state = '1' and u.user_id = ? ))) ) "
                + "tmp )";
        try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
            ps.setString(1, roleId);
            ps.setString(2, UserUtils.getUser().getId());
            ps.executeUpdate();
        } catch (Exception pe) {
            throw pe;
        }

    }


    /**
     * 改变状态
     *
     * @param role
     * @throws Exception
     */
    @Override
    public void changeState(SysRole role) throws Exception {
        roleDao.updateStatus(role.getId(), role.getState(), role.getPresentDate(), role.getgetPresentUser());
    }
    
    /**
     * 查询所有角色(子节点形式的数据)
     * @return
     * @throws Exception
     */
    @Override
    public MainRoleTreeParent queryAllRoleList() throws Exception {
    
        MainRoleTreeParent mainRoleTreeParent = new MainRoleTreeParent();
        List<MainRoleTree> mainRoleTreeList = new ArrayList<MainRoleTree>();
        List<SysRole> sysRoleList = new ArrayList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            sysRoleList = roleDao.queryAllOrderByCreateTimeAsc();
        } else {

            // sqlserver in 数量限制
            List<String> userIdPermissionList = UserUtils.getUserIdPermissionList();
            int maxIn = 1000;
            if (userIdPermissionList.size() > maxIn) {
                int num = userIdPermissionList.size() / maxIn;
                int end = 0;
                for (int i = 0; i < num; i++) {
                    end = maxIn * i + maxIn;
                    sysRoleList.addAll(
                        roleDao.queryAllOrderByCreateTimeAsc(
                            userIdPermissionList.subList(maxIn * i, end)
                        )
                    );
                }
                sysRoleList.addAll(
                    roleDao.queryAllOrderByCreateTimeAsc(
                        userIdPermissionList.subList(end, userIdPermissionList.size())
                    )
                );
            } else {
                sysRoleList = roleDao.queryAllOrderByCreateTimeAsc(userIdPermissionList);
            }
        }
        for (SysRole sysRole : sysRoleList) {
            if (sysRole.getRoleCde().equals(AdministratorUtils.getAdminRoleCde())) {
                continue;
            }
            MainRoleTree mainRoleTree = new MainRoleTree();
            mainRoleTree.setRoleId(sysRole.getId());
            mainRoleTree.setRoleCde(sysRole.getRoleCde());
            mainRoleTree.setId(sysRole.getId());
            mainRoleTree.setName(sysRole.getRoleName());
            mainRoleTree.setType(TreeNodeType.ROLE);
            mainRoleTreeList.add(mainRoleTree);
        }
        mainRoleTreeParent.setEdit(true);
        mainRoleTreeParent.setTreeNodes(mainRoleTreeList);
        return mainRoleTreeParent;
    }
    
    /**
     * 分页查询可用角色
     *
     * @param pageJson
     * @throws Exception
     */
    @Override
    public void queryAvailable(PageJson pageJson) throws Exception {
        Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
        Specification specification = new Specification<SysRole>() {
            /**
             * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
             * {@link Root} and {@link CriteriaQuery}.
             *
             * @param root
             * @param query
             * @param cb
             * @return a {@link Predicate}, must not be {@literal null}.
             */
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("state").as(String.class), SysRole.STATUS_NORMAL);
            }
        };
        Page<SysRole> page = roleDao.findAll(specification, pageable);
        pageJson.setResult(page.getContent());
        pageJson.setTotalPagets(page.getTotalPages());
    }

    /**
     * 保存菜单和按钮
     * @param functionOneToMany
     * @throws Exception
     */
    @Override
    public void saveFunctionRolePower(FunctionOneToMany functionOneToMany) throws Exception {
        
        //删除
        sysRolePowerDao.deleteSysRolePowerByRoleId(functionOneToMany.getRoleId());
        
        //增加菜单
        for (String funcId : functionOneToMany.getFuncIds()) {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setRoleId(functionOneToMany.getRoleId());
            sysRolePower.setPowerType(SysRolePower.TYPE_FUN);
            sysRolePower.setPowerId(funcId);
            sysRolePowerDao.save(sysRolePower);
        }
        
        //增加按钮
        for (String funcId : functionOneToMany.getPowerIds()) {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setRoleId(functionOneToMany.getRoleId());
            sysRolePower.setPowerType(SysRolePower.TYPE_OPER);
            sysRolePower.setPowerId(funcId);
            sysRolePowerDao.save(sysRolePower);
        }
        
    }
    
    /**
     * 根据用户id 查询角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysRole> findListByUserId(String userId) throws Exception {
        return roleDao.getSysRolesByUserId(userId);
    }
    
    /**
     * 角色详细信息
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public RoleDetailedInfo queryRoleDetailedInfo(String roleId) throws Exception {
        
        RoleDetailedInfo roleDetailedInfo = new RoleDetailedInfo();
        RoleAuthorize roleAuthorize = new RoleAuthorize();
        UserAuthorize userAuthorize = new UserAuthorize();
        List<String> funcIds = new ArrayList<>();
        List<String> buttonIds = new ArrayList<>();
//        List<AuthorizeParentId> formIds = new ArrayList<AuthorizeParentId>();
//        List<SysRoleFormItem> sysRoleFormItems = new ArrayList<SysRoleFormItem>();
        List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
        List<String> orgIds = new ArrayList<String>();
        List<String> departmentIds = new ArrayList<String>();
        List<String> userIds = new ArrayList<String>();


        // 已经拥有的菜单和按钮权限
        List<SysRolePower> alreadyExistFuncOrOperList = sysRolePowerDao.getSysRolePowerByRoleId(roleId);
        for (SysRolePower sysRolePower: alreadyExistFuncOrOperList) {
            if (sysRolePower.getPowerType().equals(SysRolePower.TYPE_FUN)) {
                funcIds.add(sysRolePower.getPowerId());
            } else {
                buttonIds.add(sysRolePower.getPowerId());
            }
        }

        SysUser user = UserUtils.getUser();

        // 文件夹 已经拥有的数据权限
        List<SysOrg> alreadyExistOrgs = null;
        if (AdministratorUtils.getAdministratorName().equals(user.getUserCde())) {
            alreadyExistOrgs = orgDao.findOrgIdsByRoleId(roleId);
        } else {
            alreadyExistOrgs = orgDao.findOrgIdsByRoleId(roleId, user.getId());
        }
        if (null != alreadyExistOrgs) {
            for (SysOrg alreadyExistOrg : alreadyExistOrgs) {
                if (SysOrg.TYPE_DEPARTMENT.equals(alreadyExistOrg.getType())) {
                    departmentIds.add(alreadyExistOrg.getId());
                } else {
                    orgIds.add(alreadyExistOrg.getId());
                }
            }
        }

        // 分析权限
        List<SysRoleMenu> roleMenus = sysRoleMenuDao.queryByRoleId(roleId);
        sysRoleMenus.addAll(roleMenus);

        // 已经拥有的归入用户
        List<String> alreadyExistUserRoleList = null;
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            alreadyExistUserRoleList = userRoleDao.getUserRoleByRoleId(roleId);
        } else {
            alreadyExistUserRoleList = userRoleDao.getUserRoleByRoleId(roleId, user.getId());
        }
        if (null != alreadyExistUserRoleList) {
            userIds.addAll(alreadyExistUserRoleList);
        }


        // 角色详细
        SysRole role = roleDao.findById(roleId).orElse(null);
        if (null == role) {
            throw new Exception(MessageUtils.getMessage("system.role.notfound"));
        }
        roleDetailedInfo.setRoleId(role.getId());
        roleDetailedInfo.setRoleCde(role.getRoleCde());
        roleDetailedInfo.setRoleName(role.getRoleName());
        roleDetailedInfo.setState(role.getState());
        roleDetailedInfo.setRemark(role.getRemark());
        roleDetailedInfo.setIsAdmin(role.getIsAdmin());
        roleAuthorize.setFuncIds(funcIds);
        roleAuthorize.setButtonIds(buttonIds);
        roleAuthorize.setOrgIds(orgIds);
        roleAuthorize.setDepartmentIds(departmentIds);
        userAuthorize.setUserIds(userIds);
        roleDetailedInfo.setRoleAuthorize(roleAuthorize);
        roleDetailedInfo.setUserAuthorize(userAuthorize);
        return roleDetailedInfo;
    }
    
    /**
     * 用户配置角色的角色列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public MainRoleTreeParent queryConfigList() throws Exception {
    
        MainRoleTreeParent mainRoleTreeParent = new MainRoleTreeParent();
        List<MainRoleTree> mainRoleTreeList = new ArrayList<MainRoleTree>();
        List<SysRole> sysRoleList = roleDao.queryAllByStateAndRoleCdeNotOrderByCreateTimeAsc(
            SysRole.STATUS_NORMAL,
            AdministratorUtils.getAdminRoleCde()
        );
        for (SysRole sysRole : sysRoleList) {
            MainRoleTree mainRoleTree = new MainRoleTree();
            mainRoleTree.setRoleId(sysRole.getId());
            mainRoleTree.setId(sysRole.getId());
            mainRoleTree.setName(sysRole.getRoleName());
            mainRoleTree.setType(TreeNodeType.ROLE);
            mainRoleTree.setRemark(sysRole.getRemark());
            mainRoleTreeList.add(mainRoleTree);
        }
        mainRoleTreeParent.setCheck(true);
        mainRoleTreeParent.setTreeNodes(mainRoleTreeList);
        return mainRoleTreeParent;
    }
    
    /**
     * 根据角色id查询实体
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public SysRole queryById(String roleId) throws Exception {
        return roleDao.findById(roleId).orElse(null);
    }

}
