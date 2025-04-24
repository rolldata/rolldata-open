package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysUserOnline;
import com.rolldata.web.system.security.session.OnlineSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * 
 * @Title:UserOnlineDao
 * @Description:在线用户工厂类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-30
 * @version V1.0
 */
public interface UserOnlineDao extends JpaRepository<SysUserOnline, String>{
	
//	@Query("select count(*) from UserOnline o where o.lastAccessTime < :expiredDate order by o.lastAccessTime asc") 
//	public Long countByHql(@Param("expiredDate") Date expiredDate);
	
//	@Query("select * from UserOnline o where o.lastAccessTime < :expiredDate order by o.lastAccessTime asc") 
//	public List<SysUserOnline> findAllByLastAccessTime(@Param("expiredDate") Date expiredDate);

	/**
	 * 查询全部带分页
	 * @param expiredDate
	 * @param pageable
	 * @return
	 */
	@Query(value = "from SysUserOnline o where o.lastAccessTime < :expiredDate order by o.lastAccessTime asc",
			    countQuery = "select count(*) from SysUserOnline o where o.lastAccessTime < :expiredDate ")
	public Page<SysUserOnline> findAllByLastAccessTime(@Param("expiredDate") Date expiredDate,
                                                       Pageable pageable) throws Exception;

	@Query(value = "from SysUserOnline u where u.userId = :userId")
	public SysUserOnline findSysUserByUserId(@Param("userId") String userId) throws Exception;

	@Modifying(clearAutomatically = true)
	@Query(value = "update SysUserOnline u set u.host = :host,u.systemHost = :systemHost,u.userAgent = :userAgent,u.status =:status,u.startTimestamp = :startTimestamp,u.lastAccessTime = :lastAccessTime,u.timeout = :timeout,u.session = :session,u.updateTime = :updateTime,u.updateUser = :updateUser,u.remarks = :remarks where u.userId = :userId")
	public void updateSysOnlineUser(@Param("userId") String userId, @Param("host") String host, @Param("systemHost") String systemHost, @Param("userAgent") String userAgent, @Param("status") OnlineSession.OnlineStatus status, @Param("startTimestamp") Date startTimestamp, @Param("lastAccessTime") Date lastAccessTime, @Param("timeout") Long timeout, @Param("session") OnlineSession session, @Param("remarks") String remarks, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser) throws Exception;

	public SysUserOnline getOneById(String sid) throws Exception;

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from SysUserOnline u where u.id = :id")
	public void deleteSysUserOnlineById(@Param("id") String id) throws Exception;

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from SysUserOnline u where u.userId = :userId")
	public void deleteSysUserOnlineByUserId(@Param("userId") String userId) throws Exception;
}
