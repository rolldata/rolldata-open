package com.rolldata.web.system.service.impl;

import com.rolldata.core.util.ContextHolderUtils;
import com.rolldata.core.util.IpUtils;
import com.rolldata.core.util.LogUtil;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.dao.UserOnlineDao;
import com.rolldata.web.system.entity.SysUserOnline;
import com.rolldata.web.system.security.shiro.UserRealm.Principal;
import com.rolldata.web.system.service.UserOnlineService;
import com.rolldata.web.system.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userOnlineService")
@Transactional
public class UserOnlineServiceImpl  implements UserOnlineService {
	
	@Autowired
    private UserOnlineDao userOnlineDao;
	
	/**
	 * 上线
	 *
	 * @param userOnline
	 */
	public void updateOnline(SysUserOnline userOnline)  throws Exception{
		if (StringUtil.isEmpty(userOnline.getHost())) {
			String hostIp = IpUtils.getIpAddr(ContextHolderUtils.getRequest());
			userOnline.setHost(hostIp);
		}
		Principal principal = UserUtils.getPrincipal(); // 如果已经登录，则跳转到管理首页
		userOnline.setUserId(principal.getId());
		SysUserOnline oldOnline = getSysUserByUserId(userOnline.getUserId());
		if (oldOnline != null) {
			userOnline.setUpdateTime(new Date());
			userOnline.setUpdateUser(principal.getId());
			userOnlineDao.updateSysOnlineUser(principal.getId(),userOnline.getHost(),userOnline.getSystemHost(), userOnline.getUserAgent(), userOnline.getStatus(), userOnline.getStartTimestamp(), userOnline.getLastAccessTime(), userOnline.getTimeout(), userOnline.getSession(), userOnline.getRemarks(), userOnline.getUpdateTime(), userOnline.getUpdateUser());
		} else {
			userOnline.setCreateTime(new Date());
			userOnline.setCreateUser(principal.getId());
			userOnlineDao.saveAndFlush(userOnline);
		}
	}

	/**
	 * 下线
	 *
	 * @param sid
	 */
	public void deleteOffline(String sid)  throws Exception{
		SysUserOnline userOnline = userOnlineDao.getOneById(sid);
		if (userOnline != null) {
			userOnlineDao.delete(userOnline);
			LogUtil.info(userOnline.getUserId()+"超时退出");
		}
	}
	/**
	 * 根据id查实体
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public SysUserOnline getUserOnline(String sid)  throws Exception{
		return userOnlineDao.getOneById(sid);
	}
	
	/**
	 * 批量下线
	 *
	 * @param needOfflineIdList
	 */
	public void deleteBatchOffline(List<?> needOfflineIdList) throws Exception {
		for (Object id : needOfflineIdList) {
			SysUserOnline userOnline = userOnlineDao.getOneById((String)id);
			userOnlineDao.deleteSysUserOnlineById((String) id);
			LogUtil.info(userOnline.getUserId()+"超时退出");
		}
	}

	/**
	 * 无效的UserOnline
	 *
	 * @return
	 */
	public Page<SysUserOnline> findExpiredUserOnlineList(Date expiredDate, int page, int rows) throws Exception {
//		Long total = userOnlineDao.countByHql(expiredDate);
		Pageable pageable = PageRequest.of(page, rows);
//		List<SysUserOnline> content = userOnlineDao.findAllByLastAccessTime(expiredDate);
//		return new PageImpl<SysUserOnline>(content, pageable, total);
		Page<SysUserOnline> list = userOnlineDao.findAllByLastAccessTime(expiredDate,pageable);
		return list;
	}

	
	public SysUserOnline getSysUserByUserId(String userId) throws Exception {
		return userOnlineDao.findSysUserByUserId(userId);
	}
	
	public void update(SysUserOnline userOnline) throws Exception {
		Principal principal = UserUtils.getPrincipal(); // 如果已经登录，则跳转到管理首页
		userOnline.setUpdateTime(new Date());
		userOnline.setUpdateUser(principal.getId());
		userOnlineDao.updateSysOnlineUser(principal.getId(),userOnline.getHost(),userOnline.getSystemHost(), userOnline.getUserAgent(), userOnline.getStatus(), userOnline.getStartTimestamp(), userOnline.getLastAccessTime(), userOnline.getTimeout(), userOnline.getSession(), userOnline.getRemarks(), userOnline.getUpdateTime(), userOnline.getUpdateUser());
	}

	/**
	 * 退出登陆删除在线状态
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public void deleteSysUserOnlineByUserId(String userId) throws Exception {
		userOnlineDao.deleteSysUserOnlineByUserId(userId);
	}

}
