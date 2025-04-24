package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysUserOnline;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface UserOnlineService {

	/**
	 * 上线
	 *
	 * @param userOnline
	 */
	public void updateOnline(SysUserOnline userOnline)  throws Exception;

	/**
	 * 下线
	 *
	 * @param sid
	 */
	public void deleteOffline(String sid)  throws Exception;
	/**
	 * 根据id查实体
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public SysUserOnline getUserOnline(String sid)  throws Exception;

	/**
	 * 批量下线
	 *
	 * @param needOfflineIdList
	 */
	public void deleteBatchOffline(List<?> needOfflineIdList)  throws Exception;

	/**
	 * 无效的UserOnline
	 *
	 * @return
	 */
	public Page<SysUserOnline> findExpiredUserOnlineList(Date expiredDate, int page, int rows)  throws Exception;

	public SysUserOnline getSysUserByUserId(String userId)  throws Exception;

	public void update(SysUserOnline online) throws Exception;

	/**
	 * 退出登陆删除在线状态
	 * @param userId
	 * @throws Exception
	 */
	public void deleteSysUserOnlineByUserId(String userId) throws Exception;
}
