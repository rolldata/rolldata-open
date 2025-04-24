package com.rolldata.web.system.service.impl;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.dao.*;
import com.rolldata.web.system.entity.SysOrg;
import com.rolldata.web.system.entity.SysOrgRole;
import com.rolldata.web.system.entity.SysRole;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.OrgService;
import com.rolldata.web.system.service.RoleService;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Title: OrgServiceImpl
 * @Description: 组织机构实现
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
@Service("orgService")
@Transactional
public class OrgServiceImpl implements OrgService {

    private static final Logger log = LogManager.getLogger(OrgServiceImpl.class);
    
    @Autowired
    private OrgDao orgDao;

    @Autowired
    private SysOrgRoleDao sysOrgRoleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SysPostUserDao sysPostUserDao;

    @Autowired
    private SysUserOrgService sysUserOrgService;

    /**
     * 保存
     * @param sysOrgJson
     * @throws Exception
     */
    @Override
    public ResponseOrgJson save(RequestOrgJson sysOrgJson) throws Exception {

        SysOrg sysOrg = new SysOrg();
        sysOrg.setOrgCde(sysOrgJson.getOrgCde());
        sysOrg.setOrgName(sysOrgJson.getOrgName());
        sysOrg.setAutomaticSummary(sysOrgJson.getAutomaticSummary());
        if (StringUtil.isNotEmpty(sysOrgJson.getpId())) {
            sysOrg.setParentId(sysOrgJson.getpId());
        } else {
            sysOrg.setParentId(SysOrg.TYPE_ROOT);
        }
        sysOrg.setCreateTime(new Date());
        sysOrg.setCreateUser(UserUtils.getUser().getId());
        sysOrg.setUpdateTime(new Date());
        sysOrg.setUpdateUser(UserUtils.getUser().getId());
        sysOrg.setType(sysOrgJson.getOrgType());
        sysOrg.setId(UUIDGenerator.generate());
        if(StringUtil.isNotEmpty(sysOrgJson.getWdModelId())){
            sysOrg.setWdModelId(sysOrgJson.getWdModelId());
        }
        sysOrg = orgDao.save(sysOrg);

        //获取父节点组织的角色
        /*List<String> roleIdList = sysOrgRoleDao.getRoleIdByOrgId(sysOrg.getParentId());
         */
        List<String> roleIdList = roleService.getRoleList();
        for (String roleId : roleIdList) {
            SysOrgRole sysOrgRole = new SysOrgRole();
            sysOrgRole.setOrgId(sysOrg.getId());
            sysOrgRole.setRoleId(roleId);
            sysOrgRole.setCreateTime(new Date());
            sysOrgRoleDao.save(sysOrgRole);
        }
        return changeResponseUserJson(sysOrg);
    }

    /**
     * 修改
     *
     * @param sysOrgJson
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
                              readOnly = false, rollbackFor = Exception.class)
    public ResponseOrgJson update(RequestOrgJson sysOrgJson) throws Exception {

        Objects.requireNonNull(sysOrgJson.getOrgId(), "页面参数异常");
        StringUtil.requireNonNull(sysOrgJson.getpId(), "参数异常:父ID不能为空");
        orgDao.updateOrg(
            sysOrgJson.getOrgName(),
            sysOrgJson.getOrgCde(),
            sysOrgJson.getpId(),
            UserUtils.getUser().getId(),
            new Date(),
            sysOrgJson.getOrgId()
        );
        SysOrg sysOrg = orgDao.findById(sysOrgJson.getOrgId()).orElse(null);
        if (null == sysOrg) {
            throw new Exception(sysOrgJson.getOrgName() + "-不存在");
        }
        return changeResponseUserJson(sysOrg);
    }
    
    /**
     * 删除部门，包括部门下的人
     * @param orgId
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteOrg (String orgId) throws Exception {

        orgDao.deleteSysOrgById(orgId);
        sysOrgRoleDao.deleteSysOrgRoleByOrgId(orgId);

        // 只有部门下才有用户,查找部门字段.
        List<String> userIds = userDao.queryUserIdByDepartmentId(orgId);
        deleteUserIds(userIds);
    }

    /**
     * 批量删除
     * <p>注意此方法修改,这里也要相应修改</p>
     *
     * @param userIds 用户id集合
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void deleteUserIds(List<String> userIds) throws Exception {

        if (null == userIds ||
            userIds.isEmpty()) {
            return;
        }
        SysUser adminUser = this.userDao.getUserByUserCde(AdministratorUtils.getAdministratorName());
        if (null == adminUser) {
            throw new Exception("超级管理员不存在");
        }

        // 移除admin
        userIds.removeIf(u -> u.equals(adminUser.getId()));

        // 未来可以限制部门下用户最多1000
        this.userDao.deleteUserByIds(userIds);
        this.userRoleDao.deleteUserRoleByUserIds(userIds);
        this.sysPostUserDao.deleteByUserIds(userIds);

        // 操作wd_sys_user_org
        this.sysUserOrgService.deleteEntitysByUserIds(userIds);
    }
    
    /**
     * 通过Id获取组织详情
     * @param id
     * @throws Exception
     */
    @Override
    public OrgDetailedJson getOrgDetailed(String id) throws Exception{
        SysOrg sysOrg = orgDao.findById(id).orElse(null);
        OrgDetailedJson orgDetailedJson = new OrgDetailedJson();
        if (SysOrg.TYPE_ROOT.equals(sysOrg.getType())) {
            orgDetailedJson.setOrgType(SysOrg.TYPE_ROOT_ZH_CN);
        } else if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
            orgDetailedJson.setOrgType(SysOrg.TYPE_COMPANY_ZH_CN);
        } else if (SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
            orgDetailedJson.setOrgType(SysOrg.TYPE_DEPARTMENT_ZH_CN);
        }
        orgDetailedJson.setOrgId(id);
        orgDetailedJson.setOrgName(sysOrg.getOrgName());
        orgDetailedJson.setAutomaticSummary(sysOrg.getAutomaticSummary());
        return orgDetailedJson;
    }

    /**
     * 根据id查询对象
     * @param orgId 组织id
     * @return
     * @throws Exception
     */
    @Override
    public SysOrg getSysOrgById(String orgId) throws Exception {

        return orgDao.findById(orgId).orElse(null);
    }
    
    /**
     * 组织管理树(子节点形式的数据)
     * @throws Exception
     */
    @Override
    public List<RoleOrgList> orgTreeList() throws Exception {

        List<RoleOrgList> roleOrgLists = new ArrayList<RoleOrgList>();
        List<SysOrg> allSysOrg = null;
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            allSysOrg = orgDao.queryAllOrgList();
        } else {
            allSysOrg = orgDao.getSysOrgByUserId(UserUtils.getUser().getId());
        }
        searchRoleOrgParent(allSysOrg, roleOrgLists);
        return roleOrgLists;
    }

    /**
     * 组织权限树
     *
     * @param dataOwn 数据归属
     * @return
     * @throws Exception
     */
    @Override
    public OrgTreeParent orgLimitTreeList(String dataOwn) throws Exception {

        OrgTreeParent orgTreeParent = new OrgTreeParent();
        List<OrgTree> treeList = new ArrayList<OrgTree>();
        List<SysOrg> sysOrgList = UserUtils.getOrgPermission();

        // 数据归属是1的时候只要公司  1组织，2部门，3个人
        searchOrgParent(sysOrgList, treeList, dataOwn);
        orgTreeParent.setTreeNodes(treeList);
        return orgTreeParent;
    }

    /**
     * 根据父ID查询记录集合
     * @param orgId
     * @throws Exception
     */
    @Override
    public List<SysOrg> findByParentId (String orgId) throws Exception {

        List<SysOrg> list = new ArrayList<SysOrg>();
        list = orgDao.findByParentId(orgId);
        return list;
    }

    /**
     * 级联删除
     *
     * @param orgId
     * @param delIds
     * @throws Exception
     * @createDate 2018-6-4
     */
    @Override
    public void deleteRecursiveMethod(String orgId, List<String> delIds, String type) throws Exception{

        // 禁止删除组织的判断
        SysUser adminUser = this.userDao.getUserByUserCde(AdministratorUtils.getAdministratorName());
        if (null == adminUser ||
            StringUtil.isEmpty(orgId)) {
            return;
        }
        if (orgId.equals(adminUser.getOrgId())) {
            throw new Exception("内置组织,禁止删除");
        }
        if (orgId.equals(adminUser.getDepartment())) {
            throw new Exception("内置部门,禁止删除");
        }
        delIds.add(orgId);
        List<SysOrg> list = findByParentId(orgId);
        if(list.size() > 0){
            for (SysOrg sysOrg2 : list) {
                if (SysOrg.TYPE_COMPANY.equals(type)) {
                    delIds.add(sysOrg2.getId());
                }
            	deleteRecursiveMethod(sysOrg2.getId(), delIds, type);
            }
        }
        deleteOrg(orgId);
    }

    /**
     * 拼接返回对象ResponseOrgJson
     *
     * @throws Exception
     */
    @Override
    public ResponseOrgJson changeResponseUserJson(SysOrg sysOrg) throws Exception {

        ResponseOrgJson rOrgJson = new ResponseOrgJson();
        List<OrgTree> list = new ArrayList<OrgTree>();

        //树
        OrgTree orgTree = new OrgTree();
        orgTree.setId(sysOrg.getId());
        orgTree.setpId(sysOrg.getParentId());
        orgTree.setName(sysOrg.getOrgName());
        orgTree.setOrgId(sysOrg.getId());
        orgTree.setIcon(sysOrg.getIcon());
        orgTree.setIconSkin(sysOrg.getIconSkin());
        orgTree.setDrag(true);
        orgTree.setCopy(Boolean.FALSE);
        orgTree.setNocheck(false);
        orgTree.setChecked(true);

        //基本信息
        OrgDetailedJson orgDetailedJson = new OrgDetailedJson();
        orgDetailedJson.setOrgId(sysOrg.getId());
        orgDetailedJson.setOrgName(sysOrg.getOrgName());

        if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
            orgTree.setDrop(true);
            orgTree.setType(TreeNodeType.COMPANY);
            orgTree.setValid_children(new ArrayList<String>() {

                private static final long serialVersionUID = 6930481117953480008L;

                {
                    this.add(TreeNodeType.COMPANY.toString());
                }
            });
            orgDetailedJson.setOrgType(SysOrg.TYPE_COMPANY_ZH_CN);
        } else if (SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
            orgTree.setDrop(true);
            orgTree.setType(TreeNodeType.DEPARTMENT);
            orgTree.setValid_children(new ArrayList<String>());
            orgDetailedJson.setOrgType(SysOrg.TYPE_DEPARTMENT_ZH_CN);
        }
        list.add(orgTree);
        rOrgJson.setTreeNodes(list);
        rOrgJson.setInfo(orgDetailedJson);
        return rOrgJson;
    }

    /**
     * 组织名称是否重复数量
     *
     * @param orgName 组织名称
     * @return
     * @throws Exception
     */
    @Override
    public int checkName(String orgName) throws Exception {

        return orgDao.checkName(orgName, SysOrg.TYPE_COMPANY);
    }

    /**
     * 修改时组织编码是否重复数量
     *
     * @param orgId  组织id
     * @param orgName 组织编码
     * @return
     * @throws Exception
     */
    @Override
    public int checkName(String orgId, String orgName) throws Exception {

        return orgDao.checkName(orgId, orgName, SysOrg.TYPE_COMPANY);
    }

    /**
     * 组织编码是否重复数量
     *
     * @param checkCode 组织编码
     * @return
     * @throws Exception
     */
    @Override
    public int checkCode(String checkCode) throws Exception {

        return orgDao.checkCode(checkCode, SysOrg.TYPE_COMPANY);
    }

    /**
     * 修改时组织编码是否重复数量
     *
     * @param orgId   组织id
     * @param checkCode 组织编码
     * @return
     * @throws Exception
     */
    @Override
    public int checkCode(String orgId, String checkCode) throws Exception {

        return orgDao.checkCode(orgId, checkCode, SysOrg.TYPE_COMPANY);
    }

    /**
     * 查询组织实体根据组织代码
     *
     * @param code 组织代码
     * @return
     * @throws Exception
     */
    @Override
    public SysOrg queryOrgByCode (String code) throws Exception {

        return orgDao.querySysOrgByOrgCde(code);
    }

    /**
     * 查询用户相应的数据权限
     * <br>权限：角色的数据权限加用户本身职务id
     *
     * @param userCode 用户code
     * @return
     * @throws Exception
     */
    @Override
    public List<String> queryDataRights(String userCode) throws Exception {

        List<String> orgIdList = new ArrayList();
        if (AdministratorUtils.getAdministratorName().equals(userCode)) {
            orgIdList = orgDao.queryAllOrgIdList();
        } else {

            // 效率有点低,待优化
            SysUser user = userDao.getUserByUserCde(userCode);
            List<SysOrg> sysOrgList = orgDao.getSysOrgByUserId(user.getId());
            SysOrg sysOrg = orgDao.findById(UserUtils.getUser().getDepartment()).orElse(null);
            sysOrgList.add(sysOrg);
            Set<SysOrg> orgs = Sets.newConcurrentHashSet(sysOrgList);
            return new ArrayList(Sets.newHashSet(Collections2.transform(orgs, SysOrg::getId)));
        }
        return orgIdList;
    }

    /**
     * 查询当前登录用户数据权限
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SysOrg> querySysOrgList() throws Exception {

        List<SysOrg> sysOrgList = new ArrayList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            sysOrgList = orgDao.queryAllOrgList();
        } else {
            List<String> orgIds = new ArrayList<>();

            // 角色可管理的组织
            sysOrgList = orgDao.getSysOrgByUserId(UserUtils.getUser().getId());
            sysOrgList.forEach(org -> {
                orgIds.add(org.getId());
            });

            // 查全部组织,通过递归找他可管理的下级组织(包括本级)
            List<SysOrg> sysOrgCompanyList = orgDao.queryAllOrgList();
            findDepartmentOrg(UserUtils.getUser().getDepartment(), sysOrgCompanyList, sysOrgList, orgIds);
            SysOrg orgDepartment = orgDao.findById(UserUtils.getUser().getDepartment()).orElse(null);
            if (null != orgDepartment && !orgIds.contains(orgDepartment.getId())) {
                sysOrgList.add(orgDepartment);
            }
        }
        return sysOrgList;
    }

    /**
     * 查询组织权限公司树
     *
     * @return
     * @throws Exception
     */
    @Override
    public OrgTreeParent queryRoleCompanyTree() throws Exception {

        OrgTreeParent orgTreeParent = new OrgTreeParent();
        List<OrgTree> treeList = new ArrayList<OrgTree>();
        List<SysOrg> sysOrgList = UserUtils.getOrgPermission();
        searchOrgCompany(sysOrgList, treeList, null);
        orgTreeParent.setTreeNodes(treeList);
        return orgTreeParent;
    }

    /**
     * 查询角色权限公司下的部门
     *
     * @param parentId 公司id
     * @return
     * @throws Exception
     */
    @Override
    public List<OrgTree> queryRoleDepartments(String parentId) throws Exception {

        List<OrgTree> treeList = new ArrayList<OrgTree>();
        if (StringUtil.isEmpty(parentId)) {
            return treeList;
        }
        List<SysOrg> sysOrgList = UserUtils.getOrgPermission();
        searchOrgCompany(sysOrgList, treeList, parentId);
        return treeList;
    }

    /**
     * 查询全部组织
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SysOrg> queryAllSysOrg() throws Exception {
        return orgDao.queryAllOrgList();
    }

    private void findDepartmentOrg(String orgId, List<SysOrg> sysOrgCompanyList, List<SysOrg> sysOrgList,
        List<String> orgIds) {

        sysOrgCompanyList.forEach((sysOrg) -> {
            if (sysOrg.getParentId().equals(orgId) && !orgIds.contains(sysOrg.getId())) {
                sysOrgList.add(sysOrg);
                findDepartmentOrg(sysOrg.getId(), sysOrgCompanyList, sysOrgList, orgIds);
            }
        });
    }

    /**
     * 树的一级节点
     *
     * @param orgs 全部节点集合
     * @param treeNodes 结果集
     */
    private void searchRoleOrgParent (List<SysOrg> orgs, List<RoleOrgList> treeNodes) {

        //记录删除标记
        List<String> fl = new ArrayList<>();

        //形成个树形
        for (int i = 0; i < orgs.size(); i++) {
            SysOrg sysOrg = orgs.get(i);
            RoleOrgList roleOrgList = new RoleOrgList();
            roleOrgList.setName(sysOrg.getOrgName());
            roleOrgList.setId(sysOrg.getId());
            roleOrgList.setType(sysOrg.getType());
            roleOrgList.setOrgId(sysOrg.getId());
            roleOrgList.setOrgCde(sysOrg.getOrgCde());
            searchOrgChild(roleOrgList, orgs, fl);
            treeNodes.add(roleOrgList);
        }

        //移除最终结果集里的id,此id已经是子节点
        treeNodes.removeIf(orgTree -> fl.contains(orgTree.getOrgId()));
    }

    /**
     * 在结果集中查找子节点
     * @param parentOrgTree 父节点
     * @param orgs 全部节点集合
     * @param fl 记录子节点id的标志
     */
    private void searchOrgChild (RoleOrgList parentOrgTree, List<SysOrg> orgs, List<String> fl) {

        List<RoleOrgList> treeNodes = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            if (parentOrgTree.getId().equals(orgs.get(i).getParentId())) {
                SysOrg sysOrg = orgs.get(i);
                RoleOrgList roleOrg = new RoleOrgList();
                roleOrg.setId(sysOrg.getId());
                roleOrg.setParentId(sysOrg.getParentId());
                roleOrg.setName(sysOrg.getOrgName());
                roleOrg.setType(sysOrg.getType());
                roleOrg.setIcon(sysOrg.getIcon());
                roleOrg.setOrgId(sysOrg.getId());
                roleOrg.setOrgCde(sysOrg.getOrgCde());
                treeNodes.add(roleOrg);
                searchOrgChild(roleOrg, orgs, fl);
                fl.add(orgs.get(i).getId());
            }
        }
        if (treeNodes.size() > 0) {
            parentOrgTree.setChildren(treeNodes);
        }
    }

    /**
     * 树的一级节点
     *
     * @param orgs      全部节点集合
     * @param treeNodes 结果集
     * @param dataOwn   数据归属  1组织，2部门，3个人
     */
    private void searchOrgParent (List<SysOrg> orgs, List<OrgTree> treeNodes, String dataOwn) {

        //记录删除标记
        List<String> fl = new ArrayList<>();

        //形成个树形
        for (int i = 0; i < orgs.size(); i++) {
            SysOrg sysOrg = orgs.get(i);
            OrgTree orgTree = new OrgTree();
            if (SysOrg.TYPE_ROOT.equals(sysOrg.getType())) {
                orgTree.setType(TreeNodeType.ROOT);
            } else if(SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                orgTree.setType(TreeNodeType.COMPANY);
            } else if(SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {

                // 数据归属是1的,只要公司
                if ("1".equals(dataOwn)) {
                    continue;
                }
                orgTree.setType(TreeNodeType.DEPARTMENT);
            }
            orgTree.setId(sysOrg.getId());
            orgTree.setpId(sysOrg.getParentId());
            orgTree.setName(sysOrg.getOrgName());
            orgTree.setIcon(sysOrg.getIcon());
            orgTree.setIconSkin(sysOrg.getIconSkin());
            orgTree.setOrgId(sysOrg.getId());
            searchOrgChild(orgTree, orgs, fl, dataOwn);
            treeNodes.add(orgTree);
        }

        //移除最终结果集里的id,此id已经是子节点
        treeNodes.removeIf(orgTree -> fl.contains(orgTree.getOrgId()));
    }

    /**
     * 在结果集中查找子节点
     *
     * @param parentOrgTree 父节点
     * @param orgs          全部节点集合
     * @param fl            记录子节点id的标志
     * @param dataOwn       数据归属  1组织，2部门，3个人
     */
    private void searchOrgChild (OrgTree parentOrgTree, List<SysOrg> orgs, List<String> fl, String dataOwn) {

        List<OrgTree> treeNodes = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            if (parentOrgTree.getId().equals(orgs.get(i).getParentId())) {
                SysOrg sysOrg = orgs.get(i);
                OrgTree orgTree = new OrgTree();
                if (SysOrg.TYPE_ROOT.equals(sysOrg.getType())) {
                    orgTree.setType(TreeNodeType.ROOT);
                } else if(SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                    orgTree.setType(TreeNodeType.COMPANY);
                } else if(SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {

                    // 数据归属是1的,只要公司
                    if ("1".equals(dataOwn)) {
                        continue;
                    }
                    orgTree.setType(TreeNodeType.DEPARTMENT);
                }
                orgTree.setId(sysOrg.getId());
                orgTree.setpId(sysOrg.getParentId());
                orgTree.setName(sysOrg.getOrgName());
                orgTree.setIcon(sysOrg.getIcon());
                orgTree.setIconSkin(sysOrg.getIconSkin());
                orgTree.setValid_children(null);
                orgTree.setOrgId(sysOrg.getId());
                treeNodes.add(orgTree);
                searchOrgChild(orgTree, orgs, fl, dataOwn);
                fl.add(orgs.get(i).getId());
            }
        }
        if (treeNodes.size() > 0) {
            parentOrgTree.setChildren(treeNodes);
        }
    }

    /**
     * 角色组织公司树,有parentId参数时查询公司下的部门
     *
     * @param orgs
     * @param treeNodes
     * @param parentId 公司id
     */
    private void searchOrgCompany (List<SysOrg> orgs, List<OrgTree> treeNodes, String parentId) {

        //记录删除标记
        List<String> fl = new ArrayList<>();

        //形成个树形
        for (int i = 0; i < orgs.size(); i++) {
            SysOrg sysOrg = orgs.get(i);
            OrgTree orgTree = new OrgTree();
            if (null == sysOrg) {
                log.info("组织集合有空的");
                continue;
            }

            // 部门
            if (StringUtil.isNotEmpty(parentId)) {
                if (parentId.equals(sysOrg.getParentId()) && SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
                    orgTree.setType(TreeNodeType.DEPARTMENT);
                } else {
                    continue;
                }
            } else {

                // 公司
                orgTree.setType(TreeNodeType.COMPANY);
                if(SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
                    continue;
                }
            }
            orgTree.setId(sysOrg.getId());
            orgTree.setpId(sysOrg.getParentId());
            orgTree.setName(sysOrg.getOrgName());
            orgTree.setIcon(sysOrg.getIcon());
            orgTree.setIconSkin(sysOrg.getIconSkin());
            orgTree.setOrgId(sysOrg.getId());
            searchOrgCompanyChild(orgTree, orgs, fl, parentId);
            treeNodes.add(orgTree);
        }

        //移除最终结果集里的id,此id已经是子节点
        treeNodes.removeIf(orgTree -> fl.contains(orgTree.getOrgId()));
    }

    /**
     * 角色组织子公司
     *
     * @param parentOrgTree
     * @param orgs
     * @param fl
     * @param parentId
     */
    private void searchOrgCompanyChild (OrgTree parentOrgTree, List<SysOrg> orgs, List<String> fl, String parentId) {

        List<OrgTree> treeNodes = new ArrayList<>();
        for (int i = 0; i < orgs.size(); i++) {
            SysOrg sysOrg = orgs.get(i);
            if (null == parentOrgTree.getId() ||
                null == sysOrg) {
                log.info("组织集合有空的");
                continue;
            }
            if (parentOrgTree.getId().equals(sysOrg.getParentId())) {
                OrgTree orgTree = new OrgTree();

                // 部门
                if (StringUtil.isNotEmpty(parentId)) {
                    if (SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
                        orgTree.setType(TreeNodeType.DEPARTMENT);
                    } else {
                        continue;
                    }
                } else {

                    // 公司
                    orgTree.setType(TreeNodeType.COMPANY);
                    if(SysOrg.TYPE_DEPARTMENT.equals(sysOrg.getType())) {
                        continue;
                    }
                }
                orgTree.setId(sysOrg.getId());
                orgTree.setpId(sysOrg.getParentId());
                orgTree.setName(sysOrg.getOrgName());
                orgTree.setIcon(sysOrg.getIcon());
                orgTree.setIconSkin(sysOrg.getIconSkin());
                orgTree.setValid_children(null);
                orgTree.setOrgId(sysOrg.getId());
                treeNodes.add(orgTree);
                searchOrgCompanyChild(orgTree, orgs, fl, parentId);
                fl.add(sysOrg.getId());
            }
        }
        if (treeNodes.size() > 0) {
            parentOrgTree.setChildren(treeNodes);
        }
    }

    /**
     * 根据类型查询组织集合
     * @param type
     * @return
     * @throws Exception
     */
	@Override
    public List<SysOrg> queryAllSysOrgByType(String type) throws Exception {
		return orgDao.queryAllSysOrgByType(type);
	}

	 /**
     * 新增组织（同步组织用）
     * @param sysOrgJson
     * @throws Exception
     */
    @Override
    public SysOrg saveOrg(RequestOrgJson sysOrgJson) throws Exception {

        SysOrg sysOrg = new SysOrg();
        sysOrg.setOrgCde(sysOrgJson.getOrgCde());
        sysOrg.setOrgName(sysOrgJson.getOrgName());
        sysOrg.setAutomaticSummary(sysOrgJson.getAutomaticSummary());
        if (StringUtil.isNotEmpty(sysOrgJson.getpId())) {
            sysOrg.setParentId(sysOrgJson.getpId());
        } else {
            sysOrg.setParentId(SysOrg.TYPE_ROOT);
        }
        sysOrg.setCreateTime(new Date());
        sysOrg.setCreateUser(sysOrgJson.getCreateUser());
        sysOrg.setUpdateTime(new Date());
        sysOrg.setUpdateUser(sysOrgJson.getCreateUser());
        sysOrg.setType(sysOrgJson.getOrgType());
        sysOrg.setId(UUIDGenerator.generate());
        sysOrg.setIsSync(sysOrgJson.getIsSync());
        sysOrg = orgDao.save(sysOrg);

        List<String> roleIds = new ArrayList<String>();
        SysRole existRole = roleDao.getSysRoleByRoleCde(AdministratorUtils.getAdminRoleCde());
        roleIds.add(existRole.getId());
        for (String roleId : roleIds) {
            SysOrgRole sysOrgRole = new SysOrgRole();
            sysOrgRole.setOrgId(sysOrg.getId());
            sysOrgRole.setRoleId(roleId);
            sysOrgRole.setCreateTime(new Date());
            sysOrgRoleDao.save(sysOrgRole);
        }
        return sysOrg;
    }
    
    /**
     * 根据组织编码查询组织详细信息
     * @param orgCode
     * @return
     * @throws Exception
     */
	@Override
    public SysOrg querySysOrgByOrgCode(String orgCode) throws Exception {
		return orgDao.querySysOrgByOrgCode(orgCode);
	}

	/**
     * 根据组织id修改组织的父级
     * @param sysOrgJson
     * @throws Exception
     */
	@Override
    public void updateOrgParentId(RequestOrgJson sysOrgJson) throws Exception {
		orgDao.updateOrgParentId(sysOrgJson.getId(),sysOrgJson.getpId(),sysOrgJson.getCreateUser(),new Date());
	}

    /**
     * 组织部门树(子节点形式的数据)（无数据权限）
     * @throws Exception
     */
    @Override
    public List<RoleOrgList> orgTreeListNoDataPermission() throws Exception {
        List<RoleOrgList> roleOrgLists = new ArrayList<RoleOrgList>();
        List<SysOrg> allSysOrg = orgDao.queryAllOrgList();
        searchRoleOrgParent(allSysOrg, roleOrgLists);
        return roleOrgLists;
    }

    /**
     * 查询公司树，所有公司类型，无数据权限
     *
     * @return
     * @throws Exception
     */
    @Override
    public OrgTreeParent queryCompanyTreeNoDataPermission() throws Exception {
        OrgTreeParent orgTreeParent = new OrgTreeParent();
        List<OrgTree> treeList = new ArrayList<OrgTree>();
        List<SysOrg> sysOrgList = orgDao.queryAllOrgList();;
        searchOrgCompany(sysOrgList, treeList, null);
        orgTreeParent.setTreeNodes(treeList);
        return orgTreeParent;
    }

    /**
     * 查询公司部门用户数树
     *
     * @return
     * @throws Exception
     */
    @Override
    public OrgTreeParent queryOrgUserTree() throws Exception {

        OrgTreeParent orgTreeParent = new OrgTreeParent();
        List<OrgTree> treeList = new ArrayList<>();
        List<SysOrg> sysOrgList = orgDao.queryAllOrgList();

        // 所有用户
        List<SysUser> users = userDao.queryUserList();

        // 把用户以部门为 key 区分开
        Map<String, List<OrgTree>> userDepMap = new HashMap<>();
        users.forEach(user -> {
            List<OrgTree> orgTrees = userDepMap.get(user.getDepartment());
            if (null == orgTrees) {
                orgTrees = new ArrayList<>();
                userDepMap.put(user.getDepartment(), orgTrees);
            }
            OrgTree orgTree = new OrgTree();
            orgTree.setName(user.getUserName());
            orgTree.setId(user.getId());
            orgTree.setCode(user.getUserCde());
            orgTree.setType(TreeNodeType.USER);
            orgTrees.add(orgTree);
        });
        matchOrgAddUser(sysOrgList, treeList, TreeNode.ROOT, userDepMap);
        orgTreeParent.setTreeNodes(treeList);
        return orgTreeParent;
    }

    /**
     * 递归成公司部门树,再把用户列表挂到部门节点下面
     *
     * @param sysOrgList 所有组织集合
     * @param treeNodes  树集合
     * @param parentId   父节点
     * @param userDepMap 部门id下的用户集合
     * @throws Exception
     */
    private void matchOrgAddUser(List<SysOrg> sysOrgList, List<OrgTree> treeNodes, String parentId,
        Map<String, List<OrgTree>> userDepMap) throws Exception {

        if (null == sysOrgList ||
            sysOrgList.isEmpty() ||
            StringUtil.isEmpty(parentId)) {
            return;
        }
        for (SysOrg sysOrg : sysOrgList) {
            if (!parentId.equals(sysOrg.getParentId())) {
                continue;
            }
            OrgTree orgTree = new OrgTree();
            orgTree.setOriginalType(sysOrg.getType());
            orgTree.setId(sysOrg.getId());
            orgTree.setpId(sysOrg.getParentId());
            orgTree.setName(sysOrg.getOrgName());
            orgTree.setIcon(sysOrg.getIcon());
            orgTree.setIconSkin(sysOrg.getIconSkin());
            orgTree.setOrgId(sysOrg.getId());
            List<OrgTree> child = new ArrayList<>();
            if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                orgTree.setType(TreeNodeType.COMPANY);
            } else {
                orgTree.setType(TreeNodeType.DEPARTMENT);
            }

            // 部门下挂用户
            if (userDepMap.containsKey(sysOrg.getId())) {
                child.addAll(userDepMap.get(sysOrg.getId()));
            }
            matchOrgAddUser(sysOrgList, child, sysOrg.getId(), userDepMap);
            if (!child.isEmpty()) {
                orgTree.setChildren(child);
            }
            treeNodes.add(orgTree);
        }
    }

    /**
     * 删除部门，不删人（同步组织用）
     * @param orgId
     * @throws Exception
     */
    @Override
    public void deleteOrgNoDelUser(String orgId) throws Exception {
        orgDao.deleteSysOrgById(orgId);
        sysOrgRoleDao.deleteSysOrgRoleByOrgId(orgId);
    }
}
