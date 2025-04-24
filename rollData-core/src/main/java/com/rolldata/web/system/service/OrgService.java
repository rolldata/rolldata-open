package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysOrg;
import com.rolldata.web.system.pojo.*;

import java.util.List;

/**
 * @Title: OrgService
 * @Description: 组织机构
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
public interface OrgService {

    /**
     * 保存
     * @param sysOrgJson
     * @throws Exception
     */
    public ResponseOrgJson save(RequestOrgJson sysOrgJson) throws Exception;

    /**
     * 修改
     * @param sysOrgJson
     * @throws Exception
     */
    public ResponseOrgJson update(RequestOrgJson sysOrgJson) throws Exception;

    /**
     * 删除部门，包括部门下的人
     * @param orgId
     * @throws Exception
     */
    public void deleteOrg(String orgId) throws Exception;

    /**
     * 通过Id获取组织详情
     * @param id
     * @throws Exception
     */
    public OrgDetailedJson getOrgDetailed(String id) throws Exception;

    /**
     * 唯一实体
     * @param orgId
     * @throws Exception
     */
    public SysOrg getSysOrgById(String orgId) throws Exception;

    /**
     * 组织管理树
     * @throws Exception
     */
    public List<RoleOrgList> orgTreeList() throws Exception;

    /**
     * 组织权限树
     *
     * @param dataOwn 数据归属
     * @return
     * @throws Exception
     */
    public OrgTreeParent orgLimitTreeList(String dataOwn) throws Exception;

    /**
     * 根据parentId查询记录集合
     * @param orgId
     * @throws Exception
     */
    public List<SysOrg> findByParentId(String orgId) throws Exception;

    /**
     * 级联删除
     * @param orgId
     * @param delIds
     * @param type
     * @throws Exception
     */
    public void deleteRecursiveMethod(String orgId, List<String> delIds, String type) throws Exception;

    /**
     * 拼接返回对象ResponseOrgJson
     * @throws Exception
     */
    public ResponseOrgJson changeResponseUserJson(SysOrg sysOrg) throws Exception;

    /**
     *  组织名称是否重复数量
     * @param orgName 组织名称
     * @return
     * @throws Exception
     */
    public int checkName(String orgName) throws Exception;

    /**
     * 修改时组织名称是否重复数量
     * @param orgId 组织id
     * @param orgName 组织名称
     * @return
     * @throws Exception
     */
    public int checkName(String orgId, String orgName) throws Exception;

    /**
     * 组织名称是否重复数量
     *
     * @param orgCde 组织编码
     * @return
     * @throws Exception
     */
    public int checkCode(String orgCde) throws Exception;

    /**
     * 修改时组织名称是否重复数量
     *
     * @param orgId   组织id
     * @param orgCde 组织编码
     * @return
     * @throws Exception
     */
    public int checkCode(String orgId, String orgCde) throws Exception;

    /**
     * 查询组织实体根据组织代码
     * @param code 组织代码
     * @return
     * @throws Exception
     */
    public SysOrg queryOrgByCode(String code) throws Exception;

    /**
     * 查询用户相应的数据权限
     * <br>权限：角色的数据权限加用户本身职务id
     *
     * @param userCode 用户code
     * @return
     * @throws Exception
     */
    public List<String> queryDataRights(String userCode) throws Exception;

    /**
     * 查询当前登录用户数据权限
     *
     * @return
     * @throws Exception
     */
    public List<SysOrg> querySysOrgList() throws Exception;

    /**
     * 查询组织权限公司树
     *
     * @return
     * @throws Exception
     */
    public OrgTreeParent queryRoleCompanyTree() throws Exception;

    /**
     * 查询角色权限公司下的部门
     *
     * @param parentId 公司id
     * @return
     * @throws Exception
     */
    public List<OrgTree> queryRoleDepartments(String parentId) throws Exception;

    /**
     * 查询全部组织
     *
     * @return
     * @throws Exception
     */
    public List<SysOrg> queryAllSysOrg() throws Exception;

    /**
     * 根据类型查询组织集合
     * @param type
     * @return
     * @throws Exception
     */
    public List<SysOrg> queryAllSysOrgByType(String type) throws Exception;

    /**
     * 新增组织（同步组织用）
     * @param sysOrgJson
     * @throws Exception
     */
    public SysOrg saveOrg(RequestOrgJson sysOrgJson) throws Exception;

    /**
     * 根据组织编码查询组织详细信息
     * @param orgCode
     * @return
     * @throws Exception
     */
    public SysOrg querySysOrgByOrgCode(String orgCode) throws Exception;

    /**
     * 根据组织id修改组织的父级
     * @param sysOrgJson
     * @throws Exception
     */
    public void updateOrgParentId(RequestOrgJson sysOrgJson) throws Exception;

    /**
     * 组织部门树（无数据权限）
     * @throws Exception
     */
    public List<RoleOrgList> orgTreeListNoDataPermission() throws Exception;

    /**
     * 查询公司树，所有公司类型，无数据权限
     *
     * @return
     * @throws Exception
     */
    public OrgTreeParent queryCompanyTreeNoDataPermission() throws Exception;

    /**
     * 查询公司部门用户数树
     *
     * @return
     * @throws Exception
     */
    OrgTreeParent queryOrgUserTree() throws Exception;

    /**
     * 删除部门，不删人（同步组织用）
     * @param orgId
     * @throws Exception
     */
    public void deleteOrgNoDelUser(String orgId) throws Exception;
}
