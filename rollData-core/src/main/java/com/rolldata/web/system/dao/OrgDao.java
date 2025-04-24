package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Title: OrgDao
 * @Description: 组织机构Dao层 接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
public interface OrgDao extends JpaRepository<SysOrg, String>, JpaSpecificationExecutor<SysOrg>{
    
    /**
     * 根据parentId查询
     * @param parentId
     * @throws Exception
     */
    @Query("from SysOrg s where s.parentId = :parentId")
    public List<SysOrg> findByParentId(@Param("parentId") String parentId) throws Exception;

    /**
     * 根据id查询
     * @throws Excption
     */
//    @Query("from SysOrg s where s.id = :id")
//    public SysOrg findById(@Param("id") String id) throws Exception;

    /**
     * 根据userId查询(角色状态为可用)(管理界面查询)(不分页)
     * @param userId
     * @return List<SysOrg>
     * @throws Exception
     * @createDate 2018-6-6
     */
    @Query(value="from SysOrg o where o.id in(select r.orgId from SysOrgRole r where r.roleId in(select u.roleId from UserRole u, SysRole srpower where u.roleId = srpower.id and srpower.state = '1' and u.userId = :userId)) order by o.createTime asc ")
    public List<SysOrg> getSysOrgByUserId(@Param("userId") String userId) throws Exception;

    /**
     * 更新
     *
     * @param orgName
     * @param orgCde
     * @param parentId
     * @param updateUser
     * @param updateTime
     * @param id
     * @throws Exception
     * @createDate 2018-6-14
     * @updateDate 2022-8-08
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysOrg o set o.orgName = :orgName, o.orgCde = :orgCde, o.parentId = :parentId,"
        + " o.updateUser = :updateUser, o.updateTime = :updateTime where o.id = :id")
    public void updateOrg(
            @Param("orgName") String orgName,
            @Param("orgCde") String orgCde,
            @Param("parentId") String parentId,
            @Param("updateUser") String updateUser,
            @Param("updateTime") Date updateTime,
            @Param("id") String id
    ) throws Exception;

    /**
     * 查组织id和编码 过滤掉type
     * @param type
     * @return
     */
    @Query("select so from SysOrg so where so.type <> :type")
    public List<SysOrg> queryDistinctByTypeNot(@Param("type") String type);

    /**
     * 根据父节点id和类型查询集合
     * @return
     * @throws Exception
     */
    public List<SysOrg> querySysOrgByParentIdAndType(String parentId, String type) throws Exception;

    /**
     * 查组织名称重复数量
     *
     * @param orgName 公司名称
     * @param type    类型
     * @return
     * @throws Exception
     */
    @Query("select count(*) from SysOrg org where org.orgName = :orgName and org.type = :type")
    public int checkName(@Param("orgName") String orgName, @Param("type") String type) throws Exception;

    /**
     * 查组织名称重复数量, 排查自身id
     *
     * @param orgId  组织id
     * @param orgCde 公司编码
     * @param type   组织类型
     * @return
     * @throws Exception
     */
    @Query("select count(*) from SysOrg org where org.orgCde = :orgCde and org.type = :type and org.id <> :orgId")
    public int checkCode(
            @Param("orgId") String orgId,
            @Param("orgCde") String orgCde,
            @Param("type") String type
    ) throws Exception;

    /**
     * 查组织名称重复数量
     *
     * @param orgCde 公司编码
     * @param type   类型
     * @return
     * @throws Exception
     */
    @Query("select count(*) from SysOrg org where org.orgCde = :orgCde and org.type = :type")
    public int checkCode(
            @Param("orgCde") String orgCde,
            @Param("type") String type
    ) throws Exception;

    /**
     * 查组织名称重复数量, 排查自身id
     *
     * @param orgId  组织id
     * @param orgCde 公司编码
     * @param type   组织类型
     * @return
     * @throws Exception
     */
    @Query("select count(*) from SysOrg org where org.orgCde = :orgCde and org.type = :type and org.id <> :orgId")
    public int checkName(
            @Param("orgId") String orgId,
            @Param("orgCde") String orgCde,
            @Param("type") String type
    ) throws Exception;

    /**
     *  查询全部组织树（无数据权限）
     * @return
     */
    @Query(value="from SysOrg o order by o.createTime asc ")
    public List<SysOrg> queryAllOrgList() throws Exception;

    /**
     * 所有组织,根据类型排序,公司在上面
     *
     * @return 组织集合
     * @throws Exception
     */
    @Query(value="from SysOrg o order by o.type asc ")
    public List<SysOrg> queryAllOrgsOrderByType() throws Exception;

    /**
     * 全部组织树id集合
     *
     * @return
     */
    @Query(value = "select o.id from SysOrg o ")
    List<String> queryAllOrgIdList() throws Exception;

    /**
     * 汇总时, 过滤方案里的组织id
     * @param parentId 父id
     * @param type 类型
     * @param orgIds 汇总方案里勾选的组织id
     * @return
     * @throws Exception
     */
    @Query(value="from SysOrg o where o.type = :type and o.parentId = :parentId and o.id in (:orgIds) ")
    public List<SysOrg> querySysOrgByParentIdAndTypeInOrgIds(@Param("parentId") String parentId, @Param("type") String type, @Param("orgIds") List<String> orgIds) throws Exception;

    /**
     * 根据id删除组织
     * @param orgId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysOrg o where o.id = :orgId")
    public void deleteSysOrgById(@Param("orgId") String orgId) throws Exception;

    /**
     * 根据组织代码查询实体
     * @param orgCde
     * @return
     * @throws Exception
     */
    public SysOrg querySysOrgByOrgCde(String orgCde) throws Exception;

    /**
     * 根据名称查询组织集合
     *
     * @param orgName 组织名称
     * @return
     * @throws Exception
     */
    List<SysOrg> querySysOrgsByOrgName(String orgName) throws Exception;

    /**
     * 根据角色id查询组织集合
     *
     * @param roleId
     * @param id
     * @return
     * @throws Exception
     */
    @Query(value="select org from SysOrg org, SysOrgRole o where org.id = o.orgId and o.roleId = :roleId" +
                 " and o.orgId in (select tor.orgId from SysOrgRole tor where tor.roleId in" +
                 " (select su.roleId from UserRole su where su.userId = :userId)) ")
    public List<SysOrg> findOrgIdsByRoleId(@Param("roleId") String roleId, @Param("userId") String userId) throws Exception;

    /**
     * 角色id对应所有org集合
     *
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    @Query(value="select org from SysOrg org, SysOrgRole o where org.id = o.orgId and o.roleId = :roleId")
    public List<SysOrg> findOrgIdsByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 根据类型查询组织集合
     * @param type
     * @return
     * @throws Exception
     */
    @Query(value="from SysOrg o where o.type = :type ")
    public List<SysOrg> queryAllSysOrgByType(@Param("type") String type) throws Exception;

    /**
     * 根据组织编码查询组织详细信息
     * @param orgCode
     * @return
     */
    @Query(value="from SysOrg o where o.orgCde = :orgCode ")
	public SysOrg querySysOrgByOrgCode(@Param("orgCode") String orgCode) throws Exception;


    @Modifying(clearAutomatically = true)
    @Query("update SysOrg o set o.parentId = :parentId, o.updateUser = :updateUser,"
        + " o.updateTime = :updateTime where o.id = :id")
    public void updateOrgParentId(@Param("id") String orgId, @Param("parentId") String parentId, @Param("updateUser") String updateUser,
                                  @Param("updateTime") Date updateTime);

    /**
     * 根据id查询实体
     *
     * @param id 主键
     * @return 组织
     */
    SysOrg queryEntityById(String id);

    /**
     * 查询表单发布过的公司部门(2022-6-30 发现好似有点问题,没带组织的上一级,后来商量先这样 )
     *
     * @param formId 表单id
     * @param userId 当前登录用户
     * @return
     */
    @Query(value="select * from wd_sys_org t, ( select COMPANY_ID as orgid from"
        + " wd_report_fill_task_org oleft , wd_report_fill_task_user luser where"
        + " oleft.id  = luser.fill_org_id and oleft.form_id = :formId and luser.user_id = :userId"
        + " union select DEPARTMENT_ID as orgid from wd_report_fill_task_org oright ,"
        + " wd_report_fill_task_user ruser where oright.id = ruser.fill_org_id and "
        + "oright.form_id = :formId and ruser.user_id = :userId) rel where t.id = rel.orgid order by t.create_time asc ", nativeQuery = true)
    List<SysOrg> queryFormOrgs(@Param("formId") String formId, @Param("userId") String userId);

    @Query(value="from SysOrg o where o.type = :type and o.isSync = :isSync ")
    public List<SysOrg> queryAllSysOrgByTypeAndSync(@Param("type") String type, @Param("isSync") String isSync) throws Exception;

    @Query(value="select count(o.id) from wd_sys_org o where o.id in (select t.company_id from wd_report_fill_task_org t union all select t.department_id from wd_report_fill_task_org t where t.department_id is not null ) ", nativeQuery = true)
    public int queryDsAllSubmitOrgNum();

}
