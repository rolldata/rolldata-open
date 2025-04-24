package com.rolldata.web.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rolldata.web.system.entity.SysRoleHomePage;

/**
 * 
 * @Title: SysRoleHomePageDao
 * @Description: 首页配置数据访问类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月8日
 * @version V1.0
 */
public interface SysRoleHomePageDao  extends JpaRepository<SysRoleHomePage, String>{
	
	/**
	 * 根据类型删除数据
	 * @param type
	 */
	@Modifying(clearAutomatically = true)
    @Query("delete from SysRoleHomePage s where s.type = :type and s.terminalType = :terminalType")
	public void deleteHomePageByType(
	    @Param("type")String type,
        @Param("terminalType") String terminalType
    ) throws Exception;

	@Query("select s from SysRoleHomePage s where s.roleId in (:rolesIds) and s.terminalType = :terminalType"
        + " order by cast(s.type as int) asc")
	public List<SysRoleHomePage> queryHomepageManageInfoByRoleId(
	    @Param("rolesIds") List<String> rolesIdList,
        @Param("terminalType") String terminalType
    ) throws Exception;

    /**
     * 根据终端类型查询
     *
     * @param terminalType 终端类型
     * @return
     */
	@Query("select s from SysRoleHomePage s where s.terminalType = :terminalType")
	List<SysRoleHomePage> queryEntitiesByTerminalType(@Param("terminalType") String terminalType) throws Exception;
}
