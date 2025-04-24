package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysMessage;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** 
 * @Title: SysMessageDao
 * @Description: Dao层 接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/19
 * @version V1.0  
 */
public interface SysMessageDao extends JpaRepository<SysMessage, String>,JpaSpecificationExecutor<SysMessage> {

	/**
	 * 查询个人所有消息
	 * @param toUser
	 * @param pageable 
	 * @return
	 * @throws Exception
	 */
	@Query(value="select s from SysMessage s where s.toUser = :toUser order by s.state asc, s.createTime desc",
			countQuery="select count(s) from SysMessage s where s.toUser = :toUser")
	public Page<SysMessage> querySysMessageByToUser(@Param("toUser")String toUser, Pageable pageable) throws Exception;

	@Modifying(clearAutomatically = true)
    @Query("delete from SysMessage s where s.toUser = :toUser")
	public void deleteSysMessageByToUser(@Param("toUser")String toUser) throws Exception;

	@Modifying(clearAutomatically = true)
    @Query("update SysMessage s set s.state = :state,s.updateUser = :updateUser,s.updateTime = :updateTime where s.id = :id")
	public void updateSysMessageStateById(@Param("state")String state, @Param("updateTime")Date updateTime,@Param("updateUser")String updateUser, @Param("id")String id);
	
	/**
	 * 更新一批消息的状态
	 *
	 * @param state      状态
	 * @param updateTime 更新时间
	 * @param updateUser 更新人
	 * @param ids        消息id集合
	 */
	@Modifying(clearAutomatically = true)
	@Query("update SysMessage s set s.state = :state,s.updateUser = :updateUser,s.updateTime = :updateTime where s.id in (:ids)")
	public void updateSysMessageStateByIds(
		@Param("state") String state,
		@Param("updateTime") Date updateTime,
		@Param("updateUser") String updateUser,
		@Param("ids") List<String> ids
	);
	
	/**
     * 查询个人所有消息数量
     *
     * @param toUser 登录用户
     * @return 消息数量
     * @throws Exception
     */
    @Query(value="select count(s) from SysMessage s where s.toUser = :toUser and s.state = '0'")
	int queryPersonalMessageNum(@Param("toUser")String toUser) throws Exception;
	
	/**
	 * 查询信息
	 *
	 * @return
	 * @throws Exception
	 */
	@Query(value="select s from SysMessage s where s.type not in ('0', '5') and s.type = (" +
		"select s.type from SysMessage s where s.id = :id) and s.toUser = :toUser and s.state = '0'")
	List<SysMessage> querySysMessageById(@Param("id")String id, @Param("toUser")String toUser) throws Exception;
}
